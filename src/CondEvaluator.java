import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class CondEvaluator {
    private PredicateEvaluator predicateEvaluator;

    public CondEvaluator(VariableManagement<Object> variableManager) {
        this.predicateEvaluator = new PredicateEvaluator(variableManager);
    }

    public String evaluarCOND(Stack<String> tokens) {
        imprimirListaTokens(tokens);

        while (!tokens.isEmpty()) {
            String token = tokens.pop();
            System.out.println("Token actual en CondEvaluator: " + token);

            if (token.trim().isEmpty()) {
                continue;
            }

            if (token.equals("(")) {
                String condicion = extraerExpresion(tokens);
                System.out.println("Condición extraída: " + condicion);

                if (condicion.trim().equalsIgnoreCase("t")) {
                    String accion = extraerAccion(tokens);
                    System.out.println("Caso por defecto, acción extraída: " + accion);
                    tokens.clear(); 
                    return accion; 
                }

                String resultadoCondicion = evaluarCondicion(condicion, tokens);
                System.out.println("Resultado de la condición: " + resultadoCondicion);

                if (resultadoCondicion.equalsIgnoreCase("T")) {
                    String accion = extraerAccion(tokens);
                    System.out.println("Acción extraída: " + accion);
                    tokens.clear(); 
                    return accion; 
                } else {
                    eliminarTokensHastaSiguienteApertura(tokens);
                    System.out.println("Condición falsa, descartando acción y buscando siguiente condición...");
                }
            } else if (token.equals(")")) {
                break;
            }
        }
        return "nil"; 
    }

    private void eliminarTokensHastaSiguienteApertura(Stack<String> tokens) {
        while (!tokens.isEmpty()) {
            String token = tokens.pop();
            if (token.equals("(")) {
                tokens.push(token); 
                break;
            }
        }
    }

    private void imprimirListaTokens(Stack<String> tokens) {
        List<String> listaTokens = new ArrayList<>(tokens);
        System.out.println("Tokens recibidos: " + listaTokens);
    }

    private String evaluarCondicion(String condicion, Stack<String> tokens) {
        Stack<String> condicionTokens = new Stack<>();
        String[] partes = condicion.split(" ");
    
        for (String parte : partes) {
            condicionTokens.push(parte);
        }
    
        String predicado = condicionTokens.get(1);  
        System.out.println("Predicado a evaluar: " + predicado);
    
        condicionTokens.remove(0);  
        condicionTokens.remove(0);  
    
        while (!condicionTokens.isEmpty() && condicionTokens.peek().equals(")")) {
            condicionTokens.pop();
        }
    
        System.out.println("Tokens para el predicado: " + condicionTokens);
    
        Stack<String> condicionTokensInvertidos = new Stack<>();
        while (!condicionTokens.isEmpty()) {
            condicionTokensInvertidos.push(condicionTokens.pop());
        }
    
        System.out.println("Tokens invertidos: " + condicionTokensInvertidos);
    
        for (int i = 0; i < condicionTokensInvertidos.size(); i++) {
            try {
                String token = condicionTokensInvertidos.get(i);
                if (isNumeric(token)) {
                    condicionTokensInvertidos.set(i, String.valueOf(Double.parseDouble(token)));
                }
            } catch (Exception e) {
                System.out.println("Error al convertir el parámetro: " + condicionTokensInvertidos.get(i));
            }
        }
    
        Object resultado = predicateEvaluator.evaluarPredicado(predicado, condicionTokensInvertidos);
        System.out.println("Resultado del predicado: " + resultado);
    
        if (resultado == null || resultado.equals(false) || (resultado instanceof String && ((String) resultado).equalsIgnoreCase("NIL"))) {
            System.out.println("Condición no cumplida, ignorando la acción.");
            return "NIL"; 
        }
    
        return "T"; 
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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

            expresion.append(token).append(" ");

            if (balance == 0) {
                break;
            }
        }

        return expresion.toString().trim();
    }

    private String extraerAccion(Stack<String> tokens) {
        if (tokens.isEmpty()) {
            return "nil";
        }

        String token = tokens.pop();
        
        if (token.equals("quote") && !tokens.isEmpty()) {
            return tokens.pop();
        }  
        return token;
    }
}