FROM gcr.io/distroless/java21-debian12
COPY /target/veilarboppgave.jar app.jar
CMD ["app.jar"]
