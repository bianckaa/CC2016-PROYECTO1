import java.util.HashMap;
import java.util.Map;

/**
 * Clase que gestiona variables de tipo genérico mediante un mapa de nombre-valor.
 * Permite asignar y recuperar valores asociados a nombres de variables.
 *
 * @param <T> Tipo de los valores almacenados en las variables.
 */
public class VariableManagement<T> {
    private final Map<String, T> variables = new HashMap<>();

    /**
     * Asigna un valor a una variable identificada por su nombre.
     * Si la variable ya existe, su valor será sobrescrito.
     *
     * @param name  Nombre de la variable.
     * @param value Valor que se asignará a la variable.
     */
    public void setVariable(String name, T value) {
        variables.put(name, value);
    }

    /**
     * Recupera el valor de una variable por su nombre.
     * Si la variable no existe, devuelve {@code null}.
     *
     * @param name Nombre de la variable a buscar.
     * @return El valor asociado a la variable o {@code null} si no existe.
     */
    public T getVariable(String name) {
        return variables.getOrDefault(name, null);
    }
}

