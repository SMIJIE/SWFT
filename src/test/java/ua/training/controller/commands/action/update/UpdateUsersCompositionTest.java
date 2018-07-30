package ua.training.controller.commands.action.update;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.constant.Attributes;
import ua.training.constant.Pages;

import javax.servlet.http.HttpServletRequest;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateUsersCompositionTest {
    @Mock
    private HttpServletRequest request;

    private String path;
    private String numComposition;
    private String amount;


    @Before
    public void setUp() {
        numComposition = "1";
        amount = "5";
    }

    @After
    public void tearDown() {
        numComposition = null;
        amount = null;
        path = null;
    }

    @Test
    public void execute() {
        UpdateUsersComposition updateUsersComposition = new UpdateUsersComposition();

        when(request.getParameter(Attributes.REQUEST_NUMBER_COMPOSITION)).thenReturn(numComposition);
        when(request.getParameter(Attributes.REQUEST_AMOUNT)).thenReturn(amount);

        path = updateUsersComposition.execute(request);

        assertEquals(Pages.DAY_RATION_LIST_REDIRECT, path);
    }
}