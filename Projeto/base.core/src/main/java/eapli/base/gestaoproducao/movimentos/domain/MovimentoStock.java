package eapli.base.gestaoproducao.movimentos.domain;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.QuantidadeDeMateriaPrima;
import eapli.base.utilities.Reflection;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Entity
public class MovimentoStock implements AggregateRoot<Long> {


    @Id
    @GeneratedValue
    @XmlAttribute(name = "id")
    private long id;

    @XmlElement(name = "deposito")
    private CodigoDeposito codigoDeposito;

    @XmlElement(name = "quantidadeDeMateriaPrima")
    private QuantidadeDeMateriaPrima quantidadeDeMateriaPrima;

    protected MovimentoStock() {
        this.codigoDeposito=null;
        this.quantidadeDeMateriaPrima=null;
    }

    public MovimentoStock(CodigoDeposito codigoDeposito, QuantidadeDeMateriaPrima quantidadeDeMateriaPrima) {
        this.codigoDeposito=codigoDeposito;
        this.quantidadeDeMateriaPrima=quantidadeDeMateriaPrima;
    }

    public static String identityAttributeName() {
        return Reflection.retrieveAttributeName(MovimentoStock.class, Long.class);
    }


    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public Long identity() {
        return this.id;
    }
}

