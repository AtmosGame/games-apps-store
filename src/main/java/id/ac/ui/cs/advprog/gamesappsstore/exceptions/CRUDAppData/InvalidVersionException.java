package id.ac.ui.cs.advprog.gamesappsstore.exceptions.CRUDAppData;

public class InvalidVersionException extends RuntimeException{
    public InvalidVersionException(){super("Version harus merupakan dalam format 'ANGKA.ANGKA.ANGKA'");}
}
