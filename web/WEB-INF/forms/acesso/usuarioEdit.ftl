<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css"/>');
</style>

<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
<#if usuario.id?exists>
	<title>Editar Usuário</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
	<#assign requerido="false"/>
<#else>
	<title>Inserir Usuário</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
	<#assign requerido="true"/>

	<#if origem == 'C'>
		<#assign readonly="true"/>
	<#else>
		<#assign readonly="false"/>
	</#if>
</#if>
<script>

	$(function() {
		$(".dados > tbody").find(":checkbox[checked]").parent().parent().find("select").attr('disabled', false);
	});

	function marcarDesmarcar(marcado)
	{	
		$(".dados > tbody").find(":checkbox").attr('checked', marcado);
		$(".dados > tbody").find("select").attr('disabled', !marcado);
	}

	function verificaSelecao(frm)
	{
		var taValendo = false;
		var perfilsArray = new Array();
		var count = 0;

		with(frm)
		{
			for(i = 0; i < elements.length; i++)
			{
				if(elements[i].name == 'colaboradoresId')
				{
					if(elements[i].type == 'checkbox' && elements[i].checked)
					{

						if(!taValendo)
							taValendo = true;
					}
				}
			}
		}
		if(taValendo)
		{
			document.form.action = "insert.action";
			document.form.submit();
			return true;
		}
		else
		{
			jAlert('Selecione ao menos um colaborador!');
			return false;
		}
	}

	function mudarPerfil(elementCheck)
	{
		var id = "selectPerfil_" + elementCheck.value;
		var elementSelect = document.getElementById(id);

		if(elementSelect.disabled)
			elementSelect.disabled = false;
		else
			elementSelect.disabled = true;
	}
	
	function enviaForm()
	{
		if($('#lista input[type=checkbox]:checked').size() > 0)
		{
			<#if usuario.id?exists>
				return validaFormulario('form', new Array('nome','login'), null);
			<#else>
				return validaFormulario('form', new Array('nome','login','senha','confNovaSenha'), null);
			</#if>
		}
		else
			jAlert("Selecione pelo menos uma empresa.");
	}
</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@ww.form name="form" action="${formAction}" onsubmit="enviaForm();" method="POST">
		
		<#if origem?exists>
			<@ww.hidden name="origem" value="${origem}" />
		</#if>
		
		<@ww.textfield label="Nome" name="usuario.nome" id="nome" cssStyle="width:445px;" required="true" cssClass="inputNome" maxLength="100"/>
		<@ww.textfield label="Login" name="usuario.login" cssClass="inputLogin" maxLength="25" id="login" required="true"/>
		
		<#if colaboradorPertenceEmpresaLogada>
			<#if colaborador?exists && colaborador.desligado>
				<br/>
				<span style="color: red;">* O usuário tem referência com o colaborador(a) ${colaborador.nome}, que está desligado(a).</span><br/><br/>
				<@ww.hidden name="colaboradorId" />
			<#else>
				<@ww.select label="Colaborador" name="colaboradorId" list="colaboradores"  listKey="id" listValue="nome"  headerKey="" headerValue="Nenhum" />
			</#if>
		<#else>
			<br/>
			* Colaborador cadastrado para esse usuário não pertence a essa empresa que você está logado(a).<br/><br/>
			<@ww.hidden name="colaboradorId" />
		</#if>
		
		
		<li>
			<@ww.div cssClass="divInfo">
				<ul>
					<#if usuario?exists && usuario.id?exists>
						(deixe em branco para manter a senha atual)<br>
					</#if>
					<@ww.password label="Senha" name="usuario.senha" id="senha" required="${requerido}" cssClass="inputLogin" maxLength="25" />
					<@ww.password label="Confirmar Senha" name="usuario.confNovaSenha" id="confNovaSenha" required="${requerido}" cssClass="inputLogin" maxLength="25" />
				</ul>
			</@ww.div>
		</li>
	
		<@ww.select label="Ativo" name="usuario.acessoSistema" list=r"#{true:'Sim',false:'Não'}"/>
	
		<#if usuarioId == 1>
			<@ww.checkbox label="Super Administrador (é permitido apenas um Super Admin. por sistema)" id="superAdmin" name="usuario.superAdmin" labelPosition="left"/>
		</#if>
	
		<@ww.hidden name="usuario.id" />
		<@ww.hidden name="usuario.caixasMensagens" />
		<@ww.hidden name="colaborador.id" />
	
		<div>Perfil do Usuário: (Listadas somente empresas cujo seu usuário tenha acesso a essa tela)</div>
	
		<#assign i = 0/>
		<@display.table name="listaEmpresas" id="lista" class="dados" defaultsort=2 sort="list">
			<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(this.checked);' />" style="width: 30px; text-align: center;">
				<#assign checked=""/>
				<#if empresasId?exists>
					<#list empresasId as empresaId>
						<#if (lista[0].id?string == empresaId)>
							<#assign checked="checked"/>
						</#if>
					</#list>
				</#if>
				<input type="checkbox" value="${lista[0].id}" name="empresasId" onclick="mudarPerfil(this);" ${checked}/>
			</@display.column>
	
			<@display.column title="Empresa">
				${lista[0].nome}
			</@display.column>
	
			<#assign selected=""/>
	
			<@display.column title="Perfil" >
				<select name="selectPerfils" id="selectPerfil_${lista[0].id}" disabled>
				<#list lista[1] as perfil>
					<#if checked == "checked" && selected == "">
						<#if selectPerfils?exists && selectPerfils[i]?exists>
							<#if perfil.id?string == selectPerfils[i]?string>
								<#assign selected="selected"/>
								<#assign i = i + 1/>
							</#if>
						<#else>
							<#if usuarioEmpresas?exists >
								<#list usuarioEmpresas as usuarioEmpresa>
									<#if lista[0].id == usuarioEmpresa.empresa.id && perfil.id == usuarioEmpresa.perfil.id>
										<#assign selected="selected"/>
										<#break/>
									</#if>
								</#list>
							</#if>
						</#if>
					<#else>
						<#assign selected=""/>
					</#if>
	
			  		<option value="${perfil.id}" ${selected}>${perfil.nome}</option>
				</#list>
				</select>
			</@display.column>
	
		</@display.table>
		<@ww.token/>
	
		<#if empresasNaoListadas?exists>
			<li>
				<fieldset class="fieldsetPadrao" style="width: 718px;text-align:justify;">
					<legend>Atenção</legend>
					Seu usuário não possui permissão para configurar usuários nas empresas: ${empresasNaoListadas}.<br>
					Este usuário tem permissão para acessar essas empresas, caso edite ele perderá acesso as mesmas.				
					<br/>
					
				</fieldset>
			</li>
		</#if>

	</@ww.form>

	
	<div class="buttonGroup">
		<button onclick="enviaForm();" class="btnGravar" accesskey="${accessKey}">
		</button>
		<#if origem == 'C'>
			<button onclick="window.location='../../geral/colaborador/list.action'" class="btnCancelar" accesskey="V">
			</button>
		<#else>
			<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
			</button>
		</#if>
	</div>
</body>
</html>