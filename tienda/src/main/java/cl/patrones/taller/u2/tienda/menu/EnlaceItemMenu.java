package cl.patrones.taller.u2.tienda.menu;

import java.util.Collections;
import java.util.List;

/**
 * Clase que representa un enlace simple en el menú (elemento hoja del patrón Composite).
 * No contiene elementos hijos.
 * 
 * @author 
 */
public class EnlaceItemMenu implements ItemMenu {
    
    private final String texto;
    private final String enlace;
    private final String slug;
    
    /**
     * Constructor para crear un enlace simple
     * @param texto texto a mostrar en el menú
     * @param enlace URL del enlace
     * @param slug identificador 
     */
    public EnlaceItemMenu(String texto, String enlace, String slug) {
        this.texto = texto;
        this.enlace = enlace;
        this.slug = slug;
    }
    
    /**
     * Constructor simplificado (slug = enlace)
     * @param texto texto a mostrar en el menú
     * @param enlace URL del enlace
     */
    public EnlaceItemMenu(String texto, String enlace) {
        this(texto, enlace, enlace);
    }
    
    @Override
    public String getTexto() {
        return texto;
    }
    
    @Override
    public String getEnlace() {
        return enlace;
    }
    
    @Override
    public String getSlug() {
        return slug;
    }
    
    @Override
    public boolean tieneHijos() {
        // Un enlace simple nunca tiene hijos
        return false;
    }
    
    @Override
    public List<ItemMenu> getHijos() {
        // Retorna lista vacía inmutable
        return Collections.emptyList();
    }
    
    @Override
    public String toString() {
        return String.format("EnlaceItemMenu[texto='%s', enlace='%s']", texto, enlace);
    }
}