package net.davidtanzer.jimvalue;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public interface SingleValue<BaseType> {
	interface LongValue extends SingleValue<Long> {}
	interface IntValue extends SingleValue<Integer> {}
	interface ShortValue extends SingleValue<Short> {}
	interface CharValue extends SingleValue<Character> {}
	interface ByteValue extends SingleValue<Byte> {}
	interface BooleanValue extends SingleValue<Boolean> {}

	interface StringValue extends SingleValue<String> {}

	interface DateValue extends SingleValue<Date> {}
	interface InstantValue extends SingleValue<Instant> {}
	interface DurationValue extends SingleValue<Duration> {}

	/**
	 *
	 * @return The raw value associated with this single value.
	 * @deprecated WARNING This method might return null. Do not use it directly - use net.davidtanzer.jimvalue.SingleValue#withValue(net.davidtanzer.jimvalue.ValueCallback)!
	 */
	BaseType getValue();

	void withValue(ValueCallback callback);

	static <B, T extends SingleValue<B>> T create(Class<T> valueClass, B value) {
		final InvocationHandler invocationHandler = new ValueInvocationHandler(value);
		return (T) Proxy.newProxyInstance(valueClass.getClassLoader(), new Class[] {valueClass}, invocationHandler);
	}

	class ValueInvocationHandler<B> implements InvocationHandler {
		private final B value;

		public ValueInvocationHandler(final B value) {
			this.value = value;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			if(method.getName().equals("getValue")) {
				return value;
			} else if(method.getName().equals("equals")) {
				return areEqual(value, args[0]);
			} else if(method.getName().equals("hashCode")) {
				return getHashCode(value);
			} else if(method.getName().equals("toString")) {
				return String.valueOf(value);
			} else if(method.getName().equals("withValue")) {
				if(value != null) {
					((ValueCallback)args[0]).run(value);
				}

			}
			return null;
		}

		private int getHashCode(final B value) {
			if(value != null) {
				return value.hashCode();
			}
			return 0;
		}

		private boolean areEqual(final B value, final Object other) {
			if(value == null) {
				if(other instanceof SingleValue) {
					return ((SingleValue) other).getValue() == null;
				} else {
					return other == null;
				}
			} else {
				if(other instanceof SingleValue) {
					return value.equals(((SingleValue) other).getValue());
				} else {
					return value.equals(other);
				}
			}
		}
	}
}
