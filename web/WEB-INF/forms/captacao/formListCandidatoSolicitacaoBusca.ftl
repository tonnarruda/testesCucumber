<@ww.form name="formCand" action="insertCandidatos.action" validate="true" method="POST">
	<#if BDS?exists && !BDS>
		<@ww.hidden name="solicitacao.id"/>
	</#if>
	<@display.table name="candidatos" id="candidato" class="dados" >
		<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(document.formCand);' />" style="width: 30px; text-align: center;">
			<input type="checkbox" value="${candidato.id?string?replace(".", "")?replace(",","")}" name="candidatosId" />
		</@display.column>
		<@display.column title="Nome">
			<a title="Ver Informação" href="javascript:popup('<@ww.url includeParams="none" value="/captacao/candidato/infoCandidato.action?candidato.id=${candidato.id?string?replace('.', '')}&palavras=${palavrasChave}&forma=${formas}"/>', 580, 750)">
			${candidato.nome}
			</a>
			<#if candidato.pessoal?exists && candidato.pessoal.indicadoPor?exists && candidato.pessoal.indicadoPor?trim != "">
				<span href=# style="cursor: hand;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'Indicado por: <br>${candidato.pessoal.indicadoPor?j_string}');return false">
					<img border="0" src="<@ww.url includeParams="none" value="/imgs/favourites.gif"/>">						
				</span>
			</#if>
		</@display.column>
		<@display.column property="pessoal.escolaridadeDescricao" title="Escolaridade" style="width: 200px;"/>
		<@display.column title="Cidade/UF" >
			<#if candidato.endereco.cidade.nome?exists>
			${candidato.endereco.cidade.nome}/${candidato.endereco.uf.sigla}
			</#if>
		</@display.column>
		<@display.column property="dataAtualizacao" title="Atualizado em" format="{0,date,dd/MM/yyyy}" style="width: 85px;text-align: center;"/>
		<@display.column property="empresa.nome" title="Empresa" />
		<@display.column property="origemDescricao" title="Origem" style="width: 150px;"/>
	</@display.table>
	<br>Total de Candidatos: ${candidatos?size}
</@ww.form>
