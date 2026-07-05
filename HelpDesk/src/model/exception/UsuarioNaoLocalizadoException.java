package model.exception;

public class UsuarioNaoLocalizadoException extends RuntimeException {
    public UsuarioNaoLocalizadoException(String email) {
        super("Usuário associado ao e-mail '" + email + "' não foi localizado!");
    }
}