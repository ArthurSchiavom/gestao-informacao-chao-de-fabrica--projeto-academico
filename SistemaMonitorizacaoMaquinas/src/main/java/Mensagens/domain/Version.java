package Mensagens.domain;

import java.util.Objects;

public class Version {
	private byte versao;

	public Version(int versao) {
		if (versao > 255 || versao < 0) {
			throw new IllegalArgumentException("Versão não pode ser maior ou menor que 0");
		}
		this.versao = (byte) versao;
	}

	public int incrementarVersao() {
		byte versaoAntiga = versao;
		if (versao == -1) {
			versao = 0;
		} else {
			versao++;
		}
		return Byte.toUnsignedInt(versaoAntiga);
	}

	public int value() {
		return Byte.toUnsignedInt(versao);
	}

	public byte byteValue() {
		return versao;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Version version = (Version) o;
		return versao == version.versao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(versao);
	}
}
