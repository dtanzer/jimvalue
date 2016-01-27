package net.davidtanzer.jimvalue.hibernate;

import org.hibernate.EntityMode;
import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.hibernate.service.ServiceRegistry;

import java.util.Map;

public class ProxiedEntityManagerFactoryBuilderImpl extends EntityManagerFactoryBuilderImpl {
	public ProxiedEntityManagerFactoryBuilderImpl(final PersistenceUnitInfoDescriptor persistenceUnitInfoDescriptor, final Map properties) {
		super(persistenceUnitInfoDescriptor, properties);
	}

	@Override
	public Configuration buildHibernateConfiguration(final ServiceRegistry serviceRegistry) {
		Configuration hibernateConfiguration = super.buildHibernateConfiguration(serviceRegistry);
		hibernateConfiguration.getEntityTuplizerFactory().registerDefaultTuplizerClass(EntityMode.POJO, ProxiedEntityTuplizer.class);
		return hibernateConfiguration;
	}
}
