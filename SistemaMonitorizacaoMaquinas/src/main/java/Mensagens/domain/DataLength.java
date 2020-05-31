package Mensagens.domain;

import java.util.Arrays;

public class DataLength {
	private final byte[] byteVal;

	public DataLength(int dataLength) {
		if (dataLength > 506 || dataLength < 0) {
			throw new IllegalArgumentException("length tem de ser um valor entre 0 e 65535");
		}
		this.byteVal = new byte[2];
		this.byteVal[0] = (byte) (dataLength & 0xFF);
		this.byteVal[1] = (byte) (byte) ((dataLength >> 8) & 0xFF);
	}

	/**
	 * Cria um data length
	 * <b>LITTLE ENDIAN</b>
	 * @param data uma array com os dados que precisamos
	 */
	public DataLength(byte[] data) {
		if(data.length != 2) {
			throw new IllegalArgumentException("Size of data has to be 2 bytes");
		}
		this.byteVal = data;
	}

	protected byte leastSignificativeByte() {
		return byteVal[0];
	}

	protected byte mostSignificativeByte() {
		return byteVal[1];
	}

	public int value() {
		return byteVal[0] | byteVal[1] << 8;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DataLength that = (DataLength) o;
		return Arrays.equals(byteVal, that.byteVal);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(byteVal);
	}
}
