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

class BasicVerbs {

    private final Move movement;
    private final PrisonBreakGame game;
    private StringBuilder response = new StringBuilder();

    BasicVerbs(ControllerMovement controller) {
        movement = controller.getMove();
        game = movement.getGame();
    }

    void resetResponse() {
        response = new StringBuilder();
    }

    String inventory() throws InventoryEmptyException {
        if (!game.getInventory().isEmpty()) {
            response.append("Nel tuo inventario ci sono:\n");
            for (TokenObject o : game.getInventory().getObjects()) {
                response
                        .append(o.getName())
                        .append(": ")
                        .append(o.getDescription())
                        .append("\n");
            }
        }
        return response.toString();
    }

    String lookAt() {
        if (movement.getObject() != null && (game.getInventory().contains(movement.getObject())
                || game.getCurrentRoom().getObjects().contains(movement.getObject())
                || game.getCurrentRoom().getObjects().stream().anyMatch(obj -> obj instanceof TokenPerson
                && ((TokenPerson) obj).getInventory().contains(movement.getObject())))) {
            response.append(movement.getObject().getDescription()).append("\n");

        } else if (game.getCurrentRoom().getLook() != null && (movement.getObject() == null
                || movement.getObject().getId() == ROOM_OBJ)) {
            response.append(game.getCurrentRoom().getLook()).append("\n");

        } else if (movement.getObject() != null && movement.getObject().getId() == SCORE_OBJ) {
            //TODO cambiare frase in base allo score
            response.append("Non male, attualmente il tuo punteggio è ").append(game.getScore()).append("\n");
        } else {
            response.append("Non c'è niente di interessante qui.\n");
        }
        return response.toString();
    }

    String pickUp() throws InventoryFullException, ObjectNotFoundInRoomException {
        if (movement.getObject() != null && movement.getObject().getId() == HACKSAW && movement.getObject().isAccept()
                && ((TokenPerson) game.getObject(GENNY_BELLO)).getInventory().contains(movement.getObject())) {

            //There is the need of the remove's operation from the room of the TokenPerson's objects
            game.getCurrentRoom().removeObject(game.getObject(HACKSAW));
            game.getInventory().add(movement.getObject());
            response.append("Hai preso ").append(movement.getObject().getName()).append("!\n");
            game.increaseScore();

        } else if (movement.getObject() != null && movement.getObject().getId() == SCALPEL
                && game.getCurrentRoom().getId() == INFIRMARY
                && !movement.getObject().isTaken()) {

            game.getCurrentRoom().removeObject(movement.getObject());
            game.getInventory().add(movement.getObject());
            movement.getObject().setTaken(true);
            game.increaseScore();

            response.append("Hai preso ").append(movement.getObject().getName()).append("!\n");
            response.append("Fai in fretta perché improvvisamente senti i passi dell’infermiera avvicinandosi " +
                    "alla porta, riesci a prendere il bisturi con te e l’infermiera ti dice che sei guarito" +
                    " e puoi ritornare nella cella visto che l’ora d’aria è finita.\n\n");
            game.setCurrentRoom(game.getRoom(MAIN_CELL));
            game.getInventory().add(game.getObject(MEDICINE));
            response.append("Zzzzzz.....\n\n");
            response.append("Caspita gli antidolorifici ti hanno fatto dormire molto e ti risvegli nella tua " +
                    "cella privo di qualsiasi dolore! Prima di andare via l’infermiera ti ha dato qualche " +
                    "medicinale tra cui un medicinale all’ortica. Guarda nel tuo inventario!\n\n");
            movement.setMove(true);

        } else if (movement.getObject() != null && movement.getObject().isPickupable()
                && game.getCurrentRoom().containsObject(movement.getObject())) {

            if ((movement.getObject().getId() == SCOTCH || movement.getObject().getId() == SCREW)
                    && !movement.getObject().isTaken()) {
                game.increaseScore();
                movement.getObject().setTaken(true);
            }
            game.getCurrentRoom().removeObject(movement.getObject());
            game.getInventory().add(movement.getObject());

            response.append("Hai preso ").append(movement.getObject().getName()).append("!\n");

        } else if (movement.getObject() == null) {
            response.append("Cosa vorresti prendere di preciso?\n");

        } else if (movement.getObject().getId() == SCREW && !movement.getObject().isPickupable()) {
            response.append("Non puoi prendere quella vite se prima non affronti il gruppetto dei detenuti!\n");
        } else if (game.getInventory().contains(movement.getObject())) {
            response.append("Guarda bene nella tua borsa, cretino!\n");
        } else if (!game.getCurrentRoom().containsObject(movement.getObject())) {
            throw new ObjectNotFoundInRoomException();
        } else if (!movement.getObject().isPickupable()) {
            response.append("Non e' certo un oggetto che si può prendere imbecille!\n");
        }
        return response.toString();
    }

    String remove() throws ObjectNotFoundInInventoryException {
        if (movement.getObject() != null && game.getInventory().contains(movement.getObject())) {
            game.getCurrentRoom().setObject(movement.getObject());
            game.getInventory().remove(movement.getObject());
            response.append("Hai lasciato a terra ").append(movement.getObject().getName()).append("!\n");

        } else if (movement.getObject() == null) {
            response.append("Cosa vorresti rimuovere dall'inventario?\n");

        } else {
            response.append("L'inventario non ha questo oggetto!\n");
            response.append("L'avrai sicuramente scordato da qualche parte!\n");
            response.append("Che pazienzaaa!!\n");
        }
        return response.toString();
    }

    String use() throws ObjectNotFoundInInventoryException, InventoryFullException {
        if (movement.getObject() != null && movement.getObject().isUsable()
                && game.getCurrentRoom().isObjectUsableHere(movement.getObject())
                && (game.getInventory().contains(movement.getObject())
                || game.getCurrentRoom().containsObject(movement.getObject()))) {

            switch (movement.getObject().getId()) {
                case SCREW:
                    game.getObject(SINK).setPushable(true);
                    game.setObjectNotAssignedRoom(movement.getObject());
                    game.getInventory().remove(movement.getObject());
                    response.append("Decidi di usare il cacciavite, chiunque abbia fissato quel lavandino " +
                            "non aveva una grande forza visto che le viti si svitano facilmente. Adesso che hai " +
                            "rimosso tuttte le viti, noti che il lavandino non è ben fissato\n");
                    game.increaseScore();
                    movement.getObject().setUsed(true);
                    break;

                case SCOTCH:
                    game.setObjectNotAssignedRoom(movement.getObject());
                    game.getInventory().remove(movement.getObject());
                    game.getInventory().add(game.getObject(COMBINATION));
                    response.append("Metti lo scotch sui numeri della porta, dallo scotch noti le impronte dei ultimi " +
                            "tasti schiacciati, ora indovinare il pin segreto sembra molto più semplice!\n");
                    game.increaseScore();
                    movement.getObject().setUsed(true);
                    break;

                case TOOLS:
                    response.append("Decidi di allenarti per un bel po’ di tempo… alla fine dell’allenamento " +
                            "ti senti già più forte!\n");

                    if (!movement.getObject().isUsed()) {
                        game.increaseScore();
                        movement.getObject().setUsed(true);
                    }
                    break;

                case BALL:
                    response.append("Il tempo è denaro, non penso sia il momento adatto per mettersi a giocare.\n");
                    movement.getObject().setUsed(true);
                    break;

                case SCALPEL:
                    game.setObjectNotAssignedRoom(movement.getObject());
                    game.getInventory().remove(movement.getObject());
                    response.append("Riesci subito a tirare fuori il bisturi dalla tasca, il gruppetto lo " +
                            "vede e capito il pericolo decide di lasciare stare (Mettere a rischio la " +
                            "vita per una panchina sarebbe veramente stupido) e vanno via con " +
                            "un'aria di vendetta. Ora sei solo vicino alla panchina.\n");
                    game.getRoom(FRONTBENCH).setDescription("Sei solo vicino alla panchina!");
                    game.getRoom(FRONTBENCH).setLook("E' una grossa panchina in legno un po' malandata, " +
                            "ci sei solo tu nelle vicinanze.");
                    game.getRoom(BENCH).setDescription("Dopo aver usato il bisturi, il giardino si è svuotato, ci sei" +
                            "solo tu qui.");
                    game.getRoom(BENCH).setLook("In lontananza vedi delle panchine tutte vuote!");
                    game.increaseScore();
                    movement.getObject().setUsed(true);
                    response.append("Riesci subito a tirare fuori il bisturi dalla tasca, il gruppetto lo vede e " +
                            "capito il pericolo decide di lasciare stare (Mettere a rischio la vita per una panchina " +
                            "sarebbe veramente stupido) e vanno via con un'aria di vendetta.\nOra sei solo vicino" +
                            " alla panchina.");
                    game.getCurrentRoom().setDescription("Sei solo vicino alla panchina!");
                    game.getCurrentRoom().setLook("E' una grossa panchina in legno un po' malandata, " +
                            "ci sei solo tu nelle vicinanze.");
                    game.getObject(SCREW).setPickupable(true);
                    movement.increaseCounterFaceUp();
                    break;

                case HACKSAW:
                    if (game.getObject(TOOLS).isUsed()) {
                        game.getRoom(AIR_DUCT_INFIRMARY).setLocked(false);
                        game.setObjectNotAssignedRoom(movement.getObject());
                        game.getInventory().remove(movement.getObject());
                        game.getRoom(AIR_DUCT_NORTH).removeObject(game.getObject(DESTROYABLE_GRATE));
                        response.append("Oh no! Il seghetto si è rotto e adesso ci sono pezzi di sega dappertutto," +
                                " per fortuna sei riuscito a rompere la grata\n");
                        response.append("Dopo esserti allenato duramente riesci a tagliare le sbarre " +
                                "con il seghetto, puoi proseguire nel condotto e capisci che quel condotto" +
                                " porta fino all’infermeria.\n");
                        game.increaseScore();
                        game.increaseScore();
                        movement.getObject().setUsed(true);
                    }
                    break;

                case SINK:
                    response.append("Decidi di lavarti le mani e il viso, l’igiene prima di tutto!\n");
                    movement.getObject().setUsed(true);
                    break;

                case GENERATOR_OBJ:
                    if (movement.getObject().isUsed() || game.getObject(BUTTON_GENERATOR).isPush()) {
                        response.append("Il generatore è gia stato usato, fai in fretta!!\n");

                    } else {
                        game.getObject(BUTTON_GENERATOR).setPush(true);
                        game.getRoom(DOOR_ISOLATION).setLocked(false);
                        game.getObject(LIGHTS).setOn(false);
                        response.append("Sembra che tutto il carcere sia nell’oscurità! È stata una bella mossa" +
                                " la tua, peccato che i poliziotti prevedono queste bravate e hanno un generatore" +
                                " di corrente ausiliario che si attiverà dopo un minuto dal blackout!\n");
                        game.increaseScore();
                        movement.getObject().setUsed(true);
                    }
                    break;

                case ACID:
                    game.getRoom(ENDGAME).setLocked(false);
                    game.setObjectNotAssignedRoom(movement.getObject());
                    game.getInventory().remove(movement.getObject());
                    response.append("Adesso la finestra presenta un buco, sarebbe meglio infilarsi dentro!\n");
                    game.increaseScore();
                    game.increaseScore();
                    game.increaseScore();
                    game.increaseScore();
                    movement.getObject().setUsed(true);
                    break;

                case COMBINATION:
                    if (!movement.getObject().isUsed()) {
                        game.setObjectNotAssignedRoom(movement.getObject());
                        game.getInventory().remove(movement.getObject());
                        game.getRoom(ISOLATION).setLocked(false);
                        response.append("La porta si apre! Puoi andare a est per entrare dentro l'isolamento oppure" +
                                " tornare indietro anche se hai poco tempo a disposizione!\n");
                        game.increaseScore();
                        movement.getObject().setUsed(true);
                    }
                    break;

                case BED:
                    response.append("Buona notte fiorellino!\n");
                    movement.getObject().setUsed(true);
                    break;

                case POSTER:
                    movement.getObject().setUsable(true);

                    // DON'T CHANGE THE ORDER
                    if (movement.getCounterFaceUp() == 0) {
                        response.append("Sei appena arrivato in carcere, perché non ti fai conoscere prendendo parte ad " +
                                "una rissa in giardino?\n");
                    } else if (!game.getObject(SCALPEL).isUsed()
                            && movement.getCounterFaceUp() == 1) {
                        response.append("Eccoti un consiglio: Dovresti andare nel giardino e utilizzare per bene quel " +
                                "bisturi, coraggio prendi la tua vendetta!\n");
                    } else if (!game.getInventory().contains(game.getObject(SCREW)) && !game.getObject(SCREW).isUsed()) {
                        response.append("Adesso dovresti prendere la vite, ti servirà molto per portare al termine" +
                                "la tua missione!\n");
                    } else if (game.getInventory().contains(game.getObject(SCREW))) {
                        response.append("Dovresti provare ad utilizzare la vite che hai in questa stanza, chissà cosa " +
                                "potrà capitare...\n");
                    } else if (game.getObject(SCREW).isUsed() && !game.getObject(SINK).isPush()) {
                        response.append("I tuoi genitori hanno anche figli normali? Come fai a non comprendere che è " +
                                "necessario spostare il lavandino!!\n");
                    } else if (((TokenPerson) game.getObject(GENNY_BELLO)).getInventory().contains(game.getObject(HACKSAW))
                            && !game.getInventory().contains(game.getObject(HACKSAW))) {
                        response.append("Dovresti cercare un utensile per rompere quelle grate che ti impediscono il " +
                                "passaggio!\n");
                    } else if (!game.getObject(HACKSAW).isUsed() && !game.getObject(TOOLS).isUsed() &&
                            game.getInventory().contains(game.getObject(HACKSAW))) {
                        response.append("Adesso che hai il seghetto dovresti aumentare un pò la tua massa muscolare\n");
                    } else if (!game.getObject(HACKSAW).isUsed() && game.getObject(TOOLS).isUsed()) {
                        response.append("Ti vedo in forma adesso, ora sarai sicuramente in grado di distruggere " +
                                "quella grata che è presente nel condotto d'aria\n");
                    } else if (game.getObject(HACKSAW).isUsed() && !game.getObject(SCOTCH).isUsed()
                            && !game.getInventory().contains(game.getObject(SCOTCH))) {
                        response.append("Nel condotto d'aria c'è qualcosa che ti tornerà utile più tardi!\n");
                    } else if (!game.getObject(GENERATOR_OBJ).isUsed() && !game.getObject(MEDICINE).isGiven()) {
                        response.append("Ti consiglio di cercare un pò nel condotto d'aria e spegnere il generatore" +
                                " ci vorrà un pò di buio per salvare tuo fratello\n");
                    } else if (game.getObject(GENERATOR_OBJ).isUsed() && !game.getObject(MEDICINE).isGiven()) {
                        response.append("Adesso che la prigione è buia potrai andare vicino alla porta d'isolamento e " +
                                "usare quello scotch che hai preso precedentemente\n");
                    } else if (game.getObject(SCOTCH).isUsed() && !game.getObject(MEDICINE).isGiven()) {
                        response.append("Dovresti usare quella combinazione che hai ottenuto utilizzando lo scotch" +
                                "nella stanza che precede l'isolamento, fai in fretta il tempo a tua disposizione" +
                                "sta per scadere!!!!\n");
                    } else if (game.getObject(COMBINATION).isUsed() && !game.getObject(MEDICINE).isGiven()) {
                        response.append("Cosa ci fai qui?? Dovresti dare la medicina a tuo fratello!!!!\n");
                    } else if (game.getObject(MEDICINE).isGiven()) {
                        response.append("Il tuo piano è quasi terminato, vai con Genny Bello in infermeria passando dal" +
                                "passaggio segreto! Buona fortuna, te ne servirà molta!!!!!!!!!\n");
                    } else {
                        response.append("Mi dispiace ma non ho suggerimenti da darti attualmente!!\n");
                    }
                    break;
            }

        } else if (movement.getObject() == null) {
            response.append("Sei sicuro di non voler usare niente?\n");
        } else if (!movement.getObject().isUsable()) {
            response.append("Mi dispiace ma questo oggetto non si può utilizzare\n");
        } else if (!game.getInventory().contains(movement.getObject())
                && !game.getCurrentRoom().containsObject(movement.getObject())) {
            response.append("Io non vedo nessun oggetto di questo tipo qui!\n");
        } else if (!game.getCurrentRoom().isObjectUsableHere(movement.getObject())) {
            response.append("C’è tempo e luogo per ogni cosa, ma non ora.\n");
        }

        if (movement.getObject() != null && movement.getObject().getId() == HACKSAW
                && !game.getObject(TOOLS).isUsed()
                && game.getCurrentRoom().isObjectUsableHere(game.getObject(HACKSAW))) {
            response.append("Il seghetto sembra molto arrugginito e non riesci a tagliare le sbarre " +
                    "della grata! In realtà la colpa non è totalmente del seghetto ma anche la tua " +
                    "poiché sei molto stanco e hai poca forza nelle braccia!\n");
        }
        return response.toString();
    }

    String open() throws ObjectNotFoundInRoomException {
        if (movement.getObject() != null
                && movement.getObject().isOpenable()
                && !movement.getObject().isOpen()
                && (game.getCurrentRoom().containsObject(movement.getObject()))) {
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
            response.append("Cosa vorresti aprire di preciso?\n");
        } else if (!game.getCurrentRoom().containsObject(movement.getObject())) {
            throw new ObjectNotFoundInRoomException();
        } else if (!movement.getObject().isOpenable()) {
            response.append("Sei serio? Vorresti veramente aprirlo?!\n");
            response.append("Sei fuori di testa!\n");
        } else if (movement.getObject().isOpen()) {
            response.append("E' gia' aperto testa di merda!\n");
        }
        return response.toString();
    }

    String close() throws ObjectNotFoundInRoomException {
        if (movement.getObject() != null
                && movement.getObject().isOpenable()
                && movement.getObject().isOpen()
                && (game.getCurrentRoom().containsObject(movement.getObject()))) {

            response.append("Hai chiuso ").append(movement.getObject().getName()).append("!\n");
            movement.getObject().setOpen(false);

        } else if (movement.getObject() == null) {
            response.append("Cosa vorresti chiudere di preciso?\n");
        } else if (!game.getCurrentRoom().containsObject(movement.getObject())) {
            throw new ObjectNotFoundInRoomException();
        } else if (!movement.getObject().isOpenable()) {
            response.append("Sei serio? Vorresti veramente chiuderlo?!\n");
            response.append("Sei fuori di testa!\n");
        } else if (!movement.getObject().isOpen()) {
            response.append("E' gia' chiuso testa di merda!\n");
        }
        return response.toString();
    }

    String pushAndPull() throws ObjectNotFoundInRoomException {
        if (movement.getObject() != null && movement.getObject().isPushable()
                && game.getCurrentRoom().containsObject(movement.getObject())) {

            switch (movement.getObject().getId()) {
                case LADDER:
                    if (game.getCurrentRoom().getId() == PASSAGE_SOUTH) {
                        game.getRoom(SECRET_PASSAGE).setObject(movement.getObject());
                        game.getRoom(PASSAGE_SOUTH).removeObject(movement.getObject());
                        response.append("La scala è stata spinta fino alla stanza a nord!\n");
                        game.increaseScore();
                    } else if (game.getCurrentRoom().getId() == SECRET_PASSAGE) {
                        game.getRoom(PASSAGE_NORTH).setObject(movement.getObject());
                        game.getRoom(SECRET_PASSAGE).removeObject(movement.getObject());
                        response.append("La scala è stata spinta fino alla stanza a nord e si è bloccata lì!\n");
                        game.increaseScore();
                    } else {
                        response.append("La scala è bloccata! Non esiste alcun modo per spostarla!\n");
                    }
                    break;

                case SINK:
                    if (game.getCurrentRoom().getId() == MAIN_CELL) {
                        if (movement.getObject().isPush()) {
                            response.append("Il Lavandino è già stato spostato!\n");
                        } else {
                            movement.getObject().setPush(true);
                            game.getRoom(SECRET_PASSAGE).setLocked(false);
                            response.append("Oissà!\n");
                            game.increaseScore();
                            game.increaseScore();
                        }
                    }
                    break;

                case PICTURE:
                    if (game.getCurrentRoom().getId() == INFIRMARY) {
                        // picture pushed
                        if (movement.getObject().isPush()) {
                            response.append("Il quadro è già stato spostato!\n");
                        } else {
                            movement.getObject().setPush(true);
                            game.getCurrentRoom().setObject(game.getObject(OLD_AIR_DUCT));
                            game.getObjectNotAssignedRoom().remove(game.getObject(OLD_AIR_DUCT));
                            response.append(game.getObject(OLD_AIR_DUCT).getDescription()).append("\n");
                        }
                    }
                    break;

                case BUTTON_GENERATOR:
                    if (game.getCurrentRoom().getId() == GENERATOR) {
                        // botton pushed
                        if (movement.getObject().isPush()) {
                            response.append("Il pulsante è già stato premuto! Fai in fretta!!!\n");
                        } else {
                            movement.getObject().setPush(true);
                            game.getObject(LIGHTS).setOn(false);
                            game.getObject(GENERATOR_OBJ).setUsed(true);
                            game.getRoom(DOOR_ISOLATION).setLocked(false);
                            response.append("Sembra che tutto il carcere sia nell’oscurità! È stata una bella mossa" +
                                    " la tua, peccato che i poliziotti prevedono queste bravate e hanno un " +
                                    "generatore di corrente ausiliario che si attiverà dopo un minuto dal blackout!\n");
                            game.increaseScore();
                            game.increaseScore();
                        }
                    }
                    break;
            }

        } else if (movement.getObject() == null) {
            response.append("Cosa vuoi spostare? L'aria?!?\n");
        } else if (!game.getCurrentRoom().containsObject(movement.getObject())) {
            throw new ObjectNotFoundInRoomException();
        } else if (!movement.getObject().isPushable()) {
            response.append("Puoi essere anche Hulk ma quell'oggetto non si può spostare!!!\n");
        }
        return response.toString();
    }

    String turnOn() {
        if (movement.getObject() != null && movement.getObject().isTurnOnAble()) {
            // lights case
            if (game.getCurrentRoom().getId() == GENERATOR) {
                // lights turnOFF
                if (movement.getObject().isOn()) {
                    response.append("Le luci sono già accese!\n");
                } else {
                    response.append("Le luci si accenderanno da sole tra qualche minuto, non avere paura!\n");
                }
            } else {
                response.append("Non puoi accendere nulla qui!\n");
            }
        } else if (movement.getObject() == null) {
            response.append("Cosa vuoi accendere esattamente???\n");
        } else if (!movement.getObject().isTurnOnAble()) {
            response.append("Come puoi accendere questo oggetto???\n");
        }
        return response.toString();
    }

    String turnOff() {
        if (movement.getObject() != null && movement.getObject().isTurnOnAble()) {
            // lights case
            if (game.getCurrentRoom().getId() == GENERATOR) {
                // lights turnOFF
                if (!movement.getObject().isOn()) {
                    response.append("Il pulsante è già stato premuto! Fai in fretta!!!\n");
                } else {
                    game.getObject(BUTTON_GENERATOR).setPush(true);
                    game.getObject(GENERATOR_OBJ).setUsable(true);
                    movement.getObject().setOn(false);
                    game.getRoom(DOOR_ISOLATION).setLocked(false);
                    response.append("Sembra che tutto il carcere sia nell’oscurità! È stata una bella mossa" +
                            " la tua, peccato che i poliziotti prevedono queste bravate e hanno un generatore" +
                            " di corrente ausiliario che si attiverà dopo un minuto dal blackout!\n");
                    game.increaseScore();
                    game.increaseScore();
                }
            } else {
                response.append("Non puoi spegnere nulla qui!\n");
            }
        } else if (movement.getObject() == null) {
            response.append("Cosa vuoi spegnere esattamente???\n");
        } else if (!movement.getObject().isTurnOnAble()) {
            response.append("Come puoi spegnere questo oggetto???\n");
        }
        return response.toString();
    }

    String end() {
        response.append("Non puoi usare quell'oggetto per uscire!\n");
        return response.toString();
    }

}
