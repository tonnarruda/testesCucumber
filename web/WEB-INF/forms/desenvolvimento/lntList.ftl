<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>
	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/buttons.css"/>');
		@import url('<@ww.url value="/css/font-awesome.min.css?version=${versao}"/>');
		
		.waDivFormulario table tbody tr td a{
			padding-right: 3px !important
		}
		#botaoDialog{
			height: 30px !important;	
		}
		
	</style>

	<title>Levantamentos de Necessidade de Treinamento (LNT)</title>
	
	<script type='text/javascript'>
			
		function relatorio(lntId){
			$('#relatorioDialog').dialog({ modal: true, width: 250, title: 'Relatório Participantes LNT',
									  		buttons: 
											[
											    {
											    	id: "botaoDialog",
											        text: "Gerar Relatório",
											        click: function() { 
											        	processando('<@ww.url includeParams="none" value="/imgs/"/>');
											        	$('#lntId').val(lntId);
											        	$('#relatorioParticipantes').submit();
											        	$(this).dialog("close");
											        }
											    },
											    {
											    	id: "botaoDialog",
											        text: "Cancelar",
											        click: function() { $(this).dialog("close"); }
											    }
											]
										});
		}
			
	</script>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<#include "../util/topFiltro.ftl" />

	<@ww.form name="form" id="form" action="list.action" method="POST">
		<table>
				<tr>
					<td width="370">
						<@ww.textfield label="Descrição" name="lnt.descricao" id="descricaoBusca" cssStyle="width: 353px;"/>
						<@ww.select label="Status" name="status" id="status" list="listaStatusLnt"/>
					</td>
				</tr>
			</table>
		<@ww.hidden id="pagina" name="page"/>
		
		<button style:"margin-left: 20px !important;" onclick="document.getElementById('pagina').value = 1;" type="submit">Pesquisar</button>
		
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>
	
	<p align="right">
		<span style="background-color: #454C54;">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Não Iniciada&nbsp;&nbsp
		<span style="background-color: #3185e4;">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Em Planejamento&nbsp;&nbsp
		<span style="background-color: #f7b451;">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Em Análise&nbsp;&nbsp
		<span style="background-color: #009900;">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Finalizada
	</p>
	<@display.table name="lnts" id="lnt" class="dados">
		<#assign style=""/>
		 
		<@display.column title="Ações" class="acao" style = "width:180px;">
			<@frt.link verifyRole="ROLE_MOV_LNT_EDITAR" href="prepareUpdate.action?lnt.id=${lnt.id}" imgTitle="Editar" iconeClass="fa-edit"/>
			<@frt.link verifyRole="ROLE_MOV_LNT_EXCLUIR" href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?lnt.id=${lnt.id}'});" imgTitle="Excluir" iconeClass="fa-times"/>
			<#if lnt?exists && lnt.status?exists>
				 
				<!-- Caso as regras abaixo mudem será necesserário reavaliar as regras implementadas na método "getStatus" da classe "StatusLnt". -->
				
				<#if lnt.status == listaStatusLnt.getNaoIniciada()>
					<#assign style="color:#454C54;"/>
					<@frt.link verifyRole="ROLE_MOV_LNT_ADICIONAR_COLABORADORES" href="#" imgTitle="Esta LNT não foi iniciada." iconeClass="fa-users" opacity=true/>
					<@frt.link verifyRole="ROLE_MOV_LNT_ANALISAR" href="#" imgTitle="Esta LNT não foi iniciada." iconeClass="fa-list" opacity=true/>			
					<@frt.link verifyRole="ROLE_MOV_LNT_FINALIZAR" href="#" imgTitle="Esta LNT não foi iniciada." iconeClass="fa-check-square" opacity=true/>
					<@frt.link verifyRole="ROLE_MOV_LNT_GERAR_CURSOS_E_TURMAS" href="#" imgTitle="Esta LNT não foi iniciada." iconeClass="fa-sitemap" opacity=true/>
				<#elseif lnt.status == listaStatusLnt.getEmPlanejamento()>
					<#assign style="color:#3185e4;"/>
					<@frt.link verifyRole="ROLE_MOV_LNT_ADICIONAR_COLABORADORES" href="prepareParticipantes.action?lnt.id=${lnt.id}" imgTitle="Adicionar colaboradores" iconeClass="fa-users"/>
					<@frt.link verifyRole="ROLE_MOV_LNT_ANALISAR" href="#" imgTitle="Esta LNT está em planejamento." iconeClass="fa-list" opacity=true/>			
					<@frt.link verifyRole="ROLE_MOV_LNT_FINALIZAR" href="#" imgTitle="Esta LNT está em planejamento." iconeClass="fa-check-square" opacity=true/>
					<@frt.link verifyRole="ROLE_MOV_LNT_GERAR_CURSOS_E_TURMAS" href="#" imgTitle="Esta LNT está em planejamento." iconeClass="fa-sitemap" opacity=true/>
				<#elseif lnt.status == listaStatusLnt.getEmAnalise()>
					<#assign style="color:#e88f0a;"/>
					<@frt.link verifyRole="ROLE_MOV_LNT_ADICIONAR_COLABORADORES" href="#" imgTitle="Esta LNT está em análise." iconeClass="fa-users" opacity=true/>
					<@frt.link verifyRole="ROLE_MOV_LNT_ANALISAR" href="prepareParticipantes.action?lnt.id=${lnt.id}" imgTitle="Analisar" iconeClass="fa-list"/>
					<@frt.link verifyRole="ROLE_MOV_LNT_FINALIZAR" href="finalizar.action?lnt.id=${lnt.id}" imgTitle="Finalizar" iconeClass="fa-check-square"/>
					<@frt.link verifyRole="ROLE_MOV_LNT_GERAR_CURSOS_E_TURMAS" href="#" imgTitle="Esta LNT está em análise." iconeClass="fa-sitemap" opacity=true/>
				<#elseif lnt.status == listaStatusLnt.getFinalizada()>
					<#assign style="color:#009900;"/>
					<@frt.link verifyRole="ROLE_MOV_LNT_ADICIONAR_COLABORADORES" href="#" imgTitle="Esta LNT está finalizada." iconeClass="fa-users" opacity=true/>
					<@frt.link verifyRole="ROLE_MOV_LNT_ANALISAR" href="#" imgTitle="Esta LNT está finalizada." iconeClass="fa-list" opacity=true/>
					<@frt.link verifyRole="ROLE_MOV_LNT_FINALIZAR" href="reabrir.action?lnt.id=${lnt.id}" imgTitle="Reabrir" iconeClass="fa-check-square-o"/>
					<@frt.link verifyRole="ROLE_MOV_LNT_GERAR_CURSOS_E_TURMAS" href="gerarCursosETurmas.action?lnt.id=${lnt.id}" imgTitle="Gerar cursos e turmas" iconeClass="fa-sitemap"/>
				</#if> 
			</#if> 
			<#if lnt.status != listaStatusLnt.getNaoIniciada()>
				<@frt.link verifyRole="ROLE_MOV_LNT_IMPRIMIR" onclick="relatorio(${lnt.id})" imgTitle="Imprimir" iconeClass="fa-print"/>
			<#else>
				<@frt.link verifyRole="ROLE_MOV_LNT_IMPRIMIR" imgTitle="Imprimir" iconeClass="fa-print" opacity=true/>
			</#if>
		</@display.column>
		
		<@display.column property="descricao" title="Descrição" style="${style}"/>
		<@display.column property="periodoFormatado" title="Período" style="width: 250px; text-align: center; ${style}"/>
		
	</@display.table>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="form"/>
	
	<@authz.authorize ifAllGranted="ROLE_MOV_LNT_INSERIR">
		<button onclick="window.location.href='prepareInsert.action'" type="button">Inserir</button>
	</@authz.authorize>
	
	<div id="relatorioDialog" style="display:none;">
		<@ww.form name="relatorioParticipantes" id="relatorioParticipantes" action="relatorioParticipantes.action" method="POST">
			Agrupar relatório por:</br></br>
			<input id="apruparRelatorioPor" name="apruparRelatorioPor" type="radio" value="A" checked/><label>Área Organizacional</label></br>
			<input id="apruparRelatorioPor" name="apruparRelatorioPor" type="radio" value="C"/><label>Curso</label>
			<@ww.hidden id="lntId" name="lnt.id" value=""/>
			<@ww.hidden name="listagem" value="true"/>
		</@ww.form>
	</div>
	
</body>
</html>
