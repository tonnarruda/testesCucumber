<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<title>Incluir Colaboradores na Turma - ${colaboradorTurma.turma.descricao}</title>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<script type='text/javascript'>
		function populaAreas(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresa(createListAreas, empresaId);
		}

		function createListAreas(data)
		{
			addChecks('areasCheck', data)
		}
		
		function enviaForm()
		{
			if(document.getElementById('divAreaOrganizacionals').style.display != 'none')
				document.getElementById('filtrarPor').value = 1;
			else if(document.getElementById('divColaboradors').style.display != 'none')
				document.getElementById('filtrarPor').value = 4;

			validaFormulario('form', null, null);
		}

		function marcarDesmarcar(frm)
		{
			var vMarcar;

			if (document.getElementById('md').checked)
				vMarcar = true;
			else
				vMarcar = false;

			with(frm)
			{
				for(i = 0; i < elements.length; i++)
				{
					if(elements[i].name == 'colaboradoresId' && elements[i].type == 'checkbox')
						elements[i].checked = vMarcar;
				}
			}
		}

		function verificaSelecao(frm)
		{
			var taValendo = false;
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
				document.formColab.submit();
				return true;
			}
			else
			{
				jAlert('Selecione ao menos um colaborador!');
				return false;
			}
		}

		function filtrarOpt()
		{
			value =	document.getElementById('optFiltro').value;
			if(value == "1")
			{
				exibe("divColaboradors");
				oculta("divAreaOrganizacionals");
			}
			else if(value == "2")
			{
				oculta("divColaboradors");
				exibe("divAreaOrganizacionals");
			}
			else if(value == "3")
			{
				oculta("divColaboradors");
				oculta("divAreaOrganizacionals");
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

<#include "../ftl/showFilterImports.ftl" />

<@ww.head/>
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

<#if !colaborador?exists>
	<#assign labelFiltro="Ocultar Filtro"/>
	<#assign imagemFiltro="/imgs/arrow_up.gif"/>
	<#assign classHidden="">
</#if>

</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="form" action="listFiltro.action" onsubmit="enviaForm();" method="POST" id="formBusca">

            <@ww.select label="Empresa" name="empresaId" id="empresaId" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="-1" onchange="populaAreas(this.value);" disabled="!compartilharColaboradores"/>
			<@ww.textfield label="Nome do Colaborador" id="nome" name="colaborador.nome" maxLength="100" cssStyle="width: 500px;" />
			<@ww.textfield label="Matrícula do Colaborador" id="matricula" name="colaborador.matricula" maxLength="20" cssStyle="width: 170px;"/>

			<@ww.select id="optFiltro" label="Filtrar Por" name="filtro"
				list=r"#{'2':'Áreas Organizacionais','1':'Colaboradores pré-inscritos no curso'}" onchange="filtrarOpt();" />

			<#--filtrarPor do colaboradoresCursosCheck tem que ser 4 ta amarrado nocodigo pois dele depende um update no ColaboradorTurmaEdit e o hidden no formColab-->
			<br>
			<span id="divColaboradors" style="display:none">
				<@frt.checkListBox name="colaboradoresCursosCheck" label="Colaboradores pré-inscritos no curso" list="colaboradoresCursosCheckList" />
			</span>

			<span id="divAreaOrganizacionals" style="display:''">
				<@frt.checkListBox name="areasCheck" label="Áreas Organizacionais" list="areasCheckList" />
			</span>

			<!-- 
				//31/01/2011 - Francisco, retirei acho que os clientes não tão usando, retirar depois de 3 meses, hehehe
				<span id="divGruposCargos" style="display:none">
					<@frt.checkListBox name="gruposCheck" label="Grupos Ocupacionais" list="gruposCheckList" onClick="populaCargos(document.forms[0],'gruposCheck', ${empresaId});"/>
					<@frt.checkListBox name="cargosCheck" label="Cargos" id="cargosCheck" list="cargosCheckList" />
				</span>
			-->

			<input type="submit" value="" class="btnPesquisar grayBGE" />

			<@ww.hidden name="colaboradorTurma.id" />
			<@ww.hidden name="turma.id" />
			<@ww.hidden id="filtrarPor" name="filtrarPor"/>
			<@ww.hidden name="turma.curso.id"/>
			<@ww.hidden name="planoTreinamento" />

		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>

	<#if colaboradorTurmas?exists>
		<@ww.form name="formColab" action="insert.action" validate="true" method="POST">
			<@display.table name="colaboradorTurmas" id="colaboradorTurma" class="dados" defaultsort=2 sort="list">
				<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(document.formColab);' checked />" style="width: 30px; text-align: center;">
					<input type="checkbox" value="${colaboradorTurma.id}" name="colaboradoresId" checked/>
				</@display.column>
				<@display.column property="colaborador.nome" title="Nome" style="width: 500px;"/>
				<@display.column property="colaborador.matricula" title="Matrícula" style="width: 100px;"/>
				<@display.column property="colaborador.areaOrganizacional.descricao" title="Área" style="width: 300px;"/>
			</@display.table>

			<@ww.hidden name="turma.id" />
			<@ww.hidden name="turma.curso.id" />
			<@ww.hidden name="filtrarPor" />
			<@ww.hidden name="planoTreinamento" />

		</@ww.form>
	</#if>

	<div class="buttonGroup">
		<#if colaboradorTurmas?exists && colaboradorTurmas?size != 0>
			<button onclick="javascript: verificaSelecao(document.formColab);" class="btnInserirSelecionados"></button>
		</#if>
		<button onclick="window.location='list.action?turma.id=${turma.id}&curso.id=${turma.curso.id}&planoTreinamento=${planoTreinamento?string}'" class="btnVoltar"></button>
	</div>

	<script type='text/javascript'>
		filtrarOpt();
	</script>
</body>
</html>