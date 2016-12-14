<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
	<head>
		<@ww.head/>
		<title>Curso - ${cursoLnt.nomeNovoCurso}</title>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
		<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.price_format.1.6.min.js"/>"></script><!-- Usado para o function.js cssClass=hora-->
		
		<#include "../ftl/showFilterImports.ftl" />
		<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
		
		<style type="text/css">
			@import url('<@ww.url value="/css/font-awesome.min.css?version=${versao}"/>');
			@import url('<@ww.url value="/css/visualizarParticipantesCursoLnt.css?version=${versao}"/>');
			@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		</style>
		
		<script type="text/javascript">
			$(function(){
				$(".visualizar-participantes").live("click", function(){
					$(this).parents(".item").find(".participantes").toggle();
					
					$(this).parents(".item").find(".info-turma").hide();
					$(this).parent().find(".editar i").removeClass("enabled");
					
					$(this).find("i:not(.fa-users)").toggle();
				});
				
				$(".editar").live("click", function(){
					$(this).parents(".item").find(".info-turma").toggle();
					
					$(this).parents(".item").find(".participantes").hide();
					$(this).parent().find(".visualizar-participantes i.fa-caret-up").hide();
					$(this).parent().find(".visualizar-participantes i.fa-caret-down").show();
					
					$(this).find("i").toggleClass("enabled");
				});
				
				$("#turmas .participantes li").live("click", function(event){
					if ( !$(this).hasClass("placeholder") && !$(this).hasClass("has-respondida") ) {
						if ( $(this).parents(".box").find("li.ui-selected").length > 0 && $(this).parents("ul").find("li.ui-selected").length == 0 )
							$(this).parents(".box").find("li.ui-selected").removeClass("ui-selected");
						
						$(this).hasClass("ui-selected") ? $(this).removeClass("ui-selected") : $(this).addClass("ui-selected");
						
						$(this).parents(".participantes").find(".mini-actions").toggle( $(this).parents(".participantes").find("li.ui-selected").length > 0 );
					}
					
					event.stopPropagation();
				});
			});
		</script>
	</head>
	<body>
		<@ww.actionerror/>
		<@ww.actionmessage/>
				
		<div id="turmas">
			<div class="title">
				Turmas
			</div>
			<div class="style-separator"></div>
			<div class="resizable">
				<div class="itens">
					<#if turmas?exists && 0 < turmas?size >	
						<#list turmas as turma>
							<div class="item">
								<div class="cabecalho">
									${turma.descricao}
									<div class="editar" title="Informações da Turma"><i class="fa fa-eye"></i></div>
									<div class="visualizar-participantes" title="Participantes da turma">
										<i class="fa fa-users" aria-hidden="true"></i>
										<i class="fa fa-caret-down"></i>
										<i style="display: none;" class="fa fa-caret-up"></i>
									</div>
								</div>
								<div class="info-turma">
									<table style="width:100%">
										<tr>
											<td><b>Data:</b> ${turma.dataPrevIni} - ${turma.dataPrevFim}</td>
											<td></td>
										</tr> 
										<tr>
											<td>
												<b>Instrutor:</b> ${turma.instrutor} 
											</td>
											<td>
												<b>Horário:</b> ${turma.horario}</br>
											</td>
										</tr>
										<tr>
											<td>	
												<b>Instituição:</b> ${turma.instituicao}
											</td>
											<td>
												<b>Custo:</b> R$ ${turma.custoFormatado}
											</td>
										</tr>
									</table>
								    <div style="clear: both;"></div>
								</div>
								<div class="participantes">
									<ul>
										<div class="legend">
										  	<div>Nome Comercial</div>
										  	<div id="tamanhoGrande">Área Organizacional</div>
										  	<div>Estabelecimento</div>
										  	<div id="tamanhoPequeno">Empresa</div>
										</div>
										
										<#if !turma.colaboradorTurmas?exists || turma.colaboradorTurmas.size() == 0 > 
											<li class="placeholder">Não existem colaboradores nessa turma</li>
										<#else>
											<#list turma.colaboradorTurmas as colaboradorTurma>
												<li id="${colaboradorTurma.colaborador.id}">
													<span style="cursor: context-menu;" href=# onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${colaboradorTurma.colaborador.nome}');return false">${colaboradorTurma.colaborador.nomeComercial}</span>
													<span style="cursor: context-menu;" id="tamanhoGrande" href=# onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${colaboradorTurma.colaborador.areaOrganizacional.nomeComHierarquia}');return false">${colaboradorTurma.colaborador.areaOrganizacional.areaNomeTruncado}</span>
													<span>${colaboradorTurma.colaborador.estabelecimentoNome}</span>
													<span id="tamanhoPequeno">${colaboradorTurma.colaborador.empresaNome}</span>
												</li>
											</#list>
										</#if>
									</ul>
								</div>
							</div>
						</#list>
					<#else>
						</br>
						<div class="info">
							<ul>
								<li>Não existem participantes inseridos nas turmas deste curso.</li>
							</ul>
						</div>
					</#if>
				</div>
			</div>
		</div>
		
		<button onclick="window.location.href='gerarCursosETurmas.action?lnt.id=${lnt.id}'" id="btVoltar" type="button">Voltar</button>
	</body>
</html>
