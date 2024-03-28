package http.response;

import http.Request;

public class HtmlResponse extends Response {
    private final String html;

    private final Request request;

    public HtmlResponse(String html, Request request) {
        this.html = html;
        this.request = request;
    }

    @Override
    public String getResponseString() {
        String response =
                "HTTP/1.1 200 OK\r\n" +
                "Set-Cookie: " + request.getCookie().getName() + "=" + request.getCookie().getValue() + "\r\n" +
                "Content-Type: text/html\r\n\r\n";
        response += html;

        //todo call second server thread for quote of the day
        return response;
    }
}
