# language: pt

Funcionalidade: Lista de Candidatos da Seleção

  Cenário: Lista de Candidatos da Seleção
    Dado que eu esteja logado
    Dado que exista a etapa seletiva "prova"
    Quando eu acesso o menu "R&S > Relatórios > Lista de Candidatos da Seleção"
    Então eu devo ver o título "Lista de Candidatos da Seleção"
    E eu clico no botão "Relatorio"
    Entao eu devo ver "Não existem dados para o filtro informado."