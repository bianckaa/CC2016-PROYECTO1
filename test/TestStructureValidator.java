public class TestStructureValidator {
    @Test
    public void testBalancedParentheses_correct() {
        // Caso en el que los paréntesis están balanceados
        String code = "(a (b c) (d e))";
        assertTrue(StructureValidator.BalancedParentheses(code), "Los paréntesis deberían estar balanceados.");
    }

    @Test
    public void testBalancedParentheses_incorrect() {
        // Caso en el que los paréntesis no están balanceados
        String code = "(a (b c) (d e)";
        assertFalse(StructureValidator.BalancedParentheses(code), "Los paréntesis deberían estar desbalanceados.");
    }

    @Test
    public void testCorrectStructure_correct() {
        // Caso donde la estructura es correcta (sin paréntesis vacíos)
        String code = "(a (b c) (d e))";
        assertTrue(StructureValidator.CorrectStructure(code), "La estructura debería ser correcta.");
    }

    @Test
    public void testCorrectStructure_incorrect() {
        // Caso donde la estructura es incorrecta (con paréntesis vacíos)
        String code = "( )";
        assertFalse(StructureValidator.CorrectStructure(code), "La estructura debería ser incorrecta debido a paréntesis vacíos.");
    }
}
