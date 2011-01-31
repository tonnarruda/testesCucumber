package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
<<<<<<< HEAD
import com.fortes.rh.model.geral.Empresa;
=======
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
>>>>>>> 510fddb8b4565cea88d5290cb1ddc73c9f616274
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio;
import com.fortes.web.tags.CheckBox;

public interface ExameManager extends GenericManager<Exame>
{
	Exame getExameAso();
	Exame findByIdProjection(Long exameId);
	Collection<Exame> findAllSelect(Long empresaId);
	Collection<Exame> findByHistoricoFuncao(Long historicoFuncaoId);
	Collection<Exame> populaExames(String[] examesCheck);
	String[] findBySolicitacaoExame(Long solicitacaoExameId);
	Collection<CheckBox> populaCheckBox(Long empresaId);
	Collection<ExamesPrevistosRelatorio> findRelatorioExamesPrevistos(Long empresaId, Date data, Long[] examesIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] colaboradoresIds, boolean agruparPorArea, boolean imprimirAfastados) throws ColecaoVaziaException, Exception;
	Collection<ExamesRealizadosRelatorio> findRelatorioExamesRealizados(Long empresaId, String nomeBusca, Date inicio, Date fim, String motivo, String exameResultado, Long clinicaAutorizadaId, Long[] examesIds, Long[] estabelecimentosIds, String vinculo) throws ColecaoVaziaException;
<<<<<<< HEAD
	void enviaLembreteExamesPrevistos(Collection<Empresa> empresas);
=======
	public Collection<ExamesRealizadosRelatorio> findRelatorioExamesRealizadosResumido(Long empresaId, Date dataInicio, Date dataFim, ClinicaAutorizada clinicaAutorizada, Long[] examesIds) throws ColecaoVaziaException;
>>>>>>> 510fddb8b4565cea88d5290cb1ddc73c9f616274
}