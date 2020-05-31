package eapli.base.infrastructure.application;

import java.util.Map;

/**
 *
 * @param <K> Chave do mapeamento
 * @param <V> Valor do mapeamento
 * @param <A> Tipo de objeto necessário para gerar o mapeamento
 */
public interface DefinirMapeamentoAction<K, V, A> {
    /**
     * Dado A, este método deve retornar um mapeamento de K para V.
     */
    Map.Entry<K, V> gerarEntrada(A a);
}
