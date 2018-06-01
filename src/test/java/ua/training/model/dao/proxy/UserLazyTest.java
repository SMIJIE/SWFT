package ua.training.model.dao.proxy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.training.model.entity.Dish;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserLazyTest {
    private UserLazy user;
    private ArrayList<Dish> arrDish;

    @Before
    public void setUp() {
        user = new UserLazy();
        user.setId(1);
        arrDish = new ArrayList<>();
    }

    @After
    public void tearDown() {
        user = null;
        arrDish = null;
    }

    @Test
    public void getListDishes() {
        assertTrue(arrDish.isEmpty());
        arrDish = user.getListDishes();
        assertFalse(arrDish.isEmpty());
    }
}