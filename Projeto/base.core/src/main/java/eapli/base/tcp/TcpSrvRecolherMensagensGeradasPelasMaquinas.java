package eapli.base.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TcpSrvRecolherMensagensGeradasPelasMaquinas {

    public static final int PORT_NUMBER = 6834;

    static ServerSocket sock;

    public static void server() throws Exception {


        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            String ip = socket.getLocalAddress().getHostAddress();
            System.out.println(ip);
        }
        Socket cliSock;

        try {
            sock = new ServerSocket(PORT_NUMBER);
        } catch(IOException ex) {
            System.out.println("Failed to open server socket");
            System.exit(1);
        }

        while(true) {
            cliSock=sock.accept();
            System.out.println("entrou");
            new Thread(new TcpSrvRecolherMensagensGeradasPelasMaquinasThread(cliSock)).start();
        }
    }
}
