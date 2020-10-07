import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static ExecutorService executeIt = Executors.newFixedThreadPool(10);
    static int number = 100;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            System.out.println("Server is ready");
            int count = 1;

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                executeIt.execute(new MonoThreadClientHandler(clientSocket, count));
                System.out.println("Клиент #" + count + " подключился.");
                count++;
            }
            executeIt.shutdown();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
