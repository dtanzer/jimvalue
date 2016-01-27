package net.davidtanzer.jimvalue;

import net.davidtanzer.jimvalue.immutable.Immutable;

public class ImmutableTest {
	interface UserName extends SingleValue.StringValue {}
	interface Password extends SingleValue.StringValue {}

	interface User extends Immutable {
		UserName getUserName();
		Password getPassword();

		static User createUser(final UserName userName, final Password password) {
			return Immutable.create(User.class, (val, prop) -> {
				val.set(prop.getUserName()).to(userName);
				val.set(prop.getPassword()).to(password);
			});
		}
	}

	public static void main(String[] args) {
		User user = User.createUser(
				SingleValue.create(UserName.class, "dtanzer"),
				SingleValue.create(Password.class, "supersecret"));

		System.out.println(user.getUserName());
		System.out.println(user.getPassword());
	}
}
