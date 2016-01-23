package net.davidtanzer.jimvalue.jpa;

import net.davidtanzer.jimvalue.SingleValue;
import net.davidtanzer.jimvalue.hibernate.ProxiedEntityNameResover;
import net.davidtanzer.jimvalue.immutable.Immutable;
import org.hibernate.EntityNameResolver;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ImmutableJpaTest.Configuration.class })
public class ImmutableJpaTest {
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Test
	public void canSaveEntityToTheDatabaseAndLoadItAgain() {
		final User user = Immutable.create(User.class, (val, prop) -> {
			val.set(prop.getUserName()).to(SingleValue.create(User.UserName.class, "dtanzer"));
			val.set(prop.getPassword()).to(SingleValue.create(User.Password.class, "supersecret"));
		});

		final UserEntity userEntity = ImmutableEntityMapper.mapImmutableToEntity(user, UserEntity.class);

		final EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(userEntity);
		em.getTransaction().commit();
	}

	public static class Configuration {
		@Bean
		public DataSource datasource() {
			DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:h2:mem:unittest;DB_CLOSE_DELAY=-1", "sa", "");
			dataSource.setDriverClassName("org.h2.Driver");
			return dataSource;
		}

		@Bean
		public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
			LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
			em.setDataSource(datasource());
			em.setPackagesToScan(new String[] { "net.davidtanzer.jimvalue" });

			JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
			em.setJpaVendorAdapter(vendorAdapter);
			em.setJpaProperties(additionalProperties());

			return em;
		}

		@Bean
		public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
			JpaTransactionManager transactionManager = new JpaTransactionManager();
			transactionManager.setEntityManagerFactory(emf);

			return transactionManager;
		}

		@Bean
		public EntityNameResolver entityNameResolver(final EntityManagerFactory entityManagerFactory) {
			final EntityNameResolver resolver = new ProxiedEntityNameResover();

			final SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
			sessionFactory.registerEntityNameResolver(resolver);

			return resolver;
		}

		Properties additionalProperties() {
			Properties properties = new Properties();
			properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
			properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
			return properties;
		}
	}
}
