package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.fortes.business.GenericManager;
import com.fortes.model.type.File;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.relatorio.MotivoDemissaoQuantidade;
import com.fortes.rh.model.geral.relatorio.TurnOver;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.web.tags.CheckBox;

@SuppressWarnings("unchecked")
public interface ColaboradorManager extends GenericManager<Colaborador>
{
	public Collection<Colaborador> findByAreasOrganizacionalIds(Long[] idsLong);
	public Collection<Colaborador> findSemUsuarios(Long empresaId, Usuario usuario);
	public Integer getCount(Map parametros);
	public Collection findList(int page, int pagingSize, Map parametros);
	public Collection<Colaborador> findByAreasOrganizacionalIds(int page, int pagingSize, Long[] longs, Colaborador colaborador);
	public Colaborador findColaboradorPesquisa(Long id,Long empresaId);
	public boolean insert(Colaborador colaborador, Double salarioColaborador, Long idCandidato, Collection<Formacao> formacaos, Collection<CandidatoIdioma> idiomas, Collection<Experiencia> experiencias, Solicitacao solicitacao, Empresa empresa) throws Exception;
	public void update(Colaborador colaborador, Collection<Formacao> formacaos, Collection<CandidatoIdioma> idiomas, Collection<Experiencia> experiencias, Empresa empresa, boolean editarHistorico, Double salarioColaborador) throws Exception;
	public void saveDetalhes(Colaborador colaborador, Collection<Formacao> formacaos, Collection<CandidatoIdioma> idiomas, Collection<Experiencia> experiencias);
	public void enviarEmailCadastro(Colaborador colaborador, Empresa empresa) throws AddressException, MessagingException;
	public boolean desligaColaboradorAC(String codigoAC, Empresa empresa, Date dataDesligamento);
	public boolean religaColaboradorAC(String codigoAC, Empresa empresa);
	public Colaborador findColaboradorUsuarioByCpf(String cpf,Long empresaId);
	public void enviaEmailEsqueciMinhaSenha(Colaborador colaborador, Empresa empresa);
	public String recuperaSenha(String cpf,Empresa empresa);
	public Collection<Colaborador> findBycandidato(Long candidatoId,Long empresaId);
	public boolean candidatoEhColaborador(Long id,Long empresaId);
	public Collection<Colaborador> findByArea(AreaOrganizacional areaFiltro);
	public Collection<Colaborador> findByFuncaoAmbiente(Long funcaoId, Long ambienteId);
	public boolean setCodigoColaboradorAC(String codigo, Long id);
	public Colaborador findByCodigoAC(String codigo, Empresa empresa);
	public Colaborador findColaboradorById(Long id);
	public Colaborador findByUsuario(Usuario usuario,Long empresaId);
	public Collection<Colaborador> findByAreaEstabelecimento(Long areaOrganizacionalId, Long estabelecimentoId);
	public Collection<Colaborador> findByAreasOrganizacionaisEstabelecimentos(Collection<Long> areaOrganizacionalIds, Collection<Long> estabelecimentoIds);
	public Collection<Colaborador> findByCargoIdsEstabelecimentoIds(Collection<Long> cargoIds, Collection<Long> estabelecimentoIds);
	public Collection<Colaborador> findByGrupoOcupacionalIdsEstabelecimentoIds(Collection<Long> grupoOcupacionalIds, Collection<Long> estabelecimentoIds);
	public Collection<Colaborador> findByEstabelecimento(Long[] estabelecimentoIds);
	public Collection<Colaborador> getColaboradoresIntegraAc(Collection<Colaborador> colaboradores);
	public Colaborador findByIdProjectionUsuario(Long colaboradorId);
	public Collection<Colaborador> findAreaOrganizacionalByAreas(HashMap<Object, Object> filtros, boolean habilitaCampoExtra);
	public Colaborador findColaboradorByIdProjection(Long colaboradorId);
	void atualizarUsuario(Long colaboradorId, Long usuarioId) throws Exception;
	public Colaborador findByIdProjectionEmpresa(Long colaboradorId);
	Collection<Colaborador> findAdmitidosByPeriodo(Date dataIni, Date dataFim, Map parametros);
	Collection<Colaborador> findDemitidosByPeriodo(Date dataIni, Date dataFim, Map parametros);
	Collection<Colaborador> findColaboradorInData(Date data, Map parametros);
	TurnOver getTurnOverByMes(Date data, Map parametros);
	Collection<TurnOver> getTurnOver(String de, String ate, Map parametros) throws ColecaoVaziaException;
	public Collection<Colaborador> findColaboradoresMotivoDemissao(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim)throws Exception;
	public Collection<MotivoDemissaoQuantidade> findColaboradoresMotivoDemissaoQuantidade(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim)throws Exception;
	public Collection<Colaborador> getColaboradoresByEstabelecimentoAreaGrupo(char filtrarPor, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds);
	public void desligaColaborador( boolean desligado, Date dataDesligamento, String observacao, Long motivoDemissaoId, Long colaboradorId) throws Exception;
	public void religaColaborador(Long colaboradorId) throws Exception;
	public Collection<Colaborador> findProjecaoSalarial(Long tabelaReajusteColaboradorId, Date data, Collection<Long> estabelecimentoIds, Collection<Long> areaIds, Collection<Long> grupoIds, Collection<Long> cargoIds, String filtro, Long empresaId) throws Exception;

	Collection<Colaborador> ordenaPorEstabelecimentoArea(Long empresaId, Collection<Colaborador> colaboradors) throws Exception;
	public void verificaColaboradoresSemCodigoAC(Collection<ReajusteColaborador> reajustes) throws Exception;

	void respondeuEntrevista(Long colaboradorId);
	public boolean setMatriculaColaborador(Long empresaId, String codigoAC, String matricula);
	public boolean setMatriculaColaborador(Long colaboradorId, String matricula);
	public Collection<Colaborador> findListComHistoricoFuturo(int page, int pagingSize, Map parametros);
	public Integer getCountComHistoricoFuturo(Map parametros);
	public Colaborador findByIdComHistoricoConfirmados(Long colaboradorId);
	public Colaborador findByIdComHistorico(Long colaboradorId);
	public Collection<Colaborador> findAllSelect(Long empresaId, String ordenarPor);
	public Collection<Colaborador> findAllSelect(Long... empresaIds);
	public Collection<Colaborador> findAllSelect(Collection<Long> colaboradorIds);
	
	public void updateInfoPessoais(Colaborador colaborador, Collection<Formacao> formacaos, Collection<CandidatoIdioma> idiomas, Collection<Experiencia> experiencias, Empresa empresa) throws Exception;
	public boolean updateInfoPessoaisByCpf(Colaborador colaborador, Long empresaId);

	public Colaborador updateEmpregado(TEmpregado empregado) throws Exception;
	public Colaborador findByCodigoAC(String empregadoCodigoAC, String empresaCodigoAC);
	public Long findByUsuario(Long usuarioId);
	public Integer getCountAtivos(Date dataIni, Date dataFim, Long empresaId);
	public File getFoto(Long id) throws Exception;
	public Collection<Colaborador> findAniversariantes(Long[] empresaIds, int mes, Long[] areaIds, Long[] estabelecimentoIds) throws Exception;
	public Collection<Colaborador> findByNomeCpfMatricula(Colaborador colaborador, Long empresaId, Boolean somenteAtivos);
	public String getNome(Long id);
	public void remove(Colaborador colaborador, Empresa empresa) throws Exception;
	public Colaborador findByIdHistoricoProjection(Long id);
	public Collection<CheckBox> populaCheckBox(Long empresaId);
	public Colaborador findByIdDadosBasicos(Long id);
	public Collection<Colaborador> findByAreaOrganizacionalIdsNome(Collection<Long> areasIds, Colaborador colaborador);
	
	public Collection<Colaborador> findByIdHistoricoAtual(Collection<Long> colaboradorIds);
	public Colaborador findByIdHistoricoAtual(Long colaboradorId);
	
	public void migrarBairro(String bairro, String bairroDestino);
	public Integer getCountAtivosEstabelecimento(Long estabelecimentoId);
	public Collection<Colaborador> findByAreaOrganizacional(Collection<Long> areaOrganizacionalIds);
	public void validaQtdCadastros() throws Exception;
	public Collection<String> findEmailsDeColaboradoresByPerfis(Collection<Perfil> perfis, Long empresaId);
	public Collection<Colaborador> findAdmitidosHaDias(Integer dias, Empresa empresa) throws Exception;
	public Collection<Colaborador> findAdmitidos(Long[] empresaIds, Date dataIni, Date dataFim, Long[] areasIds, Long[] estabelecimentosIds, boolean exibirSomenteAtivos) throws ColecaoVaziaException, Exception;
	public Collection<Colaborador> findByNomeCpfMatriculaAndResponsavelArea(Colaborador colaborador, Long empresaId, Long colaboradorLogadoId);
	public Long verificaColaboradorLogadoVerAreas();
	public Collection<Colaborador> setFamiliaAreas(Collection<Colaborador> colaboradores, Long... empresaId) throws Exception;
	public Collection<Colaborador> findByCpf(String cpf, Long empresaId);
	public Collection<Colaborador> findParticipantesDistinctByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, boolean isAvaliado, Boolean respondida);
	public Collection<Colaborador> findColaboradoresByArea(Long[] areaIds, String nome, String matricula, Long empresaId);
	public Collection<Colaborador> findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, boolean isAvaliados);
	public Integer qtdColaboradoresByTurmas(Collection<Long> turmaIds);
	public Integer getCountComHistoricoFuturoSQL(Map parametros);
	public Collection<Colaborador> findComHistoricoFuturoSQL(int page, int pagingSize, Map parametros);
	public Colaborador findTodosColaboradorCpf(String cpf, Long empresaId);
	public Collection<Colaborador> findAdmitidosNoPeriodo(Date dataReferencia, Empresa empresaSistema, String[] areasCheck, String[] estabelecimentoCheck)throws Exception;
}