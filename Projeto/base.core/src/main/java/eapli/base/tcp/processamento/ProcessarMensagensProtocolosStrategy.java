package eapli.base.tcp.processamento;

import eapli.base.tcp.domain.MensagemProtocoloCodes;
import eapli.base.tcp.domain.MensagemProtocoloComunicacao;

import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;

public abstract class ProcessarMensagensProtocolosStrategy {
    public final String NOME_FICHEIRO = "logMensagem.txt";
    private static byte version = 0; // current version


    public abstract MensagemProtocoloComunicacao processarMensagem(Socket s);


    /**
     * Constroi uma MensagemProtocoloComunicao do tipo ACK, com texto vazio
     */
    public MensagemProtocoloComunicacao mensagemACK(byte version, char idProtocolo) {

        return new MensagemProtocoloComunicacao(version,
                (byte) MensagemProtocoloCodes.ACK.getCode(),
                idProtocolo, (char) 0, null);
    }

    /**
     * Constroi uma MensagemProtocoloComunicao do tipo NACK, com texto vazio
     */
    public MensagemProtocoloComunicacao mensagemNACK(byte version, char idProtocolo) {

        return new MensagemProtocoloComunicacao(version,
                (byte) MensagemProtocoloCodes.NACK.getCode(),
                idProtocolo, (char) 0, null);
    }

    public void criarLog(byte version, byte code, char idProtocolo, char tamanhoRawData, String mensagem) {
        FileWriter fw;
        try {
            fw = new FileWriter(NOME_FICHEIRO, true);//if file exists append to file. Works fine.

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            fw.write("version: " + (int) version + " code: " + (int) code + " protocolo:" + (int) idProtocolo + " tamanho: " +(int)tamanhoRawData);
            if (tamanhoRawData > 0 && mensagem != null) {
                fw.write(" enviado: " + mensagem);
            }
            fw.close();
        } catch (IOException e) {
        }
    }

    public static byte getVersion() {
        return version;
    }
}
