<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
	<head>
		<@ww.head/>
		<title>Participantes LNT - ${lnt.descricao}</title>
		<style type="text/css">@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');</style>
		<script type="text/javascript" src='<@ww.url includeParams="none" value="/js/autoCompleteFortes.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/functions.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CursoDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
		<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.price_format.1.6.min.js"/>"></script><!-- Usado para o function.js cssClass=hora-->
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.cookie.js"/>'></script>
		
		<#include "../ftl/showFilterImports.ftl" />
		<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
		
		<style type="text/css">
			@import url('<@ww.url value="/css/font-awesome.min.css?version=${versao}"/>');
			@import url('<@ww.url value="/css/participantesCursoLnt.css?version=${versao}"/>');
			@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
			
			#formDialog { display: none; width: 600px; list-style-type: none;}
			
			.listCheckBoxBarra {
				width: 100%;
				height: 20px;
				background-color: #F2F2F2;
				text-align: left;
				border-bottom: 1px solid #E5E5E5;
				-moz-border-radius-bottomleft: 4px;
				-moz-border-radius-bottomright: 4px;
			}
			.listCheckBoxContainer {
				font: 13.3px sans-serif;
				border: 1px solid #CFCFCF;
				background: #fff;
				width: 100%;
				height: 170px;
				border-bottom: 1px solid #b5b5b5;
				-moz-border-radius-bottomleft: 4px;
				border-bottom-left-radius: 4px;
				-moz-border-radius-bottomright: 4px;
				border-bottom-right-radius: 4px;
			}
			
			.legend {
				height: 25px;
				width: 910px;
				background: #f7f7f7;
				color: #5C5C5A;
			}		
			
			.legend div{
				width: 220px;
				float: left;
				border: 1px solid #e7e7e7;
				padding: 5px 0;
				text-align: center;
				font-size: 11px;
				font-weight: bold;
			}
			
			#cursos .mini-actions, #cursos .actions {
				height: 22px;
				background: #f7f7f7;
				border-bottom: 2px solid #F0F0F0;
				margin: -5px -10px 5px;
				display: block !important;
			}
		</style>
		
		<#assign countCursos=0 />
		<#assign countParticipantes=0 />
	
		<script type="text/javascript">
			var countCursos = 0;
			var countParticipantes = 0;
			var adicionouParticipante = false;
			var adicionouCurso = false;

			$(function(){
				populaAreas(false);
				
				$(".visualizar-participantes").live("click", function(){
					$(this).parents(".item").find(".participantes").toggle();
					
					$(this).parents(".item").find(".info-curso").hide();
					$(this).parent().find(".editar i").removeClass("enabled");
					
					$(this).find("i:not(.fa-users)").toggle();
				});
				
				$(".editar").live("click", function(){
					$(this).parents(".item").find(".info-curso").toggle();
					
					$(this).parents(".item").find(".participantes").hide();
					$(this).parent().find(".visualizar-participantes i.fa-caret-up").hide();
					$(this).parent().find(".visualizar-participantes i.fa-caret-down").show();
					
					$(this).find("i").toggleClass("enabled");
				});
				
				resetAutocomplete();
				
				var nameValue;
				$(".cursoNome").live("keydown", function(e){
					nameValue = $(this).val();
					adicionouCurso = true;
				}).live("keyup", function(e){
					if( nameValue != $(this).val() )
						$(this).removeClass("cursoSelected");
				});
				
				$(".more-curso").click(function(){
					$("#cursos .itens").append('<div class="item">'
												+'	<div class="cabecalho">'
												+'		<input type="hidden" class="identificador" name="identificador" value="'+countCursos+'"/>'
												+'		<input type="hidden" class="cursoId" name="cursosLnt['+countCursos+'].cursoId" value="0"/>'
												+'		<input type="hidden" name="cursosLnt['+countCursos+'].lnt.id" value="${lnt.id}"/>'
												+'		<input class="cursoNome" name="cursosLnt['+countCursos+'].nomeNovoCurso" placeholder="Curso" value=""/>'
												+'		<div class="editar"><i class="fa fa-edit"></i></div>'
												+'		<div class="visualizar-participantes">'
												+'			<i class="fa fa-users" aria-hidden="true"></i>'
												+'			<i class="fa fa-caret-down"></i>'
												+'			<i style="display: none;" class="fa fa-caret-up"></i>'
												+'		</div>'
												+'	</div>'
												+'<div class="info-curso">'
												+'	<li id="wwgrp_custo" class="wwgrp">'
												+'		<div id="wwlbl_custo" class="wwlbl">'
												+'			<label for="custo" class="desc"> Custo:</label>'
												+'		</div> '
												+'		<div id="wwctrl_custo" class="wwctrl">'
												+'			<input type="text" name="cursosLnt['+countCursos+'].custo" size="12" maxlength="12" value="" id="custo" class="moeda" style="width:90px; text-align:right;">'
												+'		</div>'
												+'	</li>'
												+'	<li id="wwgrp_cargaHoraria" class="wwgrp">'
												+'		<div id="wwlbl_cargaHoraria" class="wwlbl">'
												+'			<label for="cargaHoraria" class="desc"> Carga horária:</label>'
												+'		</div> '
												+'		<div id="wwctrl_cargaHoraria" class="wwctrl">'
												+'			<input type="text" name="cursosLnt['+countCursos+'].cargaHorariaMinutos" maxlength="7" value="" id="cargaHoraria" class="hora" style="width:55px;text-align:right">'
												+'		</div>'
												+'	</li>'
												+'	<li id="wwgrp_formParticipantesCursoLnt_cursoLnt_'+countCursos+'__conteudoProgramatico" class="wwgrp">'
												+'		<div id="wwlbl_formParticipantesCursoLnt_cursoLnt_'+countCursos+'__conteudoProgramatico" class="wwlbl">'
												+'			<label for="formParticipantesCursoLnt_cursoLnt_'+countCursos+'__conteudoProgramatico" class="desc"> Conteúdo programático:</label>'
												+'		</div> '
												+'		<div id="wwctrl_formParticipantesCursoLnt_cursoLnt_'+countCursos+'__conteudoProgramatico" class="wwctrl">'
												+'			<textarea name="cursosLnt['+countCursos+'].conteudoProgramatico" cols="" rows="" id="formParticipantesCursoLnt_cursoLnt_0__conteudoProgramatico" style="width: 100%; height: 100px;" class="conteudoProgramatico"></textarea>'
												+'		</div>'
												+'	</li>'
												+'	<li id="wwgrp_formParticipantesCursoLnt_cursoLnt_'+countCursos+'__justificativa" class="wwgrp">'
												+'		<div id="wwlbl_formParticipantesCursoLnt_cursoLnt_'+countCursos+'__justificativa" class="wwlbl">'
												+'			<label for="formParticipantesCursoLnt_cursoLnt_'+countCursos+'__justificativa" class="desc"> Justificativa:</label>'
												+'		</div> '
												+'		<div id="wwctrl_formParticipantesCursoLnt_cursoLnt_'+countCursos+'__justificativa" class="wwctrl">'
												+'			<textarea name="cursosLnt['+countCursos+'].justificativa" cols="" rows="" id="formParticipantesCursoLnt_cursoLnt_0__justificativa" style="width: 100%; height: 100px;"></textarea>'
												+'		</div>'
												+'	</li>'
												+'	<div style="clear: both;"></div>'
												+'</div>'
												+'	<div class="participantes">'
												+'		<div class="mini-actions">'
												+'			<div class="mini-option remove only-selectables disabled" title="Remover selecionados" style="padding: 3px 15px; float: left;">'
												+'				<span class="ui-icon ui-icon-trash" style="float: none;"></span>'
												+'			</div>'
												+'			<div class="mini-option select-all" title="Selecionar todos" style="padding: 5px 15px 2px 15px; float: left;" >'
												+'				<i class="fa fa-check"></i>'
												+'				<i class="fa fa-align-justify"></i>'
												+'			</div>'
												+'			<div class="mini-option unselect-all" title="Retirar selecão de todos" style="padding: 5px 15px 2px 15px; float: left;">'
												+'				<i class="fa fa-close"></i>'
												+'				<i class="fa fa-align-justify"></i>'
												+'			</div>'
												+'			<div style="clear: both;"></div>'
												+'		</div>'
												+'		<ul>'
												+'			<div class="legend">'
												+'				<div>Nome Comercial</div>'
												+'				<div id="tamanhoGrande">Área Organizacional</div>'
												+'				<div>Estabelecimento</div>'
												+'				<div id="tamanhoPequeno">Empresa</div>'
												+'			</div>'
												+'			<li class="placeholder">Ainda não existem colaboradores nesse curso</li>'
												+'   	</ul>'
												+'	</div>'
												+'</div>');
												
					countCursos++;
					$(".itens").scrollTop($(".itens")[0].scrollHeight);
					resetAutocomplete();
				});
				
				$(".item .cabecalho").live("click", function(e){
					if (e.target !== this)
						return;
						
					$(this).parent(".item").toggleClass("selected");
					//$(this).parents("#cursos").find(".actions").toggle($(".item.selected").length > 0);
				});
				
				$("#relacionarCursos").click(function(){
					$('#listCheckBoxcursosCheck').html("");
					$("#cursos .item").each(function(){
						if ( $(this).find(".cursoNome").val() != "" )
							$('#listCheckBoxcursosCheck').append("<label>" +
																	"<input name='cursosCheck' value='"+$(this).find('.identificador').val()+"' type='checkbox'/>"+
																	$(this).find('.cursoNome').val() +
																"</label>");
					});
					
					if ( $("#listCheckBoxparticipantesCheck input:checked").length == 0 ) {
						jAlert('Não existem participantes para relacionar');
					} else if ( $('.cursoNome[value!=""]').length == 0 ) {
						jAlert('Não existem cursos para relacionar');
					} else {
						$('#dialogCursos').dialog({ modal: true, width: 650, title: 'Relacionar cursos',
													buttons: [
																    {
																    	id: "botaoDialog",
																        text: "Relacionar",
																        click: function() {
																        	relacionar();
																        	jAlert('Participantes relacionados com sucesso.')
																        	$(this).dialog("close");
																        }
																    },
																    {
																    	id: "botaoDialog",
																        text: "Sair",
																        click: function() {
																        	$(this).dialog("close");
																        }
																    }
																]
															});
					}
				});
				
				$("#cursos .participantes li").live("click", function(event){
					if ( !$(this).hasClass("placeholder") && !$(this).hasClass("has-respondida") ) {
						if ( $(this).parents(".box").find("li.ui-selected").length > 0 && $(this).parents("ul").find("li.ui-selected").length == 0 )
							$(this).parents(".box").find("li.ui-selected").removeClass("ui-selected");
						
						$(this).hasClass("ui-selected") ? $(this).removeClass("ui-selected") : $(this).addClass("ui-selected");
						
						//$(this).parents(".participantes").find(".mini-actions").toggle( $(this).parents(".participantes").find("li.ui-selected").length > 0 );
					}
					
					event.stopPropagation();
				});
				
				$(".mini-actions .remove").live("click", function(event) {
					$(this).parents(".participantes").find("li.ui-selected").each(function(){
						if($(this).find(".participanteCursoId").length > 0 )
							$("#participantesRemovidos").append("<input type='hidden' name='participantesRemovidos' value='" + $(this).parent().parent().parent().find('.cursoLntId').val()+ "_" + $(this).find(".participanteCursoId").val() + "' />");
					});
						
					if($(this).parents(".participantes").find("li.ui-selected").length > 0)
						adicionouParticipante = true;
					
					$(this).parents(".participantes").find("li.ui-selected").remove();
					
					if($(this).parents(".participantes").find("li").not('.placeholder').length == 0 && $(this).parents(".participantes").find('.placeholder').length == 0)
						$(this).parents(".participantes").find("ul").append('<li class="placeholder">Ainda não existem colaboradores nesse curso</li>');
					
					//$(this).parents(".mini-actions").hide();
					event.stopPropagation();
				});
				
				$(".mini-actions .select-all").live("click", function(event) {
					$(this).parents(".participantes").find("li").not(".ui-selected").not(".has-respondida").addClass("ui-selected");
					event.stopPropagation();
				});
				
				$(".mini-actions .unselect-all").live("click", function(event) {
					$(this).parents(".participantes").find("li").removeClass("ui-selected");
					//$(this).parents(".mini-actions").hide();
					event.stopPropagation();
				});
				
				$(".actions .remove").live("click", function(event) {
					$(this).parents("#cursos").find(".item.selected").each(function(){
						if($(this).find(".cursoLntId").length > 0 )
							$("#cursosRemovidos").append("<input type='hidden' name='cursosRemovidos' value='" + $(this).find(".cursoLntId").val() + "' />");
					});
					
					$("#cursos .selected").find(".participantes").find("li").each(function(){
						console.log($(this).find(".participanteCursoId"));
						if($(this).find(".participanteCursoId").length > 0 )
							$("#participantesRemovidos").append("<input type='hidden' name='participantesRemovidos' value='"+ $(this).parent().parent().parent().find('.cursoLntId').val()+ "_" + $(this).find(".participanteCursoId").val() + "' />");
					});
					
					if($(this).parents("#cursos").find(".item.selected").length > 0)	
						adicionouParticipante = true;
					
					$(this).parents("#cursos").find(".item.selected").remove();
					
					//$(this).parents(".actions").hide();
					event.stopPropagation();
				});
				
				$(".actions .select-all").live("click", function(event) {
					$(this).parents("#cursos").find(".item").not(".selected").not(".has-respondida").addClass("selected");
					event.stopPropagation();
				});
				
				$(".actions .unselect-all").live("click", function(event) {
					$(this).parents("#cursos").find(".item").removeClass("selected");
					$(this).parents(".actions").hide();
					event.stopPropagation();
				});
				
				$( ".resizable" ).resizable({ handles: "s" });
				
				if($.cookie("timeline_maoExplicativa") != 'true'){
					$('#maoExplicativa').append(	'<a onmouseover="$(\'#maoExplicativa\').remove();$.cookie(\'timeline_maoExplicativa\', \'true\');">'
												+	'	<img height="39" width="12" src="<@ww.url value="/imgs/mao_animada.gif"/>" style="margin-top: -20px;">'
												+ 	'</a>');
				}
			});
			
			function getColaboradoresByArea() {
				var areasIds = $("input[name='areasCheckDialog']:checked").map(function(){return $(this).val();}).get();
				ColaboradorDWR.getByAreasIds(createListColaborador, areasIds);
			}
			
			function createListColaborador(data) {
				addChecksCheckBox('participantesCheck', data);
			}
			
			function relacionar() {
				$("#listCheckBoxcursosCheck input:checked").each(function(){
					var identificador = $(this).val();
					var curso = $("#cursos .item .identificador[value="+identificador+"]").parents(".item");
	
					if($(curso).find(".visualizar-participantes .fa-caret-up").css("display") == "none")
						$(curso).find(".visualizar-participantes").click();
	
					$("#listCheckBoxparticipantesCheck label input:checked").each(function(){
						if ( $(curso).find(".participantes ul li#"+$(this).val()).length == 0 ) {
							var participante = '<li id="'+$(this).val()+'">'
								+'<input type="hidden" name="cursosLnt['+identificador+'].participanteCursoLnts['+countParticipantes+'].colaborador.id" class="participanteColaboradorId" value="'+$(this).val()+'"/>';
							
							if($(curso).find(".cursoLntId").length > 0)
								participante+='<input type="hidden" name="cursosLnt['+identificador+'].participanteCursoLnts['+countParticipantes+'].cursoLnt.id" class="participanteCursoLntId" value="'+$(curso).find(".cursoLntId").val()+'"/>';
							
							participante+='<input type="hidden" name="cursosLnt['+identificador+'].participanteCursoLnts['+countParticipantes+'].areaOrganizacional.id" class="participanteAreaId" value="'+$(this).attr("areaorganizacionalid")+'"/>';
															
							var nomeMaisNomeComercial = $(this).parent().text().split('(');
							var nome = nomeMaisNomeComercial[0];
							var momeComercial = (nomeMaisNomeComercial[1]).replace(')', ''); 
							var areaCompleta = $(this).attr('areaNome');
							var areaArray = areaCompleta.split('>');
							var areaFolha = areaArray[areaArray.length - 1];
							
							if(areaFolha.length > 45)
								areaFolha = areaFolha.substring(0,46) + "...";
										
							participante+='<span style="cursor: context-menu;" class="participanteColaboradorNomeComercial" href=# onmouseout="hideTooltip()" onmouseover="showTooltip(event,\''
										+ nome + '\');return false">' + momeComercial + '</span>'
										
										 +'<span style="cursor: context-menu;" id="tamanhoGrande" href=# onmouseout="hideTooltip()" onmouseover="showTooltip(event,\''
										 + areaCompleta + '\');return false">' + areaFolha + '</span>'
										
										 +'<span class="participanteEstabelecimentoNome">' + $(this).attr('estabelecimentoNome') + '</span>'
										 +'<span class="participanteEmpresaNome" id="tamanhoPequeno">' + $(this).attr('empresaNome') + '</span>'
							
							participante += '<input type="hidden" class="participanteColaboradorNome" value="' + nome + '"/>';
							participante += '<input type="hidden" class="participanteAreaNome" value="' + areaCompleta + '"/>';
							participante += '<input type="hidden" class="participanteColaboradorMatricula" value="' + $(this).attr('matricula') + '"/>';
							participante += '</li>';
							
							$(curso).find(".participantes ul").append(participante);
							countParticipantes++;
							$(curso).find(".participantes ul .placeholder").remove();
							adicionouParticipante = true;
						}
					});
				});
			}
			
			function resetAutocomplete() {
				$( ".cursoNome" ).unbind("autocomplete", "data");
				var urlFind = '<@ww.url includeParams="none" value="/desenvolvimento/curso/findJson.action"/>';
				var cursosValues = $('.cursoId').map(function() { if($(this).val() != 0) return $(this).val();}).toArray();
				
				$( ".cursoNome" ).each(function(){
					$(this).autocomplete({
						source: function( request, response ) {
							$.ajax({
								url: urlFind,
								dataType: "json",
								type: "POST",
								data: {
									descricao: request.term,
								},
								success: function(data ) {
									response( $.map( data, function( item ) {
										if(cursosValues.indexOf(item.id) >= 0)
											return;
										
										return {
											id: item.id,
											label: item.value,
											descricao: item.value.replace(
												new RegExp(
													"(?![^&;]+;)(?!<[^<>]*)(" +
													$.ui.autocomplete.escapeRegex(request.term) +
													")(?![^<>]*>)(?![^&;]+;)", "gi"
												), "<strong>$1</strong>" ) 
										}
									}));
								}
							});
						},
						
						minLength: 3,
						delay: 500,
						search: function( event, ui ) { 
							$(this).parent().find(".cursoId").val('');
							cursosValues = $('.cursoId').map(function() { if($(this).val() != 0) return $(this).val();}).toArray();							
						},
			      		select: function( event, ui ) { 
							$(this).parent().find(".cursoId").val(ui.item.id);
							$(this).addClass("cursoSelected");
							populaDadosCurso($(this), ui.item.id);
							$(this).parent().find('.cursoNome').val(ui.item.label);
						}
			    	}).data( "autocomplete" )._renderItem = function( ul, item ) {
							return $( "<li></li>" )
								.data( "item.autocomplete", item )
								.append( "<a>" + item.descricao + "</a>" )
								.appendTo( ul );
					};
		    	});
		    	
		    	$("#entityId").val("");
			}
			
			function populaDadosCurso(cursoTela, cursoId){
				CursoDWR.findDadosBasicosById(cursoId, function(curso){
					var cargaHoraria = "";
					if (curso.cargaHoraria && curso.cargaHoraria != null && curso.cargaHoraria != "") {
						var hora = curso.cargaHoraria / 60;   
					    var minutos = curso.cargaHoraria % 60;
					        
					     if(minutos.toString().length == 1)
					     	minutos = '0' + minutos;      
					           
					    cargaHoraria = arredondar(hora,0) + ":" + minutos;	
					}
					
					cursoTela.parent().parent().find("#cargaHoraria").val(cargaHoraria);
					cursoTela.parent().parent().find(".conteudoProgramatico").val(curso.conteudoProgramatico);
				});
			
			}
			
			function arredondar(numero, casas){
				var aux = Math.pow(10,casas);
				return Math.floor(numero * aux)/aux;
			}
			
			function populaAreas(dialog)
			{
				DWRUtil.useLoadingMessage('Carregando...');
				
				var empresaName = 'empresasCheck';
				var areaName = 'areasCheck';
				
				if(dialog){
					empresaName += 'Dialog';
					areaName += 'Dialog';
				}
				
				var empresaIds = $("input[name='" + empresaName + "']:checked").map(function(){return $(this).val();}).get();
				if(empresaIds.length > 0){
					AreaOrganizacionalDWR.getByEmpresasAndLnt(empresaIds, ${lnt.id}, function(data){
													if(dialog)
														addChecksByCollection(areaName, data, 'nome', 'getColaboradoresByArea()');
													else{
														addChecksByCollection(areaName, data, 'nome');
														selecionaArea();
													}
												});
				}else
					$("#listCheckBox" + areaName + " > label").remove();
			}	
			
			function selecionaArea(){
				var areasCheck = new Array();
				<#if areasCheckList?exists>
					<#list areasCheckList as areaCheck>
						<#if areaCheck.selecionado>
							$('#checkGroupareasCheck' + ${areaCheck.id}).attr('checked',true);  
						</#if>
					</#list>
				</#if>
			}
			
			function pesquisar(){
				if(adicionouParticipante || adicionouCurso)
					dialog("Pesquisar");
				else{
					processando('<@ww.url includeParams="none" value="/imgs/"/>');
					$('#prepareParticipantes').submit();
				}
			}
			
			function voltar(){
				if(adicionouParticipante || adicionouCurso)
					dialog("Voltar");
				else
					window.location.href='list.action'
			}
			
			function dialog(variavel){
				msg = "Existem Cursos e/ou participantes inseridos e/ou removidos que não foram gravados.<br><br> Caso opte por "  + variavel.toLowerCase() + ", os cursos e/ou participantes que acabaram de ser inseridos e/ou removidos não serão gravados .<br><br> Deseja realmente " + variavel.toLowerCase() + "? ";
				$('#dialogParticipantes').html(msg).dialog({ modal: true, width: 530, title: 'Aviso',
										  		buttons: 
												[
												    {
												    	id: "botaoDialog",
												        text: variavel,
												        click: function() { 
															processando('<@ww.url includeParams="none" value="/imgs/"/>');
															if(variavel == "Pesquisar")
																$('#prepareParticipantes').submit();
															else
																window.location.href='list.action';
												        }
												    },
												    {
												    	id: "botaoDialog",
												        text: "Cancelar",
												        click: function() { $(this).dialog("close"); }
												    }
												]
											});
			}
			
			function submeter(){
				processando('<@ww.url includeParams="none" value="/imgs/"/>');
				document.formParticipantesCursoLnt.submit();
			}
			
		</script>
	</head>
	<body>
		<@ww.actionerror/>
		<@ww.actionmessage/>
		
			<div id="formDialog">
				<@frt.checkListBox name="empresasCheckDialog" id="empresasCheckDialog" label="Empresas" list="empresasCheckListDialog" filtro="true" onClick="populaAreas(true);" form="'#formDialog'"/>
				<@frt.checkListBox name="areasCheckDialog" id="areasCheckDialog" label="Áreas organizacionais" list="areasCheckListDialog" filtro="true" onClick="getColaboradoresByArea();" form="'#formDialog'"/>
				<@frt.checkListBox name="participantesCheck" id="participantesCheck" label="Participantes" list="participantesCheckList" filtro="true" form="'#formDialog'"/>
				<button id="relacionarCursos" type="button">Relacionar Cursos</button>
				<button onclick="$('#formDialog').dialog('close');" type="button">Sair</button>
			</div>

			<#include "../util/topFiltro.ftl" />
				<@ww.form name="prepareParticipantes" id="prepareParticipantes" action="prepareParticipantes.action" method="POST">
					<@ww.hidden name="lnt.id" value="${lnt.id}"/>
					<@frt.checkListBox name="empresasCheck" id="empresasCheck" label="Empresas" list="empresasCheckList" filtro="true" onClick="populaAreas(false);"/>
					<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas organizacionais" list="areasCheckList" filtro="true"/>
					<button id="Pesquisar" type="button" onclick="pesquisar();" class="style-left">Pesquisar</button>
				</@ww.form>
			<#include "../util/bottomFiltro.ftl" />
			
			<div id="dialogCursos" style="display:none;">
				<@frt.checkListBox name="cursosCheck" id="cursosCheck" label="Cursos LNT" list="cursosCheckList" filtro="true" form="'#dialogCursos'" height="290"/>
			</div>
			
			<@ww.form name="formParticipantesCursoLnt" id="formParticipantesCursoLnt" action="saveParticipantesCursoLnt.action" method="POST">
				<@ww.hidden name="lnt.id" value="${lnt.id}"/>
				
				<div id="dialogParticipantes" style="display:none;">
					<ul></ul>
				</div>
				
				<div id="cursos">
					<div class="title">
						<span class="ui-icon ui-icon-plusthick more-curso" title="Inserir Curso" disabled="disabled"></span>
						Cursos
						<span id="inserir_Avaliador" style="float: right" title="Inserir Participante no Curso" disabled="disabled" onclick="$('#formDialog').dialog({title: 'Inserir Participantes no Curso', modal: true, width: 800 });">
							<img src='<@ww.url includeParams="none" value="/imgs/addParticipantes.png"/>' border='0'/>
						</span>
					</div>
					<div class="style-separator"></div>
					<div class="actions">
						<div class="option remove only-selectables disabled" title="Remover selecionados" style="padding: 3px 15px; float: left;">
							<span class="ui-icon ui-icon-trash" style="float: none;"></span>
					    </div>
					    <div class="option select-all" title="Selecionar todos" style="padding: 5px 15px 2px 15px; float: left;" >
							<i class="fa fa-check"></i>
							<i class="fa fa-align-justify"></i>
					    </div>
					    <div class="option unselect-all" title="Retirar selecão de todos" style="padding: 5px 15px 2px 15px; float: left;">
							<i class="fa fa-close"></i>
							<i class="fa fa-align-justify"></i>
					    </div>
					    <div style="clear: both;"></div>
					</div>
					<div class="resizable">
						<div class="itens">
							<#list cursosLnt as curso>
								<div class="item">
									<div class="cabecalho">
										<input type="hidden" class="identificador" name="identificador" value="${countCursos}"/>
										<input type="hidden" id="cursoId_${cursosLnt[countCursos].cursoId}" class="cursoId" name="cursosLnt[${countCursos}].cursoId" value="${ cursosLnt[countCursos].cursoId }"/>
										
										<#if curso.id?exists >
											<input type="hidden" name="cursosLnt[${countCursos}].id" class="cursoLntId" value="${curso.id}"/>
										</#if>
										<input type="hidden" name="cursosLnt[${countCursos}].lnt.id" value="${lnt.id}"/>
										<input 
											<#if curso.id?exists && cursosLnt[countCursos].cursoId?exists && cursosLnt[countCursos].cursoId != 0 > class="cursoNome cursoSelected" <#else> class="cursoNome" </#if>
											name="cursosLnt[${countCursos}].nomeNovoCurso" placeholder="Curso" value="${curso.nomeNovoCurso}"/>
										
										<div class="editar" title="Informações do Curso"><i class="fa fa-edit"></i></div>
										<div class="visualizar-participantes" title="Participantes do Curso">
											<i class="fa fa-users" aria-hidden="true"></i>
											<i class="fa fa-caret-down"></i>
											<i style="display: none;" class="fa fa-caret-up"></i>
										</div>
									</div>
									<div class="info-curso">
										<@ww.textfield label="Custo" cssClass="moeda" name="cursosLnt[${countCursos}].custo" id="custo" maxlength="12" size="12" cssStyle="width:90px; text-align:right;"/>
										<@ww.textfield label="Carga horária" name="cursosLnt[${countCursos}].cargaHorariaMinutos" cssStyle="width:55px;text-align:right" id="cargaHoraria" cssClass="hora" maxLength="7"/>
										<@ww.textarea label="Conteúdo programático" name="cursosLnt[${countCursos}].conteudoProgramatico" cssStyle="width: 100%; height: 100px;" cssClass="conteudoProgramatico" />
										<@ww.textarea label="Justificativa" name="cursosLnt[${countCursos}].justificativa" cssStyle="width: 100%; height: 100px;"/>
									    <div style="clear: both;"></div>
									</div>
									<div class="participantes">
										<div class="mini-actions">
											<div class="mini-option remove only-selectables disabled" title="Remover selecionados" style="padding: 3px 15px; float: left;">
												<span class="ui-icon ui-icon-trash" style="float: none;"></span>
										    </div>
										    <div class="mini-option select-all" title="Selecionar todos" style="padding: 5px 15px 2px 15px; float: left;" >
												<i class="fa fa-check"></i>
												<i class="fa fa-align-justify"></i>
										    </div>
										    <div class="mini-option unselect-all" title="Retirar selecão de todos" style="padding: 5px 15px 2px 15px; float: left;">
												<i class="fa fa-close"></i>
												<i class="fa fa-align-justify"></i>
										    </div>
										    <div style="clear: both;"></div>
										</div>
										<ul>
											<div class="legend">
											  	<div>Nome Comercial</div>
											  	<div id="tamanhoGrande">Área Organizacional</div>
											  	<div>Estabelecimento</div>
											  	<div id="tamanhoPequeno">Empresa</div>
											</div>
											
											<#if !curso.id?exists || (!curso.participanteCursoLnts?exists || curso.participanteCursoLnts.size() == 0) > 
												<li class="placeholder">Ainda não existem colaboradores nesse curso</li>
											<#else>
												<#list curso.participanteCursoLnts as participante>
													<li id="${participante.colaborador.id}">
														<input type="hidden" nameTmp="participantesCursosLnt[${countParticipantes}].id" class="participanteCursoId" value="${participante.id}"/>
														<input type="hidden" nameTmp="participantesCursosLnt[${countParticipantes}].colaborador.id" class="participanteColaboradorId" value="${participante.colaborador.id}"/>
														
														<#if participante.colaborador.matricula?exists>
															<input type="hidden" nameTmp="participantesCursosLnt[${countParticipantes}].colaborador.matricula" class="participanteColaboradorMatricula" value="${participante.colaborador.matricula}"/>
														<#else>
															<input type="hidden" nameTmp="participantesCursosLnt[${countParticipantes}].colaborador.matricula" class="participanteColaboradorMatricula" value=""/>
														</#if>
														
														<input type="hidden" nameTmp="participantesCursosLnt[${countParticipantes}].cursoLnt.id" class="participanteCursoLntId" value="${participante.cursoLnt.id}"/>
														<input type="hidden" nameTmp="participantesCursosLnt[${countParticipantes}].areaOrganizacional.id" class="participanteAreaId" value="${participante.areaOrganizacional.id}"/>
														<span style="cursor: context-menu;" class="participanteColaboradorNomeComercial" href=# onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${participante.colaborador.nome}');return false">${participante.colaborador.nomeComercial}</span>
														<input type="hidden" class="participanteColaboradorNome" value="${participante.colaborador.nome}"/>
														<input type="hidden" class="participanteAreaNome" value="${participante.areaOrganizacional.nome}"/>
														<span style="cursor: context-menu;" id="tamanhoGrande" href=# onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${participante.areaOrganizacional.nome}');return false">${participante.areaNomeFolhaTruncado}</span>
														<span class="participanteEstabelecimentoNome">${participante.estabelecimentoNome}</span>
														<span class="participanteEmpresaNome" id="tamanhoPequeno">${participante.empresaNome}</span>
													</li>
													<#assign countParticipantes = countParticipantes + 1/>
												</#list>
											</#if>
										</ul>
									</div>
								</div>
								
								<#assign countCursos = countCursos + 1/>
							</#list>
							<div id="participantesRemovidos"></div>
				      		<div id="cursosRemovidos"></div>
						</div>
					</div>
				</div>
				</br>
				<div id="maoExplicativa" style="float: right; margin-right: 480px; z-index: 1000; opacity: 0.5;"></div>
				<button type="button" onclick="submeter();" class="style-left">Gravar</button>
				<button onclick="voltar()" type="button">Voltar</button>
				<script>
					countParticipantes=${countParticipantes};
					countCursos=${countCursos};
				</script>
			</@ww.form>
	</body>
</html>
