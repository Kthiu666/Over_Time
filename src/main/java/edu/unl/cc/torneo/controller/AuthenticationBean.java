package edu.unl.cc.torneo.controller;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;
import jakarta.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Define el bean administrado con nombre "authBean" y ámbito de sesión
@Named("authBean")
@SessionScoped
public class AuthenticationBean implements Serializable {

    private String username; // Nombre de usuario ingresado
    private String password; // Contraseña ingresada
    private boolean userLoggedIn = false; // Bandera de estado de sesión

    // Lista estática de usuarios registrados
    private static List<User> registeredUsers = new ArrayList<>();

    // Bloque estático para agregar un usuario de prueba al inicio
    static {
        // Usuario de demostración por defecto
        registeredUsers.add(new User("Admin", "admin@example.com", "admin", "1234"));
    }

    // Método para autenticar usuario
    public String login() {
        // Se itera mediante los usuarios registrados para encontrar una coincidencia
        for (User user : registeredUsers) {
            // Verifica si el nombre de usuario y contraseña coinciden
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                this.userLoggedIn = true; // Marca al usuario como autenticado
                // Muestra mensaje de éxito en la interfaz
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "¡Bienvenido, " + username + "!"));
                return "index.xhtml?faces-redirect=true"; // Redirecciona al índice
            }
        }

        // Si no se encuentra registro, muestra mensaje de error
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o contraseña incorrectos."));
        return null; // Permanece en la misma página
    }

    // Método para cerrar sesión
    public String logout() {
        // Invalida la sesión actual
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.userLoggedIn = false; // Reinicia el estado de sesión
        // Muestra mensaje de cierre de sesión
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Sesión cerrada correctamente."));
        return "login.xhtml?faces-redirect=true"; // Redirecciona a login
    }

    // Getters y setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isUserLoggedIn() { return userLoggedIn; }

    // Método para agregar usuarios al sistema (registro)
    public static void addRegisteredUser(User user) {
        // Verifica que no sea nulo y que el nombre de usuario no esté en uso
        if (user != null && !isUsernameTaken(user.getUsername())) {
            registeredUsers.add(user); // Agrega el usuario
            System.out.println("User registered: " + user.getUsername()); // Muestra en consola (PRUEBA)
        }
    }

    // Método para comprobar si el nombre de usuario ya está en uso
    public static boolean isUsernameTaken(String username) {
        for (User user : registeredUsers) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true; // Usuario ya existe
            }
        }
        return false; // Usuario disponible
    }

    // Clase interna estática que representa un usuario
    public static class User implements Serializable {
        private String name;
        private String email;
        private String username;
        private String password;

        // Constructor
        public User(String name, String email, String username, String password) {
            this.name = name;
            this.email = email;
            this.username = username;
            this.password = password;
        }

        // Getters
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getUsername() { return username; }
        public String getPassword() { return password; }
    }
}
