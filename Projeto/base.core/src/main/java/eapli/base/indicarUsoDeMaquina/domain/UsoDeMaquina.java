package eapli.base.indicarUsoDeMaquina.domain;


import eapli.base.gestaoproducao.movimentos.domain.MovimentoStock;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.utilities.Reflection;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class UsoDeMaquina implements AggregateRoot<UsoDeMaquinaID> {

    @EmbeddedId
    private UsoDeMaquinaID usoDeMaquinaID;

    @ManyToOne
    private MovimentoStock movimentoStock;


    private PausaDeExecucao pausaDeExecucao;
    private RetomaDeExecucao retomaDeExecucao;

    protected UsoDeMaquina() {
        usoDeMaquinaID =null;
        pausaDeExecucao=null;
        retomaDeExecucao=null;
    }

    public UsoDeMaquina(InicioDeExecucao inicioDeExecucao, FimDeExecucao fimDeExecucao, PausaDeExecucao pausaDeExecucao, RetomaDeExecucao retomaDeExecucao, MovimentoStock movimentoStock, CodigoInternoMaquina codigoInternoMaquina) {
        this.pausaDeExecucao=pausaDeExecucao;
        this.usoDeMaquinaID =new UsoDeMaquinaID(inicioDeExecucao,fimDeExecucao,codigoInternoMaquina);
        this.retomaDeExecucao=retomaDeExecucao;
        this.movimentoStock=movimentoStock;
    }

    public static String identityAttributeName() {
        return Reflection.retrieveAttributeName(UsoDeMaquina.class, UsoDeMaquinaID.class);
    }

    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public UsoDeMaquinaID identity() {
        return this.usoDeMaquinaID;
    }
}
