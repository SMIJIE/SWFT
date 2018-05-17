package ua.training.model.dao.service.implementation;

import ua.training.model.dao.DayRationDao;
import ua.training.model.dao.service.DayRationService;
import ua.training.model.entity.DayRation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class DayRationServiceImp implements DayRationService {
    private DayRationDao dayRationDao = DAO_FACTORY.createDayRationDao();

    @Override
    public void insertNewDayRation(DayRation entity) {
        dayRationDao.create(entity);
    }

    @Override
    public Optional<DayRation> checkDayRationByDateAndUserId(LocalDate localDate,
                                                             Integer idUser) {
        return dayRationDao.checkDayRationByDateAndUserId(localDate, idUser);
    }

    @Override
    public List<DayRation> getMonthlyDayRationByUser(Integer monthVal,
                                                     Integer year,
                                                     Integer userId) {
        return dayRationDao.getMonthlyDayRationByUser(monthVal, year, userId);
    }
}
