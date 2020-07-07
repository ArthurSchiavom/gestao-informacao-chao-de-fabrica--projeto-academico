package eapli.base.tcp;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class TcpSrvRecolherMensagensGeradasPelasMaquinas implements Runnable {

    private static final int PORT_NUMBER = 6834;
    private static final String TRUSTED_STORE="ssl/server_J.jks";
    private static final String KEYSTORE_PASS="forgotten";
    private static SSLServerSocket sock=null;


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
            System.out.println("Falhou conexão ao socket.");
        }
    }
}
