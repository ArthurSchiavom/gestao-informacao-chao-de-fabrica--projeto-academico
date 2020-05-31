package Mensagens.domain;

public enum Codigos {
	HELLO(0), MSG(1), CONFIG(2), RESET(3), ACK(150), NACK(151);

	private byte codigo;

	Codigos(int codigo) {
		if(codigo > 255) {
			throw new IllegalArgumentException("Código só pode ter o tamanho máximo de um byte");
		}
		if(codigo < 0) {
			throw new IllegalArgumentException("Código não pode ser menor que 0");
		}
		this.codigo = (byte) codigo;
	}

	protected byte value() {
		return codigo;
	}

	protected static Codigos searchForCodigo(int valorCodigo) {
		for(Codigos codigo : Codigos.values()) {
			if(codigo.value() == valorCodigo) {
				return codigo;
			}
		}
		return null;
	}
}
