import java.net.*;
import java.io.*;

class Server {
    ServerSocket server = null;

    Server(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");

            while (true) {
                System.out.println("Waiting for a client ...");
                Socket socket = server.accept();
                System.out.println("Client accepted");

                // Create a new thread to handle the client
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) {
        Server server = new Server(5000);
    }

    // Inner class to handle a client in a separate thread
    private static class ClientHandler implements Runnable {
        private Socket socket;
        private DataInputStream in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

                String line = "";
                while (!line.equals("Over")) {
                    try {
                        line = in.readUTF();
                        System.out.println(line);

                        // You can add code here to send data back to the client if needed

                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
                System.out.println("Closing connection");

                socket.close();
                in.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
