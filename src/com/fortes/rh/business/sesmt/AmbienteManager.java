package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.relatorio.PpraLtcatRelatorio;
import com.fortes.web.tags.CheckBox;

public interface AmbienteManager extends GenericManager<Ambiente>
{
	Integer getCount(Long empresaId);
	Collection<Ambiente> findAmbientes(Long empresaId);
	Collection<Ambiente> findAmbientes(int page, int pagingSize, Long empresaId);
	void saveAmbienteHistorico(Ambiente ambiente, HistoricoAmbiente historicoAmbiente, String[] riscoChecks, String[] epcEficazChecks, String[] epcCheck) throws Exception;
	Collection<Ambiente> findByEstabelecimento(Long estabelecimentoId);
	Collection<Ambiente> findByEmpresa(Long empresaId);
	Collection<CheckBox> getAmbientes(Empresa empresa) throws Exception;
	public Collection<Long> getIdsAmbientes(Collection<HistoricoColaborador> colhc);
	Ambiente findByIdProjection(Long ambienteId);
	void removeCascade(Long id) throws Exception;
	Collection<CheckBox> populaCheckBox(Long estabelecimentoId);
	Collection<PpraLtcatRelatorio> montaRelatorioPpraLtcat(Empresa empresa, Long estabelecimentoId, Date data, String[] ambienteCheck, boolean gerarPpra, boolean gerarLtcat) throws Exception;
	int getQtdColaboradorByAmbiente(Long ambienteId, Date data, String sexo);
}