package com.fortes.rh.test.factory.acesso;

import com.fortes.rh.model.acesso.Papel;

public class PapelFactory {

	public static Papel getEntity() {
		Papel papel = new Papel();
		papel.setId(null);
		papel.setCodigo("ROLE_I");

		return papel;
	}

	public static Papel getEntity(Long id) {
		Papel papel = getEntity();
		papel.setId(id);

		return papel;
	}

	public static Papel getEntity(Long id, String codigo) {
		Papel usuario = getEntity(id);
		usuario.setCodigo(codigo);

		return usuario;
	}

	public static Papel getSesmt() {
		Papel papel = new Papel();
		papel.setId(1L);
		papel.setCodigo("ROLE_SESMT");
		papel.setMenu(true);
		papel.setNome("SESMT");
		papel.setOrdem(1);
		papel.setUrl("url.com.br");

		return papel;
	}

	public static Papel getFormularioSolicitacao() {
		Papel papel = new Papel();
		papel.setId(2L);
		papel.setCodigo("ROLE_FORMULARIO_SOLICITACAO_EXTERNO");
		papel.setMenu(true);
		papel.setNome("Formulário de Solicitação para o Sistema FortesRH");
		papel.setOrdem(0);
		papel.setPapelMae(getUtilitario());
		papel.setUrl("https://portaldocliente.fortestecnologia.com.br/portal_autentica_portal.php?location=/portal_solicitacao.php");

		return papel;
	}

	public static Papel getUtilitario() {
		Papel papel = new Papel();
		papel.setId(37L);
		papel.setCodigo("ROLE_UTI");
		papel.setMenu(true);
		papel.setNome("Utilitários");
		papel.setOrdem(13);

		return papel;
	}
}
