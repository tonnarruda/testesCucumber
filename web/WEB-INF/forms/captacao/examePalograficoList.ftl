<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
	</style>
	<title>Exame Palográfico</title>

	<#include "../ftl/mascarasImports.ftl" />
	<#include "../ftl/showFilterImports.ftl" />
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" id="formBusca" action="prepareExamePalografico.action" method="POST">
			<@ww.textfield label="Nome" name="nomeBusca" liClass="liLeft" cssStyle="width: 350px;" value="${nomeBusca}"/>
			<@ww.textfield label="CPF" id="cpfBusca" name="cpfBusca" value="${cpfBusca}" cssClass="mascaraCpf"/>
			
			<@ww.hidden id="pagina" name="page"/>
			
			<input type="submit" value="" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>

	<@display.table name="candidatos" id="candidato" class="dados">
		<@display.column title="Ações" media="html" style="text-align:center; width: 140px;" >
			<a href="javascript: executeLink('prepareUpdateExamePalografico.action?candidato.id=${candidato.id}');"><img border="0" title="Editar Exame Palográfico" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
		</@display.column>

		<@display.column property="nome" title="Nome"/>
		<@display.column property="pessoal.cpf" title="CPF"/>

	</@display.table>

	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="formBusca"/>
</body>
</html>