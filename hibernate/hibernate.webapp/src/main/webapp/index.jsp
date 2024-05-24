<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<jsp:useBean id="eventManager" class="events.EventManager" scope="request"/>

<html>
<head>
	<link rel="stylesheet" type="text/css" href="style.css"/>
</head>

<body>
	<h3>Events</h3>
	<div id="event.add">
	<h4>Add Event</h4>
	<form action="addEvent" method="post">
		Title: <input name="fTitle" type="text">
		<input type="submit" value="Submit">
	</form>
	</div>
	
	<div id="event.list">
	<h4>Events</h4>
	<table class="listing">
		<tr>
			<th>ID</th>
			<th>Date</th>
			<th>Title</th>
			<th>Participants</th>
		</tr>
		<c:forEach var="event" items="${eventManager.events}">
		<tr>
			<td>${event.id}</td>
			<td><fmt:formatDate value="${event.date}" dateStyle="medium"/></td>
			<td>${event.title}</td>
			<td>
				[
				<c:forEach var="person" items="${event.registrants}">
					${person.id}, 
				</c:forEach>
				]
			</td>
		</tr>
		</c:forEach>
	</table>
	</div>
	
	<h3>People</h3>
	<div id="person.add">
	<h4>Add Person</h4>
	<form action="addPerson" method="post">
		<table>
			<tr>
				<td>First Name:</td>
				<td><input name="fFirstName" type="text"></td>
			</tr>
			<tr>
				<td>Last Name:</td>
				<td><input name="fLastName" type="text"></td>
			</tr>
			<tr>
				<td>Age:</td>
				<td><input name="fAge" type="text"></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit"></td>
			</tr>
		</table>
	</form>
	</div>
	
	<div id="person.list">
	<h4>People</h4>
	<table class="listing">
		<tr>
			<th>ID</th>
			<th>Age</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Events</th>
		</tr>
		<c:forEach var="person" items="${eventManager.people}">
			<tr>
				<td>${person.id}</td>
				<td>${person.age}</td>
				<td>${person.firstName}</td>
				<td>${person.lastName}</td>
				<td>
					[
					<c:forEach var="event" items="${person.registeredEvents}">
						${event.id}, 
					</c:forEach>
					]
				</td>
				<td>${i}</td>
			</tr>
		</c:forEach>
	</table>
	</div>
	
	<div id="register">
	<h4>Register for Event</h4>
	<form action="register" method="post">
		<table>
			<tr>
				<td>Person:</td>
				<td>
					<select name="fPersonId">
					<c:forEach var="person" items="${eventManager.people}">
						<option value="${person.id}">${person.firstName} ${person.lastName}</option>
					</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>Event:</td>
				<td>
					<select name="fEventId">
					<c:forEach var="event" items="${eventManager.events}">
						<option value="${event.id}">${event.title}</option>
					</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit"></td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>
