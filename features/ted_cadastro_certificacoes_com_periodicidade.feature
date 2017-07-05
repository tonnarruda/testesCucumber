# language: pt

Funcionalidade: Cadastrar Certificações dos Cursos

  Cenário: Certificações dos Cursos
    Dado que exista um curso "tdd"
    
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "Utilitários > Cadastros > Empresas"
    Entao eu clico em editar "Empresa Padrão"
    E eu preencho "Resp. RH" com "sl@teste.com"
    E eu seleciono "CE" de "Estado"
    E eu seleciono "Periodicidade da certificação" de "Controlar vencimento da certificação por"
    E eu clico no botão "Gravar"

    Quando eu acesso o menu "T&D > Cadastros > Avaliação Prática"
    Então eu devo ver o título "Avaliação Prática"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Avaliação Prática"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"

    Então eu devo ver o título "Avaliação Prática"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Avaliação Prática"
    E eu preencho "Título" com "Pular Corda"
    E eu preencho "Nota Mínima para Aprovação" com "8"
    E eu clico no botão "Gravar"

    Quando eu acesso o menu "T&D > Cadastros > Certificações"
    Então eu devo ver o título "Certificações"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Certificação"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Certificações"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Certificação"
    E eu preencho "Periodicidade em meses" com "3"
    E eu devo ver "Avaliações Práticas"
    E eu marco "Pular Corda"
    E eu preencho "Nome" com "cbts"
    E eu marco "tdd"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Certificações"
    E eu devo ver "cbts"

    Entao eu clico em editar "cbts"
    E eu devo ver o título "Editar Certificação"
    E o campo "Nome" deve conter "cbts"
    E o campo "Nome" deve conter "cbts"

    E eu preencho "Nome" com "tester"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Certificações"

    Então eu clico em excluir "tester"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Certificação excluída com sucesso."
    E eu não devo ver "tester"