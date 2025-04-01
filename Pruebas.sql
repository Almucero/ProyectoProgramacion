USE ProyectoProgramacionSqlServer_v66;

BEGIN TRANSACTION

SELECT * FROM ordenador WHERE CodOrd=1;
SELECT * FROM ord_alm WHERE CodOrd=1;
SELECT * FROM ord_cpu WHERE CodOrd=1;
SELECT * FROM ord_fuen WHERE CodOrd=1;
SELECT * FROM ord_gpu WHERE CodOrd=1;
SELECT * FROM ord_ram WHERE CodOrd=1;
SELECT * FROM ord_vent WHERE CodOrd=1;
SELECT * FROM ordenador_PCOficina WHERE CodOrd=1;

DELETE FROM ordenador WHERE CodOrd=1;
DELETE FROM cpu WHERE CodCpu=3;
DELETE FROM ord_cpu WHERE CodOrd=1;
DELETE FROM almacenamiento WHERE CodAlm=1;
DELETE FROM ord_alm WHERE CodOrd=1;

SELECT * FROM contenido_carrito WHERE CodOrd=1;
SELECT * FROM carrito WHERE CodCar=1;

INSERT INTO ordenador (Nombre,Precio,Proposito,Stock,SO,CodCha,CodPB,CodAlmPrincipal)
VALUES ('a',0,'PC/Oficina',5,'Windows 11',1,7,1),
       ('b',0,'PC/Oficina',4,'Windows 10',4,6,2);

SELECT * FROM ordenador;
DELETE FROM ordenador WHERE CodOrd=9;

UPDATE cpu SET Precio=9000 WHERE CodCpu=3;
SELECT * FROM cpu WHERE CodCpu=3;

ROLLBACK TRANSACTION