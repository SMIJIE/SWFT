package ua.training.controller.commands.action;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.constant.Attributes;
import ua.training.constant.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChangeLanguageTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    private String path;
    private String currPage;
    private String currLang;

    @Before
    public void setUp() {
        currPage = "/swft/homePage";
        currLang = "uk_UA";
    }

    @After
    public void tearDown() {
        path = null;
        currPage = null;
        currLang = null;
    }

    @Test
    public void execute() {
        ChangeLanguage language = new ChangeLanguage();
        when(request.getParameter(Attributes.REQUEST_CURRENT_PAGE)).thenReturn(currPage);
        when(request.getParameter(Attributes.REQUEST_LANGUAGE)).thenReturn(currLang);
        when(request.getSession()).thenReturn(session);

        path = language.execute(request);
        assertEquals(Pages.HOME_REDIRECT, path);
    }
}