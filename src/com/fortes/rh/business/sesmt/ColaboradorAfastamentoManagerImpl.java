package com.fortes.rh.business.sesmt;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.CidManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.sesmt.ColaboradorAfastamentoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.dicionario.OpcaoImportacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.relatorio.Absenteismo;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.model.sesmt.Afastamento;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.model.sesmt.ColaboradorAfastamentoComparator;
import com.fortes.rh.model.sesmt.relatorio.ColaboradorAfastamentoMatriz;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.util.importacao.ImportacaoCSVUtil;

public class ColaboradorAfastamentoManagerImpl extends GenericManagerImpl<ColaboradorAfastamento, ColaboradorAfastamentoDao> implements ColaboradorAfastamentoManager
{
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private AfastamentoManager afastamentoManager;
	private ColaboradorManager colaboradorManager; 
	private CidManager cidManager;
	
	private Collection<ColaboradorAfastamento> colaboradorAfastamentos;

	public Integer getCount(Long empresaId, String matriculaBusca, String nomeBusca, String[] estabelecimentoCheck, ColaboradorAfastamento colaboradorAfastamento)
	{
		Date inicio=null,fim=null;
		Long afastamentoId = null;

		if (colaboradorAfastamento != null)
		{
			inicio = colaboradorAfastamento.getInicio();
			fim = colaboradorAfastamento.getFim();

			if (colaboradorAfastamento.getAfastamento() != null)
				afastamentoId = colaboradorAfastamento.getAfastamento().getId();
		}

		Long[] estabelecimentoIds = LongUtil.arrayStringToArrayLong(estabelecimentoCheck);

		return getDao().getCount(empresaId, afastamentoId, matriculaBusca, nomeBusca, estabelecimentoIds, inicio, fim);
	}

	public Collection<ColaboradorAfastamento> findAllSelect(int page, int pagingSize, Long empresaId, String matriculaBusca, String nomeBusca, String[] estabelecimentoCheck, String[] areasCheck, ColaboradorAfastamento colaboradorAfastamento, String[] ordenarPor, boolean isListagemColaboradorAfastamento, char afastadoPeloINSS)
	{
		Date inicio=null,fim=null;
		Long afastamentoId = null;

		if (colaboradorAfastamento != null)
		{
			inicio = colaboradorAfastamento.getInicio();
			fim = colaboradorAfastamento.getFim();

			if (colaboradorAfastamento.getAfastamento() != null)
				afastamentoId = colaboradorAfastamento.getAfastamento().getId();
		}

		Long[] estabelecimentoIds = LongUtil.arrayStringToArrayLong(estabelecimentoCheck);
		Long[] areaIds = LongUtil.arrayStringToArrayLong(areasCheck);
		
		return getDao().findAllSelect(page, pagingSize, isListagemColaboradorAfastamento, empresaId, afastamentoId, matriculaBusca, nomeBusca, estabelecimentoIds, areaIds, inicio, fim, ordenarPor, afastadoPeloINSS);
	}

	public Collection<ColaboradorAfastamento> findRelatorioAfastamentos(Long empresaId, String nomeBusca, String[] estabelecimentoCheck, String[] areasCheck, ColaboradorAfastamento colaboradorAfastamento, String[] ordenarPor, char afastadoPeloINSS) throws ColecaoVaziaException
	{
		//cuidado com os parametros desse metodo eles são unha e carne com o relatorio gerado, os parametros são fundamentais
		Collection<ColaboradorAfastamento> colaboradorAfastamentos = findAllSelect(0, 0, empresaId, null, nomeBusca, estabelecimentoCheck, areasCheck, colaboradorAfastamento, ordenarPor, false, afastadoPeloINSS);
		if (colaboradorAfastamentos == null || colaboradorAfastamentos.isEmpty())
			throw new ColecaoVaziaException("Não há afastamentos para o filtro informado.");

		if(ordenarPor != null && ordenarPor[0].equals("cid"))
		{
			for (ColaboradorAfastamento colaboradorAfastamentotemp : colaboradorAfastamentos)
			{
				if (StringUtils.isBlank(colaboradorAfastamentotemp.getCid())) {
					colaboradorAfastamentotemp.setCid("");
				} else {
					String descricaoCid = cidManager.findDescricaoByCodigo(colaboradorAfastamentotemp.getCid());
					colaboradorAfastamentotemp.setCid(colaboradorAfastamentotemp.getCid() + " - " + (descricaoCid.equals("")?"CID não identificado":descricaoCid));
				}
			}
		}
		
		setFamiliaAreas(colaboradorAfastamentos, empresaId);

		return colaboradorAfastamentos;
	}
	
	public Collection<ColaboradorAfastamento> findRelatorioResumoAfastamentos(Long empresaId, String[] estabelecimentosCheck, String[] areasCheck, String[] motivosCheck, ColaboradorAfastamento colaboradorAfastamento) throws ColecaoVaziaException 
	{
		Collection<ColaboradorAfastamento> colaboradorAfastamentos = getDao().findRelatorioResumoAfastamentos(empresaId, LongUtil.arrayStringToArrayLong(estabelecimentosCheck), LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(motivosCheck), colaboradorAfastamento);
	
		if (colaboradorAfastamentos == null || colaboradorAfastamentos.isEmpty())
			throw new ColecaoVaziaException("Não há afastamentos para o filtro informado.");
		
		return colaboradorAfastamentos;
	}

	private void setFamiliaAreas(Collection<ColaboradorAfastamento> colaboradorAfastamentos, Long empresaId)
	{
		Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, empresaId);

		try
		{
			areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		for (ColaboradorAfastamento colaboradorAfastamento: colaboradorAfastamentos)
		{
			if(colaboradorAfastamento.getColaborador().getAreaOrganizacional() != null && colaboradorAfastamento.getColaborador().getAreaOrganizacional().getId() != null)
				colaboradorAfastamento.getColaborador().setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areaOrganizacionals, colaboradorAfastamento.getColaborador().getAreaOrganizacional().getId()));
		}
	}
	
	// usados pela importação de Afastamentos.
	Map<String, Afastamento> mapDescricaoAfastamento = null; 
	Integer countAfastamentosImportados = 0;
	Integer countTiposAfastamentosCriados = 0;
	
	/**
	 * Importação de Afastamentos dos Empregados. 
	 * Recebe Csv no formato:
	 * Cód. Empregado;Nome Completo;Nome de Escala;Doença(Tipo Afastamento);Data Inicial;Data Final;Médico;Descrição do Tipo
	 * @throws Exception 
	 */
	public void importarCSV(File arquivo, Map<String, Long> afastamentos, Empresa empresa) throws Exception
	{
		countAfastamentosImportados = 0;
		countTiposAfastamentosCriados = 0;
		
		Collection<ColaboradorAfastamento> colaboradorAfastamentos = this.carregarCSV(arquivo);
		String descricao = null;
		String codigoAC = null;
		Colaborador colaborador = null;
		
		for (ColaboradorAfastamento colaboradorAfastamento : colaboradorAfastamentos)
		{
			descricao 	= colaboradorAfastamento.getAfastamento().getDescricao();
			codigoAC 	= colaboradorAfastamento.getColaborador().getCodigoAC();
			colaborador = colaboradorManager.findByCodigoAC(codigoAC, empresa);

			if(colaborador != null)
			{
				if (afastamentos != null && afastamentos.containsKey(descricao) && afastamentos.get(descricao) != null)
					colaboradorAfastamento.setAfastamentoId(afastamentos.get(descricao));//usuário relacionou o afastamento correspondente no RH
				else
					setAfastamentoCadastrado(colaboradorAfastamento);
				
				colaboradorAfastamento.setColaborador(colaborador);
				
				if(!getDao().exists(colaboradorAfastamento))
				{
					getDao().save(colaboradorAfastamento);
					countAfastamentosImportados++;					
				}
			}
		}
	}
	
	public Collection<ColaboradorAfastamento> carregarCSV(File arquivo) throws Exception 
	{
		mapDescricaoAfastamento = getMapDescricaoAfastamentos();
		
		// importacao do CSV para colecao
		ImportacaoCSVUtil importacaoCSVUtil = new ImportacaoCSVUtil();
		
		importacaoCSVUtil.importarCSV(arquivo, OpcaoImportacao.AFASTAMENTOS_TRU);
		return importacaoCSVUtil.getAfastamentos();
	}
	
	// seta tipo afastamento que já existe no banco
	private void setAfastamentoCadastrado(ColaboradorAfastamento colaboradorAfastamento) {
		
		String afastamentoDescricao = StringUtil.retiraAcento(colaboradorAfastamento.getAfastamento().getDescricao()).toLowerCase();
		Afastamento afastamento = mapDescricaoAfastamento.get(afastamentoDescricao);
		
		// se nao existe com essa descricao, 
		// salva um novo tipo de Afastamento no banco
		if (afastamento == null)
		{
			afastamento = colaboradorAfastamento.getAfastamento();
			afastamentoManager.save(afastamento);
			
			mapDescricaoAfastamento.put(afastamentoDescricao, afastamento);
			countTiposAfastamentosCriados++;
		}
		
		colaboradorAfastamento.setAfastamento(afastamento);
	}

	// busca e mapeia Descricao -> Afastamento  
	private Map<String, Afastamento> getMapDescricaoAfastamentos() {
		
		Map<String, Afastamento> mapDescricaoAfastamento = new HashMap<String, Afastamento>();
		
		Collection<Afastamento> afastamentos = afastamentoManager.findAll();
		
		for (Afastamento afastamento : afastamentos)
		{
			String descricao = StringUtil.retiraAcento(afastamento.getDescricao()).toLowerCase();
			mapDescricaoAfastamento.put(descricao, afastamento);
		}
		return mapDescricaoAfastamento;
	}
	
	public Collection<DataGrafico> findQtdCatsPorDiaSemana(Long empresaId, Date dataIni, Date dataFim) 
	{
		Collection<DataGrafico> graficoAfastamentosPorMotivo = new ArrayList<DataGrafico>();
		Collection<Afastamento> qtdAfastamentosPorMotivo = getDao().findQtdAfastamentosPorMotivo(empresaId, dataIni, dataFim);
		
		for (Afastamento afastamento : qtdAfastamentosPorMotivo)
			graficoAfastamentosPorMotivo.add(new DataGrafico(null, afastamento.getDescricao(), afastamento.getQtd(), ""));
		
		return graficoAfastamentosPorMotivo;
	}
	
	public boolean possuiAfastamentoNestePeriodo(ColaboradorAfastamento colaboradorAfastamento, boolean isUpdate) 
	{
		Collection<ColaboradorAfastamento> colAfastamentos = getDao().findByColaborador(colaboradorAfastamento.getColaborador().getId());
		
		if(isUpdate)
			colAfastamentos.remove(colaboradorAfastamento);

		for (ColaboradorAfastamento colAfastamento : colAfastamentos) 
		{
			if(colAfastamento.getFim() != null && colaboradorAfastamento.getFim() != null)
			{
				if((colaboradorAfastamento.getInicio().getTime() >= colAfastamento.getInicio().getTime()  && colaboradorAfastamento.getInicio().getTime() <= colAfastamento.getFim().getTime() ) ||
						(colaboradorAfastamento.getFim().getTime() >= colAfastamento.getInicio().getTime()   && colaboradorAfastamento.getFim().getTime() <= colAfastamento.getFim().getTime())	 )
					return false;
			}else
			{
				if(colAfastamento.getFim() != null && colaboradorAfastamento.getFim() == null && (colaboradorAfastamento.getInicio().getTime() >= colAfastamento.getInicio().getTime() && colaboradorAfastamento.getInicio().getTime() <= colAfastamento.getFim().getTime()))
					return false;
				else
				{	
					if(colAfastamento.getFim() == null && colaboradorAfastamento.getFim() != null && (colaboradorAfastamento.getInicio().getTime() <= colAfastamento.getInicio().getTime() && colaboradorAfastamento.getFim().getTime() >= colAfastamento.getInicio().getTime() ))
						return false;
					else if(colaboradorAfastamento.getInicio().getTime() == colAfastamento.getInicio().getTime())
						return false;
				}
			}
		}

		return true;
	}
	
	public Integer findQtdAfastamentosInss(Long empresaId, Date dataIni, Date dataFim, boolean inss) 
	{
		return getDao().findQtdAfastamentosInss(empresaId, dataIni, dataFim, inss);
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setAfastamentoManager(AfastamentoManager afastamentoManager) {
		this.afastamentoManager = afastamentoManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public Integer getCountAfastamentosImportados() {
		return countAfastamentosImportados;
	}

	public Integer getCountTiposAfastamentosCriados() {
		return countTiposAfastamentosCriados;
	}

	public Collection<ColaboradorAfastamento> findByColaborador(Long colaboradorId) {
		return getDao().findByColaborador(colaboradorId);
	}

	public void setCidManager(CidManager cidManager) {
		this.cidManager = cidManager;
	}

	public Collection<ColaboradorAfastamentoMatriz> montaMatrizResumo(Long empresaId, String[] estabelecimentosCheck, String[] areasCheck, String[] motivosCheck, ColaboradorAfastamento colaboradorAfastamento, char ordenarPor, char totalizarDiasPor, boolean agruparPorArea) throws Exception 
	{
		colaboradorAfastamentos = findRelatorioResumoAfastamentos(empresaId, estabelecimentosCheck, areasCheck, motivosCheck, colaboradorAfastamento);
		Map<Colaborador, Collection<Date>> datasColaboradores = new HashMap<Colaborador, Collection<Date>>();
		Map<Long, Integer> totalDiasColaboradores = new HashMap<Long, Integer>();
		Collection<ColaboradorAfastamentoMatriz> colaboradorAfastamentoMatrizes = new ArrayList<ColaboradorAfastamentoMatriz>();
		
		// distribuicao dos dias caso o afastamento passe de um mes a outro e 
		// a opcao de distribuir os dias nos meses em que ocorreram esteja selecionada
		if (totalizarDiasPor == 'D')
		{
			Collection<ColaboradorAfastamento> colaboradorAfastamentoExtras = new ArrayList<ColaboradorAfastamento>();
			ColaboradorAfastamento novoColaboradorAfastamento;
			Date dataInicio, dataFim;
			
			for (ColaboradorAfastamento colabAfast : colaboradorAfastamentos) 
			{
				if (colabAfast.getInicio() != null && colabAfast.getFim() != null && !DateUtil.mesmoMes(colabAfast.getInicio(), colabAfast.getFim()))
				{
					dataInicio = colabAfast.getInicio();
					colabAfast.setQtdDias(DateUtil.diferencaEntreDatas(dataInicio, DateUtil.getUltimoDiaMes(dataInicio), false) + 1);
					
					while (true) 
					{
						dataInicio = DateUtil.getInicioMesData(DateUtil.incrementaMes(dataInicio, 1));
						if (dataInicio.after(colabAfast.getFim()))
							break;
						
						dataFim = colabAfast.getFim();
						
						if (!DateUtil.mesmoMes(dataInicio, dataFim))
							dataFim = DateUtil.getUltimoDiaMes(dataInicio);
						
						novoColaboradorAfastamento = (ColaboradorAfastamento) colabAfast.clone();
						novoColaboradorAfastamento.setInicio(dataInicio);
						novoColaboradorAfastamento.setFim(dataFim);
						novoColaboradorAfastamento.setQtdAfastamentos(0);
						novoColaboradorAfastamento.setQtdDias(DateUtil.diferencaEntreDatas(dataInicio, dataFim, false) + 1);
						colaboradorAfastamentoExtras.add(novoColaboradorAfastamento);
					}
				}
			}
			
			colaboradorAfastamentos.addAll(colaboradorAfastamentoExtras);
		}
		
		// agrupa meses onde houveram afastamentos para o colaborador
		for (ColaboradorAfastamento colabAfastamento : colaboradorAfastamentos) 
		{
			if (!datasColaboradores.containsKey(colabAfastamento.getColaborador()))
				datasColaboradores.put(colabAfastamento.getColaborador(), new ArrayList<Date>());
			
			datasColaboradores.get(colabAfastamento.getColaborador()).add(DateUtil.getInicioMesData(colabAfastamento.getInicio()));
			
			int totalDiasAcumulados = totalDiasColaboradores.containsKey( colabAfastamento.getColaborador().getId() ) ? totalDiasColaboradores.get(colabAfastamento.getColaborador().getId()) : 0;
			totalDiasColaboradores.put(colabAfastamento.getColaborador().getId(),  totalDiasAcumulados + colabAfastamento.getQtdDias());
		}
		
		// preenche o registro do colaborador com os meses que nao possuem afastamentos
		Date dataAtual = null;
		Colaborador colab = null;
		for (Map.Entry<Colaborador, Collection<Date>> datasColaborador : datasColaboradores.entrySet())
		{
			dataAtual = DateUtil.getInicioMesData(colaboradorAfastamento.getInicio());
			
			while (dataAtual.before(DateUtil.getUltimoDiaMes(colaboradorAfastamento.getFim())))
			{
				if (!datasColaborador.getValue().contains(dataAtual))
				{
					colab = datasColaborador.getKey();
					colaboradorAfastamentos.add(new ColaboradorAfastamento(colab.getId(), colab.getMatricula(), colab.getNome(), colab.getDataAdmissao(), colab.getAreaOrganizacional().getId(), dataAtual, null, null, null));
				}
				
				dataAtual = DateUtil.incrementaMes(dataAtual, 1);
			}
		}

		Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, empresaId);
		areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);
		
		for (ColaboradorAfastamento colabAfastamento: colaboradorAfastamentos)
		{
			// monta a familia da area organizacional na descricao
			if (colabAfastamento.getColaborador().getAreaOrganizacional() != null && colabAfastamento.getColaborador().getAreaOrganizacional().getId() != null)
				colabAfastamento.getColaborador().setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areaOrganizacionals, colabAfastamento.getColaborador().getAreaOrganizacional().getId()));
			
			// configura a quantidade total de dias
			colabAfastamento.setQtdTotalDias( totalDiasColaboradores.get(colabAfastamento.getColaborador().getId()) );
		}
		
		// ordenacao
		Collections.sort((List<ColaboradorAfastamento>) colaboradorAfastamentos, new ColaboradorAfastamentoComparator(ordenarPor, agruparPorArea));
		
		// agrupamento por area organizacional
		ColaboradorAfastamentoMatriz colaboradorAfastamentoMatriz = null;
		if (agruparPorArea)
		{
			AreaOrganizacional area = null;
			
			for (ColaboradorAfastamento colabAfast : colaboradorAfastamentos) 
			{
				if (!colabAfast.getColaborador().getAreaOrganizacional().equals(area))
				{
					area = colabAfast.getColaborador().getAreaOrganizacional();
					
					colaboradorAfastamentoMatriz = new ColaboradorAfastamentoMatriz();
					colaboradorAfastamentoMatriz.setAreaOrganizacionalDescricao(area.getDescricao());
					colaboradorAfastamentoMatriz.setColaboradorAfastamentos(new ArrayList<ColaboradorAfastamento>());
					
					colaboradorAfastamentoMatrizes.add(colaboradorAfastamentoMatriz);
				}
				
				colaboradorAfastamentoMatriz.getColaboradorAfastamentos().add(colabAfast);
			}
		}
		else
		{
			colaboradorAfastamentoMatriz = new ColaboradorAfastamentoMatriz();
			colaboradorAfastamentoMatriz.setColaboradorAfastamentos(colaboradorAfastamentos);
			colaboradorAfastamentoMatrizes.add(colaboradorAfastamentoMatriz);
		}

		return colaboradorAfastamentoMatrizes;
	}

	public Collection<Absenteismo> countAfastamentosByPeriodo(Date dataIni, Date dataFim, Collection<Long> empresaIds, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> cargosIds, Collection<Long> afastamentosIds) 
	{
		return getDao().countAfastamentosByPeriodo(dataIni, dataFim, empresaIds, estabelecimentosIds, areasIds, cargosIds, afastamentosIds);
	}

	public ColaboradorAfastamento findByColaboradorAfastamentoId(Long colaboradorAfastamentoId)
	{
		return getDao().findByColaboradorAfastamentoId(colaboradorAfastamentoId);
	}

	public Collection<ColaboradorAfastamento> getColaboradorAfastamentos() {
		return colaboradorAfastamentos;
	}

	public void setColaboradorAfastamentos(
			Collection<ColaboradorAfastamento> colaboradorAfastamentos) {
		this.colaboradorAfastamentos = colaboradorAfastamentos;
	}
}