package net.davidtanzer.jimvalue.immutable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public interface Immutable {
	static <T extends Immutable> T create(Class<T> type, ImmutableInitializer<T> initializer) {
		//TODO check the stack trace whether this method was called directly by a static method of type
		PropertyIdGenerator<T> propertyIdGenerator = new PropertyIdGenerator(type);
		final ValueInitializer<T> valueInitializer = new ValueInitializer<>(propertyIdGenerator);

		initializer.init(valueInitializer, propertyIdGenerator.propertyIds());

		final InvocationHandler invocationHandler = new ValuesInvocationHandler(valueInitializer);
		return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[] {type}, invocationHandler);
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
}
