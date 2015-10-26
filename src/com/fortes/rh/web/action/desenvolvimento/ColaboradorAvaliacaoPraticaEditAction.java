package com.fortes.rh.web.action.desenvolvimento;


import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorAvaliacaoPraticaManager;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class ColaboradorAvaliacaoPraticaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager;
	private CertificacaoManager certificacaoManager;
	private ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica;
	private Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas;
	
	private Collection<Certificacao> certificacoes = new ArrayList<Certificacao>();
	private Collection<Colaborador>  colaboradores = new ArrayList<Colaborador>();
	
	private Certificacao certificacao;
	private Colaborador colaborador; 

	public void prepare() throws Exception
	{
		certificacoes = certificacaoManager.findAllSelect(getEmpresaSistema().getId());
		
		if(colaboradorAvaliacaoPratica != null && colaboradorAvaliacaoPratica.getId() != null)
			colaboradorAvaliacaoPratica = (ColaboradorAvaliacaoPratica) colaboradorAvaliacaoPraticaManager.findById(colaboradorAvaliacaoPratica.getId());

	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		colaboradorAvaliacaoPraticaManager.save(colaboradorAvaliacaoPratica);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		colaboradorAvaliacaoPraticaManager.update(colaboradorAvaliacaoPratica);
		return Action.SUCCESS;
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
	
}
