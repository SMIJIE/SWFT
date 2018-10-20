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
//import ua.training.model.entity.User;
//
//import javax.servlet.http.HttpServletRequest;
//
//import static org.junit.Assert.assertNotNull;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class UserMapperTest {
//    @Mock
//    private HttpServletRequest request;
//    private UserMapper userMapper;
//    private String name;
//    private String dob;
//    private String emailWrong;
//    private String email;
//    private String password;
//    private String height;
//    private String weight;
//    private String weightDesired;
//    private String lifestyle;
//
//    @Before
//    public void setUp() {
//        userMapper = new UserMapper();
//        name = "Pavlo";
//        dob = "1993-05-15";
//        emailWrong = "Zakusylogmail";
//        email = "Zakusylo@gmail.com";
//        password = "qwerty";
//        height = "180";
//        weight = "90";
//        weightDesired = "80";
//        lifestyle = "1.7";
//    }
//
//    @After
//    public void tearDown() {
//        userMapper = null;
//        name = null;
//        dob = null;
//        emailWrong = null;
//        email = null;
//        password = null;
//        height = null;
//        weight = null;
//        weightDesired = null;
//        lifestyle = null;
//    }
//
//    @Test
//    public void extractFromHttpServletRequest() throws DataHttpException {
//        when(request.getParameter(Attributes.REQUEST_NAME)).thenReturn(name);
//        when(request.getParameter(Attributes.REQUEST_DATE_OF_BIRTHDAY)).thenReturn(dob);
//        when(request.getParameter(Attributes.REQUEST_EMAIL)).thenReturn(email);
//        when(request.getParameter(Attributes.REQUEST_PASSWORD)).thenReturn(password);
//        when(request.getParameter(Attributes.REQUEST_HEIGHT)).thenReturn(height);
//        when(request.getParameter(Attributes.REQUEST_WEIGHT)).thenReturn(weight);
//        when(request.getParameter(Attributes.REQUEST_WEIGHT_DESIRED)).thenReturn(weightDesired);
//        when(request.getParameter(Attributes.REQUEST_LIFESTYLE)).thenReturn(lifestyle);
//
//        User user = userMapper.extractFromHttpServletRequest(request);
//        assertNotNull(user);
//    }
//
//    @Test(expected = DataHttpException.class)
//    public void checkByRegex() throws DataHttpException {
//        when(request.getParameter(Attributes.REQUEST_NAME)).thenReturn(name);
//        when(request.getParameter(Attributes.REQUEST_DATE_OF_BIRTHDAY)).thenReturn(dob);
//        when(request.getParameter(Attributes.REQUEST_EMAIL)).thenReturn(email);
//        when(request.getParameter(Attributes.REQUEST_PASSWORD)).thenReturn(password);
//        when(request.getParameter(Attributes.REQUEST_HEIGHT)).thenReturn(height);
//        when(request.getParameter(Attributes.REQUEST_WEIGHT)).thenReturn(weight);
//        when(request.getParameter(Attributes.REQUEST_WEIGHT_DESIRED)).thenReturn(weightDesired);
//        when(request.getParameter(Attributes.REQUEST_LIFESTYLE)).thenReturn(lifestyle);
//
//        User user = userMapper.extractFromHttpServletRequest(request);
//        assertNotNull(user);
//
//        user.setEmail(emailWrong);
//        userMapper.checkByRegex(user);
//    }
//}