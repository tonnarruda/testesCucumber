package com.fortes.rh.util;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

@SuppressWarnings("deprecation")
public class DateUtil
{
	private static final String DATA_VAZIA = "  /  /    ";

	/**
	 * Formata mês e ano baseado numa Date()
	 *
	 * @param Date() data
	 * @return data no formato MM/yyyy
	 */
	public static String formataMesAno(Date data)
	{
		if (data == null)
			return  "";
		
		SimpleDateFormat formatar = new SimpleDateFormat("MM/yyyy");
		String dataMesAno = formatar.format(data);

		return dataMesAno;
	}

	/**
	 * Formata ano e mês baseado numa Date()
	 *
	 * @param Date() data
	 * @return data no formato yyyyMM
	 */
	public static String formataAnoMes(Date data)
	{
		if (data == null)
			return  "";
		
		SimpleDateFormat formatar = new SimpleDateFormat("yyyyMM");
		String dataAnoMes = formatar.format(data);

		return dataAnoMes;
	}
	
	/**
	 * Formata dia, mês e ano baseado numa Date()
	 *
	 * @param Date() data
	 * @return data no formato dd/MM/yyyy
	 */
	public static String formataDiaMesAno(Date data)
	{
		if(data == null)
			return "";

		SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy");
		String dataDiaMesAno = formatar.format(data);

		return dataDiaMesAno;
	}

	public static String formataDiaMesAnoTime(Date data)
	{
		if(data == null)
			return "";
		
		SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatar.format(data);
	}

	public static String formataAnoMesDia(Date data)
	{
		if(data == null)
			return "";
		
		SimpleDateFormat formatar = new SimpleDateFormat("yyyy-MM-dd");
		String dataDiaMesAno = formatar.format(data);
		
		return dataDiaMesAno;
	}
	
	/**
	 * Formata uma Date() baseado em uma mascara
	 *
	 * @param Date data, String mascara
	 * @return data no formato enviado
	 */
	public static String formataDate(Date data, String mascara)
	{
		if (!(data != null))
			return "";
		
		SimpleDateFormat formatar = new SimpleDateFormat(mascara);
		String dataMesAno = formatar.format(data);

		return dataMesAno;
	}

	public static String formataDataExtenso(Date data)
	{
		Locale local = new Locale("pt","BR");
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", local);
		return dateFormat.format(data);
	}

	/**
	 * Recebe uma String 00/0000 e transforma em 01/00/0000
	 *
	 * @param data
	 * @return GregorianCalendar
	 * @throws Exception
	 */
	public static GregorianCalendar stringToGregorian(String data) throws Exception{

		if(data.length() < 7){
			throw new Exception("Data com formato invalido");
		}

		int ano = Integer.parseInt(data.substring(3));
		int mes = Integer.parseInt(data.substring(0,2)) - 1;
		return new GregorianCalendar(ano, mes, 1);

	}

	public static boolean equals(Date data1, Date data2)
	{
		return ((data1.getDate() == data2.getDate()) && (data1.getMonth() == data2.getMonth()) && (data1.getYear() == data2.getYear()));
	}
	
	public static boolean equalsMesAno(Date data1, Date data2)
	{
		Calendar calendar1 = Calendar.getInstance(); 
		calendar1.setTime(data1);
		Calendar calendar2 = Calendar.getInstance(); 
		calendar2.setTime(data2);
		
		return  calendar1.get(MONTH) == calendar2.get(MONTH) && calendar1.get(YEAR) == calendar2.get(YEAR);
	}

	public static String getNomeMes(int mes)
	{
		String[] nomes = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
		return nomes[mes];
	}

	public static String getNomeDiaSemana(int diaSemana)
	{
		String[] nomes = {"Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"};
		return nomes[diaSemana];
	}

	public static String getNomeMesCompleto(int mes)
	{
		String[] nomes = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
		return nomes[mes-1];
	}
	
	public static Map<Integer, String> getMeses() 
	{
		Map<Integer, String> meses = new HashMap<Integer, String>();
		meses.put(1, "Janeiro");
		meses.put(2, "Fevereiro");
		meses.put(3, "Março");
		meses.put(4, "Abril");
		meses.put(5, "Maio");
		meses.put(6, "Junho");
		meses.put(7, "Julho");
		meses.put(8, "Agosto");
		meses.put(9, "Setembro");
		meses.put(10, "Outubro");
		meses.put(11, "Novembro");
		meses.put(12, "Dezembro");
		
		return meses;
	}
	
	public static Map<Integer, String> getMesesComoOpcoesParaSelect() 
	{
		Map<Integer, String> meses = new HashMap<Integer, String>();
		meses.put(0, "Todos");
		meses.putAll(getMeses());
		
		return meses;
	}

	/**
	 * @author Francisco Barroso
	 * @param String dataMesAno, no formato mm/aaaa
	 * @return Date no formato 01/mm/aaaa
	 */
	public static Date criarDataMesAno(String dataMesAno)
	{
		String ano = "0";
		String mes = "1";
		int barra = dataMesAno.indexOf('/');
		if(barra != -1)
		{
			mes = dataMesAno.substring(0 , barra);
			ano = dataMesAno.substring(barra + 1 , dataMesAno.length());
		}

		Date currentDate = new Date();
		currentDate.setDate(1);
		currentDate.setMonth(Integer.parseInt(mes) - 1);
		currentDate.setYear(Integer.parseInt(ano) - 1900);

		return currentDate;
	}
	
	public static Date criarDataDiaMesAno(String dataDiaMesAno)
	{
		String[] parteData = dataDiaMesAno.split("/");
		
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(Integer.parseInt(parteData[2]), Integer.parseInt(parteData[1])-1, Integer.parseInt(parteData[0]));

		return c.getTime();
	}
	
	public static Date criarDataMesAno(Date data)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();
	}

	public static Date criarDataMesAno(int dia, int mes, int ano)
	{
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(ano, mes - 1, dia);
		return c.getTime();
	}

	@Deprecated
	public static Date criarAnoMesDia(int ano, int mes, int dia)
	{
		return criarDataMesAno(dia, mes, ano);
	}

	/**
	 * @author Francisco Barroso / Gustavo Fortes
	 * @param Date dataNascimento
	 * @return int idade
	 */
	public static int calcularIdade(Date dataNascimento, Date dataBase)
	{
		if(dataNascimento == null)
			return 0;

		int idade = 0;

		idade =  dataBase.getYear() - dataNascimento.getYear();

		if(dataBase.getMonth() < dataNascimento.getMonth())
			idade--;
		else if(dataBase.getMonth() == dataNascimento.getMonth() && dataBase.getDate() < dataNascimento.getDate())
			idade--;

		return idade;
	}

	public static String getIntervaloEntreDatas(Date startDate, Date endDate)
	{
		if (startDate == null)
			return "";
		
		if (endDate == null)
			endDate = new Date();
		else if (startDate.after(endDate))
			return "(Data inicial maior que data final!)";

		long diferencaEmMilisegundos = endDate.getTime() - startDate.getTime();
		long diferencaEmDias = diferencaEmMilisegundos/1000/60/60/24;
		
		if (diferencaEmDias <= 29) {
			if (diferencaEmDias == 0 || diferencaEmDias == 1) 
				return "(1 dia)";
			else 
				return "("+diferencaEmDias + " dias)";
		}
		return getIntervalDateString(startDate, endDate);
	}
	
	public static String getIntervalDateString(Date startDate, Date endDate)
	{
		if (startDate == null)
			return "";
		
		if (endDate == null)
			endDate = new Date();
		else if (startDate.after(endDate))
			return "(Data inicial maior que data final!)";

		int anoIni = startDate.getYear();
		int mesIni = startDate.getMonth();

		int anoFim = endDate.getYear();
		int mesFim = endDate.getMonth();

		int meses, anos;

		if (mesIni > mesFim)
		{
			meses = 12 - mesIni + mesFim;
			anoIni = anoIni + 1;
		}
		else
			meses = mesFim - mesIni;

		if (anoIni >= anoFim)
			anos = 0;
		else
			anos = anoFim - anoIni;

		String resultado = "(";
		if (anos > 0)
			resultado += anos + " ano";
		if (anos > 1)
			resultado += "s";
		
		if (anos > 0 && meses > 0)
			resultado += " e ";
		
		if (meses == 1)
			resultado += meses + " mês";
		else if (meses > 1)
			resultado += meses + " meses";
		
		resultado += ")";

		return resultado;
	}

	public static String calculaIntervaloAteHoje(Date data)
	{
		String resultado = "-";

		Date dataHoje = new Date();

		if (data.after(dataHoje) || data.equals(dataHoje))
			return resultado;

		int anoIni = data.getYear();
		int mesIni = data.getMonth();

		int anoFim = dataHoje.getYear();
		int mesFim = dataHoje.getMonth();

		int meses, anos;

		if (mesIni > mesFim)
		{
			meses = 12 - mesIni + mesFim;
			anoIni = anoIni + 1;
		}
		else
			meses = mesFim - mesIni;

		if (anoIni >= anoFim)
			anos = 0;
		else
			anos = anoFim - anoIni;

		String qtdAnos = "";
		String qtdMeses = "";
		if (anos > 0)
			qtdAnos = anos + (anos == 1 ? " ano" : " anos");
		if (meses > 0)
			qtdMeses = meses + (meses == 1 ? " mês" : " meses");

		resultado = qtdAnos;
		if (anos > 0 && meses > 0)
			resultado += " e ";
		resultado += qtdMeses;

		if (resultado.equals(""))
			resultado = "-";

		return resultado;
	}

	/**
	 * @author Igo Coelho, Rodrigo Maia
	 * @since 24/10/2007
	 * @param date Data a qual deseja-se obter o mês anterior
	 * @param ajustaDia TODO
	 * @return Mês imediatamente anterior
	 */
	public static Date retornaDataAnteriorQtdMeses(Date date, int qtdMeses, boolean ajustaDia)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		Calendar calendarResult = new GregorianCalendar();
		calendarResult.set(GregorianCalendar.DAY_OF_MONTH, 1);
		calendarResult.set(GregorianCalendar.HOUR_OF_DAY, 0);
		calendarResult.set(GregorianCalendar.MINUTE, 0);
		calendarResult.set(GregorianCalendar.SECOND, 0);
		calendarResult.set(GregorianCalendar.MILLISECOND, 0);
		calendarResult.set(GregorianCalendar.YEAR, calendar.get(GregorianCalendar.YEAR));
		calendarResult.set(GregorianCalendar.MONTH, calendar.get(GregorianCalendar.MONTH) - qtdMeses);

		if(ajustaDia)
		{
			if(calendar.get(GregorianCalendar.DAY_OF_MONTH) > calendarResult.getActualMaximum(GregorianCalendar.DAY_OF_MONTH))
				calendarResult.set(GregorianCalendar.DAY_OF_MONTH, calendarResult.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
			else
				calendarResult.set(GregorianCalendar.DAY_OF_MONTH, calendar.get(GregorianCalendar.DAY_OF_MONTH));
		}

		return calendarResult.getTime();

	}

	public static Date getInicioMesData(Date data)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(data);

		Calendar calendarResult = new GregorianCalendar();
		calendarResult.set(GregorianCalendar.DAY_OF_MONTH, 1);
		calendarResult.set(GregorianCalendar.HOUR_OF_DAY, 0);
		calendarResult.set(GregorianCalendar.MINUTE, 0);
		calendarResult.set(GregorianCalendar.SECOND, 0);
		calendarResult.set(GregorianCalendar.MILLISECOND, 0);
		calendarResult.set(GregorianCalendar.YEAR, calendar.get(GregorianCalendar.YEAR));
		calendarResult.set(GregorianCalendar.MONTH, calendar.get(GregorianCalendar.MONTH));

		return calendarResult.getTime();
	}

	public static Date getUltimoDiaMes(Date data)
	{
		 GregorianCalendar calendar = new GregorianCalendar();
		 calendar.setTime(data);
		 calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		 return calendar.getTime();
	}
	public static Date getUltimoDiaMesComHoraZerada(Date data)
	{
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
		calendar.set(GregorianCalendar.MINUTE, 0);
		calendar.set(GregorianCalendar.SECOND, 0);
		calendar.set(GregorianCalendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}

	public static Date setaMesPosterior(Date data)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(data);

		Calendar calendarResult = new GregorianCalendar();
		calendarResult.set(GregorianCalendar.DAY_OF_MONTH, 1);
		calendarResult.set(GregorianCalendar.HOUR_OF_DAY, 0);
		calendarResult.set(GregorianCalendar.MINUTE, 0);
		calendarResult.set(GregorianCalendar.SECOND, 0);
		calendarResult.set(GregorianCalendar.MILLISECOND, 0);
		calendarResult.set(GregorianCalendar.YEAR, calendar.get(GregorianCalendar.YEAR));
		calendarResult.set(GregorianCalendar.MONTH, calendar.get(GregorianCalendar.MONTH)+1);

		if(calendar.get(GregorianCalendar.DAY_OF_MONTH) > calendarResult.getActualMaximum(GregorianCalendar.DAY_OF_MONTH))
		{
			calendarResult.set(GregorianCalendar.DAY_OF_MONTH, calendarResult.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
		}
		else
		{
			calendarResult.set(GregorianCalendar.DAY_OF_MONTH, calendar.get(GregorianCalendar.DAY_OF_MONTH));
		}

		return calendarResult.getTime();
	}

	public static Date retornaDataDiaAnterior(Date date)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(GregorianCalendar.DAY_OF_MONTH, -1);
		return calendar.getTime();

	}

	 public static String getDiaSemanaDescritivo(Date data)
	 {
		 GregorianCalendar calendar = new GregorianCalendar();
		 calendar.setTime(data);

		 String[] diaSemana = new String[]{"domingo","segunda-feira","terça-feira","quarta-feira","quinta-feira","sexta-feira","sábado"};

		 return diaSemana[calendar.get(GregorianCalendar.DAY_OF_WEEK) - 1];
	}

	public static Date montaDataByString(String dataStr)
	{
		boolean invalida = isDataInvalida(dataStr);
		if (invalida)
			return null;
		
		boolean erroDeDigitacao = possuiPossivelErroDeDigitacao(dataStr);
		if (erroDeDigitacao)
			throw new IllegalArgumentException("Data inválida.");
		
		try {
			String[] parteData = dataStr.split("/");
			
			Calendar c = Calendar.getInstance();
			c.clear();
			c.set(Integer.parseInt(parteData[2]), Integer.parseInt(parteData[1])-1, Integer.parseInt(parteData[0]));

			return c.getTime();
		} catch (Exception e) {
			throw new IllegalArgumentException("Data inválida.");
		}
		
	}
	
	public static Date montaDataByStringComHora(String dataStr, String hora)
	{
		boolean invalida = isDataInvalida(dataStr);
		if (invalida)
			return null;
		
		boolean erroDeDigitacao = possuiPossivelErroDeDigitacao(dataStr);
		if (erroDeDigitacao)
			throw new IllegalArgumentException("Data inválida.");
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date result = sdf.parse(dataStr + " " + hora);
			return result;
		} catch (ParseException e) {
			throw new IllegalArgumentException("Data inválida.");
		}
	}

	public static Date montaDataComHora(String dataHora, String horaDefault)
	{
		boolean invalida = isDataInvalida(dataHora);
		if (invalida)
			return null;
		
		Date dataGerada = null;
		
		if(dataHora.split(" ").length == 1){
			dataGerada =  DateUtil.montaDataByStringComHora(dataHora, (horaDefault == null ? "00:00" : horaDefault));
		} else if(dataHora.split(" ").length == 2) 
			dataGerada =  DateUtil.montaDataByStringComHora(dataHora.split(" ")[0], dataHora.split(" ")[1]);
		
		return dataGerada;
	}
	
	public static Date montaDataByStringPostgres(String dataStr)
	{
		
		boolean invalida = isDataInvalida(dataStr);
		if (invalida)
			return null;
		
		boolean erroDeDigitacao = possuiPossivelErroDeDigitacao(dataStr);
		if (erroDeDigitacao)
			throw new IllegalArgumentException("Data inválida.");
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date result = sdf.parse(dataStr);
			return result;
		
		} catch (ParseException e) {
			throw new IllegalArgumentException("Data inválida.");
		}
	}
	
	public static Date montaDataByStringJson(String dataStr)
	{
		try {
			dataStr = dataStr.substring(0, 10);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date result = sdf.parse(dataStr);
			return result;
			
		} catch (ParseException e) {
			throw new IllegalArgumentException("Data inválida.");
		}
	}

	private static boolean possuiPossivelErroDeDigitacao(String dataStr) {
		Pattern pattern = Pattern.compile("(\\d{2})/(\\d{2})/(\\d{4})");
		Matcher matcher = pattern.matcher(dataStr);
		return !matcher.matches();
	}

	private static boolean isDataInvalida(String dataStr) {
		if(dataStr == null 
				|| "".trim().equals(dataStr)
					|| DATA_VAZIA.equals(dataStr)){
			return true;
		}
		return false;
	}
	
	public static boolean isDataInvalidaAll(String dataStr) {
		if(isDataInvalida(dataStr) || dataStr.replace("/", "").trim().length() < 8) {
			return true;
		}
		return false;
	}

	public static Date setaUltimaHoraDoDia(Date data){

		if(data != null && data.getDate() != 0){
			data.setHours(23);
			data.setMinutes(59);
			data.setSeconds(59);
		}

		return data;
	}

	/**
	 * Calcula a direfença de dias entre duas datas
	 * @author Israel, Igo
	 * @since 17/01/2008
	 * @param dataInicio
	 * @param dataFim
	 * @param incluirDataInicioNaDiferenca TODO
	 * @return Quantidade de dias entre as datas
	 */
	public static int diferencaEntreDatas(Date dataInicio, Date dataFim, Boolean incluirDataInicioNaDiferenca)
	{
		if(incluirDataInicioNaDiferenca)
			dataInicio = incrementaData(dataInicio, Calendar.DAY_OF_MONTH, -1, false);
		
		TimeZone timeZone = TimeZone.getDefault();
		if (timeZone.inDaylightTime(dataFim) && !timeZone.inDaylightTime(dataInicio) ) 
			dataFim = incrementaData(dataFim, Calendar.HOUR_OF_DAY, 1, false);
		
		Long diff = dataFim.getTime() - dataInicio.getTime();
		Long dias = diff/(24L*60L*60L*1000L);
		
		return dias.intValue();
	}

	public static boolean mesPosterior(Date data1, Date data2)
	{
		GregorianCalendar gc1 = new GregorianCalendar();
		gc1.setTime(data1);
		GregorianCalendar gc2 = new GregorianCalendar();
		gc2.setTime(data2);

		int mes1 = gc1.get(GregorianCalendar.MONTH);
		int mes2 = gc2.get(GregorianCalendar.MONTH);

		return (mes2-mes1==1);
	}

	public static boolean mesmoMes(Date data1, Date data2)
	{
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(data1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(data2);

		return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
	}

	public static int anosEntreDatas(Date dataIni, Date dataFim)
	{
		GregorianCalendar gc1 = new GregorianCalendar();
		gc1.setTime(dataIni);
		GregorianCalendar gc2 = new GregorianCalendar();
		gc2.setTime(dataFim);

		return gc2.get(Calendar.YEAR) - gc1.get(Calendar.YEAR);
	}

	public static int mesesEntreDatas(Date dataIni, Date dataFim)
	{
		GregorianCalendar gc1 = new GregorianCalendar();
		gc1.setTime(dataIni);
		GregorianCalendar gc2 = new GregorianCalendar();
		gc2.setTime(dataFim);

		return (anosEntreDatas(dataIni, dataFim)*12) + (gc2.get(Calendar.MONTH) - gc1.get(Calendar.MONTH));
	}

	public static String formataDataExtensoPadraoDeAta(Date data, String horario)
	{
		String dia = formataDate(data, "dd");
		String mes = formataDate(data, "MM");
		String ano = formataDate(data, "yyyy");

		String anoIni = ano.substring(0, 2);
		String anoFim = ano.substring(2, 4);

		if (Integer.parseInt(anoIni) == 20)
			ano = "dois mil e";

		String anoExtenso = ano + " " + StringUtil.getNumeroExtensoData(Integer.parseInt(anoFim));
		String mesExtenso = getNomeMesCompleto(Integer.parseInt(mes));
		String diaExtenso = StringUtil.getNumeroExtensoData(Integer.parseInt(dia));

		String frase = "";

		if (Integer.parseInt(dia) == 1)
			frase+= "No primeiro dia";
		else
			frase+= "Aos " + diaExtenso + " dias";

		frase += " do mês de " + mesExtenso;
		frase += " do ano de " + anoExtenso;
		frase += ", às " + horario;

		return frase;
	}

	public static Date incrementaData(Date data, int tipoIncremento, int incremento, boolean zerarHorario)
	{
		Calendar calendar = new GregorianCalendar();
		if(zerarHorario)
			calendar.clear();
		
		calendar.setTime(data);
		calendar.add(tipoIncremento, incremento);
			
		return calendar.getTime();
	}
	
	public static Date incrementaDias(Date dataAntiga, Integer periodicidade)
	{
		return incrementaData(dataAntiga, Calendar.DAY_OF_MONTH, periodicidade, false);
	}

	public static Date incrementaMes(Date dataAntiga, Integer periodicidade)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(dataAntiga);

		Calendar calendarResult = new GregorianCalendar();
		calendarResult.set(GregorianCalendar.MONTH, calendar.get(GregorianCalendar.MONTH) + periodicidade);
		calendarResult.set(GregorianCalendar.YEAR, calendar.get(GregorianCalendar.YEAR));

		setDias(calendar, calendarResult);

		return calendarResult.getTime();
	}
	
	public static Date incrementaAno(Date dataAntiga, Integer periodicidade)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(dataAntiga);
		
		Calendar calendarResult = new GregorianCalendar();
		calendarResult.set(GregorianCalendar.MONTH, calendar.get(GregorianCalendar.MONTH));
		calendarResult.set(GregorianCalendar.YEAR, calendar.get(GregorianCalendar.YEAR) + periodicidade);
		
		setDias(calendar, calendarResult);
		
		return calendarResult.getTime();
	}
	
	public static Date incrementa(Date dataAntiga, Integer periodicidade, Integer tipoPeriodo)
	{
		// tipo período -> 0: mês 1: ano 2: dia
		switch(tipoPeriodo)
		{
			case 0:
				return incrementaMes(dataAntiga, periodicidade);
			case 1:
				return incrementaAno(dataAntiga, periodicidade);
			case 2:
				return incrementaDias(dataAntiga, periodicidade);
			default: 
				return null;
		}
	}

	private static void setDias(Calendar calendar, Calendar calendarResult)
	{
		calendarResult.set(GregorianCalendar.DAY_OF_MONTH, 1);
		calendarResult.set(GregorianCalendar.HOUR_OF_DAY, 0);
		calendarResult.set(GregorianCalendar.MINUTE, 0);
		calendarResult.set(GregorianCalendar.SECOND, 0);
		calendarResult.set(GregorianCalendar.MILLISECOND, 0);

		if(calendar.get(GregorianCalendar.DAY_OF_MONTH) > calendarResult.getActualMaximum(GregorianCalendar.DAY_OF_MONTH))
		{
			calendarResult.set(GregorianCalendar.DAY_OF_MONTH, calendarResult.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
		}
		else
		{
			calendarResult.set(GregorianCalendar.DAY_OF_MONTH, calendar.get(GregorianCalendar.DAY_OF_MONTH));
		}
	}

	public static String getAno()
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		return Integer.toString(calendar.get(GregorianCalendar.YEAR));
	}

	public static String formataMesExtensoAno(Date data)
	{
		String dataFormatada = "";
		if(data != null)
		{
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(data);
			
			dataFormatada = getNomeMes(calendar.get(GregorianCalendar.MONTH)).toUpperCase() + "/" + calendar.get(GregorianCalendar.YEAR); 
		}
		
		return dataFormatada;
	}

	/**
	 * Compara se uma data está em um período, sem considerar horas
	 * @param _data     data a ser comparada
	 * @param _inicio   início do período
	 * @param _fim      fim do período
	 * @return
	 */
	public static boolean between(Date _data, Date _inicio, Date _fim)
	{
		Calendar data = Calendar.getInstance();
		data.setTime(_data);
		data.set(Calendar.HOUR, 0);
		
		Calendar inicio = Calendar.getInstance();
		inicio.setTime(_inicio);
		inicio.set(Calendar.HOUR, 0);
		
		Calendar fim = Calendar.getInstance();
		fim.setTime(_fim);
		fim.set(Calendar.HOUR, 0);
		
		return (data.compareTo(inicio) >= 0 && data.compareTo(fim) <= 0);
	}

	public static int contaDiasUteisMes(Date data, boolean considerarSabadoNoAbsenteismo, boolean considerarDomingoNoAbsenteismo)
	{  
		Date dataInicial = getInicioMesData(data);
		Date dataFinal = getUltimoDiaMes(data);
		
	    int diasUteis = 0;
	    int totalDias = diferencaEntreDatas( dataInicial, dataFinal, false ) + 1;  
	      
	    Calendar calendar = new GregorianCalendar( dataInicial.getYear() + 1900, dataInicial.getMonth(), dataInicial.getDate() );  
	    for( int i = 1; i <= totalDias; i++ ) 
	    {  
	        if(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) 
	        	diasUteis++;  
	        
	        if ( calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && considerarSabadoNoAbsenteismo) 
	        	diasUteis++;
	        
	        if ( calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && considerarDomingoNoAbsenteismo) 
	        	diasUteis++;
	       
	        calendar.add( Calendar.DATE, 1);  
	    }
	      
	    return diasUteis;
	}
	
	public static String getHora(Date data)
	{  
		String retorno = "";
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		
		retorno += StringUtils.leftPad(String.valueOf(calendar.get(Calendar.HOUR)), 2, "0");
		retorno += ":";
		retorno += StringUtils.leftPad(String.valueOf(calendar.get(Calendar.MINUTE)), 2, "0");
		
		return retorno;
	}
	
	public static int getDia(Date data)
	{  
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public static int getMes(Date data)
	{  
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	public static int getAnoInteger(Date data)
	{  
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		
		return calendar.get(GregorianCalendar.YEAR);
	}
	
	public static Date getUltimoDiaMesAnterior(Date data) 
	{
		return incrementaDias(data, -1);
	}
	
	public static String formataTempoExtenso(Date inicio, Date fim){
		
		String tempoExtenso = "";
		if (inicio != null && fim != null){
			GregorianCalendar dataFinal = new GregorianCalendar();
			
			dataFinal.setTime(fim);
			
			int anoFim = dataFinal.get(GregorianCalendar.YEAR);
			int mesFim = dataFinal.get(GregorianCalendar.MONTH);
			int diaFim = dataFinal.get(GregorianCalendar.DAY_OF_MONTH);

			int anoInicio = (inicio.getYear() + 1900);
			int mesInicio = inicio.getMonth();
			int diaInicio = inicio.getDate();

			int anos = anoFim - anoInicio;
			int meses;

			if (anos > 0){
				if (mesFim < mesInicio)
					anos--;
				else{
					if (mesFim == mesInicio){
						if (diaFim < diaInicio)
							anos--;
					}
				}
			}

			if (mesFim > mesInicio)
				meses = mesFim - mesInicio;
			else
				meses = (12 - mesInicio) + mesFim;

			if (diaFim < diaInicio)
				meses--;

			if (anos > 0){
				if(anos == 1)
					tempoExtenso += anos + " ano ";
				else
					tempoExtenso += anos + " anos ";
			}

			if (meses > 0){
				if(meses == 1)
					tempoExtenso += (anos > 0 ?"e ":"") + meses + " mês";
				else
					tempoExtenso += (anos > 0 ?"e ":"") + meses + " meses";
			}
			
			if(meses <= 0 && anos == 0){
				tempoExtenso = DateUtil.diferencaEntreDatas(inicio, fim, false) + " dias"; 
			}
		}
		
		return tempoExtenso.trim();
	}
	
	public static String formataPeriodoPorExtenso(Date inicio, Date fim){
		StringBuilder tempoExtenso = new StringBuilder("");
		if(inicio != null && fim != null){
	        DateTime dateTime1 = new DateTime(inicio);
	        DateTime dateTime2 = new DateTime(fim);
			
			Period periodo = new Period(dateTime1, dateTime2, PeriodType.yearMonthDayTime());
			
			if(periodo.getYears() > 0)
				tempoExtenso.append(periodo.getYears() == 1 ? periodo.getYears() + " ano" : periodo.getYears() + " anos");
			if(periodo.getMonths() > 0){
				tempoExtenso.append(tempoExtenso.length() > 0 && periodo.getDays() == 0 ? " e ": (periodo.getYears() > 0 ? ", " : "") ); 
				tempoExtenso.append(periodo.getMonths() == 1 ? periodo.getMonths() + " mês" : periodo.getMonths() + " meses");
			}
			if(periodo.getDays() > 0){
				tempoExtenso.append(tempoExtenso.length() > 0 ? " e ": (periodo.getYears() > 0 || periodo.getMonths() > 0 ? " " : "") );
				tempoExtenso.append((periodo.getDays() ) == 1 ? (periodo.getDays())  + " dia" : periodo.getDays() + " dias");
			}
			
			if(tempoExtenso.length() == 0)
				tempoExtenso.append("0 dia");
		}
		return tempoExtenso.toString();
	}
}
