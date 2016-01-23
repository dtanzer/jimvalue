package net.davidtanzer.jimvalue.immutable;

import java.util.HashMap;
import java.util.Map;

public class ValueInitializer<T extends Immutable> {
	private final PropertyIdGenerator<T> propertyIdGenerator;
	private final Map<String, Object> propertyValues = new HashMap<>();

	public ValueInitializer(final PropertyIdGenerator<T> propertyIdGenerator) {
		this.propertyIdGenerator = propertyIdGenerator;
	}

	public <U> Settable<U> set(U propertyId) {
		return new Settable<U>(propertyIdGenerator.getPropertyName(propertyId), this);
	}

	Object getValueFor(final String name) {
		return propertyValues.get(name);
	}

	<T> void setValue(final String propertyName, final T value) {
		propertyValues.put(propertyName, value);
	}
}
