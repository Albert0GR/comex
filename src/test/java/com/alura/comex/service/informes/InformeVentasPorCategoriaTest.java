package com.alura.comex.service.informes;

import com.alura.comex.domain.Pedido;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class InformeVentasPorCategoriaTest {

    @Test
    public void testGenerarInformeConPedidos() {
        // Crear pedidos de prueba usando Mockito
        Pedido pedido1 = Mockito.mock(Pedido.class);
        when(pedido1.getCategoria()).thenReturn("INFORMÁTICA");
        when(pedido1.getCantidad()).thenReturn(1);
        when(pedido1.getValorTotal()).thenReturn(new BigDecimal("604346.37"));

        Pedido pedido2 = Mockito.mock(Pedido.class);
        when(pedido2.getCategoria()).thenReturn("MUEBLES");
        when(pedido2.getCantidad()).thenReturn(1);
        when(pedido2.getValorTotal()).thenReturn(new BigDecimal("428857.77"));

        // Crear la lista de pedidos
        List<Pedido> pedidos = Arrays.asList(pedido1, pedido2);

        // Instanciar el informe
        InformeVentasPorCategoria informe = new InformeVentasPorCategoria(pedidos);

        // Verificar que el informe tiene los registros correctos
        assertEquals(2, informe.getRegistros().size());

        // Primer registro -> "INFORMÁTICA"
        assertEquals("INFORMÁTICA", informe.getRegistros().get(0).getCategoria());
        assertEquals(1, informe.getRegistros().get(0).getCantidadVendida());
        assertEquals(new BigDecimal("604346.37"), informe.getRegistros().get(0).getMontoVendido());

        // Segundo registro -> "MUEBLES"
        assertEquals("MUEBLES", informe.getRegistros().get(1).getCategoria());
        assertEquals(1, informe.getRegistros().get(1).getCantidadVendida());
        assertEquals(new BigDecimal("428857.77"), informe.getRegistros().get(1).getMontoVendido());
    }

    @Test
    public void testGenerarInformeConListaVacia() {
        // Crear una lista vacía de pedidos
        List<Pedido> pedidos = Collections.emptyList();

        // Instanciar el informe
        InformeVentasPorCategoria informe = new InformeVentasPorCategoria(pedidos);

        // Verificar que el informe no tiene registros
        assertTrue(informe.getRegistros().isEmpty());
    }
}
