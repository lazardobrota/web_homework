package org.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "chosenMeals", value = "/chosen-meals")
public class ChosenMeals extends HttpServlet {

    public static final List<String> passwords = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("passwords.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while (reader.ready()) {
                passwords.add(reader.readLine());
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        req.getSession().setAttribute("password", req.getParameter("password"));
        String password = req.getParameter("password");
        if (!passwords.contains(password)) {
            resp.getWriter().println("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "\n" +
                    "<head>\n" +
                    "<link rel=\"icon\" href=\"data:,\">\n" + //ignore favicon
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "  <h3>Wrong password</h3>" +
                    "</body>\n" +
                    "\n" +
                    "</html>");
            return;
        }

        StringBuilder builder = new StringBuilder();
        for(Map.Entry<Integer, List<String>> meals : Main.mapMeals.entrySet()) {
            builder.append("<div>");
            builder.append("<h4>").append(Main.days.get(meals.getKey())).append(":</h4>");
            builder.append("<p>");
            for (String meal : meals.getValue()) {
                builder.append(meal).append(" ").append(SavedMeals.numberOfMeal.get(meal)).append("<br>");
            }
            builder.append("</p>");
            builder.append("</div>");
        }

        resp.getWriter().println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "<link rel=\"icon\" href=\"data:,\">\n" + //ignore favicon
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "  <form method=\"POST\" action=\"/chosen-meals\">\n" +
                "    <div style=\"display: inline-block;\">\n" +
                "      <label></label>\n" +
                "      <div style=\"display: flex; flex-direction: column; gap: 10px;\">\n" +
                "        <form method=\"POST\" action=\"/chosen-meals\">\n" +
                builder.toString() +
                "          <button>Delete</button>\n" +
                "        </form>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </form>\n" +
                "</body>\n" +
                "\n" +
                "</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SavedMeals.numberOfMeal.replaceAll((k, v) -> 0);
        Main.cookiesWithMeals.replaceAll((k, v) -> false);

        resp.sendRedirect("/chosen-meals?password=" + req.getSession().getAttribute("password"));
    }
}
