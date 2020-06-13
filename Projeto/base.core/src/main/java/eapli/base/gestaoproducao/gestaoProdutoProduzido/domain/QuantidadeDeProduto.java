package eapli.base.gestaoproducao.gestaoProdutoProduzido.domain;

import eapli.base.comum.domain.medicao.UnidadeDeMedida;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class QuantidadeDeProduto implements ValueObject {

    private double quantidadeProduzida;
    private UnidadeDeMedida unidadeDeMedida;

    protected QuantidadeDeProduto(){
        this.unidadeDeMedida=null;
    }

    public QuantidadeDeProduto(double quantidadeProduzida,UnidadeDeMedida unidadeDeMedida) {
        this.quantidadeProduzida=quantidadeProduzida;
        this.unidadeDeMedida=unidadeDeMedida;
    }

}
