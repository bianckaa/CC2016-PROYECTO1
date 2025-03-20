import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Stack;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase SetqEvaluator.
 */
class SetqEvaluatorTest {

    private VariableManagement<Object> variableManager;
    private SetqEvaluator setqEvaluator;

    @BeforeEach
    void setUp() {
        variableManager = new VariableManagement<>();
        setqEvaluator = new SetqEvaluator(variableManager);
    }

    /**
     * Prueba de asignacion de un numero a una variable
     */
    @Test
    void testEvaluateSetqConNumero() {
        Stack<String> tokens = new Stack<>();
        tokens.push("5");
        tokens.push("x");

        setqEvaluator.evaluateSetq(tokens);
        assertEquals(5.0, variableManager.getVariable("x"));
    }

    /**
     * Prueba de asignacion de una espresion a una variable
     */
    @Test
    void testEvaluateSetqConExpresion() {
        Stack<String> tokens = new Stack<>();
        tokens.push(")");
        tokens.push("2");
        tokens.push("3");
        tokens.push("*");
        tokens.push("(");
        tokens.push("x");

        setqEvaluator.evaluateSetq(tokens);
        assertEquals(6.0, variableManager.getVariable("x"));
    }
}