<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
	</style>
	<#assign showFilter = true/>
	<#include "../ftl/showFilterImports.ftl" />
	

	<#assign validarCampos="return validaFormulario('form', new Array('curso'), null)"/>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<title>Controle de Frequência</title>
</head>
<body>

	<@ww.actionmessage />

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="form" action="verTurmasCurso.action" method="POST"  onsubmit="${validarCampos}" id="formBusca">
			<@ww.select label="Curso" name="curso.id" id="curso" list="cursos" listKey="id"  required="true" listValue="nome" headerValue="[Selecione o Curso]" headerKey="" cssStyle="width: 900px;"/>
			<@ww.hidden id="pagina" name="page"/>
		</@ww.form>
		<button onclick="${validarCampos};" class="btnPesquisar grayBGE" accesskey="V">
		</button>
	<#include "../util/bottomFiltro.ftl" />
	<br><br>
	<#if turmas?exists && 0 < turmas?size>
		<@display.table name="turmas" id="turma" class="dados">
			<@display.column title="Ações" class="acao">
				<a href="javascript: executeLink('preparePresenca.action?turma.id=${turma.id}&curso.id=${curso.id}&voltarPara=verTurmasCurso.action');"><img border="0" title="Lista de Freqüência" src="<@ww.url value="/imgs/check.gif"/>"></a>
			</@display.column>
			<@display.column property="descricao" title="Descrição"/>

			<@display.column title="Período" style="text-align:center; width:180px">
				${turma.dataPrevIni?string("dd'/'MM'/'yyyy")} - ${turma.dataPrevFim?string("dd'/'MM'/'yyyy")}
			</@display.column>
		</@display.table>

		<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="verTurmasCurso.action?curso.id=${curso.id}&" page='${page}' idFormulario='formBusca'/>

	</#if>
</body>
</html>