package id.ac.ui.cs.advprog.gamesappsstore.exceptions.CRUDAppData;

public class AppDataDoesNotExistException extends RuntimeException{
    public AppDataDoesNotExistException(){super("Tidak App dengan id tersebut");}
}
