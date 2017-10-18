package com.fortes.rh.model.avaliacao;

import java.util.Collection;

public class AgrupadorAnaliseDesempenhoOrganizacao implements Cloneable
{
	private Collection<AnaliseDesempenhoOrganizacao> analiseDesempenhoOrganizacaos;

	public Collection<AnaliseDesempenhoOrganizacao> getAnaliseDesempenhoOrganizacaos() {
		return analiseDesempenhoOrganizacaos;
	}

	public void setAnaliseDesempenhoOrganizacaos(
			Collection<AnaliseDesempenhoOrganizacao> analiseDesempenhoOrganizacaos) {
		this.analiseDesempenhoOrganizacaos = analiseDesempenhoOrganizacaos;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}