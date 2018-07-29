package ua.training;

import org.hibernate.Session;
import ua.training.model.dao.implemation.HSesssionFactory;
import ua.training.model.dao.utility.DishComparator;
import ua.training.model.dao.utility.SortAnnotation;
import ua.training.model.entity.Dish;
import ua.training.model.entity.enums.FoodCategory;

import java.lang.reflect.Field;

public class Main {

    public static void main(String[] args) {
        Session session = HSesssionFactory.getSession();

        Dish dish1 = Dish.builder()
                .foodCategory(FoodCategory.HOT)
                .calories(1000)
                .build();
        Dish dish2 = Dish.builder()
                .foodCategory(FoodCategory.HOT)
                .calories(2000)
                .build();

        Class<?> cl = Dish.class;
        Field[] fields = cl.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(SortAnnotation.class) && field.getName().equalsIgnoreCase("calories")) {
                field.setAccessible(true);
                DishComparator dishComparator = new DishComparator(field);
                System.out.println(dishComparator.compare(dish1, dish2));
            }
        }

//        for (Field field : fieldsForSort) {
//            if (flag) {
//                comparator = new DishComparator(field);
//                flag = false;
//                continue;
//            }
//            comparator = comparator.thenComparing(new DishComparator(field));
//        }


//        if (!fieldsForSort.isEmpty()) {
//            dishes.sort(comparator);
//        }
//
//        dishes.forEach(System.out::println);
    }
}
