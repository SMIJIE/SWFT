package ua.training.controller.commands.action.add;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateNewRationTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private User user;

    private String path;
    private String[] breakfast;
    private String[] dinner;
    private String[] supper;
    private LocalDate dob;
    private String currentDate;
    private Integer height;
    private Integer weight;
    private Integer weightDesired;
    private Integer lifestyle;


    @Before
    public void setUp() {
        breakfast = new String[]{"2", "31"};
        dinner = null;
        supper = null;
        dob = LocalDate.parse("1993-05-03");
        currentDate = "2018-05-01";
        height = 180000;
        weight = 90000;
        weightDesired = 80000;
        lifestyle = 1700;
    }

    @After
    public void tearDown() {
        path = null;
        breakfast = null;
        dinner = null;
        supper = null;
        dob = null;
        currentDate = null;
        height = null;
        weight = null;
        weightDesired = null;
        lifestyle = null;
    }

    @Test
    public void execute() {
        CreateNewRation createNewRation = new CreateNewRation();
        when(request.getParameterValues(Attributes.REQUEST_SELECT_BREAKFAST)).thenReturn(breakfast);
        when(request.getParameterValues(Attributes.REQUEST_SELECT_DINNER)).thenReturn(dinner);
        when(request.getParameterValues(Attributes.REQUEST_SELECT_SUPPER)).thenReturn(supper);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.REQUEST_USER)).thenReturn(user);
        when(user.getDob()).thenReturn(dob);
        when(user.getLifeStyleCoefficient()).thenReturn(lifestyle);
        when(user.getWeight()).thenReturn(weight);
        when(user.getHeight()).thenReturn(height);
        when(user.getWeightDesired()).thenReturn(weightDesired);
        when(request.getParameter(Attributes.REQUEST_DATE)).thenReturn(currentDate);


        path = createNewRation.execute(request);

        assertEquals(Pages.DAY_RATION, path);
    }
}