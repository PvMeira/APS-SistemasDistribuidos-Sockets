package server;

import runnable.ClientlistenerForServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by pvmeira on 15/06/17.
 */
public class ChatServer {
    private final static Logger LOGGER = Logger.getLogger(ChatServer.class.getName());

    private ServerSocket serverSocket;
    private Socket socket;
    private String name;
    private List<PrintWriter> printWriters = new ArrayList<>();

    public ChatServer(String name) {
        this.name = name;
        try {
            System.out.println("Starting the server on port : 5000");
            this.serverSocket = new ServerSocket(5000);
            System.out.println(String.format(" JAGER %s ", this.name) + " operational ");

            while (true) {
                this.socket = serverSocket.accept();
                new Thread(new ClientlistenerForServer(this.socket, this.printWriters)).start();
                this.printWriters.add(new PrintWriter(this.socket.getOutputStream()));
            }
        } catch (IOException io) {
            System.out.println("ChatServer.ChatServer");
            System.out.println("Eror when creating the server");
            io.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new ChatServer("DipsyDanger");
    }
}
