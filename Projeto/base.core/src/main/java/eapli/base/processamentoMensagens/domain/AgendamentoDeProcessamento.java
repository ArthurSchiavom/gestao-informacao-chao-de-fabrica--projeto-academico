package eapli.base.processamentoMensagens.domain;

import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaomaterial.domain.CodigoInternoMaterial;
import eapli.base.infrastructure.application.ConvertableToDTO;
import eapli.base.processamentoMensagens.application.DTO.AgendamentoDeProcessamentoDTO;
import eapli.base.utilities.Reflection;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Entity
public class AgendamentoDeProcessamento implements AggregateRoot<Long>, ConvertableToDTO<AgendamentoDeProcessamentoDTO> {
    @Version
    private Long version;

    @Id
    @GeneratedValue
    @XmlAttribute
    private Long identifier;

    @ManyToOne
    @XmlElement
    private LinhaProducao linhasProducao;

    @XmlElement
    public final InicioDeProcessamento inicioDeProcessamento;
    @XmlElement
    public final FinalDeProcessamento finalDeProcessamento;

    protected AgendamentoDeProcessamento(){
        inicioDeProcessamento=null;
        finalDeProcessamento=null;
    }

    public AgendamentoDeProcessamento(InicioDeProcessamento inicioDeProcessamento,FinalDeProcessamento finalDeProcessamento)  {
        this.inicioDeProcessamento=inicioDeProcessamento;
        this.finalDeProcessamento=finalDeProcessamento;
    }

    public static String identityAttributeName() {
        return Reflection.retrieveAttributeName(AgendamentoDeProcessamento.class, CodigoInternoMaterial.class);
    }

    public void setLinhasProducao(LinhaProducao linhasProducao) {
        this.linhasProducao = linhasProducao;
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public Long identity() {
        return this.identifier;
    }


    @Override
    public AgendamentoDeProcessamentoDTO toDTO() {
        return new AgendamentoDeProcessamentoDTO(inicioDeProcessamento.dataTempoInicio,finalDeProcessamento.dataTempoFinal,linhasProducao.estadoProcessamentoMensagens(),linhasProducao.identifier);
    }
}
