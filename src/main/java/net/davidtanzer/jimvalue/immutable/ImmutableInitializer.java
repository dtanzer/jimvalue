package net.davidtanzer.jimvalue.immutable;

public interface ImmutableInitializer<T extends Immutable> {
	void init(ValueInitializer<T> initializer, T propertyIds);
}
