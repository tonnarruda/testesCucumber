package com.fortes.rh.exception;

public class FormatoArquivoInvalidoException extends Exception {
	
	private static final long serialVersionUID = 3663879688830489892L;

	public FormatoArquivoInvalidoException() {
		super("Formato de arquivo inválido.");
	}
	
	public FormatoArquivoInvalidoException(String msg) {
		super(msg);
	}
}
