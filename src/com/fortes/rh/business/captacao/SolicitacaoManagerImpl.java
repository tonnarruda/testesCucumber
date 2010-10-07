/* Autor: Robertson Freitas
 * Data: 23/06/2006
 * Requisito: RFA015
 */

package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.acesso.PerfilManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.StringUtil;

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

	public Collection<Solicitacao> findSolicitacaoList(Long empresaId, Boolean encerrada, Boolean liberada, Boolean suspensa)
	{
		return getDao().findSolicitacaoList(empresaId, encerrada, liberada, suspensa);
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

	public void migrarBairro(Long bairroId, Long bairroDestinoId)
	{
		getDao().migrarBairro(bairroId, bairroDestinoId);
	}
	
	@Override
	public Solicitacao save(Solicitacao solicitacao)
	{
		super.save(solicitacao);
		
		try {
			
			if (!solicitacao.isLiberada())
				enviarEmailParaLiberadorSolicitacao(solicitacao, solicitacao.getEmpresa());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return solicitacao;
	}

	public void enviarEmailParaLiberadorSolicitacao(Solicitacao solicitacao, Empresa empresa) throws Exception
	{
		ParametrosDoSistema parametrosDoSistema = (ParametrosDoSistema) parametrosDoSistemaManager.findAll().toArray()[0];
		String link = parametrosDoSistema.getAppUrl();
		
		Collection<String> emails = perfilManager.getEmailsByRoleLiberaSolicitacao(empresa.getId());
		
		if (emails != null && !emails.isEmpty())
		{
			solicitacao = getDao().findByIdProjectionForUpdate(solicitacao.getId());
		
			String subject = "Liberação de Solicitação de Pessoal";
			StringBuilder body = new StringBuilder("Existe uma Solicitação de Pessoal na empresa " + empresa.getNome() + " aguardando liberação.<br>");
			if (solicitacao.getDescricao() != null)
				body.append("<p style=\"font-weight:bold;\">" + solicitacao.getDescricao() + "</p>");
			body.append("<br>Estabelecimento: " + solicitacao.getEstabelecimento().getNome());
			body.append("<br>Data: " + DateUtil.formataDiaMesAno(solicitacao.getData()));
			body.append("<br>Motivo: " + solicitacao.getMotivoSolicitacao().getDescricao());
			body.append("<br>Solicitante: " + solicitacao.getSolicitante().getNome() + " (" + solicitacao.getSolicitante().getLogin() + ")");
			body.append("<br>Acesse o FortesRH para mais detalhes:<br>");
			body.append("<a href='" + link + "'>Fortes RH</a>");
			
			mail.send(empresa, parametrosDoSistema, subject, body.toString(), null, StringUtil.converteCollectionToArrayString(emails));
		}
	}
	
	public List<IndicadorDuracaoPreenchimentoVaga> getIndicadorMotivosSolicitacao(Date dataDe, Date dataAte, Collection<Long> areasOrganizacionais, Collection<Long> estabelecimentos, Long empresaId)
	{
		return getDao().getIndicadorMotivosSolicitacao(dataDe, dataAte, areasOrganizacionais, estabelecimentos, empresaId);
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
}