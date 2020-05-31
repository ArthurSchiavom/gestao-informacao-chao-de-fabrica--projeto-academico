package LinhaProducao.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe singleton que guarda a lista de linhas de produção
 * Que por sua vez guardam uma linha de máquinas
 * Que por sua vez guardam o estado e id e a última vez que foram atualizadas
 */
public class ChaoDeFabrica {
	private static ChaoDeFabrica ourInstance = new ChaoDeFabrica();

	public static ChaoDeFabrica getInstance() {
		return ourInstance;
	}

	private ChaoDeFabrica() {
		listaLinhasProducao = Collections.synchronizedList(new ArrayList<>());
	}

	private final List<LinhaProducao> listaLinhasProducao;

	public LinhaProducao adicionarLinhaProducao(int idLinhaProducao) {
		LinhaProducao linhaProducao = new LinhaProducao(idLinhaProducao);
		synchronized (listaLinhasProducao) {
			listaLinhasProducao.add(linhaProducao);
		}
		return linhaProducao;
	}

	public boolean verificarQueListaProducaoExiste(int idLinhaProducao) {
		synchronized (listaLinhasProducao) {
			return listaLinhasProducao.stream().anyMatch(linhaProducao -> linhaProducao.identity() == idLinhaProducao);
		}
	}

	/**
	 * Procura pela linha de produção no chão de fábrica
	 * @param idLinhaProducao o id que estamos á procura
	 * @return ou a linha de produção ou então retorna um null se não encontrar nada
	 */
	public LinhaProducao procurarPorLinhaProducao(int idLinhaProducao) {
		synchronized (listaLinhasProducao) {
			return listaLinhasProducao.stream().filter(linhaProd -> linhaProd.identity() == idLinhaProducao).findFirst().orElse(null);
		}
	}
}
