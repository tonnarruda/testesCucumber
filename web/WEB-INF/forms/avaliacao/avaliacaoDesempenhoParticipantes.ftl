<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
	<head>
	
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
			@import url('<@ww.url value="/css/formModal.css?version=${versao}"/>');
			@import url('<@ww.url value="/css/avaliacaoDesempenhoParticipantes.css?version=${versao}"/>');
			
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
						$("#"+openboxtype+" ol").append('<li class="ui-widget-content ui-draggable" id="'+$(this).val()+'">' +
															'<input type="hidden" name="avaliados" value="'+$(this).val()+'"/>' +
												      		'<div class="nome">'+ $(this).parent().text().replace(/([0-9]*.-.)?(.*)(.\(.*)/g, '$2') +'</div>' +
												      		'<div class="faixa"></div>' +
												      		'<div class="area"></div>' +
												      		'<div style="clear:both; float: none;"></div>' +
												      	'</li>');
					} else if (openboxtype == "avaliadores") {
						createAvaliador( $(this).val(), $(this).parent().text().replace(/([0-9]*.-.)?(.*)(.\(.*)/g, '$2') );
						portletEvents();
					}
				}
			});
			conectAvaliadosAvaliadores();
			atualizeSelectables("#avaliados-list");
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
		
		<div style="width: 740px; margin: 0 auto;">
			<@ww.form name="formAvaliadores" id="formAvaliadores" action="gravaAssociacoesAvaliadoAvaliador" method="POST">
				<div id="avaliados" class="box">
				  <h1 class="ui-widget-header title">
				  	<span class="ui-icon ui-icon-plusthick more-avaliado" title="Inserir Avaliado" onclick="openboxAvaliado('Inserir Avaliado', 'nomeBusca');"></span>
				  	Avaliados
					<span class="ui-icon ui-icon-circle-triangle-e"></span>
					<span class="ui-icon ui-icon-circle-triangle-w"></span>
				  </h1>
				  <h1 class="ui-widget-header actions" style="display: none;">
				  	<div class="option remove" title="Remover selecionados">
						<span class="ui-icon ui-icon-trash"></span>
				    </div>
				  	<div class="option move-all" title="Relacionar selecionados ao avaliadores">
						<span class="ui-icon ui-icon-arrowthick-1-e"></span>
				    </div>
				    <#if avaliacaoDesempenho.permiteAutoAvaliacao>
					  	<div class="option generate-autoavaliacao" title="Gerar autoavaliação para selecionados">
							<span class="ui-icon ui-icon-refresh"></span>
					    </div>
				    </#if>
				  </h1>
				  <div class="legend">
				  	<div style="width: 247px;">Nome</div>
				  	<div style="width: 208px;">Cargo</div>
				  	<div style="width: 229px;">Área Organizacional</div>
				  </div>
				  <div class="ui-widget-content column">
				    <ol id="avaliados-list">
				      <#list participantes as avaliado>
				      	<li class="ui-widget-content" id="${avaliado.id}">
					      	<input type="hidden" name="avaliados" value="${avaliado.id}"/>
				      		<div class="nome">${avaliado.nome}</div>
				      		<div class="faixa">${avaliado.faixaSalarial.descricao}</div>
				      		<div class="area">${avaliado.areaOrganizacional.nome}</div>
				      		<div style="clear:both;float: none;"></div>
				      	</li>
				      </#list>
				    </ol>
				  </div>
				</div>
			 
				<div id="avaliadores" class="box">
					<h1 class="ui-widget-header">
						<span class="ui-icon ui-icon-plusthick more-avaliador" title="Inserir Avaliador" onclick="openboxAvaliador('Inserir Avaliador', 'nomeBusca');"></span>
						<span>Avaliadores</span>
						<span class="ui-icon ui-icon-circle-triangle-e"></span>
						<span class="ui-icon ui-icon-circle-triangle-w"></span>
					</h1>
					<h1 class="ui-widget-header actions" style="display: none;">
					  	<div class="option remove" title="Remover selecionados">
							<span class="ui-icon ui-icon-trash"></span>
					    </div>
					  	<div class="option move-all" title="Relacionar selecionados ao avaliadores">
							<span class="ui-icon ui-icon-arrowthick-1-e"></span>
					    </div>
					    <#if avaliacaoDesempenho.permiteAutoAvaliacao>
						  	<div class="option generate-autoavaliacao" title="Gerar autoavaliação para selecionados">
								<span class="ui-icon ui-icon-refresh"></span>
						    </div>
					    </#if>
					  </h1>
			  		 <div class="legend">
					  	<div style="width: 247px;">Nome</div>
					  	<div style="width: 208px;">Cargo</div>
					  	<div style="width: 229px;">Área Organizacional</div>
					  </div>
					<div class="column ui-widget-content">
					  	<#list avaliadors as avaliador>
						  	<div class="portlet" id="${avaliador.id}">
						  		 <div class="portlet-header">${avaliador.nome}
						  		 </div>
						  		 <div class="portlet-content">
						  		 	<ul>
								  		<input type="hidden" name="avaliadores" value="${avaliador.id}"/>
						  		 		<#if (avaliador.avaliados.size() == 0)> 
							        		<li class="placeholder">Arraste os avaliados até aqui</li>
							        	</#if>
							        	<#list avaliador.avaliados as avaliado>
								        	<li class="avaliado_${avaliado.id}">
								        		<#if avaliado.colaboradorQuestionario.respondida>
								        			<span class="ui-icon ui-icon-closethick disabled"></span>
								        		<#else>
								        			<span class="ui-icon ui-icon-closethick"></span>
								        		</#if>
								        		${avaliado.nome}
								        		<#if avaliado.colaboradorQuestionario.respondida>
								        			<span class="tag-info">Respondida</span>
								        		</#if>
								        		<input type="hidden" name="colaboradorQuestionarios[${countColaboradorQuestionarios}].id" value="${avaliado.colaboradorQuestionario.id}"/>
								        		<input type="hidden" name="colaboradorQuestionarios[${countColaboradorQuestionarios}].colaborador.id" value="${avaliado.id}"/>
								        		<input type="hidden" name="colaboradorQuestionarios[${countColaboradorQuestionarios}].avaliador.id" value="${avaliador.id}"/>
								        		<input type="hidden" name="colaboradorQuestionarios[${countColaboradorQuestionarios}].avaliacao.id" value="${avaliacaoDesempenho.avaliacao.id}"/>
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
				<button type="button" onclick="window.location='list.action'" class="btnVoltar"></button>
			</@ww.form>
			<div style="clear: both;"></div>
		</div>
	
	</body>
</html>
