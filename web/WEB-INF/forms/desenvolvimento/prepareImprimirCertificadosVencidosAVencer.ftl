<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Certificações vencidas e a vencer</title>
	
	<script type="text/javascript">
		function submeterAction(action)	{
			$('form[name=form]').attr('action', action);
			return validaFormulario('form', new Array('certificacao'), null);
		}
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
	
	<#assign validarCampos="return validaFormulario('form', new Array('dataIni', 'dataFim', 'certificacao'), new Array('dataIni', 'dataFim'))"/>
	
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@ww.form name="form" action="imprimirCertificadosVencidosAVencer.action" onsubmit="${validarCampos}" validate="true" method="POST">
	
		Período:*<br>
		<@ww.datepicker name="dataIni" id="dataIni" value="${dateIni}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim" />
	
		<@ww.select name="certificacao.id" id="certificacao" list="certificacoes" listKey="id" required="true" listValue="nome" label="Certificação" headerKey="" headerValue="Selecione..." cssStyle="width: 500px;"/>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" filtro="true"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
		<@ww.select name="filtroCertificacao" label="Considerar colaboradores que possuem certificações" list=r"#{'0':'Vencidas e a vencer','1':'A vencer', '2':'Vencidas'}" cssStyle="width: 500px;"/>
		
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="$('form[name=form]').attr('action', 'imprimirCertificadosVencidosAVencer.action');${validarCampos};" class="btnRelatorio" ></button>
		<button onclick="$('form[name=form]').attr('action', 'imprimirCertificadosVencidosAVencerXLS.action');${validarCampos};" class="btnRelatorioExportar" ></button>
	</div>
	
</body>
</html>