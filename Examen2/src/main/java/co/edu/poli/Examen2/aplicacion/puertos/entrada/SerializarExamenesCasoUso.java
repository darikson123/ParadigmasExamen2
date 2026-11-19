package co.edu.poli.Examen2.aplicacion.puertos.entrada;

/**
 * Puerto de entrada: guardar en archivo los exámenes registrados (botón Serializar).
 */
public interface SerializarExamenesCasoUso {

    /**
     * Serializa todos los exámenes registrados.
     *
     * @return cantidad de exámenes serializados
     * @throws java.io.UncheckedIOException si ocurre un error de escritura
     */
    int serializar();
}
