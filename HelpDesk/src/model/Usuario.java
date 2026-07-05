package model;

public abstract class Usuario implements Notificavel {
    private String nome;
    private String email;

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    @Override
    public String getNome() { return nome; }

    @Override
    public String getEmail() { return email; }
}