package org.panaderia.laboratorio_panaderia.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaDTO {

    private Long id;

    @NotBlank(message = "El nombre de la panadería es un campo obligatorio.")
    @Size(max = 150, message = "El nombre no puede exceder los 150 caracteres.")
    private String nombre;

    @NotBlank(message = "El NIT/RUC es obligatorio para la facturación de las ventas.")
    @Size(max = 20, message = "El NIT no puede exceder los 20 caracteres.")
    private String nit;

    @NotBlank(message = "La dirección física de la panadería es obligatoria.")
    @Size(max = 200, message = "La dirección no puede exceder los 200 caracteres.")
    private String direccion;

    @NotBlank(message = "El teléfono de contacto es obligatorio para los pedidos.")
    @Size(max = 20, message = "El teléfono no puede exceder los 20 caracteres.")
    private String telefono;

    @Email(message = "Debe ingresar un formato de correo electrónico válido (ejemplo@dominio.com).")
    @Size(max = 100, message = "El email no puede exceder los 100 caracteres.")
    private String email;

    @Size(max = 255, message = "El lema o pie de ticket no puede superar los 255 caracteres.")
    private String lema;
}