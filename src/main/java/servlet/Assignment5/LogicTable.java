

import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.script.ScriptEngineManager;
import javax.script.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.*; // servlet library
import javax.servlet.http.*; // servlet library

/**
 * Servlet implementation class LogicTable
 */
@WebServlet( name = "LogicTable", urlPatterns = {"/LogicTable"} )
public class LogicTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogicTable() {
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
		request.getRequestDispatcher("Assignment5.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		String exp = request.getParameter("expression");
		if(!exp.equals("")) {
			if(!containsSpecial(request, exp)) {
				if(containsProperVar(request, exp)) {
					ArrayList<String> vars = getVars(exp);
					if(isLogicWork(request, vars, exp)) {
						String [][] table = makeTable(vars, exp);
						PrintTable(request, table);
					}
				}
			}
		}
		else
			request.setAttribute("echo", "");
		request.setAttribute("expression", exp);
		//doGet(request, response);
		request.getRequestDispatcher("Assignment5.jsp").forward(request, response);
	}
	
	public boolean containsSpecial(HttpServletRequest request, String str){
		Pattern p = Pattern.compile("([@#$%*^_+=<>?{}\\[\\]~-])");
		Matcher m = p.matcher(str);
		
		String var = "";
		boolean b = m.find();
		if(b) {
			var = "Error: Invalid Symbol: " + m.group();
			request.setAttribute("echo", var);
		}
		else
			var = "Expression: " + str;
		return b;
	}
	
	public boolean containsProperVar(HttpServletRequest request, String str){
      Pattern p = Pattern.compile("([0-9])([^a-zA-Z])([@#$%*_+=<>?{}\\[\\]~-])");
      Matcher m = p.matcher(str);
      
      String var = "";
      boolean b = !m.find();
      if(b)
    	  var = "Expression: " + str;
      else
    	  var = "Error: Invalid Character: " + m.group();
      request.setAttribute("echo", var);
      return b;
	}
	
	public static ArrayList<String> getVars(String str){
		Pattern p = Pattern.compile("([a-zA-Z]+)");
		Matcher m = p.matcher(str);
	      
		ArrayList<String> allMatches = new ArrayList<String>();
		while (m.find()) {
			String found = m.group();
			if(!allMatches.contains(found))
				allMatches.add(found);
		}
		return allMatches;
	}
	
	public boolean isLogicWork(HttpServletRequest request, ArrayList<String> vars, String expression) {
		Pattern pattern;
		Matcher matcher;
		for(int i = 0; i < vars.size(); i++){
			pattern = Pattern.compile(vars.get(i));
			matcher = pattern.matcher(expression); 
			expression = matcher.replaceAll("true");
		}
		
		try{
			boolean result = (Boolean) new ScriptEngineManager().getEngineByName("JavaScript").eval(expression);
		}
		catch(Exception e){
			//System.out.println(e);
			String str = "Error: " + e;
			request.setAttribute("echo", str);
			return false;
		}
		return true;
	}
	
	public static String [][] makeTable(ArrayList<String> vars, String expression){
		int cols = vars.size();
		int rows = (int) Math.pow(2,cols);

		//set table header
		String [][] table = new String[rows+1][cols+1];

		for(int i = 0; i < cols; i++){
			table[0][i] = vars.get(i);
		}
		table[0][cols] = expression;

		for (int i=0; i<rows; i++) {
			int c = 0;
			ArrayList<String> vals = new ArrayList<String>();
			// set rows
			for (int j=cols-1; j>=0; j--) {
				int b = (i/(int) Math.pow(2, j))%2;
				if(b == 1) {
					vals.add("true");
					table[i+1][c] = "T";
				}
				else {
					vals.add("false");
					table[i+1][c] = "F";
				}
				c++;
			}
			table[i+1][cols] = solveLogicExpression(vars, vals, expression); // solve row
		}

		return table;
	}

	public static String solveLogicExpression(ArrayList<String> vars, ArrayList<String> vals, String expression){
		Pattern pattern;
		Matcher matcher;
		for(int i = 0; i < vars.size(); i++){
			pattern = Pattern.compile(vars.get(i));
			matcher = pattern.matcher(expression); 
			expression = matcher.replaceAll(vals.get(i));
		}

		boolean result = false;
		try{
			result = (Boolean) new ScriptEngineManager().getEngineByName("JavaScript").eval(expression);
		}
		catch(Exception e){
			//System.out.println(e);
		}

		if(result)
			return "T";
		else
			return "F";
	}
	
	private void PrintTable(HttpServletRequest request, String [][] vals) {
		String print = "<table>";
		for(int i = 0; i< vals.length; i++) {
			print += "<tr>";
			for(int j = 0; j < vals[0].length; j++) {
				if(i == 0)
					print += "<th>" + vals[i][j] + "</th>";
				else
					print += "<td>" + vals[i][j] + "</td>";
			}
			print += "</tr>";
		}
		print += "</table>";
		request.setAttribute("table", print);
	}

}
