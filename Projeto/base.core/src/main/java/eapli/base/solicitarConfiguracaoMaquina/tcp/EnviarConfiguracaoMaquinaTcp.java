package eapli.base.solicitarConfiguracaoMaquina.tcp;

import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.tcp.domain.MensagemProtocoloCodes;
import eapli.base.tcp.domain.MensagemProtocoloComunicacao;
import eapli.base.tcp.processamento.ProcessarMensagensProtocolosStrategy;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class EnviarConfiguracaoMaquinaTcp {

    public static final int PORT = 6835;
    private static InetAddress serverIP;
    private static SSLSocket sock;


    public static boolean enviarConfiguracaoMaquinaTcp(String data, Maquina maquina) {

        if (maquina.getIp() == null) {
            return false;
        }
        serverIP = maquina.getIp();
        SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();



        try {
            sock = (SSLSocket) sf.createSocket(serverIP, PORT);
            sock.setSoTimeout(10 * 1000); // 10 seconds timeout
            sock.setEnabledProtocols(new String[] { "TLSv1.1","TLSv1.2"});

        } catch (IOException ex) {
            throw new IllegalArgumentException("Conexão TCP não estabelecida");
        }

        try {
            sock.startHandshake();
        } catch (IOException e) {
            throw new IllegalArgumentException("Handshake TLS falhou");
        }

        DataOutputStream sOut;
        DataInputStream sIn;
        try {
            sOut = new DataOutputStream(sock.getOutputStream());
            sIn = new DataInputStream(sock.getInputStream());
            MensagemProtocoloComunicacao mens =
                    new MensagemProtocoloComunicacao(ProcessarMensagensProtocolosStrategy.getVersion(), (byte) MensagemProtocoloCodes.CONFIG.getCode(), (char) maquina.identificadorProtocoloComunicacao.identificadorProtocoloComunicao, (char)data.getBytes().length, data);

            enviarMensagemTcp(mens, sOut);

            MensagemProtocoloComunicacao m = lerMensagemTcp(sIn);

            if((m.code & 0xFF) == MensagemProtocoloCodes.ACK.getCode()){ // 0xFF because byte is unsigned in java
                return true;
            }

            return false;
        } catch (IOException|NullPointerException e) {
            throw new IllegalArgumentException("Conexão ao socket foi perdida.");
        }
    }


    /**
     * writes config message to socket
     */
    private static void enviarMensagemTcp(MensagemProtocoloComunicacao data, DataOutputStream sOut) throws IOException {
        sOut.write(data.version);
        sOut.write(data.code);
        sOut.writeChar(data.idProtocolo);
        sOut.writeChar(data.tamanhoRawData);
        sOut.write(data.mensagem.getBytes());

        sOut.flush();
    }


    /**
     * le a mensagem tcp
     * @throws IOException em caso de erro com o socket
     */
    private static MensagemProtocoloComunicacao lerMensagemTcp(DataInputStream sIn) throws IOException {
        char maquinaID, tamanhoData;
        int maquinaIDPlaceHolder, tamanhoDataPlaceHolder;
        Byte version, code;
        byte[] rawData;
        String dataString = "";

//        InetAddress clientIP = s.getInetAddress()
////        System.out.println("New client connection from " ;+ clientIP.getHostAddress() +
//                ", port number " + s.getPort());
        version = sIn.readByte(); // read version
//            System.out.println("recebido version: " + version.intValue());
        code = sIn.readByte(); // read code
//            System.out.println("recebido code: " + code.intValue());

        maquinaIDPlaceHolder = sIn.readShort() & 0xFFFF; // read unsigned short
        maquinaID = (char) maquinaIDPlaceHolder;

//            System.out.println("recebido maquinaID: " + (int) maquinaID);

        tamanhoDataPlaceHolder = sIn.readShort() & 0xFFFF; // read unsigned short
        tamanhoData = (char) tamanhoDataPlaceHolder;

//            System.out.println("recebido tamanho data: " + (int) tamanhoData);

        int readData = 0;
        String dataLine;

        while (readData < tamanhoData) {
            rawData = new byte[tamanhoData];
            readData += sIn.read(rawData); // return the numbers of bytes read
            dataLine = new String(rawData, StandardCharsets.UTF_8);
            dataString += dataLine; // concat line to String that holds the whole text
        }


        return new MensagemProtocoloComunicacao(version, code, maquinaID, tamanhoData, dataString);
    }
}
