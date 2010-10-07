package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.model.sesmt.relatorio.FichaEpiRelatorio;
import com.fortes.web.tags.CheckBox;

public interface EpiManager extends GenericManager<Epi>
{
	Collection<CheckBox> populaCheckToEpi(Long id);
	Collection<Epi> populaEpi(String[] episCheck);
	Epi findByIdProjection(Long epiId);
	void saveEpi(Epi epi, EpiHistorico epiHistorico) throws Exception;
	FichaEpiRelatorio findImprimirFicha(Empresa empresaSistema, Colaborador colaborador);
	Collection<Epi> findByVencimentoCa(Date data, Long empresaId);
	Collection<Epi> findEpisDoAmbiente(Long ambienteId, Date data);
	Collection<Epi> findByRiscoAmbiente(Long riscoId, Long ambienteId, Date data);
	Collection<Epi> findByRisco(Long riscoId);
	Collection<Epi> findByHistoricoFuncao(Long historicoFuncaoId);
	void removeEpi(Epi epi) throws Exception;
	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId);
}