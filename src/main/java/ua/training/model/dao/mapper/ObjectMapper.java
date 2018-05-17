package ua.training.model.dao.mapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    /**
     * Logger for ObjectMapper classes
     *
     * @see LogManager
     */
    Logger LOGGER = LogManager.getLogger(ObjectMapper.class);

    T extractFromResultSet(ResultSet rs);

    T extractFromHttpServletRequest(HttpServletRequest req) throws DataHttpException;

    void checkByRegex(T t) throws DataHttpException;

    T makeUnique(Map<Integer, T> cache, T t);


}
