package org.panaderia.laboratorio_panaderia.Repository;

import org.panaderia.laboratorio_panaderia.Models.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
}
