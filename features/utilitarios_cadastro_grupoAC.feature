# language: pt

Funcionalidade: Cadastrar Grupo AC

  Cenário: Cadastro do Grupo AC
    Dado que eu esteja logado com o usuário "fortes"

    Quando eu acesso o menu "Utilitários > Cadastros > Grupos AC"
    Então eu devo ver o título "Grupo AC (Utilizado no Fortes Pessoal)"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Grupo AC"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"

    Então eu devo ver o título "Grupo AC (Utilizado no Fortes Pessoal)"
    E eu clico no botão "Inserir"
    E eu preencho "Descrição" com "grupoAC"
    E eu preencho "Código" com "005"
    E eu preencho "Usuário AC" com "fortes"
    E eu preencho "Senha AC" com "fortes"
    E eu preencho "URL WS" com "http://localhost:1024/soap/IAcPessoal"
    E eu preencho "URL WSDL" com "http://localhost:1024/wsdl/IAcPessoal"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Grupo AC (Utilizado no Fortes Pessoal)"
    Então eu devo ver "grupoAC"

    Entao eu clico em editar "grupoAC"
    E eu devo ver o título "Editar Grupo AC"
    E o campo "Descrição" deve conter "grupoAC"
    E eu preencho "Descrição" com "FortesAc"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Grupo AC (Utilizado no Fortes Pessoal)"
    E eu não devo ver "grupoAC"
    Então eu devo ver "FortesAc"

    Então eu clico em excluir "FortesAc"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Grupo AC excluído com sucesso."
    Então eu não devo ver na listagem "FortesAc"