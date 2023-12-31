package nextstep.sessions.domain;

import nextstep.common.PeriodTest;
import nextstep.sessions.domain.Builder.SessionBuilder;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class SessionTest {

    @DisplayName("시작일, 종료일, 이미지, 가격, 상태를 전달하면 강의 객체를 생성한다.")
    @Test
    void sessionTest() {
        assertThat(
                new Session(
                        "강의1",
                        PeriodTest.NOV,
                        new SessionImages(List.of(SessionImageTest.IMAGE_JPG)),
                        SessionChargeTest.FREE,
                        SessionStatusTest.RECRUITING,
                        NsUserTest.JAVAJIGI)
        ).isInstanceOf(Session.class);
        assertThat(
                new Session(
                        "강의2",
                        PeriodTest.DEC,
                        new SessionImages(List.of(SessionImageTest.IMAGE_PNG)),
                        SessionChargeTest.CHARGE_1000,
                        SessionStatusTest.NON_RECRUITMENT,
                        NsUserTest.SANJIGI)
        ).isInstanceOf(Session.class);
    }

    @DisplayName("모집 인원이 마감된 강의는 수강신청을 하면 IllegalStateException을 던진다.")
    @Test
    void enrollExceptionTest() {
        Session session = new SessionBuilder()
                .withName("강의")
                .withPeriod(PeriodTest.DEC)
                .withSessionImages(new SessionImages(List.of(SessionImageTest.IMAGE_PNG)))
                .withSessionCharge(SessionChargeTest.CHARGE_100)
                .withSessionStatus(SessionStatusTest.RECRUITING)
                .withSessionStudents(new SessionStudents())
                .withInstructor(NsUserTest.SANJIGI)
                .build();
        session.enroll(NsUserTest.JAVAJIGI);
        session.approve(new SessionStudent(NsUserTest.JAVAJIGI, session.getId()), NsUserTest.SANJIGI);

        assertThatThrownBy(() -> session.enroll(NsUserTest.SANJIGI))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("커버 이미지를 추가할 수 있다.")
    @Test
    void addImageTest() {
        List<SessionImage> images = new ArrayList<>();
        images.add(SessionImageTest.IMAGE_JPG);
        Session session = new Session("강의1", PeriodTest.NOV, new SessionImages(images), SessionChargeTest.CHARGE_100, SessionStatusTest.RECRUITING, NsUserTest.JAVAJIGI);
        int beforeTotalCount = session.getImages().size();
        session.addImage(SessionImageTest.IMAGE_PNG);

        assertThat(session.getImages().size()).isEqualTo(beforeTotalCount + 1);
    }

    @DisplayName("강사가 아닌 사용자가 수강신청 승인을 할 경우 IllegalStateException을 던진다.")
    @Test
    void approveExceptionTest() {
        Session session = new SessionBuilder()
                .withName("강의1")
                .withPeriod(PeriodTest.NOV)
                .withSessionImages(new SessionImages(List.of(SessionImageTest.IMAGE_JPG)))
                .withSessionCharge(SessionChargeTest.FREE)
                .withSessionStatus(SessionStatusTest.RECRUITING)
                .withSessionStudents(new SessionStudents())
                .withInstructor(NsUserTest.JAVAJIGI)
                .build();
        SessionStudent sessionStudent = session.enroll(NsUserTest.SANJIGI);

        assertThatThrownBy(() -> session.approve(sessionStudent, NsUserTest.SANJIGI))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("강사가 아닌 사용자가 수강신청 취소를 할 경우 IllegalStateException을 던진다.")
    @Test
    void cancelExceptionTest() {
        Session session = new SessionBuilder()
                .withName("강의1")
                .withPeriod(PeriodTest.NOV)
                .withSessionImages(new SessionImages(List.of(SessionImageTest.IMAGE_JPG)))
                .withSessionCharge(SessionChargeTest.FREE)
                .withSessionStatus(SessionStatusTest.RECRUITING)
                .withSessionStudents(new SessionStudents())
                .withInstructor(NsUserTest.JAVAJIGI)
                .build();
        SessionStudent sessionStudent = session.enroll(NsUserTest.SANJIGI);

        assertThatThrownBy(() -> session.cancel(sessionStudent, NsUserTest.SANJIGI))
                .isInstanceOf(IllegalStateException.class);
    }
}
