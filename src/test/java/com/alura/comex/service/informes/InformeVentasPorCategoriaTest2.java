package com.alura.comex.service.informes;

import com.alura.comex.domain.Pedido;
import com.alura.comex.service.procesador.ProcesadorDeArchivos;
import com.alura.comex.service.procesador.ProcesadorDeCsv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InformeVentasPorCategoriaTest2 {

    public static final String NOMBRE_ARCHIVO_CSV = "pedidos.csv"; // Nombre del archivo CSV
    private static List<Pedido> pedidosDesdeCSV;


     //Utiliza la interfaz `ProcesadorDeArchivos` para leer los pedidos desde el CSV.

    @BeforeAll
    static void cargarPedidosDesdeCSV() throws IOException {
        ProcesadorDeArchivos procesador = new ProcesadorDeCsv(); // Usa la implementación concreta
        pedidosDesdeCSV = procesador.procesar(NOMBRE_ARCHIVO_CSV);
    }

    @Test
    void testGenerarInformeDesdeCSV() {
        // 1) Generar el informe con los pedidos procesados desde el CSV
        InformeVentasPorCategoria informe = new InformeVentasPorCategoria(pedidosDesdeCSV);

        // 2) Obtener la lista de registros generados
        List<InformeVentasPorCategoria.RegistroCategoria> registros = informe.getRegistros();

        // 3) Verificar que se generaron 5 categorias
        assertEquals(5, registros.size(), "El informe debe contener 5 categorías distintas.");

        // 4) Verificar los valores de cada categoria con als valores esperados
        assertAll("Verificación de categorías y valores",
                () -> {
                    assertEquals("AUTOMOTOR", registros.get(0).getCategoria());
                    assertEquals(2, registros.get(0).getCantidadVendida());
                    assertEquals("341022.55", registros.get(0).getMontoVendido().toPlainString());
                },
                () -> {
                    assertEquals("CELULARES", registros.get(1).getCategoria());
                    assertEquals(11, registros.get(1).getCantidadVendida());
                    assertEquals("16777173.20", registros.get(1).getMontoVendido().toPlainString());
                },
                () -> {
                    assertEquals("INFORMÁTICA", registros.get(2).getCategoria());
                    assertEquals(9, registros.get(2).getCantidadVendida());
                    assertEquals("11098564.57", registros.get(2).getMontoVendido().toPlainString());
                },
                () -> {
                    assertEquals("LIBROS", registros.get(3).getCategoria());
                    assertEquals(9, registros.get(3).getCantidadVendida());
                    assertEquals("258625.27", registros.get(3).getMontoVendido().toPlainString());
                },
                () -> {
                    assertEquals("MUEBLES", registros.get(4).getCategoria());
                    assertEquals(4, registros.get(4).getCantidadVendida());
                    assertEquals("2123514.97", registros.get(4).getMontoVendido().toPlainString());
                }
        );
    }

    @Test
    void testGenerarInformeConListaVacia() {
        // 1) Crear un informe con una lista vacía
        InformeVentasPorCategoria informe = new InformeVentasPorCategoria(List.of());

        // 2) Verificar que no se generaron registros
        assertTrue(informe.getRegistros().isEmpty(), "El informe debe estar vacío cuando no hay pedidos.");
    }
}
