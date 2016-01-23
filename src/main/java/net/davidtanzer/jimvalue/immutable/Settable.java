package net.davidtanzer.jimvalue.immutable;

public class Settable<T> {
	private final String propertyName;
	private final ValueInitializer valueInitializer;

	<T> Settable(final String propertyName, final ValueInitializer valueInitializer) {
		this.propertyName = propertyName;
		this.valueInitializer = valueInitializer;
	}

	public void to(final T value) {
		valueInitializer.setValue(propertyName, value);
	}
}
