package app;

import com.google.gson.Gson;
import http.HelpServerThread;
import http.Request;
import http.response.HtmlJsonResponse;
import http.response.Response;

public class DailyQuoteController extends Controller {
    public DailyQuoteController(Request request) {
        super(request);
    }

    @Override
    public Response doGet() {
        Gson gson = new Gson();
        String json = gson.toJson(HelpServerThread.quotesOfDay.get(HelpServerThread.index.get()));

        return new HtmlJsonResponse(json.length(), json);
    }

    @Override
    public Response doPost() {
        return null;
    }
}
