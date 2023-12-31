package nextstep.sessions.domain;

import nextstep.common.Period;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Session {
    /*
    강의
    강의 이름, 강의 날짜, 커버 이미지, 가격, 상태, 수강 신청생 목록을 관리한다.
     */

    private Long id;

    private String name;

    private Period date;

    private SessionImages images;

    private SessionCharge charge;

    private SessionStatus status;

    private SessionStudents students;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private NsUser instructor;

    public Session(String name, Period date, SessionImages images, SessionCharge charge, SessionStatus sessionStatus, NsUser loginUser) {
        this(0L, name, date, images, charge, sessionStatus, new SessionStudents(), LocalDateTime.now(), null, loginUser);
    }

    public Session(Long id, String name, Period date, SessionImages images, SessionCharge charge, SessionStatus status, SessionStudents students, LocalDateTime createdAt, LocalDateTime updatedAt, NsUser loginUser) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.images = images;
        this.charge = charge;
        this.status = status;
        this.students = students;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.instructor = loginUser;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Period getDate() {
        return date;
    }

    public SessionImages getImages() {
        return images;
    }

    public SessionCharge getCharge() {
        return charge;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public SessionStudents getStudents() {
        return students;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public NsUser getInstructor() {
        return instructor;
    }

    public void addImage(SessionImage image) {
        images.addImage(image);
    }

    private void checkSessionStatus() {
        if (status.isNotRecruiting()) {
            throw new IllegalStateException("모집중인 강의만 수강할 수 있습니다.");
        }
    }

    public SessionStudent enroll(NsUser user) {
        if (user.isGuestUser()) {
            throw new IllegalStateException("로그인 후 신청할 수 있습니다.");
        }
        checkSessionStatus();
        this.charge.checkRecruits(students.approvalStudentCount());
        SessionStudent newStudent = new SessionStudent(user, this.id);
        students.addStudent(newStudent);
        return newStudent;
    }

    public void approve(SessionStudent student, NsUser loginUser) {
        if (!this.instructor.matchUser(loginUser)) {
            throw new IllegalStateException("강사만 승인할 수 있습니다.");
        }
        students.approve(student);
    }

    public void cancel(SessionStudent student, NsUser loginUser) {
        if (!this.instructor.matchUser(loginUser)) {
            throw new IllegalStateException("강사만 취소할 수 있습니다.");
        }
        students.cancel(student);
    }
}
