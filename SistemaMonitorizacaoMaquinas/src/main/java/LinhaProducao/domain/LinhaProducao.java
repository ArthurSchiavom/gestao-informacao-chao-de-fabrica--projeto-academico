package LinhaProducao.domain;

import LinhaProducao.domain.Maquina.EstadoMaquina;
import LinhaProducao.domain.Maquina.IdMaquina;
import LinhaProducao.domain.Maquina.Maquina;
import Mensagens.domain.Codigos;

import java.util.*;

public class LinhaProducao {
	private static final String CODIGO_INVALIDO = "Foi recebido um pacote com um código inválido";

	private final int idLinhaProducao;
	private final List<Maquina> listaMaquinas;

	public LinhaProducao(int idLinhaProducao) {
		this.idLinhaProducao = idLinhaProducao;
		this.listaMaquinas = Collections.synchronizedList(new ArrayList<>());
	}

	public Maquina adicionarMaquina(IdMaquina idMaquina, Codigos codigo) {
		Maquina maquina;
		switch (codigo) {
			case ACK:
				maquina = new Maquina(idMaquina, EstadoMaquina.ATIVO);
				break;
			case NACK:
				maquina = new Maquina(idMaquina, EstadoMaquina.SUSPENSO);
				break;
			default:
				throw new IllegalArgumentException(CODIGO_INVALIDO);
		}
		synchronized (listaMaquinas) {
			listaMaquinas.add(maquina);
		}
		return maquina;
	}

	public Maquina atualizarMaquina(IdMaquina idMaquina, Codigos codigo) {
		Maquina maquina = procurarPorMaquina(idMaquina);
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

	public int identity() {
		return idLinhaProducao;
	}

	public boolean maquinaExiste(IdMaquina idMaquina) {
		synchronized (listaMaquinas) {
			return listaMaquinas.stream().anyMatch(maquina -> maquina.identity().equals(idMaquina));
		}
	}

	public Maquina procurarPorMaquina(IdMaquina idMaquina) {
		synchronized (listaMaquinas) {
			return listaMaquinas.stream().filter(maquina -> maquina.identity().equals(idMaquina)).findFirst().orElse(null);
		}
	}

	public void verificarInatividade(Date date, int tempo) {
		synchronized (listaMaquinas) {
			for(Maquina maquina : listaMaquinas) {
				maquina.verificarInatividade(date, tempo);
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
