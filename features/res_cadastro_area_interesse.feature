# language: pt

Funcionalidade: Cadastrar Area de Interesse

  Cenário: Cadastro de Area de Interesse
    Dado que exista a área organizacional "financeiro"
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "R&S > Cadastros > Áreas de Interesse"
    Então eu devo ver o título "Áreas de Interesse"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Área de Interesse"
    E eu preencho "nome" com "testes"
    E eu marco "financeiro"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Áreas de Interesse"
    Então eu devo ver "testes"
    E eu clico em editar "testes"
    E eu devo ver o título "Editar Área de Interesse"
    E o campo "nome" deve conter "testes"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Áreas de Interesse"
    E eu clico em excluir "testes"
    Então eu devo ver "Confirma exclusão?"
    E eu aperto "OK"
    Então eu devo ver "Área de Interesse excluída com sucesso."
    E eu não devo ver "testes"