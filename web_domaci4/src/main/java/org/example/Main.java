package org.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/")
public class Main extends HttpServlet {


    public static final Map<Integer, List<String>> mapMeals = new TreeMap<>();
    public static final Map<String, Boolean> cookiesWithMeals = new ConcurrentHashMap<>();
    public static final List<String> days = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");

    @Override
    public void init() throws ServletException {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("meals.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            int i = 0;
            while (reader.ready()) {
                String[] lineSplit = reader.readLine().split(",");
                mapMeals.put(i++, List.of(lineSplit));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!cookiesWithMeals.containsKey(req.getSession().getId()))
            cookiesWithMeals.put(req.getSession().getId(), false);

        if (cookiesWithMeals.get(req.getSession().getId())) {
            resp.sendRedirect("/saved-meals");
            return;
        }

        resp.setContentType("text/html");


        StringBuilder selects = new StringBuilder();
        for(Map.Entry<Integer, List<String>> meals : mapMeals.entrySet()) {
            selects.append("<label>").append(days.get(meals.getKey())).append(":</label>");
            selects.append("<select name=\"").append(days.get(meals.getKey())).append("\">\n");
            for (String meal : meals.getValue()) {
                selects.append("<option value=\"").append(meal).append("\">").append(meal).append("</option>\n");
            }
            selects.append("</select>\n");
        }

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
                "        <h4>Choose Meals</h4>\n" +
                "\n" +
                 selects.toString() +
                "        <button>Save meals</button>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </form>\n" +
                "</body>\n" +
                "\n" +
                "</html>");
    }
}