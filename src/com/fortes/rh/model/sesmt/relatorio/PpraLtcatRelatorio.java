package com.fortes.rh.model.sesmt.relatorio;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.model.dicionario.GrupoRisco;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;

public class PpraLtcatRelatorio
{
	private Ppra ppra = null;
	private Ltcat ltcat = null;
	private PpraLtcatCabecalho cabecalho;

	public PpraLtcatRelatorio(PpraLtcatCabecalho cabecalho, Ppra ppra,
								Ltcat ltcat, boolean exibirPpra, boolean exibirLtcat) 
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
							funcao.getHistoricoAtual().getDescricao());
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
			StringBuilder riscoAtual = new StringBuilder();
			String quebraDeLinha="\n";
			
			riscoAtual.append(styleTag.concat(riscoMedicao.getRisco().getDescricao()))
					.append(": ".concat(styleTagClosing) + riscoMedicao.getIntensidadeMedida() + ". " + riscoMedicao.getDescricaoPpra());
			
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
			if (riscoMedicao.getRisco().getGrupoRisco().equals(GrupoRisco.OCUPACIONAL))
			{
				riscosOcupacional.append( riscosOcupacional.length()>0 ? quebraDeLinha : "" );
				riscosOcupacional.append(riscoAtual);
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
			StringBuilder riscoAtual = new StringBuilder();
			String quebraDeLinha="\n";
			
			riscoAtual.append(styleTag.concat(riscoMedicao.getRisco().getDescricao()))
					.append(": ".concat(styleTagClosing) + riscoMedicao.getIntensidadeMedida() + ". " + riscoMedicao.getDescricaoLtcat());
			
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
}
