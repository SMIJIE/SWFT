package ua.training.model.dao.mapper;

import org.springframework.web.servlet.ModelAndView;
import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.constant.RegexExpress;
import ua.training.controller.commands.exception.DataHttpException;

/**
 * Description: This is the generic class check(valid) object
 *
 * @author Zakusylo Pavlo
 */
public interface ObjectMapper<T> extends RegexExpress, Mess, Attributes {
    void checkByRegex(T t, ModelAndView modelAndView) throws DataHttpException;
}
