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