package com.alura.comex.service.procesador;

import com.alura.comex.domain.Pedido;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class ProcesadorDeCsv implements ProcesadorDeArchivos {

    @Override
    public List<Pedido> procesar(String nombreArchivo) throws IOException {

        List<Pedido> pedidos = new ArrayList<>();

        try {
            //se obtiene el recurso csv a partir del nombre proporcionado
            URL recursoCSV = ClassLoader.getSystemResource(nombreArchivo);
            //se obtiene la ruta del archivo
            Path caminoDelArchivo = caminoDelArchivo = Path.of(recursoCSV.toURI());
            //Scanner para leer lina por linea
            Scanner lectorDeLineas = new Scanner(caminoDelArchivo);

            lectorDeLineas.nextLine();

            int cantidadDeRegistros = 0;
            //se itera sobre cada linea del csv
            while (lectorDeLineas.hasNextLine()) {
                String linea = lectorDeLineas.nextLine();
                String[] registro = linea.split(",");

                String categoria = registro[0];
                String producto = registro[1];
                BigDecimal precio = new BigDecimal(registro[2]);
                int cantidad = Integer.parseInt(registro[3]);
                LocalDate fecha = LocalDate.parse(registro[4], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String cliente = registro[5];
                // Crea un nuevo objeto Pedido y lo agrega a la lista
                Pedido pedido = new Pedido(categoria, producto, cliente, precio, cantidad, fecha);
                pedidos.add(pedido);

                cantidadDeRegistros++;
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException("Archivo pedido.csv no localizado!");
        } catch (IOException e) {
            throw new RuntimeException("Error al abrir Scanner para procesar archivo!");
        }


    return pedidos;
    }


}
