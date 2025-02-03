package com.alura.comex.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Pedido {

    private String categoria;
    private String producto;
    private String cliente;

    private BigDecimal precio;
    private int cantidad;

    private LocalDate fecha;

    public Pedido(String categoria, String producto, String cliente, BigDecimal precio, int cantidad, LocalDate fecha) {
        this.categoria = categoria;
        this.producto = producto;
        this.cliente = cliente;
        this.precio = precio;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getProducto() {
        return producto;
    }

    public String getCliente() {
        return cliente;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    //nuevos métodos

    //retorna el total del pedido (precio*cantidad)
    public BigDecimal getValorTotal() {
        return this.precio.multiply(BigDecimal.valueOf(this.cantidad));
    }
    //compara el pedido más barato
    public boolean isMasBaratoQue(Pedido otroPedido) {
        return this.getValorTotal().compareTo(otroPedido.getValorTotal()) < 0;
    }
    //compara el pedido mas caro
    public boolean isMasCaroQue(Pedido otroPedido) {
        return this.getValorTotal().compareTo(otroPedido.getValorTotal()) > 0;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "categoria='" + categoria + '\'' +
                ", producto='" + producto + '\'' +
                ", cliente='" + cliente + '\'' +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                ", fecha=" + fecha +
                '}';
    }

}
