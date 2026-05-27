package org.panaderia.laboratorio_panaderia.DTOs;

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
    private String nombre;
    private BigDecimal precio;
    private Boolean estado;
}
