FROM tomcat:8

MAINTAINER kanyun 2504954849@qq.com

LABEL version="1.0" description="这是CPA后台项目Docker镜像" by="kanyun"

WORKDIR /usr/local/tomcat/webapps

ADD ["*.war","."]

EXPOSE 8899


#使用 catalina.sh run 会显示日志,startup.sh其实就是调用的catalina.sh  ,catalina.sh run 会在启动的同时显示启动日志，关闭窗口或者ctril+c 会不会关闭tomcat
#当我尝试使用命令 docker run -itp 8899:8080 镜像ID bash 启动镜像时,发现不执行CMD中的命令,不执行主要是因为启动的时候指定了shell
#如果需要启动时指定shell,还要在启动时执行相应命令,则使用ENTRYPOINT
#CMD ["catalina.sh", "run"]
ENTRYPOINT ["catalina.sh", "run"]