<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Prontuário</title>

	<#if (colaboradors?exists && colaboradors?size > 0)>
		<#assign headerValue="Selecione..."/>
		<#assign desabilitado="" />
	<#else>
		<#assign headerValue="Utilize o Filtro acima."/>
		<#assign desabilitado="true" />
	</#if>

	<script type="text/javascript">

		function list()
		{
			document.getElementById("colaborador").value = null;
			
			document.form.submit();
		}

	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="list.action" onsubmit="list();" validate="true" method="POST">
		<li>
			<@ww.div cssClass="divInfo" cssStyle="width: 500px;">
				<ul>
					<@ww.textfield label="Nome" name="colaborador.nome" id="nome" cssClass="inputNome" maxLength="100" cssStyle="width: 400px;"/>
					<@ww.textfield label="CPF" id="cpf" name="colaborador.pessoal.cpf" liClass="liLeft" maxLength="11" onkeypress="return(somenteNumeros(event,''));" />
					<@ww.textfield label="Matrícula" id="matricula" name="colaborador.matricula" cssStyle="width:60px;"  maxLength="20"/>

					<input type="submit" value="" class="btnPesquisar grayBGDivInfo" />
				</ul>
			</@ww.div>
		</li>

		<@ww.hidden name="filtroRelatorio" value="true"/>
	</@ww.form>

	<@ww.form name="formRelatorio" action="relatorioProntuario.action" onsubmit="" method="POST" id="formBusca">
		<br/>
		<@ww.select label="Colaborador" name="colaborador.id" id="colaborador" list="colaboradors" listKey="id" listValue="nomeCpf" headerKey="" headerValue="${headerValue}" />
		<div class="buttonGroup">
			<button class="btnRelatorio" onclick="return validaFormulario('formRelatorio',new Array('colaborador'),null);"></button>
		</div>

		<@ww.hidden name="colaborador.nome" />
		<@ww.hidden name="colaborador.pessoal.cpf" />
		<@ww.hidden name="colaborador.matricula" />

	</@ww.form>

</body>
</html>