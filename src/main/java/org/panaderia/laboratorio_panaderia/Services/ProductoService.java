package org.panaderia.laboratorio_panaderia.Services;

import org.panaderia.laboratorio_panaderia.DTOs.ProductoDTO;
import org.panaderia.laboratorio_panaderia.Models.Categoria;
import org.panaderia.laboratorio_panaderia.Models.Producto;
import org.panaderia.laboratorio_panaderia.Repository.CategoriaRepository;
import org.panaderia.laboratorio_panaderia.Repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository; // Inyectamos el nuevo repositorio de categorías

    // El constructor recibe ambos repositorios automáticamente por inyección de dependencias
    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional // Mantiene el proceso transaccional de escritura por si falla la inserción
    public ProductoDTO registrarProducto(ProductoDTO dto) {

        // 1. Buscar la Categoría en MySQL usando el ID provisto por el DTO
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("No se puede registrar el producto porque la Categoría con ID " + dto.getCategoriaId() + " no existe."));

        // 2. Mapear de DTO a Entidad utilizando el Builder de Lombok
        Producto producto = Producto.builder()
                .nombre(dto.getNombre())
                .precio(dto.getPrecio())
                .stock(dto.getStock())
                .categoria(categoria) // Asignamos el objeto Categoria real e independiente
                // esta que viene del dto o true por defecto
                .estado(dto.getEstado() != null ? dto.getEstado() : true)
                .build();

        // 3. Guardar la entidad en MySQL
        Producto productoGuardado = productoRepository.save(producto);

        // 4. Convertir la entidad guardada de vuelta a DTO para la respuesta de la API
        return convertirEntidadADto(productoGuardado);
    }

    @Transactional(readOnly = true) // Optimiza la consulta en modo lectura
    public List<ProductoDTO> obtenerTodosLosProductos() {
        List<Producto> productos = productoRepository.findAll();

        // Convertimos la lista de Entidades a una lista de DTOs usando Stream API
        return productos.stream()
                .map(this::convertirEntidadADto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true) // Optimiza la consulta en modo lectura
    public ProductoDTO obtenerProductoPorId(Long id) {
        // Si no encuentra el ID, lanza una excepción controlada
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + id));

        // Mapeamos la entidad encontrada a DTO
        return convertirEntidadADto(producto);
    }

    /**
     * Método auxiliar privado (Siguiendo principios PUR) para centralizar y reutilizar
     * el mapeo de Entidad a DTO sin duplicar líneas de código con el Builder.
     */
    private ProductoDTO convertirEntidadADto(Producto producto) {
        return ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .estado(producto.getEstado())
                .categoriaId(producto.getCategoria().getId())          // Obtenemos el ID de la relación
                .build();
    }
}
