package com.fortes.rh.web.dwr;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;

@Component
@RemoteProxy(name="ColaboradorQuestionarioDWR")
public class ColaboradorQuestionarioDWR {

	@Autowired private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	
	@RemoteMethod
	public boolean existeModeloAvaliacaoEmDesempnhoEPeriodoExperiencia(Long avaliacaoId)
	{
		boolean retorno = colaboradorQuestionarioManager.existeMesmoModeloAvaliacaoEmDesempenhoEPeriodoExperiencia(avaliacaoId);
		
		return retorno;
	}
}