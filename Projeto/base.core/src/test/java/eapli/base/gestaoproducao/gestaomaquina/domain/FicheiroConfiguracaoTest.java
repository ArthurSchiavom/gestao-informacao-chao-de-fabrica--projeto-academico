package eapli.base.gestaoproducao.gestaomaquina.domain;

import org.junit.Test;

import java.io.IOException;


public class FicheiroConfiguracaoTest {
    @Test(expected = IllegalArgumentException.class)
    public void garantirDescricaoNaoPodeSerStringVazia() {
        try {
            new FicheiroConfiguracao("","x");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void garantirNomeFicheiroNaoPodeSerStringVazia() {
        try {
            new FicheiroConfiguracao("x","");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
