import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MonoThreadClientHandler implements Runnable {
    private static Socket clientDialog;
    private int clientNumber;

    MonoThreadClientHandler(Socket client, int clientNumber) {
        MonoThreadClientHandler.clientDialog = client;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        try (DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());
             DataInputStream in = new DataInputStream(clientDialog.getInputStream())) {
            int attempts = 0;

            while (!clientDialog.isClosed()) {
                if (in.available() > 0) {
                    String request = in.readUTF();
                    int number = Server.number;
                    int requestParsed = Integer.parseInt(request);
                    attempts++;
                    if (requestParsed > number) {
                        out.writeUTF("Your number is bigger");
                        out.flush();
                    } else if (requestParsed < number) {
                        out.writeUTF("Your number is lower");
                        out.flush();
                    } else {
                        out.writeUTF("You right!");
                        out.flush();
                        System.out.println("Попытки клиента #" + this.clientNumber + " угадать число:" + attempts);
                        System.out.println("Клиент #" + this.clientNumber + " отключился");
                    }
                }
            }
            clientDialog.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}