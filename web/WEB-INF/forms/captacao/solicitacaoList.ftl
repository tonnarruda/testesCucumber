<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Solicitação de Pessoal</title>
	
	<#include "../ftl/mascarasImports.ftl" />
	<#assign validarCamposSuspende = "return validaFormulario('formSuspendeSolicitacao', new Array('obsSuspensao'), null)"/>
	<#assign validarCamposEncerra = "return validaFormulario('formEncerraSolicitacao', new Array('dataEncerramento'), new Array('dataEncerramento'))"/>
	<#assign validarCamposUpdateStatus = "return validaFormulario('formUpdateStatusSolicitacao', new Array(), new Array('dataStatus'));"/>
	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');

		.alterarStatusDiv {	display: none;}
		.calendar { z-index: 99998 !important; }
		#dataEncerramento{width: 80px;}
		#formDialog { display: none; }
		#suspendeDiv { display: none; }
	</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/SolicitacaoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
	<script type='text/javascript'>
		$(function() {
			var obj = document.getElementById("legendas");
			obj.innerHTML += "&nbsp;&nbsp;<span style='background-color: #009900;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Abertas em andamento";
			obj.innerHTML += "&nbsp;&nbsp;<span style='background-color: #002EB8;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Abertas suspensas";
			obj.innerHTML += "&nbsp;&nbsp;<span style='background-color: #555;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Encerradas";
		});

		function encerraSolicitacao(solicitacaoId, observacaoLiberador)
		{
			$('#obsAprova').val(observacaoLiberador.split('$#-').join("\""));
			$('#solicitacaoIdEncerrar').val(solicitacaoId);
			$('#formDialog').dialog({ modal: true, width: 470 });
		}

		function suspenderSolicitacao(solicitacaoId)
		{
			$('#solicitacaoIdSuspender').val(solicitacaoId);
			$('#suspendeDiv').dialog({ modal: true, width: 470 });
		}

		function setPage()
		{
			document.getElementById("pagina").value=1;
		}

		function setDateStatus(dataStatus)
		{
			if(dataStatus)
				$('#dataStatus').val(dataStatus);
			else
				$('#dataStatus').val($.datepicker.formatDate('dd/mm/yy',new Date()));
		}
		
		function aprovarSolicitacao(solicitacaoId, statusAnterior, dataStatusAnterior) 
		{
			$('#solicitacaoIdAlterarStatus').val(solicitacaoId);
			$('#statusSolicitacaoAnterior').val(statusAnterior);
			$('#dataStatusSolicitacaoAnterior').val(dataStatusAnterior);
		
			$('#alterarStatusDiv').dialog({ modal: true, 
											width: 500,
											height: 300,
											title: 'Alterar status',
										    open: function(event, ui) 
											{ 
												if (solicitacaoId != '')
												{
													SolicitacaoDWR.getObsSolicitacao(solicitacaoId, function(data){
														$('#obsAprova').val(data.obs);
														$('#observacaoLiberador').val(data.obs);
														$('#statusSolicitcao').val(data.status);
														setDateStatus(data.dataStatus);
													});
												}
											}
										}); 
		}
	</script>
</head>

<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="form" action="list.action" validate="true" onsubmit="setPage();" method="POST" id="formBusca">
			<@ww.select id="visualizacao" label="Visualizar" name="visualizar" list=r"#{'T':'Todas','A':'Abertas em andamento','S':'Abertas suspensas','E':'Encerradas'}" />
			<@ww.select id="status" label="Status" name="statusBusca" list="status" headerValue="Todos" headerKey="T" />
			<@ww.textfield label="Descrição" name="descricaoBusca" id="descricaoBusca"  cssStyle="width: 229px;"/>
			<@ww.select id="cargo" label="Cargo" name="cargo.id" list="cargos" listKey="id" listValue="nome" headerValue="Todos" headerKey="-1" /><br>
			<@ww.hidden id="pagina" name="page"/>

			<input type="submit" value="" class="btnPesquisar grayBGE" accesskey="B">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br />

	<div id="legendas" align="right"></div>
	<br />

	<@display.table name="solicitacaos" id="solicitacao" class="dados">
		
		<#if solicitacao.id?exists>
			<#assign solicitacaoId="${solicitacao.id}"/>					
		<#else>
			<#assign solicitacaoId=""/>					
		</#if>
		
		<#if solicitacao.observacaoLiberador?exists>
			<#assign observacaoLiberador="${solicitacao.observacaoLiberador}"/>					
		<#else>
			<#assign observacaoLiberador=""/>					
		</#if>
		
		<#if solicitacao.obsSuspensao?exists>
			<#assign obsSuspensao="${solicitacao.obsSuspensao}"/>					
		<#else>
			<#assign obsSuspensao=""/>					
		</#if>
		
		<#if solicitacao.encerrada>
			<#assign classe=""/>
			<#assign fraseConfirma="Deseja reabrir esta solicitação?"/>
			<#assign titleEncerra="Reabrir Solicitação.\nData Encerramento: ${solicitacao.dataEncerramentoFormatada}\nObservação: ${observacaoLiberador}\n"/>
			<#assign imgEncerra="flag_green.gif"/>
			<#assign actionEncerra="reabrirSolicitacao.action"/>
		<#else>
			<#assign classe="solicitacaoEncerrada"/>
			<#if solicitacao.suspensa>
				<#assign classe="solicitacaoSuspensa"/>
			</#if>
			<#assign fraseConfirma="Deseja encerrar esta solicitação?"/>
			<#assign titleEncerra="Encerrar Solicitação"/>
			<#assign imgEncerra="flag_red.gif"/>
			<#assign actionEncerra="encerrarSolicitacao.action"/>
		</#if>
		<@display.column title="Ações" media="html" class="acao" style="width: 170px; text-align:left;">
			<@frt.link verifyRole="ROLE_MOV_SOLICITACAO_IMPRIMIR" href="imprimirSolicitacaoPessoal.action?solicitacao.id=${solicitacao.id}" imgTitle="Imprimir" imgName="printer.gif"/>
			<@frt.link verifyRole="ROLE_MOV_SOLICITACAO_EDITAR" href="prepareUpdate.action?solicitacao.id=${solicitacao.id}" imgTitleDisabled="Não é possível editar a solicitação. Esta já foi aprovada ou encerrada." imgTitle="Editar" imgName="edit.gif" opacity=solicitacao.encerrada disabled=solicitacao.encerrada/>
			<@frt.link verifyRole="ROLE_MOV_SOLICITACAO_EXCLUIR" href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?solicitacao.id=${solicitacao.id}'});" imgTitleDisabled="Não é possível excluir a solicitação. Esta já foi aprovada ou encerrada." imgTitle="Excluir" imgName="delete.gif" opacity=solicitacao.encerrada disabled=solicitacao.encerrada/>
			
			<#if solicitacao.anuncio?exists && solicitacao.anuncio.exibirModuloExterno>
				<#assign titleAnuncio = "Anunciado"/>
				<#assign imgAnuncio = "cliper_checked.gif"/>
			<#else>
				<#assign titleAnuncio = "Anunciar"/>
				<#assign imgAnuncio = "cliper.gif"/>
			</#if>
			
			<@frt.link verifyRole="ROLE_MOV_SOLICITACAO_ANUNCIAR" href="../anuncio/anunciar.action?solicitacao.id=${solicitacao.id}" imgTitleDisabled="Não é possível anunciar a solicitação. Esta já foi encerrada." imgTitle="${titleAnuncio}" imgName="${imgAnuncio}" opacity=solicitacao.encerrada disabled=solicitacao.encerrada/>

			<#if !solicitacao.encerrada>
			 	<#assign onclickEcerrar = "encerraSolicitacao('${solicitacao.id}', '${observacaoLiberador?js_string?replace('\"', '$#-')}');"/>
			<#else>
			 	<#assign onclickEcerrar = "newConfirm('${fraseConfirma}', function(){window.location='${actionEncerra}?solicitacao.id=${solicitacao.id}'});"/>
			</#if>
			
			<@frt.link verifyRole="ROLE_MOV_SOLICITACAO_ENCERRAR" href="#" onclick="${onclickEcerrar}" imgTitle="${titleEncerra}" imgName="${imgEncerra}"/>
				
			<#if !solicitacao.suspensa>
				<#assign hrefSuspender = "#"/>
				<#assign onclickSuspender = "suspenderSolicitacao('${solicitacao.id}');"/>
				<#assign titelSuspender = "Suspender solicitação"/>
				<#assign imgSuspender = "control_pause.gif"/>
			<#else>
				<#assign hrefSuspender = "liberarSolicitacao.action?solicitacao.id=${solicitacao.id}"/>
				<#assign onclickSuspender = ""/>
				<#assign titelSuspender = "Liberar solicitação. Observação: ${obsSuspensao}"/>
				<#assign imgSuspender = "control_play.gif"/>
			</#if>
		
			<@frt.link verifyRole="ROLE_MOV_SOLICITACAO_SUSPENDER" href="${hrefSuspender}" onclick="${onclickSuspender}" imgTitleDisabled="Não é possível suspender ou liberar a solicitação. Esta já foi encerrada." imgTitle="${titelSuspender}" imgName="${imgSuspender}" opacity=solicitacao.encerrada disabled=solicitacao.encerrada/>
		
			<@frt.link verifyRole="ROLE_MOV_SOLICITACAO_CLONAR" href="prepareClonar.action?solicitacao.id=${solicitacao.id}" imgTitle="Clonar" imgName="clonar.gif"/>
			
			<@frt.link verifyRole="ROLE_MOV_SOLICITACAO_CANDIDATO" href="../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}" imgTitle="Candidatos da Seleção" imgName="usuarios.gif"/>
		
			<#if solicitacao.status == 'A'>
				<#assign imgStatus = "status_green.png"/>
			<#elseif solicitacao.status == 'R'>
				<#assign imgStatus = "status_red.png"/>
			<#else>
				<#assign imgStatus = "status_yellow.png"/>
			</#if>
			
			<#if solicitacao.dataStatus?exists>
				<#assign dtStatus = "${solicitacao.dataStatus}"/>
			<#else>
				<#assign dtStatus = ""/>
			</#if>
			
			<@frt.link verifyRole="ROLE_LIBERA_SOLICITACAO" href="javascript:;" onclick="javascript:aprovarSolicitacao(${solicitacao.id},'${solicitacao.status}','${dtStatus}')" imgTitle="Alterar status (${solicitacao.statusFormatado})" imgName="${imgStatus}"/>
			
		</@display.column>
	
		<@display.column title="Código" property="id" class="${classe}"/>
		<@display.column title="Cargo" class="${classe}">
			<#if solicitacao.obsSuspensao?exists && solicitacao.obsSuspensao?trim != "">
				<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${solicitacao.obsSuspensao?j_string}');return false">${solicitacao.faixaSalarial.cargo.nome}</span>
			<#else>
				<#if solicitacao.faixaSalarial.cargo.nome?exists>
					${solicitacao.faixaSalarial.cargo.nome}
				</#if>
			</#if>
		</@display.column>
		<@display.column title="Descrição" property="descricao" class="${classe}"/>
		<@display.column property="motivoSolicitacao.descricao" title="Motivo" class="${classe}"/>
		<@display.column property="estabelecimento.nome" title="Estabelecimento" class="${classe}"/>
		<@display.column property="areaOrganizacional.nome" title="Área" class="${classe}"/>
		<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width:70px;" class="${classe}"/>

		<@display.column title="Status" style="width:33px;" class="${classe}">
			${solicitacao.statusFormatadoComData}
		</@display.column>

	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="formBusca"/>

	<div id="formDialog" title="Encerrar Solicitação">
		<@ww.form name="formEncerraSolicitacao" id="formEncerraSolicitacao" action="encerrarSolicitacao.action" validate="true" method="POST" onsubmit="${validarCamposEncerra}" >
			<@ww.datepicker label="Data de Encerramento" name="dataEncerramento" id="dataEncerramento" cssClass="mascaraData" />
			<@ww.textarea label="Observação" name="solicitacao.observacaoLiberador" id="obsAprova"/>
			<@ww.hidden name="solicitacao.id" id="solicitacaoIdEncerrar" value="${solicitacaoId}"/>
		</@ww.form>
		<button onclick="${validarCamposEncerra};" class="btnEncerrarSolicitacao grayBG"></button>
		<button onclick="window.location='list.action'" class="btnCancelar grayBG">	</button>
	</div>
	<div id="suspendeDiv" title="Suspender Solicitação">
		<@ww.form name="formSuspendeSolicitacao" id="formSuspendeSolicitacao" action="suspenderSolicitacao.action" validate="true" method="POST" onsubmit="${validarCamposSuspende}">
			<@ww.textarea label="Observações sobre a suspensão" name="solicitacao.obsSuspensao" id="obsSuspensao" />
			<@ww.hidden name="solicitacao.id" id="solicitacaoIdSuspender" value="${solicitacaoId}"/>
		</@ww.form>
		<button onclick="${validarCamposSuspende};" class="btnSuspenderSolicitacao grayBG" >
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar grayBG" id = "btnCancelarSuspenderSolicitacao">
		</button>
	</div>
	<div id="alterarStatusDiv" class="alterarStatusDiv">
		<@ww.form name="formUpdateStatusSolicitacao" action="updateStatusSolicitacao.action" method="post" onsubmit="${validarCamposUpdateStatus}">
			<@ww.select  label="Status"  name="solicitacao.status"  list="status" id="statusSolicitcao" liClass="liLeft"/>
			<@ww.datepicker label="Data" name="solicitacao.dataStatus" id="dataStatus" cssClass="mascaraData" />
			<@ww.textarea label="Observações" name="solicitacao.observacaoLiberador" id="observacaoLiberador" />
			<@ww.hidden name="solicitacao.id" id="solicitacaoIdAlterarStatus"/>
			<@ww.hidden name="statusSolicitacaoAnterior" id="statusSolicitacaoAnterior" />
			<@ww.hidden name="dataStatusSolicitacaoAnterior" id="dataStatusSolicitacaoAnterior" />
		</@ww.form>
		<button onclick="${validarCamposUpdateStatus};" class="btnGravar grayBG" id="gravarStatus"></button>
	</div>
	<div class="buttonGroup">
		<@authz.authorize ifAllGranted="ROLE_MOV_SOLICITACAO_INSERIR">
			<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I"></button>
		</@authz.authorize>
	</div>
</body>
</html>
