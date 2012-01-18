/* Autor: Robertson Freitas
 * Data: 23/06/2006
 * Requisito: RFA015
 */

package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.acesso.PerfilManager;
import com.fortes.rh.business.geral.ColaboradorManager;
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
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.StringUtil;
import com.opensymphony.xwork.ActionContext;

public class SolicitacaoManagerImpl extends GenericManagerImpl<Solicitacao, SolicitacaoDao> implements SolicitacaoManager
{
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private AnuncioManager anuncioManager;
	private Mail mail;
	private PerfilManager perfilManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

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
    	candidatoSolicitacaoManager.enviarEmailNaoApto(solicitacao.getId(), empresa);
    	getDao().updateEncerraSolicitacao(true, solicitacao.getDataEncerramento(), solicitacao.getId());
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
	
	public void updateSolicitacao(Solicitacao solicitacao, Empresa empresa, Usuario usuario) 
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
	
	public Solicitacao save(Solicitacao solicitacao, String[] emailsAvulsos)
	{
		super.save(solicitacao);
		
		try {
			enviarEmailParaLiberadorSolicitacao(solicitacao, solicitacao.getEmpresa(), emailsAvulsos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return solicitacao;
	}

	public void enviarEmailParaLiberadorSolicitacao(Solicitacao solicitacao, Empresa empresa, String[] emailsAvulsos) throws Exception
	{
		ParametrosDoSistema parametrosDoSistema = (ParametrosDoSistema) parametrosDoSistemaManager.findById(1L);
		String link = parametrosDoSistema.getAppUrl();
		
		Collection<String> emails = perfilManager.getEmailsByRoleLiberaSolicitacao(empresa.getId());
		incluiEmails(emails, emailsAvulsos);
		
		if(solicitacao.getStatus() != StatusAprovacaoSolicitacao.ANALISE)
			emails.add(empresa.getEmailRespRH());
		
		if (emails != null && !emails.isEmpty())
		{
			ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
			String nomeSolicitante = colaboradorManager.findByUsuarioProjection(solicitacao.getSolicitante().getId()).getNomeMaisNomeComercial();
			
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
	
	private void incluiEmails(Collection<String> emails, String[] emailsAvulsos) 
	{
		if(emailsAvulsos != null)
		{
			for (String emailAvulso : emailsAvulsos) 
			{
				emails.add(emailAvulso);
			}
		}
	}

	public void emailSolicitante(Solicitacao solicitacao, Empresa empresa, Usuario usuario)
	{
		try {
			ParametrosDoSistema parametrosDoSistema = (ParametrosDoSistema) parametrosDoSistemaManager.findById(1L);

			solicitacao = getDao().findByIdProjectionForUpdate(solicitacao.getId());
			String link = parametrosDoSistema.getAppUrl() + "/captacao/solicitacao/prepareUpdate.action?solicitacao.id=" + solicitacao.getId();

			ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
			Colaborador colaboradorSolicitante = colaboradorManager.findByUsuarioProjection(solicitacao.getSolicitante().getId());
			Colaborador colaboradorLiberador = colaboradorManager.findByUsuarioProjection(usuario.getId());
			
			String nomeSolicitante = colaboradorSolicitante !=null ? colaboradorSolicitante.getNomeMaisNomeComercial():"";
			String nomeLiberador = colaboradorSolicitante !=null ? colaboradorLiberador.getNomeMaisNomeComercial():usuario.getNome();
			
			String subject = "Status da solicitação de pessoal";
			StringBuilder body = new StringBuilder("<span style='font-weight:bold'>O usuário "+ nomeLiberador + " alterou o status da solicitação " + solicitacao.getDescricao() + " da empresa " + empresa.getNome() + " para " + solicitacao.getStatusFormatado().toLowerCase()  +".</span><br>");
			
			montaCorpoEmailSolicitacao(solicitacao, link, nomeSolicitante, nomeLiberador, body);
			
			String emailSolicitante = null;
			if(colaboradorSolicitante != null && colaboradorSolicitante.getContato() != null)
				emailSolicitante = colaboradorSolicitante.getContato().getEmail();
			
			mail.send(empresa, parametrosDoSistema, subject, body.toString(), new String[]{emailSolicitante, empresa.getEmailRespRH()});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void montaCorpoEmailSolicitacao(Solicitacao solicitacao, String link, String nomeSolicitante, String nomeLiberador, StringBuilder body)
	{
		body.append("<br>Descrição: " + solicitacao.getDescricao());
		body.append("<br>Data: " + DateUtil.formataDiaMesAno(solicitacao.getData()));
		body.append("<br>Motivo: " + solicitacao.getMotivoSolicitacao().getDescricao());
		body.append("<br>Estabelecimento: " + solicitacao.getEstabelecimento().getNome());
		body.append("<br>Solicitante: " + nomeSolicitante);
		body.append("<br>Status: " + solicitacao.getStatusFormatado());
		body.append("<br>Liberador: " + nomeLiberador);
		body.append("<br>Observação do Liberador: " + StringUtils.trimToEmpty(solicitacao.getObservacaoLiberador()));
		
		body.append("<br><br>Acesse o RH para mais detalhes:<br>");
		body.append("<a href='" + link + "'>RH</a>");
	}
	
	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorMotivosSolicitacao(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long empresaId, char statusSolicitacao)
	{
		return getDao().getIndicadorMotivosSolicitacao(dataDe, dataAte, areasOrganizacionais, estabelecimentos, empresaId, statusSolicitacao);
	}
	
	public void setMail(Mail mail) {
		this.mail = mail;
	}
	
	public void setPerfilManager(PerfilManager perfilManager) {
		this.perfilManager = perfilManager;
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


}