package friendsofmine.m2.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Activite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 256)
    private String titre;

    private String descriptif;

    // @NotNull
    @ManyToOne
    private Utilisateur responsable ;
    public Activite() {

    }

    public Activite(String titre, String descriptif, Utilisateur resp) {
        this.titre = titre;
        this.descriptif = descriptif;
        this.responsable = resp;
        if (resp != null)
            resp.addActivite(this);
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescriptif() {
        return descriptif;
    }

    public void setDescriptif(String descriptif) {
        this.descriptif = descriptif;
    }

    public Utilisateur getResponsable() {
        return responsable;
    }
    public void setResponsable(Utilisateur responsable) {
        this.responsable = responsable;
    }
    public Long getId() {
        return id;
    }
}
