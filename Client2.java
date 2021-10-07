import java.io.*;
import java.net.*;

public class Client2 {

    public static void main(String[] args) throws IOException {

        String hostName = "localhost";
        int portNumber = 4477;
/*
    public static void runClient() {
*/
        try (


                Socket ServerSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(ServerSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(ServerSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;
            while ((fromServer = in.readLine()) != "end") {
                System.out.println("Server: " + fromServer);
               /* if (fromServer.equals("Bye."))
                    break;
*/
                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }

}

