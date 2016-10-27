# language: pt

Funcionalidade: Lista de Candidatos da Seleção

  Cenário: Lista de Candidatos da Seleção
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista a etapa seletiva "prova"
    Quando eu acesso o menu "R&S > Relatórios > Lista de Candidatos da Seleção"
    Então eu devo ver o título "Lista de Candidatos da Seleção"
    E eu preencho o campo (JS) "dataIni" com "01/01/2013"
    E eu preencho o campo (JS) "dataFim" com "01/02/2013"
    
    E eu clico no botão "Relatorio"
    
    Entao eu devo ver "Não existem dados para o filtro informado."