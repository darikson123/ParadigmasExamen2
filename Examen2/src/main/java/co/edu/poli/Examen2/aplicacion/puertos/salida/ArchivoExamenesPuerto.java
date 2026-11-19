package co.edu.poli.Examen2.aplicacion.puertos.salida;

import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;

import java.util.List;

/**
 * Puerto de salida: persistencia de los exámenes en un archivo.
 */
public interface ArchivoExamenesPuerto {

    /**
     * Escribe los exámenes en el archivo, sobrescribiendo su contenido.
     *
     * @param examenes exámenes a escribir
     * @throws java.io.UncheckedIOException si ocurre un error de escritura
     */
    void escribir(List<ExamenSangre> examenes);

    /**
     * Lee los exámenes guardados en el archivo.
     *
     * @return exámenes leídos
     * @throws java.io.UncheckedIOException si el archivo no existe o no se puede leer
     */
    List<ExamenSangre> leer();
}
