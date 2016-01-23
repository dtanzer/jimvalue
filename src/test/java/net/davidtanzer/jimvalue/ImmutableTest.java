package net.davidtanzer.jimvalue;

import net.davidtanzer.jimvalue.immutable.Immutable;

public class ImmutableTest {
	interface UserName extends SingleValue.StringValue {}
	interface Password extends SingleValue.StringValue {}

	interface User extends Immutable {
		UserName getUserName();
		Password getPassword();
	}

	public static void main(String[] args) {
		User user = Immutable.create(User.class, (val, prop) -> {
			val.set(prop.getUserName()).to(SingleValue.create(UserName.class, "dtanzer"));
			val.set(prop.getPassword()).to(SingleValue.create(Password.class, "supersecret"));
		});

		System.out.println(user.getUserName());
		System.out.println(user.getPassword());
	}
}
