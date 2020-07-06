package adventure.games.prisonbreak.movement;

import adventure.exceptions.inventoryException.InventoryEmptyException;
import adventure.exceptions.inventoryException.InventoryFullException;
import adventure.exceptions.inventoryException.ObjectNotFoundInInventoryException;
import adventure.exceptions.objectsException.ObjectNotFoundInRoomException;
import adventure.games.prisonbreak.TokenPerson;
import adventure.type.TokenObject;
import adventure.type.TokenObjectContainer;

import static adventure.games.prisonbreak.ObjectType.*;
import static adventure.games.prisonbreak.RoomType.*;

class BasicVerbs {

    void inventory() throws InventoryEmptyException {
        if (!ControllerMove.getInstance().getGame().getInventory().isEmpty()) {
            ControllerMove.getInstance().getResponse().append("Nel tuo inventario ci sono:\n");
            for (TokenObject o : ControllerMove.getInstance().getGame().getInventory().getObjects()) {
                ControllerMove.getInstance().getResponse()
                        .append(o.getName())
                        .append(": ")
                        .append(o.getDescription())
                        .append("\n");
            }
        }
    }

    void lookAt() {
        if (ControllerMove.getInstance().getObject() != null
                && (ControllerMove.getInstance().getGame().getInventory().contains(
                ControllerMove.getInstance().getObject())
                || ControllerMove.getInstance().getGame().getCurrentRoom().getObjects().contains(
                ControllerMove.getInstance().getObject())
                || ControllerMove.getInstance().getGame().getCurrentRoom().getObjects().stream()
                .anyMatch(obj -> obj instanceof TokenPerson
                        && ((TokenPerson) obj).getInventory().contains(ControllerMove.getInstance().getObject())))) {
            ControllerMove.getInstance().getResponse()
                    .append(ControllerMove.getInstance().getObject().getDescription())
                    .append("\n");

        } else if (ControllerMove.getInstance().getGame().getCurrentRoom().getLook() != null
                && (ControllerMove.getInstance().getObject() == null
                || ControllerMove.getInstance().getObject().getId() == ROOM_OBJ)) {
            ControllerMove.getInstance().getResponse()
                    .append(ControllerMove.getInstance().getGame().getCurrentRoom().getLook())
                    .append("\n");

        } else if (ControllerMove.getInstance().getObject() != null
                && ControllerMove.getInstance().getObject().getId() == SCORE_OBJ) {
            //TODO cambiare frase in base allo score
            ControllerMove.getInstance().getResponse()
                    .append("Non male, attualmente il tuo punteggio è ")
                    .append(ControllerMove.getInstance().getGame().getScore())
                    .append("\n");
        } else {
            ControllerMove.getInstance().getResponse().append("Non c'è niente di interessante qui.\n");
        }
    }

    void pickUp() throws InventoryFullException, ObjectNotFoundInRoomException {
        if (ControllerMove.getInstance().getObject() != null
                && ControllerMove.getInstance().getObject().getId() == HACKSAW
                && ControllerMove.getInstance().getObject().isAccept()
                && ((TokenPerson) ControllerMove.getInstance().getGame().getObject(GENNY_BELLO))
                .getInventory().contains(ControllerMove.getInstance().getObject())) {

            //There is the need of the remove's operation from the room of the TokenPerson's objects
            ControllerMove.getInstance().getGame().getCurrentRoom()
                    .removeObject(ControllerMove.getInstance().getGame().getObject(HACKSAW));

            ControllerMove.getInstance().getGame().getInventory().add(ControllerMove.getInstance().getObject());

            ControllerMove.getInstance().getResponse()
                    .append("Hai preso ")
                    .append(ControllerMove.getInstance().getObject().getName())
                    .append("!\n");

        } else if (ControllerMove.getInstance().getObject() != null
                && ControllerMove.getInstance().getObject().getId() == SCALPEL
                && ControllerMove.getInstance().getGame().getCurrentRoom().getId() == INFIRMARY
                && !ControllerMove.getInstance().getObject().isTaken()) {

            ControllerMove.getInstance().getGame().getCurrentRoom()
                    .removeObject(ControllerMove.getInstance().getObject());

            ControllerMove.getInstance().getGame().getInventory()
                    .add(ControllerMove.getInstance().getObject());

            ControllerMove.getInstance().getObject().setTaken(true);
            ControllerMove.getInstance().getGame().increaseScore();

            ControllerMove.getInstance().getResponse()
                    .append("Hai preso ")
                    .append(ControllerMove.getInstance().getObject().getName())
                    .append("!\n");

            ControllerMove.getInstance().getResponse()
                    .append("Fai in fretta perché improvvisamente senti i passi dell’infermiera avvicinandosi " +
                            "alla porta, riesci a prendere il bisturi con te e l’infermiera ti dice che sei guarito" +
                            " e puoi ritornare nella cella visto che l’ora d’aria è finita.\n\n");
            ControllerMove.getInstance().getGame()
                    .setCurrentRoom(ControllerMove.getInstance().getGame().getRoom(MAIN_CELL));

            ControllerMove.getInstance().getGame().getInventory()
                    .add(ControllerMove.getInstance().getGame().getObject(MEDICINE));

            ControllerMove.getInstance().getResponse()
                    .append("Zzzzzz.....\n\n");

            ControllerMove.getInstance().getResponse()
                    .append("Caspita gli antidolorifici ti hanno fatto dormire molto e ti risvegli nella tua " +
                            "cella privo di qualsiasi dolore! Prima di andare via l’infermiera ti ha dato qualche " +
                            "medicinale tra cui un medicinale all’ortica. Guarda nel tuo inventario!\n\n");
            ControllerMove.getInstance().setMove(true);
            ;

        } else if (ControllerMove.getInstance().getObject() != null
                && ControllerMove.getInstance().getObject().isPickupable()
                && ControllerMove.getInstance().getGame().getCurrentRoom()
                .containsObject(ControllerMove.getInstance().getObject())) {

            if ((ControllerMove.getInstance().getObject().getId() == SCOTCH
                    || ControllerMove.getInstance().getObject().getId() == SCREW)
                    && !ControllerMove.getInstance().getObject().isTaken()) {
                ControllerMove.getInstance().getGame().increaseScore();
                ControllerMove.getInstance().getObject().setTaken(true);
            }
            ControllerMove.getInstance().getGame().getCurrentRoom()
                    .removeObject(ControllerMove.getInstance().getObject());

            ControllerMove.getInstance().getGame().getInventory()
                    .add(ControllerMove.getInstance().getObject());

            ControllerMove.getInstance().getResponse()
                    .append("Hai preso ")
                    .append(ControllerMove.getInstance().getObject().getName()).append("!\n");

        } else if (ControllerMove.getInstance().getObject() == null) {
            ControllerMove.getInstance().getResponse()
                    .append("Cosa vorresti prendere di preciso?\n");
        } else if (ControllerMove.getInstance().getObject().getId() == SCREW
                && !ControllerMove.getInstance().getObject().isPickupable()) {

            ControllerMove.getInstance().getResponse()
                    .append("Non puoi prendere quella vite se prima non affronti il gruppetto dei detenuti!\n");

        } else if (!ControllerMove.getInstance().getGame().getCurrentRoom()
                .containsObject(ControllerMove.getInstance().getObject())) {

            throw new ObjectNotFoundInRoomException();

        } else if (!ControllerMove.getInstance().getObject().isPickupable()) {

            ControllerMove.getInstance().getResponse()
                    .append("Non e' certo un oggetto che si può prendere imbecille!\n");

        } else if (ControllerMove.getInstance().getGame().getInventory()
                .contains(ControllerMove.getInstance().getObject())) {

            ControllerMove.getInstance().getResponse().append("Guarda bene nella tua borsa, cretino!\n");
        }
    }

    void remove() throws ObjectNotFoundInInventoryException {
        if (ControllerMove.getInstance().getObject() != null
                && ControllerMove.getInstance().getGame().getInventory()
                .contains(ControllerMove.getInstance().getObject())) {

            ControllerMove.getInstance().getGame().getCurrentRoom()
                    .setObject(ControllerMove.getInstance().getObject());

            ControllerMove.getInstance().getGame().getInventory()
                    .remove(ControllerMove.getInstance().getObject());

            ControllerMove.getInstance().getResponse()
                    .append("Hai lasciato a terra ")
                    .append(ControllerMove.getInstance().getObject().getName()).append("!\n");

        } else if (ControllerMove.getInstance().getObject() == null) {

            ControllerMove.getInstance().getResponse()
                    .append("Cosa vorresti rimuovere dall'inventario?\n");

        } else {
            ControllerMove.getInstance().getResponse()
                    .append("L'inventario non ha questo oggetto!\n");
            ControllerMove.getInstance().getResponse()
                    .append("L'avrai sicuramente scordato da qualche parte!\n");
            ControllerMove.getInstance().getResponse()
                    .append("Che pazienzaaa!!\n");
        }
    }

    void use() throws ObjectNotFoundInInventoryException, InventoryFullException {
        if (ControllerMove.getInstance().getObject() != null
                && ControllerMove.getInstance().getObject().isUsable()
                && ControllerMove.getInstance().getGame().getCurrentRoom()
                .isObjectUsableHere(ControllerMove.getInstance().getObject())
                && (ControllerMove.getInstance().getGame().getInventory()
                .contains(ControllerMove.getInstance().getObject())
                || ControllerMove.getInstance().getGame().getCurrentRoom()
                .containsObject(ControllerMove.getInstance().getObject()))) {

            switch (ControllerMove.getInstance().getObject().getId()) {
                case SCREW:
                    ControllerMove.getInstance().getGame().getObject(SINK).setPushable(true);
                    ControllerMove.getInstance().getGame()
                            .setObjectNotAssignedRoom(ControllerMove.getInstance().getObject());

                    ControllerMove.getInstance().getGame()
                            .getInventory().remove(ControllerMove.getInstance().getObject());

                    ControllerMove.getInstance().getResponse()
                            .append("Decidi di usare il cacciavite, chiunque abbia fissato quel lavandino " +
                                    "non aveva una grande forza visto che le viti si svitano facilmente. Adesso che hai " +
                                    "rimosso tuttte le viti, noti che il lavandino non è ben fissato\n");

                    ControllerMove.getInstance().getGame().increaseScore();
                    ControllerMove.getInstance().getObject().setUsed(true);
                    break;

                case SCOTCH:
                    ControllerMove.getInstance().getGame()
                            .setObjectNotAssignedRoom(ControllerMove.getInstance().getObject());

                    ControllerMove.getInstance().getGame()
                            .getInventory().remove(ControllerMove.getInstance().getObject());

                    ControllerMove.getInstance().getGame()
                            .getInventory().add(ControllerMove.getInstance().getGame().getObject(COMBINATION));

                    ControllerMove.getInstance().getResponse()
                            .append("Metti lo scotch sui numeri della porta, dallo scotch noti le impronte dei ultimi " +
                                    "tasti schiacciati, ora indovinare il pin segreto sembra molto più semplice!\n");

                    ControllerMove.getInstance().getGame().increaseScore();
                    ControllerMove.getInstance().getObject().setUsed(true);
                    break;

                case TOOLS:
                    ControllerMove.getInstance().getResponse()
                            .append("Decidi di allenarti per un bel po’ di tempo… alla fine dell’allenamento " +
                                    "ti senti già più forte!\n");

                    if (!ControllerMove.getInstance().getObject().isUsed()) {

                        ControllerMove.getInstance().getGame().increaseScore();
                        ControllerMove.getInstance().getObject().setUsed(true);

                    }
                    break;

                case BALL:
                    ControllerMove.getInstance().getResponse()
                            .append("Il tempo è denaro, non penso sia il momento adatto per mettersi a giocare.\n");
                    ControllerMove.getInstance().getObject().setUsed(true);
                    break;

                case SCALPEL:
                    ControllerMove.getInstance().getGame()
                            .setObjectNotAssignedRoom(ControllerMove.getInstance().getObject());

                    ControllerMove.getInstance().getGame()
                            .getInventory().remove(ControllerMove.getInstance().getObject());

                    ControllerMove.getInstance().getResponse()
                            .append("Riesci subito a tirare fuori il bisturi dalla tasca, il gruppetto lo " +
                                    "vede e capito il pericolo decide di lasciare stare (Mettere a rischio la " +
                                    "vita per una panchina sarebbe veramente stupido) e vanno via con " +
                                    "un'aria di vendetta. Ora sei solo vicino alla panchina.\n");

                    ControllerMove.getInstance().getGame().getRoom(FRONTBENCH)
                            .setDescription("Sei solo vicino alla panchina!");

                    ControllerMove.getInstance().getGame().getRoom(FRONTBENCH)
                            .setLook("E' una grossa panchina in legno un po' malandata, " +
                                    "ci sei solo tu nelle vicinanze.");

                    ControllerMove.getInstance().getGame().getRoom(BENCH)
                            .setDescription("Dopo aver usato il bisturi, il giardino si è svuotato, ci sei" +
                                    "solo tu qui.");
                    ControllerMove.getInstance().getGame().getRoom(BENCH)
                            .setLook("In lontananza vedi delle panchine tutte vuote!");

                    ControllerMove.getInstance().getGame().increaseScore();
                    ControllerMove.getInstance().getObject().setUsed(true);

                    ControllerMove.getInstance().getResponse()
                            .append("Riesci subito a tirare fuori il bisturi dalla tasca, il gruppetto lo vede e " +
                                    "capito il pericolo decide di lasciare stare (Mettere a rischio la vita per una panchina " +
                                    "sarebbe veramente stupido) e vanno via con un'aria di vendetta.\nOra sei solo vicino" +
                                    " alla panchina.");

                    ControllerMove.getInstance().getGame().getCurrentRoom()
                            .setDescription("Sei solo vicino alla panchina!");

                    ControllerMove.getInstance().getGame().getCurrentRoom()
                            .setLook("E' una grossa panchina in legno un po' malandata, " +
                                    "ci sei solo tu nelle vicinanze.");

                    ControllerMove.getInstance().getGame().getObject(SCREW).setPickupable(true);
                    ControllerMove.getInstance().increaseCounterFaceUp();
                    break;

                case HACKSAW:
                    if (ControllerMove.getInstance().getGame().getObject(TOOLS).isUsed()) {
                        ControllerMove.getInstance().getGame().getRoom(AIR_DUCT_INFIRMARY).setLocked(false);
                        ControllerMove.getInstance().getGame()
                                .setObjectNotAssignedRoom(ControllerMove.getInstance().getObject());

                        ControllerMove.getInstance().getGame().getInventory()
                                .remove(ControllerMove.getInstance().getObject());

                        ControllerMove.getInstance().getGame().getRoom(AIR_DUCT_NORTH)
                                .removeObject(ControllerMove.getInstance().getGame().getObject(DESTROYABLE_GRATE));

                        ControllerMove.getInstance().getResponse()
                                .append("Oh no! Il seghetto si è rotto e adesso ci sono pezzi di sega dappertutto," +
                                        " per fortuna sei riuscito a rompere la grata\n");

                        ControllerMove.getInstance().getResponse()
                                .append("Dopo esserti allenato duramente riesci a tagliare le sbarre " +
                                        "con il seghetto, puoi proseguire nel condotto e capisci che quel condotto" +
                                        " porta fino all’infermeria.\n");

                        ControllerMove.getInstance().getGame().increaseScore();
                        ControllerMove.getInstance().getGame().increaseScore();
                        ControllerMove.getInstance().getObject().setUsed(true);
                    }
                    break;

                case SINK:
                    ControllerMove.getInstance().getResponse()
                            .append("Decidi di lavarti le mani e il viso, l’igiene prima di tutto!\n");
                    ControllerMove.getInstance().getObject().setUsed(true);
                    break;

                case GENERATOR_OBJ:
                    if (ControllerMove.getInstance().getObject().isUsed()
                            || ControllerMove.getInstance().getGame().getObject(BUTTON_GENERATOR).isPush()) {

                        ControllerMove.getInstance().getResponse()
                                .append("Il generatore è gia stato usato, fai in fretta!!\n");

                    } else {
                        ControllerMove.getInstance().getGame().getObject(BUTTON_GENERATOR).setPush(true);
                        ControllerMove.getInstance().getGame().getRoom(DOOR_ISOLATION).setLocked(false);
                        ControllerMove.getInstance().getGame().getObject(LIGHTS).setOn(false);
                        ControllerMove.getInstance().getResponse()
                                .append("Sembra che tutto il carcere sia nell’oscurità! È stata una bella mossa" +
                                        " la tua, peccato che i poliziotti prevedono queste bravate e hanno un generatore" +
                                        " di corrente ausiliario che si attiverà dopo un minuto dal blackout!\n");
                        ControllerMove.getInstance().getGame().increaseScore();
                        ControllerMove.getInstance().getObject().setUsed(true);
                    }
                    break;

                case ACID:
                    ControllerMove.getInstance().getGame().getRoom(ENDGAME).setLocked(false);
                    ControllerMove.getInstance().getGame()
                            .setObjectNotAssignedRoom(ControllerMove.getInstance().getObject());

                    ControllerMove.getInstance().getGame().getInventory()
                            .remove(ControllerMove.getInstance().getObject());

                    ControllerMove.getInstance().getResponse()
                            .append("Adesso la finestra presenta un buco, sarebbe meglio infilarsi dentro!\n");

                    ControllerMove.getInstance().getGame().increaseScore();
                    ControllerMove.getInstance().getGame().increaseScore();
                    ControllerMove.getInstance().getGame().increaseScore();
                    ControllerMove.getInstance().getGame().increaseScore();
                    ControllerMove.getInstance().getObject().setUsed(true);
                    break;

                case COMBINATION:
                    if (!ControllerMove.getInstance().getObject().isUsed()) {

                        ControllerMove.getInstance().getGame()
                                .setObjectNotAssignedRoom(ControllerMove.getInstance().getObject());

                        ControllerMove.getInstance().getGame()
                                .getInventory().remove(ControllerMove.getInstance().getObject());

                        ControllerMove.getInstance().getGame()
                                .getRoom(ISOLATION).setLocked(false);

                        ControllerMove.getInstance().getResponse()
                                .append("La porta si apre! Puoi andare a est per entrare dentro l'isolamento oppure" +
                                        " tornare indietro anche se hai poco tempo a disposizione!\n");

                        ControllerMove.getInstance().getGame().increaseScore();
                        ControllerMove.getInstance().getObject().setUsed(true);
                    }
                    break;

                case BED:
                    ControllerMove.getInstance().getResponse()
                            .append("Buona notte fiorellino!\n");
                    ControllerMove.getInstance().getObject().setUsed(true);
                    break;

                case POSTER:

                    ControllerMove.getInstance().getObject().setUsable(true);

                    // DON'T CHANGE THE ORDER
                    if (ControllerMove.getInstance().getCounterFaceUp() == 0) {
                        ControllerMove.getInstance().getResponse().append("Sei appena arrivato in carcere, perché non ti fai conoscere prendendo parte ad " +
                                "una rissa in giardino?\n");
                    } else if (!ControllerMove.getInstance().getGame().getObject(SCALPEL).isUsed()
                            && ControllerMove.getInstance().getCounterFaceUp() == 1) {
                        ControllerMove.getInstance().getResponse().append("Eccoti un consiglio: Dovresti andare nel giardino e utilizzare per bene quel " +
                                "bisturi, coraggio prendi la tua vendetta!\n");
                    } else if (!ControllerMove.getInstance().getGame().getInventory().contains(ControllerMove.getInstance().getGame().getObject(SCREW)) && !ControllerMove.getInstance().getGame().getObject(SCREW).isUsed()) {
                        ControllerMove.getInstance().getResponse().append("Adesso dovresti prendere la vite, ti servirà molto per portare al termine" +
                                "la tua missione!\n");
                    } else if (ControllerMove.getInstance().getGame().getInventory().contains(ControllerMove.getInstance().getGame().getObject(SCREW))) {
                        ControllerMove.getInstance().getResponse().append("Dovresti provare ad utilizzare la vite che hai in questa stanza, chissà cosa " +
                                "potrà capitare...\n");
                    } else if (ControllerMove.getInstance().getGame().getObject(SCREW).isUsed() && !ControllerMove.getInstance().getGame().getObject(SINK).isPush()) {
                        ControllerMove.getInstance().getResponse().append("I tuoi genitori hanno anche figli normali? Come fai a non comprendere che è " +
                                "necessario spostare il lavandino!!\n");
                    } else if (((TokenPerson) ControllerMove.getInstance().getGame().getObject(GENNY_BELLO)).getInventory().contains(ControllerMove.getInstance().getGame().getObject(HACKSAW)) &&
                            !ControllerMove.getInstance().getGame().getInventory().contains(ControllerMove.getInstance().getGame().getObject(HACKSAW))) {
                        ControllerMove.getInstance().getResponse().append("Dovresti cercare un utensile per rompere quelle grate che ti impediscono il " +
                                "passaggio!\n");
                    } else if (!ControllerMove.getInstance().getGame().getObject(HACKSAW).isUsed() && !ControllerMove.getInstance().getGame().getObject(TOOLS).isUsed() &&
                            ControllerMove.getInstance().getGame().getInventory().contains(ControllerMove.getInstance().getGame().getObject(HACKSAW))) {
                        ControllerMove.getInstance().getResponse().append("Adesso che hai il seghetto dovresti aumentare un pò la tua massa muscolare\n");
                    } else if (!ControllerMove.getInstance().getGame().getObject(HACKSAW).isUsed() && ControllerMove.getInstance().getGame().getObject(TOOLS).isUsed()) {
                        ControllerMove.getInstance().getResponse().append("Ti vedo in forma adesso, ora sarai sicuramente in grado di distruggere " +
                                "quella grata che è presente nel condotto d'aria\n");
                    } else if (ControllerMove.getInstance().getGame().getObject(HACKSAW).isUsed() && !ControllerMove.getInstance().getGame().getObject(SCOTCH).isUsed()
                            && !ControllerMove.getInstance().getGame().getInventory().contains(ControllerMove.getInstance().getGame().getObject(SCOTCH))) {
                        ControllerMove.getInstance().getResponse().append("Nel condotto d'aria c'è qualcosa che ti tornerà utile più tardi!\n");
                    } else if (!ControllerMove.getInstance().getGame().getObject(GENERATOR_OBJ).isUsed() && !ControllerMove.getInstance().getGame().getObject(MEDICINE).isGiven()) {
                        ControllerMove.getInstance().getResponse().append("Ti consiglio di cercare un pò nel condotto d'aria e spegnere il generatore" +
                                " ci vorrà un pò di buio per salvare tuo fratello\n");
                    } else if (ControllerMove.getInstance().getGame().getObject(GENERATOR_OBJ).isUsed() && !ControllerMove.getInstance().getGame().getObject(MEDICINE).isGiven()) {
                        ControllerMove.getInstance().getResponse().append("Adesso che la prigione è buia potrai andare vicino alla porta d'isolamento e " +
                                "usare quello scotch che hai preso precedentemente\n");
                    } else if (ControllerMove.getInstance().getGame().getObject(SCOTCH).isUsed() && !ControllerMove.getInstance().getGame().getObject(MEDICINE).isGiven()) {
                        ControllerMove.getInstance().getResponse().append("Dovresti usare quella combinazione che hai ottenuto utilizzando lo scotch" +
                                "nella stanza che precede l'isolamento, fai in fretta il tempo a tua disposizione" +
                                "sta per scadere!!!!\n");
                    } else if (ControllerMove.getInstance().getGame().getObject(COMBINATION).isUsed() && !ControllerMove.getInstance().getGame().getObject(MEDICINE).isGiven()) {
                        ControllerMove.getInstance().getResponse().append("Cosa ci fai qui?? Dovresti dare la medicina a tuo fratello!!!!\n");
                    } else if (ControllerMove.getInstance().getGame().getObject(MEDICINE).isGiven()) {
                        ControllerMove.getInstance().getResponse().append("Il tuo piano è quasi terminato, vai con Genny Bello in infermeria passando dal" +
                                "passaggio segreto! Buona fortuna, te ne servirà molta!!!!!!!!!\n");
                    } else {
                        ControllerMove.getInstance().getResponse().append("Mi dispiace ma non ho suggerimenti da darti attualmente!!\n");
                    }
                    break;

                default:
                    if (ControllerMove.getInstance().getObject() == null) {
                        ControllerMove.getInstance().getResponse().append("Sei sicuro di non voler usare niente?\n");
                    } else if (!ControllerMove.getInstance().getObject().isUsable()) {
                        ControllerMove.getInstance().getResponse().append("Mi dispiace ma questo oggetto non si può utilizzare\n");
                    } else if (!ControllerMove.getInstance().getGame().getInventory().contains(ControllerMove.getInstance().getObject()) && !ControllerMove.getInstance().getGame().getCurrentRoom().containsObject(ControllerMove.getInstance().getObject())) {
                        ControllerMove.getInstance().getResponse().append("Io non vedo nessun oggetto di questo tipo qui!\n");
                    } else if (!ControllerMove.getInstance().getGame().getCurrentRoom().isObjectUsableHere(ControllerMove.getInstance().getObject())) {
                        ControllerMove.getInstance().getResponse().append("C’è tempo e luogo per ogni cosa, ma non ora.\n");
                    }
            }

            if (ControllerMove.getInstance().getObject() != null && ControllerMove.getInstance().getObject().getId() == HACKSAW
                    && !ControllerMove.getInstance().getGame().getObject(TOOLS).isUsed() && ControllerMove.getInstance().getGame().getCurrentRoom().isObjectUsableHere(ControllerMove.getInstance().getGame().getObject(HACKSAW))) {
                ControllerMove.getInstance().getResponse().append("Il seghetto sembra molto arrugginito e non riesci a tagliare le sbarre " +
                        "della grata! In realtà la colpa non è totalmente del seghetto ma anche la tua " +
                        "poiché sei molto stanco e hai poca forza nelle braccia!\n");
            }
        }
    }

    void open() throws ObjectNotFoundInRoomException {
        if (ControllerMove.getInstance().getObject() != null
                && ControllerMove.getInstance().getObject().isOpenable()
                && !ControllerMove.getInstance().getObject().isOpen()
                && (ControllerMove.getInstance().getGame().getCurrentRoom().containsObject(ControllerMove.getInstance().getObject()))) {
            if (!(ControllerMove.getInstance().getObject() instanceof TokenObjectContainer)) {
                ControllerMove.getInstance().getResponse().append("Hai aperto ").append(ControllerMove.getInstance().getObject().getName()).append("!\n");
            } else if (!ControllerMove.getInstance().getObject().isOpen()) {
                ControllerMove.getInstance().getResponse().append("Hai aperto ").append(ControllerMove.getInstance().getObject().getName()).append("!\n");
                ControllerMove.getInstance().getResponse().append("Contiene: \n");
                for (TokenObject obj : ((TokenObjectContainer) ControllerMove.getInstance().getObject()).getObjects()) {
                    ControllerMove.getInstance().getResponse().append(obj.getName()).append(": ").append(obj.getDescription()).append("\n");
                }
            }
            ControllerMove.getInstance().getObject().setOpen(true);
        } else if (ControllerMove.getInstance().getObject() == null) {
            ControllerMove.getInstance().getResponse().append("Cosa vorresti aprire di preciso?\n");
        } else if (!ControllerMove.getInstance().getGame().getCurrentRoom().containsObject(ControllerMove.getInstance().getObject())) {
            throw new ObjectNotFoundInRoomException();
        } else if (!ControllerMove.getInstance().getObject().isOpenable()) {
            ControllerMove.getInstance().getResponse().append("Sei serio? Vorresti veramente aprirlo?!\n");
            ControllerMove.getInstance().getResponse().append("Sei fuori di testa!\n");
        } else if (ControllerMove.getInstance().getObject().isOpen()) {
            ControllerMove.getInstance().getResponse().append("E' gia' aperto testa di merda!\n");
        }
    }

    void close() throws ObjectNotFoundInRoomException {
        if (ControllerMove.getInstance().getObject() != null
                && ControllerMove.getInstance().getObject().isOpenable()
                && ControllerMove.getInstance().getObject().isOpen()
                && (ControllerMove.getInstance().getGame().getCurrentRoom().containsObject(ControllerMove.getInstance().getObject()))) {

            ControllerMove.getInstance().getResponse().append("Hai chiuso ").append(ControllerMove.getInstance().getObject().getName()).append("!\n");
            ControllerMove.getInstance().getObject().setOpen(false);

        } else if (ControllerMove.getInstance().getObject() == null) {
            ControllerMove.getInstance().getResponse().append("Cosa vorresti chiudere di preciso?\n");
        } else if (!ControllerMove.getInstance().getGame().getCurrentRoom().containsObject(ControllerMove.getInstance().getObject())) {
            throw new ObjectNotFoundInRoomException();
        } else if (!ControllerMove.getInstance().getObject().isOpenable()) {
            ControllerMove.getInstance().getResponse().append("Sei serio? Vorresti veramente chiuderlo?!\n");
            ControllerMove.getInstance().getResponse().append("Sei fuori di testa!\n");
        } else if (!ControllerMove.getInstance().getObject().isOpen()) {
            ControllerMove.getInstance().getResponse().append("E' gia' chiuso testa di merda!\n");
        }
    }

    void pushAndPull() throws ObjectNotFoundInRoomException {
        if (ControllerMove.getInstance().getObject() != null && ControllerMove.getInstance().getObject().isPushable() && ControllerMove.getInstance().getGame().getCurrentRoom().containsObject(ControllerMove.getInstance().getObject())) {

            switch (ControllerMove.getInstance().getObject().getId()) {
                case LADDER:
                    if (ControllerMove.getInstance().getGame().getCurrentRoom().getId() == PASSAGE_SOUTH) {
                        ControllerMove.getInstance().getGame().getRoom(SECRET_PASSAGE).setObject(ControllerMove.getInstance().getObject());
                        ControllerMove.getInstance().getGame().getRoom(PASSAGE_SOUTH).removeObject(ControllerMove.getInstance().getObject());
                        ControllerMove.getInstance().getResponse().append("La scala è stata spinta fino alla stanza a nord!\n");
                        ControllerMove.getInstance().getGame().increaseScore();
                    } else if (ControllerMove.getInstance().getGame().getCurrentRoom().getId() == SECRET_PASSAGE) {
                        ControllerMove.getInstance().getGame().getRoom(PASSAGE_NORTH).setObject(ControllerMove.getInstance().getObject());
                        ControllerMove.getInstance().getGame().getRoom(SECRET_PASSAGE).removeObject(ControllerMove.getInstance().getObject());
                        ControllerMove.getInstance().getResponse().append("La scala è stata spinta fino alla stanza a nord e si è bloccata lì!\n");
                        ControllerMove.getInstance().getGame().increaseScore();
                    } else {
                        ControllerMove.getInstance().getResponse().append("La scala è bloccata! Non esiste alcun modo per spostarla!\n");
                    }
                    break;

                case SINK:
                    if (ControllerMove.getInstance().getGame().getCurrentRoom().getId() == MAIN_CELL) {
                        if (ControllerMove.getInstance().getObject().isPush()) {
                            ControllerMove.getInstance().getResponse().append("Il Lavandino è già stato spostato!\n");
                        } else {
                            ControllerMove.getInstance().getObject().setPush(true);
                            ControllerMove.getInstance().getGame().getRoom(SECRET_PASSAGE).setLocked(false);
                            ControllerMove.getInstance().getResponse().append("Oissà!\n");
                            ControllerMove.getInstance().getGame().increaseScore();
                            ControllerMove.getInstance().getGame().increaseScore();
                        }
                    }
                    break;

                case PICTURE:
                    if (ControllerMove.getInstance().getGame().getCurrentRoom().getId() == INFIRMARY) {
                        // picture pushed
                        if (ControllerMove.getInstance().getObject().isPush()) {
                            ControllerMove.getInstance().getResponse().append("Il quadro è già stato spostato!\n");
                        } else {
                            ControllerMove.getInstance().getObject().setPush(true);
                            ControllerMove.getInstance().getGame().getCurrentRoom().setObject(ControllerMove.getInstance().getGame().getObject(OLD_AIR_DUCT));
                            ControllerMove.getInstance().getGame().getObjectNotAssignedRoom().remove(ControllerMove.getInstance().getGame().getObject(OLD_AIR_DUCT));
                            ControllerMove.getInstance().getResponse().append(ControllerMove.getInstance().getGame().getObject(OLD_AIR_DUCT).getDescription()).append("\n");
                        }
                    }
                    break;

                case BUTTON_GENERATOR:
                    if (ControllerMove.getInstance().getGame().getCurrentRoom().getId() == GENERATOR) {
                        // botton pushed
                        if (ControllerMove.getInstance().getObject().isPush()) {
                            ControllerMove.getInstance().getResponse().append("Il pulsante è già stato premuto! Fai in fretta!!!\n");
                        } else {
                            ControllerMove.getInstance().getObject().setPush(true);
                            ControllerMove.getInstance().getGame().getObject(LIGHTS).setOn(false);
                            ControllerMove.getInstance().getGame().getObject(GENERATOR_OBJ).setUsed(true);
                            ControllerMove.getInstance().getGame().getRoom(DOOR_ISOLATION).setLocked(false);
                            ControllerMove.getInstance().getResponse().append("Sembra che tutto il carcere sia nell’oscurità! È stata una bella mossa" +
                                    " la tua, peccato che i poliziotti prevedono queste bravate e hanno un " +
                                    "generatore di corrente ausiliario che si attiverà dopo un minuto dal blackout!\n");
                            ControllerMove.getInstance().getGame().increaseScore();
                            ControllerMove.getInstance().getGame().increaseScore();
                        }
                    }
                    break;
            }

        } else if (ControllerMove.getInstance().getObject() == null) {
            ControllerMove.getInstance().getResponse().append("Cosa vuoi spostare? L'aria?!?\n");
        } else if (!ControllerMove.getInstance().getGame().getCurrentRoom().containsObject(ControllerMove.getInstance().getObject())) {
            throw new ObjectNotFoundInRoomException();
        } else if (!ControllerMove.getInstance().getObject().isPushable()) {
            ControllerMove.getInstance().getResponse().append("Puoi essere anche Hulk ma quell'oggetto non si può spostare!!!\n");
        }
    }

    void turnOn() {
        if (ControllerMove.getInstance().getObject() != null && ControllerMove.getInstance().getObject().isTurnOnAble()) {
            // lights case
            if (ControllerMove.getInstance().getGame().getCurrentRoom().getId() == GENERATOR) {
                // lights turnOFF
                if (ControllerMove.getInstance().getObject().isOn()) {
                    ControllerMove.getInstance().getResponse().append("Le luci sono già accese!\n");
                } else {
                    ControllerMove.getInstance().getResponse().append("Le luci si accenderanno da sole tra qualche minuto, non avere paura!\n");
                }
            } else {
                ControllerMove.getInstance().getResponse().append("Non puoi accendere nulla qui!\n");
            }
        } else if (ControllerMove.getInstance().getObject() == null) {
            ControllerMove.getInstance().getResponse().append("Cosa vuoi accendere esattamente???\n");
        } else if (!ControllerMove.getInstance().getObject().isTurnOnAble()) {
            ControllerMove.getInstance().getResponse().append("Come puoi accendere questo oggetto???\n");
        }
    }

    void turnOff() {
        if (ControllerMove.getInstance().getObject() != null && ControllerMove.getInstance().getObject().isTurnOnAble()) {
            // lights case
            if (ControllerMove.getInstance().getGame().getCurrentRoom().getId() == GENERATOR) {
                // lights turnOFF
                if (!ControllerMove.getInstance().getObject().isOn()) {
                    ControllerMove.getInstance().getResponse().append("Il pulsante è già stato premuto! Fai in fretta!!!\n");
                } else {
                    ControllerMove.getInstance().getGame().getObject(BUTTON_GENERATOR).setPush(true);
                    ControllerMove.getInstance().getGame().getObject(GENERATOR_OBJ).setUsable(true);
                    ControllerMove.getInstance().getObject().setOn(false);
                    ControllerMove.getInstance().getGame().getRoom(DOOR_ISOLATION).setLocked(false);
                    ControllerMove.getInstance().getResponse().append("Sembra che tutto il carcere sia nell’oscurità! È stata una bella mossa" +
                            " la tua, peccato che i poliziotti prevedono queste bravate e hanno un generatore" +
                            " di corrente ausiliario che si attiverà dopo un minuto dal blackout!\n");
                    ControllerMove.getInstance().getGame().increaseScore();
                    ControllerMove.getInstance().getGame().increaseScore();
                }
            } else {
                ControllerMove.getInstance().getResponse().append("Non puoi spegnere nulla qui!\n");
            }
        } else if (ControllerMove.getInstance().getObject() == null) {
            ControllerMove.getInstance().getResponse().append("Cosa vuoi spegnere esattamente???\n");
        } else if (!ControllerMove.getInstance().getObject().isTurnOnAble()) {
            ControllerMove.getInstance().getResponse().append("Come puoi spegnere questo oggetto???\n");
        }
    }

    void end() {
        ControllerMove.getInstance().getResponse().append("Non puoi usare quell'oggetto per uscire!\n");
    }


}
