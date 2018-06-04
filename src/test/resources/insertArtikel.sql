insert into artikels(naam,aankoopprijs,verkoopprijs,soort,houdbaarheid)
	values('testartikelFood',100,200,'F',7);
insert into artikels(naam,aankoopprijs,verkoopprijs,soort,garantie)
	values('testartikelNonFood',200,400,'NF',12);
insert into kortingen(artikelid,vanafAantal,percentage)
	values((select id from artikels where naam='testartikelFood'),10,10);