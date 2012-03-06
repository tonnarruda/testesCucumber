package com.fortes.rh.web.action.geral;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
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
	private HashMap<Integer, String> meioComunicacoes;
	private HashMap<Integer, String> enviarParas;
	
	private void prepare() throws Exception
	{
		if(gerenciadorComunicacao != null )
		{
			if (gerenciadorComunicacao.getId() != null)
				gerenciadorComunicacao = (GerenciadorComunicacao) gerenciadorComunicacaoManager.findById(gerenciadorComunicacao.getId());
			
			meioComunicacoes = Operacao.getMeioComunicacaosById(gerenciadorComunicacao.getOperacao());
			enviarParas = (MeioComunicacao.getMeioComunicacaoById(gerenciadorComunicacao.getMeioComunicacao())).getListEnviarPara();   
		}else
		{
			meioComunicacoes = new HashMap<Integer, String>();
			enviarParas = new HashMap<Integer, String>();
		}
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
		
		if (gerenciadorComunicacaoManager.verifyExists(gerenciadorComunicacao)){
			addActionMessage("Já existe uma configuração cadastrada com os dados informados.");
			prepareInsert();
			return Action.INPUT;
		}
		
		gerenciadorComunicacaoManager.save(gerenciadorComunicacao);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		if (gerenciadorComunicacaoManager.verifyExists(gerenciadorComunicacao)){
			addActionMessage("Já existe uma configuração cadastrada com os dados informados.");
			prepareUpdate();
			return Action.INPUT;
		}

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
			addActionMessage("Configuração excluída com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir esta configuração.");
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

	public Map<String, Collection<Operacao>> getOperacoes()
	{
		return Operacao.getHashMapGrupos();
	}

	public HashMap<Integer, String> getEnviarParas() {
		return enviarParas;
	}

	public HashMap<Integer, String> getMeioComunicacoes() {
		return meioComunicacoes;
	}
}
