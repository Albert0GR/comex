package com.alura.comex.service.informes;

import com.alura.comex.domain.Pedido;
import com.alura.comex.service.procesador.ProcesadorDeArchivos;
import com.alura.comex.service.procesador.ProcesadorDeCsv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InformeProductosMasCarosPorCategoriaTest {

    private static final String NOMBRE_ARCHIVO_CSV = "pedidos.csv"; // Nombre del archivo CSV
    private static List<Pedido> pedidosDesdeCSV;

    @BeforeAll
    static void cargarPedidosDesdeCSV() throws IOException {
        ProcesadorDeArchivos procesador = new ProcesadorDeCsv(); // Usa la implementación concreta
        pedidosDesdeCSV = procesador.procesar(NOMBRE_ARCHIVO_CSV);
    }

    @Test
    void testGenerarInformeDesdeCSV() {
        // 1) Generar el informe con los pedidos procesados desde el CSV
        InformeProductosMasCarosPorCategoria informe = new InformeProductosMasCarosPorCategoria(pedidosDesdeCSV);

        // 2) Obtener la lista de productos más caros por categoría
        List<InformeProductosMasCarosPorCategoria.RegistroCategoria> registros = informe.getRegistros();

        // 3) Verificar que se generaron 5 categorias
        assertEquals(5, registros.size(), "El informe debe contener los productos más caros de 5 categorías.");

        // 4) Validar los productos mas caros por categoria con el informe main
        assertAll("Verificación de los productos más caros en cada categoría",
                () -> {
                    assertEquals("AUTOMOTOR", registros.get(0).getCategoria());
                    assertEquals("Juego de neumáticos", registros.get(0).getProducto());
                    assertEquals(new BigDecimal("219024.52"), registros.get(0).getPrecio());
                },
                () -> {
                    assertEquals("CELULARES", registros.get(1).getCategoria());
                    assertEquals("iPhone 16 Pro", registros.get(1).getProducto());
                    assertEquals(new BigDecimal("1574079.55"), registros.get(1).getPrecio());
                },
                () -> {
                    assertEquals("INFORMÁTICA", registros.get(2).getCategoria());
                    assertEquals("Macbook Pro 16", registros.get(2).getProducto());
                    assertEquals(new BigDecimal("5446836.73"), registros.get(2).getPrecio());
                },
                () -> {
                    assertEquals("LIBROS", registros.get(3).getCategoria());
                    assertEquals("Building Microservices", registros.get(3).getProducto());
                    assertEquals(new BigDecimal("51510.96"), registros.get(3).getPrecio());
                },
                () -> {
                    assertEquals("MUEBLES", registros.get(4).getCategoria());
                    assertEquals("Mesa para cenar 6 lugares", registros.get(4).getProducto());
                    assertEquals(new BigDecimal("631089.94"), registros.get(4).getPrecio());
                }
        );
    }

    @Test
    void testGenerarInformeConUnSoloPedido() {
        // 1) Crear una lista con un solo pedido
        Pedido pedidoUnico = new Pedido("ELECTRÓNICA", "TV Samsung 55", "ClienteX",
                new BigDecimal("320000.00"), 1, java.time.LocalDate.now());
        List<Pedido> pedidos = List.of(pedidoUnico);

        // 2) Generar el informe con un solo pedido
        InformeProductosMasCarosPorCategoria informe = new InformeProductosMasCarosPorCategoria(pedidos);

        // 3) Obtener la lista de productos más caros por categoria
        List<InformeProductosMasCarosPorCategoria.RegistroCategoria> registros = informe.getRegistros();

        // 4) Verificar que solo haya un producto en el informe
        assertEquals(1, registros.size(), "El informe debe contener solo una categoría.");

        // 5) Validar que el único producto sea el esperado
        assertEquals("ELECTRÓNICA", registros.get(0).getCategoria());
        assertEquals("TV Samsung 55", registros.get(0).getProducto());
        assertEquals(new BigDecimal("320000.00"), registros.get(0).getPrecio());
    }

    @Test
    void testGenerarInformeConListaVacia() {
        // 1) Crear un informe con una lista vacía
        InformeProductosMasCarosPorCategoria informe = new InformeProductosMasCarosPorCategoria(List.of());

        // 2) Verificar que el informe está vacío
        assertTrue(informe.getRegistros().isEmpty(), "El informe debe estar vacío cuando no hay pedidos.");
    }
}
