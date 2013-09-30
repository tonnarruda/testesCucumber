alter table empresa rename column exibirColaboradorSubstituido to solPessoalExibirColabSubstituido;--.go
alter table empresa add column solPessoalExibirSalario boolean default false not null; --.go
alter table empresa add column solPessoalObrigarDadosComplementares boolean default false not null; --.go

CREATE FUNCTION ajusta_solPessoalExibirSalario() RETURNS integer AS $$
DECLARE
    mviews RECORD;
BEGIN
    FOR mviews IN
		select id as empresaId, exibirSalario from empresa
		LOOP
			update empresa set solPessoalExibirSalario = mviews.exibirSalario where id = mviews.empresaId;
		END LOOP;
    RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go
select ajusta_solPessoalExibirSalario();--.go
drop function ajusta_solPessoalExibirSalario();--.go 