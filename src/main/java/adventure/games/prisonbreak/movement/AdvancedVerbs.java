package adventure.games.prisonbreak.movement;

import adventure.exceptions.inventoryException.InventoryEmptyException;
import adventure.exceptions.inventoryException.InventoryFullException;
import adventure.exceptions.inventoryException.ObjectNotFoundInInventoryException;
import adventure.exceptions.objectsException.ObjectNotFoundInRoomException;
import adventure.games.prisonbreak.PrisonBreakGame;
import adventure.games.prisonbreak.TokenPerson;
import adventure.type.TokenObject;

import static adventure.games.prisonbreak.ObjectType.*;
import static adventure.games.prisonbreak.RoomType.*;

/**
 * @author Corona-Extra
 */
class AdvancedVerbs {

    private final Move movement;
    private final PrisonBreakGame game;
    private StringBuilder response = new StringBuilder();

    AdvancedVerbs(ControllerMovement controller) {
        movement = controller.getMove();
        game = movement.getPrisonBreakGame();
    }

    void resetResponse() {
        response = new StringBuilder();
    }

    String eat() throws ObjectNotFoundInInventoryException, InventoryEmptyException {
        if (movement.getObject() != null && movement.getObject().isEatable()
                && (game.getInventory().contains(movement.getObject())
                || game.getCurrentRoom().containsObject(movement.getObject()))) {
            //Food case
            if (game.getCurrentRoom().getObjects().contains(movement.getObject())) {
                game.getCurrentRoom().removeObject(movement.getObject());
                response.append("Gnam Gnam...");
            } else if (game.getInventory().getObjects().contains(movement.getObject())) {
                game.getInventory().remove(movement.getObject());
                response.append("Gnam Gnam...");
            }
        } else if (movement.getObject() == null) {
            response.append("Cosa vuoi mangiare??? Sembra non ci sia nulla di commestibile");
        } else if (!(game.getInventory().contains(movement.getObject())
                || game.getCurrentRoom().containsObject(movement.getObject()))) {
            response.append("Non penso si trovi qui questo oggetto!!! Compriamo un paio di occhiali?");
        } else if (!movement.getObject().isEatable()) {
            response.append("Sei veramente sicuro??? Non mi sembra una buona idea!");
        }
        return response.toString();
    }

    String standUp() {
        if (movement.getObject() == null) {
            //On foot
            if (game.getCurrentRoom().getObjects().stream()
                    .anyMatch(obj -> obj.isSitable() && obj.isSit())) {
                game.getCurrentRoom().getObjects().stream()
                        .filter(obj -> obj.isSitable() && obj.isSit())
                        .findFirst()
                        .ifPresent(obj -> obj.setSit(false));
                response.append("Oplà! Ti sei alzato!");
            } else {
                response.append("Sei così basso che non ti accorgi di stare già in piedi???");
            }
        } else {
            response.append("Non penso che questa cosa si possa fare ?!?");
        }
        return response.toString();
    }

    String sitDown() {
        if (movement.getObject() != null && movement.getObject().isSitable()
                && game.getCurrentRoom().containsObject(movement.getObject())) {

            //Bed case
            if (movement.getObject().getId() == BED) {
                if (game.getCurrentRoom().getObjects().stream()
                        .anyMatch(obj -> obj.isSitable() && !obj.isSit() && obj.getId() != WATER)) {
                    response.append("Buonanotte fiorellino!");
                } else {
                    response.append("Sei talmente stanco che nemmeno ti accorgi che sei già seduto???");
                }

                //Water case
            } else if (movement.getObject().getId() == WATER) {
                if (game.getCurrentRoom().getObjects().stream()
                        .anyMatch(obj -> obj.isSitable() && !obj.isSit() && obj.getId() == WATER)) {
                    response.append("Proprio ora devi farlo?");
                } else {
                    response.append("Sei già seduto! Ricordati di tirare lo scarico!");
                }
            } else if (movement.getObject().getId() == COT) {
                if (game.getCurrentRoom().getObjects().stream()
                        .anyMatch(obj -> obj.isSitable() && !obj.isSit() && obj.getId() == COT)) {
                    response.append("Non sembra il momento di riposarti!");
                } else {
                    response.append("Sei già sdraiato sul lettino!");
                }
            }
            movement.getObject().setSit(true);
            if (game.getCurrentRoom().getObjects().stream()
                    .filter(obj -> obj.isSitable() && obj.isSit()).count() > 1) {
                game.getCurrentRoom().getObjects().stream()
                        .filter(obj -> obj.isSitable() && obj.isSit() && obj.getId() != movement.getObject().getId())
                        .findFirst()
                        .ifPresent(obj -> obj.setSit(false));
            }
        } else if (movement.getObject() == null) {
            response.append("Sedersi sul pavimento non mi sembra una buona idea!");
        } else if (!game.getCurrentRoom().containsObject(movement.getObject())) {
            response.append("Non penso si trovi qui questo oggetto!!! Guarda meglio!");
        } else if (!movement.getObject().isSitable()) {
            response.append("Con quell'oggetto puoi fare altro ma di certo non sederti!");
        }
        return response.toString();
    }

    String climb() {
        if (game.getCurrentRoom().getId() == LOBBY) {
            game.setCurrentRoom(game.getCurrentRoom().getWest());
            movement.setMove(true);
        } else if (movement.getObject() != null && game.getCurrentRoom().containsObject(movement.getObject())) {
            if (movement.getObject().getId() == LADDER && game.getCurrentRoom().getId() == PASSAGE_NORTH) {
                game.getRoom(ON_LADDER).setLocked(false);
                game.setCurrentRoom(game.getCurrentRoom().getNorth());
                movement.setMove(true);
            } else if (movement.getObject().getId() != LADDER) {
                response.append("Non puoi salire su quell'oggetto");
            } else {
                response.append("Usa quell'oggetto altrove, qui acchiappi solo le mosche!");
            }
        } else if (movement.getObject() == null) {
            response.append("Sei Spiderman? Non puoi arrampicarti sui muri!");
        } else if (!game.getCurrentRoom().containsObject(movement.getObject())) {
            response.append("Non penso si trovi qui questo oggetto!!! Guarda meglio!");
        }
        return response.toString();
    }

    String getOff() {
        if (game.getCurrentRoom().getId() == LADDERS) {
            game.setCurrentRoom(game.getCurrentRoom().getEast());
            movement.setMove(true);
        } else if (game.getCurrentRoom().getId() == AIR_DUCT) {
            game.setCurrentRoom(game.getCurrentRoom().getSouth());
            movement.setMove(true);
            response.append("Usi la scala per scendere!");
        } else {
            response.append("Non puoi bucare il pavimento!");
        }
        return response.toString();
    }

    String enter() {
        if (game.getCurrentRoom().getId() == MAIN_CELL && game.getObject(SINK).isPush()) {
            game.setCurrentRoom(game.getCurrentRoom().getWest());
            movement.setMove(true);
        } else if (game.getCurrentRoom().getId() == ON_LADDER) {
            game.setCurrentRoom(game.getCurrentRoom().getNorth());
            movement.setMove(true);
        } else if (game.getCurrentRoom().getId() == DOOR_ISOLATION) {
            game.setCurrentRoom(game.getCurrentRoom().getEast());
            movement.setMove(true);
        } else {
            response.append("Non puoi entrare lì!");
        }
        return response.toString();
    }

    String exit() {
        if (game.getCurrentRoom().getId() == FRONTBENCH
                && !game.getInventory().contains(game.getObject(SCALPEL))) {
            game.setCurrentRoom(game.getCurrentRoom().getNorth());
            movement.setMove(true);
            response.append("Decidi di fuggire, ma prima o poi il pericolo dovrai affrontarlo!\n");
        } else {
            response.append("Perchè scappare?? Ma soprattutto da cosa??? Fifone!");
        }
        return response.toString();
    }

    String make() throws ObjectNotFoundInInventoryException, InventoryFullException, InventoryEmptyException {
        TokenObject substances = game.getObject(SUBSTANCES);

        if ((movement.getObject() != null
                && movement.getObject().isMixable()
                && (game.getInventory().contains(movement.getObject())
                || game.getCurrentRoom().containsObject(movement.getObject())))
                || ((movement.getObject() != null && movement.getObject().getId() == ACID))
                && (game.getInventory().contains(substances)
                || game.getCurrentRoom().containsObject(substances)) && game.getCurrentRoom().getId() == INFIRMARY) {

            if (game.getCurrentRoom().getObjects().contains(movement.getObject())
                    && !(movement.getObject().getId() == ACID)) {
                game.getCurrentRoom().removeObject(movement.getObject());
                game.getInventory().add(game.getObject(ACID));
                game.getObjectNotAssignedRoom().remove(game.getObject(ACID));
                movement.setMixed(true);
                game.increaseScore();
            } else if (movement.getObject().getId() != ACID
                    && game.getInventory().getObjects().contains(movement.getObject())) {
                game.getInventory().remove(movement.getObject());
                game.getInventory().add(game.getObject(ACID));
                game.getObjectNotAssignedRoom().remove(game.getObject(ACID));
                movement.setMixed(true);
                game.increaseScore();
            } else if (game.getCurrentRoom().getObjects().contains(substances)
                    && movement.getObject().getId() == ACID) {
                game.getCurrentRoom().removeObject(substances);
                game.getInventory().add(game.getObject(ACID));
                game.getObjectNotAssignedRoom().remove(game.getObject(ACID));
                movement.setMixed(true);
                game.increaseScore();
            } else if (game.getInventory().getObjects().contains(substances)
                    && movement.getObject().getId() == ACID) {
                game.getInventory().remove(substances);
                game.getInventory().add(game.getObject(ACID));
                game.getObjectNotAssignedRoom().remove(game.getObject(ACID));
                movement.setMixed(true);
                game.increaseScore();
            }
            if (movement.isMixed() || !game.getInventory().getObjects().contains(game.getObject(ACID))) {
                response.append("Hai creato un acido corrosivo, attento alle mani! ");
                response.append("L'acido è stato inserito nel tuo inventario!");
            } else {
                response.append("Hai già creato l'acido!!! Guarda bene nel tuo inventario!");
            }

        } else if (movement.getObject() == null) {
            response.append("Cosa vuoi creare esattamente?");
        } else if (!game.getCurrentRoom().containsObject(movement.getObject())) {
            response.append("Non penso si trovi qui questo oggetto!!! Guarda meglio!");
        } else if (!movement.getObject().isMixable()) {
            response.append("Non è una cosa che si può fare");
        } else if (game.getCurrentRoom().getId() != INFIRMARY) {
            response.append("Non puoi creare qui l'acido. Vai in infermeria se riesci!");
        }
        return response.toString();
    }

    String play() {
        if ((movement.getObject() != null && movement.getObject().isPlayable())
                || (movement.getObject() == null && game.getCurrentRoom().getId() == BASKET_CAMP)) {
            //Ball case
            if (game.getCurrentRoom().getId() == BASKET_CAMP) {
                //Ball play
                response.append("Il tempo è denaro, non penso sia il momento adatto per mettersi a giocare.");
            } else {
                response.append("Vuoi giocare con il tuo amico immaginario??");
            }
        } else if (movement.getObject() == null) {
            response.append("Con cosa vuoi giocare esattamente???");
        } else if (!movement.getObject().isTurnOnAble()) {
            response.append("Non puoi giocare con questo oggetto!!!");
        }
        return response.toString();
    }

    String workOut() throws ObjectNotFoundInRoomException {
        if (game.getCurrentRoom().getId() == GYM
                || (game.getCurrentRoom().getId() == GYM && movement.getObject() != null
                && movement.getObject().getId() == TOOLS)) {
            response.append("Decidi di allenarti per un bel po’ di tempo… alla fine dell’allenamento " +
                    "ti senti già più forte!");

            if (!game.getObject(TOOLS).isUsed()) {
                game.increaseScore();
                game.getObject(TOOLS).setUsed(true);
            }

        } else if (game.getCurrentRoom().getId() != GYM
                || (game.getCurrentRoom().getId() != GYM && movement.getObject() != null
                && movement.getObject().getId() == TOOLS)) {
            response.append("Ti sembra un posto dove potersi allenare?!!");
        } else if (!game.getCurrentRoom().containsObject(movement.getObject())) {
            throw new ObjectNotFoundInRoomException();
        } else {
            response.append("Non ci si può allenare con quest'oggetto!");
        }
        return response.toString();
    }

    String putIn() throws ObjectNotFoundInInventoryException {
        if (movement.getObject() != null && movement.getObject().isInsertable()) {
            if (game.getCurrentRoom().getId() == DOOR_ISOLATION) {
                game.getInventory().remove(movement.getObject());
                game.getRoom(ISOLATION).setLocked(false);
                movement.getObject().setUsed(true);
                response.append("La porta si apre! Puoi andare a est per entrare dentro l'isolamento oppure" +
                        " tornare indietro anche se hai poco tempo a disposizione!");
                game.increaseScore();
            } else {
                response.append("Non puoi inserire nulla qui!");
            }
        } else if (movement.getObject() == null) {
            response.append("Con cosa vuoi inserire??");
        } else if (!movement.getObject().isInsertable()) {
            response.append("Ho paura di quello che vuoi fare!!!");
        }
        return response.toString();
    }

    String talkTo() {
        if ((movement.getObject() instanceof TokenPerson && ((TokenPerson) movement.getObject()).isSpeakable())) {
            if (game.getCurrentRoom().getId() == CANTEEN) {
                response.append("Si avvicina a te e sussurrando ti chiede se sei interessato a qualche oggetto che " +
                        "lui possiede. Ovviamente ogni oggetto ha un costo ma tu non possiedi alcun soldo, " +
                        "per averne uno quindi sarai costretto a trattare.");
            } else if (game.getCurrentRoom().getId() == BROTHER_CELL) {
                response.append("Tuo fratello ti chiede il motivo della tua presenza nel carcere e tu gli " +
                        "racconti tutto il piano segreto per la fuga cosicché tuo fratello non venga " +
                        "giustiziato ingiustamente.\n\n" +
                        "Tuo fratello sembra al quanto felice e ti ringrazia enormemente di aver creato tutto" +
                        " questo piano per salvarlo! \n\n" +
                        "Il piano consiste nel far andare tuo fratello in qualche modo in infermeria!");
                game.getObject(MEDICINE).setGiveable(true);
            } else if (movement.getObject().getId() == GENNY_BELLO
                    && ((TokenPerson) movement.getObject()).isFollowHero()) {
                response.append("Dai, manca poco! Ce la possiamo fare, FORZA!!\n");
            }
        } else if (movement.getObject() == null) {
            response.append("Vuoi parlare da solo???");
        } else {
            response.append("Parlare con quell'oggetto non sembra essere la soluzione migliore!");
        }
        return response.toString();
    }

    String ask() {
        if ((movement.getObject() != null && movement.getObject().isAskable())) {
            if (game.getCurrentRoom().getId() == CANTEEN) {
                if (movement.getObject().getId() == DRUG) {
                    response.append("Meglio continuare il piano di fuga da lucidi e fortunatamente non hai soldi " +
                            "con te per acquistarla! Ti ricordo che il tuo piano è fuggire di prigione e non " +
                            "rimanerci qualche anno di più!");
                } else if (movement.getObject().getId() == VIDEOGAME) {
                    response.append("Sarebbe molto bello se solo avessi 8 anni! Quando uscirai di prigione" +
                            " avrai molto tempo per giocare anche a videogiochi migliori!");
                } else if (movement.getObject().getId() == HACKSAW && !movement.getObject().isAsked()) {
                    response.append("Il detenuto ti dice che a tutti quelli che ha venduto un seghetto avevano " +
                            "sempre un piano di fuga per evadere di prigione che però non sono mai andati a " +
                            "buon fine essendo un carcere di massima sicurezza ( in effetti con un seghetto " +
                            "in prigione non hai tante alternative). In cambio gli dovrai confessare tutto " +
                            "il tuo piano di fuga e farlo fuggire insieme a te, cosa scegli???");
                    movement.getObject().setAsked(true);
                } else if (movement.getObject().isAsked()) {
                    response.append("Hai già chiesto per quell'oggetto!!!");
                }
            }
        } else if (movement.getObject() == null) {
            response.append("Cosa devi chiedere?");
        } else if (!movement.getObject().isAskable()) {
            response.append("Non sembra la soluzione giusta!");
        }
        return response.toString();
    }

    String accept() {
        if (game.getObject(HACKSAW).isAsked()
                && game.getCurrentRoom().getId() == CANTEEN
                && !game.getObject(HACKSAW).isAccept()) {
            response.append("Gli racconti tutto il piano segreto di fuga, il detenuto capisce che questo è " +
                    "il miglior piano che abbia sentito fin ora e accetta subito dandoti il seghetto e " +
                    "si unisce a te. Gli dici di incontrarsi stanotte alle 21:00 di fronte alla tua cella!\n");
            response.append("Ora puoi prendere il seghetto da Genny!");
            game.getObject(HACKSAW).setPickupable(true);
            game.getObject(HACKSAW).setAccept(true);
        } else if (!game.getObject(HACKSAW).isAsked()) {
            response.append("Non puoi accettare una cosa che non hai chiesto!!!");
        } else if (game.getObject(HACKSAW).isAccept()) {
            response.append("Ormai hai già accettato! Ci avresti potuto pensare prima!");
        } else if (game.getCurrentRoom().getId() != CANTEEN) {
            response.append("Cosa vuoi accettare? Nulla???");
        }
        return response.toString();
    }

    String decline() {
        if (game.getObject(HACKSAW).isAsked() && game.getCurrentRoom().getId() == CANTEEN
                && !game.getObject(HACKSAW).isAccept() && !game.getInventory().contains(game.getObject(HACKSAW))) {
            response.append("Decidi di rifiutare l’accordo, quando vuoi il detenuto sarà sempre pronto " +
                    "a ricontrattare!");
            game.getObject(HACKSAW).setPickupable(false);
            game.getObject(HACKSAW).setAsked(false);
            game.getObject(HACKSAW).setAccept(false);
        } else if (!game.getObject(HACKSAW).isAsked()) {
            response.append("Non puoi rifiutare una cosa che non hai chiesto!!!");
        } else if (game.getObject(HACKSAW).isAccept() || game.getInventory().contains(game.getObject(HACKSAW))) {
            response.append("Ormai hai già accettato! Ci avresti potuto pensare prima!");
        } else if (game.getCurrentRoom().getId() != CANTEEN) {
            response.append("Cosa vuoi rifiutare? Nulla???");
        }
        return response.toString();
    }

    String faceUp() throws ObjectNotFoundInInventoryException {
        if (game.getCurrentRoom().getId() == FRONTBENCH && movement.getCounterFaceUp() == 0) {
            response.append("Sai benissimo che in un carcere non si possono comprare panchine e ti avvicini " +
                    "nuovamente con l’intento di prendere l’oggetto. Il gruppetto ti blocca e il piu' grosso " +
                    "di loro ti tira un pugno contro il viso... Perdendo i sensi non ti ricordi piu' nulla e" +
                    " ti svegli in infermeria.\n");
            game.setCurrentRoom(game.getRoom(INFIRMARY));
            game.increaseScore();
            movement.setMove(true);
            movement.increaseCounterFaceUp();
        } else if (game.getCurrentRoom().getId() == FRONTBENCH && !game.getObject(SCALPEL).isUsed()
                && game.getInventory().contains(game.getObject(SCALPEL)) && movement.getCounterFaceUp() == 1) {
            game.increaseScore();
            game.setObjectNotAssignedRoom(game.getObject(SCALPEL));
            game.getInventory().remove(game.getObject(SCALPEL));
            game.getObject(SCALPEL).setUsed(true);
            game.getObject(SCREW).setPickupable(true);
            response.append("Riesci subito a tirare fuori il bisturi dalla tasca, il gruppetto lo vede e " +
                    "capito il pericolo decide di lasciare stare (Mettere a rischio la vita per una panchina " +
                    "sarebbe veramente stupido) e vanno via con un'aria di vendetta.\nOra sei solo vicino" +
                    " alla panchina.");
            game.getCurrentRoom().setDescription("Sei solo vicino alla panchina!");
            game.getCurrentRoom().setLook("E' una grossa panchina in legno un po' malandata, " +
                    "ci sei solo tu nelle vicinanze.");
            game.getRoom(BENCH).setDescription("Dopo aver usato il bisturi, il giardino si è svuotato, ci sei " +
                    "solo tu qui.");
            game.getRoom(BENCH).setLook("In lontananza vedi delle panchine tutte vuote!");
            movement.increaseCounterFaceUp();
        } else if (game.getCurrentRoom().getId() != FRONTBENCH
                || game.getObject(SCALPEL).isUsed()
                || movement.getCounterFaceUp() >= 2) {
            response.append("Ehi John Cena, non puoi affrontare nessuno qui!!!");
        }
        return response.toString();
    }

    String destroy() throws ObjectNotFoundInInventoryException, ObjectNotFoundInRoomException {
        if (movement.getObject() != null
                && movement.getObject().getId() == DESTROYABLE_GRATE
                && game.getCurrentRoom().getId() == AIR_DUCT_NORTH
                && game.getInventory().contains(game.getObject(HACKSAW))
                && game.getObject(TOOLS).isUsed()
                && !game.getObject(HACKSAW).isUsed()) {
            game.getRoom(AIR_DUCT_INFIRMARY).setLocked(false);
            game.setObjectNotAssignedRoom(game.getObject(HACKSAW));
            game.getInventory().remove(game.getObject(HACKSAW));
            game.getRoom(AIR_DUCT_NORTH).removeObject(movement.getObject());
            response.append("Oh no! Il seghetto si è rotto e adesso ci sono pezzi di sega dappertutto, per " +
                    "fortuna sei riuscito a rompere la grata");
            response.append("Dopo esserti allenato duramente riesci a tagliare le sbarre con il seghetto, " +
                    "puoi proseguire nel condotto e capisci che quel condotto porta fino all’infermeria.");
            game.increaseScore();
            game.increaseScore();
        } else if (movement.getObject() == null) {
            response.append("Cosa vuoi rompere???");
        } else if (!game.getCurrentRoom().containsObject(movement.getObject())) {
            throw new ObjectNotFoundInRoomException();
        } else if (movement.getObject().getId() != DESTROYABLE_GRATE) {
            response.append("Non puoi distruggere questo oggetto!");
        } else if (game.getCurrentRoom().getId() != AIR_DUCT_NORTH) {
            response.append("Non puoi distruggere niente qui!");
        } else if (!game.getInventory().contains(game.getObject(HACKSAW))) {
            response.append("Come puoi rompere la grata? Non hai nessun oggetto utile!");
        } else if (!game.getObject(TOOLS).isUsed()) {
            response.append("Il seghetto sembra molto arrugginito e non riesci a tagliare le sbarre della grata! " +
                    "In realtà la colpa non è totalmente del seghetto ma anche la tua poichè sei molto stanco " +
                    "e hai poca forza nelle braccia!");
        } else if (!game.getObject(MEDICINE).isGiven()) {
            response.append("Dopo esserti allenato duramente riesci a tagliare le sbarre con il seghetto, " +
                    "puoi proseguire nel condotto e capisci che quel condotto porta fino all’infermeria. " +
                    "Avrebbe più senso proseguire solo se la tua squadra è al completo… non ti sembri manchi " +
                    "la persona più importante???");
        } else if (!game.getObject(HACKSAW).isUsed()) {
            response.append("Hai già usato quell'oggetto! Non puoi più rompere nulla!");
        }
        return response.toString();
    }

    String give() throws ObjectNotFoundInInventoryException {
        if (movement.getObject() != null && movement.getObject().isGiveable()
                && game.getCurrentRoom().getId() == BROTHER_CELL
                && movement.getObject().getId() == MEDICINE) {
            game.getInventory().remove(movement.getObject());
            response.append("Sai benissimo che tuo fratello ha una forte allergia alle ortiche" +
                    " e che non potrebbe prendere quel farmaco. Tu decidi di darlo ugualmente " +
                    "in modo che il tuo piano venga attuato!");
            movement.getObject().setGiven(true);
            response.append("Appena dato il farmaco decidi di fuggire fuori dalla cella isolamento prima" +
                    " che le luci si accendano e le guardie ti scoprano!!!");
            game.setCurrentRoom(game.getRoom(OUT_ISOLATION));
            movement.setMove(true);
            response.append("Sono le 20:55 hai esattamente 5 minuti per tornare alla tua cella" +
                    " e completare il tuo piano! Speriamo che abbiano portato tuo fratello in infermeria!\n\n");
            game.getCurrentRoom().setLook("Sono le 20:55 hai esattamente 5 minuti per tornare alla tua cella" +
                    "e completare il tuo piano! Speriamo che abbiano portato tuo fratello in infermeria!");

            game.getRoom(INFIRMARY).setLocked(false);

            //Lock the other rooms to lead the user to the end of the game
            game.getRoom(DOOR_ISOLATION).setLocked(true);
            game.getRoom(CANTEEN).setLocked(true);
            game.getRoom(GYM).setLocked(true);
            game.getRoom(GARDEN).setLocked(true);

            //Set the description of the principal cell
            game.getRoom(MAIN_CELL).setDescription("Sei arrivato alla tua cella , ad aspettarti puntuale " +
                    "c’è il tuo amichetto Genny. È ora di attuare il piano!");
            game.getRoom(MAIN_CELL).setLook("Non perdere ulteriore tempo, bisogna attuare il piano " +
                    "e scappare via da qui!");
            game.getRoom(AIR_DUCT_INFIRMARY).setLook("Dal condotto d'aria riesci a vedere tuo fratello " +
                    "nell'infermeria che ti aspetta!");

            game.increaseScore();
            game.increaseScore();
            game.increaseScore();
        } else if (movement.getObject() == null) {
            response.append("Cosa vuoi dare di preciso?");
        } else if (movement.getObject() instanceof TokenPerson) {
            response.append("Una persona non è un oggetto che si può regalare!!!");
        } else {
            response.append("Non puoi dare quest'oggetto a nessuno imbecille!");
        }
        return response.toString();
    }

}
