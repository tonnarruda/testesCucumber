package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.relatorio.Absenteismo;
import com.fortes.rh.security.spring.aop.callback.ColaboradorOcorrenciaAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public interface ColaboradorOcorrenciaManager extends GenericManager<ColaboradorOcorrencia>
{
	Collection<ColaboradorOcorrencia> findByColaborador(Long id);
	Collection<ColaboradorOcorrencia> filtrar(Long[] ocorrenciaCheckLong, Long[] colaboradorCheckLong, Long[] estabelecimentoCheckLong, Map parametros);
	Collection<ColaboradorOcorrencia> findProjection(int page, int pagingSize, Long colaboradorId);
	ColaboradorOcorrencia findByIdProjection(Long colaboradorOcorrenciaId);
	void saveOcorrenciasFromAC(Collection<ColaboradorOcorrencia> colaboradorOcorrencias) throws Exception;
	@Audita(operacao="Inserção/Atualização", auditor=ColaboradorOcorrenciaAuditorCallbackImpl.class)
	void saveColaboradorOcorrencia(ColaboradorOcorrencia colaboradorOcorrencia, Empresa empresa) throws Exception, IntegraACException;
	void removeFromAC(Collection<ColaboradorOcorrencia> colaboradorOcorrencias) throws Exception;
	@Audita(operacao="Remoção", auditor=ColaboradorOcorrenciaAuditorCallbackImpl.class)
	void remove(ColaboradorOcorrencia colaboradorOcorrencia, Empresa empresa) throws Exception;
	boolean verifyExistsMesmaData(Long colaboradorOcorrenciaId, Long colaboradorId, Long ocorrenciaId, Long empresaId, Date dataIni);
	Collection<Absenteismo> montaAbsenteismo(Date dataIni, Date dataFim, Collection<Long> empresaIds, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> ocorrenciasIds, Collection<Long> afastamentosIds, Collection<Long> cargosIds, Empresa empresaLogada) throws Exception;
	Collection<Object[]> montaGraficoAbsenteismo(String dataMesAnoIni, String dataMesAnoFim, Collection<Long> empresaIds, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Empresa empresaLogada);
	void deleteOcorrencias(Long[] ocorrenciaIds) throws Exception;
	Collection<ColaboradorOcorrencia> filtrarOcorrencias(Collection<Long> empresaIds, Date dataIni, Date dataFim, Collection<Long> ocorrenciaIds, Collection<Long> areaIds, Collection<Long> estabelecimentoIds, Collection<Long> colaboradorIds, boolean detalhamento, boolean agruparPorColaborador);
	Collection<ColaboradorOcorrencia> findByFiltros(int page, int pagingSize, String colaboradorNome, String ocorrenciaNome, Boolean comProvidencia, Long[] colaboradoresIds, Long empresaId);
	Collection<Colaborador> findColaboraesPermitidosByUsuario(Usuario usuarioLogado, Colaborador colaborador, Long empresaId, boolean roleVerAreas, boolean somenteDesligados);
	Collection<ColaboradorOcorrencia> findByEmpresaId(Long empresaId); 
}