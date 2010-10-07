package com.fortes.rh.model.sesmt.relatorio;

import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.util.MathUtil;


public class ExameAnualRelatorio
{
	private String exameMotivo;	 		 // Tipo do Exame
	private Long exameId;
	private String exameNome;
	private Float totalExame = 0F;	 	 // Total
	private Float totalExameAnormal = 0F;// Total Anormais
	private Float examePorAnormal = 0F;	 // (anormais*100) / total
	private String examesPrevistos = "-"; 	 // exames para ano seguinte usado apenas p/ Periódicos
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null || !(obj instanceof ExameAnualRelatorio) || ((ExameAnualRelatorio) obj).getExameMotivo() == null)
			return false;
			
		return (((ExameAnualRelatorio) obj).getExameMotivo().equals(this.exameMotivo) && ((ExameAnualRelatorio) obj).getExameId().equals(this.exameId)); 
	}
	
	@Override
	public int hashCode()
	{
		int hash = (this.exameMotivo != null ? this.exameMotivo.hashCode() : 0);
		return hash;
	}
	
	public void calculaAnormaisPorTotal() 
	{
		setExamePorAnormal(MathUtil.formataValor((getTotalExameAnormal() * 100) / getTotalExame()));
	}
	
	public void calculaExamesPrevistos(float totalAdmissoes, float totalDemissoes) 
	{
		Float previstos = this.totalExame - totalDemissoes + totalAdmissoes;
		this.examesPrevistos = Integer.valueOf(previstos.intValue()).toString();
	}
	
	public void addTotalExame() {
		this.totalExame++;
	}
	
	public void addTotalExameAnormal() {
		totalExameAnormal++;
	}

	public String getExameNome()
	{
		return exameNome;
	}
	public void setExameNome(String exameNome)
	{
		this.exameNome = exameNome;
	}

	public Float getExamePorAnormal()
	{
		return examePorAnormal;
	}

	public void setExamePorAnormal(Float examePorAnormal)
	{
		this.examePorAnormal = examePorAnormal;
	}

	public Float getTotalExame()
	{
		return totalExame;
	}

	public void setTotalExame(Float totalExame)
	{
		this.totalExame = totalExame;
	}

	public Float getTotalExameAnormal()
	{
		return totalExameAnormal;
	}

	public void setTotalExameAnormal(Float totalExameAnormal)
	{
		this.totalExameAnormal = totalExameAnormal;
	}

	public Long getExameId()
	{
		return exameId;
	}
	public void setExameId(Long exameId)
	{
		this.exameId = exameId;
	}

	public String getExameMotivo() {
		return MotivoSolicitacaoExame.getInstance().get(exameMotivo);
	}

	public void setExameMotivo(String exameMotivo) {
		this.exameMotivo = exameMotivo;
	}

	public String getExamesPrevistos() {
		return examesPrevistos;
	}
}