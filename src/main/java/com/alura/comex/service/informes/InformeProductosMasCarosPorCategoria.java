package com.alura.comex.service.informes;
import com.alura.comex.domain.Pedido;


import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class InformeProductosMasCarosPorCategoria {

    /**
     * Clase interna para agrupar la información:
     * - categoría
     * - producto más caro
     * - precio de ese producto
     */
    static class RegistroCategoria {
        private final String categoria;
        private final String producto;
        private final BigDecimal precio;

        public RegistroCategoria(String categoria, String producto, BigDecimal precio) {
            this.categoria = categoria;
            this.producto = producto;
            this.precio = precio;
        }

        public String getCategoria() {
            return categoria;
        }

        public String getProducto() {
            return producto;
        }

        public BigDecimal getPrecio() {
            return precio;
        }
    }

    // Lista con los datos consolidados: el producto más caro por cada categoría
    private final List<RegistroCategoria> registros;

    /**
     * Constructor que, al recibir la lista de pedidos,
     * encuentra el producto con mayor precio en cada categoría.
     */
    public InformeProductosMasCarosPorCategoria(List<Pedido> pedidos) {

        // Agrupamos por categoría, y obtenemos el producto de mayor precio en cada categoría
        // usando un colector de tipo "maxBy" con un Comparator que compare el precio
        Map<String, Optional<Pedido>> masCaroPorCategoria = pedidos.stream()
                .collect(Collectors.groupingBy(
                        Pedido::getCategoria,
                        Collectors.maxBy(Comparator.comparing(Pedido::getPrecio))
                ));

        // Transformamos el Map en una lista de "RegistroCategoria", ordenada alfabéticamente
        this.registros = masCaroPorCategoria.entrySet().stream()
                // Filtramos los que tengan valor presente (por si hubiera una categoría vacía)
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> {
                    String categoria = entry.getKey();
                    Pedido pedidoMasCaro = entry.getValue().get();
                    return new RegistroCategoria(
                            categoria,
                            pedidoMasCaro.getProducto(),
                            pedidoMasCaro.getPrecio()
                    );
                })
                // Ordenamos alfabéticamente por categoría
                .sorted(Comparator.comparing(RegistroCategoria::getCategoria))
                .collect(Collectors.toList());
    }
//para el test
    public List<RegistroCategoria> getRegistros() {
        return Collections.unmodifiableList(this.registros);
    }


    /**
     * Imprime el informe por consola.
     * Formato:
     *
     * CATEGORIA: <categoria>
     * PRODUCTO: <producto>
     * PRECIO: <precio en formato moneda>
     */
    public void imprimir() {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
        System.out.println("#### INFORME DE PRODUCTOS MAS CAROS DE CADA CATEDORÍA");
        for (RegistroCategoria rc : registros) {
            System.out.printf("CATEGORIA: %s\n", rc.getCategoria());
            System.out.printf("PRODUCTO: %s\n", rc.getProducto());
            System.out.printf("PRECIO: %s\n\n", formatoMoneda.format(rc.getPrecio()));
        }
    }
}
