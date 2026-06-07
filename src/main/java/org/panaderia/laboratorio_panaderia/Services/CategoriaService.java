package org.panaderia.laboratorio_panaderia.Services;

import org.panaderia.laboratorio_panaderia.DTOs.CategoriaDTO;
import org.panaderia.laboratorio_panaderia.Models.Categoria;
import org.panaderia.laboratorio_panaderia.Repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // CREATE: Registrar una categoría nueva
    @Transactional
    public CategoriaDTO registrarCategoria(CategoriaDTO dto) {
        // Regla de negocio: No permitir categorías con nombres repetidos
        if (categoriaRepository.findByNombreIgnoreCase(dto.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe una categoría registrada con el nombre: " + dto.getNombre());
        }

        Categoria categoria = Categoria.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescription())
                .estado(dto.getEstado() != null ? dto.getEstado() : true)
                .build();

        Categoria guardada = categoriaRepository.save(categoria);
        return convertirEntidadADto(guardada);
    }

    // READ: Obtener todas las categorías
    @Transactional(readOnly = true)
    public List<CategoriaDTO> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll().stream()
                .map(this::convertirEntidadADto)
                .collect(Collectors.toList());
    }

    // READ: Obtener una categoría por su ID
    @Transactional(readOnly = true)
    public CategoriaDTO obtenerCategoriaPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con el ID: " + id));
        return convertirEntidadADto(categoria);
    }

    // UPDATE: Modificar una categoría existente
    @Transactional
    public CategoriaDTO actualizarCategoria(Long id, CategoriaDTO dto) {
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar. Categoría no encontrada con ID: " + id));

        // Validar si el nuevo nombre ya lo tiene otra categoría distinta
        categoriaRepository.findByNombreIgnoreCase(dto.getNombre()).ifPresent(c -> {
            if (!c.getId().equals(id)) {
                throw new RuntimeException("Ya existe otra categoría con el nombre: " + dto.getNombre());
            }
        });

        categoriaExistente.setNombre(dto.getNombre());
        categoriaExistente.setDescripcion(dto.getDescription());
        categoriaExistente.setEstado(dto.getEstado());

        Categoria actualizada = categoriaRepository.save(categoriaExistente);
        return convertirEntidadADto(actualizada);
    }

    // DELETE: Eliminación física de la base de datos
    @Transactional
    public void eliminarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Categoría no encontrada con ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }

    // Mapeador auxiliar reutilizable de Entidad a DTO
    private CategoriaDTO convertirEntidadADto(Categoria categoria) {
        return CategoriaDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .description(categoria.getDescripcion())
                .estado(categoria.getEstado())
                .build();
    }
}
