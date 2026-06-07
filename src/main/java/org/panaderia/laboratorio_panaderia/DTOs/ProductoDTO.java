package org.panaderia.laboratorio_panaderia.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data // Lombok: Genera getters, setters, etc.
@NoArgsConstructor // Lombok: Constructor vacío
@AllArgsConstructor // Lombok: Constructor con todos los campos
@Builder // Lombok: Permite usar el patrón Builder para mapeos rápidos
public class ProductoDTO {
    private Long id;
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @PositiveOrZero(message = "El precio debe ser mayor o igual a cero")
    private BigDecimal precio;

    @NotNull(message = "El stock es obligatorio")
    @PositiveOrZero(message = "El stock debe ser mayor o igual a cero")
    private Integer stock;

    // VALIDACIÓN NUEVA: Validamos que nos manden el ID de la categoría asociada
    @NotNull(message = "El ID de la categoría es obligatorio")
    private Long categoriaId;

    private Boolean estado = true; // nace activo
}
