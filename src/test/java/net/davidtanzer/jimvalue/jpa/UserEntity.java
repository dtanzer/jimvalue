package net.davidtanzer.jimvalue.jpa;

import net.davidtanzer.jimvalue.StringValueTest;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public interface UserEntity extends User {
	@Embeddable
	class EmbeddedUserName extends EmbeddedSingleValue<String> implements User.UserName, Serializable {}
	@Embeddable
	class EmbeddedPassword extends EmbeddedSingleValue<String> implements User.Password {}

	@Override
	@EmbeddedId
	@AttributeOverrides(@AttributeOverride(name="value", column = @Column(name="username")))
	EmbeddedUserName getUserName();
	void setUserName(EmbeddedUserName userName);

	@Override
	@Embedded
	@AttributeOverrides(@AttributeOverride(name="value", column = @Column(name="password")))
	EmbeddedPassword getPassword();
	void setPassword(EmbeddedPassword password);
}
