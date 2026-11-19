package co.edu.poli.Examen2.infraestructura.salida.memoria;

import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;
import co.edu.poli.Examen2.dominio.modelo.FactorRH;
import co.edu.poli.Examen2.dominio.modelo.TipoSangre;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RepositorioExamenesMemoriaTest {

    private ExamenSangre examen(String codigo) {
        return ExamenSangre.builder().codigo(codigo).nombrePaciente("Paciente")
                .costo(50).tipoSangre(TipoSangre.AB).factorRH(FactorRH.POSITIVO).build();
    }

    @Test
    void guardaYBuscaPorCodigo() {
        RepositorioExamenesMemoria repositorio = new RepositorioExamenesMemoria();
        repositorio.guardar(examen("EXM-001"));

        assertTrue(repositorio.buscarPorCodigo("EXM-001").isPresent());
        assertFalse(repositorio.buscarPorCodigo("EXM-999").isPresent());
    }

    @Test
    void listarTodosDevuelveCopiaInmutable() {
        RepositorioExamenesMemoria repositorio = new RepositorioExamenesMemoria();
        repositorio.guardar(examen("EXM-001"));

        List<ExamenSangre> lista = repositorio.listarTodos();

        assertThrows(UnsupportedOperationException.class, () -> lista.add(examen("EXM-002")));
    }

    @Test
    void reemplazarTodosSustituyeElContenido() {
        RepositorioExamenesMemoria repositorio = new RepositorioExamenesMemoria();
        repositorio.guardar(examen("EXM-001"));

        repositorio.reemplazarTodos(List.of(examen("EXM-002"), examen("EXM-003")));

        assertEquals(2, repositorio.listarTodos().size());
        assertFalse(repositorio.buscarPorCodigo("EXM-001").isPresent());
    }
}
