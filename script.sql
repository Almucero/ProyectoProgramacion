CREATE DATABASE ProyectoProgramacionSqlServer_v54
GO
USE ProyectoProgramacionSqlServer_v54
GO

--Se establece el formato de la fecha en año-mes-dia, para evitar problemas al luego insertar datos
SET DATEFORMAT 'YMD';

--Creación de tablas
CREATE TABLE fabricante --Si se borra un fabricante, los productos que este tuviese se quedan en la bbdd, pero su fabricante sera null
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
 CodFab INT NULL FOREIGN KEY REFERENCES fabricante(CodFab) ON DELETE SET NULL ON UPDATE CASCADE
);
--en el caso de la refrigeración (aire o liquida), independientemente de si es de cpu o gpu (liquida solo), la mayoria de modelos deben de usarse en conjunto con
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
 CodFab INT NULL FOREIGN KEY REFERENCES fabricante(CodFab) ON DELETE SET NULL ON UPDATE CASCADE
);
CREATE TABLE refrigeracionCpu_aire
(
 Velocidad FLOAT NULL CHECK (Velocidad BETWEEN 0 AND 10000), --puede ser nulo si no tiene ventilador propio y depende de uno montable
 CodRefCpu INT NOT NULL PRIMARY KEY FOREIGN KEY REFERENCES refrigeracionCpu(CodRefCpu) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE refrigeracionCpu_liquida
(
 PotBomb FLOAT NOT NULL CHECK (PotBomb BETWEEN 1 AND 50),
 CodRefCpu INT NOT NULL PRIMARY KEY FOREIGN KEY REFERENCES refrigeracionCpu(CodRefCpu) ON DELETE CASCADE ON UPDATE CASCADE
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
 CodFab INT NULL FOREIGN KEY REFERENCES fabricante(CodFab) ON DELETE SET NULL ON UPDATE CASCADE
);
--en la siguiente tabla el nombre del modelo será, en caso de por aire: refrigeración por defecto con aire de (nombre gpu),
--y en el caso de ser liquida se especificaría el modelo concreto ahora sí. También, solo tendrá atributo consumo en caso
--de ser refrigeracion liquida, ya que de ser aire, seria el mismo que el de la gpu (se ignora restar el consumo de los 
--ventiladores a remover en caso de ser liquida)
CREATE TABLE refrigeracionGpu
(
 Precio FLOAT NULL CHECK (Precio>=0), --en caso de ser por aire el precio es 0 (viene incluido con la gpu)
 CodRefGpu INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Tipo VARCHAR(20) NOT NULL CHECK (Tipo IN ('aire(por defecto)','liquida')),
 Stock INT NOT NULL CHECK (Stock>=0),
 CodFab INT NULL FOREIGN KEY REFERENCES fabricante(CodFab) ON DELETE SET NULL ON UPDATE CASCADE
);
CREATE TABLE refrigeracionGpu_aire
(
 Velocidad FLOAT NOT NULL CHECK (Velocidad BETWEEN 1 AND 10000),
 CodRefGpu INT NOT NULL PRIMARY KEY FOREIGN KEY REFERENCES refrigeracionGpu(CodRefGpu) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE refrigeracionGpu_liquida
(
 PotBomb FLOAT NOT NULL CHECK (PotBomb BETWEEN 1 AND 50),
 Consumo FLOAT NOT NULL CHECK (Consumo>0),
 CodRefGpu INT NOT NULL PRIMARY KEY FOREIGN KEY REFERENCES refrigeracionGpu(CodRefGpu) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE ventilador
(
 Precio FLOAT NOT NULL CHECK (Precio>0),
 CodVent INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Consumo FLOAT NOT NULL CHECK (Consumo>0),
 Velocidad FLOAT NOT NULL CHECK (Velocidad BETWEEN 0 AND 10000),
 Stock INT NOT NULL CHECK (Stock>=0),
 CodFab INT NULL FOREIGN KEY REFERENCES fabricante(CodFab) ON DELETE SET NULL ON UPDATE CASCADE
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
 CodFab INT NULL FOREIGN KEY REFERENCES fabricante(CodFab) ON DELETE SET NULL ON UPDATE CASCADE
);
CREATE TABLE almacenamiento
(
 CodAlm INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Precio FLOAT NOT NULL CHECK (Precio>0),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Capacidad FLOAT NOT NULL CHECK (Capacidad BETWEEN 1 AND 10000),
 Consumo FLOAT NOT NULL CHECK (Consumo>0),
 Stock INT NOT NULL CHECK (Stock>=0),
 CodFab INT NULL FOREIGN KEY REFERENCES fabricante(CodFab) ON DELETE SET NULL ON UPDATE CASCADE
);
CREATE TABLE fuente
(
 CodFuen INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Precio FLOAT NOT NULL CHECK (Precio>0),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Stock INT NOT NULL CHECK (Stock>=0),
 Potencia FLOAT NOT NULL CHECK (Potencia BETWEEN 100 AND 2000),
 Eficiencia FLOAT NOT NULL CHECK (Eficiencia BETWEEN 0.5 AND 1),
 CodFab INT NULL FOREIGN KEY REFERENCES fabricante(CodFab) ON DELETE SET NULL ON UPDATE CASCADE
);
CREATE TABLE chasis
(
 CodCha INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Precio FLOAT NOT NULL CHECK (Precio>0),
 Modelo VARCHAR(100) NOT NULL UNIQUE,
 Color VARCHAR(40) NOT NULL,
 Stock INT NOT NULL CHECK (Stock>=0),
 Tamanio VARCHAR(50) CHECK (Tamanio IN ('Mini','Micro','Mid','Full Tower','E-ATX')),
 CodFab INT NULL FOREIGN KEY REFERENCES fabricante(CodFab) ON DELETE SET NULL ON UPDATE CASCADE
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
 CodFab INT NULL FOREIGN KEY REFERENCES fabricante(CodFab) ON DELETE SET NULL ON UPDATE CASCADE
);
CREATE TABLE ordenador
(
 CodOrd INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Nombre VARCHAR(100) NOT NULL UNIQUE,
 Precio FLOAT NOT NULL CHECK (Precio>=0), --Se deja a 0 de momento, despues se actualizará con la suma de todos los precios asociados
 Proposito VARCHAR(15) NOT NULL CHECK (Proposito IN ('PC/Oficina','workstation','gaming','servidor','embebido','cientifico')),
 Stock INT NOT NULL CHECK (Stock>=0),
 SO VARCHAR(30) NOT NULL,
 CodCha INT NOT NULL FOREIGN KEY REFERENCES chasis(CodCha),
 CodPB INT NOT NULL FOREIGN KEY REFERENCES placaBase(CodPB),
 CodAlmPrincipal INT NOT NULL FOREIGN KEY REFERENCES almacenamiento(CodAlm)
);
CREATE TABLE ord_cpu
(
 CodOrd INT NOT NULL FOREIGN KEY REFERENCES ordenador(CodOrd) ON DELETE CASCADE ON UPDATE CASCADE,
 CodCpu INT NOT NULL FOREIGN KEY REFERENCES cpu(CodCpu),
 CodRefCpu INT NOT NULL FOREIGN KEY REFERENCES refrigeracionCpu(CodRefCpu),
 Cantidad INT NOT NULL CHECK (Cantidad>0),
 PRIMARY KEY (CodOrd, CodCpu, CodRefCpu)
);
CREATE TABLE ord_gpu
(
 CodOrd INT NOT NULL FOREIGN KEY REFERENCES ordenador(CodOrd) ON DELETE CASCADE ON UPDATE CASCADE,
 CodGpu INT NOT NULL FOREIGN KEY REFERENCES gpu(CodGpu),
 CodRefGpu INT NOT NULL FOREIGN KEY REFERENCES refrigeracionGpu(CodRefGpu),
 Cantidad INT NOT NULL CHECK (Cantidad>0),
 PRIMARY KEY (CodOrd, CodGpu, CodRefGpu)
);
CREATE TABLE ord_vent
(
 CodOrd INT NOT NULL FOREIGN KEY REFERENCES ordenador(CodOrd) ON DELETE CASCADE ON UPDATE CASCADE,
 CodVent INT NOT NULL FOREIGN KEY REFERENCES ventilador(CodVent),
 Cantidad INT NOT NULL CHECK (Cantidad>0),
 PRIMARY KEY (CodOrd, CodVent)
);
CREATE TABLE ord_ram
(
 CodOrd INT NOT NULL FOREIGN KEY REFERENCES ordenador(CodOrd) ON DELETE CASCADE ON UPDATE CASCADE,
 CodRam INT NOT NULL FOREIGN KEY REFERENCES ram(CodRam),
 Cantidad INT NOT NULL CHECK (Cantidad>0),
 PRIMARY KEY (CodOrd, CodRam)
);
CREATE TABLE ord_fuen
(
 CodOrd INT NOT NULL FOREIGN KEY REFERENCES ordenador(CodOrd) ON DELETE CASCADE ON UPDATE CASCADE,
 CodFuen INT NOT NULL FOREIGN KEY REFERENCES fuente(CodFuen),
 Cantidad INT NOT NULL CHECK (Cantidad>0),
 PRIMARY KEY (CodOrd, CodFuen)
);
CREATE TABLE ord_alm
(
 CodOrd INT NOT NULL FOREIGN KEY REFERENCES ordenador(CodOrd) ON DELETE CASCADE ON UPDATE CASCADE,
 CodAlmSecundario INT NOT NULL FOREIGN KEY REFERENCES almacenamiento(CodAlm),
 Cantidad INT NOT NULL CHECK (Cantidad>0),
 PRIMARY KEY (CodOrd, CodAlmSecundario)
);
CREATE TABLE ordenador_PCOficina
(
 MainSoft VARCHAR(200) NOT NULL,
 CodOrd INT NOT NULL PRIMARY KEY FOREIGN KEY REFERENCES ordenador(CodOrd) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE ordenador_workstation
(
 render TINYINT NOT NULL,
 certificado VARCHAR(100) NULL,
 CodOrd INT NOT NULL PRIMARY KEY FOREIGN KEY REFERENCES ordenador(CodOrd) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE ordenador_gaming
(
 RGB TINYINT NOT NULL,
 OC TINYINT NOT NULL,
 CodOrd INT NOT NULL PRIMARY KEY FOREIGN KEY REFERENCES ordenador(CodOrd) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE ordenador_servidor
(
 escalabilidad VARCHAR(5) NOT NULL CHECK (escalabilidad IN ('alta','media','baja')),
 CodOrd INT NOT NULL PRIMARY KEY FOREIGN KEY REFERENCES ordenador(CodOrd) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE ordenador_embebido
(
 SisTiemReal TINYINT NOT NULL,
 CodOrd INT NOT NULL PRIMARY KEY FOREIGN KEY REFERENCES ordenador(CodOrd) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE ordenador_cientifico
(
 multiCpu TINYINT NOT NULL,
 CodOrd INT NOT NULL PRIMARY KEY FOREIGN KEY REFERENCES ordenador(CodOrd) ON DELETE CASCADE ON UPDATE CASCADE
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
 CodOrd INT NOT NULL FOREIGN KEY REFERENCES ordenador(CodOrd) ON DELETE CASCADE ON UPDATE CASCADE,
 CodMon INT NULL FOREIGN KEY REFERENCES montador(CodMon) ON DELETE SET NULL ON UPDATE CASCADE
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
 CodOrd INT NOT NULL FOREIGN KEY REFERENCES ordenador(CodOrd) ON DELETE CASCADE ON UPDATE CASCADE,
 CodSerTest INT NULL FOREIGN KEY REFERENCES servicioTesteo(CodSerTest) ON DELETE SET NULL ON UPDATE CASCADE
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
 PrecioTotal FLOAT NULL CHECK (PrecioTotal>=0), --Se deja a 0 y luego se cambia en base a las diferentes entradas en contenido_carrito
 Estado VARCHAR(50) NOT NULL CHECK (Estado IN ('compraRealizada','compraNoRealizada')) DEFAULT 'compraNoRealizada',
 CodUsu INT NOT NULL FOREIGN KEY REFERENCES usuario(CodUsu) ON DELETE CASCADE ON UPDATE CASCADE
);
--Esta tabla solo puede albercar un producto a la vez (en la cantidad que sea), hace referencia a una entrada de un carrito, 
--un carrito completo estará asociado a 1 o muchas entradas de esta tabla
CREATE TABLE contenido_carrito
(
 CodConCar INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
 Precio FLOAT NOT NULL CHECK (Precio>=0), --se deja a 0 y luega se actualiza en base a la cantidad
 Cantidad INT NOT NULL CHECK (Cantidad>0),
 CodCar INT NOT NULL FOREIGN KEY REFERENCES carrito(CodCar),
 CodOrd INT NULL FOREIGN KEY REFERENCES ordenador(CodOrd) ON DELETE CASCADE,
 CodCha INT NULL FOREIGN KEY REFERENCES chasis(CodCha),
 CodFuen INT NULL FOREIGN KEY REFERENCES fuente(CodFuen),
 CodPB INT NULL FOREIGN KEY REFERENCES placaBase(CodPB),
 CodAlm INT NULL FOREIGN KEY REFERENCES almacenamiento(CodAlm),
 CodRam INT NULL FOREIGN KEY REFERENCES ram(CodRam),
 CodGpu INT NULL FOREIGN KEY REFERENCES gpu(CodGpu),
 CodCpu INT NULL FOREIGN KEY REFERENCES cpu(CodCpu),
 CodVent INT NULL FOREIGN KEY REFERENCES ventilador(CodVent),
 CodRefCpu INT NULL FOREIGN KEY REFERENCES refrigeracionCpu(CodRefCpu),
 CodRefGpu INT NULL FOREIGN KEY REFERENCES refrigeracionGpu(CodRefGpu)
);
GO

--Triggers para eliminar ordenadores completos + sus relaciones con componentes, 
--o solo relaciones con componentes opcionales individuales opcionales,
--o relaciones con componentes necesarios + el ordenador entero si no quedan mas relaciones de ese tipo 
--(ej: relaciones con ram, no puede haber un pc con 0 unidades ram)
CREATE TRIGGER trg_InsteadDelete_Chasis
ON chasis
INSTEAD OF DELETE
AS
BEGIN
    -- Se eliminan los ordenadores que usan el chasis a borrar
    DELETE FROM ordenador
    WHERE CodCha IN (SELECT CodCha FROM DELETED);
    -- Se elimina el registro de chasis
    DELETE FROM chasis
    WHERE CodCha IN (SELECT CodCha FROM DELETED);
END;
GO

CREATE TRIGGER trg_InsteadDelete_PlacaBase
ON placaBase
INSTEAD OF DELETE
AS
BEGIN
    -- Se eliminan los ordenadores que usan la placa base a borrar
    DELETE FROM ordenador
    WHERE CodPB IN (SELECT CodPB FROM DELETED);
    -- Se elimina el registro de placa base
    DELETE FROM placaBase
    WHERE CodPB IN (SELECT CodPB FROM DELETED);
END;
GO

CREATE TRIGGER trg_InsteadDelete_Almacenamiento
ON almacenamiento
INSTEAD OF DELETE
AS
BEGIN
    -- Elimina ordenadores que tengan como almacenamiento principal alguno de los registros a borrar
    DELETE FROM ordenador
    WHERE CodAlmPrincipal IN (SELECT CodAlm FROM DELETED);
    -- Elimina relaciones de almacenamiento secundario en la tabla ord_alm
    DELETE FROM ord_alm
    WHERE CodAlmSecundario IN (SELECT CodAlm FROM DELETED);
    -- Finalmente, elimina el registro de almacenamiento
    DELETE FROM almacenamiento
    WHERE CodAlm IN (SELECT CodAlm FROM DELETED);
END;
GO

CREATE TRIGGER trg_InsteadDelete_CPU
ON cpu
INSTEAD OF DELETE
AS
BEGIN
    -- Se eliminan las relaciones en ord_cpu asociadas al CPU a borrar
    DELETE FROM ord_cpu
    WHERE CodCpu IN (SELECT CodCpu FROM DELETED);
    
    -- Se elimina el registro de CPU
    DELETE FROM cpu
    WHERE CodCpu IN (SELECT CodCpu FROM DELETED);
    
    -- Para cada ordenador afectado, si ya no quedan registros en ord_cpu, se elimina el ordenador
    DELETE FROM ordenador
    WHERE CodOrd IN (
         SELECT o.CodOrd
         FROM ordenador o
         WHERE NOT EXISTS (SELECT 1 FROM ord_cpu WHERE CodOrd = o.CodOrd)
    );
END;
GO

CREATE TRIGGER trg_InsteadDelete_Fuente
ON fuente
INSTEAD OF DELETE
AS
BEGIN
    DELETE FROM ord_fuen
    WHERE CodFuen IN (SELECT CodFuen FROM DELETED);
    
    DELETE FROM fuente
    WHERE CodFuen IN (SELECT CodFuen FROM DELETED);
    
    DELETE FROM ordenador
    WHERE CodOrd IN (
         SELECT o.CodOrd
         FROM ordenador o
         WHERE NOT EXISTS (SELECT 1 FROM ord_fuen WHERE CodOrd = o.CodOrd)
    );
END;
GO

CREATE TRIGGER trg_InsteadDelete_RAM
ON ram
INSTEAD OF DELETE
AS
BEGIN
    DELETE FROM ord_ram
    WHERE CodRam IN (SELECT CodRam FROM DELETED);
    
    DELETE FROM ram
    WHERE CodRam IN (SELECT CodRam FROM DELETED);
    
    DELETE FROM ordenador
    WHERE CodOrd IN (
         SELECT o.CodOrd
         FROM ordenador o
         WHERE NOT EXISTS (SELECT 1 FROM ord_ram WHERE CodOrd = o.CodOrd)
    );
END;
GO

CREATE TRIGGER trg_InsteadDelete_GPU
ON gpu
INSTEAD OF DELETE
AS
BEGIN
    DELETE FROM ord_gpu
    WHERE CodGpu IN (SELECT CodGpu FROM DELETED);
    
    DELETE FROM gpu
    WHERE CodGpu IN (SELECT CodGpu FROM DELETED);
END;
GO

CREATE TRIGGER trg_InsteadDelete_Ventilador
ON ventilador
INSTEAD OF DELETE
AS
BEGIN
    DELETE FROM ord_vent
    WHERE CodVent IN (SELECT CodVent FROM DELETED);
    
    DELETE FROM ventilador
    WHERE CodVent IN (SELECT CodVent FROM DELETED);
END;
GO

--Creación del procedimiento que actualizará el precio de los ordenadores en base a los componentes que este tenga + montaje + testeo, 
--conforme se vayan añadiendo/modificando/borrando datos
CREATE PROCEDURE recalculaPrecioTotal (@CodOrd INT)
AS
BEGIN
  UPDATE o
  SET o.Precio =
       ISNULL((SELECT c.Precio FROM chasis c WHERE (c.CodCha=o.CodCha)), 0) +
       ISNULL((SELECT pb.Precio FROM placaBase pb WHERE (pb.CodPB=o.CodPB)), 0) +
       ISNULL((SELECT a.Precio FROM almacenamiento a WHERE (a.CodAlm=o.CodAlmPrincipal)), 0) +
       ISNULL((SELECT SUM((cpu.Precio+refcpu.Precio)*oc.Cantidad) FROM ord_cpu oc JOIN cpu ON (cpu.CodCpu=oc.CodCpu) JOIN refrigeracionCpu refcpu ON (refcpu.CodRefCpu=oc.CodRefCpu) WHERE (oc.CodOrd=o.CodOrd)), 0) +
       ISNULL((SELECT SUM((g.Precio+refgpu.Precio)*og.Cantidad) FROM ord_gpu og JOIN gpu g ON (g.CodGpu=og.CodGpu) JOIN refrigeracionGpu refgpu ON (refgpu.CodRefGpu=og.CodRefGpu) WHERE (og.CodOrd=o.CodOrd)), 0) +
       ISNULL((SELECT SUM(v.Precio*ov.Cantidad) FROM ord_vent ov JOIN ventilador v ON (v.CodVent=ov.CodVent) WHERE (ov.CodOrd=o.CodOrd)), 0) +
       ISNULL((SELECT SUM(r.Precio*oram.Cantidad) FROM ord_ram oram JOIN ram r ON r.CodRam=oram.CodRam WHERE (oram.CodOrd=o.CodOrd)), 0) +
       ISNULL((SELECT SUM(f.Precio*ofu.Cantidad) FROM ord_fuen ofu JOIN fuente f ON (f.CodFuen=ofu.CodFuen) WHERE (ofu.CodOrd= o.CodOrd)), 0) +
       ISNULL((SELECT SUM(a2.Precio*oa.Cantidad) FROM ord_alm oa JOIN almacenamiento a2 ON (a2.CodAlm=oa.CodAlmSecundario) WHERE (oa.CodOrd=o.CodOrd)), 0) +
       ISNULL((SELECT SUM(m.Precio) FROM montaje m WHERE (m.CodOrd=o.CodOrd)), 0) +
       ISNULL((SELECT SUM(t.Precio) FROM testeo t WHERE (t.CodOrd=o.CodOrd)), 0)
  FROM ordenador o
  WHERE o.CodOrd = @CodOrd;
END;
GO

--Trigger para capturar inserciones, actualizaciones o borrados en la tabla "ordenador" y actualizar su precio total
CREATE TRIGGER trg_ord
ON ordenador
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
  DECLARE @CodOrd INT;
  DECLARE CodOrdCursor CURSOR LOCAL FOR SELECT DISTINCT CodOrd FROM (SELECT CodOrd FROM inserted UNION SELECT CodOrd FROM deleted) AS Cods;
  OPEN CodOrdCursor;
  FETCH NEXT FROM CodOrdCursor INTO @CodOrd;
  WHILE @@FETCH_STATUS = 0
  BEGIN
    EXEC recalculaPrecioTotal @CodOrd;
    FETCH NEXT FROM CodOrdCursor INTO @CodOrd;
  END;
  CLOSE CodOrdCursor;
  DEALLOCATE CodOrdCursor;
END;
GO

--Trigger para capturar inserciones, actualizaciones o borrados en la tabla "ord_cpu" y actualizar el precio total del ordenador asociado
CREATE TRIGGER trg_ord_cpu
ON ord_cpu
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
  DECLARE @CodOrd INT;
  DECLARE CodOrdCursor CURSOR LOCAL FOR SELECT DISTINCT CodOrd FROM (SELECT CodOrd FROM inserted UNION SELECT CodOrd FROM deleted) AS Cods;
  OPEN CodOrdCursor;
  FETCH NEXT FROM CodOrdCursor INTO @CodOrd;
  WHILE @@FETCH_STATUS = 0
  BEGIN
    EXEC recalculaPrecioTotal @CodOrd;
    FETCH NEXT FROM CodOrdCursor INTO @CodOrd;
  END;
  CLOSE CodOrdCursor;
  DEALLOCATE CodOrdCursor;
END;
GO

--Trigger para capturar inserciones, actualizaciones o borrados en la tabla "ord_gpu" y actualizar el precio total del ordenador asociado
CREATE TRIGGER trg_ord_gpu
ON ord_gpu
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
  DECLARE @CodOrd INT;
  DECLARE CodOrdCursor CURSOR LOCAL FOR SELECT DISTINCT CodOrd FROM (SELECT CodOrd FROM inserted UNION SELECT CodOrd FROM deleted) AS Cods;
  OPEN CodOrdCursor;
  FETCH NEXT FROM CodOrdCursor INTO @CodOrd;
  WHILE @@FETCH_STATUS = 0
  BEGIN
    EXEC recalculaPrecioTotal @CodOrd;
    FETCH NEXT FROM CodOrdCursor INTO @CodOrd;
  END;
  CLOSE CodOrdCursor;
  DEALLOCATE CodOrdCursor;
END;
GO

--Trigger para capturar inserciones, actualizaciones o borrados en la tabla "ord_vent" y actualizar el precio total del ordenador asociado
CREATE TRIGGER trg_ord_vent
ON ord_vent
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
  DECLARE @CodOrd INT;
  DECLARE CodOrdCursor CURSOR LOCAL FOR SELECT DISTINCT CodOrd FROM (SELECT CodOrd FROM inserted UNION SELECT CodOrd FROM deleted) AS Cods;
  OPEN CodOrdCursor;
  FETCH NEXT FROM CodOrdCursor INTO @CodOrd;
  WHILE @@FETCH_STATUS = 0
  BEGIN
    EXEC recalculaPrecioTotal @CodOrd;
    FETCH NEXT FROM CodOrdCursor INTO @CodOrd;
  END;
  CLOSE CodOrdCursor;
  DEALLOCATE CodOrdCursor;
END;
GO

--Trigger para capturar inserciones, actualizaciones o borrados en la tabla "ord_ram" y actualizar el precio total del ordenador asociado
CREATE TRIGGER trg_ord_ram
ON ord_ram
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
  DECLARE @CodOrd INT;
  DECLARE CodOrdCursor CURSOR LOCAL FOR SELECT DISTINCT CodOrd FROM (SELECT CodOrd FROM inserted UNION SELECT CodOrd FROM deleted) AS Cods;
  OPEN CodOrdCursor;
  FETCH NEXT FROM CodOrdCursor INTO @CodOrd;
  WHILE @@FETCH_STATUS = 0
  BEGIN
    EXEC recalculaPrecioTotal @CodOrd;
    FETCH NEXT FROM CodOrdCursor INTO @CodOrd;
  END;
  CLOSE CodOrdCursor;
  DEALLOCATE CodOrdCursor;
END;
GO

--Trigger para capturar inserciones, actualizaciones o borrados en la tabla "ord_fuen" y actualizar el precio total del ordenador asociado
CREATE TRIGGER trg_ord_fuen
ON ord_fuen
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
  DECLARE @CodOrd INT;
  DECLARE CodOrdCursor CURSOR LOCAL FOR SELECT DISTINCT CodOrd FROM (SELECT CodOrd FROM inserted UNION SELECT CodOrd FROM deleted) AS Cods;
  OPEN CodOrdCursor;
  FETCH NEXT FROM CodOrdCursor INTO @CodOrd;
  WHILE @@FETCH_STATUS = 0
  BEGIN
    EXEC recalculaPrecioTotal @CodOrd;
    FETCH NEXT FROM CodOrdCursor INTO @CodOrd;
  END;
  CLOSE CodOrdCursor;
  DEALLOCATE CodOrdCursor;
END;
GO

--Trigger para capturar inserciones, actualizaciones o borrados en la tabla "ord_alm" y actualizar el precio total del ordenador asociado
CREATE TRIGGER trg_ord_alm
ON ord_alm
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
  DECLARE @CodOrd INT;
  DECLARE CodOrdCursor CURSOR LOCAL FOR SELECT DISTINCT CodOrd FROM (SELECT CodOrd FROM inserted UNION SELECT CodOrd FROM deleted) AS Cods;
  OPEN CodOrdCursor;
  FETCH NEXT FROM CodOrdCursor INTO @CodOrd;
  WHILE @@FETCH_STATUS = 0
  BEGIN
    EXEC recalculaPrecioTotal @CodOrd;
    FETCH NEXT FROM CodOrdCursor INTO @CodOrd;
  END;
  CLOSE CodOrdCursor;
  DEALLOCATE CodOrdCursor;
END;
GO

--Trigger para capturar inserciones en la tabla "montaje" y actualizar el precio total del ordenador asociado
CREATE TRIGGER trg_montaje
ON montaje
AFTER INSERT
AS
BEGIN
  DECLARE @CodOrd INT;
  DECLARE CodOrdCursor CURSOR LOCAL FOR SELECT DISTINCT CodOrd FROM inserted;
  OPEN CodOrdCursor;
  FETCH NEXT FROM CodOrdCursor INTO @CodOrd;
  WHILE @@FETCH_STATUS = 0
  BEGIN
    EXEC recalculaPrecioTotal @CodOrd;
    FETCH NEXT FROM CodOrdCursor INTO @CodOrd;
  END;
  CLOSE CodOrdCursor;
  DEALLOCATE CodOrdCursor;
END;
GO

--Trigger para capturar inserciones en la tabla "testeo" y actualizar el precio total del ordenador asociado
CREATE TRIGGER trg_testeo
ON testeo
AFTER INSERT
AS
BEGIN
  DECLARE @CodOrd INT;
  DECLARE CodOrdCursor CURSOR LOCAL FOR SELECT DISTINCT CodOrd FROM inserted;
  OPEN CodOrdCursor;
  FETCH NEXT FROM CodOrdCursor INTO @CodOrd;
  WHILE @@FETCH_STATUS = 0
  BEGIN
    EXEC recalculaPrecioTotal @CodOrd;
    FETCH NEXT FROM CodOrdCursor INTO @CodOrd;
  END;
  CLOSE CodOrdCursor;
  DEALLOCATE CodOrdCursor;
END;
GO

--Trigger para actualizar precios en "contenido_carrito" y "carrito"
CREATE TRIGGER trg_actualiza_carrito
ON contenido_carrito
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
  --Almacena los carritos afectados en una tabla variable
  DECLARE @Carritos TABLE (CodCar INT);
  INSERT INTO @Carritos (CodCar) SELECT CodCar FROM inserted UNION SELECT CodCar FROM deleted;
  --Actualiza el precio de cada entrada en contenido_carrito para los carritos afectados
  UPDATE cc
  SET cc.Precio = cc.Cantidad *
      (CASE 
        WHEN cc.CodOrd IS NOT NULL THEN (SELECT o.Precio FROM ordenador o WHERE o.CodOrd = cc.CodOrd)
        WHEN cc.CodCha IS NOT NULL THEN (SELECT ch.Precio FROM chasis ch WHERE ch.CodCha = cc.CodCha)
        WHEN cc.CodFuen IS NOT NULL THEN (SELECT f.Precio FROM fuente f WHERE f.CodFuen = cc.CodFuen)
        WHEN cc.CodPB IS NOT NULL THEN (SELECT pb.Precio FROM placaBase pb WHERE pb.CodPB = cc.CodPB)
        WHEN cc.CodAlm IS NOT NULL THEN (SELECT a.Precio FROM almacenamiento a WHERE a.CodAlm = cc.CodAlm)
        WHEN cc.CodRam IS NOT NULL THEN (SELECT r.Precio FROM ram r WHERE r.CodRam = cc.CodRam)
        WHEN cc.CodGpu IS NOT NULL THEN (SELECT g.Precio FROM gpu g WHERE g.CodGpu = cc.CodGpu)
        WHEN cc.CodCpu IS NOT NULL THEN (SELECT cpu.Precio FROM cpu cpu WHERE cpu.CodCpu = cc.CodCpu)
        WHEN cc.CodVent IS NOT NULL THEN (SELECT v.Precio FROM ventilador v WHERE v.CodVent = cc.CodVent)
        WHEN cc.CodRefCpu IS NOT NULL THEN (SELECT rc.Precio FROM refrigeracionCpu rc WHERE rc.CodRefCpu = cc.CodRefCpu)
        WHEN cc.CodRefGpu IS NOT NULL THEN (SELECT rg.Precio FROM refrigeracionGpu rg WHERE rg.CodRefGpu = cc.CodRefGpu)
        ELSE 0
      END)
  FROM contenido_carrito cc
  WHERE cc.CodCar IN (SELECT CodCar FROM @Carritos);
  -- Actualiza el PrecioTotal del carrito sumando el precio de todas sus entradas
  UPDATE c
  SET c.PrecioTotal = ISNULL((SELECT SUM(cc.Precio) FROM contenido_carrito cc WHERE cc.CodCar = c.CodCar), 0) FROM carrito c WHERE c.CodCar IN (SELECT CodCar FROM @Carritos);
END;
GO

--Inserción de datos iniciales (todas las verificaciones de logica interna, ej: que el socket de la cpu sea compatible
--con la placa base a instalar en un ordenador, o que no haya más de un carrito en estado de "compraNoRealizada"; se realizará desde Java y no aqui, 
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
INSERT INTO refrigeracionGpu (Precio,Modelo,Tipo,Stock,CodFab)  
VALUES (0,'Refrigeración por defecto con aire de NVIDIA GeForce RTX 3060','aire(por defecto)',10,17),  
       (0,'Refrigeración por defecto con aire de NVIDIA GeForce RTX 3080','aire(por defecto)',5,17),  
       (0,'Refrigeración por defecto con aire de AMD Radeon RX 6700 XT','aire(por defecto)',8,13),  
       (200,'Refrigeración líquida Corsair iCUE H150i Elite Capellix','liquida',2,1),  
       (0,'Refrigeración por defecto con aire de NVIDIA GeForce RTX 4090','aire(por defecto)',3,17),  
       (180,'Refrigeración líquida NZXT Kraken Z63','liquida',7,5),  
       (0,'Refrigeración por defecto con aire de AMD Radeon RX 6800 XT','aire(por defecto)',10,13),  
       (220,'Refrigeración líquida ASUS ROG Strix LC 360','liquida',4,2),  
       (0,'Refrigeración por defecto con aire de NVIDIA Quadro RTX 8000','aire(por defecto)',3,17),  
       (0,'Refrigeración por defecto con aire de NVIDIA GeForce GTX 1650','aire(por defecto)',15,17),
       (0,'Refrigeración por defecto con aire de AMD Radeon RX 5700','aire(por defecto)',4,13),
	   (0,'Refrigeración por defecto con aire de NVIDIA Titan RTX','aire(por defecto)',7,17),
       (0,'Refrigeración por defecto con aire de NVIDIA GeForce GTX 1050 Ti','aire(por defecto)',23,17);
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
INSERT INTO refrigeracionGpu_liquida (PotBomb,Consumo,CodRefGpu)  
VALUES (25,50,4),  
       (25,50,6),  
       (25,50,8);
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

GO

--Insercion de ordenadores preemontados con componentes compatibles y precios auto-asignados con los triggers creados (se dejan a 0)
INSERT INTO ordenador (Nombre,Precio,Proposito,Stock,SO,CodCha,CodPB,CodAlmPrincipal)
VALUES ('Oficina Pro Max',0,'PC/Oficina',5,'Windows 11',1,7,1),
       ('Oficina Pro',0,'PC/Oficina',4,'Windows 10',4,6,2),
       ('Workstation Pro',0,'workstation',2,'Windows 10 Pro',8,4,2),
       ('Gaming Beast',0,'gaming',3,'Windows 11 Pro',9,7,8),
       ('Gaming Lite',0,'gaming',2,'Windows 10',10,6,2),
       ('Servidor Ultra',0,'servidor',2,'Windows Server 2019',6,7,2),
       ('Embedded Compact',0,'embebido',3,'Linux Embedded',1,7,1),
       ('HPC Titan',0,'cientifico',2,'Linux',6,8,8);
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

--Inserción de montadores
INSERT INTO montador (Nombre,Apellidos,DNI,FechaIni,Titulacion)  
VALUES ('Juan','Pérez Gómez','12345678A','2025-03-20','Ingeniería en Sistemas'),  
       ('Ana','López García','23456789B','2025-03-20','Técnico en Informática'),  
       ('Carlos','Martínez Sánchez','34567890C','2025-03-20','Licenciado en Ciencias de la Computación'),  
       ('Laura','Ramírez Torres','45678901D','2025-03-20','Grado en Ingeniería de Software'),  
       ('Pedro','Fernández Rodríguez','56789012E','2025-03-20','Técnico en Desarrollo de Aplicaciones'),  
       ('Sofía','González Pérez','67890123F','2025-03-20','Máster en Inteligencia Artificial'),  
       ('Diego','Hernández Ruiz','78901234G','2025-03-20','Ingeniería en Redes de Comunicación'),  
       ('Beatriz','Jiménez Morales','89012345H','2025-03-20','Grado en Matemáticas y Computación'),  
       ('Fernando','Vázquez Díaz','90123456I','2025-03-20','Diplomado en Desarrollo Web'),  
       ('María','Sánchez López','01234567J','2025-03-20','Máster en Ciberseguridad');

--Inserción de los montajes
INSERT INTO montaje (Detalles,Precio,CodOrd,CodMon)  
VALUES ('El montaje fue fluido, sin inconvenientes. Se organizó el cableado y la ventilación para mejorar la circulación de aire.',DEFAULT,1,3),  
       ('Se enfrentaron algunos problemas con la alineación del chasis, pero se resolvieron ajustando los soportes.',DEFAULT,2,5),  
       ('El ensamblaje de la workstation tomó más tiempo debido a la complejidad del sistema de refrigeración.',DEFAULT,3,1),  
       ('Montaje exitoso con optimización en la distribución de cables y colocación de la GPU sin interferencias.',DEFAULT,4,7),  
       ('La estructura del equipo gaming permitió una instalación rápida y eficiente, sin necesidad de ajustes adicionales.',DEFAULT,5,4),  
       ('El montaje del servidor requirió precisión en la conexión de múltiples unidades de almacenamiento.',DEFAULT,6,6),  
       ('Se presentaron inconvenientes con la compatibilidad del chasis, pero se solucionaron adaptando los puntos de anclaje.',DEFAULT,7,3),  
       ('El equipo HPC quedó ensamblado correctamente tras verificar la integración de los módulos de alto rendimiento.',DEFAULT,8,8);  

--Inserción de servicios de testeo
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

--Inserció de los testeos
INSERT INTO testeo (Precio,Fecha,Reporte,CodOrd,CodSerTest)  
VALUES (DEFAULT,'2024-01-25','Se realizaron pruebas de estabilidad y rendimiento. Todo funciona correctamente.',4,3),  
       (DEFAULT,DEFAULT,'Verificación de temperaturas y carga máxima. Sin anomalías detectadas.',1,5),  
       (DEFAULT,'2018-05-23','Se ejecutaron benchmarks de CPU y GPU. Resultados dentro de lo esperado.',6,1),  
       (DEFAULT,DEFAULT,'Comprobación de conexiones internas y sistema de refrigeración. Todo en orden.',3,7),  
       (DEFAULT,'2022-12-31','Se verificó compatibilidad de drivers y estabilidad del sistema.',8,2),  
       (DEFAULT,'2021-01-01','Test de estrés en múltiples aplicaciones. Rendimiento estable.',2,6),  
       (DEFAULT,DEFAULT,'Se revisó integridad de los componentes y configuración de BIOS.',7,3),  
       (DEFAULT,DEFAULT,'Análisis de voltajes y estabilidad energética. Sin fallos detectados.',5,1);  

--Inserción de usuarios (administradores y normales)
INSERT INTO usuario (Nombre,Ape1Usu,Ape2Usu,DNI,FecNac,DireccionCompleta,Correo,Contrasenia,EsAdministrador)
VALUES ('Juan','Pérez',NULL,'12345678A','2005-03-21','Calle Ficticia 123','juan.perez@gmail.com','contrasena123',0),
       ('Ana','López','García','23456789B','2004-07-12','Calle Real 456','ana.lopez@hotmail.com','contrasena456',0),
       ('Carlos','Martínez',NULL,'34567890C','2001-09-15','Avenida Principal 789','carlos.martinez@gmail.com','contrasena789',0),
       ('Laura','Ramírez','Torres','45678901D','2002-11-05','Plaza Mayor 101','laura.ramirez@yahoo.com','contrasena101',1),
       ('Pedro','Fernández',NULL,'56789012E','1999-02-28','Calle Falsa 202','pedro.fernandez@hotmail.com','contrasena202',0),
       ('Sofía','González','Pérez','67890123F','2003-04-10','Calle del Sol 303','sofia.gonzalez@gmail.com','contrasena303',1),
       ('Diego','Hernández',NULL,'78901234G','2000-06-01','Calle del Río 404','diego.hernandez@gmail.com','contrasena404',0),
       ('Beatriz','Jiménez','Morales','89012345H','2006-08-18','Avenida Libertad 505','beatriz.jimenez@hotmail.com','contrasena505',1),
       ('Fernando','Vázquez','Díaz','90123456I','2000-12-15','Calle del Mar 606','fernando.vazquez@gmail.com','contrasena606',0),
       ('María','Sánchez',NULL,'01234567J','2001-10-25','Avenida 25 707','maria.sanchez@yahoo.com','contrasena707',1);

--Inserción de carritos
INSERT INTO carrito (Fecha,PrecioTotal,Estado,CodUsu) 
VALUES ('2025-03-15',0,'compraRealizada',2),
       ('2025-03-18',0,'compraRealizada',5),
       ('2025-03-20',0,'compraRealizada',6),
       (DEFAULT,0,'compraRealizada',8);

--Inserción de los contenidos de los carritos
--(comprar refrigeración de GPU por aire no tendría sentido ya que su precio es 0, en java se evitará que esto ocurra, 
--permitiendo añadir solo CodRefGpu que correspondas a refrigeraciones liquidas)
INSERT INTO contenido_carrito (Precio,Cantidad,CodCar,CodOrd,CodCha,CodFuen,CodPB,CodAlm,CodRam,CodGpu,CodCpu,CodVent,CodRefCpu,CodRefGpu)
VALUES (0,1,1,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(0,5,1,NULL,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(0,8,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4),
       (0,1,2,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(0,1,2,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
       (0,1,3,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL),(0,2,3,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL),(0,1,3,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),
       (0,1,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL),(0,4,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL),(0,2,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL);
       
--añadir triggers de insert y update para ordenador y contenidos que verifique la compatibilidad, devolviendo codigos que java capturará, evitando realizar la comprobación en java
--de forma similar a esto:

/*CREATE TRIGGER verificar_compatibilidad_insert  
ON ordenador_contenidos  
INSTEAD OF INSERT  
AS  
BEGIN  
    DECLARE @Incompatible BIT = 0;  

    -- Simulación de comprobación de compatibilidad  
    IF EXISTS (SELECT 1 FROM inserted WHERE /* condición de incompatibilidad */)  
        SET @Incompatible = 1;  

    IF @Incompatible = 1  
    BEGIN  
        -- Lanzar error con código personalizado  
        THROW 50001, 'ERROR_1001: Componente incompatible', 1;  
        RETURN;  
    END  

    -- Si es compatible, proceder con la inserción  
    INSERT INTO ordenador_contenidos (col1, col2, ...)  
    SELECT col1, col2, ... FROM inserted;  
END;

try {
    PreparedStatement stmt = conexion.prepareStatement("INSERT INTO ordenador_contenidos (...) VALUES (...);");
    stmt.executeUpdate();
} catch (SQLException e) {
    String mensaje = e.getMessage();
    if (mensaje.contains("ERROR_1001")) {
        System.out.println("Componente incompatible. ¿Desea intentar otro?");
    }
}*/

--añadir comentarios