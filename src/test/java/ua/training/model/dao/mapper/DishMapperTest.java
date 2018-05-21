package ua.training.model.dao.mapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.constant.Attributes;
import ua.training.controller.commands.exception.DataHttpException;
import ua.training.model.entity.Dish;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DishMapperTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private ResultSet resultSet;
    private DishMapper dishMapper;
    private Integer id;
    private String category;
    private String name;
    private String nameWrong;
    private String weight;
    private String calories;
    private String proteins;
    private String fats;
    private String carbohydrates;
    private String generalFood;

    @Before
    public void setUp() {
        dishMapper = new DishMapper();
        id = 1;
        category = "HOT";
        name = "Scramble";
        nameWrong = "Scramble2";
        weight = "90";
        calories = "10";
        proteins = "20";
        fats = "15";
        carbohydrates = "25";
        generalFood = "1";
    }

    @After
    public void tearDown() {
        dishMapper = null;
        id = null;
        category = null;
        name = null;
        nameWrong = null;
        weight = null;
        calories = null;
        proteins = null;
        fats = null;
        carbohydrates = null;
        generalFood = null;
    }


    @Test
    public void extractFromResultSet() throws SQLException {
        when(resultSet.getInt(Attributes.SQL_DISH_ID)).thenReturn(id);
        when(resultSet.getString(Attributes.REQUEST_CATEGORY)).thenReturn(category);
        when(resultSet.getString(Attributes.REQUEST_NAME)).thenReturn(name);
        when(resultSet.getInt(Attributes.REQUEST_WEIGHT)).thenReturn(Integer.valueOf(weight));
        when(resultSet.getInt(Attributes.REQUEST_CALORIES)).thenReturn(Integer.valueOf(calories));
        when(resultSet.getInt(Attributes.REQUEST_PROTEINS)).thenReturn(Integer.valueOf(proteins));
        when(resultSet.getInt(Attributes.REQUEST_FATS)).thenReturn(Integer.valueOf(fats));
        when(resultSet.getInt(Attributes.REQUEST_CARBOHYDRATES)).thenReturn(Integer.valueOf(carbohydrates));
        when(resultSet.getBoolean(Attributes.REQUEST_GENERAL_FOOD)).thenReturn(Boolean.valueOf(generalFood));


        Dish dish = dishMapper.extractFromResultSet(resultSet);
        assertNotNull(dish);
    }

    @Test
    public void extractFromHttpServletRequest() throws DataHttpException {
        when(request.getParameter(Attributes.REQUEST_NAME)).thenReturn(name);
        when(request.getParameter(Attributes.REQUEST_WEIGHT)).thenReturn(weight);
        when(request.getParameter(Attributes.REQUEST_CALORIES)).thenReturn(calories);
        when(request.getParameter(Attributes.REQUEST_PROTEINS)).thenReturn(proteins);
        when(request.getParameter(Attributes.REQUEST_FATS)).thenReturn(fats);
        when(request.getParameter(Attributes.REQUEST_CARBOHYDRATES)).thenReturn(carbohydrates);

        Dish dish = dishMapper.extractFromHttpServletRequest(request);
        assertNotNull(dish);
    }

    @Test(expected = DataHttpException.class)
    public void checkByRegex() throws DataHttpException {
        when(request.getParameter(Attributes.REQUEST_NAME)).thenReturn(name);
        when(request.getParameter(Attributes.REQUEST_WEIGHT)).thenReturn(weight);
        when(request.getParameter(Attributes.REQUEST_CALORIES)).thenReturn(calories);
        when(request.getParameter(Attributes.REQUEST_PROTEINS)).thenReturn(proteins);
        when(request.getParameter(Attributes.REQUEST_FATS)).thenReturn(fats);
        when(request.getParameter(Attributes.REQUEST_CARBOHYDRATES)).thenReturn(carbohydrates);

        Dish dish = dishMapper.extractFromHttpServletRequest(request);
        assertNotNull(dish);

        dish.setName(nameWrong);
        dishMapper.checkByRegex(dish);
    }
}