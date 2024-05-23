# Olá, Spring Security!

Esta seção cobre a configuração mínima para iniciar a os estudos do Spring Security com o `Spring Boot` e então te apontar para os próximos passos depois daqui.

## Atualizando Dependências.

Primeiro, você tem que adicionar o Spring Security para a o `classpath` da sua aplicação. Existem 2 formas de fazer isso, usando Maven ou Grandle.

// Para adicionar, ver Seção 07 - Instalando Spring Security
/* TODO: Marcar isso no Link como na Doc Oficial - Seção 07*/

//-** Vou usar o Maven **
/* TODO: Futuramente tentar com o Grandle pra ver a diferença

## Começando o Olá Spring Security Boot

Com o Spring Security no `classpath`, você pode `rodar sua aplicação`. O console mostrará alguns output's que indicam que o Spring Security está instalado:

```bash
$ ./mvnw spring-boot:run
...
INFO 23689 --- [  restartedMain] .s.s.UserDetailsServiceAutoConfiguration :

Using generated security password: 8e557245-73e2-4286-969a-ff57fe326336

...
```

Agora que nós temos ele rodando, você pode tentar acessar um endpoins para ver o que acontece. Se você acessar um endpoint sem credenciais isso acontecerá:

```bash
$ curl -i http://localhost:8080/some/path
HTTP/1.1 401
...
```
Então o Spring Security vai negar o acesso com um erro `401 Unauthorized`.
E se você tentar um endpoint com credenciais, (encontradas no console do output) como a seguir:

```bash
$ curl -i -u user:8e557245-73e2-4286-969a-ff57fe326336 http://localhost:8080/some/path
HTTP/1.1 404
...
```

```markdown
# Nota aqui

Utilizando o Insomnia, o código deve ser colocado na aba Authorization, em Basic Auth, usuário = user, password = código NO OUTPUT
```

Então o Spring Boot irá fazer a requisição retornando erro 404, neste caso, porque a rota /some/path não existe.

**A partir de aqui você pode**
* Entender melhor o que o Spring Boot habilita em Spring Security por padrão. // Ler Expectativas de Runtime
/* TODO: Marcar isso no Link como na Doc Oficial - Essa Mesma Seção*/
* Ler sobre casos comuns em que o Spring Security vai te ajudar. // Ler Use Case de Segurança
/* TODO: Marcar isso no Link como na Doc Oficial - Essa Mesma Seção*/
* Começar configurando a autenticação
/* TODO: Marcar isso no Link como na Doc Oficial - Seção 11.3 - Autenticação*/


From here, you can:

* Better understand <<servlet-hello-auto-configuration,what Spring Boot enables in Spring Security by default>>
* Read about <<security-use-cases,common use cases>> that Spring Security helps with
* Start configuring xref:servlet/authentication/index.adoc[authentication]

[[servlet-hello-auto-configuration]]
== Runtime Expectations

The default arrangement of Spring Boot and Spring Security affords the following behaviors at runtime:

* Requires an authenticated user xref:servlet/authorization/authorize-http-requests.adoc[for any endpoint] (including Boot's `/error` endpoint)
* xref:servlet/authentication/passwords/user-details-service.adoc[Registers a default user] with a generated password at startup (the password is logged to the console; in the preceding example, the password is `8e557245-73e2-4286-969a-ff57fe326336`)
* Protects xref:servlet/authentication/passwords/password-encoder.adoc[password storage with BCrypt] as well as others
* Provides form-based xref:servlet/authentication/passwords/form.adoc[login] and xref:servlet/authentication/logout.adoc[logout] flows
* Authenticates xref:servlet/authentication/passwords/form.adoc[form-based login] as well as xref:servlet/authentication/passwords/basic.adoc[HTTP Basic]
* Provides content negotiation; for web requests, redirects to the login page; for service requests, returns a `401 Unauthorized`
* xref:servlet/exploits/csrf.adoc[Mitigates CSRF] attacks
* xref:servlet/authentication/session-management.adoc#ns-session-fixation[Mitigates Session Fixation] attacks
* Writes xref:servlet/exploits/headers.adoc#servlet-headers-hsts[Strict-Transport-Security] to https://en.wikipedia.org/wiki/HTTP_Strict_Transport_Security[ensure HTTPS]
* Writes xref:servlet/exploits/headers.adoc#servlet-headers-content-type-options[X-Content-Type-Options] to mitigate https://cheatsheetseries.owasp.org/cheatsheets/HTTP_Headers_Cheat_Sheet.html#x-content-type-options[sniffing attacks]
* Writes xref:servlet/exploits/headers.adoc#servlet-headers-cache-control[Cache Control headers] that protect authenticated resources
* Writes xref:servlet/exploits/headers.adoc#servlet-headers-frame-options[X-Frame-Options] to mitigate https://cheatsheetseries.owasp.org/cheatsheets/HTTP_Headers_Cheat_Sheet.html#x-frame-options[Clickjacking]
* Integrates with xref:servlet/integrations/servlet-api.adoc[``HttpServletRequest``'s authentication methods]
* Publishes xref:servlet/authentication/events.adoc[authentication success and failure events]

It can be helpful to understand how Spring Boot is coordinating with Spring Security to achieve this.
Taking a look at {spring-boot-api-url}org/springframework/boot/autoconfigure/security/servlet/SecurityAutoConfiguration.html[Boot's security auto configuration], it does the following (simplified for illustration):

.Spring Boot Security Auto Configuration
[source,java]
----
@EnableWebSecurity <1>
@Configuration
public class DefaultSecurityConfig {
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    InMemoryUserDetailsManager inMemoryUserDetailsManager() { <2>
        String generatedPassword = // ...;
        return new InMemoryUserDetailsManager(User.withUsername("user")
                .password(generatedPassword).roles("ROLE_USER").build());
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationEventPublisher.class)
    DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher(ApplicationEventPublisher delegate) { <3>
        return new DefaultAuthenticationEventPublisher(delegate);
    }
}
----
1. Adds the `@EnableWebSecurity` annotation. (Among other things, this publishes xref:servlet/architecture.adoc#servlet-securityfilterchain[Spring Security's default `Filter` chain] as a `@Bean`)
2. Publishes a xref:servlet/authentication/passwords/user-details-service.adoc[`UserDetailsService`] `@Bean` with a username of `user` and a randomly generated password that is logged to the console
3. Publishes an xref:servlet/authentication/events.adoc[`AuthenticationEventPublisher`] `@Bean` for publishing authentication events

[NOTE]
Spring Boot adds any `Filter` published as a `@Bean` to the application's filter chain.
This means that using `@EnableWebSecurity` in conjunction with Spring Boot automatically registers Spring Security's filter chain for every request.

[[security-use-cases]]
== Security Use Cases

There are a number of places that you may want to go from here.
To figure out what's next for you and your application, consider these common use cases that Spring Security is built to address:

* I am building a REST API, and I need to xref:servlet/oauth2/resource-server/jwt.adoc[authenticate a JWT] or xref:servlet/oauth2/resource-server/opaque-token.adoc[other bearer token]
* I am building a Web Application, API Gateway, or BFF and
** I need to xref:servlet/oauth2/login/core.adoc[login using OAuth 2.0 or OIDC]
** I need to xref:servlet/saml2/login/index.adoc[login using SAML 2.0]
** I need to xref:servlet/authentication/cas.adoc[login using CAS]
* I need to manage
** Users in xref:servlet/authentication/passwords/ldap.adoc[LDAP] or xref:servlet/authentication/passwords/ldap.adoc#_active_directory[Active Directory], with xref:servlet/integrations/data.adoc[Spring Data], or with xref:servlet/authentication/passwords/jdbc.adoc[JDBC]
** xref:servlet/authentication/passwords/storage.adoc[Passwords]

In case none of those match what you are looking for, consider thinking about your application in the following order:

1. *Protocol*: First, consider the protocol your application will use to communicate.
For servlet-based applications, Spring Security supports HTTP as well as xref:servlet/integrations/websocket.adoc[Websockets].
2. *Authentication*: Next, consider how users will xref:servlet/authentication/index.adoc[authenticate] and if that authentication will be stateful or stateless
3. *Authorization*: Then, consider how you will determine xref:servlet/authorization/index.adoc[what a user is authorized to do]
4. *Defense*: Finally, xref:servlet/exploits/csrf.adoc#csrf-considerations[integrate with Spring Security's default protections] and consider xref:servlet/exploits/headers.adoc[which additional protections you need]