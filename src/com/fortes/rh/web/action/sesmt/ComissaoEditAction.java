package com.fortes.rh.web.action.sesmt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.ComissaoManager;
import com.fortes.rh.business.sesmt.EleicaoManager;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.model.sesmt.relatorio.AtaPosseRelatorio;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;

public class ComissaoEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;

	@Autowired private ComissaoManager comissaoManager;
	@Autowired private EleicaoManager eleicaoManager;

	private Comissao comissao;
	private Collection<Comissao> comissaos;

	Collection<ComissaoPeriodo> comissaoPeriodos;

	private Collection<Eleicao> eleicaos;
	private Eleicao eleicao;
	
	private Map<String,Object> parametros = new HashMap<String, Object>();
	private Collection<ComissaoMembro> dataSource;
	private AtaPosseRelatorio ataPosseRelatorio;

	public String prepare() throws Exception
	{
		if(comissao != null && comissao.getId() != null)
		{
			comissao = (Comissao) comissaoManager.findByIdProjection(comissao.getId());
			eleicao = comissao.getEleicao();
		}

		this.eleicaos = eleicaoManager.findAllSelect(getEmpresaSistema().getId());

		return SUCCESS;
	}

	public String insert() throws Exception
	{
		comissao.setEleicao(eleicao);
		comissaoManager.save(comissao);
		return SUCCESS;
	}

	public String update() throws Exception
	{
		comissao.setEleicao(eleicao);
		comissaoManager.update(comissao);
		return list();
	}

	/**
	 * Listagem de Comissões ("aba" Geral).
	 */
	public String list() throws Exception
	{
		comissaos = comissaoManager.findAllSelect(getEmpresaSistema().getId());
		return SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			comissaoManager.remove(comissao.getId());
		}
		catch (PersistenceException e)
		{
			addActionError(e.getMessage());
			list();
		}

		return SUCCESS;
	}
	
	public String prepareDocumentos() throws Exception
	{
		prepareTextoParaAtaPosse();
		return SUCCESS;
	}
	
	private void prepareTextoParaAtaPosse()
	{
		if (StringUtils.isBlank(getComissao().getAtaPosseTexto1()))
		{
			StringBuilder ataPosseTexto1 = new StringBuilder();
			ataPosseTexto1.append("        Aos ..........  dias do mês de .........  do ano de dois mil e ................, ");
			ataPosseTexto1.append("na empresa ...................... nesta cidade, presente(s) o(s) Senhor(es) Diretor(es) ");
			ataPosseTexto1.append("da Empresa, bem como os demais presentes, conforme a lista de Presença, reuniram-se para ");
			ataPosseTexto1.append("Instalação e Posse da CIPA desta Empresa, conforme o estabelecimento pela Portaria n.º ");
			ataPosseTexto1.append("................................... o Senhor ..............................., representante da Empresa e Presidente da sessão, tendo convidado a mim, ");
			ataPosseTexto1.append("......................................, para Secretário da CIPA, declarou abertos os trabalhos, lembrando a todos os objetivos da Reunião, quais sejam: Instalação e Posse dos ");
			ataPosseTexto1.append("componentes da CIPA.");
			
			getComissao().setAtaPosseTexto1(ataPosseTexto1.toString());
		}
		
		if (StringUtils.isBlank(getComissao().getAtaPosseTexto2()))
		{
			StringBuilder ataPosseTexto2 = new StringBuilder();
			ataPosseTexto2.append("        A seguir, foi designado para Presidente da CIPA o Senhor #NOMEPRESIDENTE# e para Vice-Presidente o Senhor #NOMEVICEPRESIDENTE#. Os Representantes do empregador e ");
			ataPosseTexto2.append("dos Empregados, em comum acordo, escolheram também o(a) Senhor(a) para secretário(a) ..................................., sendo seu(sua) substituto(a) o(a) Senhor(a) ......................................");
			ataPosseTexto2.append("\n        Nada mais havendo para tratar, o Senhor Presidente da sessão deu por encerrada a Reunião, lembrando a todos que o período de gestão da CIPA ora instalada será de 01 (um) ano a contar da presente data. ");
			ataPosseTexto2.append("\n        Para constar, lavrou-se a presente Ata, que, lida e aprovada, vai assinada por mim, Secretário, pelo Presidente da Sessão, por todos os Representantes eleitos e/ou designados inclusive os Suplentes.");
			
			getComissao().setAtaPosseTexto2(ataPosseTexto2.toString());
		}
	}
	
	public String imprimirAtaPosse()
	{
		prepareImpressaoComunicado("Ata de Instalação e Posse da Comissão Interna de Prevenção de Acidentes");
		ataPosseRelatorio = comissaoManager.montaAtaPosse(comissao);
		
		dataSource = ataPosseRelatorio.getComissaoMembros();
		
		String logoEmpresa = ArquivoUtil.getPathLogoEmpresa() + getEmpresaSistema().getLogoUrl();
		parametros.put("LOGO", logoEmpresa);
		parametros.put("TEXTO1", ataPosseRelatorio.getTexto1());
		parametros.put("TEXTO2", ataPosseRelatorio.getTexto2());
		parametros.put("INDICADOS_TITULARES", ataPosseRelatorio.getIndicadosTitulares());
		parametros.put("INDICADOS_SUPLENTES", ataPosseRelatorio.getIndicadosSuplentes());
		parametros.put("ELEITOS_TITULARES", ataPosseRelatorio.getEleitosTitulares());
		parametros.put("ELEITOS_SUPLENTES", ataPosseRelatorio.getEleitosSuplentes());
		
		return SUCCESS;
	}
	
	private void prepareImpressaoComunicado(String titulo)
	{
		comissaoManager.updateTextosComunicados(comissao);
		parametros = RelatorioUtil.getParametrosRelatorio(titulo, getEmpresaSistema(), "");
	}
	
	public Comissao getComissao()
	{
		if(comissao == null)
			comissao = new Comissao();
		return comissao;
	}

	public void setComissao(Comissao comissao)
	{
		this.comissao = comissao;
	}

	public Collection<Eleicao> getEleicaos()
	{
		return eleicaos;
	}

	public Eleicao getEleicao()
	{
		return eleicao;
	}

	public void setEleicao(Eleicao eleicao)
	{
		this.eleicao = eleicao;
	}

	public Collection<Comissao> getComissaos()
	{
		return comissaos;
	}

	public Collection<ComissaoMembro> getDataSource() {
		return dataSource;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}
}