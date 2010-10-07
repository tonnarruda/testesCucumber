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
<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('nome','data','tipo'), new Array('data', 'cnpj', 'dataInat'))"/>

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
<script>
	function crmCnpj(tipo)
	{
		if (tipo == "-1")
		{
			document.getElementById("crm").disabled=true;
			document.getElementById("cnpj").disabled=true;
		}
		else if (tipo == "01")
		{
			document.getElementById("crm").disabled=true;
			document.getElementById("cnpj").disabled=false;
		}
		else if (tipo == "02")
		{
			document.getElementById("crm").disabled=false;
			document.getElementById("cnpj").disabled=true;
		}
	}
</script>

</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield label="Nome" name="clinicaAutorizada.nome" id="nome" required="true" cssClass="inputNome" maxLength="44" cssStyle="width: 320px;"/>
		<@ww.textfield label="Endereço" name="clinicaAutorizada.endereco" id="endereco" cssClass="inputNome" maxLength="80" cssStyle="width: 500px;"/>
		<@ww.textfield label="Telefone" name="clinicaAutorizada.telefone" id="telefone" maxLength="10" cssStyle="width: 75px;" />
		<@ww.textfield label="Horário de atendimento" name="clinicaAutorizada.horarioAtendimento" id="horarioAtendimento" maxLength="45" cssStyle="width: 320px;"/>
		
		<li>
			<@ww.div cssClass="divInfo" cssStyle="width: 490px;">
				<ul>
					<@ww.select label="Tipo" name="clinicaAutorizada.tipo" id="tipo" list="tipos" cssStyle="width: 120px;" headerKey="-1" headerValue="Selecione..." required="true" onchange="crmCnpj(this.value);" />
					<@ww.textfield label="CNPJ" id="cnpj" name="clinicaAutorizada.cnpj" cssClass="mascaraCnpj" />
					<@ww.textfield label="CRM" id="crm" name="clinicaAutorizada.crm" onkeypress="return(somenteNumeros(event,''));" maxLength="10" cssStyle="width:75px;" />
				</ul>
			</@ww.div>
		</li>

		<@frt.checkListBox label="Exames autorizados" name="examesCheck" id="examesCheck" list="examesCheckList"/>

		<@ww.datepicker label="Início do contrato" name="clinicaAutorizada.data" id="data"  required="true" value="${data}" cssClass="mascaraData validaDataIni"/>
		<@ww.datepicker label="Inativar a partir de (fim do contrato)" name="clinicaAutorizada.dataInativa" id="dataInat"  value="${dataInat}" cssClass="mascaraData validaDataFim"/>

		<@ww.hidden name="clinicaAutorizada.id" />
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="btnGravar" accesskey="${accessKey}"></button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V"></button>
	</div>

	<script>
		crmCnpj(document.getElementById("tipo").value);
	</script>
</body>
</html>