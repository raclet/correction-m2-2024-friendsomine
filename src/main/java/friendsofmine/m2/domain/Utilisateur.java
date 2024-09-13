package friendsofmine.m2.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Utilisateur {

    @NotNull @Size(min = 1)
    private String nom;

    @NotNull @Size(min = 1)
    private String prenom;

    @NotNull @Email
    private String email;

    @NotNull @Pattern(regexp = "^[MF]{1}$")
    private String sexe;

    public Utilisateur(String unNom, String unPrenom, String unEmail,
                       String unSexe) {
        nom = unNom;
        prenom = unPrenom;
        email = unEmail;
        sexe = unSexe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
}
