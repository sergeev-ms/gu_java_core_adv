package jcoreadv.lesson7.server;

import com.sun.istack.internal.NotNull;
import jcoreadv.lesson7.server.service.AuthService;
import jcoreadv.lesson7.server.service.BaseAuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    private ServerSocket server;
    private List<ClientHandler> clients;
    private AuthService authService;
    private Socket socket;

    public AuthService getAuthService() {
        return authService;
    }

    private final int PORT = 8189;

    public static void main(String[] args) {
        new MyServer();
    }

    public MyServer() {
        try {
            server = new ServerSocket(PORT);
            socket = null;
            authService = new BaseAuthService();
            authService.start();
            clients = new ArrayList<>();
            while (true) {
                System.out.println("Сервер ожидает подключения");
                socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при работе сервера");
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            authService.stop();
        }
    }

    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(nick)) return true;
        }
        return false;
    }

    public synchronized void broadcastMsg(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }

    public synchronized void privateMessage(@NotNull String name, @NotNull String msg, String senderName){
        for (ClientHandler client : clients) {
            if (client.getName().equals(name))
                client.sendMsg(senderName + ": " + msg);
        }
    }

    public synchronized void unsubscribe(ClientHandler o) {
        clients.remove(o);
    }

    public synchronized void subscribe(ClientHandler o) {
        clients.add(o);
    }

    public Socket getSocket() {
        return socket;
    }

}
