<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		
		.avaliacoes { display: none; }
		.avaliacoes ul { list-style: none; }
		.avaliacoes ul li { margin: 5px; }
		.avaliacoes ul li a { text-decoration: none; }
	</style>
	
	<title>Colaboradores Inscritos no Curso de ${turma.curso.nome}, Turma - ${turma.descricao}</title>
	
    <#include "../ftl/mascarasImports.ftl" />
    <#include "../ftl/showFilterImports.ftl" />
    
    <#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
    
    <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
    <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorTurmaDWR.js?version=${versao}"/>'></script>
    <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/LntDWR.js?version=${versao}"/>'></script>
    <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
    <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
    
    <script type="text/javascript">
    	var empresaIds = new Array();
		<#if empresas?exists>
			<#list empresas as empresa>
				empresaIds.push(${empresa.id});
			</#list>
		</#if>
		
		var avaliacoes = new Array();
		<#if turma.turmaAvaliacaoTurmas?exists>
			<#list turma.turmaAvaliacaoTurmas as turmaAvaliacaoTurma>
				avaliacoes.push({ turmaId: ${turma.id}, questionarioId: ${turmaAvaliacaoTurma.avaliacaoTurma.questionario.id}, questionarioTitulo: '${turmaAvaliacaoTurma.avaliacaoTurma.questionario.titulo}' });
			</#list>
		</#if>
		
		var respondidas = new Array();
		<#if colaboradorQuestionarios?exists>
			<#list colaboradorQuestionarios as colabQuestionario>
				respondidas.push(${colabQuestionario.questionario.id} + "_" + ${colabQuestionario.colaborador.id});
			</#list>
		</#if>

		function abrirMenuRespostas(e, colaboradorId) 
		{
			$('.avaliacoes').empty();
		
			var popupAvaliacoes = "<ul>";
			
			$(avaliacoes).each(function(i, avaliacao) {
				popupAvaliacoes += "<li id='"+ avaliacao.questionarioId + "_" + colaboradorId + "'>";
				popupAvaliacoes += "<a href='../../pesquisa/colaboradorResposta/prepareResponderQuestionarioPorOutroUsuario.action?questionario.id=" + avaliacao.questionarioId + "&colaborador.id=" + colaboradorId + "&turmaId=" + avaliacao.turmaId + "&voltarPara=../../desenvolvimento/colaboradorTurma/list.action?turma.id=" + avaliacao.turmaId + "'>";
				popupAvaliacoes += "<img align='absmiddle'/>&nbsp;&nbsp; " + avaliacao.questionarioTitulo;
				popupAvaliacoes += "</a></li>\n";
			});
			
			popupAvaliacoes += "</ul>";
		
			$('.avaliacoes').html(popupAvaliacoes);
		
			$('.avaliacoes').dialog({ modal: true, 
										width: 600, 
										position: [e.pageX, e.pageY], 
										open: function(event, ui) {

											$('.avaliacoes img').attr('src', '<@ww.url value="/imgs/page_new.gif"/>');
											$('.avaliacoes a').attr('title', 'Responder a avaliação da turma por este colaborador');

											$(respondidas).each(function() {
												$('#' + this + ' img').attr('src', '<@ww.url value="/imgs/page_edit.gif"/>');
												$('#' + this + ' a').attr('title', 'Revisar as respostas de avaliação da turma deste colaborador');
											});
											
										} 
									});
		}
		
		function populaEstabelecimento(empresaId)
		{
			DWREngine.setAsync(true);
			DWRUtil.useLoadingMessage('Carregando...');
			EstabelecimentoDWR.getByEmpresas(createListEstabelecimento, empresaId,empresaIds);
		}

		function createListEstabelecimento(data)
		{
			addChecks('estabelecimentosCheck',data);
		}
		
		function inserirColaboradorLnt(cursoId, colaboradorId, colaboradorTurmaId){
			LntDWR.findLntsColaborador(cursoId, colaboradorId, function(lnts){
				if(lnts == null || lnts.length == 0){
					jAlert('Não existem LNTs a serem relacionados para esse colaborador.');
					return;
				}
				
				$('#colaboradorTurmaId').val(colaboradorTurmaId);
				$('#cursoId').val(cursoId);
				
				$("#lnt").html("<option value>Sem LNT</option>");
				$.each(lnts, function(i, lnt) {
					selecionado = "";
					if(lnt.selected)
						selecionado = "selected";
					
					$("#lnt").append("<option value='"+lnt.id+"' "+ selecionado +">"+lnt.descricao+"</option>");
				});
			
				$('#dialogLnt').dialog({ modal: true, width: 555, title: 'Relacionar colaborador a LNT',
													buttons: [
																    {
																        text: "Relacionar",
																        click: function() {
															        		document.formDialog.submit();
																        }
																    },
																    {
																        text: "Sair",
																        click: function() {
															        		$(this).dialog("close");
																        }
																    }
																    
																]
															});
			});
		}

    </script>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
    <@ww.form name="formBusca" id="formBusca" action="list.action" method="POST">
		<#if compartilharColaboradores>
			<#include "../util/topFiltro.ftl" />
	            <@ww.select label="Empresa" name="empresaId" id="empresaId" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="-1" disabled="!compartilharColaboradores" onchange="populaEstabelecimento(this.value);populaCargo(this.value);"/>
	            <@ww.select label="Aprovado" name="aprovado" list=r"#{'T':'Todos','S':'Sim','N':'Não'}" />
	            
	            <@ww.textfield label="Nome do Colaborador" name="nomeBusca" id="nomeBusca" cssStyle="width: 500px;"/>
	            <@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" filtro="true"/>
	            <input type="submit" value="" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;">
	    	<#include "../util/bottomFiltro.ftl" />
	    	<br>
    	<#else>
        	<@ww.hidden id="empresaId" name="empresaId"/>
		</#if>
    	
    	<@ww.hidden name="turma.id"/>
    	<@ww.hidden id="pagina" name="page"/>
    </@ww.form>
	
	<#if totalSize?exists>
		<b>${totalSize} Colaboradores</b>
	</#if>

	<#if turma?exists && turma.id?exists>
		<@display.table name="colaboradorTurmas" id="colaboradorTurma" class="dados">
			<@display.column title="Ações" class="acao">
				<#if colaboradorTurma.possuiLntASerVinculado>
					<@frt.link verifyRole="ROLE_MOV_LNT_GERAR_CURSOS_E_TURMAS" onclick="inserirColaboradorLnt(${colaboradorTurma.curso.id}, ${colaboradorTurma.colaborador.id},${colaboradorTurma.id});" imgTitle="Relacionar colaborador a LNT" imgName="icon_download.gif"/>
				<#else>
					<@frt.link verifyRole="ROLE_MOV_LNT_GERAR_CURSOS_E_TURMAS" imgTitle="Não existem LNTs a serem relacionados para esse colaborador" imgName="icon_download.gif" opacity=true/>
				</#if>
				<@authz.authorize ifAllGranted="ROLE_RESPONDER_AVALIACAO_TURMA_POR_OUTRO_USUARIO">
					<#if turma.turmaAvaliacaoTurmas?exists && 0 < turma.turmaAvaliacaoTurmas?size>
						<#if turma.turmaAvaliacaoTurmas?size == colaboradorTurma.qtdRespostasAvaliacaoTurma>
							<a href="javascript:;" onclick="abrirMenuRespostas(event, ${colaboradorTurma.colaborador.id});"><img border="0" title="Todas avaliações foram respondidas(${colaboradorTurma.qtdRespostasAvaliacaoTurma}/${turma.turmaAvaliacaoTurmas?size})" src="<@ww.url value="/imgs/page_edit.gif"/>"/></a> 
						<#else>
							<a href="javascript:;" onclick="abrirMenuRespostas(event, ${colaboradorTurma.colaborador.id});"><img border="0" title="Avaliações(${colaboradorTurma.qtdRespostasAvaliacaoTurma}/${turma.turmaAvaliacaoTurmas?size})" src="<@ww.url value="/imgs/page_new.gif"/>"/></a> 
						</#if>
					<#else>
						<a href="javascript:;"><img border="0" title="Não existem avaliações definidas para esta turma" src="<@ww.url value="/imgs/page_new.gif"/>" style="opacity:0.3;filter:alpha(opacity=40);"/></a>
					</#if>
				</@authz.authorize>	
					
				<a href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){window.location='../colaboradorTurma/delete.action?colaboradorTurma.id=${colaboradorTurma.id}&colaboradorTurma.colaborador.id=${colaboradorTurma.colaborador.id}&turma.id=${turma.id}&planoTreinamento=${planoTreinamento?string}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			</@display.column>

			<@display.column property="colaborador.nome" title="Nome" style="width: 300px;"/>
			<@display.column property="colaborador.matricula" title="Matrícula" style="width: 80px;"/>
			<@display.column property="colaborador.historicoColaborador.estabelecimento.nome" title="Estabelecimento" style="width: 100px;"/>
			<@display.column property="colaborador.empresa.nome" title="Empresa" style="width: 120px;"/>
			<@display.column property="colaborador.areaOrganizacional.descricao" title="Área" style="width: 200px;"/>
		</@display.table>
		<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="formBusca"/>
	
	</#if>

	<div class="buttonGroup">
		<#if turma?exists && turma.id?exists>
			<button class="btnIncluirColaboradores" onclick="window.location='../colaboradorTurma/prepareInsert.action?turma.id=${turma.id}&turma.curso.id=${turma.curso.id}&planoTreinamento=${planoTreinamento?string}'"></button>
			<button class="btnIncluirColaboradoresNota" onclick="window.location='../colaboradorTurma/prepareInsertNota.action?turma.id=${turma.id}&turma.curso.id=${turma.curso.id}&planoTreinamento=${planoTreinamento?string}'"></button>
		</#if>

		<#if planoTreinamento>
			<#assign urlVoltar="../turma/filtroPlanoTreinamento.action"/>
		<#else>
			<#assign urlVoltar="../turma/list.action?curso.id=${turma.curso.id}"/>
		</#if>
		<button class="btnVoltar" onclick="window.location='${urlVoltar}'"></button>
	</div>
	
	<div class='avaliacoes' title='Respostas das avaliações'></div>
	
	<div id="dialogLnt" style="display: none;">
		<@ww.form id="formDialog" name="formDialog" action="InserirColaboradorNaLnt.action" method="POST" >
    		<@ww.hidden name="page" value="${page}" />
	    	<@ww.hidden name="turma.id" value="${turma.id}"/>
			<@ww.hidden name="colaboradorTurma.curso.id" id="cursoId" value=""/>
			<@ww.hidden name="colaboradorTurma.id" id="colaboradorTurmaId" value=""/>
			<@ww.select label="Lnt" id="lnt" name="lntId" headerKey="" headerValue="Sem Lnt" list="ltns" listKey="id" listValue="descricao" cssStyle="width: 520px;"/>	
		</@ww.form>
	</div>
</body>
</html>