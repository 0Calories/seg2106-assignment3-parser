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
		
		if (program() == ERROR || !token.equals("$")) {
			System.out.println("ERROR: The code contains a syntax mistake.");
		} else {
			System.out.println("SUCCESS: The code has been successfully parsed.");
		}
		
	}
	
	private static int program() {

		if (token.equals("begin")) {
			token = getNextToken();
			if (statement_list() == ERROR) return ERROR;
			if (token.equals("end")) {
				token = getNextToken();
				return OK;
			}
		}
		
		return ERROR;
	}
	
	private static int statement_list() {

		if (statement() == ERROR) return ERROR;
		
		if (token.equals(";")) {
			token = getNextToken();
			return statement_list_prime();
		} else {
			return ERROR; 	
		}
		
	}
	
	private static int statement_list_prime() {		
		// Since in the grammar, either <statement_list> can be called, or epsilon, this production will return OK either way.
		// Otherwise, the very last statement will be counted as an error.
		statement_list();
		return OK;
		
	}
	
	private static int statement() {

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

		if (factor() == ERROR) return ERROR;
		
		return expression_prime();
		
	}
	
	private static int expression_prime() {

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
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
