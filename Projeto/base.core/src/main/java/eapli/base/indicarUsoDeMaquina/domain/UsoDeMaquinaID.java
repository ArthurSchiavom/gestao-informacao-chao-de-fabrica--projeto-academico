package eapli.base.indicarUsoDeMaquina.domain;

import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UsoDeMaquinaID implements ValueObject, Serializable, Comparable<UsoDeMaquinaID> {
    private InicioDeExecucao inicioDeExecucao;
    private FimDeExecucao fimDeExecucao;
    private CodigoInternoMaquina codigoInternoMaquina;

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
