package eapli.base.gestaoproducao.ordemProducao.domain;

import eapli.base.infrastructure.domain.IllegalDomainValueException;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class QuantidadeAProduzirTest {

    @Test(expected = IllegalDomainValueException.class)
    public void testConstructor() throws IllegalDomainValueException {
        QuantidadeAProduzir qap = new QuantidadeAProduzir(0);
    }

    @Test
    public void testConstructor2() throws IllegalDomainValueException{
        QuantidadeAProduzir qap = new QuantidadeAProduzir(1);
        QuantidadeAProduzir qap1 = new QuantidadeAProduzir(2);
        QuantidadeAProduzir qap2 = new QuantidadeAProduzir(1000);
    }

    @Test
    public void testEquals() throws IllegalDomainValueException {
        QuantidadeAProduzir qap = new QuantidadeAProduzir(1);
        QuantidadeAProduzir qap2 = new QuantidadeAProduzir(1);
        QuantidadeAProduzir qap1 = new QuantidadeAProduzir(2);
        assertFalse(qap.equals(qap1));
        assertTrue(qap.equals(qap2));
    }

    @Test
    public void compareTo() throws IllegalDomainValueException {
        QuantidadeAProduzir qap = new QuantidadeAProduzir(1);
        QuantidadeAProduzir qap2 = new QuantidadeAProduzir(1);
        QuantidadeAProduzir qap1 = new QuantidadeAProduzir(2);
        assertTrue(qap.compareTo(qap2)==0);
        assertTrue(qap.compareTo(qap1)<0);
    }
}