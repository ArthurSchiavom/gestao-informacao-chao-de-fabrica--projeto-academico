package eapli.base.gestaoproducao.gestaoProdutoProduzido.domain;

import eapli.base.comum.domain.medicao.UnidadeDeMedida;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class QuantidadeDeProduto implements ValueObject {

    private int quantidadeProduzida;
    private UnidadeDeMedida unidadeDeMedida;

    protected QuantidadeDeProduto(){
        this.unidadeDeMedida=null;
    }

    public QuantidadeDeProduto(int quantidadeProduzida,UnidadeDeMedida unidadeDeMedida) {
        this.quantidadeProduzida=quantidadeProduzida;
        this.unidadeDeMedida=unidadeDeMedida;
    }

}
