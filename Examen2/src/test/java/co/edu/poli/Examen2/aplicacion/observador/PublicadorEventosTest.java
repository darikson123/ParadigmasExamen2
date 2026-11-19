package co.edu.poli.Examen2.aplicacion.observador;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PublicadorEventosTest {

    @Test
    void notificaATodosLosObservadores() {
        PublicadorEventos publicador = new PublicadorEventos();
        List<String> recibidos1 = new ArrayList<>();
        List<String> recibidos2 = new ArrayList<>();
        publicador.suscribir(recibidos1::add);
        publicador.suscribir(recibidos2::add);

        publicador.notificar("evento");

        assertEquals(List.of("evento"), recibidos1);
        assertEquals(List.of("evento"), recibidos2);
    }

    @Test
    void noNotificaAObservadorDesuscrito() {
        PublicadorEventos publicador = new PublicadorEventos();
        List<String> recibidos = new ArrayList<>();
        ObservadorExamen observador = recibidos::add;
        publicador.suscribir(observador);
        publicador.desuscribir(observador);

        publicador.notificar("evento");

        assertTrue(recibidos.isEmpty());
    }

    @Test
    void noDuplicaUnObservadorSuscritoDosVeces() {
        PublicadorEventos publicador = new PublicadorEventos();
        List<String> recibidos = new ArrayList<>();
        ObservadorExamen observador = recibidos::add;
        publicador.suscribir(observador);
        publicador.suscribir(observador);

        publicador.notificar("evento");

        assertEquals(1, recibidos.size());
    }
}
