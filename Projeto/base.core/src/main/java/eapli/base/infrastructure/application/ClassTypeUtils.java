package eapli.base.infrastructure.application;

import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemInicioDeAtividade;
import org.springframework.cglib.core.ClassesKey;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClassTypeUtils {
    public static <T> List<T> encontrarObjetosDoTipo(Collection<?> lista, Class<T> classe) {
        List<T> resultado = new ArrayList<>();
        for (Object obj : lista) {
            if (obj.getClass() == classe) {
                resultado.add((T) obj);
            }
        }

        return resultado;
    }

    public static <T> List<T> encontrarObjetosDoTipo(Collection<?> lista, Class<T> tipoResultado, Class<?>... classes) {
        List<T> resultado = new ArrayList<>();
        for (Object obj : lista) {
            for (Class classe : classes) {
                if (obj.getClass() == classe) {
                    resultado.add((T) obj);
                    break;
                }
            }
        }

        return resultado;
    }
}
