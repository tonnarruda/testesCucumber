/*
 * autor: Moesio Medeiros
 * Data: 07/06/2006
 * Requisito: RFA013
 */

package com.fortes.rh.dao.captacao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.AvaliacaoCandidatosRelatorio;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;

@SuppressWarnings("rawtypes")
public interface CandidatoDao extends GenericDao<Candidato>
{
	public Collection<Candidato> findBusca(Map parametros, Long[] empresaIds, Collection<Long> idsCandidatos, boolean somenteSemSolicitacao, Integer qtdRegistros, String ordenar) throws Exception;
	public Collection<Candidato> find(int page, int pagingSize, String nomeBusca, String cpfBusca, String ddd, String foneFixo, String foneCelular, String indicadoPor, char visualizar, Date dataIni, Date dataFim, String observacaoRH, boolean exibeContratados, boolean exibeExterno, Long... empresasIds);
	public Integer getCount(String nomeBusca, String cpfBusca, String ddd, String foneFixo, String foneCelular, String indicadoPor, char visualizar, Date dataIni, Date dataFim, String observacaoRH, boolean exibeContratados, boolean exibeExterno, Long... empresasIds);
	public Collection<Candidato> findCandidatosById(Long[] ids);
	public List getConhecimentosByCandidatoId(Long id);
	public Collection<Candidato> getCandidatosByCpf(String[] cpfs, Long empresaId);
	public Candidato findCandidatoCpf(String cpf, Long empresaId);
	public void updateSetContratado(Long candidatoId, Long empresaId);
	public void updateBlackList(String observacao, boolean blackList, Long... candidatoIds);
	public Candidato findByCandidatoId(Long id);
	public void atualizaSenha(Long id, String senha);
	public Candidato findByIdProjection(Long candidatoId);
	public List findConhecimentosByCandidatoId(Long candidatoId);
	public List findCargosByCandidatoId(Long candidatoId);
	public List findAreaInteressesByCandidatoId(Long candidatoId);
	public void atualizaTextoOcr(Candidato candidato);
	public Collection<Candidato> getCandidatosByNome(String candidatoNome);
	public Collection<Candidato> getCandidatosByExperiencia(Map parametros, Long[] empresaIds);
	public void updateSenha(Long candidatoId, String senha, String novaSenha);
	public Collection<AvaliacaoCandidatosRelatorio> findRelatorioAvaliacaoCandidatos(Date dataIni, Date dataFim, Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, char statusSolicitacao);
	public Collection<Candidato> findByNomeCpf(Candidato candidato, Long empresaId);
	public void migrarBairro(String bairro, String bairroDestino);
	public Collection<Candidato> findCandidatosForSolicitacao(String indicadoPor, String nomeBusca, String cpfBusca, String escolaridade, Long uf, Long[] cidadesCheck, String[] cargosCheck, String[] conhecimentosCheck, Collection<Long> candidatosDaSolicitacao, boolean somenteSemSolicitacao, Integer qtdRegistros, String ordenar, Long[] empresaIds, boolean todasEmpresasPermitidas);
	public void converteTodasAsFotosParaThumbnail();
	public String getSenha(Long id);
	public void updateDisponivelAndContratadoByColaborador(boolean disponivel, boolean contratado, Long... colaboradoresIds);
	public void updateDisponivel(boolean disponivel, Long candidatoId);
	public Collection<Candidato> findQtdCadastradosByOrigem(Date dataIni, Date dataFim);
	public Collection<String> getComoFicouSabendoVagas();
	public void updateExamePalografico(Candidato candidato);
	public Collection<Candidato> triagemAutomatica(Solicitacao solicitacao, Integer tempoExperiencia, Map<String, Integer> pesos, Integer percentualMinimo);
	public int findQtdCadastrados(Long empresaId, Date dataDe, Date dataAte);
	public Collection<ComoFicouSabendoVaga> countComoFicouSabendoVagas(Long empresaId, Date dataIni, Date dataFim);
	public void removeAreaInteresseConhecimentoCargo(Long candidatoId);
	public Collection<Candidato> findByCPF(String cpf, Long empresaId, 	Long candidatoId, Boolean contratado);
	public Collection<Colaborador> findColaboradoresMesmoCpf(String[] candidatosCpfs);
	public void deleteCargosPretendidos(Long... cargosIds);
	public void inserirNonoDigitoCelular(Long[] ufIds);
	public boolean existeCamposExtras(Long camposExtrasId);
	public Collection<AutoCompleteVO> getAutoComplete(String descricao, Long empresaId);
	public Collection<Candidato> findCandidatosIndicadosPor(Date dataIni, Date dataFim, Long[] empresasIds);
	public Collection<Candidato> getCandidatosByEtapaSeletiva(Long etapaSeletivaId);
	public Map<Long, Collection<String>> getFuncoesPretendidasByEtapaSeletiva(Long etapaSeletivaId);
	public Collection<Candidato> findPorEmpresaByCpfSenha(String cpf, String senha, Long empresaId);
}