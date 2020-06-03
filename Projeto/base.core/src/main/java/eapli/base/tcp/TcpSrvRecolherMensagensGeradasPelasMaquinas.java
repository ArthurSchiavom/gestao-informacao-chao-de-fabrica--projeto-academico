package eapli.base.tcp;

import java.io.IOException;
import java.net.*;

public class TcpSrvRecolherMensagensGeradasPelasMaquinas implements Runnable {

    public static final int PORT_NUMBER = 6834;

    static ServerSocket sock;

    @Override
    public void run() {
        try {
            try (final DatagramSocket socket = new DatagramSocket()) {
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                String ip = socket.getLocalAddress().getHostAddress();
                System.out.println("thread: "+ip);
            }

            Socket cliSock;

            try {
                sock = new ServerSocket(PORT_NUMBER);
            } catch (IOException ex) {
                System.out.println("Failed to open server socket");
                System.exit(1);
            }

            while (true) {
                cliSock = sock.accept();
                new Thread(new TcpSrvRecolherMensagensGeradasPelasMaquinasThread(cliSock)).start();
            }
        } catch (IOException e) {
        }
    }
}
