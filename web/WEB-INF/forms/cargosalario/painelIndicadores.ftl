<html>
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<head>
	<@ww.head/>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/indicadores.css"/>');
		
		.formula { margin: 7px 5px; }
	</style>
	

		<!--[if lte IE 8]><script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/excanvas.min.js"/>'></script><![endif]-->
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/grafico.js"/>'></script>
		
		
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
		
		<#include "../ftl/showFilterImports.ftl" />
		
		<title>Painel de Indicadores de Info. Funcionais</title>

		<script type="text/javascript">
			$(function () {
			
				montaPie(${grfFormacaoEscolars}, "#formacaoEscolar");
				montaPie(${grfFaixaEtarias}, "#faixaEtaria");
				montaPie(${grfSexo}, "#sexo", {percentMin:0});
				montaPie(${grfEstadoCivil}, "#estadoCivil", {percentMin: 0.02});
				montaPie(${grfDeficiencia}, "#deficiencia", {percentMin: 0.03});
				montaPie(${grfColocacao}, "#colocacao", {percentMin: 0.02});
				montaPie(${grfDesligamento}, "#desligamento", {radiusLabel:0.9, percentMin: 0.02, pieLeft:-190});
				
				
				var absenteismo = ${grfEvolucaoAbsenteismo};
				var turnover = ${grfEvolucaoTurnover};
				
				var somaAbsenteismo = 0;
				$.each(absenteismo, function (){
				    somaAbsenteismo = this[1] + somaAbsenteismo;
				});
				
				$('#mediaAbsenteismo').text('Absenteísmo: ' + (somaAbsenteismo / absenteismo.length).toFixed(4));
				
				montaLine(absenteismo, "#evolucaoAbsenteismo");
				montaLine(turnover, "#evolucaoTurnover");
			});
			
			function enviaForm1()
			{
				$('#formBusca1 input:checked').each(function(){
					$("#formBusca1").append('<input type="hidden" name="areasCheck" value='+ $(this).val() +'>');
				});

				return validaFormulario('formBusca1', new Array('dataBase'), new Array('dataBase'));
			}
			
			function enviaForm2()
			{
				$('#formBusca2 input:checked').each(function(){
					$("#formBusca2").append('<input type="hidden" name="areasCheck" value='+ $(this).val() +'>');
				});
				
				return validaFormularioEPeriodo('formBusca2', new Array('dataIni','dataFim'), new Array('dataIni','dataFim'));
			}
			
			function enviaForm3()
			{
				$('#formBusca3 input:checked').each(function(){
					$("#formBusca3").append('<input type="hidden" name="areasCheck" value='+ $(this).val() +'>');
				});
				
				return validaFormularioEPeriodoMesAno('formBusca3', new Array('dataMesAnoIni','dataMesAnoFim'), new Array('dataMesAnoIni','dataMesAnoFim'));
			}
			
			function populaAreas(empresaId)
			{
				DWRUtil.useLoadingMessage('Carregando...');
				AreaOrganizacionalDWR.getByEmpresa(createListAreas, empresaId);
				$('.empresa').val(empresaId);
			}
	
			function createListAreas(data)
			{
				addChecks('areasCheck1', data);
				addChecks('areasCheck2', data);
				addChecks('areasCheck3', data);
			}
		</script>
	
		<#include "../ftl/mascarasImports.ftl" />
	
		
		<#if dataBase?exists>
		  <#assign dateBase = dataBase?date/>
		<#else>
		  <#assign dateBase = ""/>
		</#if>
		<#if dataIni?exists>
		  <#assign dateIni = dataIni?date/>
		<#else>
		  <#assign dateIni = ""/>
		</#if>
		<#if dataFim?exists>
		  <#assign dateFim = dataFim?date/>
		<#else>
		  <#assign dateFim = ""/>
		</#if>
	
	</head>
	<body>
		<#include "../util/topFiltro.ftl" />
			<@ww.form name="formBusca1" id="formBusca1" action="painelIndicadores.action" method="POST">
				<@ww.select label="Empresa" name="empresa.id" id="empresa" cssClass="empresa" listKey="id" listValue="nome" list="empresas" onchange="populaAreas(this.value);" />
				<@ww.datepicker label="Data" name="dataBase" value="${dateBase}" id="dataBase"  cssClass="mascaraData" />
			
				<@frt.checkListBox form="document.getElementById('formBusca1')" label="Áreas Organizacionais" name="areasCheck1" id="areasCheck1" list="areasCheckList"/>
					
				<@ww.hidden name="dataIni"/>	
				<@ww.hidden name="dataFim"/>
				<@ww.hidden name="dataMesAnoIni"/>
				<@ww.hidden name="dataMesAnoFim"/>
				<button onclick="return enviaForm1();" class="btnPesquisar grayBGE"></button>
			</@ww.form>
		<#include "../util/bottomFiltro.ftl" />
		
		<div class="legendTotal">Total de Colaboradores: ${qtdColaborador}</div>
		
		<div class="fieldGraph">
			<h1>Faixa Etária</h1>
		    <div id="faixaEtaria" class="graph"></div>
	    </div>
		<div class="fieldGraph">
			<h1>Estado Civil</h1>
	    	<div id="estadoCivil" class="graph"></div>
	    </div>
	    <div class="fieldGraph">
			<h1>Deficiência</h1>
	    	<div id="deficiencia" class="graph"></div>
	    </div>
	    <div class="fieldGraph">
			<h1>Colocação</h1>
	    	<div id="colocacao" class="graph"></div>
	    </div>
		<div class="fieldGraph medium">
			<h1>Formação Escolar</h1>
		    <div id="formacaoEscolar" class="graph" ></div>
		</div>
   		<div class="fieldGraph small">
			<h1>Sexo</h1>
	    	<div id="sexo" class="graph"></div>
	    </div>
	    
		<div style="clear: both"></div>
		
		<br>
		
			<div class="fieldGraph bigger">
				<div class="divFiltro">
					<div class="divFiltroLink">
						<a href="javascript:exibeFiltro('${urlImgs}','divFiltroForm2');" id="linkFiltro"><img alt="Ocultar\Exibir Filtro" src="<@ww.url includeParams="none" value="${imagemFiltro}"/>"> <span id="labelLink" class="labelLink">${labelFiltro}</span></a>
					</div>
					<div id="divFiltroForm2" class="divFiltroForm ${classHidden}">
						<@ww.form name="formBusca2" id="formBusca2" action="painelIndicadores.action#pagebottom" method="POST">
							<@ww.select label="Empresa" name="empresa.id" id="empresa" cssClass="empresa" listKey="id" listValue="nome" list="empresas" onchange="populaAreas(this.value);" />
										
							<@frt.checkListBox form="document.getElementById('formBusca2')" label="Áreas Organizacionais" name="areasCheck2" id="areasCheck2" list="areasCheckList"/>
							
							<@ww.datepicker name="dataIni" id="dataIni" value="${dateIni}" cssClass="mascaraData validaDataIni" liClass="liLeft"/>
							<@ww.label value="a" liClass="liLeft" />
							<@ww.datepicker name="dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim" liClass="liLeft"/>
							&nbsp&nbsp&nbsp&nbsp&nbsp;Exibir os
							<@ww.textfield theme="simple" name="qtdItensDesligamento" value="${qtdItensDesligamento}" id="qtdItensDesligamento" cssStyle="width:20px; text-align:right;" maxLength="2" onkeypress = "return(somenteNumeros(event,''));"/> 
							itens de maior percentual.<br>
							
							<@ww.hidden name="dataBase"/>
							<@ww.hidden name="dataMesAnoIni"/>
							<@ww.hidden name="dataMesAnoFim"/>
							<button onclick="return enviaForm2();" class="btnPesquisar grayBGE"></button>
						</@ww.form>
					</div>
				</div><!-- fim .divFiltro -->
			
				<h1 style="border-bottom: none;">Motivos de Desligamentos</h1>
		   		<div id="desligamento" class="graph2"></div>
		    	
		    	<div style="clear: both"></div>
				
				<h1 style="border-bottom: none;">Turnover</h1>
		   		<div id="evolucaoTurnover" style="margin: 25px;height:300px;"></div>
				<div class="formula">Fórmula: [(Qtd. Admitidos + Qtd. Demitidos / 2) / Qtd. Colaboradores Ativos no início do mês] * 100</div>
		    
				<div style="clear: both"></div>
				
				<div class="fieldDados" style="border:none;border-top:1px solid #7E9DB9;">
					<div>Admitidos: ${countAdmitidos}</div>
					<div>Demitidos: ${countDemitidos}</div>
					<div>Turnover: ${turnover}</div>
				</div>
		    </div>

		    <div style="clear: both"></div>
				<a name="pagebottom"></a>
			<br>
		
			<div class="divFiltro">
				<div class="divFiltroLink">
					<a href="javascript:exibeFiltro('${urlImgs}','divFiltroForm3');" id="linkFiltro"><img alt="Ocultar\Exibir Filtro" src="<@ww.url includeParams="none" value="${imagemFiltro}"/>"> <span id="labelLink" class="labelLink">${labelFiltro}</span></a>
				</div>
				<div id="divFiltroForm3" class="divFiltroForm ${classHidden}">
				<@ww.form name="formBusca3" id="formBusca3" action="painelIndicadores.action#pagebottom" method="POST">
					<@ww.select label="Empresa" name="empresa.id" id="empresa" cssClass="empresa" listKey="id" listValue="nome" list="empresas" onchange="populaAreas(this.value);" />
					
					<@frt.checkListBox form="document.getElementById('formBusca3')" label="Áreas Organizacionais" name="areasCheck3" id="areasCheck3" list="areasCheckList"/>
			
					<@ww.textfield label="Mês/Ano" name="dataMesAnoIni" id="dataMesAnoIni" cssClass="mascaraMesAnoData validaDataIni" liClass="liLeft"/>
					<@ww.label value="a" liClass="liLeft" />
					<@ww.textfield label="Mês/Ano" name="dataMesAnoFim" id="dataMesAnoFim" cssClass="mascaraMesAnoData validaDataFim"/>
					
					<@ww.hidden name="dataBase"/>
					<@ww.hidden name="dataIni"/>	
					<@ww.hidden name="dataFim"/>
	
					<button onclick="enviaForm3();" class="btnPesquisar grayBGE"></button>
				</@ww.form>
			<#include "../util/bottomFiltro.ftl" />
			<div class="fieldGraph bigger">
				<h1>Absenteísmo</h1>
		   		<div id="evolucaoAbsenteismo" style="margin: 25px;height:300px;"></div>
				<div class="formula">Fórmula: [Total de faltas do mês / (Qtd. colaboradores ativos no início do mês * Dias trabalhados no mês)]</div>
				
				<div style="clear: both"></div>
				
				<div class="fieldDados" style="border:none;border-top:1px solid #7E9DB9;">
					<div id="mediaAbsenteismo"></div>
				</div>
		    </div>
		    
		    <br>
		    <div style="clear: both"></div>
	</body>
</html>