package cl.patrones.taller.u2.catalogo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cl.patrones.taller.u2.catalogo.domain.Clasificacion;
import cl.patrones.taller.u2.catalogo.repository.ClasificacionRepository;

/**
 * Implementación del servicio de clasificaciones.
 */
@Service
public class ClasificacionServiceImpl implements ClasificacionService {
    
    private final ClasificacionRepository clasificacionRepository;
    
    public ClasificacionServiceImpl(ClasificacionRepository clasificacionRepository) {
        this.clasificacionRepository = clasificacionRepository;
    }
    
    @Override
    public List<Clasificacion> getClasificaciones() {
        return clasificacionRepository.findAll();
    }
    
    @Override
    public List<Clasificacion> getClasificacionesPorCategoria(Long categoriaId) {
        return clasificacionRepository.findAll().stream()
            .filter(c -> c.getCategoria() != null 
                      && c.getCategoria().getId().equals(categoriaId))
            .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Clasificacion> getClasificacionPorSku(String sku) {
        return clasificacionRepository.findAll().stream()
            .filter(c -> c.getSku().equals(sku))
            .findFirst();
    }
}