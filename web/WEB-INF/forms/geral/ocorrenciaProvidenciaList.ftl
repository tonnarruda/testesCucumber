<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Providências</title>

	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
		<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="list.action" method="POST" id="formBusca">
			<@ww.textfield label="Colaborador" name="colaboradorNome" cssStyle="width: 350px;"/>
			<@ww.textfield label="Ocorrência" name="ocorrenciaDescricao" cssStyle="width: 350px;"/>
			<@ww.select label="Providêcias" name="comProvidencia" list=r"#{'S':'Sem Providência', 'C':'Com Providência'}" cssStyle="width: 150px;" headerKey="T" headerValue="Todos"/>
			<@ww.hidden id="pagina" name="page"/>
			<@ww.hidden id="showFilter" name="showFilter"/>
			<input type="button" value="" onclick="document.getElementById('pagina').value = 1; document.formBusca.submit();"  class="btnPesquisar grayBGE">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>
	<@display.table name="colaboradorOcorrencias" id="colaboradorOcorrencia" class="dados">
		<@display.column title="Ações" class="acao">
			<#if colaboradorOcorrencia.providencia?exists && colaboradorOcorrencia.providencia.descricao?exists>
				<a href="prepare.action?colaboradorOcorrencia.id=${colaboradorOcorrencia.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/form.gif"/>"></a>
			<#else>
				<a href="prepare.action?colaboradorOcorrencia.id=${colaboradorOcorrencia.id}"><img border="0" title="Inserir" src="<@ww.url value="/imgs/form2.gif"/>"></a>
			</#if>
		</@display.column>
		<@display.column property="colaborador.nome" title="Colaborador"/>
		<@display.column property="ocorrencia.descricao" title="Ocorrência"/>
		<@display.column property="dataIniString" title="Início"/>
		<@display.column property="dataFimString" title="Fim"/>
		<@display.column property="providencia.descricao" title="Providência"/>
	</@display.table>
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>
</body>
</html>