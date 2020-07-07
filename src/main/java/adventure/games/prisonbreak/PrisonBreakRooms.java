package adventure.games.prisonbreak;

import adventure.games.GameDescription;
import adventure.games.RoomsInterface;
import adventure.type.Room;

import static adventure.games.prisonbreak.RoomType.*;

/**
 * @author Corona-Extra
 */
public class PrisonBreakRooms implements RoomsInterface {

    @Override
    public void initRooms(GameDescription game) {
        Room mainCell17 = new Room(MAIN_CELL, "Cella n.ro 17", "Ti trovi nella tua cella 17," +
                " al momento sei da solo" + " visto che sei l’ultimo arrivato.");
        mainCell17.setLook("La cella e' poco accogliente... l’unica via di uscita si trova a est," +
                " al momento aperta visto che e' l’ora d’aria e tutti i detenuti devono raggiungere il giardino!");

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
        lobby.setLook("Il luogo e' affollato di guardie che controllano la situazione. Puoi salire tramite " +
                "la scalinata al piano superiore oppure andare nel giardino a est. L’atrio si estende ancora " +
                "verso sud.");

        Room lobbySouth = new Room(LOBBY_SOUTH, "Atrio", "Ti trovi a sud del grosso atrio di ingresso." +
                " Puoi notare che l'atrio prosegue sia a nord che a sud!");
        lobbySouth.setLook("Puoi notare a ovest le celle chiuse dei detenuti e a est la palestra.");

        Room lobbyEnd = new Room(END_LOBBY, "Atrio", "Ti trovi alla fine del grosso atrio, a sud vedi " +
                " tante guardie, sembra una zona particolarmente protetta!!! Noti a est la sala per la mensa e a " +
                " ovest le celle chiuse dei detenuti!");
        lobbyEnd.setLook("Non c'è nulla di particolare! Puoi ritornare indietro verso nord!");

        Room garden = new Room(GARDEN, "Giardino", "Sei in un ampio giardino verde illuminato " +
                "dal sole. Alla tua sinistra il grosso atrio.");
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
                "di detenuti che ti guardano con aria sospetta. C'è qualcosa a terra in lontananza...guarda meglio.");
        bench.setLook("Non noti nulla di particolare in loro e nelle panchine, tranne in una panchina, dove a terra " +
                "puoi notare un oggetto di metallo simile ad una vite. Vai a nord per tornare indietro!");

        Room infirmary = new Room(INFIRMARY, "Infermeria", "E' una classica infermeria e ti trovi" +
                " sdraiato sul tuo letto. Decidi di alzarti senza far rumore!");
        infirmary.setLook("Sembra non esserci nessuno oltre a te nella stanza, riesci solo ad udire delle " +
                "voci nel corridoio. A nord vedi una finestra che si affaccia sul cortile.");
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
                " che lui possiede.");

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

        Room isolationCorridorEastEastEast = new Room(ISOLATION_CORRIDOR_EAST_EAST_EAST,
                "Corridoio est isolamento",
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

        Room frontBench = new Room(FRONTBENCH, "Di fronte alle panchine",
                "Ti avvicini alla panchina, ma vieni subito fermato da un gruppo di neri che con aria" +
                        " minacciosa ti chiedono di allontanarti perche' la panchina e' la loro. Cosa scegli di fare?");
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
        createLinks(mainCell17, null, null, corridorSouth, passage);
        createLinks(corridorSouth, corridor, null, null, mainCell17);
        createLinks(corridor, corridorNorth, corridorSouth, null, cell18);
        createLinks(cell18, corridorNorth, corridorSouth, corridor, null);
        createLinks(corridorNorth, ladders, corridorSouth, null, cell19);
        createLinks(cell19, ladders, corridorSouth, corridorNorth, null);
        createLinks(ladders, null, corridorNorth, lobby, null);
        createLinks(lobby, null, lobbySouth, garden, ladders);
        createLinks(lobbySouth, lobby, lobbyEnd, gym, cell20);
        createLinks(lobbyEnd, lobbySouth, outIsolation, canteen, cell21);
        createLinks(cell20, lobby, null, lobbySouth, null);
        createLinks(garden, basket, bench, wall, lobby);
        createLinks(basket, null, garden, null, null);
        createLinks(wall, null, null, null, garden);
        createLinks(bench, garden, frontBench, null, null);
        createLinks(frontBench, bench, null, null, null);
        createLinks(gym, null, null, null, lobbySouth);
        createLinks(cell21, lobbySouth, outIsolation, lobbyEnd, null);
        createLinks(canteen, null, null, null, lobbyEnd);
        createLinks(outIsolation, lobbyEnd, null, doorIsolation, cell22);
        createLinks(cell22, lobbyEnd, null, outIsolation, null);
        createLinks(doorIsolation, null, null, isolation, outIsolation);
        createLinks(isolation, isolationCorridorNorth, isolationCorridorSouth, isolationCorridorEast, doorIsolation);
        createLinks(isolationCorridorEast, null, null, isolationCorridorEastEast, isolation);
        createLinks(isolationCorridorEastEast, null, null, isolationCorridorEastEastEast, isolationCorridorEast);
        createLinks(isolationCorridorEastEastEast, null, null, null, isolationCorridorEastEast);
        createLinks(isolationCorridorNorth, isolationCorridorNorthNorth, isolation, null, null);
        createLinks(isolationCorridorNorthNorth, isolationCorridorNorthNorthNorth, isolationCorridorNorth, null, null);
        createLinks(isolationCorridorNorthNorthNorth, null, isolationCorridorNorthNorth, null, null);
        createLinks(isolationCorridorSouth, isolation, isolationCorridorSouthSouth, null, null);
        createLinks(isolationCorridorSouthSouth, isolationCorridorSouth, isolationCorridorSouthSouthSouth, null, null);
        createLinks(isolationCorridorSouthSouthSouth, isolationCorridorSouthSouth, null, brotherCell, null);
        createLinks(brotherCell, isolationCorridorSouthSouth, null, null, isolationCorridorSouthSouthSouth);
        createLinks(passage, passageNorth, passageSouth, mainCell17, null);
        createLinks(passageSouth, passage, generator, null, null);
        createLinks(generator, passageSouth, null, null, null);
        createLinks(passageNorth, onLadder, passage, null, null);
        createLinks(onLadder, airDuct, passageNorth, null, null);
        createLinks(airDuct, airDuctNorth, onLadder, airDuctEast, airDuctWest);
        createLinks(airDuctEast, null, grateCell, null, airDuct);
        createLinks(airDuctWest, null, null, airDuct, null);
        createLinks(airDuctNorth, null, airDuct, airDuctInfirmary, null);
        createLinks(grateCell, airDuctEast, null, null, null);
        createLinks(airDuctInfirmary, null, null, infirmary, airDuctNorth);
        createLinks(infirmary, endGame, null, null, airDuctInfirmary);
        createLinks(endGame, null, infirmary, null, null);

        //Create graph
        game.createRooms(mainCell17);
    }

    private void createLinks(Room root, Room north, Room south, Room east, Room west) {
        root.setNorth(north);
        root.setSouth(south);
        root.setEast(east);
        root.setWest(west);
    }
}
