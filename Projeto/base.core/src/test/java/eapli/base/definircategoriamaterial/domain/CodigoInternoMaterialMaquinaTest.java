package eapli.base.definircategoriamaterial.domain;

import eapli.base.gestaoproducao.gestaomaterial.domain.CodigoInternoMaterial;
import org.junit.Test;

public class CodigoInternoMaterialMaquinaTest {
    @Test(expected = IllegalArgumentException.class) public void garantirCodigoInternoNaoPodeSerNull() {
            new CodigoInternoMaterial(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void garantirCodigoInternoNaoPodeSerStringVazia() {
            new CodigoInternoMaterial("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void garantirCodigoInternoNaoSerVazioComEspacos() {
            new CodigoInternoMaterial(" ");
    }

}
