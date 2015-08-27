<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>

	<#include "../ftl/mascarasImports.ftl" />

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CandidatoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

	<script type="text/javascript">
		function filtrarOpcao()
		{
			var value = document.getElementById("vinculo").value;
			if (value == 'A')
			{
				document.getElementById("matricula").disabled = true;
				setDisplay("wwgrp_candidato", "");
				setDisplay("wwgrp_colaborador", "none");
				resetSelect("colaborador");
			}
			else if (value == 'C')
			{
				document.getElementById("matricula").disabled = false;
				setDisplay("wwgrp_candidato", "none");
				setDisplay("wwgrp_colaborador", "");
				resetSelect("candidato");
			}
		}

		function pesquisar()
		{
			var nome = document.getElementById("nome").value;
			var cpf = document.getElementById("cpf").value;
			var value = document.getElementById("vinculo").value;
			var empresaId = <@authz.authentication operation="empresaId"/>;

			DWRUtil.useLoadingMessage('Carregando...');

			if (value == 'A')
			{
				CandidatoDWR.find(createListCandidato, nome, cpf, empresaId);
			}
			else if (value == 'C')
			{
				var matricula = document.getElementById("matricula").value;
				ColaboradorDWR.find(createListColaborador, nome, cpf, matricula, empresaId, false, null);
			}

			return false;
		}

		function createListCandidato(data)
		{
			resetSelect("candidato");
			DWRUtil.addOptions("candidato", data);
		}
		function createListColaborador(data)
		{
			resetSelect("colaborador");
			DWRUtil.addOptions("colaborador", data);
		}

		function resetSelect(select)
		{
			DWRUtil.removeAllOptions(select);
			document.getElementById(select).options[0] = new Option("Selecione...", "");
		}

		function enviaForm()
		{
			var value = document.getElementById("vinculo").value;

			if (value == 'A')
			{
				return validaFormulario('form', new Array('candidato','ficha'), null);
			}
			else if (value == 'C')
			{
				return validaFormulario('form', new Array('colaborador', 'ficha'), null);
			}
		}
	</script>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<#assign labelFiltro="Ocultar Filtro"/>
	<#assign imagemFiltro="/imgs/arrow_up.gif"/>
	<#assign classHidden=""/>

	<title>Fichas Médicas</title>

	<#assign validarCampos="return validaFormulario('form', new Array('nome','data'), null)"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="../../sesmt/fichaMedica/prepareResponderFichaMedica.action" onsubmit="enviaForm();" method="POST" >
		<li>
			<@ww.div cssClass="divInfo">
				<ul>
					<@ww.select label="Ficha para" name="vinculo" id="vinculo" list=r"#{'A':'Candidato','C':'Colaborador'}" onchange="filtrarOpcao();" />
					<@ww.textfield label="Nome" name="colaborador.nome" id="nome" cssStyle="width: 300px;"/>
					<@ww.textfield label="CPF" name="colaborador.pessoal.cpf" id="cpf" cssClass="mascaraCpf" liClass="liLeft" />
					<@ww.textfield label="Matrícula" name="colaborador.matricula" id="matricula" disabled="true" cssStyle="width: 140px;" />
				</ul>

				<button type="button" onclick="pesquisar();" class="btnPesquisar grayBGE"></button>
			</@ww.div>
		</li><br>

		<@ww.select label="Candidato" name="candidato.id" id="candidato" list="candidatos"  listKey="id" listValue="nomeECpf" required="true" cssStyle="width: 500px;" headerKey="" headerValue="Selecione..."/>
		<@ww.select label="Colaborador" name="colaborador.id" id="colaborador" list="colaboradors" listKey="id" listValue="nomeCpfMatricula" required="true" cssStyle="width: 500px;" headerKey="" headerValue="Selecione..."/>

		<@ww.select label="Ficha" name="questionario.id" id="ficha" list="fichaMedicas" cssStyle="width: 500px;" required="true" headerKey="" listKey="questionario.id" listValue="questionario.titulo" headerValue="Selecione..."/>

		<@ww.hidden name="voltarPara" value="../../sesmt/fichaMedica/prepareInsertFicha.action"/>
		<@ww.hidden name="inserirFichaMedica" value="true"/>
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="enviaForm();" class="btnAvancar" ></button>
		<button onclick="window.location='listPreenchida.action'" class="btnVoltar" ></button>
	</div>

	<script type="text/javascript">
		filtrarOpcao();
	</script>
</body>
</html>