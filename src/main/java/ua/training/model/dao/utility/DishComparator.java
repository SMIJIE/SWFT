package ua.training.model.dao.utility;

import lombok.extern.log4j.Log4j2;
import ua.training.constant.Mess;
import ua.training.model.entity.Dish;

import java.lang.reflect.Field;
import java.util.Comparator;

/**
 * Description: This is Comparator for sorting Dish by getting fields
 *
 * @author Zakusylo Pavlo
 */
@Log4j2
public class DishComparator implements Comparator<Dish> {
    private Field field;

    public DishComparator(Field field) {
        super();
        this.field = field;
    }

    @Override
    public int compare(Dish d1, Dish d2) {
        Comparable val1;
        Comparable val2;

        try {
            val1 = (Comparable) field.get(d1);
            val2 = (Comparable) field.get(d2);
            return val1.compareTo(val2);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage() + Mess.LOG_NOT_ACCESSIBLE_ENTITY_FIELDS);
        }

        return 0;
    }
}
