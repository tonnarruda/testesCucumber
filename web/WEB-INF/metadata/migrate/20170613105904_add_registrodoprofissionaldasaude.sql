ALTER TABLE colaboradorafastamento ADD COLUMN tipoRegistroDeSaude CHARACTER VARYING(4);--.go
UPDATE colaboradorafastamento SET tipoRegistroDeSaude = 'CRM' where medicocrm is not null and medicocrm <> '';--.go
ALTER TABLE colaboradorafastamento RENAME COLUMN medicocrm TO numeroDoRegistroDeSaude;--.go
ALTER TABLE colaboradorafastamento RENAME COLUMN mediconome TO nomeProfissionalDaSaude;--.go
