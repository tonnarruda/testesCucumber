<html>
<head>
<@ww.head/>
<#if usuarioEmpresa.id?exists>
	<title>Editar UsuarioEmpresa</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir UsuarioEmpresa</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" validate="true" method="POST">

	<#if usuarios?exists>
		<@ww.select label="Usuario" name="usuario.id" list="usuarios" listKey="id" listValue="id" headerValue="" headerKey=""/>
	</#if>

	<#if perfils?exists>
		<@ww.select label="Perfil" name="perfil.id" list="perfils" listKey="id" listValue="id" headerValue="" headerKey=""/>
	</#if>
		<@ww.textfield label="Empresa" name="empresa" />
		<@ww.hidden label="Id" name="id" />
	</@ww.form>


	<!-- com.fortes.rh.model.acesso.Usuario -->
	<#if usuarioEmpresa.usuario?exists>
	<ul>
	<li><a href="../usuario/load.action?usuario.id=${usuarioEmpresa.usuario.id}">${usuarioEmpresa.usuario}</a></li>
	</ul>
	</#if>

	<!-- com.fortes.rh.model.acesso.Perfil -->
	<#if usuarioEmpresa.perfil?exists>
	<ul>
	<li><a href="../perfil/load.action?perfil.id=${usuarioEmpresa.perfil.id}">${usuarioEmpresa.perfil}</a></li>
	</ul>
	</#if>
	
	<div class="buttonGroup">
		<button onclick="document.form.submit();" class="btnGravar" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
		</button>
	</div>
</body>
</html>