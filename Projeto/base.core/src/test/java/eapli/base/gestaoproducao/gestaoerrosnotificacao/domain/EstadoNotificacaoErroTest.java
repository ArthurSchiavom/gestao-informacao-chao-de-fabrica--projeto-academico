package eapli.base.gestaoproducao.gestaoerrosnotificacao.domain;


import eapli.base.gestaoproducao.gestaoerrosnotificacao.dto.EstadoNotificacaoErroDTO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * EstadoErroNotificacao Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>mai 31, 2020</pre>
 */
public class EstadoNotificacaoErroTest {

    EstadoNotificacaoErro instance1 = EstadoNotificacaoErro.ARQUIVADO;
    EstadoNotificacaoErro instance2 = EstadoNotificacaoErro.ATIVO;

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
        assertEquals(instance1, EstadoNotificacaoErro.actualValueOf(instance1.nomeDisplay));
        assertEquals(instance2, EstadoNotificacaoErro.actualValueOf(instance2.nomeDisplay));
    }

} 
