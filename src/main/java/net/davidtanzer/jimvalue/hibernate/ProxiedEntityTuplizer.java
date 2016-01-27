package net.davidtanzer.jimvalue.hibernate;

import org.hibernate.EntityMode;
import org.hibernate.EntityNameResolver;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.metamodel.binding.AttributeBinding;
import org.hibernate.metamodel.binding.EntityBinding;
import org.hibernate.property.Getter;
import org.hibernate.property.Setter;
import org.hibernate.proxy.ProxyFactory;
import org.hibernate.tuple.Instantiator;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.tuple.entity.PojoEntityTuplizer;

import java.util.Map;

public class ProxiedEntityTuplizer extends PojoEntityTuplizer {
	private final ProxiedEntityNameResolver entityNameResolver = new ProxiedEntityNameResolver();
	private final EntityNameResolver[] entityNameResolvers = new EntityNameResolver[]{entityNameResolver};

	public ProxiedEntityTuplizer(EntityMetamodel entityMetamodel, PersistentClass mappedEntity) {
		super(entityMetamodel, mappedEntity);
	}

	public ProxiedEntityTuplizer(final EntityMetamodel entityMetamodel, final EntityBinding mappedEntity) {
		super(entityMetamodel, mappedEntity);
	}

	@Override
	public EntityNameResolver[] getEntityNameResolvers() {
		return new EntityNameResolver[] { entityNameResolver };
	}

	@Override
	protected Instantiator buildInstantiator(final PersistentClass persistentClass) {
		return new ProxiedEntityInstantiator(persistentClass);
	}

	@Override
	public String determineConcreteSubclassEntityName(Object entityInstance, SessionFactoryImplementor factory) {
		String entityName = entityNameResolver.resolveEntityName(entityInstance);
		if ( entityName == null ) {
			entityName = super.determineConcreteSubclassEntityName( entityInstance, factory );
		}
		return entityName;
	}

	@Override
	protected ProxyFactory buildProxyFactory(final PersistentClass persistentClass, final Getter idGetter, final Setter idSetter) {
		return super.buildProxyFactory(persistentClass, idGetter, idSetter);
	}

	@Override
	protected ProxyFactory buildProxyFactoryInternal(final PersistentClass persistentClass, final Getter idGetter, final Setter idSetter) {
		return super.buildProxyFactoryInternal(persistentClass, idGetter, idSetter);
	}

	@Override
	protected ProxyFactory buildProxyFactory(final EntityBinding entityBinding, final Getter idGetter, final Setter idSetter) {
		return super.buildProxyFactory(entityBinding, idGetter, idSetter);
	}

	@Override
	protected ProxyFactory buildProxyFactoryInternal(final EntityBinding entityBinding, final Getter idGetter, final Setter idSetter) {
		return super.buildProxyFactoryInternal(entityBinding, idGetter, idSetter);
	}

	@Override
	protected Instantiator buildInstantiator(final EntityBinding entityBinding) {
		return super.buildInstantiator(entityBinding);
	}

	@Override
	public void setPropertyValues(final Object entity, final Object[] values) {
		super.setPropertyValues(entity, values);
	}

	@Override
	public Object[] getPropertyValues(final Object entity) {
		return super.getPropertyValues(entity);
	}

	@Override
	public Object[] getPropertyValuesToInsert(final Object entity, final Map mergeMap, final SessionImplementor session) throws HibernateException {
		return super.getPropertyValuesToInsert(entity, mergeMap, session);
	}

	@Override
	protected void setPropertyValuesWithOptimizer(final Object object, final Object[] values) {
		super.setPropertyValuesWithOptimizer(object, values);
	}

	@Override
	protected Object[] getPropertyValuesWithOptimizer(final Object object) {
		return super.getPropertyValuesWithOptimizer(object);
	}

	@Override
	public EntityMode getEntityMode() {
		return super.getEntityMode();
	}

	@Override
	public Class getMappedClass() {
		return super.getMappedClass();
	}

	@Override
	public boolean isLifecycleImplementor() {
		return super.isLifecycleImplementor();
	}

	@Override
	protected Getter buildPropertyGetter(final Property mappedProperty, final PersistentClass mappedEntity) {
		return super.buildPropertyGetter(mappedProperty, mappedEntity);
	}

	@Override
	protected Setter buildPropertySetter(final Property mappedProperty, final PersistentClass mappedEntity) {
		return super.buildPropertySetter(mappedProperty, mappedEntity);
	}

	@Override
	protected Getter buildPropertyGetter(final AttributeBinding mappedProperty) {
		return super.buildPropertyGetter(mappedProperty);
	}

	@Override
	protected Setter buildPropertySetter(final AttributeBinding mappedProperty) {
		return super.buildPropertySetter(mappedProperty);
	}

	@Override
	public Class getConcreteProxyClass() {
		return super.getConcreteProxyClass();
	}

	@Override
	public void afterInitialize(final Object entity, final boolean lazyPropertiesAreUnfetched, final SessionImplementor session) {
		super.afterInitialize(entity, lazyPropertiesAreUnfetched, session);
	}

	@Override
	public boolean hasUninitializedLazyProperties(final Object entity) {
		return super.hasUninitializedLazyProperties(entity);
	}

	@Override
	public boolean isInstrumented() {
		return super.isInstrumented();
	}
}