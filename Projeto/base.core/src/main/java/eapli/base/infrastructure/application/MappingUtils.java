package eapli.base.infrastructure.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappingUtils {
    public static <K, V, A> Map<K, V> gerarMapa(DefinirMapeamentoAction<K, V, A> gerador, List<A> elementos) {
        Map<K, V> resultado = new HashMap<>();

        for (A elemento : elementos) {
            Map.Entry<K,V> entrada = gerador.gerarEntrada(elemento);
            resultado.put(entrada.getKey(), entrada.getValue());
        }

        return resultado;
    }
}
