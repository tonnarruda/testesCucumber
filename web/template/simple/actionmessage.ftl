<#if (actionMessages?exists && actionMessages?size > 0)>

<div class="actionMessage">
	<table>
		<tr>
			<td align="center">
				<img valign="absmiddle" src="<@ww.url includeParams="none" value="/imgs/message_info.gif"/>" alt="Imagem de Aviso" width="32" height="32"/>
			</td>
			<td>
				<#list actionMessages as msg>
					<p>${msg}</p>
				</#list>
			</td>
		</tr>
	</table>
</div>

</#if>
