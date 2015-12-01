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
		<title>Competências - ${avaliacaoDesempenho.titulo}</title>
		
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
			<@ww.form name="formParticipantes" id="formParticipantes" action="saveParticipantes" method="POST">
				<div id="avaliados" class="box">
				  <h1 class="ui-widget-header title">
				  	Competências
				  </h1>
				  <h1 class="ui-widget-header actions">
				  	<div class="option move-all only-selectables disabled" title="Relacionar selecionados aos avaliadores">
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
				  </h1>
				  <div class="ui-widget-content column">
				    <ol id="avaliados-list">
				      	<li>
				      		<strong>Faixa 1</strong>
				      		<ul>
						      	<li class="ui-widget-content" id="1">
							      	<input type="hidden" name="competenciaId" value="1"/>
						      		<div class="nome">PROATIVIDADE</div>
						      		<div style="clear:both;float: none;"></div>
						      	</li>
					      	</ul>
				      	</li>
				    </ol>
				  </div>
				</div>
			 
				<div id="avaliadores" class="box">
					<h1 class="ui-widget-header title">
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
					</h1>
					<div class="column ui-widget-content" id="avaliadores-list">
				  		<div class="legend">
						  	<div>Nome</div>
						  	<div>Cargo</div>
						  	<div>Área Organizacional</div>
						</div>
					  	<#list avaliadors as avaliador>
						  	<div class="portlet" id="${avaliador.id}">
						  		 <div class="portlet-header">
						  		 	<div class="nome">${avaliador.nome}</div>
						  		 	<div class="faixa show-when-expand">${avaliador.faixaSalarial.descricao}</div>
						      		<div class="area show-when-expand">${avaliador.areaOrganizacional.nome}</div>
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
						  		 	<ul>
								  		<input type="hidden" name="avaliadores" value="${avaliador.id}"/>
						  		 		<#if (avaliador.avaliados.size() == 0)> 
							        		<li class="placeholder">Arraste os avaliados até aqui</li>
							        	</#if>
							        	<li>
								      		<strong>Faixa 1</strong>
								      		<ul>
										      	<li class="ui-widget-content" id="1">
											      	<li class="placeholder">Arraste os avaliados até aqui</li>
										      	</li>
									      	</ul>
								      	</li>
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
		
		<div id="black-back" style="display:none; /*display: block;*/ top: 0; left: 0; bottom: 0; background: gray; opacity: 0.6; width: 100%; position: absolute; z-index: 1000;"></div>
	</body>
</html>
