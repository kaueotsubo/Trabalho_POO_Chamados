package control;

import model.*;
import model.exception.ChaveJaCadastradaException;
import model.exception.ChamadoNaoLocalizadoException;
import model.exception.UsuarioNaoLocalizadoException;

import java.util.HashMap;
import java.util.Map;

public class Controller {
    private Map<String, Usuario> usuarioMap = new HashMap<>();
    private Map<Integer, Chamado> chamadoMap = new HashMap<>();
    private SistemaNotificacao sn = new SistemaNotificacao();
    private int proximoIdChamado = 1;

    private void cadastrarUsuario(Usuario u) {
        usuarioMap.put(u.getEmail(), u);
        if (u instanceof Notificavel) {
            sn.cadastrarNoCanal(u.getEmail(), u);
        }
    }

    public void cadastrarSolicitante(String nome, String email) {
        if (this.usuarioMap.containsKey(email)) {
            throw new ChaveJaCadastradaException(email, "E-mail");
        }
        this.cadastrarUsuario(new Cliente(nome, email));
    }

    public void cadastrarTecnico(String nome, String email, String especialidade) {
        if (this.usuarioMap.containsKey(email)) {
            throw new ChaveJaCadastradaException(email, "E-mail");
        }
        this.cadastrarUsuario(new Tecnico(nome, email, especialidade));
    }

    public void abrirChamado(String emailSolicitante, String titulo, String descricao) {
        Usuario u = usuarioMap.get(emailSolicitante);
        if (u == null) {
            throw new UsuarioNaoLocalizadoException(emailSolicitante);
        }

        int id = proximoIdChamado++;
        Chamado novoChamado = new Chamado(id, titulo, descricao, u.getNome());
        chamadoMap.put(id, novoChamado);

        sn.enviarNotificacao(emailSolicitante, "Seu chamado #" + id + " foi aberto com sucesso!");
    }

    public void atribuirTecnicoAoChamado(int idChamado, String emailTecnico) {
        Chamado c = chamadoMap.get(idChamado);
        if (c == null) {
            throw new ChamadoNaoLocalizadoException(idChamado);
        }

        Usuario u = usuarioMap.get(emailTecnico);
        if (u == null) {
            throw new UsuarioNaoLocalizadoException(emailTecnico);
        }

        if (!(u instanceof Tecnico)) {
            throw new RuntimeException("O usuário '" + u.getNome() + "' não possui perfil de Técnico!");
        }

        c.setTecnicoResponsavel(u.getNome());
        c.setStatus(StatusChamado.EM_ANDAMENTO);

        sn.enviarNotificacao(emailTecnico, "Você foi designado para o chamado #" + idChamado);
    }

    public void resolverChamado(int idChamado) {
        Chamado c = chamadoMap.get(idChamado);
        if (c == null) {
            throw new ChamadoNaoLocalizadoException(idChamado);
        }
        c.setStatus(StatusChamado.RESOLVIDO);
    }

    public String getRelatorioChamados() {
        StringBuilder sb = new StringBuilder();
        if (chamadoMap.isEmpty()) {
            return "Nenhum chamado registrado no sistema.";
        }
        for (Chamado c : chamadoMap.values()) {
            sb.append(c.toString());
            sb.append("\n-----------------------------------\n");
        }
        return sb.toString();
    }

    public String getRelatorioChamado(int id) {
        if (chamadoMap.containsKey(id)) {
            return chamadoMap.get(id).toString();
        }
        throw new ChamadoNaoLocalizadoException(id);
    }
}