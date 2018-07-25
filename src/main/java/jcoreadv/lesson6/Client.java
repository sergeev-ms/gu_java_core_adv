package jcoreadv.lesson6;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static PrintWriter outputToServer;
    static Scanner inputFromServer;

    public static void main(String[] args) {
        String serverAddress;
        int serverPort;
        Socket socket;
        Scanner keyBoardScanner = new Scanner(System.in);
        try {
            serverPort = Integer.parseInt(args[1]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            System.out.println("Будет использован дефолтный порт 8189");
            serverPort = 8189;
        }
        try {
            serverAddress = args[0];
        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            System.out.println("Будет использован дефолтный адрес localhost");
            serverAddress = "localhost";
        }
        try {
            socket = new Socket(serverAddress, serverPort);
            inputFromServer = new Scanner(socket.getInputStream());
            outputToServer = new PrintWriter(socket.getOutputStream());
            System.out.println("Клиент запущен и соединился с сервером");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Не смогли соединиться с сервером. Запустите снова, когда сервер будет готов");
            System.exit(1);
        }
        final Thread keyBoardReader = new Thread(() -> {
            try {
                String outgoingMessage;
                while (true) {
                    if (keyBoardScanner.hasNext()) {
                        outgoingMessage = keyBoardScanner.nextLine();
                        sendMessage(outgoingMessage);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        keyBoardReader.setDaemon(true);
        keyBoardReader.start();

        try {
            while (true) {
                if (inputFromServer.hasNext()) {
                    String message = inputFromServer.nextLine();
                    if (message.equalsIgnoreCase("end session")) break;
                    System.out.println("[СЕРВЕР]: " + message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void sendMessage(String message) {
        outputToServer.println(message);
        outputToServer.flush();
    }
}
