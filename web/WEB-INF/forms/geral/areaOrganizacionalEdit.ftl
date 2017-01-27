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
	<#assign desabilitado="false"/>
<#else>
	<title>Inserir Área Organizacional</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
	<#assign desabilitado="true"/>
</#if>

<#if podeEditarAreaMae == false>
	<#assign editaAreaMae="true"/>
<#else>
	<#assign editaAreaMae="false"/>
</#if>

<#assign validarCampos="areaMaeSemColaboradores();"/>

<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>

<script type="text/javascript">
	function validarCampos(areaMaeSemColaboradores)
	{
		emailsValidos = validaEmail();
		
		if (validaFormulario('form', new Array('nome'), null, true) && areaMaeSemColaboradores && emailsValidos)
		{
			$('.emailsNotificacoes').each(function() {
				if ($(this).val() == '')
					$(this).parent().remove();
			});
			
			document.form.submit();
		
		} else { return false; }
	}
	
	var timeoutId = 0;
	$('.emailsNotificacoes').live('keyup', function(){
		clearTimeout(timeoutId); // doesn't matter if it's 0
    	timeoutId = setTimeout(validaEmail, 1000);
	})
	
    function validaEmail(){
    	var regexEmail = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    	var emails = $('.emailsNotificacoes');
    	submit = true;
    	
    	$('.emailInvalido').remove();
    	for (i = 0; i<emails.size(); i++) {
	    	if($(emails[i]).val()!= "" && !regexEmail.test($(emails[i]).val())){
	    		$(emails[i]).parent().append("<span class='emailInvalido' style='color: #D8000C;padding: 3px 10px;padding-top: 2px;margin-left:2px;font-size: 11px;background: #FFD8DA;'>Digite um e-mail válido.</span>")
	    		submit = false;
	    	}
		}
		return submit;
    }
	
	
	function adicionarCampoEmail(email) 
	{
		if (!email) email = '';
		var campo = "<li style='margin-top: 3px;'>";
		campo += "<input type='text' name='emailsNotificacoes' class='emailsNotificacoes' size='40' value='" + email + "'/>";
		campo += "<img title='Remover' src='<@ww.url includeParams="none" value="/imgs/remove.png"/>' onclick='javascript:$(this).parent().remove();' border='0' align='absMiddle' style='cursor:pointer; float: left; margin: 4px 4px 4px 0;'/>";
		campo += "</li>";
		$('ul#camposEmails').append(campo);
	}
	
	function habilitaCoResponsavel(colab_id)
	{
		var slecionado = (colab_id != "") 
	
		if(!slecionado)
			$("#coResponsavel").val('');
		
		$("#coResponsavel").attr('disabled', !slecionado);
	}
	
	function habilataCampoAtivo()
	{
		<#if areaOrganizacional.id?exists>
			areaMaeId = null;
			<#if areaOrganizacional.areaMae?exists && areaOrganizacional.areaMae.id?exists>
				areaMaeId = ${areaOrganizacional.areaMae.id};
			</#if>
		
			AreaOrganizacionalDWR.verificaAlteracaoStatusAtivo(${areaOrganizacional.id}, areaMaeId, function(retorno) {  
				if(retorno)
					$('#ativo').attr('disabled', true);
			});
			
		</#if>
	}
	
	function areaMaeSemColaboradores()
	{
		AreaOrganizacionalDWR.areaSemColaboradores( $("#areaMaeId").val(), function(areaSemColaboradores) {  
			if(!areaSemColaboradores) {
				$("#areaMaeId").css("background","rgb(255, 238, 194)");
				$('#areaSemColaboradores').dialog({
					modal: true,
					title: 'Aviso',
					width: 400,
					buttons: {
						'Sim': function() {
							$("#areaMaeId").css("background", "");
							$(".msgAreaComColaboradores").hide();
							$('#areaSemColaboradores').dialog('close');
							validarCampos(true);
						},
						'Não': function() {
							$(".msgAreaComColaboradores").show();
							$('#areaSemColaboradores').dialog('close');
							validarCampos(false);
						}
					}
				});
			} else {
				$("#areaMaeId").css("background", "");
				validarCampos(true);
			}
		});
	}
	
	$(function() 
	{
		$('#tooltipEmailsExtras').qtip({
			content: 'Esses emails receberão todas as notificações inerentes à esta área organizacional.'
		});
	
		<#if areaOrganizacional.emailsNotificacoes?exists>
			<#list areaOrganizacional.emailsNotificacoes?split(";") as email>
				adicionarCampoEmail("${email}");
			</#list>
		</#if>
		
		if ($('.emailsNotificacoes').size() == 0)
			adicionarCampoEmail();
			
		habilataCampoAtivo();
	});
</script>

</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<div class="warning msgAreaComColaboradores" style="display: none;">
		<div style="float:right;"><a title="Ocultar" href="javascript:;" onclick="$(this).parent().parent().hide();">x</a></div>
		<ul>
				<li>Não é possível cadastrar área organizacional cuja área mãe tenha colaboradores vinculados.</li>
		</ul>
	</div>

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
		<@ww.select label="Área Mãe" id="areaMaeId" name="areaOrganizacional.areaMae.id" disabled="${editaAreaMae}" list="areas" listKey="id" listValue="descricao" headerValue="" headerKey="-1" cssStyle="width:445px;"/>
		<@ww.select label="Responsável" name="areaOrganizacional.responsavel.id" id="responsavel" list="responsaveis" listKey="id" headerValue="" headerKey="" listValue="nomeMaisNomeComercial" onchange="habilitaCoResponsavel(this.value);"/>
		
		<@ww.select label="Corresponsável" name="areaOrganizacional.coResponsavel.id" id="coResponsavel" list="coResponsaveis" listKey="id" headerValue="" headerKey="" listValue="nomeMaisNomeComercial" disabled = "${desabilitado}"/>
		<@ww.select label="Ativo" id="ativo" name="areaOrganizacional.ativo" list=r"#{true:'Sim',false:'Não'}"/>
		
		<label>Emails extras para notificações: <img id="tooltipEmailsExtras" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -4px" /></label>
		<ul id="camposEmails"></ul>
		<a href="javascript:;" onclick="javascript:adicionarCampoEmail();" style="text-decoration: none;">
			<img src='<@ww.url includeParams="none" value="/imgs/add.png"/>'/> 
			Inserir mais um email
		</a>
	<@ww.token/>
	</@ww.form>

	<div id="areaSemColaboradores" style="display:none;">
		A área organizacional mãe selecionada, possui colaboradores vinculados.
		</br></br>
		Deseja transferir os colaboradores da área organizacional mãe para a área organizacional que está sendo criada?
	</div>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}"></button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V"></button>
	</div>
</body>
</html>