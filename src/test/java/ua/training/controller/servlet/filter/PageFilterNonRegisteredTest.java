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
import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PageFilterNonRegisteredTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private User user;

    @Test
    public void filter() throws IOException, ServletException {
        PageFilterNonRegistered nonRegistered = new PageFilterNonRegistered();
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        nonRegistered.filter(request, response, chain, Optional.empty());
        verify(request, times(1)).getRequestDispatcher(anyString());

        nonRegistered.filter(request, response, chain, Optional.of(user));
        verify(chain, times(1)).doFilter(request, response);
    }
}