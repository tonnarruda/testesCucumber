<!--
 * autor Moésio Medeiros
 * data: 23/06/2006
 * Requisito: RFA029 - Cadastro de curriculum
-->

<table width="100%" id="detail">
	<tr>
		<td>
		<!--- FORMACAO --->
			<span <#if SESSION_TIPO_DETALHE=="FORMACAO">class="active"<#else>class="inactive"</#if>>
			 <#if SESSION_TIPO_DETALHE=="FORMACAO">
				<span class="header">
					Formações
				</span>
			 <#else>
				<a href="../formacao/list.action" class="header" accesskey="F">
					Formações
				</a>
			 </#if>
			</span>
			
		<!--- EXPERIENCIA --->
			<span <#if SESSION_TIPO_DETALHE=="EXPERIENCIA">class="active"<#else>class="inactive"</#if>>
			 <#if SESSION_TIPO_DETALHE=="EXPERIENCIA">
				<span class="header">
					Experiências
				</span>
			 <#else>
				<a href="../experiencia/list.action" class="header" accesskey="E">
					Experiências
				</a>
			 </#if>
			</span>
			
		<!--- CURSOS --->
			<span <#if SESSION_TIPO_DETALHE=="CURSO">class="active"<#else>class="inactive"</#if>>
			 <#if SESSION_TIPO_DETALHE=="CURSO">
				<span class="header">
					Cursos
				</span>
			 <#else>
				<a href="../curso/list.action" class="header" accesskey="C">
					Cursos
				</a>
			 </#if>
			</span>
			
		<!--- IDIOMAS --->
			<span <#if SESSION_TIPO_DETALHE=="IDIOMA">class="active"<#else>class="inactive"</#if>>
			 <#if SESSION_TIPO_DETALHE=="IDIOMA">
				<span class="header">
					Idiomas
				</span>
			 <#else>
				<a href="../idioma/list.action" class="header" accesskey="I">
					Idiomas
				</a>
			 </#if>
			</span>
			
		</td>
	</tr>
</table>
