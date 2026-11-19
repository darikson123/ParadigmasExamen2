package co.edu.poli.Examen2.aplicacion.puertos.entrada;

/**
 * Puerto de entrada: recuperar desde archivo los exámenes guardados (botón Deserializar).
 */
public interface DeserializarExamenesCasoUso {

    /**
     * Lee los exámenes del archivo y reemplaza los registrados en memoria.
     *
     * @return cantidad de exámenes recuperados
     * @throws java.io.UncheckedIOException si el archivo no existe o no se puede leer
     */
    int deserializar();
}
