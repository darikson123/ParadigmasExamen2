package co.edu.poli.Examen2.aplicacion.casosuso;

import co.edu.poli.Examen2.aplicacion.decorador.CodigoUnicoRepositorioDecorador;
import co.edu.poli.Examen2.aplicacion.observador.PublicadorEventos;
import co.edu.poli.Examen2.aplicacion.puertos.salida.ArchivoExamenesPuerto;
import co.edu.poli.Examen2.aplicacion.puertos.salida.ExamenRepositorioPuerto;
import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;
import co.edu.poli.Examen2.dominio.modelo.FactorRH;
import co.edu.poli.Examen2.dominio.modelo.TipoSangre;
import co.edu.poli.Examen2.infraestructura.salida.memoria.RepositorioExamenesMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CasosDeUsoTest {

    /** Doble de prueba del puerto de archivo que guarda los datos en memoria. */
    private static class ArchivoEnMemoria implements ArchivoExamenesPuerto {
        private List<ExamenSangre> contenido = new ArrayList<>();

        @Override
        public void escribir(List<ExamenSangre> examenes) {
            contenido = new ArrayList<>(examenes);
        }

        @Override
        public List<ExamenSangre> leer() {
            return new ArrayList<>(contenido);
        }
    }

    private ExamenRepositorioPuerto repositorio;
    private ArchivoEnMemoria archivo;
    private PublicadorEventos publicador;
    private List<String> mensajes;
    private RegistrarExamenServicio registrar;

    @BeforeEach
    void preparar() {
        repositorio = new CodigoUnicoRepositorioDecorador(new RepositorioExamenesMemoria());
        archivo = new ArchivoEnMemoria();
        publicador = new PublicadorEventos();
        mensajes = new ArrayList<>();
        publicador.suscribir(mensajes::add);
        registrar = new RegistrarExamenServicio(repositorio, publicador);
    }

    @Test
    void registrarGuardaYNotifica() {
        ExamenSangre examen = registrar.registrar("EXM-001", "Ana", 120, TipoSangre.B, FactorRH.POSITIVO);

        assertEquals("EXM-001", examen.getCodigo());
        assertEquals(1, repositorio.listarTodos().size());
        assertEquals(List.of("Examen EXM-001 registrado correctamente."), mensajes);
    }

    @Test
    void registrarDatosInvalidosNoGuardaNiNotifica() {
        assertThrows(IllegalArgumentException.class,
                () -> registrar.registrar("", "Ana", 120, TipoSangre.B, FactorRH.POSITIVO));

        assertTrue(repositorio.listarTodos().isEmpty());
        assertTrue(mensajes.isEmpty());
    }

    @Test
    void registrarCodigoDuplicadoLanzaExcepcion() {
        registrar.registrar("EXM-001", "Ana", 120, TipoSangre.B, FactorRH.POSITIVO);

        assertThrows(IllegalArgumentException.class,
                () -> registrar.registrar("EXM-001", "Luis", 90, TipoSangre.O, FactorRH.NEGATIVO));
    }

    @Test
    void listarDevuelveLosExamenesRegistrados() {
        registrar.registrar("EXM-001", "Ana", 120, TipoSangre.B, FactorRH.POSITIVO);
        registrar.registrar("EXM-002", "Luis", 90, TipoSangre.O, FactorRH.NEGATIVO);

        List<ExamenSangre> lista = new ListarExamenesServicio(repositorio).listar();

        assertEquals(2, lista.size());
        assertThrows(UnsupportedOperationException.class, () -> lista.add(lista.get(0)));
    }

    @Test
    void serializarEscribeTodosYNotifica() {
        registrar.registrar("EXM-001", "Ana", 120, TipoSangre.B, FactorRH.POSITIVO);
        mensajes.clear();

        int cantidad = new SerializarExamenesServicio(repositorio, archivo, publicador).serializar();

        assertEquals(1, cantidad);
        assertEquals(1, archivo.leer().size());
        assertEquals(List.of("Se serializaron 1 exámenes."), mensajes);
    }

    @Test
    void deserializarReemplazaElContenidoYNotifica() {
        registrar.registrar("EXM-001", "Ana", 120, TipoSangre.B, FactorRH.POSITIVO);
        new SerializarExamenesServicio(repositorio, archivo, publicador).serializar();
        repositorio.reemplazarTodos(List.of());
        mensajes.clear();

        int cantidad = new DeserializarExamenesServicio(repositorio, archivo, publicador).deserializar();

        assertEquals(1, cantidad);
        assertEquals("EXM-001", repositorio.listarTodos().get(0).getCodigo());
        assertEquals(List.of("Se deserializaron 1 exámenes."), mensajes);
    }
}
