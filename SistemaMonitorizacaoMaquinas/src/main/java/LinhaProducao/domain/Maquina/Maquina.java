package LinhaProducao.domain.Maquina;

import java.net.InetAddress;
import java.util.Date;
import java.util.Objects;

/**
 * Classe que guarda todas as informações necessárias para saber o estado da máquina
 */
public class Maquina {
	private final IdMaquina id;
	private Date lastUpdated;
	private EstadoMaquina estado;
	private InetAddress ip;

	public Maquina(IdMaquina idMaquina, EstadoMaquina estado, InetAddress ip) {
		if(idMaquina == null || estado == null) {
			throw new IllegalArgumentException("valores de entrada não podem ser null");
		}
		this.id = idMaquina;
		this.lastUpdated = new Date();
		this.estado = estado;
		this.ip = ip;
	}

	/**
	 * Atualiza o estado da máquina, ao mesmo tempo tambem atualiza a data de "ultima vez foi atualizado"
	 * @param estado o estado para o qual pretendemos atualizar
	 */
	public void atualizarEstado(EstadoMaquina estado) {
		this.estado = estado;
		lastUpdated = new Date();
	}

	public EstadoMaquina verificarEstado() {
		return estado;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Maquina maquina = (Maquina) o;
		return id.equals(maquina.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public IdMaquina identity() {
		return id;
	}

	/**
	 * Verifica se uma máquina está indisponivel há mais de uma certa quantidade de tempo
	 * e se estiver define o estado como sendo indisponivel
	 * @param date o segundo em que a verificação foi efetuada
	 * @param tempo o tempo que a máquina deve ficar sem ser atualizada para ser considerada indisponivel
	 */
	public void verificarInatividade(Date date, int tempo) {
		long diffInMilies = Math.abs(date.getTime() - lastUpdated.getTime());
		if(diffInMilies > tempo*1000) {
			estado = EstadoMaquina.INDISPONIVEL;
		}
	}
}
