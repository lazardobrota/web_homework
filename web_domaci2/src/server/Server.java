package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Server {

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    void startServer() {
        try {

            while (true) {
                Socket socket = serverSocket.accept();

                Thread serverThread = new Thread(new ServerThread(socket));
                serverThread.start();
            }
        }
        catch (IOException e) {
            closeServer();
        }
    }

    void closeServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(new ServerSocket(8000));
            server.startServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}