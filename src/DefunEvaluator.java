
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;

public class DefunEvaluator {

    public static void evaluateDefun(Stack<String> tokens, VariableManagement<Object> variableManager) {
        if (tokens.size() < 4) {
            throw new IllegalArgumentException("Error: defun requiere al menos tres argumentos (nombre, parametros y cuerpo).");
        }

        String nombreFuncion = tokens.pop();

        if (!tokens.peek().equals("(")) {
            throw new IllegalArgumentException("Error: Se esperaba una lista de parametros despues del nombre de la funcion.");
        }
        tokens.pop();

        Stack<String> parametros = new Stack<>();
        while (!tokens.isEmpty() && !tokens.peek().equals(")")) {
            parametros.push(tokens.pop());
        }

        if (tokens.isEmpty() || !tokens.pop().equals(")")) {
            throw new IllegalArgumentException("Error: Parentesis no balanceados en la lista de parametros.");
        }

        Stack<String> cuerpo = new Stack<>();
        while (!tokens.isEmpty() && !tokens.peek().equals(")")) {
            cuerpo.push(tokens.pop());
        }

        if (tokens.isEmpty() || !tokens.pop().equals(")")) {
            throw new IllegalArgumentException("Error: Parentesis no balanceados en el cuerpo de la funcion.");
        }

        
        Map<String, Object> funcion = new HashMap<>();
        funcion.put("parametros", parametros);
        funcion.put("cuerpo", cuerpo);
        variableManager.setVariable(nombreFuncion, funcion);

        System.out.println("Funcion " + nombreFuncion + " definida con exito.");
    }

    public static double evaluarAritmetica(String operador, double a, double b) {
        switch (operador) {
            case "+":
                return b + a;
            case "-":
                return b - a;
            case "*":
                return b * a;
            case "/":
                if (a == 0) {
                    throw new IllegalArgumentException("Error: Division por cero.");
                }
                return b / a;
            default:
                throw new IllegalArgumentException("Error: Operador aritmetico no valido: " + operador);
        }
    }

    public static double evaluarExpresionAritmetica(Stack<String> expresion, VariableManagement<Object> variableManager) {
        Stack<Double> valores = new Stack<>();
    
        while (!expresion.isEmpty()) {
            String token = expresion.pop();
    
           
            if (token.equals("(") || token.equals(")")) {
                continue;
            }
    
            if (esNumero(token)) {
                valores.push(Double.parseDouble(token));
            } else if (esOperadorAritmetico(token)) {
                if (valores.size() < 2) {
                    throw new IllegalArgumentException("Error: Faltan operandos para el operador " + token);
                }
                double b = valores.pop();
                double a = valores.pop();
                double resultado = evaluarAritmetica(token, a, b);
                valores.push(resultado);
            } else {
                
                Object valorVariable = variableManager.getVariable(token);
                if (valorVariable == null) {
                    throw new IllegalArgumentException("Error: Variable no definida: " + token);
                }
                valores.push(Double.parseDouble(valorVariable.toString()));
            }
        }
    
        if (valores.size() != 1) {
            throw new IllegalArgumentException("Error: Expresión aritmetica mal formada.");
        }
    
        return valores.pop();
    }

    private static boolean esNumero(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    
    private static boolean esOperadorAritmetico(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }
}