package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.model.type.File;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.AvaliacaoCandidatosRelatorio;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.security.spring.aop.callback.CandidatoAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public interface CandidatoManager extends GenericManager<Candidato>
{
	@Audita(operacao="Atualização", auditor=CandidatoAuditorCallbackImpl.class)
	public void update(Candidato candidato);
	@Audita(operacao="Remoção", auditor=CandidatoAuditorCallbackImpl.class)
	public void removeCandidato(Candidato candidato) throws Exception;

	public Candidato findByCPF(String cpf, Long empresaId);
	public Collection<Candidato> list(int page, int pagingSize, String nomeBusca, String cpfBusca, String ddd, String foneFixo, String foneCelular, String indicadoPor, char visualizar, Date dataIni, Date dataFim, String observacaoRH, boolean exibeContratados, boolean exibeExterno, Long... empresasIds);
	public Integer getCount(String nomeBusca, String cpfBusca, String ddd, String foneFixo, String foneCelular, String indicadoPor, char visualizar, Date dataIni, Date dataFim, String observacaoRH, boolean exibeContratados, boolean exibeExterno, Long... empresasIds);
	public Collection<Candidato> busca(Map<String, Object> parametros, Long solicitacaoId, boolean somenteSemSolicitacao, Integer qtdRegistros, String ordenar, Long... empresaIds) throws Exception;
	public File getFoto(Long id) throws Exception;
	public void updateSenha(Candidato candidato);
	public boolean exportaCandidatosBDS(Empresa empresa, Collection<Candidato> candidatos, String[] empresasCheck, String emailAvulso, String anuncioBDS) throws Throwable;
	public Collection<Candidato> findCandidatosById(Long[] longs);
	public Collection<Candidato> populaCandidatos(Collection<Candidato> candidatos);
	public void importaBDS(java.io.File arquivoSalvo, Solicitacao solicitacao) throws Exception;
	public Candidato saveOrUpdateCandidatoByColaborador(Colaborador colaborador);
	public String recuperaSenha(String cpf, Empresa empresa);
	public void enviaNovaSenha(Candidato candidato, Empresa empresa);
	public void updateSetContratado(Long candidatoId, Long empresaId);
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
	public Candidato findByCandidatoId(Long candidatoId);
	public Collection<AvaliacaoCandidatosRelatorio> findRelatorioAvaliacaoCandidatos(Date dataIni, Date dataFim, Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, char statusSolicitacao) throws ColecaoVaziaException;
	public Collection<Candidato> findByNomeCpf(Candidato candidato, Long empresaId);
	public void migrarBairro(String bairro, String bairroDestino);
	public Collection<Candidato> buscaSimplesDaSolicitacao(String indicadoPor, String nomeBusca, String cpfBusca, String escolaridade, Long uf, Long[] cidadesCheck, String[] cargosCheck, String[] conhecimentosCheck, Long solicitacaoId, boolean somenteSemSolicitacao, Integer qtdRegistro, String ordenar, boolean opcaoTodasEmpresas, Long... empresaIds);
	public Collection<Candidato> findByNomeCpfAllEmpresas(Candidato candidato);
	public Candidato verifyCPF(String cpf, Long empresId, Long candidatoId, Boolean contratado) throws Exception;
	public void ajustaSenha(Candidato candidato);
	public void enviaEmailResponsavelRh(String nomeCandidato, Long empresaId);
	public void updateDisponivelAndContratadoByColaborador(boolean disponivel, boolean contratado, Long... colaboradoresIds);
	public void enviaEmailQtdCurriculosCadastrados(Collection<Empresa> empresas);
	public String getComoFicouSabendoVagas();
	public void updateExamePalografico(Candidato candidato);
	public String getTextoExamePalografico(File ocrTexto) throws Exception;
	Collection<Candidato> triagemAutomatica(Solicitacao solicitacao, Integer tempoExperiencia, Map<String, Integer> pesos, Integer percentualMinimo);
	public int findQtdCadastrados(Long empresaId, Date dataDe, Date dataAte);
	public Collection<DataGrafico> countComoFicouSabendoVagas(Long empresaId, Date dataIni, Date dataFim);
	public int findQtdAtendidos(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim);
	public Collection<Candidato> findByCPF(String cpf, Long empresaId, Long candidatoId, Boolean contratado);
	public Collection<Colaborador> findColaboradoresMesmoCpf(String[] candidatosCpfs);
	public void updateDisponivel(boolean disponivel, Long candidatoId);
	public void deleteCargosPretendidos(Long... cargosIds);
	public void inserirNonoDigitoCelular(Long[] ufIds);
	public boolean existeCamposExtras(Long camposExtrasId);
}