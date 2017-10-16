<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	@import url('<@ww.url value="/css/buttons.css"/>');
</style>
<#include "../ftl/showFilterImports.ftl" />
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
<title>Riscos</title>

	<script type="text/javascript">
		function pesquisar()
		{
			$('#pagina').val(1);
			$('#formBusca').attr('action','list.action');
			$('#formBusca').submit();
		}
	</script>

</head>
<body>
	<@ww.actionerror/>
	<@ww.actionmessage/>
	
	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" method="POST" id="formBusca">
			<@ww.textfield label="Descricação do risco" name="risco.descricao" cssStyle="width: 502px;"/>
			<@ww.select label="Tipo de Risco" name="risco.grupoRisco" id="grupoRisco" list="grupoRiscos" cssStyle="width: 502px;" headerKey="" headerValue="Todos" required="true"/>
			<@ww.select label="Tipo de Risco eSocial" name="risco.grupoRiscoESocial" id="grupoRiscoESocial" list="grupoRiscoESocialListagemDeRiscos" cssStyle="width: 502px;" headerKey="" headerValue="Todos" required="true"  onchange="onchangeGrupoRiscoESocial();"/>
			
			<@ww.hidden id="pagina" name="page"/>
			<@ww.hidden id="showFilter" name="showFilter"/>
			<input type="submit" value="" class="btnPesquisar grayBGE" onclick="pesquisar();">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>
	
	<@display.table name="riscos" id="risco" class="dados">
		<@display.column title="Ações" class="acao" style="width:52px;">
					
			<a href="prepareUpdate.action?risco.id=${risco.id}"><i title="Editar" class="fa fa-edit" aria-hidden="true" style="font-size: 15px; color: black;"></i></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?risco.id=${risco.id}&page=${page}'});"><i title="Excluir" class="fa fa-times" aria-hidden="true" style="font-size: 18px; color: black;"></i></a>
		</@display.column>
		<@display.column property="descricao" title="Risco" style="width:300px;"/>
		<@display.column property="descricaoGrupoRisco" title="Tipo de Risco" style="width:100px;"/>
		<@display.column property="descricaoGrupoRiscoESocial" title="Tipo de Risco eSocial" style="width:150px;"/>
		<@display.column property="fatorDeRisco.descricaoComCodigo" title="Fator de Risco eSocial"/>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
		<button type="button" onclick="window.location='prepareInsert.action'" accesskey="I">Inserir</button>
	</div>

</body>
</html>