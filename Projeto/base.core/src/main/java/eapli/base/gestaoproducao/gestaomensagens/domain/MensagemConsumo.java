package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.framework.domain.model.AggregateRoot;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue(value=TipoDeMensagem.Values.CONSUMO)
public class MensagemConsumo extends Mensagem implements AggregateRoot<MensagemID> {
    public final CodigoDeposito codigoDeposito;
    public final String idMateriaPrima;
    private int quantidadeProduzir;

    //C0 -> Máquina;TipoMsg;DataHora;Produto;Quantidade;Depósito

    protected MensagemConsumo(){
        this.codigoDeposito =null;
        this.idMateriaPrima=null;
    }

    public MensagemConsumo(CodigoDeposito codigo, CodigoInternoMaquina codigoInternoMaquina, Date dataHora, int quantidadeProduzir,String idMateriaPrima) {
        super(TipoDeMensagem.CONSUMO,new TimestampEmissao(dataHora),codigoInternoMaquina);
        if (idMateriaPrima.isEmpty() || dataHora == null)
            throw new IllegalArgumentException("Parametros dados incorrectos!");
        this.codigoDeposito = codigo;
        this.quantidadeProduzir = quantidadeProduzir;
        this.idMateriaPrima=idMateriaPrima;
    }

    public int getQuantidadeProduzir() {
        return quantidadeProduzir;
    }




}
