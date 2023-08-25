import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Server {
    ServerSocket server = null;
    List<ClientHandler> clients = new ArrayList<>();

    Server(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");

            while (true) {
                System.out.println("Waiting for a client ...");
                Socket socket = server.accept();
                System.out.println("Client accepted");

                ClientHandler clientHandler = new ClientHandler(socket);
                clients.add(clientHandler); // Add the client handler to the list
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
    private class ClientHandler implements Runnable {
        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                out = new DataOutputStream(socket.getOutputStream());

                String line = "";
                while (!line.equals("Over")) {
                    try {
                        line = in.readUTF();
                        System.out.println(line);

                        // Broadcast the received message to all clients
                        broadcastMessage(line);

                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
                System.out.println("Closing connection");

                socket.close();
                in.close();
                out.close();
                clients.remove(this); // Remove this client handler from the list
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        // Broadcast a message to all connected clients
        private void broadcastMessage(String message) {
            for (ClientHandler client : clients) {
                try {
                    client.out.writeUTF(message);
                } catch (IOException e) {
                    System.out.println("Error broadcasting message: " + e);
                }
            }
        }
        
        // Method to send a message to this client
        public void sendMessage(String message) {
            try {
                out.writeUTF(message);
            } catch (IOException e) {
                System.out.println("Error sending message to client: " + e);
            }
        }
    }
}
