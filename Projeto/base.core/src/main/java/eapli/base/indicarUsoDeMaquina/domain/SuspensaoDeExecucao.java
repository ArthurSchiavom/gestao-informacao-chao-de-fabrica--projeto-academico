package eapli.base.indicarUsoDeMaquina.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
public class SuspensaoDeExecucao implements ValueObject {
    private static final long serialVersionUID = 1L;

    private Date pausaDeExecucao;
    private Date retomaDeExecucao;

    protected SuspensaoDeExecucao(){
        pausaDeExecucao=null;
        retomaDeExecucao=null;
    }

    public SuspensaoDeExecucao(Date retomaDeExecucao,Date pausaDeExecucao) {
        this.retomaDeExecucao=retomaDeExecucao;
        this.pausaDeExecucao=pausaDeExecucao;
    }
}
