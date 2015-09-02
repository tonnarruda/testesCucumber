package com.fortes.rh.model.sesmt.relatorio;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.model.dicionario.GrupoRisco;
import com.fortes.rh.model.sesmt.ComposicaoSesmt;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;
import com.fortes.rh.util.StringUtil;

public class PpraLtcatRelatorio
{
	private Ppra ppra = null;
	private Ltcat ltcat = null;
	private PpraLtcatCabecalho cabecalho;

	private Collection<ComposicaoSesmt> composicaoSesmts;

	public PpraLtcatRelatorio(PpraLtcatCabecalho cabecalho, Ppra ppra, Ltcat ltcat, boolean exibirPpra, boolean exibirLtcat) 
	{
		this.cabecalho = cabecalho; 
		
		if (exibirPpra)
		{
			this.ppra = ppra;
		}
		if (exibirLtcat)
		{
			this.ltcat = ltcat;
		}
	}
	
	public PpraLtcatRelatorio() {
	}
	
	/** 
	 * Prepara os elementos a serem exibidos em Cabeçalho PPRA e LTCAT. 
	 **/
	
	public void formataFuncoes(Collection<Funcao> funcoesDoAmbiente) 
	{
		StringBuilder funcoes = new StringBuilder();
		
		for (Funcao funcao : funcoesDoAmbiente)
		{
			if (funcoes.length() > 0)
				funcoes.append("\n");
			
			funcoes.append("<style isBold=\"true\" pdfFontName=\"Helvetica-Bold\">- ".concat(funcao.getNome())  +":</style> " +
							StringUtil.replaceXml(funcao.getHistoricoAtual().getDescricao()));
		}
		
		cabecalho.setFuncoes(funcoes.toString());
	}
	
	public void formataRiscosPpra(Collection<RiscoMedicaoRisco> riscosDoAmbiente)
	{
		
		if (ppra == null) 
			return;
		
		StringBuilder riscosFisicos = new StringBuilder();
		StringBuilder riscosQuimicos = new StringBuilder();
		StringBuilder riscosBiologicos = new StringBuilder();
		StringBuilder riscosErgonomicos = new StringBuilder();
		StringBuilder riscosAcidentes = new StringBuilder();
		StringBuilder riscosOcupacional = new StringBuilder();
		
		String styleTag = "<style isBold=\"true\" pdfFontName=\"Helvetica-Bold\">";
		String styleTagClosing = "</style>";
		
		for (RiscoMedicaoRisco riscoMedicao : riscosDoAmbiente)
		{
			StringBuilder riscoMedido = new StringBuilder();
			String quebraDeLinha="\n";
			
			riscoMedido.append(riscoMedicao.getPeriodicidadeExposicao() != null ? styleTag.concat("Periodicidade da exposição: ").concat(styleTagClosing).concat( riscoMedicao.getPeriodicidadeExposicao().toString() ).concat(". ") : "");
			riscoMedido.append(!StringUtil.isBlank(riscoMedicao.getIntensidadeMedida()) ? styleTag.concat("Intensidade/Concentração: ").concat(styleTagClosing).concat( StringUtil.replaceXml(riscoMedicao.getIntensidadeMedida()) ).concat(". ") : "");
			riscoMedido.append(!StringUtil.isBlank(riscoMedicao.getTecnicaUtilizada()) ? styleTag.concat("Técnica utilizada: ").concat(styleTagClosing).concat( StringUtil.replaceXml(riscoMedicao.getTecnicaUtilizada()) ).concat(". ") : "");
			riscoMedido.append(!StringUtil.isBlank(riscoMedicao.getDescricaoPpra()) ? styleTag.concat("Descrição: ").concat(styleTagClosing).concat( StringUtil.replaceXml(riscoMedicao.getDescricaoPpra()) ).concat(". ") : "");
			riscoMedido.append(!StringUtil.isBlank(riscoMedicao.getMedidaDeSeguranca()) ? styleTag.concat("Medida de Segurança: ").concat(styleTagClosing).concat( StringUtil.replaceXml(riscoMedicao.getMedidaDeSeguranca()) ).concat(". ") : "");
			
			if (StringUtil.isBlank(riscoMedido.toString()))
				riscoMedido.append("(não informado)");
			
			String riscoAtual = styleTag.concat(riscoMedicao.getRisco().getDescricao()).concat(" - ").concat(styleTagClosing) + riscoMedido.toString();
			
			if (riscoMedicao.getRisco().getGrupoRisco().equals(GrupoRisco.FISICO))
			{
				riscosFisicos.append( riscosFisicos.length()>0 ? quebraDeLinha : "" );
				riscosFisicos.append(riscoAtual);
			}
			if (riscoMedicao.getRisco().getGrupoRisco().equals(GrupoRisco.QUIMICO))
			{
				riscosQuimicos.append( riscosQuimicos.length()>0 ? quebraDeLinha : "" );
				riscosQuimicos.append(riscoAtual);
			}
			if (riscoMedicao.getRisco().getGrupoRisco().equals(GrupoRisco.BIOLOGICO))
			{
				riscosBiologicos.append( riscosBiologicos.length()>0 ? quebraDeLinha : "" );
				riscosBiologicos.append(riscoAtual);
			}
			if (riscoMedicao.getRisco().getGrupoRisco().equals(GrupoRisco.ERGONOMICO))
			{
				riscosErgonomicos.append( riscosErgonomicos.length()>0 ? quebraDeLinha : "" );
				riscosErgonomicos.append(riscoAtual);
			}
			if (riscoMedicao.getRisco().getGrupoRisco().equals(GrupoRisco.ACIDENTE))
			{
				riscosAcidentes.append( riscosAcidentes.length()>0 ? quebraDeLinha : "" );
				riscosAcidentes.append(riscoAtual);
			}
		}
		
		ppra.setRiscosFisicos(riscosFisicos.toString());
		ppra.setRiscosAcidentes(riscosAcidentes.toString());
		ppra.setRiscosBiologicos(riscosBiologicos.toString());
		ppra.setRiscosErgonomicos(riscosErgonomicos.toString());
		ppra.setRiscosQuimicos(riscosQuimicos.toString());
		ppra.setRiscosOcupacionais(riscosOcupacional.toString());
	}
	
	public void formataEpcs(Collection<Epc> epcsDoAmbiente) 
	{
		if (ppra == null) 
			return;
		
		StringBuilder epcs = new StringBuilder();
		
		for (Epc epc : epcsDoAmbiente)
		{
			if (epcs.length() > 0)
				epcs.append("  -  ");
			
			epcs.append(epc.getDescricao());
		}
		
		ppra.setEpcs(epcs.toString());
	}
	
	public void formataEpis(Collection<Epi> episDoAmbiente) 
	{
		if (ppra == null) 
			return;
		
		StringBuilder epis = new StringBuilder();
		
		for (Epi epi : episDoAmbiente)
		{
			if (epis.length() > 0)
				epis.append("  -  ");
			
			epis.append(epi.getNome());
			
			if (epi.getEpiHistorico() != null && StringUtils.isNotBlank(epi.getEpiHistorico().getCA()))
				epis.append(" (CA ").append(epi.getEpiHistorico().getCA()).append(")");
		}
		
		ppra.setEpis(epis.toString());
	}
	
	public void formataRiscosLtcat(Collection<RiscoMedicaoRisco> riscosDoAmbiente)
	{
		if (ltcat == null) 
			return;
		
		StringBuilder riscosFisicos = new StringBuilder();
		StringBuilder riscosQuimicos = new StringBuilder();
		StringBuilder riscosBiologicos = new StringBuilder();
		StringBuilder riscosErgonomicos = new StringBuilder();
		StringBuilder riscosAcidentes = new StringBuilder();
		
		String styleTag = "<style isBold=\"true\" pdfFontName=\"Helvetica-Bold\">";
		String styleTagClosing = "</style>";
		
		for (RiscoMedicaoRisco riscoMedicao : riscosDoAmbiente)
		{
			StringBuilder riscoMedido = new StringBuilder();
			String quebraDeLinha="\n";
			
			riscoMedido.append(riscoMedicao.getPeriodicidadeExposicao() != null ? styleTag.concat("Periodicidade da exposição: ").concat(styleTagClosing).concat( riscoMedicao.getPeriodicidadeExposicao().toString() ).concat(". ") : "");
			riscoMedido.append(!StringUtil.isBlank(riscoMedicao.getIntensidadeMedida()) ? styleTag.concat("Intensidade/Concentração: ").concat(styleTagClosing).concat( StringUtil.replaceXml(riscoMedicao.getIntensidadeMedida()) ).concat(". ") : "");
			riscoMedido.append(!StringUtil.isBlank(riscoMedicao.getTecnicaUtilizada()) ? styleTag.concat("Técnica utilizada: ").concat(styleTagClosing).concat( StringUtil.replaceXml(riscoMedicao.getTecnicaUtilizada()) ).concat(". ") : "");
			riscoMedido.append(!StringUtil.isBlank(riscoMedicao.getDescricaoLtcat()) ? styleTag.concat("Descrição: ").concat(styleTagClosing).concat( StringUtil.replaceXml(riscoMedicao.getDescricaoLtcat()) ).concat(". ") : "");
			riscoMedido.append(!StringUtil.isBlank(riscoMedicao.getMedidaDeSeguranca()) ? styleTag.concat("Medida de Segurança: ").concat(styleTagClosing).concat( StringUtil.replaceXml(riscoMedicao.getMedidaDeSeguranca()) ).concat(". ") : "");
			
			if (StringUtil.isBlank(riscoMedido.toString()))
				riscoMedido.append("(não informado)");
			
			String riscoAtual = styleTag.concat(riscoMedicao.getRisco().getDescricao()).concat(" - ").concat(styleTagClosing) + riscoMedido.toString();
			
			if (riscoMedicao.getRisco().getGrupoRisco().equals(GrupoRisco.FISICO))
			{
				riscosFisicos.append( riscosFisicos.length()>0 ? quebraDeLinha : "" );
				riscosFisicos.append(riscoAtual);
			}
			if (riscoMedicao.getRisco().getGrupoRisco().equals(GrupoRisco.QUIMICO))
			{
				riscosQuimicos.append( riscosQuimicos.length()>0 ? quebraDeLinha : "" );
				riscosQuimicos.append(riscoAtual);
			}
			if (riscoMedicao.getRisco().getGrupoRisco().equals(GrupoRisco.BIOLOGICO))
			{
				riscosBiologicos.append( riscosBiologicos.length()>0 ? quebraDeLinha : "" );
				riscosBiologicos.append(riscoAtual);
			}
			if (riscoMedicao.getRisco().getGrupoRisco().equals(GrupoRisco.ERGONOMICO))
			{
				riscosErgonomicos.append( riscosErgonomicos.length()>0 ? quebraDeLinha : "" );
				riscosErgonomicos.append(riscoAtual);
			}
			if (riscoMedicao.getRisco().getGrupoRisco().equals(GrupoRisco.ACIDENTE))
			{
				riscosAcidentes.append( riscosAcidentes.length()>0 ? quebraDeLinha : "" );
				riscosAcidentes.append(riscoAtual);
			}
		}
		
		ltcat.setRiscosFisicos(riscosFisicos.toString());
		ltcat.setRiscosAcidentes(riscosAcidentes.toString());
		ltcat.setRiscosBiologicos(riscosBiologicos.toString());
		ltcat.setRiscosErgonomicos(riscosErgonomicos.toString());
		ltcat.setRiscosQuimicos(riscosQuimicos.toString());
	}

	public Ppra getPpra() {
		return ppra;
	}
	public void setPpra(Ppra ppra) {
		this.ppra = ppra;
	}
	public Ltcat getLtcat() {
		return ltcat;
	}
	public void setLtcat(Ltcat ltcat) {
		this.ltcat = ltcat;
	}
	public PpraLtcatCabecalho getCabecalho() {
		return cabecalho;
	}
	public void setCabecalho(PpraLtcatCabecalho cabecalho) {
		this.cabecalho = cabecalho;
	}

	public void setTempoExposicao(String tempoExposicao) 
	{
		if (ppra != null)
			ppra.setTempoExposicao(tempoExposicao);
	}

	public Collection<ComposicaoSesmt> getComposicaoSesmts() {
		return composicaoSesmts;
	}

	public void setComposicaoSesmts(Collection<ComposicaoSesmt> composicaoSesmts) {
		this.composicaoSesmts = composicaoSesmts;
	}
}
