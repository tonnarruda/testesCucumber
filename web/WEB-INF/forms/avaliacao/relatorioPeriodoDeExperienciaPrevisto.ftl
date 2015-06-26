<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<title>Acompanhamento de Experiência Previsto</title>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	<script type="text/javascript">
		$(function() {
			$('#tooltipHelp').qtip({
				content: 'Caso queira visualizar todos os acompanhamentos atrasados, basta não informar a período inicial(deixá-lo em branco).'
			});
		});
	</script>
	
	<#assign validarCampos="return validaFormulario('form', new Array('periodoFim'), new Array('periodoFim','periodoIni'))"/>
	<#assign dataIni = "" />
	<#assign dataFim = "${periodoFim?date}" />
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<#include "../ftl/mascarasImports.ftl" />

		<@ww.form name="form" action="imprimeRelatorioPeriodoDeAcompanhamentoDeExperienciaPrevisto.action" onsubmit="${validarCampos}" method="POST">
		
			<div>
				Período:
				<img id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -4px" />
			</div>
			<@ww.datepicker  id="periodoIni" name="periodoIni" required="true" value="${dataIni}" cssClass="mascaraData" liClass="liLeft"/>
			<@ww.label value="a" liClass="liLeft"/>
			<@ww.datepicker  id="periodoFim" name="periodoFim" required="true" value="${dataFim}" cssClass="mascaraData"  liClass="liLeft"/>
			<@ww.label value="*"/>

			<br>
			Considerar colaboradores com até  
			<@ww.textfield theme="simple" name="tempoDeEmpresa" id="tempoDeEmpresa" cssStyle="width:60px; text-align:right;" maxLength="8" onkeypress = "return(somenteNumeros(event,''));"/> 
			dias de empresa.
			<#--
			<br>  
				Considerar colaboradores com período de acompanhamento de experiência a vencer daqui a  
				<@ww.textfield theme="simple" name="diasDeAcompanhamento" id="diasDeAcompanhamento" cssStyle="width:60px; text-align:right;" maxLength="8" onkeypress = "return(somenteNumeros(event,''));"/> 
				dias.
			-->
				<br><br>
			
			<@frt.checkListBox label="Período de Acompanhamento" name="periodoCheck" id="periodoCheck" list="periodoCheckList" filtro="true"/>						
			<@frt.checkListBox label="Estabelecimento" name="estabelecimentoCheck" id="estabelecimentoCheck" list="estabelecimentoCheckList" filtro="true"/>						
			<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areasCheck" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
			
		</@ww.form>

		<div class="buttonGroup">
			<button class="btnRelatorio" onclick="${validarCampos};" ></button>
		</div>
</body>
</html>