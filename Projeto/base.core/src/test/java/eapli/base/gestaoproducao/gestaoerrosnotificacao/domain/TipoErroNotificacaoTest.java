package eapli.base.gestaoproducao.gestaoerrosnotificacao.domain;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.dto.EstadoNotificacaoErroDTO;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.dto.TipoNotificacaoErroDTO;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.assertEquals;

/**
 * TipoErroNotificacao Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>mai 31, 2020</pre>
 */
public class TipoErroNotificacaoTest {

    TipoErroNotificacao instance1 = TipoErroNotificacao.DADOS_INVALIDOS;
    TipoErroNotificacao instance2 = TipoErroNotificacao.ELEMENTOS_INEXISTENTES;

    /**
     * Method: toDTO()
     */
    @Test
    public void testToDTO() throws Exception {
        TipoNotificacaoErroDTO dto = instance1.toDTO();
        assertEquals(dto.nomeDisplay, instance1.nomeDisplay);

        dto = instance2.toDTO();
        assertEquals(dto.nomeDisplay, instance2.nomeDisplay);
    }

    /**
     * Method: actualValueOf(String nome)
     */
    @Test
    public void testActualValueOf() throws Exception {
        assertEquals(instance1, TipoErroNotificacao.actualValueOf(instance1.nomeDisplay));
        assertEquals(instance2, TipoErroNotificacao.actualValueOf(instance2.nomeDisplay));
    }


} 
