package eapli.base.gestaoproducao.ordemProducao.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class QuantidadeAProduzirTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor(){
        QuantidadeAProduzir qap = new QuantidadeAProduzir(-1);
    }

    @Test
    public void testConstructor2(){
        QuantidadeAProduzir qap = new QuantidadeAProduzir(0);
        QuantidadeAProduzir qap1 = new QuantidadeAProduzir(1);
        QuantidadeAProduzir qap2 = new QuantidadeAProduzir(1000);
    }

    @Test
    public void testEquals() {
        QuantidadeAProduzir qap = new QuantidadeAProduzir(0);
        QuantidadeAProduzir qap2 = new QuantidadeAProduzir(0);
        QuantidadeAProduzir qap1 = new QuantidadeAProduzir(1);
        assertFalse(qap.equals(qap1));
        assertTrue(qap.equals(qap2));
    }

    @Test
    public void compareTo() {
        QuantidadeAProduzir qap = new QuantidadeAProduzir(0);
        QuantidadeAProduzir qap2 = new QuantidadeAProduzir(0);
        QuantidadeAProduzir qap1 = new QuantidadeAProduzir(1);
        assertTrue(qap.compareTo(qap2)==0);
        assertTrue(qap.compareTo(qap1)<0);
    }
}