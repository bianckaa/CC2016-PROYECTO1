import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Stack;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase CalculadoraAritmetica.
 */
class TestCalculadoraAritmetica {
    private CalculadoraAritmetica calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new CalculadoraAritmetica();
    }

    /**
     * Prueba la operacion de suma.
     */
    @Test
    void testEvaluarSuma() {
        Stack<String> tokens = new Stack<>();
        tokens.push("2");
        tokens.push("3");
        tokens.push("+");
        assertEquals(5.0, calculadora.evaluar(tokens));
    }

    /**
     * Prueba la operacion de resta.
     */
    @Test
    void testEvaluarResta() {
        Stack<String> tokens = new Stack<>();
        tokens.push("5");
        tokens.push("3");
        tokens.push("-");
        assertEquals(2.0, calculadora.evaluar(tokens));
    }

    /**
     * Prueba la operacion de multiplicacion.
     */
    @Test
    void testEvaluarMultiplicacion() {
        Stack<String> tokens = new Stack<>();
        tokens.push("2");
        tokens.push("3");
        tokens.push("*");
        assertEquals(6.0, calculadora.evaluar(tokens));
    }



    /**
     * Prueba la division por cero.
     */
    @Test
    void testEvaluarDivisionPorCero() {
        Stack<String> tokens = new Stack<>();
        tokens.push("6");
        tokens.push("0");
        tokens.push("/");
        assertThrows(ArithmeticException.class, () -> calculadora.evaluar(tokens));
    }
}