package com.alura.comex.service.informes;
import com.alura.comex.domain.Pedido;

import java.util.*;
import java.util.stream.Collectors;

public class InformeProductosMasVendidos {

    /**
     * Clase interna para almacenar el nombre de un producto
     * y la cantidad total vendida de ese producto.
     */
    private static class RegistroProducto {
        private final String nombreProducto;
        private final int cantidadVendida;

        public RegistroProducto(String nombreProducto, int cantidadVendida) {
            this.nombreProducto = nombreProducto;
            this.cantidadVendida = cantidadVendida;
        }

        public String getNombreProducto() {
            return nombreProducto;
        }

        public int getCantidadVendida() {
            return cantidadVendida;
        }
    }

    // Almacenará los 3 productos con mayor cantidad vendida
    private final List<RegistroProducto> topTresProductos;

    /**
     * Constructor que, al recibir la lista de pedidos,
     * determina los 3 productos con mayor cantidad vendida.
     */
    public InformeProductosMasVendidos(List<Pedido> pedidos) {
        // 1. Agrupar por producto y sumar cantidades
        //    key = nombre del producto, value = cantidad vendida total
        Map<String, Integer> cantidadPorProducto = pedidos.stream()
                .collect(Collectors.groupingBy(
                        Pedido::getProducto,
                        Collectors.summingInt(Pedido::getCantidad)
                ));

        // 2. Ordenar de mayor a menor por cantidad
        // 3. Tomar los primeros 3
        this.topTresProductos = cantidadPorProducto.entrySet().stream()
                // ordena por la cantidad vendida (value) en forma descendente
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(3)
                // transformar cada Entry en nuestro RegistroProducto
                .map(entry -> new RegistroProducto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Imprime el informe por consola con el formato solicitado.
     */
    public void imprimir() {
        for (RegistroProducto rp : topTresProductos) {
            System.out.printf("PRODUTO: %s\n", rp.getNombreProducto());
            System.out.printf("QUANTIDADE: %d\n", rp.getCantidadVendida());
        }
    }
}
