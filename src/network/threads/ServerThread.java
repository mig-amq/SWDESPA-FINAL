package network.threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {
    private int port = 50000;
    private ArrayList<ServerResponseThread> serverResponseThreads;
    private final ServerSocket serverSocket;
    private boolean started;

    public ServerThread (int port) throws IOException {
        this.setStarted(false);

        if (port > 0 && port < 65535) {
            this.setPort(port);
            this.serverSocket = new ServerSocket(this.port);

            this.serverResponseThreads = new ArrayList<>();
            this.setStarted(true);
        } else
            throw new IOException("Error: Invalid port number.");
    }

    @Override
    public void run() {
        System.out.println("Server Started");

        while (this.isStarted() && !this.serverSocket.isClosed()) {
            try {
                System.out.println("Accepting Clients...");
                Socket socket = this.serverSocket.accept();
                System.out.println("A client was accepted!");
                ServerResponseThread thread = new ServerResponseThread(socket, this);
                thread.start();

                this.getServerResponseThreads().add(thread);
            } catch (IOException e) {
                System.out.println("Server Closed");
            }
        }

    }

    public void close () throws IOException{
        for (ServerResponseThread cthread : this.getServerResponseThreads())
            cthread.close();

        this.setStarted(false);
        this.serverSocket.close();
    }

    public boolean isStarted() {
        return started;
    }

    public ArrayList<ServerResponseThread> getServerResponseThreads() {
        return serverResponseThreads;
    }

    public int getPort() {
        return port;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setServerResponseThreads(ArrayList<ServerResponseThread> serverResponseThreads) {
        this.serverResponseThreads = serverResponseThreads;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
