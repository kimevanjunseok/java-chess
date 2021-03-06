package domain.commend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.commend.exceptions.CommendTypeException;
import domain.pieces.Pieces;
import domain.pieces.PiecesFactory;
import domain.team.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class StateTest {

    static Pieces pieces = Pieces.of(PiecesFactory.create());

    @Test
    @DisplayName("Waiting 일때 status하면 오류")
    void waiting_Status() {
        State state = State.of(pieces);
        assertThatThrownBy(state::status)
            .isInstanceOf(CommendTypeException.class);
    }

    @Test
    @DisplayName("Waiting 일때 move하면 오류")
    void waiting_Move() {
        State state = State.of(pieces);
        assertThatThrownBy(() -> state.move("move b2 b3"))
            .isInstanceOf(CommendTypeException.class);
    }

    @Test
    @DisplayName("Playing 일때 start하면 오류")
    void playing_start() {
        State state = State.of(pieces);
        state.start();
        assertThatThrownBy(state::start)
            .isInstanceOf(CommendTypeException.class);
    }

    @Test
    @DisplayName("Playing 일때 isFinished 테스트")
    void playing_isFinished() {
        State state = State.of(pieces);
        state.start();
        assertThat(state.isFinished()).isFalse();
    }

    @Test
    @DisplayName("Turn를 바꾸는지 테스트")
    void change_Turn() {
        State state = State.of(pieces);
        state.start();
        assertThat(state.getPresentTurn()).isEqualTo(Team.WHITE);
        state.move("move b2 b3");
        assertThat(state.getPresentTurn()).isEqualTo(Team.BLACK);
    }

    @Test
    @DisplayName("status 점수 계산")
    void status() {
        State state = State.of(pieces);
        state.start();
        assertThat(state.status()).isEqualTo(38.0);
        state.move("move a2 a3");
        assertThat(state.status()).isEqualTo(38.0);
    }

    @Test
    @DisplayName("end 일때 start하면 오류")
    void finished_Start() {
        State state = State.of(pieces);
        state.end();
        assertThatThrownBy(state::start)
            .isInstanceOf(CommendTypeException.class);
    }

    @Test
    @DisplayName("end 일때 end하면 오류")
    void finished_End() {
        State state = State.of(pieces);
        state.end();
        assertThatThrownBy(state::end)
            .isInstanceOf(CommendTypeException.class);
    }

    @Test
    @DisplayName("end 일때 move하면 오류")
    void finished_Move() {
        State state = State.of(pieces);
        state.end();
        assertThatThrownBy(() -> state.move("move b2 b3"))
            .isInstanceOf(CommendTypeException.class);
    }

    @Test
    @DisplayName("end 일때 status하면 오류")
    void finished_Status() {
        State state = State.of(pieces);
        state.end();
        assertThatThrownBy(state::status)
            .isInstanceOf(CommendTypeException.class);
    }

    @Test
    @DisplayName("end를 하면 끝나는지 테스트")
    void isFinished() {
        State state = State.of(pieces);
        state.start();
        state.end();
        assertThat(state.isFinished()).isTrue();
    }

    @ParameterizedTest
    @DisplayName("move에 잘못된 좌표가 입력될 때")
    @ValueSource(strings = {"move b3", "move b33 b34"})
    void move_wrong(String input) {
        State state = State.of(pieces);
        state.start();
        assertThatThrownBy(() -> state.move(input))
            .isInstanceOf(CommendTypeException.class);
    }
}
