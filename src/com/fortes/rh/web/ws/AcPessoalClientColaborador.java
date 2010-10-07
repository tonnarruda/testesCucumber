package com.fortes.rh.web.ws;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.model.ws.TSituacao;

public interface AcPessoalClientColaborador
{
	public void atualizar(TEmpregado empregado, Empresa empresa) throws Exception;

	public boolean contratar(TEmpregado empregado, TSituacao situacao, Empresa empresa);

	public boolean verificaHistoricoNaFolhaAC(Long historicoColaboradorId, String colaboradorCodigoAC, Empresa empresa);

	public boolean remove(Colaborador colaborador, Empresa empresa);
}