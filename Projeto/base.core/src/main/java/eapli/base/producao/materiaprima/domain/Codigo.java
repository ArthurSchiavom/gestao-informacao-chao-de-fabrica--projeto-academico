package eapli.base.producao.materiaprima.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Codigo implements Serializable, Comparable<Codigo> {
    @Override
    public int compareTo(Codigo o) {
        return 0;
    }
}
