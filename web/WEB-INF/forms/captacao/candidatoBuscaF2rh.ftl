<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>

	<#if solicitacao?exists && solicitacao.id?exists>
		<title>Inserir Candidatos na Solicitação</title>
	<#else>
		<title>Triagem de currículos</title>
	</#if>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		#menuBusca a.ativaF2rh { border-bottom: 2px solid #5292C0; }
	</style>
	
	<#include "../ftl/mascarasImports.ftl" />
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CandidatoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/buscaCandidatoSolicitacao.js?version=${versao}"/>'></script>

	<script type="text/javascript">
	
		function stopEvent(e){
			if(!e) var e = window.event;
			
			//e.cancelBubble is supported by IE - this will kill the bubbling process.
			e.cancelBubble = true;
			e.returnValue = false;
		
			//e.stopPropagation works only in Firefox.
			if (e.stopPropagation) {
				e.stopPropagation();
				e.preventDefault();
			}
			return false;
		}
		
		function enviaBusca(page, evento)
		{
			if(getKeyCode(evento) == 13)
			{
				stopEvent(evento);	
				enviaBuscaLink(page);
			}
		}

		function enviaBuscaLink(page)
		{
			var candidatosSelecionados = $('.dados * input[type=checkbox]:checked');
			
			if (candidatosSelecionados.size() > 0)
			{
				jConfirm('Deseja incluir os candidatos selecionados?', 'RH', function(r) {
				    if(r)
				    {
					    candidatosSelecionados.each(function(){        
								$('#formBuscaF2rh').append("<input type='hidden' name='candidatosId' value='" + $(this).val() + "'/>");
							});
				    }	
				    
			    	$('#page').val(page);
					$('#formBuscaF2rh').submit();
				}, true);
			}
			else
			{
				$('#page').val(page);
				$('#formBuscaF2rh').submit();
			}							
		}
	</script>
	<#include "../ftl/showFilterImports.ftl" />

	<#if !palavrasChaveCurriculoEscaneado?exists>
		<#assign palavrasChaveCurriculoEscaneado=""/>
	</#if>
	<#if !formas?exists>
		<#assign formas=""/>
	</#if>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
</head>

<body>
	<#include "buscaCandidatoSolicitacaoLinks.ftl" />

	<#include "../util/topFiltro.ftl" />	
		<@ww.form name="formBuscaF2rh" id="formBuscaF2rh" action="buscaF2rh.action" method="POST">
			<@ww.textfield label="Cargo" id="cargo" name="curriculo.cargo" cssStyle="width: 526px;"  />
						
			<@ww.select label="Escolaridade" name="escolaridade" id="escolaridade" list="escolaridades" cssStyle="width: 180px;" headerKey="" headerValue="" liClass="liLeft"/>
			<@ww.select label="Idioma" name="idioma" id="idioma" list="idiomas" listKey="id" listValue="nome"  cssStyle="width: 110px;" headerKey="" headerValue=""  liClass="liLeft"/>
			<@ww.select label="Sexo" name="curriculo.sexo" id="sexo" list="sexos" cssStyle="width: 100px;"  liClass="liLeft"/>
			<li>
				<span>Idade Preferencial:</span>
			</li>
			<@ww.textfield name="idadeMin" id="dataPrevIni" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="3" onkeypress = "return(somenteNumeros(event,''));"/>
			<@ww.label value="a" liClass="liLeft" />
			<@ww.textfield name="idadeMax" id="dataPrevFim" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="3" onkeypress = "return(somenteNumeros(event,''));"/>
			<@ww.label value="anos"/><div style="clear: both"></div>
			
			<@ww.select label="Estado" name="uf" id="uf" list="ufs" liClass="liLeft" cssStyle="width: 45px;" headerKey="" headerValue="" onchange="javascript:populaCidadesCheckList()"/>
			<@ww.textfield label="Bairro" id="bairro" name="curriculo.bairro" cssStyle="width: 264px;"/>
			
			<@frt.checkListBox label="Cidade" name="cidadesCheck" id="cidadesCheck" list="cidadesCheckList" filtro="true"/>
			<@ww.textfield label="Palavra chave(Ex.: programador superior completo)" id="observacoes_complementares" name="curriculo.observacoes_complementares" cssStyle="width: 525px;"/>
			
			<@ww.hidden name="filtro" value="true"/>
			<@ww.hidden name="solicitacao.id"/>
			<@ww.hidden name="page" id="page"/>
			
			<div class="buttonGroup">
				<input type="button" value="" class="btnPesquisar grayBGE" onclick="enviaBuscaLink(1);">
			</div>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
		
		<#if solicitacao?exists && solicitacao.id?exists>
			<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar" ></button>
		</#if>
		<br>

	<#if curriculos?exists && 0 < curriculos?size>
		<br />
		<@ww.form name="formCand" id="formCand" action="insertCandidatosByF2rh.action" validate="true" method="POST">

			<@ww.hidden name="solicitacao.id"/>

			<@display.table name="curriculos" id="curriculo" class="dados" >
			
				<#if solicitacao?exists && solicitacao.id?exists>			
					<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(document.formCand);' />" style="width: 30px; text-align: center;">
						<input type="checkbox" value="${curriculo.id?string?replace(".", "")?replace(",","")}" name="candidatosId" cpf="${curriculo.user.login}" />
					</@display.column>
				</#if>
			
				<@display.column title="Nome" style="width: 250px;">
					<a title="Ver Currículo" href="javascript:popup('http://www.f2rh.com.br/curriculos/${curriculo.id?string?replace(".", "")?replace(",","")}?s=${curriculo.s}', 780, 750)" cpf="${curriculo.user.login}">
						${curriculo.nome}
					</a>
				</@display.column>
				
				<@display.column property="escolaridade_rh" title="Escolaridade" style="width: 150px;"/>
				<@display.column title="Cidade/UF" style="width: 150px;">
					<#if curriculo.cidade_rh?exists && curriculo.estado?exists>
						${curriculo.cidade_rh}/${curriculo.estado}
					</#if>
				</@display.column>
				<@display.column property="updated_rh" title="Atualizado em" style="width: 60px;text-align:center;"/>
			</@display.table>
		</@ww.form>

		<div class="linksPaginacao">
			<#if page == 1>
				<img src="<@ww.url value="/imgs/primeira.gif"/>" class="desabilitaImg">&nbsp;&nbsp;&nbsp;
				<img src="<@ww.url value="/imgs/anterior.gif"/>" class="desabilitaImg">&nbsp;&nbsp;&nbsp;
			<#else>
				<a href='javascript:;' onclick='enviaBuscaLink(1);' title="Primeira página"><img src="<@ww.url value="/imgs/primeira.gif"/>"></a>&nbsp;&nbsp;&nbsp;
				<a href='javascript:;' onclick='enviaBuscaLink(${page - 1});' title="Página anterior"><img src="<@ww.url value="/imgs/anterior.gif"/>"></a>
			</#if>
		
			Página <input name="inputPage" value="${page}" id="inputPage" class="inputPage" maxlength="4" onkeydown='enviaBusca(this.value, event);' onkeypress="somenteNumeros(event,'');" type="text">&nbsp;&nbsp;&nbsp;

			<#if 100 <= totalSize>	
				<a href='javascript:;' onclick='enviaBuscaLink(${page + 1});' title="Próxima página"><img src="<@ww.url value="/imgs/proxima.gif"/>"></a>&nbsp;&nbsp;&nbsp;
			<#else>
				<img src="<@ww.url value="/imgs/proxima.gif"/>" class="desabilitaImg">&nbsp;&nbsp;&nbsp;
			</#if>
		</div>

		<div class="buttonGroup">
			<#if solicitacao?exists && solicitacao.id?exists>
				<button onclick="prepareEnviarForm();" class="btnInserirSelecionados"></button>
				<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar"></button>
			</#if>
		</div>
	</#if>
</body>
</html>