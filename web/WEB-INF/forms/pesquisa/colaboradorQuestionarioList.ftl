<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
<head>
<@ww.head/>
	<title>Colaboradores da ${tipoQuestionario.getDescricaoMaisc(questionario.tipo)}</title>

    <#include "../ftl/mascarasImports.ftl" />
    <#include "../ftl/showFilterImports.ftl" />

    <#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
    
    <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
    <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
    
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
	</style>
	
	<script type="text/javascript">
		function populaEstabelecimento(empresaId)
		{
			var empresaIds = new Array(); 
			$('#empresaId option').each(function() {
				if($(this).val() != null && $(this).val() != -1)
					empresaIds.push($(this).val());
			});
			
			dwr.util.useLoadingMessage('Carregando...');
			EstabelecimentoDWR.getByEmpresas(empresaId, empresaIds, createListEstabelecimento);
		}

		function createListEstabelecimento(data)
		{
			addChecks('estabelecimentosCheck',data);
		}
	</script>
</head>
<body>

    <@ww.actionmessage />
    <@ww.actionerror />

    <#include "../util/topFiltro.ftl" />
        <@ww.form name="formBusca" id="formBusca" action="list.action" method="POST">

            <#if compartilharColaboradores>
	            <@ww.select label="Empresa" name="empresaId" id="empresaId" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="-1" liClass="liLeft"  disabled="!compartilharColaboradores" onchange="populaEstabelecimento(this.value);"/>
			<#else>
				<@ww.hidden id="empresaId" name="empresaId"/>
				<li class="wwgrp">
					<label>Empresa:</label><br />
					<strong><@authz.authentication operation="empresaNome"/></strong>
				</li>
			</#if>
            
            <@ww.select label="Respondida" name="respondida" id="respondida" list=r"#{'S':'Sim','N':'Não'}" headerValue="Todas" headerKey="T"/>
            <@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" width="600" filtro="true"/>
            <@ww.hidden name="questionario.id"/>

            <input type="submit" value="" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;">
        </@ww.form>
    <#include "../util/bottomFiltro.ftl" />
    <br>

	<#if colaboradorQuestionarios?exists && 0 < colaboradorQuestionarios?size>
		<@ww.form name="form" action="deleteColaboradores.action" method="POST">
            <@ww.hidden name="questionario.id"/>
            
			<@display.table name="colaboradorQuestionarios" id="colaboradorQuestionario" class="dados">
	
				<@display.column title="<input type='checkbox' id='md' onclick='atualizaChecks(\"checkColaborador\", this.checked);' />" style="width: 26px; text-align: center;">
					<#if colaboradorQuestionario.respondida>
						<input type="checkbox" disabled="disabled" title="Pesquisa já respondida. Não é possível realizar alterações."/>
					<#else>
						<input type="checkbox" class="checkColaborador" value="${colaboradorQuestionario.id}" name="colaboradorQuestionarioIds"/>
					</#if>
					<!-- Marca a posicao da tela -->
					<a name="c${colaboradorQuestionario.colaborador.id}">
				</@display.column>
	
				<@display.column title="Ações" style="width:60px;text-align:center">
					<#if !questionario.liberado>
	
						<img border="0" title="Pesquisa não liberada." src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>" style="opacity:0.2;filter:alpha(opacity=20);">
	
						<img border="0" title="Responder a pesquisa por este colaborador (a Pesquisa precisa ser liberada antes)" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
	
						<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}&questionario.id=${questionario.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
	
					<#else>
						<#if colaboradorQuestionario.respondida>
	
							<#if questionario.anonimo?exists && questionario.anonimo>
								<img border="0" title="Pesquisa Anônima." src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>" style="opacity:0.2;filter:alpha(opacity=20);">
							<#else>
								<a href="visualizarRespostasPorColaborador.action?questionario.id=${questionario.id}&colaboradorId=${colaboradorQuestionario.colaborador.id}"><img border="0" title="Visualizar respostas do colaborador" src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>"></a>
							</#if>
	
							<img border="0" title="Pesquisa já respondida. Não é possível editar esta pesquisa." src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
	
							<img border="0" title="Pesquisa já respondida. Não é possível excluir colaboradores." src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
	
						<#else>
							<img border="0" title="Esta pesquisa ainda não foi respondida." src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>" style="opacity:0.2;filter:alpha(opacity=20);">
	
							<@authz.authorize ifNotGranted="ROLE_RESPONDER_PESQUISA_POR_OUTRO_USUARIO">
								<img border="0" title="Você não tem permissão para responder a pesquisa por este colaborador." src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
							</@authz.authorize>
	
							<@authz.authorize ifAllGranted="ROLE_RESPONDER_PESQUISA_POR_OUTRO_USUARIO">
								<a href="../colaboradorResposta/prepareResponderQuestionarioPorOutroUsuario.action?questionario.id=${questionario.id}&colaborador.id=${colaboradorQuestionario.colaborador.id}&validarFormulario=true&voltarPara=../colaboradorQuestionario/list.action?questionario.id=${questionario.id}&tela=colaboradorQuestionarioList"><img border="0" title="Responder a pesquisa por este colaborador" src="<@ww.url value="/imgs/edit.gif"/>"></a>
							</@authz.authorize>
	
							<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}&questionario.id=${questionario.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
						</#if>
					</#if>
				</@display.column>
				<@display.column property="colaborador.nomeComercial" title="Colaborador"/>
				<@display.column property="colaborador.areaOrganizacional.nome" title="Área Organizacional"/>
				<@display.column property="colaborador.estabelecimento.nome" title="Estabelecimento"/>
				<@display.column property="colaborador.empresa.nome" title="Empresa" style="width:200px;"/>
			</@display.table>
		</@ww.form>
		
		<span style="float:right;">${colaboradorQuestionarios?size} colaboradores/registros. Respondeu Pesquisa: ${totalRespondidas}. Não Respondeu: ${totalNaoRespondidas}</span>
		<div style="clear:both;"></div>
		<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
		
	</#if>

	<div class="buttonGroup">
		<button onclick="window.location='prepareInsert.action?questionario.id=${questionario.id}'" id="btnInserir" class="btnInserir" ></button>
		<button onclick="javascript: newConfirm('Confirma exclusão dos colaboradores selecionados?', function(){document.form.submit();});" class="btnExcluir"></button>
		
		<button onclick="window.location='imprimirColaboradores.action?questionario.id=${questionario.id}&empresaId=' + $('#empresaId').val() + '&respondida=' + $('#respondida').val()" class="btnImprimirPdf" ></button>
		<button onclick="window.location='imprimirColaboradoresXls.action?questionario.id=${questionario.id}&empresaId=' + $('#empresaId').val() + '&respondida=' + $('#respondida').val()" class="btnRelatorioExportar"></button>

		<#-- Monta o botão de acordo com o destino pesquisa, avaliação, entrevista-->
		<#if urlVoltar?exists && !exibirBotaoConcluir>
			<button class="btnVoltar" onclick="window.location='${urlVoltar}'"></button>
		</#if>
		<#if exibirBotaoConcluir>
			<button onclick="window.location='../pesquisa/list.action'" id="btnVoltar" class="btnConcluir" ></button>
		</#if>
		
		<#if questionario.liberado>
			<button onclick="window.location='../colaboradorResposta/prepareResponderQuestionarioEmLote.action?questionario.id=${questionario.id}'" id="btnAvancar" class="btnResponderPorColaboradores"></button>
		<#else>
			<img border="0" title="Pesquisa não liberada." src="<@ww.url includeParams="none" value="/imgs/btnResponderPorColaboradores.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);" align="absMiddle"/>
		</#if>
	</div>
</body>
</html>