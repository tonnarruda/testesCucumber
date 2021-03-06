<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>

<#assign validarCampos="return imprimir();"/>
<title>Estatísticas de Vagas por Motivo</title>
	<#include "../ftl/mascarasImports.ftl" />
	<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('dataDe','dataAte'), new Array('dataDe','dataAte'))"/>

	<#if dataDe?exists>
		<#assign valueDataIni = dataDe?date/>
	<#else>
		<#assign valueDataIni = ""/>
	</#if>

	<#if dataAte?exists>
		<#assign valueDataFim = dataAte?date/>
	<#else>
		<#assign valueDataFim = ""/>
	</#if>
</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />
<@ww.form name="form" action="listMotivos.action" onsubmit="${validarCampos}" validate="true" method="POST">
	<div>Período*:</div>
	<@ww.datepicker name="dataDe" id="dataDe" liClass="liLeft" value="${valueDataIni}" cssClass="mascaraData validaDataIni"/>
	<@ww.label value="a" liClass="liLeft"/>
	<@ww.datepicker name="dataAte" id="dataAte" value="${valueDataFim}"  cssClass="mascaraData validaDataFim"/>
	<@ww.select label="Referenciar o período acima com a data da" name="dataStatusAprovacaoSolicitacao" list="tiposDataStatusAprovacaoSolicitacao" cssStyle="width: 300px;" />
	<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" filtro="true"/>
	<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>

	<@ww.select id="statusSolicitacao" label="Considerar Solicitações de Pessoal" name="statusSolicitacao" list=r"#{'T':'Abertas e Encerradas', 'A':'Abertas', 'E':'Encerradas'}"/>
	
	<@ww.checkbox label="Relatório resumido" id="indicadorResumido" name="indicadorResumido" labelPosition="left"/>
</@ww.form>


<div class="buttonGroup">
	<button onclick="${validarCampos};" class="btnRelatorio" accesskey="I">
	</button>
</div>
</body>
</html>