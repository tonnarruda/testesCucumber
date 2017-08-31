package com.fortes.rh.business.sesmt;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.model.sesmt.relatorio.FichaEpiRelatorio;
import com.fortes.web.tags.CheckBox;

public interface EpiManager extends GenericManager<Epi>
{
	Integer getCount(Long empresaId, String epiNome, Boolean ativo);
	Collection<Epi> findEpis(int page, int pagingSize, Long empresaId, String epiNome, Boolean ativo);
	Collection<CheckBox> populaCheckToEpi(Long id, Boolean epiAtivo);
	Collection<Epi> populaEpi(String[] episCheck);
	Epi findByIdProjection(Long epiId);
	void saveEpi(Epi epi, EpiHistorico epiHistorico) throws Exception;
	FichaEpiRelatorio findImprimirFicha(Empresa empresaSistema, Colaborador colaborador);
	Collection<Epi> findByVencimentoCa(Date data, Long empresaId, String[] tipoEPICheck);
	Collection<Epi> findEpisDoAmbiente(Long ambienteId, Date data);
	Collection<Epi> findByRiscoAmbienteOuFuncao(Long riscoId, Long ambienteId, Date data, boolean controlaRiscoPorAmbiente);
	Collection<Epi> findByRisco(Long riscoId);
	Collection<Epi> findByHistoricoFuncao(Long historicoFuncaoId);
	void removeEpi(Epi epi) throws Exception;
	void sincronizar(Long empresaOrigemId, Long empresaDestinoId, Map<Long, Long> epiIds);
	String findFabricantesDistinctByEmpresa(Long empresaId);
	Collection<Epi> findAllSelect(Long empresaId);
	Collection<Epi> findPriorizandoEpiRelacionado(Long empresaId, Long colaboradorId, boolean somenteAtivos);
	void importarArquivo(File file, Long empresaId) throws Exception;
}