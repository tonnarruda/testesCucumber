<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>
	<title>Solicitações de Pessoal</title>
	
	<#include "../ftl/mascarasImports.ftl" />
	<#assign validarCamposSuspende = "return validaFormulario('formSuspendeSolicitacao', new Array('obsSuspensao'), null)"/>
	<#assign validarCamposEncerra = "return validaFormulario('formEncerraSolicitacao', new Array('dataEncerramento'), new Array('dataEncerramento'))"/>
	
	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<style>
	.dataEncerramentoDiv
	{
		border: 1px solid #B0B0B0;
		padding: 5px;
		position:absolute;
		top:10px;
		left:50%;
		margin-left: -90px;
		background-color:#F0F0F0;
		width: 180px;
		top:50%;
		margin-top:-50px;
		display:none;
	}
	.suspendeDiv
	{
		border: 1px solid #B0B0B0;
		padding: 5px;
		position:absolute;
		top:10px;
		left:25%;
		margin-left: -10px;
		background-color:#F0F0F0;
		width: 445px;
		top:50%;
		margin-top:-50px;
		display:none;
	}
	.dataEncerramentoDiv *
	{
		font-size: 11px;
	}
	#modal
	{
		display:none;
		background:silver none repeat scroll 0%;
		height:700px;
		width:100%;
		left:0px;
		top:0px;
		opacity:0.65;
		-moz-opacity: 0.65;
		filter: alpha(opacity=65);
		position:absolute;
	}
	</style>

</head>

<body>

	<@ww.actionmessage />
	<@ww.actionerror />

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="form" action="list.action" validate="true" onsubmit="setPage();" method="POST" id="formBusca">
			<@ww.select id="visualizacao" label="Visualizar" name="visualizar" list=r"#{'T':'Todas','A':'Abertas em andamento','S':'Abertas suspensas','E':'Encerradas'}" /><br>
			<@ww.select id="cargo" label="Cargo" name="cargo.id" list="cargos" listKey="id" listValue="nome" headerValue="Todos" headerKey="-1" /><br>
			<@ww.hidden id="pagina" name="page"/>

			<input type="submit" value="" class="btnPesquisar grayBGE" accesskey="B">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br />

	<div id="legendas" align="right"></div>
	<br />

	<script>
		var obj = document.getElementById("legendas");
		obj.innerHTML += "&nbsp;&nbsp;<span style='background-color: #009900;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Abertas em andamento";
		obj.innerHTML += "&nbsp;&nbsp;<span style='background-color: #002EB8;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Abertas suspensas";
		obj.innerHTML += "&nbsp;&nbsp;<span style='background-color: #555;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Encerradas";

		function encerraSolicitacao(fraseConfirma, actionEncerra, solicitacaoId)
		{
			newConfirm(fraseConfirma, function(){
				var modal = document.getElementById("modal");
				modal.style.display="block";
				var janela = document.getElementById("dataEncerramentoDiv");
				janela.style.display="block";
				document.getElementById("solicitacaoId").value=solicitacaoId;
				document.getElementById("dataEncerramento").focus();
				var combo = document.getElementById("visualizacao");
				combo.style.display='none';
			});
		}

		function suspenderSolicitacao(fraseConfirma, solicitacaoId)
		{
			newConfirm(fraseConfirma, function(){
				var modal = document.getElementById("modal");
				modal.style.display="block";
				var janela = document.getElementById("suspendeDiv");
				janela.style.display="block";
				document.getElementById("solicitacaoIdSuspender").value=solicitacaoId;
				document.getElementById("obsSuspensao").focus();
				var combo = document.getElementById("visualizacao");
				combo.style.display='none';
			});
		}

		function setPage()
		{
			document.getElementById("pagina").value=1;
		}
		
	</script>

	<@display.table name="solicitacaos" id="solicitacao" class="dados">
		<#if solicitacao.encerrada>
			<#assign classe=""/>
			<#assign fraseConfirma="Deseja reabrir esta solicitação?"/>
			<#assign titleEncerra="Reabrir Solicitação"/>
			<#assign imgEncerra="flag_green.gif"/>
			<#assign actionEncerra="reabrirSolicitacao.action"/>
		<#else>
			<#assign classe="solicitacaoEncerrada"/>
			<#if solicitacao.suspensa>
				<#assign classe="solicitacaoSuspensa"/>
			</#if>
			<#assign fraseConfirma="Deseja encerrar esta solicitação?"/>
			<#assign titleEncerra="Encerrar Solicitação"/>
			<#assign imgEncerra="flag_red.gif"/>
			<#assign actionEncerra="encerrarSolicitacao.action"/>
		</#if>
		<@display.column title="Ações" media="html" class="acao" style="width: 150px; text-align:left;">
			<a href="imprimirSolicitacaoPessoal.action?solicitacao.id=${solicitacao.id}"><img border="0" title="<@ww.text name="list.print.hint"/>" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
			<@authz.authorize ifNotGranted="ROLE_MOV_SOLICITACAO_SELECAO">
				<#if !solicitacao.liberada && !solicitacao.encerrada>
					<a href="prepareUpdate.action?solicitacao.id=${solicitacao.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
					<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?solicitacao.id=${solicitacao.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
				<#else>
					<img border="0" title="Não é possível editar a solicitação. Esta já foi aprovada ou encerrada." src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
					<img border="0" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				</#if>
			</@authz.authorize>
			<@authz.authorize ifAllGranted="ROLE_MOV_SOLICITACAO_SELECAO">
				<#if !solicitacao.encerrada>
					<a href="prepareUpdate.action?solicitacao.id=${solicitacao.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
					<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?solicitacao.id=${solicitacao.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>

					<#if solicitacao.anuncio?exists>
						<#if solicitacao.anuncio.exibirModuloExterno>
							<a href="../anuncio/anunciar.action?solicitacao.id=${solicitacao.id}"><img border="0" title="Anunciado" src="<@ww.url includeParams="none" value="/imgs/cliper_checked.gif"/>"></a>
						<#else>
							<a href="../anuncio/anunciar.action?solicitacao.id=${solicitacao.id}"><img border="0" title="Anunciar" src="<@ww.url includeParams="none" value="/imgs/cliper.gif"/>"></a>
						</#if>
					</#if>

					<a href="#" onclick="encerraSolicitacao('${fraseConfirma}', '${actionEncerra}', '${solicitacao.id}');"><img border="0" title="${titleEncerra}" src="<@ww.url includeParams="none" value="/imgs/${imgEncerra}"/>"></a>
					<#if !solicitacao.suspensa>
						<a href="#" onclick="suspenderSolicitacao('Deseja suspender esta solicitação?', '${solicitacao.id}');"><img border="0" title="Suspender solicitação" src="<@ww.url includeParams="none" value="/imgs/control_pause.gif"/>"></a>
					<#else>
						<a href="liberarSolicitacao.action?solicitacao.id=${solicitacao.id}"><img border="0" title="Liberar solicitação" src="<@ww.url includeParams="none" value="/imgs/control_play.gif"/>"></a>
					</#if>
				<#else>
					<img border="0" title="Não é possível editar a solicitação. Esta já foi aprovada ou encerrada." src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
					<img border="0" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
					<img border="0" title="Não é possível anunciar a solicitação. Esta já foi encerrada." src="<@ww.url includeParams="none" value="/imgs/cliper.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
					<a href="#" onclick="newConfirm('${fraseConfirma}', function(){window.location='${actionEncerra}?solicitacao.id=${solicitacao.id}'});"><img border="0" title="${titleEncerra}" src="<@ww.url includeParams="none" value="/imgs/${imgEncerra}"/>"></a>
					<img border="0" title="Não é possível suspender a solicitação. Esta já foi encerrada." src="<@ww.url includeParams="none" value="/imgs/control_pause.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				</#if>
			</@authz.authorize>
			<a href="prepareClonar.action?solicitacao.id=${solicitacao.id}"><img border="0" title="Clonar" src="<@ww.url includeParams="none" value="/imgs/clonar.gif"/>"></a>
			
		<@authz.authorize ifAllGranted="ROLE_MOV_SOLICITACAO_CANDIDATO">		
			<a href="../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}"><img border="0" title="Candidatos da Seleção" src="<@ww.url includeParams="none" value="/imgs/usuarios.gif"/>"></a>
		</@authz.authorize>
		
		</@display.column>
		<@display.column title="Cargo" class="${classe}">
			<#if solicitacao.obsSuspensao?exists && solicitacao.obsSuspensao?trim != "">
				<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${solicitacao.obsSuspensao?j_string}');return false">${solicitacao.faixaSalarial.cargo.nome}</span>
			<#else>
				<#if solicitacao.faixaSalarial.cargo.nome?exists>
					${solicitacao.faixaSalarial.cargo.nome}
				</#if>
			</#if>
		</@display.column>
		<@display.column title="Descrição" property="descricao" class="${classe}"/>
		<@display.column property="motivoSolicitacao.descricao" title="Motivo" class="${classe}"/>
		<@display.column property="estabelecimento.nome" title="Estabelecimento" class="${classe}"/>
		<@display.column property="areaOrganizacional.nome" title="Área" class="${classe}"/>
		<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width:70px;" class="${classe}"/>

		<#-- <@authz.authorize ifAllGranted="ROLE_LIBERA_SOLICITACAO"> -->
			<@display.column title="Aprovada" style="width:43px;" class="${classe}">
				<#if solicitacao.liberada> Sim <#else> Não </#if>
			</@display.column>
		<#--/@authz.authorize -->

	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="formBusca"/>

	<div id="modal">
	</div>
	<div id="dataEncerramentoDiv" class="dataEncerramentoDiv">
		<@ww.form name="formEncerraSolicitacao" id="formEncerraSolicitacao" action="encerrarSolicitacao.action" validate="true" method="POST" onsubmit="${validarCamposEncerra}" >
			<@ww.datepicker label="Data de Encerramento" name="dataEncerramento" id="dataEncerramento"  cssClass="mascaraData" />
			<@ww.hidden name="solicitacao.id" id="solicitacaoId"/>
		</@ww.form>
		<button onclick="${validarCamposEncerra};" class="btnEncerrarSolicitacao grayBG">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar grayBG">
		</button>
	</div>

	<div id="suspendeDiv" class="suspendeDiv">
		<@ww.form name="formSuspendeSolicitacao" id="formSuspendeSolicitacao" action="suspenderSolicitacao.action" validate="true" method="POST" onsubmit="${validarCamposSuspende}" >
			<@ww.textarea label="Observações sobre a suspensão" name="solicitacao.obsSuspensao" id="obsSuspensao" />
			<@ww.hidden name="solicitacao.id" id="solicitacaoIdSuspender"/>
		</@ww.form>
		<button onclick="${validarCamposSuspende};" class="btnSuspenderSolicitacao grayBG">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar grayBG">
		</button>
	</div>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
		</button>
	</div>
</body>
</html>