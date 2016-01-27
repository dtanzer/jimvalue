package net.davidtanzer.jimvalue.immutable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public interface Immutable {
	static <T extends Immutable> T create(Class<T> type, ImmutableInitializer<T> initializer) {
		final StackTraceElement[] stackTrace = new Exception().getStackTrace();
		final StackTraceElement caller = stackTrace[1];

		if(!caller.getClassName().equals(type.getName())) {
			throw new IllegalStateException("Cannot create immutable: \"create\" must be called from immutable type.");
		}

		PropertyIdGenerator<T> propertyIdGenerator = new PropertyIdGenerator(type);
		final ValueInitializer<T> valueInitializer = new ValueInitializer<>(propertyIdGenerator);

		initializer.init(valueInitializer, propertyIdGenerator.propertyIds());

		final InvocationHandler invocationHandler = new ValuesInvocationHandler(valueInitializer);
		return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[] {type}, invocationHandler);
	}

	static <I extends Immutable, E extends I> I createFrom(E entity, Class<I> immutableClass) {
		final InvocationHandler invocationHandler = new EntityInvocationHandler(entity);
		return (I) Proxy.newProxyInstance(immutableClass.getClassLoader(), new Class[] {immutableClass}, invocationHandler);
	}

	class ValuesInvocationHandler<T extends Immutable> implements InvocationHandler {
		private final ValueInitializer<T> valueInitializer;

		public ValuesInvocationHandler(final ValueInitializer<T> valueInitializer) {
			this.valueInitializer = valueInitializer;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			return valueInitializer.getValueFor(method.getName());
		}
	}

	class EntityInvocationHandler<E> implements InvocationHandler {
		private final E entity;

		public EntityInvocationHandler(final E entity) {
			this.entity = entity;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			return method.invoke(entity, args);
		}
	}
}
