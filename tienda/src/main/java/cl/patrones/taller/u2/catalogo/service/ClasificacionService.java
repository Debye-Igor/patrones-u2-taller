package cl.patrones.taller.u2.catalogo.service;

import java.util.List;
import java.util.Optional;

import cl.patrones.taller.u2.catalogo.domain.Clasificacion;

/**
 * Servicio para gestionar clasificaciones de productos en categorías.
 */
public interface ClasificacionService {
    
    /**
     * Obtiene todas las clasificaciones
     * 
     * @return lista de clasificaciones
     */
    List<Clasificacion> getClasificaciones();
    
    /**
     * Obtiene las clasificaciones de una categoría específica
     * 
     * @param categoriaId ID de la categoría
     * @return lista de clasificaciones de la categoría
     */
    List<Clasificacion> getClasificacionesPorCategoria(Long categoriaId);
    
    /**
     * Obtiene la clasificación de un producto por su SKU
     * 
     * @param sku SKU del producto
     * @return clasificación del producto
     */
    Optional<Clasificacion> getClasificacionPorSku(String sku);
}