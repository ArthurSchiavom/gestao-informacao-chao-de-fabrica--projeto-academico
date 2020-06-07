package ChaoDeFabrica.domain.Maquina;

import java.util.Arrays;

/**
 * Guarda o id da maquina a seguir todas as regras do protocolo
 * Internalmente, armazena o valor do id da maquina como sendo 2 bytes little endian
 */
public class IdMaquina {
	private final byte[] byteVal;

	public IdMaquina(int idMaquina) {
		if(idMaquina > 65535 || idMaquina < 0) {
			throw new IllegalArgumentException("byteVal tem de ser um valor entre 0 e 65535");
		}
		this.byteVal = new byte[2];
		this.byteVal[0] = (byte) (idMaquina & 0xFF);
		this.byteVal[1] = (byte) ((idMaquina >> 8) & 0xFF);
	}

	/**
	 * Cria um idMaquina
	 * <b>LITTLE ENDIAN</b>
	 * @param data a array da data que pretendemos criar
	 */
	public IdMaquina(byte[] data) {
		if(data.length != 2) {
			throw new IllegalArgumentException("Tamanho de id máquina tem que ser 2 bytes");
		}
		this.byteVal = data;
	}

	public byte leastSignificativeByte() {
		return byteVal[0];
	}

	public byte mostSignificativeByte() {
		return byteVal[1];
	}

	public int value() {
		return byteVal[0] | byteVal[1] << 8;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		IdMaquina idMaquina = (IdMaquina) o;
		return Arrays.equals(byteVal, idMaquina.byteVal);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(byteVal);
	}
}
