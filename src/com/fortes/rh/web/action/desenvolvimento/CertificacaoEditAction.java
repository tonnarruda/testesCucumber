package com.fortes.rh.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

public class CertificacaoEditAction extends MyActionSupportEdit implements ModelDriven
{
	private static final long serialVersionUID = 1L;
	
	private CertificacaoManager certificacaoManager;
	private CursoManager cursoManager;

	private Certificacao certificacao;
	
	private Collection<Curso> cursos;
	private String[] cursosCheck;
	private Collection<CheckBox> cursosCheckList = new ArrayList<CheckBox>();
	
	private String nomeBusca;//filtro listagem

	private void prepare() throws Exception
	{
		if (certificacao != null && certificacao.getId() != null)
			certificacao = (Certificacao) certificacaoManager.findById(certificacao.getId());
		
		cursos = cursoManager.findAllSelect(getEmpresaSistema().getId());
		cursosCheckList = CheckListBoxUtil.populaCheckListBox(cursos, "getId", "getNome");
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		if (certificacao != null && certificacao.getId() != null && certificacaoManager.verificaEmpresa(certificacao.getId(), getEmpresaSistema().getId()))
		{
			prepare();
			cursosCheckList = CheckListBoxUtil.marcaCheckListBox(cursosCheckList, certificacao.getCursos(), "getId");
		}
		else
			addActionError("A Certificação solicitada não existe na empresa " + getEmpresaSistema().getNome() +".");
		
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		CollectionUtil<Curso> util = new CollectionUtil<Curso>();
		certificacao.setCursos(util.convertArrayStringToCollection(Curso.class, cursosCheck));

		certificacao.setEmpresa(getEmpresaSistema());
		certificacaoManager.save(certificacao);
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		CollectionUtil<Curso> util = new CollectionUtil<Curso>();
		certificacao.setCursos(util.convertArrayStringToCollection(Curso.class, cursosCheck));
		
		certificacao.setEmpresa(getEmpresaSistema());
		certificacaoManager.update(certificacao);
		
		return Action.SUCCESS;
	}

	public Object getModel()
	{
		return getCertificacao();
	}

	public Certificacao getCertificacao()
	{
		if (certificacao == null)
			certificacao = new Certificacao();
		return certificacao;
	}

	public void setCertificacao(Certificacao certificacao)
	{
		this.certificacao = certificacao;
	}

	public void setCertificacaoManager(CertificacaoManager certificacaoManager)
	{
		this.certificacaoManager = certificacaoManager;
	}

	public Collection<CheckBox> getCursoCheckList()
	{
		return cursosCheckList;
	}

	public Collection<Curso> getCursos()
	{
		return cursos;
	}

	public void setCursoManager(CursoManager cursoManager)
	{
		this.cursoManager = cursoManager;
	}

	public Collection<CheckBox> getCursosCheckList()
	{
		return cursosCheckList;
	}

	public void setCursosCheck(String[] cursosCheck)
	{
		this.cursosCheck = cursosCheck;
	}

	public String getNomeBusca() {
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}
}