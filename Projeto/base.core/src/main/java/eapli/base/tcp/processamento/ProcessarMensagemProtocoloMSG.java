package eapli.base.tcp.processamento;

import eapli.base.gestaoproducao.gestaomaquina.domain.IdentificadorProtocoloComunicacao;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.servicoComunicacaoComMaquinas.MessageFactory;
import eapli.base.tcp.domain.MensagemProtocoloComunicacao;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Optional;

/**
 * As máquinas industriais enviam pedidos MSG baseadas em TCP ao sistema central e recebem uma
 * resposta ACK caso o ID e endereço de rede da máquina coincidam com a informação disponível no
 * sistema central. Caso contrário, o pedido é ignorado e a resposta NACK é retornada
 */
public class ProcessarMensagemProtocoloMSG extends ProcessarMensagensProtocolosStrategy {

    public final MensagemProtocoloComunicacao mensagemProtocoloComunicacao;

    private MaquinaRepository repo = PersistenceContext.repositories().maquinas();

    public ProcessarMensagemProtocoloMSG(Byte version, Byte code, char idMaquinaProtocolo,
                                         char tamanhoData, String rawData) {
        mensagemProtocoloComunicacao = new MensagemProtocoloComunicacao(version, code, idMaquinaProtocolo,
                tamanhoData, rawData);
    }

    @Override
    public MensagemProtocoloComunicacao processarMensagem(Socket s) {
        criarLog(mensagemProtocoloComunicacao.version, mensagemProtocoloComunicacao.code,
                mensagemProtocoloComunicacao.idProtocolo, mensagemProtocoloComunicacao.tamanhoRawData,
                mensagemProtocoloComunicacao.mensagem);
        Maquina maq;
        if ((maq = maquinaIdentificadorExiste(mensagemProtocoloComunicacao.idProtocolo)) == null || mensagemProtocoloComunicacao.version != ProcessarMensagensProtocolosStrategy.getVersion()) {
            //falha, retorna NACK
            return mensagemNACK(mensagemProtocoloComunicacao.version, mensagemProtocoloComunicacao.idProtocolo);
        }

        InetAddress sockaddr = s.getInetAddress();

        // verifica se o IP recebido é igual ao IP na máquina
        if (!maq.getIp().equals(sockaddr)) {
            // o IP recebido é diferente do IP recebido
            return mensagemNACK(mensagemProtocoloComunicacao.version, mensagemProtocoloComunicacao.idProtocolo);

        }

        //Processar mensagens


        Mensagem mens;
        MessageFactory mf = new MessageFactory();
        try {
            mens = mf.getMessageType(mensagemProtocoloComunicacao.mensagem.split(";"));
        } catch (Exception e) {
            return mensagemNACK(mensagemProtocoloComunicacao.version, mensagemProtocoloComunicacao.idProtocolo);
        }

        PersistenceContext.repositories().mensagem().save(mens);

        System.out.println("MSG sucesso");
        //sucesso retorna ACK
        return mensagemACK(mensagemProtocoloComunicacao.version, mensagemProtocoloComunicacao.idProtocolo);
    }

    /**
     * Verifica se uma maquina com o Identificador de protocolo existe
     */
    private Maquina maquinaIdentificadorExiste(char maquinaProtocoloID) {
        IdentificadorProtocoloComunicacao codMaquina;

        try {
            codMaquina = new IdentificadorProtocoloComunicacao(maquinaProtocoloID);
        } catch (IllegalArgumentException ex) {
            return null;
        }

        Optional<Maquina> maqOpt = repo.findByidentificadorProtocoloComunicacao(codMaquina);
        if (maqOpt.isPresent()) {
            return maqOpt.get();
        }

        return null;
    }

//    private void criarLog() {
//        FileWriter fw;
//        try {
//            fw = new FileWriter(NOME_FICHEIRO, true);//if file exists append to file. Works fine.
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        try {
//            fw.write("version: " + (int) mensagemProtocoloComunicacao.version + " code: " + (int)
//            mensagemProtocoloComunicacao.code + " protocolo:" + (int) mensagemProtocoloComunicacao.idProtocolo + "
//            tamanho: " + (int) mensagemProtocoloComunicacao.tamanhoRawData);
//            if (mensagemProtocoloComunicacao.tamanhoRawData > 0) {
//                fw.write("enviado: " + mensagemProtocoloComunicacao.mensagem);
//            }
//            fw.write("\n");
//            System.out.println("Log escrito");
//            fw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    /**
//     * Constroi uma MensagemProtocoloComunicao do tipo ACK, com texto vazio
//     */
//    private MensagemProtocoloComunicacao mensagemACK() {
//
//        return new MensagemProtocoloComunicacao(mensagemProtocoloComunicacao.version,
//                (byte) MensagemProtocoloCodes.ACK.getCode(),
//                mensagemProtocoloComunicacao.idProtocolo, (char) 0, null);
//    }
//
//    /**
//     * Constroi uma MensagemProtocoloComunicao do tipo NACK, com texto vazio
//     */
//    private MensagemProtocoloComunicacao mensagemNACK() {
//
//        return new MensagemProtocoloComunicacao(mensagemProtocoloComunicacao.version,
//                (byte) MensagemProtocoloCodes.NACK.getCode(),
//                mensagemProtocoloComunicacao.idProtocolo, (char) 0, null);
//    }
}
