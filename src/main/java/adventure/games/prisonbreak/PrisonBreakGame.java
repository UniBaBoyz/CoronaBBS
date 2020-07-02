package adventure.games.prisonbreak;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import adventure.GameDescription;
import adventure.exceptions.inventoryException.InventoryEmptyException;
import adventure.exceptions.inventoryException.InventoryFullException;
import adventure.exceptions.inventoryException.ObjectNotFoundInInventoryException;
import adventure.exceptions.objectsException.ObjectNotFoundInRoomException;
import adventure.exceptions.objectsException.ObjectsAmbiguityException;
import adventure.parser.ParserOutput;
import adventure.parser.Token;
import adventure.type.Inventory;
import adventure.type.Room;
import adventure.type.TokenAdjective;
import adventure.type.TokenObject;
import adventure.type.TokenObjectContainer;
import adventure.type.TokenVerb;
import adventure.type.VerbType;

import static adventure.games.prisonbreak.ObjectType.*;
import static adventure.games.prisonbreak.RoomType.AIR_DUCT;
import static adventure.games.prisonbreak.RoomType.AIR_DUCT_EAST;
import static adventure.games.prisonbreak.RoomType.AIR_DUCT_NORTH;
import static adventure.games.prisonbreak.RoomType.AIR_DUCT_WEST;
import static adventure.games.prisonbreak.RoomType.BASKET_CAMP;
import static adventure.games.prisonbreak.RoomType.BENCH;
import static adventure.games.prisonbreak.RoomType.BRAWL;
import static adventure.games.prisonbreak.RoomType.BROTHER_CELL;
import static adventure.games.prisonbreak.RoomType.CANTEEN;
import static adventure.games.prisonbreak.RoomType.CELL;
import static adventure.games.prisonbreak.RoomType.CORRIDOR;
import static adventure.games.prisonbreak.RoomType.DOOR_ISOLATION;
import static adventure.games.prisonbreak.RoomType.ENDGAME;
import static adventure.games.prisonbreak.RoomType.END_LOBBY;
import static adventure.games.prisonbreak.RoomType.FRONTBENCH;
import static adventure.games.prisonbreak.RoomType.GARDEN;
import static adventure.games.prisonbreak.RoomType.GENERATOR;
import static adventure.games.prisonbreak.RoomType.GYM;
import static adventure.games.prisonbreak.RoomType.INFIRMARY;
import static adventure.games.prisonbreak.RoomType.ISOLATION;
import static adventure.games.prisonbreak.RoomType.ISOLATION_CORRIDOR_EAST;
import static adventure.games.prisonbreak.RoomType.ISOLATION_CORRIDOR_EAST_EAST;
import static adventure.games.prisonbreak.RoomType.ISOLATION_CORRIDOR_EAST_EAST_EAST;
import static adventure.games.prisonbreak.RoomType.ISOLATION_CORRIDOR_NORTH;
import static adventure.games.prisonbreak.RoomType.ISOLATION_CORRIDOR_NORTH_NORTH;
import static adventure.games.prisonbreak.RoomType.ISOLATION_CORRIDOR_NORTH_NORTH_NORTH;
import static adventure.games.prisonbreak.RoomType.ISOLATION_CORRIDOR_SOUTH;
import static adventure.games.prisonbreak.RoomType.ISOLATION_CORRIDOR_SOUTH_SOUTH;
import static adventure.games.prisonbreak.RoomType.ISOLATION_CORRIDOR_SOUTH_SOUTH_SOUTH;
import static adventure.games.prisonbreak.RoomType.LADDERS;
import static adventure.games.prisonbreak.RoomType.LOBBY;
import static adventure.games.prisonbreak.RoomType.LOBBY_SOUTH;
import static adventure.games.prisonbreak.RoomType.ON_LADDER;
import static adventure.games.prisonbreak.RoomType.OTHER_CELL;
import static adventure.games.prisonbreak.RoomType.OUT_ISOLATION;
import static adventure.games.prisonbreak.RoomType.PASSAGE_NORTH;
import static adventure.games.prisonbreak.RoomType.PASSAGE_SOUTH;
import static adventure.games.prisonbreak.RoomType.SECRET_PASSAGE;
import static adventure.games.prisonbreak.RoomType.WALL;
import static adventure.games.prisonbreak.RoomType.WINDOW_INFIRMARY;


/**
 * @author Corona-Extra
 */
public class PrisonBreakGame extends GameDescription {

    private boolean bed = false;

    public PrisonBreakGame() {
        initVerbs();
        initRooms();

        //Set starting room
        setCurrentRoom(getRoom(GENERATOR));

        //Set Inventory
        setInventory(new Inventory(5));
    }

    public boolean isBed() {
        return bed;
    }

    public void setBed(boolean bed) {
        this.bed = bed;
    }

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
        push.setAlias(new HashSet<>(Arrays.asList("Premi", "Spingi", "Attiva", "Schiaccia", "Pigia", "Togli", "Sposta")));
        getTokenVerbs().add(push);

        TokenVerb talk = new TokenVerb(VerbType.TALK_TO);
        talk.setAlias(new HashSet<>(Arrays.asList("Parla", "Chiacchiera", "Comunica", "Dialoga", "Conversa",
                "Affronta", "Ascolta", "Chiedi", "Grida", "Urla", "Mormora", "Sussurra", "Bisbiglia", "Conferisci")));
        getTokenVerbs().add(talk);

        TokenVerb eat = new TokenVerb(VerbType.EAT);
        eat.setAlias(new HashSet<>(Arrays.asList("Mangia", "Pranza", "Cena", "Divora")));
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
        climb.setAlias(new HashSet<>(Arrays.asList("Arrampicati", "Sali", "Su")));
        getTokenVerbs().add(climb);

        TokenVerb getOff = new TokenVerb(VerbType.GET_OFF);
        getOff.setAlias(new HashSet<>(Arrays.asList("Scendi", "Scivola", "Giu", "Giù")));
        getTokenVerbs().add(getOff);

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
        give.setAlias(new HashSet<>(Arrays.asList("Dai", "Cedi", "Dona", "Regala", "Poni", "Concedi", "Porgi",
                "Consegna", "Offri")));
        getTokenVerbs().add(give);

        TokenVerb make = new TokenVerb(VerbType.MAKE);
        make.setAlias(new HashSet<>(Arrays.asList("Fai", "Crea", "Prepara", "Inventa", "Mischia", "Mescola", "Produci",
                "Realizza", "Genera", "Componi", "Origina")));
        getTokenVerbs().add(make);

        TokenVerb remove = new TokenVerb(VerbType.REMOVE);
        remove.setAlias(new HashSet<>(Arrays.asList("Rimuovi", "Leva", "Elimina", "Lascia")));
        getTokenVerbs().add(remove);

        TokenVerb destroy = new TokenVerb(VerbType.DESTROY);
        destroy.setAlias(new HashSet<>(Arrays.asList("Spezza", "Distruggi", "Fracassa", "Sgretola", "Rompi",
                "Frantuma", "Corrodi")));
    }

    private void initRooms() {
        Room cell = new Room(CELL, "Cella 17", "Ti trovi nella tua cella 17, al momento sei da solo" +
                " visto che sei l’ultimo arrivato.");
        cell.setLook("La cella e' poco accogliente... l’unica via di uscita si trova a est, al momento aperta visto " +
                "che e' l’ora d’aria e tutti i detenuti devono raggiungere il giardino!");

        Room corridor = new Room(CORRIDOR, "Corridoio", "Ti trovi nel corridoio del carcere che si" +
                " estende da sud verso nord.");
        corridor.setLook("Si sentono tanti rumori e urla dei detenuti, a ovest la porta della tua cella aperta e" +
                " altre celle in cui non e' possibile entrare poiche' sono chiuse.");

        Room ladders = new Room(LADDERS, "Scalinata", "Ti trovi presso una scalinata, l’unica cosa" +
                " che puoi fare e' andare al piano di sotto oppure andare verso sud percorrendo il corridoio.");
        ladders.setLook("Puoi vedere i detenuti che si dirigono verso il giardino.");

        Room lobby = new Room(LOBBY, "Atrio", "Ti trovi in un grosso atrio di ingresso dove puoi " +
                "intravedere il giardino.");
        lobby.setLook("Il luogo e' affollato di guardie che controllano la situazione. Puoi salire tramite la scalinata" +
                " al piano superiore e a ovest le celle degl’altri detenuti. L’atrio si estende ancora " +
                "verso sud.");

        Room lobbySouth = new Room(LOBBY_SOUTH, "Atrio", "Ti trovi a sud del grosso atrio di ingresso." +
                " Puoi notare che l'atrio prosegue sia a nord che a sud!");
        lobbySouth.setLook("Puoi notare a ovest le celle chiuse dei detenuti e a est la palestra.");

        Room lobbyEnd = new Room(END_LOBBY, "Atrio", "Ti trovi alla fine del grosso atrio, a sud vedi " +
                " tante guardie, sembra una zona particolarmente protetta!!! Noti a est la sala per la mensa e a " +
                " ovest le celle chiuse dei detenuti!");
        lobbyEnd.setLook("Non c'è nulla di particolare! Puoi ritornare indietro verso nord!");

        Room garden = new Room(GARDEN, "Giardino", "Sei in un ampio giardino verde illuminato " +
                "dal sole, dietro di te il grosso atrio.");
        garden.setLook("Guardando a nord  puoi notare un grosso campo da basket, avanti a te a est un grosso muro " +
                "che separa il giardino dall’esterno con due enormi torri sulle quali ci sono le guardie come vedetta," +
                " a sud invece tre grosse panchine dove puoi sederti e rilassarti.");

        Room basket = new Room(BASKET_CAMP, "Campo da basket", "Ti trovi nel campo di basket" +
                " momentaneamente vuoto.");
        basket.setLook("Sembra un po' trascurato...");

        Room wall = new Room(WALL, "Muro prigione", "Avvicinandoti alle mura le guardie ti danno " +
                "l’ordine di indietreggiare: rappresenti un potenziale pericolo. Non penso sia un’idea geniale " +
                "fuggire da qui, la zona e' troppo controllata.");
        wall.setLook("Non c'e' nulla di particolare tranne che un grosso muro in mattoni!");

        Room bench = new Room(BENCH, "Panchine", "Tutte le panchine sono occupati da un gruppo " +
                "di detenuti che ti guardano con aria sospetta.");
        bench.setLook("Non noti nulla di particolare in loro e nelle panchine, tranne in una dove a terra puoi " +
                "notare un oggetto di metallo simile ad una vite.");

        Room infirmary = new Room(INFIRMARY, "Infermeria", "E' una classica infermeria e ti trovi" +
                " sdraiato sul tuo letto. Decidi di alzarti senza far rumore!");
        infirmary.setLook("Sembra non esserci nessuno oltre a te nella stanza, riesci solo ad udire delle " +
                "voci nel corridoio.");
        infirmary.setLocked(true);

        Room passage = new Room(SECRET_PASSAGE, "Passaggio segreto",
                "Sei nel passaggio segreto.");
        passage.setLook("Noti delle pareti in roccia un po’ malandate, un’ enorme parete blocca la strada" +
                " a nord, puoi solo andare ad sud o a nord o ritornare indietro nella tua cella prima che qualcuno" +
                " ti scopra!!!");
        passage.setLocked(true);

        Room passageSouth = new Room(PASSAGE_SOUTH, "Passaggio segreto Sud",
                "Prosegui nel passaggio segreto andando verso Sud");
        passageSouth.setLook("Il passaggio continua ancora a sud oppure puoi sempre ritornare indietro prima " +
                "che sia troppo tardi!");

        Room passageNorth = new Room(PASSAGE_NORTH, "Passaggio segreto Nord",
                "Sembra che sei arrivato gia' in un vicolo cieco, vedi solo un enorme soffitto " +
                        "e una piccola grata posta in alto!");
        passageNorth.setLook("Non c'e' nient'altro di particolare. Vai a sud per poter tornare indietro!");

        Room generator = new Room(GENERATOR, "Stanza con generatore", "Sembra che il passaggio" +
                " finisca qui, sei in una piccola stanza tutta buia.");
        generator.setLook("Non riesci a vedere nulla tranne che qualche piccola luce lampeggiante di fronte a te!");

        Room onLadder = new Room(ON_LADDER, "Sulla scala",
                "Sei salito sulla scala e noti un condotto d’aria un po’ vecchiotto.");
        onLadder.setLook("Sembra di aver già visto questo tipo di condotto da un’altra parte!");
        onLadder.setLocked(true);

        Room airDuct = new Room(AIR_DUCT, "Condotto d'aria", "Sei riuscito ad entrare nel condotto," +
                " strisci piano cercando di fare meno rumore possibile.");
        airDuct.setLook("Ci sono molte ragnatele e il condotto sembra non utilizzato, cerca di fare veloce in modo" +
                " da non risultare assente all’appello! Il condotto si divide in tre strade diverse, una a nord," +
                " una ad est e l’altra a ovest.");

        Room airDuctEast = new Room(AIR_DUCT_EAST, "Condotto d'aria", "Ti ritrovi in un vicolo cieco");
        airDuctEast.setLook("Puoi osservare un’altra cella di un detenuto.");

        Room airDuctNorth = new Room(AIR_DUCT_NORTH, "Condotto d'aria", "Vedi una grossa grata e " +
                "senti delle voci simili a quelle che sentivi quando eri in infermeria.");
        airDuctNorth.setLook("Sembra la strada giusta!");

        Room airDuctWest = new Room(AIR_DUCT_WEST, "Condotto d'aria",
                " Il condotto si fa sempre piu' stretto e non riesci piu' a passare! " +
                        "Non sembra sia la strada giusta!");
        airDuctWest.setLook("Scorgi pero' in lontananza un piccolo oggetto!");

        Room otherCell = new Room(OTHER_CELL, "Cella detenuto", "La cella e' controllata da un " +
                "poliziotto e poi non mi sembra il caso di intrufolarsi in una cella di un detenuto. Rischieresti " +
                "di mandare a rotoli il piano!!!");
        otherCell.setLook("Meglio non perdere tempo qui!");
        otherCell.setLocked(true);

        Room canteen = new Room(CANTEEN, "Mensa", "Ti trovi in una grossa mensa e puoi vedere " +
                "i detenuti che stanno mangiando.");
        canteen.setLook("Uno di loro si avvicina a te e sussurrando ti chiede se sei interessato a qualche oggetto" +
                " che lui possiede. Ovviamente ogni oggetto ha un costo ma tu non possiedi alcun soldo, " +
                "per averne uno quindi sarei costretto a trattare.");

        Room gym = new Room(GYM, "Palestra", "Ti trovi nella palestra del carcere, " +
                "e' molto grande e non e' tenuta in ottimo stato, alcuni detenuti si stanno allenando.");
        gym.setLook("Intorno a te vedi tanti attrezzi per poterti allenare e aumentare la tua forza.");

        Room outIsolation = new Room(OUT_ISOLATION, "Fuori cella isolamento",
                "Sei di fronte la cella di isolamento dove e' collocato tuo fratello. " +
                        "Essendo in un carcere di massima sicurezza, la porta e' controllata da un paio di guardie.");
        outIsolation.setLook("Puoi notare da lontano che non si tratta di una semplice porta ma di una porta che " +
                "puo' essere aperta solo tramite un PIN segreto.");

        Room doorIsolation = new Room(DOOR_ISOLATION, "Porta isolamento",
                "Sei di fronte ad una porta blindata che come gia' ti sembrava e' possibile " +
                        "aprire tramite un PIN segreto.");
        doorIsolation.setLook("Il pin e' conosciuto solo dalle guardie e quindi ti e' impossibile reperirlo!" +
                " A meno che non vuoi iniziare a sparare numeri a caso devi trovare assolutamente un’altra soluzione" +
                " prima che le luci si accendano e le guardie tornino!");
        //doorIsolation.setLocked(true);

        Room isolation = new Room(ISOLATION, "Dentro isolamento",
                "La porta si apre e ti trovi dentro il luogo dove si trovano le celle isolamento. " +
                        "Ci sono tre lunghi corridoi, uno a est, uno a sud e l’altro a nord!");
        isolation.setLook("Non noti nient’altro di particolare!");
        //isolation.setLocked(true);

        Room isolationCorridorNorth = new Room(ISOLATION_CORRIDOR_NORTH, "Corridoio nord isolamento",
                "Prosegui nel corridoio a nord, ci sono tante celle chiuse di prigionieri in isolamento." +
                        " Provi a gridare (ma non troppo), il nome di tuo fratello ma non risponde nessuno!");
        isolationCorridorNorth.setLook("Non noti nient’altro di particolare!");

        Room isolationCorridorNorthNorth = new Room(ISOLATION_CORRIDOR_NORTH_NORTH, "Corridoio nord isolamento",
                "La speranza e' l’ultima a morire ma penso proprio che tuo fratello non si trovi in questo" +
                        " corridoio! ");
        isolationCorridorNorthNorth
                .setLook("Puoi solo osservare altre celle di detenuti in cui non e' possibile entrare.");

        Room isolationCorridorNorthNorthNorth = new Room(ISOLATION_CORRIDOR_NORTH_NORTH_NORTH,
                "Corridoio nord isolamento", "Il corridoio termina" +
                " con una grossa parete di fronte e te, hai visto tutte le celle ma di tuo fratello nemmeno l’ombra, " +
                "te l’avevo detto io!!!");
        isolationCorridorNorthNorthNorth.setLook("Non c'e' nulla, puoi solo ritornare indietro!!!");

        Room isolationCorridorEast = new Room(ISOLATION_CORRIDOR_EAST, "Corridoio est isolamento",
                "Prosegui nel corridoio a est, ci sono tante celle chiuse di prigionieri in isolamento. " +
                        "Provi a gridare (ma non troppo), il nome di tuo fratello ma non risponde nessuno!");
        isolationCorridorEast.setLook("Non noti nient’altro di particolare!");

        Room isolationCorridorEastEast = new Room(ISOLATION_CORRIDOR_EAST_EAST, "Corridoio est isolamento",
                "La speranza e' l’ultima a morire ma penso proprio che tuo fratello non si trovi " +
                        "in questo corridoio! ");
        isolationCorridorEastEast
                .setLook("Puoi solo osservare altre celle di detenuti in cui non e' possibile entrare.");

        Room isolationCorridorEastEastEast = new Room(ISOLATION_CORRIDOR_EAST_EAST_EAST, "Corridoio est isolamento",
                "Il corridoio termina con una grossa parete di fronte e te, hai visto tutte le celle, " +
                        "ma di tuo fratello nemmeno l’ombra, te l’avevo detto io!!!");
        isolationCorridorEastEastEast.setLook("Non c'e' nulla, puoi solo ritornare indietro!!!");

        Room isolationCorridorSouth = new Room(ISOLATION_CORRIDOR_SOUTH, "Corridoio sud isolamento",
                "Prosegui nel corridoio a sud, ci sono tante celle chiuse di prigionieri in isolamento." +
                        " Provi a gridare (ma non troppo)!");
        isolationCorridorSouth.setLook("Senti un mormorio in lontananza!");

        Room isolationCorridorSouthSouth = new Room(ISOLATION_CORRIDOR_SOUTH_SOUTH, "Corridoio sud isolamento",
                "Il mormorio si fa sempre piu' forte ma non hai ancora trovato la cella di tuo fratello.");
        isolationCorridorSouthSouth
                .setLook("Puoi osservare le celle degli altri prigionieri in cui non e' possibile entrare!");

        Room isolationCorridorSouthSouthSouth = new Room(ISOLATION_CORRIDOR_SOUTH_SOUTH_SOUTH,
                "Corridoio sud isolamento", "Il corridoio termina con " +
                "una grossa parete di fronte a te, hai visto tutte le celle tranne l’ultima!");
        isolationCorridorSouthSouthSouth
                .setLook("Avvicinandoti a questa senti la voce di tuo fratello, hai trovato la sua cella!!!");

        Room brotherCell = new Room(BROTHER_CELL, "Cella fratello",
                "Sei nella cella di tuo fratello, l’aspetto della cella e' ripugnante.");
        brotherCell.setLook("Di fronte a te, tuo fratello con un’aria contenta di vederti ma allo stesso tempo " +
                "sorpresa!");

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

        Room windowInfirmary = new Room(WINDOW_INFIRMARY, "Finestra infermeria",
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
        airDuctNorth.setNorth(infirmary);
        infirmary.setSouth(airDuctNorth);
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

        TokenObject screw = new TokenObject(SCREW, "Vite", new HashSet<>(Arrays.asList("Vite", "Chiodo")),
                "E' una semplice vite con inciso il numero di serie: 11121147.");
        screw.setUsable(true);
        bench.setObject(screw);
        cell.setObjectsUsableHere(screw);

        TokenObject scotch = new TokenObject(SCOTCH, "Scotch", new HashSet<>(Arrays.asList("Scotch", "Nastro")),
                "E' un semplice scotch, dimenticato li forse da qualche operaio!");
        airDuctWest.setObject(scotch);
        scotch.setUsable(true);
        scotch.setPickupable(true);
        isolation.setObjectsUsableHere(scotch);

        TokenObject tools = new TokenObject(TOOLS, "Attrezzi",
                new HashSet<>(Arrays.asList("Attrezzi", "Manubri", "Pesi")),
                "Sono degli attrezzi da palestra, ottimi per allenarsi e aumentare la forza!");
        tools.setUsable(true);
        gym.setObject(tools);
        gym.setObjectsUsableHere(tools);

        TokenObject food = new TokenObject(FOOD, "Cibo",
                new HashSet<>(Arrays.asList("Cibo", "Pranzo", "Cena", "Piatto", "Tavolo")),
                "C'è solo il tuo pranzo che emana un odore non buonissimo, puoi mangiarlo anche se non" +
                        " servirà a nulla.");
        food.setEatable(true);
        food.setPickupable(true);
        cell.setObject(food);

        TokenObject ball = new TokenObject(BALL, "Pallone",
                new HashSet<>(Arrays.asList("Palla", "Pallone", "Basketball")),
                "Un semplice pallone da basket.");
        ball.setPickupable(true);
        ball.setUsable(true);
        basket.setObject(ball);
        getRooms().forEach(room -> room.setObjectsUsableHere(ball));

        TokenObject ladder = new TokenObject(LADDER, "Scala", new HashSet<>(Arrays.asList("Scala", "Scaletta")),
                "E' solo una scala in legno, sembra molto leggera e facile da spostare. " +
                        "La scala sembra non portare da nessuna parte!");
        ladder.setPushable(true);
        passageSouth.setObject(ladder);

        TokenObject scalpel = new TokenObject(SCALPEL, "Bisturi", new HashSet<>(Arrays.asList("Bisturi", "Lama")),
                "Un semplice bisturi per effettuare operazioni mediche!");
        scalpel.setPickupable(true);
        scalpel.setUsable(true);
        infirmary.setObject(scalpel);
        brawl.setObjectsUsableHere(scalpel);

        //TODO assegnare oggetto a Jonny Bello
        TokenObject hacksaw = new TokenObject(HACKSAW, "Seghetto",
                new HashSet<>(Arrays.asList("Seghetto", "Sega", "Taglierino")),
                "E’ un seghetto molto affilato, potresti riuscire a rompere qualcosa.",
                new HashSet<>(Arrays.asList(new TokenAdjective(new HashSet<>(Arrays.asList("Rotto", "Distrutto",
                        "Devastato", "Spaccato"))), new TokenAdjective(new HashSet<>(Arrays.asList("Aggiustato",
                        "Sistemato", "Riparato"))))));
        hacksaw.setPickupable(true);
        hacksaw.setUsable(true);
        airDuctNorth.setObjectsUsableHere(hacksaw);

        TokenObject substances = new TokenObject(SUBSTANCES, "Sostanze chimiche",
                new HashSet<>(Arrays.asList("Sostanze", "Ingredienti", "Oggetti")),
                "Sul tavolo puoi vedere alcuni strumenti di lavoro e alcune sostanze come: Cloruro " +
                        "di sodio, acido solforico e altre sostanze di cui non riesco nemmeno a leggere il nome!");
        substances.setPickupable(true);
        substances.setMixable(true);
        infirmary.setObject(substances);

        //TODO da mettere nell'inventario nella prima fase del gioco
        TokenObject medicine = new TokenObject(MEDICINE, "Farmaco",
                new HashSet<>(Arrays.asList("Farmaco", "Medicina", "Compresse", "Sciroppo")),
                "E' un medicinale per alleviare i dolori.");
        medicine.setGive(true);

        TokenObject sink = new TokenObject(SINK, "Lavandino",
                new HashSet<>(Arrays.asList("Lavandino", "Lavello", "Lavabo")),
                "E' un piccolo lavandino fissato al muro con delle viti arruginite... Ha un aspetto " +
                        "malandato!");
        sink.setUsable(true);
        cell.setObject(sink);
        cell.setObjectsUsableHere(sink);

        TokenObject sinkBrother = new TokenObject(SINK_BROTHER, "Lavandino",
                new HashSet<>(Arrays.asList("Lavandino", "Lavello", "Lavabo")),
                "E' un piccolo lavandino posto nella stanza di tuo fratello");
        sinkBrother.setUsable(true);
        brotherCell.setObject(sinkBrother);
        brotherCell.setObjectsUsableHere(sinkBrother);

        TokenObject bed = new TokenObject(BED, "Letto",
                new HashSet<>(Arrays.asList("Letto", "Lettino", "Brandina", "Lettuccio")),
                "E' presente un letto a castello molto scomodo e pieno di polvere!");
        bed.setSit(true);
        cell.setObject(bed);

        TokenObject bedBrother = new TokenObject(BED_BROTHER, "Letto",
                new HashSet<>(Arrays.asList("Letto", "Lettino", "Brandina", "Lettuccio")),
                "E' un letto in legno molto vecchio e sembra anche molto scomodo!");
        bed.setSit(true);
        brotherCell.setObject(bedBrother);

        TokenObject table = new TokenObject(TABLE, "Tavolo",
                new HashSet<>(Arrays.asList("Tavolo", "Tavolino", "Scrivania")),
                "E' un semplice tavolo in legno, molto piccolo e molto sporco!");
        cell.setObject(table);

        TokenObject windowCell = new TokenObject(WINDOW_CELL, "Finestra",
                new HashSet<>(Arrays.asList("Finestra", "Finestrella")),
                "E' una piccola finestra sbarrata dalla quale puoi osservare il cortile della prigione! " +
                        "Bel panorama!!!");
        cell.setObject(windowCell);

        TokenObject water = new TokenObject(WATER, "Water",
                new HashSet<>(Arrays.asList("Water", "Cesso", "Gabinetto", "Tazza", "Wc")),
                "Un water qualunque, senza nessun particolare, non penso sia bello osservarlo");
        water.setSit(true);
        cell.setObject(water);
        brotherCell.setObject(water);

        TokenObject railing = new TokenObject(RAILING, "Ringhiera",
                new HashSet<>(Arrays.asList("Ringhiera", "Scorrimano")),
                "Sei troppo giovane per camminare appoggiato ad una ringhiera e troppo giovane " +
                        "per buttarti, ti prego non farlo!!!");
        corridor.setObject(railing);

        TokenObject door = new TokenObject(DOOR_GARDEN, "Porta d'ingresso",
                new HashSet<>(Arrays.asList("Porta", "Portone", "Ingresso", "Soglia")),
                "E' una grande porta che separa giardino e atrio. E' sempre aperta e non puoi chiuderla!");
        garden.setObject(door);
        lobby.setObject(door);

        TokenObject basketObject = new TokenObject(BASKET_OBJECT, "Canestri", new HashSet<>(
                Arrays.asList("Canestri", "Canestro", "Basket")),
                "Ottimi per giocare a basket e perdere tempo!!!");
        basket.setObject(basketObject);

        TokenObject blackboard = new TokenObject(BLACKBOARD, "Lavagna",
                new HashSet<>(Arrays.asList("Lavagna", "Lavagnetta")),
                "Vedi scritto tante ricette tra cui quella per creare l’acido cloridico! ");
        infirmary.setObject(blackboard);

        TokenObject windowsInfirmary = new TokenObject(WINDOWS_INFIRMARY, "Finestra",
                new HashSet<>(Arrays.asList("Finestra", "Finestrella")),
                "La finestra è sbarrata non sembra possibile aprirla! Puoi notare un lungo cavo che porta" +
                        " fino al muro della prigione!");
        infirmary.setObject(windowsInfirmary);

        TokenObject tableInfirmary = new TokenObject(TABLE_INFIRMARY, "Tavolo",
                new HashSet<>(Arrays.asList("Tavolo", "Tavolino")),
                "Noti vari oggetti, alcuni non sai nemmeno a cosa possano servire, in particolare in un" +
                        " cassetto ci sono una decina di bisturi. Non penso che qualcuno se ne accorga se ne prendi " +
                        "uno solo!");
        infirmary.setObject(tableInfirmary);

        TokenObject picture = new TokenObject(PICTURE, "Quadro",
                new HashSet<>(Arrays.asList("Quadro", "Trump", "Dipinto",
                        "Ritratto", "Foto", "Fotografia")),
                " Il presidente ha un sorriso smagliante e uno sguardo felice, perché proprio quel quadro " +
                        "li?");
        picture.setPushable(true);
        infirmary.setObject(picture);

        TokenObject doorInfirmary = new TokenObject(DOOR_INFIRMARY, "Porta d'ingresso",
                new HashSet<>(Arrays.asList("Porta", "Soglia", "Ingresso", "Portone")),
                "La porta è chiusa, su un foglietto puoi leggere che potrai uscire solo quando" +
                        " l’infermiere verrà a dirtelo. Mi dispiace devi attendere, puoi continuare a controllare " +
                        "la stanza. ");
        infirmary.setObject(doorInfirmary);

        TokenObjectContainer wardrobe = new TokenObjectContainer(WARDROBE, "Armadio",
                new HashSet<>(Arrays.asList("Armadio", "Guardaroba", "Armadietto")),
                "E' un semplice armadio in legno molto antico pieno di camici per infermieri, " +
                        "non noti nulla di particolare.");
        wardrobe.setOpenable(true);
        wardrobe.setOpen(false);

        TokenObject gown = new TokenObject(GOWN, "Camici",
                new HashSet<>(Arrays.asList("Camici", "Camice", "Vestito", "Vestiti")),
                "Che combinazione non esiste uno della tua misura, che peccato!!! È inutile prenderne un altro");
        wardrobe.add(gown);
        infirmary.setObject(wardrobe);

        // TODO SCEGLIERE SE ELIMINARE IL CONDOTTO NUOVO DALL' INFERMERIA
        TokenObject newAirDuct = new TokenObject(NEW_AIR_DUCT_INFIRMARY, "Condotto d'aria nuovo",
                new HashSet<>(Arrays.asList("Condotto", "Passaggio")),
                "Non sei un campione di arrampicata o salto in alto, " +
                        "perché perdere tempo qui!");
        infirmary.setObject(newAirDuct);

        TokenObject grate = new TokenObject(GRATE, "Grata", new HashSet<>(Arrays.asList("Grata", "Grate")),
                "Puoi notare solo il piano superiore, ma non puoi fare nient' altro");
        passageSouth.setObject(grate);

        TokenObject generatorObject = new TokenObject(GENERATOR_OBJ, "Generatore",
                new HashSet<>(Arrays.asList("Generatore", "Alimentatore", "Alternatore")),
                "C'è un enorme pulsante rosso!!!");
        generatorObject.setUsable(true);
        generator.setObject(generatorObject);
        generator.setObjectsUsableHere(generatorObject);

        TokenObject buttonGenerator = new TokenObject(BUTTON_GENERATOR, "Pulsante", new HashSet<>(Arrays.asList
                ("Pulsante", "Bottone", "Interruttore")),
                "Noti una scritta che vieta di premerlo!!!");
        buttonGenerator.setPushable(true);
        generator.setObject(buttonGenerator);

        TokenObject lights = new TokenObject(LIGHTS, "Luci", new HashSet<>(Arrays.asList
                ("Luci", "Luce", "Lampada", "Lampadario")),
                "Sono semplici luci della prigione!");
        lights.setTurnOnAble(true);
        setObjectNotAssignedRoom(lights);

        TokenObject gratePassage = new TokenObject(GRATE_PASSAGE, "Grata", new HashSet<>(Arrays.asList
                ("Grata", "Inferriata")),
                "La grata è sulla cella del detenuto che è controllata da un poliziotto. Non mi sembra il" +
                        " caso di intrufolarsi rischieresti di mandare a rotoli il piano!!!");
        airDuctEast.setObject(gratePassage);

        TokenObject destroyableGrate = new TokenObject(DESTROYABLE_GRATE, "Grata", new HashSet<>(Arrays.asList(
                "Grata", "Inferriata")),
                "La grossa grata blocca il passaggio, ci sarà qualche modo per romperla???");
        airDuctNorth.setObject(destroyableGrate);

        //TODO ASSEGNARE DROGA A GENNY
        TokenObject drug = new TokenObject(DRUG, "Droga", new HashSet<>(Arrays.asList("Droga", "Stupefacenti")),
                "Meglio continuare il piano di fuga da lucidi e fortunatamente non hai soldi con te per" +
                        " acquistarla! \nTi ricordo che il tuo piano è fuggire di prigione e non rimanerci qualche " +
                        "anno di più!");

        //TODO ASSEGNARE VIDEOGAME A GENNY
        TokenObject videogame = new TokenObject(VIDEOGAME, "Videogame", new HashSet<>(Arrays.asList("Videogame",
                "Gioco", "Videogioco")),
                "Sarebbe molto bello se solo avessi 8 anni! Quando uscirai di prigione avrai molto tempo " +
                        "per giocare anche a videogiochi migliori!");

        TokenObject acid = new TokenObject(ACID, "Acido", new HashSet<>(Collections.singletonList("Acido")),
                "Leggendo la ricetta alla lavagna capisci come creare l’acido, mischi le sostanze " +
                        "tutte insieme utilizzando le giuste dosi in modo da non sbagliare! Sei riuscito a" +
                        " creare l’acido!");
        acid.setPickupable(true);
        acid.setUsable(true);
        acid.setMixable(true);
        setObjectNotAssignedRoom(acid);

        TokenObject combination = new TokenObject(COMBINATION, "Combinazione", new HashSet<>(Arrays.asList(
                "Combinazione", "Password", "Pin")),
                "Questa e' la combinazione che ho ricavato utilizzando lo scotch sul tastierino numerico " +
                        "della stanza");
        combination.setUsable(true);
        setObjectNotAssignedRoom(combination);
        isolation.setObjectsUsableHere(combination);

        TokenObject airDuctOld = new TokenObject(AIR_DUCT_OLD, "Condotto d'aria vecchio", new HashSet<>(
                Arrays.asList("Condotto d'aria vecchio", "Condotto d'aria", "Condotto", "Indotto")),
                "Dietro al quadro vedi un condotto d’aria dall’aspetto vecchiotto, " +
                        "sembra quasi che non serva più perché ne hanno costruito un altro… " +
                        "Perché nasconderlo?");
        setObjectNotAssignedRoom(airDuctOld);

        TokenObject roomObject = new TokenObject(ROOM_OBJ, "Stanza", new HashSet<>(
                Arrays.asList("Stanza", "Camera", "Ambiente", "Locale")));
        setObjectNotAssignedRoom(roomObject);
    }

    @Override
    public void nextMove(ParserOutput p, PrintStream out) {
        TokenObject object;
        boolean move = false;
        boolean noroom = false;
        boolean mixed = false;

        try {
            object = getCorrectObject(p.getObject());

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
                    out.println(o.getName() + ": " + o.getDescription());
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.LOOK_AT)) {
                if (object != null
                        && (getInventory().contains(object) || getCurrentRoom().getObjects().contains(object))) {
                    out.println(object.getDescription());
                } else if (getCurrentRoom().getLook() != null && (object == null || object.getId() == ROOM_OBJ)) {
                    out.println(getCurrentRoom().getLook());
                } else {
                    out.println("Non c'è niente di interessante qui.");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.PICK_UP)) {
                if (object != null && object.isPickupable()
                        && getCurrentRoom().containsObject(object)) {
                    getCurrentRoom().getObjects().remove(object);
                    getInventory().add(object);
                    out.println("Hai preso " + object.getName() + "!");
                } else if (object == null) {
                    out.println("Cosa vorresti prendere di preciso?");
                } else if (!object.isPickupable()) {
                    out.println("Non e' certo un oggetto che si può prendere imbecille!");
                } else if (getInventory().contains(object)) {
                    out.println("Guarda bene nella tua borsa, cretino!");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.REMOVE)) {
                if (object != null && getInventory().contains(object)) {
                    getCurrentRoom().setObject(object);
                    getInventory().remove(object);
                    out.println("Hai lasciato a terra " + object.getName() + "!");

                } else if (object == null) {
                    out.println("Cosa vorresti rimuovere dall'inventario?");
                } else {
                    out.println("L'inventario non ha questo oggetto!");
                    out.println("L'avrai sicuramente scordato da qualche parte!");
                    out.println("Che pazienzaaa!!");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.USE)) {
                if (object != null && object.isUsable() && getCurrentRoom().isObjectUsableHere(object)
                        && (getInventory().contains(object) || getCurrentRoom().containsObject(object))) {

                    object.setUsed(true);

                    if (object.getId() == SCREW) {
                        getObject(SINK).setPushable(true);
                        getInventory().remove(object);
                        out.println("Decidi di usare il cacciavite, chiunque abbia fissato quel lavandino non aveva una " +
                                "grande forza visto che le viti si svitano facilmente. Appena hai tolto l’ultima vite, " +
                                "sposti il lavandino e vedi un passaggio segreto");

                    } else if (object.getId() == SCOTCH) {
                        getInventory().remove(object);
                        getInventory().add(getObject(COMBINATION));
                        out.println("Metti lo scotch sui numeri della porta, dallo scotch noti le impronte dei ultimi " +
                                "tasti schiacciati, ora indovinare il pin segreto sembra molto più semplice!");

                    } else if (object.getId() == TOOLS) {
                        out.println("Decidi di allenarti per un bel po’ di tempo… alla fine dell’allenamento " +
                                "ti senti già più forte!");

                    } else if (object.getId() == BALL) {
                        out.println("Il tempo è denaro, non penso sia il momento adatto per mettersi a giocare.");

                    } else if (object.getId() == SCALPEL) {
                        getInventory().remove(object);
                        out.println("Riesci subito a tirare fuori il bisturi dalla tasca, il gruppetto lo vede e capito " +
                                "il pericolo decide di lasciare stare (Mettere a rischio la vita per una panchina " +
                                "sarebbe veramente stupido) e vanno via con un'aria di vendetta. Ora sei solo vicino " +
                                "alla panchina.");
                        getRoom(BRAWL).setLook("E' una grossa panchina in legno un po' malandata, ci sei solo tu" +
                                " nelle vicinanze.");

                    } else if (object.getId() == HACKSAW && getObject(TOOLS).isUsed()) {
                        getRoom(PASSAGE_NORTH).setLocked(false);
                        getInventory().remove(object);
                        out.println("Oh no! Il seghetto si è rotto e adesso ci sono pezzi di sega dappertutto, per" +
                                "fortuna sei riuscito a rompere la grata");
                        out.println("Dopo esserti allenato duramente riesci a tagliare le sbarre con il seghetto, " +
                                "puoi proseguire nel condotto e capisci che quel condotto porta fino all’infermeria.");
                        out.println("Avrebbe più senso proseguire solo se la tua squadra è al completo… " +
                                "non ti sembri manchi la persona più importante???");

                    } else if (object.getId() == SINK || object.getId() == SINK_BROTHER) {
                        out.println("Decidi di lavarti le mani e il viso, l’igiene prima di tutto!");
                    } else if (object.getId() == GENERATOR_OBJ) {
                        if (object.isUsed() || getObject(BUTTON_GENERATOR).isPush()) {
                            out.println("Il generatore è gia stato usato, fai in fretta!!");
                        } else {
                            getObject(BUTTON_GENERATOR).setPush(true);
                            getRoom(DOOR_ISOLATION).setLocked(false);
                            out.println("Sembra che tutto il carcere sia nell’oscurità! È stata una bella mossa" +
                                    " la tua, peccato che i poliziotti prevedono queste bravate e hanno un generatore" +
                                    " di corrente ausiliario che si attiverà dopo un minuto dal blackout!");
                        }
                    } else if (object.getId() == ACID) {
                        getRoom(ENDGAME).setLocked(false);
                        getInventory().remove(object);
                        out.println("La finestra adesso presenta un buco, sarebbe meglio infilarsi dentro!");
                    } else if (object.getId() == COMBINATION) {
                        getInventory().remove(object);
                        getRoom(ISOLATION).setLocked(false);
                        out.println("la porta si apre e ti trovi dentro il luogo dove si trovano le celle isolamento. " +
                                "Ci sono tre lunghi corridoi, uno a est, uno a ovest e l’altro a nord! Non noti " +
                                "nient’altro di particolare!");
                    }
                } else {
                    if (object == null) {
                        out.println("Sei sicuro di non voler usare niente?");
                    } else if (!object.isUsable()) {
                        if (object.getId() == HACKSAW
                                && !getObject(TOOLS).isUsed()
                                && getCurrentRoom().isObjectUsableHere(getObject(HACKSAW))) {
                            out.println("Il seghetto sembra molto arrugginito e non riesci a tagliare le sbarre " +
                                    "della grata! In realtà la colpa non è totalmente del seghetto ma anche la tua " +
                                    "poiché sei molto stanco e hai poca forza nelle braccia!");
                        }
                        out.println("Mi dispiace ma questo oggetto non si può utilizzare");
                    } else if (!getCurrentRoom().isObjectUsableHere(object)) {
                        out.println("C’è tempo e luogo per ogni cosa, ma non ora.");
                    } else if (!getInventory().contains(object) && !getCurrentRoom().containsObject(object)) {
                        out.println("Io non vedo nessun oggetto di questo tipo qui!");
                    }
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.OPEN)) {
                if (object != null
                        && object.isOpenable()
                        && !object.isOpen()
                        && (getCurrentRoom().containsObject(object))) {
                    if (!(object instanceof TokenObjectContainer)) {
                        out.println("Hai aperto " + object.getName() + "!");
                    } else if (!object.isOpen()) {
                        out.println("Hai aperto " + object.getName() + "!");
                        out.println("Contiene: ");
                        for (TokenObject obj : ((TokenObjectContainer) object).getObjects()) {
                            out.println(obj.getName() + ": " + obj.getDescription());
                        }
                    }
                    object.setOpen(true);
                } else if (object == null) {
                    out.println("Cosa vorresti aprire di preciso?");
                } else if (!object.isOpenable()) {
                    out.println("Sei serio? Vorresti veramente aprirlo?!");
                    out.println("Sei fuori di testa!");
                } else if (object.isOpen()) {
                    out.println("E' gia' aperto testa di merda!");
                } else {
                    out.println("Questo oggetto lo vedi solo nei tuoi sogni!");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.CLOSE)) {
                if (object != null
                        && object.isOpenable()
                        && object.isOpen()
                        && (getCurrentRoom().containsObject(object))) {

                    out.println("Hai chiuso " + object.getName() + "!");
                    object.setOpen(false);

                } else if (object == null) {
                    out.println("Cosa vorresti chiudere di preciso?");
                } else if (!object.isOpenable()) {
                    out.println("Sei serio? Vorresti veramente chiuderlo?!");
                    out.println("Sei fuori di testa!");
                } else if (!object.isOpen()) {
                    out.println("E' gia' chiuso testa di merda!");
                } else {
                    out.println("Questo oggetto lo vedi solo nei tuoi sogni!");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.PUSH)) {
                if (object != null && object.isPushable() && getCurrentRoom().containsObject(object)) {
                    if (object.getId() == LADDER) {
                        // ladder case
                        if (getCurrentRoom().getId() == PASSAGE_SOUTH) {
                            getRoom(SECRET_PASSAGE).setObject(object);
                            getRoom(PASSAGE_SOUTH).getObjects().remove(object);
                            out.println("La scala è stata spinta fino alla stanza a nord!");
                        } else if (getCurrentRoom().getId() == SECRET_PASSAGE) {
                            getRoom(PASSAGE_NORTH).setObject(object);
                            getRoom(SECRET_PASSAGE).getObjects().remove(object);
                            out.println("La scala è stata spinta fino alla stanza a nord e si è bloccata lì!");
                        } else {
                            out.println("La scala è bloccata! Non esiste alcun modo per spostarla!");
                        }

                    } else if (object.getId() == SINK) {
                        if (getCurrentRoom().getId() == CELL) {
                            if (object.isPush()) {
                                out.println("Il Lavandino è già stato spostato!");
                            } else {
                                object.setPush(true);
                                getRoom(SECRET_PASSAGE).setLocked(false);
                                out.println("Oissà!");
                            }
                        }

                    } else if (object.getId() == PICTURE) {
                        if (getCurrentRoom().getId() == INFIRMARY) {
                            // picture pushed
                            if (object.isPush()) {
                                out.println("Il quadro è già stato spostato!");
                            } else {
                                object.setPush(true);
                                getCurrentRoom().setObject(getObject(AIR_DUCT_OLD));
                                out.println(getObject(AIR_DUCT_OLD).getDescription());
                            }
                        }
                    } else if (object.getId() == BUTTON_GENERATOR) {
                        if (getCurrentRoom().getId() == GENERATOR) {
                            // botton pushed
                            if (object.isPush()) {
                                out.println("Il pulsante è già stato premuto! Fai in fretta!!!");
                            } else {
                                object.setPush(true);
                                getObject(GENERATOR_OBJ).setUsable(true);
                                getRoom(DOOR_ISOLATION).setLocked(false);
                                out.println("Sembra che tutto il carcere sia nell’oscurità! È stata una bella mossa" +
                                        " la tua, peccato che i poliziotti prevedono queste bravate e hanno un generatore" +
                                        " di corrente ausiliario che si attiverà dopo un minuto dal blackout!");
                            }
                        }
                    }
                } else if (object == null) {
                    out.println("Cosa vuoi spostare? L'aria?!?");
                } else if (!object.isPushable()) {
                    out.println("Puoi essere anche Hulk ma quell'oggetto non si può spostare!!!");
                } else if (!(getCurrentRoom().containsObject(object))) {
                    out.println("Forse non ci vedi bene, quell'oggetto non è presente in questa stanza!!!");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.EAT)) {
                if (object != null && object.isEatable() && (getInventory().contains(object)
                        || getCurrentRoom().containsObject(object))) {
                    // food case
                    if (getCurrentRoom().getObjects().contains(object)) {
                        getCurrentRoom().getObjects().remove(object);
                        out.println("Gnam Gnam...");
                    } else if (getInventory().getObjects().contains(object)) {
                        getInventory().remove(object);
                        out.println("Gnam Gnam...");
                    }
                } else if (object == null) {
                    out.println("Cosa vuoi mangiare??? Sembra non ci sia nulla di commestibile");
                } else if (!object.isEatable()) {
                    out.println("Sei veramente sicuro??? Non mi sembra una buona idea!");
                } else if (!(getInventory().contains(object)
                        || getCurrentRoom().containsObject(object))) {
                    out.println("Non penso si trovi qui questo oggetto!!! Compriamo un paio di occhiali?");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.STAND_UP)) {
                if (object == null) {
                    // in piedi
                    if (!isStandUp()) {
                        setStandUp(true);
                        setBed(false);
                        out.println("Oplà! Ti sei alzato!");
                    } else {
                        out.println("Sei così basso che non ti accorgi di stare già in piedi???");
                    }
                } else {
                    out.println("Non penso che questa cosa si possa fare ?!?");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.SIT_DOWN)) {
                if (object != null && object.isSit() && getCurrentRoom().containsObject(object)) {
                    // bed case
                    if (object.getId() == BED || object.getId() == BED_BROTHER) {
                        if (isStandUp() || !isBed()) {
                            setStandUp(false);
                            setBed(true);
                            out.println("Buonanotte fiorellino!");
                        } else {
                            out.println("Sei talmente stanco che nemmeno ti accorgi che sei già seduto???");
                        }
                    } else if (object.getId() == WATER) {
                        if (isStandUp() || isBed()) {
                            setStandUp(false);
                            setBed(false);
                            out.println("Proprio ora devi farlo?");
                        } else {
                            out.println("Sei già seduto! Ricordati di tirare lo scarico!");
                        }
                    }
                } else if (object == null) {
                    out.println("Sedersi sul pavimento non mi sembra una buona idea!");
                } else if (!object.isSit()) {
                    out.println("Con quell'oggetto puoi fare altro ma di certo non sederti!");
                } else if (!getCurrentRoom().containsObject(object)) {
                    out.println("Non penso si trovi qui questo oggetto!!! Guarda meglio!");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.CLIMB)) {

                if (getCurrentRoom().getId() == LOBBY) {
                    setCurrentRoom(getCurrentRoom().getWest());
                    move = true;
                } else if (object != null && getCurrentRoom().containsObject(object)) {
                    if (object.getId() == LADDER && getCurrentRoom().getId() == PASSAGE_NORTH) {
                        getRoom(ON_LADDER).setLocked(false);
                        setCurrentRoom(getCurrentRoom().getNorth());
                        move = true;
                    } else if (object.getId() != LADDER) {
                        out.println("Non puoi salire su quell'oggetto");
                    } else {
                        out.println("Usa quell'oggetto altrove, qui acchiappi solo le mosche!");
                    }
                } else if (object == null) {
                    out.println("Sei Spiderman? Non puoi arrampicarti sui muri!");
                } else if (!getCurrentRoom().containsObject(object)) {
                    out.println("Non penso si trovi qui questo oggetto!!! Guarda meglio!");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.GET_OFF)) {

                if (getCurrentRoom().getId() == LADDERS) {
                    setCurrentRoom(getCurrentRoom().getEast());
                    move = true;
                } else if (getCurrentRoom().getId() == AIR_DUCT) {
                    setCurrentRoom(getCurrentRoom().getSouth());
                    move = true;
                    out.println("Usi la scala per scendere!");
                } else {
                    out.println("Non puoi bucare il pavimento!");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.ENTER)) {

                if (getCurrentRoom().getId() == CELL && getObject(SINK).isPush()) {
                    setCurrentRoom(getCurrentRoom().getWest());
                    move = true;
                } else if (getCurrentRoom().getId() == ON_LADDER) {
                    setCurrentRoom(getCurrentRoom().getNorth());
                    move = true;
                } else if (getCurrentRoom().getId() == DOOR_ISOLATION) {
                    setCurrentRoom(getCurrentRoom().getEast());
                    move = true;
                } else {
                    out.println("Non puoi entrare lì!");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.EXIT)) {
                if (getCurrentRoom().getId() == FRONTBENCH && !getInventory().contains(getObject(SCALPEL))) {
                    setCurrentRoom(getCurrentRoom().getNorth());
                    move = true;
                    out.println("Decidi di fuggire, ma prima o poi il pericolo dovrai affrontarlo!\n");
                } else {
                    out.println("Perchè scappare?? Ma soprattutto da cosa??? Fifone!");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.MAKE)) {
                TokenObject substances = getObject(SUBSTANCES);
                if ((object != null
                        && object.isMixable()
                        && (getInventory().contains(object)
                        || getCurrentRoom().containsObject(object)))
                        || ((object != null && object.equals(getObject(ACID)))
                        && (getInventory().contains(substances)
                        || getCurrentRoom().containsObject(substances)))) {

                    if (getCurrentRoom().getObjects().contains(object) && !(object.equals(getObject(ACID)))) {
                        getCurrentRoom().getObjects().remove(object);
                        getInventory().add(getObject(ACID));
                        getObjectNotAssignedRoom().remove(getObject(ACID));
                        mixed = true;
                    } else if (!object.equals(getObject(ACID)) && getInventory().getObjects().contains(object)) {
                        getInventory().remove(object);
                        getInventory().add(getObject(ACID));
                        getObjectNotAssignedRoom().remove(getObject(ACID));
                        mixed = true;
                    } else if (getCurrentRoom().getObjects().contains(substances)
                            && object.equals(getObject(ACID))) {
                        getCurrentRoom().getObjects().remove(substances);
                        getInventory().add(getObject(ACID));
                        getObjectNotAssignedRoom().remove(getObject(ACID));
                        mixed = true;
                    } else if (getInventory().getObjects().contains(substances)
                            && object.equals(getObject(ACID))) {
                        getInventory().remove(substances);
                        getInventory().add(getObject(ACID));
                        getObjectNotAssignedRoom().remove(getObject(ACID));
                        mixed = true;
                    }
                    if (mixed || !getInventory().getObjects().contains(getObject(ACID))) {
                        out.println("Hai creato un acido corrosivo, attento alle mani!");
                        out.println("L'acido è stato inserito nel tuo inventario!");
                    } else {
                        out.println("Hai già creato l'acido!!! Guarda bene nel tuo inventario!");
                    }

                } else if (object == null) {
                    out.println("Cosa vuoi creare esattamente?");
                } else if (!object.isMixable()) {
                    out.println("Non è una cosa che si può fare");
                }
            } else if (p.getVerb().getVerbType().equals(VerbType.TURN_OFF)) {
                if (object != null && object.isTurnOnAble()) {
                    // lights case
                    if (getCurrentRoom().getId() == GENERATOR) {
                        // lights turnOFF
                        if (!object.isOn()) {
                            out.println("Il pulsante è già stato premuto! Fai in fretta!!!");
                        } else {
                            getObject(BUTTON_GENERATOR).setPush(true);
                            getObject(GENERATOR_OBJ).setUsable(true);
                            object.setOn(false);
                            getRoom(DOOR_ISOLATION).setLocked(false);
                            out.println("Sembra che tutto il carcere sia nell’oscurità! È stata una bella mossa" +
                                    " la tua, peccato che i poliziotti prevedono queste bravate e hanno un generatore" +
                                    " di corrente ausiliario che si attiverà dopo un minuto dal blackout!");
                        }
                    } else {
                        out.println("Non puoi spegnere nulla qui!");
                    }
                } else if (object == null) {
                    out.println("Cosa vuoi spegnere esattamente???");
                } else if (!object.isTurnOnAble()) {
                    out.println("Come puoi spegnere questo oggetto???");
                }
            } else if (p.getVerb().getVerbType().equals(VerbType.TURN_ON)) {
                if (object != null && object.isTurnOnAble()) {
                    // lights case
                    if (getCurrentRoom().getId() == GENERATOR) {
                        // lights turnOFF
                        if (object.isOn()) {
                            out.println("Le luci sono già accese!");
                        } else {
                            out.println("Le luci si accenderanno da sole tra qualche minuto, non avere paura!");
                        }
                    } else {
                        out.println("Non puoi accendere nulla qui!");
                    }
                } else if (object == null) {
                    out.println("Cosa vuoi accendere esattamente???");
                } else if (!object.isTurnOnAble()) {
                    out.println("Come puoi accendere questo oggetto???");
                }
            }

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
        } catch (ObjectNotFoundInInventoryException e) {
            out.println("Non hai questo oggetto nell'inventario, energumeno");
        } catch (ObjectsAmbiguityException e) {
            out.println("Ci sono più oggetti di questo tipo in questa stanza e non capisco a quale ti riferisci!");
        } catch (ObjectNotFoundInRoomException e) {
            if (p.getVerb().getVerbType() == VerbType.PICK_UP
                    || p.getVerb().getVerbType() == VerbType.USE
                    || p.getVerb().getVerbType() == VerbType.REMOVE
                    || p.getVerb().getVerbType() == VerbType.OPEN
                    || p.getVerb().getVerbType() == VerbType.CLOSE
                    || p.getVerb().getVerbType() == VerbType.DESTROY
                    || p.getVerb().getVerbType() == VerbType.GIVE
                    || p.getVerb().getVerbType() == VerbType.EAT
                    || p.getVerb().getVerbType() == VerbType.PULL
                    || p.getVerb().getVerbType() == VerbType.PUSH
                    || p.getVerb().getVerbType() == VerbType.MAKE) {
                out.println("Questo oggetto lo vedi solo nei tuoi sogni!");
            } else {
                out.println("Hai fumato qualcosa per caso?!");
            }
        } catch (Exception e) {
            //FIXME
            out.println(e.getMessage());
        }
    }
}
