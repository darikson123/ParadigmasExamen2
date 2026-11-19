package co.edu.poli.Examen2.infraestructura.salida.memoria;

import co.edu.poli.Examen2.aplicacion.puertos.salida.ExamenRepositorioPuerto;
import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Adaptador de salida que guarda los exámenes en una lista en memoria.
 */
public class RepositorioExamenesMemoria implements ExamenRepositorioPuerto {

    private final List<ExamenSangre> examenes = new ArrayList<>();

    @Override
    public void guardar(ExamenSangre examen) {
        examenes.add(examen);
    }

    @Override
    public List<ExamenSangre> listarTodos() {
        return List.copyOf(examenes);
    }

    @Override
    public Optional<ExamenSangre> buscarPorCodigo(String codigo) {
        return examenes.stream()
                .filter(examen -> examen.getCodigo().equals(codigo))
                .findFirst();
    }

    @Override
    public void reemplazarTodos(List<ExamenSangre> nuevos) {
        examenes.clear();
        examenes.addAll(nuevos);
    }
}
