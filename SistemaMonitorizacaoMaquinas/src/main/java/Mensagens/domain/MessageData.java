package Mensagens.domain;

import java.util.Arrays;
import java.util.Objects;

public class MessageData {
	final DataLength length;
	final RawData rawData;

	public MessageData(RawData rawData) {
		if(rawData == null) {
			throw new IllegalArgumentException("Raw data não pode ser null");
		}
		this.length = new DataLength(rawData.byteArraySize());
		this.rawData = rawData;
	}

	public MessageData(byte[] data) {
		this.length = new DataLength(Arrays.copyOfRange(data, 0, 2));
		this.rawData = new RawData(Arrays.copyOfRange(data, 2, 2+length.value()));
	}

	public int length() {
		return length.value();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MessageData that = (MessageData) o;
		return length.equals(that.length) &&
				rawData.equals(that.rawData);
	}

	@Override
	public int hashCode() {
		return Objects.hash(length, rawData);
	}
}
