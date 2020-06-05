package eapli.base.tcp.processamento;

public class ProcessarMensagemProtocoloFactory {

    public static ProcessarMensagensProtocolosStrategy getProcessarMensagemProtocolo(Byte version, Byte code,
                                                                                     char idMaquinaProtocolo,
                                                                                     char tamanhoData, String rawData) {

        switch(code){
            case(0):
                return new ProcessarMensagemProtocoloHello(version,code,idMaquinaProtocolo,tamanhoData,rawData);

            case(1):
                return new ProcessarMensagemProtocoloMSG(version,code,idMaquinaProtocolo,tamanhoData,rawData);

                //3 reset

            // acrescentar os outros tipos de mensagens qnd existirem
            default:
                return null;
        }
    }
}
