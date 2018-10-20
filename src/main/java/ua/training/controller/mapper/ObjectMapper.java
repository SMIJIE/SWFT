package ua.training.controller.mapper;

import org.springframework.web.servlet.ModelAndView;
import ua.training.constant.Attributes;
import ua.training.constant.Mess;
import ua.training.constant.RegexExpress;
import ua.training.controller.exception.DataHttpException;
import ua.training.model.entity.GeneralEntity;
import ua.training.model.entity.form.GeneralFormEntity;

/**
 * Description: This is the generic class check(valid) object
 *
 * @author Zakusylo Pavlo
 */
public interface ObjectMapper<E extends GeneralEntity, F extends GeneralFormEntity> extends RegexExpress, Mess, Attributes {
    E extractEntityFromHttpForm(F f, ModelAndView modelAndView) throws DataHttpException;

    void checkByRegex(E e, ModelAndView modelAndView) throws DataHttpException;
}
