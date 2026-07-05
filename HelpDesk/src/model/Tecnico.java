package model;

public class Tecnico extends Usuario {
    private String especialidade;

    public Tecnico(String nome, String email, String especialidade) {
        super(nome, email);
        this.especialidade = especialidade;
    }

    public String getEspecialidade() { return especialidade; }

    @Override
    public void receberAlerta(String mensagem) {
        System.out.println("[Painel do Técnico - " + getNome() + "]: " + mensagem);
    }
}