// src/main/java/py/edu/uc/lp32025/domain/Avatar.java
package py.edu.uc.lp32025.domain;

public class Avatar {
    private String urlImagen; // Cambiado de Image a String para que funcione en JSON
    private String nick;

    public Avatar() {
    }

    // ✅ Constructor que faltaba (Soluciona "Cannot resolve constructor")
    public Avatar(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Avatar(String urlImagen, String nick) {
        this.urlImagen = urlImagen;
        this.nick = nick;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}