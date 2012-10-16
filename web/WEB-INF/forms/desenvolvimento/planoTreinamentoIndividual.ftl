<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<title>Plano de Treinamento Individual (PDI)</title>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
		
		.empresaEstabelecimento { text-align: left; }
		.colaborador { text-align: left; background-color: #7BA6D3; color: #FFFFFF !important; font-weight: bold; }
		.titulo { background-color: #F3F3F3; font-weight: bold; }
		.dados th {	padding: 5px; }
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
	
		<@ww.form name="formAplicar" action="prepareAplicarPdi.action" method="POST">
	
			<#assign estabelecimentoAnterior = ""/>
			<#assign colaboradorAnterior = ""/>
			<#assign cursoAnterior = ""/>
			<#assign competenciaDescricaoAnterior = ""/>

			<#list configuracaoNivelCompetencias as configNivel>
				<#if estabelecimentoAnterior != configNivel.configuracaoNivelCompetenciaColaborador.colaborador.estabelecimento.nome>
					<#if estabelecimentoAnterior != "">	
							</tbody>
						</table>
					</#if>
			
					<table class="dados">
						<thead>
							<tr>
								<th colspan="4" class="empresaEstabelecimento">${configNivel.configuracaoNivelCompetenciaColaborador.colaborador.empresa.nome} - ${configNivel.configuracaoNivelCompetenciaColaborador.colaborador.estabelecimento.nome}</th>
							</tr>
						</thead>
				</#if>
				
				<#if agruparPor == 'C'>
					<#if colaboradorAnterior != configNivel.configuracaoNivelCompetenciaColaborador.colaborador.nome>
						<#assign i = 0/>			
						<tbody>
							<tr>
								<td colspan="4" class="colaborador">${configNivel.configuracaoNivelCompetenciaColaborador.colaborador.nome}</td>
							</tr>
							<tr>
								<td class="titulo" width="200">Competência</td>
								<td class="titulo" width="100">Nível Cargo/Faixa</td>
								<td class="titulo" width="100">Nível Colaborador</td>
								<td class="titulo">Treinamentos Sugeridos</td>
							</tr>
					</#if>
				
				<#else>
					<#if cursoAnterior != configNivel.cursoNome>
						<#assign i = 0/>
						<tbody>
							<tr>
								<td colspan="4" class="colaborador">${configNivel.cursoNome}</td>
							</tr>
							<tr>
								<td class="titulo" width="200">Competência</td>
								<td class="titulo" width="100">Nível Cargo/Faixa</td>
								<td class="titulo" width="100">Nível Colaborador</td>
								<td class="titulo">Colaboradores Sugeridos</td>
							</tr>
					</#if>
				</#if>
	
				<#if competenciaDescricaoAnterior != configNivel.competenciaDescricao>
					<#if estabelecimentoAnterior != "">	
							</td>
						</tr>
					</#if>
					
					<tr class="<#if i%2 == 0>odd<#else>even</#if>">
						<td>${configNivel.competenciaDescricao}</td>
						<td>${configNivel.nivelCompetencia.descricao}</td>
						<td>
							<#if configNivel.nivelCompetenciaColaborador.descricao?exists>
								${configNivel.nivelCompetenciaColaborador.descricao}
							<#else>
								-
							</#if>
						</td>
						<td>
						
					<#assign i = i + 1/>
				</#if>
				
				<#if agruparPor == 'C'>
					<input type="checkbox" name="colaboradoresCursos" value="${configNivel.configuracaoNivelCompetenciaColaborador.colaborador.id},${configNivel.configuracaoNivelCompetenciaColaborador.colaborador.nome},${configNivel.cursoId},${configNivel.cursoNome}" /> ${configNivel.cursoNome}<br />
				<#else>
					<input type="checkbox" name="colaboradoresCursos" value="${configNivel.configuracaoNivelCompetenciaColaborador.colaborador.id},${configNivel.configuracaoNivelCompetenciaColaborador.colaborador.nome},${configNivel.cursoId},${configNivel.cursoNome}" /> ${configNivel.configuracaoNivelCompetenciaColaborador.colaborador.nome}<br />
				</#if>
					 
				<#assign estabelecimentoAnterior = configNivel.configuracaoNivelCompetenciaColaborador.colaborador.estabelecimento.nome/>
				<#assign colaboradorAnterior = configNivel.configuracaoNivelCompetenciaColaborador.colaborador.nome/>
				<#assign cursoAnterior = configNivel.cursoNome/>
				<#assign competenciaDescricaoAnterior = configNivel.competenciaDescricao/>
			</#list>
						</td>
					</tr>
				</tbody>
			</table>

			<div class="buttonGroup">
				<button type="submit" class="btnAplicar"></button>
			</div>
		</@ww.form>
	</#if>
</body>
</html>