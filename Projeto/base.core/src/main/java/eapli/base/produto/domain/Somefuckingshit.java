package eapli.base.produto.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Somefuckingshit implements ValueObject, Comparable<Somefuckingshit> {

    private static final long serialVersionUID = 1L;

    public final int val;

    public Somefuckingshit() {
        val = 0;
    }

    public Somefuckingshit(int val) {
        this.val = val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Somefuckingshit)) {
            return false;
        }

        final Somefuckingshit that = (Somefuckingshit) o;

        return val == that.val;
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public String toString() {
        return "" + val;
    }

    @Override
    public int compareTo(Somefuckingshit obj) {
        return val - obj.val;
    }
}

