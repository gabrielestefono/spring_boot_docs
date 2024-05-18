# Getting Spring Security

A seção descreve como instalar os binários do Spring Security.

// Para ver o código fonte, veja a Seção 03 - Comunidade
/* TODO: Marcar isso no Link como na Doc Oficial - Seção 03*/

## Numeração de Liberação

As versões do Spring Security são formatadas como: MAJOR.MINOR.PATCH, que significa:

MAJOR: Versões que podem ter mudanças drásticas. Normalmente acontecem para melhorar a segurança ou se adequar a novas tecnologias
MINOR: Versões que podem conter melhorias, mas são consideradas mudanças passivas.
PATCH: Essas mudanças devem ser perfeitamente compatíveis, tanto maiores quanto menores, com a excessão de correção de bugs.

## Uso com o Maven

Como a maioria dos projetos open source, Spring Security implanta suas dependências como artefatos Maven. Os tópicos dessa seção descrevem como consumir o Spring Security usando o Maven.

### Spring Boot com o Maven

O Spring Boot, provém um inicializador `spring-boot-starter-security` que agrega as dependências relacionadas ao Spring Security. O modo mais fácil e simples de usar o Spring Security é utilizando o `Spring Initializr`, seja usando a integração de uma IDE como `Eclipse`, `IntelliJ`, `NetBeans` or mesmo usando o `start.spring.io`. Alternativamente, você pode adicionar manualmente o inicializador, como o exemplo a seguir mostra:


```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-security</artifactId>
	</dependency>
</dependencies>
```

O Maven provê BOM (Arquivo POM) para gerenciar suas dependências, a partir disso, você não precisa especificar uma versão. Se você desejar sobrescrever uma versão do Spring Security, você pode fazer isso adicionando uma propriedade Maven:

```xml
<properties>
	<!-- ... -->
	<spring-security.version>6.2.4</spring-security.version>
</properties>
```

Desde que o Spring Security não traga mudanças drásticas, você pode sempre usar as versões mais novas do Spring Security com o Spring Boot. Entretando, às vezes pode ser necessário atualizar a versão do Spring Framework também. Você pode fazer isso adicionando uma propriedade Maven:

```xml
<properties>
	<!-- ... -->
	<spring.version>6.1.6</spring.version>
</properties>
```
Se você usa outras funcionalidades, como LDAP, OAuth 2 e outros, você precisará adicionar apropriadamente os Modules e Dependências.

// Ver sobre Modules e Dependências na seção 09 - Modules de Projetos.
/* TODO: Marcar isso no Link como na Doc Oficial - Seção 09*/

### Mavem Sem Spring Boot

Quando você usa Spring Security sem o Spring Boot, a forma preferencial de uso, é usar o BOM pra ter certeza da consistência da versão do Spring Security que será usada em todo o projeto. O exemplo a seguir mostra como usar:

```xml
<dependencyManagement>
	<dependencies>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-bom</artifactId>
			<version>{spring-security-version}</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>
```

Uma instalação mínima do Spring Security normalmente parece com o código a seguir:


```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.security</groupId>
		<artifactId>spring-security-web</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.security</groupId>
		<artifactId>spring-security-config</artifactId>
	</dependency>
</dependencies>
```

Se você usar uma funcionalidade adicional, como o LDPA, OAuth 2 e outros, você precisará incluir apropriadamente os `Módules e Dependencias`.

// Ver Seção 9 para saber mais sobre módules de dependências.
/* TODO: Marcar isso no Link como na Doc Oficial - Seção 09*/

O Spring Security é construído com base no Spring Framework 6.1.6, mas geralmente deve funcionar com qualquer versão mais recente do Spring Framework 5.x. Muitos usuários provavelmente vão se deparar com o fato de que as dependências transitivas do Spring Security resolvem o Spring Framework 6.1.6, o que pode causar problemas estranhos no classpath. A maneira mais fácil de resolver isso é usar o `spring-framework-bom` na seção `<dependencyManagement>` do seu `pom.xml`:

```xml
<dependencyManagement>
	<dependencies>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-framework-bom</artifactId>
			<version>6.1.6</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>
```

O exemplo a seguir garante que todas as dependências transitivas do Spring Security usam os modules do Spring 6.1.6.

## Repositório Maven

Todas as versão GA (que são as versões com final .RELEASE) são implantadas no Maven Central, então você não precisa declarar nenhum repositório adicional no seu pom.

Se você usar uma versão SNAPSHOT, você precisa ter certeza de que o repositório Spring Snapshot  está definido.

```xml
<repositories>
	<repository>
		<id>spring-snapshot</id>
		<name>Spring Snapshot Repository</name>
		<url>https://repo.spring.io/snapshot</url>
	</repository>
</repositories>
```

Se você usar um milestone ou versão release, você também deve ter certeza de ter o repositórui Spring Milestone definido, como o exemplo a seguir mostra:

```xml
<repositories>
	<repository>
		<id>spring-milestone</id>
		<name>Spring Milestone Repository</name>
		<url>https://repo.spring.io/milestone</url>
	</repository>
</repositories>
```