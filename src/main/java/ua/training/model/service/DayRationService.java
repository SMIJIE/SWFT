package ua.training.model.service;

import ua.training.model.entity.DayRation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DayRationService {
    void insertNewDayRation(DayRation entity);

    Optional<DayRation> checkDayRationByDateAndUserId(LocalDate localDate,
                                                      Integer idUser);

    List<DayRation> getMonthlyDayRationByUser(Integer monthVal,
                                              Integer year,
                                              Integer userId);
}
