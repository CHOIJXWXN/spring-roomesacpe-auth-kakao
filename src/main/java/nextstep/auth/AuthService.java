package nextstep.auth;

import nextstep.member.MemberDao;
import nextstep.support.MemberNotFoundException;
import nextstep.support.UnauthorizedException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final static JwtTokenProvider TOKEN_PROVIDER = new JwtTokenProvider();
    private MemberDao memberDao;

    public AuthService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void validatePassword(TokenRequest tokenRequest) {
        if (memberDao.findByUsername(tokenRequest.getUsername())
                .orElseThrow(MemberNotFoundException::new)
                .checkWrongPassword(tokenRequest.getPassword())) {
            throw new UnauthorizedException();
        }
    }

    public String createToken(String username) {
        return TOKEN_PROVIDER.createToken(username);
    }

}
