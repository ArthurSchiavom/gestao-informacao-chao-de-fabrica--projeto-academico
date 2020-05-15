package eapli.base.gestaoproducao.gestaomaquina.domain;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class NumeroSerieTest {

    @Test(expected = IllegalArgumentException.class)
    public void garantirNumeroSerieEntreMaxMin() {

        try {
            NumeroSerie.definirRegrasNumeroSerie("20","6");
        } catch (IOException e) {
            e.printStackTrace();
        }
        new NumeroSerie("12345");
    }

    @Test
    public void garantirNumeroSerieEntreMaxMin2() {

        try {
            NumeroSerie.definirRegrasNumeroSerie("20","6");
        } catch (IOException e) {
            e.printStackTrace();
        }
        NumeroSerie numero = new NumeroSerie("123456");
        assertTrue("Objeto criado", numero != null);
    }

}