CREATE DATABASE ProyectoProgramacionSqlServer
GO
USE ProyectoProgramacionSqlServer
GO

CREATE TABLE servicioMantenimiento
(
 CodSerMan INT NOT NULL PRIMARY KEY,
 Nombre VARCHAR(50) NOT NULL,
 Estrellas FLOAT NOT NULL
);
CREATE TABLE usuario
(
 CodUsu INT NOT NULL PRIMARY KEY,
 Nombre VARCHAR(50) NOT NULL,
 Ape1Usu VARCHAR(50) NOT NULL,
 Ape2Usu VARCHAR(50) NULL,
 DNI CHAR(9) NOT NULL,
 FecNac DATE NOT NULL,
 EsAdministrador TINYINT NOT NULL
);
CREATE TABLE carrito
(
 CodCar INT NOT NULL PRIMARY KEY,
 Fecha DATE NOT NULL,
 PrecioTotal FLOAT NOT NULL,
 Estado TINYINT
);
CREATE TABLE refrigeracionExtra
(
 CodRefExt INT NOT NULL,
 Modelo VARCHAR(100) NOT NULL,
 Consumo FLOAT NOT NULL,
 Velocidad FLOAT NOT NULL,
 Stock INT NOT NULL
);
CREATE TABLE refrigeracionCpu
(
 CodRefCpu INT NOT NULL PRIMARY KEY,
 Modelo VARCHAR(100) NOT NULL,
 Consumo FLOAT NOT NULL,
 Tipo VARCHAR(7) NOT NULL CHECK (Tipo LIKE 'aire' OR Tipo LIKE 'liquida'),
 Stock INT NOT NULL
);
CREATE TABLE refrigeracionCpu_aire
(
 Velocidad FLOAT NOT NULL,
 CodRefCpu INT NOT NULL,
 FOREIGN KEY (CodRefCpu) REFERENCES refrigeracionCpu(CodRefCpu)
);
CREATE TABLE refrigeracionCpu_liquida
(
 PotBomb FLOAT NOT NULL,
 CodRefCpu INT NOT NULL,
 FOREIGN KEY (CodRefCpu) REFERENCES refrigeracionCpu(CodRefCpu)
);
CREATE TABLE refrigeracionGpu
(
 CodRefGpu INT NOT NULL PRIMARY KEY,
 Modelo VARCHAR(100) NOT NULL,
 Tipo VARCHAR(7) NOT NULL CHECK (Tipo LIKE 'aire' OR Tipo LIKE 'liquida'),
 Stock INT NOT NULL
);
CREATE TABLE refrigeracionGpu_aire
(
 Velocidad FLOAT NOT NULL,
 CodRefGpu INT NOT NULL,
 FOREIGN KEY (CodRefGpu) REFERENCES refrigeracionGpu(CodRefGpu)
);
CREATE TABLE refrigeracionGpu_liquida
(
 PotBomb FLOAT NOT NULL,
 CodRefGpu INT NOT NULL,
 FOREIGN KEY (CodRefGpu) REFERENCES refrigeracionGpu(CodRefGpu)
);
CREATE TABLE fabricante
(
 CodFab INT NOT NULL PRIMARY KEY,
 NomFab VARCHAR(100) NOT NULL
);
CREATE TABLE cpu
(
 CodCpu INT NOT NULL PRIMARY KEY,
 Modelo VARCHAR(100) NOT NULL,
 Consumo FLOAT NOT NULL,
 Stock INT NOT NULL,
 Nucleos INT NOT NULL,
 Socket VARCHAR(50) NOT NULL,
 Frecuencia FLOAT NOT NULL
);
CREATE TABLE cpu_ref
(
 CodCpu INT NOT NULL,
 CodRefCpu INT NOT NULL,
 FOREIGN KEY (CodCpu) REFERENCES cpu(CodCpu),
 FOREIGN KEY (CodRefCpu) REFERENCES refrigeracionCpu(CodRefCpu)
);
CREATE TABLE gpu
(
 CodGpu INT NOT NULL PRIMARY KEY,
 Modelo VARCHAR(100) NOT NULL,
 Stock INT NOT NULL,
 VRAM FLOAT NOT NULL,
 Frecuencia FLOAT NOT NULL,
 TipoMem VARCHAR(10) NOT NULL,
 Consumo FLOAT NOT NULL
);
CREATE TABLE gpu_ref
(
 CodGpu INT NOT NULL,
 CodRefGpu INT NOT NULL,
 FOREIGN KEY (CodGpu) REFERENCES gpu(CodGpu),
 FOREIGN KEY (CodRefGpu) REFERENCES refrigeracionGpu(CodRefGpu)
);
CREATE TABLE ram
(
 CodRam INT NOT NULL PRIMARY KEY,
 Modelo VARCHAR(100) NOT NULL,
 Frecuencia FLOAT NOT NULL,
 Tipo VARCHAR(10) NOT NULL,
 Consumo FLOAT NOT NULL,
 Stock INT NOT NULL
);
CREATE TABLE almacenamiento
(
 CodAlm INT NOT NULL PRIMARY KEY,
 Modelo VARCHAR(100) NOT NULL,
 Capacidad FLOAT NOT NULL,
 Consumo FLOAT NOT NULL,
 Stock INT NOT NULL,
 Tipo VARCHAR(10) NOT NULL CHECK (Tipo LIKE 'principal' OR Tipo LIKE 'secundario')
);
CREATE TABLE almacenamiento_principal
(
 SO VARCHAR(100) NOT NULL,
 CodAlm INT NOT NULL,
 FOREIGN KEY (CodAlm) REFERENCES almacenamiento(CodAlm)
);
CREATE TABLE almacenamiento_secundario
(
 proposito VARCHAR(100) NOT NULL,
 CodAlm INT NOT NULL,
 FOREIGN KEY (CodAlm) REFERENCES almacenamiento(CodAlm)
);
CREATE TABLE fuente
(
 CodFuen INT NOT NULL PRIMARY KEY,
 Modelo VARCHAR(100) NOT NULL,
 Stock INT NOT NULL,
 Potencia FLOAT NOT NULL,
 Eficiencia FLOAT NOT NULL
);
CREATE TABLE chasis
(
 CodCha INT NOT NULL PRIMARY KEY,
 Modelo VARCHAR(100) NOT NULL,
 Color VARCHAR(40) NOT NULL,
 Stock INT NOT NULL,
 Tamanio VARCHAR(50)
);
CREATE TABLE placaBase
(
 CodPB INT NOT NULL PRIMARY KEY,
 FF VARCHAR(50) NOT NULL,
 Stock INT NOT NULL,
 Chipset VARCHAR(50) NOT NULL,
 Socket VARCHAR(50) NOT NULL,
 Modelo VARCHAR(100) NOT NULL
);
CREATE TABLE ordenador
(
 CodOrd INT NOT NULL PRIMARY KEY,
 Nombre VARCHAR(100) NOT NULL,
 Precio FLOAT NOT NULL,
 Proposito VARCHAR(15) NOT NULL CHECK (Proposito IN ('PC/Oficina','workstation','gaming','servidor','embebido','cientifico')),
 SO VARCHAR(100) NOT NULL,
 Stock INT NOT NULL
);
CREATE TABLE ordenador_PCOficina
(
 MainSoft VARCHAR(200) NOT NULL,
 CodOrd INT NOT NULL,
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd)
);
CREATE TABLE ordenador_workstation
(
 render TINYINT NOT NULL,
 certificado VARCHAR(100) NULL,
 CodOrd INT NOT NULL,
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd)
);
CREATE TABLE ordenador_gaming
(
 RGB TINYINT NOT NULL,
 OC TINYINT NOT NULL,
 CodOrd INT NOT NULL,
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd)
);
CREATE TABLE ordenador_servidor
(
 escalabilidad VARCHAR(5) NOT NULL CHECK (escalabilidad IN ('alta','media','baja')),
 CodOrd INT NOT NULL,
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd)
);
CREATE TABLE embebido
(
 SisTiemReal TINYINT NOT NULL,
 CodOrd INT NOT NULL,
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd)
);
CREATE TABLE cientifico
(
 multiCpu TINYINT NOT NULL,
 CodOrd INT NOT NULL,
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd)
);
CREATE TABLE montador
(
 CodMon INT NOT NULL PRIMARY KEY,
 Nombre VARCHAR(50) NOT NULL,
 Apellidos VARCHAR(100) NOT NULL,
 DNI CHAR(9) NOT NULL,
 FechaIni DATE NOT NULL
);
CREATE TABLE montaje
(
 CodMontaje INT NOT NULL PRIMARY KEY,
 Fecha DATE NOT NULL,
 Detalles VARCHAR(500) NOT NULL,
 Precio FLOAT NOT NULL,
 CodOrd INT NOT NULL,
 FOREIGN KEY (CodOrd) REFERENCES ordenador(CodOrd),
 CodMon INT NOT NULL,
 FOREIGN KEY (CodMon) REFERENCES montador(CodMon)
);
CREATE TABLE contenido_carrito
(
 
);