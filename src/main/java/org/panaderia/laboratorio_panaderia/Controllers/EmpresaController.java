package org.panaderia.laboratorio_panaderia.Controllers;

import jakarta.validation.Valid;
import org.panaderia.laboratorio_panaderia.DTOs.EmpresaDTO;
import org.panaderia.laboratorio_panaderia.Services.EmpresaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    public ResponseEntity<EmpresaDTO> obtenerInformacion() {
        return ResponseEntity.ok(empresaService.obtenerInformacionEmpresa());
    }

    @PostMapping
    public ResponseEntity<EmpresaDTO> registrarOActualizar(@Valid @RequestBody EmpresaDTO dto) {
        // @Valid intercepta el JSON y evalúa las anotaciones de validación del DTO
        EmpresaDTO guardada = empresaService.guardarOActualizarEmpresa(dto);
        return ResponseEntity.ok(guardada);
    }
}