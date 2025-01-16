package com.alura.comex.service;

import com.alura.comex.CategoriasProcesadas;
import com.alura.comex.Pedido;

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

        CategoriasProcesadas categoriasProcesadas = new CategoriasProcesadas();

        for (int i = 0; i < pedidos.size(); i++) {
            Pedido pedidoActual = pedidos.get(i);

            if (pedidoActual == null) {
                break;
            }


            montoDeVentas = montoDeVentas.add(pedidoActual.getValorTotal());
            totalDeProductosVendidos += pedidoActual.getCantidad();


            if (!categoriasProcesadas.contains(pedidoActual.getCategoria())) {
                totalDeCategorias++;
                categoriasProcesadas.add(pedidoActual.getCategoria());
            }
        }


    }
}
