package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.DBmodels.UserModel;
import com.codecool.shop.dao.database.DatabaseManager;
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

@WebServlet(urlPatterns = {"/login"})
@MultipartConfig
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("product/login.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.run();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (email != null && password != null) {
            UserModel userModel = databaseManager.checkUser(email, password);
            if (userModel != null) {
                HttpSession session = req.getSession(true);
                session.setAttribute("userId", userModel.getId());
                resp.sendRedirect("/");
            } else {
                doGet(req, resp);
            }
        } else {
            doGet(req, resp);
        }

    }
}
