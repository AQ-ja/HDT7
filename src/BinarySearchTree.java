import java.util.ArrayList;
import java.util.List;

/**
 * @param <E> El valor de la key del BinaryTree
 * @author Alfredo Quezada 
 * @since 16/03/2020
 * @version prueba
 */
public class BinarySearchTree<E extends Comparable<E>>{

    protected BinaryTree<E> root;

    /**
     * Construye un nuevo root como un objeto BinaryTree
     */
    public BinarySearchTree() {
        root = new BinaryTree<>();
    }

    /**
     * Agrega un nuevo binaryTree al BinarySearchTree
     * @param value Generico E (Asociacion) valor que sera agregado al BinaryTree
     */
    public void add(E value)
    {
        //Sino hay root, configura el nuevo BinaryTree como root.
        BinaryTree<E> newNode = new BinaryTree<>(value);
        if (root.isEmpty()) root = newNode;
        else
            //Busca recursivamente un nodo hasta que el valor del nuevo nodo es menor que el del nodo encontrado
        {
            BinaryTree<E> nodeFound = searchNodeRecursive(root, value);
            nodeFound.setValue(value);
            if (nodeFound.getLeft()== null)
                nodeFound.setLeft(new BinaryTree<E>());
            if (nodeFound.getRight()== null)
                nodeFound.setRight(new BinaryTree<E>());
        }
    }

    /**
     * Busca en el BinarySearchTree la llave especifica.
     * @param key EL key a para buscar el valor.
     * @return Tru si el BinarySearchTree contiene el valor, false si no. 
     */
    public boolean contains(E key){
        return get(key) == key;
    }

    /**
     * Obtiene el valor de la asociacion con la llave especifica.
     * @param key la llave de asociacion a buscar.
     * @return El nuevo valor del nodo, que conicide con la llave. 
     */
    public E get(E key) {
        if(root.isEmpty()) return null;
        BinaryTree<E> node = searchNodeRecursive(root, key);
        if(node.isEmpty()) return null;
        return node.value();
    }

    /**
     * Crea la lista con los valores del BinarySearchTree
     * @return La lista de valores en orden alfabetico.
     */
    public List<E> inOrder()
    {
        List<E> list = new ArrayList<>();
        inOrderRecursive(root, list);
        return list;
    }

    /**
     * Busca el nodo de manera recursiva para ver donde esta la posicion en el BinaryTreeSearch. 
     * @param node El nodo raíz o el nodo para comparar la nueva clave
     * @param key El Key nuevo key que se agregara al BinaryTree
     * @return El nodo que tomara el valor acorde a la key.
     */
    protected BinaryTree searchNodeRecursive(BinaryTree<E> node,E key)
    {
        if(node.isEmpty()) return node;
        else
        {
            int compareResult =  node.value().compareTo(key);
            if (compareResult == 0 ) return node;
            else if (compareResult > 0)
            {
                return searchNodeRecursive(node.getLeft(), key);
            }
            else
            {
                return searchNodeRecursive(node.getRight(), key);
            }
        }
    }

    /**
     * Define el BinaryTree en orden. 
     * @param node Cada nodo del BinarySearchTree.
     * @param list La lista que contiene los valores del BinaryTree en orden.
     */
    protected void inOrderRecursive(BinaryTree<E> node, List<E> list)
    {
        if(node.getLeft() != null && !node.getLeft().isEmpty()) inOrderRecursive(node.getLeft(), list);
        list.add(node.value());
        if(node.getRight() != null && !node.getRight().isEmpty()) inOrderRecursive(node.getRight(), list);
    }
}
//Referencias del codigo:
//https://www.geeksforgeeks.org/binary-search-tree-data-structure/