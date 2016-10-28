package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.beanutils.BeanComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.dao.geral.GastoEmpresaDao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Gasto;
import com.fortes.rh.model.geral.GastoEmpresa;
import com.fortes.rh.model.geral.GastoEmpresaItem;
import com.fortes.rh.model.geral.GrupoGasto;
import com.fortes.rh.model.geral.relatorio.GastoRelatorio;
import com.fortes.rh.model.geral.relatorio.GastoRelatorioItem;
import com.fortes.rh.model.geral.relatorio.TotalGastoRelatorio;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.ComparatorString;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.MathUtil;
import com.opensymphony.xwork.ActionContext;

@Component
@SuppressWarnings({ "unchecked", "rawtypes" })
public class GastoEmpresaManagerImpl extends GenericManagerImpl<GastoEmpresa, GastoEmpresaDao> implements GastoEmpresaManager
{
	private GastoEmpresaItemManager gastoEmpresaItemManager;
	private ColaboradorManager colaboradorManager;
	private HistoricoColaboradorBeneficioManager historicoColaboradorBeneficioManager;
	private GastoManager gastoManager;
	private TurmaManager turmaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private AbstractPlatformTransactionManager transactionManager;
	private Mail mail;

	@Autowired
	GastoEmpresaManagerImpl(GastoEmpresaDao dao) {
		setDao(dao);
	}

	public Collection<GastoRelatorio> filtroRelatorio(LinkedHashMap parametros) throws Exception
	{
		List objetosGastos = new LinkedList();
		List objetosBeneficios = new LinkedList();
		List objetosTreinamentos = new LinkedList();
		Collection<GastoRelatorio> retorno;
		Collection<GastoRelatorio> gastos;
		Collection<GastoRelatorio> beneficios;
		Collection<GastoRelatorio> treinamentos;

		if(parametros.get("colaborador") != null)
		{
			objetosGastos = getDao().filtroRelatorioByColaborador(parametros);
			objetosBeneficios = historicoColaboradorBeneficioManager.filtroRelatorioByColaborador(parametros);
			objetosTreinamentos = turmaManager.filtroRelatorioByColaborador(parametros);
		}
		else
		{
			objetosGastos = getDao().filtroRelatorioByAreas(parametros);
			objetosBeneficios = historicoColaboradorBeneficioManager.filtroRelatorioByAreas(parametros);
			objetosTreinamentos = turmaManager.filtroRelatorioByAreas(parametros);
		}

		gastos = agruparGastosByArea(objetosGastos, parametros);
		beneficios = agruparBeneficiosByArea(objetosBeneficios, parametros);
		treinamentos = agruparTreinamentosByArea(objetosTreinamentos, parametros);

		retorno = mesclarGastosByArea(gastos, beneficios, (Long) parametros.get("empresaId"));
		retorno = mesclarGastosByArea(retorno, treinamentos, (Long) parametros.get("empresaId"));

        Comparator comp = new BeanComparator("areaOrganizacional.descricao", new ComparatorString());
        Collections.sort((List) retorno, comp);

		return retorno;
	}

	private Collection<GastoRelatorio> agruparGastosByArea(List objetosGastos, LinkedHashMap parametros)
	{
		Collection<GastoRelatorio> retorno = new LinkedList<GastoRelatorio>();
		AreaOrganizacional areaAnterior = null;
		GastoRelatorio gastoRelatorio = null;

		for (Iterator<Object[]> it = objetosGastos.iterator(); it.hasNext();)
		{
			Object[] array = it.next();

			AreaOrganizacional areaOrganizacional = new AreaOrganizacional();
			areaOrganizacional.setId((Long) array[5]);
			areaOrganizacional.setNome((String) array[0]);

			if(areaAnterior == null || !areaAnterior.getId().equals(areaOrganizacional.getId()) )
			{
				areaAnterior = areaOrganizacional;
				gastoRelatorio = new GastoRelatorio();
				gastoRelatorio.setAreaOrganizacional(areaOrganizacional);
				GastoRelatorioItem gastoRelatorioItem = getGastoRelatorioItemArea(array);

				gastoRelatorio.setGastoRelatorioItems(multiplicarGastoRelatorioItem(gastoRelatorioItem, (Date)parametros.get("dataIni"), (Date)parametros.get("dataFim")));

				retorno.add(gastoRelatorio);
			}
			else
			{
				GastoRelatorioItem gastoRelatorioItem = getGastoRelatorioItemArea(array);

				gastoRelatorio.getGastoRelatorioItems().addAll((multiplicarGastoRelatorioItem(gastoRelatorioItem, (Date)parametros.get("dataIni"), (Date)parametros.get("dataFim"))));
			}
		}

		return retorno;

	}

	private GastoRelatorioItem getGastoRelatorioItemArea(Object[] array)
	{
		GastoRelatorioItem gastoRelatorioItem = new GastoRelatorioItem();

		Gasto gasto = new Gasto();
		gasto.setNome((String) array[1]);

		GrupoGasto grupoGasto = new GrupoGasto();
		grupoGasto.setNome((String) array[2]);
		gasto.setGrupoGasto(grupoGasto);

		gastoRelatorioItem.setGasto(gasto);
		gastoRelatorioItem.setMesAno((Date) array[3]);
		gastoRelatorioItem.setTotal((Double) array[4]);
		return gastoRelatorioItem;
	}

	private Collection<GastoRelatorio> agruparBeneficiosByArea(List objetosBeneficios, LinkedHashMap parametros)
	{
		Collection<GastoRelatorio> retorno = new LinkedList<GastoRelatorio>();
		AreaOrganizacional areaAnterior = null;
		GastoRelatorio gastoRelatorio = null;

		for (Iterator<Object[]> it = objetosBeneficios.iterator(); it.hasNext();)
		{
			Object[] array = it.next();

			AreaOrganizacional areaOrganizacional = new AreaOrganizacional();
			areaOrganizacional.setId((Long) array[0]);
			areaOrganizacional.setNome((String) array[7]);

			if(areaAnterior == null || !areaAnterior.getId().equals(areaOrganizacional.getId()) )
			{
				areaAnterior = areaOrganizacional;
				gastoRelatorio = new GastoRelatorio();
				gastoRelatorio.setAreaOrganizacional(areaOrganizacional);

				GastoRelatorioItem gastoRelatorioItem = getGastoBeneficioByArea(array);
				gastoRelatorio.setGastoRelatorioItems(multiplicarGastoRelatorioItem(gastoRelatorioItem, (Date)parametros.get("dataIni"), (Date)parametros.get("dataFim")));

				retorno.add(gastoRelatorio);
			}
			else
			{
				GastoRelatorioItem gastoRelatorioItem = getGastoBeneficioByArea(array);
				gastoRelatorio.getGastoRelatorioItems().addAll((multiplicarGastoRelatorioItem(gastoRelatorioItem, (Date)parametros.get("dataIni"), (Date)parametros.get("dataFim"))));
			}
		}

		return retorno;

	}

	private GastoRelatorioItem getGastoBeneficioByArea(Object[] array)
	{
		Gasto gasto = new Gasto();
		gasto.setNome((String) array[1]);

		GrupoGasto grupoGasto = new GrupoGasto();
		grupoGasto.setNome("Benefícios");
		gasto.setGrupoGasto(grupoGasto);

		GastoRelatorioItem gastoRelatorioItem = new GastoRelatorioItem();
		gastoRelatorioItem.setGasto(gasto);
		gastoRelatorioItem.setMesAno((Date) array[2]);
		gastoRelatorioItem.setTotal((Double) array[6]);
		return gastoRelatorioItem;
	}

	private Collection<GastoRelatorio> agruparTreinamentosByArea(List objetosTreinamentos, LinkedHashMap parametros)
	{
		Collection<GastoRelatorio> retorno = new LinkedList<GastoRelatorio>();
		AreaOrganizacional areaAnterior = null;
		GastoRelatorio gastoRelatorio = null;

		for (Iterator<Object[]> it = objetosTreinamentos.iterator(); it.hasNext();)
		{
			Object[] array = it.next();

			AreaOrganizacional areaOrganizacional = new AreaOrganizacional();
			areaOrganizacional.setId((Long) array[5]);
			areaOrganizacional.setNome((String) array[0]);

			if(areaAnterior == null || !areaAnterior.getId().equals(areaOrganizacional.getId()) )
			{
				areaAnterior = areaOrganizacional;

				gastoRelatorio = new GastoRelatorio();

				gastoRelatorio.setAreaOrganizacional(areaOrganizacional);

				GastoRelatorioItem gastoRelatorioItem = getGastoRelatorioItem(array);

				gastoRelatorio.setGastoRelatorioItems(multiplicarGastoRelatorioItem(gastoRelatorioItem, (Date)parametros.get("dataIni"), (Date)parametros.get("dataFim")));

				retorno.add(gastoRelatorio);
			}
			else
			{
				GastoRelatorioItem gastoRelatorioItem = getGastoRelatorioItem(array);

				gastoRelatorio.getGastoRelatorioItems().addAll((multiplicarGastoRelatorioItem(gastoRelatorioItem, (Date)parametros.get("dataIni"), (Date)parametros.get("dataFim"))));
			}
		}

		return retorno;
	}

	private GastoRelatorioItem getGastoRelatorioItem(Object[] array)
	{
		GastoRelatorioItem gastoRelatorioItem = new GastoRelatorioItem();

		Gasto gasto = new Gasto();
		gasto.setNome((String) array[1]);

		GrupoGasto grupoGasto = new GrupoGasto();
		grupoGasto.setNome("Treinamentos");
		gasto.setGrupoGasto(grupoGasto);

		gastoRelatorioItem.setGasto(gasto);
		gastoRelatorioItem.setMesAno(DateUtil.getInicioMesData((Date) array[2]));

		if ((Integer) array[4]>0)
			gastoRelatorioItem.setTotal((Double) array[3]/(Integer) array[4]);

		return gastoRelatorioItem;
	}

	private Collection<GastoRelatorio> mesclarGastosByArea(Collection<GastoRelatorio> col1, Collection<GastoRelatorio> col2, Long empresaId) throws Exception
	{
		if(col1.isEmpty())
			return col2;
		if(col2.isEmpty())
			return col1;

		Collection<GastoRelatorio> retorno = new LinkedList<GastoRelatorio>();

		Map<AreaOrganizacional, Collection<GastoRelatorioItem>> mesclado = new HashMap<AreaOrganizacional, Collection<GastoRelatorioItem>>();

		mesclado = mapearGastos(col1, mesclado);
		mesclado = mapearGastos(col2, mesclado);

		Collection<AreaOrganizacional> areaOrganizacionals = mesclado.keySet();

		Collection<AreaOrganizacional> areasTmp = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, empresaId);
		areasTmp = areaOrganizacionalManager.montaFamilia(areasTmp);
		areaOrganizacionals = areaOrganizacionalManager.getDistinctAreaMae(areasTmp, areaOrganizacionals);
		CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
		areaOrganizacionals = cu1.sortCollectionStringIgnoreCase(areaOrganizacionals, "descricao");

		GastoRelatorio gastoRelatorioTmp;

		for (AreaOrganizacional areaOrganizacional : areaOrganizacionals)
		{
			if(mesclado.get(areaOrganizacional).size() > 0)
			{
				gastoRelatorioTmp = new GastoRelatorio();
				gastoRelatorioTmp.setAreaOrganizacional(areaOrganizacional);
				gastoRelatorioTmp.setGastoRelatorioItems(mesclado.get(areaOrganizacional));

				retorno.add(gastoRelatorioTmp);
			}
		}

		return retorno;
	}

	public Map<AreaOrganizacional, Collection<GastoRelatorioItem>> mapearGastos(Collection<GastoRelatorio> col1, Map<AreaOrganizacional, Collection<GastoRelatorioItem>> mesclado)
	{
		Collection<GastoRelatorioItem> gastoRelatorioItemTemps;
		for (GastoRelatorio col : col1)
		{
			AreaOrganizacional areaOrganizacional = col.getAreaOrganizacional();
			gastoRelatorioItemTemps = new ArrayList<GastoRelatorioItem>();

			if (mesclado.containsKey(areaOrganizacional))
			{
				gastoRelatorioItemTemps = mesclado.get(areaOrganizacional);
				gastoRelatorioItemTemps.addAll(col.getGastoRelatorioItems());
				mesclado.put(areaOrganizacional, gastoRelatorioItemTemps);
			}
			else
			{
				mesclado.put(areaOrganizacional, col.getGastoRelatorioItems());
			}
		}

		return mesclado;
	}


	public void clonarGastosPorColaborador(String dataClone, GastoEmpresa gastoEmpresa)
	{
		GastoEmpresa gastoEmp = getDao().findById(gastoEmpresa.getId());
		GastoEmpresa gastoEmpresaDolly = new GastoEmpresa();

		gastoEmpresaDolly.setColaborador(gastoEmp.getColaborador());
		gastoEmpresaDolly.setMesAno(DateUtil.criarDataMesAno(dataClone));
		gastoEmpresaDolly.setEmpresa(gastoEmp.getEmpresa());

		Collection<GastoEmpresaItem> itensDolly = new ArrayList<GastoEmpresaItem>();
		GastoEmpresaItem itemDolly = null;

		for (GastoEmpresaItem item : gastoEmp.getGastoEmpresaItems())
		{
			if(!item.getGasto().isNaoImportar())
			{
				itemDolly = new GastoEmpresaItem();
				itemDolly.setGasto(item.getGasto());
				itemDolly.setValor(item.getValor());
				itemDolly.setGastoEmpresa(item.getGastoEmpresa());
				itensDolly.add(itemDolly);
			}
		}

		gastoEmpresaDolly.setGastoEmpresaItems(itensDolly);
		gastoEmpresaDolly = save(gastoEmpresaDolly);

		saveDetalhes(itensDolly, gastoEmpresaDolly);

	}

	private void saveDetalhes(Collection<GastoEmpresaItem> itens, GastoEmpresa gastoEmpresa)
	{
		for (GastoEmpresaItem i : itens)
		{
			i.setId(null);
			i.setGastoEmpresa(gastoEmpresa);
			gastoEmpresaItemManager.save(i);
		}
	}

	public void importarGastosAC(Empresa empresa, String[] gastos, Date data) throws Exception
	{
		Map<String, Colaborador> listaColaboradores = new HashMap<String, Colaborador>();
		Map<String, Gasto> listaGastos = new HashMap<String, Gasto>();
		Map<String, GastoEmpresa> listaColaboradorGastoEmpresa = new HashMap<String, GastoEmpresa>();

		StringBuilder log = new StringBuilder();

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try{
			for (int i = 0; i < gastos.length; i++)
			{
				StringTokenizer str   = new StringTokenizer(gastos[i],"|");
				String codColaborador = str.nextToken();
				String codEvento      = str.nextToken();
				String valor		  = str.nextToken();

				Colaborador colaborador = null;
				Gasto gasto = null;
				GastoEmpresa gastoEmpresa = null;
				Collection<Gasto> gasts;

				if (listaColaboradores.containsKey(codColaborador))
				{
					colaborador = listaColaboradores.get(codColaborador);
				}
				else
				{
					colaborador = colaboradorManager.findByCodigoAC(codColaborador, empresa);
					if (colaborador == null || colaborador.getCodigoAC() == null || colaborador.getCodigoAC().equals(""))
					{
						log.append("Colaborador código " + codColaborador + " não localizado.<br>");
						continue;
					}
					else
					{
						listaColaboradores.put(codColaborador, colaborador);

						gastoEmpresa = new GastoEmpresa();
						gastoEmpresa.setColaborador(colaborador);
						gastoEmpresa.setMesAno(data);
						gastoEmpresa.setEmpresa(empresa);
						getDao().save(gastoEmpresa);

						listaColaboradorGastoEmpresa.put(codColaborador, gastoEmpresa);
					}
				}

				if (listaGastos.containsKey(codEvento))
				{
					gasto = listaGastos.get(codEvento);
				}
				else
				{
					gasts = gastoManager.find(new String[]{"codigoAc"}, new Object[]{codEvento});
					if (gasts.isEmpty())
					{
						log.append("Gasto código " + codEvento + " não localizado.<br>");
						continue;
					}
					else
					{
						gasto = (Gasto) gasts.toArray()[0];
						listaGastos.put(codEvento, gasto);
					}
				}

				GastoEmpresaItem gastoEmpresaItem = new GastoEmpresaItem();
				gastoEmpresaItem.setGasto(gasto);
				gastoEmpresaItem.setGastoEmpresa(listaColaboradorGastoEmpresa.get(codColaborador));
				gastoEmpresaItem.setValor(new Double(valor));

				gastoEmpresaItemManager.save(gastoEmpresaItem);
			}

			//enviar email, cara do parametros do sistema
			if(log.length() > 0)
			{
				try
				{
					enviarEmail(log);
				}
				catch (MessagingException e)
				{
					System.out.print("Erro ao enviar email: ");
					e.printStackTrace();
				}
			}

			transactionManager.commit(status);
		}
		catch (Exception e)
		{
			transactionManager.rollback(status);
			throw e;
		}
	}

	public void enviarEmail(StringBuilder log) throws AddressException, MessagingException
	{
		Empresa empresa = SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession());

		StringBuilder corpo = new StringBuilder();
		corpo.append("Houve erro na sincronização com o sistema Fortes Pessoal.<br><br>");
		corpo.append(log);

		mail.send(empresa, "[RH] Erro na sincronização", corpo.toString(), null, empresa.getEmailRespSetorPessoal());
	}


	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setGastoManager(GastoManager gastoManager)
	{
		this.gastoManager = gastoManager;
	}

	public void setGastoEmpresaItemManager(GastoEmpresaItemManager gastoEmpresaItemManager)
	{
		this.gastoEmpresaItemManager = gastoEmpresaItemManager;
	}

	public void setMail(Mail mail)
	{
		this.mail = mail;
	}

	public void setTransactionManager(AbstractPlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public Integer getCount(Long empresaId)
	{
		return getDao().getCount(empresaId);
	}

	public Collection<GastoEmpresa> findAllList(int page, int pagingSize, Long empresaId)
	{
		return getDao().findAllList(page, pagingSize, empresaId);
	}

	public void setHistoricoColaboradorBeneficioManager(HistoricoColaboradorBeneficioManager historicoColaboradorBeneficioManager)
	{
		this.historicoColaboradorBeneficioManager = historicoColaboradorBeneficioManager;
	}

	public void setTurmaManager(TurmaManager turmaManager)
	{
		this.turmaManager = turmaManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public Collection<GastoRelatorioItem> multiplicarGastoRelatorioItem(GastoRelatorioItem gastoRelatorioItem, Date dataIni, Date dataFim)
	{
		Collection<GastoRelatorioItem> retorno = new ArrayList<GastoRelatorioItem>();
		retorno.add(gastoRelatorioItem);

		if(dataIni != null && dataFim != null)
		{
			dataIni = DateUtil.getInicioMesData(dataIni);
			dataFim = DateUtil.getUltimoDiaMes(dataFim);
			Date dataAnterior = dataIni;

			int meses = (int) MathUtil.round((DateUtil.diferencaEntreDatas(dataIni, dataFim, false) / 30),1);

			for (int i = 0; i < meses; i++)
			{
				GastoRelatorioItem gastoRelatorioItemClone = new GastoRelatorioItem();
				gastoRelatorioItemClone.setGasto(gastoRelatorioItem.getGasto());
				gastoRelatorioItemClone.setTotal(0D);
				gastoRelatorioItemClone.setMesAno(dataAnterior);

				dataAnterior = DateUtil.setaMesPosterior(dataAnterior);

				retorno.add(gastoRelatorioItemClone);
			}
		}

		return retorno;
	}

	public Map<Date,Double> totalizarInvestimentos(Collection<GastoRelatorio> gastoRelatorios)
	{
		TreeMap<Date,Double> retorno = new TreeMap<Date, Double>();

		for (GastoRelatorio gastoRelatorio : gastoRelatorios)
		{
			for (GastoRelatorioItem item : gastoRelatorio.getGastoRelatorioItems())
			{
				if(!retorno.containsKey(item.getMesAno()))
					retorno.put(item.getMesAno(), item.getTotal());
				else
					retorno.put(item.getMesAno(), item.getTotal()+(Double)retorno.get(item.getMesAno()));
			}
		}
		return retorno;
	}

	public Collection<TotalGastoRelatorio> getTotalInvestimentos(Collection<GastoRelatorio> gastoRelatorios)
	{
		Collection<TotalGastoRelatorio> retorno = new ArrayList<TotalGastoRelatorio>();
		Map<Date,Double> totais = totalizarInvestimentos(gastoRelatorios);
		Set<Date> datas = totais.keySet();
		Double totalGeral = 0D;

		for (Iterator iter = datas.iterator(); iter.hasNext();)
		{
			Date data = (Date) iter.next();
			TotalGastoRelatorio totalGastoRelatorioTmp = new TotalGastoRelatorio();
			totalGastoRelatorioTmp.setMesAno(data);
			totalGastoRelatorioTmp.setTotal(totais.get(data));
			totalGeral += totais.get(data);
			retorno.add(totalGastoRelatorioTmp);
		}

		TotalGastoRelatorio totalGastoRelatorioTG = new TotalGastoRelatorio();
		totalGastoRelatorioTG.setMesAno(null);
		totalGastoRelatorioTG.setTotal(totalGeral);
		retorno.add(totalGastoRelatorioTG);

		return retorno;
	}
}