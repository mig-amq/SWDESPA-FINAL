package network.threads;

import udc.Model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class ClientThread extends Thread {
    private Model model;
    private Socket socket;
    private boolean started, hasMessage;
    private final LinkedList<String> messages;

    public ClientThread (Model model) throws IOException {
        this.messages = new LinkedList<>();
        this.setSocket(new Socket(model.getServerAddress(), model.getServerPort()));
        this.setHasMessage(false);
        this.setStarted(true);

    }

    public void update() {
        synchronized (this.messages) {
            this.messages.add("UPDATE");
            this.setHasMessage(!this.messages.isEmpty());
        }
    }

    @Override
    public void run() {
        String message = "";
        Scanner in;
        PrintWriter out;

        while (this.isStarted() && socket != null && !socket.isClosed()) {
            try {
                in = new Scanner(socket.getInputStream());

                if (in.hasNextLine()) { // check if server sent a message
                    if(in.nextLine().equals("UPDATE")) {
                        this.model.getState();
                    }
                }

                synchronized (this.messages) {
                    if (this.hasMessage) {
                        message = this.getMessages().pop();
                        this.setHasMessage(!this.messages.isEmpty());
                    }
                }

                if (this.hasMessage) {
                    if (!message.isEmpty()) {
                        out = new PrintWriter(socket.getOutputStream());

                        out.println(message);
                        out.flush();

                        message = "";
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public LinkedList<String> getMessages() {
        return messages;
    }

    public boolean isStarted() {
        return started;
    }

    public boolean hasMessage() {
        return hasMessage;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setHasMessage(boolean hasMessage) {
        this.hasMessage = hasMessage;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
