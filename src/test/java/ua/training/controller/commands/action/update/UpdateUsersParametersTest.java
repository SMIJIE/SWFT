package ua.training.controller.commands.action.update;

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

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class UpdateUsersParametersTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private User user;

    private String path;
    private String name;
    private String dob;
    private String emailWrong;
    private String password;
    private String height;
    private String weight;
    private String weightDesired;
    private String lifestyle;

    @Before
    public void setUp() {
        name = "Pavlo";
        dob = "2018-05-15";
        emailWrong = "Zakusylogmail";
        password = "qwerty";
        height = "180";
        weight = "90";
        weightDesired = "80";
        lifestyle = "1.7";
    }

    @After
    public void tearDown() {
        name = null;
        dob = null;
        emailWrong = null;
        password = null;
        height = null;
        weight = null;
        weightDesired = null;
        lifestyle = null;
        path = null;
    }

    @Test
    public void execute() {
        UpdateUsersParameters updateUsersParameters = new UpdateUsersParameters();

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.REQUEST_USER)).thenReturn(user);
        when(request.getParameter(Attributes.REQUEST_NAME)).thenReturn(name);
        when(request.getParameter(Attributes.REQUEST_DATE_OF_BIRTHDAY)).thenReturn(dob);
        when(request.getParameter(Attributes.REQUEST_EMAIL)).thenReturn(emailWrong);
        when(request.getParameter(Attributes.REQUEST_PASSWORD)).thenReturn(password);
        when(request.getParameter(Attributes.REQUEST_HEIGHT)).thenReturn(height);
        when(request.getParameter(Attributes.REQUEST_WEIGHT)).thenReturn(weight);
        when(request.getParameter(Attributes.REQUEST_WEIGHT_DESIRED)).thenReturn(weightDesired);
        when(request.getParameter(Attributes.REQUEST_LIFESTYLE)).thenReturn(lifestyle);

        path = updateUsersParameters.execute(request);
        assertEquals(Pages.USER_SETTINGS_REDIRECT_WITH_ERROR, path);

    }
}