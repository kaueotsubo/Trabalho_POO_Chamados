package model.exception;

public class ChaveJaCadastradaException extends RuntimeException {
    public ChaveJaCadastradaException(String chave, String tipoChave) {
        super(tipoChave + " '" + chave + "' já está cadastrado(a) no sistema!");
    }
}