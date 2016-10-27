# language: pt

Funcionalidade: Cadastrar Faturamento

  Cenário: Cadastro de Faturamentos
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "C&S > Movimentações > Faturamento Mensal"
    Então eu devo ver o título "Faturamentos"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Faturamento"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Faturamentos"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Faturamento"
    E eu preencho o campo (JS) "Mês/Ano" com "02/2010"    
    E eu preencho "Valor" com "200"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Faturamentos"
    E eu devo ver "02/2010"
    E eu devo ver "200"
    
    Entao eu clico em editar "02/2010"
    E eu devo ver o título "Editar Faturamento"
    E o campo "Mês/Ano" deve conter "02/2010"
    E eu preencho "Valor" com "300"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Faturamentos"
    E eu devo ver "02/2010"
    E eu devo ver "300"