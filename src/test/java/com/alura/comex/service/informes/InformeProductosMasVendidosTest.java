package com.alura.comex.service.informes;

import com.alura.comex.domain.Pedido;
import com.alura.comex.service.procesador.ProcesadorDeArchivos;
import com.alura.comex.service.procesador.ProcesadorDeCsv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InformeProductosMasVendidosTest {

    private static final String NOMBRE_ARCHIVO_CSV = "pedidos.csv"; // Nombre del archivo CSV
    private static List<Pedido> pedidosDesdeCSV;

    /**
     * Utiliza `ProcesadorDeArchivos` para leer los pedidos desde el CSV.
     */
    @BeforeAll
    static void cargarPedidosDesdeCSV() throws IOException {
        ProcesadorDeArchivos procesador = new ProcesadorDeCsv(); // Usa la implementación concreta
        pedidosDesdeCSV = procesador.procesar(NOMBRE_ARCHIVO_CSV);
    }

    @Test
    void testGenerarInformeDesdeCSV() {
        // 1) Generar el informe con los pedidos procesados desde el CSV
        InformeProductosMasVendidos informe = new InformeProductosMasVendidos(pedidosDesdeCSV);

        // 2) Obtener la lista de los 3 productos más vendidos
        List<InformeProductosMasVendidos.RegistroProducto> productosMasVendidos = informe.getTopTresProductos();

        // 3) Verificar que el informe generó exactamente 3 productos
        assertEquals(3, productosMasVendidos.size(), "El informe debe contener los 3 productos más vendidos.");

        // 4) Validar los productos esperados (según los datos de `pedidos.csv`)
        assertAll("Verificación de los productos más vendidos",
                () -> {
                    assertEquals("iPhone 16 Pro", productosMasVendidos.get(0).getNombreProducto());
                    assertEquals(6, productosMasVendidos.get(0).getCantidadVendida());
                },
                () -> {
                    assertEquals("Galaxy S24 Ultra", productosMasVendidos.get(1).getNombreProducto());
                    assertEquals(5, productosMasVendidos.get(1).getCantidadVendida());
                },
                () -> {
                    assertEquals("Galaxy Tab S10", productosMasVendidos.get(2).getNombreProducto());
                    assertEquals(4, productosMasVendidos.get(2).getCantidadVendida());
                }
        );
    }

    @Test
    void testGenerarInformeConUnSoloPedido() {
        // 1) Crear una lista con un solo pedido
        Pedido pedidoUnico = new Pedido("INFORMÁTICA", "Macbook Pro 16", "ClienteX",
                new java.math.BigDecimal("5446836.73"), 1, java.time.LocalDate.now());
        List<Pedido> pedidos = List.of(pedidoUnico);

        // 2) Generar el informe con un solo pedido
        InformeProductosMasVendidos informe = new InformeProductosMasVendidos(pedidos);

        // 3) Obtener la lista de productos más vendidos
        List<InformeProductosMasVendidos.RegistroProducto> productosMasVendidos = informe.getTopTresProductos();

        // 4) Verificar que solo haya un producto en el informe
        assertEquals(1, productosMasVendidos.size(), "El informe debe contener solo un producto.");

        // 5) Validar que el único producto sea el esperado
        assertEquals("Macbook Pro 16", productosMasVendidos.get(0).getNombreProducto());
        assertEquals(1, productosMasVendidos.get(0).getCantidadVendida());
    }
}
