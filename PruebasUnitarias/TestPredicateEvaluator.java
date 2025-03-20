import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Stack;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Pruebas Unitarias para clase PredicateEvaluator
 */
class TestPredicateEvaluator {

    private PredicateEvaluator evaluator;
    private VariableManagement<Object> variableManager;


    /**
     * Configura antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
       
        variableManager = new VariableManagement<>();
        evaluator = new PredicateEvaluator(variableManager);
    }

    /**
     * Prueba del metodo isAtom
     */
    @Test
    void testIsAtom() {
        Stack<String> tokens = new Stack<>();
        tokens.push("x");
        assertEquals("T", evaluator.isAtom(tokens), "regresa T por ser un atomo");

        tokens.push("y");
        assertEquals("nil", evaluator.isAtom(tokens), "regresa nil porque ya no es un atomo");
    }

    
    /**
     * Prueba el metodo isEqual
     */
    @Test
    void testIsEqual() {
        Stack<String> tokens = new Stack<>();
        tokens.push("2");
        tokens.push("1");
        assertEquals("nil", evaluator.isEqual(tokens), "regresa nil por ser diferentes");

        tokens.push("5");
        tokens.push("5");
        assertEquals("T", evaluator.isEqual(tokens), "regresa T ya que si son iguales los valores");
    }


      /**
     * Prue a el metodo isLessThan
     */
    @Test
    void testIsLessThan() {
        Stack<String> tokens = new Stack<>();
        tokens.push("44");
        tokens.push("8");
        assertEquals("T", evaluator.isLessThan(tokens), "1 < 2 regresa T");

        tokens.push("2");
        tokens.push("9");
        assertEquals("nil", evaluator.isLessThan(tokens), "2 < 1 regresa nil");
        
        tokens.push("2");
        tokens.push("2");
        assertEquals("nil", evaluator.isLessThan(tokens), "2 < 2 regresa nil porque son iguales");
    
    
    }
      /**
     * Prueba el metodo isGreaterThan
     */
    @Test
    void testIsGreaterThan() {
        Stack<String> tokens = new Stack<>();
        tokens.push("7");
        tokens.push("9");
        assertEquals("T", evaluator.isGreaterThan(tokens), "9 > 7  regresa T");

        tokens.push("4");
        tokens.push("3");
        assertEquals("nil", evaluator.isGreaterThan(tokens), "3 > 4 regresa nil");
    }



}