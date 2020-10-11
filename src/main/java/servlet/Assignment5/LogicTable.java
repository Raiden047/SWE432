

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
			if(!hasLowerChars(request, exp)) {
				if(!containsSpecial(request, exp)) {
					if(!isLogicRepeating(request, exp)) {
						request.setAttribute("echo", "Expression: "+exp);
						String exp2 = replaceLogicSyntax(request, exp);
						if(isSingleChar(request, exp2)) {
							ArrayList<String> vars = getVars(exp2);
							ArrayList<String> varChain = getVarSequence(exp2);
							if(isLogicWork(request, vars, varChain, exp2)) {
								String [][] table = makeTable(vars, exp2);
								PrintTable(request, table);
							}
						}
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
	
	public boolean hasLowerChars(HttpServletRequest request, String str){
		Pattern p = Pattern.compile("([a-z])+");
		Matcher m = p.matcher(str);
		String var = "";
		boolean b = m.find();
		if(b){
			var = "Error Lower case Letters are not allowed: " + m.group();
			request.setAttribute("echo", var);
			return true;
		}

		return false;
	}
	
	public boolean containsSpecial(HttpServletRequest request, String str){
		Pattern p = Pattern.compile("([,';\\\".\\\\/\\\\\\\\:@#$%*_+<>?{}\\\\[\\\\]~-])|([0-9])");
		Matcher m = p.matcher(str);
		
		String var = "";
		boolean b = m.find();
		if(b) {
			var = "Error Invalid Symbol/Character: " + m.group();
			request.setAttribute("echo", var);
			return true;
		}
		return false;
	}
	
	public boolean isLogicRepeating(HttpServletRequest request, String str){
		Matcher match = Pattern.compile("(and|AND|&|or|OR|\\||xor|XOR|!=|\\^){2,}", Pattern.CASE_INSENSITIVE).matcher(str);
		String var = "";
		while(match.find()){
			if(!match.group().equals("&&") && !match.group().equals("||")){
				var = "Error Logic is Repeating: "+ match.group();
				request.setAttribute("echo", var);
				return true;
			}
		}
		return false;
	}
	
	/*
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
      request.setAttribute("expression", str);
      return b;
	}*/
	
	public String replaceLogicSyntax(HttpServletRequest request, String str){
		Matcher matchAND = Pattern.compile("(AND|and|&+)", Pattern.CASE_INSENSITIVE).matcher(str);
		//String str2 = "";
		while(matchAND.find()){
			str = matchAND.replaceAll("&&");
		}
		
		String str4 = "";
		Matcher matchXOR = Pattern.compile("(XOR|xor|!=|\\^)", Pattern.CASE_INSENSITIVE).matcher(str);
		while(matchXOR.find()){
			str = matchXOR.replaceAll("!=");
		}
		
		String str3 = "";
		Matcher matchOR = Pattern.compile("(OR|or|\\|+)", Pattern.CASE_INSENSITIVE).matcher(str);
		while(matchOR.find()){
			str = matchOR.replaceAll("||");
		}

		//System.out.println("Replaced Logic: " + str);
		return str;
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
	
	public boolean isSingleChar(HttpServletRequest request, String str){
	      Pattern p = Pattern.compile("(\\w){2,}");
	      Matcher m = p.matcher(str);
	      String var = "";
	      boolean b = m.find();
	      if(b){
	         var = "Error Only Single Characters Symbols Allowed:  " + m.group();
	         request.setAttribute("echo", var);
	         return false;
	      }
	         
	      return true;
	   }
	
	public static ArrayList<String> getVarSequence(String str){
		Pattern p = Pattern.compile("([a-zA-Z]+)");
		Matcher m = p.matcher(str);
	      
		ArrayList<String> allMatches = new ArrayList<String>();
		while (m.find()) {
			allMatches.add(m.group());
		}
		return allMatches;
	}
	
	public boolean isLogicWork(HttpServletRequest request, ArrayList<String> vars, ArrayList<String> varChain, String expression) {
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
			for(int i = 0; i < varChain.size(); i++){
				pattern = Pattern.compile("true");
				matcher = pattern.matcher(str); 
				str = matcher.replaceFirst(varChain.get(i));
			}
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
