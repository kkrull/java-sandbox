package events;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/*
 * Created on May 20, 2009
 */

/**
 * An event, in which multiple people may participate.
 * @author kkrull
 */
@Entity
public final class Event {
	
	/* Properties (non-final so Hibernate can modify directly) */
	
	private @Id @GeneratedValue @Column(name="EVENT_ID") Long id; //Nullable
	private @Column(name="TITLE") String title;
	private @Column(name="DATE") Date date;
	
	@ManyToMany(mappedBy="events")
	private Set<Person> participants; //Nonnull
	
	public Event() {
		
		//Required for reflection-based instantiation used by Hibernate
	}
	
	/**
	 * @param title Non-empty name of the event
	 * @param date Nonnull date at which the event takes place
	 * @param participants Nonnull people participating in this event
	 */
	public Event(String title, Date date, Set<Person> participants) {
		
		this.title = title;
		this.date = new Date(date.getTime());
		this.participants = new HashSet<Person> (participants);
	}
	
	/* Query methods */
	
	/** @return Nullable ID used for persistence */
	public Long getId() {
		
		return this.id;
	}
	
	public String getTitle() {
		
		return this.title;
	}
	
	public Date getDate() {
		
		return new Date(this.date.getTime());
	}
	
	/** @return Nonnull people associated with this event, if any. */
	public Set<Person> getRegistrants() { //Note: Avoids JavaBean convention
		
		//TODO Change this back to participants since Hibernate uses field access
		return new HashSet<Person> (this.participants);
	}
	
	/* Update methods */
	
	public void addParticipant(Person person) {
		
		this.participants.add(person);
	}
	
	/* Object methods */
	
	@Override
	public String toString() {
		
		final StringBuffer buffer = new StringBuffer("id=");
		buffer.append(this.id);
		buffer.append(", title=");
		buffer.append(this.title);
		buffer.append(", date=");
		buffer.append(this.date);
		buffer.append(", participants=[");
		for(Person person : this.participants) {
			
			buffer.append(person.getId());
			buffer.append(", ");
		}
		
		//Delete ', ' after the last element if there were any
		if(!this.participants.isEmpty()) {
			
			final int length = buffer.length();
			buffer.delete(length - 2, length);
		}
		buffer.append("]");
		return buffer.toString();
	}
}
