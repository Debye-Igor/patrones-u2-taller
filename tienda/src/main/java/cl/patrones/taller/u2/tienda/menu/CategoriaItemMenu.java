package cl.patrones.taller.u2.tienda.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una categoría del menú que puede contener subcategorías
 * (elemento compuesto del patrón Composite).
 * 
 * @author 
 */
public class CategoriaItemMenu implements ItemMenu {
    
    private final String texto;
    private final String enlace;
    private final String slug;
    private final List<ItemMenu> hijos;
    
    /**
     * Constructor para crear una categoría
     * @param texto texto a mostrar en el menú
     * @param enlace URL del enlace
     * @param slug identificador 
     */
    public CategoriaItemMenu(String texto, String enlace, String slug) {
        this.texto = texto;
        this.enlace = enlace;
        this.slug = slug;
        this.hijos = new ArrayList<>();
    }
    
    /**
     * Agrega un elemento hijo (subcategoría o enlace) a esta categoría
     * @param hijo elemento a agregar
     */
    public void agregarHijo(ItemMenu hijo) {
        if (hijo != null) {
            this.hijos.add(hijo);
        }
    }
    
    /**
     * Agrega múltiples elementos hijos
     * @param hijos elementos a agregar
     */
    public void agregarHijos(List<ItemMenu> hijos) {
        if (hijos != null) {
            this.hijos.addAll(hijos);
        }
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
        return !hijos.isEmpty();
    }
    
    @Override
    public List<ItemMenu> getHijos() {
        return hijos;
    }
    
    @Override
    public String toString() {
        return String.format("CategoriaItemMenu[texto='%s', enlace='%s', hijos=%d]", 
                           texto, enlace, hijos.size());
    }
}
