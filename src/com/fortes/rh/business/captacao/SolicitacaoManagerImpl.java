package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.PausaPreenchimentoVagas;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.SpringUtil;
import com.opensymphony.xwork.ActionContext;

public class SolicitacaoManagerImpl extends GenericManagerImpl<Solicitacao, SolicitacaoDao> implements SolicitacaoManager
{
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private SolicitacaoAvaliacaoManager solicitacaoAvaliacaoManager;
	private AnuncioManager anuncioManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private EmpresaManager empresaManager;
	private PausaPreenchimentoVagasManager pausaPreenchimentoVagasManager;
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;

	public Integer getCount(char visualizar, Long empresaId, Long usuarioId, Long estabelecimentoId, Long areaOrganizacionalId, Long cargoId, Long motivoId, String descricaoBusca, char statusBusca, Long[] areasIds, String codigoBusca, Date dataInicio, Date dataFim, boolean visualiazarTodasAsSolicitacoes)
	{
		return getDao().getCount(visualizar, empresaId, usuarioId, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, descricaoBusca, statusBusca, areasIds, codigoBusca, dataInicio, dataFim, visualiazarTodasAsSolicitacoes);
	}

	public Collection<Solicitacao> findAllByVisualizacao(int page, int pagingSize, char visualizar, Long empresaId, Long usuarioId, Long estabelecimentoId, Long areaOrganizacionalId, Long cargoId, Long motivoId,
			String descricaoBusca, char statusBusca, Long[] areasIds, String codigoBusca, Date dataInicio, Date dataFim, boolean visualiazarTodasAsSolicitacoes)
	{
		return getDao().findAllByVisualizacao(page, pagingSize, visualizar, empresaId, usuarioId, estabelecimentoId, areaOrganizacionalId, cargoId, motivoId, descricaoBusca, statusBusca, areasIds, codigoBusca, 
				dataInicio, dataFim, visualiazarTodasAsSolicitacoes);
	}

	@Override
	@Deprecated
	public void remove(Solicitacao arg0)
	{
		throw new NoSuchMethodError("Não utilize a implementação genérica deste método. Use: removeCascade(Long id).");
	}

	@Override
	@Deprecated
	public void remove(Long[] arg0)
	{
		throw new NoSuchMethodError("Não utilize a implementação genérica deste método. Use: removeCascade(Long id).");
	}

	@Override
	@Deprecated
	public void remove(Long arg0)
	{
		throw new NoSuchMethodError("Não utilize a implementação genérica deste método. Use: removeCascade(Long id).");
	}

	public boolean removeCascade(Long solicitacaoId)
	{
		//verifica existencia candidatos na solicitação
		if(candidatoSolicitacaoManager.verifyExists(new String[]{"solicitacao.id"}, new Object[]{solicitacaoId}))
			return false;
		else
		{
			ColaboradorQuestionarioManager colaboradorQuestionarioManager = (ColaboradorQuestionarioManager) SpringUtil.getBean("colaboradorQuestionarioManager");
			configuracaoNivelCompetenciaManager.removeBySolicitacaoId(solicitacaoId);
			colaboradorQuestionarioManager.removeBySolicitacaoId(solicitacaoId);
			pausaPreenchimentoVagasManager.removeBySolicitacaoId(solicitacaoId);
			solicitacaoAvaliacaoManager.removeBySolicitacaoId(solicitacaoId);
			anuncioManager.removeBySolicitacao(solicitacaoId);
			getDao().remove(new Long[]{solicitacaoId});
			return true;
		}
	}

	

	public void setAnuncioManager(AnuncioManager anuncioManager) {
		this.anuncioManager = anuncioManager;
	}

	public Collection<Solicitacao> findSolicitacaoList(Long empresaId, Boolean encerrada, Character status, Boolean suspensa)
	{
		return getDao().findSolicitacaoList(empresaId, encerrada, status, suspensa);
	}

	public Solicitacao getValor(Long id)
	{
		return getDao().getValor(id);
	}

	public void encerraSolicitacao(Solicitacao solicitacao, Empresa empresa) throws Exception
	{
    	getDao().updateEncerraSolicitacao(true, solicitacao.getDataEncerramento(), solicitacao.getId(), solicitacao.getObservacaoLiberador());
    	gerenciadorComunicacaoManager.enviaEmailCandidatosNaoAptos(empresa, solicitacao.getId());
	}

	public void encerrarSolicitacaoAoPreencherTotalVagas(Solicitacao solicitacao, Empresa empresa) throws Exception
	{
		solicitacao.setDataEncerramento(new Date());
		solicitacao.setObservacaoLiberador("Encerrado através da solicitação de pessoal.");
		encerraSolicitacao(solicitacao, empresa);
	}

	public Solicitacao findByIdProjection(Long solicitacaoId)
	{
		return getDao().findByIdProjection(solicitacaoId);
	}

	public Solicitacao findByIdProjectionForUpdate(Long solicitacaoId)
	{
		return getDao().findByIdProjectionForUpdate(solicitacaoId);
	}

	public void updateEncerraSolicitacao(boolean encerrar, Date dataEncerramento, Long solicitacaoId)
	{
		getDao().updateEncerraSolicitacao(encerrar, dataEncerramento, solicitacaoId, null);
	}

	public Solicitacao findByIdProjectionAreaFaixaSalarial(Long solicitacaoId)
	{
		return getDao().findByIdProjectionAreaFaixaSalarial(solicitacaoId);
	}

	public void updateSuspendeSolicitacao(boolean suspender, String obsSuspensao, Long solicitacaoId)
	{
		getDao().updateSuspendeSolicitacao(suspender, obsSuspensao, solicitacaoId);
		
		pausarPreenchimentoVagas(suspender, solicitacaoId);
	}

	private void pausarPreenchimentoVagas(boolean suspender, Long solicitacaoId) {
		PausaPreenchimentoVagas pausaPreenchimentoVagas;
		if (suspender) {
			pausaPreenchimentoVagas = new PausaPreenchimentoVagas();
			pausaPreenchimentoVagas.setSolicitacao(getDao().findById(solicitacaoId));
			pausaPreenchimentoVagas.setDataPausa(new Date());
			pausaPreenchimentoVagasManager.save(pausaPreenchimentoVagas);
		} else {
			pausaPreenchimentoVagas =  pausaPreenchimentoVagasManager.findUltimaPausaBySolicitacaoId(solicitacaoId);
			if (pausaPreenchimentoVagas == null) {
				pausaPreenchimentoVagas = new PausaPreenchimentoVagas();
				pausaPreenchimentoVagas.setSolicitacao(getDao().findById(solicitacaoId));
				pausaPreenchimentoVagas.setDataPausa(new Date());
				pausaPreenchimentoVagas.setDataReinicio(new Date());
				pausaPreenchimentoVagasManager.save(pausaPreenchimentoVagas);
			} else {
				pausaPreenchimentoVagas.setDataReinicio(new Date());
				pausaPreenchimentoVagasManager.update(pausaPreenchimentoVagas);
			}
		}
	}
	
	public void updateSolicitacao(Solicitacao solicitacao, Long[] avaliacaoIds, Empresa empresa, Usuario usuario) throws Exception 
	{
		Solicitacao solicitacaoAux = findByIdProjectionForUpdate(solicitacao.getId());

       	if(!SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_LIBERA_SOLICITACAO"}))
        {
        	solicitacao.setStatus(solicitacaoAux.getStatus());
        	solicitacao.setLiberador(solicitacaoAux.getLiberador());
        }

       	if (solicitacao.getAreaOrganizacional() == null || solicitacao.getAreaOrganizacional().getId() == null)
        	solicitacao.setAreaOrganizacional(solicitacaoAux.getAreaOrganizacional());
        if (solicitacao.getEstabelecimento() == null || solicitacao.getEstabelecimento().getId() == null)
        	solicitacao.setEstabelecimento(solicitacaoAux.getEstabelecimento());
        if (solicitacao.getFaixaSalarial() == null || solicitacao.getFaixaSalarial().getId() == null)
        	solicitacao.setFaixaSalarial(solicitacaoAux.getFaixaSalarial());
        if (solicitacao.getEmpresa() == null || solicitacao.getEmpresa().getId() == null)
        	solicitacao.setEmpresa(solicitacaoAux.getEmpresa());
        if (solicitacao.getCidade() != null && solicitacao.getCidade().getId() == null)
        	solicitacao.setCidade(null);
      	if(solicitacao.getLiberador() == null || solicitacao.getLiberador().getId() == null)
    		solicitacao.setLiberador(null);
      	if (solicitacao.getFuncao() == null || solicitacao.getFuncao().getId() == null || solicitacao.getFuncao().getId() == -1L)
      		solicitacao.setFuncao(null);
      	if (solicitacao.getAmbiente() == null || solicitacao.getAmbiente().getId() == null || solicitacao.getAmbiente().getId() == -1L)
      		solicitacao.setAmbiente(null);
        
        update(solicitacao);
        
        solicitacaoAvaliacaoManager.saveAvaliacoesSolicitacao(solicitacao.getId(), avaliacaoIds);
		
		if(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_LIBERA_SOLICITACAO"}))
        {
        	if(solicitacao.getStatus() != solicitacaoAux.getStatus())
        	{
        		solicitacao.setLiberador(usuario);
        		emailSolicitante(solicitacao, empresa, usuario);
        	}
        }
	}
	
	public void updateStatusSolicitacao(Solicitacao solicitacao) 
	{
		getDao().updateStatusSolicitacao(solicitacao);
	}

	public void migrarBairro(Long bairroId, Long bairroDestinoId)
	{
		getDao().migrarBairro(bairroId, bairroDestinoId);
	}
	
	public Solicitacao save(Solicitacao solicitacao, String[] emailsMarcados, Long[] avaliacaoIds)
	{
		super.save(solicitacao);
		solicitacaoAvaliacaoManager.saveAvaliacoesSolicitacao(solicitacao.getId(), avaliacaoIds);
		try {
			gerenciadorComunicacaoManager.enviarEmailParaResponsaveisSolicitacaoPessoal(solicitacao, solicitacao.getEmpresa(), emailsMarcados);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return solicitacao;
	}

	public void emailSolicitante(Solicitacao solicitacao, Empresa empresa, Usuario usuario)
	{
		solicitacao = getDao().findByIdProjectionForUpdate(solicitacao.getId());
		gerenciadorComunicacaoManager.enviaEmailSolicitanteSolicitacao(solicitacao, empresa, usuario);
	}

	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorMotivosSolicitacao(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long empresaId, char statusSolicitacao, char dataStatusAprovacaoSolicitacao, boolean indicadorResumido)
	{
		return getDao().getIndicadorMotivosSolicitacao(dataDe, dataAte, areasOrganizacionais, estabelecimentos, empresaId, statusSolicitacao, dataStatusAprovacaoSolicitacao, indicadorResumido);
	}
	
	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorQtdVagas(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long[] solicitacaoIds) {
		return getDao().getIndicadorQtdVagas(dataDe, dataAte, areasOrganizacionais, estabelecimentos, solicitacaoIds);
	}

	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorMediaDiasPreenchimentoVagas(Date inicio, Date fim, Collection<Long> areasIds, Collection<Long> estabelecimentosIds, Long[] solicitacaoIds, Long empresaId, boolean considerarContratacaoFutura) {
		return getDao().getIndicadorMediaDiasPreenchimentoVagas(inicio, fim, areasIds, estabelecimentosIds, solicitacaoIds, empresaId, considerarContratacaoFutura);
	}

	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorQtdCandidatos(Date dataDe, Date dataAte, Collection<Long> areasIds, Collection<Long> estabelecimentosIds, Long[] solicitacaoIds) {
		return getDao().getIndicadorQtdCandidatos(dataDe, dataAte, areasIds, estabelecimentosIds, solicitacaoIds);
	}

	public Collection<Solicitacao> findAllByCandidato(Long candidatoId) {
		return getDao().findAllByCandidato(candidatoId);
	}

	public Collection<FaixaSalarial> findQtdVagasDisponiveis(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim, char dataStatusAprovacaoSolicitacao) {
		return getDao().findQtdVagasDisponiveis(empresaId, estabelecimentoIds, areaIds, solicitacaoIds, dataIni, dataFim, dataStatusAprovacaoSolicitacao);
	}

	public Collection<DataGrafico> findQtdContratadosPorFaixa(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim) {
		Collection<DataGrafico> graficoContratadosFaixa = new ArrayList<DataGrafico>();
		Collection<FaixaSalarial> faixasSalariaisContratados = getDao().findQtdContratadosFaixa(empresaId, estabelecimentoIds, areaIds, solicitacaoIds, dataIni, dataFim);
		
		for (FaixaSalarial faixaSalarial : faixasSalariaisContratados)
			graficoContratadosFaixa.add(new DataGrafico(null, faixaSalarial.getDescricao(), faixaSalarial.getQtdContratados(), ""));
		
		return graficoContratadosFaixa;
	}

	public Collection<DataGrafico> findQtdContratadosPorArea(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim) {
		Collection<DataGrafico> graficoContratadosArea = new ArrayList<DataGrafico>();
		Collection<AreaOrganizacional> areasContratados = getDao().findQtdContratadosArea(empresaId, estabelecimentoIds, areaIds, solicitacaoIds, dataIni, dataFim);
		
		for (AreaOrganizacional area : areasContratados)
			graficoContratadosArea.add(new DataGrafico(null, area.getNome(), area.getQtdContratados(), ""));
		
		return graficoContratadosArea;
	}

	public Collection<DataGrafico> findQtdContratadosPorMotivo(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] solicitacaoIds, Date dataIni, Date dataFim) {
		Collection<DataGrafico> graficoContratadosMotivo = new ArrayList<DataGrafico>();
		Collection<MotivoSolicitacao> motivos = getDao().findQtdContratadosMotivo(empresaId, estabelecimentoIds, areaIds, solicitacaoIds, dataIni, dataFim);
		
		for (MotivoSolicitacao motivo : motivos)
			graficoContratadosMotivo.add(new DataGrafico(null, motivo.getDescricao(), motivo.getQtdContratados(), ""));
		
		return graficoContratadosMotivo;
	}

	public Collection<String> getNomesColabSubstituidosSolicitacaoEncerrada(Long empresaId) {
		Collection<Solicitacao> nomesColabSubstituidosSolicitacaoEncerrada = getDao().getNomesColabSubstituidosSolicitacaoEncerrada(empresaId);
		Collection<String> nomesColabSubstituidos = new ArrayList<String>();
		String[] nomesArray;
		for (Solicitacao solicitacao : nomesColabSubstituidosSolicitacaoEncerrada){
			if(solicitacao.getColaboradorSubstituido() != null){
				nomesArray = solicitacao.getColaboradorSubstituido().split(",");
				for (String nomeArray : nomesArray) 
					nomesColabSubstituidos.add(nomeArray.trim());
			}
		}
				
		return nomesColabSubstituidos;
	}
	
	public Collection<Solicitacao> findByEmpresaEstabelecimentosAreas(Long empresaId, Long[] estabelecimentosIds, Long[] areasIds){
		return getDao().findByEmpresaEstabelecimentosAreas(empresaId, estabelecimentosIds, areasIds);
	}

	@SuppressWarnings("deprecation")
	public void atualizaStatusSolicitacaoByColaborador(Colaborador colaborador,	char status, boolean disponibilizarCandidato) 
	{
		if(colaborador.getSolicitacao() != null && colaborador.getSolicitacao().getId() != null)
			candidatoSolicitacaoManager.setStatusBySolicitacaoAndCandidato(status, colaborador.getCandidato().getId(), colaborador.getSolicitacao().getId() );
		
		Empresa empresa = empresaManager.findByIdProjection(colaborador.getEmpresa().getId()); 
		if(empresa.isSolPessoalReabrirSolicitacao() && colaborador.getSolicitacao() != null && colaborador.getSolicitacao().getId() != null){
			updateEncerraSolicitacao(false, null, colaborador.getSolicitacao().getId());
		}
		
		CandidatoManager candidatoManager = (CandidatoManager) SpringUtil.getBeanOld("candidatoManager");
		if(disponibilizarCandidato && colaborador.getCandidato() != null && colaborador.getCandidato().getId() != null)
			candidatoManager.updateDisponivelAndContratadoByColaborador(true, false, colaborador.getId());
	}
	
	public double calculaIndicadorVagasPreenchidasNoPrazo(Long empresaId, Long[] estabelecimentosIds, Long[] areasIds, Long[] solicitacoesIds, Date dataDe, Date dataAte) {
		
		Collection<Solicitacao> solicitacoes = getDao().calculaIndicadorVagasPreenchidasNoPrazo(empresaId, estabelecimentosIds, areasIds, solicitacoesIds, dataDe, dataAte);

		if(solicitacoes == null || solicitacoes.size() == 0)
			return 0.0;
		
		Double totalPercentual = 0.0;
		for (Solicitacao solicitacao : solicitacoes) {
			if(solicitacao.getQtdVagasPreenchidas() >= solicitacao.getQuantidade())
				totalPercentual += 1.0;
			else
				totalPercentual += new Double(solicitacao.getQtdVagasPreenchidas()) / solicitacao.getQuantidade(); 
		}
		
		return (totalPercentual / solicitacoes.size()) * 100.0;
	}

	public void setCandidatoSolicitacaoManager(	CandidatoSolicitacaoManager candidatoSolicitacaoManager) {
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}
	
	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}

	public void setSolicitacaoAvaliacaoManager(SolicitacaoAvaliacaoManager solicitacaoAvaliacaoManager) {
		this.solicitacaoAvaliacaoManager = solicitacaoAvaliacaoManager;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public void setPausaPreenchimentoVagasManager(PausaPreenchimentoVagasManager pausaPreenchimentoVagasManager) {
		this.pausaPreenchimentoVagasManager = pausaPreenchimentoVagasManager;
	}

	public void setConfiguracaoNivelCompetenciaManager(
			ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}
}