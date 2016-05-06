package com.fortes.rh.business.ws;

import com.fortes.rh.exception.TokenException;
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
	
	String getToken(String login, String senha) throws Exception;

	//TEM QUE VER SE O AC, NÃO SEI QUE USSSSAAAAAAA
	
	boolean criarEmpresa(String token, TEmpresa empresa);
	boolean removerEmpresa(String empresaCodigoAC, String grupoAC);

	TEmpresa[] getEmpresas(String token) throws TokenException;
	TCidade[] getCidades(String token, String uf) throws TokenException;
	TCargo[] getCargos(String token, Long empresaId) throws TokenException;
	TCargo[] getFaixas(String token) throws TokenException;
	public String getNomesHomologos(String token, String nomeCandidato) throws TokenException;

	TGrupo[] getGrupos(String token) throws TokenException;
	TCargo[] getCargosAC(String token, String empCodigo, String codigo, String grupoAC) throws TokenException;

	//UTILIZADO PELO PLUGIN DO OFFICE
	boolean cadastrarCandidato(TCandidato candidato) throws Exception;

	//Tabela empregado no AC -> EPG
	FeedbackWebService removerEmpregado(String token, TEmpregado empregado);
	FeedbackWebService removerEmpregadoComDependencia(String token, TEmpregado empregado, TAuditoria tAuditoria);
	FeedbackWebService atualizarEmpregado(String token, TEmpregado empregado);
	FeedbackWebService atualizarEmpregadoAndSituacao(String token, TEmpregado empregado, TSituacao situacao);//O AC confirma cadastro de empregado que estava na CTT
	FeedbackWebService desligarEmpregado(String token, String codigo, String empCodigo, String dataDesligamento, String grupoAC);
	FeedbackWebService desligarEmpregadosEmLote(String token, String[] codigosAC, String empCodigo, String dataDesligamento, String grupoAC);
	FeedbackWebService religarEmpregado(String token, String codigo, String empCodigo, String grupoAC);
	FeedbackWebService atualizarCodigoEmpregado(String token, String grupoAC, String empresaCodigo, String codigo, String codigoNovo);
	FeedbackWebService cancelarContratacao(String token, TEmpregado empregado, TSituacao situacao,  String mensagem);
	FeedbackWebService cancelarSolicitacaoDesligamentoAC(String token, TEmpregado empregado, String mensagem);
	void reSincronizarTabelaTemporariaAC (String token, String gruposAC) throws Exception;

	//Tabela situacao no AC -> SEP
	FeedbackWebService removerSituacao(String token, TSituacao situacao);
	FeedbackWebService removerSituacaoEmLote(String token, Integer movimentoSalarialId, String empCodigo, String grupoAC);
	FeedbackWebService criarSituacaoEmLote(String token, TSituacao[] situacao);
	FeedbackWebService atualizarSituacaoEmLote(String token, TSituacao[] situacao);
	FeedbackWebService criarSituacao(String token, TEmpregado empregado, TSituacao situacao);
	FeedbackWebService atualizarSituacao(String token, TSituacao situacao);
	FeedbackWebService cancelarSituacao(String token, TSituacao situacao, String mensagem);

	FeedbackWebService criarEstabelecimento(String token, TEstabelecimento testabelecimento);
	FeedbackWebService atualizarEstabelecimento(String token, TEstabelecimento testabelecimento);
	FeedbackWebService removerEstabelecimento(String token, String codigo, String empCodigo, String grupoAC);

	// AreaOrganizacional -> LOT
	FeedbackWebService criarAreaOrganizacional(String token, TAreaOrganizacional areaOrganizacional);
	FeedbackWebService atualizarAreaOrganizacional(String token, TAreaOrganizacional areaOrganizacional);
	FeedbackWebService removerAreaOrganizacional(String token, TAreaOrganizacional areaOrganizacional);
	
	// Indice -> IND
	FeedbackWebService criarIndice(String token, TIndice tindice);
	FeedbackWebService atualizarIndice(String token, TIndice tindice);
	FeedbackWebService removerIndice(String token, String codigo, String grupoAC);

	// Historico do Indice -> VID
	FeedbackWebService criarIndiceHistorico(String token, TIndiceHistorico tindiceHistorico);
	FeedbackWebService removerIndiceHistorico(String token, String data, String indiceCodigo, String grupoAC);

	FeedbackWebService setStatusFaixaSalarialHistorico(String token, Long faixaSalarialHistoricoId, Boolean aprovado, String mensagem, String empresaCodigoAC, String grupoAC);

	//Cargo, FaixaSalarial -> CAR
	FeedbackWebService criarCargo(String token, TCargo tCargo);
	FeedbackWebService atualizarCargo(String token, TCargo tCargo);
	FeedbackWebService removerCargo(String token, TCargo tCargo);

	FeedbackWebService criarSituacaoCargo(String token, TSituacaoCargo tSituacaoCargo);
	FeedbackWebService atualizarSituacaoCargo(String token, TSituacaoCargo tSituacaoCargo);
	FeedbackWebService removerSituacaoCargo(String token, TSituacaoCargo tSituacaoCargo);

	// Tipo de Ocorrencia -> OCR
	FeedbackWebService criarOcorrencia(String token, TOcorrencia tocorrencia);
	FeedbackWebService removerOcorrencia(String token, TOcorrencia tocorrencia);
	// Ocorrencia de Empregado -> OCE
	FeedbackWebService criarOcorrenciaEmpregado(String token, TOcorrenciaEmpregado[] ocorrenciaEmpregados);
	FeedbackWebService removerOcorrenciaEmpregado(String token, TOcorrenciaEmpregado[] ocorrenciaEmpregados);
	
	//Transferência em lote
	FeedbackWebService transferir(String token, TEmpresa tEmpresaOrigin, TEmpresa tEmpresaDestino, TEmpregado[] tEmpregados, TSituacao[] tSituacoes, String dataDesligamento);
	FeedbackWebService atualizarMovimentacaoEmLote(String token, String[] empregadoCodigos, String movimentacao, String codPessoalEstabOuArea, boolean atualizarTodasSituacoes, String empCodigo, String grupoAC) throws NumberFormatException, Exception;
	
	//TRU
	TAula[] getTreinamentosPrevistos(String empregadoCodigo, String empresaCodigo, String empresaGrupo, String dataIni, String dataFim);
	TAula[] getTreinamentosCursados(String empregadoCodigo, String empresaCodigo, String empresaGrupo, String dataIni, String dataFim);
	boolean existePesquisaParaSerRespondida(String empregadoCodigo, String empresaCodigo, String empresaGrupo);

	//Leitor10
	public boolean existeColaboradorAtivo(String cpf);
	
	String versaoDoSistema();
}