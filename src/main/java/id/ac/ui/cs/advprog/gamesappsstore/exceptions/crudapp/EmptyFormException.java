package id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp;

public class EmptyFormException extends RuntimeException {
    public EmptyFormException(String form){super(form + " tidak boleh kosong");}
}
