package com.fortes.rh.model.sesmt.relatorio;

import java.util.Collection;

import com.fortes.rh.model.sesmt.ExtintorInspecao;
import com.fortes.rh.model.sesmt.ExtintorManutencao;

public class ManutencaoAndInspecaoRelatorio
{
	private Collection<ExtintorInspecao> extintoresComInspecaoVencida;
	private Collection<ExtintorManutencao> extintoresComCargaVencida;
	private Collection<ExtintorManutencao> extintoresComTesteHidrostaticoVencido;
	
	public Collection<ExtintorManutencao> getExtintoresComCargaVencida()
	{
		return extintoresComCargaVencida;
	}
	public void setExtintoresComCargaVencida(Collection<ExtintorManutencao> extintoresComCargaVencida)
	{
		this.extintoresComCargaVencida = extintoresComCargaVencida;
	}
	public Collection<ExtintorInspecao> getExtintoresComInspecaoVencida()
	{
		return extintoresComInspecaoVencida;
	}
	public void setExtintoresComInspecaoVencida(Collection<ExtintorInspecao> extintoresComInspecaoVencida)
	{
		this.extintoresComInspecaoVencida = extintoresComInspecaoVencida;
	}
	public Collection<ExtintorManutencao> getExtintoresComTesteHidrostaticoVencido()
	{
		return extintoresComTesteHidrostaticoVencido;
	}
	public void setExtintoresComTesteHidrostaticoVencido(Collection<ExtintorManutencao> extintoresComTesteHidrostaticoVencido)
	{
		this.extintoresComTesteHidrostaticoVencido = extintoresComTesteHidrostaticoVencido;
	}
}
