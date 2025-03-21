import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Esta clase se encarga de evaluar condiciones y ejecutar acciones basadas en esas condiciones. 
 * Utiliza un evaluador de predicados para determinar si una condición es verdadera y ejecuta la acción correspondiente.
 */
public class CondEvaluator {
    
    private PredicateEvaluator predicateEvaluator;

    /**
     * Constructor de la clase CondEvaluator.
     * Inicializa el evaluador de predicados con un gestor de variables.
     * 
     * @param variableManager El gestor de variables para el evaluador de predicados.
     */
    public CondEvaluator(VariableManagement<Object> variableManager) {
        this.predicateEvaluator = new PredicateEvaluator(variableManager);
    }

    /**
     * Evalúa una pila de tokens y ejecuta la acción asociada a la primera condición verdadera.
     * 
     * @param tokens Una pila de tokens que representa la expresión condicional.
     * @return La acción a ejecutar si la condición es verdadera, o "nil" si no se cumple ninguna condición.
     */
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

    /**
     * Elimina los tokens hasta encontrar un token de apertura de paréntesis.
     * 
     * @param tokens La pila de tokens a procesar.
     */
    private void eliminarTokensHastaSiguienteApertura(Stack<String> tokens) {
        while (!tokens.isEmpty()) {
            String token = tokens.pop();
            if (token.equals("(")) {
                tokens.push(token); 
                break;
            }
        }
    }

    /**
     * Imprime la lista de tokens recibidos en la pila.
     * 
     * @param tokens La pila de tokens a imprimir.
     */
    private void imprimirListaTokens(Stack<String> tokens) {
        List<String> listaTokens = new ArrayList<>(tokens);
        System.out.println("Tokens recibidos: " + listaTokens);
    }

    /**
     * Evalúa una condición en formato de cadena de texto y retorna el resultado de la evaluación.
     * 
     * @param condicion La condición a evaluar.
     * @param tokens La pila de tokens que contiene los valores necesarios para la evaluación.
     * @return "T" si la condición es verdadera, "NIL" si no se cumple.
     */
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

    /**
     * Verifica si una cadena es un número.
     * 
     * @param str La cadena a verificar.
     * @return true si la cadena es un número, false en caso contrario.
     */
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Extrae una expresión de la pila de tokens, eliminando los paréntesis balanceados.
     * 
     * @param tokens La pila de tokens a procesar.
     * @return La expresión extraída como cadena.
     */
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

    /**
     * Extrae una acción de la pila de tokens. Si encuentra el token "quote", extrae el siguiente token.
     * 
     * @param tokens La pila de tokens a procesar.
     * @return La acción extraída como cadena.
     */
    private String extraerAccion(Stack<String> tokens) {
        if (tokens.isEmpty()) {
            return "nil";
        }
    
        String token = tokens.pop();
    
        if (token.equals("(")) {
            StringBuilder accion = new StringBuilder();
            int balance = 1; 
    
            while (!tokens.isEmpty() && balance > 0) {
                String subToken = tokens.pop();
                if (subToken.equals("(")) {
                    balance++;
                } else if (subToken.equals(")")) {
                    balance--;
                    if (balance == 0) {
                        break;
                    }
                }
                accion.append(subToken).append(" ");
            }
    
            String accionStr = accion.toString().trim();
            String accionInvertida = new StringBuilder(accionStr).reverse().toString();
            System.out.println("Acción extraída (invertida): " + accionInvertida);
    
            return evaluarOperacionMatematica(accionInvertida);
        } else if (token.equals("quote") && !tokens.isEmpty()) {
            return tokens.pop();
        }  return token;
    }
    

    private String evaluarOperacionMatematica(String accion) {
        Stack<String> tokensOperacion = new Stack<>();
        String[] partes = accion.split(" ");
    
        for (String parte : partes) {
            tokensOperacion.push(parte);
        }
    
        CalculadoraAritmetica calculadora = new CalculadoraAritmetica();
        Double resultado = calculadora.evaluar(tokensOperacion);
    
        return String.valueOf(resultado); 
    }
}
