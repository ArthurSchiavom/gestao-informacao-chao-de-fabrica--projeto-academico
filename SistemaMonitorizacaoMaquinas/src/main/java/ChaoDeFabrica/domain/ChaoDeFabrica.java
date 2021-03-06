package ChaoDeFabrica.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

	/**
	 * Adiciona uma linha de produção ao chão de fábrica
	 * @param idLinhaProducao a linha de produção que pretendemos adicionar
	 * @return a linha de produção adicionada
	 */
	public LinhaProducao adicionarLinhaProducao(int idLinhaProducao) {
		LinhaProducao linhaProducao = new LinhaProducao(idLinhaProducao);
		synchronized (listaLinhasProducao) {
			listaLinhasProducao.add(linhaProducao);
		}
		return linhaProducao;
	}

	/**
	 * Verifica se uma lista de produção existe
	 * @param idLinhaProducao o id pelo qual pretendemos procurar
	 * @return verdadeiro se existir
	 */
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

	/**
	 * Verifica a inatividade de cada máquina registada no chão de fábrica
	 * @param date o momento que pretendemos verificar
	 * @param tempo o tempo necessário para ser considerado inativo(em segundos)
	 */
	public void verificarInatividade(Date date, int tempo) {
		synchronized (listaLinhasProducao) {
			for(LinhaProducao linhaProducao : listaLinhasProducao) {
				linhaProducao.verificarInatividade(date, tempo);
			}
		}
	}
}
