package jcoreadv.lesson7.server;


import jcoreadv.lesson7.utils.Contract;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private MyServer myServer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;
    private static String incomingMessageTemplate = "от %s: %s\n";
    private static String newUserNotification = " зашел в чат";
    private static String userLeaveChatNotification = "%s вышел из чата";
    private static final String nickIsBusyMessage = "Учетная запись уже используется";
    private static final String incorrectAuthPairMessage = "Неверные логин/пароль";

    public String getName() {
        return name;
    }

    public ClientHandler(MyServer myServer) {
        try {
            this.myServer = myServer;
            this.socket = myServer.getSocket();
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "";
            new Thread(() -> {
                try {
                    while (true) { // цикл авторизации
                        String str = in.readUTF();
                        if (str.startsWith(Contract.AUTH_COMMAND)) {
                            String[] parts = str.split("\\s");
                            String nick = myServer.getAuthService().getNickByLoginPass(parts[1], parts[2]);
                            if (nick != null) {
                                if (!myServer.isNickBusy(nick)) {
                                    sendMsg(Contract.AUTH_OK_MESSAGE + nick);
                                    name = nick;
                                    myServer.broadcastMsg(name + newUserNotification);
                                    myServer.subscribe(this);
                                    break;
                                } else {
                                    sendMsg(nickIsBusyMessage);
                                }
                            } else {
                                sendMsg(incorrectAuthPairMessage);
                            }
                        }
                    }
                    while (true) { // цикл получения сообщений
                        String message = in.readUTF();
                        System.out.printf(incomingMessageTemplate, name, message);
                        if (message.equals(Contract.END_COMMAND)) break;
                        if (message.matches("(\\/w\\s+)(\\w+\\s+)(\\w+.*\\s*)+")) {
                            final String[] split = message.split("\\s+",3);
                            myServer.privateMessage(split[1], split[2], name);
                        }
                        else myServer.broadcastMsg(name + ": " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    myServer.unsubscribe(this);
                    myServer.broadcastMsg(userLeaveChatNotification + name);
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании обработчика клиента");
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
