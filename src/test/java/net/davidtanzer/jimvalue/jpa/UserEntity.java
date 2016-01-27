package net.davidtanzer.jimvalue.jpa;

import net.davidtanzer.jimvalue.StringValueTest;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public abstract class UserEntity implements User {
	@Embeddable
	public static class EmbeddedUserName extends EmbeddedSingleValue<String> implements User.UserName, Serializable {}
	@Embeddable
	public static class EmbeddedPassword extends EmbeddedSingleValue<String> implements User.Password {}

	@Override
	@EmbeddedId
	public abstract EmbeddedUserName getUserName();
	public abstract void setUserName(EmbeddedUserName userName);

	@Override
	@Embedded
	@AttributeOverrides(@AttributeOverride(name="value", column = @Column(name="password")))
	public abstract EmbeddedPassword getPassword();
	public abstract void setPassword(final EmbeddedPassword password);
}
