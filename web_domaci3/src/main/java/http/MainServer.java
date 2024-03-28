package http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;

public class MainServer {
    public static final int mainServerPort = 8080;

    public static void main(String[] args) {

        try {
            ServerSocket ss = new ServerSocket(mainServerPort);
            while (true) {
                Socket sock = ss.accept();
                new Thread(new MainServerThread(sock)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}