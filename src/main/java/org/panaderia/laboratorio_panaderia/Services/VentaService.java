package org.panaderia.laboratorio_panaderia.Services;

import org.panaderia.laboratorio_panaderia.DTOs.DetalleVentaDTO;
import org.panaderia.laboratorio_panaderia.DTOs.VentaDTO;
import org.panaderia.laboratorio_panaderia.Models.DetalleVenta;
import org.panaderia.laboratorio_panaderia.Models.Producto;
import org.panaderia.laboratorio_panaderia.Models.Venta;
import org.panaderia.laboratorio_panaderia.Repository.ProductoRepository;
import org.panaderia.laboratorio_panaderia.Repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    public VentaService(VentaRepository ventaRepository, ProductoRepository productoRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional // Asegura que si algo falla (ej. falta de stock), se cancele toda la operación (Rollback)
    public VentaDTO registrarVenta(VentaDTO ventaDTO) {

        // 1. Instanciar la entidad Venta Maestra
        Venta venta = Venta.builder()
                .fecha(LocalDateTime.now())
                .total(BigDecimal.ZERO)
                .detalles(new ArrayList<>())
                .build();

        BigDecimal totalVenta = BigDecimal.ZERO;
        List<DetalleVentaDTO> respuestaDetallesDTO = new ArrayList<>();

        // 2. Procesar cada detalle enviado por el cliente
        for (DetalleVentaDTO detalleDTO : ventaDTO.getDetalles()) {
            // Buscar producto e indicar error si no existe
            Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalleDTO.getProductoId()));

            // VALIDACIÓN DE STOCK:
            if (producto.getStock() < detalleDTO.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre() + ". Disponible: " + producto.getStock());
            }

            // Restar stock y actualizar producto
            producto.setStock(producto.getStock() - detalleDTO.getCantidad());
            productoRepository.save(producto);

            // Calcular subtotales
            BigDecimal precioUnitario = producto.getPrecio();
            BigDecimal subtotal = precioUnitario.multiply(new BigDecimal(detalleDTO.getCantidad()));
            totalVenta = totalVenta.add(subtotal);

            // Crear entidad Detalle
            DetalleVenta detalleVenta = DetalleVenta.builder()
                    .venta(venta)
                    .producto(producto)
                    .cantidad(detalleDTO.getCantidad())
                    .precioUnitario(precioUnitario)
                    .subtotal(subtotal)
                    .build();

            venta.getDetalles().add(detalleVenta);

            // Llenar el DTO de respuesta para el cliente
            DetalleVentaDTO resDetalle = new DetalleVentaDTO();
            resDetalle.setProductoId(producto.getId());
            resDetalle.setProductoNombre(producto.getNombre());
            resDetalle.setCantidad(detalleDTO.getCantidad());
            resDetalle.setPrecioUnitario(precioUnitario);
            resDetalle.setSubtotal(subtotal);
            respuestaDetallesDTO.add(resDetalle);
        }

        // 3. Asignar el total calculado y guardar la venta con sus detalles
        venta.setTotal(totalVenta);
        Venta ventaGuardada = ventaRepository.save(venta);

        // 4. Estructurar el DTO final de respuesta
        VentaDTO respuestaDTO = new VentaDTO();
        respuestaDTO.setId(ventaGuardada.getId());
        respuestaDTO.setFecha(ventaGuardada.getFecha());
        respuestaDTO.setTotal(ventaGuardada.getTotal());
        respuestaDTO.setDetalles(respuestaDetallesDTO);

        return respuestaDTO;
    }
}
