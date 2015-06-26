<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	<title>Registro de Prontuário</title>

	<#if colaboradors?exists>
		<#assign headerValue="Selecione..."/>
	<#else>
		<#assign headerValue="Utilize o Filtro acima."/>
	</#if>

	<script type="text/javascript">
		function listSelect(colaboradorId)
		{
			document.form.submit();
		}

		function list()
		{
			document.getElementById("colab").value = null;
			
			document.form.submit();
		}

		function prepareInsert()
		{
			document.form.action="prepareInsert.action";
			document.getElementById("prontuario").value = null;
			
			document.form.submit();
		}

		function prepareUpdate(prontuarioId)
		{
			document.form.action="prepareUpdate.action";
			document.getElementById("prontuario").value = prontuarioId;
			
			document.form.submit();
		}

		function prepareDelete(prontuarioId)
		{
			newConfirm('Confirma exclusão?', function(){
				document.form.action="delete.action";
				document.getElementById("prontuario").value = prontuarioId;
				
				document.form.submit();
			});
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="list.action" onsubmit="list();" validate="true" method="POST">
		<li>
			<@ww.div cssClass="divInfo" cssStyle="width: 98%;">
				<ul>
					<@ww.textfield label="Nome" name="colaborador.nome" id="nome" cssClass="inputNome" maxLength="100" cssStyle="width: 400px;"/>
					<@ww.textfield label="CPF" id="cpf" name="colaborador.pessoal.cpf" liClass="liLeft" maxLength="11" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:150px;"/>
					<@ww.textfield label="Matrícula" id="matricula" name="colaborador.matricula" cssStyle="width:150px;"  maxLength="20"/>

					<@ww.hidden name="prontuario.id" id="prontuario"/>
					<input type="submit" value="" class="btnPesquisar grayBGDivInfo" />

					<br><br>
					<@ww.select label="Colaborador" name="colaborador.id" id="colab" list="colaboradors" listKey="id" listValue="nomeCpf" headerKey="" headerValue="${headerValue}" onchange="listSelect(this.value);" />
				</ul>
			</@ww.div>
		</li>
	</@ww.form>

	<#if colaborador?exists && colaborador.id?exists>
		<h4>Colaborador: ${colaboradorNome}</h4>
		<@display.table name="prontuarios" id="prontuario" class="dados" >
			<@display.column title="Ações" class="acao">
				<a href="#" onclick="prepareUpdate(${prontuario.id});"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<a href="#" onclick="prepareDelete(${prontuario.id});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			</@display.column>
			<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="text-align: center;width:80px;"/>
			<@display.column property="descricao" style="width:340px;" title="Descrição"/>
			<@display.column property="usuario.login" style="width:200px;" title="Responsável"/>
		</@display.table>

		<div class="buttonGroup">
			<button class="btnInserir" onclick="prepareInsert();"></button>
		</div>
	</#if>

</body>
</html>