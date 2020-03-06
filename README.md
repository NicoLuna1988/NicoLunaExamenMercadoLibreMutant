# NicoLunaExamenMercadoLibreMutant
Examen Tecnico de Mercado Libre

El examen consiste en la creacion de una API REST que cumple lo siguiente.

- Analizar una secuencia de ADN y determinar si esta pertenece a un mutante o a un humano. 
- Brindar un Servicio de Estadisticas de todas las secuencias analizadas

Restricciones 

- Las bases nitrogenadas de la secuencia solo pueden ser (A,T,C,G)
- Solo es mutante, si se encuentra más de una secuencia de cuatro bases nitrogenadas iguales , de forma oblicua, horizontal o vertical.
- El tamaño de cada secuencia debe ser igual a la cantidad de secuencias enviadas formando una matriz (NxN).

El enunciado completo del examen se encuentra se encuentra [aqui](./DOC/ExamenMutantesML.pdf) .

## Especificaciones de la API
  - Java JDK 1.8
  - Maven
  - Spring Boot
  - JDBCTemplate
  - AWS with load balancer and database RDS
  - JUnit with Mockito 
  

## Setup

### Instrucciones
Para poder correr la app local es necesario instalar:
  - Java JDK 1.8
  - Maven

Luego
  - Clonar este repositorio : https://github.com/NicoLuna1988/NicoLunaExamenMercadoLibreMutant.git
  - Crear la Base de datos con el siguiente scrips
  ```
    CREATE SCHEMA `dbMutantExamenMercadoLibreNicoLuna` ;
    CREATE TABLE `dbMutantExamenMercadoLibreNicoLuna`.`Dna` (
   `IdDna` INT AUTO_INCREMENT NOT NULL,
   `DnaSequence` nvarchar(65535) not null,
   `IsMutant` boolean not null,
   `Activo` Bit NULL DEFAULT true,
   PRIMARY KEY (`IdDna`)) ENGINE=InnoDB AUTO_INCREMENT=1;
```
  - Cambiar el DataSource en application.properties para que apunte a su instancia local
 ```
  spring.datasource.url=jdbc:mysql://localhost:3306/dbMutantExamenMercadoLibreNicoLuna??profileSQL=true&useSSL=false
  ```
- Luego debe iniciar en su IDE MutanApplication

Una vez que la aplicacion se inicio, se pueden realizar las request a la API.

El puerto por defecto de la API es 8081.

 La API se encuentra hosteada en AWS.

### API Url

URL local: http://localhost:8081

URL AWS: http://nicolunaexamenmercadolibremutant-dev.eba-arx9qsfi.sa-east-1.elasticbeanstalk.com

### Servicios de la Aplicación
#### Mutant

Request: 
- POST http://nicolunaexamenmercadolibremutant-dev.eba-arx9qsfi.sa-east-1.elasticbeanstalk.com/mutant/

Request body (ADN mutante):

```
 {
 "dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
 }
```

Response:

```
Status 200 OK
```
```
{
    "ok": true,
    "errorCode": 0,
    "message": "Is Mutant"
}
```
Request body (ADN humano):

```
 {
 "dna":["ATGCCA", "CAGTGC", "TTCTGG", "AGAAGG", "CCCGTA", "TCGCTG"]
 }
```

Response:

```
 Status 403 Forbidden
```
```
 {
     "ok": true,
     "errorCode": 0,
     "message": "Is Human"
 }
```
Request body (ADN Erroneo):

```
  {"dna":["AT", "CAGTGC", "TTCTGG", "AGAAGG", "TCGCTG"]}
```

Response:

```
  500 Internal Server Error
```
```
{
    "ok": false,
    "errorCode": 1,
    "message": "It does not have the minimum characters to determine if it is mutant or human"
}
```
Request body (ADN vacio):

```
  {"dna":[]}
```

Response:

```
  500 Internal Server Error
```
```
{
    "ok": false,
    "errorCode": 1,
    "message": "Empty Sequence"
}
```
Request body (ADN Base Nitrogena Erronea):

```
  {"dna":["kkkkkk", "kkkkkk", "CAGTGC", "TTCTGG", "AGAAGG", "TCGCTG"]}
```

Response:

```
  500 Internal Server Error
```
```
{
    "ok": false,
    "errorCode": 2,
    "message": "DNA contains an invalid nitrogen base"
}
```
Se definieron 3 ErrorCode en base a la excepciones que la API pueda devolver.
 - InvalidDataReceived=1
 - IncorrectNitrogenBase=2
 - GeneralError=3
    
#### Stats

Request: 
- GET http://nicolunaexamenmercadolibremutant-dev.eba-arx9qsfi.sa-east-1.elasticbeanstalk.com/stats

```
Response: 200 OK
```
```
{
    "count_mutant_dna": 1,
    "count_human_dna": 2,
    "ratio": 0.5
}
```
Para las fluctuaciones agresivas de tráfico se monto esta API con AWS Elastic Beanstalk de Amazon con un Application Load Balancer para poder administrar el equilibrio de carga , el escalado automático y la monitorización del estado del micorservicio.




