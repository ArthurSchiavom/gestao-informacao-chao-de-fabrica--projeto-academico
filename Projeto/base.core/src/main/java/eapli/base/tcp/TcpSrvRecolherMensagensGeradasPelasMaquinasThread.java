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
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class TcpSrvRecolherMensagensGeradasPelasMaquinasThread implements Runnable {

    private Socket s;
    private DataOutputStream sOut;
    private DataInputStream sIn;
    private MaquinaRepository repository = PersistenceContext.repositories().maquinas();

    public TcpSrvRecolherMensagensGeradasPelasMaquinasThread(Socket cliSock) {
        s = cliSock;
    }


    @Override
    public void run() {

        char maquinaID, tamanhoData;
        int maquinaIDPlaceHolder, tamanhoDataPlaceHolder;
        Byte version, code;

        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        byte[] rawData;
        String dataString = null;
        InetAddress clientIP = s.getInetAddress();
        System.out.println("New client connection from " + clientIP.getHostAddress() +
                ", port number " + s.getPort());
        try {
            sOut = new DataOutputStream(s.getOutputStream());
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


//            if (tamanhoData > 0) {
//                rawData = new byte[tamanhoData];
//                sIn.readFully(rawData);
//                dataString = new String(rawData, StandardCharsets.UTF_8); // UTF-8
//            int readData = 0;
//            String dataLine;
////
//            while (readData < tamanhoData) {
//                rawData = new byte[tamanhoData];
//                readData += sIn.read(rawData); // return the numbers of bytes read
//                dataLine = new String(rawData, StandardCharsets.UTF_8);
//                dataString += dataLine; // concat line to String that holds the whole text
//            }

//            }

            //Factory pattern
            ProcessarMensagensProtocolosStrategy mens =
                    ProcessarMensagemProtocoloFactory.getProcessarMensagemProtocolo(version, code, maquinaID,
                            tamanhoData, dataString);

            MensagemProtocoloComunicacao mensagemProtocoloComunicacao = mens.processarMensagem(s);

            escreveMensagem(mensagemProtocoloComunicacao, sOut);

            System.out.println(mensagemProtocoloComunicacao.idProtocolo);
            System.out.println(mensagemProtocoloComunicacao.version);
            System.out.println(mensagemProtocoloComunicacao.mensagem);

            System.out.println("Client " + clientIP.getHostAddress() + ",port number: " + s.getPort() +
                    " disconnected");

//            sOut.flush();
            sOut.close();
            sIn.close();
            s.close();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    /**
     * Escreve a MensagemProtocoloComunicaçao através do socket
     *
     *
     * @throws IOException
     */
    public boolean escreveMensagem(MensagemProtocoloComunicacao mensagemProtocoloComunicacao, DataOutputStream sOut) throws IOException {

        sOut.write(mensagemProtocoloComunicacao.version);
        sOut.write(mensagemProtocoloComunicacao.code);
        sOut.write(mensagemProtocoloComunicacao.idProtocolo);
        sOut.write(mensagemProtocoloComunicacao.tamanhoRawData);
        if (mensagemProtocoloComunicacao.tamanhoRawData > 0 && mensagemProtocoloComunicacao.mensagem != null) {
            sOut.write(mensagemProtocoloComunicacao.mensagem.getBytes());
            System.out.println("mensagem escrita, version: " +mensagemProtocoloComunicacao.version +"\ncode: " + mensagemProtocoloComunicacao.code+"\nidProt: "+mensagemProtocoloComunicacao.idProtocolo+"\ntamanho: "+ mensagemProtocoloComunicacao.tamanhoRawData);
        }

        System.out.println("mensagem escrita, version: " +(mensagemProtocoloComunicacao.version  & 0xFF) +"\ncode: " + (mensagemProtocoloComunicacao.code  & 0xFF)+"\nidProt: "+ (short) (mensagemProtocoloComunicacao.idProtocolo & 0xFFFF ) +"\ntamanho: "+ (short) (mensagemProtocoloComunicacao.tamanhoRawData & 0xFFFF));

        return true;
    }
}
