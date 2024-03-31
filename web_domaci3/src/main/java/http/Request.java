package http;

public class Request {

    private final HttpMethod httpMethod;
    private final String path;

    private Cookie cookie;

    public Request(HttpMethod httpMethod, String path, Cookie cookie) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.cookie = cookie;
    }

    public Request(HttpMethod httpMethod, String path) {
        this.httpMethod = httpMethod;
        this.path = path;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public Cookie getCookie() {
        return cookie;
    }
}
