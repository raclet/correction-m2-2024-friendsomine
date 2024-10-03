package friendsofmine.m2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull @Size(min = 1)
    private String nom;

    @NotNull @Size(min = 1)
    private String prenom;

    @NotNull @Email
    private String email;

    @NotNull @Pattern(regexp = "^[MF]{1}$")
    private String sexe;

    @Version
    private Long version;

    @JsonIgnore // attention à choisir le bon import !!!
    @OneToMany(mappedBy = "responsable", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Collection<Activite> activites = new ArrayList<>();
    public Utilisateur() {}

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

    public Long getId() {
        return id;
    }
    public Collection<Activite> getActivites() {
        return activites;
    }
    public void setActivites(Collection<Activite> activites) {
        this.activites = activites;
    }
    public void addActivite(Activite activite) {
        if (!activites.contains(activite))
            activites.add(activite);
        activite.setResponsable(this);
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        if (nom != null ? !nom.equals(that.nom) : that.nom != null) return false;
        if (prenom != null ? !prenom.equals(that.prenom) : that.prenom != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return sexe != null ? sexe.equals(that.sexe) : that.sexe == null;
    }

}
