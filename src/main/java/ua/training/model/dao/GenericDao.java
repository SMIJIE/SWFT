package ua.training.model.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.utility.DbProperties;

import java.util.List;
import java.util.Optional;

/**
 * Description: This is the generic(CRUD) dao for DataBase
 *
 * @author Zakusylo Pavlo
 */
public interface GenericDao<T> extends AutoCloseable {
    /**
     * Logger for GenericDao<T> classes
     *
     * @see LogManager
     */
    Logger LOGGER = LogManager.getLogger(GenericDao.class);

    /**
     * DbProperties for GenericDao<T> classes
     *
     * @see DbProperties
     */
    DbProperties DB_PROPERTIES = new DbProperties();

    void create(T entity);

    Optional<T> findById(Integer id);

    List<T> findAll();

    void update(T entity);

    void delete(Integer id);

    void close();
}
