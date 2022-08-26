import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;  

public class Task1RPNcalc2 {
    public static void main(String[] args) throws IOException{

        Boolean tag = false;
        List<String> stk = Files.readAllLines(Paths.get("Calc3.stk"));
        Stack<Float> values = new Stack<Float>();
        for (String string : stk) {
            try {
                if (tag) System.out.println("Push " + Float.parseFloat(string));

                values.push(Float.parseFloat(string));
                
            }

            catch (NumberFormatException ex) {
                float b = values.pop();
                float a = values.pop();
                float result = 0;

                if(tag) System.out.println("Pop " + a);
                if(tag) System.out.println("Pop " + b);
                
                if(string.equals("+")) result = a + b;
                else if (string.equals("-")) result = a - b;
                else if (string.equals("*")) result = a * b;
                else if (string.equals("/")) result = a / b;
                
                if(!string.isEmpty()){
                    values.push(result);
                    if(tag) System.out.println("Push " + result);
                }
            }
        }

        System.out.println("The result is: " + values.pop());

    }
}
