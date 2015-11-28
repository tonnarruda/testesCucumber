<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<#include "../ftl/mascarasImports.ftl" />
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
		<#assign validarCampos="return validaFormulario('form', null, new Array('dataIni','dataFim'), true)"/>

	<#include "../ftl/showFilterImports.ftl" />
	<#if dataIni?exists >
		<#assign dateIni = dataIni?date/>
	<#else>
		<#assign dateIni = ""/>
	</#if>
	<#if dataFim?exists>
		<#assign dateFim = dataFim?date/>
	<#else>
		<#assign dateFim = ""/>
	</#if>

	<#if entrega>
		<title>Entrega/Devolução de EPIs</title>
	<#else><script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
		<title>Solicitações de EPIs</title>
	</#if>
	
	<script type="text/javascript">
		$(function() {
			insereHelp(4, 'Para exibir informações de EPIs Entregues e A Entregar, coloque o ponteiro do mouse sobre a situação desejada.');		
			insereHelp(5, 'Para exibir informações de EPIs Devolvidos e A Devolver, coloque o ponteiro do mouse sobre a situação desejada.');
		});
		
		
		function imprimir()
		{
			verificaSeLimpaEstabelecimentoCheck();
			
			$('#form').attr('action','imprimir.action').submit();
		}

		function pesquisar()
		{
			verificaSeLimpaEstabelecimentoCheck();
			
			$('#pagina').val(1);
			$('#form').attr('action','list.action').submit();
		}
		
		function verificaSeLimpaEstabelecimentoCheck()
		{
			$('#limpaEstabelecimentoCheck').val(true);
			$("#form").find("input[name='estabelecimentoCheck']").each(function(i, item) {
				if(item.checked){
					$('#limpaEstabelecimentoCheck').val(false);
				}
			});
		}
	
		function insereHelp(posicao, help)
		{
			var id = "tooltipHelp" + posicao;
			$("#solicitacaoEpi th:eq(" + posicao + ")" ).append('<img id="' + id + '" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: 1px" />');
			
			$('#' + id).qtip({
				content: help
			});
		}
		
		
		
	</script>
</head>
<body>

	<#include "../util/topFiltro.ftl" />
	<@ww.form name="form" id="form" action="list.action" onsubmit="${validarCampos}" method="POST">

		<@ww.select label="Situação da Solicitação" id="situacao" name="situacao" list=r'#{"T":"Todas","A":"Aberta","E":"Entregue","P":"Entregue Parcialmente","D":"Devolvido", "DP":"Devolvido Parcialmente","S":"Sem Devolução"}' />
		<@frt.checkListBox label="Estabelecimento" name="estabelecimentoCheck" id="estabelecimentoCheck" list="estabelecimentoCheckList" filtro="true"/>
		<@ww.textfield label="Matrícula do Colaborador" name="matriculaBusca" id="matriculaBusca" cssStyle="width: 60px;"/>
		<@ww.textfield label="Nome do Colaborador" name="nomeBusca" id="nomeBusca" cssStyle="width: 260px;"/>
		<@ww.select label="Situação do Colaborador" name="situacaoColaborador" id="situacaoColaborador" list="situacoesDoColaborador" cssStyle="width: 165px;"/>
		Período da Solicitação:<br/>
		<@ww.datepicker name="dataIni" id="dataIni"  value="${dateIni}" liClass="liLeft" cssClass="mascaraData"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData" />
		<@ww.select label="Categorias de EPI" name="tipoEpi" id="tipoEpi" listKey="id" listValue="nome" list="tipoEpis" headerKey="" headerValue="Selecione..." />
		<@ww.select label="Ordenação" id="ordem" name="ordem" list=r"#{'D':'Por Data','N':'Por Nome'}" />
		<@ww.hidden id="pagina" name="page"/>
		<@ww.hidden id="showFilter" name="showFilter"/>		
		<@ww.hidden name="entrega"/>
		<@ww.hidden name="limpaEstabelecimentoCheck" id="limpaEstabelecimentoCheck" value="true"/>
		<input type="button" value="" onclick="pesquisar()" class="btnPesquisar grayBGE" />
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />

	<br/>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@display.table name="solicitacaoEpis" id="solicitacaoEpi" class="dados" >
		<@display.column title="Ações" class="acao" style="width: 70px;">
			<#if solicitacaoEpi.situacaoEntrega == 'E' || solicitacaoEpi.situacaoEntrega == 'P'>
				<a href="prepareEntrega.action?solicitacaoEpi.id=${solicitacaoEpi.id}"><img border="0" title="Entrega/Devolução" src="<@ww.url value="/imgs/check.gif"/>"></a>
				<@authz.authorize ifAllGranted="ROLE_CAD_SOLICITACAOEPI" >
					<img border="0" title="Não é possível editar uma solicitação já entregue, ou com algum item entregue" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
					<img border="0" title="Não é possível excluir uma solicitação já entregue, ou com algum item entregue" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				</@authz.authorize>
			<#else>
				<#if solicitacaoEpi.colaborador.historicoColaborador.motivo?exists && solicitacaoEpi.colaborador.historicoColaborador.motivo == "C" && solicitacaoEpi.colaborador.historicoColaborador.status != 1>
					<a href="#"><img border="0" title="Não é permitida entrega de EPIs a colaboradores não confirmados no AC" src="<@ww.url value="/imgs/check.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);"></a>
				<#else>
					<a href="prepareEntrega.action?solicitacaoEpi.id=${solicitacaoEpi.id}"><img border="0" title="Entrega" src="<@ww.url value="/imgs/check.gif"/>"></a>
				</#if>
				<@authz.authorize ifAllGranted="ROLE_CAD_SOLICITACAOEPI" >
					<a href="prepareUpdate.action?solicitacaoEpi.id=${solicitacaoEpi.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
					<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?solicitacaoEpi.id=${solicitacaoEpi.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
				</@authz.authorize>
			</#if>
		</@display.column>
		<@display.column property="colaborador.nomeDesligado" style="width: 320px;" title="Colaborador"/>
		<@display.column property="data" title="Data" style="width: 70px; text-align: center;" format="{0,date,dd/MM/yyyy}"/>
		<@display.column property="cargo.nome" title="Cargo" style="width: 220px;"/>
		<@display.column title="Entrega" style="width: 170px;">
			 <span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover='showTooltip(event,"${solicitacaoEpi.informativoEntrega?j_string}");return false;'>
			 	${solicitacaoEpi.situacaoDescricaoEntrega} (${solicitacaoEpi.qtdEpiEntregue}/${solicitacaoEpi.qtdEpiSolicitado})
			 </span>
		</@display.column>
		<@display.column title="Devolução" style="width: 170px;">
			 <span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover='showTooltip(event,"${solicitacaoEpi.informativoDevolucao?j_string}");return false;'>
			 	${solicitacaoEpi.situacaoDescricaoDevolucao} (${solicitacaoEpi.qtdEpiDevolvido}/${solicitacaoEpi.qtdEpiEntregue})
			 </span>
		</@display.column>
	</@display.table>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="form"/>

	<div class="buttonGroup">
		<@authz.authorize ifAllGranted="ROLE_CAD_SOLICITACAOEPI">
			<button onclick="window.location='prepareInsert.action'" class="btnInserir"></button>
		</@authz.authorize>
		<button onclick="imprimir()" class="btnImprimir"></button>
	</div>
</body>
</html>