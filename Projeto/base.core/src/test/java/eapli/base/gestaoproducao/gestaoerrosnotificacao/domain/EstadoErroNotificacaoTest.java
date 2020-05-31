package eapli.base.gestaoproducao.gestaoerrosnotificacao.domain;


import eapli.base.gestaoproducao.gestaoerrosnotificacao.dto.EstadoNotificacaoErroDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * EstadoErroNotificacao Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>mai 31, 2020</pre>
 */
public class EstadoErroNotificacaoTest {

    EstadoErroNotificacao instance1 = EstadoErroNotificacao.ARQUIVADO;
    EstadoErroNotificacao instance2 = EstadoErroNotificacao.ATIVO;

    /**
     * Method: toDTO()
     */
    @Test
    public void testToDTO() throws Exception {
        EstadoNotificacaoErroDTO dto = instance1.toDTO();
        assertEquals(dto.estado, instance1.nomeDisplay);

        dto = instance2.toDTO();
        assertEquals(dto.estado, instance2.nomeDisplay);
    }

    /**
     * Method: actualValueOf(String nome)
     */
    @Test
    public void testActualValueOf() throws Exception {
        assertEquals(instance1, EstadoErroNotificacao.actualValueOf(instance1.nomeDisplay));
        assertEquals(instance2, EstadoErroNotificacao.actualValueOf(instance2.nomeDisplay));
    }

} 
