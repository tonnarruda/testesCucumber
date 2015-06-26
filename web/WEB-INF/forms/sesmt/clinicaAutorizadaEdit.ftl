<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<#if clinicaAutorizada.id?exists>
	<title>Editar Clínica ou Médico Autorizado</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Clínica ou Médico Autorizado</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<#if clinicaAutorizada.data?exists>
		<#assign data = clinicaAutorizada.data>
	<#else>
		<#assign data = "">
</#if>
<#if clinicaAutorizada.dataInativa?exists>
		<#assign dataInat = clinicaAutorizada.dataInativa>
	<#else>
		<#assign dataInat = "">
</#if>

	<#include "../ftl/mascarasImports.ftl" />
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>
	<style type="text/css">@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');</style>
	
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');

		.yui-ac-container, .yui-ac-content, .yui-ac-shadow, .yui-ac-content ul{
			width: 400px;
		}
	</style>
	
<script>
	var tipoOutros = [${tipoOutros}];

	function crmCnpj(tipo)
	{
		if (tipo == "-1")
		{
			$('#cnpjCampo').hide();
			$('#crmCampo').hide();
			$('#outroCampo').hide();
		}
		else if (tipo == "01")
		{
			showHide('cnpj', 'crm', 'outro');
		}
		else if (tipo == "02")
		{
			showHide('crm', 'cnpj', 'outro');
		}
		else if (tipo == "03")
		{
			showHide('outro', 'cnpj', 'crm');
			$("#outro").autocomplete(tipoOutros);
		}
	}
	
	function showHide(show, hide1, hide2)
	{
		show = "#" + show;
		hide1 = "#" + hide1;
		hide2 = "#" + hide2;
		
		showCampo = show + "Campo";
		hideCampo1 = hide1 + "Campo";
		hideCampo2 = hide2 + "Campo";
		
		$(showCampo).show();
		$(hideCampo1).hide();
		$(hideCampo2).hide();
		
		$(show).attr("disabled", false);
		$(hide1).attr("disabled", true);
		$(hide2).attr("disabled", true);	
	} 
	
	function submeter()
	{
		if ($('#tipo').val() == "01")
			return validaFormularioEPeriodo('form', new Array('nome','data','tipo'), new Array('data', 'cnpj', 'dataInat'));
		else
			return validaFormularioEPeriodo('form', new Array('nome','data','tipo'), new Array('data', 'dataInat'));
	}
	
</script>

</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="submeter();" validate="true" method="POST">
		<@ww.textfield label="Nome" name="clinicaAutorizada.nome" id="nome" required="true" cssClass="inputNome" maxLength="44" cssStyle="width: 320px;"/>
		<@ww.textfield label="Endereço" name="clinicaAutorizada.endereco" id="endereco" cssClass="inputNome" maxLength="80" cssStyle="width: 500px;"/>
		<@ww.textfield label="Telefone" name="clinicaAutorizada.telefone" id="telefone" maxLength="10" cssStyle="width: 75px;" />
		<@ww.textfield label="Horário de atendimento" name="clinicaAutorizada.horarioAtendimento" id="horarioAtendimento" maxLength="45" cssStyle="width: 320px;"/>
		
		<li>
			<@ww.div cssClass="divInfo" cssStyle="width: 490px;">
				<ul>
					<@ww.select label="Tipo" name="clinicaAutorizada.tipo" id="tipo" list="tipos" cssStyle="width: 120px;" headerKey="-1" headerValue="Selecione..." required="true" onchange="crmCnpj(this.value);" />

					<@ww.div id="cnpjCampo">
						<@ww.textfield label="CNPJ" id="cnpj" name="clinicaAutorizada.cnpj" cssClass="mascaraCnpj" />
					</@ww.div>
					
					<@ww.div id="crmCampo">
						<@ww.textfield label="CRM" id="crm" name="clinicaAutorizada.crm" onkeypress="return(somenteNumeros(event,''));" maxLength="10" cssStyle="width:75px;" />
					</@ww.div>

					<@ww.div id="outroCampo">
						<@ww.textfield label="Nome" name="clinicaAutorizada.outro" id="outro" cssStyle="width:360px;" cssClass="inputNome" maxLength="50"/>
					</@ww.div>
				</ul>
			</@ww.div>
		</li>

		<@frt.checkListBox label="Exames autorizados" name="examesCheck" id="examesCheck" list="examesCheckList" filtro="true"/>

		<@ww.datepicker label="Início do contrato" name="clinicaAutorizada.data" id="data"  required="true" value="${data}" cssClass="mascaraData validaDataIni"/>
		<@ww.datepicker label="Inativar a partir de (fim do contrato)" name="clinicaAutorizada.dataInativa" id="dataInat"  value="${dataInat}" cssClass="mascaraData validaDataFim"/>

		<@ww.hidden name="clinicaAutorizada.id" />
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="submeter();" class="btnGravar" accesskey="${accessKey}"></button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V"></button>
	</div>

	<script>
		crmCnpj(document.getElementById("tipo").value);
	</script>
</body>
</html>