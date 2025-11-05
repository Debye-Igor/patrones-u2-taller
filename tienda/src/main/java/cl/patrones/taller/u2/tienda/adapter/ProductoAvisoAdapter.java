package cl.patrones.taller.u2.tienda.adapter;

import cl.patrones.taller.u2.bodegaje.domain.Producto;
import cl.patrones.taller.u2.bodegaje.domain.Stock;
import cl.patrones.taller.u2.catalogo.domain.Aviso;
import cl.patrones.taller.u2.catalogo.domain.Categoria;

/**
 * Adaptador que convierte objetos Producto (del módulo bodegaje) 
 * en objetos Aviso (para mostrar en la tienda).
 */
public class ProductoAvisoAdapter {   
    /**
     * Convierte un Producto en un Aviso.
     * 
     * @param producto el producto a adaptar
     * @param categoria la categoría a asignar al aviso
     * @return un objeto Aviso con los datos del producto
     */
    public static Aviso adaptar(Producto producto, Categoria categoria) {
        if (producto == null) {
            return null;
        }
        
        // Calcular el stock total sumando todos los stocks del producto
        int stockTotal = producto.getStocks().stream()
            .mapToInt(Stock::getCantidad)
            .sum();
        
        // Calcular el precio (costo + margen de ganancia del 30%)
        Long precio = calcularPrecio(producto.getCosto());
        
        // Crear el aviso con los datos adaptados
        Aviso aviso = new Aviso();
        aviso.setId(producto.getId());
        aviso.setSku(producto.getSku());
        aviso.setTitulo(producto.getNombre());      // nombre -> titulo
        aviso.setPrecio(precio);                     // costo -> precio (con margen)
        aviso.setImagen(producto.getImagen());
        aviso.setStock(stockTotal);                  // suma de stocks -> stock
        aviso.setCategoria(categoria);
        
        return aviso;
    }
    
    /**
     * Calcula el precio de venta aplicando un margen de ganancia del 30% sobre el costo
     * @param costo el costo del producto
     * @return el precio de venta
     */
    private static Long calcularPrecio(Long costo) {
        if (costo == null) {
            return 0L;
        }
        // Aplicar margen de ganancia del 30% sobr eel costo
        return (long) (costo * 1.3);
    }
}