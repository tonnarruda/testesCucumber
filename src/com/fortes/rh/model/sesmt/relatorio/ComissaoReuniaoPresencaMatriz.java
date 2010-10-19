package com.fortes.rh.model.sesmt.relatorio;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ComissaoReuniaoPresenca;

public class ComissaoReuniaoPresencaMatriz
{
	private Colaborador colaborador;
	private boolean membroDaComissao = false;
	private Collection<ComissaoReuniaoPresenca> comissaoReuniaoPresencas = new ArrayList<ComissaoReuniaoPresenca>();

	public boolean isMembroDaComissao() {
		return membroDaComissao;
	}
	public void setMembroDaComissao(boolean membroDaComissao) {
		this.membroDaComissao = membroDaComissao;
	}
	
	public Colaborador getColaborador()
	{
		return colaborador;
	}
	
	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}
	public Collection<ComissaoReuniaoPresenca> getComissaoReuniaoPresencas()
	{
		return comissaoReuniaoPresencas;
	}
	public void setComissaoReuniaoPresencas(Collection<ComissaoReuniaoPresenca> comissaoReuniaoPresencas)
	{
		this.comissaoReuniaoPresencas = comissaoReuniaoPresencas;
	}
	public void addComissaoReuniaoPresencas(ComissaoReuniaoPresenca presenca)
	{
		comissaoReuniaoPresencas.add(presenca);
	}
}
