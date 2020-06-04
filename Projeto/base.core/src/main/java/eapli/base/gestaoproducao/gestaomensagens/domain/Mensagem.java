package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.ordemProducao.domain.IdentificadorOrdemProducao;
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

    @ManyToOne
    private LinhaProducao linhaProducao;

    private IdentificadorOrdemProducao identificadorOrdemDeProducao;


    public Mensagem(TipoDeMensagem tipo, TimestampEmissao tempoEmissao, CodigoInternoMaquina codigoInternoMaquina) {
        if ((tipo == null || tempoEmissao == null||codigoInternoMaquina==null)) {
            throw new IllegalArgumentException("Mensagem não pode ter parametros null");
        }
        this.mensagemID = new MensagemID(tipo,tempoEmissao,codigoInternoMaquina);
        this.estadoProcessamento=EstadoProcessamento.NAO_PROCESSADO;
    }
    public Mensagem(TipoDeMensagem tipo, TimestampEmissao tempoEmissao, CodigoInternoMaquina codigoInternoMaquina,IdentificadorOrdemProducao ordemProducao) {
        if ((tipo == null || tempoEmissao == null||codigoInternoMaquina==null)) {
            throw new IllegalArgumentException("Mensagem não pode ter parametros null");
        }
        this.mensagemID = new MensagemID(tipo,tempoEmissao,codigoInternoMaquina);
        this.estadoProcessamento=EstadoProcessamento.NAO_PROCESSADO;
        this.identificadorOrdemDeProducao=ordemProducao;
    }

    protected Mensagem() {
        //FOR ORM
        this.linhaProducao=null;
        this.identificadorOrdemDeProducao=null;
        this.mensagemID = null;
    }

    public void setLinhaProducao(LinhaProducao linhaProducao) {
        this.linhaProducao = linhaProducao;
    }

    public void setIdentificadorOrdemDeProducao(IdentificadorOrdemProducao identificadorOrdemDeProducao) {
        this.identificadorOrdemDeProducao = identificadorOrdemDeProducao;
    }

    public void setEstadoProcessamento(EstadoProcessamento estadoProcessamento) {
        this.estadoProcessamento = estadoProcessamento;
    }

    public IdentificadorOrdemProducao getIdentificadorOrdemDeProducao() {
        return identificadorOrdemDeProducao;
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
        return Objects.hash(mensagemID,estadoProcessamento);
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
                '}';
    }
}
