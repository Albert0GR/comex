package com.alura.comex.service.informes;

import com.alura.comex.domain.Pedido;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class InformeSintetico {

    private int totalDeProductosVendidos;
    private int totalDePedidosRealizados;
    private BigDecimal montoDeVentas;
    private Pedido pedidoMasBarato;
    private Pedido pedidoMasCaro;
    private int totalDeCategorias;

    public int getTotalDeProductosVendidos() {
        return totalDeProductosVendidos;
    }

    public void setTotalDeProductosVendidos(int totalDeProductosVendidos) {
        this.totalDeProductosVendidos = totalDeProductosVendidos;
    }

    public int getTotalDePedidosRealizados() {
        return totalDePedidosRealizados;
    }

    public void setTotalDePedidosRealizados(int totalDePedidosRealizados) {
        this.totalDePedidosRealizados = totalDePedidosRealizados;
    }

    public BigDecimal getMontoDeVentas() {
        return montoDeVentas;
    }

    public void setMontoDeVentas(BigDecimal montoDeVentas) {
        this.montoDeVentas = montoDeVentas;
    }

    public Pedido getPedidoMasBarato() {
        return pedidoMasBarato;
    }

    public void setPedidoMasBarato(Pedido pedidoMasBarato) {
        this.pedidoMasBarato = pedidoMasBarato;
    }

    public Pedido getPedidoMasCaro() {
        return pedidoMasCaro;
    }

    public void setPedidoMasCaro(Pedido pedidoMasCaro) {
        this.pedidoMasCaro = pedidoMasCaro;
    }

    public int getTotalDeCategorias() {
        return totalDeCategorias;
    }

    public void setTotalDeCategorias(int totalDeCategorias) {
        this.totalDeCategorias = totalDeCategorias;
    }

    public InformeSintetico(List<Pedido> pedidos) {
        //inicializacion de los atributos
        this.totalDeProductosVendidos = 0;
        this.totalDePedidosRealizados = 0;
        this.montoDeVentas = BigDecimal.ZERO;
        this.pedidoMasBarato = null;
        this.pedidoMasCaro = null;
        this.totalDeCategorias = 0;

        //Pedido mas barato
        this.pedidoMasBarato = pedidos
                .stream()
                .min(Comparator.comparing(Pedido::getValorTotal))
                .orElse(null);
        //Pedido mas caro
        this.pedidoMasCaro = pedidos
                .stream()
                .max(Comparator.comparing(Pedido::getValorTotal))
                .orElse(null);

        //Total de pedidos realizados (tamaño de la lista)
        this.totalDePedidosRealizados = pedidos.size();

        //total de productos vendidos (sumar "cantidad")
        this.totalDeProductosVendidos = pedidos
                .stream()
                .mapToInt(Pedido::getCantidad)
                .sum();

        //Monto de ventas total (sumar "precio * cantidad" de cada pedido, que
        // en nuestra clase se encapsula con getValorTotal())
        this.montoDeVentas = pedidos
                .stream()
                .map(Pedido::getValorTotal)             // Obtiene el valor total de cada pedido
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //total de categorías distintas
        this.totalDeCategorias = (int) pedidos
                .stream()
                .map(Pedido::getCategoria)   // extrae solo la categoría
                .distinct()                  // elimina duplicados
                .count();                    // cuenta cuántas categorías hay
/*
        CategoriasProcesadas categoriasProcesadas = new CategoriasProcesadas();

        for (int i = 0; i < pedidos.size(); i++) {
            Pedido pedidoActual = pedidos.get(i);

            if (pedidoActual == null) {
                break;
            }



            if (!categoriasProcesadas.contains(pedidoActual.getCategoria())) {
                totalDeCategorias++;
                categoriasProcesadas.add(pedidoActual.getCategoria());
            }
        }
*/

    }
}
