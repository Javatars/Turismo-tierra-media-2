CREATE TABLE "Tipo_Atraccion" (
	"nombre"	TEXT NOT NULL,
	PRIMARY KEY("nombre")
);

CREATE TABLE "Usuario" (
	"nombre"	TEXT NOT NULL,
	"presupuesto"	INTEGER NOT NULL,
	"tiempo_disponible"	REAL NOT NULL,
	"tipo_atraccion"	TEXT NOT NULL,
	FOREIGN KEY("tipo_atraccion") REFERENCES "Tipo_Atraccion"("nombre"),
	PRIMARY KEY("nombre")
);

CREATE TABLE "Itinerario" (
	"id"	INTEGER NOT NULL,
	"usuario"	TEXT NOT NULL,
	FOREIGN KEY("usuario") REFERENCES "Usuario"("nombre"),
	PRIMARY KEY("id" AUTOINCREMENT)
);

CREATE TABLE "Atraccion" (
	"nombre"	TEXT NOT NULL,
	"costo"	INTEGER NOT NULL,
	"cupo"	INTEGER NOT NULL,
	"tiempo"	REAL NOT NULL,
	"tipo_atraccion"	TEXT NOT NULL,
	FOREIGN KEY("tipo_atraccion") REFERENCES "Tipo_Atraccion"("nombre"),
	PRIMARY KEY("nombre")
);

CREATE TABLE "Promocion" (
	"nombre"	TEXT NOT NULL,
	"tipo_atraccion"	TEXT NOT NULL,
	FOREIGN KEY("tipo_atraccion") REFERENCES "Tipo_Atraccion"("nombre"),
	PRIMARY KEY("nombre")
);

CREATE TABLE "Tiene_Atracciones" (
	"id_itinerario"	INTEGER NOT NULL,
	"atraccion"	TEXT NOT NULL,
	FOREIGN KEY("atraccion") REFERENCES "Atraccion"("nombre"),
	FOREIGN KEY("id_itinerario") REFERENCES "Itinerario"("id"),
	PRIMARY KEY("id_itinerario","atraccion")
);

CREATE TABLE "Tiene_Promociones" (
	"id_itinerario"	INTEGER NOT NULL,
	"promocion"	TEXT NOT NULL,
	FOREIGN KEY("promocion") REFERENCES "Promocion"("nombre"),
	FOREIGN KEY("id_itinerario") REFERENCES "Itinerario"("id"),
	PRIMARY KEY("id_itinerario","promocion")
);

CREATE TABLE "Lo_Puede_Contener" (
	"atraccion"	TEXT NOT NULL,
	"promocion"	TEXT NOT NULL,
	FOREIGN KEY("promocion") REFERENCES "Promocion"("nombre"),
	FOREIGN KEY("atraccion") REFERENCES "Atraccion"("nombre"),
	PRIMARY KEY("atraccion","promocion")
);

CREATE TABLE "Promocion_AxB" (
	"nombre"	TEXT NOT NULL,
	"atraccion_gratis"	TEXT NOT NULL,
	FOREIGN KEY("nombre") REFERENCES "Promocion"("nombre"),
	FOREIGN KEY("atraccion_gratis") REFERENCES "Atraccion"("nombre"),
	PRIMARY KEY("nombre")
);

CREATE TABLE "Promocion_Porcentual" (
	"nombre"	TEXT NOT NULL,
	"porcentaje_descuento"	REAL NOT NULL,
	FOREIGN KEY("nombre") REFERENCES "Promocion"("nombre"),
	PRIMARY KEY("nombre")
);

CREATE TABLE "Promocion_Absoluta" (
	"nombre"	TEXT NOT NULL,
	"costo_final"	INTEGER NOT NULL,
	FOREIGN KEY("nombre") REFERENCES "Promocion"("nombre"),
	PRIMARY KEY("nombre")
);