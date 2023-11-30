package nextstep.qna.domain;

import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Answers {

    private List<Answer> values = new ArrayList<>();

    public Answers() {
        this.values = new ArrayList<>();
    }

    public Answers(List<Answer> values) {
        this.values = values;
    }

    public List<Answer> getValues() {
        return Collections.unmodifiableList(values);
    }

    public void add(Answer answer) {
        values.add(answer);
    }

    public boolean isAnswerOwner(NsUser loginUser) {
        long answerOwnerCount = values.stream().filter(answer -> answer.isOwner(loginUser)).count();
        if (this.values.size() == answerOwnerCount) {
            return true;
        }
        return false;
    }

    public List<DeleteHistory> delete() {
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        for (Answer answer : values) {
            deleteHistories.add(answer.delete());
        }
        return deleteHistories;
    }
}
