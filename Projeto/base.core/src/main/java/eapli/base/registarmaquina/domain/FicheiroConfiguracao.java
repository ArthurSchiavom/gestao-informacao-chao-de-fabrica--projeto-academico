package eapli.base.registarmaquina.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

/**
 * Não está completo, esta Classe vai ser usada noutro UC
 * é para completar quando for precisso ou houver tempo
 */
@Embeddable
public class FicheiroConfiguracao implements ValueObject, Comparable<FicheiroConfiguracao> {

    private static final long serialVersionUID = 1L;

    public final String descricao;

    public FicheiroConfiguracao() {
        descricao = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FicheiroConfiguracao that = (FicheiroConfiguracao) o;
        return Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descricao);
    }

    @Override
    public String toString() {
        return "FicheiroConfiguracao{" +
                "descricao='" + descricao + '\'' +
                '}';
    }

    @Override
    public int compareTo(FicheiroConfiguracao obj) {
        return this.descricao.compareTo(obj.descricao);
    }
}