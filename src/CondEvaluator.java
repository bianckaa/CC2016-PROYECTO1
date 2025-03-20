import java.util.Stack;

public class CondEvaluator {
    private PredicateEvaluator predicateEvaluator;

    public CondEvaluator() {
        this.predicateEvaluator = new PredicateEvaluator();
    }

    public String evaluarCond(Stack<String> tokens) {
        while (!tokens.isEmpty()) {
            String token = tokens.pop();
            System.out.println("Token actual en CondEvaluator: " + token); 

            if (token.trim().isEmpty()) {
                continue;
            }

            if (token.equals("(")) {
                String condicion = extraerExpresion(tokens);
                System.out.println("Condición extraída: " + condicion); 

                String resultadoCondicion = evaluarCondicion(condicion, tokens);
                System.out.println("Resultado de la condición: " + resultadoCondicion); 

                if (resultadoCondicion.equals("T")) {
                    String accion = extraerExpresion(tokens);
                    System.out.println("Acción extraída: " + accion); 
                    return accion;
                } else {
                    descartarExpresion(tokens);
                    System.out.println("Condición falsa, descartando acción..."); 
                }
            } else if (token.equals(")")) {               
                break;
            }
        }
        return "esta vacio"; 
    }

    private String evaluarCondicion(String condicion, Stack<String> tokens) {
        Stack<String> condicionTokens = new Stack<>();
        String[] partes = condicion.split(" ");
        for (int i = partes.length - 1; i >= 0; i--) {
            condicionTokens.push(partes[i]);
        }

        String predicado = condicionTokens.pop();
        System.out.println("Predicado a evaluar: " + predicado); 

        Object resultado = predicateEvaluator.evaluarPredicado(predicado, condicionTokens);
        return resultado.toString();
    }

    private String extraerExpresion(Stack<String> tokens) {
        StringBuilder expresion = new StringBuilder();
        int balance = 0;

        while (!tokens.isEmpty()) {
            String token = tokens.pop();

            if (token.equals("(")) {
                balance++;
            } else if (token.equals(")")) {
                balance--;
                if (balance < 0) {
                    tokens.push(token); 
                    break;
                }
            }

            expresion.insert(0, token + " "); 

            if (balance == 0) {
                break;
            }
        }

        return expresion.toString().trim();
    }

    private void descartarExpresion(Stack<String> tokens) {
        int balance = 0;

        while (!tokens.isEmpty()) {
            String token = tokens.pop();

            if (token.equals("(")) {
                balance++;
            } else if (token.equals(")")) {
                balance--;
                if (balance < 0) {
                    break;
                }
            }

            if (balance == 0) {
                break;
            }
        }
    }
}