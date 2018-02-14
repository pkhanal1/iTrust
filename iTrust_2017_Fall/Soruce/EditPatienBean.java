/**
 * 
 */
package edu.ncsu.csc.itrust.model.patientPicture;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.model.patientPicture.PatientPictureMySQL;

/**
 * @author pkhanal
 *
 */
@WebServlet("/images/*")
public class EditPatienBean extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mid = request.getPathInfo().substring(1); 
		System.out.println(mid);
		long MID = 0;
		if ((mid != null) && (ValidationFormat.NPMID.getRegex().matcher(mid).matches())) {
			MID = Long.parseLong(mid);
			PatientPictureMySQL database;
			InputStream pic = null;
			try {
				database = new PatientPictureMySQL();
				
				if((pic = database.getPatientPicture(MID).getPatientPicture()) != null){
					byte[] content = IOUtils.toByteArray(pic);
					response.setContentType(getServletContext().getMimeType("image.png"));
	                response.setContentLength(content.length);
	                response.getOutputStream().write(content);
				} 
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
