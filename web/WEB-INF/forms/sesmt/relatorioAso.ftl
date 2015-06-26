<html>
<head>
<@ww.head/>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/fortes.js?version=${versao}"/>'></script>

	<#include "../ftl/mascarasImports.ftl" />

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<#assign formAction="imprimirAso.action" />

	<title>ASOs</title>

<script>

	function filtrarOpcao()
	{
		value = document.getElementById('emitirPara').value;
		if (value == 'A')
		{
			exibe('divCandidato');
			oculta('divColaborador');
		}
		else if (value == 'C')
		{
			exibe('divColaborador');
			oculta('divCandidato');
		}
	}

	function oculta(id_da_div)
	{
		document.getElementById(id_da_div).style.display = 'none';
	}
	function exibe(id_da_div)
	{
		document.getElementById(id_da_div).style.display = '';
	}

</script>

</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />

<@ww.form name="form" action="filtroImprimirAso.action" method="POST" >

		<li>
			<@ww.div cssClass="divInfo" cssStyle="width: 500px;">
				<ul>
					<@ww.select label="Exames para" name="emitirPara" id="emitirPara" list=r"#{'A':'Candidato','C':'Colaborador'}" onchange="filtrarOpcao();" />
					<span id="divCandidato" style="display:''">
						<@ww.textfield label="Nome" name="candidato.nome" id="nomeCandidato" cssStyle="width: 300px;"/>
						<@ww.textfield label="CPF" name="candidato.pessoal.cpf" id="cpfCandidato" cssClass="mascaraCpf"/>
					</span>
					<span id="divColaborador" style="display:none">
						<@ww.textfield label="Nome" name="colaborador.nome" id="nomeColaborador" cssStyle="width: 300px;"/>
						<@ww.textfield label="Matrícula" name="colaborador.matricula" id="matriculaBusca" liClass="liLeft" cssStyle="width: 60px;"/>
						<@ww.textfield label="CPF" name="colaborador.pessoal.cpf" id="cpfColaborador" cssClass="mascaraCpf"/>
					</span>

					<input type="submit" value="" class="btnPesquisar grayBGDivInfo" />
				</ul>
			</@ww.div>
		</li>
	<br/>
</@ww.form>

<#if (candidatos?exists && candidatos?size > 0) || (colaboradors?exists && colaboradors?size > 0)>

	<@ww.form name="formRelatorio" action="${formAction}" method="post">

		<#if (candidatos?exists && candidatos?size > 0)>
			<@ww.select label="Candidato" name="candidato.id" id="candidato" required="true" list="candidatos" listKey="id" listValue="nomeECpf"/>
		</#if>

		<#if (colaboradors?exists && colaboradors?size > 0)>
			<@ww.select label="Colaborador" name="colaborador.id" id="colaborador" required="true" list="colaboradors" listKey="id" listValue="nomeCpfMatricula"/>
		</#if>

	 	<@ww.select label="Médico" name="medicoCoordenador.id" id="medico" list="medicoCoordenadors" required="true" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." />

		<div class="buttonGroup">
			<button class="btnRelatorio" onclick="return validaFormulario('formRelatorio',new Array('medico'),null);"></button>
			</button>
		</div>
	</@ww.form>
</#if>

<script type="text/javascript">
	filtrarOpcao();
</script>

</body>
</html>