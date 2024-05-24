/* Copyright Notice
 * 
 * All trademarks mentioned herein belong to their respective owners. Unless identified 
 * with the designation "COPY FREE", the contents of this document is copyrighted by Simulex INC. 
 * Simulex INC hereby authorizes you to copy this document
 * for non-commercial use within your organization only. In consideration of this 
 * authorization, you agree that any copy of these documents you make shall retain all 
 * copyright and other proprietary notices contained herein. You may not otherwise 
 * copy or transmit the contents of this code either electronically or in hard copies. 
 * You may not alter the content of this document in any manner. If you are interested 
 * in using the contents of this document in any manner except as described above,
 * please contact Simulex INC at for information on licensing. 
 *
 * Individual documents published by Simulex INC may contain other 
 * proprietary notices and copyright information specific to that individual document. 
 * Nothing contained herein shall be construed as conferring by implication, estoppel 
 * or otherwise any license or right under any patent, trademark or other property right 
 * of Simulex INC or any third party. Except as expressly provided above nothing contained 
 * herein shall be construed as conferring any license or right under any copyright or 
 * other property right of Simulex INC or any third party. Note that any product, process, 
 * or technology in this document may be the subject of other intellectual property 
 * rights reserved by Simulex INC and may not be licensed here under. 
 */

package webapp.add;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import events.EventManager;
import events.Person;

/*
 * Created on Jun 11, 2009
 */

/**
 * Adds a person to the database.
 * @author kkrull
 */
public final class AddPerson extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		//Parse request parameters, each of which should have exactly 1 non-empty value
		final String firstName = request.getParameter("fFirstName");
		final String lastName = request.getParameter("fLastName");
		final String ageStr = request.getParameter("fAge");
		final int age = Integer.parseInt(ageStr);
		
		//Create and store the person then return to the original page
		final EventManager manager = new EventManager();
		final Person person = manager.createPerson(age, firstName, lastName);
		response.getWriter().printf("Adding Person: firstName=%s, lastName=%s, age=%d\n",
			person.getFirstName(), person.getLastName(), person.getAge());
	}
}
