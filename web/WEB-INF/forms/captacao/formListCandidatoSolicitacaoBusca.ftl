<@ww.form name="formCand" id="formCand" action="insertCandidatos.action" validate="true" method="POST">

	<@ww.hidden name="solicitacao.id"/>

	<@display.table name="candidatos" id="candidato" class="dados" >
		
		<#if candidato.inscritoSolicitacao?exists && candidato.inscritoSolicitacao>
			<#assign classe="candidanoNaSelecao"/>
		<#else>
			<#assign classe=""/>
		</#if>

		<#if candidato.pessoal.cpf?exists>
			<#assign cpf = candidato.pessoal.cpf?string/>
		<#else>
			<#assign cpf= ""/>
		</#if>

		<#if solicitacao?exists && solicitacao.id?exists>
			<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(document.formCand);' />" style="width: 30px; text-align: center;" class="${classe}">
				<input type="checkbox" name="candidatosId" value="${candidato.id?string?replace(".", "")?replace(",","")}" cpf="${cpf}" />
			</@display.column>
		</#if>
		
		<@display.column title="Nome"  class="${classe}">
			<a title="Ver Informação" class="${classe}" href="javascript:popup('<@ww.url includeParams="none" value="/captacao/candidato/infoCandidato.action?candidato.id=${candidato.id?string?replace('.', '')}&palavras=${palavrasChave}&forma=${formas}"/>', 580, 750)" cpf="${cpf}">
				${candidato.nome}
			</a>
			<#if candidato.pessoal?exists && candidato.pessoal.indicadoPor?exists && candidato.pessoal.indicadoPor?trim != "">
				<span href=#  style="cursor: hand;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'Indicado por: <br>${candidato.pessoal.indicadoPor?j_string}');return false">
					<img border="0" src="<@ww.url includeParams='none' value='/imgs/favourites.gif'/>">						
				</span>
			</#if>
		</@display.column>
		<@display.column property="pessoal.escolaridadeDescricao" title="Escolaridade" style="width: 200px;" class="${classe}"/>
		<@display.column title="Cidade/UF" class="${classe}">
			<#if candidato.endereco.cidade.nome?exists && candidato.endereco.uf.sigla?exists>
				${candidato.endereco.cidade.nome}/${candidato.endereco.uf.sigla}
			</#if>
		</@display.column>
		<@display.column property="dataAtualizacao" title="Atualizado em" format="{0,date,dd/MM/yyyy}" style="width: 85px;text-align: center;" class="${classe}"/>
		<@display.column property="empresa.nome" title="Empresa" class="${classe}"/>
		<@display.column property="origemDescricao" title="Origem" style="width: 150px;" class="${classe}"/>
		
		
	</@display.table>
	<br>Total de Candidatos: ${candidatos?size}
</@ww.form>
