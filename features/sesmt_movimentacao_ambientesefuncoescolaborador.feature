# language: pt

Funcionalidade: Ambientes e Funções do Colaborador

  Cenário: Cadastro de Ambientes e Funções do Colaborador
    Dado que exista um colaborador "geraldo", da area "administracao", com o cargo "desenvolvedor" e a faixa salarial "I"
    Dado que exista uma funcao "programador"
    Dado que exista um ambiente "desenvolvimento" com o risco "ergonomia"
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "SESMT > Movimentações > Ambientes e Funções do Colaborador"
    Então eu devo ver o título "Ambientes e Funções do Colaborador"
    E eu preencho "Nome" com "Gera"
    E eu clico no botão "Pesquisar"
    Então eu seleciono "geraldo - 123.213.623-91" de "Colaborador"
    E eu devo ver "desenvolvedor I"

    Então eu seleciono "programador" de "historicoColaboradors[0].funcao.id"
    E eu seleciono "desenvolvimento" de "historicoColaboradors[0].ambiente.id"
    
    Então eu clico no botão "Gravar"
    E eu devo ver "Ambientes e funções do colaborador gravados com sucesso."
