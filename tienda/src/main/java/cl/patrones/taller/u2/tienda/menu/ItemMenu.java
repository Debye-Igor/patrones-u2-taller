package cl.patrones.taller.u2.tienda.menu;

import java.util.List;

/**
 * Interfaz común para elementos del menú (Patrón Composite).
 * Permite tratar uniformemente tanto enlaces simples como categorías con subcategorías.
 * 
 * @author 
 */
public interface ItemMenu {
    
    /**
     * Obtiene el texto a mostrar en el menú
     * @return texto del elemento
     */
    String getTexto();
    
    /**
     * Obtiene el enlace (URL) del elemento
     * @return enlace del elemento
     */
    String getEnlace();
    
    /**
     * Obtiene el slug del elemento
     * @return slug del elemento
     */
    String getSlug();
    
    /**
     * Indica si el elemento tiene hijos (subcategorías)
     * @return true si tiene hijos, false en caso contrario
     */
    boolean tieneHijos();
    
    /**
     * Obtiene la lista de elementos hijos (subcategorías)
     * @return lista de items del menú hijos
     */
    List<ItemMenu> getHijos();
}