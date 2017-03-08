<style type="text/css">
	@import url('<@ww.url value="/css/font-awesome.min.css"/>');
</style>
<script type="text/javascript">
	$(function() {
		var curHeight = 0;
		if($('.info').height())
			curHeight = $('.info').height();
		else if($('.warning').height())
			curHeight = $('.warning').height();
		else if($('.success').height())
			curHeight = $('.success').height();
		
		if(curHeight > 75){
			$('.expande').append('<div id="expandir" style="text-align:center;"><i class="fa fa-chevron-circle-down down fa-lg" aria-hidden="true"></i></div>');
			$('.expande').css({ overflow: "hidden", height: "75px" });
			$('#expandeMsg').css({ overflow: "hidden", height: "54px" });
			
			$('#expandir').click(function() {
				$('#expandeMsg').height(curHeight).animate({height: $("#expandeMsg")[0].scrollHeight}, 2000);
				$('.expande').height(curHeight).animate({height: $("#expandeMsg")[0].scrollHeight}, 2000);
			});
		}
	});
</script>

<#if (actionMessages?exists && actionMessages?size > 0)>
	<div class="info expande">
		<div style="float:right;"><a title="Ocultar" href="javascript:;" onclick="$(this).parent().parent().remove();">x</a></div>
		<div id="expandeMsg">
			<ul>
				<#list actionMessages as msg>
					<li>${msg}</li>
				</#list>
			</ul>
		</div>
	</div>
</#if>

<#if (actionWarnings?exists && actionWarnings?size > 0)>
	<div class="warning expande">
		<div style="float:right;"><a title="Ocultar" href="javascript:;" onclick="$(this).parent().parent().remove();">x</a></div>
		<div id="expandeMsg">
			<ul>
				<#list actionWarnings as msg>
					<li>${msg}</li>
				</#list>
			</ul>
		</div>
	</div>
</#if>

<#if (actionSuccess?exists && actionSuccess?size > 0)>
	<div class="success expande">
		<div style="float:right;"><a title="Ocultar" href="javascript:;" onclick="$(this).parent().parent().remove();">x</a></div>
		<div id="expandeMsg">
			<ul>
				<#list actionSuccess as msg>
					<li>${msg}</li>
				</#list>
			</ul>
		</div>
	</div>
</#if>

<#if (actionErrors?exists && actionErrors?size > 0)>
	<div class="error expande">
		<div style="float:right;"><a title="Ocultar" href="javascript:;" onclick="$(this).parent().parent().remove();">x</a></div>
		<div id="expandeMsg">
			<ul>
				<#list actionErrors as msg>
					<li>${msg}</li>
				</#list>
			</ul>
		</div>
	</div>
</#if>