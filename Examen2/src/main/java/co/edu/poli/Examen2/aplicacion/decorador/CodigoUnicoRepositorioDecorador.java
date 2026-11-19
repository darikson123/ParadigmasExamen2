package co.edu.poli.Examen2.aplicacion.decorador;

import co.edu.poli.Examen2.aplicacion.puertos.salida.ExamenRepositorioPuerto;
import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;

/**
 * Decorador concreto que impide guardar dos exámenes con el mismo código.
 * Funciona con cualquier implementación de {@link ExamenRepositorioPuerto}.
 */
public class CodigoUnicoRepositorioDecorador extends RepositorioExamenDecorador {

    /**
     * @param envuelto repositorio a decorar
     */
    public CodigoUnicoRepositorioDecorador(ExamenRepositorioPuerto envuelto) {
        super(envuelto);
    }

    /**
     * Guarda el examen solo si su código no está registrado.
     *
     * @param examen examen a guardar
     * @throws IllegalArgumentException si ya existe un examen con el mismo código
     */
    @Override
    public void guardar(ExamenSangre examen) {
        if (envuelto.buscarPorCodigo(examen.getCodigo()).isPresent()) {
            throw new IllegalArgumentException(
                    "Ya existe un examen con el código " + examen.getCodigo() + ".");
        }
        super.guardar(examen);
    }
}
