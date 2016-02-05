package net.davidtanzer.jimvalue;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

@Embeddable
@MappedSuperclass
public interface MutableSingleValue<T> extends SingleValue<T> {
	void setValue(T value);
}
