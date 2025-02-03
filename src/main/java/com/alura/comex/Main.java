package com.alura.comex;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.*;

import com.alura.comex.domain.Pedido;
import com.alura.comex.service.informes.InformeClientesFieles;
import com.alura.comex.service.informes.InformeSintetico;
import com.alura.comex.service.informes.InformeVentasPorCategoria;
import com.alura.comex.service.informes.InformeProductosMasVendidos;
import com.alura.comex.service.procesador.ProcesadorDeArchivos;
import com.alura.comex.service.procesador.ProcesadorDeCsv;
import com.alura.comex.service.procesador.ProcesadorDeJson;
import com.alura.comex.service.procesador.ProcesadorDeXml;


public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        // Lectura del archivo cxv
        //ProcesadorDeArchivos procesador = new ProcesadorDeCsv();
        //List<Pedido> pedidos = procesador.procesar("pedidos.csv");

        // lectura de json
        //ProcesadorDeArchivos procesador = new ProcesadorDeJson();
        //List<Pedido> pedidos = procesador.procesar("pedidos.json");

        // lectura de xml
        ProcesadorDeArchivos procesador = new ProcesadorDeXml();
        List<Pedido> pedidos = procesador.procesar("pedidos.xml");

        // Nueva clase InformeSintetico que hará todos los cálculos
        InformeSintetico informe = new InformeSintetico(pedidos);

        System.out.println("#### INFORME DE VALORES TOTALES");
        System.out.printf("- TOTAL DE PEDIDOS REALIZADOS: %s\n", informe.getTotalDePedidosRealizados());
        System.out.printf("- TOTAL DE PRODUCTOS VENDIDOS: %s\n", informe.getTotalDeProductosVendidos());
        System.out.printf("- TOTAL DE CATEGORIAS: %s\n", informe.getTotalDeCategorias());
        System.out.printf("- MONTO DE VENTAS: %s\n", NumberFormat.getCurrencyInstance(new Locale("es", "AR")).format(informe.getMontoDeVentas().setScale(2, RoundingMode.HALF_DOWN))); //Pueden cambiar el Locale a la moneda de su pais, siguiendo esta documentación: https://www.oracle.com/java/technologies/javase/java8locales.html
        System.out.printf("- PEDIDO MAS BARATO: %s (%s)\n", NumberFormat.getCurrencyInstance(new Locale("es", "AR")).format(informe.getPedidoMasBarato().getValorTotal().setScale(2, RoundingMode.HALF_DOWN)), informe.getPedidoMasBarato().getProducto());
        System.out.printf("- PEDIDO MAS CARO: %s (%s)\n", NumberFormat.getCurrencyInstance(new Locale("es", "AR")).format(informe.getPedidoMasCaro().getValorTotal().setScale(2, RoundingMode.HALF_DOWN)), informe.getPedidoMasCaro().getProducto());

        // Informe de clientes fieles
        InformeClientesFieles informeClientesFieles = new InformeClientesFieles(pedidos);
        informeClientesFieles.imprimir();

        // informe de ventas por categoria
        InformeVentasPorCategoria informePorCategoria = new InformeVentasPorCategoria(pedidos);
        informePorCategoria.imprimir();

        // Crea el informe de productos más vendidos
        InformeProductosMasVendidos informeTopProductos = new InformeProductosMasVendidos(pedidos);
        informeTopProductos.imprimir();
    }
}
