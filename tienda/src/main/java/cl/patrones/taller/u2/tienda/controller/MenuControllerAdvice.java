package cl.patrones.taller.u2.tienda.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import cl.patrones.taller.u2.catalogo.domain.Categoria;
import cl.patrones.taller.u2.catalogo.service.CategoriaService;
import cl.patrones.taller.u2.tienda.menu.CategoriaItemMenu;
import cl.patrones.taller.u2.tienda.menu.EnlaceItemMenu;
import cl.patrones.taller.u2.tienda.menu.ItemMenu;
import cl.patrones.taller.u2.tienda.menu.util.Slugger;

/**
 * Implementa el patrón Composite para manejar enlaces simples y categorías jerárquicas.
 */
@ControllerAdvice
public class MenuControllerAdvice {

    private final CategoriaService categoriaService;
    
    /**
     * Constructor con inyección de dependencias
     * @param categoriaService servicio para acceder a las categorías
     */
    public MenuControllerAdvice(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }
    
    /**
     * Construye y retorna el menú completo de navegación.
     * Este método es invocado automáticamente por Spring y el resultado
     * está disponible en todas las vistas con el nombre "menu"
     * 
     * @return lista de elementos del menú (ItemMenu)
     */
    @ModelAttribute("menu")
    public List<ItemMenu> menu() {
        List<ItemMenu> menuItems = new ArrayList<>();
        
        // 1. Agregar enlace "Inicio"
        menuItems.add(new EnlaceItemMenu("Inicio", "/", "inicio"));
        
        // 2. Agregar elemento "Categorías" con sus subcategorías desde la BD
        CategoriaItemMenu categoriasMenu = construirMenuCategorias();
        if (categoriasMenu.tieneHijos()) {
            menuItems.add(categoriasMenu);
        }
        
        // 3. Agregar enlace "Ubicación"
        menuItems.add(new EnlaceItemMenu("Ubicación", "/ubicacion", "ubicacion"));
        
        // 4. Agregar enlace "Contacto"
        menuItems.add(new EnlaceItemMenu("Contacto", "/contacto", "contacto"));
        
        return menuItems;
    }
    
    /**
     * Construye el elemento "Categorías" con toda su jerarquía de subcategorías.
     * 
     * @return elemento de menú con las categorías
     */
    private CategoriaItemMenu construirMenuCategorias() {
        // Crear el elemento padre "Categorías"
        CategoriaItemMenu categoriasMenu = new CategoriaItemMenu(
            "Categorías", 
            "/categoria", 
            "categorias"
        );
        
        // Obtener todas las categorías de nivel superior (sin padre)
        List<Categoria> categoriasRaiz = obtenerCategoriasRaiz();
        
        // Construir recursivamente cada categoría y sus hijos
        for (Categoria categoria : categoriasRaiz) {
            ItemMenu itemCategoria = construirItemCategoria(categoria);
            categoriasMenu.agregarHijo(itemCategoria);
        }
        
        return categoriasMenu;
    }
    
    /**
     * Construye recursivamente un ItemMenu a partir de una Categoría y sus subcategorías.
     * 
     * @param categoria categoría a convertir en ItemMenu
     * @return ItemMenu que representa la categoría
     */
    private ItemMenu construirItemCategoria(Categoria categoria) {
        // Generar el slug usando la utilidad Slugger
        String slug = Slugger.toSlug(categoria.getNombre());
        
        // Generar el enlace con el formato: /categoria/{id}/{slug}
        String enlace = String.format("/categoria/%d/%s", categoria.getId(), slug);
        
        // Obtener las subcategorías (hijos) de esta categoría
        List<Categoria> subcategorias = obtenerSubcategorias(categoria.getId());
        
        // Si no tiene hijos, crear un EnlaceItemMenu 
        if (subcategorias.isEmpty()) {
            return new EnlaceItemMenu(categoria.getNombre(), enlace, slug);
        }
        
        // Si tiene hijos, crear un CategoriaItemMenu 
        CategoriaItemMenu categoriaMenu = new CategoriaItemMenu(
            categoria.getNombre(), 
            enlace, 
            slug
        );
        
        // Agregar recursivamente cada subcategoría
        for (Categoria subcategoria : subcategorias) {
            ItemMenu itemSubcategoria = construirItemCategoria(subcategoria);
            categoriaMenu.agregarHijo(itemSubcategoria);
        }
        
        return categoriaMenu;
    }
    
    /**
     * Obtiene las categorías de nivel superior (sin padre).
     * 
     * @return lista de categorías raíz
     */
    private List<Categoria> obtenerCategoriasRaiz() {
        return categoriaService.getCategorias().stream()
            .filter(c -> c.getPadre() == null)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene las subcategorías de una categoría padre específica.
     * 
     * @param idPadre ID de la categoría padre
     * @return lista de subcategorías
     */
    private List<Categoria> obtenerSubcategorias(Long idPadre) {
        return categoriaService.getCategorias().stream()
            .filter(c -> c.getPadre() != null && c.getPadre().getId().equals(idPadre))
            .collect(Collectors.toList());
    }
}