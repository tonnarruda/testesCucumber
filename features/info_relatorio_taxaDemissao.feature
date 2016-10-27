# language: pt

Funcionalidade: Relatório de colaboradores que fizeram um treinamento

  Cenário: Relatório de taxa de demissão
    Dado que eu esteja logado com o usuário "SOS"
    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Taxa de Demissão"
    Então eu devo ver o título "Taxa de Demissão"
    E eu clico no botão "Relatorio"
    E eu devo ver o alert "Preencha os campos indicados." e clico no ok

    E eu preencho o campo (JS) "dataDe" com "01/2015"
	E eu preencho o campo (JS) "dataAte" com "12/2015"        
    E eu clico no botão "Relatorio"
    E eu não devo ver "Não é permitido um período maior que 12 meses para a geração deste relatório"

    E eu preencho o campo (JS) "dataAte" com "12/2016"
    E eu clico no botão "Relatorio"
    E eu devo ver "Não é permitido um período maior que 12 meses para a geração deste relatório"
    
    