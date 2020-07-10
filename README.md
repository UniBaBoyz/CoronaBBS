# CoronaBBS

La struttura della repository si presenta nel seguente modo:
```
|-- .github
|    |-- workflows
|    |      |-- maven.yml
|-- database
|    |-- coronaDB.db
|–– doc
|    |–– Documentazione.md
|    |-- img
|–– src
|    |–– main
|–– .gitignore
|-- Corona-Extra.iml
|–– README.md
|-- pom.xml
```

Nel seguito si dettagliano i ruoli dei diversi componenti:
- **.github/workflows/maven.yml**: dettaglia le direttive per assicurare la *continuous integration* attraverso l’uso di GitHub Actions;
- **database**: in questa cartella, all'avvio dell'applicazione, sarà creato il database necessario alla memorizzazione degli utenti e delle rispettive partite salvate;
- **doc**: in questa cartella è stata inserita tutta la documentazione relativa al progetto;
- **src**: la cartella principale del progetto, in cui è scritto tutto il codice dell’applicazione. In *main* ci sono i file sorgente;
- **.gitignore**: specifica tutti i file che devono essere esclusi dal sistema di controllo versione.
- **Corona-Extra.iml**: file usato per la gestione dell'applicazione Java
- **pom.xml**: file XML che descrive le dipendenze fra il progetto e le varie versioni di librerie necessarie nonché le dipendenze fra di esse.
