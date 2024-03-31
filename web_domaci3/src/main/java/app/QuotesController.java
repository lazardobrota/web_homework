package app;

import http.HelpServerThread;
import http.MainServerThread;
import http.Quote;
import http.Request;
import http.response.HtmlResponse;
import http.response.RedirectResponse;
import http.response.Response;

import java.util.List;

public class QuotesController extends Controller{

    public QuotesController(Request request) {
        super(request);
    }

    @Override
    public Response doGet() {
        StringBuilder htmlBody = new StringBuilder("<body>" +
                "<form method=\"POST\" action=\"/save-quote\">" +
                "   <label>Author</label>" +
                "   <input name=\"author\" type=\"text\"> <br><br>" +
                "   <label>Quote</label>" +
                "   <input name=\"quote\" type=\"text\"> <br><br>" +
                "   <button>Save Quote</button>" +
                "</form>"
        );

        htmlBody.append("<h3>Quote of the day</h3>");
        htmlBody.
                append("<p>").
                append(MainServerThread.quoteOfDay).
                append("</p>");

        htmlBody.append("<h2>Saved Quotes</h2>");
        List<Quote> quotesList = MainServerThread.cookieQuotes.get(request.getCookie());
        if (quotesList != null) {
            for(Quote q : quotesList) {
                htmlBody.
                        append("<p>").
                        append(q.toString()).
                        append("</p>");
            }
        }
        htmlBody.append(" </body>");

        String content =
                "<!DOCTYPE html>" +
                "<html>" +
                "  <head>" +
                        "<link rel=\"icon\" href=\"data:;base64,=\">" + //satisfy annoying \favicon call
                "  </head>" +
                    htmlBody.toString() +
                "</html>";

        return new HtmlResponse(content, request);
    }

    @Override
    public Response doPost() {

        return new RedirectResponse("/quotes");
    }
}
