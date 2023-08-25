import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class SocketServer {
    public static void main(String[] args) {
        int port = 12345;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port " + port);

            ArrayList<Player> playerList = new ArrayList<>();
            LudoBoard board = new LudoBoard();  // Assuming you have a LudoBoard class

            for (int i = 0; i < 4; i++) {
                Player player = new Player("Player " + (i + 1));
                playerList.add(player);
            }

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                PlayerConnectionHandler handler = new PlayerConnectionHandler(clientSocket, playerList, board);
                Thread handlerThread = new Thread(handler);
                handlerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
