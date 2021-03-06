package eapli.base.gestaoproducao.gestaomaquina.domain;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
public class MaquinaTest {

    @Test
    public void garantirNomeAtributoIdentidadeExiste() {
        boolean found = false;
        String identificador = Maquina.identityAttributeName();
        for (Field field : Maquina.class.getDeclaredFields()) {
            if (identificador.equals(field.getName()) &&
                    field.getType().isAssignableFrom(CodigoInternoMaquina.class)) {
                found = true;
                break;
            }
        }
        if (found) {
            assertTrue(true);
        } else {
            fail("Verificar valor de retorno do metodo identityAttributeName");
        }
    }
}