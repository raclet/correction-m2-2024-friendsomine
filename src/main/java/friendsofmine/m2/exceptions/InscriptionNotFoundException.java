package friendsofmine.m2.exceptions;

public class InscriptionNotFoundException extends RuntimeException {

    public InscriptionNotFoundException(Long InscriptionId) {
        super("Could not find exception" + InscriptionId + ".");
    }

}