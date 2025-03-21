import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Stack;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase DefunEvaluator.
 */
class TetsDefunEvaluator {

    private VariableManagement<Object> variableManager;

    @BeforeEach
    void setUp() {
        variableManager = new VariableManagement<>();
    }

    /**
     * Prueba la definicion de una funcion.
     */
    @Test
    void testEvaluateDefun() {
        Stack<String> tokens = new Stack<>();
        tokens.push(")");
        tokens.push("1");
        tokens.push("2");
        tokens.push("+");
        tokens.push("(");
        tokens.push(")");
        tokens.push("x");
        tokens.push("(");
        tokens.push("miFuncion");

        DefunEvaluator.evaluateDefun(tokens, variableManager);
        assertNotNull(variableManager.getVariable("miFuncion"));
    }

    /**
     * Prueba la evaluación de una expresion aritmetica.
     */
    @Test
    void testEvaluarExpresionAritmetica() {
        Stack<String> expresion = new Stack<>();
        expresion.push(")");
        expresion.push("2");
        expresion.push("3");
        expresion.push("*");
        expresion.push("(");

        double resultado = DefunEvaluator.evaluarExpresionAritmetica(expresion, variableManager);
        assertEquals(6.0, resultado);
    }
}