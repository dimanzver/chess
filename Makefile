migrate:
	./mvnw flyway:migrate -Dflyway.configFile=flyway.properties
shell:
	jshell --class-path=target/classes
run:
	./mvnw spring-boot:run
