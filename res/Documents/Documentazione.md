# Documentazione
Questo documento fornisce una breve documentazione riguardante l'applicazione *PrisonBreakGame*.
L'applicazione è stata realizzata dal gruppo di lavoro **Corona-Extra** composto da: 
- [Michele Stelluti](https://github.com/MicheleStelluti)
- [Vincenzo Susso](https://github.com/VincenzoSusso)
- [Giuseppe Tanzi](https://github.com/Giuseppe-Tanzi)

## Indice

1. [Introduzione](#1-introduzione)
2. [Requisiti](#2-requisiti)
   1. [Requisiti funzionali](#21-requisiti-funzionali)
   2. [Requisiti non funzionali](#22-requisiti-non-funzionali)
3. [Divisione in package e classi implementate](#3-divisione-in-package-e-classi-implementate)
4. [Manuale utente](#4-manuale-utente)
   
## 1. Introduzione
L'applicazione integra due giochi:
- Fire House;
- Prison Break.

Entrambi sono giochi d'avventura testuali giocabili tramite interfaccia grafica basata su Java e avviabile su terminali Windows, Linux e Mac OS.
L'applicazione utilizza un'architettura client/server.
Per poter giocare ad uno dei due giochi, è obbligatorio registrarsi e effettuare, successivamente, il login. Dopodichè, il sistema offre la possibilità di iniziare una nuova partita o caricarne una già salvata sul database memorizzato sul server.
Il server contiene un database, il quale memorizza le credenziali di ogni utente e le partite salvate di ognuno dei giochi disponibili, associate ad ogni utente.
Ogni client ha la possibilità di memorizzare 1 partita per gioco disponibile: se è già presente una partita salvata, il sistema mostra un messaggio di errore, il quale avvisa che la partita già salvata potrà essere sovrascritta dalla partita attuale.
L'applicazione è estendibile attraverso l'inserimento di nuovi giochi da offrire all'utente.

## 2. Requisiti

### 2.1 Requisiti funzionali 
ID     | TITOLO | DESCRIZIONE 
:----: | ----- |  -------
`RF1`  | Iniziare una nuova partita     |  L'utente deve essere in grado di iniziare una nuova partita attraverso il comando play  
`RF2`  | Ricominciare una partita       |  L'utente durante una partita deve essere in grado di iniziare una nuova partita
`RF3`  | Uscire dall' applicazione | L'utente deve poter uscire dall'Applicazione con il comando quit  
`RF4`  | Muovere un Pedone |  L'utente deve essere in grado di poter inserire una notazione valida per muovere un Pedone
`RF5`  | Muovere un Re|  L'utente deve essere in grado di poter inserire una notazione valida per muovere il Re
`RF6`  | Muovere una Donna |  L'utente deve essere in grado di poter inserire una notazione valida per muovere  la Donna
`RF7`  | Muovere un Cavallo|  L'utente deve essere in grado di poter inserire una notazione valida per muovere un Cavallo
`RF8`  | Muovere un Alfiere |  L'utente deve essere in grado di poter inserire una notazione valida per muovere un Alfiere 
`RF9`  | Muovere una Torre|  L'utente deve essere in grado di poter inserire una notazione valida per muovere una Torre
`RF9`  | Catturare un pezzo |  L'utente deve essere in grado di poter inserire una notazione valida per eseguire la catturara classica di un pezzo
`RF10` | Cattura en Passant | L'utente deve essere in grado di eseguire, attraverso una notazione valida, la presa en Passant
`RF11` | Arrocco corto | L'utente deve essere in grado di eseguire, attraverso una notazione valida, l'Arrocco corto (chiamato anche arrocco lato Re)
`RF12` | Arrocco lungo | L'utente deve essere in grado di eseguire, attraverso una notazione valida, l'Arrocco lungo (chiamato anche arrocco lato Donna)
`RF13` | Visualizzare mosse | L'utente deve essere in grado, in qualunque punto della partita, con il comando moves, di visualizzare lo storico delle mosse.
`RF14` | Visualizzare catture | L'utente deve essere in grado, in qualunque punto della partita, con il comando captures, di visualizzare le catture di entrambi i giocatori
`RF15` | Consultare help | L'utente deve essere in grado di poter consultare l'help inline
`RF16` | Visualizzare scacchiera | L'utente deve essere in grado, in qualunque punto della partita, con il comando board, di poter visualizzare la scacchiera

### 2.2 Requisiti non funzionali
|  ID     | REQUISITO        | DESCRIZIONE                                                  |
| :---:   | ---------------- | :----------------------------------------------------------- |
| `RNF1`  | Affidabilità     | Il programma è capace di evitare che si verificano errori, malfunzionamenti o che siano prodotti risultati non corretti o inattesi. |
| `RNF2`  | Compatibilità    | È necessario essere in possesso di una macchina con uno dei seguenti SO: Windows, Mac OS, distribuzioni Linux |
| `RNF3`  | Efficienza       | Il sistema richiede un quantitativo basso di risorse. In particolare richiede: 50MB di RAM e 110MB di spazio su disco (immagine Docker) |
| `RNF4`  | Eseguibilità     | Per eseguire l'applicazione è necessario aver installato Docker sulla macchina o disporre di una Java Virtual Machine 8 (o superiore) |
| `RNF5`  | Operabilità      | L'applicazione non richiede l'installazione sulla macchina. Si presenta come eseguibile in formato `Jar` e la propria esecuzione avviene su Command Line |
| `RNF6`  | Estendibilità    | L'applicazione è predisposta a poter accettare qualsiasi cambiamento od aggiunta di nuove features |
| `RNF7`  | Interoperabilità | Il sistema è autonomo, pertanto non prevedere interazioni con sistemi esterni|
| `RNF8`  | Manutentabilità  | L'applicazione è facilmente manutentabile grazie alla modularità delle componenti, ai principi dell'Information Hiding applicati, la medio-alta coesione introdotta e la presentazione separata delle componenti |
| `RNF9`  | Modularità       | L'applicazione fa uso del concetto di modularità che aiuta a ridurre la complessità del problema e ne facilità l'estendibilità |
| `RNF10` | Portabilità      | Trattandosi di un gioco eseguito in locale, si suppone che la piattaforma hw/sw di utilizzo sia unica e stabile |
| `RNF11` | Riusabilità      | Alcuni componenti dell'applicazione potrebbero essere riutilizzati per realizzare giochi simili |
| `RNF12` | Scalabilità      | Il programma prevede l'esecuzione da parte di un singolo utente |
| `RNF13` | Sicurezza        | Il sistema non gestisce informazioni riservate               |
| `RNF14` | Stabilità        | Il sistema, dopo l'inserimento di un comando, deve rispondere entro 1 secondo |
| `RNF15` | Robustezza       | Il programma è capace di gestire le situazioni in cui si manifestano errori o eccezioni dovuti a mosse e/o comandi non validi |
| `RNF16` | Usabilità        | Il sistema accetta comandi di tipo stringa che permettono all'utente di selezionare una delle operazioni messe a disposizione dall'applicazione. Inoltre, accetta come input di gioco mosse descritte tramite notazione algebrica abbreviata  |
| `RNF17` | Utilizzo         | L'applicazione è rivolta ad utenti di qualsiasi fascia d'età. Il requisito minimo richiesto è un livello di conoscenza medio-bassa delle regole degli scacchi e della notazione utilizzata |


### 3 Divisione in package e classi implementate

Per una corretta modulazione del sistema sono stati creati diversi package:
- Il package *client* contiene l'intera interfaccia grafica e le classi inerenti al client. Sono state create diverse classi swing per la comunicazione con l'utente. Tra queste ci sono:
    - ClientMainClass, la quale si occupa di avviare la comunicazione con il server e l'interfaccia grafica del client;
    - GameView e ManagageGameView, le quali si occupano della gestione dell'interfaccia con l'utente riguardante i comandi dell'avventura testuale durante il gioco;
    - Login e ManageLogin, le quali si occupano della gestione dell'interfaccia con l'utente riguardante il login iniziale per accedere ad uno dei giochi presenti nel database presente sul server;
    - SignUp e ManageSignUp, le quali si occupano della gestione dell'interfaccia con l'utente riguardante la registrazione sul database, memorizzato sul server, necessaria al successivo login;
    - GameChooserView e ManageGameChooser, le quali si occupano della gestione dell'interfaccia con l'utente riguardante la scelta del gioco da avviare tra quelli presenti nel database.

- Il package *exceptions* contiene le varie eccezioni che potrebbero essere chiamate durante il gioco, tra cui:
    - InputExceptions, le quali sono eccezioni chiamate con la comparsa di errori di tipo lessicali, sintattici o di altro tipo riguardante l'input inviato dall'utente;
    - InventoryExceptions, le quali sono eccezioni chiamate con la comparsa di errori legati all'accesso all'inventario in game;
    - ObjectExceptions, le quali sono eccezioni chiamate con la comparsa di errori legati alla ricerca di oggetti da parte del client;
    - RoomExceptions, le quali sono eccezioni chiamate con la comparsa di errori legati al movimento del personaggio nelle varie stanze del gioco inacessibili all'utente.

- Il package *server* contiene:
    - una classe *ServerMainClass*, la quale si occupa della creazione del database e delle 3 tabelle utili alla memorizzazione delle credenziali utente e delle partite. Inoltre per ogni client connesso al server, essa crea un nuovo thread che si occuperà della gestione con esso;
    - una classe *RequestThread*, la quale si occupa della comunicazione con il client;
    - il package *games*, il quale contiene, a sua volta, i due giochi al momento disponibili:
        - il package *prisonBreak* contiene l'inizializzazione delle stanze, degli oggetti e dei comandi disponibili. Inoltre contiene un package *movement* il quale gestisce il movimento del personaggio nel gioco con le relative conseguenze dei comandi inseriti, tra cui la risoluzione di enigmi, blocco/sblocco delle stanze, presa degli oggetti e relativa aggiunta all'inventario, ecc...;
    - il package *parser* contiene le diverse classi utili alla verifica lessicale e sintattica dell'input inserito dall'utente e la consequenziale verifica semantica. La lingua utilizzata da entrambi i giochi è dipendente dalla lingua italiana, ma il parser è estendibile ad altre lingue.
    - il package *type* contiene le diverse entità riscontrabili nel gioco, tra cui l'inventario, le stanze, gli oggetti, oggetti contenitori, comandi e aggettivi. 

- Il package *utils* contiene classi utili all'intero sistema. Una tra queste è la classe *Password*, la quale contiene un metodo per l'operazione di *hash* della password e un metodo per il check della password inserita dall'utente con quella memorizzata in database. Entrambi i metodi fanno uso della libreria esterna *Bcrypt*.

## 4. Manuale utente
### Indice 
