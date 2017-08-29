<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		
		ul.aviso { list-style-type: disc; list-style-position: outside; margin-left: 20px; }
	</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ComissaoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/FaixaSalarialDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>"></script>
	
	<#include "../ftl/mascarasImports.ftl" />

	<script>
		$(function() {
			$('#cargo').change(function() {
				var cargoId = $(this).val();
				
				if (cargoId)
					FaixaSalarialDWR.findByCargo(createListFaixas, cargoId);
			});
			
			$('#cargo').change();
		});
		
		function createListFaixas(data)
		{
			addOptionsByCollection('faixaSalarial', data, 'Selecione...');
			
			<#if faixaSalarial.id?exists>
				$('#faixaSalarial').val(${faixaSalarial.id});
			</#if>
		}
	
		function enviarPrepareUpDate(colaborador)
		{
			link = "prepareUpdate.action?colaborador.id="+colaborador+
					"&nomeBusca="+document.getElementById('nomeBusca').value+
					"&cpfBusca="+limpaCamposMascaraCpf(document.getElementById('cpfBusca').value)+
					"&page="+document.getElementById('pagina').value;
			window.location = link;
		}
		
		
		function verificaComissaoByColaborador(colaboradorId, colaboradorNome, colaboradorNaoIntegraAc, colaboradorDesligado)
		{
			ColaboradorDWR.existeHistoricoAguardandoConfirmacaoNoFortesPessoal(colaboradorId, function(data){
				if(data){
						var msg = 'O Colaborador '+ colaboradorNome +', possui uma situação aguardando confirmação no Fortes Pessoal.' +  
						'</br>Para desligar este colaborador é necessário confirmar ou cancelar esta situação no Fortes Pessoal.';
			
						$('<div>'+ msg +'</div>').dialog({title: 'Alerta!',
																modal: true, 
																height: 135,
																width: 800,
																buttons: [
																    {
																        text: "OK",
																        click: function() { $(this).dialog("close"); }
																    }
																] 
																});
					
				}
				else{
					DWRUtil.useLoadingMessage('Carregando...');
					ComissaoDWR.dataEstabilidade(               function(data){
																	resultComissaoByColaborador(data, colaboradorId, colaboradorNome, colaboradorNaoIntegraAc, colaboradorDesligado);
																}, colaboradorId);
				}
			});
		}
		
		function resultComissaoByColaborador(data, colaboradorId, colaboradorNome, colaboradorNaoIntegraAc, colaboradorDesligado)
		{
			integradoComAc = ${integraAc?string};
			solicitarConfirmacaoDesligamento = ${empresaSistema.solicitarConfirmacaoDesligamento?string};
			link = "prepareDesliga.action?colaborador.id="+colaboradorId+
					"&nomeBusca="+document.getElementById('nomeBusca').value+
					"&cpfBusc="+limpaCamposMascaraCpf(document.getElementById('cpfBusca').value);
			
			if(integradoComAc && !colaboradorNaoIntegraAc && !colaboradorDesligado && !solicitarConfirmacaoDesligamento)
					link = "prepareDesligaAC.action?colaborador.id="+colaboradorId;
			
			if (data != null){
				enviarPrepareDesliga(colaboradorId, colaboradorNome, link, data);
			} else {
				window.location=link; 
			}
		}
		
		function enviarPrepareDesliga(colaboradorId, colaboradorNome, link, data)
		{
			var msg = 'O Colaborador '+ colaboradorNome +', faz parte <br>da CIPA e possui estabilidade até o dia ' + data + '.' +
						'<br> Deseja realmente desligá-lo?';
			
			$('<div>'+ msg +'</div>').dialog({title: 'Alerta!',
													modal: true, 
													height: 150,
													width: 500,
													buttons: [
													    {
													        text: "Sim",
													        click: function() { window.location=link; }
													    },
													    {
													        text: "Não",
													        click: function() { $(this).dialog("close"); }
													    }
													] 
													});
			
		}

		function enviarPrepareProgressaoColaborador(colaborador)
		{
			link = "../../cargosalario/historicoColaborador/list.action?colaborador.id="+colaborador;
			window.location = link;
		}
	</script>

	<#include "../ftl/showFilterImports.ftl" />
	<#assign validarCampos="return validaFormulario('formBusca', null, null, true)"/>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<#if empresaIntegradaEAderiuAoESocial>
		<#assign empresaEstaIntegradaEAderiuAoESocial=true/>
	<#else>
		<#assign empresaEstaIntegradaEAderiuAoESocial=false/>
	</#if>

	<title>Colaboradores</title>
</head>
<body>
	<@ww.actionmessage/>
	<@ww.actionerror/>

	<#if !nomeBusca?exists>
		<#assign nomeBusca="">
	</#if>
	<#if !nomeComercialBusca?exists>
		<#assign nomeComercialBusca="">
	</#if>
	<#if !cpfBusca?exists>
		<#assign cpfBusca="">
	</#if>
	<#if !page?exists>
		<#assign page=1>
	</#if>
	
	<#if usuarioLogado?exists && usuarioLogado.id == 1>
		<#assign avisoExclusao>
			Procedimento diferenciado para o usuário <strong>${usuarioLogado.nome}</strong>:
			<ul class=aviso>
				<li>O colaborador será excluído permanentemente com as suas dependências.</li>
				<li>Essa exclusão não poderá ser desfeita.</li>
				<li>Recomendamos que seja efetuado um backup antes da realização desse procedimento.</li>
				<li>O colaborador não será removido do Fortes Pessoal, mesmo que os sistemas estejam integrados.</li>
			</ul>
			Deseja realmente continuar?
		</#assign>
	<#else>
		<#assign avisoExclusao="Confirma exclusão?"/>
	</#if>

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="list.action" onsubmit="${validarCampos}" method="POST" id="formBusca">
			<table>
				<tr>
					<td width="370">
						<#if integraAc>
							<@ww.textfield label="Cód. Fortes Pessoal" name="codigoACBusca" id="codigoACBusca" cssStyle="width: 135px;" liClass="liLeft"/>
							<@ww.textfield label="Matrícula" name="matriculaBusca" id="matriculaBusca" liClass="liLeft" cssStyle="width: 95px;"/>
							<@ww.textfield label="CPF" name="cpfBusca" id="cpfBusca" cssClass="mascaraCpf" cssStyle="width: 110px;"/>
						<#else>
							<@ww.textfield label="Matrícula" name="matriculaBusca" id="matriculaBusca" liClass="liLeft" cssStyle="width: 226px;"/>
							<@ww.textfield label="CPF" name="cpfBusca" id="cpfBusca" cssClass="mascaraCpf"/>
						</#if>	
						<@ww.textfield label="Nome" name="nomeBusca" id="nomeBusca" cssStyle="width: 353px;"/>
						<@ww.textfield label="Nome Comercial" name="nomeComercialBusca" id="nomeComercialBusca" cssStyle="width: 353px;"/>
						
						<#if integraAc>
							<@ww.select label="Situação" name="situacao" id="situacao" list="situacaosIntegraAC" cssStyle="width: 355px;"/>
						<#else>
							<@ww.select label="Situação" name="situacao" id="situacao" list="situacaos" cssStyle="width: 355px;"/>
						</#if>
					</td>
					<td>
						<@ww.select label="Área Organizacional" name="areaOrganizacional.id" id="areaOrganizacional" list="areasList"  listKey="id" listValue="descricao" headerKey="" headerValue="Selecione..." cssStyle="width: 355px;"/>
						<@ww.select label="Estabelecimento" name="estabelecimento.id" id="estabelecimento" list="estabelecimentosList" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." cssStyle="width: 355px;"/>
						<@ww.select label="Cargo" name="cargo.id" id="cargo" list="cargosList" listKey="id" listValue="nomeMercado" headerKey="" headerValue="Selecione..." cssStyle="width: 355px;"/>
						<@ww.select label="Faixa Salarial" name="faixaSalarial.id" id="faixaSalarial" headerValue="Selecione..." headerKey="" cssStyle="width:355px;"/>
					</td>
				</tr>
			</table>
			
			<@ww.hidden id="pagina" name="page"/>
			<input type="submit" value="" id="btnPesquisar" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />

	<p>
		<span style="background-color: #454C54;">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Ativos&nbsp;&nbsp;<span style="background-color: #e36f6f;">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Desligados
	</P>
	
	<#if integraAc >
		<#assign colunaParaOrdenar=4 />
	<#else>
		<#assign colunaParaOrdenar=3 />
	</#if>

	<@display.table name="colaboradors" id="colaborador" pagesize=20 class="dados" defaultsort=colunaParaOrdenar >
		<#assign style=""/>
		<@display.column title="Ações" media="html" class="acao" style = "width:250px;">
			
			<#if colaborador.respondeuEntrevista?exists && colaborador.respondeuEntrevista>
				<#assign imgRespondeuEntrevista="entrevistaBalaoDesligaEdita.gif"/>
				<#assign imgTitleEntrevista="Entrevista de desligamento"/>
				<#assign linkEntrevista="../../pesquisa/entrevista/prepareResponderEntrevista.action?colaborador.id=${colaborador.id}&validarFormulario=false&voltarPara=../../geral/colaborador/list.action"/>
				<#assign opacityEntrevista=false />
			<#else>
				<@authz.authorize ifAllGranted="ROLE_COLAB_LIST_ENTREVISTA_RESPONDER">
					<#assign imgRespondeuEntrevista="entrevistaBalaoDesligaNova.gif"/>
					<#assign imgTitleEntrevista="Entrevista de desligamento"/>
					<#assign opacityEntrevista=false />
					<#assign linkEntrevista="../../pesquisa/entrevista/prepareResponderEntrevista.action?colaborador.id=${colaborador.id}&validarFormulario=false&voltarPara=../../geral/colaborador/list.action"/>
				</@authz.authorize>
				<@authz.authorize ifNotGranted="ROLE_COLAB_LIST_ENTREVISTA_RESPONDER">
					<@authz.authorize ifAllGranted="ROLE_COLAB_LIST_ENTREVISTA_VISUALIZAR">
						<#assign imgRespondeuEntrevista="entrevistaBalaoDesligaNova.gif"/>
						<#assign imgTitleEntrevista="A entrevista ainda não foi respondida. Você não tem permissão para respondê-la."/>
						<#assign linkEntrevista="javascript:;"/>
						<#assign opacityEntrevista=true />
					</@authz.authorize>
				</@authz.authorize>
			</#if>
			
			<#if !colaborador.desligado>
				<#assign statusSolicitacao="A"/>
				<#assign linkDesligamento="javascript:verificaComissaoByColaborador('${colaborador.id}', '${colaborador.nome}', ${colaborador.naoIntegraAc?string}, ${colaborador.desligado?string})"/>
				
				<#if colaborador.id == colaboradorLogadoId>
					<@frt.link verifyRole="ROLE_COLAB_LIST_DESLIGAR" href="javascript:;" imgTitle="Não é possível realizar sua própria solicitação de desligamento." imgName="desliga_colab.gif" opacity=true />
				<#elseif integraAc && !colaborador.naoIntegraAc>
					<#if colaborador.dataSolicitacaoDesligamentoAc?exists>
						<@frt.link verifyRole="ROLE_COLAB_LIST_DESLIGAR" href="javascript:;" imgTitle="Solicitação de desligamento aguardando confirmação no Fortes Pessoal" imgName="desliga_colab.gif" opacity=true />
					<#elseif colaborador.dataSolicitacaoDesligamento?exists>
						<@frt.link verifyRole="ROLE_COLAB_LIST_DESLIGAR" href="javascript:;" imgTitle="Aguardando confirmação de desligamento" imgName="desliga_colab.gif" opacity=true />
					<#elseif empresaSistema.solicitarConfirmacaoDesligamento>
						<@frt.link verifyRole="ROLE_COLAB_LIST_DESLIGAR" href="${linkDesligamento}" imgTitle="Solicitar desligamento" imgName="desliga_colab.gif" />
					<#else>
						<@frt.link verifyRole="ROLE_COLAB_LIST_DESLIGAR" href="${linkDesligamento}" imgTitle="Solicitação de desligamento no Fortes Pessoal" imgName="desliga_colab.gif" />
					</#if>
				<#else>
					<#if empresaSistema.solicitarConfirmacaoDesligamento && !colaborador.dataSolicitacaoDesligamento?exists>
						<@frt.link verifyRole="ROLE_COLAB_LIST_DESLIGAR" href="${linkDesligamento}" imgTitle="Solicitar desligamento" imgName="desliga_colab.gif"/>
					<#elseif empresaSistema.solicitarConfirmacaoDesligamento && colaborador.dataSolicitacaoDesligamento?exists>
						<@frt.link verifyRole="ROLE_COLAB_LIST_DESLIGAR" href="javascript:;" imgTitle="Aguardando confirmação de desligamento" imgName="desliga_colab.gif" opacity=true/>
					<#elseif empresaSistema.solicitarConfirmacaoDesligamento && colaborador.dataSolicitacaoDesligamento?exists>
						<@frt.link verifyRole="ROLE_COLAB_LIST_DESLIGAR" href="${linkDesligamento}" imgTitle="Solicitar desligamento" imgName="desliga_colab.gif"/>
					<#else>
						<@frt.link verifyRole="ROLE_COLAB_LIST_DESLIGAR" href="${linkDesligamento}" imgTitle="Desligar colaborador" imgName="desliga_colab.gif"/>
					</#if>
				</#if>
				
				<#if colaborador.dataSolicitacaoDesligamentoAc?exists && empresaSistema.acIntegra && colaborador.id != colaboradorLogadoId>
					<@authz.authorize ifAllGranted="ROLE_COLAB_LIST_ENTREVISTA"><!--Tem que existir esse authorize devido a um bug. Não remover-->
						<@frt.link verifyRole="ROLE_COLAB_LIST_ENTREVISTA" href="${linkEntrevista}"	imgTitle="${imgTitleEntrevista}" imgName="${imgRespondeuEntrevista}" opacity=opacityEntrevista />
					</@authz.authorize>	
				<#else>
					<@frt.link verifyRole="ROLE_COLAB_LIST_ENTREVISTA" href="javascript:;" imgTitle="Entrevista de Desligamento - disponível apenas após o desligamento do colaborador" imgName="entrevistaBalaoDesligaNova.gif" opacity=true/>
				</#if>
			<#else>
				<#assign statusSolicitacao="I"/>
				<#if colaborador.dataDesligamento?exists && !colaborador.motivoDemissao.motivo?exists>
					<#if integraAc && !colaborador.naoIntegraAc>
						<#assign imgMotivoDeslig="desligadoAC5.gif"/>
					<#else>
						<#assign imgMotivoDeslig="desliga_colab.gif"/>
					</#if>
					
					<@frt.link verifyRole="ROLE_COLAB_LIST_DESLIGAR" href="javascript:verificaComissaoByColaborador('${colaborador.id}', '${colaborador.nome}', ${colaborador.naoIntegraAc?string}, ${colaborador.desligado?string})" imgTitle="Inserir motivo de desligamento" imgName="${imgMotivoDeslig}"/>
				<#else>
					<@frt.link verifyRole="ROLE_COLAB_LIST_DESLIGAR" href="javascript:verificaComissaoByColaborador('${colaborador.id}', '${colaborador.nome}', ${colaborador.naoIntegraAc?string}, ${colaborador.desligado?string})" imgTitle="Colaborador já desligado" imgName="desliga_colab.gif" opacity=true/>
				</#if>

				<@authz.authorize ifAllGranted="ROLE_COLAB_LIST_ENTREVISTA"><!--Tem que existir esse authorize devido a um bug. Não remover-->
					<@frt.link verifyRole="ROLE_COLAB_LIST_ENTREVISTA" href="${linkEntrevista}"	imgTitle="${imgTitleEntrevista}" imgName="${imgRespondeuEntrevista}" opacity=opacityEntrevista />
				</@authz.authorize>
			</#if>

			<@frt.link verifyRole="ROLE_COLAB_LIST_EDITAR" href="javascript:enviarPrepareUpDate('${colaborador.id}')" imgTitle="Editar" imgName="edit.gif"/>
			
			<@authz.authorize ifAllGranted="ROLE_COLAB_LIST_EXCLUIR">
				<#if !empresaEstaIntegradaEAderiuAoESocial || colaborador.naoIntegraAc || usuarioLogado.id == 1>
					<@frt.link verifyRole="ROLE_COLAB_LIST_EXCLUIR" href="javascript:;" onclick="newConfirm('${avisoExclusao?js_string}', function(){window.location='delete.action?colaborador.id=${colaborador.id}'});" imgTitle="Excluir" imgName="delete.gif"/>
				<#elseif empresaEstaIntegradaEAderiuAoESocial>
					<img border="0" title="Devido as adequações ao eSocial, não é possível excluir colaborador no Fortes RH." src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.5;filter:alpha(opacity=50);">
				</#if>
			</@authz.authorize>
		
		
			<@frt.link verifyRole="ROLE_CAD_HISTORICOCOLABORADOR" href="javascript:enviarPrepareProgressaoColaborador('${colaborador.id}')" imgTitle="Visualizar Progressão" imgName="progressao.gif"/>

			<@frt.link verifyRole="ROLE_COLAB_LIST_PERFORMANCE" href="preparePerformanceFuncional.action?colaborador.id=${colaborador.id}" imgTitle="Performance Profissional" imgName="medalha.gif"/>
			
			<@frt.link verifyRole="ROLE_COLAB_LIST_NIVELCOMPETENCIA" href="../../captacao/nivelCompetencia/listCompetenciasColaborador.action?colaborador.id=${colaborador.id}" imgTitle="Competências" imgName="competencias.gif"/>
			
			<@frt.link verifyRole="ROLE_COLAB_LIST_SOLICITACAO" href="prepareColaboradorSolicitacao.action?colaborador.id=${colaborador.id}&statusCandSol=${statusSolicitacao}&voltarPara=../../geral/colaborador/list.action" imgTitle="Incluir em Solicitação" imgName="db_add.gif" disabled=colaborador.statusAcPessoalAguardandoConfirmacao />
			
			<@authz.authorize ifAllGranted="ROLE_VISUALIZAR_ANEXO_COLAB_LOGADO">
				<@frt.link verifyRole="ROLE_COLAB_LIST_DOCUMENTOANEXO" href="../documentoAnexo/listColaborador.action?documentoAnexo.origem=D&documentoAnexo.origemId=${colaborador.id}" imgTitle="Documentos do Colaborador" imgName="anexos.gif"/>
			</@authz.authorize>

			<@authz.authorize ifNotGranted="ROLE_VISUALIZAR_ANEXO_COLAB_LOGADO">
				<#if colaborador.id == colaboradorLogadoId>
					<@frt.link verifyRole="ROLE_COLAB_LIST_DOCUMENTOANEXO" href="#" imgTitle="Você não ter permissão de visualizar seus documentos em anexo." imgName="anexos.gif" opacity=true/>
				<#else>
					<@frt.link verifyRole="ROLE_COLAB_LIST_DOCUMENTOANEXO" href="../documentoAnexo/listColaborador.action?documentoAnexo.origem=D&documentoAnexo.origemId=${colaborador.id}" imgTitle="Documentos do Colaborador" imgName="anexos.gif"/>
				</#if>
			</@authz.authorize>	

			<#if colaborador.usuario.id?exists>
				<@frt.link verifyRole="ROLE_COLAB_LIST_CRIARUSUARIO" href="../../acesso/usuario/prepareUpdate.action?origem=C&usuario.id=${colaborador.usuario.id}&colaborador.id=${colaborador.id}" imgTitle="Editar Acesso ao Sistema" imgName="key.gif"/>
			<#else>
				<#if colaborador.nomeComercial?exists>
					<#assign nomComercial=colaborador.nomeComercial/>
				<#else>
					<#assign nomComercial=""/>
				</#if>
				<@frt.link verifyRole="ROLE_COLAB_LIST_CRIARUSUARIO" href="../../acesso/usuario/prepareInsert.action?origem=C&colaborador.id=${colaborador.id}&nome=${nomComercial}" imgTitle="Criar Acesso ao Sistema" imgName="key_add.gif"/>
			</#if>
		
			<#if colaborador.candidato?exists && colaborador.candidato.id?exists>
				<@frt.link verifyRole="ROLE_COLAB_LIST_VISUALIZARCURRICULO" href="javascript:popup('../../captacao/candidato/infoCandidato.action?candidato.id=${colaborador.candidato.id}&origemList=CO', 580, 750)" imgTitle="Visualizar Currículo" imgName="page_curriculo.gif"/>
			<#else>
				<@frt.link verifyRole="ROLE_COLAB_LIST_VISUALIZARCURRICULO" imgTitle="Não é possível visualizar currículo, este colaborador não é candidato." imgName="page_curriculo.gif" opacity=true/>
			</#if>
		</@display.column>

		<#if colaborador.dataDesligamento?exists>
			<#assign style="color:#e36f6f;"/>
		</#if> 

		<#if integraAc>
			<@display.column property="codigoAC" title="Cód. Fortes Pessoal" style='${style}; text-align: center; width: 60px'/>
		</#if>
		
		<@display.column property="matricula" title="Matrícula" style='${style};text-align: center;'/>
		<@display.column property="nome" title="Nome" style='${style}'/>
		<@display.column property="nomeComercial" title="Nome Comercial" style='${style}'/>
		<@display.column property="pessoal.cpf" title="CPF" style='${style}'/>
		<@display.column property="dataAdmissao" title="Data de Admissão" format="{0,date,dd/MM/yyyy}" style='${style}'/>
	</@display.table>

	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="formBusca"/>

	<div class="buttonGroup">
		<@authz.authorize ifAllGranted="ROLE_CAD_COLABORADOR">
			<button class="btnInserir" onclick="window.location='prepareInsert.action'"></button>
		</@authz.authorize>
		<@authz.authorize ifAllGranted="ROLE_REL_LISTA_COLAB">
			<button class="btnListagemColaborador" onclick="window.location='prepareRelatorioDinamico.action'"></button>
		</@authz.authorize>
	</div>
</body>
</html>