package org.panaderia.laboratorio_panaderia.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private Integer stock;

    // RELACIÓN: Muchos productos pertenecen a una Categoría
    @ManyToOne(fetch = FetchType.LAZY) // LAZY evita cargar la categoría de golpe si no la necesitas, optimizando memoria
    @JoinColumn(name = "categoria_id", nullable = false) // Crea la columna 'categoria_id' como FK en la tabla productos
    private Categoria categoria;

    @Builder.Default
    @Column(nullable = false)
    private Boolean estado = true;
}

