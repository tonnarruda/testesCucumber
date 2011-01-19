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

	<#assign validarCampos="return validaFormulario('form', new Array('nome','login'), null)"/>
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

	<#assign validarCampos="return validaFormulario('form', new Array('nome','login','senha','confNovaSenha'), null)"/>
</#if>
<script>
	function marcarDesmarcar(frm)
	{
		var vMarcar;

		if (document.getElementById('md').checked)
		{
			vMarcar = true;
		}
		else
		{
			vMarcar = false;
		}

		with(frm)
		{
			for(i = 0; i < elements.length; i++)
			{
				if(elements[i].name == 'empresasId' && elements[i].type == 'checkbox')
				{
					elements[i].checked = vMarcar;
					document.getElementById("selectPerfil_" + elements[i].value).disabled = !vMarcar;
				}
			}
		}
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

	function desabilitaSelect()
	{
		var id = "";
		for (var i = 0; i < document.forms[0].elements.length; i++)
		{
			var elementForm = document.forms[0].elements[i];
			if ((elementForm != null) && (elementForm.type == 'checkbox') && (elementForm.checked))
			{
				id = "selectPerfil_" + elementForm.value;

				if(id != "selectPerfil_on")
				{
					var elementSelect = document.getElementById(id);
					elementSelect.disabled = false;
				}
			}
		}
	}
</script>
</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />

<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
	<@ww.textfield label="Nome" name="usuario.nome" id="nome" cssStyle="width:445px;" required="true" cssClass="inputNome" maxLength="100"/>
	<@ww.textfield label="Login" name="usuario.login" cssClass="inputLogin" maxLength="25" id="login" required="true"/>
	<@ww.select label="Colaborador" name="colaboradorId" list="colaboradores"  listKey="id" listValue="nome"  headerKey="" headerValue="Nenhum" />
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
	<@ww.hidden name="usuario.id" />
	<@ww.hidden name="colaborador.id" />

	<div>Perfil do Usuário:</div>

	<#assign i = 0/>
	<@display.table name="listaEmpresas" id="lista" class="dados" defaultsort=2 sort="list">
		<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(document.form);' />" style="width: 30px; text-align: center;">
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

		<@display.column title="Nome da Empresa">
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
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}">
		</button>
		<#if origem == 'C'>
			<button onclick="window.location='../../geral/colaborador/list.action'" class="btnCancelar" accesskey="V">
			</button>
		<#else>
			<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
			</button>
		</#if>
	</div>

	<script type="text/javascript">
		desabilitaSelect();
	</script>
</body>
</html>