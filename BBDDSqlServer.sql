CREATE DATABASE ProyectoProgramacionSqlServer
GO
USE ProyectoProgramacionSqlServer
GO

CREATE TABLE fabricante
(
 CodFab INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 NomFab VARCHAR(100) NOT NULL
);
CREATE TABLE cpu
(
 CodCpu INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Consumo FLOAT NOT NULL CHECK (Consumo>0),
 Stock INT NOT NULL CHECK (Stock>=0),
 Nucleos INT NOT NULL CHECK (Nucleos BETWEEN 1 AND 256),
 Socket VARCHAR(50) NOT NULL,
 Frecuencia FLOAT NOT NULL CHECK (Frecuencia BETWEEN 1 AND 10),
 CodFab INT NOT NULL,
 FOREIGN KEY (CodFab) REFERENCES fabricante(CodFab)
);
CREATE TABLE gpu
(
 CodGpu INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Stock INT NOT NULL CHECK (Stock>=0),
 VRAM FLOAT NOT NULL CHECK (VRAM BETWEEN 1 AND 64),
 Frecuencia FLOAT NOT NULL CHECK (Frecuencia>0),
 TipoMem VARCHAR(10) NOT NULL CHECK (TipoMem IN ('GDDR5','GDDR6','GDDR7','GDDR6X','HBM','HBM2','HBM3')),
 Consumo FLOAT NOT NULL CHECK (Consumo>0),
 CodFab INT NOT NULL,
 FOREIGN KEY (CodFab) REFERENCES fabricante(CodFab)
);
CREATE TABLE refrigeracionExtra
(
 CodRefExt INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Consumo FLOAT NOT NULL CHECK (Consumo>0),
 Velocidad FLOAT NOT NULL CHECK (Velocidad BETWEEN 0 AND 10000),
 Stock INT NOT NULL CHECK (Stock>=0),
 CodFab INT NOT NULL,
 FOREIGN KEY (CodFab) REFERENCES fabricante(CodFab)
);
CREATE TABLE refrigeracionCpu
(
 CodRefCpu INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Consumo FLOAT NOT NULL CHECK (Consumo>0),
 Tipo VARCHAR(7) NOT NULL CHECK (Tipo IN ('aire','liquida')),
 Stock INT NOT NULL CHECK (Stock>=0),
 CodFab INT NOT NULL,
 FOREIGN KEY (CodFab) REFERENCES fabricante(CodFab)
);
CREATE TABLE refrigeracionCpu_aire
(
 Velocidad FLOAT NOT NULL CHECK (Velocidad BETWEEN 0 AND 10000),
 CodRefCpu INT NOT NULL PRIMARY KEY,
 FOREIGN KEY (CodRefCpu) REFERENCES refrigeracionCpu(CodRefCpu)
);
CREATE TABLE refrigeracionCpu_liquida
(
 PotBomb FLOAT NOT NULL CHECK (PotBomb BETWEEN 1 AND 50),
 CodRefCpu INT NOT NULL PRIMARY KEY,
 FOREIGN KEY (CodRefCpu) REFERENCES refrigeracionCpu(CodRefCpu)
);
CREATE TABLE refrigeracionGpu
(
 CodRefGpu INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Consumo FLOAT NOT NULL CHECK (Consumo>0),
 Tipo VARCHAR(7) NOT NULL CHECK (Tipo IN ('aire','liquida')),
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
 CodRefGpu INT NOT NULL PRIMARY KEY,
 FOREIGN KEY (CodRefGpu) REFERENCES refrigeracionGpu(CodRefGpu)
);
CREATE TABLE ram
(
 CodRam INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
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
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Capacidad FLOAT NOT NULL CHECK (Capacidad BETWEEN 1 AND 10000),
 Consumo FLOAT NOT NULL CHECK (Consumo>0),
 Stock INT NOT NULL CHECK (Stock>=0),
 Tipo VARCHAR(10) NOT NULL CHECK (Tipo IN ('principal','secundario')),
 CodFab INT NOT NULL,
 FOREIGN KEY (CodFab) REFERENCES fabricante(CodFab)
);
CREATE TABLE almacenamiento_principal
(
 SO VARCHAR(100) NOT NULL,
 CodAlm INT NOT NULL PRIMARY KEY,
 FOREIGN KEY (CodAlm) REFERENCES almacenamiento(CodAlm)
);
CREATE TABLE almacenamiento_secundario
(
 proposito VARCHAR(100) NOT NULL,
 CodAlm INT NOT NULL PRIMARY KEY,
 FOREIGN KEY (CodAlm) REFERENCES almacenamiento(CodAlm)
);
CREATE TABLE fuente
(
 CodFuen INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
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
 FF VARCHAR(50) NOT NULL CHECK (FF IN ('ATX','Micro ATX','Mini ITX','E-ATX')),
 Stock INT NOT NULL CHECK (Stock>=0),
 Chipset VARCHAR(50) NOT NULL,
 Socket VARCHAR(50) NOT NULL CHECK (Socket IN ('AM4','AM5','LGA1200','LGA1151','sTRX4','LGA2066')),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 CodFab INT NOT NULL,
 FOREIGN KEY (CodFab) REFERENCES fabricante(CodFab)
);
CREATE TABLE ordenador
(
 CodOrd INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Nombre VARCHAR(100) NOT NULL UNIQUE,
 Precio FLOAT NOT NULL CHECK (Precio>0),
 Proposito VARCHAR(15) NOT NULL CHECK (Proposito IN ('PC/Oficina','workstation','gaming','servidor','embebido','cientifico')),
 SO VARCHAR(100) NOT NULL,
 Stock INT NOT NULL CHECK (Stock>=0),
 CodCha INT NOT NULL,
 CodPB INT NOT NULL,
 FOREIGN KEY (CodCha) REFERENCES chasis(CodCha),
 FOREIGN KEY (CodPB) REFERENCES placaBase(CodPB)
);
CREATE TABLE ord_cpu
(
 CodOrd INT NOT NULL,
 CodCpu INT NOT NULL,
 PRIMARY KEY (CodOrd, CodCpu),
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd),
 FOREIGN KEY (CodCpu) REFERENCES cpu(CodCpu)
);
CREATE TABLE ord_gpu
(
 CodOrd INT NOT NULL,
 CodGpu INT NOT NULL,
 PRIMARY KEY (CodOrd, CodGpu),
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd),
 FOREIGN KEY (CodGpu) REFERENCES gpu(CodGpu)
);
CREATE TABLE ord_refCpu
(
 CodOrd INT NOT NULL,
 CodRefCpu INT NOT NULL,
 PRIMARY KEY (CodOrd, CodRefCpu),
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd),
 FOREIGN KEY (CodRefCpu) REFERENCES refrigeracionCpu(CodRefCpu)
);
CREATE TABLE ord_refGpu
(
 CodOrd INT NOT NULL,
 CodRefGpu INT NOT NULL,
 PRIMARY KEY (CodOrd, CodRefGpu),
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd),
 FOREIGN KEY (CodRefGpu) REFERENCES refrigeracionGpu(CodRefGpu)
);
CREATE TABLE ord_refExt
(
 CodOrd INT NOT NULL,
 CodRefExt INT NOT NULL,
 PRIMARY KEY (CodOrd, CodRefExt),
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd),
 FOREIGN KEY (CodRefExt) REFERENCES refrigeracionExtra(CodRefExt)
);
CREATE TABLE ord_ram
(
 CodOrd INT NOT NULL,
 CodRam INT NOT NULL,
 PRIMARY KEY (CodOrd, CodRam),
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd),
 FOREIGN KEY (CodRam) REFERENCES ram(CodRam)
);
CREATE TABLE ord_fuen
(
 CodOrd INT NOT NULL,
 CodFuen INT NOT NULL,
 PRIMARY KEY (CodOrd, CodFuen),
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd),
 FOREIGN KEY (CodFuen) REFERENCES fuente(CodFuen)
);
CREATE TABLE ord_alm
(
 CodOrd INT NOT NULL,
 CodAlm INT NOT NULL,
 PRIMARY KEY (CodOrd, CodAlm),
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd),
 FOREIGN KEY (CodAlm) REFERENCES almacenamiento(CodAlm)
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
 Nombre VARCHAR(50) NOT NULL,
 Estrellas FLOAT NOT NULL CHECK (Estrellas BETWEEN 1 AND 5)
);
CREATE TABLE mantenimiento
(
 CodMan INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Tipo VARCHAR(50) NOT NULL,
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
 CodRefExt INT NULL,
 FOREIGN KEY (CodRefExt) REFERENCES refrigeracionExtra(CodRefExt),
 CodRefCpu INT NULL,
 FOREIGN KEY (CodRefCpu) REFERENCES refrigeracionCpu(CodRefCpu),
 CodRefGpu INT NULL,
 FOREIGN KEY (CodRefGpu) REFERENCES refrigeracionGpu(CodRefGpu)
);

