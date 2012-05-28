package com.fortes.web.tags;

public class PaginacaoUtil {

	/**
	 * Método responsível por criar uma paginação de registros atraves de setas de navegação
	 * @param initialPage = pagina em que a aplicação se encontra
	 * @param totalSize = quantidade de registros a serem paginados
	 * @param pagingSize = quantidade de registros por pá
	 * @param link = link que será utilizado para as setas ">" e/ou "<"
	 * @param url
	 * @param limpaCampos
	 * @return links responsíveis pela paginação
	 */
	public static String makeRealPaging(int initialPage, int totalSize, int pagingSize, String link, String idFormulario, String url, String limpaCampos)
	{
		//retorno do metodo com os links anterior e/ou posterior
		String paginacao = "";
		//quantidade de paginas da paginação
		int qtdPages = totalSize / pagingSize;;
		if ((totalSize % pagingSize) > 0)
			qtdPages = (totalSize / pagingSize) + 1;

		String montaHrefIni = link + "page=";
		String montaHrefFim = "";
		String mudaPagina = "mudaPaginaLink(event, this.value, "+ qtdPages +", \""+ montaHrefIni +"\");";

		String funcaoLimpaCampos = "";
		if(limpaCampos != null && !limpaCampos.equals(""))
		{
			String[] campos = limpaCampos.split(",");
			String arrayCampos = "new Array(";
			for (String campo : campos)
			{
				arrayCampos += "\"" + campo + "\",";
			}

			arrayCampos = arrayCampos.substring(0, (arrayCampos.length() - 1));
			arrayCampos += ")";

			funcaoLimpaCampos = "limpaMascaras("+ arrayCampos + ");";
		}

		if (link.trim().equals(""))
		{
			montaHrefIni = "javascript:" + funcaoLimpaCampos + "paging(";
			montaHrefFim = ", \"" + idFormulario + "\");";
			mudaPagina = "mudaPagina(event, this.value, "+ qtdPages +", \""+ idFormulario +"\");";
		}

		String imgIni = "<img src='"+ url;
		String imgFim = "'>";

		String setaAnterior = "<a href='" + montaHrefIni + "1" + montaHrefFim + "' title='Primeira página'>"+ imgIni + "primeira.gif" + imgFim + "</a>&nbsp;&nbsp;&nbsp;<a href='" + montaHrefIni + (initialPage - 1) + montaHrefFim +" ' title='Página anterior'>"+ imgIni + "anterior.gif" + imgFim + "</a>&nbsp;&nbsp;&nbsp;";
		String setaPosterior = "&nbsp;&nbsp;&nbsp;<a href='" + montaHrefIni + (initialPage+1)+ montaHrefFim + "' title='Próxima página'>"+ imgIni + "proxima.gif" + imgFim + "</a>&nbsp;&nbsp;&nbsp;<a href='" + montaHrefIni +(qtdPages)+ montaHrefFim + "' title='Última página'>"+ imgIni + "ultima.gif" + imgFim + "</a>";

		//verifica se a pagina passada é zero
		if(initialPage == 0)
			initialPage = 1;

		//verifica se a pagina passada esta fora das possíveis
		if(initialPage < 1 || initialPage > qtdPages)
			return "";

		// Total da paginação
		paginacao = getTotalDeRegistrosEncontrados(totalSize);

		//a quantidade de registros é menor ou  igual que o tamanho de registros por pagina, desta forma, não existe paginação
		if(totalSize > pagingSize)
		{
			//estou na primeira página, não existe seta anterior

			if(initialPage == 1)//apaga seta anterior
				setaAnterior = imgIni + "primeira.gif' class='desabilitaImg'>&nbsp;&nbsp;&nbsp;"+ imgIni + "anterior.gif' class='desabilitaImg'>&nbsp;&nbsp;&nbsp;";
			else if(initialPage == qtdPages)//apaga seta posterior
				setaPosterior = "&nbsp;&nbsp;&nbsp;"+ imgIni + "proxima.gif' class='desabilitaImg'>&nbsp;&nbsp;&nbsp;"+ imgIni + "ultima.gif' class='desabilitaImg'>";

			String inputPage = "<input type='text' name='inputPage' value='"+ initialPage +"' id='inputPage' class='inputPage' maxLength='4' onkeydown='"+ mudaPagina +"'  onkeypress=\"return(somenteNumeros(event,''));\"/>";
			paginacao += "<div class='linksPaginacao'>" + setaAnterior + " Página " + inputPage + " de " + qtdPages + setaPosterior + "</div>";
		}

		return paginacao;
	}
	
	private static String getTotalDeRegistrosEncontrados(int total) {
		
		String html = "<div id='totalDeRegistrosEncontrados'> " + total
					+ " registro(s) encontrado(s).</div>";
		
		return html;
	}
	
}