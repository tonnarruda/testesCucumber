<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		
		ul.aviso { list-style-type: disc; list-style-position: outside; margin-left: 20px; }
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

	<#if usuarioLogado?exists && usuarioLogado.id == 1>
		<#assign avisoExclusao>
			Procedimento diferenciado para o usuário <strong>${usuarioLogado.nome}</strong>:
			<ul class=aviso>
				<li>A área organizacional será excluída permanentemente com as suas dependências.</li>
				<li>Existem algumas dependências que não podem ser excluídas automaticamente. Se estas existirem, será exibido um alerta e a área não será excluída.</li>
				<li>Essa exclusão não poderá ser desfeita.</li>
				<li>Recomendamos que seja efetuado um backup antes da realização desse procedimento.</li>
				<li>A lotação correspondente não será removida do AC Pessoal, mesmo que os sistemas estejam integrados.</li>
			</ul>
			Deseja realmente continuar?
		</#assign>
	<#else>
		<#assign avisoExclusao="Confirma exclusão?"/>
	</#if>

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
			<@authz.authorize ifAllGranted="ROLE_CAD_AREA_EDITAR">
				<a href="prepareUpdate.action?areaOrganizacional.id=${areaOrganizacional.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			</@authz.authorize>
			<@authz.authorize ifAllGranted="ROLE_CAD_AREA_EXCLUIR">
				<a href="#" onclick="newConfirm('${avisoExclusao?js_string}', function(){window.location='delete.action?areaOrganizacional.id=${areaOrganizacional.id}&areaOrganizacional.empresa.id=${areaOrganizacional.empresa.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
			</@authz.authorize>
		</@display.column>
		<@display.column property="descricao" title="Descrição"/>
		<@display.column property="responsavel.nomeComercial" title="Responsável"/>
		<@display.column property="coResponsavel.nomeComercial" title="Corresponsável"/>

		<#if integradoAC?exists && integradoAC>
			<@display.column property="codigoAC" title="Código AC" />
		</#if>
	</@display.table>

	<div class="buttonGroup">
		<@authz.authorize ifAllGranted="ROLE_CAD_AREA_INSERIR">
			<button type="button" class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I"></button>
		</@authz.authorize>
		<button class="btnImprimir" onclick="imprimir();" accesskey="P"></button>
		<button type="button"class="btnOrganograma" onclick="window.location='organograma.action'" accesskey="O"></button>
	</div>
</body>
</html>