import java.util.Stack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestInterprete {
    private VariableManagement<Object> variableManager;
    private Interprete interprete;

    @BeforeEach
    void setUp() {
        new DocumentControler("dummyPath");
        variableManager = new VariableManagement<>();
        interprete = new Interprete("dummyPath");
    }

    @Test
    void testInterpretarConQuote() {
        new DocumentControler("dummyPath") {
            @Override
            public Stack<String> processLispCode() {
                Stack<String> tokens = new Stack<>();
                tokens.push("'");
                tokens.push("someValue");
                return tokens;
            }
        };
        interprete.interpretar();
    }

    @Test
    void testInterpretarConSetq() {
        new DocumentControler("dummyPath") {
            @Override
            public Stack<String> processLispCode() {
                Stack<String> tokens = new Stack<>();
                tokens.push("setq");
                tokens.push("varName");
                tokens.push("value");
                return tokens;
            }
        };

        interprete.interpretar();

        assertEquals("value", variableManager.getVariable("varName"));
    }

    @Test
    void testInterpretarConDefun() {
        new DocumentControler("dummyPath") {
            @Override
            public Stack<String> processLispCode() {
                Stack<String> tokens = new Stack<>();
                tokens.push("defun");
                tokens.push("myFunction");
                tokens.push("param1");
                tokens.push("param2");
                tokens.push("body");
                return tokens;
            }
        };
        interprete.interpretar();
    }
}
