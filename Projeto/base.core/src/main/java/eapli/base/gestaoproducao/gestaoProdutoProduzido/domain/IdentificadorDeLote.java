package eapli.base.gestaoproducao.gestaoProdutoProduzido.domain;


import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlElement;

@Embeddable
public class IdentificadorDeLote implements ValueObject, Comparable<IdentificadorDeLote> {
    private static final long serialVersionUID = 1L;
    @XmlElement
    private String identificador;

    protected IdentificadorDeLote(){
    }
    public IdentificadorDeLote(String identificador) {
        this.identificador = identificador;
    }

    @Override
    public int compareTo(IdentificadorDeLote o) {
        return 0;
    }
}
