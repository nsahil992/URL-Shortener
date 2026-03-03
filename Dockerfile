# ----- BUILD STAGE -----

FROM maven@sha256:8d63d4c1902cb12d9e79a70671b18ebe26358cb592561af33ca1808f00d935cb AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

# ----- RUN STAGE -----

FROM eclipse-temurin:21-jre-alpine AS runtime

WORKDIR /app

RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]