package edu.unl.cc.torneo.controller;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;
import jakarta.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named("torneoBean")
@SessionScoped
public class TorneoBean implements Serializable {

    private String nombreNuevoTorneo;
    private Date fechaInicioTorneo;
    private int numeroEquipos;

    private String equipoLocal;
    private int golesLocal;
    private String equipoVisitante;
    private int golesVisitante;
    private Date fechaPartido;

    private List<EstadisticasBean.Equipo> tablaPosiciones;

    private List<Partido> partidosRegistrados;

    @PostConstruct
    public void init() {
        tablaPosiciones = new ArrayList<>();
        partidosRegistrados = new ArrayList<>();

    }

    // Método para crear un nuevo torneo
    public void crearNuevoTorneo() {
        System.out.println("--- Entering crearNuevoTorneo() method ---");

        // Valida que todos los campos deben estar completos y mínimo 2 equipos
        if (nombreNuevoTorneo == null || nombreNuevoTorneo.trim().isEmpty() || fechaInicioTorneo == null || numeroEquipos < 2) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Por favor, complete todos los campos para crear el torneo."));
            System.out.println("Validation failed for crearNuevoTorneo.");
            return;
        }

        // SIMULACION DE "BASE DE DATOS"
        System.out.println("Creando torneo: " + nombreNuevoTorneo + " | Fecha: " + fechaInicioTorneo + " | Equipos: " + numeroEquipos);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "¡Torneo '" + nombreNuevoTorneo + "' creado con éxito!"));

        // Se limpian los campos del formulario para dejarlo listo para otro registro
        nombreNuevoTorneo = null;
        fechaInicioTorneo = null;
        numeroEquipos = 2;

        System.out.println("--- Exiting crearNuevoTorneo() method (success) ---");
    }

    // Método para registrar un partido
    public void registrarPartido() {
        System.out.println("--- Entering registrarPartido() method ---");

        // Validación: todos los campos requeridos deben estar presentes
        if (equipoLocal == null || equipoLocal.trim().isEmpty() || equipoVisitante == null || equipoVisitante.trim().isEmpty() || fechaPartido == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Por favor, complete todos los campos para registrar el partido."));
            System.out.println("Validation failed for registrarPartido: missing fields.");
            return;
        }

        // Validación: el equipo local y el visitante deben ser distintos
        if (equipoLocal.equalsIgnoreCase(equipoVisitante)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El equipo local y el visitante no pueden ser el mismo."));
            System.out.println("Validation failed for registrarPartido: same teams.");
            return;
        }

        // Se crea un nuevo partido y se agrega a la lista
        Partido nuevoPartido = new Partido(equipoLocal, golesLocal, equipoVisitante, golesVisitante, fechaPartido);
        partidosRegistrados.add(nuevoPartido);

        System.out.println("Partido registrado: " + equipoLocal + " " + golesLocal + " - " + golesVisitante + " " + equipoVisitante + " (" + fechaPartido + ")");
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "¡Partido registrado exitosamente!"));

        // Se limpian los campos del formulario después del registro
        equipoLocal = null;
        golesLocal = 0;
        equipoVisitante = null;
        golesVisitante = 0;
        fechaPartido = null;

        System.out.println("--- Exiting registrarPartido() method (success) ---");
    }

    // Getters y Setters para creación de torneo
    public String getNombreNuevoTorneo() { return nombreNuevoTorneo; }
    public void setNombreNuevoTorneo(String nombreNuevoTorneo) { this.nombreNuevoTorneo = nombreNuevoTorneo; }

    public Date getFechaInicioTorneo() { return fechaInicioTorneo; }
    public void setFechaInicioTorneo(Date fechaInicioTorneo) { this.fechaInicioTorneo = fechaInicioTorneo; }

    public int getNumeroEquipos() { return numeroEquipos; }
    public void setNumeroEquipos(int numeroEquipos) { this.numeroEquipos = numeroEquipos; }

    // Getters y Setters para registro de partido
    public String getEquipoLocal() { return equipoLocal; }
    public void setEquipoLocal(String equipoLocal) { this.equipoLocal = equipoLocal; }

    public int getGolesLocal() { return golesLocal; }
    public void setGolesLocal(int golesLocal) { this.golesLocal = golesLocal; }

    public String getEquipoVisitante() { return equipoVisitante; }
    public void setEquipoVisitante(String equipoVisitante) { this.equipoVisitante = equipoVisitante; }

    public int getGolesVisitante() { return golesVisitante; }
    public void setGolesVisitante(int golesVisitante) { this.golesVisitante = golesVisitante; }

    public Date getFechaPartido() { return fechaPartido; }
    public void setFechaPartido(Date fechaPartido) { this.fechaPartido = fechaPartido; }

    //Getters para listas
    public List<EstadisticasBean.Equipo> getTablaPosiciones() {
        return tablaPosiciones;
    }

    public List<Partido> getPartidosRegistrados() {
        return partidosRegistrados;
    }

    //Clase interna estática para representar un partido
    public static class Partido implements Serializable {
        private String equipoLocal;
        private int golesLocal;
        private String equipoVisitante;
        private int golesVisitante;
        private Date fecha;

        // Constructor
        public Partido(String equipoLocal, int golesLocal, String equipoVisitante, int golesVisitante, Date fecha) {
            this.equipoLocal = equipoLocal;
            this.golesLocal = golesLocal;
            this.equipoVisitante = equipoVisitante;
            this.golesVisitante = golesVisitante;
            this.fecha = fecha;
        }

        // Getters
        public String getEquipoLocal() { return equipoLocal; }
        public int getGolesLocal() { return golesLocal; }
        public String getEquipoVisitante() { return equipoVisitante; }
        public int getGolesVisitante() { return golesVisitante; }
        public Date getFecha() { return fecha; }
    }
}
