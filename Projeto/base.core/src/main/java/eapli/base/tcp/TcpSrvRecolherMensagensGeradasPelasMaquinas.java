package eapli.base.tcp;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.*;

public class TcpSrvRecolherMensagensGeradasPelasMaquinas implements Runnable {

    public static final int PORT_NUMBER = 6834;
    static final String TRUSTED_STORE="server_J.jks";
    static final String KEYSTORE_PASS="forgotten";

//    static ServerSocket sock;
    static SSLServerSocket sock=null;


    @Override
    public void run() {
        try {
            try (final DatagramSocket socket = new DatagramSocket()) {
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                String ip = socket.getLocalAddress().getHostAddress();
                System.out.println("thread: "+ip);
            }

            // Trust these certificates provided by authorized clients
            System.setProperty("javax.net.ssl.trustStore", TRUSTED_STORE);
            System.setProperty("javax.net.ssl.trustStorePassword",KEYSTORE_PASS);

            // Use this certificate and private key as server certificate
            System.setProperty("javax.net.ssl.keyStore",TRUSTED_STORE);
            System.setProperty("javax.net.ssl.keyStorePassword",KEYSTORE_PASS);

            SSLServerSocketFactory sslF = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

            Socket cliSock;

            try {
//                sock = new ServerSocket(PORT_NUMBER);
                sock = (SSLServerSocket) sslF.createServerSocket(PORT_NUMBER);
                sock.setNeedClientAuth(true);
            } catch (IOException ex) {
                System.out.println("Failed to open server socket " + ex.getMessage());
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
