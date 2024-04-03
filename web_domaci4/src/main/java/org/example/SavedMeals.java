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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "savedMeals", value = "/saved-meals")
public class SavedMeals extends HttpServlet {

    public static Map<String, Integer> numberOfMeal = new ConcurrentHashMap<>();

    //If I take from Main hashmap instead of using meal.txt, someone could first call this route and numberOfMeal would be empty forever
    @Override
    public void init() throws ServletException {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("meals.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            while (reader.ready()) {
                String[] lineSplit = reader.readLine().split(",");
                for (String s : lineSplit) numberOfMeal.put(s, 0);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        StringBuilder selected = new StringBuilder();
        selected.append("<p>");
        for (String day: Main.days) {
            String meal = (String) req.getSession().getAttribute(day);
            selected.append(day).append(": ").append(meal).append("<br>");
        }
        selected.append("</p>\n");

        resp.getWriter().println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<link rel=\"icon\" href=\"data:,\">\n" + //ignore favicon
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "  <form method=\"POST\" action=\"/saved-meals\">\n" +
                "    <div style=\"display: inline-block;\">\n" +
                "      <div style=\"display: flex; flex-direction: column; gap: 10px;\">\n" +
                "\n" +
                "        <h4>Meals for you</h4>\n" +
                "\n" +
                selected.toString() +
                "      </div>\n" +
                "    </div>\n" +
                "  </form>\n" +
                "</body>\n" +
                "\n" +
                "</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Main.cookiesWithMeals.put(req.getSession().getId(), true);

        for (String day : Main.days) {
            req.getSession().setAttribute(day, req.getParameter(day));
            String meal = (String) req.getSession().getAttribute(day);
            numberOfMeal.put(meal, numberOfMeal.get(meal) + 1);
        }

        resp.sendRedirect("/saved-meals");
    }
}
