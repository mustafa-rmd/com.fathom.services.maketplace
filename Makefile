# -*- MakeFile -*-
export VERSION := 0.1
export ARTIFACT_ID := marketplaces
export JAR_NAME := marketplaces-*.jar
export DOCK_REPO := b4ft1bt3.gra7.container-registry.ovh.net/library


.PHONY: info
info:
	wget https://fathomsjenkinsdeployment.s3.eu-central-1.amazonaws.com/buildBanner-java.sh
	wget https://fathomsjenkinsdeployment.s3.eu-central-1.amazonaws.com/getAppProp-java.sh
	wget https://fathomsjenkinsdeployment.s3.eu-central-1.amazonaws.com/getLastCommit-java.sh
	wget https://fathomsjenkinsdeployment.s3.eu-central-1.amazonaws.com/getVersion-java.sh
	chmod +x buildBanner-java.sh getAppProp-java.sh getLastCommit-java.sh getVersion-java.sh
	./buildBanner-java.sh

mvnw:
	mvn -N io.takari:maven:wrapper

mvnw.cmd:
	mvn -N io.takari:maven:wrapper

.PHONY: clean
clean: mvnw mvnw.cmd
	./mvnw clean

.PHONY: compile
compile: mvnw mvnw.cmd info
	./mvnw compile

.PHONY: package
package: compile
	./mvnw package -Dmaven.test.skip=true

.PHONY: repackage
repackage: clean package

.PHONY: build-artifact
build-artifact: clean info
	echo "Build myservice service jar"
	./mvnw install

.PHONY: docker
docker: info
	docker build -t $(DOCK_REPO)/$(ARTIFACT_ID):$(VERSION) --build-arg JAR_PATH="target/$(JAR_NAME)" .

.PHONY: docker-latest
docker-latest:
	docker tag $(DOCK_REPO)/$(ARTIFACT_ID):$(VERSION) $(DOCK_REPO)/$(ARTIFACT_ID):latest

.PHONY: docker-nexus
docker-nexus:
	docker push $(DOCK_REPO)/$(ARTIFACT_ID):latest