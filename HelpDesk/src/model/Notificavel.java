package model;

public interface Notificavel {
    String getNome();
    String getEmail();
    void receberAlerta(String mensagem);
}