# Pré-requisitos

O Spring Security requer um ambiente de tempo de execução Java 8 ou superior.

Como o Spring Security visa operar de maneira autônoma, você não precisa colocar nenhum arquivo de configuração especial em seu Ambiente de Tempo de Execução Java.
Em particular, você não precisa configurar um arquivo de política de Serviço de Autorização e Autenticação Java (JAAS) especial ou colocar o Spring Security em locais de classpath comuns.

Da mesma forma, se você usar um Container EJB ou Container Servlet, você não precisa colocar nenhum arquivo de configuração especial em nenhum lugar nem incluir o Spring Security em um carregador de classe do servidor.
Todos os arquivos necessários estão contidos em seu aplicativo.

Este design oferece máxima flexibilidade no tempo de implantação, pois você pode copiar seu artefato de destino (seja um JAR, WAR ou EAR) de um sistema para outro e ele funciona imediatamente.