package client.buttonListner;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by pvmeira on 15/06/17.
 */
public class SendButtonListener implements ActionListener {

    private PrintWriter writer;
    private JTextField field;
    private String clientName;

    @Override
    public void actionPerformed(ActionEvent e) {

        this.writer.println(this.clientName + "  send :   " + field.getText());
        this.writer.flush();
        this.field.setText("");
        this.field.requestFocus();

    }

    public SendButtonListener(PrintWriter writer, JTextField field, String clientName) {
        this.clientName = clientName;
        this.writer = writer;
        this.field = field;
    }
}
