INSERT INTO "Tipo_Atraccion" VALUES ('AVENTURA'),('DEGUSTACION'),('PAISAJE');

INSERT INTO "Usuario"("nombre","presupuesto","tiempo_disponible","tipo_atraccion") VALUES 
	('Eowyn',10,8,'AVENTURA'),
	('Gandalf',100,5,'PAISAJE'),
	('Sam',36,8,'DEGUSTACION'),
	('Galadriel',120,4,'PAISAJE'),
	('Aragorn',50,7,'AVENTURA'),
	('Legolas',35,10,'DEGUSTACION'),
	('Sauron',70,20,'AVENTURA'),
	('Arwen',40,11,'PAISAJE');

INSERT INTO "Atraccion"("nombre","costo","tiempo","cupo","tipo_atraccion") VALUES
	('Moria',10,2,6,'AVENTURA'),
	('Minas Tirith',5,2.5,25,'PAISAJE'),
	('La Comarca',3,6.5,150,'DEGUSTACION'),
	('Mordor',25,3,4,'AVENTURA'),
	('Abismo de Helm',5,2,15,'PAISAJE'),
	('Lothlorien',35,1,30,'DEGUSTACION'),
	('Erebor',12,3,32,'PAISAJE'),
	('Bosque Negro',3,4,12,'AVENTURA'),
	('Rivendel',14,4.5,3,'DEGUSTACION'),
	('Isengard',17,5.6,2,'AVENTURA'),
	('Edoras',9,4.2,5,'PAISAJE'),
	('Llanuras de Rohan',7,6.3,8,'PAISAJE'),
	('Bosque de Fangorn',18,4.7,11,'AVENTURA');
	
INSERT INTO "Promocion"("nombre","tipo_atraccion") VALUES
	('Pack aventura','AVENTURA'),
	('Pack degustacion','DEGUSTACION'),
	('Pack paisajes','PAISAJE'),
	('Pack paisajes 2','PAISAJE'),
	('Pack aventura 2','AVENTURA'),
	('Pack degustacion 2','DEGUSTACION'),
	('Pack full aventura','AVENTURA');

INSERT INTO "Lo_Puede_Contener"("atraccion","promocion") VALUES
	('Bosque Negro','Pack aventura'),
	('Mordor','Pack aventura'),
	('Lothlorien','Pack degustacion'),
	('La Comarca','Pack degustacion'),
	('Minas Tirith','Pack paisajes'),
	('Abismo de Helm','Pack paisajes'),
	('Erebor','Pack paisajes'),
	('Llanuras de Rohan','Pack paisajes 2'),
	('Edoras','Pack paisajes 2'),
	('Abismo de Helm','Pack paisajes 2'),
	('Moria','Pack aventura 2'),
	('Mordor','Pack aventura 2'),
	('La Comarca','Pack degustacion 2'),
	('Rivendel','Pack degustacion 2'),
	('Lothlorien','Pack degustacion 2'),
	('Isengard','Pack full aventura'),
	('Bosque de Fangorn','Pack full aventura'),
	('Moria','Pack full aventura'),
	('Mordor','Pack full aventura');

INSERT INTO "Promocion_Porcentual"("nombre","porcentaje_descuento") VALUES
	('Pack aventura',0.2),
	('Pack paisajes 2',0.3);
	
INSERT INTO "Promocion_Absoluta"("nombre","costo_final") VALUES
	('Pack degustacion',36),
	('Pack aventura 2',27);

INSERT INTO "Promocion_AxB"("nombre","atraccion_gratis") VALUES
	('Pack paisajes','Erebor'),
	('Pack degustacion 2','La Comarca'),
	('Pack full aventura','Isengard');