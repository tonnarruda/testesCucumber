# language: pt

Funcionalidade: Importar Cadastros

  Cenário: Importar Cadastros
    Dado que exista uma empresa "Empresa Modelo"

    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "Utilitários > Importar Cadastros"
    Então eu devo ver o título "Importar Cadastros"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    
    Então eu seleciono "Empresa Padrão" de "Origem"
    E eu seleciono "Empresa Padrão" de "Destino"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert "Selecione empresas Origem e Destino diferentes." e clico no ok

    Então eu seleciono "Empresa Padrão" de "Origem"
    E eu seleciono "Empresa Modelo" de "Destino"
    E eu clico no botão "Gravar"
    E eu devo ver o alert do valida campos e clico no ok
    E eu marco "Cargos"
    E eu marco "Conhecimentos"
    E eu marco "Habilidades"
    E eu marco "Atitudes"
    Então eu clico no botão "Gravar"
    E eu devo ver "Cadastros importados com sucesso."
