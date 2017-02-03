<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Relatório de Formação Escolar</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/populaEstabAreaCargo.js?version=${versao}"/>"></script>
	
	<script type="text/javascript">
		var empresaIds = new Array();
		<#if empresaIds?exists>
			<#list empresaIds as empresaId>
				empresaIds.push(${empresaId});
			</#list>
		</#if>
		
		$(function() {
			var empresa = $('#empresa').val();
	
			populaAreaComCargoVinculado(empresa);
			populaEstabelecimento(empresa);
			populaCargosByAreaVinculados(empresa);
			
			$('#cargosVinculadosAreas').click(function() {
				populaCargosByAreaVinculados();
			});
			
			$('#cargosVinculadosAreas').attr('checked', true);;
		});
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="formFormacao" id="formFormacao" action="imprimeRelatorioFormacaoEscolar.action" validate="true" method="POST">
		<#if compartilharColaboradores>
			<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="" onchange="newChangeEmpresa(this.value);"/>
		<#else>
			<@ww.hidden id="empresa" name="empresa.id"/>
			<li class="wwgrp">
				<label>Empresa:</label><br />
				<strong><@authz.authentication operation="empresaNome"/></strong>
			</li>
		</#if>
		
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" width="600" filtro="true"/>
		<@frt.checkListBox name="areasCheck" id="areaCheck" label="Áreas Organizacionais" list="areaOrganizacionalsCheckList" width="600" onClick="populaCargosByAreaVinculados();" filtro="true" selectAtivoInativo="true"/>
		<@ww.checkbox label="Exibir somente os cargos vinculados às áreas organizacionais acima." id="cargosVinculadosAreas" name="" labelPosition="left"/>
		<@frt.checkListBox name="cargosCheck" label="Cargos" list="cargosCheckList" width="600" filtro="true" selectAtivoInativo="true"/>
		
		<div class="buttonGroup">
			<button type="submit" class="btnRelatorio"></button>
		</div>
	</@ww.form>

</body>
</html>