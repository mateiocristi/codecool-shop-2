package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.database.DatabaseManager;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

@WebServlet(urlPatterns = {"/register"})
@MultipartConfig
public class RegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("product/register.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.run();

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String firstPass = req.getParameter("firstPassword");
        String secPass = req.getParameter("secondPassword");
        if (firstName != null && lastName != null & email != null && firstPass != null)
            if (firstPass.equals(secPass))
            {
                HttpSession session = req.getSession(true);
                User user = new User(email, firstName, lastName, firstPass);
                user.setCart("");
                user.setOrders("");
                user = databaseManager.addUser(user);
                session.setAttribute("userId", user.getId());
                resp.sendRedirect("/");
            }
        doGet(req, resp);
    }
}
