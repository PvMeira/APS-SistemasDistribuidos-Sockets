package runnable;

import javax.swing.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by pvmeira on 15/06/17.
 */
public class ServerListenerForInterface implements Runnable {

    private final static Logger LOGGER = Logger.getLogger(ServerListenerForInterface.class.getName());

    private final Scanner scanner;
    private final Socket socket;
    private JTextArea jTextArea;
    private String clientName;

    public ServerListenerForInterface(Scanner scanner, Socket socket, JTextArea jTextArea, String clientName) {
        this.scanner = scanner;
        this.socket = socket;
        this.jTextArea = jTextArea;
        this.clientName = clientName;
    }

    @Override
    public void run() {
        try {
            String currenttext;
            while ((currenttext = scanner.nextLine()) != null)
                this.jTextArea.append(clientName + "  says :  " + currenttext + "\n");
        } catch (NoSuchElementException e) {
            LOGGER.warning(" Client disconect ");
        } catch (IllegalStateException e) {
            LOGGER.severe("Error when sending a message");
            e.printStackTrace();
        }

    }
}
