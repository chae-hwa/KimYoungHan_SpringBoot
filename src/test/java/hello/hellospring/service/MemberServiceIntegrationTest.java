package hello.hellospring.service;

import hello.hellospring.Service.MemberService;
import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional // test가 끝나면 Rollback을 자동으로 실행( DB 지우기)
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;


    @Test
    void 회원가입(){
        //given : 무언가가 주어졌는데 (이 데이터를 기반으로)
        Member member = new Member();
        member.setName("spring");

        //when : 이거를 실행했을 때 (이걸 검증하는구나)
        Long saveId = memberService.join(member);

        //then : 결과가 이렇게 나와야 된다.
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복_회원_예외(){
        //given : 무언가가 주어졌는데 (이 데이터를 기반으로)
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        //when : 이거를 실행했을 때 (이걸 검증하는구나)
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        //then : 결과가 이렇게 나와야 된다.
    }

}

