<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
	<head>
	
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
			@import url('<@ww.url value="/css/formModal.css?version=${versao}"/>');
			@import url('<@ww.url value="/css/avaliacaoDesempenhoParticipantes.css?version=${versao}"/>');
			@import url('<@ww.url value="/css/font-awesome.min.css?version=${versao}"/>');
			
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
		<#assign countParticipantesAvaliados=0 />
		<#assign countParticipantesAvaliadores=0 />
		<#assign gerarAutoAvaliacoesEmLoteAction="gerarAutoAvaliacoesEmLote.action"/>
		
		<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formModal.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/avaliacaoDesempenhoParticipantes.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
		
		<script type="text/javascript">
		var permiteAutoAvaliacao = ${avaliacaoDesempenho.permiteAutoAvaliacao.toString()};
		var avaliacaoDesempenhoId = ${avaliacaoDesempenho.id};
		var avaliacaoId = ${avaliacaoDesempenho.avaliacao.id};
		var avaliacaoLiberada = ${avaliacaoDesempenho.liberada?string};
		
		$(function() {
			$('#tooltipHelp').qtip({
				content: 'Gera uma nova avaliação para cada um dos colaboradores desta avaliação, na qual ele irá avaliar apenas a si próprio.'
			});
			
			if (!avaliacaoLiberada) {
				conectAvaliadosAvaliadores();
			
		    	atualizeSelectables("#avaliados-list", "li", "avaliados");
				atualizeSelectables("#avaliadores-list", ".portlet", "avaliadores");
				atualizeSelectablesMini();
		    } else {
		    	$("li, .portlet-header").css("color", "#A1A1A1");
		    }
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
		
		var openboxtype = "";
		function openboxAvaliado() {
			openboxtype = "avaliados";
		  	openbox('Inserir Avaliado', 'nomeBusca');
		}
		  
		function openboxAvaliador() {
			openboxtype = "avaliadores";
		  	openbox('Inserir Avaliador', 'nomeBusca');
		}
		
		function populeList() {
			$("input[name=colaboradorsCheck]:checked").each(function(){
				if ( $("#"+openboxtype+" #"+$(this).val()).length == 0 ) {
					if(openboxtype == "avaliados") {
						$("#"+openboxtype+" ol").append('<li class="ui-widget-content ui-draggable ui-selectable" id="'+$(this).val()+'">' +
													      	'<input type="hidden" name="participantesAvaliados['+countParticipantesAvaliados+'].colaborador.id" value="'+$(this).val()+'"/>' +
													      	'<input type="hidden" name="participantesAvaliados['+countParticipantesAvaliados+'].avaliacaoDesempenho.id" value="'+avaliacaoDesempenhoId+'"/>' +
													      	'<input type="hidden" name="participantesAvaliados['+countParticipantesAvaliados+'].tipo" value="A"/>' +
													      	'<input type="text" name="participantesAvaliados['+countParticipantesAvaliados+'].produtividade" class="notaProdutividade" />' +
												      		'<div class="nome">'+ $(this).parent().text().replace(/([0-9]*.-.)?(.*)(.\(.*)/g, '$2') +'</div>' +
												      		'<div class="faixa"></div>' +
												      		'<div class="area"></div>' +
												      		'<div style="clear:both; float: none;"></div>' +
												      	'</li>');
						countParticipantesAvaliados++;
					} else if (openboxtype == "avaliadores") {
						createAvaliador( $(this).val(), $(this).parent().text().replace(/([0-9]*.-.)?(.*)(.\(.*)/g, '$2') );
						portletEvents();
					}
				}
			});
			conectAvaliadosAvaliadores();
		}
		
		function validFormModal() {
			var validForm = validaFormulario('formModal', new Array('@colaboradorsCheck'), null, true);
			if ( validForm ) {
				populeList();
				$("#listCheckBoxcolaboradorsCheck").html("");
				openboxtype = "";
				closebox();
			}
		};
		
		function validForm() {
			var validForm = true;
			$(".pesoAvaliador").each(function(){
				if( $(this).val() == "" ) {
					$(this).css("background", "#FFEEC2");
					validForm = false;
				} else {
					$(this).css("background", "");
				}
			});
			
			$(".pesoAvaliador").toggle(!validForm);
			$(".portlet-toggle").toggle(validForm);
			if(validForm)
				$("#formParticipantes").submit();
			else {
				jAlert("Preencha os campos indicados.");
			}
		};
		
		</script>
	</head>
	<body>
		<@ww.actionerror />
		
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
				<button onclick="validFormModal();" class="btnGravar"></button>
				<button onclick="closebox();" class="btnCancelar"></button>
			</div>
		</div>
		
		<div style="width: 760px; margin: 0 auto;">
			<@ww.form name="formParticipantes" id="formParticipantes" action="saveParticipantes" method="POST">
				<div id="avaliados" class="box">
				  <h1 class="ui-widget-header title">
				  	<span class="ui-icon ui-icon-plusthick more-avaliado" title="Inserir Avaliado" onclick="openboxAvaliado('Inserir Avaliado', 'nomeBusca');"></span>
				  	Avaliados
					<span class="ui-icon ui-icon-circle-triangle-e show-info"></span>
					<span class="ui-icon ui-icon-circle-triangle-w hide-info"></span>
				  </h1>
				  <h1 class="ui-widget-header actions">
				  	<div class="option remove only-selectables disabled" title="Remover selecionados">
						<span class="ui-icon ui-icon-trash"></span>
				    </div>
				  	<div class="option move-all only-selectables disabled" title="Relacionar selecionados ao avaliadores">
						<i class="fa fa-users"></i>
						<i class="fa fa-long-arrow-right"></i>
				    </div>
				    <div class="option select-all" title="Selecionar todos">
						<i class="fa fa-check"></i>
						<i class="fa fa-align-justify"></i>
				    </div>
				    <div class="option unselect-all" title="Retirar selecão de todos">
						<i class="fa fa-close"></i>
						<i class="fa fa-align-justify"></i>
				    </div>
				    <div class="option produtividade" title="Nota de produtividade">
						<i class="fa fa-line-chart"></i>
				    </div>
				    <#if avaliacaoDesempenho.permiteAutoAvaliacao>
					  	<div class="option generate-autoavaliacao only-selectables disabled" title="Gerar autoavaliação para selecionados">
							<span class="ui-icon ui-icon-refresh"></span>
					    </div>
				    </#if>
				  </h1>
				  <div class="ui-widget-content column">
				    <div class="legend">
				  	  <div>Nome</div>
				  	  <div>Cargo</div>
				  	  <div>Área Organizacional</div>
				    </div>
				    <ol id="avaliados-list">
				      <#list participantesAvaliados as avaliado>
				      	<li class="ui-widget-content" id="${avaliado.colaborador.id}">
					      	<input type="hidden" name="participantesAvaliados[${countParticipantesAvaliados}].id" value="${avaliado.id}"/>
					      	<input type="hidden" name="participantesAvaliados[${countParticipantesAvaliados}].colaborador.id" value="${avaliado.colaborador.id}"/>
					      	<input type="hidden" name="participantesAvaliados[${countParticipantesAvaliados}].avaliacaoDesempenho.id" value="${avaliado.avaliacaoDesempenho.id}"/>
					      	<input type="hidden" name="participantesAvaliados[${countParticipantesAvaliados}].tipo" value="${avaliado.tipo}"/>
					      	<input type="text" name="participantesAvaliados[${countParticipantesAvaliados}].produtividade" class="notaProdutividade" value="${avaliado.produtividadeString}" />
					      	
				      		<div class="nome">${avaliado.colaborador.nome}</div>
				      		<div class="faixa show-when-expand">${avaliado.colaborador.faixaSalarial.descricao}</div>
				      		<div class="area show-when-expand">${avaliado.colaborador.areaOrganizacional.nome}</div>
				      		<div style="clear:both;float: none;"></div>
				      	</li>
				      	
				      	<#assign countParticipantesAvaliados = countParticipantesAvaliados + 1/>
				      </#list>
				    </ol>
				  </div>
				</div>
			 
				<div id="avaliadores" class="box">
					<h1 class="ui-widget-header title">
						<span class="ui-icon ui-icon-plusthick more-avaliador" title="Inserir Avaliador" onclick="openboxAvaliador('Inserir Avaliador', 'nomeBusca');"></span>
						<span>Avaliadores</span>
						<span class="ui-icon ui-icon-circle-triangle-e show-info"></span>
						<span class="ui-icon ui-icon-circle-triangle-w hide-info"></span>
					</h1>
					<h1 class="ui-widget-header actions">
					  	<div class="option remove only-selectables disabled" title="Remover selecionados">
							<span class="ui-icon ui-icon-trash"></span>
					    </div>
					    <div class="option select-all" title="Selecionar todos">
							<i class="fa fa-check"></i>
							<i class="fa fa-align-justify"></i>
					    </div>
					    <div class="option unselect-all" title="Retirar selecão de todos">
							<i class="fa fa-close"></i>
							<i class="fa fa-align-justify"></i>
					    </div>
					    <div class="option configure-pesos" title="Configurar pesos">
							<i class="fa fa-balance-scale"></i>
					    </div>
					</h1>
					<div class="column ui-widget-content" id="avaliadores-list">
				  		<div class="legend">
						  	<div>Nome</div>
						  	<div>Cargo</div>
						  	<div>Área Organizacional</div>
						</div>
						
					  	<#list participantesAvaliadores as avaliador>
						  	<div class="portlet" id="${avaliador.colaborador.id}">
						  		<input type="hidden" name="participantesAvaliadores[${countParticipantesAvaliadores}].id" value="${avaliador.id}"/>
						      	<input type="hidden" name="participantesAvaliadores[${countParticipantesAvaliadores}].colaborador.id" value="${avaliador.colaborador.id}"/>
						      	<input type="hidden" name="participantesAvaliadores[${countParticipantesAvaliadores}].avaliacaoDesempenho.id" value="${avaliador.avaliacaoDesempenho.id}"/>
						      	<input type="hidden" name="participantesAvaliadores[${countParticipantesAvaliadores}].tipo" value="${avaliador.tipo}"/>
					      	
						  		 <div class="portlet-header">
						  		 	<input type="text" class="pesoAvaliador" value="" <#if avaliacaoDesempenho.liberada>disabled="disabled"</#if> />
						  		 	<div class="nome">${avaliador.colaborador.nome}</div>
						  		 	<div class="faixa show-when-expand">${avaliador.colaborador.faixaSalarial.descricao}</div>
						      		<div class="area show-when-expand">${avaliador.colaborador.areaOrganizacional.nome}</div>
						      		<div style="clear:both;float: none;"></div>
						  		 </div>
						  		 <div class="portlet-header mini-actions" style="background: #F3F3F3; padding: 0; display: none;">
						  		 	<div class="mini-option remove only-selectables disabled" title="Remover selecionados" style="padding: 3px 15px; float: left;">
										<span class="ui-icon ui-icon-trash" style="float: none;"></span>
								    </div>
								    <div class="mini-option select-all" title="Selecionar todos" style="padding: 2px 15px; float: left;" >
										<i class="fa fa-check"></i>
										<i class="fa fa-align-justify"></i>
								    </div>
								    <div class="mini-option unselect-all" title="Retirar selecão de todos" style="padding: 2px 15px; float: left;">
										<i class="fa fa-close"></i>
										<i class="fa fa-align-justify"></i>
								    </div>
								    <div style="clear: both;"></div>
								 </div>
						  		 <div class="portlet-content hide-when-expand">
						  		 	<ul id="${avaliador.colaborador.id}">
						  		 		<#if (avaliador.colaborador.avaliados.size() == 0)> 
							  		 		<#if avaliacaoDesempenho.liberada > 
								        		<li class="placeholder">Não avaliados configurados</li>
								        	<#else>
								        		<li class="placeholder">Arraste os avaliados até aqui</li>
								        	</#if>
							        	</#if>
							        	<#list avaliador.colaborador.avaliados as avaliado>
								        	<li class="avaliado_${avaliado.id}">
								        		${avaliado.nome}
								        		<input type="hidden" name="colaboradorQuestionarios[${countColaboradorQuestionarios}].id" value="${avaliado.colaboradorQuestionario.id}"/>
								        		<#if avaliado.id == avaliador.colaborador.id >
									        		<input type="text" name="colaboradorQuestionarios[${countColaboradorQuestionarios}].pesoAvaliador" class="pesoAvaliador" value="${avaliado.colaboradorQuestionario.pesoAvaliador?string}" <#if avaliacaoDesempenho.liberada>disabled="disabled"</#if> />
								        		<#else>
									        		<input type="hidden" name="colaboradorQuestionarios[${countColaboradorQuestionarios}].pesoAvaliador" class="peso" value="${avaliado.colaboradorQuestionario.pesoAvaliador?string}"/>
								        		</#if>
								        		<input type="hidden" name="colaboradorQuestionarios[${countColaboradorQuestionarios}].colaborador.id" value="${avaliado.id}"/>
								        		<input type="hidden" name="colaboradorQuestionarios[${countColaboradorQuestionarios}].avaliador.id" value="${avaliador.colaborador.id}"/>
								        		<input type="hidden" name="colaboradorQuestionarios[${countColaboradorQuestionarios}].avaliacao.id" value="${avaliacaoDesempenho.avaliacao.id}"/>
								        		<input type="hidden" name="colaboradorQuestionarios[${countColaboradorQuestionarios}].avaliacaoDesempenho.id" value="${avaliacaoDesempenho.id}"/>
								        		<#if avaliado.colaboradorQuestionario.respondida>
								        			<i class="fa fa-check respondida" title="Respondida"></i>
								        		</#if>
								        		<#assign countColaboradorQuestionarios = countColaboradorQuestionarios + 1/>
								        	</li>
							        	</#list>
							      	</ul>
						  		 </div>
						  	</div>
						  	
						  	<#assign countParticipantesAvaliadores = countParticipantesAvaliadores + 1/>
					  	</#list>
				  	</div>
				</div>
				
				<script>countColaboradorQuestionarios=${countColaboradorQuestionarios};</script>
				<script>countParticipantesAvaliados=${countParticipantesAvaliados};</script>
				<script>countParticipantesAvaliadores=${countParticipantesAvaliadores};</script>
				<@ww.hidden name="avaliacaoDesempenho.id"/>
				
				<button type="button" onclick="validForm();" class="btnGravar"></button>
				<button type="button" onclick="window.location='list.action'" class="btnVoltar"></button>
			</@ww.form>
			<div style="clear: both;"></div>
		</div>
		
		<div id="black-back" style="display:none; /*display: block;*/ top: 0; left: 0; bottom: 0; background: gray; opacity: 0.6; width: 100%; position: absolute; z-index: 1000;"></div>
	</body>
</html>
