package cl.patrones.taller.u2.tienda.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.patrones.taller.u2.catalogo.domain.Aviso;
import cl.patrones.taller.u2.catalogo.domain.Categoria;
import cl.patrones.taller.u2.catalogo.service.CategoriaService;
import cl.patrones.taller.u2.tienda.service.AvisoService;

/**
 * Controlador principal de la tienda.
 * Maneja las vistas de inicio, categorías, ubicación y contacto
 */
@Controller
public class TiendaController {
    
    private final AvisoService avisoService;
    private final CategoriaService categoriaService;
    
    public TiendaController(
            AvisoService avisoService,
            CategoriaService categoriaService) {
        this.avisoService = avisoService;
        this.categoriaService = categoriaService;
    }
    
    /**
     * Página de inicio - muestra todos los productos
     */
    @GetMapping("/")
    public String inicio(Model model) {
        // Obtener todos los avisos (productos adaptados)
        List<Aviso> avisos = avisoService.getTodosLosAvisos();
        
        // Enviar avisos al template
        model.addAttribute("avisos", avisos);
        
        return "inicio";
    }
    
    /**
     * Página de categoría - muestra productos de una categoría específica
     */
    @GetMapping("/categoria/{categoriaId}/{slug}")
    public String categoria(
            @PathVariable(name = "categoriaId") Long categoriaId,
            @PathVariable(name = "slug") String slug,
            Model model
    ) {
        // Obtener avisos de la categoría
        List<Aviso> avisos = avisoService.getAvisosPorCategoria(categoriaId);
        
        // Obtener información de la categoría
        Categoria categoria = categoriaService.getCategoriaPorId(categoriaId)
            .orElse(null);
        
        // Enviar datos al template
        model.addAttribute("avisos", avisos);
        model.addAttribute("categoria", categoria);
        
        return "categoria";
    }
    
    @GetMapping("/ingresar")
    public String login() {
        return "login";
    }
    
    @GetMapping("/ubicacion")
    public String ubicacion() {
        return "ubicacion";
    }
    
    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }
}