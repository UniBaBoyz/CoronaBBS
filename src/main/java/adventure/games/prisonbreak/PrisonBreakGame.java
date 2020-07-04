package adventure.games.prisonbreak;

import adventure.exceptions.LockedRoomException;
import adventure.exceptions.NotAccessibleRoomException;
import adventure.exceptions.inventoryException.InventoryEmptyException;
import adventure.exceptions.inventoryException.InventoryFullException;
import adventure.exceptions.inventoryException.ObjectNotFoundInInventoryException;
import adventure.exceptions.objectsException.ObjectNotFoundInRoomException;
import adventure.exceptions.objectsException.ObjectsAmbiguityException;
import adventure.games.GameDescription;
import adventure.parser.ParserOutput;
import adventure.type.Inventory;
import adventure.type.TokenObject;
import adventure.type.TokenObjectContainer;
import adventure.type.VerbType;

import java.io.PrintStream;

import static adventure.games.prisonbreak.ObjectType.*;
import static adventure.games.prisonbreak.RoomType.*;
import static adventure.utils.Utils.AFTER_FOUGHT;
import static adventure.utils.Utils.FOUGHT_SCORE;


/**
 * @author Corona-Extra
 */
public class PrisonBreakGame extends GameDescription {

    public PrisonBreakGame() {
        new PrisonBreakVerbs(this);
        new PrisonBreakRooms(this);
        new PrisonBreakObjects(this);

        //Set starting room
        setCurrentRoom(getRoom(MAIN_CELL));

        //Set Inventory
        setInventory(new Inventory(5));
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
                } else if (object != null && object.isPickupable()
                        && getCurrentRoom().containsObject(object)) {

                    //FIXME se lascia e riprende questi oggetti, il punteggio aumenta sempre
                    if (object.getId() == SCALPEL || object.getId() == SCOTCH || object.getId() == SCREW) {
                        increaseScore();
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
                        getInventory().remove(object);
                        out.println("Decidi di usare il cacciavite, chiunque abbia fissato quel lavandino non aveva una " +
                                "grande forza visto che le viti si svitano facilmente. Appena hai tolto l’ultima vite, " +
                                "sposti il lavandino e vedi un passaggio segreto");
                        increaseScore();
                        object.setUsed(true);

                    } else if (object.getId() == SCOTCH) {
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
                        getInventory().remove(object);
                        out.println("La finestra adesso presenta un buco, sarebbe meglio infilarsi dentro!");
                        increaseScore();
                        increaseScore();
                        increaseScore();
                        object.setUsed(true);

                    } else if (object.getId() == COMBINATION && !object.isUsed()) {
                        getInventory().remove(object);
                        getRoom(ISOLATION).setLocked(false);
                        out.println("La porta si apre! Puoi andare a est per entrare dentro l'isolamento oppure" +
                                " tornare indietro anche se hai poco tempo a disposizione!");
                        increaseScore();
                        object.setUsed(true);
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
                if (getCurrentRoom().getId() == FRONTBENCH && getScore() < FOUGHT_SCORE) {
                    out.println("Sai benissimo che in un carcere non si possono comprare panchine e ti avvicini " +
                            "nuovamente con l’intendo di prendere l’oggetto. Il gruppetto ti blocca e il piu' grosso " +
                            "di loro ti tira un pugno contro il viso... Perdendo i sensi non ti ricordi piu' nulla e" +
                            " ti svegli in infermeria.");
                    setCurrentRoom(getRoom(INFIRMARY));
                    increaseScore();
                    getObject(SCREW).setPickupable(true);
                    move = true;
                } else if (getCurrentRoom().getId() == FRONTBENCH
                        && getScore() < AFTER_FOUGHT
                        && !getObject(SCALPEL).isUsed() && getInventory().contains(getObject(SCALPEL))) {
                    increaseScore();
                    getInventory().remove(getObject(SCALPEL));
                    getObject(SCALPEL).setUsed(true);
                    out.println("Riesci subito a tirare fuori il bisturi dalla tasca, il gruppetto lo vede e " +
                            "capito il pericolo decide di lasciare stare (Mettere a rischio la vita per una panchina " +
                            "sarebbe veramente stupido) e vanno via con un'aria di vendetta.\nOra sei solo vicino" +
                            " alla panchina.");
                    getCurrentRoom().setDescription("Sei solo vicino alla panchina!");
                    getCurrentRoom().setLook("E' una grossa panchina in legno un po' malandata, " +
                            "ci sei solo tu nelle vicinanze. A terra noti la vite!");
                } else if (getCurrentRoom().getId() != FRONTBENCH || getScore() >= AFTER_FOUGHT
                        || getObject(SCALPEL).isUsed()) {
                    out.println("Ehi John Cena, non puoi affrontare nessuno qui!!!");
                }
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
