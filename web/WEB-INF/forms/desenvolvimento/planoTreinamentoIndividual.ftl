<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<title>Plano de Desenvolvimento Individual (PDI)</title>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		
		.empresaEstabelecimento { text-align: left; }
		.colaborador { text-align: left; background-color: #7BA6D3; color: #FFFFFF !important; font-weight: bold; }
		.titulo { background-color: #F3F3F3; font-weight: bold; }
		.dados th {	padding: 5px; }
	</style>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript'>
		var empresaIds = new Array();
		<#if empresaIds?exists>
			<#list empresaIds as empresaId>
				empresaIds.push(${empresaId});
			</#list>
		</#if>
		
		function populaEstabelecimento(empresaId)
		{
			DWREngine.setAsync(true);
			DWRUtil.useLoadingMessage('Carregando...');
			EstabelecimentoDWR.getByEmpresas(createListEstabelecimento, empresaId, empresaIds);
		}

		function createListEstabelecimento(data)
		{
			addChecks('estabelecimentosCheck',data);
		}
		
		function populaArea(empresaId)
		{
			DWREngine.setAsync(true);
			DWRUtil.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresas(createListArea, empresaId, empresaIds, null);
		}

		function createListArea(data)
		{
			addChecks('areasCheck',data);
		}
		
		function prepareCriarTurmas()
		{
			var qtdMarcados = $(":checkbox[name='colaboradoresCursos']:checked").size();
			<#if agruparPor == 'C'>
				var mensagem = 'Selecione os treinamentos para formação das turmas';
			<#else>
				var mensagem = 'Selecione os colaboradores para formação das turmas';
			</#if>
			
			if (qtdMarcados < 1)
			{
				jAlert(mensagem);
				return false;
			}
			
			document.formAplicar.submit();
		}
		
		function carregar()
		{
			document.form.action = 'pdi.action';
			return validaFormulario('form', null, null);
		}
		
		function imprimir()
		{
			document.form.action = 'imprimirPdi.action';
			document.form.submit();
		}
		
		function marcarDesmarcarTodos(marcar, id)
		{
			var classe = id ? '.check' + id : '.check';
		
			if (marcar)
				$(classe).attr('checked', 'checked');
			else 
				$(classe).removeAttr('checked');
		}
	</script>

	<#include "../ftl/mascarasImports.ftl" />

	<@ww.head/>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@ww.form name="form" action="" method="POST">
		<@ww.select label="Empresa" name="empresaId" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="" onchange="populaEstabelecimento(this.value); populaArea(this.value);"/>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" filtro="true"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
		<@ww.select label="Filtrar colaboradores" name="colaboradoresAvaliados" id="colaboradoresAvaliados" list=r"#{'S':'Somente colaboradores com avaliação de nível de competência','N':'Somente colaboradores sem avaliação de nível de competência','T':'Todos os colaboradores'}" cssStyle="width:500px;" />
		<@ww.select label="Agrupar por" name="agruparPor" id="agruparPor" list=r"#{'C':'Colaborador','T':'Treinamento'}" />
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="carregar()" class="btnCarregar"></button>
	</div>
	
	<#if configuracaoNivelCompetencias?exists && 0 < configuracaoNivelCompetencias?size>
		
		<label style="float: right;">
			<input type="checkbox" onclick="marcarDesmarcarTodos(this.checked)"/> 
			Marcar/Desmarcar todos
		</label>
		
		<br clear="all"/><br />
	
		<form name="formAplicar" action="prepareAplicarPdi.action" method="POST">
	
			<#assign estabelecimentoAnterior = ""/>
			<#assign colaboradorAnterior = ""/>
			<#assign cursoAnterior = ""/>
			<#assign competenciaDescricaoAnterior = ""/>

			<#list configuracaoNivelCompetencias as configNivel>
				<#if agruparPor == 'C'>
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
							<tbody>
					</#if>
					
					<#if colaboradorAnterior != configNivel.configuracaoNivelCompetenciaColaborador.colaborador.nome>
						<#assign i = 0/>			
						<tr>
							<td colspan="4" class="colaborador">${configNivel.configuracaoNivelCompetenciaColaborador.colaborador.nome}</td>
						</tr>
						<tr>
							<td class="titulo" width="200">Competência</td>
							<td class="titulo" width="100">Nível Cargo/Faixa</td>
							<td class="titulo" width="100">Nível Colaborador</td>
							<td class="titulo">
								<input type="checkbox" onclick="marcarDesmarcarTodos(this.checked, ${configNivel.configuracaoNivelCompetenciaColaborador.colaborador.id});" class="check"/> 
								Treinamentos Sugeridos
							</td>
						</tr>
					</#if>
		
					<#if colaboradorAnterior != configNivel.configuracaoNivelCompetenciaColaborador.colaborador.nome || competenciaDescricaoAnterior != configNivel.competenciaDescricao>
						<#if estabelecimentoAnterior != "">	
								</td>
							</tr>
						</#if>
						
						<tr class="<#if i%2 == 0>odd<#else>even</#if>">
							<td valign="top">${configNivel.competenciaDescricao}</td>
							<td valign="top">${configNivel.nivelCompetencia.descricao}</td>
							<td valign="top">
								<#if configNivel.nivelCompetenciaColaborador.descricao?exists>
									${configNivel.nivelCompetenciaColaborador.descricao}
								<#else>
									Não avaliado
								</#if>
							</td>
							<td>
							
						<#assign i = i + 1/>
					</#if>
					
					<label>
						<input type="checkbox" name="colaboradoresCursos" value="${configNivel.configuracaoNivelCompetenciaColaborador.colaborador.id},${configNivel.configuracaoNivelCompetenciaColaborador.colaborador.nome},${configNivel.cursoId},${configNivel.cursoNome?html?replace("'","\'")}" class="check check${configNivel.configuracaoNivelCompetenciaColaborador.colaborador.id}"/> 
						${configNivel.cursoNome}
					</label>
					<br />
				
				<#else>
					<#-- ESTABELECIMENTO -->
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
							<tbody>
					</#if>
					
					<#-- CURSO -->
					<#if cursoAnterior != configNivel.cursoNome>
						<#assign i = 0/>
						<tr>
							<td colspan="4" class="colaborador">${configNivel.cursoNome}</td>
						</tr>
						<tr>
							<td class="titulo" width="200">Competência</td>
							<td class="titulo" width="100">Nível Cargo/Faixa</td>
							<td class="titulo" width="100">Nível Colaborador</td>
							<td class="titulo">
								<input type="checkbox" onclick="marcarDesmarcarTodos(this.checked, ${configNivel.cursoId});" class="check"/>
								Colaboradores Sugeridos
							</td>
						</tr>
					</#if>

					<#-- COMPETENCIA -->
					<#if competenciaDescricaoAnterior != configNivel.competenciaDescricao || cursoAnterior != configNivel.cursoNome>
						<#if estabelecimentoAnterior != "">	
								</td>
							</tr>
						</#if>
						
						<tr class="<#if i%2 == 0>odd<#else>even</#if>">
							<td valign="top">${configNivel.competenciaDescricao}</td>
							<td valign="top">${configNivel.nivelCompetencia.descricao}</td>
							<td valign="top">
								<#if configNivel.nivelCompetenciaColaborador.descricao?exists>
									${configNivel.nivelCompetenciaColaborador.descricao}
								<#else>
									-
								</#if>
							</td>
							<td>
							
						<#assign i = i + 1/>
					</#if>

					<label>
						<input type="checkbox" name="colaboradoresCursos" value="${configNivel.configuracaoNivelCompetenciaColaborador.colaborador.id},${configNivel.configuracaoNivelCompetenciaColaborador.colaborador.nome},${configNivel.cursoId},${configNivel.cursoNome}" class="check check${configNivel.cursoId}"/> 
						${configNivel.configuracaoNivelCompetenciaColaborador.colaborador.nome}
					</label>
					<br />

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
			<@ww.hidden name="empresaId" />
		</form>

		<div class="buttonGroup">
			<button type="button" onclick="prepareCriarTurmas()" class="btnCriarTurmas"></button>
			<button type="button" onclick="imprimir()" class="btnImprimirPdf"></button>
		</div>
	</#if>
</body>
</html>