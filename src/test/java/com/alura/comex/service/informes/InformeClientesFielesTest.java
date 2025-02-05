package com.alura.comex.service.informes;

import com.alura.comex.domain.Pedido;
import com.alura.comex.service.procesador.ProcesadorDeArchivos;
import com.alura.comex.service.procesador.ProcesadorDeCsv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class InformeClientesFielesTest {

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
        InformeClientesFieles informe = new InformeClientesFieles(pedidosDesdeCSV);

        // 2) Obtener el mapa de clientes y sus números de pedidos
        Map<String, Long> pedidosPorCliente = informe.getPedidosPorCliente();

        // 3) Verificar que se generaron todos los clientes esperados
        assertEquals(6, pedidosPorCliente.size(), "El informe debe contener 6 clientes distintos.");

        // 4) Validar los clientes más fieles y su número de pedidos
        assertAll("Verificación de los clientes más fieles",
                () -> assertEquals(4, pedidosPorCliente.get("ANA"), "ANA debería tener 4 pedidos."),
                () -> assertEquals(4, pedidosPorCliente.get("DANI"), "DANI debería tener 4 pedidos."),
                () -> assertEquals(3, pedidosPorCliente.get("CAIO"), "CAIO debería tener 3 pedidos."),
                () -> assertEquals(3, pedidosPorCliente.get("BIA"), "BIA debería tener 2 pedidos."),
                () -> assertEquals(1, pedidosPorCliente.get("GABI"), "GABI debería tener 2 pedidos."),
                () -> assertEquals(1, pedidosPorCliente.get("ELI"), "ELI debería tener 1 pedido.")
        );
    }

    @Test
    void testGenerarInformeConUnSoloPedido() {
        // 1) Crear una lista con un solo pedido
        Pedido pedidoUnico = new Pedido("INFORMÁTICA", "Macbook Pro 16", "ClienteX",
                new java.math.BigDecimal("5446836.73"), 1, java.time.LocalDate.now());
        List<Pedido> pedidos = List.of(pedidoUnico);

        // 2) Generar el informe con un solo pedido
        InformeClientesFieles informe = new InformeClientesFieles(pedidos);

        // 3) Obtener el mapa de clientes y sus números de pedidos
        Map<String, Long> pedidosPorCliente = informe.getPedidosPorCliente();

        // 4) Verificar que solo haya un cliente en el informe
        assertEquals(1, pedidosPorCliente.size(), "El informe debe contener solo un cliente.");

        // 5) Validar que el único cliente sea el esperado con 1 pedido
        assertEquals(1, pedidosPorCliente.get("ClienteX"), "ClienteX debería tener 1 pedido.");
    }

    @Test
    void testGenerarInformeConListaVacia() {
        // 1) Crear un informe con una lista vacía
        InformeClientesFieles informe = new InformeClientesFieles(List.of());

        // 2) Obtener el mapa de clientes y sus números de pedidos
        Map<String, Long> pedidosPorCliente = informe.getPedidosPorCliente();

        // 3) Verificar que el informe está vacío
        assertTrue(pedidosPorCliente.isEmpty(), "El informe debe estar vacío cuando no hay pedidos.");
    }
}
