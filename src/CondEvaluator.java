import java.util.Stack;

public class CondEvaluator {
    public static String evaluarCondicion(Stack<String> tokens, VariableManagement<String> variableManager) {
        String operador = tokens.pop();
        String operando1 = tokens.pop();
        String operando2 = tokens.pop();
        String resultadoTrue = tokens.pop();
        String resultadoFalse = tokens.pop();

        if (operador.equals(">")) {
            String valor1 = variableManager.getVariable(operando1);
            String valor2 = variableManager.getVariable(operando2);

            if (valor1 != null && valor2 != null) {
                Integer intValor1 = Integer.parseInt(valor1);
                Integer intValor2 = Integer.parseInt(valor2);
                if (intValor1 > intValor2) {
                    return resultadoTrue;
                } else {
                    return resultadoFalse;
                }
            }
        }

        return null; 
    }
}
