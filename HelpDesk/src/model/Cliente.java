package model;

public class Cliente extends Usuario {
    public Cliente(String nome, String email) {
        super(nome, email);
    }

    @Override
    public void receberAlerta(String mensagem) {
        System.out.println("[E-mail enviado para " + getEmail() + "]: " + mensagem);
    }
}