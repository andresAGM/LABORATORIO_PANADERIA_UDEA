package org.panaderia.laboratorio_panaderia.Services;

import jakarta.transaction.Transactional;
import org.panaderia.laboratorio_panaderia.DTOs.ProductoDTO;
import org.panaderia.laboratorio_panaderia.Models.Producto;
import org.panaderia.laboratorio_panaderia.Repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    // Usamos 'final' para garantizar la inmutabilidad
    private final ProductoRepository productoRepository;

    // Inyección por constructor (Forma recomendada sobre @Autowired)
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Transactional // Asegura que la operación sea transaccional en la BD
    public ProductoDTO registrarProducto(ProductoDTO dto) {

        // 1. Mapear de DTO a Entidad utilizando el Builder de Lombok
        Producto producto = Producto.builder()
                .nombre(dto.getNombre())
                .precio(dto.getPrecio())
                .estado(dto.getEstado())
                .stock(dto.getStock())
                .build();

        // 2. Guardar la entidad en MySQL
        Producto productoGuardado = productoRepository.save(producto);

        // 3. Convertir la entidad guardada de vuelta a DTO para la respuesta de la API
        return ProductoDTO.builder()
                .id(productoGuardado.getId())
                .nombre(productoGuardado.getNombre())
                .precio(productoGuardado.getPrecio())
                .estado(productoGuardado.getEstado())
                .stock(productoGuardado.getStock())
                .build();
    }

    @Transactional // Optimiza la consulta en modo lectura
    public List<ProductoDTO> obtenerTodosLosProductos() {
        List<Producto> productos = productoRepository.findAll();

        // Convertimos la lista de Entidades a una lista de DTOs usando Stream API de Java
        return productos.stream()
                .map(producto -> ProductoDTO.builder()
                        .id(producto.getId())
                        .nombre(producto.getNombre())
                        .precio(producto.getPrecio())
                        .estado(producto.getEstado())
                        .stock(producto.getStock())
                        .build())
                .collect(Collectors.toList());
    }

    // MÉTODO 2: Obtener un producto por ID
    @Transactional
    public ProductoDTO obtenerProductoPorId(Long id) {
        // Si no encuentra el ID, lanza una excepción (puedes personalizarla después)
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + id));

        // Mapeamos la entidad encontrada a DTO
        return ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .precio(producto.getPrecio())
                .estado(producto.getEstado())
                .stock(producto.getStock())
                .build();
    }
}
