package co.edu.poli.Examen2.aplicacion.casosuso;

import co.edu.poli.Examen2.aplicacion.puertos.entrada.ListarExamenesCasoUso;
import co.edu.poli.Examen2.aplicacion.puertos.salida.ExamenRepositorioPuerto;
import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;

import java.util.List;
import java.util.Objects;

/**
 * Caso de uso que lista los exámenes registrados.
 */
public class ListarExamenesServicio implements ListarExamenesCasoUso {

    private final ExamenRepositorioPuerto repositorio;

    /**
     * @param repositorio puerto de almacenamiento
     */
    public ListarExamenesServicio(ExamenRepositorioPuerto repositorio) {
        this.repositorio = Objects.requireNonNull(repositorio);
    }

    @Override
    public List<ExamenSangre> listar() {
        return List.copyOf(repositorio.listarTodos());
    }
}
