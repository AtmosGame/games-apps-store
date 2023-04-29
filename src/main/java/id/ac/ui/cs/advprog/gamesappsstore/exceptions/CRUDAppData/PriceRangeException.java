package id.ac.ui.cs.advprog.gamesappsstore.exceptions.CRUDAppData;

public class PriceRangeException extends RuntimeException{
    public PriceRangeException(){super("Harga app harus berada pada rentang >= 0 dan <= 1000000");}
}
