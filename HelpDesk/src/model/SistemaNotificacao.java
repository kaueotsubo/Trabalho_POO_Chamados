package model;

import java.util.HashMap;
import java.util.Map;

public class SistemaNotificacao {
    private Map<String, Notificavel> assinantes = new HashMap<>();

    public void cadastrarNoCanal(String email, Notificavel assinante) {
        this.assinantes.put(email, assinante);
    }

    public void enviarNotificacao(String email, String mensagem) {
        Notificavel n = assinantes.get(email);
        if (n != null) {
            n.receberAlerta(mensagem);
        }
    }
}