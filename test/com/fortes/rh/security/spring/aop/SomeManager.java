package com.fortes.rh.security.spring.aop;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.Evento;
import com.fortes.security.auditoria.Audita;
import com.fortes.security.auditoria.Modulo;

@Modulo("Cadastro de Bugigangas")
public interface SomeManager extends GenericManager<Evento> {

	@Audita(operacao="Inserção Customizada")
	public Evento save(Evento entity);
	
	@Audita(operacao="Atualização Customizada")
	public void update(Evento entity);
	
	@Audita(operacao="Remoçao Customizada")
	public void remove(Long id);
	
	@Audita(operacao="Metodo Invalido utilizado apenas nos testes unitarios")
	public Integer getCount();
	
}
