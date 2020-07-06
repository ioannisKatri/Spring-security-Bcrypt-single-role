FROM tomcat:8.5.54

COPY tomcat-users.xml /usr/local/tomcat/conf

COPY server.xml /usr/local/tomcat/conf

ENV JPDA_ADDRESS=8000
ENV JPDA_TRANSPORT=dt_socket

CMD ["catalina.sh", "jpda", "run"]