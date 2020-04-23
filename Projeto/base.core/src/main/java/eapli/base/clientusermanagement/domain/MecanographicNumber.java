/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.base.clientusermanagement.domain;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.StringPredicates;

/**
 *
 * @author Jorge Santos ajs@isep.ipp.pt
 */
@Embeddable
public class MecanographicNumber implements ValueObject, Comparable<MecanographicNumber> {

    private static final long serialVersionUID = 1L;

    private String number;

    public MecanographicNumber(String mecanographicNumber) {
        if (StringPredicates.isNullOrEmpty(mecanographicNumber)) {
            throw new IllegalArgumentException(
                    "Mecanographic Number should neither be null nor empty");
        }
        // TODO validate invariants, i.e., mecanographic number regular
        // expression
        this.number = mecanographicNumber;
    }

    protected MecanographicNumber() {
        // for ORM
    }

    public static MecanographicNumber valueOf(final String mecanographicNumber) {
        return new MecanographicNumber(mecanographicNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MecanographicNumber)) {
            return false;
        }

        final MecanographicNumber that = (MecanographicNumber) o;
        return this.number.equals(that.number);
    }

    @Override
    public int hashCode() {
        return this.number.hashCode();
    }

    @Override
    public String toString() {
        return this.number;
    }

    @Override
    public int compareTo(MecanographicNumber arg0) {
        return number.compareTo(arg0.number);
    }
}
