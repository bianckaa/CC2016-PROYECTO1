import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Stack;

public class TestTokenizador2 {
    @Test
    public void testTokenizeBasic() {
        Tokenizador2 tokenizer = new Tokenizador2();
        String code = "(setq x 10)";
        
        Stack<String> tokens = tokenizer.tokenize(code);
        
        assertEquals(5, tokens.size());
        assertEquals("(", tokens.get(0));
        assertEquals("setq", tokens.get(1));
        assertEquals("x", tokens.get(2));
        assertEquals("10", tokens.get(3));
        assertEquals(")", tokens.get(4));
    }

    @Test
    public void testTokenizeQuote() {
        Tokenizador2 tokenizer = new Tokenizador2();
        String code = "(quote (a b c))";
        
        Stack<String> tokens = tokenizer.tokenize(code);
        
        assertEquals(3, tokens.size());
        assertEquals("quote", tokens.get(0));
        assertEquals("(a b c)", tokens.get(1)); // El bloque entre paréntesis debe ser tokenizado como un solo token.
    }

    @Test
    public void testTokenizeSingleQuote() {
        Tokenizador2 tokenizer = new Tokenizador2();
        String code = "'x";
        
        Stack<String> tokens = tokenizer.tokenize(code);
        
        assertEquals(2, tokens.size());
        assertEquals("quote", tokens.get(0));
        assertEquals("x", tokens.get(1)); // El token 'x' debe ser separado correctamente.
    }

    @Test
    public void testTokenizeInvalidQuote() {
        Tokenizador2 tokenizer = new Tokenizador2();
        String code = "(quote ";
        
        assertThrows(IllegalArgumentException.class, () -> {
            tokenizer.tokenize(code);
        });
    }

    @Test
    public void testTokenizeSetq() {
        Tokenizador2 tokenizer = new Tokenizador2();
        String code = "(setq x \"Hello World\")";
        
        Stack<String> tokens = tokenizer.tokenize(code);
        
        assertEquals(4, tokens.size());
        assertEquals("setq", tokens.get(0));
        assertEquals("x", tokens.get(1));
        assertEquals("Esto es un JUNIT Test", tokens.get(2)); // La cadena "Esto es un JUNIT Test" debe ser un solo token
        assertEquals(")", tokens.get(3));
    }

    @Test
    public void testTokenizeOperators() {
        Tokenizador2 tokenizer = new Tokenizador2();
        String code = "(+ 2 3)";
        
        Stack<String> tokens = tokenizer.tokenize(code);
        
        assertEquals(4, tokens.size());
        assertEquals("(", tokens.get(0));
        assertEquals("+", tokens.get(1));
        assertEquals("2", tokens.get(2));
        assertEquals("3", tokens.get(3));
    }
}
