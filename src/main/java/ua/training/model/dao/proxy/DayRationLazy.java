package ua.training.model.dao.proxy;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.implemation.JDBCRationCompositionDao;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.RationComposition;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: This is the proxy to get some parameters
 * {@link ua.training.model.entity.DayRation#compositions}
 *
 * @author Zakusylo Pavlo
 */
public class DayRationLazy extends DayRation {
    static final DaoFactory DAO_FACTORY = DaoFactory.getInstance();
    static final JDBCRationCompositionDao JDBC_RATION_COMPOSITION_DAO = (JDBCRationCompositionDao) DAO_FACTORY.createRationCompositionDao();


    @Override
    public ArrayList<RationComposition> getCompositions() {
        ArrayList<RationComposition> arrDish = new ArrayList<>();
        List<RationComposition> listDish = JDBC_RATION_COMPOSITION_DAO.getAllCompositionByRation(super.getId());

        if (!listDish.isEmpty()) {
            arrDish = (ArrayList<RationComposition>) listDish;
        }

        return arrDish;
    }
}
