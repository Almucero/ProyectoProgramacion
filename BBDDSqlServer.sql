CREATE DATABASE ProyectoProgramacionSqlServer_11
GO
USE ProyectoProgramacionSqlServer_11
GO

SET DATEFORMAT 'YMD';

--Creaci�n de tablas
CREATE TABLE fabricante
(
 CodFab INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 NomFab VARCHAR(100) NOT NULL
);
CREATE TABLE cpu
(
 CodCpu INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Precio FLOAT NOT NULL CHECK (Precio>0),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Consumo FLOAT NOT NULL CHECK (Consumo>0),
 Stock INT NOT NULL CHECK (Stock>=0),
 Nucleos INT NOT NULL CHECK (Nucleos BETWEEN 1 AND 256),
 Socket VARCHAR(50) NOT NULL,
 Frecuencia FLOAT NOT NULL CHECK (Frecuencia BETWEEN 1 AND 10),
 CodFab INT NOT NULL,
 FOREIGN KEY (CodFab) REFERENCES fabricante(CodFab)
);
--en el caso de la refrigeraci�n (aire o liquida), independientemente de si es de cpu o gpu, la mayoria de modelos deben de usarse en conjunto con
--ventiladores adicionales, para no complicar mucho m�s la cosa, esos ventiladores en el caso de ordenadores ya montados se incluirian en la tabla
--de refrigeracion extra sin una relacion directa con la refrigeracion en cuestion y en conjunto con todos los ventiladores usados en el ordenador,
--sin distincion. En el caaso de compra individual del producto, en este tienda se vender�n las refrigeraciones sin ventiladores asociados
CREATE TABLE refrigeracionCpu
(
 CodRefCpu INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Precio FLOAT NOT NULL CHECK (Precio>0),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Consumo FLOAT NOT NULL CHECK (Consumo>0),
 Tipo VARCHAR(7) NOT NULL CHECK (Tipo IN ('aire','liquida')),
 Stock INT NOT NULL CHECK (Stock>=0),
 CodFab INT NOT NULL,
 FOREIGN KEY (CodFab) REFERENCES fabricante(CodFab)
);
CREATE TABLE refrigeracionCpu_aire
(
 Velocidad FLOAT NULL CHECK (Velocidad BETWEEN 0 AND 10000), --puede ser nulo si no tiene ventilador propio y depende de uno montable
 CodRefCpu INT NOT NULL PRIMARY KEY,
 FOREIGN KEY (CodRefCpu) REFERENCES refrigeracionCpu(CodRefCpu)
);
CREATE TABLE refrigeracionCpu_liquida
(
 PotBomb FLOAT NOT NULL CHECK (PotBomb BETWEEN 1 AND 50),
 CodRefCpu INT NOT NULL PRIMARY KEY,
 FOREIGN KEY (CodRefCpu) REFERENCES refrigeracionCpu(CodRefCpu)
);
CREATE TABLE gpu
(
 CodGpu INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Precio FLOAT NOT NULL CHECK (Precio>0),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Stock INT NOT NULL CHECK (Stock>=0),
 VRAM FLOAT NOT NULL CHECK (VRAM BETWEEN 1 AND 64),
 Frecuencia FLOAT NOT NULL CHECK (Frecuencia>0),
 TipoMem VARCHAR(10) NOT NULL CHECK (TipoMem IN ('GDDR5','GDDR6','GDDR6X','GDDR7','HBM','HBM2','HBM3')),
 Consumo FLOAT NOT NULL CHECK (Consumo>0),
 CodFab INT NOT NULL,
 FOREIGN KEY (CodFab) REFERENCES fabricante(CodFab)
);
--en la siguiente tabla el nombre del modelo ser�, en caso de por aire: refrigeraci�n por defecto con aire de (nombre gpu),
--y en el caso de ser liquida se especificar�a el modelo concreto ahora s�. Tambi�n, solo tendr� atributo consumo en caso
--de ser refrigeracion liquida, ya que de ser aire, seria el mismo que el de la gpu (se ignora restar el consumo de los 
--ventiladores a remover en caso de ser liquida)
CREATE TABLE refrigeracionGpu
(
 CodRefGpu INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Tipo VARCHAR(20) NOT NULL CHECK (Tipo IN ('aire(por defecto)','liquida')),
 Stock INT NOT NULL CHECK (Stock>=0),
 CodFab INT NOT NULL,
 FOREIGN KEY (CodFab) REFERENCES fabricante(CodFab)
);
CREATE TABLE refrigeracionGpu_aire
(
 Velocidad FLOAT NOT NULL CHECK (Velocidad BETWEEN 1 AND 10000),
 CodRefGpu INT NOT NULL PRIMARY KEY,
 FOREIGN KEY (CodRefGpu) REFERENCES refrigeracionGpu(CodRefGpu)
);
CREATE TABLE refrigeracionGpu_liquida
(
 PotBomb FLOAT NOT NULL CHECK (PotBomb BETWEEN 1 AND 50),
 Precio FLOAT NOT NULL CHECK (Precio>0),
 Consumo FLOAT NOT NULL CHECK (Consumo>0),
 CodRefGpu INT NOT NULL PRIMARY KEY,
 FOREIGN KEY (CodRefGpu) REFERENCES refrigeracionGpu(CodRefGpu)
);
CREATE TABLE ventilador
(
 CodVent INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Precio FLOAT NOT NULL CHECK (Precio>0),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Consumo FLOAT NOT NULL CHECK (Consumo>0),
 Velocidad FLOAT NOT NULL CHECK (Velocidad BETWEEN 0 AND 10000),
 Stock INT NOT NULL CHECK (Stock>=0),
 CodFab INT NOT NULL,
 FOREIGN KEY (CodFab) REFERENCES fabricante(CodFab)
);
CREATE TABLE ram
(
 CodRam INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Precio FLOAT NOT NULL CHECK (Precio>0),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Frecuencia FLOAT NOT NULL CHECK (Frecuencia BETWEEN 500 AND 8000),
 Tipo VARCHAR(10) NOT NULL CHECK (Tipo IN ('DDR','DDR2','DDR3','DDR4','DDR5')),
 Consumo FLOAT NOT NULL CHECK (Consumo>0),
 Stock INT NOT NULL CHECK (Stock>=0),
 CodFab INT NOT NULL,
 FOREIGN KEY (CodFab) REFERENCES fabricante(CodFab)
);
CREATE TABLE almacenamiento
(
 CodAlm INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Precio FLOAT NOT NULL CHECK (Precio>0),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Capacidad FLOAT NOT NULL CHECK (Capacidad BETWEEN 1 AND 10000),
 Consumo FLOAT NOT NULL CHECK (Consumo>0),
 Stock INT NOT NULL CHECK (Stock>=0),
 CodFab INT NOT NULL,
 FOREIGN KEY (CodFab) REFERENCES fabricante(CodFab)
);
CREATE TABLE fuente
(
 CodFuen INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Precio FLOAT NOT NULL CHECK (Precio>0),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Stock INT NOT NULL CHECK (Stock>=0),
 Potencia FLOAT NOT NULL CHECK (Potencia BETWEEN 100 AND 2000),
 Eficiencia FLOAT NOT NULL CHECK (Eficiencia BETWEEN 0.5 AND 1),
 CodFab INT NOT NULL,
 FOREIGN KEY (CodFab) REFERENCES fabricante(CodFab)
);
CREATE TABLE chasis
(
 CodCha INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Precio FLOAT NOT NULL CHECK (Precio>0),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Color VARCHAR(40) NOT NULL,
 Stock INT NOT NULL CHECK (Stock>=0),
 Tamanio VARCHAR(50) CHECK (Tamanio IN ('Mini','Micro','Mid','Full Tower','E-ATX')),
 CodFab INT NOT NULL,
 FOREIGN KEY (CodFab) REFERENCES fabricante(CodFab)
);
CREATE TABLE placaBase
(
 CodPB INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Precio FLOAT NOT NULL CHECK (Precio>0),
 maxRam INT NOT NULL CHECK (maxRam>=2),
 maxCpu INT NOT NULL CHECK (maxCpu>=1),
 maxGpu INT NOT NULL CHECK (maxGpu>=0),
 tipoRam VARCHAR(10) NOT NULL CHECK (tipoRam IN ('DDR','DDR2','DDR3','DDR4','DDR5')),
 FF VARCHAR(50) NOT NULL CHECK (FF IN ('ATX','Micro ATX','Mini ITX','E-ATX')),
 Stock INT NOT NULL CHECK (Stock>=0),
 Chipset VARCHAR(50) NOT NULL,
 Socket VARCHAR(50) NOT NULL CHECK (Socket IN ('AM4','AM5','LGA1200','LGA1700','LGA1851','sTRX4','sTR5','SP3','LGA4189','ARM','Apple M4','LGA2066')),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 CodFab INT NOT NULL,
 FOREIGN KEY (CodFab) REFERENCES fabricante(CodFab)
);
CREATE TABLE ordenador
(
 CodOrd INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Nombre VARCHAR(100) NOT NULL UNIQUE,
 Precio FLOAT NULL CHECK (Precio>0), --Se deja en null de momento, despues se actualizar� con la suma de todos los precios asociados
 Proposito VARCHAR(15) NOT NULL CHECK (Proposito IN ('PC/Oficina','workstation','gaming','servidor','embebido','cientifico')),
 Stock INT NOT NULL CHECK (Stock>=0),
 SO VARCHAR(30) NOT NULL,
 CodCha INT NOT NULL,
 CodPB INT NOT NULL,
 CodAlmPrincipal INT NOT NULL,
 FOREIGN KEY (CodCha) REFERENCES chasis(CodCha),
 FOREIGN KEY (CodPB) REFERENCES placaBase(CodPB),
 FOREIGN KEY (CodAlmPrincipal) REFERENCES almacenamiento(CodAlm)
);
CREATE TABLE ord_cpu
(
 CodOrd INT NOT NULL,
 CodCpu INT NOT NULL,
 CodRefCpu INT NOT NULL,
 Cantidad INT NOT NULL CHECK (Cantidad>0),
 PRIMARY KEY (CodOrd, CodCpu, CodRefCpu),
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd),
 FOREIGN KEY (CodCpu) REFERENCES cpu(CodCpu),
 FOREIGN KEY (CodRefCpu) REFERENCES refrigeracionCpu(CodRefCpu)
);
CREATE TABLE ord_gpu
(
 CodOrd INT NOT NULL,
 CodGpu INT NOT NULL,
 CodRefGpu INT NOT NULL,
 Cantidad INT NOT NULL CHECK (Cantidad>0),
 PRIMARY KEY (CodOrd, CodGpu, CodRefGpu),
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd),
 FOREIGN KEY (CodGpu) REFERENCES gpu(CodGpu)
);
CREATE TABLE ord_vent
(
 CodOrd INT NOT NULL,
 CodVent INT NOT NULL,
 Cantidad INT NOT NULL CHECK (Cantidad>0),
 PRIMARY KEY (CodOrd, CodVent),
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd),
 FOREIGN KEY (CodVent) REFERENCES ventilador(CodVent)
);
CREATE TABLE ord_ram
(
 CodOrd INT NOT NULL,
 CodRam INT NOT NULL,
 Cantidad INT NOT NULL CHECK (Cantidad>0),
 PRIMARY KEY (CodOrd, CodRam),
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd),
 FOREIGN KEY (CodRam) REFERENCES ram(CodRam)
);
CREATE TABLE ord_fuen
(
 CodOrd INT NOT NULL,
 CodFuen INT NOT NULL,
 Cantidad INT NOT NULL CHECK (Cantidad>0),
 PRIMARY KEY (CodOrd, CodFuen),
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd),
 FOREIGN KEY (CodFuen) REFERENCES fuente(CodFuen)
);
CREATE TABLE ord_alm
(
 CodOrd INT NOT NULL,
 CodAlmSecundario INT NOT NULL,
 Cantidad INT NOT NULL CHECK (Cantidad>0),
 PRIMARY KEY (CodOrd, CodAlmSecundario),
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd),
 FOREIGN KEY (CodAlmSecundario) REFERENCES almacenamiento(CodAlm)
);
CREATE TABLE ordenador_PCOficina
(
 MainSoft VARCHAR(200) NOT NULL,
 CodOrd INT NOT NULL PRIMARY KEY,
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd)
);
CREATE TABLE ordenador_workstation
(
 render TINYINT NOT NULL,
 certificado VARCHAR(100) NULL,
 CodOrd INT NOT NULL PRIMARY KEY,
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd)
);
CREATE TABLE ordenador_gaming
(
 RGB TINYINT NOT NULL,
 OC TINYINT NOT NULL,
 CodOrd INT NOT NULL PRIMARY KEY,
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd)
);
CREATE TABLE ordenador_servidor
(
 escalabilidad VARCHAR(5) NOT NULL CHECK (escalabilidad IN ('alta','media','baja')),
 CodOrd INT NOT NULL PRIMARY KEY,
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd)
);
CREATE TABLE ordenador_embebido
(
 SisTiemReal TINYINT NOT NULL,
 CodOrd INT NOT NULL PRIMARY KEY,
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd)
);
CREATE TABLE ordenador_cientifico
(
 multiCpu TINYINT NOT NULL,
 CodOrd INT NOT NULL PRIMARY KEY,
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd)
);
CREATE TABLE montador
(
 CodMon INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Nombre VARCHAR(50) NOT NULL,
 Apellidos VARCHAR(100) NOT NULL,
 DNI CHAR(9) NOT NULL UNIQUE CHECK (LEN(DNI)=9 AND DNI LIKE (REPLICATE('[0-9]',8)+'[A-Z]')),
 FechaIni DATE NOT NULL DEFAULT GETDATE(),
 Titulacion VARCHAR(100) NOT NULL
);
CREATE TABLE montaje
(
 CodMontaje INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Fecha DATE NOT NULL DEFAULT GETDATE(),
 Detalles VARCHAR(500) NOT NULL,
 Precio FLOAT NOT NULL DEFAULT 250,
 CodOrd INT NOT NULL,
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd),
 CodMon INT NOT NULL,
 FOREIGN KEY (CodMon) REFERENCES montador(CodMon)
);
CREATE TABLE servicioTesteo
(
 CodSerTest INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Nombre VARCHAR(50) NOT NULL UNIQUE,
 Estrellas FLOAT NOT NULL CHECK (Estrellas BETWEEN 1 AND 5)
);
CREATE TABLE testeo
(
 CodTest INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Precio FLOAT NOT NULL DEFAULT 50,
 Fecha DATE NOT NULL DEFAULT GETDATE(),
 Reporte VARCHAR(500) NOT NULL,
 CodOrd INT NOT NULL,
 CodSerTest INT NOT NULL,
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd),
 FOREIGN KEY (CodSerTest) REFERENCES servicioTesteo(CodSerTest)
);
CREATE TABLE usuario
(
 CodUsu INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Nombre VARCHAR(50) NOT NULL,
 Ape1Usu VARCHAR(50) NOT NULL,
 Ape2Usu VARCHAR(50) NULL,
 DNI CHAR(9) NOT NULL UNIQUE CHECK (LEN(DNI)=9 AND DNI LIKE (REPLICATE('[0-9]',8)+'[A-Z]')),
 FecNac DATE NOT NULL CHECK (FecNac <= DATEADD(YEAR, -18, GETDATE())),
 DireccionCompleta VARCHAR(200) NOT NULL,
 Correo VARCHAR(100) NOT NULL,
 Contrasenia VARCHAR(100) NOT NULL,
 EsAdministrador TINYINT NOT NULL
);
CREATE TABLE carrito
(
 CodCar INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Fecha DATE NOT NULL DEFAULT GETDATE(),
 PrecioTotal FLOAT NULL CHECK (PrecioTotal>0), --Se deja a null y luego se cambia en base a las diferentes entradas en contenido_carrito
 Estado VARCHAR(50) NOT NULL CHECK (Estado IN ('compraRealizada','compraNoRealizada')) DEFAULT 'compraNoRealizada',
 CodUsu INT NOT NULL,
 FOREIGN KEY (CodUsu) REFERENCES usuario(CodUsu)
);
--Esta tabla solo puede albercar un producto a la vez (en la cantidad que sea), hace referencia a una entrada de un carrito, 
--un carrito completo estar� asociado a 1 o muchas entradas de esta tabla
CREATE TABLE contenido_carrito
(
 CodConCar INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Precio FLOAT NULL CHECK (Precio>0), --se deja a null y luega se actualiza en base a la cantidad
 Cantidad INT NOT NULL CHECK (Cantidad>0),
 CodCar INT NOT NULL,
 FOREIGN KEY (CodCar) REFERENCES carrito(CodCar),
 CodOrd INT NULL,
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd),
 CodCha INT NULL,
 FOREIGN KEY (CodCha) REFERENCES chasis(CodCha),
 CodFuen INT NULL,
 FOREIGN KEY (CodFuen) REFERENCES fuente(CodFuen),
 CodPB INT NULL,
 FOREIGN KEY (CodPB) REFERENCES placaBase(CodPB),
 CodAlm INT NULL,
 FOREIGN KEY (CodAlm) REFERENCES almacenamiento(CodAlm),
 CodRam INT NULL,
 FOREIGN KEY (CodRam) REFERENCES ram(CodRam),
 CodGpu INT NULL,
 FOREIGN KEY (CodGpu) REFERENCES gpu(CodGpu),
 CodCpu INT NULL,
 FOREIGN KEY (CodCpu) REFERENCES cpu(CodCpu),
 CodVent INT NULL,
 FOREIGN KEY (CodVent) REFERENCES ventilador(CodVent),
 CodRefCpu INT NULL,
 FOREIGN KEY (CodRefCpu) REFERENCES refrigeracionCpu(CodRefCpu),
 CodRefGpu INT NULL,
 FOREIGN KEY (CodRefGpu) REFERENCES refrigeracionGpu(CodRefGpu)
);

--Inserci�n de datos iniciales (todas las verificaciones de logica interna, ej: que el socket de la cpu sea compatible
--con la placa base, o que no haya m�s de un carrito en estado de "compraNoRealizada"; se realizar� desde Java y no aqui, 
--los ordenadores preemontados iniciales que se agregar�n cumpliran ya con todas las restricciones
INSERT INTO fabricante (NomFab)
VALUES ('Corsair'),
       ('Asus'),
       ('MSI'),
       ('Gigabyte'),
       ('NZXT'),
       ('Cooler Master'),
       ('EVGA'),
       ('Seasonic'),
       ('Be Quiet!'),
       ('Western Digital'),
       ('Samsung'),
       ('Crucial'),
       ('AMD'),
       ('Intel'),
       ('Apple'),
       ('ARM'),
       ('Nvidia'),
       ('Noctua'),
       ('Arctic'),
       ('Thermaltake'),
       ('Fractal Design'),
       ('G.SKILL'),
       ('Kingston'),
       ('ADATA'),
       ('Patriot'),
       ('TeamGroup'),
       ('Seagate'),
       ('SanDisk'),
       ('SilverStone'),
       ('Antec'),
       ('Phanteks'),
       ('Lian Li'),
       ('ASRock');
INSERT INTO cpu (Precio,Modelo,Consumo,Stock,Nucleos,Socket,Frecuencia,CodFab)
VALUES (299,'Ryzen 5 5600X',65,10,6,'AM4',3.7,13),
       (399,'Ryzen 7 5800X',105,8,8,'AM4',3.8,13),
       (319,'Core i5-12600K',125,15,10,'LGA1700',3.7,14),
       (449,'Core i7-13700K',125,12,16,'LGA1700',3.4,14),
       (749,'Ryzen 9 5950X',105,5,16,'AM4',3.4,13),
       (799,'Core i9-12900KS',150,7,16,'LGA1700',3.4,14),
       (3990,'Threadripper 3990X',280,3,64,'sTRX4',2.9,13),
       (7890,'EPYC 7763',280,2,64,'SP3',2.45,13),
       (9999,'Xeon Platinum 8380',270,1,40,'LGA4189',2.3,14),
       (50,'Cortex-A76',5,50,4,'ARM',2.2,16),
       (3200,'M4 Max 16-Core',60,20,16,'Apple M4',3.2,15),
       (699,'Core Ultra 9 285K',125,10,24,'LGA1851',3.5,14),
       (4999,'Threadripper 7980X',350,5,64,'sTR5',3.2,13),
       (3999,'Threadripper 7970X',350,8,32,'sTR5',4.0,13),
       (6999,'Threadripper 7995WX',350,2,96,'sTR5',2.5,13),
	   (8500,'Xeon W-3300',200,5,16,'LGA2066',3.0,14);
INSERT INTO refrigeracionCpu (Precio,Modelo,Consumo,Tipo,Stock,CodFab)  
VALUES (40,'Cooler Master Hyper 212',20,'aire',15,6),  
       (80,'Corsair A500',25,'aire',10,1),  
       (120,'NZXT Kraken X53',30,'aire',5,5),  
       (45,'Be Quiet! Pure Rock 2',15,'aire',8,9),  
       (35,'Cooler Master Hyper 212 EVO',18,'aire',0,6),
       (150,'Corsair iCUE H100i Elite Capellix',50,'liquida',7,1),  
       (180,'NZXT Kraken Z73',60,'liquida',4,5),  
       (140,'Be Quiet! Silent Loop 2',45,'liquida',3,9),  
       (170,'Corsair iCUE H150i Elite Capellix',55,'liquida',12,1),  
       (100,'Cooler Master MasterLiquid ML240L',40,'liquida',0,6);
INSERT INTO refrigeracionCpu_aire (Velocidad,CodRefCpu)  
VALUES (1500,1),  
       (1800,2),  
       (NULL,3),
       (2500,4),  
       (NULL,5);
INSERT INTO refrigeracionCpu_liquida (PotBomb,CodRefCpu)  
VALUES (20,6),  
       (25,7),  
       (30,8),  
       (35,9),  
       (40,10);
INSERT INTO gpu (Precio,Modelo,Stock,VRAM,Frecuencia,TipoMem,Consumo,CodFab)  
VALUES (300,'NVIDIA GeForce RTX 3060',10,12,1.78,'GDDR6',170,17),  
       (800,'NVIDIA GeForce RTX 3080',0,10,1.44,'GDDR6X',320,17),  
       (400,'AMD Radeon RX 6700 XT',8,12,2.42,'GDDR6',230,13),  
       (1600,'NVIDIA GeForce RTX 4090',0,24,2.52,'GDDR6X',450,17),  
       (550,'AMD Radeon RX 6800 XT',7,16,2.25,'GDDR6',300,13),  
       (200,'NVIDIA GeForce GTX 1650',15,4,1.50,'GDDR5',75,17),  
       (5000,'NVIDIA Quadro RTX 8000',3,48,1.77,'GDDR6',295,17),  
       (300,'AMD Radeon RX 5700',10,8,1.73,'GDDR6',225,13),  
       (2500,'NVIDIA Titan RTX',4,24,1.77,'GDDR6',280,17),  
       (150,'NVIDIA GeForce GTX 1050 Ti',12,4,1.39,'GDDR5',75,17);
INSERT INTO refrigeracionGpu (Modelo,Tipo,Stock,CodFab)  
VALUES ('Refrigeraci�n por defecto con aire de NVIDIA GeForce RTX 3060','aire(por defecto)',10,17),  
       ('Refrigeraci�n por defecto con aire de NVIDIA GeForce RTX 3080','aire(por defecto)',5,17),  
       ('Refrigeraci�n por defecto con aire de AMD Radeon RX 6700 XT','aire(por defecto)',8,13),  
       ('Refrigeraci�n l�quida Corsair iCUE H150i Elite Capellix','liquida',2,1),  
       ('Refrigeraci�n por defecto con aire de NVIDIA GeForce RTX 4090','aire(por defecto)',3,17),  
       ('Refrigeraci�n l�quida NZXT Kraken Z63','liquida',7,5),  
       ('Refrigeraci�n por defecto con aire de AMD Radeon RX 6800 XT','aire(por defecto)',10,13),  
       ('Refrigeraci�n l�quida ASUS ROG Strix LC 360','liquida',4,2),  
       ('Refrigeraci�n por defecto con aire de NVIDIA Quadro RTX 8000','aire(por defecto)',3,17),  
       ('Refrigeraci�n por defecto con aire de NVIDIA GeForce GTX 1650','aire(por defecto)',15,17),
       ('Refrigeraci�n por defecto con aire de AMD Radeon RX 5700','aire(por defecto)',4,13),
	   ('Refrigeraci�n por defecto con aire de NVIDIA Titan RTX','aire(por defecto)',7,17),
       ('Refrigeraci�n por defecto con aire de NVIDIA GeForce GTX 1050 Ti','aire(por defecto)',23,17);
INSERT INTO refrigeracionGpu_aire (Velocidad,CodRefGpu)  
VALUES (2500,1),  
       (2500,2),  
       (2500,3),  
       (2500,5),  
       (2500,7),  
       (2500,9),  
       (2500,10),  
       (2500,11),  
       (2500,12),  
       (2500,13);
INSERT INTO refrigeracionGpu_liquida (Precio,PotBomb,Consumo,CodRefGpu)  
VALUES (200,25,50,4),  
       (180,25,50,6),  
       (220,25,50,8);
INSERT INTO ventilador (Precio,Modelo,Consumo,Velocidad,Stock,CodFab)  
VALUES (25,'Ventilador Noctua NF-A12x25 PWM',0.6,2000,10,18),  
       (28,'Ventilador Corsair ML120 PRO',0.4,2400,5,1),  
       (23,'Ventilador be quiet! Silent Wings 3',0.5,2200,8,9),  
       (15,'Ventilador Arctic P12 PWM',0.3,1800,15,19),  
       (30,'Ventilador Cooler Master MasterFan MF120R ARGB',0.6,2000,0,6),  
       (35,'Ventilador Thermaltake Riing Plus 12 RGB',0.7,1500,7,20),  
       (18,'Ventilador Fractal Design Dynamic X2 GP-12',0.4,1200,12,21),  
       (22,'Ventilador NZXT Aer P 120mm',0.5,1500,3,5),  
       (40,'Ventilador EVGA Hybrid 120mm',0.9,2200,0,7),  
       (20,'Ventilador Cooler Master SickleFlow 120mm',0.5,1800,6,6);
INSERT INTO ram (Precio,Modelo,Frecuencia,Tipo,Consumo,Stock,CodFab)  
VALUES (35,'Corsair Vengeance LPX 8GB DDR4',3200,'DDR4',1.2,10,1),  
       (70,'G.SKILL Ripjaws V 16GB DDR4',3600,'DDR4',1.35,5,22),  
       (65,'Kingston HyperX Fury 16GB DDR4',2666,'DDR4',1.2,0,23),  
       (40,'Crucial Ballistix 8GB DDR4',3000,'DDR4',1.35,8,12),  
       (120,'Corsair Vengeance LPX 32GB DDR4',2933,'DDR4',1.2,3,1),  
       (50,'ADATA XPG Z1 16GB DDR3',1600,'DDR3',1.5,12,24),  
       (85,'HyperX Predator 8GB DDR4',4000,'DDR4',1.35,0,23),  
       (75,'G.SKILL Trident Z 16GB DDR4',3200,'DDR4',1.35,7,22),  
       (80,'Patriot Viper 4 16GB DDR4',3600,'DDR4',1.35,6,25),  
       (45,'TeamGroup T-Force Delta RGB 8GB DDR4',3200,'DDR4',1.35,2,26),
       (30,'Samsung 8GB DDR3',1333,'DDR3',1.5,4,11),
       (20,'Crucial 4GB DDR2',800,'DDR2',1.8,0,12),
       (150,'Corsair Vengeance 16GB DDR5',4800,'DDR5',1.4,10,1),
       (280,'Kingston FURY Beast 32GB DDR5',5200,'DDR5',1.5,5,23),
       (200,'Corsair Dominator Platinum 8GB DDR5',6000,'DDR5',1.4,8,1);
INSERT INTO almacenamiento (Precio,Modelo,Capacidad,Consumo,Stock,CodFab)  
VALUES (75,'Samsung 970 EVO Plus 500GB',500,'5',20,11),  
       (90,'Kingston A2000 1TB',1000,'4.2',15,23),  
       (50,'Western Digital Blue 500GB',500,'3.5',25,10),  
       (180,'Crucial MX500 2TB',2000,'4.5',8,12),  
       (100,'Seagate Barracuda 4TB',4000,'6',12,27),  
       (400,'Intel Optane 905P 480GB',480,'8',6,14),  
       (110,'SanDisk Ultra 3D 1TB',1000,'4.5',18,28),  
       (130,'Corsair MP600 1TB',1000,'7.5',10,1),  
       (60,'ADATA SU800 512GB',512,'3.8',14,24),  
       (150,'Western Digital Black SN850 1TB',1000,'8',10,10);
INSERT INTO fuente (Precio,Modelo,Stock,Potencia,Eficiencia,CodFab)  
VALUES (140,'Corsair RM850x',50,850,0.9,1),  
       (120,'Seasonic Focus GX-750',30,750,0.92,8),  
       (180,'EVGA SuperNOVA 1000 G5',25,1000,0.94,7),  
       (90,'Cooler Master MWE Gold 650',40,650,0.85,6),  
       (250,'be quiet! Dark Power Pro 12 1500W',10,1500,0.94,9),  
       (130,'Thermaltake Toughpower PF1 750W',20,750,0.88,20),  
       (140,'Gigabyte AORUS P850W',35,850,0.9,4),  
       (190,'SilverStone Strider Platinum 1000W',15,1000,0.92,29),  
       (160,'Fractal Design Ion+ 860W',18,860,0.9,21),  
       (110,'Antec High Current Gamer 750W',22,750,0.86,30);
INSERT INTO chasis (Precio,Modelo,Color,Stock,Tamanio,CodFab)
VALUES (100,'NZXT H510','Negro',25,'Mid',5),
       (120,'Corsair iCUE 4000X','Blanco',15,'Mid',1),
       (110,'Fractal Design Meshify C','Negro',30,'Mid',21),
       (60,'Cooler Master MasterBox Q300L','Negro',40,'Micro',6),
       (90,'be quiet! Pure Base 500','Blanco',10,'Mid',9),
       (140,'Thermaltake Core P5','Negro',5,'Full Tower',20),
       (110,'Phanteks Eclipse P400A','Negro',20,'Mid',31),
       (150,'Lian Li PC-O11 Dynamic','Aluminio',18,'E-ATX',32),
       (100,'Corsair 275R Airflow','Negro',28,'Mid',1),
       (130,'Antec P120 Crystal','Blanco',12,'Mid',30);
INSERT INTO placaBase (Precio,maxRam,maxCpu,maxGpu,tipoRam,FF,Stock,Chipset,Socket,Modelo,CodFab)  
VALUES (400,4,1,3,'DDR4','ATX',30,'X570','AM4','ASUS ROG Crosshair VIII Hero',2),  
       (150,4,1,2,'DDR4','Micro ATX',25,'B460','LGA1200','MSI MAG B460M Mortar WiFi',3),  
       (250,2,1,1,'DDR4','Mini ITX',18,'Z490','LGA1200','ASRock Z490 Phantom Gaming-ITX/TB3',33),  
       (700,8,2,4,'DDR4','E-ATX',10,'TRX40','sTRX4','Gigabyte TRX40 AORUS XTREME',4),  
       (300,4,1,3,'DDR4','ATX',40,'Z590','LGA1200','ASUS TUF Gaming Z590-Plus WiFi',2),  
       (180,4,1,2,'DDR4','Micro ATX',50,'B550','AM4','MSI MAG B550M Mortar WiFi',3),  
       (500,4,1,3,'DDR5','ATX',20,'Z690','LGA1700','Gigabyte Z690 AORUS Master',4),  
       (600,8,2,4,'DDR4','E-ATX',5,'X299','LGA2066','ASRock X299 Taichi CL',33),  
       (220,4,1,2,'DDR4','ATX',15,'B550','AM4','MSI MPG B550 Gaming Edge WiFi',3),  
       (270,2,1,1,'DDR4','Mini ITX',12,'Z590','LGA1200','ASRock Z590 Phantom Gaming-ITX/TB3',33);

--Insercion de ordenadores preemontados con componentes compatibles
INSERT INTO ordenador (Nombre,Precio,Proposito,Stock,SO,CodCha,CodPB,CodAlmPrincipal)
VALUES ('Oficina Pro Max',NULL,'PC/Oficina',5,'Windows 11',1,7,1),
       ('Oficina Pro',NULL,'PC/Oficina',4,'Windows 10',4,6,2),
       ('Workstation Pro',NULL,'workstation',2,'Windows 10 Pro',8,4,2),
       ('Gaming Beast',NULL,'gaming',3,'Windows 11 Pro',9,7,8),
       ('Gaming Lite',NULL,'gaming',2,'Windows 10',10,6,2),
       ('Servidor Ultra',NULL,'servidor',2,'Windows Server 2019',6,7,2),
       ('Embedded Compact',NULL,'embebido',3,'Linux Embedded',1,7,1),
       ('HPC Titan',NULL,'cientifico',2,'Linux',6,8,8);
INSERT INTO ord_cpu (CodOrd,CodCpu,CodRefCpu,Cantidad)
VALUES (1,3,1,1),
       (2,1,2,1),
       (3,7,6,2),
       (4,4,9,1),
       (5,1,1,1),
       (6,4,6,1),
       (7,3,1,1),
       (8,16,6,2);
INSERT INTO ord_gpu (CodOrd,CodGpu,CodRefGpu,Cantidad)
VALUES (1,10,13,1),
       (3,4,5,2),
	   (4,5,7,2),
	   (5,6,10,1),
	   (6,7,9,1),
	   (8,7,9,1);  
INSERT INTO ord_vent (CodOrd,CodVent,Cantidad)
VALUES (1,1,2),
	   (2,2,2),
	   (3,4,6),
	   (4,4,9),
	   (5,4,10),
	   (6,4,3),
	   (7,4,1),
	   (8,2,8);
INSERT INTO ord_ram (CodOrd,CodRam,Cantidad)
VALUES (1,13,2),
       (2,2,2),
       (3,4,8),
       (4,13,4),
       (5,2,2),
       (6,13,4),
       (7,13,1),
       (8,4,8);
INSERT INTO ord_fuen (CodOrd,CodFuen,Cantidad)
VALUES (1,1,1),
       (2,2,1),
       (3,2,2),
       (4,3,1),
       (5,2,1),
       (6,3,1),
       (7,2,1),
       (8,3,1);
INSERT INTO ord_alm (CodOrd,CodAlmSecundario,Cantidad)
VALUES (1,2,1),(1,3,1),
       (2,3,2),
       (3,3,1),(3,5,1),
       (4,3,1),(4,5,1),
       (5,3,1),
       (6,3,1),(6,4,1),
       (7,3,1),
       (8,10,1),(8,9,1);
INSERT INTO ordenador_PCOficina (MainSoft,CodOrd)
VALUES ('Office Suite',1),
       ('Office Suite Pro',2);
INSERT INTO ordenador_workstation (render,certificado,CodOrd)
VALUES (1,'Certificado Pro',3);
INSERT INTO ordenador_gaming (RGB,OC,CodOrd)
VALUES (1,1,4),
       (1,0,5);
INSERT INTO ordenador_servidor (escalabilidad,CodOrd)
VALUES ('alta',6);
INSERT INTO ordenador_embebido (SisTiemReal,CodOrd)
VALUES (1,7);
INSERT INTO ordenador_cientifico (multiCpu,CodOrd)  
VALUES (1,8);

--Inserci�n de montadores
INSERT INTO montador (Nombre,Apellidos,DNI,FechaIni,Titulacion)  
VALUES ('Juan','P�rez G�mez','12345678A','2025-03-20','Ingenier�a en Sistemas'),  
       ('Ana','L�pez Garc�a','23456789B','2025-03-20','T�cnico en Inform�tica'),  
       ('Carlos','Mart�nez S�nchez','34567890C','2025-03-20','Licenciado en Ciencias de la Computaci�n'),  
       ('Laura','Ram�rez Torres','45678901D','2025-03-20','Grado en Ingenier�a de Software'),  
       ('Pedro','Fern�ndez Rodr�guez','56789012E','2025-03-20','T�cnico en Desarrollo de Aplicaciones'),  
       ('Sof�a','Gonz�lez P�rez','67890123F','2025-03-20','M�ster en Inteligencia Artificial'),  
       ('Diego','Hern�ndez Ruiz','78901234G','2025-03-20','Ingenier�a en Redes de Comunicaci�n'),  
       ('Beatriz','Jim�nez Morales','89012345H','2025-03-20','Grado en Matem�ticas y Computaci�n'),  
       ('Fernando','V�zquez D�az','90123456I','2025-03-20','Diplomado en Desarrollo Web'),  
       ('Mar�a','S�nchez L�pez','01234567J','2025-03-20','M�ster en Ciberseguridad');

--Inserci�n de los montajes
INSERT INTO montaje (Detalles,Precio,CodOrd,CodMon)  
VALUES ('El montaje fue fluido, sin inconvenientes. Se organiz� el cableado y la ventilaci�n para mejorar la circulaci�n de aire.',DEFAULT,1,3),  
       ('Se enfrentaron algunos problemas con la alineaci�n del chasis, pero se resolvieron ajustando los soportes.',DEFAULT,2,5),  
       ('El ensamblaje de la workstation tom� m�s tiempo debido a la complejidad del sistema de refrigeraci�n.',DEFAULT,3,1),  
       ('Montaje exitoso con optimizaci�n en la distribuci�n de cables y colocaci�n de la GPU sin interferencias.',DEFAULT,4,7),  
       ('La estructura del equipo gaming permiti� una instalaci�n r�pida y eficiente, sin necesidad de ajustes adicionales.',DEFAULT,5,4),  
       ('El montaje del servidor requiri� precisi�n en la conexi�n de m�ltiples unidades de almacenamiento.',DEFAULT,6,6),  
       ('Se presentaron inconvenientes con la compatibilidad del chasis, pero se solucionaron adaptando los puntos de anclaje.',DEFAULT,7,3),  
       ('El equipo HPC qued� ensamblado correctamente tras verificar la integraci�n de los m�dulos de alto rendimiento.',DEFAULT,8,8);  

--Inserci�n de servicios de testeo
INSERT INTO servicioTesteo (Nombre,Estrellas)  
VALUES ('TechCare Solutions',4.5),  
       ('PC Master Repair',4.8),  
       ('HardFix Express',4.2),  
       ('Byte Savers',3.9),  
       ('ReparaTech',4.6),  
       ('Overclock Services',4.1),  
       ('DataRescue Labs',4.7),  
       ('Elite PC Support',4.3),  
       ('Chipset Fixers',3.8),  
       ('UltraTech Repairs',4.9);  

--Inserci� de los testeos
INSERT INTO testeo (Precio,Fecha,Reporte,CodOrd,CodSerTest)  
VALUES (DEFAULT,'2024-01-25','Se realizaron pruebas de estabilidad y rendimiento. Todo funciona correctamente.',4,3),  
       (DEFAULT,DEFAULT,'Verificaci�n de temperaturas y carga m�xima. Sin anomal�as detectadas.',1,5),  
       (DEFAULT,'2018-05-23','Se ejecutaron benchmarks de CPU y GPU. Resultados dentro de lo esperado.',6,1),  
       (DEFAULT,DEFAULT,'Comprobaci�n de conexiones internas y sistema de refrigeraci�n. Todo en orden.',3,7),  
       (DEFAULT,'2022-12-31','Se verific� compatibilidad de drivers y estabilidad del sistema.',8,2),  
       (DEFAULT,'2021-01-01','Test de estr�s en m�ltiples aplicaciones. Rendimiento estable.',2,6),  
       (DEFAULT,DEFAULT,'Se revis� integridad de los componentes y configuraci�n de BIOS.',7,3),  
       (DEFAULT,DEFAULT,'An�lisis de voltajes y estabilidad energ�tica. Sin fallos detectados.',5,1);  

--Inserci�n de usuarios (administradores y normales)
INSERT INTO usuario (Nombre,Ape1Usu,Ape2Usu,DNI,FecNac,DireccionCompleta,Correo,Contrasenia,EsAdministrador)
VALUES ('Juan','P�rez',NULL,'12345678A','2005-03-21','Calle Ficticia 123','juan.perez@gmail.com','contrasena123',0),
       ('Ana','L�pez','Garc�a','23456789B','2004-07-12','Calle Real 456','ana.lopez@hotmail.com','contrasena456',0),
       ('Carlos','Mart�nez',NULL,'34567890C','2001-09-15','Avenida Principal 789','carlos.martinez@gmail.com','contrasena789',0),
       ('Laura','Ram�rez','Torres','45678901D','2002-11-05','Plaza Mayor 101','laura.ramirez@yahoo.com','contrasena101',1),
       ('Pedro','Fern�ndez',NULL,'56789012E','1999-02-28','Calle Falsa 202','pedro.fernandez@hotmail.com','contrasena202',0),
       ('Sof�a','Gonz�lez','P�rez','67890123F','2003-04-10','Calle del Sol 303','sofia.gonzalez@gmail.com','contrasena303',1),
       ('Diego','Hern�ndez',NULL,'78901234G','2000-06-01','Calle del R�o 404','diego.hernandez@gmail.com','contrasena404',0),
       ('Beatriz','Jim�nez','Morales','89012345H','2006-08-18','Avenida Libertad 505','beatriz.jimenez@hotmail.com','contrasena505',1),
       ('Fernando','V�zquez','D�az','90123456I','2000-12-15','Calle del Mar 606','fernando.vazquez@gmail.com','contrasena606',0),
       ('Mar�a','S�nchez',NULL,'01234567J','2001-10-25','Avenida 25 707','maria.sanchez@yahoo.com','contrasena707',1);

--Inserci�n de carritos
INSERT INTO carrito (Fecha,PrecioTotal,Estado,CodUsu) 
VALUES ('2025-03-15',NULL,'compraRealizada',2),
       ('2025-03-18',NULL,'compraRealizada',5),
       ('2025-03-20',NULL,'compraRealizada',6),
       (DEFAULT,NULL,'compraRealizada',8);

--Inserci�n de los contenidos de los carritos
INSERT INTO contenido_carrito (Precio,Cantidad,CodCar,CodOrd,CodCha,CodFuen,CodPB,CodAlm,CodRam,CodGpu,CodCpu,CodVent,CodRefCpu,CodRefGpu)
VALUES (NULL,1,1,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(NULL,5,1,NULL,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(NULL,8,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),
       (NULL,1,2,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(NULL,1,2,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
       (NULL,1,3,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL),(NULL,2,3,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL),(NULL,1,3,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),
       (NULL,1,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL),(NULL,4,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL),(NULL,2,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL);
       
--Asignacion del precio de los ordenadores (suma de componentes + montaje + testeo inicial)

--Asignaci�n del precio a los contenidos de los carritos (producto asociado * cantidad del mismo en la orden)

--Asignaci�n del precio a los carritos (suma del precio de todas las entradas de contenido_carrito vinculadas al carrito)


--trigger para precios
--a�adir triggers de insert y update que verifiquen que los datos introducidos al crear tablas sean compatibles (quizas, en java puede ser suficiente)
--a�adir comentarios
--reglas de borrado en FKs