]<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	
	<title>Absenteísmo</title>
	<#include "../ftl/mascarasImports.ftl" />
	
	<#assign validarCampos="return validaFormularioEPeriodoMesAno('form', new Array('dataDe','dataAte'), new Array('dataDe','dataAte'));"/>

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	<script type="text/javascript">
		$(function () {
			$('#tooltipAbsenteismo').qtip({
				content: 'São consideradas faltas as Ocorrências cujo o tipo está marcado como Absenteísmo e os Afastamentos cujo o motivo está marcado como Absenteísmo.'
			});
		});
	</script>
</head>
<body>
	
	<@ww.actionerror />
	<@ww.actionmessage />
	
	
	<@ww.div >
	O <i>absenteísmo</i> é usado para designar as ausências dos trabalhadores no processo de trabalho, seja por falta ou atraso, devido a algum motivo interveniente.
	Ele é calculado pela fórmula [Total de faltas do mês<img id="tooltipAbsenteismo" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" align="absmiddle" /> / (Qtd. colaboradores ativos no início do mês * Dias trabalhados no mês)]
	</@ww.div>
	<br/>
	
	<@ww.form name="form" action="relatorioAbsenteismo.action" validate="true" method="POST">
		<div>Período (Mês/Ano)*:</div>
		<@ww.textfield name="dataDe" id="dataDe" required="true"  cssClass="mascaraMesAnoData validaDataIni" liClass="liLeft"/>
		<@ww.label value="a" liClass="liLeft"/>
		<@ww.textfield name="dataAte" id="dataAte" required="true" cssClass="mascaraMesAnoData validaDataFim"/>
		
		<@frt.checkListBox name="ocorrenciasCheck" id="ocorrenciasCheck" label="Ocorrências" list="ocorrenciasCheckList" filtro="true"/>
		<@frt.checkListBox name="afastamentosCheck" id="afastamentosCheck" label="Afastamentos" list="afastamentosCheckList" filtro="true"/>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" filtro="true"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio" accesskey="I"></button>
	</div>

</body>
</html>