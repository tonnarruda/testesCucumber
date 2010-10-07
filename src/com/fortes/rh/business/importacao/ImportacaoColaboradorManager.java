package com.fortes.rh.business.importacao;

import java.io.IOException;

import com.fortes.rh.model.geral.Empresa;

public interface ImportacaoColaboradorManager {

	public void importarDadosPessoaisByCpf(java.io.File arquivoCriado, Empresa empresa) throws IOException;
	public Integer getCountColaboradoresImportados();
	public Integer getCountColaboradoresNaoEncontrados();
	public Integer getCountColaboradoresSemCpf();
	public String getCpfsNaoEncontrados();
}
