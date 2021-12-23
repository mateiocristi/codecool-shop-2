package com.codecool.shop.servlets;

import com.codecool.shop.dao.DBmodels.UserModel;
import com.codecool.shop.dao.database.DatabaseManager;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.User;
import com.codecool.shop.serialization.ProductSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/api/shoppingCart/add")
public class AddToCartApi extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(true);
        String userId = session.getAttribute("userId").toString();
        if ( userId != null) {

            System.out.println("user is " + userId);

            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.run();
            String productId = request.getParameter("id");
            System.out.println("adding to cart api " + productId);

            UserModel user = databaseManager.getUser(Integer.parseInt(userId));
            user.addToCart(Integer.parseInt(productId));
//            System.out.println("test");
//            user.getCartProductsId().forEach(System.out::println);

            databaseManager.updateCart(user);

            PrintWriter out = response.getWriter();
            out.println("{'response': 'ok'}");
        }


    }
}

//            Gson gson;
//            gson = new GsonBuilder().registerTypeAdapter(Product.class, new ProductSerializer()).create();
//            out.println(gson.toJson(cartProducts));