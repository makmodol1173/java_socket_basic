import java.net.*;
import java.io.*;

class Server
{
	 Socket socket = null;
	 ServerSocket server = null;
	 DataInputStream in = null;

	Server(int port)
	{
		try
		{
			server = new ServerSocket(port);
			System.out.println("Server started");

			System.out.println("Waiting for a client ...");

			socket = server.accept();
			System.out.println("Client accepted");

			in = new DataInputStream(
				new BufferedInputStream(socket.getInputStream()));

			String line = "";

			while (!line.equals("Over"))
			{
				try
				{
					line = in.readUTF();
					System.out.println(line);

				}
				catch(IOException e)
				{
					System.out.println(e);
				}
			}
			System.out.println("Closing connection");

			socket.close();
			in.close();
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}

	public static void main(String args[])
	{
		Server server = new Server(5000);
	}
}
