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
		    .info { width: 670px; margin: 0 auto; }
		    .info li { color: #00529B !important; }
	  	</style>
	  	
		<@ww.head/>
		<title>Competências - ${avaliacaoDesempenho.titulo}</title>
		
		<#assign countConfiguracaoCompetencia=0 />
		<#assign gerarAutoAvaliacoesEmLoteAction="gerarAutoAvaliacoesEmLote.action"/>
		
		<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formModal.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/avaliacaoDesempenhoCompetencias.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
		
		<script type="text/javascript">
			var permiteAutoAvaliacao = ${avaliacaoDesempenho.permiteAutoAvaliacao.toString()};
			var avaliacaoDesempenhoId = ${avaliacaoDesempenho.id};
			var avaliacaoLiberada = ${avaliacaoDesempenho.liberada?string};
			
			$(function(){
				if (!avaliacaoLiberada) {
					conectCompetenciasAvaliadores();
				
			    	atualizeSelectables("#competencias-list ul", "li", "competencias");
			    	atualizeSelectablesMini();
			    } else {
			    	$("li, .portlet-header").css("color", "#A1A1A1");
			    }
		    });
		</script>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		
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
			<@ww.form name="formCompetencias" id="formCompetencias" action="saveCompetencias" method="POST">
				<div id="competencias" class="box">
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
				    <ol id="competencias-list">
				    	<#list faixaSalariais as faixa>
				    		<li>
				    			<div class="faixa-descricao">
					    			<i class="fa fa-caret-up" style="display: none;"></i>
					    			<i class="fa fa-caret-down"></i>
						      		<strong>${faixa.descricao}</strong>
					      		</div>
					      		<ul class="faixa_${faixa.id}" style="display: none;">
					      			<#list faixa.configuracaoNivelCompetencias as cnc>
								      	<li class="ui-widget-content" id="${cnc.competenciaId}" tipo="${cnc.tipoCompetencia}">
									      	<input type="hidden" name="competenciaId" value="${cnc.competenciaId}"/>
									      	<input type="hidden" name="competenciaTipo" value="${cnc.tipoCompetencia}"/>
									      	<input type="hidden" name="configuracaoNivelCompetenciaFaixaSalarial" value="${cnc.configuracaoNivelCompetenciaFaixaSalarial.id}"/>
									      	<input type="hidden" name="configuracaoNivelCompetenciaFaixaSalarial.faixaSalarial" value="${faixa.id}"/>
									      	<input type="hidden" name="configuracaoNivelCompetenciaFaixaSalarial.faixaSalarial.descricao" value="${faixa.descricao}"/>
									      	<input type="hidden" name="competenciaDescricao" value="${cnc.competenciaDescricao}"/>
								      		<div class="nome">${cnc.competenciaDescricao}</div>
								      		<div style="clear:both;float: none;"></div>
								      	</li>
							      	</#list>
						      	</ul>
					      	</li>
				    	</#list>
				    </ol>
				  </div>
				</div>
			 
				<div id="avaliadores" class="box">
					<h1 class="ui-widget-header title">
						<span>Avaliadores</span>
						<span class="ui-icon ui-icon-circle-triangle-e show-info"></span>
						<span class="ui-icon ui-icon-circle-triangle-w hide-info"></span>
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
						      		<div style="clear:both;float:none;"></div>
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
						  		 		<#if (avaliador.faixaSalariaisAvaliados.size() == 0)> 
							        		<li class="placeholder">Não existem avaliados configurados para esse avaliador</li>
							        	</#if>
							        	<#list avaliador.faixaSalariaisAvaliados as faixa>
								        	<li class="faixa_${faixa.id}">
							        			<div style="font-size: 11px;" class="faixa-descricao">
									        		<i class="fa fa-caret-up" style="display: none;"></i>
						    						<i class="fa fa-caret-down"></i>
										      		<strong>${faixa.descricao}</strong>
									      		</div>
									      		<ul class="competencias">
									      			<#if (faixa.configuracaoCompetenciaAvaliacaoDesempenhos.size() == 0) >
										      			<#if avaliacaoDesempenho.liberada >
											      			<li class="placeholder">Não existem competências configuradas</li>
											      		<#else>
											      			<li class="placeholder">Arraste os competências até aqui</li>
										      			</#if>
											      	</#if>
											      	<#list faixa.configuracaoCompetenciaAvaliacaoDesempenhos as ccad>
											      		<li class="competencia_${ccad.competenciaId}_${ccad.tipoCompetencia}">
											      			${ccad.competenciaDescricao}
					        								<#if ccad.possuiResposta>
								        						<i class="fa fa-check possuiResposta" title="Possui Resposta"></i>
								        					</#if>
												      		<input type="hidden" name="configuracaoCompetenciaAvaliacaoDesempenhos[${countConfiguracaoCompetencia}].competenciaId" value="${ccad.competenciaId}"/>
												      		<input type="hidden" name="configuracaoCompetenciaAvaliacaoDesempenhos[${countConfiguracaoCompetencia}].competenciaDescricao" value="${ccad.competenciaDescricao}"/>
	    													<input type="hidden" name="configuracaoCompetenciaAvaliacaoDesempenhos[${countConfiguracaoCompetencia}].tipoCompetencia" value="${ccad.tipoCompetencia}"/>
					        								<input type="hidden" name="configuracaoCompetenciaAvaliacaoDesempenhos[${countConfiguracaoCompetencia}].avaliador.id" value="${avaliador.id}"/>
					        								<input type="hidden" name="configuracaoCompetenciaAvaliacaoDesempenhos[${countConfiguracaoCompetencia}].avaliador.nome" value="${avaliador.nome}"/>
					        								<input type="hidden" name="configuracaoCompetenciaAvaliacaoDesempenhos[${countConfiguracaoCompetencia}].configuracaoNivelCompetenciaFaixaSalarial.id" value="${ccad.configuracaoNivelCompetenciaFaixaSalarial.id}"/>
					        								<input type="hidden" name="configuracaoCompetenciaAvaliacaoDesempenhos[${countConfiguracaoCompetencia}].configuracaoNivelCompetenciaFaixaSalarial.faixaSalarial.id" value="${faixa.id}"/>
					        								<input type="hidden" name="configuracaoCompetenciaAvaliacaoDesempenhos[${countConfiguracaoCompetencia}].configuracaoNivelCompetenciaFaixaSalarial.faixaSalarial.descricao" value="${faixa.descricao}"/>
					        								<input type="hidden" name="configuracaoCompetenciaAvaliacaoDesempenhos[${countConfiguracaoCompetencia}].avaliacaoDesempenho.id" value="${avaliacaoDesempenho.id}"/>
    													</li>
    													<#assign countConfiguracaoCompetencia = countConfiguracaoCompetencia + 1/>
											      	</#list>
										      	</ul>
									      	</li>
								      	</#list>
							      	</ul>
						  		 </div>
						  	</div>
					  	</#list>
				  	</div>
				</div>
				
				<script>countConfiguracaoCompetencia=${countConfiguracaoCompetencia};</script>
				<@ww.hidden name="avaliacaoDesempenho.id"/>
				
				<#if avaliacaoDesempenho.liberada>
					<button type="button" class="btnGravarDesabilitado"></button>
				<#else>
					<button type="submit" class="btnGravar"></button>
				</#if>
				<button type="button" onclick="window.location='list.action'" class="btnVoltar"></button>
			</@ww.form>
			<div style="clear: both;"></div>
		</div>
		
		<div id="black-back" style="display:none; /*display: block;*/ top: 0; left: 0; bottom: 0; background: gray; opacity: 0.6; width: 100%; position: absolute; z-index: 1000;"></div>
	</body>
</html>
