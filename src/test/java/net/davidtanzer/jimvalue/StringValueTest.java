package net.davidtanzer.jimvalue;

import java.lang.reflect.InvocationTargetException;

public class StringValueTest {
	public interface UserName extends SingleValue.StringValue {}

	public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		UserName userName = SingleValue.create(UserName.class, "dtanzer");
		UserName userName2 = SingleValue.create(UserName.class, "dtanzer");

		System.out.println(userName.getValue());
		System.out.println(userName.equals(userName2));
		System.out.println(userName.equals("dtanzer"));
		System.out.println(userName.hashCode() + "==" + "dtanzer".hashCode());
		System.out.println(userName);

		userName.withValue(v -> System.out.println("With value: "+v));

		UserName embeddedUserName = SingleValue.mutable(UserName.class);
		SingleValue.set(embeddedUserName, SingleValue.create(UserName.class, "foobar"));
		System.out.println(embeddedUserName.getValue());
	}
}
