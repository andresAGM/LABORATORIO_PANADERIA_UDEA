package org.panaderia.laboratorio_panaderia.DTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class DetalleVentaDTO {
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    private String productoNombre; // Opcional: Para mostrarlo en la respuesta al cliente

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a cero")
    private Integer cantidad;

    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}