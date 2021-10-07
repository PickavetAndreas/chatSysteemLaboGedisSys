import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MultiServerThread extends Thread {
    private Socket socket = null;
    private List<PrintWriter> outputs = new ArrayList<>();
    private PrintWriter out;

    public MultiServerThread(Socket socket) {
        super("MultiServerThread");
        this.socket = socket;
        this.start();
    }

    public PrintWriter getOutput() {
        return out;
    }

    public void addOutput(PrintWriter p) {
        this.outputs.add(p);
    }

    public void removeOutput(PrintWriter p) {
        this.outputs.remove(p);
    }

    public PrintWriter createOutput() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            return out;
        }
        catch (Exception e) {

        }
        return null;
    }

    public void run() {
        try (

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            ServerProtocol protocol = new ServerProtocol();
            outputLine = protocol.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                /*outputLine = protocol.processInput(inputLine);
                out.println(outputLine);*/
                for(PrintWriter outt: outputs) {
                    outt.println(inputLine);
                }
                if (outputLine.equals("Bye"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
