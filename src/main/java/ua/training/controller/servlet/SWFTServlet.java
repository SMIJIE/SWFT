package ua.training.controller.servlet;

import ua.training.constant.Attributes;
import ua.training.constant.Pages;
import ua.training.constant.RegexExpress;
import ua.training.controller.commands.Command;
import ua.training.controller.commands.utility.CommandsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Description: This is the SWFTServlet class
 *
 * @author Zakusylo Pavlo
 */
@WebServlet(urlPatterns = {"/swft/*"})
public class SWFTServlet extends HttpServlet {
    private Map<String, Command> commands;

    @Override
    public void init() {
        commands = CommandsUtil.commandMapInitialize();
    }

    @Override
    public void destroy() {
        commands = null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Execute command according to request and response
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getRequestURI();

        path = path.replaceAll(RegexExpress.REDIRECT_PAGE, "");

        Command command = commands.getOrDefault(path, (r) -> Pages.INDEX);

        String page = command.execute(req);

        if (page.contains(Pages.REDIRECT)) {
            resp.sendRedirect(page.replace(Pages.REDIRECT, Attributes.PAGE_PATH));
        } else {
            req.getRequestDispatcher(page).forward(req, resp);
        }
    }
}
