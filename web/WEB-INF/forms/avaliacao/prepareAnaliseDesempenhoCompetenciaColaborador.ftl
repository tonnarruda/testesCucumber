<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Análise de Desempenho das Competências do Colaborador</title>
	
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
	</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AvaliacaoDesempenhoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EmpresaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/NivelCompetenciaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<#assign validarCampos="return validaFormulario('form', new Array('avaliacao', 'avaliados'), null)"/>
	
	<style>
		#previews {
			height: 150px;
	   		width: 295px;
	   		margin: 30px;
			margin-top: 0;
			margin-left: 10px;
		}
		
		#previews .box-type {
			height: 170px;
			text-align: center;
			float: left;
			margin-right: 15px;
			margin-bottom: 10px;
		}
		
		#previews input {
			margin-bottom: 10px;
		}
		
		#previews .box-type:hover, #previews .box-type.selected {
			color: #5292C0;
			cursor: pointer;
		}
		
		#previews .box-type:hover .paper, #previews .box-type.selected .paper {
			border: 1px solid #5292C0;
		}
		
		#previews .paper {
			width: 70px;
		    height: 99px;
		    border: 1px solid #e7e7e7;
		    padding: 5px;
		    box-shadow: 1px 1px 0px #e7e7e7;
		    margin-bottom: 10px;
		}
		
		#previews .box-type-disabled:hover {
			color: #000 !important;
		}
		
		#previews .box-type-disabled:hover .paper {
			border: 1px solid #e7e7e7 !important;
			cursor: default !important;
		}
		
		#previews .box-type-disabled .paper {
			background-color: #EEEEEE !important;
		}
		
		#previews .paper .x1 {
			width: 68px;
	    	height: 97px;
	    	border: 1px solid #C3C3C3;
		}
		#previews .paper .x2 {
			width: 66px;
		    height: 36px;
		    margin: 1px;
		    margin-bottom: 2px;
		    border: 1px solid #C3C3C3;
		}
		#previews .paper .x2 .graph.horizontal{
			width: 60px;
		    height: 14px !important;
		    margin: 3px;
		    margin-top: 8px !important;
		    border-left: 1px solid gray;
		    border-top: 1px solid gray;
		    border-bottom: none !important;
		    padding-top: 5px !important;	
		}
		#previews .paper .x2 .graph.horizontal .graph-item{
			width: 50px;
		    height: 8px;
		    background: #C3C3C3;
		    float: left;	
		}
		#previews .paper .x2 .graph {
			width: 60px;
		    height: 25px;
		    margin: 3px;
		    border-left: 1px solid gray;
		    border-bottom: 1px solid gray;
		    padding-top: 4px;			
		}
		#previews .paper .x2 .graph .graph-item-1{
			width: 20px;
		    height: 25px;
		    margin-left: 6px;
		    background: #C3C3C3;
		    float: left;		
		}
		#previews .paper .x2 .graph .graph-item-2{
			width: 20px; 
		    height: 16px; 
		    margin-left: 6px;
		    background: #C3C3C3;
		    float: left;
		    margin-top: 9px;
		}
		#previews .paper .x4 {
			width: 31px;
		    height: 36px;
		    float: left;
		    margin: 1px;
		    border: 1px solid #C3C3C3;
		}
		#previews .paper .x4 {
			width: 31px;
		    height: 36px;
		    float: left;
		    margin: 1px;
		    border: 1px solid #C3C3C3;
		}
		#previews .paper .x4 .graph {
			width: 26px;
		    height: 25px;
		    margin: 3px;
		    border-left: 1px solid gray;
		    border-bottom: 1px solid gray;
		    padding-top: 4px;			
		}
		#previews .paper .x4 .graph .graph-item-1{
			width: 8px;
		    height: 25px;
		    margin-left: 3px;
		    background: #C3C3C3;
		    float: left;		
		}
		#previews .paper .x4 .graph .graph-item-2{
			width: 8px; 
		    height: 16px; 
		    margin-left: 3px;
		    background: #C3C3C3;
		    float: left;
		    margin-top: 9px;
		}
		#previews .cabecalho {
			width: 66px;
			height: 14px;
			margin-bottom: 2px !important;
			margin: 1px;
			border: 1px solid #C3C3C3;
		}
		
		.dados {
			width: 602px;
		} 
	</style>
	
	<script type="text/javascript">
		var empresaIds = new Array();
				
		$(function(){
			$(".box-type").click(function(){
				selectPrintType(this);
			});
			
			selectPrintType($('input[name=relatorioDetalhado]:eq(0)').parent() );
			
			$('#tooltipHelp').qtip({
				content: 'Ao selecionar a nota mínima, o gráfico de "Média Geral das Competências" apresentará as competências avaliadas de acordo com o nível selecionado.'
			});
			
			exibeOuOcultaFiltros();
			populaOrdensNivelCompetencia();
			mudaLabelMultCheckBoxAvaliadores();
			
			$('#cargosVinculadosAreas').click(function() {
				populaCargosByAreaVinculados();
			});
			
			$('#cargosVinculadosAreas').attr('checked', true);
			
			$('#wwctrl_avaliadores * span').eq(0).removeAttr('onclick').css('color', '#6E7B8B').css('cursor', 'default');
			
			$('#listCheckBoxareasCheck').append('<div class="info"> <ul> <li>Selecione uma avaliação de desempenho para para popular as áreas.</li> </ul> </div>');
			$('#listCheckBoxcargosCheck').append('<div class="info"> <ul> <li>Selecione uma avaliação de desempenho para popular os cargos.</li> </ul> </div>');
			
			populaAvaliados();
		})
		
		function selectPrintType(box) {
			$(".box-type").removeClass("selected");
			$(box).addClass("selected");
			$(box).find("input").attr("checked", "checked").change();
		}
		
		function populaAvaliados()
		{
			limpaOrdensNivelCompetencia();
			var areasIds   = getArrayCheckeds(document.form, 'areasCheck');
			var cargosIds  = getArrayCheckeds(document.form, 'cargosCheck');
			
			DWREngine.setAsync(false);
			DWRUtil.useLoadingMessage('Carregando...');
			
			if($('#avaliacao').val() !==""){
			
				EmpresaDWR.findDistinctEmpresasByAvaliacaoDesempenho($('#avaliacao').val(),populaEmpresaPorAvaliacaoDesempenho);
				
				populaArea();
				populaCargosByAreaVinculados();
				populaAvaliadores();
			}
			else{
				limpaCamposPorAvaliacao();
			}
		}
		
		function populaEmpresaPorAvaliacaoDesempenho(data){
			if(empresaIds.length===0){
				addOptionsByCollection('empresa', data, 'Todas');
				for(var empresa in data){
					empresaIds.push(data[empresa].id);
				}
			}else if ($('#empresa').children().length<=1) {
				addOptionsByCollection('empresa', data, 'Todas');
			}
		}	
		
		function populaSelectAvaliados(data)
		{
			$('#avaliados').find('option').remove().end();
			if(data != "")
				addOptionsByCollection('avaliados', data, 'Selecione...');
			else
				$('#avaliados').append('<option value="" selected="selected">Não existem avaliados para o filtro informado.</option>');
				
			populaAvaliadores();
		}
		
		function populaArea()
		{
			DWREngine.setAsync(true);
			DWRUtil.useLoadingMessage('Carregando...');
			
			AreaOrganizacionalDWR.getByEmpresas(createListArea, $('#empresa').val(), empresaIds, null);
			
		}
		
		function createListArea(data)
		{
			addChecks('areasCheck',data, 'populaCargosByAreaVinculados();');
		}
	
		function populaCargosByAreaVinculados()
		{
			var areasIds = getArrayCheckeds(document.forms[0],'areasCheck');
			var exibirSomenteCargoVinculadoComAreasSelecionadas = $('#cargosVinculadosAreas').is(":checked");
			
			if($('#empresa').val()!=='')
				CargoDWR.getByEmpresasEArea(empresaIds, new Array($('#empresa').val()), areasIds, exibirSomenteCargoVinculadoComAreasSelecionadas, createListCargosByArea);
			else
				CargoDWR.getByEmpresasEArea(empresaIds,null, areasIds, exibirSomenteCargoVinculadoComAreasSelecionadas, createListCargosByArea);
			
			populaAvaliadosPorAreaCargoEmpresa();
		}
		
		function createListCargosByArea(data)
		{
			addChecks('cargosCheck',data,'populaAvaliadosPorAreaCargoEmpresa()');
		}
	
		function exibeOuOcultaFiltros(){
			if($('input[name=relatorioDetalhado]:checked').val() == 'true') {
				populaAvaliadores();
				$('#paraRelatorioDetalhado').show();
			} else
				$('#paraRelatorioDetalhado').hide();
		}
		
		function populaAvaliadores()
		{
			if(($('input[name=relatorioDetalhado]:checked').val() == 'true') && ($('#avaliados').val() != 0) && ($('#avaliados').val() != -1)){
				DWREngine.setAsync(true);
				DWRUtil.useLoadingMessage('Carregando...');
				AvaliacaoDesempenhoDWR.getAvaliadores(createListCargosAvaliadores, $('#avaliacao').val(), $('#avaliados').val());
			}else{
				$("input[name=avaliadores]").parent().remove();
				$("#listCheckBoxavaliadores > .info").remove();
				$('#listCheckBoxavaliadores').append('<div class="info"> <ul> <li>Selecione um avaliado para popular os avaliadores.</li> </ul> </div>');
			}
		}
		
		function createListCargosAvaliadores(data)
		{
			addChecks('avaliadores',data, 'verificaQtdMarcados()');
			$("input[name=avaliadores]").each(function(){
				if($(this).val() == $("#avaliados").val() ){
					$(this).attr('checked', 'checked');
					$(this).attr('disabled', 'disabled');
				}
			});
		}
		
		function populaOrdensNivelCompetencia()
		{
			if(($('input[name=relatorioDetalhado]:checked').val() == 'true') && ($('#avaliacao').val() != 0) && ($('#avaliacao').val() != -1)
			&& ($('#avaliados').val() != 0) && ($('#avaliados').val() != -1)){
				DWREngine.setAsync(true);
				DWRUtil.useLoadingMessage('Carregando...');
				NivelCompetenciaDWR.findNiveisCompetenciaByAvDesempenho(populaRadiosNiveisCompetencia, $('#avaliacao').val(),$('#avaliados').val());
			}else{
				limpaOrdensNivelCompetencia();
			}
		}
		
		function limpaOrdensNivelCompetencia (){
			$('#ordensNivelCompetencia').empty();
			$('#ordensNivelCompetencia').append('<div class="info" style="width: 533px;"> <ul> <li>Utilize o filtro "Avaliado" para popular as competências.</li> </ul> </div>');
		}
		
		function populaRadiosNiveisCompetencia(data)
		{
			$('#ordensNivelCompetencia').empty();
			
			tabela = '<table class="dados"> <thead><tr>';				
			
			for (var key in data) {
				tabela += '<th width=100 align=middle>' + data[key].descricao + ' (' + data[key].ordem + ')</th>';
			}
			
			tabela += '</tr> </thead> <tr>';
			
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
		
		function verificaQtdMarcados()
		{
			qtdColunasAdicionais = 4;
			$("input[name=avaliadores]:not(:checked)").removeAttr('disabled');
			
			if($('#agruparPorCargo').attr("checked")){
				cargosAdicionados = [];
				$("input[name=avaliadores]:checked").each(function(){
					textoChecked = $(this).parent().text();
					regExp = /\(([^)]+)\)/;
					cargo = regExp.exec(textoChecked)[1];
					
					if(!(cargosAdicionados.indexOf(cargo) == 0))
						cargosAdicionados.push(cargo);
				});
				
				if(cargosAdicionados.length > qtdColunasAdicionais)
					$("input[name=avaliadores]:checked").removeAttr('checked');
					
				if(cargosAdicionados.length == qtdColunasAdicionais){
					$("input[name=avaliadores]:not(:checked)").each(function(){
						textoChecked = $(this).parent().text();
						regExp = /\(([^)]+)\)/;
						cargo = regExp.exec(textoChecked)[1];
						
						if(!(cargosAdicionados.indexOf(cargo) == 0))
							$(this).attr('disabled', true);
					});
				}
			}else{
				if($("input[name=avaliadores]:checked").length > qtdColunasAdicionais)
					$("input[name=avaliadores]:checked").removeAttr('checked');
					
				if($("input[name=avaliadores]:checked").length == qtdColunasAdicionais)
					$("input[name=avaliadores]:not(:checked)").attr('disabled', true);
			}
		}
		
		function mudaLabelMultCheckBoxAvaliadores(){
			if($('#agruparPorCargo').attr("checked"))
				$('#wwlbl_avaliadores > .desc').text('Avaliadores (máx. 4 Cargos Distintos):');
			else
				$('#wwlbl_avaliadores > .desc').text('Avaliadores (máx. 4 Avaliadores):');
		}
		
		function populaChecks()
		{
			$('#listCheckBoxareasCheck [type="checkbox"]').attr('checked', false);
			$('#listCheckBoxcargosCheck [type="checkbox"]').attr('checked', false);
			
			var empresaId = $('#empresa').val() == 0 ? null : $('#empresa').val() ;
			
			DWREngine.setAsync(false);
			
			populaArea(empresaId);
			populaCargosByAreaVinculados();
			limpaOrdensNivelCompetencia();
		}
		
		function limpaCamposPorAvaliacao()
		{
			var existeMsgDefaultArea = !!$('#listCheckBoxareasCheck').find('.info').length;
			var existeMsgDefaultCargo = !!$('#listCheckBoxcargosCheck').find('.info').length;
			
			if($('#avaliacao').val() ==="" && (existeMsgDefaultArea || existeMsgDefaultCargo)){
				return false;
			}else{
				$('#listCheckBoxareasCheck').empty();
				$('#listCheckBoxcargosCheck').empty();
				$('#listCheckBoxavaliadores').empty();
				$('#empresa').empty();
				$('#avaliados').empty();
				
				$('#listCheckBoxareasCheck').append('<div class="info"> <ul> <li>Selecione uma avaliação de desempenho para para popular as áreas.</li> </ul> </div>');
				$('#listCheckBoxcargosCheck').append('<div class="info"> <ul> <li>Selecione uma avaliação de desempenho para popular os cargos.</li> </ul> </div>');
				$('#listCheckBoxavaliadores').append('<div class="info"> <ul> <li>Selecione um avaliado para popular os avaliadores.</li> </ul> </div>');
				
				$('#avaliados').append('<option value="" selected="selected">Não existem avaliados para o filtro informado.</option>');
				$('#empresa').append('<option value="" 	 selected="selected">Selecione uma avaliação de desempenho.</option>');
			}
		}
		function populaAvaliadosPorAreaCargoEmpresa()
		{
			var areasIds   = getArrayCheckeds(document.form, 'areasCheck');
			var cargosIds  = getArrayCheckeds(document.form, 'cargosCheck');
			
			if($('#empresa').val() !==''){
				AvaliacaoDesempenhoDWR.getAvaliados(populaSelectAvaliados, $('#avaliacao').val(), new Array($('#empresa').val()), areasIds, cargosIds);
			}else{
				AvaliacaoDesempenhoDWR.getAvaliados(populaSelectAvaliados, $('#avaliacao').val(), empresaIds, areasIds, cargosIds);
			}
		}
		
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@ww.form name="form" action="analiseDesempenhoCompetenciaColaborador.action" onsubmit="${validarCampos}" method="POST">
		<!-- <@ww.select label="Gerar relatório" name="relatorioDetalhado" id="relatorioDetalhado" list=r"#{false:'Resumido', true:'Detalhado'}" cssStyle="width: 600px;" onchange="exibeOuOcultaFiltros();"/> -->
		
		<div id="previews">
			<div id="x4" class="box-type">
				<input type="radio" value="true" name="relatorioDetalhado" onchange="exibeOuOcultaFiltros();"/>
				<div class="paper">
					<div class="cabecalho"></div>
					<div class="x4">
						<div class="graph">
							<div class="graph-item-1"></div>
						  	<div class="graph-item-2"></div>
						</div>
					</div>
					<div class="x4">
						<div class="graph">
							<div class="graph-item-1"></div>
						  	<div class="graph-item-2"></div>
						</div>
					</div>
					<div class="x4">
						<div class="graph">
							<div class="graph-item-1"></div>
						  	<div class="graph-item-2"></div>
						</div>
					</div>
					<div class="x4">
						<div class="graph">
							<div class="graph-item-1"></div>
						  	<div class="graph-item-2"></div>
						</div>
					</div>
				</div>
				<div>Detalhado</div>
			</div>
			<div id="x2" class="box-type">
				<input type="radio" value="false" name="relatorioDetalhado" onchange="exibeOuOcultaFiltros();"/>
				<div class="paper">
					<div class="cabecalho"></div>
					<div class="x2">
						<div class="graph horizontal">
							<div class="graph-item"></div>
						</div>
					</div>
					<div class="x2">
						<div class="graph">
							<div class="graph-item-1"></div>
						  	<div class="graph-item-2"></div>
						</div>
					</div>
				</div>
				<div>Resumido</div>
			</div>
		</div>
		
		<!--<@ww.hidden name="relatorioDetalhado" id="relatorioDetalhado" value="false" />-->
		<@ww.select label="Avaliação de desempenho que avaliam competência" required="true" name="avaliacaoDesempenho.id" id="avaliacao" list="avaliacaoDesempenhos" listKey="id" listValue="titulo" cssStyle="width: 600px;" headerKey="" headerValue="Selecione..." onchange="populaAvaliados();"/>
		<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Selecione uma avaliação de desempenho" headerKey="" cssStyle="width: 600px;" onchange="populaChecks();"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" width="600" onClick="populaCargosByAreaVinculados();" filtro="true" selectAtivoInativo="true"/>
		<@ww.checkbox label="Exibir somente os cargos vinculados às áreas organizacionais acima." id="cargosVinculadosAreas" name="" labelPosition="left"/>
		<@frt.checkListBox label="Cargos" name="cargosCheck" id="cargosCheck" list="cargosCheckList"  width="600" onClick="populaAvaliadosPorAreaCargoEmpresa();" filtro="true" selectAtivoInativo="true"/>
		<@ww.select label="Avaliado" required="true" name="avaliado.id" id="avaliados" list="participantesAvaliadores" listKey="id" listValue="nome" cssStyle="width: 600px;" headerKey="-1" headerValue="Selecione uma avaliação de desempenho"  onchange="populaAvaliadores();populaOrdensNivelCompetencia();"/>
		
		<div id="paraRelatorioDetalhado">
			<@ww.checkbox label="Agrupar avaliadores por cargo." id="agruparPorCargo" name="agruparPorCargo" labelPosition="left" onchange="verificaQtdMarcados();mudaLabelMultCheckBoxAvaliadores();"/>
			<@frt.checkListBox label="Avaliadores (máx. 4 Avaliadores)" name="avaliadores" id="avaliadores" list="avaliadoresCheckList" width="600" filtro="true" selectAtivoInativo="true"/>
			Nota mínima considerada em "Média Geral das Competências":
			<img id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-top:20px;" />
			</br>
			<div id="ordensNivelCompetencia"/>
		</div>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="btnRelatorio"></button>		
	</div>
</body>
</html>