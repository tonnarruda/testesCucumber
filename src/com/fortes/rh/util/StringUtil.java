package com.fortes.rh.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;

public final class StringUtil
{
	private static final String[] REPLACES = new String[] { "a", "e", "i", "o", "u", "c", "A", "E", "I", "O", "U", "C"};
	private static final Pattern[] PATTERNS = new Pattern[] {
			Pattern.compile("[âãáàä]"),
			Pattern.compile("[éèêë]"),
			Pattern.compile("[íìîï]"),
			Pattern.compile("[óòôõö]"),
			Pattern.compile("[úùûü]"),
			Pattern.compile("[ç]"),
			Pattern.compile("[ÂÃÁÀÄ]"),
			Pattern.compile("[ÉÈÊË]"),
			Pattern.compile("[ÍÌÎÏ]"),
			Pattern.compile("[ÓÒÔÕÖ]"),
			Pattern.compile("[ÚÙÛÜ]"),
			Pattern.compile("[Ç]")
	};

	public static String toJSON(Object valor, String[] excludes) 
	{
		JsonConfig jsonConfig = new JsonConfig();
		if(excludes != null)
			jsonConfig.setExcludes(excludes);
		
		return JSONSerializer.toJSON(valor, jsonConfig).toString();
	}
	
	public static Object simpleJSONtoArrayJava(String json, Class<?> clazz) throws JSONException 
	{
		JSONArray jsonObject = JSONArray.fromObject(json);
		JsonConfig cfg = new JsonConfig();
		cfg.setRootClass(clazz);
		
		return JSONSerializer.toJava(jsonObject, cfg);			
	}
	
	public static Object simpleJSONObjecttoArrayJava(String json, Class<?> clazz) throws JSONException 
	{
		JSONObject jsonObject = JSONObject.fromObject(json);
		JsonConfig cfg = new JsonConfig();
		cfg.setRootClass(clazz);
		
		return JSONSerializer.toJava(jsonObject, cfg);	
	}
	
	public static String subStr(String value, int max)
	{
		if(value != null && value.length() > max)
			value = value.substring(0, max);
		return value;
	}
	
	public static String subStr(String value, int max, String suffix)
	{
		if(value != null && value.length() > max)
			value = value.substring(0, max) + suffix;
		return value;
	}
	
	public static String montaTokenF2rh(String nome)
	{
		try {
			Calendar calendar = new GregorianCalendar();
			
			return nome.length() + nome.substring(0, 1)+ (calendar.get(GregorianCalendar.MONTH) + 1) + "0t3" + calendar.get(GregorianCalendar.DAY_OF_MONTH);
		} catch (Exception e) {
			return "";
		}

	}
	
	public static String decodeString(String str)
	{
		try
		{
			byte[] decoded = Base64.decodeBase64(str.getBytes());
			return new String(decoded);
		}
		catch (Exception io)
		{
			throw new RuntimeException(io.getMessage(), io.getCause());
		}
	}

	public static String encodeString(String str)
	{
		return new String( Base64.encodeBase64(str.getBytes()) ).trim();
	}

	public static String removeBreak(String value)
	{
		return value.replaceAll("\r", "").replaceAll("\n", "").replaceAll("'","`");
	}

	public static String removeApostrofo(String value)
	{
		return value.replaceAll("'", "\\\\'");
	}

	public static Long[] stringToLong(String[] chaves)
	{
		if (chaves == null || chaves.length == 0)
			return new Long[]{};

		Long[] longs = new Long[chaves.length];

		for (int i = 0; i < chaves.length; i++)
		{
			longs[i] = Long.parseLong(chaves[i].replace(".", ""));
		}
		return longs;
	}
	
	public static String[] LongToString(Long[] chaves)
	{
		if (chaves == null || chaves.length == 0)
			return new String[]{};
		
		String[] strings = new String[chaves.length];
		
		for (int i = 0; i < chaves.length; i++)
		{
			strings[i] = chaves[i].toString();
		}
		return strings;
	}

	public static String getSenhaRandom(int tamanho)
	{
		String senha = "";
		String letras = "abcdefghijklmnopqrstuvwxzy";
		String letra = "";

		for (int i = 1; i <= tamanho; i++)
		{

			if (i % 2 == 0)
			{
				double d = Math.random() * 25;
				long l = Math.round(d);
				String lStr = Long.toString(l);
				int indice = Integer.parseInt(lStr);

				letra = Character.toString(letras.charAt(indice));
			}
			else
			{
				double d = Math.random() * 9;
				long l = Math.round(d);
				String lStr = Long.toString(l);
				int indice = Integer.parseInt(lStr);

				letra = Integer.toString(indice);
			}

			senha += letra;
		}

		return senha;
	}

	public static String letraByNumero(int ordem)
	{
		if (ordem < 1)
			return "-";
		
		if(ordem < 1 || ordem > 26)
			return "-";
		String[] letras = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		return letras[ordem - 1];
	}

	public static String mudaExtensaoFile(String nomeArquivo, String novaExtensao)
	{
		String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf("."), nomeArquivo.length());

		return nomeArquivo.replace(extensao, novaExtensao);
	}

	public static int converteStringToInt(String s)
	{
		try
		{
			return Integer.parseInt(s);
		}
		catch (Exception e)
		{
			return 0;
		}
	}

	public static String destacarExpressoesApresentacao(String textoOriginal, String[] palavrasProcuradas)
	{
		String textoComDestaques = textoOriginal;
		int qtdPalavras = palavrasProcuradas.length;

		for(int i = 0; i < qtdPalavras; i++)
		{
			String regex = montaRegex(palavrasProcuradas[i]);
			Pattern pattern = Pattern.compile("(?i)" + regex.toLowerCase());
			Matcher matcher = pattern.matcher(textoComDestaques);

			while(matcher.find())
			{
				String palavra = matcher.group();
				textoComDestaques = textoComDestaques.replaceAll(palavra,"<span class='xz'>" + palavra + "</span>");
			}
		}

		return textoComDestaques;
	}

	private static String montaRegex(String regex)
	{
		regex = regex.replaceAll("(?i)[aáàãâ]", "[AÁÀÃÂ]");
		regex = regex.replaceAll("(?i)[eéèê]", "[EÉÈÊ]");
		regex = regex.replaceAll("(?i)[iíì]", "[IÍÌ]");
		regex = regex.replaceAll("(?i)[oóòõô]", "[OÓÒÕÔ]");
		regex = regex.replaceAll("(?i)[uúù]", "[UÚÙ]");
		regex = regex.replaceAll("(?i)[cç]", "[CÇ]");
		return regex;
	}

	public static String removeMascara(String s)
	{
		if(s != null)
		{
			s = s.replace(".", "");
			s = s.replace("-", "");
			s = s.replace("/", "");
			s = s.trim();
			return s;
		}
		else
			return "";

	}

	public static String converteArrayToString(String[] array)
	{
		StringBuilder retorno = new StringBuilder();

		if(array != null && array.length > 0)
		{
			for (String valor : array)
			{
				retorno.append(valor);
				retorno.append(",");
			}
		}

		if(retorno.length() > 0)
			return retorno.substring(0, (retorno.length() - 1));

		return retorno.toString();
	}

	public static String converteCharArrayToString(Character[] array)
	{
		StringBuilder retorno = new StringBuilder();
		
		if(array != null && array.length > 0)
		{
			for (Character valor : array)
			{
				retorno.append(valor);
				retorno.append(",");
			}
		}
		
		if(retorno.length() > 0)
			return retorno.substring(0, (retorno.length() - 1));
		
		return retorno.toString();
	}
	
	public static String[] append(String[] arr, String element) 
	{
	    int N = arr.length;
	    arr = Arrays.copyOf(arr, N + 1);
	    arr[N] = element;
	    return arr;
	}
	
	public static String[] appendAll(String[] arr, String[] elements) 
	{
		int i = arr.length;
		int N = arr.length + elements.length;
		arr = Arrays.copyOf(arr, N);

		for (String element : elements) 
			arr[i++] = element;
		
		return arr;
	}
	
	public static String[] appendAllDistinct(String[] arrs, String[] elements) 
	{
		if (arrs == null && elements == null)
			return new String[]{};
		else if(arrs != null && elements == null)
			return arrs;
		else if (arrs == null && elements != null)
			return elements;
		else {
			Collection<String> repeat = new ArrayList<String>();
			for (String arr : arrs) 
				for (String element : elements) 
					if(arr.equals(element))
						repeat.add(arr);
			
			int i = arrs.length;
			int N = arrs.length + elements.length - repeat.size();
			arrs = Arrays.copyOf(arrs, N);
			
			for (String element : elements) 
				if(!repeat.contains(element))
					arrs[i++] = element;
			
			return arrs;
		}
	}
	
	public static String converteCollectionToString(Collection<String> colecao)
	{
		StringBuilder retorno = new StringBuilder("");
		
		if(colecao != null && !colecao.isEmpty())
		{
			for (String valor : colecao)
			{
				retorno.append(valor);
				retorno.append(",");
			}
		}
		
		if(retorno.length() > 0)
			return retorno.substring(0, (retorno.length() - 1));
		
		return retorno.toString();
	}
	
	public static String converteCollectionLongToString(Collection<Long> colecao)
	{
		StringBuilder retorno = new StringBuilder("");
		
		if(colecao != null && !colecao.isEmpty())
		{
			for (Long valor : colecao)
			{
				retorno.append(valor.toString());
				retorno.append(",");
			}
		}
		
		if(retorno.length() > 0)
			return retorno.substring(0, (retorno.length() - 1));
		
		return retorno.toString();
	}
	
	public static String converteCollectionToStringComAspas(Collection<String> colecao)
	{
		StringBuilder retorno = new StringBuilder();
		
		if(colecao != null && !colecao.isEmpty())
		{
			for (String valor : colecao)
			{
				if (valor != null)
					valor = valor.replaceAll("\"", "\'");
				
				retorno.append("\"" + valor + "\"");
//				retorno.append("'" + valor + "'");
				retorno.append(",");
			}
		}
		
		if(retorno.length() > 0)
			return retorno.substring(0, (retorno.length() - 1));
		
		return retorno.toString();
	}

	public static String criarMascaraCpf(String cpf)
	{
		if (cpf == null || cpf.length() != 11)
			return cpf;

		String cpfFormatado = cpf.substring(0,3) + "." + cpf.substring(3,6) + "."
							+ cpf.substring(6, 9) + "-" + cpf.substring(9,11);

		return cpfFormatado;

	}
	
	public static String criarMascaraTelefone(String telefone)
	{
		String telefoneFormatado = telefone; 
		if (StringUtils.isBlank(telefone))
			return telefoneFormatado;
		
		if(telefone.length() == 8)
			telefoneFormatado = telefone.substring(0,4) + "-" + telefone.substring(4,8);
		if(telefone.length() == 7)
			telefoneFormatado = telefone.substring(0,3) + "-" + telefone.substring(3,7);
		
		return telefoneFormatado;
	}

	public static String retiraAcento(String text)
	{
		return replacePatterns(text, PATTERNS, REPLACES);
	}

	private static String replacePatterns(String text, Pattern[] patterns, String[] replaces)
	{
		if(text == null || text.trim().equals(""))
			return "";

		String result = text;

		for (int i = 0; i < patterns.length; i++)
		{
			Matcher matcher = patterns[i].matcher(result);
			result = matcher.replaceAll(replaces[i]);
		}

		return result;
	}

	/*
	 * Números por extenso para datas
	 */
	public static String getNumeroExtensoData(int numero)
	{
		String[] extenso = {"um",
				"dois","três","quatro","cinco","seis","sete","oito",
				"nove","dez","onze","doze","treze","quatorze","quinze","dezesseis","dezessete",
				"dezoito", "dezenove","vinte","vinte e um","vinte e dois","vinte e três",
				"vinte e quatro","vinte e cinco","vinte e seis","vinte e sete",
				"vinte e oito", "vinte e nove", "trinta", "trinta e um"};

		return extenso[numero-1];
	}
	
	public static String formataCnpj(String cnpj, String complementoCnpj) 
	{
		if (isBlank(cnpj) || isBlank(complementoCnpj))
			return cnpj;
		
		String cnpjFormatado = CnpjUtil.formata(cnpj+complementoCnpj);
		
		cnpjFormatado = cnpjFormatado.substring(0,2).concat(".")
							.concat(cnpjFormatado.substring(2,5)).concat(".")
							.concat(cnpjFormatado.substring(5,cnpjFormatado.length()));
		
		return cnpjFormatado;
	}
	
	public static String formataCep(String cep)
	{
		if (isBlank(cep))
			return cep;
		if (cep.length() < 8)
			do { cep = cep.concat("0"); } while (cep.length() < 8);
		
		String cepFormatado = cep.substring(0, 2).concat(".").concat(cep.substring(2,5)).concat("-").concat(cep.substring(5,8));
		
		return cepFormatado;
	}
	
	public static boolean isBlank(String str)
	{
		return StringUtils.isBlank(str);
	}

	public static String[] converteCollectionToArrayString(Collection<String> colecao)
	{	
		if(colecao == null || colecao.isEmpty())
			return null;

		String[] retorno = new String[colecao.size()];
		int i = 0;
		for (String valor : colecao)
		{
			retorno[i++] = valor;
		}
		
		return retorno;
	}
	
	/**
	 *Recebe código (do Empregado AC) e preenche os zeros à esquerda até ficar 6 dígitos.   
	 */
	public static String formataCodigoAC(String codigo)
	{
		if (StringUtils.isBlank(codigo) || !StringUtils.isNumeric(codigo))
			return "";
		
		while (codigo.length() < 6)
		{
			codigo = "0" + codigo;
		}
		
		return codigo;
	}
	
	public static boolean equalsIgnoreCaseAcento(String a, String b)
	{
		return StringUtils.equalsIgnoreCase(retiraAcento(a), retiraAcento(b));
	}

	public static String valueOf(Integer valor) 
	{
		try 
		{
			if(valor != null)
				return String.valueOf(valor);
			else
				return "";
		}
		catch (Exception e) 
		{
			return "";
		}

	}

	public static String removeAspas(String value) 
	{
		return value.replaceAll("'","`");
	}

	public static String lower(String nomeBusca) 
	{
		return nomeBusca!=null?nomeBusca.toLowerCase():"";
	}

	public static String camelCaseToSnakeCase(String str) 
	{
		if(str == null)
			return null;
		else
			return str.replaceAll("([a-z])([A-Z])", "$1 $2");
	}

	public static String getHTML(String url) 
	{
		String pagina = "";
		Integer timeout = 15000;
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.socket.timeout", timeout);
		client.getParams().setParameter("http.connection.timeout", timeout);

		try {
			GetMethod get = new GetMethod(url);
			client.executeMethod(get);
			pagina = get.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pagina;
	}

	public static String replaceXml(String texto) {
		return texto.replace("&", "&amp;").replace(">", "&gt;").replace("<", "&lt;").replace("\"", "&quot;").replace("'", "&apos;").replace("	", "");
	}
	
	public static String convertUTF8toISO(String str) 
	{
		String ret = null;
		try {
		ret = new String(str.getBytes("ISO-8859-1"), "UTF-8");
		}
		catch (java.io.UnsupportedEncodingException e) {
		return null;
		}
		return ret;
	}
	
	public static Collection<String> getSomenteNumero(String string)
	{
		Collection<String> retorno = new ArrayList<String>();
		
		Pattern p = Pattern.compile("-?\\d+");
		Matcher m = p.matcher(string);
		while (m.find()) {
			retorno.add(m.group());
		}
		
		return retorno;
	}
}