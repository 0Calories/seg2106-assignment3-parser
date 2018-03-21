import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Parser {
	
	private static final int OK = 1;
	private static final int ERROR = 0;
	
	private static String token;
	private static BufferedReader reader;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		FileInputStream stream = new FileInputStream(args[0]);
		reader = new BufferedReader(new InputStreamReader(stream));
		
		token = getNextToken();
		
		if (program() == ERROR || token != "$") {
			System.out.println("ERROR: The code contains a syntax mistake.");
		} else {
			System.out.println("SUCCESS: The code has been successfully parsed.");
		}
		
	}
	
	private static int program() {
		System.out.println("program()");
		if (token.equals("begin")) {
			token = getNextToken();
			if (statement_list() == ERROR) return ERROR;
			if (token.equals("end")) {
				return OK;
			}
		}
		
		return ERROR;
	}
	
	private static int statement_list() {
		System.out.println("statement_list()");
		if (statement() == ERROR) return ERROR;
		token = getNextToken();
		
		if (token.equals(";")) {
			token = getNextToken();
			return statement_list_prime();
		}
		
		return ERROR; 
		
	}
	
	private static int statement_list_prime() {
		System.out.println("statement_list_prime()");
		if (statement_list() == ERROR) return ERROR;
		
		return OK;
		
	}
	
	private static int statement() {
		System.out.println("statement()");
		if (token.equals("id")) {
			token = getNextToken();
			if (token.equals("=")) {
				token = getNextToken();
				return expression();
			}
		}
		
		return ERROR;
		
	}
	
	private static int expression() {
		System.out.println("expression()");
		if (factor() == ERROR) return ERROR;
		
		return expression_prime();
		
	}
	
	private static int expression_prime() {
		System.out.println("expression_prime()");
		if (token.equals("+")) {
			token = getNextToken();
			return factor();
		} else if (token.equals("-")) {
			token = getNextToken();
			return factor();
		} else {
			return OK;
		}
		
	}
	
	private static int factor() {
		System.out.println("factor()");
		if (token.equals("id")) {
			token = getNextToken();
			return OK;
		} else if (token.equals("num")) {
			token = getNextToken();
			return OK;
		} else {
			return ERROR;
		}
		
	}
	
	private static String getNextToken() {
		try {
			String newLine = reader.readLine();
			System.out.println(newLine);
			return newLine;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
