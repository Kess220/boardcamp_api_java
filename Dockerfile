# Stage 1: Build
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copia o pom.xml primeiro para aproveitar o cache de dependências do Docker
COPY pom.xml .
# Agora copie o código-fonte e outros arquivos de configuração
COPY . .

# Garanta permissão de execução para o mvnw
RUN chmod +x mvnw

# Executa o Maven build
RUN ./mvnw clean install -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copie o jar construído na etapa anterior
COPY --from=build /app/target/*.jar api.jar

# Exponha a porta 8080
EXPOSE 8080

# Comando para executar a aplicação
CMD ["java", "-jar", "api.jar"]
