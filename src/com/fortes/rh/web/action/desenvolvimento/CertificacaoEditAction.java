package com.fortes.rh.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.dicionario.FiltroControleVencimentoCertificacao;
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
	private AvaliacaoPraticaManager avaliacaoPraticaManager;
	private Collection<Certificacao> certificacoes = new ArrayList<Certificacao>();

	private Certificacao certificacao;
	
	private String[] cursosCheck;
	private Collection<CheckBox> cursosCheckList = new ArrayList<CheckBox>();
	
	private String[] avaliacoesPraticasCheck;
	private Collection<CheckBox> avaliacoesPraticasCheckList = new ArrayList<CheckBox>();
		
	private String nomeBusca;//filtro listagem

	private boolean exibirPeriodicidade;

	private void prepare() throws Exception
	{
		if (certificacao != null && certificacao.getId() != null)
			certificacao = (Certificacao) certificacaoManager.findById(certificacao.getId());
		
		Collection<Curso> cursos = cursoManager.findAllByEmpresasParticipantes(getEmpresaSistema().getId());
		cursosCheckList = CheckListBoxUtil.populaCheckListBox(cursos, "getId", "getNome");
		
		Collection<AvaliacaoPratica> avaliacaoPraticas = avaliacaoPraticaManager.find(new String[] {"empresa.id"}, new Object[] { getEmpresaSistema().getId() }, new String[] { "titulo" });
		avaliacoesPraticasCheckList = CheckListBoxUtil.populaCheckListBox(avaliacaoPraticas, "getId", "getTitulo");
		
		setExibirPeriodicidade(getEmpresaSistema().getControlarVencimentoCertificacaoPor() == FiltroControleVencimentoCertificacao.CERTIFICACAO.getOpcao());
		
		certificacoes = certificacaoManager.findAllSelectNotCertificacaoId(getEmpresaSistema().getId(), certificacao.getId());
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
			avaliacoesPraticasCheckList = CheckListBoxUtil.marcaCheckListBox(avaliacoesPraticasCheckList, certificacao.getAvaliacoesPraticas(), "getId");
			
		}
		else
			addActionError("A Certificação solicitada não existe na empresa " + getEmpresaSistema().getNome() +".");
		
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		populaCertificacao();
		certificacaoManager.save(certificacao);
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		populaCertificacao();
		certificacaoManager.update(certificacao);
		
		return Action.SUCCESS;
	}

	private void populaCertificacao() throws Exception 
	{
		CollectionUtil<Curso> util = new CollectionUtil<Curso>();
		certificacao.setCursos(util.convertArrayStringToCollection(Curso.class, cursosCheck));
		
		CollectionUtil<AvaliacaoPratica> utilAvaliacoesPraticas = new CollectionUtil<AvaliacaoPratica>();
		certificacao.setAvaliacoesPraticas(utilAvaliacoesPraticas.convertArrayStringToCollection(AvaliacaoPratica.class, avaliacoesPraticasCheck));
		
		if(!(certificacao.getCertificacaoPreRequisito() != null && certificacao.getCertificacaoPreRequisito().getId() != null))
			certificacao.setCertificacaoPreRequisito(null);
		
		certificacao.setEmpresa(getEmpresaSistema());
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

	public Collection<CheckBox> getAvaliacoesPraticasCheckList() {
		return avaliacoesPraticasCheckList;
	}

	public void setAvaliacaoPraticaManager(
			AvaliacaoPraticaManager avaliacaoPraticaManager) {
		this.avaliacaoPraticaManager = avaliacaoPraticaManager;
	}

	public void setAvaliacoesPraticasCheck(String[] avaliacoesPraticasCheck) {
		this.avaliacoesPraticasCheck = avaliacoesPraticasCheck;
	}

	public boolean isExibirPeriodicidade() {
		return exibirPeriodicidade;
	}

	public void setExibirPeriodicidade(boolean exibirPeriodicidade) {
		this.exibirPeriodicidade = exibirPeriodicidade;
	}

	public Collection<Certificacao> getCertificacoes() {
		return certificacoes;
	}
}