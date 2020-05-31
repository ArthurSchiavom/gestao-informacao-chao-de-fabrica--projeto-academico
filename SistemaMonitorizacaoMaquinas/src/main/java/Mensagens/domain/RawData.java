package Mensagens.domain;

import java.util.Arrays;

public class RawData {
	private final byte[] content;

	public RawData(String content) {
		if(content == null) {
			throw new IllegalArgumentException("conteudo não pode ser null, utilize uma string vazia");
		}
		this.content = content.getBytes();
	}

	public RawData(byte[] data) {
		if(data == null) {
			throw new IllegalArgumentException("data não pode ser null");
		}
		this.content = data;
	}

	byte[] toByteArray() {
		return content;
	}

	int byteArraySize() {
		return content.length;
	}

	String getTextContent() {
		return new String(content);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RawData rawData = (RawData) o;
		return Arrays.equals(content, rawData.content);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(content);
	}
}
