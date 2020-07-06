package adventure.games.prisonbreak.movement;

import adventure.exceptions.LockedRoomException;
import adventure.exceptions.NotAccessibleRoomException;
import adventure.exceptions.inventoryException.InventoryEmptyException;
import adventure.exceptions.inventoryException.InventoryFullException;
import adventure.exceptions.inventoryException.ObjectNotFoundInInventoryException;
import adventure.exceptions.objectsException.ObjectNotFoundInRoomException;
import adventure.exceptions.objectsException.ObjectsAmbiguityException;
import adventure.games.prisonbreak.PrisonBreakGame;
import adventure.parser.ParserOutput;
import adventure.type.TokenObject;
import adventure.type.VerbType;

import static adventure.games.prisonbreak.ObjectType.MEDICINE;
import static adventure.games.prisonbreak.RoomType.*;
import static adventure.type.VerbType.*;

class Move {
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

    PrisonBreakGame getGame() {
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
                        controller.getBasicVerbs().inventory();
                        break;
                    case LOOK_AT:
                        controller.getBasicVerbs().lookAt();
                        break;
                    case PICK_UP:
                        controller.getBasicVerbs().pickUp();
                        break;
                    case REMOVE:
                        controller.getBasicVerbs().remove();
                        break;
                    case USE:
                        controller.getBasicVerbs().use();
                        break;
                    case OPEN:
                        controller.getBasicVerbs().open();
                        break;
                    case CLOSE:
                        controller.getBasicVerbs().close();
                        break;
                    case PUSH:
                    case PULL:
                        controller.getBasicVerbs().pushAndPull();
                        break;
                    case TURN_ON:
                        controller.getBasicVerbs().turnOn();
                        break;
                    case TURN_OFF:
                        controller.getBasicVerbs().turnOff();
                        break;
                    case END:
                        controller.getBasicVerbs().end();
                        break;
                }

            } else if (isAdvancedVerb(p.getVerb().getVerbType())) {

                switch (p.getVerb().getVerbType()) {
                    case EAT:
                        controller.getAdvancedVerbs().eat();
                        break;
                    case STAND_UP:
                        controller.getAdvancedVerbs().standUp();
                        break;
                    case SIT_DOWN:
                        controller.getAdvancedVerbs().sitDown();
                        break;
                    case CLIMB:
                        controller.getAdvancedVerbs().climb();
                        break;
                    case GET_OFF:
                        controller.getAdvancedVerbs().getOff();
                        break;
                    case ENTER:
                        controller.getAdvancedVerbs().enter();
                        break;
                    case EXIT:
                        controller.getAdvancedVerbs().exit();
                        break;
                    case MAKE:
                        controller.getAdvancedVerbs().make();
                        break;
                    case PLAY:
                        controller.getAdvancedVerbs().play();
                        break;
                    case WORK_OUT:
                        controller.getAdvancedVerbs().workOut();
                        break;
                    case PUT_IN:
                        controller.getAdvancedVerbs().putIn();
                        break;
                    case TALK_TO:
                        controller.getAdvancedVerbs().talkTo();
                        break;
                    case ASK:
                        controller.getAdvancedVerbs().ask();
                        break;
                    case ACCEPT:
                        controller.getAdvancedVerbs().accept();
                        break;
                    case DECLINE:
                        controller.getAdvancedVerbs().decline();
                        break;
                    case FACE_UP:
                        controller.getAdvancedVerbs().faceUp();
                        break;
                    case DESTROY:
                        controller.getAdvancedVerbs().destroy();
                        break;
                    case GIVE:
                        controller.getAdvancedVerbs().give();
                        break;
                }
            }

            if (move) {
                response.append(game.getCurrentRoom().getName()).append("\n");
                response.append("================================================\n");
                response.append(game.getCurrentRoom().getDescription()).append("\n");
            }

        } catch (NotAccessibleRoomException e) {
            if (game.getCurrentRoom().getId() == BROTHER_CELL && p.getVerb().getVerbType().equals(EAST)
                    || game.getCurrentRoom().getId() == OTHER_CELL && p.getVerb().getVerbType().equals(WEST)) {
                response.append("Non hai ancora il potere di allargare le sbarre o oltrepassarle!!\n");
            } else {
                response.append("Da quella parte non si può andare c'è un muro! Non hai ancora acquisito i poteri" +
                        " per oltrepassare i muri...\n");
            }
        } catch (LockedRoomException e) {
            if (game.getObject(MEDICINE).isGiven()) {
                response.append("Non perdere ulteriore tempo, bisogna completare il piano!\n");
            } else if (game.getCurrentRoom().getEast() != null && game.getCurrentRoom().getId() == AIR_DUCT_INFIRMARY
                    && game.getCurrentRoom().getEast().getId() == INFIRMARY) {
                response.append("Avrebbe più senso proseguire solo se la tua squadra è al completo… " +
                        "non ti sembri manchi la persona più importante???\n");
            } else {
                response.append("Questa stanza è bloccata, dovrai fare qualcosa per sbloccarla!!\n");
            }
        } catch (InventoryEmptyException e) {
            response.append("L'inventario è vuoto!\n");
        } catch (InventoryFullException e) {
            response.append("Non puoi mettere più elementi nel tuo inventario!\n");
            response.append("!!!!Non hai mica la borsa di Mary Poppins!!!!!\n");
        } catch (ObjectNotFoundInInventoryException e) {
            response.append("Non hai questo oggetto nell'inventario, energumeno\n");
        } catch (ObjectsAmbiguityException e) {
            response.append("Ci sono più oggetti di questo tipo in questa stanza e non capisco a quale ti riferisci!\n");
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
                response.append("Lo vedi solo nei tuoi sogni!\n");
            } else {
                response.append("Hai fumato qualcosa per caso?!\n");
            }
        } catch (Exception e) {
            response.append("Qualcosa è andato storto....\n");
        }

        return response.toString();
    }

    boolean isMovementVerb(VerbType verb) {
        return verb.equals(NORD) || verb.equals(SOUTH) || verb.equals(EAST) || verb.equals(WEST);
    }

    boolean isBasicVerb(VerbType verb) {
        return verb.equals(LOOK_AT) || verb.equals(PICK_UP) || verb.equals(OPEN) || verb.equals(CLOSE)
                || verb.equals(INVENTORY) || verb.equals(REMOVE) || verb.equals(PULL) || verb.equals(TURN_ON)
                || verb.equals(TURN_OFF) || verb.equals(END) || verb.equals(PUSH) || verb.equals(USE);
    }

    boolean isAdvancedVerb(VerbType verb) {
        return verb.equals(EAT) || verb.equals(STAND_UP) || verb.equals(SIT_DOWN) || verb.equals(CLIMB)
                || verb.equals(GET_OFF) || verb.equals(ENTER) || verb.equals(EXIT) || verb.equals(MAKE)
                || verb.equals(PLAY) || verb.equals(WORK_OUT) || verb.equals(PUT_IN) || verb.equals(TALK_TO)
                || verb.equals(ASK) || verb.equals(ACCEPT) || verb.equals(DECLINE) || verb.equals(FACE_UP)
                || verb.equals(DESTROY) || verb.equals(GIVE);
    }

}
