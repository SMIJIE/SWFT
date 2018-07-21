package ua.training.model.dao.mapper;

import ua.training.controller.commands.exception.DataHttpException;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.util.Map;

/**
 * Description: This is the generic class for extract from ResultSet and HttpServletRequest
 *
 * @author Zakusylo Pavlo
 */
public interface ObjectMapper<T> {

    T extractFromResultSet(ResultSet rs);

    T extractFromHttpServletRequest(HttpServletRequest req) throws DataHttpException;

    void checkByRegex(T t) throws DataHttpException;

    T makeUnique(Map<Integer, T> cache, T t);


}
