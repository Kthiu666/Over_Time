package edu.unl.cc.torneo.controller;

import jakarta.annotation.PostConstruct; // Se usa para ejecutar el método init() después de la construcción del bean
import jakarta.enterprise.context.SessionScoped; // O ViewScoped si solo es para esta página
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import jakarta.faces.context.FacesContext;
import jakarta.faces.application.FacesMessage;

// Define el bean administrado con nombre "estadisticasBean" y alcance de sesión
@Named("estadisticasBean")
@SessionScoped // Mantener SessionScoped si quieres que las estadísticas persistan al cambiar de vista
public class EstadisticasBean implements Serializable {

    private List<Equipo> tablaPosiciones; // Lista de equipos para la tabla de posiciones
    private List<Goleador> topGoleadores; // Nueva lista para goleadores

    // @Inject aquí si TorneoBean fuera un EJB o gestionado por CDI de otra forma
    // private TorneoBean torneoBean; // Si necesitas acceder a partidos de TorneoBean

    @PostConstruct
    public void init() {
        // Inicializar listas para evitar NullPointerExceptions
        tablaPosiciones = new ArrayList<>();
        topGoleadores = new ArrayList<>();

        // Datos de ejemplo para Tabla de Posiciones
        tablaPosiciones.add(new Equipo("Los Leones", 5, 3, 1, 1, 10));
        tablaPosiciones.add(new Equipo("Las Águilas", 5, 2, 2, 1, 8));
        tablaPosiciones.add(new Equipo("Los Tigres", 5, 1, 2, 2, 5));
        tablaPosiciones.add(new Equipo("Los Zorros", 5, 1, 1, 3, 4));

        // Datos de ejemplo para Top de Goleadores
        topGoleadores.add(new Goleador("Jugador A", "Los Leones", 7));
        topGoleadores.add(new Goleador("Jugador B", "Las Águilas", 5));
        topGoleadores.add(new Goleador("Jugador C", "Los Tigres", 5));
        topGoleadores.add(new Goleador("Jugador D", "Los Leones", 4));
        topGoleadores.add(new Goleador("Jugador E", "Los Zorros", 3));

        // Ordenar y asignar posiciones iniciales
        recalcularPosiciones();
        recalcularGoleadores();
    }

    // Método para simular recalcular posiciones de equipos
    public void recalcularPosiciones() {
        System.out.println("--- Recalculando posiciones de equipos ---");
        // En una aplicación real, se usarían los partidos jugados
        // Por ahora solo ordena la lista fija con criterios simples

        tablaPosiciones.sort(Comparator
                .comparingInt(Equipo::getPuntos).reversed()
                .thenComparingInt(Equipo::getGanados).reversed()
                .thenComparingInt(Equipo::getEmpatados).reversed()); // Criterio de desempate adicional

        // Asignar posiciones numéricas a cada equipo
        for (int i = 0; i < tablaPosiciones.size(); i++) {
            tablaPosiciones.get(i).setPosicion(i + 1);
        }

        // Notificación al usuario vía JSF
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Tabla de posiciones actualizada."));
        System.out.println("Tabla de posiciones recalculada y ordenada.");
    }

    // Método para simular recalcular el top de goleadores
    public void recalcularGoleadores() {
        System.out.println("--- Recalculando top de goleadores ---");
        // En una implementación real, se sumarían los goles desde los datos de partidos

        topGoleadores.sort(Comparator
                .comparingInt(Goleador::getGoles).reversed()
                .thenComparing(Goleador::getNombre)); // Desempate por nombre alfabético

        // Asignar posiciones en la tabla de goleadores
        for (int i = 0; i < topGoleadores.size(); i++) {
            topGoleadores.get(i).setPosicion(i + 1);
        }

        // Mensaje informativo en la interfaz JSF
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Top de goleadores actualizado."));
        System.out.println("Top de goleadores recalculado y ordenado.");
    }

    // Getter para tabla de posiciones
    public List<Equipo> getTablaPosiciones() {
        return tablaPosiciones;
    }

    // Getter para top de goleadores
    public List<Goleador> getTopGoleadores() {
        return topGoleadores;
    }

    // --- Clase interna para representar un equipo ---
    public static class Equipo implements Serializable {
        private int posicion;
        private String nombre;
        private int jugados;
        private int ganados;
        private int empatados;
        private int perdidos;
        private int puntos;

        // Constructor completo
        public Equipo(String nombre, int jugados, int ganados, int empatados, int perdidos, int puntos) {
            this.nombre = nombre;
            this.jugados = jugados;
            this.ganados = ganados;
            this.empatados = empatados;
            this.perdidos = perdidos;
            this.puntos = puntos;
        }

        // Getters y setters
        public int getPosicion() { return posicion; }
        public void setPosicion(int posicion) { this.posicion = posicion; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public int getJugados() { return jugados; }
        public void setJugados(int jugados) { this.jugados = jugados; }
        public int getGanados() { return ganados; }
        public void setGanados(int ganados) { this.ganados = ganados; }
        public int getEmpatados() { return empatados; }
        public void setEmpatados(int empatados) { this.empatados = empatados; }
        public int getPerdidos() { return perdidos; }
        public void setPerdidos(int perdidos) { this.perdidos = perdidos; }
        public int getPuntos() { return puntos; }
        public void setPuntos(int puntos) { this.puntos = puntos; }
    }

    // --- Clase interna para representar a un goleador ---
    public static class Goleador implements Serializable {
        private int posicion;
        private String nombre;
        private String equipo;
        private int goles;

        // Constructor completo
        public Goleador(String nombre, String equipo, int goles) {
            this.nombre = nombre;
            this.equipo = equipo;
            this.goles = goles;
        }

        // Getters y setters
        public int getPosicion() { return posicion; }
        public void setPosicion(int posicion) { this.posicion = posicion; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getEquipo() { return equipo; }
        public void setEquipo(String equipo) { this.equipo = equipo; }
        public int getGoles() { return goles; }
        public void setGoles(int goles) { this.goles = goles; }
    }
}
