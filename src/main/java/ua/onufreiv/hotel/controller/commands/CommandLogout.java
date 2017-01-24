package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by yurii on 12/27/16.
 */
public class CommandLogout implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String page;

        if (session != null) {
            session.invalidate();
            page = PathConfig.getInstance().getProperty(PathConfig.MAIN_PAGE_PATH);
        } else {
            //request.setAttribute("errorMessage", true);
            page = PathConfig.getInstance().getProperty(PathConfig.NOT_SIGNED_IN_ERROR_PAGE_PATH);
        }

        return page;
    }
}
