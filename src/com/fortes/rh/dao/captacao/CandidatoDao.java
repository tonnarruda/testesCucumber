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
import com.fortes.model.type.File;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.relatorio.AvaliacaoCandidatosRelatorio;

public interface CandidatoDao extends GenericDao<Candidato>
{
    public Candidato findByCPF(String cpf, Long empresaId, Long candidatoId);
	public Collection<Candidato> findBusca(Map parametros, long empresa, Collection<Long> idsCandidatos, boolean somenteSemSolicitacao) throws Exception;
	public Collection<Candidato> find(int page, int pagingSize, String nomeBusca, String cpfBusca, Long empresaId, String indicadoPor, char visualizar, Date dataIni, Date dataFim, String observacaoRH, boolean exibeContratados, boolean exibeExterno);
	public Integer getCount(String nomeBusca, String cpfBusca, Long empresaId, String indicadoPor, char visualizar, Date dataIni, Date dataFim, String observacaoRH, boolean exibeContratados, boolean exibeExterno);
	public Collection<Candidato> findCandidatosById(Long[] ids);
	public List getConhecimentosByCandidatoId(Long id);
	public Collection<Candidato> getCandidatosByCpf(String[] cpfs, Long empresaId);
	public Candidato findCandidatoCpf(String cpf, Long empresaId);
	public void updateSetContratado(Long candidatoId);
	public void updateBlackList(String observacao, boolean blackList, Long... candidatoIds);
	public Candidato findByCandidatoId(Long id);
	public void atualizaSenha(Long id, String senha);
	public Candidato findByIdProjection(Long candidatoId);
	public List findConhecimentosByCandidatoId(Long candidatoId);
	public List findCargosByCandidatoId(Long candidatoId);
	public List findAreaInteressesByCandidatoId(Long candidatoId);
	public void atualizaTextoOcr(Candidato candidato);
	public Collection<Candidato> getCandidatosByNome(String candidatoNome);
	public Collection<Candidato> getCandidatosByExperiencia(Map parametros, long empresa);
	public void updateSenha(Long candidatoId, String senha, String novaSenha);
	Integer getCount(Map parametros, long empresaId);
	public Collection<AvaliacaoCandidatosRelatorio> findRelatorioAvaliacaoCandidatos(Date dataIni, Date dataFim, Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds);
	public Collection<Candidato> findByNomeCpf(Candidato candidato, Long empresaId);
	public void migrarBairro(String bairro, String bairroDestino);
	public Collection<Candidato> findCandidatosForSolicitacaoAllEmpresas(String indicadoPor, String nomeBusca, String cpfBusca, Long uf, Long cidade, String[] cargosCheck, String[] conhecimentosCheck, Collection<Long> candidatosDaSolicitacao, boolean somenteSemSolicitacao);
	public Collection<Candidato> findCandidatosForSolicitacaoByEmpresa(Long empresaId, String indicadoPor, String nomeBusca, String cpfBusca, Long uf, Long cidade, Long[] cargosCheck, Long[] conhecimentosCheck, Collection<Long> candidatosDaSolicitacao, boolean somenteSemSolicitacao);
	public void converteTodasAsFotosParaThumbnail();
	public String getSenha(Long id);
}