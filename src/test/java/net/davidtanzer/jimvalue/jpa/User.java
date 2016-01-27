package net.davidtanzer.jimvalue.jpa;

import net.davidtanzer.jimvalue.SingleValue;
import net.davidtanzer.jimvalue.immutable.Immutable;

import javax.persistence.*;

public interface User extends Immutable {
	interface UserName extends SingleValue.StringValue {}
	interface Password extends SingleValue.StringValue {}

	UserName getUserName();
	Password getPassword();

	static User createUser(final UserName userName, final Password password) {
		return Immutable.create(User.class, (val, prop) -> {
			val.set(prop.getUserName()).to(userName);
			val.set(prop.getPassword()).to(password);
		});
	}

}
