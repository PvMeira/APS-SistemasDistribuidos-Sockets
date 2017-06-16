package client;

import client.buttonListner.SendButtonListener;
import runnable.ServerListenerForInterface;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by pvmeira on 15/06/17.
 */
public class ChatClientInterface extends JFrame {

    private final static Logger LOGGER = Logger.getLogger(ChatClientInterface.class.getName());

    private JTextField usertext;
    private PrintWriter writer;
    private Socket socket;
    private String clientName;
    private JTextArea history;
    private JScrollPane scrollPane;
    private Scanner scanner;

    public ChatClientInterface(String clientName) {
        super("Aps3 - chatInterface - " + clientName);
        this.clientName = clientName;
        //configuration
        this.usertext = new JTextField();
        this.history = new JTextArea();
        this.scrollPane = new JScrollPane(this.history);
        JButton sendButton = new JButton("Send");
        LOGGER.info("Starting the config of the server." + " Client name : " + this.clientName);
        this.configClientNetWork();
        LOGGER.info("Config of the server was successful complete." + " Client name : " + this.clientName);
        sendButton.addActionListener(new SendButtonListener(this.writer, this.usertext, this.clientName));

        //Container configuration
        Container container = new Container();
        container.setLayout(new BorderLayout());
        container.add(BorderLayout.CENTER, this.usertext);
        container.add(BorderLayout.EAST, sendButton);
        getContentPane().add(BorderLayout.SOUTH, container);
        getContentPane().add(BorderLayout.CENTER, this.scrollPane);
        //End of container config

        //Setings from Jpanel
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }

    private void configClientNetWork() {
        try {
            this.socket = new Socket("192.168.0.14", 5000);
            this.writer = new PrintWriter(this.socket.getOutputStream());
            this.scanner = new Scanner(this.socket.getInputStream());
            LOGGER.info("Connect on server : 192.168.0.14:" + this.socket.getPort());
            LOGGER.info(" Starting a new Thread  ");
            new Thread(new ServerListenerForInterface(this.scanner, this.socket, this.history, this.clientName)).start();

        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.severe("Error on creating and configure the server conection");
        }


    }

    public static void main(String[] args) {
        new ChatClientInterface(" Perosn A");
        new ChatClientInterface(" Person B ");
        new ChatClientInterface(" Person C ");

    }


}
