package adventure.server.games.prisonbreak.movement;

import adventure.exceptions.LockedRoomException;
import adventure.exceptions.NotAccessibleRoomException;
import adventure.exceptions.inventoryException.InventoryEmptyException;
import adventure.exceptions.inventoryException.InventoryFullException;
import adventure.exceptions.inventoryException.ObjectNotFoundInInventoryException;
import adventure.exceptions.objectsException.ObjectNotFoundInRoomException;
import adventure.exceptions.objectsException.ObjectsAmbiguityException;
import adventure.server.games.prisonbreak.PrisonBreakGame;
import adventure.server.games.prisonbreak.TokenPerson;
import adventure.server.parser.ParserOutput;
import adventure.server.type.TokenObject;
import adventure.server.type.VerbType;

import java.io.Serializable;

import static adventure.server.games.prisonbreak.ObjectType.GENNY_BELLO;
import static adventure.server.games.prisonbreak.ObjectType.MEDICINE;
import static adventure.server.games.prisonbreak.RoomType.*;
import static adventure.server.type.VerbType.*;

/**
 * @author Corona-Extra
 */
class Move implements Serializable {
    private ControllerMovement controller;
    private PrisonBreakGame game;
    private StringBuilder response = new StringBuilder();
    private TokenObject object;
    private boolean move = false;
    private boolean mixed = false;
    private short counterFaceUp = 0;

    Move(ControllerMovement controller, PrisonBreakGame game) {
        this.controller = controller;
        this.game = game;
    }

    PrisonBreakGame getPrisonBreakGame() {
        return game;
    }

    StringBuilder getResponse() {
        return response;
    }

    TokenObject getObject() {
        return object;
    }

    void setMove(boolean move) {
        this.move = move;
    }

    boolean isMixed() {
        return mixed;
    }

    void setMixed(boolean mixed) {
        this.mixed = mixed;
    }

    short getCounterFaceUp() {
        return counterFaceUp;
    }

    void increaseCounterFaceUp() {
        this.counterFaceUp++;
    }

    String nextMove(ParserOutput p) {
        move = false;
        response = new StringBuilder();
        try {
            object = game.getCorrectObject(p.getObject());

            if (isMovementVerb(p.getVerb().getVerbType())) {

                switch (p.getVerb().getVerbType()) {
                    case NORD:
                        controller.getMovementVerbs().nord();
                        break;
                    case SOUTH:
                        controller.getMovementVerbs().south();
                        break;
                    case EAST:
                        controller.getMovementVerbs().east();
                        break;
                    case WEST:
                        controller.getMovementVerbs().west();
                        break;
                }

            } else if (isBasicVerb(p.getVerb().getVerbType())) {

                switch (p.getVerb().getVerbType()) {
                    case INVENTORY:
                        response.append(controller.getBasicVerbs().inventory());
                        break;
                    case LOOK_AT:
                        response.append(controller.getBasicVerbs().lookAt());
                        break;
                    case PICK_UP:
                        response.append(controller.getBasicVerbs().pickUp());
                        break;
                    case REMOVE:
                        response.append(controller.getBasicVerbs().remove());
                        break;
                    case USE:
                        response.append(controller.getBasicVerbs().use());
                        break;
                    case OPEN:
                        response.append(controller.getBasicVerbs().open());
                        break;
                    case CLOSE:
                        response.append(controller.getBasicVerbs().close());
                        break;
                    case PUSH:
                    case PULL:
                        response.append(controller.getBasicVerbs().pushAndPull());
                        break;
                    case TURN_ON:
                        response.append(controller.getBasicVerbs().turnOn());
                        break;
                    case TURN_OFF:
                        response.append(controller.getBasicVerbs().turnOff());
                        break;
                    case END:
                        response.append(controller.getBasicVerbs().end());
                        break;
                    case SUICIDE:
                        response.append(controller.getBasicVerbs().suicide());
                }

            } else if (isAdvancedVerb(p.getVerb().getVerbType())) {

                switch (p.getVerb().getVerbType()) {
                    case EAT:
                        response.append(controller.getAdvancedVerbs().eat());
                        break;
                    case STAND_UP:
                        response.append(controller.getAdvancedVerbs().standUp());
                        break;
                    case SIT_DOWN:
                        response.append(controller.getAdvancedVerbs().sitDown());
                        break;
                    case CLIMB:
                        response.append(controller.getAdvancedVerbs().climb());
                        break;
                    case GET_OFF:
                        response.append(controller.getAdvancedVerbs().getOff());
                        break;
                    case ENTER:
                        response.append(controller.getAdvancedVerbs().enter());
                        break;
                    case EXIT:
                        response.append(controller.getAdvancedVerbs().exit());
                        break;
                    case MAKE:
                        response.append(controller.getAdvancedVerbs().make());
                        break;
                    case PLAY:
                        response.append(controller.getAdvancedVerbs().play());
                        break;
                    case WORK_OUT:
                        response.append(controller.getAdvancedVerbs().workOut());
                        break;
                    case PUT_IN:
                        response.append(controller.getAdvancedVerbs().putIn());
                        break;
                    case TALK_TO:
                        response.append(controller.getAdvancedVerbs().talkTo());
                        break;
                    case ASK:
                        response.append(controller.getAdvancedVerbs().ask());
                        break;
                    case ACCEPT:
                        response.append(controller.getAdvancedVerbs().accept());
                        break;
                    case DECLINE:
                        response.append(controller.getAdvancedVerbs().decline());
                        break;
                    case FACE_UP:
                        response.append(controller.getAdvancedVerbs().faceUp());
                        break;
                    case DESTROY:
                        response.append(controller.getAdvancedVerbs().destroy());
                        break;
                    case GIVE:
                        response.append(controller.getAdvancedVerbs().give());
                        break;
                }
            }

            if (move) {
                response.append("=============================================================================" +
                        "====\n");
                response.append(game.getCurrentRoom().getName()).append("\n");
                response.append("=============================================================================" +
                        "====\n");
                response.append(game.getCurrentRoom().getDescription());

                if (((TokenPerson) game.getObject(GENNY_BELLO)).isFollowHero()) {
                    game.getCurrentRoom().setObject(game.getObject(GENNY_BELLO));
                    if (game.getCurrentRoom().getId() == INFIRMARY) {
                        game.getCurrentRoom().getWest().setLocked(true);
                        game.getObject(PICTURE).setPush(true);
                    } else if (game.getCurrentRoom().getId() == ENDGAME) {
                        game.getCurrentRoom().getSouth().setLocked(true);
                    }
                }
                if (game.getObject(MEDICINE).isGiven() && game.getCurrentRoom().getId() == MAIN_CELL) {
                    ((TokenPerson) game.getObject(GENNY_BELLO)).setFollowHero(true);
                    game.getCurrentRoom().getEast().setLocked(true);
                    game.getCurrentRoom().setObject(game.getObject(GENNY_BELLO));
                }
            }

        } catch (NotAccessibleRoomException e) {
            if (game.getCurrentRoom().getId() == BROTHER_CELL && p.getVerb().getVerbType().equals(EAST)
                    || game.getCurrentRoom().getId() == OTHER_CELL && p.getVerb().getVerbType().equals(WEST)) {
                response.append("Non hai ancora il potere di allargare le sbarre o oltrepassarle!!");
            } else {
                response.append("Da quella parte non si può andare c'è un muro! Non hai ancora acquisito i poteri" +
                        " per oltrepassare i muri...");
            }
        } catch (LockedRoomException e) {
            if (game.getCurrentRoom().getId() == ENDGAME) {
                response.append("Hai terminato il gioco! Basta camminare!");
            } else if (game.getObject(MEDICINE).isGiven()) {
                if (!(game.getCurrentRoom().getId() == INFIRMARY && p.getVerb().getVerbType().equals(NORD))) {
                    response.append("Non perdere ulteriore tempo, bisogna completare il piano!");
                }
            } else if (game.getCurrentRoom().getEast() != null && game.getCurrentRoom().getId() == AIR_DUCT_INFIRMARY
                    && game.getCurrentRoom().getEast().getId() == INFIRMARY) {
                response.append("Avrebbe più senso proseguire solo se la tua squadra è al completo… " +
                        "non ti sembri manchi la persona più importante???");
            } else {
                response.append("Questa stanza è bloccata, dovrai fare qualcosa per sbloccarla!!");
            }
        } catch (InventoryEmptyException e) {
            response.append("L'inventario è vuoto!");
        } catch (InventoryFullException e) {
            response.append("Non puoi mettere più elementi nel tuo inventario!");
            response.append("!!!!Non hai mica la borsa di Mary Poppins!!!!!");
        } catch (ObjectNotFoundInInventoryException e) {
            response.append("Non hai questo oggetto nell'inventario, energumeno");
        } catch (ObjectsAmbiguityException e) {
            response.append("Ci sono più oggetti di questo tipo in questa stanza e non capisco a quale ti riferisci!");
        } catch (ObjectNotFoundInRoomException e) {
            if (p.getVerb().getVerbType() == PICK_UP
                    || p.getVerb().getVerbType() == USE
                    || p.getVerb().getVerbType() == REMOVE
                    || p.getVerb().getVerbType() == OPEN
                    || p.getVerb().getVerbType() == CLOSE
                    || p.getVerb().getVerbType() == DESTROY
                    || p.getVerb().getVerbType() == GIVE
                    || p.getVerb().getVerbType() == EAT
                    || p.getVerb().getVerbType() == PULL
                    || p.getVerb().getVerbType() == PUSH
                    || p.getVerb().getVerbType() == MAKE
                    || p.getVerb().getVerbType() == PLAY
                    || p.getVerb().getVerbType() == WORK_OUT) {
                response.append("Lo vedi solo nei tuoi sogni!");
            } else {
                response.append("Hai fumato qualcosa per caso?!");
            }
        } catch (Exception e) {
            response.append("Qualcosa è andato storto....");
        }

        return response.toString();
    }

    boolean isMovementVerb(VerbType verb) {
        return verb.equals(NORD) || verb.equals(SOUTH) || verb.equals(EAST) || verb.equals(WEST);
    }

    boolean isBasicVerb(VerbType verb) {
        return verb.equals(LOOK_AT) || verb.equals(PICK_UP) || verb.equals(OPEN) || verb.equals(CLOSE)
                || verb.equals(INVENTORY) || verb.equals(REMOVE) || verb.equals(PULL) || verb.equals(TURN_ON)
                || verb.equals(TURN_OFF) || verb.equals(END) || verb.equals(PUSH) || verb.equals(USE)
                || verb.equals(SUICIDE);
    }

    boolean isAdvancedVerb(VerbType verb) {
        return verb.equals(EAT) || verb.equals(STAND_UP) || verb.equals(SIT_DOWN) || verb.equals(CLIMB)
                || verb.equals(GET_OFF) || verb.equals(ENTER) || verb.equals(EXIT) || verb.equals(MAKE)
                || verb.equals(PLAY) || verb.equals(WORK_OUT) || verb.equals(PUT_IN) || verb.equals(TALK_TO)
                || verb.equals(ASK) || verb.equals(ACCEPT) || verb.equals(DECLINE) || verb.equals(FACE_UP)
                || verb.equals(DESTROY) || verb.equals(GIVE);
    }

}
