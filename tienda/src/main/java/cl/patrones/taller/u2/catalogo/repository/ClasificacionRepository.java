package cl.patrones.taller.u2.catalogo.repository;

import org.springframework.data.repository.ListCrudRepository;

import cl.patrones.taller.u2.catalogo.domain.Clasificacion;



public interface ClasificacionRepository extends ListCrudRepository<Clasificacion, Long> {
    
}