

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SortStrings
 */
//@WebServlet("/SortStrings")
@WebServlet( name = "/SortStrings", urlPatterns = {"/SortStrings"} )
public class SortStrings extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SortStrings() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html"); // Tells the web container what we're sending back
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.getRequestDispatcher("FinalExam/SortStrings.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html"); // Tells the web container what we're sending back
		String input = request.getParameter("inputString");
		String delim = request.getParameter("delim");
		//System.out.println("delim: "+delim);
		PrintRadioSeparator(request, delim);
		List<String> strList = getStringList(input, delim);
		
		String sortOrder = request.getParameter("sortOrder");
		if(sortOrder.equals("Ascending")) {
			Collections.sort(strList);
		}
		else {
			Collections.sort(strList, Collections.reverseOrder());
		}
		PrintRadioSortOrder(request, sortOrder); 
		strList = removeDuplicates(strList);
		String sortedString = getStringFromList(strList, delim);
		
		request.setAttribute("inputString", input);
		request.setAttribute("sortedString", sortedString);
		//doGet(request, response);
		//System.out.println(sortOrder);
		request.getRequestDispatcher("FinalExam/SortStrings.jsp").forward(request, response);
	}
	
	public List<String> getStringList(String input, String delim) {
		List<String> strList = new ArrayList<String>();
		switch(delim) {
			case "space": 
				strList = new ArrayList<String>(Arrays.asList(input.split("\\s+")));
				break;
			case "comma":
				strList = new ArrayList<String>(Arrays.asList(input.split(",")));
				break;
			case "newLine": 
				strList = new ArrayList<String>(Arrays.asList(input.split("\n")));
				break;
		}
		for(int i = 0; i < strList.size(); i++) {
			String str = strList.get(i).trim();
			strList.set(i, str);
	    }
		return strList;
	}
	
	public void PrintRadioSeparator(HttpServletRequest request, String delim) {
		String print = "";
		String check1 = " ";
		String check2 = " ";
		String check3 = " ";
		switch(delim) {
			case "space":
				check1 = "checked";
				break;
			case "comma":
				check2 = "checked";
				break;
			case "newLine":
				check3 = "checked";
				break;
		}
		print = "<input type=\"radio\" name=\"delim\" value=\"space\" " + check1 + "> Space (\" \")<br>\r\n" + 
				"<input type=\"radio\" name=\"delim\" value=\"comma\" " + check2 + "> Comma (\",\")<br>\r\n" + 
				"<input type=\"radio\" name=\"delim\" value=\"newLine\" " + check3  + "> New Line<br>";
		request.setAttribute("radioDelim", print);
	}
	
	public void PrintRadioSortOrder(HttpServletRequest request, String order) {
		String print = "";
		if(order.equals("Ascending")) {
			print = "<input type=\"radio\" name=\"sortOrder\" value=\"Ascending\" checked> Ascending<br>\r\n" + 
				"<input type=\"radio\" name=\"sortOrder\" value=\"Descending\" > Descending<br>\r\n" + 
				"";
		}
		else {
			print = "<input type=\"radio\" name=\"sortOrder\" value=\"Ascending\" > Ascending<br>\r\n" + 
					"<input type=\"radio\" name=\"sortOrder\" value=\"Descending\" checked> Descending<br>\r\n" + 
					"";
		}
		request.setAttribute("radioSortOrder", print);
	}
	
	public String getStringFromList(List<String> strList, String delim) {
		String str = "";
		switch(delim) {
			case "space": 
				str = String.join(" ", strList);
				break;
			case "comma":
				str = String.join(", ", strList);
				break;
			case "newLine": 
				str = String.join("\n", strList);
				break;
		}
		return str;
	}
	
	public List<String> removeDuplicates(List<String> list){ 
	  List<String> uniqueStr = new ArrayList<String>();
	  //List<String> set1 = new ArrayList<String>();

	  for (String str : list){
		  if (!uniqueStr.contains(str)){
			  uniqueStr.add(str);
		  }
	  }
	  return uniqueStr;
	}

}
