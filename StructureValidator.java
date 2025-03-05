import java.util.Stack;

public class StructureValidator {

    public static boolean BalancedParentheses(String code){
        Stack<Character> stack = new Stack<>();
        for (char ch : code.toCharArray()){
            if(ch == '('){

                stack.push(ch);

            }else if (ch == ')'){
                if(stack.isEmpty() || stack.pop() != '('){
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static boolean CorrectStructure(String code) {
        if (code.contains("()")) {
            return false;
        }
        return true;
    }

    public static void validateSyntax(String code) {
        if (!BalancedParentheses(code)) {
            throw new IllegalArgumentException("Error, los parentesis no estan balanceados");
        }
        if (!CorrectStructure(code)) {
            throw new IllegalArgumentException("Error, la expresion contiene parentesis vacios o esta mal estructurada");
        }
    }
}
    

