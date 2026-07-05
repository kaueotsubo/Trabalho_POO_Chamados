package model;

public class Chamado {
    private int id;
    private String titulo;
    private String descricao;
    private String solicitanteNome;
    private String tecnicoResponsavel;
    private StatusChamado status;

    public Chamado(int id, String titulo, String descricao, String clienteNome) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.solicitanteNome = clienteNome;
        this.status = StatusChamado.ABERTO;
        this.tecnicoResponsavel = "Não atribuído";
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public StatusChamado getStatus() { return status; }

    public void setStatus(StatusChamado status) { this.status = status; }
    public void setTecnicoResponsavel(String tecnicoResponsavel) {
        this.tecnicoResponsavel = tecnicoResponsavel;
    }

    @Override
    public String toString() {
        return "Chamado #" + id + " [" + status + "] - Título: " + titulo +
                "\n  Descrição: " + descricao +
                "\n  Autor: " + solicitanteNome + " | Técnico: " + tecnicoResponsavel;
    }
}