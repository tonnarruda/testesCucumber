package com.fortes.rh.security.spring.aop;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.Evento;
import com.fortes.security.auditoria.Audita;

public interface ComponenteSemAnotacaoDeModuloManager extends GenericManager<Evento> {

	@Audita(operacao="Inserção Customizada")
	public Evento save(Evento entity);
	
	@Audita(operacao="Atualização Customizada")
	public void update(Evento entity);
	
	@Audita(operacao="Remoçao Customizada")
	public void remove(Long id);
	
}
