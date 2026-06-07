package org.panaderia.laboratorio_panaderia.Services;

import org.panaderia.laboratorio_panaderia.DTOs.EmpresaDTO;
import org.panaderia.laboratorio_panaderia.Models.Empresa;
import org.panaderia.laboratorio_panaderia.Repository.EmpresaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Transactional(readOnly = true)
    public EmpresaDTO obtenerInformacionEmpresa() {
        Empresa empresa = empresaRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("Configuración base no encontrada. Por favor, registre los datos de la panadería."));
        return deEntidadADto(empresa);
    }

    @Transactional
    public EmpresaDTO guardarOActualizarEmpresa(EmpresaDTO dto) {
        // Lógica de negocio idempotente: garantiza un único registro en MySQL
        Empresa empresaADeterminar = empresaRepository.findFirstByOrderByIdAsc()
                .map(existente -> {
                    existente.setNombre(dto.getNombre());
                    existente.setNit(dto.getNit());
                    existente.setDireccion(dto.getDireccion());
                    existente.setTelefono(dto.getTelefono());
                    existente.setEmail(dto.getEmail());
                    existente.setLema(dto.getLema());
                    return existente;
                })
                .orElseGet(() -> Empresa.builder()
                        .nombre(dto.getNombre())
                        .nit(dto.getNit())
                        .direccion(dto.getDireccion())
                        .telefono(dto.getTelefono())
                        .email(dto.getEmail())
                        .lema(dto.getLema())
                        .build());

        Empresa guardada = empresaRepository.save(empresaADeterminar);
        return deEntidadADto(guardada);
    }

    // Principios PUR: Reutilización de transformaciones de datos
    private EmpresaDTO deEntidadADto(Empresa empresa) {
        return EmpresaDTO.builder()
                .id(empresa.getId())
                .nombre(empresa.getNombre())
                .nit(empresa.getNit())
                .direccion(empresa.getDireccion())
                .telefono(empresa.getTelefono())
                .email(empresa.getEmail())
                .lema(empresa.getLema())
                .build();
    }
}