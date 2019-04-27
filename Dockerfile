FROM tomcat:8

MAINTAINER kanyun 2504954849@qq.com

LABEL version="1.0" description="这是CPA后台项目Docker镜像" by="kanyun"

WORKDIR /usr/local/tomcat/webapp

ADD ["*.war","."]

EXPOSE 8899



CMD ["catalina.sh", "run"]