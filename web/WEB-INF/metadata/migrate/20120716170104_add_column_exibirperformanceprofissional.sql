alter table avaliacaodesempenho add column exibirperformanceprofissional boolean default true;--.go
update avaliacaodesempenho set exibirperformanceprofissional = false where anonima = true;--.go