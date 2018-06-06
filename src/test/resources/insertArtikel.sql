insert into artikels(naam,aankoopprijs,verkoopprijs,soort,artikelgroepid,houdbaarheid)
	values('testartikelFood',100,200,'F',(select id from artikelgroepen where naam ='testgroep'),7);
insert into artikels(naam,aankoopprijs,verkoopprijs,soort,artikelgroepid,garantie)
	values('testartikelNonFood',200,400,'NF',(select id from artikelgroepen where naam ='testgroep'),12);
insert into kortingen(artikelid,vanafAantal,percentage)
	values((select id from artikels where naam='testartikelFood'),10,10);