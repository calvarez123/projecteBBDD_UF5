<div style="display: flex; width: 100%;">
    <div style="flex: 1; padding: 0px;">
        <p>© Albert Palacios Jiménez, 2023</p>
    </div>
    <div style="flex: 1; padding: 0px; text-align: right;">
        <img src="../../assets/ieti.png" height="32" alt="Logo de IETI" style="max-height: 32px;">
    </div>
</div>
<hr/>

### Exercici 0 (originalment un exercici d'exàmen)

##### Puntuació

- Passar els testos 'A': 2 punts
- Passar els testos 'B': 1 punt
- Passar els testos 'A' i 'B': (2 + 1) = 3 punts

S'entén que la puntuació màxima és de 3 punts

##### Enunciat

Fent servir el Singleton 'AppData' i una base de dades *sqlite* anomenada **dades.sqlite**, desenvolupa una aplicació que gestioni Estudiants, Assignatures i Matriculacions.


**Taula Estudiants:**

```sql
estudiantId: INTEGER (Clau Primària)
nom: (TEXT)
cognoms: (TEXT)
anyMatricula: INTEGER
```

**Taula Assignatures:**

```sql
assignaturaId: INTEGER (Clau Primària)
nom: VARCHAR
credits: INTEGER

```

**Taula Matriculacions:**

```sql
estudiantId: INTEGER (Clau Externa que apunta a Estudiants)
assignaturaId: INTEGER (Clau Externa que apunta a Assignatures)
notaFinal: DECIMAL
```


Defineix també les següents funcions, i mira l'exemple de sortida per saber com mostren les dades:



```java
createTables(): Crea les taules i les relacions en la base de dades.

// Crea les taules i les relacions en la base de dades.
void createTables();

// Afegir un estudiant
void addEstudiant(String nom, String cognoms, int anyMatricula);

// Afegir una assignatura
void addAssignatura(String nom, int credits);

// Matricular un estudiant
void addMatriculacio(int estudiantId, int assignaturaId, int notaFinal);

// Llista tots els estudiants
List<Estudiant> listEstudiants();

con esta estructura: 
"id: 1, nom: Zorglon, cognoms: Alpha , anyMatricula: 4"

// Llista totes les assignatures
List<Assignatura> listAssignatures();


con esta estructura: 
"id: 1, nom: Zorglon, credits: Alpha"


// Llista totes les matriculacions
List<Matriculacio> listMatriculacions();

con esta estructura: 
"id estudiante: 1, id asignatura: Zorglon, notaFinal: Alpha"


// Actualitza les dades d'un estudiant
void updateEstudiant(int estudiantId, String nom, String cognoms, int anyMatricula);

// Esborra un estudiant
void deleteEstudiant(int estudiantId);

// Esborra una assignatura
void deleteAssignatura(int assignaturaId);

// el nombre del alumno con la nota mas alta por id de asignatura
void nombreAlumnoPorAsignatura(int Assignatura);




Fes anar els programes:

```bash
./run.sh com.project.MainA

```

Assegura't que passa els testos:

```bash
./runTest.sh com.project.TestMain#testMainOutputA
./runTest.sh com.project.TestMain#testMainTablesA
./runTest.sh com.project.TestMain#testMainCallsA


```




Fes anar els programes:

```bash
./run.sh com.project.MainA
```

