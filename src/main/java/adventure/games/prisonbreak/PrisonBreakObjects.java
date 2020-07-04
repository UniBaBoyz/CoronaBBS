package adventure.games.prisonbreak;

import adventure.exceptions.inventoryException.InventoryFullException;
import adventure.games.GameObjects;
import adventure.type.Room;
import adventure.type.TokenAdjective;
import adventure.type.TokenObject;
import adventure.type.TokenObjectContainer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static adventure.games.prisonbreak.ObjectType.*;
import static adventure.games.prisonbreak.RoomType.*;

public class PrisonBreakObjects implements GameObjects {

    private final PrisonBreakGame game;

    public PrisonBreakObjects(PrisonBreakGame game) {
        this.game = game;
        initObjects();
    }

    public void initObjects() {
        initPerson();
        initObjectsCell17(this.game.getRoom(MAIN_CELL));
        initObjectsGym(this.game.getRoom(GYM));
        initObjectsInfirmary(this.game.getRoom(INFIRMARY));
        initObjectsFrontBench(this.game.getRoom(FRONTBENCH));
        initObjectsGenerator(this.game.getRoom(GENERATOR));
        initObjectsBasket(this.game.getRoom(BASKET_CAMP));
        initDoorGarden(this.game.getRoom(GARDEN), this.game.getRoom(LOBBY));
        initObjectsAirDuct(this.game.getRoom(AIR_DUCT_NORTH),
                this.game.getRoom(AIR_DUCT_EAST),
                this.game.getRoom(AIR_DUCT_WEST));
        initObjectsPassageSouth(this.game.getRoom(PASSAGE_SOUTH));
        initObjectsCorridor(this.game.getRoom(CORRIDOR_NORTH),
                this.game.getRoom(CORRIDOR),
                this.game.getRoom(CORRIDOR_SOUTH));
        initObjectsNotAssignedRoom();
    }

    private void initPerson() {

        TokenPerson gennyBello = new TokenPerson(GENNY_BELLO, "Genny Bello",
                new HashSet<>(Collections.singletonList("Genny")),
                "E' un detenuto come te che smista oggetti illegali nella prigione in cambio di favori",
                new HashSet<>(
                        Collections.singletonList(
                                new TokenAdjective(new HashSet<>(Collections.singletonList("Bello"))))),
                3);
        gennyBello.setSpeakable(true);
        this.game.getRoom(CANTEEN).setObject(gennyBello);

        TokenObject hacksaw = new TokenObject(HACKSAW, "Seghetto",
                new HashSet<>(Arrays.asList("Seghetto", "Sega", "Taglierino")),
                "E’ un seghetto molto affilato, potresti riuscire a rompere qualcosa.",
                new HashSet<>(Collections.singletonList(new TokenAdjective(new HashSet<>(Arrays.asList("Rotto",
                        "Distrutto", "Devastato", "Spaccato"))))));
        hacksaw.setUsable(true);
        hacksaw.setAskable(true);
        game.getRoom(AIR_DUCT_NORTH).setObjectsUsableHere(hacksaw);

        try {
            gennyBello.getInventory().add(hacksaw);
        } catch (
                InventoryFullException ignored) {
        }

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

        TokenPerson brother = new TokenPerson(BROTHER, "Tuo fratello Lincoln",
                new HashSet<>(Arrays.asList("Lincoln", "fratello")),
                "E' tuo fratello! Non ho nient'altro da dirti che già non sai. " +
                        "Dovresti già sapere tutto su di lui!",
                new HashSet<>(
                        Collections.singletonList(
                                new TokenAdjective(new HashSet<>(Collections.singletonList("mio"))))),
                0);
        brother.setSpeakable(true);
        this.game.getRoom(BROTHER_CELL).setObject(brother);
    }

    private void initObjectsCell17(Room cell17) {

        TokenObject food = new TokenObject(FOOD, "Cibo",
                new HashSet<>(Arrays.asList("Cibo", "Pranzo", "Cena", "Piatto", "Tavolo")),
                "C'è solo il tuo pranzo che emana un odore non buonissimo, puoi mangiarlo anche se non" +
                        " servirà a nulla.");
        food.setEatable(true);
        food.setPickupable(true);
        cell17.setObject(food);

        TokenObject sink = new TokenObject(SINK, "Lavandino",
                new HashSet<>(Arrays.asList("Lavandino", "Lavello", "Lavabo")),
                "E' un piccolo lavandino fissato al muro con delle viti arruginite... Ha un aspetto " +
                        "malandato!");
        sink.setUsable(true);
        cell17.setObject(sink);
        cell17.setObjectsUsableHere(sink);

        TokenObject bed = new TokenObject(BED, "Letto",
                new HashSet<>(Arrays.asList("Letto", "Lettino", "Brandina", "Lettuccio")),
                "E' presente un letto a castello molto scomodo e pieno di polvere!");
        bed.setSitable(true);
        cell17.setObject(bed);

        TokenObject table = new TokenObject(TABLE, "Tavolo",
                new HashSet<>(Arrays.asList("Tavolo", "Tavolino", "Scrivania")),
                "E' un semplice tavolo in legno, molto piccolo e molto sporco!");
        cell17.setObject(table);

        TokenObject windowCell = new TokenObject(WINDOW_CELL, "Finestra",
                new HashSet<>(Arrays.asList("Finestra", "Finestrella")),
                "E' una piccola finestra sbarrata dalla quale puoi osservare il cortile della prigione! " +
                        "Bel panorama!!!");
        cell17.setObject(windowCell);

        TokenObject water = new TokenObject(WATER, "Water",
                new HashSet<>(Arrays.asList("Water", "Cesso", "Gabinetto", "Tazza", "Wc")),
                "Un water qualunque, senza nessun particolare, non penso sia bello osservarlo");
        water.setSitable(true);
        cell17.setObject(water);
    }

    private void initObjectsGym(Room gym) {
        TokenObject tools = new TokenObject(TOOLS, "Attrezzi",
                new HashSet<>(Arrays.asList("Attrezzi", "Manubri", "Pesi")),
                "Sono degli attrezzi da palestra, ottimi per allenarsi e aumentare la forza!");
        tools.setUsable(true);
        gym.setObject(tools);
        gym.setObjectsUsableHere(tools);

    }

    private void initObjectsInfirmary(Room infirmary) {
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

        TokenObject substances = new TokenObject(SUBSTANCES, "Sostanze chimiche",
                new HashSet<>(Arrays.asList("Sostanze", "Ingredienti", "Oggetti")),
                "Sul tavolo puoi vedere alcuni strumenti di lavoro e alcune sostanze come: Cloruro " +
                        "di sodio, acido solforico e altre sostanze di cui non riesco nemmeno a leggere il nome!");
        substances.setPickupable(true);
        substances.setMixable(true);
        infirmary.setObject(substances);

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

        TokenObject scalpel = new TokenObject(SCALPEL, "Bisturi", new HashSet<>(Arrays.asList("Bisturi", "Lama")),
                "Un semplice bisturi per effettuare operazioni mediche!");
        scalpel.setPickupable(true);
        scalpel.setUsable(true);
        infirmary.setObject(scalpel);
        game.getRoom(FRONTBENCH).setObjectsUsableHere(scalpel);

    }

    private void initObjectsFrontBench(Room frontBench) {
        TokenObject screw = new TokenObject(SCREW, "Vite", new HashSet<>(Arrays.asList("Vite", "Chiodo")),
                "E' una semplice vite con inciso il numero di serie: 11121147.");
        screw.setUsable(true);
        frontBench.setObject(screw);
        game.getRoom(MAIN_CELL).setObjectsUsableHere(screw);
    }

    private void initObjectsGenerator(Room generator) {
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
    }

    private void initObjectsBasket(Room basket) {
        TokenObject basketObject = new TokenObject(BASKET_OBJECT, "Canestri", new HashSet<>(
                Arrays.asList("Canestri", "Canestro", "Basket")),
                "Ottimi per giocare a basket e perdere tempo!!!");
        basketObject.setPlayable(true);
        basket.setObject(basketObject);

        TokenObject ball = new TokenObject(BALL, "Pallone",
                new HashSet<>(Arrays.asList("Palla", "Pallone", "Basketball")),
                "Un semplice pallone da basket.");
        ball.setPickupable(true);
        ball.setUsable(true);
        ball.setPlayable(true);
        basket.setObject(ball);
        game.getRooms().forEach(room -> room.setObjectsUsableHere(ball));
    }

    private void initDoorGarden(Room garden, Room lobby) {
        TokenObject door = new TokenObject(DOOR_GARDEN, "Porta d'ingresso",
                new HashSet<>(Arrays.asList("Porta", "Portone", "Ingresso", "Soglia")),
                "E' una grande porta che separa giardino e atrio. E' sempre aperta e non puoi chiuderla!");
        garden.setObject(door);
        lobby.setObject(door);
    }

    private void initObjectsAirDuct(Room airDuctNorth, Room airDuctEast, Room airDuctWest) {
        TokenObject gratePassage = new TokenObject(GRATE_PASSAGE, "Grata", new HashSet<>(Arrays.asList
                ("Grata", "Inferriata")),
                "La grata è sulla cella del detenuto che è controllata da un poliziotto. Non mi sembra il" +
                        " caso di intrufolarsi rischieresti di mandare a rotoli il piano!!!");
        airDuctEast.setObject(gratePassage);

        TokenObject destroyableGrate = new TokenObject(DESTROYABLE_GRATE, "Grata", new HashSet<>(Arrays.asList(
                "Grata", "Inferriata")),
                "La grossa grata blocca il passaggio, ci sarà qualche modo per romperla???");
        airDuctNorth.setObject(destroyableGrate);

        TokenObject scotch = new TokenObject(SCOTCH, "Scotch", new HashSet<>(Arrays.asList("Scotch", "Nastro")),
                "E' un semplice scotch, dimenticato li forse da qualche operaio!");
        airDuctWest.setObject(scotch);
        scotch.setUsable(true);
        scotch.setPickupable(true);
        game.getRoom(DOOR_ISOLATION).setObjectsUsableHere(scotch);
    }

    private void initObjectsPassageSouth(Room passageSouth) {
        TokenObject grate = new TokenObject(GRATE, "Grata", new HashSet<>(Arrays.asList("Grata", "Grate")),
                "Puoi notare solo il piano superiore, ma non puoi fare nient' altro");
        passageSouth.setObject(grate);

        TokenObject ladder = new TokenObject(LADDER, "Scala", new HashSet<>(Arrays.asList("Scala", "Scaletta")),
                "E' solo una scala in legno, sembra molto leggera e facile da spostare. " +
                        "La scala sembra non portare da nessuna parte!");
        ladder.setPushable(true);
        passageSouth.setObject(ladder);
    }

    private void initObjectsCorridor(Room corridorNorth, Room corridor, Room corridorSouth) {
        TokenObject railing = new TokenObject(RAILING, "Ringhiera",
                new HashSet<>(Arrays.asList("Ringhiera", "Scorrimano")),
                "Sei troppo giovane per camminare appoggiato ad una ringhiera e troppo giovane " +
                        "per buttarti, ti prego non farlo!!!");
        corridorNorth.setObject(railing);
        corridor.setObject(railing);
        corridorSouth.setObject(railing);
    }

    private void initObjectsNotAssignedRoom() {
        TokenObject medicine = new TokenObject(MEDICINE, "Farmaco",
                new HashSet<>(Arrays.asList("Farmaco", "Medicina", "Compresse", "Sciroppo")),
                "E' un medicinale per alleviare i dolori.");
        game.setObjectNotAssignedRoom(medicine);

        TokenObject acid = new TokenObject(ACID, "Acido", new HashSet<>(Collections.singletonList("Acido")),
                "Leggendo la ricetta alla lavagna capisci come creare l’acido, mischi le sostanze " +
                        "tutte insieme utilizzando le giuste dosi in modo da non sbagliare! Sei riuscito a" +
                        " creare l’acido!");
        acid.setPickupable(true);
        acid.setUsable(true);
        acid.setMixable(true);
        game.setObjectNotAssignedRoom(acid);

        TokenObject combination = new TokenObject(COMBINATION, "Combinazione", new HashSet<>(Arrays.asList(
                "Combinazione", "Password", "Pin")),
                "Questa e' la combinazione che ho ricavato utilizzando lo scotch sul tastierino numerico " +
                        "della stanza");
        combination.setUsable(true);
        combination.setInsertable(true);
        game.setObjectNotAssignedRoom(combination);
        game.getRoom(DOOR_ISOLATION).setObjectsUsableHere(combination);

        TokenObject oldAirDuct = new TokenObject(OLD_AIR_DUCT, "Condotto d'aria vecchio", new HashSet<>(
                Arrays.asList("Condotto d'aria vecchio", "Condotto d'aria", "Condotto", "Indotto")),
                "Dietro al quadro vedi un condotto d’aria dall’aspetto vecchiotto, " +
                        "sembra quasi che non serva più perché ne hanno costruito un altro… " +
                        "Perché nasconderlo?",
                new HashSet<>(Collections.singletonList(new TokenAdjective(new HashSet<>(Arrays.asList("Vecchio", "Anziano",
                        "Decrepito", "Antico", "Vetusto", "Antiquato", "Disusato", "Obsoleto", "Consumato"))))));
        game.setObjectNotAssignedRoom(oldAirDuct);

        TokenObject roomObject = new TokenObject(ROOM_OBJ, "Stanza", new HashSet<>(
                Arrays.asList("Stanza", "Camera", "Ambiente", "Locale")));
        game.setObjectNotAssignedRoom(roomObject);

        TokenObject scoreObject = new TokenObject(SCORE_OBJ, "Punteggio", new HashSet<>(
                Arrays.asList("Punteggio", "Punti", "Score")));
        game.setObjectNotAssignedRoom(scoreObject);

        TokenObject lights = new TokenObject(LIGHTS, "Luci", new HashSet<>(Arrays.asList
                ("Luci", "Luce", "Lampada", "Lampadario")),
                "Sono semplici luci della prigione!");
        lights.setTurnOnAble(true);
        game.setObjectNotAssignedRoom(lights);
    }
}