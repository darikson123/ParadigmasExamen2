package co.edu.poli.Examen2.aplicacion.puertos.salida;

import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: almacenamiento de los exámenes mientras la aplicación se ejecuta.
 */
public interface ExamenRepositorioPuerto {

    /**
     * @param examen examen a almacenar
     */
    void guardar(ExamenSangre examen);

    /**
     * @return todos los exámenes almacenados
     */
    List<ExamenSangre> listarTodos();

    /**
     * @param codigo código a buscar
     * @return el examen con ese código, si existe
     */
    Optional<ExamenSangre> buscarPorCodigo(String codigo);

    /**
     * Reemplaza todo el contenido del repositorio.
     *
     * @param examenes nuevos exámenes
     */
    void reemplazarTodos(List<ExamenSangre> examenes);
}
