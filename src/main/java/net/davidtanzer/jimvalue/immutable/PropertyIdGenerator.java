package net.davidtanzer.jimvalue.immutable;

import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

class PropertyIdGenerator<T extends Immutable> {
	private final Class<T> type;
	private final Map<Object, String> propertyNames = new HashMap<>();

	PropertyIdGenerator(final Class<T> type) {
		this.type = type;
	}

	T propertyIds() {
		final InvocationHandler invocationHandler = new PropertyIdInvocationHandler();
		return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[] {type}, invocationHandler);
	}

	<U> String getPropertyName(final U propertyId) {
		return propertyNames.get(propertyId);
	}

	private class PropertyIdInvocationHandler implements InvocationHandler {
		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			Object propertyId;

			Class<?> type = method.getReturnType();
			if(type.isInterface()) {
				propertyId = Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, (proxy1, method1, args1) -> {
					if(method1.getName().equals("hashCode")) {
						return System.identityHashCode(proxy1);
					}
					return null;
				});
			} else {
				final ProxyFactory factory = new ProxyFactory();
				factory.setSuperclass(type);

				propertyId = factory.create(new Class[0], new Object[0]);
			}

			propertyNames.put(propertyId, method.getName());
			return propertyId;
		}
	}
}
