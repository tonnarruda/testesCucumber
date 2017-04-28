<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		#legendas { float: right; margin-bottom: 10px; }
		.naoApto { color: #F00 !important; }
		.apto { color: #0000FF !important; }
		.indiferente { color: #555 !important; }
		.contratado { color: #008000 !important; }
		.btnTriagem, .btnInserirEtapasEmGrupo, .btnResultadoAvaliacao, .btnVoltar{margin: 5px 5px 0px 0px;}
		.alterarAvaliacao {	display: none;}
	</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/SolicitacaoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

	<script>
		function marcarDesmarcar(frm)
		{
			var vMarcar;
	
			if (document.getElementById('md').checked)
			{
				vMarcar = true;
			}
			else
			{
				vMarcar = false;
			}
	
			with(frm)
			{
				for(i = 0; i < elements.length; i++)
				{
					if(elements[i].name == 'candidatosId' && elements[i].type == 'checkbox' )
					{
						elements[i].checked = vMarcar;
					}
				}
			}
		}
	
		function verificaMarcados(frm)
		{
			var resultadoCand = false;
			var resultadoSol  = false;
	
			with(frm)
			{
				for(i = 0; i < elements.length; i++)
				{
					if(elements[i].name == 'candidatosId' && elements[i].type == 'checkbox' )
					{
						if (elements[i].checked && resultadoCand == false)
						{
							resultadoCand = true;
						}
					}
					else if(elements[i].name == 'solicitacaoDestino.id' && elements[i].type == 'radio' )
					{
						if (elements[i].checked && resultadoSol == false)
						{
							resultadoSol = true;
						}
					}
				}
			}
	
			if (resultadoCand && resultadoSol)
				return true;
			else
			{
				jAlert("Informe pelo menos um candidato e uma solicitação!");
				return false;
			}
		}
		
		function submeter(){
			if(verificaMarcados(formCand)) { 
				DWRUtil.useLoadingMessage('Carregando...');
				var candidatosSolicitacaoIds = [];
				$('input:checkbox[name=candidatosId]:checked').each(function(){
					candidatosSolicitacaoIds.push($(this).val());
				});
				
				SolicitacaoDWR.verificaModeloAvaliacaoSolicitacaoDestinoExiste(solicitacaoDialog, ${solicitacao.id}, $('input[name=solicitacaoDestino.id]:checked').val(), candidatosSolicitacaoIds);
			}
		}
		
		function solicitacaoDialog(data){
			if(data != ""){
				$('#avaliacoes').text(data);
				$('#alterarAvaliacao').dialog({ modal: true, 
											width: 700,
											height: 250,
											title: 'Alerta',
										    buttons: 
											[
											    {
											        text: "Gravar",
											        click: function() {
														if($('input[id="atualizarModelo"]:checked').length > 0){
															$('#atualizarModeloSubmit').val($('input[id="atualizarModelo"]:checked').val());
															document.formCand.submit();
														}else{
															jAlert("Marque pelo menos uma opção.");
														} 																							        
											        }
											    },
											    {
											        text: "Cancelar",
											        click: function() { $(this).dialog("close"); }
											    }
											]
										}); 
			}else{
				$('#atualizarModeloSubmit').val(true);
				document.formCand.submit();
			} 
		}
	</script>

	<title>Mover Candidatos da Seleção</title>
</head>
<body>
	<@ww.actionmessage /> 
	<@ww.actionerror />

	<table width="100%">
		<tr>
			<td>Área: <#if solicitacao.areaOrganizacional?exists && solicitacao.areaOrganizacional.nome?exists>${solicitacao.areaOrganizacional.nome}</#if></td>
			<td>Cargo: <#if solicitacao.faixaSalarial?exists && solicitacao.faixaSalarial.cargo?exists && solicitacao.faixaSalarial.cargo.nome?exists>${solicitacao.faixaSalarial.cargo.nome}</#if></td>
			<td>Solicitante: <#if solicitacao.solicitante?exists && solicitacao.solicitante.nome?exists>${solicitacao.solicitante.nome}</#if></td>
			<td>Vagas: <#if solicitacao.quantidade?exists>${solicitacao.quantidade}</#if></td>
		</tr>
	</table>

	<br/>
	<@ww.form name="formCand" action="mover.action" method="POST" >
		<div id="legendas">
			<span style='background-color: #0000FF;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Aptos
			<span style='background-color: #555;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Indiferente
			<span style='background-color: #F00;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Não Aptos
			<span style='background-color: #008000;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Contratados/Promovidos
		</div>
		<br>
		<@ww.hidden name="solicitacao.id"/>
		<#if candidatoSolicitacaos?exists && 0<candidatoSolicitacaos.size()>
			<@display.table name="candidatoSolicitacaos" id="candidatoSolicitacao" class="dados" defaultsort=2>
				
				<#if candidatoSolicitacao?exists && candidatoSolicitacao.aptoBoolean>
					<#if candidatoSolicitacao.apto == 'I'>
						<#assign classe="indiferente"/>
					<#else>
						<#assign classe="apto"/>
					</#if>			
				<#else>
					<#assign classe="naoApto"/>
				</#if>
				<#if candidatoSolicitacao?exists && (candidatoSolicitacao.status == 'P' || candidatoSolicitacao.status == 'C')>
					<#assign classe="contratado"/>
				</#if>
				
				<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(document.formCand);' />" style="width: 30px; text-align: center;">
					<input type="checkbox" value="${candidatoSolicitacao.id}" name="candidatosId" />
				</@display.column>
				<@display.column property="candidato.nome" title="Nome" class="${classe}"/>
				<@display.column property="etapaSeletiva.nome" title="Etapa" class="${classe}"/>
				<@display.column property="responsavel" title="Responsável" class="${classe}"/>
				<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}"  style="text-align: center;" class="${classe}"/>
				<@display.column title="Obs."  style="text-align: center;" class="${classe}">
					<#if candidatoSolicitacao.observacao?exists && candidatoSolicitacao.observacao?trim != "">
						<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${candidatoSolicitacao.observacao?j_string}');return false">...</span>
					</#if>
				</@display.column>
			</@display.table>
		</#if>

		<h3>:: Solicitações em aberto</h3>

		<@display.table name="solicitacaos" id="sol" class="dados" >
			<@display.column title="" style="width: 30px; text-align: center;">
				<input type="radio" value="${sol.id}" name="solicitacaoDestino.id"/>
			</@display.column>
			<@display.column property="id" title="Código"/>
			<@display.column property="faixaSalarial.cargo.nome" title="Cargo" />
			<@display.column property="empresa.nome" title="Empresa" />
			<@display.column property="areaOrganizacional.nome" title="Área" />
			<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" />
			<@display.column property="solicitante.nome" title="Solicitante" />
			<@display.column property="quantidade" title="Vagas" style="text-align: right;"/>
		</@display.table>
	
		<@ww.hidden name="atualizarModelo" id="atualizarModeloSubmit" value="false"/>
		<div id="alterarAvaliacao" class="alterarAvaliacao">
			<@ww.div >
				O(s) candidato(s) selecionado(s) que deseja mover possue(m) avaliações respondidas.<br/><br/>
				A solicitação destino não possue a(s) avaliação(ões): <label id="avaliacoes"></label><br/><br/>
				<b>Você deseja:</b><br/>
			</@ww.div>
			<@ww.div id="divatualizarModelo" cssClass="radio">
				<input id="atualizarModelo" type="radio" value="true" name="atualizarModelo"/><label>Vincular o modelo da avaliação a solicitação destino e transportar respostas do(s) candidato(s).</label></br>
				<input id="atualizarModelo" type="radio" value="false" name="atualizarModelo"/><label>Não vincular o modelo da avaliação a solicitação destino (Essa opção irá apagar as repostas do modelo de avaliação do(s) candidato(s)).</label>
			</@ww.div>
		</div>
	</@ww.form>
	
	<div class="buttonGroup">
		<button class="btnGravar" onclick="submeter();" accesskey="G">
		</button>

		<button onclick="window.location='list.action?solicitacao.id=${solicitacao.id}'" class="btnVoltar" accesskey="V">
		</button>
	</div>
</body>
</html>