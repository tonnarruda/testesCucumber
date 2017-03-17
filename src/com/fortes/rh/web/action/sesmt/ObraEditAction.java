package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.sesmt.ObraManager;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.sesmt.Obra;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class ObraEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	@Autowired private ObraManager obraManager;
	@Autowired private EstadoManager estadoManager;
	@Autowired private CidadeManager cidadeManager;

	private Obra obra;
	
	private Collection<Obra> obras;
	private Collection<Cidade> cidades;
	private Collection<Estado> estados;
	
	private String nome;

	private void prepare() throws Exception
	{
		if (obra != null && obra.getId() != null)
			obra = (Obra) obraManager.findById(obra.getId());

		estados = estadoManager.findAll(new String[]{"sigla"});
		if (obra != null && obra.getEndereco() != null && obra.getEndereco().getUf() != null)
			cidades = cidadeManager.find(new String[]{"uf.id"}, new Object[]{obra.getEndereco().getUf().getId()}, new String[]{"nome"});
		else
			cidades = new ArrayList<Cidade>();
		
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
		try {
			obra.setEmpresa(getEmpresaSistema());
			obraManager.save(obra);
			
			addActionSuccess("Obra cadastrada com sucesso.");
			
		} catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível cadastrar a obra.");
			
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try {
			obraManager.update(obra);
			addActionSuccess("Obra atualizada com sucesso.");
			
		} catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível atualizar a obra.");
			
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		obras = obraManager.findAllSelect(nome, getEmpresaSistema().getId());
		System.out.println(getShowFilter());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			obraManager.remove(obra.getId());
			addActionSuccess("Obra excluída com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir esta obra.");
		}

		return list();
	}
	
	public Obra getObra()
	{
		if(obra == null)
			obra = new Obra();
		return obra;
	}

	public void setObra(Obra obra)
	{
		this.obra = obra;
	}
	
	public Collection<Obra> getObras()
	{
		return obras;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Collection<Cidade> getCidades() {
		return cidades;
	}

	public Collection<Estado> getEstados() {
		return estados;
	}
}