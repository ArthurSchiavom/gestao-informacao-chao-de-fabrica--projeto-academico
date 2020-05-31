package LinhaProducao.domain.Maquina;

import java.util.Date;
import java.util.Objects;

/**
 * Classe que guarda todas as informações necessárias para saber o estado da máquina
 */
public class Maquina {
	private final IdMaquina id;
	private Date lastUpdated;
	private EstadoMaquina estado;

	public Maquina(IdMaquina idMaquina, EstadoMaquina estado) {
		if(idMaquina == null || estado == null) {
			throw new IllegalArgumentException("valores de entrada não podem ser null");
		}
		this.id = idMaquina;
		this.lastUpdated = new Date();
		this.estado = estado;
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
}
