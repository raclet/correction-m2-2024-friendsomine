package friendsofmine.m2.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Activite {

    @NotNull
    @Size(min = 1, max = 256)
    private String titre;

    private String descriptif;

    public Activite(String titre, String descriptif) {
        this.titre = titre;
        this.descriptif = descriptif;
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
}
