package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
import com.fortes.rh.dao.geral.ColaboradorOcorrenciaDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.relatorio.Absenteismo;
import com.fortes.rh.model.ws.TOcorrenciaEmpregado;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.ws.AcPessoalClientColaboradorOcorrencia;

public class ColaboradorOcorrenciaManagerImpl extends GenericManagerImpl<ColaboradorOcorrencia, ColaboradorOcorrenciaDao> implements ColaboradorOcorrenciaManager
{
	private PlatformTransactionManager transactionManager;
	private ColaboradorManager colaboradorManager;
	private ColaboradorAfastamentoManager colaboradorAfastamentoManager;
	private OcorrenciaManager ocorrenciaManager;
	private AcPessoalClientColaboradorOcorrencia acPessoalClientColaboradorOcorrencia;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;

	public Collection<ColaboradorOcorrencia> findByColaborador(Long id)
	{
		return getDao().findByColaborador(id);
	}

	public Collection<ColaboradorOcorrencia> filtrar(Long[] ocorrenciaCheckLong, Long[] colaboradorCheckLong, Long[] estabelecimentoCheckLong, Map parametros)
	{
		Collection<ColaboradorOcorrencia> colaboradorOcorrenciasDefinitivo = new ArrayList<ColaboradorOcorrencia>();

		Collection<ColaboradorOcorrencia> colaboradorOcorrencias = getDao().filtrar(ocorrenciaCheckLong, colaboradorCheckLong, estabelecimentoCheckLong, parametros);

		HashSet<Long> ids = new HashSet<Long>();

		for(ColaboradorOcorrencia colaboradorOcorrencia : colaboradorOcorrencias){
			if(!ids.contains(colaboradorOcorrencia.getId()))
				colaboradorOcorrenciasDefinitivo.add(colaboradorOcorrencia);

			ids.add(colaboradorOcorrencia.getId());
		}

		return colaboradorOcorrenciasDefinitivo;
	}

	public Collection<ColaboradorOcorrencia> findProjection(int page, int pagingSize, Long colaboradorId)
	{
		return getDao().findProjection(page, pagingSize, colaboradorId);
	}

	public ColaboradorOcorrencia findByIdProjection(Long colaboradorOcorrenciaId)
	{
		return getDao().findByIdProjection(colaboradorOcorrenciaId);
	}

	/**
	 * Recebe uma ou mais Ocorrências em lote do AC Pessoal.
	 */
	public void saveOcorrenciasFromAC(Collection<ColaboradorOcorrencia> colaboradorOcorrencias) throws Exception
	{
		ColaboradorOcorrencia colaboradorOcorrenciaTmp = verificaUpdate(colaboradorOcorrencias);

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			for (ColaboradorOcorrencia colaboradorOcorrencia : colaboradorOcorrencias)
			{
				String empresaCodigoAC = colaboradorOcorrencia.getOcorrencia().getEmpresa().getCodigoAC();
				String grupoAC = colaboradorOcorrencia.getOcorrencia().getEmpresa().getGrupoAC();

				Ocorrencia ocorrencia = ocorrenciaManager.findByCodigoAC(colaboradorOcorrencia.getOcorrencia().getCodigoAC(), empresaCodigoAC, grupoAC);
				colaboradorOcorrencia.setOcorrencia(ocorrencia);

				Colaborador colaborador = colaboradorManager.findByCodigoAC(colaboradorOcorrencia.getColaborador().getCodigoAC(), empresaCodigoAC, grupoAC);
				colaboradorOcorrencia.setColaborador(colaborador);

				if (colaboradorOcorrenciaTmp != null)
				{
					colaboradorOcorrencia.setId(colaboradorOcorrenciaTmp.getId());
					colaboradorOcorrencia.setDataFim(colaboradorOcorrenciaTmp.getDataFim());

					getDao().update(colaboradorOcorrencia);
				}
				else
					getDao().save(colaboradorOcorrencia);
			}

			transactionManager.commit(status);
		}
		catch (Exception e)
		{
			transactionManager.rollback(status);
			e.printStackTrace();
			throw new Exception("Erro ao cadastrar ocorrência(s).");
		}
	}

	private ColaboradorOcorrencia verificaUpdate(Collection<ColaboradorOcorrencia> colaboradorOcorrencias)
	{
		if (colaboradorOcorrencias.size() == 1)
		{
			ColaboradorOcorrencia colaboradorOcorrencia = (ColaboradorOcorrencia) colaboradorOcorrencias.toArray()[0];
			colaboradorOcorrencia = getDao().findByDadosAC(colaboradorOcorrencia.getDataIni(), colaboradorOcorrencia.getOcorrencia().getCodigoAC(), colaboradorOcorrencia.getColaborador().getCodigoAC(), colaboradorOcorrencia.getOcorrencia().getEmpresa().getCodigoAC(), colaboradorOcorrencia.getOcorrencia().getEmpresa().getGrupoAC());

			return colaboradorOcorrencia != null ? colaboradorOcorrencia : null;
		}
		return null;
	}

	/**
	 * Remove Ocorrências recebidas do AC Pessoal.
	 */
	public void removeFromAC(Collection<ColaboradorOcorrencia> colaboradorOcorrencias) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			for (ColaboradorOcorrencia colaboradorOcorrencia : colaboradorOcorrencias)
			{
				ColaboradorOcorrencia tmp =
					getDao().findByDadosAC(
						colaboradorOcorrencia.getDataIni(),
						colaboradorOcorrencia.getOcorrencia().getCodigoAC(),
						colaboradorOcorrencia.getColaborador().getCodigoAC(),
						colaboradorOcorrencia.getOcorrencia().getEmpresa().getCodigoAC(), 
						colaboradorOcorrencia.getOcorrencia().getEmpresa().getGrupoAC());

				getDao().remove(tmp.getId());
			}

			transactionManager.commit(status);
		}
		catch (Exception e)
		{
			transactionManager.rollback(status);
			e.printStackTrace();
			throw new Exception("Erro ao remover ocorrência(s).");
		}
	}

	/**
	 * Grava localmente/Envia para o AC se integrado
	 */
	public void saveColaboradorOcorrencia(ColaboradorOcorrencia colaboradorOcorrencia, Empresa empresa) throws Exception
	{
		if (empresa.isAcIntegra() && colaboradorOcorrencia.getOcorrencia().getIntegraAC())
		{
			try
			{
				boolean sucesso = acPessoalClientColaboradorOcorrencia.criarColaboradorOcorrencia(bindColaboradorOcorrencia(colaboradorOcorrencia,empresa), empresa);

				if (!sucesso)
					throw new IntegraACException("Método: AcPessoalClientColaboradorOcorrencia.criarColaboradorOcorrencia retornou false");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IntegraACException(e.getMessage());
			}
		}

		if (colaboradorOcorrencia.getId() == null)
		{
			getDao().save(colaboradorOcorrencia);
			gerenciadorComunicacaoManager.enviaAvisoOcorrenciaCadastrada(colaboradorOcorrencia, empresa.getId());
		}
		else
			getDao().update(colaboradorOcorrencia);

	}

	public void remove(ColaboradorOcorrencia colaboradorOcorrencia, Empresa empresa) throws Exception
	{
		try
		{
			colaboradorOcorrencia = findByIdProjection(colaboradorOcorrencia.getId());

			if (empresa.isAcIntegra() && colaboradorOcorrencia.getOcorrencia().getIntegraAC())
			{
				boolean sucesso = acPessoalClientColaboradorOcorrencia.removerColaboradorOcorrencia(bindColaboradorOcorrencia(colaboradorOcorrencia, empresa), empresa);
				if (!sucesso)
					throw new IntegraACException("Método: AcPessoalClientColaboradorOcorrencia.removerColaboradorOcorrencia retornou false");
			}

			remove(colaboradorOcorrencia.getId());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IntegraACException(e.getMessage());
		}
	}

	private TOcorrenciaEmpregado bindColaboradorOcorrencia(ColaboradorOcorrencia colaboradorOcorrencia, Empresa empresa)
	{
		TOcorrenciaEmpregado tColaboradorOcorrencia = new TOcorrenciaEmpregado();
		tColaboradorOcorrencia.setCodigoEmpregado(colaboradorOcorrencia.getColaborador().getCodigoAC());
		tColaboradorOcorrencia.setEmpresa(empresa.getCodigoAC());
		tColaboradorOcorrencia.setCodigo(colaboradorOcorrencia.getOcorrencia().getCodigoAC());
		tColaboradorOcorrencia.setData(DateUtil.formataDiaMesAno(colaboradorOcorrencia.getDataIni()));
		tColaboradorOcorrencia.setObs(colaboradorOcorrencia.getObservacao());
		return tColaboradorOcorrencia;
	}

	public boolean verifyExistsMesmaData(Long colaboradorOcorrenciaId, Long colaboradorId, Long ocorrenciaId, Long empresaId, Date dataIni)
	{
		return getDao().verifyExistsMesmaData(colaboradorOcorrenciaId, colaboradorId, ocorrenciaId, empresaId, dataIni);
	}

	public Collection<Absenteismo> montaAbsenteismo(Date dataIni, Date dataFim, Collection<Long> empresaIds, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> ocorrenciasIds, Collection<Long> afastamentosIds, Collection<Long> cargosIds) throws Exception 
	{
		Collection<Absenteismo> absenteismos = getDao().countFaltasByPeriodo(dataIni, dataFim, empresaIds, estabelecimentosIds, areasIds, cargosIds, ocorrenciasIds);
		Collection<Absenteismo> afastamentos = colaboradorAfastamentoManager.countAfastamentosByPeriodo(dataIni, dataFim, empresaIds, estabelecimentosIds, areasIds, cargosIds, afastamentosIds);
		
		for (Absenteismo absenteismo : absenteismos) 
		{
			for (Absenteismo afastamento : afastamentos) 
			{
				if (absenteismo.getAno().equals(afastamento.getAno()) && absenteismo.getMes().equals(afastamento.getMes()))
				{
					Date inicioDoMes = DateUtil.criarDataMesAno(1, Integer.parseInt(absenteismo.getMes()), Integer.parseInt(absenteismo.getAno()));
					absenteismo.setQtdAtivos(colaboradorManager.countAtivosPeriodo(DateUtil.getUltimoDiaMesAnterior(inicioDoMes), empresaIds, estabelecimentosIds, areasIds, cargosIds, ocorrenciasIds, true, null, true));
					absenteismo.setQtdDiasTrabalhados(DateUtil.contaDiasUteisMes(inicioDoMes));
		
					absenteismo.setQtdTotalFaltas(absenteismo.getQtdTotalFaltas() + afastamento.getQtdTotalFaltas());
					
					if(!absenteismo.getQtdTotalFaltas().equals(0))
						absenteismo.setAbsenteismo( new Double(absenteismo.getQtdTotalFaltas()) / (absenteismo.getQtdAtivos() * absenteismo.getQtdDiasTrabalhados()) * 100.0);
				}
			}
		}

		if (absenteismos.isEmpty())
			throw new ColecaoVaziaException();
		
		return absenteismos;
	}
	
	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}
	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}
	public void setOcorrenciaManager(OcorrenciaManager ocorrenciaManager)
	{
		this.ocorrenciaManager = ocorrenciaManager;
	}

	public void setAcPessoalClientColaboradorOcorrencia(AcPessoalClientColaboradorOcorrencia acPessoalClientColaboradorOcorrencia)
	{
		this.acPessoalClientColaboradorOcorrencia = acPessoalClientColaboradorOcorrencia;
	}

	public Collection<Object[]> montaGraficoAbsenteismo(String dataMesAnoIni, String dataMesAnoFim, Collection<Long> empresaIds, Collection<Long> areasIds, Collection<Long> cargosIds) 
	{
		Collection<Object[]>  graficoEvolucaoAbsenteismo = new ArrayList<Object[]>();
		Date dataIni = DateUtil.criarDataMesAno(dataMesAnoIni);
		Date dataFim = DateUtil.getUltimoDiaMes(DateUtil.criarDataMesAno(dataMesAnoFim));

		try {
			Collection<Absenteismo> absenteismos = montaAbsenteismo(dataIni, dataFim, empresaIds, null, areasIds, null, null, cargosIds);
			
			for (Absenteismo absenteismo : absenteismos) 
			{
				Date ultimoDiaMes = DateUtil.getInicioMesData(DateUtil.criarDataMesAno(absenteismo.getMes() + "/" + absenteismo.getAno()));
				graficoEvolucaoAbsenteismo.add(new Object[]{ultimoDiaMes.getTime(), absenteismo.getAbsenteismo() });	
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return graficoEvolucaoAbsenteismo;
	}

	public void deleteOcorrencias(Long[] ocorrenciaIds) throws Exception {
		
		if(ocorrenciaIds != null && ocorrenciaIds.length > 0){
			getDao().deleteByOcorrencia(ocorrenciaIds);
			ocorrenciaManager.remove(ocorrenciaIds);
		}
	}
	
	public Collection<Colaborador> findColaboraesPermitidosByUsuario(Usuario usuarioLogado, Colaborador colaborador, Long empresaId, boolean roleVerAreas) 
	{
		Long[] areasIds = null;
		if(usuarioLogado.getId() != 1L && !roleVerAreas)
		{
			areasIds = areaOrganizacionalManager.findIdsAreasDoResponsavelCoResponsavel(usuarioLogado, empresaId);
			
			if(areasIds.length == 0)
				areasIds = new Long[]{-1L};//não vai achar nenhum colaborador
		}
			
		return colaboradorManager.findByAreasOrganizacionalIds(null, null, areasIds, null, colaborador, null, null, empresaId, false);
	}
	
	public Collection<ColaboradorOcorrencia> findByFiltros(int page, int pagingSize, String colaboradorNome, String ocorrenciaNome, Boolean comProvidencia, Long[] colaboradoresIds, Long empresaId) 
	{
		return getDao().findByFiltros(page, pagingSize, colaboradorNome, ocorrenciaNome, comProvidencia, colaboradoresIds, empresaId);
	}

	public Collection<ColaboradorOcorrencia> filtrarOcorrencias(Collection<Long> empresaIds, Date dataIni, Date dataFim, Collection<Long> ocorrenciaIds, Collection<Long> areaIds, Collection<Long> estabelecimentoIds, Collection<Long> colaboradorIds, boolean detalhamento, boolean agruparPorColaborador)
	{
		return getDao().findColaboradorOcorrencia(ocorrenciaIds, colaboradorIds, dataIni, dataFim, empresaIds, areaIds, estabelecimentoIds, detalhamento, agruparPorColaborador);
	}

	public void setColaboradorAfastamentoManager(ColaboradorAfastamentoManager colaboradorAfastamentoManager) {
		this.colaboradorAfastamentoManager = colaboradorAfastamentoManager;
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}
}