package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;
import eapli.base.gestaoproducao.ordemProducao.domain.Identificador;
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
public abstract class Mensagem implements AggregateRoot<Long> {

    @Version
    private Long version;

    @Id
    @GeneratedValue
    private Long identifier; // can be public bc its final
    @XmlElement
    @Enumerated(EnumType.STRING)
    private EstadoProcessamento estadoProcessamento;

    @Column(insertable=false, updatable=false)
    public final TipoDeMensagem tipo;
    public final TimestampEmissao tempoEmissao;
    public final CodigoInternoMaquina codigoInternoMaquina;
    @ManyToOne
    private LinhaProducao linhaProducao;
    @ManyToOne
    private OrdemProducao ordemProducao;


    public Mensagem(TipoDeMensagem tipo, TimestampEmissao tempoEmissao, CodigoInternoMaquina codigoInternoMaquina) {
        if ((tipo == null || tempoEmissao == null||codigoInternoMaquina==null)) {
            throw new IllegalArgumentException("Mensagem não pode ter parametros null");
        }
        this.tipo = tipo;
        this.tempoEmissao = tempoEmissao;
        this.estadoProcessamento=EstadoProcessamento.NAO_PROCESSADO;
        this.codigoInternoMaquina=codigoInternoMaquina;
    }
    public Mensagem(TipoDeMensagem tipo, TimestampEmissao tempoEmissao, CodigoInternoMaquina codigoInternoMaquina,OrdemProducao ordemProducao) {
        if ((tipo == null || tempoEmissao == null||codigoInternoMaquina==null)) {
            throw new IllegalArgumentException("Mensagem não pode ter parametros null");
        }
        this.tipo = tipo;
        this.tempoEmissao = tempoEmissao;
        this.estadoProcessamento=EstadoProcessamento.NAO_PROCESSADO;
        this.codigoInternoMaquina=codigoInternoMaquina;
        this.ordemProducao=ordemProducao;
    }

    protected Mensagem() {
        //FOR ORM
        this.identifier = null;
        this.tipo = null;
        this.tempoEmissao = null;
        this.codigoInternoMaquina=null;
        this.linhaProducao=null;
        this.ordemProducao=null;
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
        return "identifier";
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mensagem mensagem = (Mensagem) o;
        return tipo == mensagem.tipo &&
                tempoEmissao.equals(mensagem.tempoEmissao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, tempoEmissao,estadoProcessamento,codigoInternoMaquina);
    }

    @Override
    public Long identity() {
        return identifier;
    }

    @Override
    public String toString() {
        return "Mensagem{" +
                "version=" + version +
                ", identifier=" + identifier +
                ", estadoProcessamento=" + estadoProcessamento +
                ", tipo=" + tipo +
                ", tempoEmissao=" + tempoEmissao +
                ", codigoInternoMaquina=" + codigoInternoMaquina +
                '}';
    }
}
