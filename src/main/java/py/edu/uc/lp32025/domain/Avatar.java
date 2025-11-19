// src/main/java/py/edu/uc/lp32025/domain/Avatar.java
package py.edu.uc.lp32025.domain;

import java.awt.Image; // O usa java.awt.image.BufferedImage si prefieres

public class Avatar {
    private Image imagen;
    private String nick;

    // Constructor vacío
    public Avatar() {
    }

    // Constructor con parámetros
    public Avatar(Image imagen, String nick) {
        this.imagen = imagen;
        this.nick = nick;
    }

    // Getters y Setters
    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public String toString() {
        return "Avatar{" +
                "nick='" + nick + '\'' +
                '}';
    }
}