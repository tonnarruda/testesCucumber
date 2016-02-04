package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.model.type.File;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.model.sesmt.relatorio.CatRelatorioAnual;

public interface CatManager extends GenericManager<Cat>
{
	Collection<Cat> findByColaborador(Colaborador colaborador);
	Collection<Cat> findCatsColaboradorByDate(Colaborador colaborador, Date data);
	Collection<Cat> findAllSelect(Long empresaId, Date inicio, Date fim, String[] estabelecimentosCheck, String nomeBusca, String[] areasCheck);
	Collection<CatRelatorioAnual> getRelatorioAnual(Long estabelecimentoId, Date dataFim);
	public Collection<Cat> findRelatorioCats (Long empresaId, Date inicio, Date fim, String[] estabelecimentosCheck, String nomeBusca) throws ColecaoVaziaException;
	int findQtdDiasSemAcidentes(Long empresaId);
	Collection<DataGrafico> findQtdCatsPorDiaSemana(Long empresaId, Date dataDe, Date dataAte);
	Collection<DataGrafico> findQtdCatsPorHorario(Long empresaId, Date dataDe, Date dataAte);
	Cat findByIdProjectionSimples(Long catId);
	Cat findByIdProjectionDetalhada(Long catId);
	void setFoto(Cat cat, boolean manterFoto, File foto, String local);
}