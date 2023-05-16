package id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp;

public class InvalidVersionException extends RuntimeException{
    public InvalidVersionException(){super("Version harus merupakan dalam format 'ANGKA.ANGKA.ANGKA'");}
}
