package com.alura.comex.service.informes;

import com.alura.comex.domain.Pedido;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class InformeVentasPorCategoria {

    
    private static class RegistroCategoria {
        private final String categoria;
        private final int cantidadVendida;
        private final BigDecimal montoVendido;

        public RegistroCategoria(String categoria, int cantidadVendida, BigDecimal montoVendido) {
            this.categoria = categoria;
            this.cantidadVendida = cantidadVendida;
            this.montoVendido = montoVendido;
        }

        public String getCategoria() {
            return categoria;
        }

        public int getCantidadVendida() {
            return cantidadVendida;
        }

        public BigDecimal getMontoVendido() {
            return montoVendido;
        }
    }

    // Lista con los datos consolidados por categoría
    private final List<RegistroCategoria> registros;

    /**
     * Constructor que, al recibir la lista de pedidos,
     * calcula para cada categoría:
     * - la cantidad total de productos vendidos
     * - el monto total vendido
     */
    public InformeVentasPorCategoria(List<Pedido> pedidos) {
        // Agrupamos los pedidos por categoría
        Map<String, List<Pedido>> pedidosPorCategoria = pedidos.stream()
                .collect(Collectors.groupingBy(Pedido::getCategoria));

        // Transformamos cada grupo de pedidos en un RegistroCategoria
        this.registros = pedidosPorCategoria.entrySet().stream()
                .map(entry -> {
                    String categoria = entry.getKey();
                    List<Pedido> pedidosDeEstaCategoria = entry.getValue();

                    // Cantidad total vendida (sumando las cantidades)
                    int cantidadVendida = pedidosDeEstaCategoria.stream()
                            .mapToInt(Pedido::getCantidad)
                            .sum();

                    // Monto total vendido (sumando el getValorTotal() de cada pedido)
                    BigDecimal montoVendido = pedidosDeEstaCategoria.stream()
                            .map(Pedido::getValorTotal)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return new RegistroCategoria(categoria, cantidadVendida, montoVendido);
                })
                // Ordenamos alfabéticamente por el nombre de la categoría
                .sorted(Comparator.comparing(RegistroCategoria::getCategoria))
                .collect(Collectors.toList());
    }


    public void imprimir() {
        // Preparar un NumberFormat para formatear el monto
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));

        System.out.println("#### INFORME DE VENTAS POR CATEGORÍA");
        for (RegistroCategoria registro : registros) {
            System.out.printf("CATEGORIA: %s\n", registro.getCategoria());
            System.out.printf("CANTIDAD VENDIDA: %d\n", registro.getCantidadVendida());
            System.out.printf("MONTO: %s\n\n",
                    formatoMoneda.format(registro.getMontoVendido()));
        }
    }
}
