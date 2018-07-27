package ua.training.model.dao;

import ua.training.model.dao.utility.DbProperties;

import java.util.Optional;

/**
 * Description: This is the generic(CRUD) dao for DataBase
 *
 * @author Zakusylo Pavlo
 */
public interface GenericDao<T> extends AutoCloseable {
    /**
     * DbProperties for GenericDao<T> classes
     *
     * @see DbProperties
     */
    DbProperties DB_PROPERTIES = new DbProperties();

    void create(T entity);

    Optional<T> findById(Integer id);

    void update(T entity);

    void delete(Integer id);

    void close();
}
