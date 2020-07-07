package eapli.base.definircategoriamaterial.domain;


import eapli.base.gestaoproducao.gestaomaterial.domain.FichaTecnicaPDF;
import org.junit.Test;

import java.io.IOException;


public class FichaTecnicaPDFTest {
    @Test(expected = IllegalArgumentException.class) public void garantirPathNaoPodeSerNull() {
        try {
            new FichaTecnicaPDF(null,"teste","vazio");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void garantirPathNaoPodeSerStringVazia() {
        try {
            new FichaTecnicaPDF("","teste","vazio");
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void garantirPathNaoSerVazioComEspacos() {
        try {
            new FichaTecnicaPDF("        ","teste","vazio");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
