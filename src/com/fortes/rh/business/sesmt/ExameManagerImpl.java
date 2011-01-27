package com.fortes.rh.business.sesmt;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.zip.ZipOutputStream;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.dao.sesmt.ExameDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.Zip;
import com.fortes.web.tags.CheckBox;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ExameManagerImpl extends GenericManagerImpl<Exame, ExameDao> implements ExameManager
{
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private Mail mail;
	
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

	public Collection<ExamesPrevistosRelatorio> findRelatorioExamesPrevistos(Long empresaId, Date data, Long[] examesChecks, Long[] estabelecimentosChecks, Long[] areasChecks, Long[] colaboradoresChecks, boolean agruparPorArea, boolean imprimirAfastados) throws Exception
	{
		Collection<ExamesPrevistosRelatorio> examesRealizadosAteData = getDao().findExamesPeriodicosPrevistos(empresaId, data, examesChecks, estabelecimentosChecks, areasChecks, colaboradoresChecks, imprimirAfastados);

		Collection<ExamesPrevistosRelatorio> examesAVencer = this.prepararExamesAVencer(data, examesRealizadosAteData);
		
		examesAVencer = this.filtrarApenasExamesVencidos(data, examesRealizadosAteData, examesAVencer);
		
		if (examesAVencer.isEmpty())
			throw new ColecaoVaziaException("Não há exames previstos.");

		CollectionUtil<ExamesPrevistosRelatorio> collectionUtil = new CollectionUtil<ExamesPrevistosRelatorio>();
		
		examesAVencer = collectionUtil.sortCollectionDate(examesAVencer, "dataProximoExame", "asc");
		examesAVencer = collectionUtil.sortCollectionStringIgnoreCase(examesAVencer, "colaboradorNome");
		
		if (agruparPorArea)
		{
			examesAVencer = areaOrganizacionalManager.setFamiliaAreas(examesAVencer, empresaId);
			examesAVencer = collectionUtil.sortCollectionStringIgnoreCase(examesAVencer, "areaOrganizacional.nome");			
		}
		
		
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

	public Exame getExameAso()
	{
		ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
		return parametrosDoSistema.getExame();
	}

	public Collection<ExamesRealizadosRelatorio> findRelatorioExamesRealizados(Long empresaId, String nomeBusca, Date inicio, Date fim, String motivo, String exameResultado, Long clinicaAutorizadaId, Long[] examesIds, Long[] estabelecimentosIds, String vinculo) throws ColecaoVaziaException
	{
		Collection<ExamesRealizadosRelatorio> examesRealizados = getDao().findExamesRealizados(empresaId, nomeBusca, inicio, fim, motivo, exameResultado, clinicaAutorizadaId, examesIds, estabelecimentosIds, vinculo);
		
		if (examesRealizados == null || examesRealizados.isEmpty())
			throw new ColecaoVaziaException("Não existem exames realizados para os filtros informados.");
		
		CollectionUtil<ExamesRealizadosRelatorio> collectionUtil = new CollectionUtil<ExamesRealizadosRelatorio>();
		
		examesRealizados = collectionUtil.sortCollectionDate(examesRealizados, "data", "asc");
		examesRealizados = collectionUtil.sortCollectionStringIgnoreCase(examesRealizados, "nome");
		examesRealizados = collectionUtil.sortCollectionStringIgnoreCase(examesRealizados, "clinicaNome");
		
		return examesRealizados;
	}
	
	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void enviaLembreteExamesPrevistos(Collection<Empresa> empresas) 
	{
		//java.io.File xmlFile = null;
		//java.io.File zipFile = null;

		try
		{
			// Cria o arquivo xml
//			String fileName = "candidato" + Calendar.getInstance().getTimeInMillis() + ".xml";
//			xmlFile = new java.io.File(fileName);
//			FileOutputStream outputStream = new FileOutputStream(xmlFile);
//			String encoding = "UTF-8";
//			XStream stream = new XStream(new DomDriver(encoding));
//
//			outputStream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n".getBytes());
//			stream.toXML(candidatos, outputStream);
//			outputStream.flush();
//			outputStream.close();
//
//			// Compacta o arquivo
//			ZipOutputStream zipOutputStream = new Zip().compress(new java.io.File[] { xmlFile }, fileName, ".fortesrh");// cria o arquivo candidatos.zip
//			zipOutputStream.close();
//
//			// Envia o arquivo por email
//			zipFile = new java.io.File(fileName + ".fortesrh");

			String body = "TESTE:<br>"+
						  "Exames previstos <br>";
			
			//String subject = "Exames previstos no período: " + DateUtil.formataDiaMesAno(inicioMes) + " a " + DateUtil.formataDiaMesAno(fimMes);
			String subject = "Exames previstos no período: TODO a TODO";
			
			for (Empresa empresa : empresas) 
			{
				mail.send(empresa, subject, body.toString(), null, empresa.getEmailRespRH());
				//mail.send(empresa, assunto, body, new java.io.File[] { zipFile }, anuncioManager.montaEmails(emailAvulso, empresasCheck));
			}
			
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			//throw e;
		}
		finally
		{
//			if(zipFile != null && zipFile.exists())
//			{
//				zipFile.delete();
//			}
//
//			if(xmlFile != null && xmlFile.exists())
//			{
//				xmlFile.delete();
//			}
		}
	}

	public void setMail(Mail mail)
	{
		this.mail = mail;
	}
	
}