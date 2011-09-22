<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/jquery-ui/jquery-ui-1.8.9.custom.css"/>');
	
		#formDialog { display: none; width: 600px; }
	</style>

	<title>Avaliações de Desempenho</title>
	
	<#include "../ftl/showFilterImports.ftl" />
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
	<script type='text/javascript'>
		function clonar(avaliacaoDesempenhoId, titulo)
		{
			$('#avaliacaoDesempenhoId').val(avaliacaoDesempenhoId);
			$('#formDialog').dialog({ modal: true, width: 530, title: 'Clonar: ' + titulo });
		}
	</script>
	
	<#assign validarCampos="return validaFormulario('formBusca', null, null)"/>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="list.action" onsubmit="${validarCampos}" validate="true" method="POST" id="formBusca">
			<@ww.textfield label="Título" name="nomeBusca" id="nomeBusca" liClass="liLeft" cssStyle="width: 350px;"/>
			<@ww.select label="Modelo." name="avaliacaoId" list="avaliacaos" listKey="id" listValue="titulo" headerValue="selecione..." headerKey=""/>
			<input type="submit" value="" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>
		
	<@display.table name="avaliacaoDesempenhos" id="avaliacaoDesempenho" class="dados">
		<@display.column title="Ações" class="acao" style="width:180px;">
		
			<a href="prepareUpdate.action?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="prepareAvaliados.action?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}"><img border="0" title="Participantes" src="<@ww.url includeParams="none" value="/imgs/usuarios.gif"/>"></a>
			
			<#if avaliacaoDesempenho.liberada>
				<a href="javascript:newConfirm('Deseja bloquear esta Avaliação?', function(){window.location='bloquear.action?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}'});"><img border="0" title="Bloquear" src="<@ww.url includeParams="none" value="/imgs/bloquear.gif"/>"></a>
				<a href="prepareResultado.action?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}"><img border="0" title="Resultado da Avaliação" src="<@ww.url includeParams="none" value="/imgs/grafico_pizza.gif"/>"></a>
				<a href="javascript:newConfirm('Deseja enviar e-mail de lembrete para os colaboradores que ainda não respoderam esta avaliação desempenho?', function(){window.location='enviarLembrete.action?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}'});"><img border="0" title="Enviar e-mail de Lembrete" src="<@ww.url includeParams="none" value="/imgs/icon_email.gif"/>"></a>
			<#else>
				<a href="javascript:newConfirm('Deseja liberar esta Avaliação?', function(){window.location='liberar.action?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}'});"><img border="0" title="Liberar" src="<@ww.url includeParams="none" value="/imgs/liberar.gif"/>"></a>
				<img border="0" title="Avaliação bloqueada" src="<@ww.url includeParams="none" value="/imgs/grafico_pizza.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				<img border="0" title="Avaliação bloqueada" src="<@ww.url includeParams="none" value="/imgs/icon_email.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			</#if>
			<a href="javascript:;" onclick="javascript:clonar(${avaliacaoDesempenho.id}, '${avaliacaoDesempenho.titulo}')"><img border="0" title="Clonar" src="<@ww.url includeParams="none" value="/imgs/clonar.gif"/>"></a>
			<a href="../modelo/imprimir.action?avaliacao.id=${avaliacaoDesempenho.avaliacao.id}"><img border="0" title="Imprimir Modelo da Avaliação" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
			<a href="../modelo/imprimir.action?avaliacao.id=${avaliacaoDesempenho.avaliacao.id}&imprimirFormaEconomica=true"><img border="0" title="Imprimir Modelo da Avaliação em formato econômico" src="<@ww.url includeParams="none" value="/imgs/iconPrint.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column title="Título" property="titulo" />
		<@display.column title="Período" property="periodoFormatado" />
		<@display.column title="Modelo" property="avaliacao.titulo" />
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'"></button>
	</div>
	
	<div id="formDialog">
		<@ww.form name="formModal" id="formModal" action="clonar.action" method="POST">
			<@frt.checkListBox label="Selecione as empresas para as quais deseja clonar esta avaliação" name="empresasCheck" list="empresasCheckList" form="document.getElementById('formModal')"/>
			* Caso nenhuma empresa seja selecionada, a avaliação será clonada apenas para a empresa <@authz.authentication operation="empresaNome"/><br>
			<@ww.hidden name="avaliacaoDesempenho.id" id="avaliacaoDesempenhoId"/>
			<button class="btnClonar" type="submit"></button>
		</@ww.form>
	</div>
</body>
</html>
