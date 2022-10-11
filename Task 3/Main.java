
import java.util.Scanner;
import java.util.Stack;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class Main {

    private static Stack<String> stack = new Stack<>();
    private static Boolean aux = true;

    public static String op(String op, Stack<String> stack, Map<String,String> hT) throws Exception {
        String output = (stack.empty()) ? "0" : stack.pop();

        if (!stack.empty()) {
            output = Integer.toString(algorithm(op, stack.pop(), output, hT)) ;
        }

        return output;
    }

    public static void main(String[] args) throws Exception {
    	
    	Map<String,String> hT = new HashMap<String,String>();
    	hT.put("teste", new String("13"));
        
        try {
	        File text = new File("Calc.stk");
	        try (Scanner scanner = new Scanner(text)) {
                while (scanner.hasNextLine()) {
                    String str = scanner.nextLine();

                    if (isInteger(str)) {
                        stack.push(str);
                    } else if (isop(str)) {
                        String value_now = op(str, stack, hT);          
                        if (stack.size() == 0) {
                            System.out.println();
                        }

                        stack.push(value_now);
                    } 
                    else if (isId(str)) {
                    	stack.push(str);
                    }
                    
                    else {
                        System.out.println("Error: Character not allowed: " + str);
                        throw new Exception("Character not allowed");
                    }

                }
                System.out.print("The result is... " + stack.pop() + "!");

                scanner.close();
            }
	    } catch (FileNotFoundException e) {
	        System.out.println("Error when read the file...");
	        e.printStackTrace();
	    }
    }

    // REGEX

    public static boolean isInteger(String input) {
        if (input == null)
            return false;

        return input.matches("(\\d)+");
    }

    public static boolean isop(String input) {
        if (input == null)
            return false;

        return input.matches("(\\+|-|\\*|/)");
    }
    
    public static boolean isId(String input) {
        if (input == null)
            return false;

        return input.matches("([A-Za-z])");
    }

    // END OF REGEX
    
    public static int hm (Map<String,String> hT, String key) throws Exception {
    	 if (hT.containsKey(key)) {
    		 return Integer.parseInt(hT.get(key)) ;
    	 }
    	 else {
    		 throw new Exception(key + " cannot be resolved");
    	 }
    }

    public static int algorithm(String op, String l, String r, Map<String,String> hT) throws Exception {
    	 
    	
    	TokenType lTokenType = TokenType.EOF;
    	TokenType rTokenType = TokenType.EOF;
    	
    	int value_l = 0;
    	int value_r = 0;
    	
    	if(isId(l)) {
    		lTokenType = TokenType.ID;
    		value_l = hm(hT, l);
    	}
    	else {
    		lTokenType = TokenType.NUM;
    		value_l = Integer.parseInt(l);
    	}
    	
    	if(isId(r)) {
    		rTokenType = TokenType.ID;
    		value_r = hm(hT, r);
    	}
    	else {
    		rTokenType = TokenType.NUM;
    		value_r = Integer.parseInt(r);
    	}
    	
    	if(aux) {
    		System.out.println(new Token(lTokenType, l));
    		System.out.println(new Token(rTokenType, r));
    	}
    	
    	TokenType tokenType = TokenType.EOF;
    	int value = 0;
    	
    	switch (op) {
        case "+":
        	tokenType = TokenType.PLUS;
            value = value_l + value_r;
            break;
        case "-":
        	tokenType = TokenType.MINUS;
        	value = value_l - value_r;
            break;
        case "*":
        	tokenType = TokenType.STAR;
        	value = value_l * value_r;
            break;
        case "/": {
        	tokenType = TokenType.SLASH;
        	value = value_l / value_r;
        	if (value_r == 0) {
        		throw new Exception("Cannot divide by 0");
        	}
            break;
        }
        default:
            value = value_l;
        }
    	
    	if(aux) {
    		System.out.println(new Token(tokenType, op));
    	}
    	
    	return value;
    }

}