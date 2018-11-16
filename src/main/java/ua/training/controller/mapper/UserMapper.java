package ua.training.controller.mapper;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import ua.training.controller.exception.DataHttpException;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.Roles;
import ua.training.model.entity.form.FormUser;
import ua.training.model.utility.PasswordEncoder;

import java.time.LocalDate;
import java.time.Period;

@Component
public class UserMapper implements ObjectMapper<User, FormUser> {
    /**
     * Extract entity 'User' from HTTP form
     *
     * @param formUser     {@link FormUser}
     * @param modelAndView {@link ModelAndView}
     * @return new {@link User}
     * @throws DataHttpException
     */
    @Override
    public User extractEntityFromHttpForm(FormUser formUser, ModelAndView modelAndView) throws DataHttpException {
        String email;
        String password;
        int height;
        int weight;
        int weightDesired;
        int lifeStyleCoefficient;
        boolean flagPassword = formUser.getPassword().isEmpty();

        email = formUser.getEmail().toLowerCase();
        password = flagPassword ? "" : PasswordEncoder.encodePassword(formUser.getPassword());
        height = (int) (formUser.getHeight() * 100);
        weight = (int) (formUser.getWeight() * 1000);
        weightDesired = (int) (formUser.getWeightDesired() * 1000);
        lifeStyleCoefficient = (int) (formUser.getLifeStyleCoefficient() * 1000);

        User user = User.builder()
                .name(formUser.getName())
                .dob(formUser.getDob())
                .email(email)
                .password(password)
                .role(Roles.USER)
                .height(height)
                .weight(weight)
                .weightDesired(weightDesired)
                .lifeStyleCoefficient(lifeStyleCoefficient)
                .build();

        checkByRegex(user, modelAndView);

        return user;
    }

    /**
     * Check users fields by regex
     *
     * @param user         {@link User}
     * @param modelAndView {@link ModelAndView}
     * @throws DataHttpException
     */
    @Override
    public void checkByRegex(User user, ModelAndView modelAndView) throws DataHttpException {
        LocalDate localDate = LocalDate.now();
        Period period = Period.between(user.getDob(), localDate);
        boolean flag = true;

        if (!(user.getName().matches(USER_NAME_PATTERN))) {
            modelAndView.addObject(PAGE_USER_ERROR, USER_VALID_NAME_WRONG);
            flag = false;
        } else if ((period.getYears() < 15 || period.getYears() > 99)) {
            modelAndView.addObject(PAGE_USER_ERROR, USER_VALID_DOB_AGE_BETWEEN);
            flag = false;
        } else if (!(user.getEmail().matches(USER_EMAIL_PATTERN))) {
            modelAndView.addObject(PAGE_USER_ERROR, USER_VALID_EMAIL_WRONG);
            flag = false;
        } else if (user.getHeight() < 50 * 100 || user.getHeight() > 250 * 100) {
            modelAndView.addObject(PAGE_USER_ERROR, USER_VALID_HEIGHT_SIZE);
            flag = false;
        } else if (user.getWeight() < 50 * 1000 || user.getWeight() > 150 * 1000) {
            modelAndView.addObject(PAGE_USER_ERROR, USER_VALID_WEIGHT_SIZE);
            flag = false;
        } else if (user.getWeightDesired() < 50 * 1000 || user.getWeightDesired() > 150 * 1000) {
            modelAndView.addObject(PAGE_USER_ERROR, USER_VALID_WEIGHT_SIZE);
            flag = false;
        }

        if (!flag) {
            throw new DataHttpException(LOG_USER_HTTP_FORM);
        }
    }
}