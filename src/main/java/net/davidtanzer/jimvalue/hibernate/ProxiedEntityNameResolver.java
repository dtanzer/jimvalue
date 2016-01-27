package net.davidtanzer.jimvalue.hibernate;

import javassist.util.proxy.ProxyFactory;
import org.hibernate.EntityNameResolver;

public class ProxiedEntityNameResolver implements EntityNameResolver {
	@Override
	public String resolveEntityName(final Object entity) {
		if(ProxyFactory.isProxyClass(entity.getClass())) {
			return entity.getClass().getInterfaces()[0].getName();
		}
		return null;
	}
}
