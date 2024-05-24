package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import events.Event;
import events.Person;

/*
 * Created on May 21, 2009
 */

/**
 * Provides global access to the Hibernate {@link SessionFactory}.
 * @author kkrull
 */
public final class HibernateUtil {
	
	/** Singleton shared by all threads */
	private static SessionFactory _factory;
	
	private HibernateUtil() {
		
		//No instances for util class
	}
	
	public static SessionFactory getSessionFactory() {
		
		//Lazy-initialize the factory
		if(_factory == null) {
			
			final AnnotationConfiguration config = new AnnotationConfiguration()
				.addAnnotatedClass(Event.class)
				.addAnnotatedClass(Person.class);
			_factory = config.configure().buildSessionFactory();
		}
		
		return _factory;
	}
}
