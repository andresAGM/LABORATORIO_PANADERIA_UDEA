package org.panaderia.laboratorio_panaderia.DTOs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaDTO {
    private Long id;
    private LocalDateTime fecha;
    private BigDecimal total;

    @NotEmpty(message = "La venta debe contener al menos un producto")
    @Valid // Activa la validación interna para cada detalle de la lista
    private List<DetalleVentaDTO> detalles;
}