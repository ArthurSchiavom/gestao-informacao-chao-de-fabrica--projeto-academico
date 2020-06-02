package eapli.base.tcp;

import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.tcp.domain.MensagemProtocoloComunicacao;
import eapli.base.tcp.processamento.ProcessarMensagemProtocoloFactory;
import eapli.base.tcp.processamento.ProcessarMensagensProtocolosStrategy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class TcpSrvRecolherMensagensGeradasPelasMaquinasThread implements Runnable {


    private MaquinaRepository repository = PersistenceContext.repositories().maquinas();

    private Socket sock;

    public TcpSrvRecolherMensagensGeradasPelasMaquinasThread(Socket cliSock) {
        sock = cliSock;
    }


    @Override
    public void run() {
        DataOutputStream sOut;
        DataInputStream sIn;
        try {
            sOut = new DataOutputStream(sock.getOutputStream());
            sIn = new DataInputStream(sock.getInputStream());

        } catch (IOException e) {
            return;
        }

        ProcessarMensagensProtocolosStrategy mens = lerMensagemTcp(sock, sIn);


        if (mens == null) {
//            break;
        }

        MensagemProtocoloComunicacao mensagemProtocoloComunicacao = mens.processarMensagem(sock);

        try {
            escreveMensagemTcp(mensagemProtocoloComunicacao, sOut);


            System.out.println("Client " + sock.getInetAddress().getHostAddress() + ",port number: " + sock.getPort() +
                    " disconnected");

            sOut.close();
            sIn.close();
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Escreve a MensagemProtocoloComunicaçao através do socket
     *
     * @throws IOException
     */
    public boolean escreveMensagemTcp(MensagemProtocoloComunicacao mensagemProtocoloComunicacao,
                                      DataOutputStream sOut) throws IOException {

        sOut.write(mensagemProtocoloComunicacao.version);
        sOut.write(mensagemProtocoloComunicacao.code);
        sOut.writeChar(mensagemProtocoloComunicacao.idProtocolo);
        sOut.writeChar(mensagemProtocoloComunicacao.tamanhoRawData);
        if (mensagemProtocoloComunicacao.tamanhoRawData > 0 && mensagemProtocoloComunicacao.mensagem != null) {
            sOut.write(mensagemProtocoloComunicacao.mensagem.getBytes());
        }
        sOut.flush();


        return true;
    }

    /**
     * Le mensagem tcp do client
     */
    public ProcessarMensagensProtocolosStrategy lerMensagemTcp(Socket s, DataInputStream sIn) {
        char maquinaID, tamanhoData;
        int maquinaIDPlaceHolder, tamanhoDataPlaceHolder;
        Byte version, code;
        byte[] rawData;
        String dataString = "";

        InetAddress clientIP = s.getInetAddress();
        System.out.println("New client connection from " + clientIP.getHostAddress() +
                ", port number " + s.getPort());
        try {
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
            String dataLine = null;

            while (readData < tamanhoData) {
                rawData = new byte[tamanhoData];
                readData += sIn.read(rawData); // return the numbers of bytes read
                dataLine = new String(rawData, StandardCharsets.UTF_8);
                dataString += dataLine; // concat line to String that holds the whole text
            }

            System.out.println("data length: " + tamanhoDataPlaceHolder);
            if (dataLine != null)
                System.out.println("string recebida: " + dataString);

            //Factory pattern
            ProcessarMensagensProtocolosStrategy mens =
                    ProcessarMensagemProtocoloFactory.getProcessarMensagemProtocolo(version, code, maquinaID,
                            tamanhoData, dataString);
            return mens;
        } catch (IOException e) {
            return null;
        }
    }
}
