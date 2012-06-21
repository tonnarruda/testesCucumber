<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>

<title>Exportação de Treinamentos</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/TurmaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CursoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/OcorrenciaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
	<#assign validarCampos="return validaFormulario('form', new Array('inicio','fim','@turmasCheck', 'ocorrencias'), new Array('inicio','fim'))"/>
	<#include "../ftl/mascarasImports.ftl" />
	
	<#if dataIni?exists>
		<#assign inicio=dataIni?date />
	<#else>
		<#assign inicio="" />
	</#if>
	<#if dataFim?exists>
		<#assign fim=dataFim?date />
	<#else>
		<#assign fim="" />
	</#if>
	
	<script type='text/javascript'>
		$(function(){
			
			$('input[name="cursosCheck"]').bind(function(){
					alert('a');
					
				if (this.checked)
					alert('checked');
				else
					alert('não checked');
			});
			
			var empresaValue = $('#empresaId').val();
			populaArea(empresaValue);
			populaEstabelecimento(empresaValue);
			populaCurso(empresaValue);
			populaOcorrencia(empresaValue);
		});
		
		function populaEstabelecimento(empresaId)
		{
			DWREngine.setAsync(false);
			DWRUtil.useLoadingMessage('Carregando...');
			EstabelecimentoDWR.getByEmpresas(createListEstabelecimento, empresaId, null);
		}
	
		function createListEstabelecimento(data)
		{
			addChecks('estabelecimentosCheck',data);
		}
			
		function populaArea(empresaId)
		{
			DWREngine.setAsync(false);
			DWRUtil.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresas(createListArea, empresaId, null);
		}

		function createListArea(data)
		{
			addChecks('areasCheck',data);
		}
		
		function populaCurso(empresaId)
		{
			DWREngine.setAsync(false);
			DWRUtil.useLoadingMessage('Carregando...');
			
			CursoDWR.getCursosByEmpresa(populaCursos, empresaId);
		}

		function populaCursos(data)
		{
			addChecks('cursosCheck', data);
			$('#cursosCheck').append('onClick', '');
		}
		
		function getTurmasByFiltro()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var cursoIds = getArrayCheckeds(document.forms[0], 'cursosCheck');
			TurmaDWR.getTurmasByCursos(populaTurmas, cursoIds);
		}

		function populaTurmas(data)
		{
			addChecks('turmasCheck', data);
		}
		
		function populaOcorrencia(empresaId)
		{
			DWREngine.setAsync(false);
			DWRUtil.useLoadingMessage('Carregando...');
			OcorrenciaDWR.getByEmpresaComCodigoAc(populaOcorrencias, empresaId);
		}

		function populaOcorrencias(data)
		{
			DWRUtil.removeAllOptions("ocorrencias");
			
			var mensagem = "Selecione..."; 
			
			if(data.toSource() === "({})")
				mensagem = "Não existe ocorrencias para essa empresa"; 
			
			$('#ocorrencias').append('<option value=\"\">'+mensagem+'</option>');
			DWRUtil.addOptions("ocorrencias", data);
		}
	</script>
	
</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@ww.form name="form" action="gerarArquivoExportacao.action" validate="true" onsubmit="${validarCampos}" method="POST" >
		<@ww.select label="Empresa" name="empresaId" id="empresaId" list="empresas" listKey="id" listValue="nome" onchange="populaEstabelecimento(this.value);populaArea(this.value);populaCurso(this.value);populaOcorrencia(this.value);" disabled="!compartilharColaboradores"/>		
		<@ww.select label="Ocorrencia" name="ocorrenciaId" id="ocorrencias" list="ocorrencias" listKey="id" listValue="descricaoComEmpresa"/>		
		
		<@ww.datepicker label="Período" required="true" value="${inicio}" name="dataIni" id="inicio" cssClass="mascaraData validaDataIni" after="a" liClass="liLeft"/>
		<@ww.datepicker label="" value="${fim}" name="dataFim" id="fim" cssClass="mascaraData validaDataFim"/>

		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos"  list="estabelecimentosCheckList" width="600"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList"  width="600"/>

		<@frt.checkListBox name="cursosCheck" id="cursosCheck" label="Cursos" list="cursosCheckList" onClick="getTurmasByFiltro();" width="600" />
		<@frt.checkListBox name="turmasCheck" id="turmasCheck" label="Cursos / Turmas *" list="turmasCheckList" width="600" />
		

		
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnSalvarArquivos"></button>
	</div>
</body>
</html>