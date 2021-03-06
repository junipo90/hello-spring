package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memoryMemberRepository;

    @BeforeEach
    public void beforeEach(){
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository);
    }

    @AfterEach
    public void afterEach(){
        memoryMemberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long joinId = memberService.join(member);

        //then
        memberService.findOne(joinId).ifPresent(m -> {
            Assertions.assertThat(m.getName()).isEqualTo(member.getName());
        });
    }

    @Test
    void 중복회원검증() {
        //given
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");

        //when
        Long joinId = memberService.join(member1);

        //then
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(illegalStateException.getMessage()).isEqualTo("이미 존재 하는 회원 입니다.");
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}