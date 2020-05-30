package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.framework.domain.model.AggregateRoot;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue(value=TipoDeMensagem.Values.CONSUMO)
public class MensagemConsumo extends Mensagem implements AggregateRoot<Long> {
    private final CodigoDeposito codigo;
    public final CodigoInternoMaquina maquinaID;
    private final CodigoInternoMaquina codigoInternoMaquina;
    public final Date dataHora;
    private int quantidadeProduzir;

    protected MensagemConsumo(){
        this.codigo=null;
        this.maquinaID=null;
        this.codigoInternoMaquina=null;
        this.dataHora=null;

    }

    public MensagemConsumo(CodigoDeposito codigo, CodigoInternoMaquina maquinaID, CodigoInternoMaquina codigoInternoMaquina, Date dataHora, int quantidadeProduzir) {
        super(TipoDeMensagem.CONSUMO,new TimestampEmissao(dataHora));
        if (maquinaID==null && codigoInternoMaquina ==null && dataHora==null && quantidadeProduzir<=0)
            throw new IllegalArgumentException("Parametros dados incorrectos!");
        this.codigo = codigo;
        this.maquinaID = maquinaID;
        this.codigoInternoMaquina = codigoInternoMaquina;
        this.dataHora = dataHora;
        this.quantidadeProduzir = quantidadeProduzir;
    }


    //C0 -> Máquina;TipoMsg;DataHora;Produto;Quantidade;Depósito
//




}
