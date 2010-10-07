<#if ((actionErrors?exists && actionErrors?size > 0) || (fieldErrors?exists && fieldErrors?size > 0))>
	<div class="errorMessage">
		<table>
			<tr>
				<td>
					<img src="<@ww.url includeParams="none" value="/imgs/erro_msg.gif"/>">
				</td>
				<td>
					<#if (actionErrors?exists && actionErrors?size > 0)>
						<ul>
							<#list actionErrors as error>
								<li>${error}</li>
							</#list>
						</ul>
					</#if>
				</td>
			</tr>
		</table>
	</div>
</#if>