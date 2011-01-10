package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.f2rh.Curriculo;
import com.fortes.model.type.File;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Idioma;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.AvaliacaoCandidatosRelatorio;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;

public interface CandidatoManager extends GenericManager<Candidato>
{
    public Candidato findByCPF(String cpf, Long empresaId);
    //public void importa(File xmlFile);
	public Collection<Candidato> list(int page, int pagingSize, String nomeBusca, String cpfBusca, Long empresaId, String indicadoPor, char visualizar, Date dataIni, Date dataFim, String observacaoRH, boolean exibeContratados, boolean exibeExterno);
	public Integer getCount(String nomeBusca, String cpfBusca, Long empresaId, String indicadoPor, char visualizar, Date dataIni, Date dataFim, String observacaoRH, boolean exibeContratados, boolean exibeExterno);
	public Collection<Candidato> busca(Map<String, Object> parametros, Long empresa, Long solicitacaoId, boolean somenteSemSolicitacao) throws Exception;
	public File getFoto(Long id) throws Exception;
	public void removeCandidato(Candidato candidato) throws Exception;
	public void updateSenha(Candidato candidato);
	public boolean exportaCandidatosBDS(Empresa empresa, Collection<Candidato> candidatos, String[] empresasCheck, String emailAvulso, String anuncioBDS) throws Throwable;
	public Collection<Candidato> findCandidatosById(Long[] longs);
	public Collection<Candidato> populaCandidatos(Collection<Candidato> candidatos);
	public void importaBDS(java.io.File arquivoSalvo, Solicitacao solicitacao) throws Exception;
	public Candidato criarCandidatoByColaborador(Colaborador colaborador);
	public String recuperaSenha(String cpf, Empresa empresa);
	public void enviaNovaSenha(Candidato candidato, Empresa empresa);
	public void updateSetContratado(Long candidatoId);
	public void updateBlackList(String observacao, boolean blackList, Long... candidatoIds);
	public void setBlackList(HistoricoCandidato historicoCandidato, Long candidatoSolicitacaoId, boolean blacklist);
	public void setBlackList(HistoricoCandidato historicoCandidato, String[] candidatosCheck, boolean blacklist) throws Exception;
	public Candidato findByIdProjection(Long candidatoId);
	public Collection<Conhecimento> findConhecimentosByCandidatoId(Long candidatoId);
	public Collection<Cargo> findCargosByCandidatoId(Long candidatoId);
	public Collection<AreaInteresse> findAreaInteressesByCandidatoId(Long candidatoId);
	public Candidato saveCandidatoCurriculo(Candidato candidato, File[] imagemEscaneada, File ocrTexto) throws Exception;
	public String getOcrTextoById(Long candidatoId);
	public void atualizaTextoOcr(Candidato candidato);
	public Collection<Candidato> getCandidatosByNome(String candidatoNome);
	public int getTotalSize();
	public Collection<Candidato> getCandidatosByExperiencia(Map<String, Object> parametros, long empresaId);
	public Candidato findByCandidatoId(Long candidatoId);
	public void update(Candidato candidato);
	public Collection<AvaliacaoCandidatosRelatorio> findRelatorioAvaliacaoCandidatos(Date dataIni, Date dataFim, Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds) throws ColecaoVaziaException;
	public Collection<Candidato> findByNomeCpf(Candidato candidato, Long empresaId);
	public void migrarBairro(String bairro, String bairroDestino);
	public Collection<Candidato> buscaSimplesDaSolicitacao(Long empresaId, String indicadoPor, String nomeBusca, String cpfBusca, Long uf, Long cidade, String[] cargosCheck, String[] conhecimentosCheck, Long solicitacaoId, boolean somenteSemSolicitacao);
	public void validaQtdCadastros() throws Exception;
	public Collection<Candidato> findByNomeCpfAllEmpresas(Candidato candidato);
	public Candidato verifyCPF(String cpf, Long empresId, Long candidatoId) throws Exception;
	public void ajustaSenha(Candidato candidato);
	public void enviaEmailResponsavelRh(String nomeCandidato, Long empresaId);
	public String[] montaStringBuscaF2rh(Curriculo curriculo, Long uf, Long cidade, String escolaridade, Date dataCadIni, Date dataCadFim, String idadeMin, String idadeMax, Long idioma, Map ufs, Map cidades, Collection<Idioma> idiomas);
	public Collection<Candidato> getCurriculosF2rh(String[] curriculosId, Empresa empresa);
	public void habilitaByColaborador(Long colaboradorId);
	public void reabilitaByColaborador(Long colaboradorId);
	public String enviaEmailQtdCurriculosCadastrados();
}