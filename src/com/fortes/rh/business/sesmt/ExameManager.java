package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio;
import com.fortes.web.tags.CheckBox;

public interface ExameManager extends GenericManager<Exame>
{
	Exame findByIdProjection(Long exameId);
	Collection<Exame> findByHistoricoFuncao(Long historicoFuncaoId);
	Collection<Exame> populaExames(String[] examesCheck);
	String[] findBySolicitacaoExame(Long solicitacaoExameId);
	Collection<CheckBox> populaCheckBox(Long empresaId);
	Collection<ExamesPrevistosRelatorio> findRelatorioExamesPrevistos(Long empresaId, Date dataInicio, Date dateFim, Long[] examesIds, Long[] estabelecimentosIds, Long[] areasIds, Long[] colaboradoresIds, char agruparPor, boolean imprimirAfastados, boolean imprimirDesligados) throws ColecaoVaziaException, Exception;
	Collection<ExamesRealizadosRelatorio> findRelatorioExamesRealizados(Long empresaId, String nomeBusca, Date inicio, Date fim, String motivo, String exameResultado, Long clinicaAutorizadaId, Long[] examesIds, Long[] estabelecimentosIds, Character tipoPessoa) throws ColecaoVaziaException;
	void enviaLembreteExamesPrevistos(Collection<Empresa> empresas);
	public Collection<ExamesRealizadosRelatorio> findRelatorioExamesRealizadosResumido(Long empresaId, Date dataInicio, Date dataFim, ClinicaAutorizada clinicaAutorizada, Long[] examesIds, String resultadoExame) throws ColecaoVaziaException;
	Integer count(Long empresaId, Exame exame);
	Collection<Exame> find(Integer page, Integer pagingSize, Long empresaId, Exame exame);
	Collection<Exame> findPriorizandoExameRelacionado(Long empresaId, Long colaboradorId);
	public Collection<Exame> findByEmpresaComAsoPadrao(Long empresaId);
	public Collection<Exame> findByAsoPadrao();
}