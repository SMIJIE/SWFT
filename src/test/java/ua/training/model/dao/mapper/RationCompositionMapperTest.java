package ua.training.model.dao.mapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.constant.Attributes;
import ua.training.model.entity.RationComposition;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RationCompositionMapperTest {
    @Mock
    private ResultSet resultSet;
    private Integer id;
    private String foodIntake;
    private Integer numberDish;
    private Integer caloriesOfDish;
    private RationCompositionMapper rationCompositionMapper;

    @Before
    public void setUp() {
        id = 1;
        foodIntake = "DINNER";
        numberDish = 2;
        caloriesOfDish = 300;
        rationCompositionMapper = new RationCompositionMapper();
    }

    @After
    public void tearDown() {
        id = null;
        foodIntake = null;
        numberDish = null;
        caloriesOfDish = null;
        rationCompositionMapper = null;
    }

    @Test
    public void extractFromResultSet() throws SQLException {
        when(resultSet.getInt(Attributes.SQL_RC_ID)).thenReturn(id);
        when(resultSet.getString(Attributes.REQUEST_FOOD_INTAKE)).thenReturn(foodIntake);
        when(resultSet.getInt(Attributes.SQL_NUMBER_OF_DISH)).thenReturn(numberDish);
        when(resultSet.getInt(Attributes.SQL_CALORIES_OF_DISH)).thenReturn(caloriesOfDish);

        RationComposition rationComposition = rationCompositionMapper.extractFromResultSet(resultSet);

        assertNotNull(rationComposition);

    }
}