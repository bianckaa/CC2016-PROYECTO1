import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Stack;

public class TestCondEvaluator {
    /**
     * Test que verifica el comportamiento básico de evaluarCOND con una condición verdadera.
     */
    @Test
    public void testEvaluarCONDTrue() {
        Stack<String> tokens = new Stack<>();
        tokens.push("(");
        tokens.push("t");
        tokens.push(")");
        tokens.push("accion1");

        VariableManagement<Object> variableManager = new VariableManagement<>();
        CondEvaluator condEvaluator = new CondEvaluator(variableManager);

        String resultado = condEvaluator.evaluarCOND(tokens);

        assertEquals("accion1", resultado, "La acción debe ser 'accion1' cuando la condición es verdadera.");
    }

    /**
     * Test que verifica el comportamiento de evaluarCOND con una condición falsa.
     */
    @Test
    public void testEvaluarCONDFalse() {
        Stack<String> tokens = new Stack<>();
        tokens.push("(");
        tokens.push("f");
        tokens.push(")");
        tokens.push("accion2");

        VariableManagement<Object> variableManager = new VariableManagement<>();
        CondEvaluator condEvaluator = new CondEvaluator(variableManager);

        String resultado = condEvaluator.evaluarCOND(tokens);

        assertEquals("nil", resultado, "La acción debe ser 'nil' cuando la condición es falsa.");
    }

    /**
     * Test que verifica la evaluación de una condición con un predicado numérico.
     */
    @Test
    public void testEvaluarCondicionPredicado() {
        Stack<String> tokens = new Stack<>();
        tokens.push("(");
        tokens.push("predicado 5 10)");
        tokens.push("accion3");

        VariableManagement<Object> variableManager = new VariableManagement<>();
        CondEvaluator condEvaluator = new CondEvaluator(variableManager);

        String resultado = condEvaluator.evaluarCOND(tokens);

        assertEquals("accion3", resultado, "La acción debe ser 'accion3' si el predicado es verdadero.");
    }

    /**
     * Test que verifica que se obtiene 'nil' cuando no se cumple la condición.
     */
    @Test
    public void testEvaluarCondicionNoCumplida() {
        Stack<String> tokens = new Stack<>();
        tokens.push("(");
        tokens.push("predicado 5 3)");
        tokens.push("accion4");

        VariableManagement<Object> variableManager = new VariableManagement<>();
        CondEvaluator condEvaluator = new CondEvaluator(variableManager);

        String resultado = condEvaluator.evaluarCOND(tokens);

        assertEquals("nil", resultado, "La acción debe ser 'nil' cuando la condición no se cumple.");
    }
}