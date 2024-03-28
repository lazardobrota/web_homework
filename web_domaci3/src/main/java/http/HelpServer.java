package http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HelpServer {

    public static final int helpServerPort = 8081;

    public static void main(String[] args) {
        //Waits for day to pass
        new Thread(()-> {

            while (true) {
                try {
                    HelpServerThread.index.set(HelpServerThread.getRandomNumber(0, HelpServerThread.quotesOfDay.size() - 1));

                    Duration seconds = Duration.between(LocalDateTime.now(), LocalDate.now().atStartOfDay().plusDays(1)); //Time left until end of the day
                    TimeUnit.SECONDS.sleep(seconds.getSeconds()); //Thread.sleep
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        try {
            ServerSocket serverSocket = new ServerSocket(helpServerPort);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new HelpServerThread(socket)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
