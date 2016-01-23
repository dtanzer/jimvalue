package net.davidtanzer.jimvalue.hibernate;

import javassist.util.proxy.ProxyFactory;
import org.hibernate.EntityNameResolver;

public class ProxiedEntityNameResover implements EntityNameResolver {
	@Override
	public String resolveEntityName(final Object entity) {
		if(ProxyFactory.isProxyClass(entity.getClass())) {
			return entity.getClass().getSuperclass().getName();
		}
		return null;
	}
}
