 $( function() {
	constroiContainsIgnoreAccents();
    $(".search").keyup(function(e){
    	$(this).parents(".box").find(".column").find(".fatoresDeRiscoLi").hide();
    	$(this).parents(".box").find(".column").find(".fatoresDeRiscoLi").find(".nome:contains-IgnoreAccents('"+$(this).val()+"')").parent().show();
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

function openBox(titulo){
	$('#formDialogSelect').dialog({
						title:titulo, 	
						modal: true, 
						width: 750,
						buttons: 
								[
								    {
								        text: "Selecionar",
								        click: function() { 
								        	if($('#fatoresDeRisco-list .ui-selected').text() == ""){
								        		jAlert("Selecione um Fator de Risco.")
								        	}
								        	else{
								        		setFator();
								        	}  
								        }
								    },
								    {
								        text: "Sair",
								        click: function() { 
								        	$(this).dialog("close");
								        }
								    }
								]
					});
}