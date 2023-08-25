
import java.io.*;
import java.net.*;

 class Client {
     Socket socket = null;
     BufferedReader input = null;
     DataOutputStream out = null;

    Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            input = new BufferedReader(new InputStreamReader(System.in));
            out = new DataOutputStream(socket.getOutputStream());

            String line = "";

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
        Client client = new Client("127.0.0.1", 5000);
    }
}
