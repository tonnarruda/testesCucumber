package com.fortes.rh.web.action.sesmt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.ComissaoManager;
import com.fortes.rh.business.sesmt.ComissaoMembroManager;
import com.fortes.rh.business.sesmt.ComissaoReuniaoManager;
import com.fortes.rh.business.sesmt.ComissaoReuniaoPresencaManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoReuniao;
import com.fortes.rh.model.sesmt.ComissaoReuniaoPresenca;
import com.fortes.rh.model.sesmt.relatorio.ComissaoReuniaoPresencaMatriz;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;

public class ComissaoReuniaoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	@Autowired private ComissaoReuniaoManager comissaoReuniaoManager;
	@Autowired private ComissaoReuniaoPresencaManager comissaoReuniaoPresencaManager;
	@Autowired private ComissaoManager comissaoManager;
	@Autowired private ComissaoMembroManager comissaoMembroManager;

	private ComissaoReuniao comissaoReuniao;
	private Collection<ComissaoReuniao> comissaoReuniaos;
	private Collection<ComissaoReuniaoPresenca> presencas;
	private Collection<Colaborador> colaboradors;
	Collection<ComissaoMembro> comissaoMembros;

	CollectionUtil<ComissaoReuniao> collectionUtil = new CollectionUtil<ComissaoReuniao>();

	private Collection<Comissao> comissaos;
	private Comissao comissao;

	private Map<String, Object> parametros;
	private String[] colaboradorChecks;
	private String[] colaboradorIds;
	private String[] justificativas;

	Collection<ComissaoReuniaoPresencaMatriz> comissaoReuniaoPresencaMatrizes;

	public String prepare() throws Exception
	{
		if(comissaoReuniao != null && comissaoReuniao.getId() != null)
			comissaoReuniao = (ComissaoReuniao) comissaoReuniaoManager.findByIdProjection(comissaoReuniao.getId());

		return SUCCESS;
	}

	public String insert() throws Exception
	{
		comissaoReuniao.setComissao(comissao);
		comissaoReuniaoManager.saveOrUpdate(comissaoReuniao, colaboradorChecks, colaboradorIds, justificativas);
		return SUCCESS;
	}
	
	public String sugerirReuniao() throws Exception
	{
		Comissao comissaotemp = comissaoManager.findByIdProjection(comissao.getId());
		comissaoReuniaoManager.sugerirReuniao(comissaotemp);
		return SUCCESS;
	}

	public String update() throws Exception
	{
		comissaoReuniao.setComissao(comissao);
		comissaoReuniaoManager.saveOrUpdate(comissaoReuniao, colaboradorChecks, colaboradorIds, justificativas);
		return SUCCESS;
	}

	public String list() throws Exception
	{
		comissaoReuniaos = comissaoReuniaoManager.findByComissao(comissao.getId());
		// Presenças
		colaboradors = comissaoMembroManager.findColaboradorByComissao(comissao.getId());

		return SUCCESS;
	}

	public String delete() throws Exception
	{
		comissaoReuniaoManager.remove(comissaoReuniao.getId());
		return SUCCESS;
	}

	public String imprimirCalendario() throws Exception
	{
		try
		{
			comissaoReuniaos = comissaoReuniaoManager.findImprimirCalendario(comissao.getId());
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			list();
			return INPUT;
		}

		parametros = new HashMap<String, Object>();
		String logoEmpresa = ArquivoUtil.getPathLogoEmpresa() + getEmpresaSistema().getLogoUrl();
		parametros.put("LOGO", logoEmpresa);

		return SUCCESS;
	}

	public String imprimirFrequencia() throws Exception
	{
		presencas = comissaoReuniaoPresencaManager.findByComissao(comissao.getId(), true);
		comissao = comissaoManager.findByIdProjection(comissao.getId());
		
		parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Frequência nas Reuniões da CIPA", getEmpresaSistema(), "Eleição: " + comissao.getEleicao().getDescricaoFormatada());
		
		return SUCCESS;
	}

	public String imprimirAta() throws Exception
	{
		comissaoMembros = comissaoReuniaoManager.findImprimirAta(comissaoReuniao, comissao.getId());
		parametros = new HashMap<String, Object>();
		String logoEmpresa = ArquivoUtil.getPathLogoEmpresa() + getEmpresaSistema().getLogoUrl();

		// complementa o título se houver descrição
		String reuniaoDescricao = comissaoReuniao.getDescricao() == null ? "" : " - ".concat(comissaoReuniao.getDescricao()); 
		
		parametros.put("LOGO", logoEmpresa);
		parametros.put("REUNIAO_TIPO", comissaoReuniao.getTipoDic().toUpperCase());
		parametros.put("REUNIAO_DESCRICAO", reuniaoDescricao);
		parametros.put("REUNIAO_ATA", comissaoReuniao.getAta());
		parametros.put("REUNIAO_OBS_ANTERIOR", comissaoReuniao.getObsReuniaoAnterior());

		parametros.put("REUNIAO_TEXTO_FORMATADO", formatarTextoData(comissaoReuniao));

		return SUCCESS;
	}

	private String formatarTextoData(ComissaoReuniao comissaoReuniao)
	{
		String textoFormatado = DateUtil.formataDataExtensoPadraoDeAta(comissaoReuniao.getData(), comissaoReuniao.getHorario());

		if (StringUtils.isNotBlank(comissaoReuniao.getLocalizacao()))
			textoFormatado += ", no local " + comissaoReuniao.getLocalizacao() + ",";
		else
			textoFormatado += ",";

		textoFormatado += " foram discutidos os seguintes assuntos:";

		return textoFormatado;
	}

	public String relatorioPresenca()
	{
		try {
			presencas = comissaoReuniaoManager.findRelatorioPresenca(comissao.getId());
			comissaoReuniaos = comissaoReuniaoManager.findByComissao(comissao.getId());
			comissaoReuniaos = collectionUtil.sortCollectionDate(comissaoReuniaos, "data", "asc");
		
		} catch (FortesException e) 
		{
			addActionMessage(e.getMessage());
		}
		
		return SUCCESS;
	}

	public ComissaoReuniao getComissaoReuniao()
	{
		if(comissaoReuniao == null)
			comissaoReuniao = new ComissaoReuniao();
		return comissaoReuniao;
	}

	public void setComissaoReuniao(ComissaoReuniao comissaoReuniao)
	{
		this.comissaoReuniao = comissaoReuniao;
	}

	public Collection<Comissao> getComissaos()
	{
		return comissaos;
	}

	public Collection<ComissaoReuniao> getComissaoReuniaos()
	{
		return comissaoReuniaos;
	}

	public Comissao getComissao()
	{
		return comissao;
	}

	public void setComissao(Comissao comissao)
	{
		this.comissao = comissao;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public Collection<ComissaoReuniaoPresenca> getPresencas()
	{
		return presencas;
	}

	public Collection<Colaborador> getColaboradors()
	{
		return colaboradors;
	}

	public String[] getColaboradorChecks()
	{
		return colaboradorChecks;
	}

	public String[] getColaboradorIds()
	{
		return colaboradorIds;
	}

	public void setColaboradorIds(String[] colaboradorIds)
	{
		this.colaboradorIds = colaboradorIds;
	}

	public String[] getJustificativas()
	{
		return justificativas;
	}

	public void setJustificativas(String[] justificativas)
	{
		this.justificativas = justificativas;
	}

	public void setColaboradorChecks(String[] colaboradorChecks)
	{
		this.colaboradorChecks = colaboradorChecks;
	}

	public Collection<ComissaoMembro> getComissaoMembros()
	{
		return comissaoMembros;
	}

	public Collection<ComissaoReuniaoPresencaMatriz> getComissaoReuniaoPresencaMatrizes()
	{
		return comissaoReuniaoPresencaMatrizes;
	}
}