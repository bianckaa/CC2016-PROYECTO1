import java.util.Stack;

public class PredicateEvaluator {

    public Object isAtom(Stack<String> tokens) {
        if (tokens.isEmpty()) throw new IllegalArgumentException("Faltan argumentos en ATOM");
        String expresion = tokens.pop();
        return (expresion.startsWith("(") && expresion.endsWith(")")) ? "NIL" : "T";
    }

    public Object isList(Stack<String> tokens) {
        if (tokens.isEmpty()) throw new IllegalArgumentException("Faltan argumentos en LISTP");
        String expresion = tokens.pop();
        return (expresion.startsWith("(") && expresion.endsWith(")")) ? "T" : "NIL";
    }

    public Object isEqual(Stack<String> tokens) {
        if (tokens.size() < 2) throw new IllegalArgumentException("Faltan argumentos en EQUAL");
        String expresion1 = tokens.pop();
        String expresion2 = tokens.pop();
        return expresion1.equals(expresion2) ? "T" : "NIL";
    }

    public Object isLessThan(Stack<String> tokens) {
        if (tokens.size() < 2) throw new IllegalArgumentException("Faltan argumentos en <");
        try {
            double num1 = Double.parseDouble(tokens.pop());
            double num2 = Double.parseDouble(tokens.pop());
            return num1 < num2 ? "T" : "NIL";
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Los argumentos de < deben ser numeros");
        }
    }

    public Object isGreaterThan(Stack<String> tokens) {
        if (tokens.size() < 2) throw new IllegalArgumentException("Faltan argumentos en >");
        try {
            double num1 = Double.parseDouble(tokens.pop());
            double num2 = Double.parseDouble(tokens.pop());
            return num1 > num2 ? "T" : "NIL";
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Los argumentos de > deben ser numeros");
        }
    }
}