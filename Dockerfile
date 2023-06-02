FROM gradle:7-jdk17 as build
WORKDIR /backend
COPY . .
RUN ./gradlew build --stacktrace 

FROM openjdk:17
WORKDIR /backend
EXPOSE 80
COPY --from=build /backend/build/libs/backend-0.0.1-SNAPSHOT.jar .
COPY static/key.json /backend/static/key.json
CMD java -jar backend-0.0.1-SNAPSHOT.jar