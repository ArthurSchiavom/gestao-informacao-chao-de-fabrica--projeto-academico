package eapli.base.registarmaquina.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrdemLinhaProducao implements ValueObject, Comparable<OrdemLinhaProducao> {

    private static final long serialVersionUID = 1L;

    public final int ordemLinhaProducao;

    protected OrdemLinhaProducao() {
        //  for ORM
        ordemLinhaProducao = 0;
    }

    public OrdemLinhaProducao(int ordemLinhaProducao) throws IllegalArgumentException{
        if(ordemLinhaProducao < 0){
            throw new IllegalArgumentException("Ordem na linha de produção tem que ser maior que 0");
        }
        this.ordemLinhaProducao = ordemLinhaProducao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdemLinhaProducao that = (OrdemLinhaProducao) o;
        return ordemLinhaProducao == that.ordemLinhaProducao;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ordemLinhaProducao);
    }

    @Override
    public String toString() {
        return "OrdemLinhaProducao{" +
                "ordemLinhaProducao=" + ordemLinhaProducao +
                '}';
    }

    @Override
    public int compareTo(OrdemLinhaProducao obj) {
        return this.ordemLinhaProducao-obj.ordemLinhaProducao;
    }
}