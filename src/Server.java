import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            int count = 0;
            int number = 100;

            try (Socket clientSocket = serverSocket.accept();
                 DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                 DataInputStream in = new DataInputStream(clientSocket.getInputStream())) {
                while (!clientSocket.isClosed()) {
                    System.out.println("Client accepted " + (count++));
                    String request = in.readUTF();

                    int requestParsed = Integer.parseInt(request);
                    System.out.println(requestParsed);
                    if (requestParsed > number) {
                        out.writeUTF("Your number is bigger");
                        out.flush();
                    } else if (requestParsed < number) {
                        out.writeUTF("Your number is lower");
                        out.flush();
                    } else {
                        out.writeUTF("You right!");
                        out.flush();
                        break;
                    }
                }
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
