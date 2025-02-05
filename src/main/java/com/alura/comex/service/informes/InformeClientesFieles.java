package com.alura.comex.service.informes;

import com.alura.comex.domain.Pedido;
import com.alura.comex.interfaces.Informe;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

public class InformeClientesFieles implements Informe {

    private final Map<String, Long> pedidosPorCliente;

    /**
     * Constructor que, al recibir la lista de pedidos,
     * agrupa y cuenta cuántos pedidos realizó cada cliente.
     */
    public InformeClientesFieles(List<Pedido> pedidos) {
        this.pedidosPorCliente = pedidos.stream()
                // Se agrupa por cliente y contamos
                .collect(Collectors.groupingBy(
                        Pedido::getCliente,
                        Collectors.counting()
                ));
    }
    public Map<String, Long> getPedidosPorCliente() {
        return Collections.unmodifiableMap(this.pedidosPorCliente);
    }


    /**
     * Imprime en consola la cantidad de pedidos por cada cliente,
     * ordenados alfabéticamente por el nombre del cliente.
     */
    public void imprimir() {
        System.out.println("#### INFORME DE CLIENTES FIELES");

        pedidosPorCliente.entrySet().stream()
                // Ordenamos por clave (nombre del cliente) en orden alfabético
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    System.out.printf("NOMBRE: %s\n", entry.getKey());
                    System.out.printf("Nº DE PEDIDOS: %d\n\n", entry.getValue());
                });
    }
}
