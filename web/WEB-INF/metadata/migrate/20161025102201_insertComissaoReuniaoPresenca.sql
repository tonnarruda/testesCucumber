
CREATE OR REPLACE FUNCTION insertComissaoReuniaoPresenca() RETURNS integer AS $$
DECLARE 
mv RECORD; 
BEGIN 
	FOR mv IN 
		select cr.id as crId, cr.data as crData from comissaoreuniao cr where not exists (select * from comissaoreuniaopresenca crp where crp.comissaoreuniao_id = cr.id)
	LOOP 	
		insert into comissaoreuniaopresenca (id, comissaoreuniao_id, presente, colaborador_id) 
		select nextval('comissaoreuniaopresenca_sequence'), mv.crId, false, colab.id as colabId from  comissao c
		    inner join ComissaoPeriodo cp on cp.comissao_id=c.id 
		    inner join Comissaoreuniao cr on cr.comissao_id=c.id 
		    inner join ComissaoMembro cm on cm.comissaoPeriodo_id  = cp.id
		    inner join colaborador colab on colab.id = cm.colaborador_id
		    where cr.id=mv.crId 
			    and (
			    colab.dataDesligamento is null 
			    or colab.dataDesligamento >= mv.crData
			); 
	END LOOP; 
	RETURN 1; 
END; 
$$ LANGUAGE plpgsql;--.go

select insertComissaoReuniaoPresenca();--.go
drop function insertComissaoReuniaoPresenca();--.go