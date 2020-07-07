package adventure.games.prisonbreak.movement;

import adventure.exceptions.inventoryException.InventoryEmptyException;
import adventure.exceptions.inventoryException.InventoryFullException;
import adventure.exceptions.inventoryException.ObjectNotFoundInInventoryException;
import adventure.exceptions.objectsException.ObjectNotFoundInRoomException;
import adventure.games.prisonbreak.PrisonBreakGame;
import adventure.games.prisonbreak.TokenPerson;
import adventure.type.TokenObject;
import adventure.type.TokenObjectContainer;

import static adventure.games.prisonbreak.ObjectType.*;
import static adventure.games.prisonbreak.RoomType.*;

/**
 * @author Corona-Extra
 */
class BasicVerbs {

    private final Move movement;
    private final PrisonBreakGame prisonBreakGame;
    private StringBuilder response = new StringBuilder();

    BasicVerbs(ControllerMovement controller) {
        movement = controller.getMove();
        prisonBreakGame = movement.getPrisonBreakGame();
    }

    void resetResponse() {
        response = new StringBuilder();
    }

    String inventory() throws InventoryEmptyException {
        if (!prisonBreakGame.getInventory().isEmpty()) {
            response.append("Nel tuo inventario ci sono:");
            for (TokenObject o : prisonBreakGame.getInventory().getObjects()) {
                response
                        .append("\n- ")
                        .append(o.getName())
                        .append(": ")
                        .append(o.getDescription());
            }
        }
        return response.toString();
    }

    String lookAt() {
        if (movement.getObject() != null && (prisonBreakGame.getInventory().contains(movement.getObject())
                || prisonBreakGame.getCurrentRoom().getObjects().contains(movement.getObject())
                || prisonBreakGame.getCurrentRoom().getObjects().stream().anyMatch(obj -> obj instanceof TokenPerson
                && ((TokenPerson) obj).getInventory().contains(movement.getObject())))) {
            response.append(movement.getObject().getDescription()).append("\n");

        } else if (prisonBreakGame.getCurrentRoom().getLook() != null && (movement.getObject() == null
                || movement.getObject().getId() == ROOM_OBJ)) {
            response.append(prisonBreakGame.getCurrentRoom().getLook());

        } else if (movement.getObject() != null && movement.getObject().getId() == SCORE_OBJ) {
            //TODO cambiare frase in base allo score
            response.append("Non male, attualmente il tuo punteggio è ").append(prisonBreakGame.getScore());
        } else {
            response.append("Non c'è niente di interessante qui.");
        }
        return response.toString();
    }

    String pickUp() throws InventoryFullException, ObjectNotFoundInRoomException {
        if (movement.getObject() != null && movement.getObject().getId() == HACKSAW && movement.getObject().isAccept()
                && ((TokenPerson) prisonBreakGame.getObject(GENNY_BELLO)).getInventory().contains(movement.getObject())) {

            //There is the need of the remove's operation from the room of the TokenPerson's objects
            prisonBreakGame.getCurrentRoom().removeObject(prisonBreakGame.getObject(HACKSAW));
            prisonBreakGame.getInventory().add(movement.getObject());
            response.append("Hai preso ").append(movement.getObject().getName()).append("!");
            prisonBreakGame.increaseScore();

        } else if (movement.getObject() != null && movement.getObject().getId() == SCALPEL
                && prisonBreakGame.getCurrentRoom().getId() == INFIRMARY
                && !movement.getObject().isTaken()) {

            prisonBreakGame.getCurrentRoom().removeObject(movement.getObject());
            prisonBreakGame.getInventory().add(movement.getObject());
            movement.getObject().setTaken(true);
            prisonBreakGame.increaseScore();

            response.append("Hai preso ").append(movement.getObject().getName()).append("!");
            response.append("\nFai in fretta perché improvvisamente senti i passi dell’infermiera avvicinandosi " +
                    "alla porta, riesci a prendere il bisturi con te e l’infermiera ti dice che sei guarito" +
                    " e puoi ritornare nella cella visto che l’ora d’aria è finita.\n\n");
            prisonBreakGame.setCurrentRoom(prisonBreakGame.getRoom(MAIN_CELL));
            prisonBreakGame.getInventory().add(prisonBreakGame.getObject(MEDICINE));
            response.append("Zzzzzz.....\n\n");
            response.append("Caspita gli antidolorifici ti hanno fatto dormire molto e ti risvegli nella tua " +
                    "cella privo di qualsiasi dolore! Prima di andare via l’infermiera ti ha dato qualche " +
                    "medicinale tra cui un medicinale all’ortica. Guarda nel tuo inventario!\n\n");
            movement.setMove(true);
            prisonBreakGame.getRoom(INFIRMARY).setDescription("Sul lettino dell'infermeria c'è tuo fratello" +
                    " leggermente dolorante");
            prisonBreakGame.getRoom(INFIRMARY).setLook("Nell'aria c'e' molta tensione, sarebbe meglio a cercare " +
                    "una via di fuga!!!!!!");

        } else if (movement.getObject() != null && movement.getObject().isPickupable()
                && prisonBreakGame.getCurrentRoom().containsObject(movement.getObject())) {

            if ((movement.getObject().getId() == SCOTCH || movement.getObject().getId() == SCREW)
                    && !movement.getObject().isTaken()) {
                prisonBreakGame.increaseScore();
                movement.getObject().setTaken(true);
            }
            prisonBreakGame.getCurrentRoom().removeObject(movement.getObject());
            prisonBreakGame.getInventory().add(movement.getObject());

            response.append("Hai preso ").append(movement.getObject().getName()).append("!");

        } else if (movement.getObject() == null) {
            response.append("Cosa vorresti prendere di preciso?");

        } else if (movement.getObject().getId() == SCREW && !movement.getObject().isPickupable()) {
            response.append("Non puoi prendere quella vite se prima non affronti il gruppetto dei detenuti!");
        } else if (prisonBreakGame.getInventory().contains(movement.getObject())) {
            response.append("Guarda bene nella tua borsa, cretino!");
        } else if (!prisonBreakGame.getCurrentRoom().containsObject(movement.getObject())) {
            throw new ObjectNotFoundInRoomException();
        } else if (!movement.getObject().isPickupable()) {
            response.append("Non e' certo un oggetto che si può prendere imbecille!");
        }
        return response.toString();
    }

    String remove() throws ObjectNotFoundInInventoryException {
        if (movement.getObject() != null && prisonBreakGame.getInventory().contains(movement.getObject())) {
            prisonBreakGame.getCurrentRoom().setObject(movement.getObject());
            prisonBreakGame.getInventory().remove(movement.getObject());
            response.append("Hai lasciato a terra ").append(movement.getObject().getName()).append("!\n");

        } else if (movement.getObject() == null) {
            response.append("Cosa vorresti rimuovere dall'inventario?");

        } else {
            response.append("L'inventario non ha questo oggetto!");
            response.append("L'avrai sicuramente scordato da qualche parte!");
            response.append("Che pazienzaaa!!");
        }
        return response.toString();
    }

    String use() throws ObjectNotFoundInInventoryException, InventoryFullException {
        if (movement.getObject() != null && movement.getObject().isUsable()
                && prisonBreakGame.getCurrentRoom().isObjectUsableHere(movement.getObject())
                && (prisonBreakGame.getInventory().contains(movement.getObject())
                || prisonBreakGame.getCurrentRoom().containsObject(movement.getObject()))) {

            switch (movement.getObject().getId()) {
                case SCREW:
                    prisonBreakGame.getObject(SINK).setPushable(true);
                    prisonBreakGame.setObjectNotAssignedRoom(movement.getObject());
                    prisonBreakGame.getInventory().remove(movement.getObject());
                    response.append("Decidi di usare la vite per smontare il lavandino. Chiunque abbia fissato " +
                            "quel lavandino non aveva una grande forza visto che le viti si svitano facilmente." +
                            " Adesso che hai rimosso tutte le viti, noti che il lavandino non è ben fissato\n");
                    prisonBreakGame.increaseScore();
                    movement.getObject().setUsed(true);
                    break;

                case SCOTCH:
                    prisonBreakGame.setObjectNotAssignedRoom(movement.getObject());
                    prisonBreakGame.getInventory().remove(movement.getObject());
                    prisonBreakGame.getInventory().add(prisonBreakGame.getObject(COMBINATION));
                    response.append("Metti lo scotch sui numeri della porta, dallo scotch noti le impronte dei ultimi " +
                            "tasti schiacciati, ora indovinare il pin segreto sembra molto più semplice!\n");
                    prisonBreakGame.increaseScore();
                    movement.getObject().setUsed(true);
                    break;

                case TOOLS:
                    response.append("Decidi di allenarti per un bel po’ di tempo… alla fine dell’allenamento " +
                            "ti senti già più forte!");

                    if (!movement.getObject().isUsed()) {
                        prisonBreakGame.increaseScore();
                        movement.getObject().setUsed(true);
                    }
                    break;

                case BALL:
                    response.append("Il tempo è denaro, non penso sia il momento adatto per mettersi a giocare.");
                    movement.getObject().setUsed(true);
                    break;

                case SCALPEL:
                    prisonBreakGame.setObjectNotAssignedRoom(movement.getObject());
                    prisonBreakGame.getInventory().remove(movement.getObject());
                    response.append("Riesci subito a tirare fuori il bisturi dalla tasca, il gruppetto lo " +
                            "vede e capito il pericolo decide di lasciare stare (Mettere a rischio la " +
                            "vita per una panchina sarebbe veramente stupido) e vanno via con " +
                            "un'aria di vendetta. Ora sei solo vicino alla panchina.");
                    prisonBreakGame.getRoom(FRONTBENCH).setDescription("Sei solo vicino alla panchina!");
                    prisonBreakGame.getRoom(FRONTBENCH).setLook("E' una grossa panchina in legno un po' malandata, " +
                            "ci sei solo tu nelle vicinanze.");
                    prisonBreakGame.getRoom(BENCH).setDescription("Dopo aver usato il bisturi, il giardino si è svuotato, ci sei" +
                            "solo tu qui.");
                    prisonBreakGame.getRoom(BENCH).setLook("In lontananza vedi delle panchine tutte vuote!");
                    prisonBreakGame.increaseScore();
                    movement.getObject().setUsed(true);
                    response.append("Riesci subito a tirare fuori il bisturi dalla tasca, il gruppetto lo vede e " +
                            "capito il pericolo decide di lasciare stare (Mettere a rischio la vita per una panchina " +
                            "sarebbe veramente stupido) e vanno via con un'aria di vendetta.\nOra sei solo vicino" +
                            " alla panchina.");
                    prisonBreakGame.getCurrentRoom().setDescription("Sei solo vicino alla panchina!");
                    prisonBreakGame.getCurrentRoom().setLook("E' una grossa panchina in legno un po' malandata, " +
                            "ci sei solo tu nelle vicinanze.");
                    prisonBreakGame.getObject(SCREW).setPickupable(true);
                    movement.increaseCounterFaceUp();
                    break;

                case HACKSAW:
                    if (prisonBreakGame.getObject(TOOLS).isUsed()) {
                        prisonBreakGame.getRoom(AIR_DUCT_INFIRMARY).setLocked(false);
                        prisonBreakGame.setObjectNotAssignedRoom(movement.getObject());
                        prisonBreakGame.getInventory().remove(movement.getObject());
                        prisonBreakGame.getRoom(AIR_DUCT_NORTH).removeObject(prisonBreakGame.getObject(DESTROYABLE_GRATE));
                        response.append("Oh no! Il seghetto si è rotto e adesso ci sono pezzi di sega dappertutto," +
                                " per fortuna sei riuscito a rompere la grata");
                        response.append("Dopo esserti allenato duramente riesci a tagliare le sbarre " +
                                "con il seghetto, puoi proseguire nel condotto e capisci che quel condotto" +
                                " porta fino all’infermeria.");
                        prisonBreakGame.increaseScore();
                        prisonBreakGame.increaseScore();
                        movement.getObject().setUsed(true);
                    }
                    break;

                case SINK:
                    response.append("Decidi di lavarti le mani e il viso, l’igiene prima di tutto!");
                    movement.getObject().setUsed(true);
                    break;

                case GENERATOR_OBJ:
                    if (movement.getObject().isUsed() || prisonBreakGame.getObject(BUTTON_GENERATOR).isPush()) {
                        response.append("Il generatore è gia stato usato, fai in fretta!!");

                    } else {
                        prisonBreakGame.getObject(BUTTON_GENERATOR).setPush(true);
                        prisonBreakGame.getRoom(DOOR_ISOLATION).setLocked(false);
                        prisonBreakGame.getObject(LIGHTS).setOn(false);
                        response.append("Sembra che tutto il carcere sia nell’oscurità! È stata una bella mossa" +
                                " la tua, peccato che i poliziotti prevedono queste bravate e hanno un generatore" +
                                " di corrente ausiliario che si attiverà dopo un minuto dal blackout!");
                        prisonBreakGame.increaseScore();
                        movement.getObject().setUsed(true);
                    }
                    break;

                case ACID:
                    prisonBreakGame.getRoom(ENDGAME).setLocked(false);
                    prisonBreakGame.setObjectNotAssignedRoom(movement.getObject());
                    prisonBreakGame.getInventory().remove(movement.getObject());
                    response.append("Adesso la finestra presenta un buco, sarebbe meglio infilarsi dentro!");
                    prisonBreakGame.increaseScore();
                    prisonBreakGame.increaseScore();
                    prisonBreakGame.increaseScore();
                    prisonBreakGame.increaseScore();
                    movement.getObject().setUsed(true);
                    break;

                case COMBINATION:
                    if (!movement.getObject().isUsed()) {
                        prisonBreakGame.setObjectNotAssignedRoom(movement.getObject());
                        prisonBreakGame.getInventory().remove(movement.getObject());
                        prisonBreakGame.getRoom(ISOLATION).setLocked(false);
                        response.append("La porta si apre! Puoi andare a est per entrare dentro l'isolamento oppure" +
                                " tornare indietro anche se hai poco tempo a disposizione!");
                        prisonBreakGame.increaseScore();
                        movement.getObject().setUsed(true);
                    }
                    break;

                case BED:
                    response.append("Buona notte fiorellino!");
                    movement.getObject().setUsed(true);
                    break;

                case POSTER:
                    movement.getObject().setUsed(true);

                    // DON'T CHANGE THE ORDER
                    if (movement.getCounterFaceUp() == 0) {
                        response.append("Sei appena arrivato in carcere, perché non ti fai conoscere prendendo parte ad " +
                                "una rissa in giardino?");
                    } else if (!prisonBreakGame.getObject(SCALPEL).isUsed()
                            && movement.getCounterFaceUp() == 1) {
                        response.append("Eccoti un consiglio: Dovresti andare nel giardino e utilizzare per bene quel " +
                                "bisturi, coraggio prendi la tua vendetta!");
                    } else if (!prisonBreakGame.getInventory().contains(prisonBreakGame.getObject(SCREW)) && !prisonBreakGame.getObject(SCREW).isUsed()) {
                        response.append("Adesso dovresti prendere la vite, ti servirà molto per portare al termine" +
                                "la tua missione!");
                    } else if (prisonBreakGame.getInventory().contains(prisonBreakGame.getObject(SCREW))) {
                        response.append("Dovresti provare ad utilizzare la vite che hai in questa stanza, chissà cosa " +
                                "potrà capitare...");
                    } else if (prisonBreakGame.getObject(SCREW).isUsed() && !prisonBreakGame.getObject(SINK).isPush()) {
                        response.append("I tuoi genitori hanno anche figli normali? Come fai a non comprendere che è " +
                                "necessario spostare il lavandino!!");
                    } else if (((TokenPerson) prisonBreakGame.getObject(GENNY_BELLO)).getInventory().contains(prisonBreakGame.getObject(HACKSAW))
                            && !prisonBreakGame.getInventory().contains(prisonBreakGame.getObject(HACKSAW))) {
                        response.append("Dovresti cercare un utensile per rompere quelle grate che ti impediscono il " +
                                "passaggio!");
                    } else if (!prisonBreakGame.getObject(HACKSAW).isUsed() && !prisonBreakGame.getObject(TOOLS).isUsed() &&
                            prisonBreakGame.getInventory().contains(prisonBreakGame.getObject(HACKSAW))) {
                        response.append("Adesso che hai il seghetto dovresti aumentare un pò la tua massa muscolare");
                    } else if (!prisonBreakGame.getObject(HACKSAW).isUsed() && prisonBreakGame.getObject(TOOLS).isUsed()) {
                        response.append("Ti vedo in forma adesso, ora sarai sicuramente in grado di distruggere " +
                                "quella grata che è presente nel condotto d'aria");
                    } else if (prisonBreakGame.getObject(HACKSAW).isUsed() && !prisonBreakGame.getObject(SCOTCH).isUsed()
                            && !prisonBreakGame.getInventory().contains(prisonBreakGame.getObject(SCOTCH))) {
                        response.append("Nel condotto d'aria c'è qualcosa che ti tornerà utile più tardi!");
                    } else if (!prisonBreakGame.getObject(GENERATOR_OBJ).isUsed() && !prisonBreakGame.getObject(MEDICINE).isGiven()) {
                        response.append("Ti consiglio di cercare un pò nel condotto d'aria e spegnere il generatore" +
                                " ci vorrà un pò di buio per salvare tuo fratello");
                    } else if (prisonBreakGame.getObject(GENERATOR_OBJ).isUsed() && !prisonBreakGame.getObject(MEDICINE).isGiven()) {
                        response.append("Adesso che la prigione è buia potrai andare vicino alla porta d'isolamento e " +
                                "usare quello scotch che hai preso precedentemente");
                    } else if (prisonBreakGame.getObject(SCOTCH).isUsed() && !prisonBreakGame.getObject(MEDICINE).isGiven()) {
                        response.append("Dovresti usare quella combinazione che hai ottenuto utilizzando lo scotch" +
                                "nella stanza che precede l'isolamento, fai in fretta il tempo a tua disposizione" +
                                "sta per scadere!!");
                    } else if (prisonBreakGame.getObject(COMBINATION).isUsed() && !prisonBreakGame.getObject(MEDICINE).isGiven()) {
                        response.append("Cosa ci fai qui?? Dovresti dare la medicina a tuo fratello!!!");
                    } else if (prisonBreakGame.getObject(MEDICINE).isGiven()) {
                        response.append("Il tuo piano è quasi terminato, vai con Genny Bello in infermeria passando dal" +
                                "passaggio segreto! Buona fortuna, te ne servirà molta!!!");
                    } else {
                        response.append("Mi dispiace ma non ho suggerimenti da darti attualmente!!");
                    }
                    break;
            }

        } else if (movement.getObject() == null) {
            response.append("Sei sicuro di non voler usare niente?");
        } else if (!movement.getObject().isUsable()) {
            response.append("Mi dispiace ma questo oggetto non si può utilizzare");
        } else if (!prisonBreakGame.getInventory().contains(movement.getObject())
                && !prisonBreakGame.getCurrentRoom().containsObject(movement.getObject())) {
            response.append("Io non vedo nessun oggetto di questo tipo qui!");
        } else if (!prisonBreakGame.getCurrentRoom().isObjectUsableHere(movement.getObject())) {
            response.append("C’è tempo e luogo per ogni cosa, ma non ora.");
        }

        if (movement.getObject() != null && movement.getObject().getId() == HACKSAW
                && !prisonBreakGame.getObject(TOOLS).isUsed()
                && prisonBreakGame.getCurrentRoom().isObjectUsableHere(prisonBreakGame.getObject(HACKSAW))) {
            response.append("Il seghetto sembra molto arrugginito e non riesci a tagliare le sbarre " +
                    "della grata! In realtà la colpa non è totalmente del seghetto ma anche la tua " +
                    "poiché sei molto stanco e hai poca forza nelle braccia!");
        }
        return response.toString();
    }

    String open() throws ObjectNotFoundInRoomException {
        if (movement.getObject() != null
                && movement.getObject().isOpenable()
                && !movement.getObject().isOpen()
                && (prisonBreakGame.getCurrentRoom().containsObject(movement.getObject()))) {
            if (!(movement.getObject() instanceof TokenObjectContainer)) {
                response.append("Hai aperto ").append(movement.getObject().getName()).append("!\n");
            } else if (!movement.getObject().isOpen()) {
                response.append("Hai aperto ").append(movement.getObject().getName()).append("!\n");
                response.append("Contiene: \n");
                for (TokenObject obj : ((TokenObjectContainer) movement.getObject()).getObjects()) {
                    response.append(obj.getName()).append(": ").append(obj.getDescription()).append("\n");
                }
            }
            movement.getObject().setOpen(true);
        } else if (movement.getObject() == null) {
            response.append("Cosa vorresti aprire di preciso?");
        } else if (!prisonBreakGame.getCurrentRoom().containsObject(movement.getObject())) {
            throw new ObjectNotFoundInRoomException();
        } else if (!movement.getObject().isOpenable()) {
            response.append("Sei serio? Vorresti veramente aprirlo?!");
            response.append("Sei fuori di testa!");
        } else if (movement.getObject().isOpen()) {
            response.append("E' gia' aperto testa di merda!");
        }
        return response.toString();
    }

    String close() throws ObjectNotFoundInRoomException {
        if (movement.getObject() != null
                && movement.getObject().isOpenable()
                && movement.getObject().isOpen()
                && (prisonBreakGame.getCurrentRoom().containsObject(movement.getObject()))) {

            response.append("Hai chiuso ").append(movement.getObject().getName()).append("!");
            movement.getObject().setOpen(false);

        } else if (movement.getObject() == null) {
            response.append("Cosa vorresti chiudere di preciso?");
        } else if (!prisonBreakGame.getCurrentRoom().containsObject(movement.getObject())) {
            throw new ObjectNotFoundInRoomException();
        } else if (!movement.getObject().isOpenable()) {
            response.append("Sei serio? Vorresti veramente chiuderlo?!");
            response.append("Sei fuori di testa!");
        } else if (!movement.getObject().isOpen()) {
            response.append("E' gia' chiuso testa di merda!");
        }
        return response.toString();
    }

    String pushAndPull() throws ObjectNotFoundInRoomException {
        if (movement.getObject() != null && movement.getObject().isPushable()
                && prisonBreakGame.getCurrentRoom().containsObject(movement.getObject())) {

            switch (movement.getObject().getId()) {
                case LADDER:
                    if (prisonBreakGame.getCurrentRoom().getId() == PASSAGE_SOUTH) {
                        prisonBreakGame.getRoom(SECRET_PASSAGE).setObject(movement.getObject());
                        prisonBreakGame.getRoom(PASSAGE_SOUTH).removeObject(movement.getObject());
                        response.append("La scala è stata spinta fino alla stanza a nord!");
                        prisonBreakGame.increaseScore();
                    } else if (prisonBreakGame.getCurrentRoom().getId() == SECRET_PASSAGE) {
                        prisonBreakGame.getRoom(PASSAGE_NORTH).setObject(movement.getObject());
                        prisonBreakGame.getRoom(SECRET_PASSAGE).removeObject(movement.getObject());
                        response.append("La scala è stata spinta fino alla stanza a nord e si è bloccata lì!");
                        prisonBreakGame.increaseScore();
                    } else {
                        response.append("La scala è bloccata! Non esiste alcun modo per spostarla!");
                    }
                    break;

                case SINK:
                    if (prisonBreakGame.getCurrentRoom().getId() == MAIN_CELL) {
                        if (movement.getObject().isPush()) {
                            response.append("Il Lavandino è già stato spostato!");
                        } else {
                            movement.getObject().setPush(true);
                            prisonBreakGame.getRoom(SECRET_PASSAGE).setLocked(false);
                            response.append("Oissà!");
                            prisonBreakGame.increaseScore();
                            prisonBreakGame.increaseScore();
                        }
                    }
                    break;

                case PICTURE:
                    if (prisonBreakGame.getCurrentRoom().getId() == INFIRMARY) {
                        // picture pushed
                        if (movement.getObject().isPush()) {
                            response.append("Il quadro è già stato spostato!");
                        } else {
                            movement.getObject().setPush(true);
                            prisonBreakGame.getCurrentRoom().setObject(prisonBreakGame.getObject(OLD_AIR_DUCT));
                            prisonBreakGame.getObjectNotAssignedRoom().remove(prisonBreakGame.getObject(OLD_AIR_DUCT));
                            response.append(prisonBreakGame.getObject(OLD_AIR_DUCT).getDescription());
                        }
                    }
                    break;

                case BUTTON_GENERATOR:
                    if (prisonBreakGame.getCurrentRoom().getId() == GENERATOR) {
                        // botton pushed
                        if (movement.getObject().isPush()) {
                            response.append("Il pulsante è già stato premuto! Fai in fretta!!");
                        } else {
                            movement.getObject().setPush(true);
                            prisonBreakGame.getObject(LIGHTS).setOn(false);
                            prisonBreakGame.getObject(GENERATOR_OBJ).setUsed(true);
                            prisonBreakGame.getRoom(DOOR_ISOLATION).setLocked(false);
                            response.append("Sembra che tutto il carcere sia nell’oscurità! È stata una bella mossa" +
                                    " la tua, peccato che i poliziotti prevedono queste bravate e hanno un " +
                                    "generatore di corrente ausiliario che si attiverà dopo un minuto dal blackout!");
                            prisonBreakGame.increaseScore();
                            prisonBreakGame.increaseScore();
                        }
                    }
                    break;
            }

        } else if (movement.getObject() == null) {
            response.append("Cosa vuoi spostare? L'aria?!?");
        } else if (!prisonBreakGame.getCurrentRoom().containsObject(movement.getObject())) {
            throw new ObjectNotFoundInRoomException();
        } else if (!movement.getObject().isPushable()) {
            response.append("Puoi essere anche Hulk ma quell'oggetto non si può spostare!!!");
        }
        return response.toString();
    }

    String turnOn() {
        if (movement.getObject() != null && movement.getObject().isTurnOnAble()) {
            // lights case
            if (prisonBreakGame.getCurrentRoom().getId() == GENERATOR) {
                // lights turnOFF
                if (movement.getObject().isOn()) {
                    response.append("Le luci sono già accese!");
                } else {
                    response.append("Le luci si accenderanno da sole tra qualche minuto, non avere paura!");
                }
            } else {
                response.append("Non puoi accendere nulla qui!");
            }
        } else if (movement.getObject() == null) {
            response.append("Cosa vuoi accendere esattamente???");
        } else if (!movement.getObject().isTurnOnAble()) {
            response.append("Come puoi accendere questo oggetto?");
        }
        return response.toString();
    }

    String turnOff() {
        if (movement.getObject() != null && movement.getObject().isTurnOnAble()) {
            // lights case
            if (prisonBreakGame.getCurrentRoom().getId() == GENERATOR) {
                // lights turnOFF
                if (!movement.getObject().isOn()) {
                    response.append("Il pulsante è già stato premuto! Fai in fretta!!!");
                } else {
                    prisonBreakGame.getObject(BUTTON_GENERATOR).setPush(true);
                    prisonBreakGame.getObject(GENERATOR_OBJ).setUsable(true);
                    movement.getObject().setOn(false);
                    prisonBreakGame.getRoom(DOOR_ISOLATION).setLocked(false);
                    response.append("Sembra che tutto il carcere sia nell’oscurità! È stata una bella mossa" +
                            " la tua, peccato che i poliziotti prevedono queste bravate e hanno un generatore" +
                            " di corrente ausiliario che si attiverà dopo un minuto dal blackout!");
                    prisonBreakGame.increaseScore();
                    prisonBreakGame.increaseScore();
                }
            } else {
                response.append("Non puoi spegnere nulla qui!");
            }
        } else if (movement.getObject() == null) {
            response.append("Cosa vuoi spegnere esattamente???");
        } else if (!movement.getObject().isTurnOnAble()) {
            response.append("Come puoi spegnere questo oggetto???");
        }
        return response.toString();
    }

    String end() {
        response.append("Non puoi usare quell'oggetto per uscire!");
        return response.toString();
    }

}
