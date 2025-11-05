package cl.patrones.taller.u2.tienda.service;

import java.util.List;

import cl.patrones.taller.u2.catalogo.domain.Aviso;

/**
 * Servicio para obtener avisos de productos.
 * Los avisos son la representación de productos para mostrar en la tiendad
 */
public interface AvisoService {
    
    /**
     * Obtiene todos los avisos disponibles (todos los productos)
     * 
     * @return lista de avisos
     */
    List<Aviso> getTodosLosAvisos();
    
    /**
     * Obtiene los avisos de una categoría específica
     * 
     * @param categoriaId ID de la categoría
     * @return lista de avisos de la categoría
     */
    List<Aviso> getAvisosPorCategoria(Long categoriaId);
}