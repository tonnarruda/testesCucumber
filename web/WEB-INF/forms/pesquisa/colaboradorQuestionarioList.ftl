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
    
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>
</head>
<body>

    <@ww.actionmessage />
    <@ww.actionerror />

    <#include "../util/topFiltro.ftl" />
        <@ww.form name="formBusca" id="formBusca" action="list.action" method="POST">

            <@ww.select label="Empresa" name="empresaId" id="empresaId" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="-1" liClass="liLeft"/>
            <@ww.select label="Respondida" name="respondida" id="respondida" list=r"#{'S':'Sim','N':'Não'}" headerValue="Todas" headerKey="T"/>
            <@ww.hidden name="questionario.id"/>

            <input type="submit" value="" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;">
        </@ww.form>
    <#include "../util/bottomFiltro.ftl" />
    <br>

	<#if colaboradorQuestionarios?exists && 0 < colaboradorQuestionarios?size>
		<@display.table name="colaboradorQuestionarios" id="colaboradorQuestionario" class="dados">
			<@display.column title="Ações" class="acao">

				<#if !questionario.liberado>

					<img border="0" title="Pesquisa não liberada." src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>" style="opacity:0.2;filter:alpha(opacity=20);">

					<img border="0" title="Responder a pesquisa por este colaborador (a Pesquisa precisa ser liberada antes)" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">

					<a href="#" onclick="if (confirm('Confirma exclusão?')) window.location='delete.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}&questionario.id=${questionario.id}'"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>

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

						<a href="#" onclick="if (confirm('Confirma exclusão?')) window.location='delete.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}&questionario.id=${questionario.id}'"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>

					</#if>

				</#if>

			</@display.column>
			<@display.column property="colaborador.nomeComercial" title="Colaborador"/>
			<@display.column property="colaborador.contato.email" title="E-mail"/>
			<@display.column property="colaborador.empresa.nome" title="Empresa" style="width:200px;"/>
		</@display.table>

		${colaboradorQuestionarios?size} registro(s) encontrado(s).
		<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
		
	</#if>

	<div class="buttonGroup">
		<button onclick="window.location='prepareInsert.action?questionario.id=${questionario.id}'" id="btnInserir" class="btnInserir" ></button>

		<button onclick="window.location='imprimirColaboradores.action?questionario.id=${questionario.id}&empresaId=' + jQuery('#empresaId').val() + '&respondida=' + jQuery('#respondida').val()" class="btnImprimirPdf" ></button>

		<#-- Monta o botão de acordo com o destino pesquisa, avaliação, entrevista-->
		<#if urlVoltar?exists && !exibirBotaoConcluir>
			<button class="btnVoltar" onclick="window.location='${urlVoltar}'"></button>
		</#if>
		<#if exibirBotaoConcluir>
			<button onclick="window.location='../pesquisa/list.action'" id="btnVoltar" class="btnConcluir" ></button>
		</#if>
	</div>
</body>
</html>