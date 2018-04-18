package network.threads;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ServerResponseThread extends Thread {
    private boolean started;
    private Socket socket;
    private ServerThread serverThread;

    public ServerResponseThread(Socket socket, ServerThread server) {
        this.socket = socket;
        this.serverThread = server;
        this.setStarted(true);
    }

    @Override
    public void run() {
        String message;

        while (this.isStarted() && socket != null && !socket.isClosed()) {
            try {
                Scanner in = new Scanner(this.socket.getInputStream());

                if (in.hasNextLine()) {
                    message = in.nextLine();
                    switch (message) {
                        case "UPDATE":
                            for (ServerResponseThread sthread : this.serverThread.getServerResponseThreads()) {
                                PrintWriter writer = new PrintWriter(sthread.getSocket().getOutputStream());

                                writer.println("UPDATE");
                                writer.flush();
                            }
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close () {
        this.setStarted(true);
    }

    public boolean isStarted() {
        return started;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
