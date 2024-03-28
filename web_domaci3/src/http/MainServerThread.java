package http;

import app.RequestHandler;
import http.response.Response;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MainServerThread implements Runnable {

    private final Socket socket;

    public static AtomicInteger cookieNum = new AtomicInteger(1);

    public static final Map<Cookie, List<Quote>> cookieQuotes = new ConcurrentHashMap<>();

    public static String quoteOfDay = "";

    private BufferedReader in;
    private PrintWriter out;
    public MainServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            System.out.println();
            String requestLine = in.readLine();

            String[] lineSplit = requestLine.split(" ");
            String method = lineSplit[0]; //get, post, put, ...
            String path = lineSplit[1];
            Cookie cookie = null;
            int contentLen = 0;

            //skip to content-length or end of header
            do {
                System.out.println(requestLine);
                requestLine = in.readLine();
                lineSplit = requestLine.split(" ");
                if (lineSplit[0].equals("Content-Length:")) {
                    contentLen = Integer.parseInt(lineSplit[1]);
                }
                if (lineSplit[0].equals("Cookie:")) {
                    String[] cookieSplit = lineSplit[1].split("=");
                    cookie = new Cookie(cookieSplit[0], cookieSplit[1]);
                }

            }while (!requestLine.trim().isEmpty());

            if (cookie == null) {

                cookie = new Cookie("user" + cookieNum.get(), "s" + cookieNum.get());
                cookieNum.getAndAdd(1);

                cookieQuotes.put(cookie, new ArrayList<>());
            }

            if (method.equals(HttpMethod.POST.toString())) {
                char[] buffer = new char[contentLen];
                in.read(buffer);

                createQuote(new String(buffer), cookie);
                System.out.println("Yooooooooooo: " + new String(buffer));
            }


            quoteOfDay = getQuoteOfDay();

            Request request = new Request(HttpMethod.valueOf(method), path, cookie);

            RequestHandler requestHandler = new RequestHandler();
            Response response = requestHandler.handle(request);

            System.out.println("\nRESPONSE\n");
            System.out.println(response.getResponseString());
            out.println(response.getResponseString());

            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
            closeSocket();
        }
    }

    private void createQuote(String buffer, Cookie cookie) {
        //Example: author=foo&quote=bar
        String[] splitInput = buffer.split("[&=]"); //[&=] split by & or =

        if (splitInput.length != 4)
            return;
        cookieQuotes.get(cookie).add(new Quote(splitInput[1], splitInput[3]));
    }

    private void closeSocket() {
        try {
            if (in != null)
                in.close();

            if (out != null)
                out.close();

            if (socket != null)
                socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getQuoteOfDay() throws IOException {
        Socket s = new Socket("localhost", 8081);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        return bufferedReader.readLine();
    }
}
