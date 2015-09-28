<html>
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<head>
	<@ww.head/>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		
		div.graph {
			width: 366px !important;
			height: 416px;
			float: left;
		}		
		.fieldGraph {
			float: left;
			margin-top: 3px;
			margin-right: 3px;
			border: 1px solid #BEBEBE;
			background: #FFF;
		}
		.fieldGraph h1 {
			display: block;
			margin: 0;
			padding: 3px 4px;
			width: 938px;
			font-size: 12px;
			font-weight: normal;
			background: #EBECF1;
			border-bottom: 1px solid #BEBEBE;
		}
		.grid-cell{
			background: #FFF !important;
		}
		
		#salarioAreasLegenda{
			float: left;
			width: 580px !important;
		}
		.label {
			color: #5C5C5A !important;
			text-align: center;
			font-size: 10px !important;
		}
		.legend {
			color: #5C5C5A !important;
		}
		
		#divBoxSalario, #divBoxPromoHorizontal, #divBoxPromoVertical{
			width: 450px;
			height: 300px;
		}
		#divBoxSalario a, #divBoxPromoHorizontal a, #divBoxPromoVertical a{
			color: #85B5D9 !important;
			text-decoration: none;
		}
		#boxSalario, #boxPromoHorizontal, #boxPromoVertical{
			float: left;
			width: 300px;
			height: 250px;
		}
		#boxSalarioLegend, #boxPromoHorizontalLegend, #boxPromoVerticalLegend{
			float: left;
			width: 350px;
			height: 250px !important;
		}
		
		.legendTotal{
			text-align: left !important;
		}
		
		.btnImprimir { float: right; margin: 10px; }
		.icoImprimir { float: right; cursor: pointer; }
		
		.aba { display: inline; padding: 2px 8px; *padding: 5px 8px; background-color: #D5D5D5; border: 1px solid #CCC; }
		.aba a { font-family: Verdana, sans-serif; font-size: 10px; color: #000; text-decoration: none; text-transform: uppercase; }
		.aba a:hover { text-decoration: none; color: #600; }
		.aba.ativa { background-color: #F6F6F6; border-bottom-color: #F6F6F6; }
		#abas { position: relative; z-index: 500; margin-bottom: 2px; }
		
		.conteudo { background-color: #F6F6F6; padding: 10px; border: 1px solid #CCC; }
		
		.x1Axis .tickLabel { cursor: default; }
	</style>
		<!--[if lte IE 8]><script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/excanvas.min.js"/>'></script><![endif]-->
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/grafico.js?version=${versao}"/>'></script>
		
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
		
		<#include "../ftl/showFilterImports.ftl" />
		
		<title>Painel de Indicadores de C&S</title>

		<#if dataBase?exists>
		  <#assign dateBase = dataBase?date/>
		<#else>
		  <#assign dateBase = ""/>
		</#if>
		<#if empresaId?exists>
		  <#assign empId = empresaId/>
		<#else>
		  <#assign empId = ""/>
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

		<script type="text/javascript">
			$(function() {
			
				$("input[name=areasPieChartCheck]").change(function(){
					if( $(this).is(":visible") ) {
						var descricaoArea = $(this).parent().text().replace("(Ativa)","").replace("(Inativa)","").trim();
						var isChecked = $(this).is(":checked");
						
						$("#listCheckBoxareasPieChartCheck label:contains("+descricaoArea+" >)").toggle(!isChecked);
						$("#listCheckBoxareasPieChartCheck label:contains("+descricaoArea+" >) input").toggleDisabled(isChecked);
						$("#listCheckBoxareasPieChartCheck label:contains("+descricaoArea+" >) input").removeAttr("checked","checked");
					}
				});
				$("input[name=areasPieChartCheck]:checked").change();
			
				BrowserDetect.init( function ( informacaoesDesteBrowser ){
				    if(informacaoesDesteBrowser.name == 'Explorer')
				    	$('.btnImprimir').hide();
				});
			
				$("#divBoxSalario").dialog({autoOpen: false});
				$("#divBoxPromoHorizontal").dialog({autoOpen: false});
				$("#divBoxPromoVertical").dialog({autoOpen: false});
			
				salarioAreasOrdered = ${grfSalarioAreas}.sort(function (a, b){
					return (a.data > b.data) ? -1 : (a.data < b.data) ? 1 : 0;
				});
				
				graficoPizza(salarioAreasOrdered, '#salarioAreas', '#salarioAreasLegenda', '#salarioAreasImprimir', 2, null, null, true);
				
				promocaoHorizontalOrdered= ${grfPromocaoHorizontalArea}.sort(function (a, b){
					return (a.data > b.data) ? -1 : (a.data < b.data) ? 1 : 0;
				}); 
				
				graficoPizza(promocaoHorizontalOrdered, '#promocoesHorizontaisAreas', '#promocoesHorizontaisAreasLegenda', '#promocoesHorizontaisAreasImprimir', 2);

				promocaoVerticalOrdered= ${grfPromocaoVerticalArea}.sort(function (a, b){
					return (a.data > b.data) ? -1 : (a.data < b.data) ? 1 : 0;
				}); 
				
				graficoPizza(promocaoVerticalOrdered, '#promocoesVerticaisAreas', '#promocoesVerticaisAreasLegenda', '#promocoesVerticaisAreasImprimir', 2);
			
				var folha = ${grfEvolucaoFolha};
				var faturamento = ${grfEvolucaoFaturamento};
				
				var dadosLinha = [{label: 'Evolução Salarial', data: folha}, {label: 'Faturamento', data: faturamento }];
				
				graficoLinha(dadosLinha, "#evolucaoFolha", "Evolução Salarial - Faturamento ");
				
				var promocaoHorizontal = ${grfPromocaoHorizontal};
		        var promocaoVertical = ${grfPromocaoVertical};
		        
		        var dadosBarra = [
					        {label: 'Horizontal', data: promocaoHorizontal , bars:{align : "right", barWidth: 900000020}},
					        {label: 'Vertical', data: promocaoVertical, bars:{align : "left", barWidth: 900000000} }
					    ];
				
		        graficoBarra(dadosBarra, "#faixaSalarial", "#faixaSalarialImprimir");
		        
		        var promocaoHorizontalArea = ${grfBarraPromocaoHorizontalArea};
		        var promocaoVerticalArea = ${grfBarraPromocaoVerticalArea};
		        
		        var dadosBarraArea = [
					        {label: 'Horizontal', data: promocaoHorizontalArea, bars:{align : "right"}},
					        {label: 'Vertical', data: promocaoVerticalArea, bars:{align : "left"} }
					    ];
				
		        graficoBarraCategoria(dadosBarraArea, "#faixaSalarialArea", "#faixaSalarialAreaImprimir");
		        
		        $('.conteudo-aba').hide();
				
				$('.aba a').click(function() {
					var aba = $(this).parent();
					var idAba = aba.attr('id').replace('aba','');
					
					$('.aba').removeClass('ativa');
					aba.addClass('ativa');

					$('.conteudo-aba').hide();
					$('#abaMarcada').val(idAba);
					$('.conteudo-' + idAba).show();
				});
				
				$('#aba${abaMarcada} a').click();
			});
			
			function graficoLinha(dados, obj, titulo)
			{
				montaLine(dados, obj, null);
				
				$(obj + "Imprimir")
						.unbind()
						.bind('click', 
							function(event) 
							{ 
								popup = window.open("<@ww.url includeParams="none" value="/grafico.jsp"/>");
								popup.window.onload = function() 
								{
									popup.focus();
									popup.document.getElementById('popupTitulo').innerHTML = titulo;
									popup.document.getElementById('popupGraficoLegenda').innerHTML = '<br />' + $(obj + 'Info .formula').text()+'<br /><br />' + $(obj + 'Info .fieldDados').text();
									popup.window.opener.montaLine(dados, popup.document.getElementById('popupGrafico'));
									popup.window.print();
									
									if($.browser.mozilla)
										popup.window.close();
								}
							}
						);
			}
			
			function graficoPizza(dados, divGrafico, divLegenda, btnImprimir, numColunas, titulo, divTitulo, valorEmDinheiro, descricaoTitulo)
			{
				montaPie(dados, divGrafico, {
					radiusLabel:0.9, 
					percentMin: 0.02,
					pieLeft: 0, 
					noColumns: numColunas, 
					container: divLegenda,
					hoverable: true,
        			clickable: true,
					legendLabelFormatter: function(label, series) {
						return '<span class="legend">' + label + ' &#x2013; '+ series.percent.toFixed(2) + '% ('+ formataNumero(series.datapoints.points[1], valorEmDinheiro) + ')</span>';
					}
				});
				
				$(divGrafico).bind("plothover", plotPieHover)
							 .bind("plotclick", pieClick);
							 
				if (titulo && divTitulo)
					$(divTitulo).text(titulo);
				
				if (btnImprimir) {
					var infoPopup = '';
					
					if(valorEmDinheiro)
						var infoPopup = $('.legendTotal').text();
						
					if(!descricaoTitulo)
						descricaoTitulo = "";
					
					$(btnImprimir).unbind().bind('click', { dados: dados }, function(event) { imprimirPizza(event.data.dados, descricaoTitulo, infoPopup); });
				}
			}
			
			function graficoBarra(dados, divGrafico, btnImprimir, titulo, divTitulo, obs, divObs)
			{
				montaBar(dados, divGrafico);
				
				if (titulo && divTitulo)
					$(divTitulo).text(titulo);
					
				if (obs && divObs)
					$(divObs).text(obs);
				
				if (btnImprimir) 
					$(btnImprimir).unbind().bind('click', function(event) { imprimirBarra(dados); });
			}
			
			var tamanhoTexto = 0;
			var nomesParaRelacionar = new Array();
			function preparaDadosGraficoBarraCategoria(data)
			{
				for (var keyData in data) 
				{	
					for (var key in data[keyData].data) 
					{
						if(nomesParaRelacionar.indexOf(data[keyData].data[key][0]) == -1)
						{	
							nomesParaRelacionar[key] = data[keyData].data[key][0];
						
							var texts = nomesParaRelacionar[key].split(' ');
							for (var keyText in texts)
								if(texts[keyText].length > tamanhoTexto)
									tamanhoTexto = texts[keyText].length; 
						}
						
						data[keyData].data[key][0] = key.toString();
					}
				}
			}
			
			function graficoBarraCategoria(data, divGrafico, btnImprimir, titulo, divTitulo, obs, divObs)
			{
				if(btnImprimir)	
					preparaDadosGraficoBarraCategoria(data);
				
				montaBarDuploCategoria(data, divGrafico, nomesParaRelacionar, ((tamanhoTexto/2)*9));
				
				if (titulo && divTitulo)
					$(divTitulo).text(titulo);
					
				if (obs && divObs)
					$(divObs).text(obs);
				
				if (btnImprimir){ 
					$(btnImprimir).unbind().bind('click', function(event) { imprimirBarraCategoria(data); });
					plothoverGrafico(divGrafico);
				}
			}
			
			function plothoverGrafico(divGrafico)
			{
				var previousPoint = null;				
				$(divGrafico).bind("plothover", function (event, pos, item) {
			        if (item) 
			        {
			        	if (previousPoint != item.dataIndex) 
			        	{
			        		previousPoint = item.dataIndex;
			                $("#tooltip").remove();
			                var y = formataNumero(item.datapoint[1]);
			                showTooltip(item.pageX, item.pageY, y);
			            }
			        }
					else 
					{
			        	$("#tooltip").remove();
			        	previousPoint = null;            
			        }
				});
			}
			
			var popup;
			function imprimirPizza(dados, descricaoTitulo, info) 
			{
				popup = window.open("<@ww.url includeParams="none" value="/grafico.jsp"/>");
				popup.window.onload = function() 
				{
					popup.focus();
					popup.document.getElementById('info').innerHTML = info;
					popup.window.opener.graficoPizza(dados, popup.document.getElementById('popupGrafico'), popup.document.getElementById('popupGraficoLegenda'), false, 1, descricaoTitulo, popup.document.getElementById('popupTitulo'));
					popup.window.print();
					
					if($.browser.mozilla)
						popup.window.close();
				}
			}
			
			function imprimirBarra(dados) 
			{
				popup = window.open("<@ww.url includeParams="none" value="/grafico.jsp"/>");
				popup.window.onload = function() 
				{
					popup.focus();
					if($("#divBoxSalario").dialog("isOpen"))
						infoPopup = $('.ui-dialog-titlebar').text().replace('close','');
						
					popup.window.opener.graficoBarra(dados, popup.document.getElementById('popupGrafico'), false, 'Promoção', popup.document.getElementById('popupTitulo'), "* Promoção Horizontal: Mudança de Faixa Salarial ou Salário. / Promoção Vertical: Mudança de Cargo.", popup.document.getElementById('popupObs'));
					popup.window.print();
					
					if($.browser.mozilla)
						popup.window.close();
				}
			}
			
			function imprimirBarraCategoria(data) 
			{
				popup = window.open("<@ww.url includeParams="none" value="/grafico.jsp"/>");
				popup.window.onload = function() 
				{
					popup.focus();
					if($("#divBoxSalario").dialog("isOpen"))
						infoPopup = $('.ui-dialog-titlebar').text().replace('close','');
						
					popup.window.opener.graficoBarraCategoria(data, popup.document.getElementById('popupGrafico'), false, 'Promoções verticais e horizontais agrupadas por área organizacional', popup.document.getElementById('popupTitulo'), "*Promoção Horizontal: Mudança de Faixa Salarial ou Salário./ Promoção Vertical: Mudança de Cargo.", popup.document.getElementById('info'));
					
					for (var key in nomesParaRelacionar){
						popup.window.document.querySelectorAll('.tickLabel')[key].textContent = nomesParaRelacionar[key];
					}

					var dist = tamanhoTexto + 20;
					popup.window.document.querySelectorAll('#popupObs')[0].style.margin = dist + 'px';
					popup.window.document.querySelectorAll('#info')[0].style.margin = '10px 30px';
					popup.window.print();
					
					if($.browser.mozilla)
						popup.window.close();
				}
			}

			//CUIDADO com o tamanho do grafico(bug da sombra)http://code.google.com/p/flot/issues/detail?id=5#c110
			var dataBase_ = '${dateBase}';
			var empresaId_ = '${empId}';
			var dataIni_ = '${dateIni}';
			var dataFim_ = '${dateFim}';
			var urlFind = "";
			var areaId_ = 0;
			var popap = "";
			var valorEmDinheiro = false;
			var descricaoTitulo = "";
			
			function pieClick(event, pos, obj)
			{
				if(event.currentTarget.id == "salarioAreas"){
					areaId_ = salarioAreasOrdered[obj.seriesIndex].id;
					valorEmDinheiro = true;
					popap = "Salario";
					urlFind = "<@ww.url includeParams="none" value="/cargosalario/historicoColaborador/grfSalarioAreasFilhas.action"/>";
					descricaoTitulo = $('#' + event.currentTarget.id).parent().find('h1').text().trim().trim();
				}else if(event.currentTarget.id == "promocoesHorizontaisAreas"){
					areaId_ = promocaoHorizontalOrdered[obj.seriesIndex].id;
					valorEmDinheiro = false;
					popap = 'PromoHorizontal';
					urlFind = "<@ww.url includeParams="none" value="/cargosalario/historicoColaborador/grfPromocaoHorizontalAreasFilhas.action"/>";
					descricaoTitulo = $('#' + event.currentTarget.id).parent().find('h1').text().trim().trim();
				}else if(event.currentTarget.id == "promocoesVerticaisAreas"){
					areaId_ = promocaoVerticalOrdered[obj.seriesIndex].id;
					valorEmDinheiro = false;
					popap = 'PromoVertical';
					urlFind = "<@ww.url includeParams="none" value="/cargosalario/historicoColaborador/grfPromocaoVerticalAreasFilhas.action"/>";
					descricaoTitulo = $('#' + event.currentTarget.id).parent().find('h1').text().trim().trim();
				}else{
					areaId_ = box_[obj.seriesIndex].id;
				}
				
				$.ajax({
					url: urlFind,
					dataType: "json",
					async: false,
					data: {areaId: areaId_, dataBase: dataBase_, dataIni: dataIni_, dataFim: dataFim_, empresaId: empresaId_},
					success: function(data){
						if(data.length == 0)
						{
							jAlert("Área Organizacional não possui área filha.");
							return false;
						}
						
						$('#box' + popap).empty(); 
						$('#box' + popap + 'Legend').empty();
						
						box_ = data.sort(function (a, b){
							return (a.data > b.data) ? -1 : (a.data < b.data) ? 1 : 0;
						});
						
						graficoPizza(box_, '#box' + popap, '#box' + popap + 'Legend', '#box' + popap + 'Imprimir', 1, null, null, valorEmDinheiro, descricaoTitulo);
						
						var percent = parseFloat(obj.series.percent).toFixed(2);
						var descricaoArea = data[0].descricao;
						var titleSubArea = descricaoArea + ' &#x2013; '+ percent + '% (' + formataNumero(obj.series.datapoints.points[1], valorEmDinheiro) + ')';
						
						$("#divBox" + popap).dialog("option" , { zIndex: 9999, title: titleSubArea, width: 700, height: 350 });
						$("#divBox" + popap).dialog("open");
					}
				});
			}		
			
			function plotPieHover(event, pos, item) 
			{
	            if (item) 
	            {
            		previousIndex = item.dataIndex;
                    $("#tooltip").remove();
                    showTooltip(pos.pageX, pos.pageY, "Clique para exibir áreas filhas");
	            }
				else 
                	$("#tooltip").remove();
			}
			
			function showTooltip(x, y, contents) 
			{
		        $('<div id="tooltip">' + contents + '</div>').css( {
		            position: 'absolute',
		            display: 'none',
		            top: y - 30,
		            left: x + 5,
		            border: '1px solid #fdd',
		            padding: '2px',
		            'background-color': '#fee',
		            opacity: 0.80,
		            'z-index': 20000
		        }).appendTo("body").fadeIn(0);
		    }
			
			function enviaForm()
			{
				return validaFormulario('formBusca', new Array('dataBase','mesAnoIni','mesAnoFim'), new Array('dataBase','mesAnoIni','mesAnoFim'));
			}
			
			function formataNumero(value, valorEmDinheiro)
			{
				var retorno = "";
				if(valorEmDinheiro)
					retorno = 'R$';
			
				return retorno + $('<span>' + value + '</span>').format({}).text().replace(/,/g,'#').replace(/\./g,',').replace(/#/g,'.');
			}

			function populaAreas(empresaId)
			{
				DWRUtil.useLoadingMessage('Carregando...');
				AreaOrganizacionalDWR.getByEmpresa(createListAreas, empresaId);
				$('.empresa').val(empresaId);
			}
	
			function createListAreas(data)
			{
				addChecks('areasCheck', data);
			}
			
			function populaAreasPieChart(empresaId)	{
				DWRUtil.useLoadingMessage('Carregando...');
				AreaOrganizacionalDWR.getByEmpresa(createListAreasPieChart, empresaId);
				$('.empresa').val(empresaId);
			}
	
			function createListAreasPieChart(data){
				addChecks('areasPieChartCheck', data, 'escondeFilhas()');
			}

		</script>
	
		<#include "../ftl/mascarasImports.ftl" />
	
	</head>
	<body>
		
		<div id="abas">
			<div id="aba1" class="aba"><a href="javascript:;">Evolução Salarial</a></div>
			<div id="aba2" class="aba"><a href="javascript:;">Promoções por área organizacional</a></div>
		</div>
		
		<div class="conteudo">
			<@ww.form name="formBusca" id="formBusca" action="painelIndicadoresCargoSalario.action" method="POST">
				<@ww.hidden name="abaMarcada" id="abaMarcada" value="1"/>
				<#include "../util/topFiltro.ftl" />
				<@ww.select label="Empresa" name="empresa.id" id="empresa" cssClass="empresa" listKey="id" listValue="nome" list="empresas" onchange="populaAreas(this.value);populaAreasPieChart(this.value);" />
				
				<div class="conteudo-1 conteudo-aba">
					<li>&nbsp;</li>
					<li><strong>Indicador de Salário por Área Organizacional</strong></li>
					<@ww.datepicker label="Data" name="dataBase" value="${dateBase}" id="dataBase"  cssClass="mascaraData" />
					<@ww.select label="Área Organizacional" name="areaOrganizacioanal.id" list="areasCheckList" listKey="id" listValue="nome" headerValue="Selecione..." headerKey=""/>
					<li>&nbsp;</li>
					<li><strong>Indicadores de Evolução Salarial e Promoção</strong></li>
				</div>
				<@ww.textfield label="Mês/Ano" name="dataMesAnoIni" id="mesAnoIni" cssClass="mascaraMesAnoData" liClass="liLeft"/>
				<@ww.label value="a" liClass="liLeft" />
				<@ww.textfield label="Mês/Ano" name="dataMesAnoFim" id="mesAnoFim" cssClass="mascaraMesAnoData"/>
				
				<div class="conteudo-1 conteudo-aba">
					<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areasCheck" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
				</div>
				<div class="conteudo-2 conteudo-aba">
					<@frt.checkListBox label="Áreas Organizacionais" name="areasPieChartCheck" id="areasPieChartCheck" list="areasPieChartCheckList" filtro="true" selectAtivoInativo="true" onClick=""/>
				</div>
		
				<button onclick="return enviaForm();" class="btnPesquisar grayBGE"></button>
				<#include "../util/bottomFiltro.ftl" />
			</@ww.form>
			<div class="conteudo-1 conteudo-aba">
			
				<div class="legendTotal">Valor total da folha em ${dateBase}: ${valorTotalFolha}</div>
	
				<div class="fieldGraph">
					<h1>
						Salário por Área Organizacional
						<img id="salarioAreasImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</h1>
				    <div id="salarioAreas" class="graph"></div>
				    <div id="salarioAreasLegenda"></div>
				</div>
				
				<div style="clear: both"></div>
				
				<div class="fieldGraph bigger">
					<h1>
						Evolução Salarial - Faturamento
						<img id="evolucaoFolhaImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</h1>
			   		<div id="evolucaoFolha" style="margin: 25px; height: 300px; width: 900px"></div>
			    </div>
		
				<div class="fieldGraph bigger">
					<h1>
						Promoção
						<img id="faixaSalarialImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</h1>
			   		<div id="faixaSalarial" style="margin: 25px; height: 300px; width: 900px"></div>
			   		<div class="obs" style="margin: 5px;text-align: right;">
				   		 * Promoção Horizontal: Mudança de Faixa Salarial ou Salário. / Promoção Vertical: Mudança de Cargo.
			   		</div>
			    </div>
		    </div>
		    <div class="conteudo-2 conteudo-aba">
			   
			   	<div class="fieldGraph">
					<h1>
						Promoções horizontais
						<img id="promocoesHorizontaisAreasImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</h1>
				    <div id="promocoesHorizontaisAreas" class="graph"></div>
				    <div id="promocoesHorizontaisAreasLegenda"></div>
				</div>
			   
			   	<div style="clear: both"></div>
			   
			   	<div class="fieldGraph">
					<h1>
						Promoções verticais
						<img id="promocoesVerticaisAreasImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</h1>
				    <div id="promocoesVerticaisAreas" class="graph"></div>
				    <div id="promocoesVerticaisAreasLegenda"></div>
				</div>
			   
			   <div style="clear: both"></div>
			   
			   <div class="fieldGraph bigger">
					<h1>
						Promoções verticais e horizontais
						<img id="faixaSalarialAreaImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</h1>
					<div class="obs" style="margin: 15px 25px 5px 10px; text-align: right;">
				   		 * Promoção Horizontal: Mudança de Faixa Salarial ou Salário. / Promoção Vertical: Mudança de Cargo.
			   		</div>
					<div id="scrollBar" style="width: 900px; height:500px; margin: 0 auto; overflow-x: auto; overflow-y: hidden;">
			   			<div id="faixaSalarialArea" style="margin: 5px; min-height: 400px; min-width: 890px;"></div>
			   		</div>
			   		<ul><li>&nbsp;</li></ul>
			    </div>
			</div>
			
			<div style="clear: both"></div>
		    <div style="clear: both"></div>
			<a name="pagebottom"></a>
			
			<div id="divBoxSalario">
				<div id="boxSalario"></div>
				<div id="boxSalarioLegend"></div>
	
				<button class="btnImprimir" id="boxSalarioImprimir"></button>
				
				<div style="clear: both"></div>
			</div>
			
			<div id="divBoxPromoHorizontal">
				<div id="boxPromoHorizontal"></div>
				<div id="boxPromoHorizontalLegend"></div>
	
				<button class="btnImprimir" id="boxPromoHorizontalImprimir"></button>
				
				<div style="clear: both"></div>
			</div>
			
			<div id="divBoxPromoVertical">
				<div id="boxPromoVertical"></div>
				<div id="boxPromoVerticalLegend"></div>
	
				<button class="btnImprimir" id="boxPromoVerticalImprimir"></button>
				
				<div style="clear: both"></div>
			</div>
			
			<div id="aviso"></div>
		</div>
	</body>
</html>