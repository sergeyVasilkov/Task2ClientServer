import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] args) {
        try (Socket clientSocket = new Socket("127.0.0.1", 8000);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream oos = new DataOutputStream(clientSocket.getOutputStream());
             DataInputStream ois = new DataInputStream(clientSocket.getInputStream())) {
            while (!clientSocket.isOutputShutdown()) {
                if (br.ready()) {
                    String message = br.readLine();
                    oos.writeUTF(message);
                    oos.flush();

                    String response = ois.readUTF();
                    System.out.println(response);

                    if (response.equalsIgnoreCase("You Right!")) {
                        break;
                    }
                }
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
