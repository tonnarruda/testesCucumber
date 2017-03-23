UPDATE candidato SET nome='.' where nome IS NULL; --.go
ALTER TABLE candidato ALTER COLUMN nome SET NOT NULL; --.go