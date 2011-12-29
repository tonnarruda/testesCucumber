<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<title>Incluir Colaboradores na Turma - ${colaboradorTurma.turma.descricao}</title>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/jquery-ui/jquery-ui-1.8.9.custom.css"/>');
	</style>
	
	<#include "../ftl/mascarasImports.ftl" />

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorTurmaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>

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

			validaFormularioEPeriodo('form', false, new Array('admIni', 'admFim'));
		}

		function marcarDesmarcar()
		{
			var marcar = $('#md').attr('checked');
			$("input[name='colaboradoresId']").attr('checked', marcar);
		}

		function verificaSelecao()
		{
			var colabsIds = $("input[name='colaboradoresId']:checked").map(function(){
			    return parseInt($(this).val());
			});
			
			if(colabsIds.length > 0)
			{
				DWRUtil.useLoadingMessage('Carregando...');
				ColaboradorTurmaDWR.checaColaboradorInscritoEmOutraTurma(colabNaOutraTurma, $("input[name='turma.id']").val(), $("input[name='turma.curso.id']").val(), colabsIds.toArray());
			}
			else
			{
				jAlert('Selecione ao menos um colaborador!');
				return false;
			}
		}

		function colabNaOutraTurma(msg)
		{
			//newConfirm(msg, function() { document.formColab.submit(); });
			if (msg != "")
				$('<div>' + msg + '</div>').dialog({title: 'Os seguintes colaboradores já estão inscritos neste curso.<br />Deseja realmente incluí-los nesta turma?',
													modal: true, 
													height: 400,
													width: 700,
													buttons: [
													    {
													        text: "Sim",
													        click: function() { document.formColab.submit(); }
													    },
													    {
													        text: "Não",
													        click: function() { $(this).dialog("close"); }
													    }
													] 
													});
			else 
				document.formColab.submit();
		}
		
		function filtrarOpt()
		{
			value =	$('#optFiltro').val();
			if(value == "1")
			{
				$("#divColaboradors").show();
				$("#divAreaOrganizacionals").hide();
			}
			else if(value == "2")
			{
				$("#divColaboradors").hide();
				$("#divAreaOrganizacionals").show();
			}
			else if(value == "3")
			{
				$("#divColaboradors").hide();
				$("#divAreaOrganizacionals").hide();
			}
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

<#assign dataAdmIni=""/>
<#if dataAdmissaoIni?exists>
	<#assign dataAdmIni=dataAdmissaoIni?date/>
</#if>
<#assign dataAdmFim=""/>
<#if dataAdmissaoFim?exists>
	<#assign dataAdmFim=dataAdmissaoFim?date/>
</#if>

</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="form" action="listFiltro.action" onsubmit="enviaForm();" method="POST" id="formBusca">

            <@ww.select label="Empresa" name="empresaId" id="empresaId" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="-1" onchange="populaAreas(this.value);" disabled="!compartilharColaboradores"/>
			
			<label>Admitidos entre:</label><br />
			<@ww.datepicker name="dataAdmissaoIni" value="${dataAdmIni}" id="admIni" cssClass="mascaraData validaDataIni" liClass="liLeft"/>
			<@ww.label value="e" liClass="liLeft" />
			<@ww.datepicker name="dataAdmissaoFim" value="${dataAdmFim}" id="admFim" cssClass="mascaraData validaDataFim"/>
			
			<@ww.textfield label="Nome do Colaborador" id="nome" name="colaborador.nome" maxLength="100" cssStyle="width: 500px;" />
			<@ww.textfield label="Matrícula do Colaborador" id="matricula" name="colaborador.matricula" maxLength="20" cssStyle="width: 170px;"/>

			<@ww.select id="optFiltro" label="Filtrar Por" name="filtro"
				list=r"#{'2':'Áreas Organizacionais','1':'Colaboradores pré-inscritos no curso'}" onchange="filtrarOpt();" />

			<#--filtrarPor do colaboradoresCursosCheck tem que ser 4 ta amarrado no codigo pois dele depende um update no ColaboradorTurmaEdit e o hidden no formColab-->
			<br>
			<span id="divColaboradors" style="display:none">
				<@frt.checkListBox name="colaboradoresCursosCheck" label="Colaboradores pré-inscritos no curso" list="colaboradoresCursosCheckList" />
			</span>

			<span id="divAreaOrganizacionals" style="display:''">
				<@frt.checkListBox name="areasCheck" label="Áreas Organizacionais" list="areasCheckList" />
			</span>

			<input type="button" onclick="enviaForm();" value="" class="btnPesquisar grayBGE" />

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
			
			<@display.table name="colaboradorTurmas" id="colaboradorTurma" class="dados" defaultsort=2 sort="list" >
				<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar();' checked='checked'/>" style="width: 30px; text-align: center;">
					<input type="checkbox" value="${colaboradorTurma.id}" name="colaboradoresId" checked='checked'/>
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
			<button onclick="javascript: verificaSelecao();" class="btnInserirSelecionados"></button>
		</#if>
		<button onclick="window.location='list.action?turma.id=${turma.id}&curso.id=${turma.curso.id}&planoTreinamento=${planoTreinamento?string}'" class="btnVoltar"></button>
	</div>

	<script type='text/javascript'>
		filtrarOpt();
	</script>
</body>
</html>