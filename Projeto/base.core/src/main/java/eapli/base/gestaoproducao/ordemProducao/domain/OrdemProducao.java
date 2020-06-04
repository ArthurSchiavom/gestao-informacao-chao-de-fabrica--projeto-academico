package eapli.base.gestaoproducao.ordemProducao.domain;

import eapli.base.gestaoproducao.exportacao.application.xml.DateAdapter;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;
import eapli.base.gestaoproducao.ordemProducao.application.OrdemProducaoDTO;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.domain.IllegalDomainValueType;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class OrdemProducao implements AggregateRoot<IdentificadorOrdemProducao> {

    @Version
    private Long version;

    @XmlAttribute
    @EmbeddedId
    public final IdentificadorOrdemProducao identificador;

    @XmlElement
    private QuantidadeAProduzir quantidadeAProduzir;

    @XmlElementWrapper(name = "encomendas")
    @XmlElement(name = "idEncomenda")
    @ElementCollection // to store a List which is a Collection
    private List<IdentificadorEncomenda> identificadorEncomendaList;

    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    @Temporal(TemporalType.DATE)
    public final Date dataEmissao;

    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    @Temporal(TemporalType.DATE)
    public final Date dataPrevistaExecucao;

    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    @Temporal(TemporalType.DATE)
    public Date fimExecucao;

    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    @Temporal(TemporalType.DATE)
    public Date inicioExecucao;

    @XmlElement
    private Estado estado;

    @XmlElement
    private CodigoUnico produto;

    /**
     * Do moodle:
     * "A data prevista de execução deve ser igual ou posterior à data de emissão da ordem de produção.
     * <p>
     * A data de realização real pode ser diferente da data prevista de execução."
     */
    public OrdemProducao(IdentificadorOrdemProducao identificador, QuantidadeAProduzir quantidadeAProduzir,
                         List<IdentificadorEncomenda> identificadorEncomendaList, Date dataEmissao,
                         Date dataPrevistaExecucao, Estado estado, CodigoUnico produto) throws IllegalArgumentException, IllegalDomainValueException {

        if (dataPrevistaExecucao == null || dataEmissao == null) {
            throw new IllegalArgumentException("A data de emissão e data prevista de execução da ordem de produção devem ser especificadas");
        }
        if (identificadorEncomendaList == null) {
            throw new IllegalArgumentException("Os identificadores de encomendas da ordem de produção devem ser especificados");
        }
        if (identificadorEncomendaList.isEmpty()) {
            throw new IllegalDomainValueException("Os identificadores de encomendas da ordem de produção devem ser especificados", IllegalDomainValueType.ILLEGAL_VALUE);
        }

        Date dataAtual = new Date(System.currentTimeMillis());
        if (dataPrevistaExecucao.compareTo(dataEmissao) < 0 && dataAtual.compareTo(dataEmissao) < 0) {
            throw new IllegalDomainValueException("Datas inválidas", IllegalDomainValueType.ILLEGAL_VALUE);
        }

        this.identificador = identificador;
        this.quantidadeAProduzir = quantidadeAProduzir;
        this.identificadorEncomendaList = identificadorEncomendaList;
        this.dataEmissao = dataEmissao;
        this.dataPrevistaExecucao = dataPrevistaExecucao;
        this.estado = estado;
        this.produto = produto;

    }

    public OrdemProducao() {
        identificador = null;
        dataEmissao = null;
        dataPrevistaExecucao = null;
    }

    public static String identityAttributeName() {
        return "identificador";
    }

    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public boolean sameAs(final Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public IdentificadorOrdemProducao identity() {
        return identificador;
    }

    public static List<OrdemProducaoDTO> gerarOrdensProducaoDTO(List<OrdemProducao> ordens) {
        List<OrdemProducaoDTO> ordensDTO = new ArrayList<>();

        for (OrdemProducao op : ordens) {
            ordensDTO.add(new OrdemProducaoDTO(op));
        }

        return ordensDTO;
    }

    public static OrdemProducaoDTO gerarOrdensProducaoDTO(OrdemProducao ordemProducao) {
        return new OrdemProducaoDTO(ordemProducao);
    }

    @Override
    public String toString() {

        String idEncomendaString = identificadorEncomendaList.stream().map(Object::toString)
                .collect(Collectors.joining(", "));


        return "OrdemProducao{" +
                "identificador=" + identificador.id +
                "\n quantidadeAProduzir=" + quantidadeAProduzir.quantidade +
                "\n identificadorEncomendaList=" + idEncomendaString +
                "\n dataEmissao=" + dataEmissao +
                "\n dataPrevistaExecucao=" + dataPrevistaExecucao +
                "\n estado=" + estado +
                "\n produto=" + produto.codigoUnicoValor +
                '}';
    }
}