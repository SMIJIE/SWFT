package ua.training.model.dao;

import ua.training.model.entity.DayRation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DayRationDao extends GenericDao<DayRation> {
    Optional<DayRation> checkDayRationByDateAndUserId(LocalDate localDate,
                                                      Integer integer);

    List<DayRation> getMonthlyDayRationByUser(Integer monthVal,
                                              Integer year,
                                              Integer userId);
}
