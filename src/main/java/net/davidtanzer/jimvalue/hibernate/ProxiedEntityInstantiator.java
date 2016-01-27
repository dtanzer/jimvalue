package net.davidtanzer.jimvalue.hibernate;

import org.hibernate.mapping.PersistentClass;
import org.hibernate.tuple.Instantiator;

import java.io.Serializable;

public class ProxiedEntityInstantiator implements Instantiator {
	private final PersistentClass persistentClass;

	public ProxiedEntityInstantiator(final PersistentClass persistentClass) {
		this.persistentClass = persistentClass;
	}

	@Override
	public Object instantiate(final Serializable id) {
		return null;
	}

	@Override
	public Object instantiate() {
		return null;
	}

	@Override
	public boolean isInstance(final Object object) {
		return persistentClass.getMappedClass().isAssignableFrom(object.getClass());
	}
}
