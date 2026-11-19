package co.edu.poli.Examen2.aplicacion.puertos.entrada;

import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;

import java.util.List;

/**
 * Puerto de entrada: listar los exámenes registrados (botón Listar).
 */
public interface ListarExamenesCasoUso {

    /**
     * @return lista inmutable con los exámenes registrados
     */
    List<ExamenSangre> listar();
}
