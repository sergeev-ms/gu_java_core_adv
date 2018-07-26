package jcoreadv.lesson7.client;

import jcoreadv.lesson7.utils.Contract;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends JFrame {

    public static void main(String[] args) {
        new Client();
    }

    private JTextField jtf;
    private JTextArea textArea;
    private final String SERVER_ADDR = "localhost";//127.0.0.1
    private final int SERVER_PORT = 8189;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private final static String authMessageTemplate = "%s %s %s";
    public Client() {
        try {
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            setAuthorized(false);
            Thread t = new Thread(() -> {
                try {
                    while (true) {
                        String str = in.readUTF();
                        if(str.startsWith(Contract.AUTH_OK_MESSAGE)) {
                            setAuthorized(true);
                            break;
                        }
                        textArea.append(str + "\n");
                    }
                    while (true) {
                        String str = in.readUTF();
                        if (str.equals(Contract.END_COMMAND)) {
                            break;
                        }
                        textArea.append(str + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setAuthorized(false);
                }
            });
            t.setDaemon(true);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBounds(600, 300, 500, 500);
        setTitle("Client");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        JScrollPane jsp = new JScrollPane(textArea);
        add(jsp, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);
        JButton jbAuth = new JButton("AUTH_COMMAND");
        JButton jbSend = new JButton("SEND");
        bottomPanel.add(jbSend, BorderLayout.EAST);
        bottomPanel.add(jbAuth, BorderLayout.WEST);
        jtf = new JTextField();
        bottomPanel.add(jtf, BorderLayout.CENTER);
        jbSend.addActionListener(e -> {
                    sendMsg();
                    jtf.grabFocus();
                });
        jbAuth.addActionListener(e -> onAuthClick());
        jtf.addActionListener(e -> sendMsg());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    out.writeUTF("end");
                    out.flush();
                    socket.close();
                    out.close();
                    in.close();
                } catch (IOException exc) {
                }
            }
        });
        setVisible(true);
    }

    public void setAuthorized(boolean v) {

    }

    public void onAuthClick() {
        try {
            String login = "login1";
            String password = "pass1";
            out.writeUTF(String.format(authMessageTemplate, Contract.AUTH_COMMAND, login, password));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMsg() {
        try {
            out.writeUTF(jtf.getText());
            jtf.setText("");
        } catch (IOException e) {
            System.out.println("Ошибка отправки сообщения");
        }
    }

}
