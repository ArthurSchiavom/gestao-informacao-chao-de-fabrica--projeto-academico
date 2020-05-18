package eapli.base.definircategoriamaterial.domain;

import eapli.base.gestaoproducao.gestaomaterial.domain.CodigoInterno;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CodigoInternoTest {
    @Test(expected = IllegalArgumentException.class) public void garantirCodigoInternoNaoPodeSerNull() {
            new CodigoInterno(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void garantirCodigoInternoNaoPodeSerStringVazia() {
            new CodigoInterno("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void garantirCodigoInternoNaoSerVazioComEspacos() {
            new CodigoInterno(" ");
    }

}
