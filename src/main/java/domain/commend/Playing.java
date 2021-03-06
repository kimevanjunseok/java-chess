package domain.commend;

import domain.commend.exceptions.CommendTypeException;
import domain.pieces.Pieces;
import domain.point.Point;
import domain.point.MovePoint;
import domain.team.Team;
import java.util.Arrays;
import java.util.List;

public class Playing extends GameState {

    private static final String BLANK = " ";
    private static final int COMMEND_SIZE = 3;
    private static final int PIECE_INDEX = 1;
    private static final int TARGET_INDEX = 2;
    private static final int POINT_SIZE = 2;

    public Playing(Pieces pieces) {
        super(pieces);
    }

    @Override
    public StateStrategy start() {
        throw new CommendTypeException("이미 시작하였습니다.");
    }

    @Override
    public StateStrategy end() {
        return new Finished(pieces);
    }

    @Override
    public StateStrategy move(Team turn, String input) {
        validate(input);
        List<String> splitInput = Arrays.asList(input.split(BLANK));
        MovePoint movePoint = new MovePoint(Point.of(splitInput.get(1)),
            Point.of(splitInput.get(2)));

        return process(turn, movePoint);
    }

    private StateStrategy process(Team turn, MovePoint movePoint) {
        pieces.move(turn, movePoint);
        return getGameState();
    }

    private StateStrategy getGameState() {
        if (pieces.isTargetKing()) {
            return end();
        }
        return this;
    }

    private void validate(String input) {
        validateSizeThree(input);
        validateEachSizeTwo(input);
    }

    private void validateSizeThree(String input) {
        if (Arrays.asList(input.split(BLANK)).size() != COMMEND_SIZE) {
            throw new CommendTypeException("잘못된 입력입니다.");
        }
    }

    private void validateEachSizeTwo(String input) {
        List<String> splitInput = Arrays.asList(input.split(BLANK));
        if (!(splitInput.get(PIECE_INDEX).length() == POINT_SIZE
            && splitInput.get(TARGET_INDEX).length() == POINT_SIZE)) {
            throw new CommendTypeException("잘못된 입력입니다.");
        }
    }

    @Override
    public StateStrategy status() {
        return this;
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
