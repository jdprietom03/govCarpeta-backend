FROM gradle:7-jdk17 as build
WORKDIR /backend
COPY . .
RUN ./gradlew build --stacktrace 

FROM openjdk:17
WORKDIR /backend
EXPOSE 80
COPY --from=build /backend/build/libs/backend-0.0.1-SNAPSHOT.jar .
COPY /backend/build/resources/main /backend/backend-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!
CMD java -jar backend-0.0.1-SNAPSHOT.jar