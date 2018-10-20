//package ua.training.model.dao.mapper;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import ua.training.constant.Attributes;
//import ua.training.controller.controllers.exception.DataHttpException;
//import ua.training.model.entity.Dish;
//
//import javax.servlet.http.HttpServletRequest;
//
//import static org.junit.Assert.assertNotNull;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class DishMapperTest {
//    @Mock
//    private HttpServletRequest request;
//    private DishMapper dishMapper;
//    private String name;
//    private String nameWrong;
//    private String weight;
//    private String calories;
//    private String proteins;
//    private String fats;
//    private String carbohydrates;
//
//    @Before
//    public void setUp() {
//        dishMapper = new DishMapper();
//        name = "Scramble";
//        nameWrong = "Scramble2";
//        weight = "90";
//        calories = "10";
//        proteins = "20";
//        fats = "15";
//        carbohydrates = "25";
//    }
//
//    @After
//    public void tearDown() {
//        dishMapper = null;
//        name = null;
//        nameWrong = null;
//        weight = null;
//        calories = null;
//        proteins = null;
//        fats = null;
//        carbohydrates = null;
//    }
//
//    @Test
//    public void extractFromHttpServletRequest() throws DataHttpException {
//        when(request.getParameter(Attributes.REQUEST_NAME)).thenReturn(name);
//        when(request.getParameter(Attributes.REQUEST_WEIGHT)).thenReturn(weight);
//        when(request.getParameter(Attributes.REQUEST_CALORIES)).thenReturn(calories);
//        when(request.getParameter(Attributes.REQUEST_PROTEINS)).thenReturn(proteins);
//        when(request.getParameter(Attributes.REQUEST_FATS)).thenReturn(fats);
//        when(request.getParameter(Attributes.REQUEST_CARBOHYDRATES)).thenReturn(carbohydrates);
//
//        Dish dish = dishMapper.extractFromHttpServletRequest(request);
//        assertNotNull(dish);
//    }
//
//    @Test(expected = DataHttpException.class)
//    public void checkByRegex() throws DataHttpException {
//        when(request.getParameter(Attributes.REQUEST_NAME)).thenReturn(name);
//        when(request.getParameter(Attributes.REQUEST_WEIGHT)).thenReturn(weight);
//        when(request.getParameter(Attributes.REQUEST_CALORIES)).thenReturn(calories);
//        when(request.getParameter(Attributes.REQUEST_PROTEINS)).thenReturn(proteins);
//        when(request.getParameter(Attributes.REQUEST_FATS)).thenReturn(fats);
//        when(request.getParameter(Attributes.REQUEST_CARBOHYDRATES)).thenReturn(carbohydrates);
//
//        Dish dish = dishMapper.extractFromHttpServletRequest(request);
//        assertNotNull(dish);
//
//        dish.setName(nameWrong);
//        dishMapper.checkByRegex(dish);
//    }
//}