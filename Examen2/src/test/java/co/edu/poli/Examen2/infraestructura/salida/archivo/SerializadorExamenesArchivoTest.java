package co.edu.poli.Examen2.infraestructura.salida.archivo;

import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;
import co.edu.poli.Examen2.dominio.modelo.FactorRH;
import co.edu.poli.Examen2.dominio.modelo.TipoSangre;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SerializadorExamenesArchivoTest {

    @TempDir
    Path carpeta;

    @Test
    void escribeYLeeLosMismosExamenes() {
        SerializadorExamenesArchivo serializador =
                new SerializadorExamenesArchivo(carpeta.resolve("examenes.dat"));
        ExamenSangre examen = ExamenSangre.builder().codigo("EXM-001").nombrePaciente("Ana")
                .costo(75.5).tipoSangre(TipoSangre.A).factorRH(FactorRH.NEGATIVO).build();

        serializador.escribir(List.of(examen));
        List<ExamenSangre> leidos = serializador.leer();

        assertEquals(1, leidos.size());
        ExamenSangre leido = leidos.get(0);
        assertEquals("EXM-001", leido.getCodigo());
        assertEquals("Ana", leido.getNombrePaciente());
        assertEquals(75.5, leido.getCosto());
        assertEquals(TipoSangre.A, leido.getTipoSangre());
        assertEquals(FactorRH.NEGATIVO, leido.getFactorRH());
    }

    @Test
    void leerArchivoInexistenteLanzaExcepcion() {
        SerializadorExamenesArchivo serializador =
                new SerializadorExamenesArchivo(carpeta.resolve("no-existe.dat"));

        assertThrows(UncheckedIOException.class, serializador::leer);
    }

    @Test
    void leerArchivoCorruptoLanzaExcepcion() throws IOException {
        Path ruta = carpeta.resolve("corrupto.dat");
        Files.writeString(ruta, "esto no es un objeto serializado");

        assertThrows(UncheckedIOException.class, new SerializadorExamenesArchivo(ruta)::leer);
    }
}
