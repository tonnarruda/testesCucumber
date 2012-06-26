<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>

<title>Exportar Treinamentos para o TRU</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-1.4.4.min.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/TurmaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CursoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
	<#assign validarCampos="return validaFormulario('form', new Array('@turmasCheck', 'descricaoTRU', 'codigoTRU'), new Array('inicio','fim'))"/>
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
		var cursosIds = [];

		$(function(){
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
			EstabelecimentoDWR.getByEmpresa(createListEstabelecimento, empresaId);
		}
	
		function createListEstabelecimento(data)
		{
			addChecks('estabelecimentosCheck',data);
		}
			
		function populaArea(empresaId)
		{
			DWREngine.setAsync(false);
			DWRUtil.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresa(createListArea, empresaId);
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
					
			$("input[name='cursosCheck']").each(function() {
				$(this).click(function() {
					if($(this).is(':checked'))
						cursosIds.push($(this).val());
					else
					  cursosIds.splice(cursosIds.indexOf($(this).val()),1);
					
					getTurmasByFiltro();	
				});
			});
		}
		
		function getTurmasByFiltro()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			TurmaDWR.getTurmasByCursos(populaTurmas, cursosIds);
		}

		function populaTurmas(data)
		{
			addChecks('turmasCheck', data);
		}

	</script>
	
</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@ww.form name="form" action="gerarArquivoExportacao.action" validate="true" onsubmit="${validarCampos}" method="POST" >
		<@ww.select label="Empresas Integradas" name="empresaId" id="empresaId" list="empresas" listKey="id" listValue="nome" onchange="populaEstabelecimento(this.value);populaArea(this.value);populaCurso(this.value);"/>		

		<@ww.datepicker label="Período" value="${inicio}" name="dataIni" id="inicio" cssClass="mascaraData validaDataIni" after="a" liClass="liLeft"/>
		<@ww.datepicker label="" value="${fim}" name="dataFim" id="fim" cssClass="mascaraData validaDataFim"/>

		<@ww.textfield label="Cód. da Ocorrência no TRU" name="codigoTRU" id="codigoTRU" onkeypress="return(somenteNumeros(event,''));" size="3"  maxLength="3"/>
		<@ww.textfield label="Nomeclatura da Ocorrência no TRU" name="descricaoTRU" id="descricaoTRU" cssStyle="width: 250px;" maxLength="30"/>		
		<@ww.select label="Tirar o colaborador da escala?" name="escala" list=r"#{'S':'Sim','N':'Não'}"/>
		
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos"  list="estabelecimentosCheckList" width="600"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList"  width="600"/>

		<@frt.checkListBox name="cursosCheck" id="cursosCheck" label="Cursos" list="cursosCheckList" onClick="getTurmasByFiltro();" width="600" />
		<@frt.checkListBox name="turmasCheck" id="turmasCheck" label="Cursos / Turmas *" list="turmasCheckList" width="600" />
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnExportar"></button>
	</div>
</body>
</html>