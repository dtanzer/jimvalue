package net.davidtanzer.jimvalue;

public class StringValueTest {
	public interface UserName extends SingleValue.StringValue {}

	public static void main(String[] args) {
		UserName userName = SingleValue.create(UserName.class, "dtanzer");
		UserName userName2 = SingleValue.create(UserName.class, "dtanzer");

		System.out.println(userName.getValue());
		System.out.println(userName.equals(userName2));
		System.out.println(userName.equals("dtanzer"));
		System.out.println(userName.hashCode() + "==" + "dtanzer".hashCode());
		System.out.println(userName);

		userName.withValue(v -> System.out.println("With value: "+v));
	}
}
