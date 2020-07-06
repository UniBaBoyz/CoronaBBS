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

class AdvancedVerbs {

    private final PrisonBreakGame game = ControllerMove.getInstance().getGame();
    private final TokenObject object = ControllerMove.getInstance().getObject();
    private final StringBuilder response = ControllerMove.getInstance().getResponse();
    private final boolean mixed = ControllerMove.getInstance().isMixed();
    private final short counterFaceUp = ControllerMove.getInstance().getCounterFaceUp();

    void eat() throws ObjectNotFoundInInventoryException, InventoryEmptyException {
        if (object != null && object.isEatable() && (game.getInventory().contains(object)
                || game.getCurrentRoom().containsObject(object))) {
            //Food case
            if (game.getCurrentRoom().getObjects().contains(object)) {
                game.getCurrentRoom().removeObject(object);
                response.append("Gnam Gnam...\n");
            } else if (game.getInventory().getObjects().contains(object)) {
                game.getInventory().remove(object);
                response.append("Gnam Gnam...\n");
            }
        } else if (object == null) {
            response.append("Cosa vuoi mangiare??? Sembra non ci sia nulla di commestibile\n");
        } else if (!(game.getInventory().contains(object)
                || game.getCurrentRoom().containsObject(object))) {
            response.append("Non penso si trovi qui questo oggetto!!! Compriamo un paio di occhiali?\n");
        } else if (!object.isEatable()) {
            response.append("Sei veramente sicuro??? Non mi sembra una buona idea!\n");
        }
    }

    void standUp() {
        if (object == null) {
            //On foot
            if (game.getCurrentRoom().getObjects().stream()
                    .anyMatch(obj -> obj.isSitable() && obj.isSit())) {
                game.getCurrentRoom().getObjects().stream()
                        .filter(obj -> obj.isSitable() && obj.isSit())
                        .findFirst()
                        .ifPresent(obj -> obj.setSit(false));
                response.append("Oplà! Ti sei alzato!\n");
            } else {
                response.append("Sei così basso che non ti accorgi di stare già in piedi???\n");
            }
        } else {
            response.append("Non penso che questa cosa si possa fare ?!?\n");
        }
    }

    void sitDown() {
        if (object != null && object.isSitable() && game.getCurrentRoom().containsObject(object)) {

            //Bed case
            if (object.getId() == BED) {
                if (game.getCurrentRoom().getObjects().stream()
                        .anyMatch(obj -> obj.isSitable() && !obj.isSit() && obj.getId() != WATER)) {
                    response.append("Buonanotte fiorellino!\n");
                } else {
                    response.append("Sei talmente stanco che nemmeno ti accorgi che sei già seduto???\n");
                }

                //Water case
            } else if (object.getId() == WATER) {
                if (game.getCurrentRoom().getObjects().stream()
                        .anyMatch(obj -> obj.isSitable() && !obj.isSit() && obj.getId() == WATER)) {
                    response.append("Proprio ora devi farlo?\n");
                } else {
                    response.append("Sei già seduto! Ricordati di tirare lo scarico!\n");
                }
            } else if (object.getId() == COT) {
                if (game.getCurrentRoom().getObjects().stream()
                        .anyMatch(obj -> obj.isSitable() && !obj.isSit() && obj.getId() == COT)) {
                    response.append("Non sembra il momento di riposarti!\n");
                } else {
                    response.append("Sei già sdraiato sul lettino!\n");
                }
            }
            object.setSit(true);
            if (game.getCurrentRoom().getObjects().stream()
                    .filter(obj -> obj.isSitable() && obj.isSit()).count() > 1) {
                game.getCurrentRoom().getObjects().stream()
                        .filter(obj -> obj.isSitable() && obj.isSit() && obj.getId() != object.getId())
                        .findFirst()
                        .ifPresent(obj -> obj.setSit(false));
            }
        } else if (object == null) {
            response.append("Sedersi sul pavimento non mi sembra una buona idea!\n");
        } else if (!game.getCurrentRoom().containsObject(object)) {
            response.append("Non penso si trovi qui questo oggetto!!! Guarda meglio!\n");
        } else if (!object.isSitable()) {
            response.append("Con quell'oggetto puoi fare altro ma di certo non sederti!\n");
        }
    }

    void climb() {
        if (game.getCurrentRoom().getId() == LOBBY) {
            game.setCurrentRoom(game.getCurrentRoom().getWest());
            ControllerMove.getInstance().setMove(true);
        } else if (object != null && game.getCurrentRoom().containsObject(object)) {
            if (object.getId() == LADDER && game.getCurrentRoom().getId() == PASSAGE_NORTH) {
                game.getRoom(ON_LADDER).setLocked(false);
                game.setCurrentRoom(game.getCurrentRoom().getNorth());
                ControllerMove.getInstance().setMove(true);
            } else if (object.getId() != LADDER) {
                response.append("Non puoi salire su quell'oggetto\n");
            } else {
                response.append("Usa quell'oggetto altrove, qui acchiappi solo le mosche!\n");
            }
        } else if (object == null) {
            response.append("Sei Spiderman? Non puoi arrampicarti sui muri!\n");
        } else if (!game.getCurrentRoom().containsObject(object)) {
            response.append("Non penso si trovi qui questo oggetto!!! Guarda meglio!\n");
        }
    }

    void getOff() {
        if (game.getCurrentRoom().getId() == LADDERS) {
            game.setCurrentRoom(game.getCurrentRoom().getEast());
            ControllerMove.getInstance().setMove(true);
        } else if (game.getCurrentRoom().getId() == AIR_DUCT) {
            game.setCurrentRoom(game.getCurrentRoom().getSouth());
            ControllerMove.getInstance().setMove(true);
            response.append("Usi la scala per scendere!\n");
        } else {
            response.append("Non puoi bucare il pavimento!\n");
        }
    }

    void enter() {
        if (game.getCurrentRoom().getId() == MAIN_CELL && game.getObject(SINK).isPush()) {
            game.setCurrentRoom(game.getCurrentRoom().getWest());
            ControllerMove.getInstance().setMove(true);
        } else if (game.getCurrentRoom().getId() == ON_LADDER) {
            game.setCurrentRoom(game.getCurrentRoom().getNorth());
            ControllerMove.getInstance().setMove(true);
        } else if (game.getCurrentRoom().getId() == DOOR_ISOLATION) {
            game.setCurrentRoom(game.getCurrentRoom().getEast());
            ControllerMove.getInstance().setMove(true);
        } else {
            response.append("Non puoi entrare lì!\n");
        }
    }

    void exit() {
        if (game.getCurrentRoom().getId() == FRONTBENCH && !game.getInventory().contains(game.getObject(SCALPEL))) {
            game.setCurrentRoom(game.getCurrentRoom().getNorth());
            ControllerMove.getInstance().setMove(true);
            response.append("Decidi di fuggire, ma prima o poi il pericolo dovrai affrontarlo!\n\n");
        } else {
            response.append("Perchè scappare?? Ma soprattutto da cosa??? Fifone!\n");
        }
    }

    void make() throws ObjectNotFoundInInventoryException, InventoryFullException, InventoryEmptyException {
        TokenObject substances = game.getObject(SUBSTANCES);

        if ((object != null
                && object.isMixable()
                && (game.getInventory().contains(object)
                || game.getCurrentRoom().containsObject(object)))
                || ((object != null && object.getId() == ACID))
                && (game.getInventory().contains(substances)
                || game.getCurrentRoom().containsObject(substances))) {

            if (game.getCurrentRoom().getObjects().contains(object) && !(object.getId() == ACID)) {
                game.getCurrentRoom().removeObject(object);
                game.getInventory().add(game.getObject(ACID));
                game.getObjectNotAssignedRoom().remove(game.getObject(ACID));
                ControllerMove.getInstance().setMixed(true);
                game.increaseScore();
            } else if (object.getId() != ACID && game.getInventory().getObjects().contains(object)) {
                game.getInventory().remove(object);
                game.getInventory().add(game.getObject(ACID));
                game.getObjectNotAssignedRoom().remove(game.getObject(ACID));
                ControllerMove.getInstance().setMixed(true);
                game.increaseScore();
            } else if (game.getCurrentRoom().getObjects().contains(substances)
                    && object.getId() == ACID) {
                game.getCurrentRoom().removeObject(substances);
                game.getInventory().add(game.getObject(ACID));
                game.getObjectNotAssignedRoom().remove(game.getObject(ACID));
                ControllerMove.getInstance().setMixed(true);
                game.increaseScore();
            } else if (game.getInventory().getObjects().contains(substances)
                    && object.getId() == ACID) {
                game.getInventory().remove(substances);
                game.getInventory().add(game.getObject(ACID));
                game.getObjectNotAssignedRoom().remove(game.getObject(ACID));
                ControllerMove.getInstance().setMixed(true);
                game.increaseScore();
            }
            if (mixed || !game.getInventory().getObjects().contains(game.getObject(ACID))) {
                response.append("Hai creato un acido corrosivo, attento alle mani!\n");
                response.append("L'acido è stato inserito nel tuo inventario!\n");
            } else {
                response.append("Hai già creato l'acido!!! Guarda bene nel tuo inventario!\n");
            }

        } else if (object == null) {
            response.append("Cosa vuoi creare esattamente?\n");
        } else if (!game.getCurrentRoom().containsObject(object)) {
            response.append("Non penso si trovi qui questo oggetto!!! Guarda meglio!\n");
        } else if (!object.isMixable()) {
            response.append("Non è una cosa che si può fare\n");
        }
    }

    void play() {
        if ((object != null && object.isPlayable())
                || (object == null && game.getCurrentRoom().getId() == BASKET_CAMP)) {
            //Ball case
            if (game.getCurrentRoom().getId() == BASKET_CAMP) {
                //Ball play
                response.append("Il tempo è denaro, non penso sia il momento adatto per mettersi a giocare.\n");
            } else {
                response.append("Vuoi giocare con il tuo amico immaginario??\n");
            }
        } else if (object == null) {
            response.append("Con cosa vuoi giocare esattamente???\n");
        } else if (!object.isTurnOnAble()) {
            response.append("Non puoi giocare con questo oggetto!!!\n");
        }
    }

    void workOut() throws ObjectNotFoundInRoomException {
        if (game.getCurrentRoom().getId() == GYM
                || (game.getCurrentRoom().getId() == GYM && object != null && object.getId() == TOOLS)) {
            response.append("Decidi di allenarti per un bel po’ di tempo… alla fine dell’allenamento " +
                    "ti senti già più forte!\n");

            if (!game.getObject(TOOLS).isUsed()) {
                game.increaseScore();
                game.getObject(TOOLS).setUsed(true);
            }

        } else if (game.getCurrentRoom().getId() != GYM
                || (game.getCurrentRoom().getId() != GYM && object != null && object.getId() == TOOLS)) {
            response.append("Ti sembra un posto dove potersi allenare?!!\n");
        } else if (!game.getCurrentRoom().containsObject(object)) {
            throw new ObjectNotFoundInRoomException();
        } else {
            response.append("Non ci si può allenare con quest'oggetto!\n");
        }
    }

    void putIn() throws ObjectNotFoundInInventoryException {
        if (object != null && object.isInsertable()) {
            if (game.getCurrentRoom().getId() == DOOR_ISOLATION) {
                game.getInventory().remove(object);
                game.getRoom(ISOLATION).setLocked(false);
                object.setUsed(true);
                response.append("La porta si apre! Puoi andare a est per entrare dentro l'isolamento oppure" +
                        " tornare indietro anche se hai poco tempo a disposizione!\n");
                game.increaseScore();
            } else {
                response.append("Non puoi inserire nulla qui!\n");
            }
        } else if (object == null) {
            response.append("Con cosa vuoi inserire??\n");
        } else if (!object.isInsertable()) {
            response.append("Ho paura di quello che vuoi fare!!!\n");
        }
    }

    void talkTo() {
        if ((object instanceof TokenPerson && ((TokenPerson) object).isSpeakable())) {
            if (game.getCurrentRoom().getId() == CANTEEN) {
                response.append("Si avvicina a te e sussurrando ti chiede se sei interessato a qualche oggetto che " +
                        "lui possiede. Ovviamente ogni oggetto ha un costo ma tu non possiedi alcun soldo, " +
                        "per averne uno quindi sarai costretto a trattare.\n");
            } else if (game.getCurrentRoom().getId() == BROTHER_CELL) {
                response.append("Tuo fratello ti chiede il motivo della tua presenza nel carcere e tu gli " +
                        "racconti tutto il piano segreto per la fuga cosicché tuo fratello non venga " +
                        "giustiziato ingiustamente.\n\n" +
                        "Tuo fratello sembra al quanto felice e ti ringrazia enormemente di aver creato tutto" +
                        " questo piano per salvarlo! \n\n" +
                        "Il piano consiste nel far andare tuo fratello in qualche modo in infermeria!");
                game.getObject(MEDICINE).setGiveable(true);
            }
        } else if (object == null) {
            response.append("Vuoi parlare da solo???\n");
        } else {
            response.append("Parlare con quell'oggetto non sembra essere la soluzione migliore!\n");
        }
    }

    void ask() {
        if ((object != null && object.isAskable())) {
            if (game.getCurrentRoom().getId() == CANTEEN) {
                if (object.getId() == DRUG) {
                    response.append("Meglio continuare il piano di fuga da lucidi e fortunatamente non hai soldi " +
                            "con te per acquistarla! Ti ricordo che il tuo piano è fuggire di prigione e non " +
                            "rimanerci qualche anno di più!\n");
                } else if (object.getId() == VIDEOGAME) {
                    response.append("Sarebbe molto bello se solo avessi 8 anni! Quando uscirai di prigione" +
                            " avrai molto tempo per giocare anche a videogiochi migliori!\n");
                } else if (object.getId() == HACKSAW && !object.isAsked()) {
                    response.append("Il detenuto ti dice che a tutti quelli che ha venduto un seghetto avevano " +
                            "sempre un piano di fuga per evadere di prigione che però non sono mai andati a " +
                            "buon fine essendo un carcere di massima sicurezza ( in effetti con un seghetto " +
                            "in prigione non hai tante alternative). In cambio gli dovrai confessare tutto " +
                            "il tuo piano di fuga e farlo fuggire insieme a te, cosa scegli???\n");
                    object.setAsked(true);
                } else if (object.isAsked()) {
                    response.append("Hai già chiesto per quell'oggetto!!!\n");
                }
            }
        } else if (object == null) {
            response.append("Cosa devi chiedere?\n");
        } else if (!object.isAskable()) {
            response.append("Non sembra la soluzione giusta!\n");
        }
    }

    void accept() {
        if (game.getObject(HACKSAW).isAsked()
                && game.getCurrentRoom().getId() == CANTEEN
                && !game.getObject(HACKSAW).isAccept()) {
            response.append("Gli racconti tutto il piano segreto di fuga, il detenuto capisce che questo è " +
                    "il miglior piano che abbia sentito fin ora e accetta subito dandoti il seghetto e " +
                    "si unisce a te. Gli dici di incontrarsi stanotte alle 21:00 di fronte alla tua cella!\n");
            response.append("Ora puoi prendere il seghetto da Genny!\n");
            game.getObject(HACKSAW).setPickupable(true);
            game.getObject(HACKSAW).setAccept(true);
        } else if (!game.getObject(HACKSAW).isAsked()) {
            response.append("Non puoi accettare una cosa che non hai chiesto!!!\n");
        } else if (game.getObject(HACKSAW).isAccept()) {
            response.append("Ormai hai già accettato! Ci avresti potuto pensare prima!\n");
        } else if (game.getCurrentRoom().getId() != CANTEEN) {
            response.append("Cosa vuoi accettare? Nulla???\n");
        }
    }

    void decline() {
        if (game.getObject(HACKSAW).isAsked() && game.getCurrentRoom().getId() == CANTEEN
                && !game.getObject(HACKSAW).isAccept() && !game.getInventory().contains(game.getObject(HACKSAW))) {
            response.append("Decidi di rifiutare l’accordo, quando vuoi il detenuto sarà sempre pronto " +
                    "a ricontrattare!\n");
            game.getObject(HACKSAW).setPickupable(false);
            game.getObject(HACKSAW).setAsked(false);
            game.getObject(HACKSAW).setAccept(false);
        } else if (!game.getObject(HACKSAW).isAsked()) {
            response.append("Non puoi rifiutare una cosa che non hai chiesto!!!\n");
        } else if (game.getObject(HACKSAW).isAccept() || game.getInventory().contains(game.getObject(HACKSAW))) {
            response.append("Ormai hai già accettato! Ci avresti potuto pensare prima!\n");
        } else if (game.getCurrentRoom().getId() != CANTEEN) {
            response.append("Cosa vuoi rifiutare? Nulla???\n");
        }
    }

    void faceUp() throws ObjectNotFoundInInventoryException {
        if (game.getCurrentRoom().getId() == FRONTBENCH && counterFaceUp == 0) {
            response.append("Sai benissimo che in un carcere non si possono comprare panchine e ti avvicini " +
                    "nuovamente con l’intento di prendere l’oggetto. Il gruppetto ti blocca e il piu' grosso " +
                    "di loro ti tira un pugno contro il viso... Perdendo i sensi non ti ricordi piu' nulla e" +
                    " ti svegli in infermeria.\n");
            game.setCurrentRoom(game.getRoom(INFIRMARY));
            game.increaseScore();
            ControllerMove.getInstance().setMove(true);
            ControllerMove.getInstance().increaseCounterFaceUp();
        } else if (game.getCurrentRoom().getId() == FRONTBENCH && !game.getObject(SCALPEL).isUsed()
                && game.getInventory().contains(game.getObject(SCALPEL)) && counterFaceUp == 1) {
            game.increaseScore();
            game.setObjectNotAssignedRoom(game.getObject(SCALPEL));
            game.getInventory().remove(game.getObject(SCALPEL));
            game.getObject(SCALPEL).setUsed(true);
            game.getObject(SCREW).setPickupable(true);
            response.append("Riesci subito a tirare fuori il bisturi dalla tasca, il gruppetto lo vede e " +
                    "capito il pericolo decide di lasciare stare (Mettere a rischio la vita per una panchina " +
                    "sarebbe veramente stupido) e vanno via con un'aria di vendetta.\nOra sei solo vicino" +
                    " alla panchina.\n");
            game.getCurrentRoom().setDescription("Sei solo vicino alla panchina!");
            game.getCurrentRoom().setLook("E' una grossa panchina in legno un po' malandata, " +
                    "ci sei solo tu nelle vicinanze.");
            game.getRoom(BENCH).setDescription("Dopo aver usato il bisturi, il giardino si è svuotato, ci sei" +
                    "solo tu qui.");
            game.getRoom(BENCH).setLook("In lontananza vedi delle panchine tutte vuote!");
            ControllerMove.getInstance().increaseCounterFaceUp();
        } else if (game.getCurrentRoom().getId() != FRONTBENCH || game.getObject(SCALPEL).isUsed() || counterFaceUp >= 2) {
            response.append("Ehi John Cena, non puoi affrontare nessuno qui!!!\n");
        }
    }

    void destroy() throws ObjectNotFoundInInventoryException, ObjectNotFoundInRoomException {
        if (object != null
                && object.getId() == DESTROYABLE_GRATE
                && game.getCurrentRoom().getId() == AIR_DUCT_NORTH
                && game.getInventory().contains(game.getObject(HACKSAW))
                && game.getObject(TOOLS).isUsed()
                && !game.getObject(HACKSAW).isUsed()) {
            game.getRoom(AIR_DUCT_INFIRMARY).setLocked(false);
            game.getInventory().remove(game.getObject(HACKSAW));
            game.getRoom(AIR_DUCT_NORTH).removeObject(object);
            response.append("Oh no! Il seghetto si è rotto e adesso ci sono pezzi di sega dappertutto, per " +
                    "fortuna sei riuscito a rompere la grata\n");
            response.append("Dopo esserti allenato duramente riesci a tagliare le sbarre con il seghetto, " +
                    "puoi proseguire nel condotto e capisci che quel condotto porta fino all’infermeria.\n");
            game.increaseScore();
            game.increaseScore();
        } else if (object == null) {
            response.append("Cosa vuoi rompere???\n");
        } else if (!game.getCurrentRoom().containsObject(object)) {
            throw new ObjectNotFoundInRoomException();
        } else if (object.getId() != DESTROYABLE_GRATE) {
            response.append("Non puoi distruggere questo oggetto!\n");
        } else if (game.getCurrentRoom().getId() != AIR_DUCT_NORTH) {
            response.append("Non puoi distruggere niente qui!\n");
        } else if (!game.getInventory().contains(game.getObject(HACKSAW))) {
            response.append("Come puoi rompere la grata? Non hai nessun oggetto utile!\n");
        } else if (!game.getObject(TOOLS).isUsed()) {
            response.append("Il seghetto sembra molto arrugginito e non riesci a tagliare le sbarre della grata! " +
                    "In realtà la colpa non è totalmente del seghetto ma anche la tua poichè sei molto stanco " +
                    "e hai poca forza nelle braccia!\n");
        } else if (!game.getObject(MEDICINE).isGiven()) {
            response.append("Dopo esserti allenato duramente riesci a tagliare le sbarre con il seghetto, " +
                    "puoi proseguire nel condotto e capisci che quel condotto porta fino all’infermeria. " +
                    "Avrebbe più senso proseguire solo se la tua squadra è al completo… non ti sembri manchi " +
                    "la persona più importante???\n");
        } else if (!game.getObject(HACKSAW).isUsed()) {
            response.append("Hai già usato quell'oggetto! Non puoi più rompere nulla!\n");
        }
    }

    void give() throws ObjectNotFoundInInventoryException {
        if (object != null && object.isGiveable() && game.getCurrentRoom().getId() == BROTHER_CELL
                && object.getId() == MEDICINE) {
            game.getInventory().remove(object);
            response.append("Sai benissimo che tuo fratello ha una forte allergia alle ortiche" +
                    " e che non potrebbe prendere quel farmaco. Tu decidi di darlo ugualmente " +
                    "in modo che il tuo piano venga attuato!\n");
            object.setGiven(true);
            response.append("Appena dato il farmaco decidi di fuggire fuori dalla cella isolamento prima" +
                    " che le luci si accendano e le guardie ti scoprano!!!\n");
            game.setCurrentRoom(game.getRoom(OUT_ISOLATION));
            ControllerMove.getInstance().setMove(true);
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
            game.getRoom(MAIN_CELL).setLook("Non perdere ulteriore tempo, bisogna attuare il piano e scappare via da qui!");
            game.getRoom(AIR_DUCT_INFIRMARY).setLook("Dal condotto d'aria riesci a vedere tuo fratello " +
                    "nell'infermeria che ti aspetta!");

            game.increaseScore();
            game.increaseScore();
            game.increaseScore();
        } else if (object == null) {
            response.append("Cosa vuoi dare di preciso?\n");
        } else if (object instanceof TokenPerson) {
            response.append("Una persona non è un oggetto che si può regalare!!!\n");
        } else {
            response.append("Non puoi dare quest'oggetto a nessuno imbecille!\n");
        }
    }

}
