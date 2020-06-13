package eapli.base.indicarUsoDeMaquina.domain;

import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UsoDeMaquinaID implements ValueObject, Serializable, Comparable<UsoDeMaquinaID> {
    public final InicioDeExecucao inicioDeExecucao;
    public final FimDeExecucao fimDeExecucao;
    public final CodigoInternoMaquina codigoInternoMaquina;

    protected UsoDeMaquinaID(){
        this.inicioDeExecucao=null;
        this.fimDeExecucao=null;
        this.codigoInternoMaquina=null;
    }

    public UsoDeMaquinaID(InicioDeExecucao inicioDeExecucao, FimDeExecucao fimDeExecucao,CodigoInternoMaquina codigoInternoMaquina) {
        this.inicioDeExecucao=inicioDeExecucao;
        this.fimDeExecucao=fimDeExecucao;
        this.codigoInternoMaquina=codigoInternoMaquina;
    }

    @Override
    public int compareTo(UsoDeMaquinaID o) {
        return 0;
    }
}
