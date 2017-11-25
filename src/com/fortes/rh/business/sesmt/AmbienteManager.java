package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.model.sesmt.relatorio.PpraLtcatRelatorio;
import com.fortes.web.tags.CheckBox;

public interface AmbienteManager extends GenericManager<Ambiente>
{
	Integer getCount(Long empresaId, HistoricoAmbiente historicoAmbiente);
	Collection<Ambiente> findAmbientes(Long empresaId);
	Collection<Ambiente> findAmbientes(int page, int pagingSize, Long empresaId, HistoricoAmbiente historicoAmbiente) throws ColecaoVaziaException;
	void saveAmbienteHistorico(Empresa empresa, HistoricoAmbiente historicoAmbiente, String[] riscoChecks, Collection<RiscoAmbiente> riscosAmbientes, String[] epcCheck) throws Exception;
	Collection<Ambiente> findAmbientesPorEstabelecimento(Long[] estabelecimentoIds, Date data);
	Collection<Ambiente> findByEmpresa(Long empresaId);
	Ambiente findByIdProjection(Long ambienteId);
	void removeCascade(Long ambienteId) throws Exception;
	Collection<CheckBox> populaCheckBox(Long empresaId, Long estabelecimentoId, Integer localAmbiente, Date data);
	public Collection<CheckBox> populaCheckBoxByEstabelecimentos(Long[] estabelecimentoIds);
	Collection<PpraLtcatRelatorio> montaRelatorioPpraLtcat(Empresa empresa, Estabelecimento estabelecimento, Integer localAmbiente, Date data, String[] ambienteCheck, boolean gerarPpra, boolean gerarLtcat, boolean exibirComposicaoSesmt) throws Exception;
	public int getQtdColaboradorByAmbiente(Long ambienteId, Date data, String sexo);
	void sincronizar(Long empresaOrigemId, Long empresaDestinoId);
	void atualizaDadosParaUltimoHistorico(Long ambienteId);
	void deleteAmbienteSemHistorico() throws Exception;
	Collection<Ambiente> findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(Long empresaId, Long estabelecimentoId, Integer localAmbiente, Date data);
	public Map<String,Collection<Ambiente>> montaMapAmbientes(Long empresaId, Long estabelecimentoId, String estabelecimentoNome, Date data);
}