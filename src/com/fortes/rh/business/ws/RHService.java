package com.fortes.rh.business.ws;

import com.fortes.rh.model.ws.FeedbackWebService;
import com.fortes.rh.model.ws.TAreaOrganizacional;
import com.fortes.rh.model.ws.TAuditoria;
import com.fortes.rh.model.ws.TAula;
import com.fortes.rh.model.ws.TCandidato;
import com.fortes.rh.model.ws.TCargo;
import com.fortes.rh.model.ws.TCidade;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.model.ws.TEmpresa;
import com.fortes.rh.model.ws.TEstabelecimento;
import com.fortes.rh.model.ws.TGrupo;
import com.fortes.rh.model.ws.TIndice;
import com.fortes.rh.model.ws.TIndiceHistorico;
import com.fortes.rh.model.ws.TOcorrencia;
import com.fortes.rh.model.ws.TOcorrenciaEmpregado;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.model.ws.TSituacaoCargo;

public interface RHService
{
	String eco(String texto);

	//TEM QUE VER SE O AC, NÃO SEI QUE USSSSAAAAAAA
	boolean criarEmpresa(TEmpresa empresa);
	boolean removerEmpresa(String empresaCodigoAC, String grupoAC);

	TEmpresa[] getEmpresas();
	TCidade[] getCidades(String uf);
	TCargo[] getCargos(Long empresaId);
	TCargo[] getFaixas();
	public String getNomesHomologos(String nomeCandidato);

	TGrupo[] getGrupos();
	TCargo[] getCargosAC(String empCodigo, String codigo, String grupoAC);

	//UTILIZADO PELO PLUGIN DO OFFICE
	boolean cadastrarCandidato(TCandidato candidato) throws Exception;

	//Tabela empregado no AC -> EPG
	FeedbackWebService removerEmpregado(TEmpregado empregado);
	FeedbackWebService removerEmpregadoComDependencia(TEmpregado empregado, TAuditoria tAuditoria);
	FeedbackWebService atualizarEmpregado(TEmpregado empregado);
	FeedbackWebService atualizarEmpregadoAndSituacao(TEmpregado empregado, TSituacao situacao);//O AC confirma cadastro de empregado que estava na CTT
	FeedbackWebService desligarEmpregado(String codigo, String empCodigo, String dataDesligamento, String grupoAC);
	FeedbackWebService desligarEmpregadosEmLote(String[] codigosAC, String empCodigo, String dataDesligamento, String grupoAC);
	FeedbackWebService religarEmpregado(String codigo, String empCodigo, String grupoAC);
	FeedbackWebService atualizarCodigoEmpregado(String grupoAC, String empresaCodigo, String codigo, String codigoNovo);
	FeedbackWebService cancelarContratacao(TEmpregado empregado, TSituacao situacao,  String mensagem);
	FeedbackWebService cancelarSolicitacaoDesligamentoAC(TEmpregado empregado, String mensagem);
	void reSincronizarTabelaTemporariaAC (String gruposAC) throws Exception;

	//Tabela situacao no AC -> SEP
	FeedbackWebService removerSituacao(TSituacao situacao);
	FeedbackWebService removerSituacaoEmLote(Integer movimentoSalarialId, String empCodigo, String grupoAC);
	FeedbackWebService criarSituacaoEmLote(TSituacao[] situacao);
	FeedbackWebService atualizarSituacaoEmLote(TSituacao[] situacao);
	FeedbackWebService criarSituacao(TEmpregado empregado, TSituacao situacao);
	FeedbackWebService atualizarSituacao(TSituacao situacao);
	FeedbackWebService cancelarSituacao(TSituacao situacao, String mensagem);

	FeedbackWebService criarEstabelecimento(TEstabelecimento testabelecimento);
	FeedbackWebService atualizarEstabelecimento(TEstabelecimento testabelecimento);
	FeedbackWebService removerEstabelecimento(String codigo, String empCodigo, String grupoAC);

	// AreaOrganizacional -> LOT
	FeedbackWebService criarAreaOrganizacional(TAreaOrganizacional areaOrganizacional);
	FeedbackWebService atualizarAreaOrganizacional(TAreaOrganizacional areaOrganizacional);
	FeedbackWebService removerAreaOrganizacional(TAreaOrganizacional areaOrganizacional);
	
	// Indice -> IND
	FeedbackWebService criarIndice(TIndice tindice);
	FeedbackWebService atualizarIndice(TIndice tindice);
	FeedbackWebService removerIndice(String codigo, String grupoAC);

	// Historico do Indice -> VID
	FeedbackWebService criarIndiceHistorico(TIndiceHistorico tindiceHistorico);
	FeedbackWebService removerIndiceHistorico(String data, String indiceCodigo, String grupoAC);

	FeedbackWebService setStatusFaixaSalarialHistorico(Long faixaSalarialHistoricoId, Boolean aprovado, String mensagem, String empresaCodigoAC, String grupoAC);

	//Cargo, FaixaSalarial -> CAR
	FeedbackWebService criarCargo(TCargo tCargo);
	FeedbackWebService atualizarCargo(TCargo tCargo);
	FeedbackWebService removerCargo(TCargo tCargo);

	FeedbackWebService criarSituacaoCargo(TSituacaoCargo tSituacaoCargo);
	FeedbackWebService atualizarSituacaoCargo(TSituacaoCargo tSituacaoCargo);
	FeedbackWebService removerSituacaoCargo(TSituacaoCargo tSituacaoCargo);

	// Tipo de Ocorrencia -> OCR
	FeedbackWebService criarOcorrencia(TOcorrencia tocorrencia);
	FeedbackWebService removerOcorrencia(TOcorrencia tocorrencia);
	// Ocorrencia de Empregado -> OCE
	FeedbackWebService criarOcorrenciaEmpregado(TOcorrenciaEmpregado[] ocorrenciaEmpregados);
	FeedbackWebService removerOcorrenciaEmpregado(TOcorrenciaEmpregado[] ocorrenciaEmpregados);
	
	//Transferência em lote
	FeedbackWebService transferir(TEmpresa tEmpresaOrigin, TEmpresa tEmpresaDestino, TEmpregado[] tEmpregados, TSituacao[] tSituacoes, String dataDesligamento);
	FeedbackWebService atualizarMovimentacaoEmLote(String[] empregadoCodigos, String movimentacao, String codPessoalEstabOuArea, boolean atualizarTodasSituacoes, String empCodigo, String grupoAC) throws NumberFormatException, Exception;
	
	//TRU
	TAula[] getTreinamentosPrevistos(String empregadoCodigo, String empresaCodigo, String empresaGrupo, String dataIni, String dataFim);
	TAula[] getTreinamentosCursados(String empregadoCodigo, String empresaCodigo, String empresaGrupo, String dataIni, String dataFim);
	boolean existePesquisaParaSerRespondida(String empregadoCodigo, String empresaCodigo, String empresaGrupo);
	
	String versaoDoSistema();
}