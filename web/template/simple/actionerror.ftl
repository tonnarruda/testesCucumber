<#if (actionErrors?exists && actionErrors?size > 0)>
	<div class="error">
		<div style="float:right;"><a title="Ocultar" href="javascript:;" onclick="$(this).parent().parent().remove();">x</a></div>
		<ul>
			<#list actionErrors as msg>
				<li>${msg}</li>
			</#list>
		</ul>
	</div>
</#if>