import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Task2RPNCalcSS {

    private static boolean isValid(String string) {
        return string != null && string.matches("[0-9]*");
    }

    public static void main(String[] args) throws Exception {
        Stack<String> stackPile = new Stack<String>();
        List<Token> tokenList = new ArrayList<>();
        File text = new File("Calc1.stk");
        try (Scanner scanner = new Scanner(text)) {
            while(scanner.hasNextLine()){
                String digit = scanner.nextLine();

                if (isValid(digit)) {
                    Token token = new Token(TokenType.NUM, digit);
                    tokenList.add(token);
                } else if (digit.equals("+")) {
                    Token token = new Token(TokenType.PLUS, digit);
                    tokenList.add(token);
                } else if (digit.equals("-")) {
                    Token token = new Token(TokenType.MINUS, digit);
                    tokenList.add(token);
                } else if (digit.equals("/")) {
                    Token token = new Token(TokenType.SLASH, digit);
                    tokenList.add(token);
                } else if (digit.equals("*")) {
                    Token token = new Token(TokenType.STAR, digit);
                    tokenList.add(token);
                } else {
                    throw new Exception();
                }
            }
        }
        
        while(!tokenList.isEmpty()){
            String digit = tokenList.remove(0).lexeme;
            switch(digit){
                case "+":
                case "-":
                case "/":
                case "*":
                    int x, y;
                    x = Integer.parseInt(stackPile.pop());
                    y = Integer.parseInt(stackPile.pop());
                    switch(digit){
                        case "+":
                            stackPile.push(Integer.toString(y + x));
                        break;
                        case "-":
                            stackPile.push(Integer.toString(y - x));
                        break;
                        case "/":
                            stackPile.push(Integer.toString(y / x));
                        break;
                        case "*":
                            stackPile.push(Integer.toString(y * x));
                        break;
                    }
                break;
                default:
                    stackPile.push(digit);
                break;
            }
        }

        System.out.println("The RPN Stacker Manual/Simple Scanning result is: " + stackPile.pop());
    }
}
