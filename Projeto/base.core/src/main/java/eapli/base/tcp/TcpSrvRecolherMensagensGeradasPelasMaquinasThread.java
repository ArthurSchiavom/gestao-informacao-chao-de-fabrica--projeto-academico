package eapli.base.tcp;

import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.tcp.domain.MensagemProtocoloComunicacao;
import eapli.base.tcp.processamento.ProcessarMensagemProtocoloFactory;
import eapli.base.tcp.processamento.ProcessarMensagensProtocolosStrategy;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class TcpSrvRecolherMensagensGeradasPelasMaquinasThread implements Runnable {

    private Socket sock;
    private BufferedOutputStream sOut;
    private DataInputStream sIn;
    private MaquinaRepository repository = PersistenceContext.repositories().maquinas();

    public TcpSrvRecolherMensagensGeradasPelasMaquinasThread(Socket cliSock) {
        sock = cliSock;
        try {
            sock.setTcpNoDelay(true);
        } catch (SocketException e) {
            System.out.println("\n\n\n\nwtf\n\n\n\n");
        }
    }


    @Override
    public void run() {
        ProcessarMensagensProtocolosStrategy mens = lerMensagemTcp(sock);

        if (mens == null) {
//            break;
        }

        MensagemProtocoloComunicacao mensagemProtocoloComunicacao = mens.processarMensagem(sock);

        try {
            escreveMensagemTcp(mensagemProtocoloComunicacao, sock);


            System.out.println("Client " + sock.getInetAddress().getHostAddress() + ",port number: " + sock.getPort() +
                    " disconnected");
            sOut.flush();

            Thread.sleep(10000);

            System.out.println("Waiting done");

            sOut.close();
            sIn.close();
            sock.close();
        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Escreve a MensagemProtocoloComunicaçao através do socket
     *
     * @throws IOException
     */
    public boolean escreveMensagemTcp(MensagemProtocoloComunicacao mensagemProtocoloComunicacao,
                                      Socket s) throws IOException {

        sOut = new BufferedOutputStream(s.getOutputStream());

        sOut.write(mensagemProtocoloComunicacao.version);
        sOut.write(mensagemProtocoloComunicacao.code);
        sOut.write(mensagemProtocoloComunicacao.idProtocolo);
        sOut.write(mensagemProtocoloComunicacao.tamanhoRawData);
        if (mensagemProtocoloComunicacao.tamanhoRawData > 0 && mensagemProtocoloComunicacao.mensagem != null) {
            sOut.write(mensagemProtocoloComunicacao.mensagem.getBytes());
            System.out.println("mensagem escrita, version: " + mensagemProtocoloComunicacao.version + "\ncode: " + mensagemProtocoloComunicacao.code + "\nidProt: " + mensagemProtocoloComunicacao.idProtocolo + "\ntamanho: " + mensagemProtocoloComunicacao.tamanhoRawData);
        }
        sOut.flush();

        System.out.println("mensagem escrita, version: " + (mensagemProtocoloComunicacao.version & 0xFF) + "\ncode: " + (mensagemProtocoloComunicacao.code & 0xFF) + "\nidProt: " + (short) (mensagemProtocoloComunicacao.idProtocolo & 0xFFFF) + "\ntamanho: " + (short) (mensagemProtocoloComunicacao.tamanhoRawData & 0xFFFF));

        return true;
    }

    /**
     * Le mensagem tcp do client
     */
    public ProcessarMensagensProtocolosStrategy lerMensagemTcp(Socket s) {
        char maquinaID, tamanhoData;
        int maquinaIDPlaceHolder, tamanhoDataPlaceHolder;
        Byte version, code;
        byte[] rawData;
        String dataString = "";

        InetAddress clientIP = s.getInetAddress();
        System.out.println("New client connection from " + clientIP.getHostAddress() +
                ", port number " + s.getPort());
        try {
//            sOut = new BufferedOutputStream(s.getOutputStream());
            sIn = new DataInputStream(s.getInputStream());

            System.out.println("get streams");
            version = sIn.readByte(); // read version
            System.out.println("recebido version: " + version.intValue());
            code = sIn.readByte(); // read code
            System.out.println("recebido code: " + code.intValue());

            maquinaIDPlaceHolder = sIn.readShort() & 0xFFFF; // read unsigned short
            maquinaID = (char) maquinaIDPlaceHolder;

            System.out.println("recebido maquinaID: " + (int) maquinaID);

            tamanhoDataPlaceHolder = sIn.readShort() & 0xFFFF; // read unsigned short
            tamanhoData = (char) tamanhoDataPlaceHolder;

            System.out.println("recebido tamanho data: " + (int) tamanhoData);

            int readData = 0;
            String dataLine;

            while (readData < tamanhoData) {
                rawData = new byte[tamanhoData];
                readData += sIn.read(rawData); // return the numbers of bytes read
                dataLine = new String(rawData, StandardCharsets.UTF_8);
                dataString += dataLine; // concat line to String that holds the whole text
            }

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
