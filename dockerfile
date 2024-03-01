# Utilizando la imagen base Alpine 3.17
FROM alpine:3.17

# Argumento para especificar la versión de Amazon Corretto 17
ARG version=17.0.6.10.1

# Descargar las licencias de terceros y verificar el checksum
RUN wget -O /THIRD-PARTY-LICENSES-20200824.tar.gz https://corretto.aws/downloads/resources/licenses/alpine/THIRD-PARTY-LICENSES-20200824.tar.gz && \
    echo "82f3e50e71b2aee21321b2b33de372feed5befad6ef2196ddec92311bc09becb  /THIRD-PARTY-LICENSES-20200824.tar.gz" | sha256sum -c - && \
    tar xzf THIRD-PARTY-LICENSES-20200824.tar.gz && \
    rm -rf THIRD-PARTY-LICENSES-20200824.tar.gz

# Descargar la clave pública de Amazon Corretto
RUN wget -O /etc/apk/keys/amazoncorretto.rsa.pub https://apk.corretto.aws/amazoncorretto.rsa.pub && \
    SHA_SUM="6cfdf08be09f32ca298e2d5bd4a359ee2b275765c09b56d514624bf831eafb91" && \
    echo "${SHA_SUM}  /etc/apk/keys/amazoncorretto.rsa.pub" | sha256sum -c - && \
    echo "https://apk.corretto.aws" >> /etc/apk/repositories

# Instalar Amazon Corretto 17
RUN apk add --no-cache amazon-corretto-17=$version-r0

# Configurar variables de entorno para Java
ENV LANG C.UTF-8
ENV JAVA_HOME=/usr/lib/jvm/default-jvm
ENV PATH=$PATH:/usr/lib/jvm/default-jvm/bin

# Establecer el directorio de trabajo en /app
WORKDIR /app

# Copiar archivos necesarios para la construcción de dependencias
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# Otorgar permisos de ejecución al script mvnw
RUN chmod +x mvnw

# Descargar las dependencias de Maven
RUN ./mvnw dependency:go-offline

# Copiar el código fuente de la aplicación
COPY src ./src

# Comando por defecto para ejecutar la aplicación Spring Boot
CMD ["./mvnw", "spring-boot:run"]
