package co.edu.poli.Examen2.aplicacion.casosuso;

import co.edu.poli.Examen2.aplicacion.observador.PublicadorEventos;
import co.edu.poli.Examen2.aplicacion.puertos.entrada.RegistrarExamenCasoUso;
import co.edu.poli.Examen2.aplicacion.puertos.salida.ExamenRepositorioPuerto;
import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;
import co.edu.poli.Examen2.dominio.modelo.FactorRH;
import co.edu.poli.Examen2.dominio.modelo.TipoSangre;

import java.util.Objects;

/**
 * Caso de uso que registra un examen de sangre.
 */
public class RegistrarExamenServicio implements RegistrarExamenCasoUso {

    private final ExamenRepositorioPuerto repositorio;
    private final PublicadorEventos publicador;

    /**
     * @param repositorio puerto de almacenamiento
     * @param publicador  sujeto que notifica a los observadores
     */
    public RegistrarExamenServicio(ExamenRepositorioPuerto repositorio, PublicadorEventos publicador) {
        this.repositorio = Objects.requireNonNull(repositorio);
        this.publicador = Objects.requireNonNull(publicador);
    }

    @Override
    public ExamenSangre registrar(String codigo, String nombrePaciente, double costo,
                                  TipoSangre tipoSangre, FactorRH factorRH) {
        ExamenSangre examen = ExamenSangre.builder()
                .codigo(codigo)
                .nombrePaciente(nombrePaciente)
                .costo(costo)
                .tipoSangre(tipoSangre)
                .factorRH(factorRH)
                .build();
        repositorio.guardar(examen);
        publicador.notificar("Examen " + examen.getCodigo() + " registrado correctamente.");
        return examen;
    }
}
