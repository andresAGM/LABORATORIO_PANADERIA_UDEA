package org.panaderia.laboratorio_panaderia.Controllers;

import org.panaderia.laboratorio_panaderia.DTOs.ProductoDTO;
import org.panaderia.laboratorio_panaderia.Services.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos") // Ruta base para el recurso de productos
public class ProductoController {
    private final ProductoService productoService;

    // Inyección por constructor
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping // Maneja peticiones HTTP POST
    public ResponseEntity<ProductoDTO> registrarProducto(@RequestBody ProductoDTO productoDTO) {

        // Ejecutamos el registro a través del servicio
        ProductoDTO nuevoProducto = productoService.registrarProducto(productoDTO);

        // Retornamos el DTO con el código HTTP 201 (Created), que es la buena práctica para inserciones
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    // ENDPOINT 1: Listar todos los productos
    @GetMapping // Maneja peticiones HTTP GET a /api/productos
    public ResponseEntity<List<ProductoDTO>> listarProductos() {
        List<ProductoDTO> productos = productoService.obtenerTodosLosProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK); // Código 200 OK
    }

    // ENDPOINT 2: Buscar un producto específico por su ID
    @GetMapping("/{id}") // Maneja peticiones HTTP GET a /api/productos/{id} (ej: /api/productos/1)
    public ResponseEntity<ProductoDTO> buscarPorId(@PathVariable Long id) {
        ProductoDTO producto = productoService.obtenerProductoPorId(id);
        return new ResponseEntity<>(producto, HttpStatus.OK); // Código 200 OK
    }
}
