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

public class ControllerMove {
    private static ControllerMove instance;
    private StringBuilder response = new StringBuilder();
    private PrisonBreakGame game;
    private TokenObject object;
    private boolean move = false;
    private boolean mixed = false;
    private short counterFaceUp = 0;

    private ControllerMove() {
    }

    public static ControllerMove getInstance() {
        if (instance == null) {
            instance = new ControllerMove();
        }
        return instance;
    }

    public PrisonBreakGame getGame() {
        return game;
    }

    public void setGame(PrisonBreakGame game) {
        this.game = game;
    }

    public StringBuilder getResponse() {
        return response;
    }

    public TokenObject getObject() {
        return object;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public boolean isMixed() {
        return mixed;
    }

    public void setMixed(boolean mixed) {
        this.mixed = mixed;
    }

    public short getCounterFaceUp() {
        return counterFaceUp;
    }

    public void increaseCounterFaceUp() {
        this.counterFaceUp++;
    }

    public String nextMove(ParserOutput p) {
        response = new StringBuilder();
        try {
            object = game.getCorrectObject(p.getObject());

            if (isMovementVerb(p.getVerb().getVerbType())) {
                MovementVerbs movement = new MovementVerbs();

                switch (p.getVerb().getVerbType()) {
                    case NORD:
                        movement.nord();
                        break;
                    case SOUTH:
                        movement.south();
                        break;
                    case EAST:
                        movement.east();
                        break;
                    case WEST:
                        movement.west();
                        break;
                }

            } else if (isBasicVerb(p.getVerb().getVerbType())) {
                BasicVerbs movement = new BasicVerbs();

                switch (p.getVerb().getVerbType()) {
                    case INVENTORY:
                        movement.inventory();
                        break;
                    case LOOK_AT:
                        movement.lookAt();
                        break;
                    case PICK_UP:
                        movement.pickUp();
                        break;
                    case REMOVE:
                        movement.remove();
                        break;
                    case USE:
                        movement.use();
                        break;
                    case OPEN:
                        movement.open();
                        break;
                    case CLOSE:
                        movement.close();
                        break;
                    case PUSH:
                    case PULL:
                        movement.pushAndPull();
                        break;
                    case TURN_ON:
                        movement.turnOn();
                        break;
                    case TURN_OFF:
                        movement.turnOff();
                        break;
                    case END:
                        movement.end();
                        break;
                }

            } else if (isAdvancedVerb(p.getVerb().getVerbType())) {
                AdvancedVerbs movement = new AdvancedVerbs();

                switch (p.getVerb().getVerbType()) {
                    case EAT:
                        movement.eat();
                        break;
                    case STAND_UP:
                        movement.standUp();
                        break;
                    case SIT_DOWN:
                        movement.sitDown();
                        break;
                    case CLIMB:
                        movement.climb();
                        break;
                    case GET_OFF:
                        movement.getOff();
                        break;
                    case ENTER:
                        movement.enter();
                        break;
                    case EXIT:
                        movement.exit();
                        break;
                    case MAKE:
                        movement.make();
                        break;
                    case PLAY:
                        movement.play();
                        break;
                    case WORK_OUT:
                        movement.workOut();
                        break;
                    case PUT_IN:
                        movement.putIn();
                        break;
                    case TALK_TO:
                        movement.talkTo();
                        break;
                    case ASK:
                        movement.ask();
                        break;
                    case ACCEPT:
                        movement.accept();
                        break;
                    case DECLINE:
                        movement.decline();
                        break;
                    case FACE_UP:
                        movement.faceUp();
                        break;
                    case DESTROY:
                        movement.destroy();
                        break;
                    case GIVE:
                        movement.give();
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

    private boolean isMovementVerb(VerbType verb) {
        return verb.equals(NORD) || verb.equals(SOUTH) || verb.equals(EAST) || verb.equals(WEST);
    }

    private boolean isBasicVerb(VerbType verb) {
        return verb.equals(LOOK_AT) || verb.equals(PICK_UP) || verb.equals(OPEN) || verb.equals(CLOSE)
                || verb.equals(INVENTORY) || verb.equals(REMOVE) || verb.equals(PULL) || verb.equals(TURN_ON)
                || verb.equals(TURN_OFF) || verb.equals(END) || verb.equals(PUSH) || verb.equals(USE);
    }

    private boolean isAdvancedVerb(VerbType verb) {
        return verb.equals(EAT) || verb.equals(STAND_UP) || verb.equals(SIT_DOWN) || verb.equals(CLIMB)
                || verb.equals(GET_OFF) || verb.equals(ENTER) || verb.equals(EXIT) || verb.equals(MAKE)
                || verb.equals(PLAY) || verb.equals(WORK_OUT) || verb.equals(PUT_IN) || verb.equals(TALK_TO)
                || verb.equals(ASK) || verb.equals(ACCEPT) || verb.equals(DECLINE) || verb.equals(FACE_UP)
                || verb.equals(DESTROY) || verb.equals(GIVE);
    }

}
