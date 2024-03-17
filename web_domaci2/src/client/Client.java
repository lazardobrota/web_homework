package client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Client {

    private static final List<String> uniqueUsernames = new ArrayList<>();
    private static final int PORT = 8000;

    private final Socket socket;

    private BufferedReader bufferedReader = null;
    private PrintWriter printWriter = null;

    public Client(Socket socket) {
        this.socket = socket;
    }


    private void sendMessage() {
        Scanner scanner = new Scanner(System.in);
        try {

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            receiveMessage();

            while (!socket.isClosed()) {
                if (scanner.hasNext())
                    printWriter.println(scanner.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Close Client send");
            scanner.close();
            closeClient();
        }
    }

    private void receiveMessage() {
        new Thread(() -> {
            try {
                String message;
                while (!socket.isClosed()) {
                    System.out.println(bufferedReader.readLine());
                }
            } catch (IOException e) {
                System.out.println("Close Client recive");
                closeClient();
            }
        }).start();
    }

    private void closeClient() {
        if (printWriter != null)
            printWriter.close();

        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (socket != null) {
            try {
                socket.close(); //when this is closed it will close socket on server.ServerThread, same address
                System.out.println("Catch socket close " + socket.isClosed());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void main(String[] args) {
        try {
            Client client = new Client(new Socket("localhost", PORT));
            client.sendMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
