package jcoreadv.lesson4;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChatWindow extends JFrame{
    private static final String USERNAME_PREFIX = "[Вы]: ";
    private JPanel mainPanel;
    private JTextArea chartArea;
    private JButton buttonSend;
    private JTextArea msgFld;

    public ChatWindow(String title) {
        super.setTitle(title);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        buttonSend.addActionListener(e -> onSend());
        msgFld.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER)
                    msgFld.append("\n");
                else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    onSend();
                }
            }
        });
    }

    private void onSend() {
        final String text = msgFld.getText();
        if (!text.isEmpty()) {
            chartArea.append("\n");
            chartArea.append(USERNAME_PREFIX);
            chartArea.append(text);
            msgFld.setText(null);
        }
        msgFld.requestFocus();
    }
}
