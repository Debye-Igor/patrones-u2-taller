package cl.patrones.taller.u2.tienda.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cl.patrones.taller.u2.bodegaje.domain.Producto;
import cl.patrones.taller.u2.bodegaje.service.BodegajeService;
import cl.patrones.taller.u2.catalogo.domain.Aviso;
import cl.patrones.taller.u2.catalogo.domain.Categoria;
import cl.patrones.taller.u2.catalogo.domain.Clasificacion;
import cl.patrones.taller.u2.catalogo.service.CategoriaService;
import cl.patrones.taller.u2.catalogo.service.ClasificacionService;
import cl.patrones.taller.u2.tienda.adapter.ProductoAvisoAdapter;

/**
 * Implementación del servicio de avisos.
 * Utiliza el patrón Adapter para convertir productos en aAvisos.

 */
@Service
public class AvisoServiceImpl implements AvisoService {
    
    private final BodegajeService bodegajeService;
    private final ClasificacionService clasificacionService;
    private final CategoriaService categoriaService;
    
    public AvisoServiceImpl(
            BodegajeService bodegajeService,
            ClasificacionService clasificacionService,
            CategoriaService categoriaService) {
        this.bodegajeService = bodegajeService;
        this.clasificacionService = clasificacionService;
        this.categoriaService = categoriaService;
    }
    
    @Override
    public List<Aviso> getTodosLosAvisos() {
        List<Aviso> avisos = new ArrayList<>();
        
        // Obtener todos los productos del módulo bodegaje
        List<Producto> productos = bodegajeService.getProductos();
        
        // Convertir cada producto en un aviso usando el adaptador
        for (Producto producto : productos) {
            // Obtener la categoría del producto desde clasificación
            Categoria categoria = obtenerCategoriaPorSku(producto.getSku());
            
            // Usar el adaptador para convertir Producto → Aviso
            Aviso aviso = ProductoAvisoAdapter.adaptar(producto, categoria);
            
            if (aviso != null) {
                avisos.add(aviso);
            }
        }
        
        return avisos;
    }
    
    @Override
    public List<Aviso> getAvisosPorCategoria(Long categoriaId) {
        // Obtener todas las clasificaciones de la categoría
        List<Clasificacion> clasificaciones = clasificacionService
            .getClasificacionesPorCategoria(categoriaId);
        
        // Extraer los skus de las clasificaciones
        List<String> skus = clasificaciones.stream()
            .map(Clasificacion::getSku)
            .collect(Collectors.toList());
        
        if (skus.isEmpty()) {
            return List.of();
        }
        
        // Obtener los productos por SKU
        List<Producto> productos = bodegajeService.getProductosBySku(
            skus.toArray(new String[0])
        );
        
        // Obtener la categoría
        Categoria categoria = categoriaService.getCategoriaPorId(categoriaId)
            .orElse(null);
        
        // Convertir productos a avisos usando el adaptador
        List<Aviso> avisos = new ArrayList<>();
        for (Producto producto : productos) {
            Aviso aviso = ProductoAvisoAdapter.adaptar(producto, categoria);
            if (aviso != null) {
                avisos.add(aviso);
            }
        }
        
        return avisos;
    }
    
    /**
     * Obtiene la categoría de un producto mediante su SKU.
     * Busca en la tabla de clasificación.
     * 
     * @param sku el SKU del producto
     * @return la categoría del producto, o null si no se encuentra
     */
    private Categoria obtenerCategoriaPorSku(String sku) {
        return clasificacionService.getClasificacionPorSku(sku)
            .map(Clasificacion::getCategoria)
            .orElse(null);
    }
}