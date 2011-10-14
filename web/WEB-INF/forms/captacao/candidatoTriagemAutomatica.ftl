<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
		
		#menuBusca a.ativaTriagemAutomatica{color: #FFCB03;}
	</style>

	<#if solicitacao?exists && solicitacao.id?exists>
		<title>Inserir Candidatos na Solicitação</title>
	<#else>
		<title>Triagem de currículos</title>
	</#if>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CandidatoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/buscaCandidatoSolicitacao.js"/>'></script>
	<script type='text/javascript'>
		$(function() {
			$("input[name='ativaParam']").click(function() {
				var marcado = $(this).is(":checked");
				$(this).parent().parent().find("input:text").attr("disabled", !marcado);
			});
		});
		
		function triar() 
		{
			$("input:text").css("background-color", "#FFF");
			var valido = true;
		
			$("input[name='ativaParam']").each(function() {
				var marcado = $(this).is(":checked");
				var peso = $(this).parent().parent().find(".peso");
				
				if (marcado && $(peso).val() == "") {
					$(peso).css("background-color", "#FFEEC2");
					valido = false;
				}
			});
			
			if (!valido) {
				jAlert("Informe os pesos indicados");
				return false;
			
			} else {
				return validaFormularioEPeriodo('formBusca', new Array('percentualMinimo'), false);
			}
		}
	</script>

	<#include "../ftl/mascarasImports.ftl" />

	<#if dataCadIni?exists>
		<#assign dataIni = dataCadIni?date/>
	<#else>
		<#assign dataIni = ""/>
	</#if>
	<#if dataCadFim?exists>
		<#assign dataFim = dataCadFim?date/>
	<#else>
		<#assign dataFim = ""/>
	</#if>

<#include "../ftl/showFilterImports.ftl" />
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

</head>

<body>
	<#if !BDS>
		<#include "buscaCandidatoSolicitacaoLinks.ftl" />
	</#if>

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" id="formBusca" action="triagemAutomatica.action" method="POST">
			
			<@ww.hidden name="BDS"/>

			<#if BDS?exists && !BDS && solicitacao?exists && solicitacao.id?exists>
				<@ww.hidden name="solicitacao.id"/>
			</#if>

			<table cellpadding="3" cellspacing="2">
				<thead>
					<tr>
						<th>&nbsp;</th>
						<th>Peso</th>
						<th>Parâmetro</th>
						<th>Valor</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td align="center">
							<input type="checkbox" checked="checked" disabled="disabled" name="ativaParam" />
						</td>
						<td align="center">
							<@ww.textfield theme="simple" name="pesos['cargo']" cssStyle="width:30px; text-align:right;" liClass="liLeft" cssClass="peso" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
						</td>
						<td>Cargo</td>
						<td>
							<@ww.hidden name="solicitacao.faixaSalarial.cargo.id"/>
							${solicitacao.faixaSalarial.descricao}
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="checkbox" checked="checked" name="ativaParam" />
						</td>
						<td align="center">
							<@ww.textfield theme="simple" name="pesos['escolaridade']" cssStyle="width:30px; text-align:right;" liClass="liLeft" cssClass="peso" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
						</td>
						<td>Escolaridade</td>
						<td>
							<@ww.hidden name="solicitacao.escolaridade"/>
							${escolaridades.get('${solicitacao.escolaridade}')}
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="checkbox" checked="checked" name="ativaParam" />
						</td>
						<td align="center">
							<@ww.textfield theme="simple" name="pesos['cidade']" cssStyle="width:30px; text-align:right;" liClass="liLeft" cssClass="peso" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
						</td>
						<td>Cidade</td>
						<td>
							<@ww.hidden name="solicitacao.cidade.id"/>
							${solicitacao.cidade.nome}/${solicitacao.cidade.uf.sigla}
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="checkbox" checked="checked" name="ativaParam" />
						</td>
						<td align="center">
							<@ww.textfield theme="simple" name="pesos['sexo']" cssStyle="width:30px; text-align:right;" liClass="liLeft" cssClass="peso" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
						</td>
						<td>Sexo</td>
						<td>
							<@ww.hidden name="solicitacao.sexo"/>
							${sexos.get('${solicitacao.sexo}')}
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="checkbox" checked="checked" name="ativaParam" />
						</td>
						<td align="center">
							<@ww.textfield theme="simple" name="pesos['idade']" cssStyle="width:30px; text-align:right;" liClass="liLeft" cssClass="peso" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
						</td>
						<td>Faixa etária</td>
						<td>
							<@ww.hidden name="solicitacao.idadeMinima"/>
							<@ww.hidden name="solicitacao.idadeMaxima"/>
							${solicitacao.idadeMinima} a ${solicitacao.idadeMaxima} anos
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="checkbox" checked="checked" name="ativaParam" />
						</td>
						<td align="center">
							<@ww.textfield theme="simple" name="pesos['pretensaoSalarial']" cssStyle="width:30px; text-align:right;" liClass="liLeft" cssClass="peso" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
						</td>
						<td>Remuneração</td>
						<td>
							<@ww.hidden name="solicitacao.remuneracao"/>
							${solicitacao.remuneracao?string(",##0.00")}
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="checkbox" checked="checked" name="ativaParam" />
						</td>
						<td align="center">
							<@ww.textfield theme="simple" name="pesos['tempoExperiencia']" cssStyle="width:30px; text-align:right;" liClass="liLeft" cssClass="peso" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
						</td>
						<td>Experiência em meses</td>
						<td>
							<@ww.textfield name="tempoExperiencia" id="tempoExperiencia" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="3" onkeypress="return somenteNumeros(event,''));"/>
						</td>
					</tr>
				</tbody>
			</table>

			<li style="clear:both;"><br /></li>
			
			<label>Percentual Mínimo de Compatibilidade: *<label>
			<@ww.textfield theme="simple" name="percentualMinimo" id="percentualMinimo" onkeypress = "return(somenteNumeros(event,''));" maxLength="3" required="true" cssStyle="width: 30px; text-align:right;" />
			%
			
			<div class="buttonGroup">
				<input type="button" value="" class="btnPesquisar grayBGE" onclick="triar();">
			</div>
		</@ww.form>

	<#include "../util/bottomFiltro.ftl" />

	<#if BDS?exists && !BDS && solicitacao?exists && solicitacao.id?exists>
		<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar" accesskey="V">
		</button>
	</#if>

	<#if candidatos?exists >
		<br>


<@ww.form name="formCand" action="insertCandidatos.action" validate="true" method="POST">
	<#if BDS?exists && !BDS>
		<@ww.hidden name="solicitacao.id"/>
	</#if>
	<@display.table name="candidatos" id="candidato" class="dados" >

		<#if solicitacao?exists && solicitacao.id?exists>
			<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(document.formCand);' />" style="width: 30px; text-align: center;">
				<input type="checkbox" value="${candidato.id?string?replace(".", "")?replace(",","")}" name="candidatosId" />
			</@display.column>
		</#if>
		
		<@display.column title="Nome">
			<a title="Ver Informação" href="javascript:popup('<@ww.url includeParams="none" value="/captacao/candidato/infoCandidato.action?candidato.id=${candidato.id?string?replace('.', '')}"/>', 580, 750)">
			${candidato.nome}
			</a>
		</@display.column>
		<@display.column property="pessoal.sexo" title="Sexo" style="width: 30px; text-align: center;" />
		<@display.column property="pessoal.idade" title="Idade" style="width: 30px; text-align: center;" />
		<@display.column title="Cidade/UF" >
			<#if candidato.endereco.cidade.nome?exists>
			${candidato.endereco.cidade.nome}/${candidato.endereco.uf.sigla}
			</#if>
		</@display.column>
		<@display.column property="pessoal.escolaridadeDescricao" title="Escolaridade" style="width: 180px;"/>
		<@display.column property="tempoExperiencia" title="Experiencia (meses)" style="width: 65px; text-align: center;"/>
		<@display.column title="Pretensão Salarial" style="text-align: right;">
			<#if candidato.pretencaoSalarial?exists> ${candidato.pretencaoSalarial?string(",##0.00")}</#if>
		</@display.column>
		<@display.column title="Compatibilidade" style="text-align: right;">
			<#if candidato.percentualCompatibilidade?exists> ${candidato.percentualCompatibilidade?string(",##0.00")}%</#if>
		</@display.column>
		
	</@display.table>
	<br>Total de Candidatos: ${candidatos?size}
</@ww.form>



		<#if solicitacao?exists && solicitacao.id?exists>
			<div class="buttonGroup">
				<button onclick="prepareEnviarForm();" class="btnInserirSelecionados" accesskey="I"></button>
				<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar" accesskey="V"></button>
			</div>
		</#if>
	</#if>
</body>
</html>