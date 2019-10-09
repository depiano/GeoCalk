/**
 * Author: Antonio De Piano
 * Web site: http://www.depiano.it
 * email: depianoantonio@gmail.com
 */

package it.unisa.geocalk.control;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/WriteFile")
public class WriteFile extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private JSONObject obj;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String json = request.getParameter("elements");
		
		System.out.println("Mi arriva [doPOST] : "+json+"\n");
		this.obj = new JSONObject(json);
		System.out.println(obj.toString());
		
		if(((String)obj.get("action")).equals("write"))
		{
			 String modelXMI = "/Users/depiano/Desktop/WorkspaceEE/GeoCalk/WebContent/temp/citiBikeNEW.xmi";
			
			 FileWriter writerModelXMI = new FileWriter(modelXMI);
			 BufferedWriter bwModelXMI= new BufferedWriter(writerModelXMI);
			 bwModelXMI.write((String)obj.get("text"));
			 System.out.println("Generated file citiBikeNEW.xmi\nFinished!");
			 bwModelXMI.close();
	       
		}
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	

}