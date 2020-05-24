package eapli.base.gestaoproducao.ordemProducao.domain;

import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.ordemProducao.application.OrdemProducaoDTO;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class OrdemProducao implements AggregateRoot<Identificador> {

    @Version
    private Long version;

    @XmlAttribute
    @EmbeddedId
    public final Identificador identificador;
    @XmlElement
    private QuantidadeAProduzir quantidadeAProduzir;
    @XmlElementWrapper(name = "encomendas")
    @XmlElement(name = "identificadorEncomenda")
    @ElementCollection // to store a List which is a Collection
    private List<IdentificadorEncomenda> identificadorEncomendaList;
    //TODO Diogo muda isto para LocalDate
    @XmlElement
    public final Date dataEmissao;
    @XmlElement
    public final Date dataPrevistaExecucao;
    @XmlElement
    public Date fimExecucao;
    @XmlElement
    public Date inicioExecucao;
    @XmlElement
    private Estado estado;
    @OneToMany
    @XmlElementWrapper(name = "produtos")
    @XmlElement(name = "codigoProduto")
    //TODO Diogo muda isto para CodigoUnico
    private List<Produto> produtos;

    /**
     * Do moodle:
     * "A data prevista de execução deve ser igual ou posterior à data de emissão da ordem de produção.
     * <p>
     * A data de realização real pode ser diferente da data prevista de execução."
     */
    public OrdemProducao(Identificador identificador, QuantidadeAProduzir quantidadeAProduzir,
                         List<IdentificadorEncomenda> identificadorEncomendaList, Date dataEmissao,
                         Date dataPrevistaExecucao, Estado estado) throws IllegalArgumentException {

        if (dataPrevistaExecucao.compareTo(dataEmissao) >= 0) {

            this.identificador = identificador;
            this.quantidadeAProduzir = quantidadeAProduzir;
            this.identificadorEncomendaList = identificadorEncomendaList;
            this.dataEmissao = dataEmissao;
            this.dataPrevistaExecucao = dataPrevistaExecucao;
            this.estado = estado;
            this.produtos = new ArrayList<>();
        } else {
            throw new IllegalArgumentException("Datas não válidas");
        }
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
    public Identificador identity() {
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


        String produtosString = produtos.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
//        for(Produto p : produtos){
//            produtosString += p + "\n";
//        }

        String idEncomendaString = identificadorEncomendaList.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
//        for(IdentificadorEncomenda p : identificadorEncomendaList){
//            idEncomendaString += p + "\n";
//        }

        return "OrdemProducao{" +
                "identificador=" + identificador.identificador +
                "\n quantidadeAProduzir=" + quantidadeAProduzir.quantidade +
                "\n identificadorEncomendaList=" + idEncomendaString +
                "\n dataEmissao=" + dataEmissao +
                "\n dataPrevistaExecucao=" + dataPrevistaExecucao +
                "\n estado=" + estado +
                "\n produtos=" + produtosString +
                '}';
    }
}