package adventure.games.prisonbreak;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import adventure.GameDescription;
import adventure.exceptions.InventoryEmptyException;
import adventure.exceptions.InventoryFullException;
import adventure.exceptions.ObjectNotFoundException;
import adventure.parser.ParserOutput;
import adventure.type.Inventory;
import adventure.type.Room;
import adventure.type.TokenAdjective;
import adventure.type.TokenObject;
import adventure.type.TokenObjectContainer;
import adventure.type.TokenVerb;
import adventure.type.VerbType;

import static adventure.games.prisonbreak.ObjectType.*;
import static adventure.games.prisonbreak.RoomType.AIRDUCT;
import static adventure.games.prisonbreak.RoomType.AIRDUCTEAST;
import static adventure.games.prisonbreak.RoomType.AIRDUCTNORTH;
import static adventure.games.prisonbreak.RoomType.AIRDUCTWEST;
import static adventure.games.prisonbreak.RoomType.BASKETCAMP;
import static adventure.games.prisonbreak.RoomType.BENCH;
import static adventure.games.prisonbreak.RoomType.BRAWL;
import static adventure.games.prisonbreak.RoomType.BROTHERCELL;
import static adventure.games.prisonbreak.RoomType.CANTEEN;
import static adventure.games.prisonbreak.RoomType.CELL;
import static adventure.games.prisonbreak.RoomType.CORRIDOR;
import static adventure.games.prisonbreak.RoomType.DOORISOLATION;
import static adventure.games.prisonbreak.RoomType.ENDGAME;
import static adventure.games.prisonbreak.RoomType.ENDLOBBY;
import static adventure.games.prisonbreak.RoomType.FRONTBENCH;
import static adventure.games.prisonbreak.RoomType.GARDEN;
import static adventure.games.prisonbreak.RoomType.GENERATOR;
import static adventure.games.prisonbreak.RoomType.GYM;
import static adventure.games.prisonbreak.RoomType.INFIRMARY;
import static adventure.games.prisonbreak.RoomType.ISOLATION;
import static adventure.games.prisonbreak.RoomType.ISOLATIONCORRIDOREAST;
import static adventure.games.prisonbreak.RoomType.ISOLATIONCORRIDOREASTEAST;
import static adventure.games.prisonbreak.RoomType.ISOLATIONCORRIDOREASTEASTEAST;
import static adventure.games.prisonbreak.RoomType.ISOLATIONCORRIDORNORTH;
import static adventure.games.prisonbreak.RoomType.ISOLATIONCORRIDORNORTHNORTH;
import static adventure.games.prisonbreak.RoomType.ISOLATIONCORRIDORNORTHNORTHNORTH;
import static adventure.games.prisonbreak.RoomType.ISOLATIONCORRIDORSOUTH;
import static adventure.games.prisonbreak.RoomType.ISOLATIONCORRIDORSOUTHSOUTH;
import static adventure.games.prisonbreak.RoomType.ISOLATIONCORRIDORSOUTHSOUTHSOUTH;
import static adventure.games.prisonbreak.RoomType.LADDERS;
import static adventure.games.prisonbreak.RoomType.LOBBY;
import static adventure.games.prisonbreak.RoomType.LOBBYSOUTH;
import static adventure.games.prisonbreak.RoomType.ONLADDER;
import static adventure.games.prisonbreak.RoomType.OTHERCELL;
import static adventure.games.prisonbreak.RoomType.OUTISOLATION;
import static adventure.games.prisonbreak.RoomType.PASSAGENORTH;
import static adventure.games.prisonbreak.RoomType.PASSAGESOUTH;
import static adventure.games.prisonbreak.RoomType.SECRETPASSAGE;
import static adventure.games.prisonbreak.RoomType.WALL;
import static adventure.games.prisonbreak.RoomType.WINDOWINFIRMARY;


/**
 * @author Corona-Extra
 */
public class PrisonBreakGame extends GameDescription {

    private void initVerbs() {
        TokenVerb nord = new TokenVerb(VerbType.NORD);
        nord.setAlias(new HashSet<>(Arrays.asList("N", "Nord")));
        getTokenVerbs().add(nord);

        TokenVerb inventory = new TokenVerb(VerbType.INVENTORY);
        inventory.setAlias(new HashSet<>(Arrays.asList("Inv", "I", "Inventario")));
        getTokenVerbs().add(inventory);

        TokenVerb sud = new TokenVerb(VerbType.SOUTH);
        sud.setAlias(new HashSet<>(Arrays.asList("S", "Sud")));
        getTokenVerbs().add(sud);

        TokenVerb est = new TokenVerb(VerbType.EAST);
        est.setAlias(new HashSet<>(Arrays.asList("E", "Est")));
        getTokenVerbs().add(est);

        TokenVerb ovest = new TokenVerb(VerbType.WEST);
        ovest.setAlias(new HashSet<>(Arrays.asList("O", "Ovest")));
        getTokenVerbs().add(ovest);

        TokenVerb end = new TokenVerb(VerbType.END);
        end.setAlias(new HashSet<>(Arrays.asList("End", "Exit", "Fine", "Esci", "Muori", "Ammazzati", "Ucciditi",
                "Suicidati", "Crepa")));
        getTokenVerbs().add(end);

        TokenVerb look = new TokenVerb(VerbType.LOOK_AT);
        look.setAlias(new HashSet<>(Arrays.asList("Osserva", "Guarda", "Vedi", "Trova", "Cerca", "Descrivi",
                "Controlla", "Leggi")));
        getTokenVerbs().add(look);

        TokenVerb pickup = new TokenVerb(VerbType.PICK_UP);
        pickup.setAlias(new HashSet<>(Arrays.asList("Prendi", "Afferra", "Ottieni", "Raccogli")));
        getTokenVerbs().add(pickup);

        TokenVerb open = new TokenVerb(VerbType.OPEN);
        open.setAlias(new HashSet<>(Arrays.asList("Sblocca", "Apri")));
        getTokenVerbs().add(open);

        TokenVerb close = new TokenVerb(VerbType.CLOSE);
        close.setAlias(new HashSet<>(Arrays.asList("Chiudi", "Sbarra", "Richiudi")));
        getTokenVerbs().add(close);

        TokenVerb pull = new TokenVerb(VerbType.PULL);
        pull.setAlias(new HashSet<>(Arrays.asList("Trascina", "Tira")));
        getTokenVerbs().add(pull);

        TokenVerb turnOn = new TokenVerb(VerbType.TURN_ON);
        turnOn.setAlias(new HashSet<>(Arrays.asList("Accendi", "On")));
        getTokenVerbs().add(turnOn);

        TokenVerb turnOff = new TokenVerb(VerbType.TURN_OFF);
        turnOff.setAlias(new HashSet<>(Arrays.asList("Spegni", "Off")));
        getTokenVerbs().add(turnOff);

        TokenVerb push = new TokenVerb(VerbType.PUSH);
        push.setAlias(new HashSet<>(Arrays.asList("Premi", "Spingi", "Attiva", "Schiaccia", "Pigia")));
        getTokenVerbs().add(push);

        TokenVerb talk = new TokenVerb(VerbType.TALK_TO);
        talk.setAlias(new HashSet<>(Arrays.asList("Parla", "Chiacchiera", "Comunica", "Dialoga", "Conversa",
                "Affronta", "Ascolta", "Chiedi", "Grida", "Urla", "Mormora", "Sussurra", "Bisbiglia", "Conferisci")));
        getTokenVerbs().add(talk);

        TokenVerb eat = new TokenVerb(VerbType.EAT);
        eat.setAlias(new HashSet<>(Arrays.asList("Mangia", "Pranza", "Cena", "Divora", "Finisci")));
        getTokenVerbs().add(eat);

        TokenVerb play = new TokenVerb(VerbType.PLAY);
        play.setAlias(new HashSet<>(Arrays.asList("Gioca", "Allenati")));
        getTokenVerbs().add(play);

        TokenVerb walk = new TokenVerb(VerbType.WALK);
        walk.setAlias(new HashSet<>(Arrays.asList("Cammina", "Corri", "Vai", "Muoviti", "Striscia", "Avvicinati",
                "Avanza", "Prosegui")));
        getTokenVerbs().add(walk);

        TokenVerb standUp = new TokenVerb(VerbType.STAND_UP);
        standUp.setAlias(new HashSet<>(Arrays.asList("Alzati", "Svegliati")));
        getTokenVerbs().add(standUp);

        TokenVerb sitDown = new TokenVerb(VerbType.SIT_DOWN);
        sitDown.setAlias(new HashSet<>(Arrays.asList("Siediti", "Sdraiati", "Dormi", "Rilassati", "Abbassati")));
        getTokenVerbs().add(sitDown);

        TokenVerb climb = new TokenVerb(VerbType.CLIMB);
        climb.setAlias(new HashSet<>(Arrays.asList("Arrampicati", "Sali", "Scendi", "Buttati", "Scivola",
                "Scavalca", "Salta")));
        getTokenVerbs().add(climb);

        TokenVerb use = new TokenVerb(VerbType.USE);
        use.setAlias(new HashSet<>(Arrays.asList("Usa", "Testa", "Utilizza", "Adopera", "Usufruisci")));
        getTokenVerbs().add(use);

        //Exit from a room, not from the game
        TokenVerb exit = new TokenVerb(VerbType.EXIT);
        exit.setAlias(new HashSet<>(Arrays.asList("Indietreggia", "Ritorna", "Scappa",
                "Spostati", "Togliti", "Levati", "Fuggi")));
        getTokenVerbs().add(exit);

        TokenVerb enter = new TokenVerb(VerbType.ENTER);
        enter.setAlias(new HashSet<>(Arrays.asList("Entra", "Accedi")));
        getTokenVerbs().add(enter);

        TokenVerb accept = new TokenVerb(VerbType.ACCEPT);
        accept.setAlias(new HashSet<>(Arrays.asList("Accetta", "Sì", "Si", "Ok", "Okay", "Conferma", "D'accordo",
                "Perfetto", "Concedi")));
        getTokenVerbs().add(accept);

        TokenVerb decline = new TokenVerb(VerbType.DECLINE);
        decline.setAlias(new HashSet<>(Arrays.asList("Declina", "Rifiuta", "No", "Respingi", "Evita", "Rinuncia")));
        getTokenVerbs().add(decline);

        TokenVerb put_in = new TokenVerb(VerbType.PUT_IN);
        put_in.setAlias(new HashSet<>(Arrays.asList("Immetti", "Inserisci", "Introduci", "Prova")));
        getTokenVerbs().add(put_in);

        TokenVerb give = new TokenVerb(VerbType.GIVE);
        give.setAlias(new HashSet<>(Arrays.asList("Dai", "Lascia", "Cedi", "Dona", "Regala", "Poni", "Concedi", "Porgi",
                "Consegna", "Offri")));
        getTokenVerbs().add(give);

        TokenVerb make = new TokenVerb(VerbType.MAKE);
        make.setAlias(new HashSet<>(Arrays.asList("Fai", "Crea", "Prepara", "Inventa", "Mischia", "Mescola", "Produci",
                "Costruisci", "Fabbrica", "Realizza", "Genera", "Componi", "Origina")));
        getTokenVerbs().add(make);

        TokenVerb remove = new TokenVerb(VerbType.REMOVE);
        remove.setAlias(new HashSet<>(Arrays.asList("Rimuovi", "Leva", "Elimina")));
        getTokenVerbs().add(remove);

        TokenVerb destroy = new TokenVerb(VerbType.DESTROY);
        destroy.setAlias(new HashSet<>(Arrays.asList("Spezza", "Distruggi", "Fracassa", "Sgretola", "Rompi",
                "Frantuma", "Corrodi")));

        TokenVerb move = new TokenVerb(VerbType.MOVE);
        move.setAlias(new HashSet<>(Arrays.asList("Muovi", "Sposta", "Togli")));
        getTokenVerbs().add(move);

    }

    private void initRooms() {
        Room cell = new Room(CELL, "Cella 17", "Ti trovi nella tua cella 17, al momento sei da solo" +
                " visto che sei l’ultimo arrivato.");
        cell.setLook("La cella e' poco accogliente: e' presente un letto a castello molto scomodo e pieno di polvere, " +
                "un lavandino fissato al muro con delle viti e il water. Sul tavolo c’e' il tuo pranzo che emana " +
                "un odore non buonissimo. La stanza e' illuminata da una piccola finestra sbarrata e l’unica via di " +
                "uscita si trova a est, al momento aperta visto che e' l’ora d’aria e tutti i detenuti " +
                "devono raggiungere il giardino!");

        Room corridor = new Room(CORRIDOR, "Corridoio", "Ti trovi nel corridoio del carcere che si" +
                " estende da sud verso nord.");
        corridor.setLook("Si sentono tanti rumori e urla dei detenuti, a ovest la porta della tua cella aperta e" +
                " altre celle in cui non e' possibile entrare poiche' sono chiuse.");

        Room ladders = new Room(LADDERS, "Scalinata", "Ti trovi presso una scalinata, l’unica cosa" +
                "che puoi fare e' andare al piano di sotto oppure andare verso sud percorrendo il corridoio.");
        ladders.setLook("Puoi vedere i detenuti che si dirigono verso il giardino.");

        Room lobby = new Room(LOBBY, "Atrio", "Ti trovi in un grosso atrio di ingresso, puoi notare" +
                " un enorme porta aperta dove si intravede il giardino.");
        lobby.setLook("Il luogo e' affollato di guardie che controllano la situazione. Vedi a nord un enorme scalinata" +
                " che porta al piano superiore e a ovest le celle degl’altri detenuti. L’atrio si estende ancora " +
                "verso sud.");

        Room lobbySouth = new Room(LOBBYSOUTH, "Atrio", "Ti trovi a sud del grosso atrio di ingresso." +
                " Puoi notare che l'atrio prosegue sia a nord che a sud!");
        lobbySouth.setLook("Puoi notare a ovest le celle chiuse dei detenuti e a est la palestra.");

        Room lobbyEnd = new Room(ENDLOBBY, "Atrio", "Ti trovi alla fine del grosso atrio, a sud vedi " +
                " tante guardie, sembra una zona particolarmente protetta!!! Noti a est la sala per la mensa e a " +
                " ovest le celle chiuse dei detenuti!");
        lobbyEnd.setLook("Non c'è nulla di particolare! Puoi ritornare indietro verso nord!");

        Room garden = new Room(GARDEN, "Giardino", "Sei in un ampio giardino verde illuminato " +
                "dal sole, dietro di te la porta che collega al grosso atrio.");
        garden.setLook("Guardando a nord  puoi notare un grosso campo da basket, avanti a te a est un grosso muro " +
                "che separa il giardino dall’esterno con due enormi torri sulle quali ci sono le guardie come vedetta," +
                " a sud invece tre grosse panchine dove puoi sederti e rilassarti.");

        Room basket = new Room(BASKETCAMP, "Campo da basket", "Ti trovi nel campo di basket" +
                " momentaneamente vuoto.");
        basket.setLook("Puoi notare due canestri e un pallone in mezzo al campo.");

        Room wall = new Room(WALL, "Muro prigione", "Avvicinandoti alle mura le guardie ti danno " +
                "l’ordine di indietreggiare: rappresenti un potenziale pericolo. Non penso sia un’idea geniale " +
                "fuggire da qui, la zona e' troppo controllata.");
        wall.setLook("Non c'e' nulla di particolare tranne che un grosso muro in mattoni!");

        Room bench = new Room(BENCH, "Panchine", "Tutte le panchine sono occupati da un gruppo " +
                "di detenuti che ti guardano con aria sospetta.");
        bench.setLook("Non noti nulla di particolare in loro e nelle panchine, tranne in una dove a terra puoi " +
                "notare un oggetto di metallo simile ad una vite.");

        Room infirmary = new Room(INFIRMARY, "Infermeria", "E' una classica infermeria e ti trovi" +
                " sdraiato sul tuo letto.");
        infirmary.setLook("Aprendo gli occhi noti un tavolo con molti strumenti, un grosso armadio in legno, " +
                "una lavagna, una porta che si collega al corridoio e una finestra sbarrata a nord. Puoi osservare anche " +
                "un quadro di Donald Trump appeso alla parete e in alto un condotto d’aria nuovissimo che sembra " +
                "irraggiungibile.  Sembra non esserci nessuno oltre a te nella stanza, riesci solo ad udire delle " +
                "voci nel corridoio.");

        Room passage = new Room(SECRETPASSAGE, "Passaggio segreto",
                "Sei appena entrato nel passaggio segreto.");
        passage.setLook("Noti delle pareti in roccia un po’ malandate, un’ enorme parete sti blocca la strada" +
                " a nord, puoi solo andare ad sud o a nord o ritornare indietro nella tua cella prima che qualcuno" +
                " ti scopra!!!");
        passage.setLocked(true);

        Room passageSouth = new Room(PASSAGESOUTH, "Passaggio segreto",
                "Prosegui nel passaggio segreto e vedi una scala appoggiata al muro.");
        passageSouth.setLook("La scala sembra non portare da nessuna parte, infatti sopra ci sono solo" +
                " delle grate che porta al secondo piano in cui non e' possibile accedere. Il passaggio continua" +
                " ancora a sud oppure puoi sempre ritornare indietro prima che sia troppo tardi!");

        Room passageNorth = new Room(PASSAGENORTH, "Passaggio segreto",
                "Sembra che sei arrivato gia' in un vicolo cieco, vedi solo un enorme soffitto " +
                        "e una piccola grata posta in alto!");
        passageNorth.setLook("Non c'e' nient'altro di particolare.");

        Room generator = new Room(GENERATOR, "Stanza con generatore", "Sembra che il passaggio" +
                " finisca qui, sei in una piccola stanza tutta buia.");
        generator.setLook("Non riesci a vedere nulla tranne che qualche piccola luce lampeggiante di fronte a te!");

        Room onLadder = new Room(ONLADDER, "Sulla scala",
                "Sei salito sulla scala e noti un condotto d’aria un po’ vecchiotto.");
        onLadder.setLook("Sembra di aver già visto questo tipo di condotto da un’altra parte!");
        onLadder.setLocked(true);

        Room airDuct = new Room(AIRDUCT, "Condotto d'aria", "Sei riuscito ad entrare nel condotto," +
                " strisci piano cercando di fare meno rumore possibile.");
        airDuct.setLook("Ci sono molte ragnatele e il condotto sembra non utilizzato, cerca di fare veloce in modo" +
                " da non risultare assente all’appello! Il condotto si divide in tre strade diverse, una a nord," +
                " una ad est e l’altra a ovest.");

        Room airDuctEast = new Room(AIRDUCTEAST, "Condotto d'aria", "Ti ritrovi in un vicolo ceco, " +
                "sotto di te c’e' un’enorme grata.");
        airDuctEast.setLook("Puoi osservare un’altra cella di un detenuto.");

        Room airDuctNorth = new Room(AIRDUCTNORTH, "Condotto d'aria", "Vedi una grossa grata e " +
                "senti delle voci simili a quelle che sentivi quando eri in infermeria.");
        airDuctNorth.setLook("C'e' solo una grossa grata");

        Room airDuctWest = new Room(AIRDUCTWEST, "Condotto d'aria",
                " Il condotto si fa sempre piu' stretto e non riesci piu' a passare! " +
                        "Non sembra sia la strada giusta!");
        airDuctWest.setLook("Scorgi pero' in lontananza un piccolo oggetto!");

        Room otherCell = new Room(OTHERCELL, "Cella detenuto", "La cella e' controllata da un " +
                "poliziotto e poi non mi sembra il caso di intrufolarsi in una cella di un detenuto. Rischieresti " +
                "di mandare a rotoli il piano!!!");
        otherCell.setLook("Meglio non perdere tempo qui!");
        otherCell.setLocked(true);

        Room canteen = new Room(CANTEEN, "Mensa", " Ti trovi in una grossa mensa e puoi vedere " +
                "i detenuti che stanno mangiando.");
        canteen.setLook("Uno di loro si avvicina a te e sussurrando ti chiede se sei interessato a qualche oggetto" +
                " che lui possiede. Ovviamente ogni oggetto ha un costo ma tu non possiedi alcun soldo, " +
                "per averne uno quindi sarei costretto a trattare.");

        Room gym = new Room(GYM, "Palestra", "Ti trovi nella palestra del carcere, " +
                "e' molto grande e non e' tenuta in ottimo stato, alcuni detenuti si stanno allenando.");
        gym.setLook("Intorno a te vedi tanti attrezzi per poterti allenare e aumentare la tua forza.");

        Room outIsolation = new Room(OUTISOLATION, "Fuori cella isolamento",
                "Sei di fronte la cella di isolamento dove e' collocato tuo fratello. " +
                        "Essendo in un carcere di massima sicurezza, la porta " +
                        "e' controllata da un paio di guardie.");
        outIsolation.setLook("Puoi notare da lontano che non si tratta di una semplice porta ma di una porta che " +
                "puo' essere aperta solo tramite un PIN segreto.");

        Room doorIsolation = new Room(DOORISOLATION, "Porta isolamento",
                "Sei di fronte ad una porta blindata che come gia' ti sembrava e' possibile " +
                        "aprire tramite un PIN segreto.");
        doorIsolation.setLook("Il pin e' conosciuto solo dalle guardie e quindi ti e' impossibile reperirlo!" +
                " A meno che non vuoi iniziare a sparare numeri a caso devi trovare assolutamente un’altra soluzione" +
                " prima che le luci si accendano e le guardie tornino!");
        doorIsolation.setLocked(true);

        Room isolation = new Room(ISOLATION, "Dentro isolamento",
                "La porta si apre e ti trovi dentro il luogo dove si trovano le celle isolamento. " +
                        "Ci sono tre lunghi corridoi, uno a est, uno a sud e l’altro a nord!");
        isolation.setLook("Non noti nient’altro di particolare!");
        isolation.setLocked(true);

        Room isolationCorridorNorth = new Room(ISOLATIONCORRIDORNORTH, "Corridoio nord isolamento",
                "Prosegui nel corridoio a nord, ci sono tante celle chiuse di prigionieri in isolamento." +
                        " Provi a gridare (ma non troppo), il nome di tuo fratello ma non risponde nessuno!");
        isolationCorridorNorth.setLook("Non noti nient’altro di particolare!");

        Room isolationCorridorNorthNorth = new Room(ISOLATIONCORRIDORNORTHNORTH, "Corridoio nord isolamento",
                "La speranza e' l’ultima a morire ma penso proprio che tuo fratello non si trovi in questo" +
                        " corridoio! ");
        isolationCorridorNorthNorth
                .setLook("Puoi solo osservare altre celle di detenuti in cui non e' possibile entrare.");

        Room isolationCorridorNorthNorthNorth = new Room(ISOLATIONCORRIDORNORTHNORTHNORTH,
                "Corridoio nord isolamento", "Il corridoio termina" +
                " con una grossa parete di fronte e te, hai visto tutte le celle ma di tuo fratello nemmeno l’ombra, " +
                "te l’avevo detto io!!!");
        isolationCorridorNorthNorthNorth.setLook("Non c'e' nulla, puoi solo ritornare indietro!!!");

        Room isolationCorridorEast = new Room(ISOLATIONCORRIDOREAST, "Corridoio est isolamento",
                "Prosegui nel corridoio a est, ci sono tante celle chiuse di prigionieri in isolamento. " +
                        "Provi a gridare (ma non troppo), il nome di tuo fratello ma non risponde nessuno!");
        isolationCorridorEast.setLook("Non noti nient’altro di particolare!");

        Room isolationCorridorEastEast = new Room(ISOLATIONCORRIDOREASTEAST, "Corridoio est isolamento",
                "La speranza e' l’ultima a morire ma penso proprio che tuo fratello non si trovi " +
                        "in questo corridoio! ");
        isolationCorridorEastEast
                .setLook("Puoi solo osservare altre celle di detenuti in cui non e' possibile entrare.");

        Room isolationCorridorEastEastEast = new Room(ISOLATIONCORRIDOREASTEASTEAST, "Corridoio est isolamento",
                "Il corridoio termina con una grossa parete di fronte e te, hai visto tutte le celle, " +
                        "ma di tuo fratello nemmeno l’ombra, te l’avevo detto io!!!");
        isolationCorridorEastEastEast.setLook("Non c'e' nulla, puoi solo ritornare indietro!!!");

        Room isolationCorridorSouth = new Room(ISOLATIONCORRIDORSOUTH, "Corridoio sud isolamento",
                "Prosegui nel corridoio a sud, ci sono tante celle chiuse di prigionieri in isolamento." +
                        " Provi a gridare (ma non troppo)!");
        isolationCorridorSouth.setLook("Senti un mormorio in lontananza!");

        Room isolationCorridorSouthSouth = new Room(ISOLATIONCORRIDORSOUTHSOUTH, "Corridoio sud isolamento",
                "Il mormorio si fa sempre piu' forte ma non hai ancora trovato la cella di tuo fratello.");
        isolationCorridorSouthSouth
                .setLook("Puoi osservare le celle degli altri prigionieri in cui non e' possibile entrare!");

        Room isolationCorridorSouthSouthSouth = new Room(ISOLATIONCORRIDORSOUTHSOUTHSOUTH,
                "Corridoio sud isolamento", "Il corridoio termina con " +
                "una grossa parete di fronte a te, hai visto tutte le celle tranne l’ultima!");
        isolationCorridorSouthSouthSouth
                .setLook("Avvicinandoti a questa senti la voce di tuo fratello, hai trovato la sua cella!!!");

        Room brotherCell = new Room(BROTHERCELL, "Cella fratello",
                "Sei nella cella di tuo fratello, l’aspetto della cella e' ripugnante.");
        brotherCell.setLook("Vedi solo un piccolo lavandino e un water e un letto in legno che sembra scomodissimo." +
                " Di fronte a te, tuo fratello con un’aria contenta di vederti ma allo stesso tempo sorpresa!");

        Room endGame = new Room(ENDGAME, "Fine", "Decidi di fuggire dalla finestra. Tu e tutta" +
                " la tua squadra usate il lungo cavo che collega la finestra al grande muro della prigione. Arrivati" +
                " sul muro riuscite a scavalcare con molta attenzione il filo spinato presente. Fate tutti un grande" +
                " salto fuori dalla prigione e scappate per 100 metri verso nord. Li trovare ad aspettarvi un " +
                "elicottero guidato da vostro padre, che sapeva tutto del vostro piano segreto e l’ora esatta in cui " +
                "dovevate attuarlo! Salite tutti sull’elicottero e' fuggite tutti insieme verso il Messico cosicché" +
                " nessuno potrà piu' ritrovarvi!\n\n" +
                "COMPLIMENTI HAI VINTO!");
        endGame.setLook("");
        endGame.setLocked(true);

        Room windowInfirmary = new Room(WINDOWINFIRMARY, "Finestra infermeria",
                "La finestra e' sbarrata e non sembra possibile aprirla! " +
                        "Puoi notare un lungo cavo che porta fino al muro della prigione!");
        windowInfirmary.setLook("Non noti nient'altro di particolare!");

        Room frontBench = new Room(FRONTBENCH, "Di fronte alle panchine",
                "Ti avvicini alla panchina per controllare meglio l’oggetto ma vieni subito fermato " +
                        "da un gruppo di neri che con aria minacciosa ti chiedono di allontanarti " +
                        "perche' la panchina e' la loro. Cosa scegli di fare?");
        frontBench.setLook("Vedi il gruppo di neri che ti fissa aspettando una tua mossa, non credo sia l’idea " +
                "migliore restare lì impalato.");

        Room brawl = new Room(BRAWL, "Rissa", "Sai benissimo che in un carcere non si possono" +
                " comprare panchine e ti avvicini nuovamente con l’intendo di prendere l’oggetto. Il gruppetto " +
                "ti blocca e il piu' grosso di loro ti tira un pugno contro il viso... \n Perdendo i sensi" +
                " non ti ricordi piu' nulla e ti svegli in infermeria.");
        brawl.setLook("");


        //maps
        cell.setEast(corridor);
        cell.setWest(passage);
        corridor.setWest(cell);
        corridor.setNorth(ladders);
        ladders.setSouth(corridor);
        ladders.setWest(otherCell);
        ladders.setEast(lobby);
        lobby.setWest(ladders);
        lobby.setEast(garden);
        lobby.setSouth(lobbySouth);
        lobbySouth.setEast(gym);
        lobbySouth.setNorth(lobby);
        lobbySouth.setWest(otherCell);
        lobbySouth.setSouth(lobbyEnd);
        garden.setEast(wall);
        garden.setWest(lobby);
        garden.setNorth(basket);
        garden.setSouth(bench);
        basket.setSouth(garden);
        wall.setWest(garden);
        bench.setNorth(garden);
        bench.setSouth(frontBench);
        frontBench.setNorth(bench);
        frontBench.setSouth(brawl);
        brawl.setNorth(frontBench);
        gym.setWest(lobbySouth);
        lobbyEnd.setEast(canteen);
        lobbyEnd.setWest(otherCell);
        lobbyEnd.setNorth(lobbySouth);
        lobbyEnd.setSouth(outIsolation);
        canteen.setWest(lobbyEnd);
        outIsolation.setNorth(lobbyEnd);
        outIsolation.setEast(doorIsolation);
        outIsolation.setWest(otherCell);
        doorIsolation.setWest(outIsolation);
        doorIsolation.setEast(isolation);
        isolation.setWest(doorIsolation);
        isolation.setEast(isolationCorridorEast);
        isolation.setNorth(isolationCorridorNorth);
        isolation.setSouth(isolationCorridorSouth);
        isolationCorridorEast.setEast(isolationCorridorEastEast);
        isolationCorridorEast.setWest(isolation);
        isolationCorridorEastEast.setEast(isolationCorridorEastEastEast);
        isolationCorridorEastEast.setWest(isolationCorridorEast);
        isolationCorridorEastEastEast.setWest(isolationCorridorEastEast);
        isolationCorridorNorth.setNorth(isolationCorridorNorthNorth);
        isolationCorridorNorth.setSouth(isolation);
        isolationCorridorNorthNorth.setNorth(isolationCorridorNorthNorthNorth);
        isolationCorridorNorthNorth.setSouth(isolationCorridorNorth);
        isolationCorridorNorthNorthNorth.setSouth(isolationCorridorNorthNorth);
        isolationCorridorSouth.setNorth(isolation);
        isolationCorridorSouth.setSouth(isolationCorridorSouthSouth);
        isolationCorridorSouthSouth.setNorth(isolationCorridorSouth);
        isolationCorridorSouthSouth.setSouth(isolationCorridorSouthSouthSouth);
        isolationCorridorSouthSouthSouth.setNorth(isolationCorridorSouthSouth);
        isolationCorridorSouthSouthSouth.setEast(brotherCell);
        brotherCell.setWest(isolationCorridorSouthSouthSouth);
        passage.setEast(cell);
        passage.setNorth(passageNorth);
        passage.setSouth(passageSouth);
        passageSouth.setNorth(passage);
        passageSouth.setSouth(generator);
        generator.setNorth(passageSouth);
        passageNorth.setSouth(passage);
        passageNorth.setNorth(onLadder);
        onLadder.setSouth(passageNorth);
        onLadder.setNorth(airDuct);
        airDuct.setSouth(onLadder);
        airDuct.setEast(airDuctEast);
        airDuct.setNorth(airDuctNorth);
        airDuct.setWest(airDuctWest);
        airDuctEast.setSouth(otherCell);
        airDuctEast.setWest(airDuct);
        airDuctWest.setEast(airDuct);
        airDuctNorth.setSouth(airDuct);
        infirmary.setNorth(windowInfirmary);
        windowInfirmary.setSouth(infirmary);
        windowInfirmary.setNorth(endGame);

        getRooms().add(cell);
        getRooms().add(corridor);
        getRooms().add(ladders);
        getRooms().add(lobby);
        getRooms().add(garden);
        getRooms().add(basket);
        getRooms().add(wall);
        getRooms().add(bench);
        getRooms().add(frontBench);
        getRooms().add(brawl);
        getRooms().add(lobbySouth);
        getRooms().add(otherCell);
        getRooms().add(gym);
        getRooms().add(canteen);
        getRooms().add(lobbyEnd);
        getRooms().add(outIsolation);
        getRooms().add(doorIsolation);
        getRooms().add(isolation);
        getRooms().add(isolationCorridorNorth);
        getRooms().add(isolationCorridorSouth);
        getRooms().add(isolationCorridorEast);
        getRooms().add(isolationCorridorNorthNorth);
        getRooms().add(isolationCorridorSouthSouth);
        getRooms().add(isolationCorridorEastEast);
        getRooms().add(isolationCorridorNorthNorthNorth);
        getRooms().add(isolationCorridorSouthSouthSouth);
        getRooms().add(isolationCorridorEastEastEast);
        getRooms().add(brotherCell);
        getRooms().add(passage);
        getRooms().add(passageSouth);
        getRooms().add(generator);
        getRooms().add(passageNorth);
        getRooms().add(onLadder);
        getRooms().add(airDuct);
        getRooms().add(airDuctEast);
        getRooms().add(airDuctWest);
        getRooms().add(airDuctNorth);
        getRooms().add(infirmary);
        getRooms().add(endGame);
        getRooms().add(windowInfirmary);

        TokenObject screw = new TokenObject(SCREW, new HashSet<>(Arrays.asList("Vite", "Chiodo")),
                "E' una semplice vite con inciso il numero di serie: 11121147.");
        bench.getObjects().add(screw);
        getRoom(CELL).setObjectsUsableHere(screw);

        TokenObject scotch = new TokenObject(SCOTCH, new HashSet<>(Arrays.asList("Scotch", "Nastro")),
                "E' un semplice scotch, dimenticato li forse da qualche operaio!");
        airDuctWest.getObjects().add(scotch);
        scotch.setPickupable(true);
        getRoom(ISOLATION).setObjectsUsableHere(scotch);

        TokenObject tools = new TokenObject(TOOLS, new HashSet<>(Arrays.asList("Attrezzi", "Manubri", "Pesi")),
                "Sono degli attrezzi da palestra, ottimi per allenarsi e aumentare la forza!");
        tools.setUsable(true);
        gym.getObjects().add(tools);
        gym.setObjectsUsableHere(tools);

        TokenObject food = new TokenObject(FOOD, new HashSet<>(Arrays.asList("Cibo", "Pranzo", "Cena", "Piatto",
                "Tavolo")),
                "C'è solo il tuo pranzo, puoi mangiarlo anche se non servirà a nulla.");
        food.setEatable(true);
        food.setPickupable(true);
        cell.getObjects().add(food);

        TokenObject ball = new TokenObject(BALL, new HashSet<>(Arrays.asList("Palla", "Pallone", "Basketball")),
                "E' un semplice pallone da basket.");
        ball.setPickupable(true);
        ball.setUsable(true);
        basket.getObjects().add(ball);
        passageSouth.getObjects().add(ball);

        TokenObject ladder = new TokenObject(LADDER, new HashSet<>(Arrays.asList("Scala", "Scaletta")),
                "E' solo una scala in legno, sembra molto leggera e facile da spostare.");
        ladder.setPushable(true);
        passageSouth.getObjects().add(ladder);

        TokenObject scalpel = new TokenObject(SCALPEL, new HashSet<>(Arrays.asList("Bisturi", "Lama")),
                "Sono solo tanti bisturi tutti uguali!");
        scalpel.setPickupable(true);
        scalpel.setUsable(true);
        infirmary.getObjects().add(scalpel);

        //TODO assegnare oggetto a Jonny Bello
        TokenObject hacksaw = new TokenObject(HACKSAW, new HashSet<>(Arrays.asList("Seghetto", "Sega", "Taglierino")),
                "E’ un seghetto molto affilato, potresti riuscire a rompere qualcosa.",
                new HashSet<>(Arrays.asList(new TokenAdjective(new HashSet<>(Arrays.asList("Rotto", "Distrutto",
                        "Devastato", "Spaccato"))), new TokenAdjective(new HashSet<>(Arrays.asList("Aggiustato",
                        "Sistemato", "Riparato"))))));
        scalpel.setPickupable(true);
        scalpel.setUsable(true);
        cell.getObjects().add(hacksaw);

        TokenObject substances = new TokenObject(SUBSTANCES, new HashSet<>(Arrays.asList("Sostanze", "Ingredienti",
                "Acido", "Oggetti")),
                "Sul tavolo puoi vedere alcuni strumenti di lavoro e alcune sostanze come: Cloruro " +
                        "di sodio, acido solforico e altre sostanze di cui non riesco nemmeno a leggere il nome!");
        substances.setPickupable(true);
        substances.setUsable(true);
        substances.setMixable(true);
        infirmary.getObjects().add(substances);

        //TODO da mettere nell'inventario nella prima fase del gioco
        TokenObject medicine = new TokenObject(MEDICINE, new HashSet<>(Arrays.asList("Farmaco", "Medicina",
                "Compresse", "Sciroppo")), "E' un medicinale per alleviare i dolori.");
        medicine.setGive(true);

        TokenObject sink = new TokenObject(SINK, new HashSet<>(Arrays.asList("Lavandino", "Lavello", "Lavabo")),
                "E' un piccolo lavandino fissato al muro con delle viti arruginite... Ha un aspetto " +
                        "malandato!");
        sink.setUsable(true);
        cell.getObjects().add(sink);

        TokenObject sinkBrother = new TokenObject(SINKBROTHER,
                new HashSet<>(Arrays.asList("Lavandino", "Lavello", "Lavabo")),
                "E' un piccolo lavandino posto nella stanza di tuo fratello");
        sink.setUsable(true);
        brotherCell.getObjects().add(sinkBrother);

        TokenObject bed = new TokenObject(BED, new HashSet<>(Arrays.asList("Letto", "Lettino", "Brandina",
                "Lettuccio")),
                "E' un letto in legno molto vecchio e sembra anche molto scomodo!");
        cell.getObjects().add(bed);

        TokenObject bedBrother = new TokenObject(BEDBROTHER, new HashSet<>(Arrays.asList("Letto", "Lettino", "Brandina",
                "Lettuccio")),
                "E' un letto in legno molto vecchio e sembra anche molto scomodo!");
        brotherCell.getObjects().add(bedBrother);

        TokenObject table = new TokenObject(TABLE, new HashSet<>(Arrays.asList("Tavolo", "Tavolino", "Scrivania")),
                "E' un semplice tavolo in legno, molto piccolo e molto sporco!");
        cell.getObjects().add(table);

        TokenObject windowCell = new TokenObject(WINDOWCELL, new HashSet<>(Arrays.asList("Finestra", "Finestrella")),
                "E' una piccola finestra dalla quale puoi osservare il cortile della prigione! " +
                        "Bel panorama!!!");
        cell.getObjects().add(windowCell);

        TokenObject water = new TokenObject(WATER, new HashSet<>(Arrays.asList("Water", "Cesso", "Gabinetto",
                "Tazza", "Wc")),
                "Un water qualunque, senza nessun particolare, non penso sia bello osservarlo");
        cell.getObjects().add(water);
        brotherCell.getObjects().add(water);

        TokenObject railing = new TokenObject(RAILING, new HashSet<>(Arrays.asList("Ringhiera", "Scorrimano")),
                "Sei troppo giovane per camminare appoggiato ad una ringhiera e troppo giovane " +
                        "per buttarti, ti prego non farlo!!!");
        corridor.getObjects().add(railing);

        TokenObject door = new TokenObject(DOORGARDEN, new HashSet<>(Arrays.asList("Porta", "Portone", "Ingresso")),
                "E' una grande porta che separa giardino e atrio. E' sempre aperta e non puoi chiuderla!");
        garden.getObjects().add(door);
        lobby.getObjects().add(door);

        TokenObject basketObject = new TokenObject(BASKETOBJECT, new HashSet<>(Arrays.asList("Canestro", "Basket")),
                "Sono due canestri, ottimi per giocare a basket e perdere tempo!!!");
        basket.getObjects().add(basketObject);

        TokenObject blackboard = new TokenObject(BLACKBOARD, new HashSet<>(Arrays.asList("Lavagna", "Lavagnetta")),
                "Vedi scritto tante ricette tra cui quella per creare l’acido cloridico! ");
        infirmary.getObjects().add(blackboard);

        TokenObject windowsInfirmary = new TokenObject(WINDOWSINFIRMARY,
                new HashSet<>(Arrays.asList("Finestra", "Finestrella")),
                "La finestra è sbarrata non sembra possibile aprirla! Puoi notare un lungo cavo che porta" +
                        " fino al muro della prigione!");
        infirmary.getObjects().add(windowsInfirmary);

        TokenObject tableInfirmary = new TokenObject(TABLEINFIRMARY, new HashSet<>(Arrays.asList("Tavolo", "Tavolino")),
                "Noti vari oggetti, alcuni non sai nemmeno a cosa possano servire, in particolare in un" +
                        " cassetto ci sono una decina di bisturi. Non penso che qualcuno se ne accorga se ne prendi " +
                        "uno solo!");
        infirmary.getObjects().add(tableInfirmary);

        TokenObject picture = new TokenObject(PICTURE, new HashSet<>(Arrays.asList("Quadro", "Tramp", "Dipinto",
                "Ritratto", "Foto", "Fotografia")),
                " Il presidente ha un sorriso smagliante e uno sguardo felice, perché proprio quel quadro " +
                        "li?");
        picture.setPushable(true);
        infirmary.getObjects().add(picture);

        TokenObject doorInfirmary = new TokenObject(DOORINFIRMARY, new HashSet<>(Arrays.asList("Porta",
                "Soglia", "Passaggio", "Portone")),
                " la porta è chiusa, su un foglietto puoi leggere che potrai uscire solo quando" +
                        " l’infermiere verrà a dirtelo. Mi dispiace devi attendere, puoi continuare a controllare " +
                        "la stanza. ");
        infirmary.getObjects().add(doorInfirmary);

        TokenObjectContainer wardrobe = new TokenObjectContainer(WARDROBE, new HashSet<>(Arrays.asList("Armadio",
                "Guardaroba", "Armadietto")),
                "E' un semplice armadio in legno molto antico pieno di camici per infermieri, " +
                        "non noti nulla di particolare.");
        wardrobe.setOpenable(true);
        wardrobe.setOpen(false);
        infirmary.getObjects().add(wardrobe);

        TokenObject gown = new TokenObject(GOWN, new HashSet<>(Arrays.asList("Camici", "Camice", "Vestito",
                "Vestiti")),
                "Combinazione non esiste uno della tua misura, che peccato!!! È inutile prenderne un altro");
        wardrobe.add(gown);

        TokenObject newAirDuct = new TokenObject(NEWAIRDUCTINFIRMARY, new HashSet<>(Arrays.asList("Condotto",
                "Passaggio")), "Non sei un campione di arrampicata o salto in alto, " +
                "perché perdere tempo qui!");
        newAirDuct.setPickupable(false);
        infirmary.getObjects().add(newAirDuct);

        TokenObject grate = new TokenObject(GRATE, new HashSet<>(Arrays.asList("Grata", "Grate")),
                "Puoi notare solo il piano superiore, ma non puoi fare nient' altro");
        passageSouth.getObjects().add(grate);

        TokenObject generatorObject = new TokenObject(GENERATOROBJ, new HashSet<>(Arrays.asList("Generatore",
                "Alimentatore", "Alternatore")),
                "C'è un enorme pulsante rosso con una scritta che vieta di premerlo!!!");
        generatorObject.setUsable(true);
        generator.getObjects().add(generatorObject);

        TokenObject buttonGenerator = new TokenObject(BUTTONGENERATOR, new HashSet<>(Arrays.asList("Pulsante", "Bottone",
                "Interruttore")),
                "C'è un enorme pulsante rosso con una scritta che vieta di premerlo!!!");
        buttonGenerator.setPushable(true);
        generator.getObjects().add(buttonGenerator);

        TokenObject gratePassage = new TokenObject(GRATEPASSAGE, new HashSet<>(Arrays.asList("Grata", "Inferriata")),
                "La cella è controllata da un poliziotto e poi non mi sembra il caso di intrufolarsi" +
                        " in una cella di un detenuto. Rischieresti di mandare a rotoli il piano!!!");
        airDuctEast.getObjects().add(gratePassage);

        TokenObject drug = new TokenObject(DRUG, new HashSet<>(Arrays.asList("Droga", "Stupefacenti")),
                "Meglio continuare il piano di fuga da lucidi e fortunatamente non hai soldi con te per" +
                        " acquistarla! \nTi ricordo che il tuo piano è fuggire di prigione e non rimanerci qualche " +
                        "anno di più!");
        //TODO ASSEGNARE DROGA A GENNY

        TokenObject videogame = new TokenObject(VIDEOGAME, new HashSet<>(Arrays.asList("VideoGame", "Gioco",
                "Videogioco")),
                "Sarebbe molto bello se solo avessi 8 anni! Quando uscirai di prigione avrai molto tempo " +
                        "per giocare anche a videogiochi migliori!");
        //TODO ASSEGNARE DROGA A GENNY

        TokenObject acid = new TokenObject(ACID, new HashSet<>(Collections.singletonList("Acido")),
                "Leggendo la ricetta alla lavagna capisci come creare l’acido, mischi le sostanze " +
                        "tutte insieme utilizzando le giuste dosi in modo da non sbagliare! Sei riuscito a" +
                        " creare l’acido!");
        acid.setPickupable(true);
        acid.setUsable(true);
        //TODO NON E' ASSEGNATO A NULLA

        TokenObject combination = new TokenObject(COMBINATION, new HashSet<>(Collections.singletonList("Combinazione")),
                "Questa e' la combinazione che ho ricavato utilizzando lo scotch sul tastierino numerico " +
                        "della stanza");
        setObjectNotAssignedRoom(combination);
        getRoom(ISOLATION).setObjectsUsableHere(combination);
    }

    @Override
    public void init() {
        initVerbs();
        initRooms();

        //Set starting room
        setCurrentRoom(getRooms().stream()
                .filter(room -> room.getId() == CELL)
                .findFirst()
                .orElse(null));

        //Set Inventory
        setInventory(new Inventory(5));
    }

    @Override
    public void nextMove(ParserOutput p, PrintStream out) {
        int minScore = getIncreaseScore();
        boolean move = false;
        boolean noroom = false;

        try {

            if (p.getVerb().getVerbType().equals(VerbType.NORD)) {
                if (getCurrentRoom().getNorth() != null && !getCurrentRoom().getNorth().isLocked()) {
                    setCurrentRoom(getCurrentRoom().getNorth());
                    move = true;
                } else {
                    noroom = true;
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.SOUTH)) {
                if (getCurrentRoom().getSouth() != null && !getCurrentRoom().getSouth().isLocked()) {
                    setCurrentRoom(getCurrentRoom().getSouth());
                    move = true;
                } else {
                    noroom = true;
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.EAST)) {
                if (getCurrentRoom().getEast() != null && !getCurrentRoom().getEast().isLocked()) {
                    setCurrentRoom(getCurrentRoom().getEast());
                    move = true;
                } else {
                    noroom = true;
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.WEST)) {
                if (getCurrentRoom().getWest() != null && !getCurrentRoom().getWest().isLocked()) {
                    setCurrentRoom(getCurrentRoom().getWest());
                    move = true;
                } else {
                    noroom = true;
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.INVENTORY)) {
                if (!getInventory().isEmpty()) {
                    out.println("Nel tuo inventario ci sono:");
                }
                for (TokenObject o : getInventory().getObjects()) {
                    out.println(o.getAlias().iterator().next() + ": " + o.getDescription());
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.LOOK_AT)) {
                if (getCurrentRoom().getLook() != null) {
                    out.println(getCurrentRoom().getLook());
                } else {
                    out.println("Non c'è niente di interessante qui.");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.PICK_UP)) {
                if (p.getObject() != null && p.getObject().isPickupable()
                        && getCurrentRoom().containsObject(p.getObject())) {
                    getInventory().add(p.getObject());
                } else if (p.getObject() == null) {
                    out.println("Cosa vorresti prendere di preciso?");
                } else if (!p.getObject().isPickupable()) {
                    out.println("Non e' certo un oggetto che si può prendere imbecille!");
                } else if (!getCurrentRoom().containsObject(p.getObject())) {
                    out.println("Questo oggetto lo vedi solo nei tuoi sogni!");
                } else if (getInventory().contains(p.getObject())) {
                    out.println("Guarda bene nella tua borsa, cretino!");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.REMOVE)) {
                if (p.getObject() != null && getInventory().contains(p.getObject())) {
                    getCurrentRoom().getObjects().add(p.getObject());
                    getInventory().remove(p.getObject());
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.USE)) {
                if (p.getObject() != null && p.getObject().isUsable() && getCurrentRoom().isObjectUsableHere(p.getObject())
                        && (getInventory().contains(p.getObject()) || getCurrentRoom().containsObject(p.getObject()))) {
                    if (p.getObject().getId() == SCREW) {
                        getObject(SINK).setPushable(true);
                    } else if (p.getObject().getId() == SCOTCH) {
                        getInventory().remove(p.getObject());
                        getInventory().add(getObject(COMBINATION));
                    } else if (p.getObject().getId() == TOOLS) {
                        getObject(HACKSAW).setUsable(true);
                    } else if (p.getObject().getId() == COMBINATION) {
                        getInventory().remove(p.getObject());
                        getRoom(ISOLATION).setLocked(false);
                    }
                } else {
                    if(p.getObject() == null) {
                        out.println("Sei sicuro di non voler usare niente?");
                    } else if(!p.getObject().isUsable()) {
                        out.println("Mi dispiace ma questo oggetto non si può utilizzare");
                    } else if(!getCurrentRoom().isObjectUsableHere(p.getObject())) {
                        out.println("C’è tempo e luogo per ogni cosa, ma non ora.");
                    } else if(!getInventory().contains(p.getObject()) && !getCurrentRoom().containsObject(p.getObject())) {
                        out.println("Io non vedo nessun oggetto di questo tipo qui!");
                    }
                }

                //FIXME Risolvere problema oggetto contenitore
            } else if (p.getVerb().getVerbType().equals(VerbType.OPEN)) {
                if (p.getObject() != null
                        && p.getObject().isOpenable()
                        && !p.getObject().isOpen()
                        && getCurrentRoom().containsObject(p.getObject())) {
                    if (!(p.getObject() instanceof TokenObjectContainer)) {
                        out.println("Hai aperto " + p.getObject().getAlias().iterator().next() + "!");
                        p.getObject().setOpen(true);
                    } else {
                        out.println("Hai aperto " + p.getObject().getAlias().iterator().next() + "!");
                        out.println("Contiene: ");
                        for (TokenObject object : ((TokenObjectContainer) p.getObject()).getList()) {
                            out.println(object.getAlias().iterator().next() + ": " + object.getDescription());
                        }
                    }
                } else if (p.getObject() == null) {
                    out.println("Cosa vorresti aprire di preciso?");
                } else if (!p.getObject().isOpenable()) {
                    out.println("Sei serio? Vorresti veramente aprirlo?!");
                    out.println("Sei fuori di testa!");
                } else if (p.getObject().isOpen()) {
                    out.println("E' gia' aperto testa di merda!");
                } else if (!getCurrentRoom().containsObject(p.getObject())) {
                    out.println("Questo oggetto lo vedi solo nei tuoi sogni!");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.CLOSE)) {

            }

            // while (getScore() < minScore) {
            //TODO PRENDERE IL BISTURI PER AUMENTARE IL PUNTEGGIO

            /* //TODO per passare allo step successivo eseguire queste istruzioni
            increaseScore();
            minScore += getIncreaseScore();
             */
            //}

            //while (getScore() < getIncreaseScore() * 2) {
            //TODO PRENDERE LA VITE
            //}

            //while (getScore() < getIncreaseScore() * 3) {

            //}
            if (noroom) {
                out.println("Da quella parte non si può andare c'è un muro! Non hai ancora acquisito i poteri per oltrepassare i muri...");
            } else if (move) {
                out.println(getCurrentRoom().getName());
                out.println("================================================");
                out.println(getCurrentRoom().getDescription());
            }
        } catch (InventoryEmptyException e) {
            out.println("L'inventario è vuoto!");
        } catch (InventoryFullException e) {
            out.println("Non puoi mettere più elementi nel tuo inventario!");
            out.println("!!!!Non hai mica la borsa di Mary Poppins!!!!!");
        } catch (ObjectNotFoundException e) {
            out.println("Non hai questo oggetto nell'inventario");
        } catch (Exception e) {
            //FIXME
            out.println(e.getMessage());
        }
    }
}

