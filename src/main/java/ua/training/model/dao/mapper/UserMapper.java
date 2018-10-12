package ua.training.model.dao.mapper;

import org.springframework.web.servlet.ModelAndView;
import ua.training.controller.commands.exception.DataHttpException;
import ua.training.model.dao.utility.PasswordEncoder;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.Roles;
import ua.training.model.entity.form.FormUser;

import java.time.LocalDate;
import java.time.Period;

public class UserMapper implements ObjectMapper<User> {
    /**
     * Extract entity 'User' from HTTP form
     *
     * @param formUser     {@link FormUser}
     * @param modelAndView {@link ModelAndView}
     * @return new {@link User}
     * @throws DataHttpException
     */
    public User extractUserFromHttpForm(FormUser formUser, ModelAndView modelAndView) throws DataHttpException {
        String email;
        String password;
        int height;
        int weight;
        int weightDesired;
        int lifeStyleCoefficient;

        email = formUser.getEmail().toLowerCase();
        password = PasswordEncoder.encodePassword(formUser.getPassword());
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

    /**
     * Check every users http input by regex separately
     *
     * @param object {@link Object}
     * @param regex  {@link String}
     * @param min    {@link Double}
     * @param max    {@link Double}
     * @return mess {@link String}
     */
    public String checkByAjaxUserParamFromHttpFormByRegex(Object object,
                                                          String regex,
                                                          Double min,
                                                          Double max) {
        if (object.getClass() == String.class) {
            String temp = (String) object;
            if (temp.matches(regex)) {
                return "";
            }
        } else if (object.getClass() == Double.class) {
            Double temp = (Double) object;
            if (temp >= min && temp <= max) {
                return "";
            }
        } else if (object.getClass() == LocalDate.class) {
            LocalDate localDate = LocalDate.now();
            Period period = Period.between((LocalDate) object, localDate);
            if ((period.getYears() >= min && period.getYears() <= max)) {
                return "";
            }
        }

        return AJAX_MESS_ERROR;
    }
}
