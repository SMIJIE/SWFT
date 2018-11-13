package ua.training.model.dao;

import ua.training.constant.Attributes;
import ua.training.constant.Mess;

import java.util.Optional;

/**
 * Description: This is the generic(CRUD) dao for DataBase
 *
 * @author Zakusylo Pavlo
 */
public interface GenericDao<T> extends AutoCloseable, Mess, Attributes {

    void create(T entity);

    Optional<T> findById(Integer id);

    void update(T entity);

    void delete(Integer id);

    void close();
}
