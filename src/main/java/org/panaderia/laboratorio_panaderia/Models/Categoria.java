package org.panaderia.laboratorio_panaderia.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true) // El nombre de la categoría no se debe repetir
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @Builder.Default
    @Column(nullable = false)
    private Boolean estado = true; // Activa por defecto
}
