import java.util.HashMap;
import java.util.Map;

public class VariableManagement<T> {
    private final Map<String, T> variables = new HashMap<>();

    public void setVariable(String name, T value) {
        variables.put(name, value);
    }

    public T getVariable(String name) {
        return variables.getOrDefault(name, null);
    }
}

