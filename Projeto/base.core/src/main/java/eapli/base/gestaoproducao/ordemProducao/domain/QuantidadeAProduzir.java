package eapli.base.gestaoproducao.ordemProducao.domain;

import eapli.framework.domain.model.ValueObject;

import java.util.Objects;

public class QuantidadeAProduzir implements ValueObject, Comparable<QuantidadeAProduzir>{

    private static final long serialVersionUID = 1L;

    public final int quantidade;

    public QuantidadeAProduzir() {
        quantidade = 0;
    }

    public QuantidadeAProduzir(int quantidade) throws IllegalArgumentException{
        if(quantidade >= 0) {
            this.quantidade = quantidade;
        }else{
            throw new IllegalArgumentException("Quantidade não pode ser menor que 0");
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuantidadeAProduzir that = (QuantidadeAProduzir) o;
        return quantidade == that.quantidade;
    }


    @Override
    public int hashCode() {
        return Objects.hash(quantidade);
    }

    @Override
    public String toString() {
        return "QuantidadeAProduzir{" +
                "quantidade=" + quantidade +
                '}';
    }

    @Override
    public int compareTo(QuantidadeAProduzir quantidadeAProduzir) {
        return this.quantidade-quantidadeAProduzir.quantidade;
    }
}
