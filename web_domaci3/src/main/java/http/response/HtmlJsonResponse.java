package http.response;

public class HtmlJsonResponse extends Response{

    private final int len;

    private final String json;

    public HtmlJsonResponse(int len, String json) {
        this.len = len;
        this.json = json;
    }

    @Override
    public String getResponseString() {
        String response =
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "Content-Length: " + len + "\r\n\r\n";

        response += json;

        return response;
    }
}
