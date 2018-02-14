/**
 * 
 */
package edu.ncsu.csc.itrust.model.obstetricsOfficeVisit;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

/**
 * @author pkhanal
 *
 */
@ManagedBean
@SessionScoped
public class ViewEditUltrasoundRecord extends HttpServlet  {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9168038514393558810L;
	private UltrasoundRecordMySQL database;
	private SessionUtils midFromWeb;
	private UltrasoundRecord record;
	
	public ViewEditUltrasoundRecord(){
		midFromWeb = SessionUtils.getInstance();
		try{
			database = new UltrasoundRecordMySQL();
		} catch (DBException e1) {
			e1.printStackTrace();
		}
	}
	
	public List<UltrasoundRecord> getUltrasoundRecordListForPage(String MID, String date){
		//String date = "2017-03-29";
		//System.out.println(MID + "      " + date);
		//System.out.println(midFromWeb.getCurrentOfficeVisitId());
		List<UltrasoundRecord> d = Collections.emptyList();
		long mid = -1;
		if ((MID != null) && ValidationFormat.NPMID.getRegex().matcher(MID).matches()) {
			mid = Long.parseLong(MID);
			try {
				d = database.getUltrasoundRecordList(mid, date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return d;
		
	}
	
	
    
    public StreamedContent FileDownloadView(String MID, String date, String childNum) {        
    	StreamedContent file = null;
    	InputStream stream;
        try{
        	UltrasoundRecord rec = database.getUltrasoundRecord(Long.parseLong(MID), date, Integer.parseInt(childNum));
			stream = rec.getUltraImage();
			if(rec.getFiletype().endsWith("jpg")){
				file = new DefaultStreamedContent(stream,"image/jpg", "downloaded.jpg");
			} else if(rec.getFiletype().endsWith("png")){
				file = new DefaultStreamedContent(stream, "image/png", "downloaded.png");
			}else if(rec.getFiletype().endsWith("pdf")){
				file = new DefaultStreamedContent(stream, "pdf", "downloaded.pdf");
			} else {
				file = new DefaultStreamedContent(stream, "pdf", "downloaded.pdf");
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
		return file;
    }
}
