package org.panaderia.laboratorio_panaderia.Repository;

import org.panaderia.laboratorio_panaderia.Models.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    // Patrón DAO: Extrae directamente la única configuración existente
    Optional<Empresa> findFirstByOrderByIdAsc();
}
