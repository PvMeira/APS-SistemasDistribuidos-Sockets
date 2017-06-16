package runnable;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by pvmeira on 15/06/17.
 */
public class ClientlistenerForServer implements Runnable {

    private final static Logger LOGGER = Logger.getLogger(ClientlistenerForServer.class.getName());


    private Socket socket;
    private Scanner scanner;
    private List<PrintWriter> printWriters;

    public ClientlistenerForServer(Socket socket, List<PrintWriter> printWriters) {
        try {
            this.printWriters = printWriters;
            this.socket = socket;
            this.scanner = new Scanner(this.socket.getInputStream());

        } catch (IOException e) {
            LOGGER.severe("Error when trying to get the client inputStream ");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String text;
            String banner = this.socket.getLocalAddress().toString() + this.socket.getPort() + "  client ";
            while ((text = this.scanner.nextLine()) != null) {
                System.out.println(banner + "  " + text);
                this.sendMessageToAllClients(text);
            }
        } catch (NoSuchElementException e) {
            LOGGER.warning(" Client disconect ");
            e.printStackTrace();
        } catch (IllegalStateException e) {
            LOGGER.severe("Error when sending a message");
            e.printStackTrace();
        }
    }

    private void sendMessageToAllClients(String message) {
        for (PrintWriter printWriter : this.printWriters) {
            try {
                printWriter.println(message);
                printWriter.flush();
            } catch (Exception e) {
                LOGGER.severe("Error when server was trying to update the history on client");
                e.printStackTrace();
            }
        }

    }

}
