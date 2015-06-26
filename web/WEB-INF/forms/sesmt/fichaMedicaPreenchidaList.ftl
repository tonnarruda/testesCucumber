<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<#include "../ftl/mascarasImports.ftl" />

	<script type="text/javascript">
		function filtrarOpcao()
		{
			vinculo = $('#vinculo').val();
			if (vinculo == 'A')
				$('#matriculaBusca').css('background', '#F8F8F8').attr('disabled', 'true').val('');
			else if (vinculo == 'C')
				$('#matriculaBusca').css('background', '#FFFFFF').removeAttr('disabled');
		}
	</script>

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

	<title>Fichas Médicas</title>
</head>
<body>

	<@ww.actionerror />
	<@ww.actionmessage />

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="form" id="form" action="listPreenchida.action" onsubmit="${validarCampos}" method="POST">
			Período:<br>
			<@ww.datepicker name="dataIni" id="dataIni"  value="${dateIni}" liClass="liLeft" cssClass="mascaraData"/>
			<@ww.label value="a" liClass="liLeft" />
			<@ww.datepicker name="dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData" />

			<@ww.select label="Vínculo" id="vinculo" name="vinculo" list=r"#{'A':'Candidato','C':'Colaborador'}" onchange="filtrarOpcao();"/>

			<@ww.textfield label="Nome" name="nomeBusca" id="nomeBusca" cssStyle="width: 260px;"/>
			<@ww.textfield label="CPF" name="cpfBusca" id="cpfBusca" cssStyle="width: 150px;" maxLength="11" onkeypress="return(somenteNumeros(event,''));" liClass="liLeft"/>

			<@ww.textfield label="Matrícula" name="matriculaBusca" id="matriculaBusca" cssStyle="width: 60px;" maxLength="20" disabled="true" />

			<input type="submit" value="" class="btnPesquisar grayBGE">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br/>

	<#if colaboradorQuestionarios?exists && 0 < colaboradorQuestionarios?size>
		<@display.table name="colaboradorQuestionarios" id="colaboradorQuestionario" pagesize=20 class="dados">
			<@display.column title="Ações" class="acao" style="text-align: center;width:80px;">
				<a href="../../sesmt/fichaMedica/prepareEditFichaMedica.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='deletePreenchida.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
				<a href="../../sesmt/fichaMedica/imprimirFichaMedica.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}&questionario.id=${colaboradorQuestionario.questionario.id}"><img border="0" title="Imprimir ficha" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
			</@display.column>
			<@display.column title="Data" property="respondidaEm" format="{0,date,dd/MM/yyyy}" style="text-align: center;width:70px;"/>

			<@display.column title="Vínculo" title="Vínculo" style="text-align: center;width:80px;">
				<#if vinculo?exists && vinculo == 'A'>
					Candidato
				<#elseif vinculo?exists && vinculo == 'C'>
					Colaborador
				<#else>
					${colaboradorQuestionario.pessoaVinculo}
				</#if>
			</@display.column>
			<@display.column title="Nome" property="pessoaNome" style="width: 290px;" title="Nome"/>
			<@display.column title="Ficha" property="questionario.titulo" title="Ficha"/>
		</@display.table>
	</#if>
	<div class="buttonGroup">
		<button onclick="window.location='prepareInsertFicha.action'" class="btnInserir"></button>
	</div>

	<script>
		filtrarOpcao();
	</script>
</body>
</html>