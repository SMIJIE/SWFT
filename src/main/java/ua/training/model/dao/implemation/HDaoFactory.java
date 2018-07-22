package ua.training.model.dao.implemation;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import ua.training.model.dao.*;

@Log4j2
public class HDaoFactory extends DaoFactory {
    /**
     * The main runtime interface between a Java application and Hibernate.
     *
     * @see Session
     */
    private Session session = HSesssionFactory.getSession();

    @Override
    public UserDao createUserDao() {
        return new HUserDao(session);
    }

    @Override
    public DishDao createDishDao() {
        return new HDishDao(session);
    }

    @Override
    public DayRationDao createDayRationDao() {
        return new HDayRationDao(session);
    }

    @Override
    public RationCompositionDao createRationCompositionDao() {
        return new HRationCompositionDao(session);
    }
}
