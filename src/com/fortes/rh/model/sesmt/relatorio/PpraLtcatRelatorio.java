package com.fortes.rh.model.sesmt.relatorio;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
	
	private static final String RISCOFISICO = "riscosFisicos";
	private static final String RISCOQUIMICOS = "riscosQuimicos";
	private static final String RISCOBIOLOGICOS = "riscosBiologicos";
	private static final String RISCOERGONOMICOS = "riscosErgonomicos";
	private static final String RISCOACIDENTES = "riscosAcidentes";

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
	
	public void formataRiscosLtcat(Collection<RiscoMedicaoRisco> riscosDoAmbiente)
	{
		if (ltcat == null) 
			return;
		
		Map<String, StringBuilder> riscosMap = new HashMap<String, StringBuilder>();
		montaRiscos(riscosDoAmbiente, riscosMap, false);
		
		ltcat.setRiscosFisicos(riscosMap.get(RISCOFISICO).toString());
		ltcat.setRiscosAcidentes(riscosMap.get(RISCOACIDENTES).toString());
		ltcat.setRiscosBiologicos(riscosMap.get(RISCOBIOLOGICOS).toString());
		ltcat.setRiscosErgonomicos(riscosMap.get(RISCOERGONOMICOS).toString());
		ltcat.setRiscosQuimicos(riscosMap.get(RISCOQUIMICOS).toString());
	}
	
	public void formataRiscosPpra(Collection<RiscoMedicaoRisco> riscosDoAmbiente)
	{
		if (ppra == null) 
			return;
		
		Map<String, StringBuilder> riscosMap = new HashMap<String, StringBuilder>();
		montaRiscos(riscosDoAmbiente, riscosMap, true);
		
		ppra.setRiscosFisicos(riscosMap.get(RISCOFISICO).toString());
		ppra.setRiscosAcidentes(riscosMap.get(RISCOACIDENTES).toString());
		ppra.setRiscosBiologicos(riscosMap.get(RISCOBIOLOGICOS).toString());
		ppra.setRiscosErgonomicos(riscosMap.get(RISCOERGONOMICOS).toString());
		ppra.setRiscosQuimicos(riscosMap.get(RISCOQUIMICOS).toString());
	}

	private void montaRiscos(Collection<RiscoMedicaoRisco> riscosDoAmbiente, Map<String, StringBuilder> riscosMap, boolean isPpra) {
		riscosMap.put(RISCOFISICO, new StringBuilder(""));
		riscosMap.put(RISCOQUIMICOS, new StringBuilder(""));
		riscosMap.put(RISCOBIOLOGICOS, new StringBuilder(""));
		riscosMap.put(RISCOERGONOMICOS, new StringBuilder(""));
		riscosMap.put(RISCOACIDENTES, new StringBuilder(""));
		
		String styleTag = "<style isBold=\"true\" pdfFontName=\"Helvetica-Bold\">";
		String styleTagClosing = "</style>";
		
		for (RiscoMedicaoRisco riscoMedicao : riscosDoAmbiente){
			StringBuilder riscoMedido = new StringBuilder();
			String quebraDeLinha="\n";
			
			riscoMedido.append(riscoMedicao.getPeriodicidadeExposicao() != null ? styleTag.concat("Periodicidade da exposição: ").concat(styleTagClosing).concat( riscoMedicao.getPeriodicidadeExposicao().toString() ).concat(". ") : "");
			riscoMedido.append(!StringUtil.isBlank(riscoMedicao.getIntensidadeMedida()) ? styleTag.concat("Intensidade/Concentração: ").concat(styleTagClosing).concat( StringUtil.replaceXml(riscoMedicao.getIntensidadeMedida()) ).concat(". ") : "");
			riscoMedido.append(!StringUtil.isBlank(riscoMedicao.getTecnicaUtilizada()) ? styleTag.concat("Técnica utilizada: ").concat(styleTagClosing).concat( StringUtil.replaceXml(riscoMedicao.getTecnicaUtilizada()) ).concat(". ") : "");
			
			if(isPpra)	
				riscoMedido.append(!StringUtil.isBlank(riscoMedicao.getDescricaoPpra()) ? styleTag.concat("Descrição: ").concat(styleTagClosing).concat( StringUtil.replaceXml(riscoMedicao.getDescricaoPpra()) ).concat(". ") : "");
			else		
				riscoMedido.append(!StringUtil.isBlank(riscoMedicao.getDescricaoLtcat()) ? styleTag.concat("Descrição: ").concat(styleTagClosing).concat( StringUtil.replaceXml(riscoMedicao.getDescricaoLtcat()) ).concat(". ") : "");
			
			riscoMedido.append(!StringUtil.isBlank(riscoMedicao.getMedidaDeSeguranca()) ? styleTag.concat("Medida de Segurança: ").concat(styleTagClosing).concat( StringUtil.replaceXml(riscoMedicao.getMedidaDeSeguranca()) ).concat(". ") : "");
			
			if (StringUtil.isBlank(riscoMedido.toString()))
				riscoMedido.append("(não informado)");
			
			String riscoAtual = styleTag.concat(riscoMedicao.getRisco().getDescricao()).concat(" - ").concat(styleTagClosing) + riscoMedido.toString();
			
			if (riscoMedicao.getRisco().getGrupoRisco().equals(GrupoRisco.FISICO)){
				riscosMap.get(RISCOFISICO).append( riscosMap.get(RISCOFISICO).length()>0 ? quebraDeLinha : "" );
				riscosMap.get(RISCOFISICO).append(riscoAtual);
			} 
			if (riscoMedicao.getRisco().getGrupoRisco().equals(GrupoRisco.QUIMICO)){
				riscosMap.get(RISCOQUIMICOS).append( riscosMap.get(RISCOQUIMICOS).length()>0 ? quebraDeLinha : "" );
				riscosMap.get(RISCOQUIMICOS).append(riscoAtual);
			} 
			if (riscoMedicao.getRisco().getGrupoRisco().equals(GrupoRisco.BIOLOGICO)){
				riscosMap.get(RISCOBIOLOGICOS).append( riscosMap.get(RISCOBIOLOGICOS).length()>0 ? quebraDeLinha : "" );
				riscosMap.get(RISCOBIOLOGICOS).append(riscoAtual);
			} 
			if (riscoMedicao.getRisco().getGrupoRisco().equals(GrupoRisco.ERGONOMICO)){
				riscosMap.get(RISCOERGONOMICOS).append( riscosMap.get(RISCOERGONOMICOS).length()>0 ? quebraDeLinha : "" );
				riscosMap.get(RISCOERGONOMICOS).append(riscoAtual);
			} 
			if (riscoMedicao.getRisco().getGrupoRisco().equals(GrupoRisco.ACIDENTE)){
				riscosMap.get(RISCOACIDENTES).append( riscosMap.get(RISCOACIDENTES).length()>0 ? quebraDeLinha : "" );
				riscosMap.get(RISCOACIDENTES).append(riscoAtual);
			}
		}
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
