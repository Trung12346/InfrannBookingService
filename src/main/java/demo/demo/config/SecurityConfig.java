package demo.demo.config;

import demo.demo.service.CustomerOAuth2UserService;
import demo.demo.service.OauthSuccessHandler;
import demo.demo.service.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UsersDetailsService usersDetailsService;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;


    @Autowired
    private OauthSuccessHandler oauthSuccessHandler;

    @Autowired
    CustomerOAuth2UserService customerOauth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        return http.csrf(customizer -> customizer.disable()).
                authenticationProvider(authenticationProvider()).
                authorizeHttpRequests(req ->
                        req.

                                requestMatchers("/register","/Login","/error","/verify-email",
                                        "/home","/Login/oauth2/code/google")
                                .permitAll().
                                requestMatchers("/Admin").hasRole("ADMIN")
                                // t cấu hình phân quyền rồi nếu muốn phân quyền chỉ cần gọi cái
                                // requestMatchers xong gọi hàm hasRole(Tên Quyền) là xong tên quyền là String
                                .anyRequest().authenticated()).
                    formLogin(login -> login.loginPage("/Login")
                            .defaultSuccessUrl("/home",true)
                            .failureUrl("/Login?error=true").permitAll()).logout(
                                    logout ->logout.disable()
                ).oauth2Login(customizer ->
                        customizer.loginPage("/Login").userInfoEndpoint(oauth ->
                                oauth.oidcUserService(customerOauth2UserService)). // đoạn này là lúc chọn tài khoản google sẽ authen xem tài khoản có tồn tại không xong trả về token cho Spring xử lý
                                // nói ngắn gọn là bình thường cái oidcUserService sẽ là Spring tự cấu hình default thì khi mình đưa
                                        //method mình từ cấu hình vào thì Spring sẽ dùng hàm cusomter0authUserService kia để duyệt data từ google trả về
                        successHandler(oauthSuccessHandler).
                                authorizationEndpoint(authorization -> authorization
                                        .authorizationRequestResolver(
                                                customerAuthorizationreq(clientRegistrationRepository)
                                        ))
                ).
                sessionManagement(
                        session ->
                                session.sessionCreationPolicy(
                        SessionCreationPolicy.IF_REQUIRED).invalidSessionUrl("/login")).
                build();
    }

    private OAuth2AuthorizationRequestResolver customerAuthorizationreq(
            ClientRegistrationRepository repo
    ){
        DefaultOAuth2AuthorizationRequestResolver resolver = new DefaultOAuth2AuthorizationRequestResolver(
                repo,"/oauth2/authorization"
        );
        resolver.setAuthorizationRequestCustomizer(customizer -> customizer.additionalParameters(
                req -> req.put("prompt","select_account")
        ));
        return resolver;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(usersDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(authenticationProvider());
    }
}
