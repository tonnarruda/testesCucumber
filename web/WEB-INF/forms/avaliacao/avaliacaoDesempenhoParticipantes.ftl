<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
	<head>
	
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
			@import url('<@ww.url value="/css/formModal.css?version=${versao}"/>');
			
			<#if isAvaliados>
		    	#menuParticipantes a.ativaAvaliado{border-bottom: 2px solid #5292C0;}
			<#else>
		    	#menuParticipantes a.ativaAvaliador{border-bottom: 2px solid #5292C0;}
		    </#if>
		    
		    #box { height: 500px; }
	  	</style>
	  	
		<@ww.head/>
		<title>Participantes - ${avaliacaoDesempenho.titulo}</title>
		
		<#assign countColaboradorQuestionarios=0 />
		<#assign gerarAutoAvaliacoesEmLoteAction="gerarAutoAvaliacoesEmLote.action"/>
		
		<#assign validarCamposModal="return validaFormulario('formModal', new Array('@colaboradorsCheck'), null)"/>
		
		<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formModal.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
		
		<script type="text/javascript">
		$(function() {
			$('#tooltipHelp').qtip({
				content: 'Gera uma nova avaliação para cada um dos colaboradores desta avaliação, na qual ele irá avaliar apenas a si próprio.'
			});
		});
		
		function pesquisar()
		{
			var matricula = $("#matriculaBusca").val();
			var nome = $("#nomeBusca").val();
			var empresaId = $("#empresa").val();
			var areasIds = getArrayCheckeds(document.getElementById('formPesquisa'), 'areasCheck');

			DWRUtil.useLoadingMessage('Carregando...');
			ColaboradorDWR.getColaboradoresByAreaNome(createListColaborador, areasIds, nome, matricula, empresaId);

			return false;
		}

		function createListColaborador(data)
		{
			addChecks('colaboradorsCheck',data);
		}

		function populaAreas(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresa(createListAreas, empresaId);
		}

		function createListAreas(data)
		{
			addChecks('colaboradorsCheck');
			addChecks('areasCheck', data);
		}
		
		function excluir()
		{
			newConfirm('Confirma exclusão dos colaboradores selecionados?', function(){ 
				document.form.action = "";
				document.form.submit();
			});
		}
		
		function gerarAutoAvaliacoesEmLote()
		{
			$.alerts.okButton = '&nbsp;Sim&nbsp;';
			$.alerts.cancelButton = '&nbsp;Não&nbsp;';
			newConfirm('Esta ação irá criar uma nova avaliação para cada um dos colaboradores desta avaliação, na qual ele irá avaliar apenas a si próprio. Deseja continuar?', function(){
				document.form.action = "${gerarAutoAvaliacoesEmLoteAction}";
				document.form.submit();
			});
		}
		</script>
	</head>
	<body>
	
	
		<@ww.actionerror />
		<!-- <@ww.form name="form" action="" method="POST">
			<@display.table name="participantes" id="participante" class="dados">
			
				<#if avaliacaoDesempenho.liberada>
					<@display.column title="">
						<img border="0" src="<@ww.url value="/imgs/no_check.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
					</@display.column>
				<#else>
					<@display.column title="<input type='checkbox' id='md' onclick='atualizaChecks(\"checkParticipante\", this.checked);' />" style="width: 26px; text-align: center;">
						<input type="checkbox" class="checkParticipante" value="${participante.id}" name="participanteIds" />
					</@display.column>
				</#if>
			
				<@display.column title="Ações" class="acao" style="width:40px;">
					<#if avaliacaoDesempenho.liberada>
						<img border="0" title="" src="<@ww.url value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
					<#else>
						<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}&participanteIds=${participante.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
					</#if>
				</@display.column>
				<@display.column title="" property="nome" style="width:330px;"/>
				<@display.column title="Cargo" property="faixaSalarial.descricao" style="width:300px;"/>
				<@display.column title="Área Organizacional" property="areaOrganizacional.nome" style="width:300px;"/>
			</@display.table>

			<@ww.hidden name="avaliacaoDesempenho.id"/>
		</@ww.form>
		
		
		<div class="buttonGroup">
			<#if avaliacaoDesempenho.liberada>
				<button class="btnInserirDesabilitado" disabled="disabled" onmouseover="cursor:pointer;" ></button>
				<button class="btnExcluirDesabilitado" disabled="disabled" onmouseover="cursor:pointer;" ></button>
			<#else>
				<button onclick="openbox('', 'nomeBusca');" class="btnInserir"></button>
				<button onclick="javascript: excluir();" class="btnExcluir"></button>
			</#if>
			
			<#if !avaliacaoDesempenho.liberada && isAvaliados && (!avaliadors?exists || (avaliadors?exists && avaliadors?size == 0))>
				<button onclick="gerarAutoAvaliacoesEmLote();" class="btnAutoAvaliacoesEmLote"></button>
				<img id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -22px" />
			<#elseif isAvaliados>	
				<button class="btnAutoAvaliacoesEmLoteDesabilitado" onclick="javascript: jAlert('Não é possível gerar autoavaliações em lote a partir de avaliações onde já foram definidos avaliadores.');" style="cursor:pointer;" ></button>
			</#if>
			
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
		-->
		<!--
		 Modal para Inserir Participantes
		-->
		
	<style>
		#avaliados, #avaliadores {
			float: left;
			width: 350px;
			margin: 10px;
		}
		#avaliados h1, #avaliadores h1 {
			//background: #E0DFDF !important;
			background: none;
			border: 1px solid #e7e7e7;
			border-bottom: 2px solid #5292C0;
			padding: 7px 15px;
			text-align: center;
			color: #5C5C5A;
			border-radius: 0;
			font-size: 12px;
			font-weight: normal;
			margin: 0;
		}
		
		#avaliados li {
			padding: 10px 10px;
			cursor: copy;
			list-style: none;
			border-bottom: 1px solid #e7e7e7;
			color: #5C5C5A;
			font-size: 11px;
			min-height: 13px;
		}
		
		#avaliados li div{
			font-size: 11px;
			float: left;
		}

		.legend {
			height: 25px;
			background: #e7e7e7;
			color: #5C5C5A;
			display: none;
		}		
		
		.legend div{
			width: 228px;
			float: left;
			border: 1px solid #C6C6C6;
			padding: 5px;
			text-align: center;
			font-size: 11px;
			font-weight: bold;
		}
		
		.nome {
			width: 238px;
		}
		
		.faixa, .area {
			display: none;
			width: 220px;
		}
				
		#avaliadores ul {
			min-height: 50px !important;
		}
		
		.ui-state-hover, .ui-state-default{
			background: #f7f7f7 !important;
			color: #5C5C5A !important;
		}
		.ui-state-hover li, .ui-state-default li{
			font-weight: normal;
		}
		.ui-state-default{
			border-color: #e7e7e7 !important;
		}
		
		.ui-state-hover{
			border-color: #b2b2b2 !important;
		}
		
		.portlet-header .ui-icon {
			float: right;
			cursor: pointer;
			margin-top: -2px;
			background-image: url(../../imgs/ui-icons_6da8d5_256x240.png);
		}
		
		.portlet { margin: 5px;}
		.portlet-header, .portlet-content { padding: 5px; font-size: 11px; color: #5C5C5A; }
		.portlet-header {
			background: #f7f7f7;
			border: 1px solid #e7e7e7;
			font-weight: bold;
		}
		.portlet-content { padding: 5px 10px; border: 1px solid #e7e7e7; }
		.portlet-content li {
			padding: 5px;
			border-bottom: 1px solid #e7e7e7;
			cursor: default;
		}
		.portlet-content li.placeholder {
			border: none;
		}
		
		.column {
			padding: 5px 10px;
			height: 500px;
			overflow-x: hidden;
			border: 1px solid #e7e7e7;
		}
	
		.more-avaliador, .more-avaliado {
			float: left;
			cursor: pointer;
			margin-left: -7px;
			background-image: url(../../imgs/ui-icons_6da8d5_256x240.png) !important;
		}
		
		.ui-icon-circle-triangle-e {
			float: right;
			cursor: pointer;
			margin-right: -7px;
			background-image: url(../../imgs/ui-icons_6da8d5_256x240.png) !important;
		}
		
		.ui-icon-circle-triangle-w {
			float: right;
			cursor: pointer;
			margin-right: -7px;
			display: none;
			background-image: url(../../imgs/ui-icons_6da8d5_256x240.png) !important;
		}
	
		#feedback { font-size: 1.4em; }
	  	#selectable .ui-selecting { background: #7BB5DF; }
	  	#selectable .ui-selected { background: #5292C0; color: white; }
	  	#selectable { list-style-type: none; margin: 0; padding: 0;}
	  	#selectable li { margin: 3px; padding: 7px; }
	</style>
	
	<script>
  	  var countColaboradorQuestionarios = 0;
	  $(function() {
	    //$( "#avaliados" ).accordion();
	    $( "#avaliadores ul" ).droppable({
	      activeClass: "ui-state-default",
	      hoverClass: "ui-state-hover",
	      accept: ":not(.ui-sortable-helper)",
	      drop: function( event, ui ) {
	        <#if !avaliacaoDesempenho.permiteAutoAvaliacao >
	        if( ui.draggable.attr('id') != $(this).attr('id')) {
		        $( this ).find( ".placeholder" ).remove();
	        </#if>
		        if( $(this).find(".avaliado_"+ui.draggable.attr('id')).length == 0 ) {
		        	$("<li class='avaliado_"+ui.draggable.attr('id')+"'></li>").text( ui.draggable.find(".nome").text() ).appendTo( this );
		        	$(".avaliado_"+ui.draggable.attr('id')).append('<input type="hidden" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].colaborador.id" value="' + ui.draggable.attr("id") + '"/>' +
							        							   '<input type="hidden" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].avaliador.id" value="' + $(this).attr("id") + '"/>' +
							        							   '<input type="hidden" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].avaliacao.id" value="${avaliacaoDesempenho.avaliacao.id}"/>' +
							        							   '<input type="hidden" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].avaliacaoDesempenho.id" value="${avaliacaoDesempenho.id}"/>');
		        	countColaboradorQuestionarios++;
		        }
		    <#if !avaliacaoDesempenho.permiteAutoAvaliacao >
	        } else {
	        	$("<div>A avaliação não permite autoavaliação</div>").dialog({
	        		modal: true,
	        		height: '120',
	        		title: "Aviso",
	        		buttons: { "Ok": function() { $( this ).dialog( "close" );} }
	        	});
	        }
	        </#if>
	      }
	    }).sortable({
	      revert: true,
	      items: "li:not(.placeholder)",
	      sort: function() {
	        $( this ).removeClass( "ui-state-default");
	      }
	    });
	    
	    $( "#avaliados li" ).draggable({
	      connectToSortable: "#sortable ul",
	      helper: "clone",
	      revert: "invalid"
	    });
	    
	    //$("#selectable").selectable();
	    
	    $( ".portlet" )
	      .addClass( "ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" )
	      .find( ".portlet-header" )
	        .addClass( "ui-widget-header ui-corner-all" )
	        .prepend( "<span class='ui-icon ui-icon-minusthick portlet-toggle'></span>");
	 
	    $( ".portlet-toggle" ).click(function() {
	      var icon = $( this );
	      icon.toggleClass( "ui-icon-minusthick ui-icon-plusthick" );
	      icon.closest( ".portlet" ).find( ".portlet-content" ).toggle();
	    });
	    
	    var selecteds = new Array;
	    var lastSelected;
	    var activeShift = false;
	    var activeCtrl = false;
	    $("#selectable li").click(function(event){
	    	console.log(lastSelected);
	    	if (activeCtrl) {
	    		if( selecteds.indexOf($(this).attr("id")) == -1 ) {
	    			selecteds.push($(this).attr("id"));
		    		$(this).addClass("ui-selected");
		    	} else {
		    		selecteds.splice(selecteds.indexOf($(this).attr("id")), 1);
			    	$(this).removeClass("ui-selected");
		    	}
	    	} else if (activeShift) {
	    		var elements = $("#selectable li");
	    		if (typeof lastSelected == "undefined") {
	    			selecteds.push($(this).attr("id"));
	    			$(this).addClass("ui-selected");
	    		} else {
		    		var lastElement = $("#selectable li[id="+lastSelected+"]");
		    		var element = $(this);
		    		
		    		elements = elements.splice(elements.index(lastElement), elements.index(element) - elements.index(lastElement) + 1);
		    		
		    		$(elements).each(function(){
		    			selecteds.push($(this).attr("id"));
		    			$(this).addClass("ui-selected");
		    		});
	    		}
	    	} else {
	    		if (selecteds.length >= 1) {
	    			if ( selecteds.length == 1 && selecteds.indexOf($(this).attr("id")) != -1 ) {
				    	$(this).removeClass("ui-selected");
				    	selecteds = new Array;
	    			} else {
	    				$("#selectable li").removeClass("ui-selected");
	    				selecteds = new Array;
	    			
	    				selecteds.push($(this).attr("id"));
	    				$(this).addClass("ui-selected");
	    			}
	    		} else {
	    			selecteds.push($(this).attr("id"));
		    		$(this).addClass("ui-selected");
	    		}
	    	}
	    	
	    	lastSelected= $(this).attr("id");
	    });
	    
	    $(".ui-icon-circle-triangle-e").click(function(){
	    	$('#avaliados, #avaliadores').hide();
	    	$(this).parent().parent().show();
	    	$(this).parent().parent().css("width","720px");
	    	$(this).parent().parent().find(".faixa").toggle();
	    	$(this).parent().parent().find(".area").toggle();
	    	$(this).hide();
	    	$(this).parent().find(".ui-icon-circle-triangle-w").toggle();
	    	$(".legend").toggle();
	    });
	    
	    $(".ui-icon-circle-triangle-w").click(function(){
	    	$('#avaliados, #avaliadores').show();
	    	$(this).parent().parent().css("width","350px");
	    	$(this).parent().parent().find(".faixa").toggle();
	    	$(this).parent().parent().find(".area").toggle();
	    	$(this).hide();
	    	$(this).parent().find(".ui-icon-circle-triangle-e").toggle();
	    	$(".legend").toggle();
	    });
	    
	    $('body').keydown(function(event){
	    	if ( event.which == 16 )
	    		activeShift = true;
	    	if ( event.which == 17 )
	    		activeCtrl = true;
	    }).keyup(function(event){
	    	if ( event.which == 16 )
	    		activeShift = false;
	    	if ( event.which == 17 )
	    		activeCtrl = false;
	    });;
	  });
	  
	  function openboxAvaliado() {
	  	$("#formModal").attr("action", "insertAvaliados.action");
	  	openbox('Inserir Avaliado', 'nomeBusca');
	  }
	  
	  function openboxAvaliador() {
	  	$("#formModal").attr("action", "insertAvaliadores.action");
	  	openbox('Inserir Avaliador', 'nomeBusca');
	  	
	  }
	</script>
	  
	  	<div id="box">
			<span id="boxtitle"></span>
			<@ww.form name="formPesquisa" id="formPesquisa" action="" onsubmit="pesquisar();return false;" method="POST">
				Empresa: <@ww.select theme="simple" label="Empresa" onchange="populaAreas(this.value);" name="empresaId" id="empresa" list="empresas" listKey="id" listValue="nome" cssStyle="width: 245px;" headerKey="" disabled="!compartilharColaboradores" />
				<br>
				<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" list="areasCheckList" form="document.getElementById('formPesquisa')" filtro="true" selectAtivoInativo="true"/>
				<@ww.textfield label="Matrícula" name="matriculaBusca" id="matriculaBusca" liClass="liLeft" cssStyle="width:80px;"/>
				<@ww.textfield label="Nome" name="nomeBusca" id="nomeBusca" cssStyle="width:410px;"/>
				<button onclick="pesquisar();return false;" class="btnPesquisar"></button>
				<br><br>
			</@ww.form>
	
			<@ww.form name="formModal" id="formModal" action="" method="POST" style="display: none !important;">
				<@frt.checkListBox label="Colaboradores" name="colaboradorsCheck" list="colaboradorsCheckList" form="document.getElementById('formModal')" filtro="true"/>
				<@ww.hidden name="avaliacaoDesempenho.id"/>
				<@ww.hidden name="isAvaliados"/>
				<@ww.hidden name="empresaId"/>
				<@ww.token/>
			</@ww.form>
	
			<div class="buttonGroup">
				<button onclick="${validarCamposModal};" class="btnGravar"></button>
				<button onclick="closebox();" class="btnCancelar"></button>
			</div>
		</div>
		
		<div style="width: 740px; margin: 0 auto;">
			<@ww.form name="form" action="" method="POST">
				<div id="avaliados">
				  <h1 class="ui-widget-header">
				  	<span class="ui-icon ui-icon-plusthick more-avaliado" title="Inserir Avaliado" onclick="openboxAvaliado('Inserir Avaliado', 'nomeBusca');"></span>
				  	Avaliados
					<span class="ui-icon ui-icon-circle-triangle-e"></span>
					<span class="ui-icon ui-icon-circle-triangle-w"></span>
				  </h1>
				  <div class="legend">
				  	<div style="width: 247px;">Nome</div>
				  	<div style="width: 208px;">Cargo</div>
				  	<div style="width: 229px;">Área Organizacional</div>
				  </div>
				  <div class="ui-widget-content column">
				    <ol id="selectable">
				      <#list participantes as avaliado>
				      	<li class="ui-widget-content" id="${avaliado.id}">
				      		<div class="nome">${avaliado.nome}</div>
				      		<div class="faixa">${avaliado.faixaSalarial.descricao}</div>
				      		<div class="area">${avaliado.areaOrganizacional.nome}</div>
				      	</li>
				      </#list>
				    </ol>
				  </div>
				</div>
			</@ww.form>
			 
			<@ww.form name="formAvaliadores" id="formAvaliadores" action="gravaAssociacoesAvaliadoAvaliador" method="POST">
				<div id="avaliadores">
					<h1 class="ui-widget-header">
						<span class="ui-icon ui-icon-plusthick more-avaliador" title="Inserir Avaliador" onclick="openboxAvaliador('Inserir Avaliador', 'nomeBusca');"></span>
						<span>Avaliadores</span>
						<span class="ui-icon ui-icon-circle-triangle-e"></span>
						<span class="ui-icon ui-icon-circle-triangle-w"></span>
					</h1>
			  		 <div class="legend">
					  	<div style="width: 247px;">Nome</div>
					  	<div style="width: 208px;">Cargo</div>
					  	<div style="width: 229px;">Área Organizacional</div>
					  </div>
					<div class="column ui-widget-content">
					  	<#list avaliadors as avaliador>
						  	<div class="portlet avaliador_${avaliador.id}">
						  		 <div class="portlet-header">${avaliador.nome}
						  		 </div>
						  		 <div class="portlet-content">
						  		 	<ul id="${avaliador.id}">
						  		 		<#if (avaliador.avaliados.size() == 0)> 
							        		<li class="placeholder">Arraste os avaliados até aqui</li>
							        	</#if>
							        	<#list avaliador.avaliados as avaliado>
								        	<li class="avaliado_${avaliado.id}">
								        		${avaliado.nome}
								        		<input type="hidden" name="colaboradorQuestionarios[${countColaboradorQuestionarios}].id" value="${avaliado.colaboradorQuestionario.id}"/>
								        		<input type="hidden" name="colaboradorQuestionarios[${countColaboradorQuestionarios}].colaborador.id" value="${avaliado.id}"/>
								        		<input type="hidden" name="colaboradorQuestionarios[${countColaboradorQuestionarios}].avaliador.id" value="${avaliador.id}"/>
								        		<input type="hidden" name="colaboradorQuestionarios[${countColaboradorQuestionarios}].avaliacaoDesempenho.id" value="${avaliacaoDesempenho.id}"/>
								        		<#assign countColaboradorQuestionarios = countColaboradorQuestionarios + 1/>
								        	</li>
							        	</#list>
							      	</ul>
						  		 </div>
						  	</div>
					  	</#list>
				  	</div>
				</div>
				
				<script>countColaboradorQuestionarios=${countColaboradorQuestionarios};</script>
				<@ww.hidden name="avaliacaoDesempenho.id"/>
				<button type="submit" class="btnGravar"></button>
			</@ww.form>
			<div style="clear: both;"></div>
		</div>
	
	</body>
</html>
