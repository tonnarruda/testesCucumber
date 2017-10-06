var countFuncoesASeremCriadasNoRH = 0;
var countFuncoesASeremCriadasNoFPessoal = 0;
var countFuncoesASeremRemovidasRH = 0;
var countFuncoesASeremRelacionadas = 0;

$(function(){
	atualizeSelectablesRH();
	atualizeSelectablesFP();
	
	constroiContainsIgnoreAccents();
	
	$(".searchRH").keyup(function(e){
		$(this).parent().parent().find(".funcoesRHLi:[removido!='true']").hide();
		$(this).parent().parent().find(".funcoesRHLi:[removido!='true']").find(".nome:contains-IgnoreAccents('" + $(this).val() + "')").parent().show();
	});
	
	$(".searchFP").keyup(function(e){
		$(this).parent().parent().find(".funcoesFPessoalLi:[removido!='true']").hide();
		$(this).parent().parent().find(".funcoesFPessoalLi:[removido!='true']").find(".nome:contains-IgnoreAccents('" + $(this).val() + "')").parent().show();
	});
	
	$(".searchAcao").keyup(function(e){
		$(this).parent().find("tr").find(".tdRH").parent().hide();
		$(this).parent().find("tr").find(".tdRH:contains-IgnoreAccents('" + $(this).val() + "')").parent().show();
		$(this).parent().find("tr").find(".tdFP:contains-IgnoreAccents('" + $(this).val() + "')").parent().show();
	});
});

function constroiContainsIgnoreAccents() {
	var accent_map = {
            'á':'a',
            'à':'a',
            'â':'a',
            'å':'a',
            'ä':'a',
            'a':'a',
            'ã':'a',
            'ç':'c',
            'é':'e',
            'è':'e',
            'ê':'e',
            'ë':'e',
            'í':'i',
            'ì':'i',
            'î':'i',
            'ï':'i',
            'ñ':'n',
            'ó':'o',
            'ò':'o',
            'ô':'o',
            'ö':'o',
            'õ':'o',
            'ú':'u',
            'ù':'u',
            'û':'u',
            'ü':'u',};


	String.prototype.replaceEspecialChars = function() {
        var ret = '', s = this.toString();
        if (!s) { return ''; }
        for (var i=0; i<s.length; i++) {
            ret += accent_map[s.charAt(i)] || s.charAt(i);
        }
        return ret;
	};

    String.prototype.contains = function(otherString) {
        return this.toString().indexOf(otherString) !== -1;
    };
	
	
    $.extend($.expr[':'], {
        'contains-IgnoreAccents' : function(elemt, idx, math) {
            var expression1 = math[3].toLowerCase(),
                semAcent1 = expression1.replaceEspecialChars(),
                expression2 = elemt.innerHTML.toLowerCase(),
                semAcent2 = expression2.replaceEspecialChars();

            return semAcent2.contains(semAcent1);             
        }
    });
}

function getRandomColor(){
	var colorR = Math.floor((Math.random() * 256));
	var colorG = Math.floor((Math.random() * 256));
	var colorB = Math.floor((Math.random() * 256));

	return "rgb(" + colorR + "," + colorG + "," + colorB + ")";
}

var colorArray = new Array() ;
contadorRH = 0;
contadorFP = 0;

function recalculaContadores(){
	if($('.funcoesFPessoalLi[associacao]').length == 0 && $('.funcoesRHLi[associacao]').length == 0){
		contadorRH = 0;
		contadorFP = 0;
		colorArray = new Array() ;
	}else if(contadorFP > 0 && $('.funcoesRHLi[associacao]').length == 0 && $('.funcoesFPessoalLi[associacao]').length == 1){
		contadorRH = 0;
		contadorFP = 1;
		colorArray = new Array() ;
		colorArray[0] = $(".funcoesFPessoalLi[associacao]").css("background");;
		$('.funcoesFPessoalLi[associacao]').attr('associacao',0);
	}else if(contadorRH > 0 && $('.funcoesFPessoalLi[associacao]').length == 0 && $('.funcoesRHLi[associacao]').length == 1){
		contadorRH = 1;
		contadorFP = 0;
		colorArray = new Array() ;
		colorArray[0] = $(".funcoesRHLi[associacao]").css("background");
		$('.funcoesRHLi[associacao]').attr('associacao',0);
	}
}

function atualizeSelectablesRH() {
	$(".funcoesRHLi").click(function(){
		
		if($('.funcoesFPessoalLi[associacao]').length > 1 && $('.funcoesRHLi[associacao]').length ==0){
			jAlert('Não é mais possível selecionar essa função, pois existem mais de uma opção selecionada na coluna "funções do Fortes Pessoal"');
			return false;
		}
		
		if(!$(this).hasClass("ui-selected")){ 
			if(contadorFP != 0 && (contadorRH - contadorFP) > 0){
				jAlert('Marque uma das opções na coluna "funções do Fortes Pessoal"');
				return false;
			}
		
			if(contadorFP == contadorRH){
				backGroudColor = getRandomColor();
				colorArray[contadorRH] = backGroudColor;
			}else{
				if(contadorRH > contadorFP){
					if(colorArray[contadorFP]){
						backGroudColor = colorArray[contadorFP];
					}else{
						backGroudColor = getRandomColor();
						colorArray[contadorRH] = backGroudColor;
					}	
				}else{
					if(colorArray[contadorRH]){
						backGroudColor = colorArray[contadorRH];
					}else{
						backGroudColor = getRandomColor();
						colorArray[contadorFP] = backGroudColor;
					}	
				}
			}
			
			$(this).addClass("ui-selected").attr('associacao', contadorRH++).css("background", backGroudColor);
		}else{
			associacao = $(this).attr('associacao');
			$(this).removeClass("ui-selected").removeAttr('associacao').css("background", "white");

			if($(".funcoesFPessoalLi[associacao='" + associacao + "']").length == 0)
				contadorRH--;
			else
				$(".funcoesFPessoalLi[associacao='" + associacao + "']").removeClass("ui-selected").removeAttr('associacao').css("background", "white");
		}
		
		botoes();
	});
}

function atualizeSelectablesFP() {
	$(".funcoesFPessoalLi").click(function(){
		
		if($('.funcoesRHLi[associacao]').length > 1 && $('.funcoesFPessoalLi[associacao]').length == 0){
			jAlert('Não é mais possível selecionar essa função, pois existem mais de uma opção selecionada na coluna "funções do RH"');
			return false;
		}
		
		if(!$(this).hasClass("ui-selected")){ 
			if(contadorRH != 0 && (contadorFP - contadorRH) > 0){
				jAlert('Marque uma das opções na coluna "funções do RH"');
				return false;
			}
		
			if(contadorFP == contadorRH){
				backGroudColor = getRandomColor();
				colorArray[contadorFP] = backGroudColor;
			}else{

				if(contadorRH > contadorFP){
					if(colorArray[contadorFP]){
						backGroudColor = colorArray[contadorFP];
					}else{
						backGroudColor = getRandomColor();
						colorArray[contadorRH] = backGroudColor;
					}	
				}else{
					if(colorArray[contadorRH]){
						backGroudColor = colorArray[contadorRH];
					}else{
						backGroudColor = getRandomColor();
						colorArray[contadorFP] = backGroudColor;
					}	
				}
			}
			
			$(this).addClass("ui-selected").attr('associacao', contadorFP++).css("background", backGroudColor);
		}else{
			associacao = $(this).attr('associacao');
			$(this).removeClass("ui-selected").removeAttr('associacao').css("background", "white");

			if($(".funcoesRHLi[associacao='" + associacao + "']").length == 0)
				contadorFP--;
			else
				$(".funcoesRHLi[associacao='" + associacao + "']").removeClass("ui-selected").removeAttr('associacao').css("background", "white");
		}
		
		botoes();
	});
}

function removeselecao(){
	$(".funcoesRHLi[associacao]").removeClass("ui-selected").removeAttr('associacao').css("background", "white");
	$(".funcoesFPessoalLi[associacao]").removeClass("ui-selected").removeAttr('associacao').css("background", "white");
}

function botoes(){
	recalculaContadores();
	funcoesFPessoalSelecionado = $('#funcoesFPessoal-list li').hasClass("ui-selected");
	funcoesRHSelecionado = $('#funcoesRH-list li').hasClass("ui-selected");
	
	if(funcoesFPessoalSelecionado && funcoesRHSelecionado){
		$('#relacionar').removeAttr('disabled').css({ opacity: 1 });
		$('#criarRH').attr('disabled', 'disabled').css({ opacity: 0.4 });
		$('#excluirRH').attr('disabled', 'disabled').css({ opacity: 0.4 });
		$('#criarPessoal').attr('disabled', 'disabled').css({ opacity: 0.4 });
	}else if(funcoesFPessoalSelecionado){
		$('#criarRH').removeAttr('disabled').css({ opacity: 1 });
		$('#excluirRH').attr('disabled', 'disabled').css({ opacity: 0.4 });
		$('#criarPessoal').attr('disabled', 'disabled').css({ opacity: 0.4 });
		$('#relacionar').attr('disabled', 'disabled').css({ opacity: 0.4 });
	}else if(funcoesRHSelecionado){
		$('#criarPessoal').removeAttr('disabled').css({ opacity: 1 });
		$('#excluirRH').removeAttr('disabled').css({ opacity: 1 });
		$('#relacionar').attr('disabled', 'disabled').css({ opacity: 0.4 });
		$('#criarRH').attr('disabled', 'disabled').css({ opacity: 0.4 });
	}else{
		$('#excluirRH').attr('disabled', 'disabled').css({ opacity: 0.4 });
		$('#criarPessoal').attr('disabled', 'disabled').css({ opacity: 0.4 });
		$('#relacionar').attr('disabled', 'disabled').css({ opacity: 0.4 });
		$('#criarRH').attr('disabled', 'disabled').css({ opacity: 0.4 });
	}
	
	if($('.funcoesRHLi:visible').length == 0 || $('.funcoesFPessoalLi:visible').length == 0)
		$('#relacionarAutomaticamente').attr('disabled', 'disabled').css({ opacity: 0.4 });
	else
		$('#relacionarAutomaticamente').removeAttr('disabled').css({ opacity: 1 });
}

function addAcaoRelacionarAutomaticamente(){
	$('.funcoesFPessoalLi:visible').each(function(i, fp){
		thisFuncaoFP = $(fp);
		nomeFuncaoFP = $(fp).text().trim().trim();
		idFuncaoFP = $(fp).find('.funcaoFP').val();
		$('.funcoesRHLi:visible').each(function(i, rh){
			if($(rh).text().trim().trim() == nomeFuncaoFP){
				nomeFuncaoRH = $(rh).text().trim().trim();
				idFuncaoRH = $(rh).find('.funcaoRH').val();
				
				$(rh).attr('removido','true').removeClass("ui-selected").hide();
				thisFuncaoFP.attr('removido','true').removeClass("ui-selected").hide();
				
				$('#acoesFuncoes-list table').append('<tr>' + addItemExcluir() +  
					'<td class="tdRH">' + nomeFuncaoRH + '</td>' +
					'<input type="hidden" class="funcoesASeremRelacionadas" name="funcoesASeremRelacionadas[' + countFuncoesASeremRelacionadas + ']" value="' + idFuncaoRH + '_' + idFuncaoFP + '"/>' +
					'<td class="tdFP">' + nomeFuncaoFP + '</td>' + 
					'</tr>'
				);
				countFuncoesASeremRelacionadas++;
				return false;
			}
		});
	});
	removeselecao();
	botoes();
}

function addAcaoRelacionar(){
	
	$(".funcoesFPessoalLi[associacao]").each(function(){
		
		associacao = $(this).attr('associacao');
		nomeFuncaoPessoalSelecionado = $(this).text().trim().trim();
		idFuncaoPessoalSelecionado = $(this).parent().find('.funcaoFP').val();
		$(this).attr('removido','true').removeClass("ui-selected").removeAttr('associacao').css("background", "white").hide();
		
		$(".funcoesRHLi[associacao]").each(function(){
			if(associacao == $(this).attr('associacao')){
				nomeFuncaoRHSelecionado = $(this).text().trim().trim();
				idFuncaoRHSelecionado = $(this).parent().find('.funcaoRH').val();
				$(this).attr('removido','true').removeClass("ui-selected").removeAttr('associacao').css("background", "white").hide();
				
				$('#acoesFuncoes-list table').append('<tr>' + addItemExcluir() +  
					'<td class="tdRH">' + nomeFuncaoRHSelecionado + '</td>' +
					'<input type="hidden" class="funcoesASeremRelacionadas" name="funcoesASeremRelacionadas[' + countFuncoesASeremRelacionadas + ']" value="' + idFuncaoRHSelecionado + '_' + idFuncaoPessoalSelecionado + '"/>' +
					'<td class="tdFP">' + nomeFuncaoPessoalSelecionado + '</td>' + 
					'</tr>'
				);
			
				countFuncoesASeremRelacionadas++;
				return false;
			}
		});
	});
	removeselecao();	
	botoes();
}

function addAcaoExcluirRH(){
	$(".funcoesRHLi[associacao]").each(function(){
	
		nomeFuncaoRHSelecionado = $(this).text().trim().trim();
		idFuncaoRHSelecionado = $(this).parent().find('.funcaoRH').val();
		$(this).attr('removido','true').removeClass("ui-selected").removeAttr('associacao').css("background", "white").hide();		
		
		$('#acoesFuncoes-list table').append('<tr>' + addItemExcluir() +  
			'<td class="tdRH">' + nomeFuncaoRHSelecionado + ' (Remover)</td>' +
			'<input type="hidden" class="funcoesASeremRemovidasRH" name="funcoesASeremRemovidasRH[' + countFuncoesASeremRemovidasRH + ']" value="' + idFuncaoRHSelecionado + '"/>' +
			'<td class="tdFP">-</td>' + 
			'</tr>'
		);
		
		countFuncoesASeremRemovidasRH++;
	});
	removeselecao();
	botoes();
}

function addAcaoCriarPessoal(){
	$(".funcoesRHLi[associacao]").each(function(){
		
		nomeFuncaoRHSelecionado = $(this).text().trim().trim();
		idFuncaoRHSelecionado = $(this).parent().find('.funcaoRH').val();
		$(this).attr('removido','true').removeClass("ui-selected").removeAttr('associacao').css("background", "white").hide();	
	
		$('#acoesFuncoes-list table').append('<tr>' + addItemExcluir() +  
			'<td class="tdRH">-</td>' +
			'<input type="hidden" class="funcoesASeremCriadasNoFPessoal" name="funcoesASeremCriadasNoFPessoal[' + countFuncoesASeremCriadasNoFPessoal + ']" value="' + idFuncaoRHSelecionado + '"/>' +
			'<td class="tdFP">' + nomeFuncaoRHSelecionado + ' (Novo) </td>' + 
			'</tr>'
		);
		
		countFuncoesASeremCriadasNoFPessoal++;
	});
	removeselecao();
	botoes();
}

function addAcaoCriarRH(){
	
	$(".funcoesFPessoalLi[associacao]").each(function(){
		nomeFuncaoPessoalSelecionado = $(this).text().trim().trim();
		idFuncaoPessoalSelecionado = $(this).parent().find('.funcaoFP').val();
		$(this).attr('removido','true').removeClass("ui-selected").removeAttr('associacao').css("background", "white").hide();
		
		$('#acoesFuncoes-list table').append('<tr>' + addItemExcluir() +  
			'<td class="tdRH">' + nomeFuncaoPessoalSelecionado + ' (Novo)</td>' +
			'<input type="hidden" class="funcoesASeremCriadasNoRH" name="funcoesASeremCriadasNoRH[' + countFuncoesASeremCriadasNoRH + ']" value="' + idFuncaoPessoalSelecionado + '"/>' +
			'<td class="tdFP">-</td>' + 
			'</tr>'
		);
		
		countFuncoesASeremCriadasNoRH++;
	});
	removeselecao();
	botoes();
}

function addItemExcluir(){
	return '<td>' +
	'<a onmouseover="$(this).find(\'i\').addClass(\'fa-2x\').css(\'color\',\'#6965ec\')" onmouseout="$(this).find(\'i\').removeClass(\'fa-2x\').css(\'color\',\'black\')" href="javascript:;"' +  
	'onclick="removerAcao($(this).parent().parent());"><i title="Excluir" class="fa fa-times fa-lg" aria-hidden="true" style="color: black;"></i>&nbsp;</a>' + 							
	'</td>';
}

function removerAcao(acao){
	if(acao.find(".funcoesASeremCriadasNoRH").length > 0){
		funcaoNome= acao.find(".tdRH").text().replace(' (Novo)','').trim();
		$('.funcoesFPessoalLi:hidden').each(function(){
			if($(this).text().trim().trim() == funcaoNome){
				$(this).removeAttr('removido').show();
			}
		});
	}else if(acao.find(".funcoesASeremCriadasNoFPessoal").length > 0){
		funcaoNome= acao.find(".tdFP").text().replace(' (Novo)','').trim();
		$('.funcoesRHLi:hidden').each(function(){
			if($(this).text().trim().trim() == funcaoNome){
				$(this).removeAttr('removido').show();
			}
		});
	}else if(acao.find(".funcoesASeremRemovidasRH").length > 0){
		funcaoNome= acao.find(".tdRH").text().replace(' (Remover)','').trim();
		$('.funcoesRHLi:hidden').each(function(){
			if($(this).text().trim().trim() == funcaoNome){
				$(this).removeAttr('removido').show();
			}
		});
	}else if(acao.find(".funcoesASeremRelacionadas").length > 0){
		funcaoNome= acao.find(".tdRH").text().trim();
		$('.funcoesRHLi:hidden').each(function(){
			if($(this).text().trim().trim() == funcaoNome){
				$(this).removeAttr('removido').show();
			}
		});
		funcaoNome= acao.find(".tdFP").text().trim();
		$('.funcoesFPessoalLi:hidden').each(function(){
			if($(this).text().trim().trim() == funcaoNome){
				$(this).removeAttr('removido').show();
			}
		});
	}
	
	botoes();
	acao.remove();
}

function removerTodasAcoes(){
	$('#acoesFuncoes-list').find('table').find('tr').remove();
	$('.funcoesFPessoalLi:hidden, .funcoesRHLi:hidden').removeAttr('removido').removeAttr('associacao').css("background", "white").show();
	$('#acoesFuncoes-list').find('table').append('<tr><th width="4%">Ações</th><th width="48%">Funções no RH</th><th width="48%">Funções do Fortes Pessoal</th></tr>');
	botoes();
}
