<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Certificações vencidas e a vencer</title>
	
	<script type="text/javascript">
		$(function() {
			$('#tooltipHelp').qtip({
				content: 'Algumas Certificações estão desabilitadas por não possuirem periodicidade.'
			});
		});
	</script>
	
	<#include "../ftl/mascarasImports.ftl" />
	
	<#if dataIni?exists>
		<#assign dateIni = dataIni?date/>
	<#else>
		<#assign dateIni = ""/>
	</#if>
	<#if dataFim?exists>
		<#assign dateFim = dataFim?date/>
	<#else>
		<#assign dateFim = ""/>
	</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('dataIni', 'dataFim', '@certificacoesCheck'), new Array('dataIni', 'dataFim'))"/>
	
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@ww.form name="form" action="imprimirCertificadosVencidosAVencer.action" onsubmit="${validarCampos}" validate="true" method="POST">
	
		Período Certificado:*<br>
		<@ww.datepicker name="dataIni" id="dataIni" value="${dateIni}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim" />
	
		Certificações*:
		<img id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16"  />
		<@frt.checkListBox name="certificacoesCheck" id="certificacoesCheck" list="certificacoesCheckList" filtro="true"/>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" filtro="true"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
		<@ww.select label="Considerar colaboradores que possuem certificações" name="filtroCetificacao" id="filtroCetificacao" list="tipoCertificacoes" cssStyle="width: 500px;"/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="$('form[name=form]').attr('action', 'imprimirCertificadosVencidosAVencer.action');${validarCampos};" class="btnRelatorio" ></button>
		<button onclick="$('form[name=form]').attr('action', 'imprimirCertificadosVencidosAVencerXlS.action');${validarCampos};" class="btnRelatorioExportar" ></button>
	</div>
	
</body>
</html>