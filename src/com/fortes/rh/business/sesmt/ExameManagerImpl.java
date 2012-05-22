package com.fortes.rh.business.sesmt;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.dao.sesmt.ExameDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.dicionario.TipoPessoa;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.Mail;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.webwork.ServletActionContext;

public class ExameManagerImpl extends GenericManagerImpl<Exame, ExameDao> implements ExameManager
{
	private static final ServletActionContext String = null;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private ColaboradorManager colaboradorManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private Mail mail;
	Map<String,Object> parametros = new HashMap<String, Object>();
	
	public Exame findByIdProjection(Long exameId)
	{
		return getDao().findByIdProjection(exameId);
	}

	public Collection<Exame> findAllSelect(Long empresaId)
	{
		return findToList(new String[]{"id", "nome","periodicidade"}, new String[]{"id", "nome","periodicidade"}, new String[]{"empresa.id"}, new Object[]{empresaId}, new String[]{"nome"});
	}

	public Collection<Exame> findByHistoricoFuncao(Long historicoFuncaoId)
	{
		return getDao().findByHistoricoFuncao(historicoFuncaoId);
	}

	public Collection<Exame> populaExames(String[] examesCheck)
	{
		Collection<Exame> exames = new ArrayList<Exame>();

		if(examesCheck != null && examesCheck.length > 0)
		{
			Long examesIds[] = LongUtil.arrayStringToArrayLong(examesCheck);

			Exame exame;
			for (Long exameId: examesIds)
			{
				exame = new Exame();
				exame.setId(exameId);

				exames.add(exame);
			}
		}

		return exames;
	}

	public String[] findBySolicitacaoExame(Long solicitacaoExameId)
	{
		Collection<Long> exameIds = getDao().findBySolicitacaoExame(solicitacaoExameId);
		String[] retorno = new String[exameIds.size()];

		int i=0;
		for (Long exameId : exameIds)
		{
			retorno[i++] = exameId.toString();
		}
		return retorno;
	}

	public Collection<CheckBox> populaCheckBox(Long empresaId)
	{
		try
		{
			Collection<Exame> examesTmp = findAllSelect(empresaId);
			return CheckListBoxUtil.populaCheckListBox(examesTmp, "getId", "getNome");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ArrayList<CheckBox>();
	}

	//TODO BACALHAU, consulta gigante e uns forsss
	public Collection<ExamesPrevistosRelatorio> findRelatorioExamesPrevistos(Long empresaId, Date data, Long[] examesChecks, Long[] estabelecimentosChecks, Long[] areasChecks, Long[] colaboradoresChecks, char agruparPor, boolean imprimirAfastados, boolean imprimirDesligados) throws Exception
	{
		Collection<ExamesPrevistosRelatorio> examesRealizadosAteData = getDao().findExamesPeriodicosPrevistos(empresaId, data, examesChecks, estabelecimentosChecks, areasChecks, colaboradoresChecks, imprimirAfastados, imprimirDesligados);

		Collection<ExamesPrevistosRelatorio> examesAVencer = this.prepararExamesAVencer(data, examesRealizadosAteData);
		
		examesAVencer = this.filtrarApenasExamesVencidos(data, examesRealizadosAteData, examesAVencer);
		
		if (examesAVencer.isEmpty())
			return examesAVencer;

		CollectionUtil<ExamesPrevistosRelatorio> collectionUtil = new CollectionUtil<ExamesPrevistosRelatorio>();
		
		examesAVencer = collectionUtil.sortCollectionDate(examesAVencer, "dataProximoExame", "asc");
		examesAVencer = collectionUtil.sortCollectionStringIgnoreCase(examesAVencer, "colaboradorNome");
		
		if (agruparPor == 'A'){
			examesAVencer = areaOrganizacionalManager.setFamiliaAreas(examesAVencer, empresaId);
			examesAVencer = collectionUtil.sortCollectionStringIgnoreCase(examesAVencer, "areaOrganizacional.nome");			
		} else 	if (agruparPor == 'E')
			examesAVencer = collectionUtil.sortCollectionStringIgnoreCase(examesAVencer, "estabelecimento.nome");			
		
		
		return examesAVencer;
	}

	private Collection<ExamesPrevistosRelatorio> prepararExamesAVencer(Date data, Collection<ExamesPrevistosRelatorio> colecaoExamesRealizadosAteData) 
	{
		Collection<ExamesPrevistosRelatorio> colecaoExamesAVencer = new ArrayList<ExamesPrevistosRelatorio>();
		
		ExamesPrevistosRelatorio examesPrevistosAnterior = null;

		for (ExamesPrevistosRelatorio examesPrevistosRelatorio : colecaoExamesRealizadosAteData)
		{
			Date dataProximoExame = examesPrevistosRelatorio.getDataProximoExame();

			// A consulta recupera todos os exames realizados até a data. 
			// Essa condição filtra apenas os resultados com data do Próximo exame dentro do período.
			if (dataProximoExame.compareTo(data) <= 0)
			{
				boolean adicionar = true;
				if (examesPrevistosAnterior != null)
				{
					if (examesPrevistosAnterior.getColaboradorId().equals(examesPrevistosRelatorio.getColaboradorId())
							&& examesPrevistosAnterior.getExameId().equals(examesPrevistosRelatorio.getExameId()))
					{
						if (examesPrevistosAnterior.getDataProximoExame().compareTo(examesPrevistosRelatorio.getDataProximoExame()) == -1)
						{
							colecaoExamesAVencer.remove(examesPrevistosAnterior);
						}
						else
						{
							adicionar = false;
						}
					}
				}

				examesPrevistosRelatorio.setAdicionar(adicionar);

				if (adicionar)
				{
					colecaoExamesAVencer.add(examesPrevistosRelatorio);
					examesPrevistosAnterior = examesPrevistosRelatorio;
				}
			}
		}
		return colecaoExamesAVencer;
	}
	
	private Collection<ExamesPrevistosRelatorio> filtrarApenasExamesVencidos(Date data, 
							Collection<ExamesPrevistosRelatorio> colecaoExamesRealizadosAteData, Collection<ExamesPrevistosRelatorio> colecaoExamesAVencer) 
	{
		Collection<ExamesPrevistosRelatorio> colecaoResultado = new ArrayList<ExamesPrevistosRelatorio>();

		// Em seguida, são considerados os exames que foram realizados após os filtrados, e que não venceram ainda na data.
		// Neste caso, o exame previamente filtrado é descartado.
		for (ExamesPrevistosRelatorio examesPrevistosRelatorio : colecaoExamesAVencer)
		{
			for (ExamesPrevistosRelatorio examesPrevistos2 : colecaoExamesRealizadosAteData)
			{
				if (examesPrevistosRelatorio.getAdicionar() &&
						!colecaoExamesAVencer.contains(examesPrevistos2)
						&& examesPrevistos2.getColaboradorId().equals(examesPrevistosRelatorio.getColaboradorId())
						&& examesPrevistos2.getExameId().equals(examesPrevistosRelatorio.getExameId()))
				{
					if ((examesPrevistos2.getDataProximoExame().compareTo(data) > 0)
						&& (examesPrevistos2.getDataRealizacaoExame().compareTo(examesPrevistosRelatorio.getDataRealizacaoExame()) > 0))
					{
						examesPrevistosRelatorio.setAdicionar(false);
					}
				}
			}
		}

		// Setando o resultado final (exames vencidos).
		for (ExamesPrevistosRelatorio examesPrevistosRelatorio : colecaoExamesAVencer)
		{
			if (examesPrevistosRelatorio.getAdicionar())
				colecaoResultado.add(examesPrevistosRelatorio);
		}
		return colecaoResultado;
	}

	public Collection<ExamesRealizadosRelatorio> findRelatorioExamesRealizados(Long empresaId, String nomeBusca, Date inicio, Date fim, String motivo, String exameResultado, Long clinicaAutorizadaId, Long[] examesIds, Long[] estabelecimentosIds, Character tipoPessoa) throws ColecaoVaziaException
	{
		Collection<ExamesRealizadosRelatorio> examesRealizados = new ArrayList<ExamesRealizadosRelatorio>();
		
		if (!tipoPessoa.equals(TipoPessoa.CANDIDATO.getChave()))
			examesRealizados.addAll(getDao().findExamesRealizadosColaboradores(empresaId, nomeBusca, inicio, fim, motivo, exameResultado, clinicaAutorizadaId, examesIds, estabelecimentosIds));

		if (!tipoPessoa.equals(TipoPessoa.COLABORADOR.getChave()))
			examesRealizados.addAll(getDao().findExamesRealizadosCandidatos(empresaId, nomeBusca, inicio, fim, motivo, exameResultado, clinicaAutorizadaId, examesIds, estabelecimentosIds));
		
		if (examesRealizados == null || examesRealizados.isEmpty())
			throw new ColecaoVaziaException("Não existem exames realizados para os filtros informados.");
		
		CollectionUtil<ExamesRealizadosRelatorio> collectionUtil = new CollectionUtil<ExamesRealizadosRelatorio>();
		
		examesRealizados = collectionUtil.sortCollectionDate(examesRealizados, "data", "asc");
		examesRealizados = collectionUtil.sortCollectionStringIgnoreCase(examesRealizados, "nome");
		examesRealizados = collectionUtil.sortCollectionStringIgnoreCase(examesRealizados, "clinicaNome");
		examesRealizados = collectionUtil.sortCollectionStringIgnoreCase(examesRealizados, "estabelecimentoNome");
		
		return examesRealizados;
	}

	public Collection<ExamesRealizadosRelatorio> findRelatorioExamesRealizadosResumido(Long empresaId, Date dataInicio, Date dataFim, ClinicaAutorizada clinicaAutorizada, Long[] examesIds) throws ColecaoVaziaException
	{
		Collection<ExamesRealizadosRelatorio> examesRealizados = getDao().findExamesRealizadosRelatorioResumido(empresaId, dataInicio, dataFim, clinicaAutorizada, examesIds);
		
		if (examesRealizados == null || examesRealizados.isEmpty())
			throw new ColecaoVaziaException("Não existem exames realizados para os filtros informados.");
		
		return examesRealizados;
	}
	
	public void enviaLembreteExamesPrevistos(Collection<Empresa> empresas) 
	{
		gerenciadorComunicacaoManager.enviaLembreteExamesPrevistos(empresas);
	}
	
	private JasperReport compileReport(String reportPath, InputStream reportInputStream) throws JRException {
		JasperReport jasperReport;// = JasperCompileManager.compileReport(systemId.replaceAll("jasper", "jrxml"));
		if (reportInputStream == null)
			jasperReport = JasperCompileManager.compileReport(reportPath.replaceAll("jasper", "jrxml"));
		else
			jasperReport = JasperCompileManager.compileReport(reportInputStream);
		return jasperReport;
	}
	
	public Collection<Exame> findPriorizandoExameRelacionado(Long empresaId, Long colaboradorId) {
		return getDao().findPriorizandoExameRelacionado(empresaId, colaboradorId);
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}
	
	public void setMail(Mail mail)
	{
		this.mail = mail;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}
	
	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public Integer count(Long empresaId, Exame exame) {
		return getDao().getCount(empresaId, exame);
	}

	public Collection<Exame> find(Integer page, Integer pagingSize, Long empresaId, Exame exame) 
	{
		return getDao().find(page, pagingSize, empresaId, exame);
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}
	
}