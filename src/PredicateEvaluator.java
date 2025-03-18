
import java.util.Stack;


public class PredicateEvaluator {

   
    public boolean isAtom(Stack<String> tokens) {
        
        if (!tokens.pop().equals("ATOM")) {
            throw new IllegalArgumentException("Sintaxis incorrecta para ATOM.");
        }

        String expresion = tokens.pop();
        return !expresion.startsWith("(");
    }

    
    public boolean isList(Stack<String> tokens) {
        
        if (!tokens.pop().equals("LIST")) {
            throw new IllegalArgumentException("Sintaxis incorrecta para LIST.");
        }
        String expresion = tokens.pop();
        return expresion.startsWith("(");
    }

   
    public boolean isEqual(Stack<String> tokens) {
        
        if (!tokens.pop().equals("EQUAL")) {
            throw new IllegalArgumentException("Sintaxis incorrecta para EQUAL.");
    
        }

        String expresion1 = tokens.pop();
        String expresion2 = tokens.pop();
        return expresion1.equals(expresion2);
    }

   
    public boolean isLessThan(Stack<String> tokens) {
        
        if (!tokens.pop().equals("<")) {
            throw new IllegalArgumentException("Sintaxis incorrecta para <.");
        }

       
        String expresion1 = tokens.pop();
        String expresion2 = tokens.pop();

        
        double num1 = Double.parseDouble(expresion1);
        double num2 = Double.parseDouble(expresion2);
        return num1 < num2;
    }

   
    public boolean isGreaterThan(Stack<String> tokens) {
       
        if (!tokens.pop().equals(">")) {
            throw new IllegalArgumentException("Sintaxis incorrecta para >.");
        }

        
        String expresion1 = tokens.pop();
        String expresion2 = tokens.pop();

       
        double num1 = Double.parseDouble(expresion1);
        double num2 = Double.parseDouble(expresion2);
        return num1 > num2;
    }
}