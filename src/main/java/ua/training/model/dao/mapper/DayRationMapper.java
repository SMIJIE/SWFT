package ua.training.model.dao.mapper;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.ModelAndView;
import ua.training.constant.Mess;
import ua.training.controller.controllers.exception.DataHttpException;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.User;
import ua.training.model.entity.form.FormDayRationComposition;

import java.time.LocalDate;
import java.time.Period;

import static java.util.Objects.isNull;

@Log4j2
public class DayRationMapper implements ObjectMapper<DayRation, FormDayRationComposition> {
    /**
     * @param lifeStyleCoefficient Integer
     * @param weight               Integer
     * @param height               Integer
     * @param age                  Integer
     * @return amount of calories calculate by formula
     */
    public static Integer formulaMifflinSanJerura(Integer lifeStyleCoefficient,
                                                  Integer weight,
                                                  Integer height,
                                                  Integer age) {

        if (weight != 0) {
            return Math.toIntExact(Math.round(((double) lifeStyleCoefficient / 1000) *
                    ((double) 10 * ((double) weight / 1000) + 6.25 *
                            ((double) height / 100) - ((double) 5 * age))));
        }

        return 0;
    }

    /**
     * Extract entity 'DayRation' from HTTP form
     *
     * @param formDRC      {@link FormDayRationComposition}
     * @param modelAndView {@link ModelAndView}
     * @return new {@link DayRation}
     * @throws DataHttpException
     */
    @Override
    public DayRation extractEntityFromHttpForm(FormDayRationComposition formDRC, ModelAndView modelAndView) throws DataHttpException {
        User user = formDRC.getUser();
        Integer usersAge;
        LocalDate date;
        LocalDate localDate = LocalDate.now();
        Integer userCalories;
        Integer userCaloriesDesired;

        Period period = Period.between(user.getDob(), localDate);
        usersAge = period.getYears();

        date = isNull(formDRC.getDate()) ? localDate : formDRC.getDate();
        userCalories = formulaMifflinSanJerura(user.getLifeStyleCoefficient(),
                user.getWeight(), user.getHeight(), usersAge);
        userCaloriesDesired = formulaMifflinSanJerura(user.getLifeStyleCoefficient(),
                user.getWeightDesired(), user.getHeight(), usersAge);

        DayRation dayRation = DayRation.builder()
                .date(date)
                .user(user)
                .userCalories(userCalories * 1000)
                .userCaloriesDesired(userCaloriesDesired * 1000)
                .build();

        checkByRegex(dayRation, modelAndView);

        return dayRation;
    }

    /**
     * Check day ration fields by regex
     *
     * @param dayRation    {@link DayRation}
     * @param modelAndView {@link ModelAndView}
     * @throws DataHttpException
     */
    @Override
    public void checkByRegex(DayRation dayRation, ModelAndView modelAndView) throws DataHttpException {
        boolean flag = true;
        LocalDate localDate = LocalDate.now();
        Period period = Period.between(dayRation.getDate(), localDate);

        if (period.getDays() > 14 || period.getDays() < -14) {
            modelAndView.addObject(PAGE_USER_ERROR, DAY_RATION_VALID_DATE);
            flag = false;
        } else if (period.getDays() == period.getDays() && period.getMonths() != 0) {
            modelAndView.addObject(PAGE_USER_ERROR, DAY_RATION_VALID_DATE);
            flag = false;
        }

        if (!flag) {
            throw new DataHttpException(Mess.LOG_DAY_RATION_HTTP_NOT_EXTRACT);
        }
    }
}
