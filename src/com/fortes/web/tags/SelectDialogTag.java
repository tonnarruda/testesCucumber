package com.fortes.web.tags;

import java.lang.reflect.Method;
import java.util.Collection;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
public class SelectDialogTag extends TagSupport
{
	private String label = "";
	private String id;
	private String name;
	private String list;
	private String listKey = "id";
	private String listValue = "nome";
	private String img;
	private String tituloDialog;
	private String width = "480px";
	private String heightDialog = "600px";
	private String onclick = "";
	private String required = "false";
	private boolean addRemover;
	
	public SelectDialogTag(){
		super();
	}

	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException
	{
		try {
			if(id == null || name == null || list == null)
				return 0;
			
			ServletRequest request = pageContext.getRequest();
			Collection<Object> objects = ((Collection<Object>) request.getAttribute(list));
			Object objectValor = (Object) request.getAttribute(name);
			
			String itemSelecionadoValor = "";
			String itemSelecionadoDescricao = "";

			if(objectValor != null && objectValor.toString() != "[]")
				itemSelecionadoValor = objectValor.toString();
			
			if(tituloDialog == null)
				tituloDialog = label;
			
			String labelFormatado = label + (StringUtils.isBlank(label)?"":":");
			if("true".equals(required))
				labelFormatado+= "*";
			
			StringBuilder selectGroup = new StringBuilder();
			StringBuilder dialog = new StringBuilder();
			
			if(listKey!= null && listValue!= null && !"".equals(listKey) && !"".equals(listValue)){
				Method methodKey = null;
				Method methodValue = null;
				if(objects != null && !objects.isEmpty()){
					methodKey = objects.toArray()[0].getClass().getMethod("get" +  StringUtils.capitalize(listKey));
					methodValue = objects.toArray()[0].getClass().getMethod("get" +  StringUtils.capitalize(listValue));
				}
				
				dialog.append("<div id='formSelectDialog" + id + "' variacao='0' style='display:none; width: 600px; list-style-type: none;'>");
				dialog.append("	<div class='box' style='height: " + heightDialog + "; max-height: " + heightDialog + "'>");
				dialog.append("		<div class='box-search' style='display: block; padding: 5px; color: #A1A1A1; background: #F9F9F9; border-left: 1px solid #e7e7e7; border-right: 1px solid #e7e7e7;'>");
				dialog.append("			<input type='text' class='search' placeholder='Pesquisar...' style='width: 90%;	border-radius: 3px;	padding: 3px 2%; margin: 0 3%; font-family: sans-serif !important; border-color: #C8C8C8;'>");
				dialog.append("		</div>");
				dialog.append("		<div class='column'>");
				dialog.append("		    <ol id='" + id + "-list' class='listSelectDialog'>");

				
				String descricao = "";
				String valor = "";
				
				if(objects != null){
					for (Object object : objects){
						valor = methodKey.invoke(object,new Object[]{}).toString();
						descricao = methodValue.invoke(object,new Object[]{}).toString();
						
						dialog.append("			<li class='ui-widget-content " + id + "Li'>");
						dialog.append("				<input type='hidden' class='selecionadoDialog' value='" + valor + "'/>");
						dialog.append("				<div class='nome'>" + descricao + "</div>");
						dialog.append("				<div style='clear:both;float: none;'></div>");
						dialog.append("			</li>");
						
						if(!"".equals(valor) && itemSelecionadoValor.equals(valor))
							itemSelecionadoDescricao = descricao;
					}
				}
				dialog.append("		    </ol>");
				dialog.append("		</div>");
				dialog.append("	</div>");
				dialog.append("</div>");
			}
			
			selectGroup.append("<div id='block"+ id + "' nomename='" + name + "' possuiitemremover='" + addRemover + "' style='margin-top: 4px;'>");
			selectGroup.append("	<label> "+ labelFormatado +" </label>");

			if(img != null && !"".equals(img))
				selectGroup.append("<img border='0' src='/fortesrh/imgs/" + img + "' style='margin-top: -11px; margin-bottom: -5px; width: 27px; height: 27px;'/>");

			selectGroup.append("	<div id='divSelectDialog" + id + "0' class='divSelectDialog' style='width: " + width + ";text-align: justify;border: 1px solid #BEBEBE;padding: 10px;margin-bottom: 4px;border-radius: 3px;background-color: #f3f3f3;'>");
			
			if("".equals(itemSelecionadoValor)){
				selectGroup.append("		<span class='openSelectDialog' onclick=\"openSelectDialog('" + tituloDialog + "','" + id + "',0);" + onclick + ";\" style='cursor: pointer; color: #1c96e8;'>");
				selectGroup.append("			<i class='fa fa-plus-circle' aria-hidden='true' style='font-size: 16px;'></i> Selecione um Ítem");
				selectGroup.append("		</span>");
			}else{
				if(addRemover){
					selectGroup.append("<a class='fontOuser' onmouseover=\"$(this).find('i').addClass('fa-2x').css('color','#6965ec')\" onmouseout=\"$(this).find('i').removeClass('fa-2x').css('color','black')\" ");
					selectGroup.append("style='cursor:pointer; float: right; margin: -10px -10px -80px 595px;' ");
					selectGroup.append("onclick=\"$(this).parent().parent().find('input').remove();$(this).parent().find('span').remove();addSelecioneItemSelectDialog('" + tituloDialog + "','" + id + "','0')\"> ");
					selectGroup.append("<i title='Remover' class='fa fa-times fa-lg' aria-hidden='true'></i>&nbsp;</a>");
				}
				selectGroup.append("		<span class='openSelectDialog' title='Modificar Ítem' onclick=\"openSelectDialog('" + tituloDialog + "','" + id + "',0);" + onclick + ";\">");
				selectGroup.append("			<i class='fa fa-pencil-square-o' aria-hidden='true' style='color: #1c96e8;font-size: 16px;cursor: pointer;'></i>");
				selectGroup.append("		</span>");
				selectGroup.append("		<span id='itemSelecionadoDescricao" + id + "' style='margin-left: 8px;'>" + itemSelecionadoDescricao + "</span>");
				selectGroup.append("		<input type='hidden' id='" + id + "0' class='selectDialog' name='" + name + "' value='" + itemSelecionadoValor + "'/>");
			}
			selectGroup.append("	</div>");
			selectGroup.append("</div> ");
			selectGroup.append(dialog);
			
			pageContext.getOut().print(selectGroup);			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public String getListKey() {
		return listKey;
	}

	public void setListKey(String listKey) {
		this.listKey = listKey;
	}

	public String getListValue() {
		return listValue;
	}

	public void setListValue(String listValue) {
		this.listValue = listValue;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getTituloDialog() {
		return tituloDialog;
	}

	public void setTituloDialog(String tituloDialog) {
		this.tituloDialog = tituloDialog;
	}

	public String getHeightDialog() {
		return heightDialog;
	}

	public void setHeightDialog(String heightDialog) {
		this.heightDialog = heightDialog;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public boolean isAddRemover() {
		return addRemover;
	}

	public void setAddRemover(boolean addRemover) {
		this.addRemover = addRemover;
	}
	
	public void setAddRemover(String addRemover) {
		if("true".equals(addRemover))
			this.addRemover = true;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}
}