<br />
<fieldset>
	<legend>Avaliar as Competências do Colaborador para o Cargo</legend><br />
	
	<div id="legendas">
		<#if colaboradorQuestionario.avaliacaoDesempenho.exibirNivelCompetenciaExigido >
			<span style='background-color: #BFC0C3;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Nível de Competência exigido para o Cargo/Faixa Salarial
			<br /><br />
		</#if>
		<span style='background-color: #E4F0FE;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Nível de Competência do Colaborador
		<br /><br />
		<span style='background-color: #A4E2DB;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Nível de Competência do Colaborador é igual ao nível exigido para o Cargo/Faixa Salarial
	</div>

	<br /><br />
	<#if exibirPerformance>
		<pre id="performanceCompetencias" style="text-align:right; font-weight: bold;">Performance Questionário: - </pre>
	</#if>
	<table id="configuracaoNivelCompetencia" class="dados">
		<thead>
			<tr>
				<th><input type="checkbox" id="checkAllCompetencia"> Competência/Comportamento para avaliação</th>
				<#list nivelCompetencias as nivel>
					<th>${nivel.descricao}</th>
				</#list>
				
				<#if exibirPerformance>
					<th>Performance(%)</th>
				</#if>
			</tr>
		</thead>
		<tbody>
			<#assign i = 0/>
			<#list niveisCompetenciaFaixaSalariais as configuracaoNivelCompetencia>
				<#assign hasCriterios = (configuracaoNivelCompetencia.criteriosAvaliacaoCompetencia.size() >0) />
				<tr class="even">
					<td>
						<@ww.hidden name="niveisCompetenciaFaixaSalariais[${i}].tipoCompetencia"/>
						<@ww.hidden name="niveisCompetenciaFaixaSalariais[${i}].pesoCompetencia" id="peso"/>
						<#-- não utilizar decorator no hidden abaixo -->
						<input type="hidden" name="niveisCompetenciaFaixaSalariais[${i}].nivelCompetencia.ordem" id="ordem_${i}" class="ordem" value=""/>
						
						<input type="checkbox" id="competencia_${i}" name="niveisCompetenciaFaixaSalariais[${i}].competenciaId"
						<#if hasCriterios > onclick="return false;" disabled="disabled" class="checkCompetencia checados"<#else> class="checkCompetencia changed checados" </#if>
						value="${configuracaoNivelCompetencia.competenciaId}" />
						
						<label for="competencia_${i}">${configuracaoNivelCompetencia.competenciaDescricao}</label>
						
						<#if configuracaoNivelCompetencia.competenciaObservacao?exists && configuracaoNivelCompetencia.competenciaObservacao != "">
							<img id="competencia_${i}_obs" onLoad="toolTipCompetenciaObs(${i}, '${configuracaoNivelCompetencia.competenciaObservacao?j_string?replace('\"','$#-')?replace('\'','\\\'')}')" src="<@ww.url value='/imgs/help-info.gif'/>" width='16' height='16' style='margin-left: 0px;margin-top: 0px;vertical-align: top;'/>
						</#if>
												
					</td>
					
					<#list nivelCompetencias as nivel>
						<#if colaboradorQuestionario.avaliacaoDesempenho.exibirNivelCompetenciaExigido && configuracaoNivelCompetencia?exists && configuracaoNivelCompetencia.nivelCompetencia?exists && configuracaoNivelCompetencia.nivelCompetencia.id?exists && configuracaoNivelCompetencia.nivelCompetencia.id == nivel.id>
							<#assign class="nivelFaixa"/>
							<#assign bgcolor="background-color: #BFC0C3;"/>
						<#else>
							<#assign class=""/>
							<#assign bgcolor=""/>
						</#if>
						
						<#if nivel.percentual?exists>
							<#assign nivelPercentual="${nivel.percentualString}"/>
						<#else>
							<#assign nivelPercentual=""/>
						</#if>
						
						<td style="${bgcolor} width: 100px; text-align: center;" class="${class}">
							<input type="radio" disabled="disabled" percentual="${nivelPercentual}" ordem="${nivel.ordem}"
							<#if hasCriterios > onclick="return false;"  class="checkNivel radio" <#else>  class="checkNivel changed radio" </#if>
							id="niveisCompetenciaFaixaSalariais_${i}" name="niveisCompetenciaFaixaSalariais[${i}].nivelCompetencia.id" value="${nivel.id}" onchange="setOrdem(${i}, ${nivel.ordem})"/>
						</td>
					</#list>
					
					<#if exibirPerformance>
						<td style="width: 100px; text-align: center;" class="${class}">
							<label class="performance">-</label>
						</td>
					</#if>
					
				</tr>
				
				<#assign y = 0/>
				<#list configuracaoNivelCompetencia.criteriosAvaliacaoCompetencia as criterio>
					<tr class="odd">
						<td style="padding: 5px 25px;">
							<input type="hidden" name="niveisCompetenciaFaixaSalariais[${i}].configuracaoNivelCompetenciaCriterios[${y}].nivelCompetencia.ordem" id="ordemNivel_criterio_${i}_${y}" class="ordem" value=""/>
							<input type="hidden" name="niveisCompetenciaFaixaSalariais[${i}].configuracaoNivelCompetenciaCriterios[${y}].criterioDescricao" value="${criterio.descricao}" />
							<input type="checkbox" id="competencia_${i}_criterio_${y}" name="niveisCompetenciaFaixaSalariais[${i}].configuracaoNivelCompetenciaCriterios[${y}].criterioId" value="${criterio.id}" class="checkCompetenciaCriterio" />
							<label for="competencia_${i}_criterio_${y}">${criterio.descricao}</label>
						</td>
						
						<#list nivelCompetencias as nivelCriterio>
							<td style="width: 100px; text-align: center;">
								<input type="radio" disabled="disabled" class="checkNivelCriterio radio" competencia="${i}" percentual="${nivelCriterio.percentualString}" name="niveisCompetenciaFaixaSalariais[${i}].configuracaoNivelCompetenciaCriterios[${y}].nivelCompetencia.id" value="${nivelCriterio.id}" onchange="setOrdemNivelCriterio(${i}, ${y}, ${nivelCriterio.ordem})"/>
							</td>
						</#list>
					</tr>
					
					<#assign y = y + 1/>
				</#list>
				
				<#assign i = i + 1/>
			</#list>
	
			<#if niveisCompetenciaFaixaSalariais?exists && niveisCompetenciaFaixaSalariais?size == 0>
				<tr>
					<td colspan="15">
						<div class="info"> 
						<ul>
							<#if existConfigCompetenciaAvaliacaoDesempenho>
								<li>Não existem níveis de competências configurados para avaliar.</li>
							<#else>
								<li>Não existem níveis de competências configurados para o cargo atual do colaborador.</li>
							</#if>
						</ul>
						</div>
					</td>
				</tr>
			</#if>
			
		</tbody>
	</table>
</fieldset>