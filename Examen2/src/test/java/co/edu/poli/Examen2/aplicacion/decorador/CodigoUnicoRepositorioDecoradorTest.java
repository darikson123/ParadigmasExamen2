package co.edu.poli.Examen2.aplicacion.decorador;

import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;
import co.edu.poli.Examen2.dominio.modelo.FactorRH;
import co.edu.poli.Examen2.dominio.modelo.TipoSangre;
import co.edu.poli.Examen2.infraestructura.salida.memoria.RepositorioExamenesMemoria;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CodigoUnicoRepositorioDecoradorTest {

    private ExamenSangre examen(String codigo) {
        return ExamenSangre.builder().codigo(codigo).nombrePaciente("Paciente")
                .costo(100).tipoSangre(TipoSangre.O).factorRH(FactorRH.NEGATIVO).build();
    }

    @Test
    void guardaExamenesConCodigosDistintos() {
        CodigoUnicoRepositorioDecorador repositorio =
                new CodigoUnicoRepositorioDecorador(new RepositorioExamenesMemoria());

        repositorio.guardar(examen("EXM-001"));
        repositorio.guardar(examen("EXM-002"));

        assertEquals(2, repositorio.listarTodos().size());
    }

    @Test
    void rechazaCodigoDuplicado() {
        CodigoUnicoRepositorioDecorador repositorio =
                new CodigoUnicoRepositorioDecorador(new RepositorioExamenesMemoria());
        repositorio.guardar(examen("EXM-001"));

        assertThrows(IllegalArgumentException.class, () -> repositorio.guardar(examen("EXM-001")));
        assertEquals(1, repositorio.listarTodos().size());
    }

    @Test
    void delegaLasDemasOperacionesAlRepositorioEnvuelto() {
        RepositorioExamenesMemoria envuelto = new RepositorioExamenesMemoria();
        CodigoUnicoRepositorioDecorador repositorio = new CodigoUnicoRepositorioDecorador(envuelto);

        repositorio.reemplazarTodos(List.of(examen("EXM-010")));

        assertEquals(1, envuelto.listarTodos().size());
        assertTrue(repositorio.buscarPorCodigo("EXM-010").isPresent());
    }

    @Test
    void rechazaRepositorioEnvueltoNulo() {
        assertThrows(NullPointerException.class, () -> new CodigoUnicoRepositorioDecorador(null));
    }
}
