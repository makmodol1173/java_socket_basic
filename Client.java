import java.io.*;
import java.net.*;

class Client {
    Socket socket = null;
    BufferedReader input = null;
    DataOutputStream out = null;
    DataInputStream serverIn = null; // New data input stream for server messages

    Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            input = new BufferedReader(new InputStreamReader(System.in));
            out = new DataOutputStream(socket.getOutputStream());
            serverIn = new DataInputStream(new BufferedInputStream(socket.getInputStream())); // Initialize server input stream

            String line = "";

            // Start a new thread to read and display server messages
            Thread serverReaderThread = new Thread(() -> {
                try {
                    while (true) {
                        String serverMessage = serverIn.readUTF();
                        System.out.println("Server: " + serverMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading from server: " + e);
                }
            });
            serverReaderThread.start();

            while (!line.equals("Over")) {
                try {
                    line = input.readLine();
                    out.writeUTF(line);
                } catch (IOException i) {
                    System.out.println(i);
                }
            }

            input.close();
            out.close();
            socket.close();

        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        Client client = new Client("192.168.0.175", 5000);
    }
}
