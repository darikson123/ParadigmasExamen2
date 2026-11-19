package co.edu.poli.Examen2.aplicacion.decorador;

import co.edu.poli.Examen2.aplicacion.puertos.salida.ExamenRepositorioPuerto;
import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Decorador base del patrón <b>Decorator</b> sobre el puerto
 * {@link ExamenRepositorioPuerto}. Delega todas las operaciones en el
 * repositorio envuelto; las subclases agregan comportamiento sin modificar
 * los adaptadores de salida.
 */
public abstract class RepositorioExamenDecorador implements ExamenRepositorioPuerto {

    /** Repositorio envuelto. */
    protected final ExamenRepositorioPuerto envuelto;

    /**
     * @param envuelto repositorio al que se delegan las operaciones
     */
    protected RepositorioExamenDecorador(ExamenRepositorioPuerto envuelto) {
        this.envuelto = Objects.requireNonNull(envuelto, "El repositorio envuelto es obligatorio.");
    }

    @Override
    public void guardar(ExamenSangre examen) {
        envuelto.guardar(examen);
    }

    @Override
    public List<ExamenSangre> listarTodos() {
        return envuelto.listarTodos();
    }

    @Override
    public Optional<ExamenSangre> buscarPorCodigo(String codigo) {
        return envuelto.buscarPorCodigo(codigo);
    }

    @Override
    public void reemplazarTodos(List<ExamenSangre> examenes) {
        envuelto.reemplazarTodos(examenes);
    }
}
