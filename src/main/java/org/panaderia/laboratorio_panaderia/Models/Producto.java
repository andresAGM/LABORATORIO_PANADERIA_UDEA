package org.panaderia.laboratorio_panaderia.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
@Data // Lombok: Genera Getters, Setters, toString, equals y hashCode automáticamente
@NoArgsConstructor // Lombok: Genera el constructor vacío requerido obligatoriamente por JPA
@AllArgsConstructor // Lombok: Genera un constructor con todos los atributos
@Builder // Lombok: Permite crear objetos de forma fluida (Producto.builder().nombre("...").build())
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Hace que el ID sea AUTO_INCREMENT en la base de datos
    private Long id;

    @Column(nullable = false, length = 100) // Campo obligatorio y con límite de caracteres
    private String nombre;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio; // Usamos BigDecimal por regla de negocio (manejo exacto de dinero)

    @Column(nullable = false)
    private Integer stock; // Cantidad disponible en el inventario de la panadería

    @Column(nullable = false, length = 50)
    private String categoria;

    @Column(nullable = false)
    private Boolean estado; // Representa si está Activo (true) o Inactivo (false)
}

