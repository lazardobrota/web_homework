package http.response;

public class RedirectResponse extends Response {

    private final String relocation;

    public RedirectResponse(String relocation) {
        this.relocation = relocation;
    }

    @Override
    public String getResponseString() {
        return  "HTTP/1.1 303 See Other\r\n" +
                "Location: " + relocation + "\r\n";
    }
}
