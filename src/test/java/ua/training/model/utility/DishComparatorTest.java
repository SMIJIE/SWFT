package ua.training.model.dao.utility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.training.model.entity.Dish;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class DishComparatorTest {
    private Dish dish1;
    private Dish dish2;
    private DishComparator dishComparator;

    @Before
    public void setUp() {
        dish1 = Dish.builder()
                .calories(1000)
                .build();
        dish2 = Dish.builder()
                .calories(2000)
                .build();
    }

    @After
    public void tearDown() {
        dish1 = null;
        dish2 = null;
        dishComparator = null;
    }


    @Test
    public void compare() {
        Class<?> cl = Dish.class;
        Field[] fields = cl.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(SortAnnotation.class) && field.getName().equalsIgnoreCase("calories")) {
                field.setAccessible(true);
                dishComparator = new DishComparator(field);
                int tempComp = dishComparator.compare(dish1, dish2);
                assertEquals(-1, tempComp);
            }
        }
    }
}