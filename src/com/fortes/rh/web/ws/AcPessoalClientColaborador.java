package com.fortes.rh.web.ws;


import java.util.Collection;
import java.util.Date;

import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.model.ws.TFeedbackPessoalWebService;
import com.fortes.rh.model.ws.TNaturalidadeAndNacionalidade;
import com.fortes.rh.model.ws.TPeriodoGozo;
import com.fortes.rh.model.ws.TRemuneracaoVariavel;
import com.fortes.rh.model.ws.TSituacao;

public interface AcPessoalClientColaborador
{
	public void atualizar(TEmpregado empregado, Empresa empresa, Date dataAlteracao) throws IntegraACException, Exception;

	public boolean contratar(TEmpregado empregado, TSituacao situacao, Empresa empresa);

	public boolean verificaHistoricoNaFolhaAC(Long historicoColaboradorId, String colaboradorCodigoAC, Empresa empresa);

	public boolean remove(Colaborador colaborador, Empresa empresa);
	
	public TRemuneracaoVariavel[] getRemuneracoesVariaveis(Empresa empresa, String[] colaboradoresIds, String anoMesInicial, String anoMesFinal) throws Exception;

	public TFeedbackPessoalWebService solicitacaoDesligamentoAc(Collection<HistoricoColaborador> historicosAc, Empresa empresa) throws IntegraACException;

	public String getReciboPagamento(Colaborador colaborador, Date mesAno) throws Exception;
	
	public String getReciboDeDecimoTerceiro(Colaborador colaborador, String dataCalculo) throws Exception;
	
	public String getDeclaracaoRendimentos(Colaborador colaborador, String ano) throws Exception;

	public String getAvisoReciboDeFerias(Colaborador colaborador, String dataInicioGozo, String dataFimGozo) throws Exception;
	
	public String[] getDatasDecimoTerceiroPorEmpregado(Colaborador colaborador) throws Exception;

	public String[] getDatasPeriodoDeGozoPorEmpregado(Colaborador colaborador) throws Exception;
	
	public void confirmarReenvio(TFeedbackPessoalWebService tFeedbackPessoalWebService, Empresa empresa) throws Exception;
	
	public String getReciboDePagamentoComplementar(Colaborador colaborador, Date mesAno) throws Exception;
	
	public String getReciboPagamentoAdiantamentoDeFolha(Colaborador colaborador, Date mesAno) throws Exception;
	
	public TPeriodoGozo[] getFerias(Empresa empresa, String[] colaboradoresCodigosACs, String dataInicioGozo, String dataFimGozo) throws Exception;

	public TNaturalidadeAndNacionalidade[] getNaturalidadesAndNacionalidades(Empresa empresa, String[] colaboradoresIds) throws IntegraACException;
}