package server;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

class ServerThread implements Runnable{

    private static final List<String> badWords = new CopyOnWriteArrayList<>(List.of("bad", "hello", "google"));
    private static final List<ServerThread> serverThreads = new CopyOnWriteArrayList<>(); //static because it will be shared across threads
    private static final List<String> messageHistory = new CopyOnWriteArrayList<>();
    private static final List<String> uniqueUsernames = new ArrayList<>();

    private static final Object key = new Object();
    private static final int historyLength = 100;

    private final Socket socket;

    private BufferedReader bufferedReader = null;
    private PrintWriter printWriter = null;

    private String clientUsername;

    public ServerThread(Socket socket) {
        this.socket = socket;

    }

    //byte stream - end word stream
    //char stream - end word writer
    //foreach and iterator both use getArray(), so both are valid for CopyOnWriteArrayList

    @Override
    public void run() {

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            do {
                printWriter.println(formatMessage("Enter a username: ", true));
                clientUsername = bufferedReader.readLine();

            } while (!checkIfUnique(clientUsername) || !socket.isConnected());

            serverThreads.add(this);

            printWriter.println(formatMessage("Welcome \"" + clientUsername + "\"", true));
            sendChatHistory(printWriter);
            broadcastMessage(formatMessage("\"" + clientUsername + "\" has joined.", true));

            String message;

            while (socket.isConnected()) {
                message = censorMessage(bufferedReader.readLine());

                broadcastMessage(formatMessage(message, false));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            closeThread();
        }
    }

    private String censorMessage(String message) {
        for(String word : badWords) {
            Pattern rx = Pattern.compile("\\b" + word + "\\b", Pattern.CASE_INSENSITIVE);
            message = rx.matcher(message).replaceAll(makeReplaceString(word)).replace('\0', '*');
        }
        return message;
    }

    //example: guess -> g000s
    private String makeReplaceString(String word) {
        return word.charAt(0) + new String(new char[word.length() - 2]) + word.substring(word.length() - 1);
    }

    private boolean checkIfUnique(String username) {
        synchronized (key) {
            if (uniqueUsernames.contains(username)) {
                printWriter.println(formatMessage("Username already taken, try again", true));
                return false;
            }

            uniqueUsernames.add(username);
            return true;
        }
    }

    private String formatMessage(String message, boolean isServer) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LocalDateTime.now()).append(" - ");
        if (isServer)
            stringBuilder.append("server.Server: ");
        else
            stringBuilder.append(clientUsername).append(": ");

        stringBuilder.append(message);
        return stringBuilder.toString();
    }

    private void broadcastMessage(String message) {
        if (!message.toLowerCase().contains("server:"))
            messageHistory.add(message);
        while (messageHistory.size() > historyLength) {
            messageHistory.removeFirst();
        }

        serverThreads.iterator().forEachRemaining((serverThread) -> {
            //Don't send to self
            if (!serverThread.getClientUsername().equals(clientUsername))
                serverThread.getPrintWriter().println(message);
        });
    }

    private void sendChatHistory(PrintWriter printWriter) {
        messageHistory.iterator().forEachRemaining(printWriter::println);
    }

    private void closeThread() {
        serverThreads.remove(this);
        broadcastMessage(formatMessage("client \"" + this.clientUsername + "\" has left.", true));

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
                socket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public String getClientUsername() {
        return clientUsername;
    }
}
