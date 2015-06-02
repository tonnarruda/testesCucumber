<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
		#menuBusca a.ativaTriagemAutomatica{border-bottom: 2px solid #5292C0;}
		.cinza { color: #BBB; }
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
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.cookie.js"/>'></script>
	<script type='text/javascript'>
		$(function() {
			$("input[name='ativaParam']").click(function() {
				var marcado = $(this).is(":checked");
				$(this).parent().parent().find("input:text").attr("disabled", !marcado);
			});
			
			$(".peso").each(function() {
				if ($(this).val() == "") {
					$(this).parent().parent().find("input:checkbox").attr("checked", false);
					$(this).attr("disabled", true);
				}
			});
			
			$("#tbl-params * :hidden").each(function() {
				if ($(this).val() == "" || ($(this).attr("name") == "solicitacao.sexo" && $(this).val() == "I"))
					$(this).parent().parent().find("input:checkbox, input:text").attr("disabled", true).val("");
			});
			
			<#if !candidatos?exists>
				$(".peso").each(function() {
					if ($.cookie($(this).attr("id")))
					 	$("#" + $(this).attr("id")).val($.cookie($(this).attr("id")));
				});
			</#if>
			
			$("#legendas").append("&nbsp;&nbsp;<span style='background-color: #009900;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Participa ou participou de processo seletivo");
		});
		
		function triar() 
		{
			$(".peso").each(function() {
				$.cookie($(this).attr("id"), $(this).val(), { expires: 90 });
			});
		
			$("input:text").css("background-color", "#FFF");
			var valido = true;
		
			$("input[name='ativaParam']").each(function() {
				var marcado = $(this).is(":checked");
				var peso = $(this).parent().parent().find(".peso:enabled");
				
				if (marcado && $(peso).val() == "") {
					$(peso).css("background-color", "#FFEEC2");
					valido = false;
				}
			});
			
			if (!valido) {
				jAlert("Informe corretamente os pesos indicados");
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
				<@ww.hidden name="solicitacao.empresa.id"/>
			</#if>

			<table cellpadding="3" cellspacing="2" id="tbl-params">
				<thead>
					<tr>
						<th>&nbsp;</th>
						<th>Peso</th>
						<th>&nbsp;</th>
						<th>&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td align="center">
							<input type="checkbox" checked="checked" disabled="disabled" name="ativaParam" />
						</td>
						<td align="center">
							<@ww.textfield theme="simple" name="pesos['cargo']" id="pesoCargo" cssStyle="width:30px; text-align:right;" liClass="liLeft" cssClass="peso" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
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
							<@ww.textfield theme="simple" name="pesos['escolaridade']" id="pesoEscolaridade" cssStyle="width:30px; text-align:right;" liClass="liLeft" cssClass="peso" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
						</td>
						<td>Escolaridade</td>
						<td>
							<@ww.hidden name="solicitacao.escolaridade"/>
							<#if solicitacao.escolaridade?exists && solicitacao.escolaridade != "">
								${escolaridades.get('${solicitacao.escolaridade}')}
							<#else>
								<span class="cinza">&lt;indefinido&gt;</span>
							</#if>
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="checkbox" checked="checked" name="ativaParam" />
						</td>
						<td align="center">
							<@ww.textfield theme="simple" name="pesos['cidade']" id="pesoCidade" cssStyle="width:30px; text-align:right;" liClass="liLeft" cssClass="peso" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
						</td>
						<td>Cidade</td>
						<td>
							<@ww.hidden name="solicitacao.cidade.id"/>
							<#if solicitacao.cidade?exists && solicitacao.cidade.nome?exists && solicitacao.cidade.uf?exists && solicitacao.cidade.uf.sigla?exists>
								${solicitacao.cidade.nome}/${solicitacao.cidade.uf.sigla}
							<#else>
								<span class="cinza">&lt;indefinido&gt;</span>
							</#if>
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="checkbox" checked="checked" name="ativaParam" />
						</td>
						<td align="center">
							<@ww.textfield theme="simple" name="pesos['sexo']" id="pesoSexo" cssStyle="width:30px; text-align:right;" liClass="liLeft" cssClass="peso" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
						</td>
						<td>Sexo</td>
						<td>
							<@ww.hidden name="solicitacao.sexo"/>
							<#if solicitacao.sexo?exists && solicitacao.sexo != "I">
								${sexos.get('${solicitacao.sexo}')}
							<#else>
								<span class="cinza">&lt;indefinido&gt;</span>
							</#if>
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="checkbox" checked="checked" name="ativaParam" />
						</td>
						<td align="center">
							<@ww.textfield theme="simple" name="pesos['idade']" id="pesoIdade" cssStyle="width:30px; text-align:right;" liClass="liLeft" cssClass="peso" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
						</td>
						<td>Faixa etária</td>
						<td>
							<@ww.hidden name="solicitacao.idadeMinima"/>
							<@ww.hidden name="solicitacao.idadeMaxima"/>
							<#if solicitacao.idadeMinima?exists && solicitacao.idadeMaxima?exists>
								${solicitacao.idadeMinima} a ${solicitacao.idadeMaxima} anos
							<#else>
								<span class="cinza">&lt;indefinido&gt;</span>
							</#if>
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="checkbox" checked="checked" name="ativaParam" />
						</td>
						<td align="center">
							<@ww.textfield theme="simple" name="pesos['pretensaoSalarial']" id="pesoPretensaoSalarial" cssStyle="width:30px; text-align:right;" liClass="liLeft" cssClass="peso" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
						</td>
						<td>Remuneração</td>
						<td>
							<@ww.hidden name="solicitacao.remuneracao"/>
							<#if solicitacao.remuneracao?exists>
								${solicitacao.remuneracao?string(",##0.00")}
							<#else>
								<span class="cinza">&lt;indefinido&gt;</span>
							</#if>
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="checkbox" checked="checked" name="ativaParam" />
						</td>
						<td align="center">
							<@ww.textfield theme="simple" name="pesos['tempoExperiencia']" id="pesoTempoExperiencia" cssStyle="width:30px; text-align:right;" liClass="liLeft" cssClass="peso" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
						</td>
						<td>Experiência em meses</td>
						<td>
							<@ww.textfield name="tempoExperiencia" id="tempoExperiencia" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="3" onkeypress="return somenteNumeros(event,'');"/>
						</td>
					</tr>
				</tbody>
			</table>

			<li style="clear:both;"><br /></li>
			
			<label>Percentual Mínimo de Compatibilidade: *<label>
			<@ww.textfield theme="simple" name="percentualMinimo" id="percentualMinimo" onkeypress = "return somenteNumeros(event,'');" maxLength="3" required="true" cssStyle="width: 30px; text-align:right;" />
			%
			
			<div class="buttonGroup">
				<input type="button" value="" class="btnPesquisar grayBGE" onclick="triar();">
			</div>
		</@ww.form>

	<#include "../util/bottomFiltro.ftl" />
	<br />

	<#if BDS?exists && !BDS && solicitacao?exists && solicitacao.id?exists>
		<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar" accesskey="V">
		</button>
	</#if>


	<#if candidatos?exists >
		<div id="legendas" align="right"></div>
		<br>
		<@ww.form name="formCand" id="formCand" action="insertCandidatos.action" validate="true" method="POST">
			<#if BDS?exists && !BDS>
				<@ww.hidden name="solicitacao.id"/>
			</#if>
			
			<@display.table name="candidatos" id="candidato" class="dados" >
		
				<#if candidato.inscritoSolicitacao?exists && candidato.inscritoSolicitacao>
					<#assign classe="candidanoNaSelecao"/>
				<#else>
					<#assign classe=""/>
				</#if>
		
				<#if solicitacao?exists && solicitacao.id?exists>
					<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(document.formCand);' />" style="width: 30px; text-align: center;" >
						<input type="checkbox" value="${candidato.id?string?replace(".", "")?replace(",","")}" name="candidatosId" cpf="${candidato.pessoal.cpf}" />
					</@display.column>
				</#if>
				
				<@display.column title="Nome">
					<a title="Ver Informação" class="${classe}" href="javascript:popup('<@ww.url includeParams="none" value="/captacao/candidato/infoCandidato.action?candidato.id=${candidato.id?string?replace('.', '')}"/>', 580, 750)" cpf="${candidato.pessoal.cpf}">
						${candidato.nome}
					</a>
				</@display.column>
				<@display.column property="pessoal.sexo" title="Sexo" style="width: 30px; text-align: center;" class="${classe}"/>
				<@display.column property="pessoal.idade" title="Idade" style="width: 30px; text-align: center;" class="${classe}"/>
				<@display.column title="Cidade/UF" class="${classe}">
					<#if candidato.endereco.cidade.nome?exists>
					${candidato.endereco.cidade.nome}/${candidato.endereco.uf.sigla}
					</#if>
				</@display.column>
				<@display.column property="pessoal.escolaridadeDescricao" title="Escolaridade" style="width: 180px;" class="${classe}"/>
				<@display.column property="tempoExperiencia" title="Experiencia (meses)" style="width: 65px; text-align: center;" class="${classe}"/>
				<@display.column title="Pretensão Salarial" style="text-align: right;" class="${classe}">
					<#if candidato.pretencaoSalarial?exists> ${candidato.pretencaoSalarial?string(",##0.00")}</#if>
				</@display.column>
				<@display.column title="Compatibilidade" style="text-align: right;" class="${classe}">
					<#if candidato.percentualCompatibilidade?exists> 
						<#if 90 <= candidato.percentualCompatibilidade?int>
							<#assign color="green"/>
						<#elseif 50 <= candidato.percentualCompatibilidade?int> 
							<#assign color="orange"/>
						<#else>
							<#assign color="red"/>
						</#if>
						
						<div style="background-color: ${color}; width: ${candidato.percentualCompatibilidade?int}px; height: 3px;"></div>
						<div style="clear: both"></div>
						${candidato.percentualCompatibilidade?string(",##0.00")}%
					</#if>
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