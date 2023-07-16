# docker build -t to-do-app-sample .
FROM openjdk:19
ARG USERNAME=to-do-app-sample
RUN adduser $USERNAME
USER $USERNAME
WORKDIR /home/$USERNAME/