package com.alura.comex.service.procesador;

import com.alura.comex.domain.Pedido;

import java.io.IOException;
import java.util.List;

public interface ProcesadorDeArchivos {
    List<Pedido> procesar(String nombreArchivo) throws IOException;
}
