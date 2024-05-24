package events;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.hibernate.FetchMode;
import org.hibernate.Session;

import util.HibernateUtil;

/*
 * Created on May 21, 2009
 */

/**
 * Accesses all data about events and the people who are associated with them.
 * @author kkrull
 */
public final class EventManager {
	
	public static void main(String[] args) {
		
		/* Define command-line options */
		
		//Define each possible option to use; all required
		final Option listOption = new Option("l", "list", false, "List all available data");
		final Option makeEventOption = new Option("e", "event", true, "Make a new event <title>");
		final OptionGroup commandGroup = new OptionGroup();
		commandGroup.addOption(listOption);
		commandGroup.addOption(makeEventOption);
		
		//Define options to specify on the command-line
		final Options options = new Options();
		options.addOptionGroup(commandGroup);
		commandGroup.setRequired(true);
		
		/* Parse command-line arguments */
		
		//Parse command-line options
		final CommandLineParser parser = new PosixParser();
		try {
			
			parser.parse(options, args);
		}
		catch(Exception e) {
			
			//Print usage and exit
			new HelpFormatter().printHelp(EventManager.class.getName(), options);
			System.exit(1);
		}
		
		/* Process commands */
		
		final EventManager manager = new EventManager();
		final Option selectedCommand = options.getOption(commandGroup.getSelected());
		if(selectedCommand.getOpt() == listOption.getOpt()) {
			
			final List<Event> events = manager.getEvents();
			System.out.println("Events:");
			for(Event event : events) {
				
				System.out.println(event);
			}
			
			final List<Person> people = manager.getPeople();
			System.out.println("\nPeople:");
			for(Person person : people) {
				
				System.out.println(person);
			}
			
		} else if(selectedCommand.getOpt() == makeEventOption.getOpt()) {
			
			final String title = makeEventOption.getValue();
			final Event event = manager.createEvent(title, new Date());
			System.out.println("Added event: " + event);
		}
	}
	
	/* Query methods */
	
	/** @return Nonnull events that exist, if any */
	public List<Event> getEvents() {
		
		//Apparently, even queries need to be done under a transaction
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		final List events = session.createCriteria(Event.class)
			.setFetchMode("participants", FetchMode.JOIN).list();
		session.getTransaction().commit();
		
		return events;
	}
	
	/** @return Nonnull people that exist, if any */
	public List<Person> getPeople() {
		
		//Apparently, even queries need to be done under a transaction
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		final List people = session.createCriteria(Person.class).list();
		session.getTransaction().commit();
		
		return people;
	}
	
	/* Data update methods */
	
	/**
	 * Creates and stores an Event for which no people are yet registered.
	 * @param title Nonnull; see {@link Event}
	 * @param date Nonnull; see {@link Event}
	 * @return Nonnull event that was created
	 */
	public Event createEvent(String title, Date date) {
		
		//Start a transaction under the thread's current session
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		//Create/persist the event and commit the transaction so changes take effect
		final Event event = new Event(title, date, new HashSet<Person> ());
		session.save(event);
		session.getTransaction().commit();
		return event;
	}
	
	/**
	 * Creates and stores a person who is not registered for any events.
	 * @param age see {@link Person}
	 * @param firstName Nonnull; see {@link Person}
	 * @param lastName Nonnull; see {@link Person}
	 * @return Nonnull person that was created
	 */
	public Person createPerson(int age, String firstName, String lastName) {
		
		//Start a transaction under the thread's current session
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		//Create/persist the person and commit the transaction so changes take effect
		final Person person = new Person(age, firstName, lastName, new HashSet<Event> ());
		session.save(person);
		session.getTransaction().commit();
		return person;
	}
	
	/**
	 * Associates the specified person with the specified event, both of which must already exist.
	 * @param personId ID of an existing person
	 * @param eventId ID of an existing event
	 */
	public void addPersonToEvent(long personId, long eventId) {
		
		//Start a transaction under the thread's current session
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		//Load the person/event of interest, adding the event the others the person is with
		final Person person = (Person) session.load(Person.class, personId);
		final Event event = (Event) session.load(Event.class, eventId);
		person.addEvent(event);
		event.addParticipant(person);
		session.getTransaction().commit();
	}
}
