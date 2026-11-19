package co.edu.poli.Examen2.infraestructura.salida.archivo;

import co.edu.poli.Examen2.aplicacion.puertos.salida.ArchivoExamenesPuerto;
import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Adaptador de salida que serializa los exámenes en un archivo binario
 * mediante {@link ObjectOutputStream} y {@link ObjectInputStream}.
 */
public class SerializadorExamenesArchivo implements ArchivoExamenesPuerto {

    private final Path ruta;

    /**
     * @param ruta ruta del archivo donde se guardan los exámenes
     */
    public SerializadorExamenesArchivo(Path ruta) {
        this.ruta = Objects.requireNonNull(ruta);
    }

    @Override
    public void escribir(List<ExamenSangre> examenes) {
        try (ObjectOutputStream salida = new ObjectOutputStream(Files.newOutputStream(ruta))) {
            salida.writeObject(new ArrayList<>(examenes));
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo escribir el archivo " + ruta.getFileName() + ".", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ExamenSangre> leer() {
        if (!Files.exists(ruta)) {
            throw new UncheckedIOException("El archivo " + ruta.getFileName()
                    + " no existe. Serialice primero.", new NoSuchFileException(ruta.toString()));
        }
        try (ObjectInputStream entrada = new ObjectInputStream(Files.newInputStream(ruta))) {
            return new ArrayList<>((List<ExamenSangre>) entrada.readObject());
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo leer el archivo " + ruta.getFileName() + ".", e);
        } catch (ClassNotFoundException | ClassCastException e) {
            throw new UncheckedIOException("El archivo " + ruta.getFileName()
                    + " no contiene exámenes válidos.", new IOException(e));
        }
    }
}
