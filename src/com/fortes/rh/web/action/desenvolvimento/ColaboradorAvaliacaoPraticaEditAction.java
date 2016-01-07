package com.fortes.rh.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorAvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class ColaboradorAvaliacaoPraticaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager;
	private CertificacaoManager certificacaoManager;
	private ColaboradorManager colaboradorManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private AvaliacaoPraticaManager avaliacaoPraticaManager;
	private ColaboradorCertificacaoManager colaboradorCertificacaoManager;

	private ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica;
	private Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas;
	
	private Collection<Certificacao> certificacoes = new ArrayList<Certificacao>();
	private Collection<Colaborador>  colaboradores = new ArrayList<Colaborador>();
	private Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
	private Collection<ColaboradorCertificacao> colaboradorCertificacaos = new ArrayList<ColaboradorCertificacao>(); 
	
	private Certificacao certificacao;
	private Colaborador colaborador; 
	private ColaboradorCertificacao colaboradorCertificacao;
	
	public String prepare() throws Exception
	{
		certificacoes = certificacaoManager.findAllSelect(getEmpresaSistema().getId());
		
		if(colaboradorAvaliacaoPratica != null && colaboradorAvaliacaoPratica.getId() != null)
			colaboradorAvaliacaoPratica = (ColaboradorAvaliacaoPratica) colaboradorAvaliacaoPraticaManager.findById(colaboradorAvaliacaoPratica.getId());
		
		return Action.SUCCESS;
	}
	
	public String buscaColaboradores() throws Exception
	{
		prepare();
		if(certificacao == null || certificacao.getId() == null)
			return Action.SUCCESS;

		Collection<Colaborador> colaboradoresNaCertificacao = certificacaoManager.findColaboradoresNaCertificacoa(certificacao.getId());

		if(colaborador != null)
		{
			findColaboradoresPermitidosNaCertificacao(colaboradoresNaCertificacao);	
			
			colaboradorTurmas = new ArrayList<ColaboradorTurma>();
 			if(colaborador.getId() != null && certificacao != null && certificacao.getId() != null)
			{
				colaboradorCertificacaos = colaboradorCertificacaoManager.findByColaboradorIdAndCertificacaoId(colaborador.getId(), certificacao.getId());
				colaboradorTurmas = colaboradorTurmaManager.findByColaboradorIdAndCertificacaoIdAndColabCertificacaoId(colaborador.getId(), certificacao.getId(), colaboradorCertificacao.getId());
				populaColaboradorAvaliacaoPratica();
			}
		}
		
		return Action.SUCCESS;
	}

	private void findColaboradoresPermitidosNaCertificacao(Collection<Colaborador> colaboradoresNaCertificacao) throws Exception 
	{
		Colaborador colaboradorLogado = SecurityUtil.getColaboradorSession(ActionContext.getContext().getSession());
		Collection<Colaborador> colaboradoresPermitidos = new ArrayList<Colaborador>();

		if (SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VER_AREAS"}))
		{
			colaboradoresPermitidos = colaboradorManager.findByNomeCpfMatriculaComHistoricoComfirmado(colaborador, getEmpresaSistema().getId(), null);
		}
		else if (colaboradorLogado != null && colaboradorLogado.getId() != null)
		{
			Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findAreasByUsuarioResponsavel(getUsuarioLogado(), getEmpresaSistema().getId());
			Long[] areasIds = new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(areas);
			if (areasIds.length == 0)
				areasIds = new Long[]{-1L};
			colaboradoresPermitidos = colaboradorManager.findByNomeCpfMatriculaComHistoricoComfirmado(colaborador, getEmpresaSistema().getId(), areasIds);
		}

		for (Colaborador colaboradorCertificado : colaboradoresNaCertificacao) 
		{
			for (Colaborador colaboradorPermitido : colaboradoresPermitidos) 
			{
				if(colaboradorCertificado.getId().equals(colaboradorPermitido.getId()))
					colaboradores.add(colaboradorPermitido);
			}
		}
	}

	private void populaColaboradorAvaliacaoPratica() 
	{
		Collection<AvaliacaoPratica> avaliacoesPraticasDoCertificado = avaliacaoPraticaManager.findByCertificacaoId(certificacao.getId());
		Collection<ColaboradorAvaliacaoPratica> avaliacoesPraticasDoColaboradorRealizadas = colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colaborador.getId(), certificacao.getId(), colaboradorCertificacao.getId());
		colaboradorAvaliacaoPraticas = new ArrayList<ColaboradorAvaliacaoPratica>();
		boolean existeNota = false;
		
		for (AvaliacaoPratica avaliacaoPraticaDoCertificado : avaliacoesPraticasDoCertificado) 
		{
			existeNota = false;
			
			for (ColaboradorAvaliacaoPratica avaliacaoPraticaDoColaboradorRealizada : avaliacoesPraticasDoColaboradorRealizadas) 
			{
				if(avaliacaoPraticaDoColaboradorRealizada.getAvaliacaoPratica().getId().equals(avaliacaoPraticaDoCertificado.getId()))
				{
					avaliacaoPraticaDoColaboradorRealizada.setAvaliacaoPratica(avaliacaoPraticaDoCertificado);
					colaboradorAvaliacaoPraticas.add(avaliacaoPraticaDoColaboradorRealizada);
					existeNota = true;
					break;
				}
			}
			
			if(!existeNota && avaliacaoPraticaDoCertificado.getId() != null){
				ColaboradorAvaliacaoPratica ColaboradorAvaliacaoPratica = new ColaboradorAvaliacaoPratica();
				ColaboradorAvaliacaoPratica.setAvaliacaoPratica(avaliacaoPraticaDoCertificado);
				colaboradorAvaliacaoPraticas.add(ColaboradorAvaliacaoPratica);
			}
		}
	}
	
	public String insertOrUpdate() throws Exception
	{
		Collection<ColaboradorAvaliacaoPratica> avaliacoesPraticasDoColaboradorRealizadas = colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colaborador.getId(), certificacao.getId(), colaboradorCertificacao.getId());
		
		boolean edicao;
		for (ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica : colaboradorAvaliacaoPraticas) 
		{
			edicao = false;
			for (ColaboradorAvaliacaoPratica colaboradorAvaliacaoPraticaRealizada : avaliacoesPraticasDoColaboradorRealizadas) 
			{
				if(colaboradorAvaliacaoPraticaRealizada.getId().equals(colaboradorAvaliacaoPratica.getId()))
				{
					edicao = true;	
					
					if(colaboradorAvaliacaoPratica.getData() != null && colaboradorAvaliacaoPratica.getNota() != null){
						colaboradorAvaliacaoPraticaRealizada.setNota(colaboradorAvaliacaoPratica.getNota());
						colaboradorAvaliacaoPraticaRealizada.setCertificacao(certificacao);
						colaboradorAvaliacaoPraticaRealizada.setColaborador(colaborador);
						colaboradorAvaliacaoPraticaManager.update(colaboradorAvaliacaoPraticaRealizada);
						AvaliacaoPratica avaliacaoPratica = avaliacaoPraticaManager.findEntidadeComAtributosSimplesById(colaboradorAvaliacaoPratica.getAvaliacaoPratica().getId());
						if(colaboradorAvaliacaoPratica.getNota() < avaliacaoPratica.getNotaMinima())
							colaboradorCertificacaoManager.descertificarColaborador(colaboradorCertificacao.getId(), false);
					}else{
						colaboradorAvaliacaoPraticaManager.removeColaboradorAvaliacaoPraticaAndColaboradorCertificado(colaboradorAvaliacaoPraticaRealizada);
					}
					
					break;
				}
			}

			if(!edicao && colaboradorAvaliacaoPratica.getData() != null && colaboradorAvaliacaoPratica.getNota() != null)
			{
				colaboradorAvaliacaoPratica.setId(null);
				colaboradorAvaliacaoPratica.setCertificacao(certificacao);
				colaboradorAvaliacaoPratica.setColaborador(colaborador);
				colaboradorAvaliacaoPraticaManager.save(colaboradorAvaliacaoPratica);
				
			}	
		}
		
		if(getEmpresaSistema().isControlarVencimentoPorCertificacao())
		{
			Collection<ColaboradorCertificacao> colaboradoresCertificados = colaboradorCertificacaoManager.colaboradoresCertificadosByColaboradorIdAndCertificacaId(colaborador.getId(), certificacao.getId());
			
			for (ColaboradorCertificacao colaboradorCertificacao : colaboradoresCertificados) 
			{
				colaboradorCertificacaoManager.saveColaboradorCertificacao(colaboradorCertificacao);
			}
		}
		
		return buscaColaboradores();
	}

	public ColaboradorAvaliacaoPratica getColaboradorAvaliacaoPratica()
	{
		if(colaboradorAvaliacaoPratica == null)
			colaboradorAvaliacaoPratica = new ColaboradorAvaliacaoPratica();
		return colaboradorAvaliacaoPratica;
	}

	public void setColaboradorAvaliacaoPratica(ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica)
	{
		this.colaboradorAvaliacaoPratica = colaboradorAvaliacaoPratica;
	}

	public Collection<ColaboradorAvaliacaoPratica> getColaboradorAvaliacaoPraticas()
	{
		return colaboradorAvaliacaoPraticas;
	}

	public Collection<Certificacao> getCertificacoes() {
		return certificacoes;
	}

	public void setCertificacao(Certificacao certificacao) {
		this.certificacao = certificacao;
	}
	
	public Colaborador getColaborador() {
		return colaborador;
	}
	
	public void setColaborador(Colaborador colaborador){
		this.colaborador = colaborador;
	}

	public void setCertificacaoManager(CertificacaoManager certificacaoManager) {
		this.certificacaoManager = certificacaoManager;
	}

	public void setColaboradorAvaliacaoPraticaManager(ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager)
	{
		this.colaboradorAvaliacaoPraticaManager = colaboradorAvaliacaoPraticaManager;
	}

	public Collection<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public Certificacao getCertificacao() {
		return certificacao;
	}

	public Collection<ColaboradorTurma> getColaboradorTurmas() {
		return colaboradorTurmas;
	}

	public void setColaboradorTurmaManager(ColaboradorTurmaManager colaboradorTurmaManager) {
		this.colaboradorTurmaManager = colaboradorTurmaManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setColaboradorAvaliacaoPraticas(Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas) {
		this.colaboradorAvaliacaoPraticas = colaboradorAvaliacaoPraticas;
	}

	public void setAvaliacaoPraticaManager(
			AvaliacaoPraticaManager avaliacaoPraticaManager) {
		this.avaliacaoPraticaManager = avaliacaoPraticaManager;
	}

	public Date getHoje() {
		return new Date();
	}

	public Collection<ColaboradorCertificacao> getColaboradorCertificacaos() {
		return colaboradorCertificacaos;
	}

	public void setColaboradorCertificacao(
			ColaboradorCertificacao colaboradorCertificacao) {
		this.colaboradorCertificacao = colaboradorCertificacao;
	}

	public void setColaboradorCertificacaoManager(
			ColaboradorCertificacaoManager colaboradorCertificacaoManager) {
		this.colaboradorCertificacaoManager = colaboradorCertificacaoManager;
	}

	public ColaboradorCertificacao getColaboradorCertificacao() {
		return colaboradorCertificacao;
	}
}
