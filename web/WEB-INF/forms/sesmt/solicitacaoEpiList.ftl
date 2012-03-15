<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>

	<#include "../ftl/mascarasImports.ftl" />

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
		<title>Entrega de EPIs</title>
	<#else>
		<title>Solicitações de EPIs</title>
	</#if>
	
	<script type="text/javascript">
		function imprimir()
		{
			$('#form').attr('action','imprimir.action').submit();
		}

		function pesquisar()
		{
			$('#pagina').val(1);
			$('#form').attr('action','list.action').submit();
		}
	</script>
</head>
<body>

	<#include "../util/topFiltro.ftl" />
	<@ww.form name="form" id="form" action="list.action" onsubmit="${validarCampos}" method="POST">

		<@ww.select label="Situação" id="situacao" name="situacao" list=r"#{'T':'Todas','A':'Aberta','E':'Entregue','P':'Entregue Parcialmente'}" />

		<@ww.textfield label="Matrícula" name="matriculaBusca" id="matriculaBusca" cssStyle="width: 60px;"/>
		<@ww.textfield label="Nome" name="nomeBusca" id="nomeBusca" cssStyle="width: 260px;"/>

		Período:<br/>
		<@ww.datepicker name="dataIni" id="dataIni"  value="${dateIni}" liClass="liLeft" cssClass="mascaraData"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData" />

		<@ww.hidden id="pagina" name="page"/>
		<@ww.hidden name="entrega"/>

		<input type="button" value="" onclick="pesquisar()" class="btnPesquisar grayBGE" />
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />

	<br/>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@display.table name="solicitacaoEpis" id="solicitacaoEpi" class="dados" >
		<@display.column title="Ações" class="acao">
			<#if solicitacaoEpi.situacaoSolicitacaoEpi == 'E'>
				<a href="prepareEntrega.action?solicitacaoEpi.id=${solicitacaoEpi.id}"><img border="0" title="Entrega" src="<@ww.url value="/imgs/check.gif"/>"></a>
				<@authz.authorize ifAllGranted="ROLE_CAD_SOLICITACAOEPI" >
				<img border="0" title="Não é possível editar uma solicitação já entregue, ou com algum item entregue" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				<img border="0" title="Não é possível excluir uma solicitação já entregue, ou com algum item entregue" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				</@authz.authorize>
			<#else>
				<#if solicitacaoEpi.colaborador.historicoColaborador.status == 1>
					<a href="prepareEntrega.action?solicitacaoEpi.id=${solicitacaoEpi.id}"><img border="0" title="Entrega" src="<@ww.url value="/imgs/check.gif"/>"></a>
				<#else>
					<a href="#"><img border="0" title="Não é permitida entrega de EPIs a colaboradores não confirmados no AC" src="<@ww.url value="/imgs/check.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);"></a>
				</#if>
				<@authz.authorize ifAllGranted="ROLE_CAD_SOLICITACAOEPI" >
				<a href="prepareUpdate.action?solicitacaoEpi.id=${solicitacaoEpi.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?solicitacaoEpi.id=${solicitacaoEpi.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
				</@authz.authorize>
			</#if>
		</@display.column>
		<@display.column property="colaborador.nome" style="width: 320px;" title="Colaborador"/>
		<@display.column property="data" title="Data Solicitação" style="width: 100px;" format="{0,date,dd/MM/yyyy}"/>
		<@display.column property="cargo.nome" title="Cargo" style="width: 290px;"/>
		<@display.column title="Situação" style="width: 90px;">
			 ${solicitacaoEpi.situacao} (${solicitacaoEpi.qtdEpiEntregue}/${solicitacaoEpi.qtdEpiSolicitado})
		</@display.column>
		<@display.footer>
		  	<tr>
				<td colspan="6" >Total : ${totalSize} solicitações</td>
	  		</tr>
		</@display.footer>
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