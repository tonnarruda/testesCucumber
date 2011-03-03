package com.fortes.rh.dao.geral;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("unchecked")
public interface ColaboradorDao extends GenericDao<Colaborador>
{
	public Collection<Colaborador> findByAreaOrganizacionalIds(Collection<Long> areaOrganizacionalIds, Integer page, Integer pagingSize, Colaborador colaborador, Long empresaId);
	public Collection<Colaborador> findByAreaOrganizacionalIds(int page, int pagingSize, Long[] ids, Colaborador colaborador, Long empresaId);
	public Collection<Colaborador> findByAreaOrganizacionalIds(Long[] ids);
	public Collection<Colaborador> findByArea(AreaOrganizacional areaFiltro);

	public Collection<Colaborador> findSemUsuarios(Long empresaId, Usuario usuario);
	public Integer getCount(Map parametros, int tipoBuscaHistorico);
	public Collection findList(int page, int pagingSize, Map parametros, int tipoBuscaHistorico);
	public Colaborador findColaboradorPesquisa(Long id,Long empresaId);
	public Colaborador findByUsuario(Usuario usuario,Long empresaId);
	public Colaborador findColaboradorUsuarioByCpf(String cpf, Long empresaId);
	public Collection<Colaborador> findbyCandidato(Long candidatoId,Long empresaId);
	public Collection<Colaborador> findByFuncaoAmbiente(Long funcaoId, Long ambienteId);
	public boolean setCodigoColaboradorAC(String codigo, Long id);
	public Colaborador findByCodigoAC(String codigo, Empresa empresa);
	public Colaborador findColaboradorById(Long id);
	public Collection<Colaborador> findByAreaEstabelecimento(Long areaOrganizacionalId, Long estabelecimentoId);
	public Collection<Colaborador> findByAreasOrganizacionaisEstabelecimentos(Collection<Long> areasOrganizacionaisIds, Collection<Long> estabelecimentoIds, String colaboradorNome);
	public Collection<Colaborador> findByCargoIdsEstabelecimentoIds(Collection<Long> cargoIds, Collection<Long> estabelecimentoIds, String colaboradorNome);
	public Collection<Colaborador> findByGrupoOcupacionalIdsEstabelecimentoIds(Collection<Long> grupoOcupacionalIds, Collection<Long> estabelecimentoIds);
	public Collection<Colaborador> findByEstabelecimento(Long[] estabelecimentoIds);
	public Colaborador findByIdProjectionUsuario(Long colaboradorId);
	public Collection<Colaborador> findAreaOrganizacionalByAreas(boolean habilitaCampoExtra, Collection<Long> estabelecimentoIds, Collection<Long> areaOrganizacionalIds, CamposExtras camposExtras, Long empresaId, String order);
	public Colaborador findColaboradorByIdProjection(Long colaboradorId);
	public boolean atualizarUsuario(Long colaboradorId, Long usuarioId);
	public Colaborador findByIdProjectionEmpresa(Long colaboradorId);
	Collection<Colaborador> findColaborador(Map parametros);
	public Collection<Colaborador> findColaboradoresMotivoDemissao(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim);
	public List<Object[]> findColaboradoresMotivoDemissaoQuantidade(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim);

	public boolean updateDataDesligamentoByCodigo(String codigoac, Empresa empresa, Date data);
	public void desligaColaborador(boolean desligado, Date dataDesligamento, String observacao, Long motivoDemissaoId, Long colaboradorId);
	public void religaColaborador(Long colaboradorId);
	public Collection<Colaborador> findProjecaoSalarialByHistoricoColaborador(Date data, Collection<Long> estabelecimentoIds, Collection<Long> areaIds, Collection<Long> grupoIds, Collection<Long> cargoIds, String filtro, Long empresaId);
	public Collection<Colaborador> findProjecaoSalarialByTabelaReajusteColaborador(Long tabelaReajusteColaboradorId, Date data, Collection<Long> estabelecimentoIds, Collection<Long> areaIds, Collection<Long> grupoIds, Collection<Long> cargoIds, String filtro, Long empresaId);

	void setRespondeuEntrevista(Long colaboradorId);
	public boolean setMatriculaColaborador(Long empresaId, String codigoAC, String matricula);
	public boolean setMatriculaColaborador(Long colaboradorId, String matricula);
	public Colaborador findByIdComHistorico(Long colaboradorId, Integer statusRetornoAC);
	public Collection<Colaborador> findAllSelect(Long empresaId, String ordenarPor);
	public Collection<Colaborador> findAllSelect(Long... empresaIds);
	public Collection<Colaborador> findAllSelect(Collection<Long> colaboradorIds, Boolean colabDesligado);
	public void updateInfoPessoais(Colaborador colaborador);
	public Colaborador findByCodigoAC(String empregadoCodigoAC, String empresaCodigoAC, String grupoAC);
	public Long findByUsuario(Long usuarioId);
	public Colaborador findByCodigoACEmpresaCodigoAC(String codigoAC, String empresaCodigoAC, String grupoAC);
	public Integer getCountAtivos(Date dataIni, Date dataFim, Long empresaId);
	public Collection<Colaborador> findAniversariantes(Long[] empresaIds, int mes, Long[] areaIds, Long[] estabelecimentoIds);
	public Collection<Colaborador> findByNomeCpfMatricula(Colaborador colaborador, Long empresaId, Boolean somenteAtivos);
	public Colaborador findByIdHistoricoProjection(Long id);
	public Colaborador findByIdDadosBasicos(Long id);
	public Collection<Colaborador> findByIdHistoricoAtual(Collection<Long> colaboradorIds);
	public Colaborador findByIdHistoricoAtual(Long colaboradorId);
	public Integer countSemMotivos(Long[] estabelecimentoIds, Long[] areaIds, Long[] cargoIds, Date dataIni, Date dataFim);
	public void migrarBairro(String bairro, String bairroDestino);
	public Integer getCountAtivosByEstabelecimento(Long estabelecimentoId);
	public Collection<Colaborador> findByAreaOrganizacional(Collection<Long> areaIds);
	public Collection<String> findEmailsDeColaboradoresByPerfis(Long[] perfilIds, Long empresaId);
	public Collection<Colaborador> findAdmitidosHaDias(Integer dias, Empresa empresa);
	public Collection<Colaborador> findAdmitidos(Date dataIni, Date dataFim, Long[] areasIds, Long[] estabelecimentosIds, boolean exibirSomenteAtivos);
	public Collection<Colaborador> findByNomeCpfMatriculaAndResponsavelArea(Colaborador colaborador, Long id, Long colaboradorLogadoId);
	public Collection<Colaborador> findByCpf(String cpf, Long empresaId);
	public boolean updateInfoPessoaisByCpf(Colaborador colaborador, Long empresaId);
	public Collection<Colaborador> findParticipantesDistinctByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, boolean isAvaliado, Boolean respondida);
	public Collection<Colaborador> findColaboradoresByArea(Long[] areaIds, String nome, String matricula, Long empresaId);
	public Collection<Colaborador> findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, boolean isAvaliados);
	public Integer qtdColaboradoresByTurmas(Collection<Long> turmaIds);
	public Collection<Object> findComHistoricoFuturoSQL(Map parametros, Integer pagingSize, Integer page);
	public Colaborador findTodosColaboradorCpf(String cpf, Long empresaId, Long colaboradorId);
	public Collection<Colaborador> findColaboradoresEleicao(Long empresaId, Long estabelecimentosIds, Date data);
	public Collection<Colaborador> findAdmitidosNoPeriodo(Date dataReferencia, Empresa empresa, String[] areasCheck, String[] estabelecimentoCheck);
	public Collection<Colaborador> findColabPeriodoExperiencia(Long empresaId, Date periodoIni, Date periodoFim, Long modeloAvaliacaoId, Long[] areasCheck, Long[] estabelecimentosCheck);
	public void setCandidatoNull(Long idCandidato);
	public Colaborador findByUsuarioProjection(Long usuarioId);
	public Collection<String> findEmailsByPapel(Long empresaId, String codPapel);

}