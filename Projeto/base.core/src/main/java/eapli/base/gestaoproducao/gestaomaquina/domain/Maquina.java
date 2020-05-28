package eapli.base.gestaoproducao.gestaomaquina.domain;

import eapli.base.gestaoproducao.exportacao.application.xml.DateAdapter;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaomaquina.aplication.dto.MaquinaDTO;
import eapli.base.infrastructure.application.HasDTO;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Date;

/**
 * "O código interno é aquele que é comumente usado para identificar uma máquina."
 * <p>
 * https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=29197
 * ---------------------------------------------------------------------------------------------------------------
 * O ficheiro de configuração vai ser parte de outro UC:
 * <p>
 * "Na definição da máquina (US 3001) não devem considerar essa necessidade.
 * <p>
 * Isso é tipicamente realizado num momento diferente e, será alvo de outra US futuramente."
 * <p>
 * https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=29442
 */
@Entity
public class Maquina implements AggregateRoot<CodigoInternoMaquina>, HasDTO<MaquinaDTO>{

    @Version
    private Long version;

    @XmlElement
    private NumeroSerie numeroSerie;
    @EmbeddedId
    @XmlAttribute(name = "codigoInterno")
    private CodigoInternoMaquina codigoInternoMaquina;
    @XmlElement
    private OrdemLinhaProducao ordemLinhaProducao;
    @XmlElement
    private FicheiroConfiguracao ficheiroConfiguracao;
    @XmlElement
    private IdentificadorProtocoloComunicacao identificadorProtocoloComunicacao;
    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    public final Date dataInstalacao;
    @XmlElement
    private String marca; // might change so it's not final
    @XmlElement
    private String modelo; // might change so it's not final
    @XmlElement
    private String descricaoMaquina; // might change so it's not final
    @XmlElement
    private IdentificadorLinhaProducao linhaProducao;
    @XmlElement
    private InetAddress ip;

    protected Maquina() {
        ficheiroConfiguracao=null;
        dataInstalacao = null;
    }

    public Maquina(NumeroSerie numeroSerie, CodigoInternoMaquina codigoInterno, OrdemLinhaProducao ordemLinhaProducao,
                   IdentificadorProtocoloComunicacao identificadorProtocoloComunicacao, String descricao, String marca,
                   String modelo, LinhaProducao linha) throws IllegalArgumentException {
        try {
            this.numeroSerie = numeroSerie;
            this.codigoInternoMaquina = codigoInterno;
            this.ordemLinhaProducao = ordemLinhaProducao;
            this.identificadorProtocoloComunicacao = identificadorProtocoloComunicacao;
            this.descricaoMaquina = descricao;
            this.marca = marca;
            this.modelo = modelo;
            this.linhaProducao = linha.identifier;
            this.dataInstalacao = Calendar.getInstance().getTime(); // get current time
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Argumentos incorretos");
        }
    }

    public static String identityAttributeName() {
        return "codigoInternoMaquina";
    }

    @Override
    public CodigoInternoMaquina identity() {
            return this.codigoInternoMaquina;
    }

    @XmlTransient
    public OrdemLinhaProducao getOrdemLinhaProducao() {
            return ordemLinhaProducao;
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
    public String toString() {
        return "Maquina{" +
                "numeroSerie=" + numeroSerie.numeroSerie +
                ", ordemLinhaProducao=" + ordemLinhaProducao.ordemLinhaProducao +
                ", dataInstalacao=" + dataInstalacao +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", descricaoMaquina='" + descricaoMaquina + '\'' +
                ", linhaProducao=" + linhaProducao.identifier +
                '}';
    }

    public void setOrdemLinhaProducao(OrdemLinhaProducao ordemLinhaProducao) {
        this.ordemLinhaProducao = ordemLinhaProducao;
    }

    public void setFicheiroConfiguracao(FicheiroConfiguracao ficheiroConfiguracao) {
        this.ficheiroConfiguracao = ficheiroConfiguracao;
    }

    @XmlTransient
    public FicheiroConfiguracao getFicheiroConfiguracao() {
        return ficheiroConfiguracao;
    }

    @XmlTransient
    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    @Override
    public MaquinaDTO toDTO() {
        return new MaquinaDTO(numeroSerie.numeroSerie,codigoInternoMaquina.codigoInterno,""+ordemLinhaProducao.ordemLinhaProducao,""+identificadorProtocoloComunicacao.identificadorProtocoloComunicao,marca,modelo,descricaoMaquina,linhaProducao.identifier);
    }
}