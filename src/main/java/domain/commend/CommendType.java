package domain.commend;

import domain.commend.exceptions.CommendTypeException;
import java.util.Arrays;
import java.util.List;

public enum CommendType {
    START("start"),
    END("end"),
    MOVE("move"),
    STATUS("status");

    private static final String BLANK = " ";
    private static final int COMMEND_TYPE = 0;

    private String commend;

    CommendType(String commend) {
        this.commend = commend;
    }

    public static CommendType find(String input) {
        List<String> inputSplit = Arrays.asList(input.split(BLANK));
        return Arrays.stream(CommendType.values())
            .filter(type -> type.isSameType(inputSplit.get(COMMEND_TYPE)))
            .findFirst()
            .orElseThrow(() -> new CommendTypeException("올바른 명령어가 아닙니다."));
    }

    private boolean isSameType(String input) {
        return commend.equals(input);
    }
}
