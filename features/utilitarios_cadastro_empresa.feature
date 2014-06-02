# language: pt

Funcionalidade: Cadastrar Empresa

  Cenário: Empresa
    Dado que exista um curso "tdd"
	
    Dado que eu esteja logado com o usuário "fortes"

    Quando eu acesso o menu "Utilitários > Cadastros > Empresas"
    Então eu devo ver o título "Empresas"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Empresa"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do confirmar e clico no ok
    E eu preencho "Base CNPJ" com "12345678"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Empresas"

    E eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Empresa"
    E eu preencho "Nome" com "fortes"
    E eu preencho "Denominação Social" com "fortes sa"
    E eu seleciono "CE" de "Estado"
    E eu seleciono "Fortaleza" de "cidade"
    E eu seleciono "[(Admissões + Demissões / 2) / Ativos no final do mês anterior] * 100"
    E eu preencho "Base CNPJ" com "12345678"
    E eu preencho "Email Remetente" com "ab@com.com"
    E eu preencho "Email Resp. Setor Pessoal" com "ab@com.com"
    E eu preencho "Email Resp. RH" com "ab@com.com"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Empresas"

    Quando eu clico em editar "fortes"
    Então o campo "Nome" deve conter "fortes"
    Quando eu preencho "Nome" com "ente"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Empresas"

    Então eu clico em excluir "ente"
    E eu devo ver o alert "Deseja realmente excluir a empresa?" e clico no ok
    E eu devo ver "Empresa excluída com sucesso."
    Então eu não devo ver na listagem "ente"


