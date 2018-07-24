package ua.training.model.dao.mapper;

import lombok.extern.log4j.Log4j2;
import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.controller.commands.exception.DataHttpException;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.Period;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import static java.util.Objects.isNull;

@Log4j2
public class DayRationMapper implements ObjectMapper<DayRation> {

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
     * @param req HttpServletRequest
     * @return new UserDayRation
     */
    @Override
    public DayRation extractFromHttpServletRequest(HttpServletRequest req) throws DataHttpException {
        User user;
        Integer usersAge;
        LocalDate date;
        Integer userCalories;
        Integer userCaloriesDesired;
        LocalDate localDate = LocalDate.now();

        user = (User) req.getSession().getAttribute(Attributes.REQUEST_USER);
        Period period = Period.between(user.getDob(), localDate);
        usersAge = period.getYears();
        String tempDate = req.getParameter(Attributes.REQUEST_DATE);

        date = isNullOrEmpty(tempDate) ? localDate : LocalDate.parse(tempDate);
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

        checkByRegex(dayRation);

        return dayRation;
    }

    /**
     * Check UserDayRation by regex
     *
     * @param dayRation UserDayRation
     * @throws DataHttpException
     */
    @Override
    public void checkByRegex(DayRation dayRation) throws DataHttpException {
        boolean flag = true;
        LocalDate localDate = LocalDate.now();
        Period period = Period.between(dayRation.getDate(), localDate);

        if (period.getDays() > 14 || period.getDays() < -14) {
            flag = false;
        } else if (period.getDays() == period.getDays() && period.getMonths() != 0) {
            flag = false;
        } else if (isNull(dayRation.getUser())) {
            flag = false;
        }

        if (!flag) {
            throw new DataHttpException(Mess.LOG_DAY_RATION_HTTP_NOT_EXTRACT);
        }
    }
}
