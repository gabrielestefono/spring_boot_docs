# Autenticação

Spring Security provê um suport compreensivo para `autenticação`. Começaremos a discutir a visão gerak da `Arquitetura de Autenticação Servlet`. Como você deve esperar, essa seção é mais para descrever a arquitetura sem discutir muito sobre como isso se aplica a o fluxo concreto.

Se você preferir, você pode ir a `Mecanismo de Autenticação` para meios concretos de como um usuário pode se autenticar. Essas seções focam em meios específicos que você pode querer para autenticar e aponta de volta às seções de arquitetura para descrever como o fluxo funciona.

## Mecanismo de Autenticação

`Username and Password` - Como autenticar com username e password

`OAuth 2.0 Login` - OAuth 2.0 Log In com OpenID Connect e non-standard OAuth 2.0 Login (i.e. GitHub)

`SAML 2.0 Login` - SAML 2.0 Log In

`Central Authentication Server (CAS)` - Central Authentication Server (CAS) Support

`Remember Me` - Como lembrar a sessão anterior de um usuário após a expiração?

`JAAS Authentication` - Autenticação com JAAS

`Pre-Authentication Scenarios` - Autenticação com um mecanismo externo como o SiteMinder ou Java EE Security, mas ainda usa Spring Security para autenticação e proteção contra brechas comuns.

`X509 Authentication` - Autenticação com X509