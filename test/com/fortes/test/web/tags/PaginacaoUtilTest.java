package com.fortes.test.web.tags;

import org.jmock.MockObjectTestCase;

import com.fortes.web.tags.PaginacaoUtil;

public class PaginacaoUtilTest extends MockObjectTestCase
{
	public void testMakeRealPaging()
	{
		String url = "fortesrh/imgs/";
		String stringPaginacao = "<div id='totalDeRegistrosEncontrados'> 50 registro(s) encontrado(s).</div><div class='linksPaginacao'><img src='fortesrh/imgs/primeira.gif' class='desabilitaImg'>&nbsp;&nbsp;&nbsp;<img src='fortesrh/imgs/anterior.gif' class='desabilitaImg'>&nbsp;&nbsp;&nbsp; Página <input type='text' name='inputPage' value='1' id='inputPage' class='inputPage' maxLength='4' onkeydown='mudaPagina(event, this.value, 5, \"formularioTeste\");'  onkeypress=\"return(somenteNumeros(event,''));\"/> de 5&nbsp;&nbsp;&nbsp;<a href='javascript:paging(2, \"formularioTeste\");' title='Próxima página'><img src='fortesrh/imgs/proxima.gif'></a>&nbsp;&nbsp;&nbsp;<a href='javascript:paging(5, \"formularioTeste\");' title='Última página'><img src='fortesrh/imgs/ultima.gif'></a></div>";
		String retorno = PaginacaoUtil.makeRealPaging(1, 50, 10, "", "formularioTeste", url, null);
		assertEquals("test 1", stringPaginacao, retorno);

		stringPaginacao = "<div id='totalDeRegistrosEncontrados'> 50 registro(s) encontrado(s).</div><div class='linksPaginacao'><a href='javascript:limpaMascaras(new Array(\"data1\",\"data2\"));paging(1, \"formularioTeste\");' title='Primeira página'><img src='fortesrh/imgs/primeira.gif'></a>&nbsp;&nbsp;&nbsp;<a href='javascript:limpaMascaras(new Array(\"data1\",\"data2\"));paging(1, \"formularioTeste\"); ' title='Página anterior'><img src='fortesrh/imgs/anterior.gif'></a>&nbsp;&nbsp;&nbsp; Página <input type='text' name='inputPage' value='2' id='inputPage' class='inputPage' maxLength='4' onkeydown='mudaPagina(event, this.value, 5, \"formularioTeste\");'  onkeypress=\"return(somenteNumeros(event,''));\"/> de 5&nbsp;&nbsp;&nbsp;<a href='javascript:limpaMascaras(new Array(\"data1\",\"data2\"));paging(3, \"formularioTeste\");' title='Próxima página'><img src='fortesrh/imgs/proxima.gif'></a>&nbsp;&nbsp;&nbsp;<a href='javascript:limpaMascaras(new Array(\"data1\",\"data2\"));paging(5, \"formularioTeste\");' title='Última página'><img src='fortesrh/imgs/ultima.gif'></a></div>";
		retorno = PaginacaoUtil.makeRealPaging(2, 50, 10, "", "formularioTeste", url, "data1,data2");
		assertEquals("test 2", stringPaginacao, retorno);

		stringPaginacao = "<div id='totalDeRegistrosEncontrados'> 50 registro(s) encontrado(s).</div><div class='linksPaginacao'><a href='javascript:paging(1, \"formularioTeste\");' title='Primeira página'><img src='fortesrh/imgs/primeira.gif'></a>&nbsp;&nbsp;&nbsp;<a href='javascript:paging(1, \"formularioTeste\"); ' title='Página anterior'><img src='fortesrh/imgs/anterior.gif'></a>&nbsp;&nbsp;&nbsp; Página <input type='text' name='inputPage' value='2' id='inputPage' class='inputPage' maxLength='4' onkeydown='mudaPagina(event, this.value, 5, \"formularioTeste\");'  onkeypress=\"return(somenteNumeros(event,''));\"/> de 5&nbsp;&nbsp;&nbsp;<a href='javascript:paging(3, \"formularioTeste\");' title='Próxima página'><img src='fortesrh/imgs/proxima.gif'></a>&nbsp;&nbsp;&nbsp;<a href='javascript:paging(5, \"formularioTeste\");' title='Última página'><img src='fortesrh/imgs/ultima.gif'></a></div>";
		retorno = PaginacaoUtil.makeRealPaging(2, 50, 10, "", "formularioTeste", url, null);
		assertEquals("test 3", stringPaginacao, retorno);

		stringPaginacao = "<div id='totalDeRegistrosEncontrados'> 50 registro(s) encontrado(s).</div><div class='linksPaginacao'><a href='javascript:paging(1, \"formularioTeste\");' title='Primeira página'><img src='fortesrh/imgs/primeira.gif'></a>&nbsp;&nbsp;&nbsp;<a href='javascript:paging(4, \"formularioTeste\"); ' title='Página anterior'><img src='fortesrh/imgs/anterior.gif'></a>&nbsp;&nbsp;&nbsp; Página <input type='text' name='inputPage' value='5' id='inputPage' class='inputPage' maxLength='4' onkeydown='mudaPagina(event, this.value, 5, \"formularioTeste\");'  onkeypress=\"return(somenteNumeros(event,''));\"/> de 5&nbsp;&nbsp;&nbsp;<img src='fortesrh/imgs/proxima.gif' class='desabilitaImg'>&nbsp;&nbsp;&nbsp;<img src='fortesrh/imgs/ultima.gif' class='desabilitaImg'></div>";
		retorno = PaginacaoUtil.makeRealPaging(5, 50, 10, "", "formularioTeste", url, null);
		assertEquals("test 4", stringPaginacao, retorno);

		stringPaginacao = "<div id='totalDeRegistrosEncontrados'> 50 registro(s) encontrado(s).</div><div class='linksPaginacao'><img src='fortesrh/imgs/primeira.gif' class='desabilitaImg'>&nbsp;&nbsp;&nbsp;<img src='fortesrh/imgs/anterior.gif' class='desabilitaImg'>&nbsp;&nbsp;&nbsp; Página <input type='text' name='inputPage' value='1' id='inputPage' class='inputPage' maxLength='4' onkeydown='mudaPaginaLink(event, this.value, 5, \"list.action?page=\");'  onkeypress=\"return(somenteNumeros(event,''));\"/> de 5&nbsp;&nbsp;&nbsp;<a href='list.action?page=2' title='Próxima página'><img src='fortesrh/imgs/proxima.gif'></a>&nbsp;&nbsp;&nbsp;<a href='list.action?page=5' title='Última página'><img src='fortesrh/imgs/ultima.gif'></a></div>";
		retorno = PaginacaoUtil.makeRealPaging(1, 50, 10, "list.action?", "", url, null);
		assertEquals("test 5", stringPaginacao, retorno);

		stringPaginacao = "<div id='totalDeRegistrosEncontrados'> 50 registro(s) encontrado(s).</div><div class='linksPaginacao'><a href='list.action?page=1' title='Primeira página'><img src='fortesrh/imgs/primeira.gif'></a>&nbsp;&nbsp;&nbsp;<a href='list.action?page=1 ' title='Página anterior'><img src='fortesrh/imgs/anterior.gif'></a>&nbsp;&nbsp;&nbsp; Página <input type='text' name='inputPage' value='2' id='inputPage' class='inputPage' maxLength='4' onkeydown='mudaPaginaLink(event, this.value, 5, \"list.action?page=\");'  onkeypress=\"return(somenteNumeros(event,''));\"/> de 5&nbsp;&nbsp;&nbsp;<a href='list.action?page=3' title='Próxima página'><img src='fortesrh/imgs/proxima.gif'></a>&nbsp;&nbsp;&nbsp;<a href='list.action?page=5' title='Última página'><img src='fortesrh/imgs/ultima.gif'></a></div>";
		retorno = PaginacaoUtil.makeRealPaging(2, 50, 10, "list.action?", "", url, null);
		assertEquals("test 6", stringPaginacao, retorno);

		stringPaginacao = "<div id='totalDeRegistrosEncontrados'> 50 registro(s) encontrado(s).</div><div class='linksPaginacao'><a href='list.action?page=1' title='Primeira página'><img src='fortesrh/imgs/primeira.gif'></a>&nbsp;&nbsp;&nbsp;<a href='list.action?page=4 ' title='Página anterior'><img src='fortesrh/imgs/anterior.gif'></a>&nbsp;&nbsp;&nbsp; Página <input type='text' name='inputPage' value='5' id='inputPage' class='inputPage' maxLength='4' onkeydown='mudaPaginaLink(event, this.value, 5, \"list.action?page=\");'  onkeypress=\"return(somenteNumeros(event,''));\"/> de 5&nbsp;&nbsp;&nbsp;<img src='fortesrh/imgs/proxima.gif' class='desabilitaImg'>&nbsp;&nbsp;&nbsp;<img src='fortesrh/imgs/ultima.gif' class='desabilitaImg'></div>";
		retorno = PaginacaoUtil.makeRealPaging(5, 50, 10, "list.action?", "", url, null);
		assertEquals("test 7", stringPaginacao, retorno);

		stringPaginacao = "<div id='totalDeRegistrosEncontrados'> 5 registro(s) encontrado(s).</div>";
		retorno = PaginacaoUtil.makeRealPaging(1, 5, 10, "", "formularioTeste", url, null);
		assertEquals("test 8", stringPaginacao, retorno);

		stringPaginacao = "<div id='totalDeRegistrosEncontrados'> 10 registro(s) encontrado(s).</div>";
		retorno = PaginacaoUtil.makeRealPaging(1, 10, 10, "", "formularioTeste", url, null);
		assertEquals("test 9", stringPaginacao, retorno);
		
		retorno = PaginacaoUtil.makeRealPaging(0, 0, 10, "", "formularioTeste", url, null);
		assertEquals("test 10", "", retorno);
	}
}
