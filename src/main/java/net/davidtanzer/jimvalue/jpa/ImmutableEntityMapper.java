package net.davidtanzer.jimvalue.jpa;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;
import net.davidtanzer.jimvalue.SingleValue;
import net.davidtanzer.jimvalue.immutable.Immutable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ImmutableEntityMapper {
	public static <T extends Immutable, E extends T> E mapImmutableToEntity(T immutable, Class<E> entityClass) {
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setInterfaces(new Class[] {entityClass});


		final MethodHandler handler = new EntityMethodHandler(immutable);
		final Object proxy;

		try {

			proxy = proxyFactory.create(new Class[0], new Object[0], handler);
			//((Proxy)obj).setHandler(mh);
		} catch (Exception e) {
			throw new IllegalStateException("Could not create entity proxy for object "+immutable+", proxy class "+entityClass.getName(), e);
		}

		return (E) proxy;
	}

	public static <T> Class<? extends T> entityClass(final Class<T> entityClass) {
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setSuperclass(entityClass);

		return proxyFactory.createClass();
	}

	public static <I extends Immutable, E extends I> I mapEntityToImmutable(final E entity, final Class<I> immutableClass) {
		return Immutable.createFrom(entity, immutableClass);
	}

	public static <V, T extends SingleValue<V>, R extends EmbeddedSingleValue<V>> R mapSingleValue(final T value, final Class<R> toClass) {
		try {
			final R toVal = toClass.newInstance();
			toVal.setValue(value.getValue());
			return toVal;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalStateException("Could not create value of type "+toClass, e);
		}
	}

	private static class EntityMethodHandler implements MethodHandler {
		private final Immutable immutable;

		public <T extends Immutable> EntityMethodHandler(final T immutable) {
			this.immutable = immutable;
		}

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args) throws Throwable {
			try {
				final Method immutableMethod = immutable.getClass().getMethod(thisMethod.getName(), thisMethod.getParameterTypes());
				final SingleValue immutableValue = (SingleValue) immutableMethod.invoke(immutable, args);

				final EmbeddedSingleValue returnValue = (EmbeddedSingleValue) thisMethod.getReturnType().newInstance();
				returnValue.setValue(immutableValue.getValue());

				return returnValue;
			} catch (NoSuchMethodException e) {
				//Safely ignore, just proceed...
			}
			if(proceed != null) {
				return proceed.invoke(self, args);
			}
			return null;
		}
	}
}
