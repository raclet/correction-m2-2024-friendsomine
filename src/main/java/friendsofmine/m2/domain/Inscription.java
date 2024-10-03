package friendsofmine.m2.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    private Utilisateur participant;

    @NotNull
    @ManyToOne
    private Activite activite;

    private Date dateInscription;

    public Inscription(Utilisateur utilisateur, Activite activite, Date dateInscription) {
        this.activite = activite;
        this.participant = utilisateur;
        this.dateInscription = dateInscription;
    }

    public Inscription(Utilisateur utilisateur, Activite activite) {
        this(utilisateur, activite, null);
    }

    public Inscription() { }

    public Utilisateur getParticipant() {
        return participant;
    }

    public void setParticipant(Utilisateur utilisateur) {
        this.participant = utilisateur;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    @PrePersist
    public void updateInscriptionDate() {
        if(dateInscription == null)
            setDateInscription(new Date());
    }

    @Override
    public String toString() {
        return "Inscription{" +
                ", participant=" + participant +
                ", activite=" + activite +
                '}';
    }

    public Long getId() {
        return id;
    }

}
