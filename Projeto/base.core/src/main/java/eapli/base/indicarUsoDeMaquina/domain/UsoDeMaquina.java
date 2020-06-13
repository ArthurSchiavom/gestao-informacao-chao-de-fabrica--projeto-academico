package eapli.base.indicarUsoDeMaquina.domain;


import eapli.base.gestaoproducao.movimentos.domain.MovimentoStock;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.utilities.Reflection;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UsoDeMaquina implements AggregateRoot<UsoDeMaquinaID> {

    @EmbeddedId
    public final UsoDeMaquinaID usoDeMaquinaID;

    @OneToMany(cascade = CascadeType.ALL)
    public final List<MovimentoStock> movimentoStockList;

    @ElementCollection
    public final List<PausaDeExecucao> pausaDeExecucaoList;

    @ElementCollection
    public final List<RetomaExecucao> retomaExecucaoList;


    protected UsoDeMaquina() {
        usoDeMaquinaID =null;
        pausaDeExecucaoList=null;
        movimentoStockList=null;
        retomaExecucaoList=null;
    }

    public UsoDeMaquina(InicioDeExecucao inicioDeExecucao, FimDeExecucao fimDeExecucao, CodigoInternoMaquina codigoInternoMaquina) {
        this.usoDeMaquinaID =new UsoDeMaquinaID(inicioDeExecucao,fimDeExecucao,codigoInternoMaquina);
        this.retomaExecucaoList=new ArrayList<>();
        this.movimentoStockList=new ArrayList<>();
        this.pausaDeExecucaoList=new ArrayList<>();
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
