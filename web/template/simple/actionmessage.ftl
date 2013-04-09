<#if (actionMessages?exists && actionMessages?size > 0)>
	<div class="info">
		<div style="float:right;"><a title="Ocultar" href="javascript:;" onclick="$(this).parent().parent().remove();">x</a></div>
		<ul>
			<#list actionMessages as msg>
				<li>${msg}</li>
			</#list>
		</ul>
	</div>
</#if>

<#if (actionWarnings?exists && actionWarnings?size > 0)>
	<div class="warning">
		<div style="float:right;"><a title="Ocultar" href="javascript:;" onclick="$(this).parent().parent().remove();">x</a></div>
		<ul>
			<#list actionWarnings as msg>
				<li>${msg}</li>
			</#list>
		</ul>
	</div>
</#if>

<#if (actionSuccess?exists && actionSuccess?size > 0)>
	<div class="success">
		<div style="float:right;"><a title="Ocultar" href="javascript:;" onclick="$(this).parent().parent().remove();">x</a></div>
		<ul>
			<#list actionSuccess as msg>
				<li>${msg}</li>
			</#list>
		</ul>
	</div>
</#if>