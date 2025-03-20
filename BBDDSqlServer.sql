CREATE DATABASE ProyectoProgramacionSqlServer_02
GO
USE ProyectoProgramacionSqlServer_02
GO

--Creación de tablas
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
--en el caso de la refrigeración (aire o liquida), independientemente de si es de cpu o gpu, la mayoria de modelos deben de usarse en conjunto con
--ventiladores adicionales, para no complicar mucho más la cosa, esos ventiladores en el caso de ordenadores ya montados se incluirian en la tabla
--de refrigeracion extra sin una relacion directa con la refrigeracion en cuestion y en conjunto con todos los ventiladores usados en el ordenador,
--sin distincion. En el caaso de compra individual del producto, en este tienda se venderán las refrigeraciones sin ventiladores asociados
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
--en la siguiente tabla el nombre del modelo será, en caso de por aire: refrigeración por defecto con aire de (nombre gpu),
--y en el caso de ser liquida se especificaría el modelo concreto ahora sí. También, solo tendrá atributo consumo en caso
--de ser refrigeracion liquida, ya que de ser aire, seria el mismo que el de la gpu (se ignora restar el consumo de los 
--ventiladores a remover en caso de ser liquida)
CREATE TABLE refrigeracionGpu
(
 CodRefGpu INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Tipo VARCHAR(7) NOT NULL CHECK (Tipo IN ('aire(por defecto)','liquida')),
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
 Frecuencia FLOAT NOT NULL CHECK (Frecuencia BETWEEN 1000 AND 8000),
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
 FF VARCHAR(50) NOT NULL CHECK (FF IN ('ATX','Micro ATX','Mini ITX','E-ATX')),
 Stock INT NOT NULL CHECK (Stock>=0),
 Chipset VARCHAR(50) NOT NULL,
 Socket VARCHAR(50) NOT NULL CHECK (Socket IN ('AM4','AM5','LGA1200','LGA1700','LGA1851','sTRX4','sTR5','SP3','LGA4189','ARM','Apple M4')),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 CodFab INT NOT NULL,
 FOREIGN KEY (CodFab) REFERENCES fabricante(CodFab)
);
CREATE TABLE ordenador
(
 CodOrd INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Nombre VARCHAR(100) NOT NULL UNIQUE,
 Precio FLOAT NULL CHECK (Precio>0), --Se deja en null de momento, despues se actualizará con la suma de todos los precios asociados
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
 Precio FLOAT NOT NULL CHECK (Precio>0),
 CodOrd INT NOT NULL,
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd),
 CodMon INT NOT NULL,
 FOREIGN KEY (CodMon) REFERENCES montador(CodMon)
);
CREATE TABLE servicioMantenimiento
(
 CodSerMan INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Nombre VARCHAR(50) NOT NULL UNIQUE,
 Estrellas FLOAT NOT NULL CHECK (Estrellas BETWEEN 1 AND 5)
);
CREATE TABLE mantenimiento
(
 CodMan INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Tipo VARCHAR(50) NOT NULL CHECK (Tipo IN ('ComprobacionFuncionamientoInicial','Reparacion')),
 Precio FLOAT NOT NULL CHECK (Precio>0),
 Fecha DATE NOT NULL DEFAULT GETDATE(),
 Reporte VARCHAR(500) NOT NULL,
 CodOrd INT NOT NULL,
 CodSerMan INT NOT NULL,
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd),
 FOREIGN KEY (CodSerMan) REFERENCES servicioMantenimiento(CodSerMan)
);
CREATE TABLE usuario
(
 CodUsu INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Nombre VARCHAR(50) NOT NULL,
 Ape1Usu VARCHAR(50) NOT NULL,
 Ape2Usu VARCHAR(50) NULL,
 DNI CHAR(9) NOT NULL UNIQUE CHECK (LEN(DNI)=9 AND DNI LIKE (REPLICATE('[0-9]',8)+'[A-Z]')),
 FecNac DATE NOT NULL,
 EsAdministrador TINYINT NOT NULL
);
CREATE TABLE carrito
(
 CodCar INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Fecha DATE NOT NULL DEFAULT GETDATE(),
 PrecioTotal FLOAT NOT NULL CHECK (PrecioTotal>0),
 Estado VARCHAR(50) NOT NULL CHECK (Estado IN ('compraNoRealizada','compraNoRealizada')) DEFAULT 'compraNoRealizada',
 CodUsu INT NOT NULL,
 FOREIGN KEY (CodUsu) REFERENCES usuario(CodUsu)
);
CREATE TABLE contenido_carrito
(
 CodConCar INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Precio FLOAT NOT NULL,
 Cantidad INT NOT NULL,
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

--Inserción de datos iniciales (todas las verificaciones de logica interna, ej: que el socket de la cpu sea compatible
--con la placa base, o que no haya más de un carrito en estado de "compraNoRealizada"; se realizará desde Java y no aqui, 
--los ordenadores preemontados iniciales que se agregarán cumpliran ya con todas las restricciones
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
       ('Crucial');
INSERT INTO cpu (Precio,Modelo,Consumo,Stock,Nucleos,Socket,Frecuencia,CodFab)
VALUES (0,'Ryzen 5 5600X',65,10,6,'AM4',3.7,1),
       (0,'Ryzen 7 5800X',105,8,8,'AM4',3.8,1),
       (0,'Core i5-12600K',125,15,10,'LGA1700',3.7,2),
       (0,'Core i7-13700K',125,12,16,'LGA1700',3.4,2),
       (0,'Ryzen 9 5950X',105,5,16,'AM4',3.4,1),
       (0,'Core i9-12900KS',150,7,16,'LGA1700',3.4,2),
       (0,'Threadripper 3990X',280,3,64,'sTRX4',2.9,1),
       (0,'EPYC 7763',280,2,64,'SP3',2.45,1),
       (0,'Xeon Platinum 8380',270,1,40,'LGA4189',2.3,2),
       (0,'Cortex-A76',5,50,4,'ARM',2.2,3),
       (0,'M4 Max 16-Core',60,20,16,'Apple M4',3.2,4),
       (0,'Core Ultra 9 285K',125,10,24,'LGA1851',3.5,2),
       (0,'Threadripper 7980X',350,5,64,'sTR5',3.2,1),
       (0,'Threadripper 7970X',350,8,32,'sTR5',4.0,1),
       (0,'Threadripper 7995WX',350,2,96,'sTR5',2.5,1);
INSERT INTO refrigeracionCpu (Precio,Modelo,Consumo,Tipo,Stock,CodFab)  
VALUES (0,'Cooler Master Hyper 212',20,'aire',15,1),  
       (0,'Corsair A500',25,'aire',10,2),  
       (0,'NZXT Kraken X53',30,'aire',5,3),  
       (0,'Be Quiet! Pure Rock 2',15,'aire',8,4),  
       (0,'Cooler Master Hyper 212 EVO',18,'aire',0,1),
       (0,'Corsair iCUE H100i Elite Capellix',50,'liquida',7,2),  
       (0,'NZXT Kraken Z73',60,'liquida',4,3),  
       (0,'Be Quiet! Silent Loop 2',45,'liquida',3,4),  
       (0,'Corsair iCUE H150i Elite Capellix',55,'liquida',12,2),  
       (0,'Cooler Master MasterLiquid ML240L',40,'liquida',0,1);
INSERT INTO refrigeracionCpu_aire (Velocidad, CodRefCpu)  
VALUES (1500,1),  
       (1800,2),  
       (NULL,3),
       (2500,4),  
       (NULL,5);
INSERT INTO refrigeracionCpu_liquida (PotBomb, CodRefCpu)  
VALUES (20,6),  
       (25,7),  
       (30,8),  
       (35,9),  
       (40,10);
INSERT INTO gpu (Precio,Modelo,Stock,VRAM,Frecuencia,TipoMem,Consumo,CodFab)  
VALUES (0,'NVIDIA GeForce RTX 3060',10,12,1.78,'GDDR6',170,1),  
       (0,'NVIDIA GeForce RTX 3080',0,10,1.44,'GDDR6X',320,1),  
       (0,'AMD Radeon RX 6700 XT',8,12,2.42,'GDDR6',230,2),  
       (0,'NVIDIA GeForce RTX 4090',0,24,2.52,'GDDR6X',450,1),  
       (0,'AMD Radeon RX 6800 XT',7,16,2.25,'GDDR6',300,2),  
       (0,'NVIDIA GeForce GTX 1650',15,4,1.50,'GDDR5',75,1),  
       (0,'NVIDIA Quadro RTX 8000',3,48,1.77,'GDDR6',295,1),  
       (0,'AMD Radeon RX 5700',10,8,1.73,'GDDR6',225,2),  
       (0,'NVIDIA Titan RTX',4,24,1.77,'GDDR6',280,1),  
       (0,'NVIDIA GeForce GTX 1050 Ti',12,4,1.39,'GDDR5',75,1);  
INSERT INTO refrigeracionGpu (Modelo,Tipo,Stock,CodFab)  
VALUES ('Refrigeración por defecto con aire de NVIDIA GeForce RTX 3060','aire(por defecto)',10,1),  
       ('Refrigeración por defecto con aire de NVIDIA GeForce RTX 3080','aire(por defecto)',5,1),  
       ('Refrigeración por defecto con aire de AMD Radeon RX 6700 XT','aire(por defecto)',8,2),  
       ('Refrigeración líquida Corsair iCUE H150i Elite Capellix','liquida',2,1),  
       ('Refrigeración por defecto con aire de NVIDIA GeForce RTX 4090','aire(por defecto)',3,1),  
       ('Refrigeración líquida NZXT Kraken Z63','liquida',7,2),  
       ('Refrigeración por defecto con aire de AMD Radeon RX 6800 XT','aire(por defecto)',10,2),  
       ('Refrigeración líquida ASUS ROG Strix LC 360','liquida',4,2),  
       ('Refrigeración por defecto con aire de NVIDIA Quadro RTX 8000','aire(por defecto)',3,1),  
       ('Refrigeración por defecto con aire de NVIDIA GeForce GTX 1650','aire(por defecto)',15,1),
       ('Refrigeración por defecto con aire de AMD Radeon RX 5700','aire(por defecto)',4,1),
	   ('Refrigeración por defecto con aire de NVIDIA Titan RTX','aire(por defecto)',7,1),
       ('Refrigeración por defecto con aire de NVIDIA GeForce GTX 1050 Ti','aire(por defecto)',23,1);
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
VALUES (0,25,50,4),  
       (0,25,50,6),  
       (0,25,50,8);
INSERT INTO ventilador (Precio,Modelo,Consumo,Velocidad,Stock,CodFab)  
VALUES (0,'Ventilador Noctua NF-A12x25 PWM',0.6,2000,10,1),  
       (0,'Ventilador Corsair ML120 PRO',0.4,2400,5,2),  
       (0,'Ventilador be quiet! Silent Wings 3',0.5,2200,8,1),  
       (0,'Ventilador Arctic P12 PWM',0.3,1800,15,3),  
       (0,'Ventilador Cooler Master MasterFan MF120R ARGB',0.6,2000,0,4),  
       (0,'Ventilador Thermaltake Riing Plus 12 RGB',0.7,1500,7,2),  
       (0,'Ventilador Fractal Design Dynamic X2 GP-12',0.4,1200,12,3),  
       (0,'Ventilador NZXT Aer P 120mm',0.5,1500,3,4),  
       (0,'Ventilador EVGA Hybrid 120mm',0.9,2200,0,5),  
       (0,'Ventilador Cooler Master SickleFlow 120mm',0.5,1800,6,1);
INSERT INTO ram (Precio,Modelo,Frecuencia,Tipo,Consumo,Stock,CodFab)  
VALUES (0,'Corsair Vengeance LPX 8GB DDR4',3200,'DDR4',1.2,10,1),  
       (0,'G.SKILL Ripjaws V 16GB DDR4',3600,'DDR4',1.35,5,2),  
       (0,'Kingston HyperX Fury 16GB DDR4',2666,'DDR4',1.2,0,3),  
       (0,'Crucial Ballistix 8GB DDR4',3000,'DDR4',1.35,8,4),  
       (0,'Corsair Vengeance LPX 32GB DDR4',2933,'DDR4',1.2,3,1),  
       (0,'ADATA XPG Z1 16GB DDR3',1600,'DDR3',1.5,12,2),  
       (0,'HyperX Predator 8GB DDR4',4000,'DDR4',1.35,0,5),  
       (0,'G.SKILL Trident Z 16GB DDR4',3200,'DDR4',1.35,7,3),  
       (0,'Patriot Viper 4 16GB DDR4',3600,'DDR4',1.35,6,4),  
       (0,'TeamGroup T-Force Delta RGB 8GB DDR4',3200,'DDR4',1.35,2,1),
       (0,'Samsung 8GB DDR3',1333,'DDR3',1.5,4,2),
       (0,'Crucial 4GB DDR2',800,'DDR2',1.8,0,3),
       (0,'Corsair Vengeance 16GB DDR5',4800,'DDR5',1.4,10,1),
       (0,'Kingston FURY Beast 32GB DDR5',5200,'DDR5',1.5,5,4),
       (0,'Corsair Dominator Platinum 8GB DDR5',6000,'DDR5',1.4,8,2);
INSERT INTO almacenamiento (Precio,Modelo,Capacidad,Consumo,Stock,CodFab)  
VALUES (0,'Samsung 970 EVO Plus 500GB',500,'5',20,1),  
       (0,'Kingston A2000 1TB',1000,'4.2',15,2),  
       (0,'Western Digital Blue 500GB',500,'3.5',25,3),  
       (0,'Crucial MX500 2TB',2000,'4.5',8,4),  
       (0,'Seagate Barracuda 4TB',4000,'6',12,5),  
       (0,'Intel Optane 905P 480GB',480,'8',6,1),  
       (0,'SanDisk Ultra 3D 1TB',1000,'4.5',18,2),  
       (0,'Corsair MP600 1TB',1000,'7.5',10,6),  
       (0,'ADATA SU800 512GB',512,'3.8',14,3),  
       (0,'Western Digital Black SN850 1TB',1000,'8',10,4);
INSERT INTO fuente (Precio,Modelo,Stock,Potencia,Eficiencia,CodFab)  
VALUES (0,'Corsair RM850x',50,850,0.9,1),  
       (0,'Seasonic Focus GX-750',30,750,0.92,2),  
       (0,'EVGA SuperNOVA 1000 G5',25,1000,0.94,3),  
       (0,'Cooler Master MWE Gold 650',40,650,0.85,4),  
       (0,'be quiet! Dark Power Pro 12 1500W',10,1500,0.94,5),  
       (0,'Thermaltake Toughpower PF1 750W',20,750,0.88,6),  
       (0,'Gigabyte AORUS P850W',35,850,0.9,7),  
       (0,'SilverStone Strider Platinum 1000W',15,1000,0.92,8),  
       (0,'Fractal Design Ion+ 860W',18,860,0.9,9),  
       (0,'Antec High Current Gamer 750W',22,750,0.86,10);
INSERT INTO chasis (Precio,Modelo,Color,Stock,Tamanio,CodFab)
VALUES (0,'NZXT H510','Negro',25,'Mid',1),
       (0,'Corsair iCUE 4000X','Blanco',15,'Mid',2),
       (0,'Fractal Design Meshify C','Negro',30,'Mid',3),
       (0,'Cooler Master MasterBox Q300L','Negro',40,'Micro',4),
       (0,'be quiet! Pure Base 500','Blanco',10,'Mid',5),
       (0,'Thermaltake Core P5','Negro',5,'Full Tower',6),
       (0,'Phanteks Eclipse P400A','Negro',20,'Mid',7),
       (0,'Lian Li PC-O11 Dynamic','Aluminio',18,'E-ATX',8),
       (0,'Corsair 275R Airflow','Negro',28,'Mid',9),
       (0,'Antec P120 Crystal','Blanco',12,'Mid',10);
INSERT INTO placaBase (Precio,maxRam,maxCpu,maxGpu,FF,Stock,Chipset,Socket,Modelo,CodFab)  
VALUES (0,0,0,0,'ATX',30,'X570','AM4','ASUS ROG Crosshair VIII Hero',1),  
       (0,0,0,0,'Micro ATX',25,'B460','LGA1200','MSI MAG B460M Mortar WiFi',2),  
       (0,0,0,0,'Mini ITX',18,'Z490','LGA1200','ASRock Z490 Phantom Gaming-ITX/TB3',3),  
       (0,0,0,0,'E-ATX',10,'TRX40','sTRX4','Gigabyte TRX40 AORUS XTREME',4),  
       (0,0,0,0,'ATX',40,'Z590','LGA1200','ASUS TUF Gaming Z590-Plus WiFi',5),  
       (0,0,0,0,'Micro ATX',50,'B550','AM4','MSI MAG B550M Mortar WiFi',6),  
       (0,0,0,0,'ATX',20,'Z690','LGA1700','Gigabyte Z690 AORUS Master',7),  
       (0,0,0,0,'E-ATX',5,'X299','LGA2066','ASRock X299 Taichi CL',8),  
       (0,0,0,0,'ATX',15,'B550','AM4','MSI MPG B550 Gaming Edge WiFi',9),  
       (0,0,0,0,'Mini ITX',12,'Z590','LGA1200','ASRock Z590 Phantom Gaming-ITX/TB3',10);

--Insercion de ordenadores preemontados con componentes compatibles
INSERT INTO ordenador (Nombre,Precio,Proposito,Stock,SO,CodCha,CodPB,CodAlmPrincipal)
VALUES ('Oficina Pro Max',NULL,'PC/Oficina',5,'Windows 11',1,7,1),
       ('Oficina Pro',NULL,'PC/Oficina',4,'Windows 10',4,6,2),
	   ('Workstation Pro',NULL,'workstation',2,'Windows 10 Pro',8,7,2),
	   ('Gaming Beast',NULL,'gaming',3,'Windows 11 Pro',9,7,8),
	   ('Gaming Lite',NULL,'gaming',2,'Windows 10',10,6,2),
	   ('Servidor Ultra',NULL,'servidor',2,'Windows Server 2019',6,7,2),
	   ('Embedded Compact',NULL,'embebido',3,'Linux Embedded',4,3,1),
	   ('HPC Titan',NULL,'cientifico',2,'Linux',6,7,8);
INSERT INTO ord_cpu (CodOrd,CodCpu,CodRefCpu,Cantidad)
VALUES (1,3,1,1),
       (2,1,2,1),
	   (3,4,6,2),
	   (4,4,9,1),
	   (5,1,1,1),
	   (6,4,6,1),
	   (7,10,1,1),
	   (8,6,9,1); 
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
VALUES (1,1,2),
	   (2,2,2),
	   (3,2,4),
	   (4,2,4),
	   (5,2,2),
	   (6,2,4),
	   (7,2,1),
	   (8,6,4);
INSERT INTO ord_fuen (CodOrd,CodFuen,Cantidad)
VALUES (1,1,1),
       (2,2,1),
	   (3,2,2),
	   (4,2,1),
	   (5,2,1),
	   (6,3,1),
	   (7,2,1),
	   (8,4,1);  
INSERT INTO ord_alm (CodOrd,CodAlmSecundario,Cantidad)
VALUES (1,2,1),(1,3,1),
	   (2,3,2),
	   (3,3,1),(3,5,1),
	   (4,3,1),(4,5,1),
	   (5,3,1),
	   (6,3,1),(6,4,1),
	   (7,3,1),
	   (8,10,1),(8,11,1);  
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

--Asignacion del precio de los ordenadores (suma de componentes + montaje + mantenimiento inicial)
UPDATE ordenador
SET Precio = (SELECT SUM(C.Precio) FROM ord_cpu OC JOIN cpu C ON (OC.CodCpu=C.CodCpu))

--añadir precio a todo + trigger para precio de ordenador
--añadir atributos de cantidad maxima de ram, cpus y (gpus) para placa base comprobando los datos de los ordenadores
--añadir triggers de insert y update que verifiquen que los datos introducidos al crear un ordenador sean compatibles
--añadir comentarios
--reglas de borrado en FKs
--revisar datos