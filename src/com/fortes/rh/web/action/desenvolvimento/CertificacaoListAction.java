package com.fortes.rh.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.relatorio.MatrizTreinamento;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class CertificacaoListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private CertificacaoManager certificacaoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private CursoManager cursoManager;

	private Collection<Certificacao> certificacaos;
	private Collection<MatrizTreinamento> matrizTreinamentos;
	private Collection<Curso> cursos;
	private Certificacao certificacao;

	private String[] cargosCheck;
	private String[] faixaSalarialsCheck;
	private Map<String, Object> parametros = new HashMap<String, Object>();

	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> faixaSalarialsCheckList = new ArrayList<CheckBox>();

	private String nomeBusca;

	public String list() throws Exception
	{
		setTotalSize(certificacaoManager.getCount(getEmpresaSistema().getId(), nomeBusca));
		certificacaos = certificacaoManager.findAllSelect(getPage(), getPagingSize(), getEmpresaSistema().getId(), nomeBusca);

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try {
			if (certificacao != null && certificacao.getId() != null && certificacaoManager.verificaEmpresa(certificacao.getId(), getEmpresaSistema().getId()))
			{
				certificacaoManager.remove(certificacao.getId());
				addActionSuccess("Certificação excluída com sucesso.");
			}
			else
				addActionWarning("A Certificação solicitada não existe na empresa " + getEmpresaSistema().getNome() +".");
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionUtil.traduzirMensagem(this, e, "Não foi possível excluir esta certificação.");
		}

		return Action.SUCCESS;
	}

	public String matrizTreinamento() throws Exception
	{
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		return Action.SUCCESS;
	}

	public String imprimirMatrizTreinamento() throws Exception
	{
		try
		{
			matrizTreinamentos = certificacaoManager.getByFaixasOrCargos(faixaSalarialsCheck, cargosCheck);
			if(matrizTreinamentos.isEmpty())
			{
				addActionMessage("Não existem dados para o filtro informado.");
				areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
				return Action.INPUT;
			}

			parametros = RelatorioUtil.getParametrosRelatorio("Matriz de Treinamento", getEmpresaSistema(), null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível gerar a matriz de treinamento.");
			areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}
	
	public String imprimir() throws Exception
	{
		cursos = cursoManager.findByCertificacao(certificacao.getId());
		certificacao = certificacaoManager.findById(certificacao.getId());
		
		parametros = RelatorioUtil.getParametrosRelatorio("Certificação", getEmpresaSistema(), certificacao.getNome());
		
		return Action.SUCCESS;
	}

	public Collection<Certificacao> getCertificacaos()
	{
		return certificacaos;
	}

	public Certificacao getCertificacao()
	{
		if (certificacao == null)
		{
			certificacao = new Certificacao();
		}
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

	public Collection<CheckBox> getAreasCheckList()
	{
		return areasCheckList;
	}

	public Collection<CheckBox> getCargosCheckList()
	{
		return cargosCheckList;
	}

	public Collection<CheckBox> getFaixaSalarialsCheckList()
	{
		return faixaSalarialsCheckList;
	}

	public void setCargosCheck(String[] cargosCheck)
	{
		this.cargosCheck = cargosCheck;
	}

	public void setFaixaSalarialsCheck(String[] faixaSalarialsCheck)
	{
		this.faixaSalarialsCheck = faixaSalarialsCheck;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public Collection<MatrizTreinamento> getMatrizTreinamentos()
	{
		return matrizTreinamentos;
	}

	public Collection<Curso> getCursos() {
		return cursos;
	}

	public void setCursoManager(CursoManager cursoManager) {
		this.cursoManager = cursoManager;
	}

	public String getNomeBusca() {
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}

}