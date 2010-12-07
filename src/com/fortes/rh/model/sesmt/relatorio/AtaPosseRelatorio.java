package com.fortes.rh.model.sesmt.relatorio;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.model.dicionario.FuncaoComissao;
import com.fortes.rh.model.sesmt.ComissaoMembro;

public class AtaPosseRelatorio
{
	private Collection<ComissaoMembro> comissaoMembros;
	private StringBuilder indicadosTitulares = new StringBuilder();
	private StringBuilder indicadosSuplentes = new StringBuilder();
	private StringBuilder eleitosTitulares = new StringBuilder();
	private StringBuilder eleitosSuplentes = new StringBuilder();
	
	private String texto1;
	private String texto2;
	
	
	public AtaPosseRelatorio(Collection<ComissaoMembro> comissaoMembros, String texto1, String texto2)
	{
		this.comissaoMembros = comissaoMembros;
		this.texto1 = texto1;
		this.texto2 = texto2;
	}
	
	public AtaPosseRelatorio() {
		// TODO Auto-generated constructor stub
	}

	//monta a parte 2 do texto com nomes do presidente e vice presidente da comissão 
	public void montarTexto()
	{
		String nomePresidente=""; 
		String nomeVicePresidente="";
		
		for (ComissaoMembro comissaoMembro : comissaoMembros)
		{
			if (StringUtils.isNotBlank(nomePresidente) && StringUtils.isNotBlank(nomeVicePresidente))
				break;
			
 			if (comissaoMembro.getFuncao().equals(FuncaoComissao.PRESIDENTE))
			{
				nomePresidente = comissaoMembro.getColaborador().getNome();
			}
 			else if (comissaoMembro.getFuncao().equals(FuncaoComissao.VICE_PRESIDENTE))
			{
				nomeVicePresidente = comissaoMembro.getColaborador().getNome();
			}
		}
		
		this.texto2 = this.texto2.replaceAll("#NOMEPRESIDENTE#", nomePresidente)
								.replaceAll("#NOMEVICEPRESIDENTE#", nomeVicePresidente);
	}

	
	public void montarMembrosIndicados(Collection<ComissaoMembro> membrosIndicados)
	{
		montarMembros(membrosIndicados, indicadosTitulares, indicadosSuplentes);
	}
	
	public void montarMembrosEleitos(Collection<ComissaoMembro> membrosEleitos)
	{
		montarMembros(membrosEleitos, eleitosTitulares, eleitosSuplentes);
	}
	
	public void montarMembros(Collection<ComissaoMembro> membros, StringBuilder titulares, StringBuilder suplentes)
	{
		if (membros != null)
		{
			for (ComissaoMembro comissaoMembro : membros)
			{
				String funcao = comissaoMembro.getFuncao();
				
//				if (funcao.equals(FuncaoComissao.PRESIDENTE) || funcao.equals(FuncaoComissao.VICE_PRESIDENTE))
//						continue;
				
				if (funcao.equals(FuncaoComissao.MEMBRO_SUPLENTE))
					suplentes.append(comissaoMembro.getColaborador().getNome()).append("\n");
				else
					titulares.append(comissaoMembro.getColaborador().getNome()).append("\n");
			}
		}
	}
	
	public Collection<ComissaoMembro> getComissaoMembros() {
		return comissaoMembros;
	}
	public String getIndicadosTitulares() {
		return indicadosTitulares.toString();
	}
	public String getIndicadosSuplentes() {
		return indicadosSuplentes.toString();
	}
	public String getEleitosTitulares() {
		return eleitosTitulares.toString();
	}
	public String getEleitosSuplentes() {
		return eleitosSuplentes.toString();
	}

	public String getTexto1() {
		return texto1;
	}

	public String getTexto2() {
		return texto2;
	}
}
