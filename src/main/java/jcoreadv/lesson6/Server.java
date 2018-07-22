package jcoreadv.lesson6;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    static PrintWriter outputToClient;
    static Scanner keyBoardScanner = new Scanner(System.in);
    public static void main(String[] args) {
        int serverPort;
        try {
            serverPort = Integer.parseInt(args[0]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            System.out.println("Будет использован дефолтный порт 8189");
            serverPort = 8189;
        }
        final Thread keyBoardReader = new Thread(() -> {
            while (true) {
                if (keyBoardScanner.hasNext()) {
                    final String message = keyBoardScanner.nextLine();
                    sendMessage(message);
                }
            }
        });
        keyBoardReader.setDaemon(true);
        keyBoardReader.start();

        final int finalServerPort = serverPort;
            ServerSocket serverSocket = null;
            Socket socket;
            try {
                serverSocket = new ServerSocket(finalServerPort);
                System.out.println("Сервер запущен, ожидаем подключения...");
                socket = serverSocket.accept();
                System.out.println("Клиент подключился");
                Scanner inputFromClient = new Scanner(socket.getInputStream());
                outputToClient = new PrintWriter(socket.getOutputStream());
                while (true) {
                    if (inputFromClient.hasNext()) {
                        String clientMessage = inputFromClient.nextLine();
                        System.out.println("[КЛИЕНТ]: " + clientMessage);
                        if (clientMessage.equals("end")) break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Ошибка инициализации сервера");
            } finally {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }
    private static void sendMessage(String message){
        outputToClient.println(message);
        outputToClient.flush();
    }
}
