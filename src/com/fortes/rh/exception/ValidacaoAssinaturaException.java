package com.fortes.rh.exception;

public class ValidacaoAssinaturaException extends FortesException {
	
	private static final long serialVersionUID = 6509328519792083474L;

	public ValidacaoAssinaturaException() {
		super("Assinatura inválida");
	}
	
	public ValidacaoAssinaturaException(String msg) {
		super(msg);
	}
}
