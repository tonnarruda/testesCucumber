<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		#formDialog { display: none; width: 600px; }
	</style>
	
	<#include "../ftl/showFilterImports.ftl" />
	
	<#assign validarCampos="return validaFormulario('formBusca', null, null, true)"/>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<title>Pesquisas</title>
	
	<script type='text/javascript'>
		function clonar(pesquisaId, titulo)
		{
			$('#pesquisaId').val(pesquisaId);
			$('#formDialog').dialog({ modal: true, width: 530, title: 'Clonar: ' + titulo });
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />  

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="list.action" onsubmit="${validarCampos}" validate="true" method="POST" id="formBusca">
			<@ww.textfield label="Título" name="questionarioTitulo" cssStyle="width: 350px;"/>
			<@ww.select label="Status" id="status" name="questionarioLiberado" list=r"#{'T':'Todas','L':'Liberadas','N':'Não liberadas'}"/>
			<@ww.hidden id="pagina" name="page"/>
			<input type="submit" value="" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>
	<@display.table name="pesquisas" id="pesquisa" class="dados">
		<@display.column title="Ações" style="width:230px">
		
			<#if !pesquisa.questionario.liberado>
				<a href="javascript:newConfirm('Deseja liberar esta pesquisa?', function(){executeLink('../questionario/liberar.action?questionario.id=${pesquisa.questionario.id}');});"><img border="0" title="Liberar Pesquisa" src="<@ww.url includeParams="none" value="/imgs/liberar.gif"/>"></a>
				<img border="0" title="Não é possível enviar e-mail para pesquisa bloqueada." src="<@ww.url includeParams="none" value="/imgs/icon_email.gif"/>" style="opacity:0.3;filter:alpha(opacity=30);">
			<#else>
				<img border="0" title="Liberar Pesquisa - Esta pesquisa já está liberada." src="<@ww.url includeParams="none" value="/imgs/liberar.gif"/>" style="opacity:0.3;filter:alpha(opacity=30);">
				<a href="javascript:newConfirm('Deseja enviar e-mail de lembrete para os colaboradores que ainda não respoderam esta pesquisa?', function(){executeLink('../questionario/enviarLembrete.action?questionario.id=${pesquisa.questionario.id}');});"><img border="0" title="Enviar e-mail de Lembrete" src="<@ww.url includeParams="none" value="/imgs/icon_email.gif"/>"></a>
			</#if>
			
			<#if pesquisa.questionario.aplicarPorAspecto>
				<a href="javascript: executeLink('../questionario/prepareAplicarByAspecto.action?questionario.id=${pesquisa.questionario.id}&preview=true');"><img border="0" title="Visualizar pesquisa" src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>"></a>
			<#else>
				<a href="javascript: executeLink('../questionario/prepareAplicar.action?questionario.id=${pesquisa.questionario.id}&preview=true');"><img border="0" title="Visualizar pesquisa" src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>"></a>
			</#if>
			
			<a href="javascript: executeLink('prepareUpdate.action?pesquisa.id=${pesquisa.id}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<#if !pesquisa.questionario.liberado>
				<a href="javascript: executeLink('../pergunta/list.action?questionario.id=${pesquisa.questionario.id}');"><img border="0" title="Questionário da pesquisa" src="<@ww.url includeParams="none" value="/imgs/question.gif"/>"></a>
				<a href="javascript: executeLink('../aspecto/list.action?questionario.id=${pesquisa.questionario.id}');"><img border="0" title="Aspectos da pesquisa" src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>"></a>
			<#else>
				<img border="0" title="Questionário da pesquisa - Pesquisa já liberada, não é permitido editar" src="<@ww.url includeParams="none" value="/imgs/question.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				<img border="0" title="Aspectos da pesquisa - Pesquisa já liberada, não é permitido editar" src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			</#if>
			
			<a href="javascript: executeLink('../colaboradorQuestionario/list.action?questionario.id=${pesquisa.questionario.id}');"><img border="0" title="Colaboradores" src="<@ww.url includeParams="none" value="/imgs/usuarios.gif"/>"></a>
			<a href="javascript: executeLink('../questionario/imprimir.action?questionario.id=${pesquisa.questionario.id}');"><img border="0" title="Imprimir pesquisa" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
			<a href="javascript: executeLink('../questionario/imprimir.action?questionario.id=${pesquisa.questionario.id}&imprimirFormaEconomica=true');"><img border="0" title="Imprimir pesquisa em formato econômico" src="<@ww.url includeParams="none" value="/imgs/iconPrint.gif"/>"></a>
			<a href="javascript: executeLink('../questionario/prepareResultado.action?questionario.id=${pesquisa.questionario.id}');"><img border="0" title="Relatório da Pesquisa" src="<@ww.url includeParams="none" value="/imgs/grafico_pizza.gif"/>"></a>
			<a href="javascript:;" onclick="javascript:clonar(${pesquisa.id}, '${pesquisa.questionario.titulo}')"><img border="0" title="Clonar" src="<@ww.url includeParams="none" value="/imgs/clonar.gif"/>"></a>
			<a href="javascript:newConfirm('Confirma exclusão?', function(){executeLink('delete.action?pesquisa.id=${pesquisa.id}&pesquisa.questionario.empresa.id=${pesquisa.questionario.empresa.id}&page=${page}');});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>

		</@display.column>

		<@display.column property="questionario.titulo" title="Título" />

		<@display.column title="Período" style="width: 140px;">
			<#if pesquisa?exists && pesquisa.questionario?exists && pesquisa.questionario.dataInicio?exists && pesquisa.questionario.dataFim?exists>
				${pesquisa.questionario.dataInicio?string("dd'/'MM'/'yyyy")} a ${pesquisa.questionario.dataFim?string("dd'/'MM'/'yyyy")}
			</#if>
		</@display.column>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action');" accesskey="I">
		</button>
	</div>
	
	<div id="formDialog">
		<@ww.form name="formModal" id="formModal" action="clonarPesquisa.action" method="POST">
			<@frt.checkListBox label="Selecione as empresas para as quais deseja clonar esta pesquisa" name="empresasCheck" list="empresasCheckList" form="document.getElementById('formModal')" filtro="true"/>
			* Caso nenhuma empresa seja selecionada, a pesquisa será clonada apenas para a empresa <@authz.authentication operation="empresaNome"/><br>
			<@ww.hidden name="pesquisa.id" id="pesquisaId"/>
			<button class="btnClonar" type="submit"></button>
		</@ww.form>
	</div>
</body>
</html>