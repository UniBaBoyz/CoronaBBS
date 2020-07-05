package adventure.games.prisonbreak;

import adventure.GameDescription;
import adventure.exceptions.LockedRoomException;
import adventure.exceptions.NotAccessibleRoomException;
import adventure.exceptions.inventoryException.InventoryEmptyException;
import adventure.exceptions.inventoryException.InventoryFullException;
import adventure.exceptions.inventoryException.ObjectNotFoundInInventoryException;
import adventure.exceptions.objectsException.ObjectNotFoundInRoomException;
import adventure.exceptions.objectsException.ObjectsAmbiguityException;
import adventure.parser.ParserOutput;
import adventure.type.*;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static adventure.games.prisonbreak.ObjectType.*;
import static adventure.games.prisonbreak.RoomType.*;
import static adventure.utils.Utils.AFTER_FOUGHT;
import static adventure.utils.Utils.FOUGHT_SCORE;


/**
 * @author Corona-Extra
 */
public class PrisonBreakGame extends GameDescription {

    private short counterFaceUp = 0;

    public PrisonBreakGame() {
        initVerbs();
        initRooms();

        //Set starting room
        setCurrentRoom(getRoom(MAIN_CELL));

        //Set Inventory
        setInventory(new Inventory(5));
    }

    private void initVerbs() {
        TokenVerb nord = new TokenVerb(VerbType.NORD);
        nord.setAlias(new HashSet<>(Collections.singletonList("Nord")));
        getTokenVerbs().add(nord);

        TokenVerb sud = new TokenVerb(VerbType.SOUTH);
        sud.setAlias(new HashSet<>(Collections.singletonList("Sud")));
        getTokenVerbs().add(sud);

        TokenVerb est = new TokenVerb(VerbType.EAST);
        est.setAlias(new HashSet<>(Collections.singletonList("Est")));
        getTokenVerbs().add(est);

        TokenVerb ovest = new TokenVerb(VerbType.WEST);
        ovest.setAlias(new HashSet<>(Collections.singletonList("Ovest")));
        getTokenVerbs().add(ovest);

        TokenVerb inventory = new TokenVerb(VerbType.INVENTORY);
        inventory.setAlias(new HashSet<>(Arrays.asList("Inv", "I", "Inventario")));
        getTokenVerbs().add(inventory);

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
                "Ascolta", "Grida", "Urla", "Mormora", "Sussurra", "Bisbiglia", "Conferisci")));
        getTokenVerbs().add(talk);

        TokenVerb faceUp = new TokenVerb(VerbType.FACE_UP);
        faceUp.setAlias(new HashSet<>(Arrays.asList("Affronta", "Affrontali", "Attacca", "Mena", "Azzuffati", "Litiga",
                "Scontrati", "Lotta", "Combatti", "Attaccali", "Menali")));
        getTokenVerbs().add(faceUp);

        TokenVerb ask = new TokenVerb(VerbType.ASK);
        ask.setAlias(new HashSet<>(Arrays.asList("Chiedi", "Domanda", "Desidero")));
        getTokenVerbs().add(ask);

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
        getTokenVerbs().add(destroy);
    }

    private void initRooms() {
        Room mainCell17 = new Room(MAIN_CELL, "Cella n.ro 17", "Ti trovi nella tua cella 17," +
                " al momento sei da solo" + " visto che sei l’ultimo arrivato.");
        mainCell17.setLook("La cella e' poco accogliente... l’unica via di uscita si trova a est, al momento aperta visto " +
                "che e' l’ora d’aria e tutti i detenuti devono raggiungere il giardino!");

        Room corridorNorth = new Room(CORRIDOR_NORTH, "Corridoio nord",
                "Ti trovi nel corridoio del carcere che si estende da sud verso nord.");
        corridorNorth.setLook("Si sentono tanti rumori e urla dei detenuti, a ovest le" +
                " altre celle in cui non e' possibile entrare poiche' sono chiuse.");

        Room corridor = new Room(CORRIDOR, "Corridoio", "Ti trovi nel corridoio del carcere che si" +
                " estende da sud verso nord.");
        corridor.setLook("Si sentono tanti rumori e urla dei detenuti, a ovest le" +
                " altre celle in cui non e' possibile entrare poiche' sono chiuse.");

        Room corridorSouth = new Room(CORRIDOR_SOUTH, "Corridoio sud",
                "Ti trovi nel corridoio del carcere che si estende da sud verso nord.");
        corridorSouth.setLook("Si sentono tanti rumori e urla dei detenuti, a ovest la porta della tua cella aperta e" +
                " altre celle in cui non e' possibile entrare poiche' sono chiuse.");

        Room ladders = new Room(LADDERS, "Scalinata", "Ti trovi presso una scalinata, l’unica cosa" +
                " che puoi fare e' andare al piano di sotto oppure andare verso sud percorrendo il corridoio.");
        ladders.setLook("Puoi vedere i detenuti che si dirigono verso il giardino.");

        Room lobby = new Room(LOBBY, "Atrio", "Ti trovi in un grosso atrio di ingresso dove puoi " +
                "intravedere a est il giardino.");
        lobby.setLook("Il luogo e' affollato di guardie che controllano la situazione. Puoi salire tramite la scalinata" +
                " al piano superiore oppure andare nel giardino a est. L’atrio si estende ancora verso sud.");

        Room lobbySouth = new Room(LOBBY_SOUTH, "Atrio", "Ti trovi a sud del grosso atrio di ingresso." +
                " Puoi notare che l'atrio prosegue sia a nord che a sud!");
        lobbySouth.setLook("Puoi notare a ovest le celle chiuse dei detenuti e a est la palestra.");

        Room lobbyEnd = new Room(END_LOBBY, "Atrio", "Ti trovi alla fine del grosso atrio, a sud vedi " +
                " tante guardie, sembra una zona particolarmente protetta!!! Noti a est la sala per la mensa e a " +
                " ovest le celle chiuse dei detenuti!");
        lobbyEnd.setLook("Non c'è nulla di particolare! Puoi ritornare indietro verso nord!");

        Room garden = new Room(GARDEN, "Giardino", "Sei in un ampio giardino verde illuminato " +
                "dal sole, dietro di te il grosso atrio.");
        garden.setLook("Guardando a nord puoi notare un grosso campo da basket, avanti a te a est un grosso muro " +
                "che separa il giardino dall’esterno con due enormi torri sulle quali ci sono le guardie come vedetta," +
                " a sud invece tre grosse panchine dove puoi sederti e rilassarti.");

        Room basket = new Room(BASKET_CAMP, "Campo da basket", "Ti trovi nel campo di basket" +
                " momentaneamente vuoto.");
        basket.setLook("Sembra un po' trascurato... C'è un grosso giardino alle tue spalle a sud");

        Room wall = new Room(WALL, "Muro prigione", "Avvicinandoti alle mura le guardie ti danno " +
                "l’ordine di indietreggiare: rappresenti un potenziale pericolo. Non penso sia un’idea geniale " +
                "fuggire da qui, la zona e' troppo controllata.");
        wall.setLook("Non c'e' nulla di particolare tranne che un grosso muro in mattoni! Vai a ovest per tornare " +
                "indietro!");

        Room bench = new Room(BENCH, "Panchine", "Tutte le panchine sono occupati da un gruppo " +
                "di detenuti che ti guardano con aria sospetta.");
        bench.setLook("Non noti nulla di particolare in loro e nelle panchine, tranne in una dove a terra puoi " +
                "notare un oggetto di metallo simile ad una vite. Vai a nord per tornare indietro!");

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

        Room airDuctInfirmary = new Room(AIR_DUCT_INFIRMARY, "Condotto d'aria", "Ti trovi nell'ultimo" +
                " tratto del condotto d'aria, sei quasi arrivato in infermeria");
        airDuctInfirmary.setLook("Dal condotto d'aria riesci a vedere tuo fratello nell'infermeria che ti aspetta!");
        airDuctInfirmary.setLocked(true);

        Room airDuctWest = new Room(AIR_DUCT_WEST, "Condotto d'aria",
                " Il condotto si fa sempre piu' stretto e non riesci piu' a passare! " +
                        "Non sembra sia la strada giusta!");
        airDuctWest.setLook("Scorgi pero' in lontananza un piccolo oggetto!");

        Room canteen = new Room(CANTEEN, "Mensa", "Ti trovi in una grossa mensa e puoi vedere " +
                "i detenuti che stanno mangiando.");
        canteen.setLook("Uno di loro si avvicina a te e sussurrando ti chiede se sei interessato a qualche oggetto" +
                " che lui possiede. Ovviamente ogni oggetto ha un costo ma tu non possiedi alcun soldo, " +
                "per averne uno quindi sarei costretto a trattare.");

        Room gym = new Room(GYM, "Palestra", "Ti trovi nella palestra del carcere, " +
                "e' molto grande e non e' tenuta in ottimo stato, alcuni detenuti si stanno allenando.");
        gym.setLook("Intorno a te vedi tanti attrezzi per poterti allenare e aumentare la tua forza.");

        Room outIsolation = new Room(OUT_ISOLATION, "Fuori cella isolamento",
                "Sei di fronte all'entrata dell'isolamento dove e' collocata la cella di tuo fratello. " +
                        "Essendo in un carcere di massima sicurezza, la porta e' controllata da un paio di guardie.");
        outIsolation.setLook("Puoi notare da lontano che non si tratta di una semplice porta ma di una porta che " +
                "puo' essere aperta solo tramite un PIN segreto.");

        Room doorIsolation = new Room(DOOR_ISOLATION, "Porta isolamento",
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
                "Sei vicino alla cella di tuo fratello, l’aspetto della cella e' ripugnante.");
        brotherCell.setLook("Di fronte a te, attraverso le sbarre, c'è tuo fratello con un’aria contenta di vederti," +
                " ma allo stesso tempo sorpresa!");

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
                "migliore restare lì impalato. Scegli se affrontarli oppure scappare!!!");

        Room grateCell = new Room(GRATE_CELL, "Grata che si affaccia su una cella",
                "Dall'alto riesci ad osservare tutta la cella, tra cui anche il detenuto che dorme." +
                        "Meglio non svegliarlo. Shhhhhhh!");
        grateCell.setLook("Meglio non perdere tempo qui!");

        Room cell18 = new Room(OTHER_CELL, "Cella n.ro 18", "La cella e' controllata da un " +
                "poliziotto e poi non mi sembra il caso di intrufolarsi in una cella di un detenuto. Rischieresti " +
                "di mandare a rotoli il piano!!!");
        cell18.setLook("Meglio non perdere tempo qui!");

        Room cell19 = new Room(OTHER_CELL, "Cella n.ro 19", "La cella e' controllata da un " +
                "poliziotto e poi non mi sembra il caso di intrufolarsi in una cella di un detenuto. Rischieresti " +
                "di mandare a rotoli il piano!!!");
        cell19.setLook("Meglio non perdere tempo qui!");

        Room cell20 = new Room(OTHER_CELL, "Cella n.ro 20", "La cella e' controllata da un " +
                "poliziotto e poi non mi sembra il caso di intrufolarsi in una cella di un detenuto. Rischieresti " +
                "di mandare a rotoli il piano!!!");
        cell20.setLook("Meglio non perdere tempo qui!");

        Room cell21 = new Room(OTHER_CELL, "Cella n.ro 21", "La cella e' controllata da un " +
                "poliziotto e poi non mi sembra il caso di intrufolarsi in una cella di un detenuto. Rischieresti " +
                "di mandare a rotoli il piano!!!");
        cell20.setLook("Meglio non perdere tempo qui!");

        Room cell22 = new Room(OTHER_CELL, "Cella n.ro 22", "La cella e' controllata da un " +
                "poliziotto e poi non mi sembra il caso di intrufolarsi in una cella di un detenuto. Rischieresti " +
                "di mandare a rotoli il piano!!!");
        cell20.setLook("Meglio non perdere tempo qui!");

        //Maps
        mainCell17.setEast(corridorSouth);
        mainCell17.setWest(passage);
        corridorSouth.setWest(mainCell17);
        corridorSouth.setNorth(corridor);
        corridor.setSouth(corridorSouth);
        corridor.setNorth(corridorNorth);
        corridor.setWest(cell18);
        cell18.setEast(corridor);
        cell18.setNorth(corridorNorth);
        cell18.setSouth(corridorSouth);
        corridorNorth.setWest(cell19);
        corridorNorth.setNorth(ladders);
        corridorNorth.setSouth(corridorSouth);
        cell19.setNorth(ladders);
        cell19.setEast(corridorNorth);
        cell19.setSouth(corridorSouth);
        ladders.setSouth(corridorNorth);
        ladders.setEast(lobby);
        lobby.setWest(ladders);
        lobby.setEast(garden);
        lobby.setSouth(lobbySouth);
        lobbySouth.setEast(gym);
        lobbySouth.setNorth(lobby);
        lobbySouth.setWest(cell20);
        cell20.setEast(lobbySouth);
        cell20.setNorth(lobby);
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
        gym.setWest(lobbySouth);
        lobbyEnd.setEast(canteen);
        lobbyEnd.setWest(cell21);
        lobbyEnd.setNorth(lobbySouth);
        lobbyEnd.setSouth(outIsolation);
        cell21.setEast(lobbyEnd);
        cell21.setNorth(lobbySouth);
        cell21.setSouth(outIsolation);
        canteen.setWest(lobbyEnd);
        outIsolation.setNorth(lobbyEnd);
        outIsolation.setEast(doorIsolation);
        outIsolation.setWest(cell22);
        cell22.setEast(outIsolation);
        cell22.setNorth(lobbyEnd);
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
        brotherCell.setNorth(isolationCorridorSouthSouth);
        passage.setEast(mainCell17);
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
        airDuctEast.setSouth(grateCell);
        airDuctEast.setWest(airDuct);
        airDuctWest.setEast(airDuct);
        airDuctNorth.setSouth(airDuct);
        airDuctNorth.setEast(airDuctInfirmary);
        grateCell.setNorth(airDuctEast);
        airDuctInfirmary.setWest(airDuctNorth);
        airDuctInfirmary.setEast(infirmary);
        infirmary.setWest(airDuctInfirmary);
        infirmary.setNorth(windowInfirmary);
        windowInfirmary.setSouth(infirmary);
        windowInfirmary.setNorth(endGame);

        createRooms(mainCell17);

        TokenPerson gennyBello = new TokenPerson(GENNY_BELLO, "Genny Bello",
                new HashSet<>(Collections.singletonList("Genny")),
                "E' un detenuto come te che smista oggetti illegali nella prigione in cambio di favori",
                new HashSet<>(
                        Collections.singletonList(
                                new TokenAdjective(new HashSet<>(Collections.singletonList("Bello"))))),
                3);
        gennyBello.setSpeakable(true);
        canteen.setObject(gennyBello);

        TokenPerson brother = new TokenPerson(BROTHER, "Tuo fratello Lincoln",
                new HashSet<>(Arrays.asList("Lincoln", "fratello")),
                "E' tuo fratello! Non ho nient'altro da dirti che già non sai. " +
                        "Dovresti già sapere tutto su di lui!",
                new HashSet<>(
                        Collections.singletonList(
                                new TokenAdjective(new HashSet<>(Collections.singletonList("mio"))))),
                0);
        brother.setSpeakable(true);
        brotherCell.setObject(brother);

        TokenObject screw = new TokenObject(SCREW, "Vite", new HashSet<>(Arrays.asList("Vite", "Chiodo")),
                "E' una semplice vite con inciso il numero di serie: 11121147.");
        screw.setUsable(true);
        frontBench.setObject(screw);
        mainCell17.setObjectsUsableHere(screw);

        TokenObject scotch = new TokenObject(SCOTCH, "Scotch", new HashSet<>(Arrays.asList("Scotch", "Nastro")),
                "E' un semplice scotch, dimenticato li forse da qualche operaio!");
        airDuctWest.setObject(scotch);
        scotch.setUsable(true);
        scotch.setPickupable(true);
        doorIsolation.setObjectsUsableHere(scotch);

        TokenObject tools = new TokenObject(TOOLS, "Attrezzi",
                new HashSet<>(Arrays.asList("Attrezzi", "Manubri", "Pesi")),
                "Sono degli attrezzi da palestra, ottimi per allenarsi e aumentare la forza!");
        tools.setUsable(true);
        gym.setObject(tools);
        gym.setObjectsUsableHere(tools);

        TokenObject food = new TokenObject(FOOD, "Cibo",
                new HashSet<>(Arrays.asList("Cibo", "Pranzo", "Cena", "Piatto")),
                "C'è solo il tuo pranzo che emana un odore non buonissimo, puoi mangiarlo anche se non" +
                        " servirà a nulla.");
        food.setEatable(true);
        food.setPickupable(true);
        mainCell17.setObject(food);

        TokenObject ball = new TokenObject(BALL, "Pallone",
                new HashSet<>(Arrays.asList("Palla", "Pallone", "Basketball")),
                "Un semplice pallone da basket.");
        ball.setPickupable(true);
        ball.setUsable(true);
        ball.setPlayable(true);
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
        frontBench.setObjectsUsableHere(scalpel);

        TokenObject hacksaw = new TokenObject(HACKSAW, "Seghetto",
                new HashSet<>(Arrays.asList("Seghetto", "Sega", "Taglierino")),
                "E’ un seghetto molto affilato, potresti riuscire a rompere qualcosa.",
                new HashSet<>(Collections.singletonList(new TokenAdjective(new HashSet<>(Arrays.asList("Rotto",
                        "Distrutto", "Devastato", "Spaccato"))))));
        hacksaw.setUsable(true);
        hacksaw.setAskable(true);
        airDuctNorth.setObjectsUsableHere(hacksaw);

        try {
            gennyBello.getInventory().add(hacksaw);
        } catch (InventoryFullException ignored) {
        }

        TokenObject substances = new TokenObject(SUBSTANCES, "Sostanze chimiche",
                new HashSet<>(Arrays.asList("Sostanze", "Ingredienti", "Oggetti")),
                "Sul tavolo puoi vedere alcuni strumenti di lavoro e alcune sostanze come: Cloruro " +
                        "di sodio, acido solforico e altre sostanze di cui non riesco nemmeno a leggere il nome!");
        substances.setPickupable(true);
        substances.setMixable(true);
        infirmary.setObject(substances);

        TokenObject medicine = new TokenObject(MEDICINE, "Farmaco",
                new HashSet<>(Arrays.asList("Farmaco", "Medicina", "Compresse", "Sciroppo")),
                "E' un medicinale per alleviare i dolori.");
        setObjectNotAssignedRoom(medicine);

        TokenObject sink = new TokenObject(SINK, "Lavandino",
                new HashSet<>(Arrays.asList("Lavandino", "Lavello", "Lavabo")),
                "E' un piccolo lavandino fissato al muro con delle viti arruginite... Ha un aspetto " +
                        "malandato!");
        sink.setUsable(true);
        mainCell17.setObject(sink);
        mainCell17.setObjectsUsableHere(sink);

        TokenObject bed = new TokenObject(BED, "Letto",
                new HashSet<>(Arrays.asList("Letto", "Lettino", "Brandina", "Lettuccio")),
                "E' presente un letto a castello molto scomodo e pieno di polvere!");
        bed.setSitable(true);
        mainCell17.setObject(bed);

        TokenObject table = new TokenObject(TABLE, "Tavolo",
                new HashSet<>(Arrays.asList("Tavolo", "Tavolino", "Scrivania")),
                "E' un semplice tavolo in legno, molto piccolo e molto sporco!");
        mainCell17.setObject(table);

        TokenObject windowCell = new TokenObject(WINDOW_CELL, "Finestra",
                new HashSet<>(Arrays.asList("Finestra", "Finestrella")),
                "E' una piccola finestra sbarrata dalla quale puoi osservare il cortile della prigione! " +
                        "Bel panorama!!!");
        mainCell17.setObject(windowCell);

        TokenObject water = new TokenObject(WATER, "Water",
                new HashSet<>(Arrays.asList("Water", "Cesso", "Gabinetto", "Tazza", "Wc")),
                "Un water qualunque, senza nessun particolare, non penso sia bello osservarlo");
        water.setSitable(true);
        mainCell17.setObject(water);

        TokenObject railing = new TokenObject(RAILING, "Ringhiera",
                new HashSet<>(Arrays.asList("Ringhiera", "Scorrimano")),
                "Sei troppo giovane per camminare appoggiato ad una ringhiera e troppo giovane " +
                        "per buttarti, ti prego non farlo!!!");
        corridorNorth.setObject(railing);

        TokenObject door = new TokenObject(DOOR_GARDEN, "Porta d'ingresso",
                new HashSet<>(Arrays.asList("Porta", "Portone", "Ingresso", "Soglia")),
                "E' una grande porta che separa giardino e atrio. E' sempre aperta e non puoi chiuderla!");
        garden.setObject(door);
        lobby.setObject(door);

        TokenObject basketObject = new TokenObject(BASKET_OBJECT, "Canestri", new HashSet<>(
                Arrays.asList("Canestri", "Canestro", "Basket")),
                "Ottimi per giocare a basket e perdere tempo!!!");
        basketObject.setPlayable(true);
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

        TokenObject newAirDuct = new TokenObject(NEW_AIR_DUCT_INFIRMARY, "Condotto d'aria nuovo",
                new HashSet<>(Arrays.asList("Condotto", "Passaggio")),
                "Non sei un campione di arrampicata o salto in alto, " +
                        "perché perdere tempo qui!",
                new HashSet<>(Arrays.asList(new TokenAdjective(new HashSet<>(
                                Arrays.asList("Nuovo", "Recente", "Migliorato"))),
                        new TokenAdjective(new HashSet<>(Collections.singletonList("D'aria"))))));
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

        TokenObject drug = new TokenObject(DRUG, "Droga", new HashSet<>(Arrays.asList("Droga", "Stupefacenti")),
                "Meglio continuare il piano di fuga da lucidi e fortunatamente non hai soldi con te per" +
                        " acquistarla! \nTi ricordo che il tuo piano è fuggire di prigione e non rimanerci qualche " +
                        "anno di più!");
        drug.setAskable(true);
        try {
            gennyBello.getInventory().add(drug);
        } catch (InventoryFullException ignored) {
        }

        TokenObject videogame = new TokenObject(VIDEOGAME, "Videogame", new HashSet<>(Arrays.asList("Videogame",
                "Gioco", "Videogioco")),
                "Sarebbe molto bello se solo avessi 8 anni! Quando uscirai di prigione avrai molto tempo " +
                        "per giocare anche a videogiochi migliori!");
        videogame.setAskable(true);
        try {
            gennyBello.getInventory().add(videogame);
        } catch (InventoryFullException ignored) {
        }

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
        combination.setInsertable(true);
        setObjectNotAssignedRoom(combination);
        doorIsolation.setObjectsUsableHere(combination);

        TokenObject oldAirDuct = new TokenObject(OLD_AIR_DUCT, "Condotto d'aria vecchio", new HashSet<>(
                Arrays.asList("Condotto d'aria vecchio", "Condotto d'aria", "Condotto", "Indotto")),
                "Dietro al quadro vedi un condotto d’aria dall’aspetto vecchiotto, " +
                        "sembra quasi che non serva più perché ne hanno costruito un altro… " +
                        "Perché nasconderlo?",
                new HashSet<>(Collections.singletonList(new TokenAdjective(new HashSet<>(Arrays.asList("Vecchio", "Anziano",
                        "Decrepito", "Antico", "Vetusto", "Antiquato", "Disusato", "Obsoleto", "Consumato"))))));
        setObjectNotAssignedRoom(oldAirDuct);

        TokenObject roomObject = new TokenObject(ROOM_OBJ, "Stanza", new HashSet<>(
                Arrays.asList("Stanza", "Camera", "Ambiente", "Locale")));
        setObjectNotAssignedRoom(roomObject);

        TokenObject scoreObject = new TokenObject(SCORE_OBJ, "Punteggio", new HashSet<>(
                Arrays.asList("Punteggio", "Punti", "Score")));
        setObjectNotAssignedRoom(scoreObject);

        TokenObject poster = new TokenObject(POSTER, "Poster di Rita Hayworth", new HashSet<>(
                Arrays.asList("Poster", "Manifesto", "Affisso")),
                "Non ti sembra di aver visto questo poster da qualche altra parte?\nCoincidenze? " +
                        "Io non credo, utilizzare questo poster ti darà dei suggerimenti per portare a termine la " +
                        "tua missione, non abusarne molto mi raccomando!",
                new HashSet<>(Collections.singletonList(new TokenAdjective(new HashSet<>(Arrays.asList("Rita", "Hayworth"))))));
        mainCell17.getObjects().add(poster);
        poster.setUsable(true);
        mainCell17.setObjectsUsableHere(poster);

        TokenObject cot = new TokenObject(COT, "Lettino",
                new HashSet<>(Arrays.asList("Lettino", "Letto", "Barella", "Brandina", "Lettuccio")),
                "E' presente un letto a castello molto scomodo e pieno di polvere!");
        cot.setSitable(true);
        infirmary.setObject(cot);
    }

    @Override
    public void nextMove(ParserOutput p, PrintStream out) {
        TokenObject object;
        boolean move = false;
        boolean mixed = false;

        try {
            object = getCorrectObject(p.getObject());
            if (p.getVerb().getVerbType().equals(VerbType.NORD)) {
                if (getCurrentRoom().getNorth() != null && !getCurrentRoom().getNorth().isLocked()) {
                    setCurrentRoom(getCurrentRoom().getNorth());
                    move = true;
                } else if (getCurrentRoom().getNorth() == null) {
                    throw new NotAccessibleRoomException();
                } else if (getCurrentRoom().getNorth().isLocked()) {
                    throw new LockedRoomException();
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.SOUTH)) {
                if (getCurrentRoom().getSouth() != null && !getCurrentRoom().getSouth().isLocked()) {
                    setCurrentRoom(getCurrentRoom().getSouth());
                    move = true;
                } else if (getCurrentRoom().getSouth() == null) {
                    throw new NotAccessibleRoomException();
                } else if (getCurrentRoom().getSouth().isLocked()) {
                    throw new LockedRoomException();
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.EAST)) {
                if (getCurrentRoom().getEast() != null && !getCurrentRoom().getEast().isLocked()) {
                    setCurrentRoom(getCurrentRoom().getEast());
                    move = true;
                } else if (getCurrentRoom().getEast() == null) {
                    throw new NotAccessibleRoomException();
                } else if (getCurrentRoom().getEast().isLocked()) {
                    throw new LockedRoomException();
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.WEST)) {
                if (getCurrentRoom().getWest() != null && !getCurrentRoom().getWest().isLocked()) {
                    setCurrentRoom(getCurrentRoom().getWest());
                    move = true;
                } else if (getCurrentRoom().getWest() == null) {
                    throw new NotAccessibleRoomException();
                } else if (getCurrentRoom().getWest().isLocked()) {
                    throw new LockedRoomException();
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.INVENTORY)) {
                if (!getInventory().isEmpty()) {
                    out.println("Nel tuo inventario ci sono:");
                    for (TokenObject o : getInventory().getObjects()) {
                        out.println(o.getName() + ": " + o.getDescription());
                    }
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.LOOK_AT)) {
                if (object != null
                        && (getInventory().contains(object) || getCurrentRoom().getObjects().contains(object)
                        || getCurrentRoom().getObjects().stream()
                        .anyMatch(obj -> obj instanceof TokenPerson
                                && ((TokenPerson) obj).getInventory().contains(object)))) {
                    out.println(object.getDescription());
                } else if (getCurrentRoom().getLook() != null && (object == null || object.getId() == ROOM_OBJ)) {
                    out.println(getCurrentRoom().getLook());
                } else if (object != null && object.getId() == SCORE_OBJ) {
                    out.println("Non male, attualmente il tuo punteggio è " + getScore());
                } else {
                    out.println("Non c'è niente di interessante qui.");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.PICK_UP)) {
                if (object != null && object.getId() == HACKSAW && object.isAccept()
                        && ((TokenPerson) getObject(GENNY_BELLO)).getInventory().contains(object)) {

                    //There is the need of the remove's operation from the room of the TokenPerson's objects
                    getCurrentRoom().removeObject(getObject(HACKSAW));
                    getInventory().add(object);
                    out.println("Hai preso " + object.getName() + "!");
                } else if (object != null
                        && object.getId() == SCALPEL
                        && getCurrentRoom().getId() == INFIRMARY
                        && !object.isTaken()) {

                    getCurrentRoom().removeObject(object);
                    getInventory().add(object);
                    object.setTaken(true);
                    increaseScore();
                    out.println("Hai preso " + object.getName() + "!");
                    out.println("Fai in fretta perché improvvisamente senti i passi dell’infermiera avvicinandosi " +
                            "alla porta, riesci a prendere il bisturi con te e l’infermiera ti dice che sei guarito" +
                            " e puoi ritornare nella cella visto che l’ora d’aria è finita\n");
                    setCurrentRoom(getRoom(MAIN_CELL));
                    getInventory().add(getObject(MEDICINE));
                    out.println("Caspita gli antidolorifici ti hanno fatto dormire molto e ti risvegli nella tua " +
                            "cella privo di qualsiasi dolore! Prima di andare via l’infermiera ti ha dato qualche " +
                            "medicinale tra cui un medicinale all’ortica. Guarda nel tuo inventario!\n");
                    move = true;

                } else if (object != null && object.isPickupable()
                        && getCurrentRoom().containsObject(object)) {

                    if ((object.getId() == SCOTCH || object.getId() == SCREW) && !object.isTaken()) {
                        increaseScore();
                        object.setTaken(true);
                    }
                    getCurrentRoom().removeObject(object);
                    getInventory().add(object);
                    out.println("Hai preso " + object.getName() + "!");

                } else if (object == null) {
                    out.println("Cosa vorresti prendere di preciso?");
                } else if (object.getId() == SCREW && !object.isPickupable()) {
                    out.println("Non puoi prendere quella vite se prima non affronti il gruppetto dei detenuti!");
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

                    if (object.getId() == SCREW) {
                        getObject(SINK).setPushable(true);
                        setObjectNotAssignedRoom(object);
                        getInventory().remove(object);
                        out.println("Decidi di usare il cacciavite, chiunque abbia fissato quel lavandino non aveva una " +
                                "grande forza visto che le viti si svitano facilmente. Adesso che hai rimosso tuttte le " +
                                "viti, noti che il lavandino non è ben fissato");
                        increaseScore();
                        object.setUsed(true);

                    } else if (object.getId() == SCOTCH) {
                        setObjectNotAssignedRoom(object);
                        getInventory().remove(object);
                        getInventory().add(getObject(COMBINATION));
                        out.println("Metti lo scotch sui numeri della porta, dallo scotch noti le impronte dei ultimi " +
                                "tasti schiacciati, ora indovinare il pin segreto sembra molto più semplice!");
                        increaseScore();
                        object.setUsed(true);

                    } else if (object.getId() == TOOLS) {
                        out.println("Decidi di allenarti per un bel po’ di tempo… alla fine dell’allenamento " +
                                "ti senti già più forte!");

                        if (!object.isUsed()) {
                            increaseScore();
                        }

                        object.setUsed(true);

                    } else if (object.getId() == BALL) {
                        out.println("Il tempo è denaro, non penso sia il momento adatto per mettersi a giocare.");
                        object.setUsed(true);

                    } else if (object.getId() == SCALPEL) {
                        setObjectNotAssignedRoom(object);
                        getInventory().remove(object);
                        out.println("Riesci subito a tirare fuori il bisturi dalla tasca, il gruppetto lo vede e capito " +
                                "il pericolo decide di lasciare stare (Mettere a rischio la vita per una panchina " +
                                "sarebbe veramente stupido) e vanno via con un'aria di vendetta. Ora sei solo vicino " +
                                "alla panchina.");
                        getRoom(FRONTBENCH).setLook("E' una grossa panchina in legno un po' malandata, ci sei solo tu" +
                                " nelle vicinanze.");
                        increaseScore();
                        object.setUsed(true);

                    } else if (object.getId() == HACKSAW && getObject(TOOLS).isUsed()) {
                        getRoom(AIR_DUCT_INFIRMARY).setLocked(false);
                        setObjectNotAssignedRoom(object);
                        getInventory().remove(object);
                        getRoom(AIR_DUCT_NORTH).removeObject(getObject(DESTROYABLE_GRATE));
                        out.println("Oh no! Il seghetto si è rotto e adesso ci sono pezzi di sega dappertutto, per " +
                                "fortuna sei riuscito a rompere la grata");
                        out.println("Dopo esserti allenato duramente riesci a tagliare le sbarre con il seghetto, " +
                                "puoi proseguire nel condotto e capisci che quel condotto porta fino all’infermeria.");
                        increaseScore();
                        increaseScore();
                        object.setUsed(true);

                    } else if (object.getId() == SINK) {
                        out.println("Decidi di lavarti le mani e il viso, l’igiene prima di tutto!");
                        object.setUsed(true);
                    } else if (object.getId() == GENERATOR_OBJ) {
                        if (object.isUsed() || getObject(BUTTON_GENERATOR).isPush()) {
                            out.println("Il generatore è gia stato usato, fai in fretta!!");
                        } else {
                            getObject(BUTTON_GENERATOR).setPush(true);
                            getRoom(DOOR_ISOLATION).setLocked(false);
                            getObject(LIGHTS).setOn(false);
                            out.println("Sembra che tutto il carcere sia nell’oscurità! È stata una bella mossa" +
                                    " la tua, peccato che i poliziotti prevedono queste bravate e hanno un generatore" +
                                    " di corrente ausiliario che si attiverà dopo un minuto dal blackout!");
                            increaseScore();
                            object.setUsed(true);
                        }

                    } else if (object.getId() == ACID) {
                        getRoom(ENDGAME).setLocked(false);
                        setObjectNotAssignedRoom(object);
                        getInventory().remove(object);
                        out.println("Adesso la finestra presenta un buco, sarebbe meglio infilarsi dentro!");
                        increaseScore();
                        increaseScore();
                        increaseScore();
                        increaseScore();
                        object.setUsed(true);

                    } else if (object.getId() == COMBINATION && !object.isUsed()) {
                        setObjectNotAssignedRoom(object);
                        getInventory().remove(object);
                        getRoom(ISOLATION).setLocked(false);
                        out.println("La porta si apre! Puoi andare a est per entrare dentro l'isolamento oppure" +
                                " tornare indietro anche se hai poco tempo a disposizione!");
                        increaseScore();
                        object.setUsed(true);
                    } else if (object.getId() == POSTER) {

                        object.setUsable(true);

                        // DON'T CHANGE THE ORDER
                        if (counterFaceUp == 0) {
                            out.println("Sei appena arrivato in carcere, perché non ti fai conoscere prendendo parte ad " +
                                    "una rissa in giardino?");
                        } else if (!getObject(SCALPEL).isUsed() && counterFaceUp == 1) {
                            out.println("Eccoti un consiglio: Dovresti andare nel giardino e utilizzare per bene quel " +
                                    "bisturi, coraggio prendi la tua vendetta!");
                        } else if (!getInventory().contains(getObject(SCREW)) && !getObject(SCREW).isUsed()) {
                            out.println("Adesso dovresti prendere la vite, ti servirà molto per portare al termine" +
                                    "la tua missione!");
                        } else if (getInventory().contains(getObject(SCREW))) {
                            out.println("Dovresti provare ad utilizzare la vite che hai in questa stanza, chissà cosa " +
                                    "potrà capitare...");
                        } else if (getObject(SCREW).isUsed() && !getObject(SINK).isPush()) {
                            out.println("I tuoi genitori hanno anche figli normali? Come fai a non comprendere che è " +
                                    "necessario spostare il lavandino!!");
                        } else if (((TokenPerson) getObject(GENNY_BELLO)).getInventory().contains(getObject(HACKSAW))) {
                            out.println("Dovresti cercare un utensile per rompere quelle grate che ti impediscono il " +
                                    "passaggio!");
                        } else if (!getObject(HACKSAW).isUsed() && !getObject(TOOLS).isUsed()) {
                            out.println("Adesso che hai il seghetto dovresti aumentare un pò la tua massa muscolare");
                        } else if (!getObject(HACKSAW).isUsed() && getObject(TOOLS).isUsed()) {
                            out.println("Ti vedo in forma adesso, ora sarai sicuramente in grado di distruggere " +
                                    "quella grata che è presente nel condotto d'aria");
                        } else if (getObject(HACKSAW).isUsed() && !getObject(SCOTCH).isUsed() && !getInventory().contains(getObject(SCOTCH))) {
                            out.println("Nel condotto d'aria c'è qualcosa che ti tornerà utile più tardi!");
                        } else if (!getObject(GENERATOR_OBJ).isUsed() && !getObject(MEDICINE).isGiven()) {
                            out.println("Ti consiglio di cercare un pò nel condotto d'aria e spegnere il generatore" +
                                    "ci vorrà un pò di buio per salvare tuo fratello");
                        } else if (getObject(GENERATOR_OBJ).isUsed() && !getObject(MEDICINE).isGiven()) {
                            out.println("Adesso che la prigione è buia potrai andare vicino alla porta d'isolamento e " +
                                    "usare quello scotch che hai preso precedentemente");
                        } else if (getObject(SCOTCH).isUsed() && !getObject(MEDICINE).isGiven()) {
                            out.println("Dovresti usare quella combinazione che hai ottenuto utilizzando lo scotch" +
                                    "nella stanza che precede l'isolamento, fai in fretta il tempo a tua disposizione" +
                                    "sta per scadere!!!!");
                        } else if (getObject(COMBINATION).isUsed() && !getObject(MEDICINE).isGiven()) {
                            out.println("Cosa ci fai qui?? Dovresti dare la medicina a tuo fratello!!!!");
                        } else if (getObject(MEDICINE).isGiven()) {
                            out.println("Il tuo piano è quasi terminato, vai con Genny Bello in infermeria passando dal" +
                                    "passaggio segreto! Buona fortuna, te ne servirà molta!!!!!!!!!");
                        } else {
                            out.println("Mi dispiace ma non ho suggerimenti da darti attualmente!!");
                        }
                    }

                } else {
                    if (object == null) {
                        out.println("Sei sicuro di non voler usare niente?");
                    } else if (!object.isUsable()) {
                        out.println("Mi dispiace ma questo oggetto non si può utilizzare");
                    } else if (!getCurrentRoom().isObjectUsableHere(object)) {
                        out.println("C’è tempo e luogo per ogni cosa, ma non ora.");
                    } else if (!getInventory().contains(object) && !getCurrentRoom().containsObject(object)) {
                        out.println("Io non vedo nessun oggetto di questo tipo qui!");
                    }
                }

                if (object != null && object.getId() == HACKSAW
                        && !getObject(TOOLS).isUsed() && getCurrentRoom().isObjectUsableHere(getObject(HACKSAW))) {
                    out.println("Il seghetto sembra molto arrugginito e non riesci a tagliare le sbarre " +
                            "della grata! In realtà la colpa non è totalmente del seghetto ma anche la tua " +
                            "poiché sei molto stanco e hai poca forza nelle braccia!");
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

            } else if (p.getVerb().getVerbType().equals(VerbType.PUSH)
                    || p.getVerb().getVerbType().equals(VerbType.PULL)) {
                if (object != null && object.isPushable() && getCurrentRoom().containsObject(object)) {
                    if (object.getId() == LADDER) {
                        // ladder case
                        if (getCurrentRoom().getId() == PASSAGE_SOUTH) {
                            getRoom(SECRET_PASSAGE).setObject(object);
                            getRoom(PASSAGE_SOUTH).removeObject(object);
                            out.println("La scala è stata spinta fino alla stanza a nord!");
                            increaseScore();
                        } else if (getCurrentRoom().getId() == SECRET_PASSAGE) {
                            getRoom(PASSAGE_NORTH).setObject(object);
                            getRoom(SECRET_PASSAGE).removeObject(object);
                            out.println("La scala è stata spinta fino alla stanza a nord e si è bloccata lì!");
                            increaseScore();
                        } else {
                            out.println("La scala è bloccata! Non esiste alcun modo per spostarla!");
                        }

                    } else if (object.getId() == SINK) {
                        if (getCurrentRoom().getId() == MAIN_CELL) {
                            if (object.isPush()) {
                                out.println("Il Lavandino è già stato spostato!");
                            } else {
                                object.setPush(true);
                                getRoom(SECRET_PASSAGE).setLocked(false);
                                out.println("Oissà!");
                                increaseScore();
                                increaseScore();
                            }
                        }

                    } else if (object.getId() == PICTURE) {
                        if (getCurrentRoom().getId() == INFIRMARY) {
                            // picture pushed
                            if (object.isPush()) {
                                out.println("Il quadro è già stato spostato!");
                            } else {
                                object.setPush(true);
                                getCurrentRoom().setObject(getObject(OLD_AIR_DUCT));
                                getObjectNotAssignedRoom().remove(getObject(OLD_AIR_DUCT));
                                out.println(getObject(OLD_AIR_DUCT).getDescription());
                            }
                        }
                    } else if (object.getId() == BUTTON_GENERATOR) {
                        if (getCurrentRoom().getId() == GENERATOR) {
                            // botton pushed
                            if (object.isPush()) {
                                out.println("Il pulsante è già stato premuto! Fai in fretta!!!");
                            } else {
                                object.setPush(true);
                                getObject(LIGHTS).setOn(false);
                                getObject(GENERATOR_OBJ).setUsable(true);
                                getRoom(DOOR_ISOLATION).setLocked(false);
                                out.println("Sembra che tutto il carcere sia nell’oscurità! È stata una bella mossa" +
                                        " la tua, peccato che i poliziotti prevedono queste bravate e hanno un generatore" +
                                        " di corrente ausiliario che si attiverà dopo un minuto dal blackout!");
                                increaseScore();
                                increaseScore();
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
                        getCurrentRoom().removeObject(object);
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
                    //On foot
                    if (getCurrentRoom().getObjects().stream()
                            .anyMatch(obj -> obj.isSitable() && obj.isSit())) {
                        getCurrentRoom().getObjects().stream()
                                .filter(obj -> obj.isSitable() && obj.isSit())
                                .findFirst()
                                .ifPresent(obj -> obj.setSit(false));
                        out.println("Oplà! Ti sei alzato!");
                    } else {
                        out.println("Sei così basso che non ti accorgi di stare già in piedi???");
                    }
                } else {
                    out.println("Non penso che questa cosa si possa fare ?!?");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.SIT_DOWN)) {
                if (object != null && object.isSitable() && getCurrentRoom().containsObject(object)) {

                    //Bed case
                    if (object.getId() == BED) {
                        if (getCurrentRoom().getObjects().stream()
                                .anyMatch(obj -> obj.isSitable() && !obj.isSit() && obj.getId() != WATER)) {
                            out.println("Buonanotte fiorellino!");
                        } else {
                            out.println("Sei talmente stanco che nemmeno ti accorgi che sei già seduto???");
                        }

                        //Water case
                    } else if (object.getId() == WATER) {
                        if (getCurrentRoom().getObjects().stream()
                                .anyMatch(obj -> obj.isSitable() && !obj.isSit() && obj.getId() == WATER)) {
                            out.println("Proprio ora devi farlo?");
                        } else {
                            out.println("Sei già seduto! Ricordati di tirare lo scarico!");
                        }
                    } else if (object.getId() == COT) {
                        if (getCurrentRoom().getObjects().stream()
                                .anyMatch(obj -> obj.isSitable() && !obj.isSit() && obj.getId() == COT)) {
                            out.println("Non sembra il momento di riposarti!");
                        } else {
                            out.println("Sei già sdraiato sul lettino!");
                        }
                    }
                    object.setSit(true);
                    if (getCurrentRoom().getObjects().stream()
                            .filter(obj -> obj.isSitable() && obj.isSit()).count() > 1) {
                        getCurrentRoom().getObjects().stream()
                                .filter(obj -> obj.isSitable() && obj.isSit() && obj.getId() != object.getId())
                                .findFirst()
                                .ifPresent(obj -> obj.setSit(false));
                    }
                } else if (object == null) {
                    out.println("Sedersi sul pavimento non mi sembra una buona idea!");
                } else if (!object.isSitable()) {
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

                if (getCurrentRoom().getId() == MAIN_CELL && getObject(SINK).isPush()) {
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

                //TODO NON FAR CREARE L' ACIDO LA PRIMA VOLTA IN INFERMERIA
            } else if (p.getVerb().getVerbType().equals(VerbType.MAKE)) {
                TokenObject substances = getObject(SUBSTANCES);
                if ((object != null
                        && object.isMixable()
                        && (getInventory().contains(object)
                        || getCurrentRoom().containsObject(object)))
                        || ((object != null && object.getId() == ACID))
                        && (getInventory().contains(substances)
                        || getCurrentRoom().containsObject(substances))) {

                    if (getCurrentRoom().getObjects().contains(object) && !(object.getId() == ACID)) {
                        getCurrentRoom().removeObject(object);
                        getInventory().add(getObject(ACID));
                        getObjectNotAssignedRoom().remove(getObject(ACID));
                        mixed = true;
                        increaseScore();
                    } else if (object.getId() != ACID && getInventory().getObjects().contains(object)) {
                        getInventory().remove(object);
                        getInventory().add(getObject(ACID));
                        getObjectNotAssignedRoom().remove(getObject(ACID));
                        mixed = true;
                        increaseScore();
                    } else if (getCurrentRoom().getObjects().contains(substances)
                            && object.getId() == ACID) {
                        getCurrentRoom().removeObject(substances);
                        getInventory().add(getObject(ACID));
                        getObjectNotAssignedRoom().remove(getObject(ACID));
                        mixed = true;
                        increaseScore();
                    } else if (getInventory().getObjects().contains(substances)
                            && object.getId() == ACID) {
                        getInventory().remove(substances);
                        getInventory().add(getObject(ACID));
                        getObjectNotAssignedRoom().remove(getObject(ACID));
                        mixed = true;
                        increaseScore();
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
                            increaseScore();
                            increaseScore();
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

            } else if (p.getVerb().getVerbType().equals(VerbType.PLAY)) {
                if ((object != null && object.isPlayable())
                        || (object == null && getCurrentRoom().getId() == BASKET_CAMP)) {
                    // ball case
                    if (getCurrentRoom().getId() == BASKET_CAMP) {
                        // ball play
                        out.println("Il tempo è denaro, non penso sia il momento adatto per mettersi a giocare.");
                    } else {
                        out.println("Vuoi giocare con il tuo amico immaginario??");
                    }
                } else if (object == null) {
                    out.println("Con cosa vuoi giocare esattamente???");
                } else if (!object.isTurnOnAble()) {
                    out.println("Non puoi giocare con questo oggetto!!!");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.PUT_IN)) {
                if ((object != null && object.isInsertable())) {
                    if (getCurrentRoom().getId() == DOOR_ISOLATION) {
                        getInventory().remove(object);
                        getRoom(ISOLATION).setLocked(false);
                        getObject(COMBINATION).setUsed(true);
                        out.println("La porta si apre! Puoi andare a est per entrare dentro l'isolamento oppure" +
                                " tornare indietro anche se hai poco tempo a disposizione!");
                        increaseScore();
                    }
                } else if (object == null) {
                    out.println("Con cosa vuoi inserire??");
                } else if (!object.isInsertable()) {
                    out.println("Ho paura di quello che vuoi fare!!!");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.TALK_TO)) {
                if ((object instanceof TokenPerson && ((TokenPerson) object).isSpeakable())) {
                    if (getCurrentRoom().getId() == CANTEEN) {
                        out.println("Si avvicina a te e sussurrando ti chiede se sei interessato a qualche oggetto che " +
                                "lui possiede. Ovviamente ogni oggetto ha un costo ma tu non possiedi alcun soldo, " +
                                "per averne uno quindi sarai costretto a trattare.");
                    } else if (getCurrentRoom().getId() == BROTHER_CELL) {
                        out.println("Tuo fratello ti chiede il motivo della tua presenza nel carcere e tu gli " +
                                "racconti tutto il piano segreto per la fuga cosicché tuo fratello non venga " +
                                "giustiziato ingiustamente. \n " +
                                "Tuo fratello sembra al quanto felice e ti ringrazia enormemente di aver creato tutto" +
                                " questo piano per salvarlo! \n" +
                                "Il piano consiste nel far andare tuo fratello in qualche modo in infermeria!");
                        getObject(MEDICINE).setGiveable(true);
                    }
                } else if (object == null) {
                    out.println("Vuoi parlare da solo???");
                } else {
                    out.println("Parlare con quell'oggetto non sembra essere la soluzione migliore!");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.ASK)) {
                if ((object != null && object.isAskable())) {
                    if (getCurrentRoom().getId() == CANTEEN) {
                        if (object.getId() == DRUG) {
                            out.println("Meglio continuare il piano di fuga da lucidi e fortunatamente non hai soldi " +
                                    "con te per acquistarla! Ti ricordo che il tuo piano è fuggire di prigione e non " +
                                    "rimanerci qualche anno di più!");
                        } else if (object.getId() == VIDEOGAME) {
                            out.println("Sarebbe molto bello se solo avessi 8 anni! Quando uscirai di prigione" +
                                    " avrai molto tempo per giocare anche a videogiochi migliori!");
                        } else if (object.getId() == HACKSAW && !object.isAsked()) {
                            out.println("Il detenuto ti dice che a tutti quelli che ha venduto un seghetto avevano " +
                                    "sempre un piano di fuga per evadere di prigione che però non sono mai andati a " +
                                    "buon fine essendo un carcere di massima sicurezza ( in effetti con un seghetto " +
                                    "in prigione non hai tante alternative). In cambio gli dovrai confessare tutto " +
                                    "il tuo piano di fuga e farlo fuggire insieme a te, cosa scegli???");
                            object.setAsked(true);
                        } else if (object.isAsked()) {
                            out.println("Hai già chiesto per quell'oggetto!!!");
                        }
                    }
                } else if (object == null) {
                    out.println("Cosa devi chiedere?");
                } else if (!object.isAskable()) {
                    out.println("Non sembra la soluzione giusta!");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.ACCEPT)) {
                if (getObject(HACKSAW).isAsked()
                        && getCurrentRoom().getId() == CANTEEN
                        && !getObject(HACKSAW).isAccept()) {
                    out.println("Gli racconti tutto il piano segreto di fuga, il detenuto capisce che questo è " +
                            "il miglior piano che abbia sentito fin ora e accetta subito dandoti il seghetto e " +
                            "si unisce a te. Gli dici di incontrarsi stanotte alle 21:00 di fronte alla tua cella!");
                    out.println("Ora puoi prendere il seghetto da Genny!");
                    getObject(HACKSAW).setPickupable(true);
                    getObject(HACKSAW).setAccept(true);
                } else if (!getObject(HACKSAW).isAsked()) {
                    out.println("Non puoi accettare una cosa che non hai chiesto!!!");
                } else if (getObject(HACKSAW).isAccept()) {
                    out.println("Ormai hai già accettato! Ci avresti potuto pensare prima!");
                } else if (getCurrentRoom().getId() != CANTEEN) {
                    out.println("Cosa vuoi accettare? Nulla???");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.DECLINE)) {
                if (getObject(HACKSAW).isAsked() && getCurrentRoom().getId() == CANTEEN
                        && !getObject(HACKSAW).isAccept() && !getInventory().contains(getObject(HACKSAW))) {
                    out.println("Decidi di rifiutare l’accordo, quando vuoi il detenuto sarà sempre pronto " +
                            "a ricontrattare!");
                    getObject(HACKSAW).setPickupable(false);
                    getObject(HACKSAW).setAsked(false);
                    getObject(HACKSAW).setAccept(false);
                } else if (!getObject(HACKSAW).isAsked()) {
                    out.println("Non puoi rifiutare una cosa che non hai chiesto!!!");
                } else if (getObject(HACKSAW).isAccept() || getInventory().contains(getObject(HACKSAW))) {
                    out.println("Ormai hai già accettato! Ci avresti potuto pensare prima!");
                } else if (getCurrentRoom().getId() != CANTEEN) {
                    out.println("Cosa vuoi rifiutare? Nulla???");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.FACE_UP)) {
                if (getCurrentRoom().getId() == FRONTBENCH && counterFaceUp == 0) {
                    out.println("Sai benissimo che in un carcere non si possono comprare panchine e ti avvicini " +
                            "nuovamente con l’intento di prendere l’oggetto. Il gruppetto ti blocca e il piu' grosso " +
                            "di loro ti tira un pugno contro il viso... Perdendo i sensi non ti ricordi piu' nulla e" +
                            " ti svegli in infermeria.");
                    setCurrentRoom(getRoom(INFIRMARY));
                    increaseScore();
                    move = true;
                    counterFaceUp++;
                } else if (getCurrentRoom().getId() == FRONTBENCH && !getObject(SCALPEL).isUsed() && getInventory().contains(getObject(SCALPEL)) && counterFaceUp == 1) {
                    increaseScore();
                    setObjectNotAssignedRoom(getObject(SCALPEL));
                    getInventory().remove(getObject(SCALPEL));
                    getObject(SCALPEL).setUsed(true);
                    getObject(SCREW).setPickupable(true);
                    out.println("Riesci subito a tirare fuori il bisturi dalla tasca, il gruppetto lo vede e " +
                            "capito il pericolo decide di lasciare stare (Mettere a rischio la vita per una panchina " +
                            "sarebbe veramente stupido) e vanno via con un'aria di vendetta.\nOra sei solo vicino" +
                            " alla panchina.");
                    getCurrentRoom().setDescription("Sei solo vicino alla panchina!");
                    getCurrentRoom().setLook("E' una grossa panchina in legno un po' malandata, " +
                            "ci sei solo tu nelle vicinanze. A terra noti la vite!");
                    counterFaceUp++;
                } else if (getCurrentRoom().getId() != FRONTBENCH || getObject(SCALPEL).isUsed() || counterFaceUp >= 2) {
                    out.println("Ehi John Cena, non puoi affrontare nessuno qui!!!");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.END)) {
                out.println("Non puoi usare quell'oggetto per uscire!");

            } else if (p.getVerb().getVerbType().equals(VerbType.DESTROY)) {
                if (object != null
                        && object.getId() == DESTROYABLE_GRATE
                        && getCurrentRoom().getId() == AIR_DUCT_NORTH
                        && getInventory().contains(getObject(HACKSAW))
                        && getObject(TOOLS).isUsed()
                        && !getObject(HACKSAW).isUsed()) {
                    getRoom(AIR_DUCT_INFIRMARY).setLocked(false);
                    getInventory().remove(getObject(HACKSAW));
                    getRoom(AIR_DUCT_NORTH).removeObject(object);
                    out.println("Oh no! Il seghetto si è rotto e adesso ci sono pezzi di sega dappertutto, per " +
                            "fortuna sei riuscito a rompere la grata");
                    out.println("Dopo esserti allenato duramente riesci a tagliare le sbarre con il seghetto, " +
                            "puoi proseguire nel condotto e capisci che quel condotto porta fino all’infermeria.");
                    increaseScore();
                    increaseScore();
                } else if (object == null) {
                    out.println("Cosa vuoi rompere???");
                } else if (object.getId() != DESTROYABLE_GRATE) {
                    out.println("Non puoi distruggere questo oggetto!");
                } else if (getCurrentRoom().getId() != AIR_DUCT_NORTH) {
                    out.println("Non puoi distruggere niente qui!");
                } else if (!getInventory().contains(getObject(HACKSAW))) {
                    out.println("Come puoi rompere la grata? Non hai nessun oggetto utile!");
                } else if (!getObject(TOOLS).isUsed()) {
                    out.println("Il seghetto sembra molto arrugginito e non riesci a tagliare le sbarre della grata! " +
                            "In realtà la colpa non è totalmente del seghetto ma anche la tua poichè sei molto stanco " +
                            "e hai poca forza nelle braccia!");
                } else if (!getObject(MEDICINE).isGiven()) {
                    out.println("Dopo esserti allenato duramente riesci a tagliare le sbarre con il seghetto, " +
                            "puoi proseguire nel condotto e capisci che quel condotto porta fino all’infermeria. " +
                            "Avrebbe più senso proseguire solo se la tua squadra è al completo… non ti sembri manchi " +
                            "la persona più importante???");
                } else if (!getObject(HACKSAW).isUsed()) {
                    out.println("Hai già usato quell'oggetto! Non puoi più rompere nulla!");
                }

            } else if (p.getVerb().getVerbType().equals(VerbType.GIVE)) {
                if (object != null && object.isGiveable() && getCurrentRoom().getId() == BROTHER_CELL
                        && object.getId() == MEDICINE) {
                    getInventory().remove(object);
                    out.println("Sai benissimo che tuo fratello ha una forte allergia alle ortiche" +
                            " e che non potrebbe prendere quel farmaco. Tu decidi di darlo ugualmente " +
                            "in modo che il tuo piano venga attuato!");
                    object.setGiven(true);
                    out.println("Appena dato il farmaco decidi di fuggire fuori dalla cella isolamento prima" +
                            " che le luci si accendano e le guardie ti scoprano!!!");
                    setCurrentRoom(getRoom(OUT_ISOLATION));
                    move = true;
                    out.println("Sono le 20:55 hai esattamente 5 minuti per tornare alla tua cella" +
                            " e completare il tuo piano! Speriamo che abbiano portato tuo fratello in infermeria!\n");
                    getCurrentRoom().setLook("Sono le 20:55 hai esattamente 5 minuti per tornare alla tua cella" +
                            "e completare il tuo piano! Speriamo che abbiano portato tuo fratello in infermeria!");

                    getRoom(INFIRMARY).setLocked(false);

                    //Lock the other rooms to lead the user to the end of the game
                    getRoom(DOOR_ISOLATION).setLocked(true);
                    getRoom(CANTEEN).setLocked(true);
                    getRoom(GYM).setLocked(true);
                    getRoom(GARDEN).setLocked(true);

                    //Set the description of the principal cell
                    getRoom(MAIN_CELL).setDescription("Sei arrivato alla tua cella , ad aspettarti puntuale " +
                            "c’è il tuo amichetto Genny. È ora di attuare il piano!");
                    getRoom(MAIN_CELL).setLook("Non perdere ulteriore tempo, bisogna attuare il piano e scappare via da qui!");
                    getRoom(AIR_DUCT_INFIRMARY).setLook("Dal condotto d'aria riesci a vedere tuo fratello " +
                            "nell'infermeria che ti aspetta!");

                    increaseScore();
                    increaseScore();
                    increaseScore();
                } else if (object == null) {
                    out.println("Cosa vuoi dare di preciso?");
                } else if (object instanceof TokenPerson) {
                    out.println("Una persona non è un oggetto che si può regalare!!!");
                } else {
                    out.println("Non puoi dare quest'oggetto a nessuno imbecille!");
                }
            }

            if (move) {
                out.println(getCurrentRoom().getName());
                out.println("================================================");
                out.println(getCurrentRoom().getDescription());
            }

        } catch (NotAccessibleRoomException e) {
            if (getCurrentRoom().getId() == BROTHER_CELL && p.getVerb().getVerbType().equals(VerbType.EAST)
                    || getCurrentRoom().getId() == OTHER_CELL && p.getVerb().getVerbType().equals(VerbType.WEST)) {
                out.println("Non hai ancora il potere di allargare le sbarre o oltrepassarle!!");
            } else {
                out.println("Da quella parte non si può andare c'è un muro! Non hai ancora acquisito i poteri" +
                        " per oltrepassare i muri...");
            }
        } catch (LockedRoomException e) {
            if (getObject(MEDICINE).isGiven()) {
                out.println("Non perdere ulteriore tempo, bisogna completare il piano!");
            } else if (getCurrentRoom().getEast() != null && getCurrentRoom().getId() == AIR_DUCT_INFIRMARY
                    && getCurrentRoom().getEast().getId() == INFIRMARY) {
                out.println("Avrebbe più senso proseguire solo se la tua squadra è al completo… " +
                        "non ti sembri manchi la persona più importante???");
            } else {
                out.println("Questa stanza è bloccata, dovrai fare qualcosa per sbloccarla!!");
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
                out.println("Lo vedi solo nei tuoi sogni!");
            } else {
                out.println("Hai fumato qualcosa per caso?!");
            }
        } catch (Exception e) {
            out.println("Qualcosa è andato storto....");
        }
    }
}
