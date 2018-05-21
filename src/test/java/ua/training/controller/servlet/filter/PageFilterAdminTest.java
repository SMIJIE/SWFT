package ua.training.controller.servlet.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.Roles;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PageFilterAdminTest {
    @Mock
    private HttpServletRequest requestRegisterUser;
    @Mock
    private HttpServletRequest requestRegisterAdmin;
    @Mock
    private HttpServletRequest requestNonRegisterUser;
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
        PageFilterAdmin pageFilterAdmin = new PageFilterAdmin();

        when(user.getRole()).thenReturn(Roles.ADMIN);
        pageFilterAdmin.filter(requestRegisterAdmin, response, chain, Optional.of(user));
        verify(requestRegisterAdmin, times(0)).getRequestDispatcher(anyString());
        verify(chain, times(1)).doFilter(requestRegisterAdmin, response);

        when(user.getRole()).thenReturn(Roles.USER);
        when(requestRegisterUser.getSession()).thenReturn(session);
        when(requestRegisterUser.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        pageFilterAdmin.filter(requestRegisterUser, response, chain, Optional.of(user));
        verify(chain, times(0)).doFilter(requestRegisterUser, response);
        verify(requestRegisterUser, times(1)).getRequestDispatcher(anyString());

        when(requestNonRegisterUser.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        pageFilterAdmin.filter(requestNonRegisterUser, response, chain, Optional.empty());
        verify(chain, times(0)).doFilter(requestNonRegisterUser, response);
        verify(requestNonRegisterUser, times(1)).getRequestDispatcher(anyString());
    }
}