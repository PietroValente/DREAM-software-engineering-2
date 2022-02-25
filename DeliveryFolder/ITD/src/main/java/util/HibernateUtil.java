package util;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

//Hibernate settings equivalent to hibernate.cfg.xml's properties
public class HibernateUtil {

	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			Configuration configuration = null;
			ServiceRegistry serviceRegistry = null;
			try {
				configuration = new Configuration();

				Properties settings = new Properties();
				settings.put(Environment.DRIVER, "org.postgresql.Driver");
				settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/dream");
				settings.put(Environment.USER, VARIABLES.databaseUser);
				settings.put(Environment.PASS, VARIABLES.databasePassword);
				settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

				settings.put(Environment.SHOW_SQL, "false");

				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

				settings.put(Environment.HBM2DDL_AUTO, "update");

				configuration.setProperties(settings);

				configuration.addAnnotatedClass(entities.Code.class);
				configuration.addAnnotatedClass(entities.Person.class);
				configuration.addAnnotatedClass(entities.SessionID.class);
				configuration.addAnnotatedClass(entities.Farm.class);
				configuration.addAnnotatedClass(entities.Land.class);
				configuration.addAnnotatedClass(entities.Product.class);
				configuration.addAnnotatedClass(entities.Ranking.class);
				configuration.addAnnotatedClass(entities.Help_Request.class);
				configuration.addAnnotatedClass(entities.Report.class);
				configuration.addAnnotatedClass(entities.Discussion.class);
				configuration.addAnnotatedClass(entities.Comment.class);
				configuration.addAnnotatedClass(entities.Crop.class);

				serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
						.build();
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);

				return sessionFactory;

			} catch (Exception e) {
				serviceRegistry.close();
			}
		}
		return sessionFactory;
	}
}
