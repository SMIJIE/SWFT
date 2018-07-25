package ua.training.model.dao.mapper;

import lombok.extern.log4j.Log4j2;
import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.constant.RegexExpress;
import ua.training.controller.commands.exception.DataHttpException;
import ua.training.model.dao.utility.PasswordEncoder;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.Roles;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.Period;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;

@Log4j2
public class UserMapper implements ObjectMapper<User> {

    /**
     * @param req HttpServletRequest
     * @return new User
     */
    @Override
    public User extractFromHttpServletRequest(HttpServletRequest req) throws DataHttpException {
        String name;
        LocalDate dob;
        String email;
        String password;
        Roles role;
        double height;
        double weight;
        double weightDesired;
        double lifeStyleCoefficient;
        String wd;
        String tempPassword;

        name = req.getParameter(Attributes.REQUEST_NAME);
        dob = LocalDate.parse(req.getParameter(Attributes.REQUEST_DATE_OF_BIRTHDAY));
        email = req.getParameter(Attributes.REQUEST_EMAIL).toLowerCase();
        tempPassword = req.getParameter(Attributes.REQUEST_PASSWORD);
        password = isNullOrEmpty(tempPassword) ? "" : PasswordEncoder.encodePassword(tempPassword);
        role = Roles.USER;
        height = Double.valueOf(req.getParameter(Attributes.REQUEST_HEIGHT));
        weight = Double.valueOf(req.getParameter(Attributes.REQUEST_WEIGHT));
        wd = req.getParameter(Attributes.REQUEST_WEIGHT_DESIRED).equals("") ? "0" : req.getParameter(Attributes.REQUEST_WEIGHT_DESIRED);
        weightDesired = Double.valueOf(wd);
        lifeStyleCoefficient = Double.valueOf(req.getParameter(Attributes.REQUEST_LIFESTYLE));

        User user = User.builder()
                .name(name)
                .dob(dob)
                .email(email)
                .password(password)
                .role(role)
                .height((int) (height * 100))
                .weight((int) (weight * 1000))
                .weightDesired((int) (weightDesired * 1000))
                .lifeStyleCoefficient((int) (lifeStyleCoefficient * 1000))
                .build();

        checkByRegex(user);

        return user;
    }

    /**
     * Check users by regex
     *
     * @param user User
     * @throws DataHttpException
     */
    @Override
    public void checkByRegex(User user) throws DataHttpException {
        LocalDate localDate = LocalDate.now();

        Period period = Period.between(user.getDob(), localDate);
        boolean flag = true;

        if (!(user.getName().matches(RegexExpress.USER_NAME_US)
                || user.getName().matches(RegexExpress.USER_NAME_US2)
                || user.getName().matches(RegexExpress.USER_NAME_UA)
                || user.getName().matches(RegexExpress.USER_NAME_UA2))) {
            flag = false;
        } else if ((period.getYears() < 15
                || period.getYears() > 99)) {
            flag = false;
        } else if (!(user.getEmail().matches(RegexExpress.USER_EMAIL)
                || user.getEmail().matches(RegexExpress.USER_EMAIL2)
                || user.getEmail().matches(RegexExpress.USER_EMAIL3))) {
            flag = false;
        } else if ((user.getHeight() / 100) < 50
                || (user.getHeight() / 100) > 250) {
            flag = false;
        } else if ((user.getWeight() / 1000) < 50
                || (user.getWeight() / 1000) > 150) {
            flag = false;
        } else if ((user.getWeightDesired() != 0) && ((user.getWeightDesired() / 1000) < 50
                || (user.getWeightDesired() / 1000) > 150)) {
            flag = false;
        }

        if (!flag) {
            throw new DataHttpException(Mess.LOG_USER_HTTP_NOT_EXTRACT);
        }
    }
}
