<#assign ww=JspTaglibs["/WEB-INF/tlds/webwork.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="Content-Language" content="pt-br" />
<meta http-equiv="Cache-Control" content="no-cache, no-store" />
<meta http-equiv="Pragma" content="no-cache, no-store" />
<meta http-equiv="Expires" content="0" />

<head>
	<style>
	div.odd
	{
		background-color: #FFF;
		font-family: Arial, Helvetica, sans-serif;
		font-size:13px;
		padding: 10px;
	}
	div.even
	{
		background-color: #E4F0FE;
		font-family: Arial, Helvetica, sans-serif;
		font-size:13px;
		padding: 10px;
	}
	div.even a, div.odd a
	{
		font-weight: bold;
		text-decoration: none;
		color: #454C54;
	}
	</style>
</head>
<body>

	<#assign usuarioId><@authz.authentication operation="id"/></#assign>

		<#if pesquisasColaborador?exists>
			<div class="waDivTituloX">Pesquisas/Avaliações em Aberto</div>
			<div class="waDivFormularioX">
				<#list pesquisasColaborador as pesquisa>
					<a href="#" onclick="popup('<@ww.url includeParams="none" value="/pesquisa/pesquisa/preview.action?pesquisa.id=${pesquisa.id}&colaborador.id=${colaborador.id}"/>', 580, 750)">${pesquisa.titulo}</a><br>
				</#list>

				<#if pesquisasColaborador?size < 1>
					<span>NÃO EXISTEM PESQUISAS DISPONÍVEIS</span>
				</#if>
			</div>
		</#if>

	<@authz.authorize ifAllGranted="ROLE_PESQUISA">
		<#if pesquisas?exists>
		<br><br>
			<#--div class="waDivTitulo">Pesquisas Bloqueadas</div>
			<div class="waDivFormularioX" style="padding: 0 !important;">

				<#assign odd = true />
				<#list pesquisasAtrasadas as pesq>
					<#if odd>
						<#assign class="odd"/>
					<#else>
						<#assign class="even"/>
					</#if>
					<div class="${class}">
						<a href="javascript:if (confirm('Deseja liberar esta pesquisa?')) window.location='./pesquisa/pesquisa/liberar.action?pesquisa.id=${pesq.id}'">${pesq.titulo}</a>
						<br>
						Data de Inicio: ${pesq.dataInicio}
					</div>
					<#assign odd = !odd />
				</#list>

				<#assign odd = true />
				<#list pesquisas as pesq>
					<#if odd>
						<#assign class="odd"/>
					<#else>
						<#assign class="even"/>
					</#if>
					<div class="${class}">
						<a style="color:#000; font-size: 14px;" href="javascript:if (confirm('Deseja liberar esta Pesquisa?')) window.location='./pesquisa/pesquisa/liberar.action?pesquisa.id=${pesq.id}'">${pesq.titulo}</a>
						<br>
						Data de Inicio: ${pesq.dataInicio}
					</div>
					<#assign odd = !odd />
				</#list-->


				<#if pesquisas?size < 1>
					<span style="color:#F00; font-size: 12px;">	NÃO EXISTEM PESQUISAS BLOQUEADAS</span>
				<#else>
					<@display.table name="pesquisas" id="pesquisa" class="dados">
						<@display.column title="Liberar" class="acao">
							<a href="javascript:if (confirm('Deseja liberar esta Pesquisa?')) window.location='./pesquisa/pesquisa/liberar.action?pesquisa.id=${pesquisa.id}'"><img border="0" title="Liberar" src="<@ww.url includeParams="none" value="/imgs/flag_green.gif"/>"></a>
						</@display.column>
						<@display.column property="titulo" title="Pesquisas Bloqueadas"/>
						<@display.column property="dataInicio" title="Início"  format="{0,date,dd/MM/yyyy}" style="width:80px;text-align:center;"/>
					</@display.table>
				</#if>
			</div>

		</#if>
	</@authz.authorize>


</body>
</html>