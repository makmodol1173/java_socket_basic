import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    private ServerSocket ss;
    public Server(ServerSocket ss) {
        this.ss = ss;
    }

    public void startServer() {
        try {

            while(true) {

                Socket socket = ss.accept();
                System.out.println("A new client recently joined.....");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();

            }

        } catch (IOException e) {

        }
    }

    public void closeServerSocket() {
        try {
            if(ss != null) {
                ss.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(1234);
        Server server = new Server(ss);

        System.out.println(InetAddress.getLocalHost());
        server.startServer();

    }
}
