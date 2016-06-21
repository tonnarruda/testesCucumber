<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Análise de Desempenho das Competências do Colaborador</title>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AvaliacaoDesempenhoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/NivelCompetenciaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<#assign validarCampos="return validaFormulario('form', new Array('avaliacao', 'avaliados'), null)"/>
	
	<script type="text/javascript">
		var empresaIds = new Array();
		<#if empresas?exists>
			<#list empresas as empresa>
				empresaIds.push(${empresa.id});
			</#list>
		</#if>
		
		$(document).ready(function($){
			var empresa = ${empresaSistema.id};
	
			populaArea(empresa);
			populaCargosByAreaVinculados();
			populaCargosAvaliado();
			populaOrdensNivelCompetencia();
			
			if($('#avaliacao').val())
			 	populaAvaliados();
			
			$('#cargosVinculadosAreas').click(function() {
				populaCargosByAreaVinculados();
			});
			
			$('#cargosVinculadosAreas').attr('checked', true);
			
			$('#wwctrl_avaliadores * span').eq(0).removeAttr('onclick').css('color', '#6E7B8B').css('cursor', 'default');
		});
		
		function populaAvaliados()
		{
			var areasIds   = getArrayCheckeds(document.form, 'areasCheck');
			var cargosIds  = getArrayCheckeds(document.form, 'cargosCheck');
			
			DWREngine.setAsync(true);
			DWRUtil.useLoadingMessage('Carregando...');
			AvaliacaoDesempenhoDWR.getAvaliados(populaSelectAvaliados, $('#avaliacao').val(), ${empresaSistema.id}, areasIds, cargosIds);
		}
			
		function populaSelectAvaliados(data)
		{
			$('#avaliados').find('option').remove().end();
			if(data != "")
				addOptionsByCollection('avaliados', data, 'Selecione...');
			else
				$('#avaliados').append('<option value="" selected="selected">Não existem avaliados para o filtro informado.</option>');
				
			populaCargosAvaliado();
		}
		
		function populaArea(empresaId)
		{
			DWREngine.setAsync(true);
			DWRUtil.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresas(createListArea, empresaId, empresaIds, null);
		}
		
		function createListArea(data)
		{
			addChecks('areasCheck',data, 'populaCargosByAreaVinculados();populaAvaliados();');
		}
	
		function populaCargosByAreaVinculados()
		{
			DWREngine.setAsync(true);
			DWRUtil.useLoadingMessage('Carregando...');
			var areasIds = getArrayCheckeds(document.forms[0],'areasCheck');
			var empresaId = ${empresaSistema.id};
			
			if ($('#cargosVinculadosAreas').is(":checked") && areasIds.length != 0)
				CargoDWR.getCargoByArea(createListCargosByArea, areasIds, "getNomeMercadoComEmpresa", empresaId);
			else
				CargoDWR.getByEmpresas(createListCargosByArea, empresaId, empresaIds);
		}
		
		function createListCargosByArea(data)
		{
			addChecks('cargosCheck',data, 'populaAvaliados()');
		}
	
		function exibeOuOcultaFiltros(){
			if($('#relatorioDetalhado').val() == 'true')
				$('#paraRealatorioDetalahado').show();
			else
				$('#paraRealatorioDetalahado').hide();
		}
		
		function populaCargosAvaliado()
		{
			if(($('#relatorioDetalhado').val() == 'true') && ($('#avaliados').val() != 0) && ($('#avaliados').val() != -1)){
				DWREngine.setAsync(true);
				DWRUtil.useLoadingMessage('Carregando...');
				AvaliacaoDesempenhoDWR.getAvaliadores(createListCargosAvaliadores, $('#avaliacao').val(), $('#avaliados').val());
			}else{
				$("input[name=avaliadores]").parent().remove();
				$("#listCheckBoxavaliadores > .info").remove();
				$('#listCheckBoxavaliadores').append('<div class="info"> <ul> <li>Utilize o filtro avaliado para popular os avaliadores.</li> </ul> </div>');
			}
		}
		
		function createListCargosAvaliadores(data)
		{
			addChecks('avaliadores',data, 'verificaQtdBarraGrafico()');
		}
		
		function populaOrdensNivelCompetencia()
		{
			if(($('#relatorioDetalhado').val() == 'true') && ($('#avaliacao').val() != 0) && ($('#avaliacao').val() != -1)){
				DWREngine.setAsync(true);
				DWRUtil.useLoadingMessage('Carregando...');
				NivelCompetenciaDWR.findNiveisCompetenciaByAvDesempenho(populaRadiosNiveisCompetencia, $('#avaliacao').val());
			}else{
				$('#ordensNivelCompetencia').empty();
				$('#ordensNivelCompetencia').append('<div class="info" style="width: 533px;"> <ul> <li>Utilize o filtro "Avaliação de desempenho que avaliam competência" para popular as competências.</li> </ul> </div>');
			}
		}
			
		function populaRadiosNiveisCompetencia(data)
		{
			$('#ordensNivelCompetencia').empty();
			
			tabela = '<table border=1> <tr>';				
			
			for (var key in data) {
				tabela += '<td width=100 align=middle>' + data[key].descricao + ' (' + data[key].ordem + ')</td>';
			}
			
			tabela += '	</tr> <tr>';
			
			checked = true;
			for (var key in data) {
				tabela += '<td width=100 align=middle><input type="radio" name="notaMinimaMediaGeralCompetencia" value="' + data[key].ordem  + '"'; 
			
				if(checked){
					tabela += ' checked';
					checked = false;
				}
				
				tabela +=  '/></td>';
			}
			
			tabela += '</tr></table>'
			
			$('#ordensNivelCompetencia').append(tabela);
		}
		
		function verificaQtdBarraGrafico()
		{
			qtdColunasAdicionais = 4;
			$("input[name=avaliadores]:not(:checked)").removeAttr('disabled');
			
			if($('#agruparAvaliador').checked){
				cargosAdicionados = [];
				$("input[name=avaliadores]:checked").each(function(){
					textoChecked = $(this).parent().text();
					regExp = /\(([^)]+)\)/;
					cargo = regExp.exec(textoChecked)[1];
					
					if(!(cargosAdicionados.indexOf(cargo) == 0))
						cargosAdicionados.push(cargo);
				});
				
				if(cargosAdicionados.length >= qtdColunasAdicionais)
					$("input[name=avaliadores]:not(:checked)").attr('disabled', true);
			}else{
				if(	$("input[name=avaliadores]:checked").length >= qtdColunasAdicionais)
					$("input[name=avaliadores]:not(:checked)").attr('disabled', true);
			}
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@ww.form name="form" action="analiseDesempenhoCompetenciaColaborador.action" onsubmit="${validarCampos}" method="POST">
		<@ww.select label="Gerar relatório" name="relatorioDetalhado" id="relatorioDetalhado" list=r"#{true:'Detalhado',false:'Resumido'}" cssStyle="width: 600px;" onchange="exibeOuOcultaFiltros();"/>
		<@ww.select label="Avaliação de desempenho que avaliam competência" required="true" name="avaliacaoDesempenho.id" id="avaliacao" list="avaliacaoDesempenhos" listKey="id" listValue="titulo" cssStyle="width: 600px;" headerKey="" headerValue="Selecione..." onchange="populaAvaliados();populaOrdensNivelCompetencia();"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" width="600" onClick="populaCargosByAreaVinculados();populaAvaliados();" filtro="true" selectAtivoInativo="true"/>
		<@ww.checkbox label="Exibir somente os cargos vinculados às áreas organizacionais acima." id="cargosVinculadosAreas" name="" labelPosition="left"/>
		<@frt.checkListBox label="Cargos" name="cargosCheck" id="cargosCheck" list="cargosCheckList"  width="600" onClick="populaAvaliados();" filtro="true" selectAtivoInativo="true"/>
		<@ww.select label="Avaliado" required="true" name="avaliado.id" id="avaliados" list="participantesAvaliadores" listKey="id" listValue="nome" cssStyle="width: 600px;" headerKey="-1" headerValue="Selecione uma avaliação de desempenho"  onchange="populaCargosAvaliado();"/>
		
		<div id="paraRealatorioDetalahado">
			<@ww.checkbox label="Agrupar pelo cargo do avaliador." id="agruparAvaliador" name="" labelPosition="left"/>
			<@frt.checkListBox label="Avaliadores (máx. 4 opções)" name="avaliadores" id="avaliadores" list="avaliadoresCheckList" width="600" filtro="true" selectAtivoInativo="true"/>
			Nota mínima considerada em "Média Geral das Competências":</br>
			<div id="ordensNivelCompetencia"/>
		</div>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="btnRelatorio"></button>		
	</div>
</body>
</html>