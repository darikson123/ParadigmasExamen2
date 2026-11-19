package co.edu.poli.Examen2.aplicacion.observador;

import java.util.ArrayList;
import java.util.List;

/**
 * Sujeto del patrón <b>Observer</b>. Mantiene la lista de observadores y les
 * notifica los eventos producidos por los casos de uso, sin que estos conozcan
 * quién los recibe (por ejemplo, la interfaz JavaFX).
 */
public class PublicadorEventos {

    private final List<ObservadorExamen> observadores = new ArrayList<>();

    /**
     * Registra un observador.
     *
     * @param observador observador a registrar
     */
    public void suscribir(ObservadorExamen observador) {
        if (observador != null && !observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    /**
     * Elimina un observador.
     *
     * @param observador observador a eliminar
     */
    public void desuscribir(ObservadorExamen observador) {
        observadores.remove(observador);
    }

    /**
     * Notifica un evento a todos los observadores registrados.
     *
     * @param mensaje descripción del evento
     */
    public void notificar(String mensaje) {
        for (ObservadorExamen observador : new ArrayList<>(observadores)) {
            observador.actualizar(mensaje);
        }
    }
}
