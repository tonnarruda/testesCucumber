package com.fortes.rh.web.action.geral;


import java.util.Collection;
import java.util.HashMap;

import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.model.dicionario.EnviarPara;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;
import com.fortes.rh.model.geral.GerenciadorComunicacao;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class GerenciadorComunicacaoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private GerenciadorComunicacao gerenciadorComunicacao;
	private Collection<GerenciadorComunicacao> gerenciadorComunicacaos;
	private HashMap<Integer, String> operacoes;
	private HashMap<Integer, String> meioComunicacoes;
	private HashMap<Integer, String> enviarParas;
	
	private void prepare() throws Exception
	{
		if(gerenciadorComunicacao != null && gerenciadorComunicacao.getId() != null)
			gerenciadorComunicacao = (GerenciadorComunicacao) gerenciadorComunicacaoManager.findById(gerenciadorComunicacao.getId());

	    operacoes = new Operacao();
		meioComunicacoes = new MeioComunicacao();
		enviarParas = new EnviarPara();
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
		gerenciadorComunicacao.setEmpresa(getEmpresaSistema());
		gerenciadorComunicacaoManager.save(gerenciadorComunicacao);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		gerenciadorComunicacao.setEmpresa(getEmpresaSistema());
		gerenciadorComunicacaoManager.update(gerenciadorComunicacao);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		gerenciadorComunicacaos = gerenciadorComunicacaoManager.find(new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()}, new String[]{});
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			gerenciadorComunicacaoManager.remove(gerenciadorComunicacao.getId());
			addActionMessage("Gerenciador de Comunicação excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir este Gerenciador de Comunicação.");
		}

		return list();
	}
	
	public GerenciadorComunicacao getGerenciadorComunicacao()
	{
		if(gerenciadorComunicacao == null)
			gerenciadorComunicacao = new GerenciadorComunicacao();
		return gerenciadorComunicacao;
	}

	public void setGerenciadorComunicacao(GerenciadorComunicacao gerenciadorComunicacao)
	{
		this.gerenciadorComunicacao = gerenciadorComunicacao;
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager)
	{
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}
	
	public Collection<GerenciadorComunicacao> getGerenciadorComunicacaos()
	{
		return gerenciadorComunicacaos;
	}

	public HashMap<Integer, String> getOperacoes() {
		return operacoes;
	}

	public void setOperacoes(HashMap<Integer, String> operacoes) {
		this.operacoes = operacoes;
	}

	public HashMap<Integer, String> getMeioComunicacoes() {
		return meioComunicacoes;
	}

	public void setMeioComunicacoes(HashMap<Integer, String> meioComunicacoes) {
		this.meioComunicacoes = meioComunicacoes;
	}

	public HashMap<Integer, String> getEnviarParas() {
		return enviarParas;
	}

	public void setEnviarParas(HashMap<Integer, String> enviarParas) {
		this.enviarParas = enviarParas;
	}
}
