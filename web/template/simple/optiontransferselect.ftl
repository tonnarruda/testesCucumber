<#if !stack.findValue("#optiontransferselect_js_included")?exists>
	<script language="javascript" src="<@ww.url includeParams="none" value="/webwork/optiontransferselect/optiontransferselect.js?version=${versao}" encode='false' />"></script>
	<#assign temporaryVariable = stack.setValue("#optiontransferselect_js_included", "true") />
</#if>
<#if !stack.findValue("#buttonsOnclick_js_included")?exists>
	<script language="javascript" src="<@ww.url includeParams="none" value="/webwork/optiontransferselect/buttonsOnclick.js?version=${versao}" encode='false' />"></script>
	<#assign temporaryVariable = stack.setValue("#buttonsOnclick_js_included", "true") />
</#if>
<table border="0">
<tr>
<td>
<#if parameters.leftTitle?exists>
	<label for="leftTitle">${parameters.leftTitle}</label><br/>
</#if>
<#include "/${parameters.templateDir}/simple/select.ftl" /> </td>
<td valign="middle" align="center">
	<#if parameters.allowAddToLeft?default(true)>
		<#assign addToLeftLabel = " "/>
		<#if parameters.doubleHeaderKey?exists>
			<input type="button"
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}"
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			<#if parameters.buttonsOnclick?exists>
			 value="${addToLeftLabel}" class="btnLeft" onclick="setValuesOption(document.getElementById('${parameters.id?html}'));moveSelectedOptions(document.getElementById('${parameters.doubleId?html}'), document.getElementById('${parameters.id?html}'), false, '${parameters.doubleHeaderKey}', '');if(verifyValuesOption(document.getElementById('${parameters.id?html}'))){${parameters.buttonsOnclick?html}}" /><br/><br/>
			<#else>
			 value="${addToLeftLabel}" class="btnLeft" onclick="moveSelectedOptions(document.getElementById('${parameters.doubleId?html}'), document.getElementById('${parameters.id?html}'), false, '${parameters.doubleHeaderKey}', '')" /><br/><br/>
			</#if>
		<#else>
			<input type="button"
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}"
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			<#if parameters.buttonsOnclick?exists>
			 value="${addToLeftLabel}" class="btnLeft" onclick="setValuesOption(document.getElementById('${parameters.id?html}'));moveSelectedOptions(document.getElementById('${parameters.doubleId?html}'), document.getElementById('${parameters.id?html}'), false, '');if(verifyValuesOption(document.getElementById('${parameters.id?html}'))){${parameters.buttonsOnclick?html}}" /><br/><br/>
			<#else>
			 value="${addToLeftLabel}" class="btnLeft" onclick="moveSelectedOptions(document.getElementById('${parameters.doubleId?html}'), document.getElementById('${parameters.id?html}'), false, '')" /><br/><br/>
			</#if>
		</#if>
	</#if>
	<#if parameters.allowAddToRight?default(true)>
		<#assign addToRightLabel= " " />
		<#if parameters.headerKey?exists>
			<input type="button"
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}"
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			<#if parameters.buttonsOnclick?exists>
			 value="${addToRightLabel}" class="btnRight" onclick="setValuesOption(document.getElementById('${parameters.id?html}'));moveSelectedOptions(document.getElementById('${parameters.id?html}'), document.getElementById('${parameters.doubleId?html}'), false, '${parameters.headerKey}', '');if(verifyValuesOption(document.getElementById('${parameters.id?html}'))){${parameters.buttonsOnclick?html}}" /><br/><br/>
			<#else>
			 value="${addToRightLabel}" class="btnRight" onclick="moveSelectedOptions(document.getElementById('${parameters.id?html}'), document.getElementById('${parameters.doubleId?html}'), false, '${parameters.headerKey}', '')" /><br/><br/>
			</#if>
		<#else>
			<input type="button"
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}"
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			<#if parameters.buttonsOnclick?exists>
             value="${addToRightLabel}" class="btnRight" onclick="setValuesOption(document.getElementById('${parameters.id?html}'));moveSelectedOptions(document.getElementById('${parameters.id?html}'), document.getElementById('${parameters.doubleId?html}'), false, '');if(verifyValuesOption(document.getElementById('${parameters.id?html}'))){${parameters.buttonsOnclick?html}}" /><br/><br/>
			<#else>
			 value="${addToRightLabel}" class="btnRight" onclick="moveSelectedOptions(document.getElementById('${parameters.id?html}'), document.getElementById('${parameters.doubleId?html}'), false, '')" /><br/><br/>
			</#if>
		</#if>
	</#if>
	<#if parameters.allowAddAllToLeft?default(true)>
		<#assign addAllToLeftLabel= " " />
		<#if parameters.doubleHeaderKey?exists>
			<input type="button"
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass}"
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle}"
			</#if>
			<#if parameters.buttonsOnclick?exists>
			 value="${addAllToLeftLabel}" class="btnAllLeft" onclick="setValuesOption(document.getElementById('${parameters.id?html}'));moveAllOptions(document.getElementById('${parameters.doubleId?html}'), document.getElementById('${parameters.id?html}'), false, '${parameters.doubleHeaderKey}', '');if(verifyValuesOption(document.getElementById('${parameters.id?html}'))){${parameters.buttonsOnclick?html}}" /><br/><br/>
			<#else>
			 value="${addAllToLeftLabel}" class="btnAllLeft" onclick="moveAllOptions(document.getElementById('${parameters.doubleId?html}'), document.getElementById('${parameters.id?html}'), false, '${parameters.doubleHeaderKey}', '')" /><br/><br/>
			</#if>
		<#else>
			<input type="button"
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}"
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			<#if parameters.buttonsOnclick?exists>
			 value="${addAllToLeftLabel}" class="btnAllLeft" onclick="setValuesOption(document.getElementById('${parameters.id?html}'));moveAllOptions(document.getElementById('${parameters.doubleId?html}'), document.getElementById('${parameters.id?html}'), false, '');if(verifyValuesOption(document.getElementById('${parameters.id?html}'))){${parameters.buttonsOnclick?html}}" /><br/><br/>
			<#else>
			 value="${addAllToLeftLabel}" class="btnAllLeft" onclick="moveAllOptions(document.getElementById('${parameters.doubleId?html}'), document.getElementById('${parameters.id?html}'), false, '')" /><br/><br/>
			</#if>
		</#if>
	</#if>
	<#if parameters.allowAddAllToRight?default(true)>
		<#assign addAllToRightLabel= " " />
		<#if parameters.headerKey?exists>
			<input type="button"
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}"
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			<#if parameters.buttonsOnclick?exists>
			 value="${addAllToRightLabel}" class="btnAllRight" onclick="setValuesOption(document.getElementById('${parameters.id?html}'));moveAllOptions(document.getElementById('${parameters.id?html}'), document.getElementById('${parameters.doubleId?html}'), false, '${parameters.headerKey}', '');if(verifyValuesOption(document.getElementById('${parameters.id?html}'))){${parameters.buttonsOnclick?html}}" /><br/><br/>
			<#else>
			 value="${addAllToRightLabel}" class="btnAllRight" onclick="moveAllOptions(document.getElementById('${parameters.id?html}'), document.getElementById('${parameters.doubleId?html}'), false, '${parameters.headerKey}', '')" /><br/><br/>
			</#if>
		<#else>
			<input type="button"
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}"
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			<#if parameters.buttonsOnclick?exists>
			 value="${addAllToRightLabel}" class="btnAllRight" onclick="setValuesOption(document.getElementById('${parameters.id?html}'));moveAllOptions(document.getElementById('${parameters.id?html}'), document.getElementById('${parameters.doubleId?html}'), false, '');if(verifyValuesOption(document.getElementById('${parameters.id?html}'))){${parameters.buttonsOnclick?html}}" /><br/><br/>
			<#else>
			 value="${addAllToRightLabel}" class="btnAllRight" onclick="moveAllOptions(document.getElementById('${parameters.id?html}'), document.getElementById('${parameters.doubleId?html}'), false, '')" /><br/><br/>
			</#if>
		</#if>
	</#if>
	<#if parameters.allowSelectAll?default(true)>
		<#assign selectAllLabel=parameters.selectAllLabel?default("<*>")?html />
		<#if parameters.headerKey?exists && parameters.doubleHeaderKey?exists>
			<input type="button"
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}"
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			 value="${selectAllLabel}" onclick="selectAllOptionsExceptSome(document.getElementById('${parameters.id?html}'), 'key', '${parameters.headerKey}');selectAllOptionsExceptSome(document.getElementById('${parameters.doubleId?html}'), 'key', '${parameters.doubleHeaderKey}');" /><br/><br/>
		<#elseif parameters.headerKey?exists>
			<input type="button"
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}"
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			 value="${selectAllLabel}" onclick="selectAllOptionsExceptSome(document.getElementById('${parameters.id?html}'), 'key', '${parameters.headerKey}');selectAllOptions(document.getElementById('${parameters.doubleId?html}'));" /><br/><br/>
		<#elseif parameters.doubleHeaderKey?exists>
			<input type="button"
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}"
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			 value="${selectAllLabel}" onclick="selectAllOptions(document.getElementById('${parameters.id?html}'));selectAllOptionsExceptSome(document.getElementById('${parameters.doubleId?html}'), 'key', '${parameters.doubleHeaderKey}');" /><br/><br/>
		<#else>
			<input type="button"
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}"
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			 value="${selectAllLabel}" onclick="selectAllOptions(document.getElementById('${parameters.id?html}'));selectAllOptions(document.getElementById('${parameters.doubleId?html}'));" /><br/><br/>
		</#if>

	</#if>
</td>
<td>
<#if parameters.rightTitle?exists>
	<label for="rightTitle">${parameters.rightTitle}</label><br/>
</#if>
<select
	name="${parameters.doubleName?default("")?html}"
	<#if parameters.get("doubleSize")?exists>
	size="${parameters.get("size")?html}"
	</#if>
	<#if parameters.doubleDisabled?default(false)>
	disabled="disabled"
	</#if>
	<#if parameters.doubleMultiple?exists>
	multiple="multiple"
	</#if>
	<#if parameters.doubleTabindex?exists>
	tabindex="${parameters.tabindex?html}"
	</#if>
	<#if parameters.doubleId?exists>
	id="${parameters.doubleId?html}"
	</#if>
	<#if parameters.doubleCssClass?exists>
	class="${parameters.cssClass?html}"
	</#if>
	<#if parameters.doubleCssStyle?exists>
	style="${parameters.cssStyle?html}"
	<#else>
	 style="width: 250px"
	</#if>
    <#if parameters.doubleOnclick?exists>
    onclick="${parameters.doubleOnclick?html}"
    </#if>
    <#if parameters.doubleOndblclick?exists>
    ondblclick="${parameters.doubleOndblclick?html}"
    </#if>
    <#if parameters.doubleOnmousedown?exists>
    onmousedown="${parameters.doubleOnmousedown?html}"
    </#if>
    <#if parameters.doubleOnmouseup?exists>
    onmouseup="${parameters.doubleMnmouseup?html}"
    </#if>
    <#if parameters.doubleOnmousemove?exists>
    onmousemove="${parameters.doubleOnmousemove?html}"
    </#if>
    <#if parameters.doubleOnmouseout?exists>
    onmouseout="${parameters.doubleOnmouseout?html}"
    </#if>
    <#if parameters.doubleOnfocus?exists>
    onfocus="${parameters.doubleOnfocus?html}"
    </#if>
    <#if parameters.doubleOnblur?exists>
    onblur="${parameters.doubleOnblur?html}"
    </#if>
    <#if parameters.doubleOnkeypress?exists>
    onkeypress="${parameters.doubleOnkeypress?html}"
    </#if>
    <#if parameters.doubleOnKeydown?exists>
    onkeydown="${parameters.doubleOnkeydown?html}"
    </#if>
    <#if parameters.doubleOnkeyup?exists>
    onkeyup="${parameters.doubleOnkeyup?html}"
    </#if>
    <#if parameters.doubleOnselect?exists>
    onselect="${parameters.doubleOnselect?html}"
    </#if>
    <#if parameters.doubleOnchange?exists>
    onchange="${parameters.doubleOnchange?html}"
    </#if>
>


	<#if parameters.doubleHeaderKey?exists && parameters.doubleHeaderValue?exists>
    <option value="${parameters.doubleHeaderKey?html}">${parameters.doubleHeaderValue?html}</option>
	</#if>
	<#if parameters.doubleEmptyOption?default(false)>
    <option value=""></option>
	</#if>
	<@ww.iterator value="parameters.doubleList">
        <#if parameters.doubleListKey?exists>
            <#assign doubleItemKey = stack.findValue(parameters.doubleListKey) />
        <#else>
            <#assign doubleItemKey = stack.findValue('top') />
        </#if>
        <#assign doubleItemKeyStr = doubleItemKey.toString() />
        <#if parameters.doubleListValue?exists>
            <#assign doubleItemValue = stack.findString(parameters.doubleListValue) />
        <#else>
            <#assign doubleItemValue = stack.findString('top') />
        </#if>
    	<option value="${doubleItemKeyStr?html}"<#rt/>
        <#if tag.contains(parameters.doubleNameValue, doubleItemKey)>
 		selected="selected"<#rt/>
        </#if>
    	>${doubleItemValue?html}</option><#lt/>
	</@ww.iterator>
</select> </td>
</tr>

<#if parameters.buttonLabel1?exists || parameters.button2Label?exists>
<tr>
  <td colspan="3">
    <div align="right">
      <#if parameters.button1Label?exists>
        <input type="button" name="button1${parameters.id?html}"
        <#if parameters.button1Action?exists>
          onclick="${parameters.button1Action}"
        </#if>
        value="${parameters.button1Label}"/>
      </#if>

      <#if parameters.button2Label?exists>
        <input type="button" name="button2${parameters.id?html}"
        <#if parameters.button2Action?exists>
          onclick="${parameters.button2Action}"
        </#if>
        value="${parameters.button2Label}"/>
      </#if>
    </div>
  </td>
</tr>
</#if>

</table>
</fieldset>