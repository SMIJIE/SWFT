package ua.training.model.dao.mapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.constant.Attributes;
import ua.training.controller.commands.exception.DataHttpException;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private ResultSet resultSet;
    private UserMapper userMapper;
    private Integer id;
    private String name;
    private String dob;
    private String emailWrong;
    private String email;
    private String password;
    private String role;
    private String height;
    private String weight;
    private String weightDesired;
    private String lifestyle;

    @Before
    public void setUp() {
        userMapper = new UserMapper();
        id = 1;
        name = "Pavlo";
        dob = "1993-05-15";
        emailWrong = "Zakusylogmail";
        email = "Zakusylo@gmail.com";
        password = "qwerty";
        role = "USER";
        height = "180";
        weight = "90";
        weightDesired = "80";
        lifestyle = "1.7";
    }

    @After
    public void tearDown() {
        userMapper = null;
        id = null;
        name = null;
        dob = null;
        emailWrong = null;
        email = null;
        password = null;
        role = null;
        height = null;
        weight = null;
        weightDesired = null;
        lifestyle = null;
    }


    @Test
    public void extractFromResultSet() throws SQLException {
        when(resultSet.getInt(Attributes.SQL_USER_ID)).thenReturn(id);
        when(resultSet.getString(Attributes.REQUEST_NAME)).thenReturn(name);
        when(resultSet.getDate(Attributes.REQUEST_DATE_OF_BIRTHDAY)).thenReturn(Date.valueOf(dob));
        when(resultSet.getString(Attributes.REQUEST_EMAIL)).thenReturn(email);
        when(resultSet.getString(Attributes.REQUEST_PASSWORD)).thenReturn(password);
        when(resultSet.getString(Attributes.REQUEST_USER_ROLE)).thenReturn(role);
        when(resultSet.getInt(Attributes.REQUEST_HEIGHT)).thenReturn(Integer.valueOf(height));
        when(resultSet.getInt(Attributes.REQUEST_WEIGHT)).thenReturn(Integer.valueOf(height));
        when(resultSet.getInt(Attributes.REQUEST_WEIGHT_DESIRED)).thenReturn(Integer.valueOf(weightDesired));
        when(resultSet.getInt(Attributes.SQL_LIFESTYLE_COEFFICIENT)).thenReturn((int) Math.round(Double.valueOf(lifestyle)));

        User user = userMapper.extractFromResultSet(resultSet);
        assertNotNull(user);
    }

    @Test
    public void extractFromHttpServletRequest() throws DataHttpException {
        when(request.getParameter(Attributes.REQUEST_NAME)).thenReturn(name);
        when(request.getParameter(Attributes.REQUEST_DATE_OF_BIRTHDAY)).thenReturn(dob);
        when(request.getParameter(Attributes.REQUEST_EMAIL)).thenReturn(email);
        when(request.getParameter(Attributes.REQUEST_PASSWORD)).thenReturn(password);
        when(request.getParameter(Attributes.REQUEST_HEIGHT)).thenReturn(height);
        when(request.getParameter(Attributes.REQUEST_WEIGHT)).thenReturn(weight);
        when(request.getParameter(Attributes.REQUEST_WEIGHT_DESIRED)).thenReturn(weightDesired);
        when(request.getParameter(Attributes.REQUEST_LIFESTYLE)).thenReturn(lifestyle);

        User user = userMapper.extractFromHttpServletRequest(request);
        assertNotNull(user);
    }

    @Test(expected = DataHttpException.class)
    public void checkByRegex() throws DataHttpException {
        when(request.getParameter(Attributes.REQUEST_NAME)).thenReturn(name);
        when(request.getParameter(Attributes.REQUEST_DATE_OF_BIRTHDAY)).thenReturn(dob);
        when(request.getParameter(Attributes.REQUEST_EMAIL)).thenReturn(email);
        when(request.getParameter(Attributes.REQUEST_PASSWORD)).thenReturn(password);
        when(request.getParameter(Attributes.REQUEST_HEIGHT)).thenReturn(height);
        when(request.getParameter(Attributes.REQUEST_WEIGHT)).thenReturn(weight);
        when(request.getParameter(Attributes.REQUEST_WEIGHT_DESIRED)).thenReturn(weightDesired);
        when(request.getParameter(Attributes.REQUEST_LIFESTYLE)).thenReturn(lifestyle);

        User user = userMapper.extractFromHttpServletRequest(request);
        assertNotNull(user);

        user.setEmail(emailWrong);
        userMapper.checkByRegex(user);
    }
}