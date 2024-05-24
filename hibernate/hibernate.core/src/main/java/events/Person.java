package events;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/*
 * Created on May 21, 2009
 */

/**
 * A person, which may be registered in multiple events.
 * @author kkrull
 */
@Entity
public final class Person {
	
	/* Properties (non-final so Hibernate can modify directly) */
	private @Id @GeneratedValue @Column(name="PERSON_ID") Long id;
	private @Column(name="AGE") int age;
	private @Column(name="FIRST_NAME") String firstName;
	private @Column(name="LAST_NAME") String lastName;
	private @ManyToMany Set<Event> events; //Nonnull
	
	public Person() {
		
		//Required for reflection-based instantiation used by Hibernate
	}
	
	/**
	 * @param age Age
	 * @param firstName First name
	 * @param lastName Last name
	 * @param Nonnull events in which this person is involved
	 */
	public Person(int age, String firstName, String lastName, Set<Event> events) {
		
		this.age = age;
		this.firstName = firstName;
		this.lastName = lastName;
		this.events = new HashSet<Event> (events);
	}
	
	/* Query methods */
	
	/** @return Nullable ID used for persistence */
	public Long getId() {
		
		return this.id;
	}
	
	public int getAge() {
		
		return this.age;
	}
	
	public String getFirstName() {
		
		return this.firstName;
	}
	
	public String getLastName() {
		
		return this.lastName;
	}
	
	public Set<Event> getRegisteredEvents() { //Note: Avoids JavaBean convention
		
		//TODO Change this back to events since Hibernate uses field access
		return new HashSet<Event> (this.events);
	}
	
	/* Update methods */
	
	public void addEvent(Event event) {
		
		this.events.add(event);
	}
	
	/* Object methods */
	
	@Override
	public String toString() {
		
		final StringBuffer buffer = new StringBuffer("id=");
		buffer.append(this.id);
		buffer.append(", age=");
		buffer.append(this.age);
		buffer.append(", firstName=");
		buffer.append(this.firstName);
		buffer.append(", lastName=");
		buffer.append(this.lastName);
		buffer.append(", events=[");
		for(Event event : this.events) {
			
			buffer.append(event.getId());
			buffer.append(", ");
		}
		
		//Delete ', ' after the last element if there were any
		if(!this.events.isEmpty()) {
			
			final int length = buffer.length();
			buffer.delete(length - 2, length);
		}
		buffer.append("]");
		return buffer.toString();
	}
}
