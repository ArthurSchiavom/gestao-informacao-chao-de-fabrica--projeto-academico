package eapli.base.gestaoproducao.gestaoProdutoProduzido.domain;

import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.utilities.Reflection;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.*;

@Entity
public class ProdutoProduzido implements AggregateRoot<Long> {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Produto produto;

    private IdentificadorDeLote identificadorDeLote;

    private QuantidadeDeProduto quantidadeDeProduto;


    protected ProdutoProduzido() {
    }


    public ProdutoProduzido(Produto produto,IdentificadorDeLote identificadorDeLote, QuantidadeDeProduto quantidadeDeProduto){
        this.produto=produto;
        this.identificadorDeLote=identificadorDeLote;
        this.quantidadeDeProduto=quantidadeDeProduto;
    }
    public ProdutoProduzido(Produto produto, QuantidadeDeProduto quantidadeDeProduto){
        this.produto=produto;
        this.quantidadeDeProduto=quantidadeDeProduto;
    }

    public static String identityAttributeName() {
        return Reflection.retrieveAttributeName(ProdutoProduzido.class, Long.class);
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
