package org.panaderia.laboratorio_panaderia.Controllers;

import jakarta.validation.Valid;
import org.panaderia.laboratorio_panaderia.DTOs.VentaDTO;
import org.panaderia.laboratorio_panaderia.Services.VentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    public ResponseEntity<VentaDTO> registrarVenta(@Valid @RequestBody VentaDTO ventaDTO) {
        VentaDTO nuevaVenta = ventaService.registrarVenta(ventaDTO);
        return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
    }
}
