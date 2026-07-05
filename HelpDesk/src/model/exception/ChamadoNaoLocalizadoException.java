package model.exception;

public class ChamadoNaoLocalizadoException extends RuntimeException {
    public ChamadoNaoLocalizadoException(int id) {
        super("Chamado com ID " + id + " não foi localizado no sistema!");
    }
}