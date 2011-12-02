<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>

	<#include "../ftl/showFilterImports.ftl" />
	<#assign validarCampos="return validaFormulario('formBusca', null, null, true)"/>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<title>Áreas Organizacionais</title>
	
	<script type="text/javascript">
		function pesquisar()
		{
			$('#pagina').val(1);
			$('#formBusca').attr('action','list.action');
			$('#formBusca').submit();
		}

		function imprimir()
		{ 
			$('#formBusca').attr('action','imprimirLista.action');
			$('#formBusca').submit();
		}
	</script>
	
</head>
<body>

	<@ww.actionerror />
	<@ww.actionmessage />

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="list.action" onsubmit="${validarCampos}" validate="true" method="POST" id="formBusca">
			<@ww.textfield label="Nome" name="areaOrganizacional.nome" cssStyle="width: 350px;"/>
			
			<@ww.select label="Ativas" name="ativa" list=r"#{'S':'Sim', 'N':'Não'}" cssStyle="width: 65px;" headerKey="T" headerValue="Todas"/>
			<@ww.hidden id="pagina" name="page"/>
			
			<input type="submit" value="" class="btnPesquisar grayBGE" onclick="pesquisar();">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>
	<@display.table name="areaOrganizacionals" id="areaOrganizacional" class="dados" >
		<@display.column title="Ações" style="text-align:center; width: 80px;" media="html">
			<a href="prepareUpdate.action?areaOrganizacional.id=${areaOrganizacional.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?areaOrganizacional.id=${areaOrganizacional.id}&areaOrganizacional.empresa.id=${areaOrganizacional.empresa.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="descricao" title="Descrição"/>
		<@display.column property="responsavel.nomeComercial" title="Responsável"/>

		<#if integradoAC?exists && integradoAC>
			<@display.column property="codigoAC" title="Código AC" />
		</#if>

	</@display.table>

	<div class="buttonGroup">
		<button type="button" class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
		</button>
		<button class="btnImprimir" onclick="imprimir();" accesskey="P"></button>
	</div>
</body>
</html>