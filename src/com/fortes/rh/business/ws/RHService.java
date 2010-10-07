package com.fortes.rh.business.ws;

import com.fortes.rh.model.ws.TAreaOrganizacional;
import com.fortes.rh.model.ws.TCandidato;
import com.fortes.rh.model.ws.TCargo;
import com.fortes.rh.model.ws.TCidade;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.model.ws.TEmpresa;
import com.fortes.rh.model.ws.TEstabelecimento;
import com.fortes.rh.model.ws.TIndice;
import com.fortes.rh.model.ws.TIndiceHistorico;
import com.fortes.rh.model.ws.TOcorrencia;
import com.fortes.rh.model.ws.TOcorrenciaEmpregado;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.model.ws.TSituacaoCargo;

public interface RHService
{
	String eco(String texto);

	boolean criarEmpresa(TEmpresa empresa);
	boolean removerEmpresa(String empresaCodigoAC);

	TEmpresa[] getEmpresas();
	TCidade[] getCidades(String uf);
	TCargo[] getCargos(Long empresaId);
	TCargo[] getCargosAC(String empCodigo, String codigo);
	public String getNomesHomologos(String nomeCandidato);

	boolean cadastrarCandidato(TCandidato candidato) throws Exception;

	//Tabela empregado no AC -> EPG
	boolean atualizarEmpregado(TEmpregado empregado);
	boolean atualizarEmpregadoAndSituacao(TEmpregado empregado, TSituacao situacao);//O AC confirma cadastro de empregado que estava na CTT
	boolean desligarEmpregado(String codigo, String empCodigo, String dataDesligamento);
	boolean religarEmpregado(String codigo, String empCodigo);

	//Tabela situacao no AC -> SEP
	boolean removerSituacao(TSituacao situacao);
	boolean removerSituacaoEmLote(Integer movimentoSalarialId, String empCodigo);
	boolean criarSituacao(TSituacao situacao);
	boolean criarSituacaoEmLote(TSituacao[] situacao);
	boolean atualizarSituacao(TSituacao situacao);
	boolean cancelarSituacao(TSituacao situacao, String mensagem);

	boolean criarEstabelecimento(TEstabelecimento testabelecimento);
	boolean atualizarEstabelecimento(TEstabelecimento testabelecimento);
	boolean removerEstabelecimento(String codigo, String empCodigo);

	// AreaOrganizacional -> LOT
	boolean criarAreaOrganizacional(TAreaOrganizacional areaOrganizacional);
	boolean atualizarAreaOrganizacional(TAreaOrganizacional areaOrganizacional);
	boolean removerAreaOrganizacional(TAreaOrganizacional areaOrganizacional);
	
	// Indice -> IND
	boolean criarIndice(TIndice tindice);
	boolean atualizarIndice(TIndice tindice);
	boolean removerIndice(String codigo);

	// Historico do Indice -> VID
	boolean criarIndiceHistorico(TIndiceHistorico tindiceHistorico);
	boolean removerIndiceHistorico(String data, String indiceCodigo);

	boolean setStatusFaixaSalarialHistorico(Long faixaSalarialHistoricoId, Boolean aprovado, String mensagem, String empresaCodigoAC);

	//Cargo, FaixaSalarial -> CAR
	boolean criarCargo(TCargo tCargo);
	boolean atualizarCargo(TCargo tCargo);
	boolean removerCargo(TCargo tCargo);

	boolean criarSituacaoCargo(TSituacaoCargo tSituacaoCargo);
	boolean atualizarSituacaoCargo(TSituacaoCargo tSituacaoCargo);
	boolean removerSituacaoCargo(TSituacaoCargo tSituacaoCargo);

	// Tipo de Ocorrencia -> OCR
	boolean criarOcorrencia(TOcorrencia tocorrencia);
	boolean removerOcorrencia(TOcorrencia tocorrencia);
	// Ocorrencia de Empregado -> OCE
	boolean criarOcorrenciaEmpregado(TOcorrenciaEmpregado[] ocorrenciaEmpregados);
	boolean removerOcorrenciaEmpregado(TOcorrenciaEmpregado[] ocorrenciaEmpregados);
}
