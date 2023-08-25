import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SocketClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";  // Change this to the server's IP address
        int serverPort = 12345;

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner userInput = new Scanner(System.in);

            while (true) {
                System.out.print("Enter your choice: ");
                String choice = userInput.nextLine();
                out.println(choice);

                String response = in.readLine();
                System.out.println("Server response: " + response);

                if (response.equalsIgnoreCase("exit")) {
                    break;
                }
            }

            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
