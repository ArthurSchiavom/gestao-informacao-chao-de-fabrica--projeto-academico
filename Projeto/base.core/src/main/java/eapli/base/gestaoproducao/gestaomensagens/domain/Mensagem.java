package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

/**
 * http://www.thejavageek.com/2014/05/14/jpa-single-table-inheritance-example/
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public abstract class Mensagem implements AggregateRoot<MensagemID> {

    @Version
    private Long version;

    @EmbeddedId
    public final MensagemID mensagemID;

    @XmlElement
    @Enumerated(EnumType.STRING)
    private EstadoProcessamento estadoProcessamento;

    public final CodigoInternoMaquina codigoInternoMaquina;
    @ManyToOne
    private LinhaProducao linhaProducao;
    @ManyToOne
    private OrdemProducao ordemProducao;


    public Mensagem(TipoDeMensagem tipo, TimestampEmissao tempoEmissao, CodigoInternoMaquina codigoInternoMaquina) {
        if ((tipo == null || tempoEmissao == null||codigoInternoMaquina==null)) {
            throw new IllegalArgumentException("Mensagem não pode ter parametros null");
        }
        this.mensagemID = new MensagemID(tipo,tempoEmissao);

        this.estadoProcessamento=EstadoProcessamento.NAO_PROCESSADO;
        this.codigoInternoMaquina=codigoInternoMaquina;
    }
    public Mensagem(TipoDeMensagem tipo, TimestampEmissao tempoEmissao, CodigoInternoMaquina codigoInternoMaquina,OrdemProducao ordemProducao) {
        if ((tipo == null || tempoEmissao == null||codigoInternoMaquina==null)) {
            throw new IllegalArgumentException("Mensagem não pode ter parametros null");
        }
        this.mensagemID = new MensagemID(tipo,tempoEmissao);
        this.estadoProcessamento=EstadoProcessamento.NAO_PROCESSADO;
        this.codigoInternoMaquina=codigoInternoMaquina;
        this.ordemProducao=ordemProducao;
    }

    protected Mensagem() {
        //FOR ORM
        this.codigoInternoMaquina=null;
        this.linhaProducao=null;
        this.ordemProducao=null;
        this.mensagemID = null;
    }

    public void setLinhaProducao(LinhaProducao linhaProducao) {
        this.linhaProducao = linhaProducao;
    }

    public void setOrdemProducao(OrdemProducao ordemProducao) {
        this.ordemProducao = ordemProducao;
    }

    public void setEstadoProcessamento(EstadoProcessamento estadoProcessamento) {
        this.estadoProcessamento = estadoProcessamento;
    }

    public OrdemProducao getOrdemProducao() {
        return ordemProducao;
    }

    public static String identityAttributeName() {
        return "mensagemID";
    }

    @Override
    public boolean sameAs(Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mensagem mensagem = (Mensagem) o;
        return this.mensagemID.equals(((Mensagem) o).mensagemID);

    }

    @Override
    public int hashCode() {
        return Objects.hash(mensagemID,estadoProcessamento,codigoInternoMaquina);
    }

    @Override
    public MensagemID identity() {
        return mensagemID;
    }

    @Override
    public String toString() {
        return "Mensagem{" +
                "version=" + version +
                ", estadoProcessamento=" + estadoProcessamento +
                ", tipo=" + mensagemID.tipoDeMensagem +
                ", tempoEmissao=" + mensagemID.tempoEmissao +
                ", codigoInternoMaquina=" + codigoInternoMaquina +
                '}';
    }
}
