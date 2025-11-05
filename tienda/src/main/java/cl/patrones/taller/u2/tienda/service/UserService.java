package cl.patrones.taller.u2.tienda.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import cl.patrones.taller.u2.tienda.adapter.Usuario;
import cl.patrones.taller.u2.tienda.adapter.UsuarioAnonimo;


@Service
public class UserService {

     // @return Usuario el usuario actual (autenticado o anónimo), nunca null
     
    public Usuario getCurrentUser() {
        // Obtener el contexto de seguridad de Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Verificar si hay una autenticación válida y si está autenticada
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            
            // Verificar si el principal es un Usuario
            // (puede ser "anonymousUser" string si es anónimo)
            if (principal instanceof Usuario) {
                return (Usuario) principal;
            }
        }
        
        // Si no hay usuario autenticado, retornar un UsuarioAnonimo
        // Patrón Null Object en acción: nunca retornamos null
        return new UsuarioAnonimo();
    }
    
    /**
     * Verifica si el usuario actual está autenticadox
     * @return true si el usuario está autenticado, false si es anónimo
     */
    public boolean isAuthenticated() {
        Usuario usuario = getCurrentUser();
        return !(usuario instanceof UsuarioAnonimo);
    }
}