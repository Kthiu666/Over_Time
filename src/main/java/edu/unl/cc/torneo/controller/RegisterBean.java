package edu.unl.cc.torneo.controller;

// Alcance por solicitud: el bean vive solo durante una petición HTTP
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;
import jakarta.faces.application.FacesMessage;

@Named("registerBean")
@RequestScoped
public class RegisterBean {

    private String name;
    private String email;
    private String username;
    private String password;
    private String confirmPassword;

    // Método que se llama al enviar el formulario de registro
    public String register() {
        // 1. Validar que la contraseña y la confirmación coincidan
        if (!password.equals(confirmPassword)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las contraseñas no coinciden."));
            return null; // Permanece en la misma página (registro)
        }

        // 2. Validar que el nombre de usuario no esté ya registrado
        if (AuthenticationBean.isUsernameTaken(username)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El nombre de usuario ya está en uso."));
            return null;
        }

        // 3. Crear un nuevo usuario y agregarlo a la lista estática de usuarios registrados
        AuthenticationBean.User newUser = new AuthenticationBean.User(name, email, username, password);
        AuthenticationBean.addRegisteredUser(newUser);

        // 4. Enviar mensaje de éxito (flash scope) y redirigir a login
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "¡Registro completado! Ahora puedes iniciar sesión."));

        return "login.xhtml?faces-redirect=true";
    }

    // Getters y Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}
