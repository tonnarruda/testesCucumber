/* Autor: Robertson Freitas
 * Data: 23/06/2006
 * Requisito: RFA015
 */

package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.ant.task.emma.manager.Gerenciador;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.StringUtil;
import com.opensymphony.xwork.ActionContext;

public class SolicitacaoManagerImpl extends GenericManagerImpl<Solicitacao, SolicitacaoDao> implements SolicitacaoManager
{
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private AnuncioManager anuncioManager;
	private Mail mail;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;

	public Integer getCount(char visualizar, boolean liberaSolicitacao, Long empresaId, Long usuarioId, Long cargoId)
	{
		Usuario usuario = new Usuario();
		usuario.setId(usuarioId);

		return getDao().getCount(visualizar, liberaSolicitacao, empresaId, usuario, cargoId);
	}

	public Collection<Solicitacao> findAllByVisualizacao(int page, int pagingSize, char visualizar, boolean liberaSolicitacao, Long empresaId, Long usuarioId, Long cargoId)
	{
		Usuario usuario = new Usuario();
		usuario.setId(usuarioId);

		return getDao().findAllByVisualizacao(page, pagingSize, visualizar, liberaSolicitacao, empresaId, usuario, cargoId);
	}

	public Integer getCount(char visualizar, boolean liberaSolicitacao, Long empresaId, Long cargoId)
	{
		return getDao().getCount(visualizar, liberaSolicitacao, empresaId, null, cargoId);
	}

	public Collection<Solicitacao> findAllByVisualizacao(int page, int pagingSize, char visualizar, boolean liberaSolicitacao, Long empresaId, Long cargoId)
	{
		return getDao().findAllByVisualizacao(page, pagingSize, visualizar,liberaSolicitacao, empresaId, null, cargoId);
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
			anuncioManager.removeBySolicitacao(solicitacaoId);
			getDao().remove(new Long[]{solicitacaoId});
			return true;
		}
	}

	public void setCandidatoSolicitacaoManager(
			CandidatoSolicitacaoManager candidatoSolicitacaoManager) {
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
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
    	getDao().updateEncerraSolicitacao(true, solicitacao.getDataEncerramento(), solicitacao.getId());
    	gerenciadorComunicacaoManager.executeEncerrarSolicitacao(empresa, solicitacao.getId());
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
		getDao().updateEncerraSolicitacao(encerrar, dataEncerramento, solicitacaoId);
	}

	public Solicitacao findByIdProjectionAreaFaixaSalarial(Long solicitacaoId)
	{
		return getDao().findByIdProjectionAreaFaixaSalarial(solicitacaoId);
	}

	public void updateSuspendeSolicitacao(boolean suspender, String obsSuspensao, Long solicitacaoId)
	{
		getDao().updateSuspendeSolicitacao(suspender, obsSuspensao, solicitacaoId);
	}
	
	public void updateSolicitacao(Solicitacao solicitacao, Empresa empresa, Usuario usuario) throws Exception 
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

        if (solicitacao.getAvaliacao().getId() == null)
        	solicitacao.setAvaliacao(null);
        if (solicitacao.getCidade() != null && solicitacao.getCidade().getId() == null)
        	solicitacao.setCidade(null);
      	if(solicitacao.getLiberador() == null || solicitacao.getLiberador().getId() == null)
    		solicitacao.setLiberador(null);
        
        update(solicitacao);
		
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
	
	public Solicitacao save(Solicitacao solicitacao, String[] emailsMarcados)
	{
		super.save(solicitacao);
		
		try {
			enviarEmailParaResponsaveis(solicitacao, solicitacao.getEmpresa(), emailsMarcados);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return solicitacao;
	}

	public void enviarEmailParaResponsaveis(Solicitacao solicitacao, Empresa empresa, String[] emailsMarcados) throws Exception
	{
		ParametrosDoSistema parametrosDoSistema = (ParametrosDoSistema) parametrosDoSistemaManager.findById(1L);
		String link = parametrosDoSistema.getAppUrl();
		
		Collection<String> emails = new CollectionUtil<String>().convertArrayToCollection(emailsMarcados);
		emails.add(empresa.getEmailRespRH());
		
		if (emails != null && !emails.isEmpty())
		{
			ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
			Colaborador solicitante = colaboradorManager.findByUsuarioProjection(solicitacao.getSolicitante().getId());
			
			String nomeSolicitante = "";
			if(solicitante != null)
				nomeSolicitante = solicitante.getNomeMaisNomeComercial();
			
			solicitacao = getDao().findByIdProjectionForUpdate(solicitacao.getId());
		
			String nomeLiberador = "";
			if(solicitacao.getStatus() != StatusAprovacaoSolicitacao.ANALISE)
		        nomeLiberador = nomeSolicitante;
			
			String subject = "Liberação de Solicitação de Pessoal";
			StringBuilder body = new StringBuilder("Existe uma Solicitação de Pessoal na empresa " + empresa.getNome() + " aguardando liberação.<br>");
			
			if (solicitacao.getDescricao() != null)
				body.append("<p style=\"font-weight:bold;\">" + solicitacao.getDescricao() + "</p>");
			
			montaCorpoEmailSolicitacao(solicitacao, link, nomeSolicitante, nomeLiberador, body);
			
			mail.send(empresa, parametrosDoSistema, subject, body.toString(), StringUtil.converteCollectionToArrayString(emails));
		}
	}

	public void emailSolicitante(Solicitacao solicitacao, Empresa empresa, Usuario usuario)
	{
		solicitacao = getDao().findByIdProjectionForUpdate(solicitacao.getId());
		gerenciadorComunicacaoManager.emailSolicitante(solicitacao, empresa, usuario);
	}

	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorMotivosSolicitacao(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long empresaId, char statusSolicitacao)
	{
		return getDao().getIndicadorMotivosSolicitacao(dataDe, dataAte, areasOrganizacionais, estabelecimentos, empresaId, statusSolicitacao);
	}
	
	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorQtdVagas(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos) {
		return getDao().getIndicadorQtdVagas(dataDe, dataAte, areasOrganizacionais, estabelecimentos);
	}

	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorMediaDiasPreenchimentoVagas(Date inicio, Date fim, Collection<Long> areasIds, Collection<Long> estabelecimentosIds) {
		return getDao().getIndicadorMediaDiasPreenchimentoVagas(inicio, fim, areasIds, estabelecimentosIds);
	}

	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorQtdCandidatos(Date dataDe, Date dataAte, Collection<Long> areasIds, Collection<Long> estabelecimentosIds) {
		return getDao().getIndicadorQtdCandidatos(dataDe, dataAte, areasIds, estabelecimentosIds);
	}

	public Collection<Solicitacao> findAllByCandidato(Long candidatoId) {
		return getDao().findAllByCandidato(candidatoId);
	}

	public Collection<FaixaSalarial> findQtdVagasDisponiveis(Long empresaId, Date dataIni, Date dataFim) {
		return getDao().findQtdVagasDisponiveis(empresaId, dataIni, dataFim);
	}

	public Collection<DataGrafico> findQtdContratadosPorFaixa(Long empresaId, Date dataIni, Date dataFim) {
		Collection<DataGrafico> graficoContratadosFaixa = new ArrayList<DataGrafico>();
		Collection<FaixaSalarial> faixasSalariaisContratados = getDao().findQtdContratadosFaixa(empresaId, dataIni, dataFim);
		
		for (FaixaSalarial faixaSalarial : faixasSalariaisContratados)
			graficoContratadosFaixa.add(new DataGrafico(null, faixaSalarial.getDescricao(), faixaSalarial.getQtdContratados(), ""));
		
		return graficoContratadosFaixa;
	}

	public Collection<DataGrafico> findQtdContratadosPorArea(Long empresaId, Date dataIni, Date dataFim) {
		Collection<DataGrafico> graficoContratadosArea = new ArrayList<DataGrafico>();
		Collection<AreaOrganizacional> areasContratados = getDao().findQtdContratadosArea(empresaId, dataIni, dataFim);
		
		for (AreaOrganizacional area : areasContratados)
			graficoContratadosArea.add(new DataGrafico(null, area.getNome(), area.getQtdContratados(), ""));
		
		return graficoContratadosArea;
	}

	public Collection<DataGrafico> findQtdContratadosPorMotivo(Long empresaId, Date dataIni, Date dataFim) {
		Collection<DataGrafico> graficoContratadosMotivo = new ArrayList<DataGrafico>();
		Collection<MotivoSolicitacao> motivos = getDao().findQtdContratadosMotivo(empresaId, dataIni, dataFim);
		
		for (MotivoSolicitacao motivo : motivos)
			graficoContratadosMotivo.add(new DataGrafico(null, motivo.getDescricao(), motivo.getQtdContratados(), ""));
		
		return graficoContratadosMotivo;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}
}