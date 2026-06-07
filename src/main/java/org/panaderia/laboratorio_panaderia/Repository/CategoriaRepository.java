package org.panaderia.laboratorio_panaderia.Repository;

import org.panaderia.laboratorio_panaderia.Models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Método personalizado útil para validar que no se registren nombres duplicados
    Optional<Categoria> findByNombreIgnoreCase(String nombre);
    // Buscar por id
    Optional<Categoria> findById(Long id);
}