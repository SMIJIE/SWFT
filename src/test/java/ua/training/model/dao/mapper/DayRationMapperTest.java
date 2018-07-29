package ua.training.model.dao.mapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.constant.Attributes;
import ua.training.controller.commands.exception.DataHttpException;
import ua.training.model.entity.DayRation;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.Period;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DayRationMapperTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private User user;

    private DayRationMapper dayRationMapper;
    private LocalDate date;
    private LocalDate dateWrong;
    private LocalDate dob;
    private String userCalories;
    private String userCaloriesDesired;
    private String height;
    private String weight;
    private String weightDesired;
    private String lifestyle;

    @Before
    public void setUp() {
        dayRationMapper = new DayRationMapper();
        date = LocalDate.now();
        dateWrong = LocalDate.parse("2018-05-01");
        dob = LocalDate.parse("1993-05-03");
        userCalories = "3230000";
        userCaloriesDesired = "3060000";
        height = "18000";
        weight = "90000";
        weightDesired = "80000";
        lifestyle = "1700";
    }

    @After
    public void tearDown() {
        dayRationMapper = null;
        date = null;
        dateWrong = null;
        dob = null;
        userCalories = null;
        userCaloriesDesired = null;
        height = null;
        weight = null;
        weightDesired = null;
        lifestyle = null;
    }

    @Test
    public void extractFromHttpServletRequest() throws DataHttpException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.REQUEST_USER)).thenReturn(user);
        when(request.getParameter(Attributes.REQUEST_DATE)).thenReturn(date.toString());
        when(user.getDob()).thenReturn(dob);
        when(user.getLifeStyleCoefficient()).thenReturn(Integer.valueOf(lifestyle));
        when(user.getHeight()).thenReturn(Integer.valueOf(height));
        when(user.getWeight()).thenReturn(Integer.valueOf(weight));
        when(user.getWeightDesired()).thenReturn(Integer.valueOf(weightDesired));

        DayRation dayRation = dayRationMapper.extractFromHttpServletRequest(request);
        assertNotNull(dayRation);
    }

    @Test(expected = DataHttpException.class)
    public void checkByRegex() throws DataHttpException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.REQUEST_USER)).thenReturn(user);
        when(request.getParameter(Attributes.REQUEST_DATE)).thenReturn(date.toString());
        when(user.getDob()).thenReturn(dob);
        when(user.getLifeStyleCoefficient()).thenReturn(Integer.valueOf(lifestyle));
        when(user.getHeight()).thenReturn(Integer.valueOf(height));
        when(user.getWeight()).thenReturn(Integer.valueOf(weight));
        when(user.getWeightDesired()).thenReturn(Integer.valueOf(weightDesired));

        DayRation dayRation = dayRationMapper.extractFromHttpServletRequest(request);
        assertNotNull(dayRation);

        when(request.getParameter(Attributes.REQUEST_DATE)).thenReturn(dateWrong.toString());
        dayRationMapper.extractFromHttpServletRequest(request);
    }

    @Test
    public void formulaMifflinSanJerura() {
        Period period = Period.between(dob, date);
        Integer tempCalories = dayRationMapper.formulaMifflinSanJerura(Integer.valueOf(lifestyle),
                Integer.valueOf(weight), Integer.valueOf(height), period.getYears());
        Integer tempUserCalories = Integer.valueOf(userCalories) / 1000;
        Integer tempCaloriesDesired = dayRationMapper.formulaMifflinSanJerura(Integer.valueOf(lifestyle),
                Integer.valueOf(weightDesired), Integer.valueOf(height), period.getYears());
        Integer tempUserCaloriesDesired = Integer.valueOf(userCaloriesDesired) / 1000;

        assertEquals(tempUserCalories, tempCalories);
        assertEquals(tempUserCaloriesDesired, tempCaloriesDesired);
    }
}