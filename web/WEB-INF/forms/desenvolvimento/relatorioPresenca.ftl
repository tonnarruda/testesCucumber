<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/TurmaDWR.js"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/DiaTurmaDWR.js"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css"/>');
</style>
<title>Lista de Freqüência</title>
<script>
	function populaDias(frm)
	{
		turmasList = document.getElementById('turma');
		turmaId = turmasList.options[turmasList.selectedIndex].value

		DWRUtil.useLoadingMessage('Carregando...');
		DiaTurmaDWR.getDiasPorTurma(montaListDias, turmaId, 'diasCheck');
	}

	function montaListDias(dias)
	{
		if(dias != null)
		{
			document.getElementById('listCheckBoxdiasCheck').innerHTML = dias;;
		}
		else
			jAlert("Não existem datas a serem selecinadas.");
	}

	function listaTurma()
	{
		cursos = document.getElementById('cursos');
		curso = cursos.options[cursos.selectedIndex].value;

		if(!curso)
		{
			DWRUtil.removeAllOptions("turma");
		}
		else
		{
			DWRUtil.useLoadingMessage('Carregando...');
			TurmaDWR.getTurmas(fillSelectTurma, curso);
		}

	}

	function fillSelectTurma(turma)
	{
		DWRUtil.removeAllOptions("turma");
		document.getElementById("turma").options[0] = new Option("", "");
		DWRUtil.addOptions("turma", turma);
	}

	function validaQtdColunas(check, onclick)
	{
		var qtdChecked = 0;

		if(!onclick || check.checked)
		{
			if(document.getElementById('exibirCargo').checked)
				qtdChecked++;
			if(document.getElementById('exibirArea').checked)
				qtdChecked++;
			if(document.getElementById('exibirEstabelecimento').checked)
				qtdChecked++;
			if(document.getElementById('exibirAssinatura').checked)
				qtdChecked++;

			// verificação via onclick, não deixa selecionar mais de duas colunas
			if (onclick)
			{
				if(qtdChecked > 2)
				{
					check.checked = false;
					jAlert("Não é permitido exibir mais de duas colunas extras.");
				}
			}
		}

		// verificação do submit, se há exatamente duas colunas
		if (!onclick && qtdChecked != 2)
		{
			jAlert("É necessário selecionar exatamente duas colunas extras.");
			return false;
		}

		return true;
	}

	function valida()
	{
		if (validaFormulario('form', new Array('cursos','turma','@diasCheck','qtdLinhas'), null, true))
		{
			valido = validaQtdColunas(null, false);
			if (valido)
			{
				document.form.submit();
			}
			return valido;
		}
		return false;
	}

</script>
<#assign validarCampos="return valida();"/>
</head>
<body>
	<@ww.actionmessage/>
	<@ww.actionerror />

	<@ww.form name="form" action="list.action" validate="true" onsubmit="${validarCampos}" method="POST">
		<@ww.select label="Curso" cssStyle="width:180px" name="colaboradorTurma.curso.id" id="cursos" list="cursos" listKey="id" listValue="nome" headerValue="" headerKey="" liClass="liLeft" onchange="listaTurma();"  required="true"/>
	  	<@ww.select label="Turma" cssStyle="width:200px" name="colaboradorTurma.turma.id" id="turma"  list="turmas" listKey="id" listValue="descricao" headerValue="" headerKey="" liClass="liLeft"  onchange="populaDias(document.forms[0]);"  required="true"/>
		<@frt.checkListBox name="diasCheck" id="diasCheck" label="Dias Previstos" list="diasCheckList" readonly=false valueString=true/>

		<@ww.label value="Configuração" />
		<li>
			<@ww.div cssClass="divInfo" cssStyle="width:490px;">
				<ul>
					<li style="font-weight:bold;">Cabeçalho</li>
					<@ww.checkbox label="Exibir conteúdo programático" name="exibirConteudoProgramatico" labelPosition="left"/>
					<@ww.checkbox label="Exibir critérios de avaliação" name="exibirCriteriosAvaliacao" labelPosition="left"/>
					<br/>
					<@ww.checkbox label="Exibir nome comercial" name="exibirNomeComercial" labelPosition="left"/>
					<@ww.checkbox label="Exibir espaço para nota" name="exibirNota" labelPosition="left"/>
					<@ww.textfield label="Qtd. Linhas" id="qtdLinhas" name="qtdLinhas" maxLength="3" onkeypress = "return(somenteNumeros(event,''));" cssStyle="width:30px; text-align:right;"/>
				</ul>
			</@ww.div>
		</li>
		<br>
		<li>
			<@ww.div cssClass="divInfo" cssStyle="width:490px;">
				<ul>
					<@ww.label value="Exibir colunas extras (selecione duas colunas):*" />
					<@ww.checkbox label="Cargo" name="exibirCargo" id="exibirCargo" labelPosition="left" onchange="validaQtdColunas(this, true);"/>
					<@ww.checkbox label="Área Organizacional" name="exibirArea" id="exibirArea" labelPosition="left" onchange="validaQtdColunas(this, true);"/>
					<@ww.checkbox label="Estabelecimento" name="exibirEstabelecimento" id="exibirEstabelecimento" labelPosition="left" onchange="validaQtdColunas(this, true);"/>
					<@ww.checkbox label="Assinatura" name="exibirAssinatura" id="exibirAssinatura" labelPosition="left" onchange="validaQtdColunas(this, true);"/>
				</ul>
			</@ww.div>
		</li>
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio" onclick="${validarCampos};"  accesskey="I">
		</button>
	</div>
</body>
</html>