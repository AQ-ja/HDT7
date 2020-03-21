import java.util.Map;

/**
 * Clase de asociacion para la comparacion. 
 * @param <K> palabra en ingles 
 * @param <V> palabra en español 
 * @author Alfredo Quezada 
 * @since 16/03/2020
 * @version 1.0
 */
public class Association<K extends Comparable<K>,V> implements Comparable<Association<K,V>>{

    //Key = english
    private final K key;
    //Value = español
    private V value;

    /**
     * Constructs a new Asociation with an specific key and value
     * @param key Palabra en ingles, osea la key.
     * @param value Palabra en español, osea el valor. 
     */
    public Association(final K key, final V value){
        this.key = key;
        this.value = value;
    }

    /**
     * Obtiene la key de asociacion.
     * @return Obtiene la palabra en ingles, osea el key.
     */
    public Object getKey() {
        return key;
    }

    /**
     * Obtiene el valor de la asociacion.
     * @return palabra en español
     */
    public Object getValue() {
        return value;
    }

    /**
     * Define un valor especifico para la asociacion. 
     * @param value el valor de la asociacion.
     * @return el valor que se encontró. 
     */
    public Object setValue(final Object value) {
        final V val = (V) value;
        return this.value = val;
    }

    @Override
    public int compareTo(final Association<K, V> o) {
        final Association ob = (Association) o;
        return key.toString().compareToIgnoreCase(ob.key.toString());
    }
}
