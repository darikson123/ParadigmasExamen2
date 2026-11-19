package co.edu.poli.Examen2.aplicacion.casosuso;

import co.edu.poli.Examen2.aplicacion.observador.PublicadorEventos;
import co.edu.poli.Examen2.aplicacion.puertos.entrada.SerializarExamenesCasoUso;
import co.edu.poli.Examen2.aplicacion.puertos.salida.ArchivoExamenesPuerto;
import co.edu.poli.Examen2.aplicacion.puertos.salida.ExamenRepositorioPuerto;
import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;

import java.util.List;
import java.util.Objects;

/**
 * Caso de uso que guarda en archivo los exámenes registrados.
 */
public class SerializarExamenesServicio implements SerializarExamenesCasoUso {

    private final ExamenRepositorioPuerto repositorio;
    private final ArchivoExamenesPuerto archivo;
    private final PublicadorEventos publicador;

    /**
     * @param repositorio puerto de almacenamiento en memoria
     * @param archivo     puerto de persistencia en archivo
     * @param publicador  sujeto que notifica a los observadores
     */
    public SerializarExamenesServicio(ExamenRepositorioPuerto repositorio,
                                      ArchivoExamenesPuerto archivo,
                                      PublicadorEventos publicador) {
        this.repositorio = Objects.requireNonNull(repositorio);
        this.archivo = Objects.requireNonNull(archivo);
        this.publicador = Objects.requireNonNull(publicador);
    }

    @Override
    public int serializar() {
        List<ExamenSangre> examenes = repositorio.listarTodos();
        archivo.escribir(examenes);
        publicador.notificar("Se serializaron " + examenes.size() + " exámenes.");
        return examenes.size();
    }
}
