package com.fortes.rh.security.spring.aop;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.security.spring.aop.callback.crud.InsertAuditorCallbackImpl;
import com.fortes.rh.security.spring.aop.callback.crud.RemoveAuditorCallbackImpl;
import com.fortes.rh.security.spring.aop.callback.crud.UpdateAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;
import com.fortes.security.auditoria.Modulo;

@Modulo("Cadastro de Bugigangas")
public interface SomeManager extends GenericManager<Evento> {

	@Audita(operacao="Inserção Customizada", auditor=InsertAuditorCallbackImpl.class)
	public Evento save(Evento entity);
	
	@Audita(operacao="Atualização Customizada" , auditor=UpdateAuditorCallbackImpl.class)
	public void update(Evento entity);
	
	@Audita(operacao="Remoçao Customizada", auditor=RemoveAuditorCallbackImpl.class)
	public void remove(Long id);
	
	@Audita(operacao="Metodo Invalido utilizado apenas nos testes unitarios")
	public Integer getCount();
	
}
