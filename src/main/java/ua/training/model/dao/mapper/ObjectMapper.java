package ua.training.model.dao.mapper;

import ua.training.controller.commands.exception.DataHttpException;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: This is the generic class for extract from ResultSet and HttpServletRequest
 *
 * @author Zakusylo Pavlo
 */
public interface ObjectMapper<T> {

    T extractFromHttpServletRequest(HttpServletRequest req) throws DataHttpException;

    void checkByRegex(T t) throws DataHttpException;

}
