package ua.training.controller.servlet.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.model.entity.User;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PageFilterRegisteredTest {
    @Mock
    private HttpServletRequest requestClient;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private HttpSession session;
    @Mock
    private User user;

    @Test
    public void filter() throws IOException, ServletException {
        PageFilterRegistered registered = new PageFilterRegistered();

        when(requestClient.getSession()).thenReturn(session);
        when(requestClient.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        registered.filter(requestClient, response, chain, Optional.of(user));
        verify(chain, times(0)).doFilter(requestClient, response);
        verify(requestClient, times(1)).getRequestDispatcher(anyString());

        registered.filter(request, response, chain, Optional.empty());
        verify(request, times(0)).getRequestDispatcher(anyString());
        verify(chain, times(1)).doFilter(request, response);
    }
}