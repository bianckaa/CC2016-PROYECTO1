import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase QuoteEvaluator.
 */
class TestQuoteEvaluator {

    /**
     * Prueba de quote que decuelva si evaluar
     */
    @Test
    void testEvaluateQuote() {
        String expresion = "(1 2 3)";
        Object resultado = QuoteEvaluator.evaluateQuote(expresion);
        assertEquals(expresion, resultado);
    }
}