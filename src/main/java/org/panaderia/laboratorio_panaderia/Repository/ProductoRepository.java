package org.panaderia.laboratorio_panaderia.Repository;

import org.panaderia.laboratorio_panaderia.Models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductoRepository extends JpaRepository<Producto, Long>{
    // Ejemplo de un método personalizado (Query Method) automático de Spring Data:
    // Busca productos filtrando por su estado (activos/inactivos)
    List<Producto> findByEstado(Boolean estado);

    // Ejemplo de búsqueda por nombre aproximado (LIKE %nombre%)
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
