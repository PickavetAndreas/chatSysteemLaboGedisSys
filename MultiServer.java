import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MultiServer {
    public static void main(String[] args) throws IOException {
        int portNumber = 4477;
        boolean listening = true;
        List<MultiServerThread> clients = new ArrayList<>();

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                MultiServerThread t = new MultiServerThread(serverSocket.accept());
                PrintWriter o = t.createOutput();
                try {
                    for (MultiServerThread c : clients) {
                        c.addOutput(o);
                        t.addOutput(c.getOutput());
                    }
                }catch (Exception e) {

                }
                clients.add(t);
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
