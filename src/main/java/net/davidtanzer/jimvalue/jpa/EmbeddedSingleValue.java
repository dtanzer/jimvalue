package net.davidtanzer.jimvalue.jpa;

import net.davidtanzer.jimvalue.SingleValue;
import net.davidtanzer.jimvalue.ValueCallback;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class EmbeddedSingleValue<T> implements SingleValue<T> {
	private T value;

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void withValue(final ValueCallback callback) {
	}

	public void setValue(final T value) {
		this.value = value;
	}

}
