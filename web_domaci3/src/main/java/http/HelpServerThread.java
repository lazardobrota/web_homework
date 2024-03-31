package http;

import app.RequestHandler;
import com.google.gson.Gson;
import http.response.Response;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class HelpServerThread implements Runnable {

    private final Socket socket;

    public static final List<Quote> quotesOfDay = new ArrayList<>(
            List.of(
                    new Quote("Nikola Tesla", "One must be sane to think clearly, but one can think deeply and be quite insane"),
                    new Quote("Albert Einstein", "Life is like riding a bicycle. To keep your balance, you must keep moving"),
                    new Quote("Zivkovic Slobodan", "polupoljoprivrednik polucovek kojeg su isterali iz elektronske industrije"),
                    new Quote("Lazar", "Weeeeeeeeeeeeeeeeeeeeeeee")
            ));

    public static AtomicInteger index = new AtomicInteger(0);

    private BufferedReader in;
    private PrintWriter out;

    public HelpServerThread(Socket socket) {
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

            do {
                System.out.println(requestLine);
                requestLine = in.readLine();
            }while (!requestLine.trim().isEmpty());

            Request request = new Request(HttpMethod.valueOf(method), path);

            RequestHandler requestHandler = new RequestHandler();
            Response response = requestHandler.handle(request);

            System.out.println("\nRESPONSE\n");
            System.out.println(response.getResponseString());
            out.println(response.getResponseString());

//            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
//
//            Gson gson = new Gson();
//            out.println(gson.toJson(quotesOfDay.get(index.get())));

            out.close();
            socket.close();
        } catch (IOException e) {
            closeSocket();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int getRandomNumber(int min, int max) {
        return (int) Math.round((Math.random() * (max - min)) + min);
    }

    private void closeSocket() {
        try {
            if (out != null)
                out.close();

            if (socket != null)
                socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
