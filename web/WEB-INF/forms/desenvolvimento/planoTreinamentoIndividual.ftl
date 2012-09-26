<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<title>Plano de Treinamento Individual (PDI)</title>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript'>
		var empresaIds = new Array();
		<#if empresaIds?exists>
			<#list empresaIds as empresaId>
				empresaIds.push(${empresaId});
			</#list>
		</#if>
		
		function populaEstabelecimento(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			EstabelecimentoDWR.getByEmpresas(createListEstabelecimento, empresaId, empresaIds);
		}

		function createListEstabelecimento(data)
		{
			addChecks('estabelecimentosCheck',data);
		}
		
		function populaArea(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresas(createListArea, empresaId, empresaIds);
		}

		function createListArea(data)
		{
			addChecks('areasCheck',data);
		}
		
		$(function($)
		{
			var empresa = $('#empresa').val();
		
			DWREngine.setAsync(false);
			
			populaArea(empresa);
			populaEstabelecimento(empresa);
		});
	</script>

	<#include "../ftl/mascarasImports.ftl" />

	<@ww.head/>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@ww.form name="form" action="pdi.action" method="POST">
		<@ww.select label="Empresa" name="empresaId" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="" onchange="populaEstabelecimento(this.value); populaArea(this.value);"/>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList"/>
		<@ww.select label="Agrupar por" name="agruparPor" id="agruparPor" list=r"#{'C':'Colaborador','T':'Treinamento'}" />
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="return validaFormulario('form', null, null);" class="btnCarregar" ></button>
	</div>
	
	<br /><br />

	<#if configuracaoNivelCompetencias?exists>
		<#assign empresaAnterior = ""/>
		<#assign estabelecimentoAnterior = ""/>
		<#assign colaboradorAnterior = ""/>

		<ul>
			<#list configuracaoNivelCompetencias as configNivel>
				<li>
					<#if empresaAnterior != configNivel.configuracaoNivelCompetenciaColaborador.colaborador.empresa.nome>
						<li>- ${configNivel.configuracaoNivelCompetenciaColaborador.colaborador.empresa.nome}</li>
					</#if>
					
					<#if estabelecimentoAnterior != configNivel.configuracaoNivelCompetenciaColaborador.colaborador.estabelecimento.nome>
						<li>--- ${configNivel.configuracaoNivelCompetenciaColaborador.colaborador.estabelecimento.nome}</li>
					</#if>
					
					<#if colaboradorAnterior != configNivel.configuracaoNivelCompetenciaColaborador.colaborador.nome>
						<li>----- ${configNivel.configuracaoNivelCompetenciaColaborador.colaborador.nome}</li>
					</#if>
	
					<li>------- ${configNivel.nivelCompetencia.descricao} - ${configNivel.competenciaDescricao}</li>
				</li>
				
				<#assign empresaAnterior = configNivel.configuracaoNivelCompetenciaColaborador.colaborador.empresa.nome/>
				<#assign estabelecimentoAnterior = configNivel.configuracaoNivelCompetenciaColaborador.colaborador.estabelecimento.nome/>
				<#assign colaboradorAnterior = configNivel.configuracaoNivelCompetenciaColaborador.colaborador.nome/>
			</#list>
		</ul>
	</#if>
</body>
</html>