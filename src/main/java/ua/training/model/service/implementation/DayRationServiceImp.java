package ua.training.model.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.training.model.dao.implemation.HDayRationDao;
import ua.training.model.entity.DayRation;
import ua.training.model.service.DayRationService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DayRationServiceImp implements DayRationService {
    @Autowired
    private HDayRationDao hDayRationDao;

    @Override
    public void insertNewDayRation(DayRation entity) {
        hDayRationDao.create(entity);
    }

    @Override
    public Optional<DayRation> checkDayRationByDateAndUserId(LocalDate localDate,
                                                             Integer idUser) {

        return hDayRationDao.checkDayRationByDateAndUserId(localDate, idUser);
    }

    @Override
    public List<DayRation> getMonthlyDayRationByUser(Integer monthVal,
                                                     Integer year,
                                                     Integer userId) {

        return hDayRationDao.getMonthlyDayRationByUser(monthVal, year, userId);
    }
}
