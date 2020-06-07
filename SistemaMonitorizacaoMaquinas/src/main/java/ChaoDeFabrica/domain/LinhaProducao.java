package ChaoDeFabrica.domain;

import ChaoDeFabrica.domain.Maquina.EstadoMaquina;
import ChaoDeFabrica.domain.Maquina.IdMaquina;
import ChaoDeFabrica.domain.Maquina.Maquina;
import Mensagens.domain.Codigos;

import java.net.InetAddress;
import java.util.*;

public class LinhaProducao {
	private static final String CODIGO_INVALIDO = "Foi recebido um pacote com um código inválido";

	private final int idLinhaProducao;
	private final List<Maquina> listaMaquinas;

	public LinhaProducao(int idLinhaProducao) {
		this.idLinhaProducao = idLinhaProducao;
		this.listaMaquinas = Collections.synchronizedList(new ArrayList<>());
	}

	/**
	 * Adiciona uma máquina á linha de produção
	 *
	 * @param idMaquina o id da máquina que pretendemos adicionar
	 * @param codigo    o código de resposta
	 * @param ip        o ip da máquina
	 * @return uma referência ao objeto criado
	 */
	public Maquina adicionarMaquina(IdMaquina idMaquina, Codigos codigo, InetAddress ip) {
		Maquina maquina;
		switch (codigo) {
			case ACK:
				maquina = new Maquina(idMaquina, EstadoMaquina.ATIVO, ip);
				break;
			case NACK:
				maquina = new Maquina(idMaquina, EstadoMaquina.SUSPENSO, ip);
				break;
			default:
				throw new IllegalArgumentException(CODIGO_INVALIDO);
		}
		synchronized (listaMaquinas) {
			listaMaquinas.add(maquina);
		}
		return maquina;
	}

	/**
	 * Atualiza o estado da máquina
	 *
	 * @param idMaquina o id da máquina que pretendemos atualizar
	 * @param codigo    o código que foi recebido para atualizar o estado da máquina
	 * @param ip        o ip da máquina que foi recebido para verificar se não há máquinas com ips diferentes mas com o mesmo id
	 * @return uma referência para a máquina atualizada
	 */
	public Maquina atualizarMaquina(IdMaquina idMaquina, Codigos codigo, InetAddress ip) {
		Maquina maquina = procurarPorMaquina(idMaquina);
		if (maquina.addressIsntCorrect(ip)) {
			System.out.println("Atenção, máquina " + maquina.toString() + "devia ter ip " + maquina.address()
					+ " mas foi atualizada pelo ip " + ip);
		}
		switch (codigo) {
			case ACK:
				maquina.atualizarEstado(EstadoMaquina.ATIVO);
				break;
			case NACK:
				maquina.atualizarEstado(EstadoMaquina.SUSPENSO);
				break;
			default:
				throw new IllegalArgumentException(CODIGO_INVALIDO);
		}
		return maquina;
	}

	/**
	 * @return a identidade da linha de produção
	 */
	public int identity() {
		return idLinhaProducao;
	}

	/**
	 * Procura na linha de produção pela existência de uma máquina
	 * @param idMaquina o id da máquina que pretendemos procurar
	 * @return verdadeiro se existir
	 */
	public boolean maquinaExiste(IdMaquina idMaquina) {
		synchronized (listaMaquinas) {
			return listaMaquinas.stream().anyMatch(maquina -> maquina.identity().equals(idMaquina));
		}
	}

	/**
	 * Vai buscar uma referência á máquina com o id que foi passado por argumento
	 * @param idMaquina o id pelo qual pretendemos procurar
	 * @return uma referência para a máquina com o id da máquina que foi procurado
	 */
	public Maquina procurarPorMaquina(IdMaquina idMaquina) {
		synchronized (listaMaquinas) {
			return listaMaquinas.stream().filter(maquina -> maquina.identity().equals(idMaquina)).findFirst().orElse(null);
		}
	}

	/**
	 * Verifica a inatividade das máquinas na linha de produção
	 * @param date o momento em que pretendemos verificar
	 * @param tempo o tempo que a máquina tem que estar sem resposta para ser considerada inativa
	 */
	public void verificarInatividade(Date date, int tempo) {
		synchronized (listaMaquinas) {
			for (Maquina maquina : listaMaquinas) {
				maquina.verificarInatividade(date, tempo, idLinhaProducao);
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LinhaProducao that = (LinhaProducao) o;
		return idLinhaProducao == that.idLinhaProducao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idLinhaProducao);
	}
}
