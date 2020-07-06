package adventure.games.prisonbreak.movement;

import adventure.games.prisonbreak.PrisonBreakGame;
import adventure.parser.ParserOutput;

public class ControllerMovement {
    private final PrisonBreakGame game;
    private final Move move;
    private final MovementVerbs movementVerbs;
    private final BasicVerbs basicVerbs;
    private final AdvancedVerbs advancedVerbs;

    public ControllerMovement(PrisonBreakGame game) {
        this.game = game;
        this.move = new Move(this, game);
        basicVerbs = new BasicVerbs(this);
        movementVerbs = new MovementVerbs(this);
        advancedVerbs = new AdvancedVerbs(this);
    }

    public Move getMove() {
        return move;
    }

    public MovementVerbs getMovementVerbs() {
        return movementVerbs;
    }

    public BasicVerbs getBasicVerbs() {
        return basicVerbs;
    }

    public AdvancedVerbs getAdvancedVerbs() {
        return advancedVerbs;
    }

    public String nextMove(ParserOutput p) {
        return move.nextMove(p);
    }
}
