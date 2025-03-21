import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestInterprete {
    private DocumentControler controller;
    private VariableManagement<Object> variableManager;
    private Interprete interprete;

    @BeforeEach
    void setUp() {
        controller = new DocumentControler("dummyPath");
        variableManager = new VariableManagement<>();
        interprete = new Interprete("dummyPath");
    }

    @Test
    void testInterpretarConQuote() {
        controller = new DocumentControler("dummyPath") {
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
        controller = new DocumentControler("dummyPath") {
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
        // Simulamos tokens para definir una función
        controller = new DocumentControler("dummyPath") {
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
