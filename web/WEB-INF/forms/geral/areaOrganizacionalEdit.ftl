<!--
  Autor: Bruno Bachiega
  Data: 7/06/2006
  Requisito: RFA0026
 -->
<html>
<head>
<@ww.head/>
<#if areaOrganizacional.id?exists>
	<title>Editar Área Organizacional</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Área Organizacional</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<#if podeEditarAreaMae == false>
	<#assign editaAreaMae="true"/>
<#else>
	<#assign editaAreaMae="false"/>
</#if>

<#assign validarCampos="validarCampos();"/>

<script type="text/javascript">
	function validarCampos()
	{
		if (validaFormulario('form', new Array('nome'), null, true))
		{
			$('.emailsNotificacoes').each(function() {
				if ($(this).val() == '')
					$(this).parent().remove();
			});
			
			document.form.submit();
		
		} else { return false; }
	}
	
	function adicionarCampoEmail(email) 
	{
		if (!email) email = '';
		var campo = "<li>";
		campo += "<input type='text' name='emailsNotificacoes' class='emailsNotificacoes' size='40' value='" + email + "'/>";
		campo += "<img title='Remover' src='<@ww.url includeParams="none" value="/imgs/delete.gif"/>' onclick='javascript:$(this).parent().remove();' style='cursor:pointer;'/>";
		campo += "</li>";
		$('ul#camposEmails').append(campo);
	}
	
	$(function() 
	{
		<#if areaOrganizacional.emailsNotificacoes?exists>
			<#list areaOrganizacional.emailsNotificacoes?split(";") as email>
				adicionarCampoEmail("${email}");
			</#list>
		</#if>
		
		if ($('.emailsNotificacoes').size() == 0)
			adicionarCampoEmail();
	});
</script>

</head>
<body>
	<@ww.actionerror />

	<@ww.form name="form" id="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">

		<@ww.hidden name="areaOrganizacional.id" />
		<@ww.hidden name="areaOrganizacional.codigoAC" />
		<@ww.hidden name="areaOrganizacional.empresa.id" />

		<#if podeEditarAreaMae == false>
			<@ww.hidden name="areaOrganizacional.nome" />
		</#if>
		<#if podeEditarAreaMae == false && areaOrganizacional.id?exists && areaOrganizacional.areaMae?exists && areaOrganizacional.areaMae.id?exists>
			<@ww.hidden name="areaOrganizacional.areaMae.id" />
		</#if>
		
		<@ww.textfield label="Nome" name="areaOrganizacional.nome" id="nome" cssClass="inputNome" maxLength="60" required="true" disabled="${editaAreaMae}"/>
		<@ww.select label="Área Mãe" name="areaOrganizacional.areaMae.id" disabled="${editaAreaMae}" list="areas" listKey="id" listValue="descricao" headerValue="" headerKey="-1" cssStyle="width:445px;"/>
		<@ww.select label="Responsável" name="areaOrganizacional.responsavel.id" id="responsavel" list="responsaveis" listKey="id" headerValue="" headerKey="" listValue="nomeMaisNomeComercial"/>
		<@ww.select label="Ativo" name="areaOrganizacional.ativo" list=r"#{true:'Sim',false:'Não'}"/>
		
		<label>E-mails extras para notificações:</label>
		<ul id="camposEmails"></ul>
		<a href="javascript:;" onclick="javascript:adicionarCampoEmail();" style="text-decoration: none;">
			<img src='<@ww.url includeParams="none" value="/imgs/mais.gif"/>'/> 
			Inserir mais um email
		</a>
	<@ww.token/>
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}"></button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V"></button>
	</div>
</body>
</html>