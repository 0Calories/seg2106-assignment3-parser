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
		
		if (token == "begin") {
			token = getNextToken();
			if (statement_list() == ERROR) return ERROR;
			if (token == "end") {
				return OK;
			}
		}
		
		return ERROR;
	}
	
	private static int statement_list() {
		
		if (statement() == ERROR) return ERROR;
		token = getNextToken();
		
		if (token == ";") {
			token = getNextToken();
			return statement_list_prime();
		}
		
		return ERROR; 
		
	}
	
	private static int statement_list_prime() {
		
		if (statement_list() == ERROR) return ERROR;
		
		return OK;
		
	}
	
	private static int statement() {
		
		if (token == "id") {
			token = getNextToken();
			if (token == "=") {
				if (expression() == ERROR) return ERROR;
				return expression();
			}
		}
		
		return ERROR;
		
	}
	
	private static int expression() {
		return 0;
	}
	
	private static int expression_prime() {
		return 0;
	}
	
	private static int factor() {
		return 0;
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
