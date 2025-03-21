import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Stack;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase PredicateEvaluator.
 */
class PredicateEvaluatorTest {

    private PredicateEvaluator evaluator;
    private VariableManagement<Object> variableManager;

    @BeforeEach
    void setUp() {
        variableManager = new VariableManagement<>();
        evaluator = new PredicateEvaluator(variableManager);
    }

    /**
     * Prueba el predicado ATOM con un atomo.
     */
    @Test
    void testAtomConAtomo() {
        Stack<String> tokens = new Stack<>();
        tokens.push("x");
        assertEquals("T", evaluator.evaluarPredicado("ATOM", tokens), "ATOM con 'x' debe devolver 'T'");
    }

    /**
     * Prueba el predicado ATOM con una lista.
     */
    @Test
    void testAtomConLista() {
        Stack<String> tokens = new Stack<>();
        tokens.push("(1 2 3)");
        assertEquals("nil", evaluator.evaluarPredicado("ATOM", tokens), "ATOM con '(1 2 3)' debe devolver 'nil'");
    }

    /**
     * Prueba el predicado EQUAL con valores iguales.
     */
    @Test
    void testEqualConValoresIguales() {
        Stack<String> tokens = new Stack<>();
        tokens.push("5");
        tokens.push("5");
        assertEquals("T", evaluator.evaluarPredicado("EQUAL", tokens), "EQUAL con '5' y '5' debe devolver 'T'");
    }

    /**
     * Prueba el predicado EQUAL con valores diferentes.
     */
    @Test
    void testEqualConValoresDiferentes() {
        Stack<String> tokens = new Stack<>();
        tokens.push("5");
        tokens.push("10");
        assertEquals("nil", evaluator.evaluarPredicado("EQUAL", tokens), "EQUAL con '5' y '10' debe devolver 'nil'");
    }

    /**
     * Prueba el predicado < con un valor menor.
     */
    @Test
    void testLessThanConValorMenor() {
        Stack<String> tokens = new Stack<>();
        tokens.push("10");
        tokens.push("5");
        assertEquals("T", evaluator.evaluarPredicado("<", tokens), "5 < 10 debe devolver 'T'");
    }

    /**
     * Prueba el predicado > con un valor mayor.
     */
    @Test
    void testGreaterThanConValorMayor() {
        Stack<String> tokens = new Stack<>();
        tokens.push("5");
        tokens.push("10");
        assertEquals("T", evaluator.evaluarPredicado(">", tokens), "10 > 5 debe devolver 'T'");
    }
}