#指定基础镜像，并且必须是第一条指令，如果不以任何镜像为基础，那么写法为：FROM scratch,同时意味着接下来所写的指令将作为镜像的第一层开始
#语法：
#FROM <image>
#FROM <image>:<tag>
#FROM <image>:<digest>
#三种写法，其中<tag>和<digest> 是可选项，如果没有选择，那么默认值为latest

#以centos为基础镜像(默认会从dockerhub下载镜像.也可以指定仓库地址Form <host>/<project>/<repo>:<tag>),[也可以使用alipine Docker镜像是只有5M的轻量级Linux系统]
FROM centos:7

#指定镜像作者
MAINTAINER kanyun 2504954849@qq.com

#功能是为镜像指定标签(元数据)
#语法：
#LABEL <key>=<value> <key>=<value> <key>=<value> ...
#一个Dockerfile种可以有多个LABEL,但最好就写成一行，如太长需要换行的话则使用\符号
#如下：
#LABEL multi.label1="value1" \
#multi.label2="value2" \
#other="value3"
#说明：LABEL会继承基础镜像种的LABEL，如遇到key相同，则值覆盖

LABEL version="1.0" description="这是CPA后台项目Docker镜像" by="kanyun"

#功能为设置环境变量
#语法有两种
#1. ENV <key> <value>
#2. ENV <key>=<value> ...
#两者的区别就是第一种是一次设置一个，第二种是一次设置多个

#设置环境变量,通过ENV定义的环境变量，可以被后面的所有指令中使用($MAVEN_HOME),通过ENV定义的环境变量，会永久的保存到该镜像创建的任何容器中
#可以在后续容器的操作中使用
#定义环境变量的同时，可以引用已经定义的环境变量,在ENV指令中，可以直接引用如下环境变量：
#HOME，用户主目录
#HOSTNAME，默认容器的主机名
#PATH，
#TERM，默认xterm

#这样才可以使用 mvn 命令
ENV MAVEN_HOME "/usr/local/apache-maven-3.6.0"
ENV JAVA_HOME "/usr/local/jdk1.8"
ENV CLASSPATH .:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
ENV JAVA_DOWNLOAD_URL "https://download.oracle.com/otn-pub/java/jdk/8u201-b09/42970487e3af4f5aa5bca3f542482c60/jdk-8u201-linux-x64.tar.gz"
ENV PATH $MAVEN_HOME/bin:$PATH
ENV PATH $JAVA_HOME/bin:$PATH

#功能为运行指定的命令，RUN命令有两种格式
#1. RUN <command>
#2. RUN ["executable", "param1", "param2"]
#第一种后边直接跟shell命令
#在linux操作系统上默认 /bin/sh -c
#在windows操作系统上默认 cmd /S /C
#第二种是类似于函数调用。
#可将executable理解成为可执行文件，后面就是两个参数。
#两种写法比对：
#RUN /bin/bash -c 'source $HOME/.bashrc; echo $HOME
#RUN ["/bin/bash", "-c", "echo hello"]
#注意：多行命令不要写多个RUN，原因是Dockerfile中每一个指令都会建立一层.多少个RUN就构建了多少层镜像，会造成镜像的臃肿、多层，不仅仅增加了构件部署的时间，还容易出错,RUN书写时的换行符是 \

#安装git 和 maven
RUN echo  " Start building docker image " \
    echo  " Start downloading essential software " \
    # 从oracle下载jdk到指定目录,由于oracle下载jdk需要认证,所以需要加前缀
    wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" $JAVA_DOWNLOAD_URL  -P /usr/local \
    yum install git -y \
    # 下载maven到指定目录并重命名
    wget http://mirrors.hust.edu.cn/apache/maven/maven-3/3.6.0/binaries/apache-maven-3.6.0-bin.tar.gz -O /usr/local/apache-maven.tar.gz \
    echo  " downloading essential software over " \
    cd /usr/local/ \
    tar xzvf apache-maven.tar.gz  \
    tar xzvf jdk-8u201-linux-x64.tar.gz  \
    mv jdk1* jdk1.8 \
    # 下载项目 -b 选择clone分支
    git clone https://github.com/chenwuwen/CpaByNg2.git -b master




#设置工作目录，对RUN,CMD,ENTRYPOINT,COPY,ADD生效。如果不存在则会创建，也可以设置多次( 切换目录。可以多次切换工作目录)
#如：WORKDIR /a
#WORKDIR b
#WORKDIR c
#RUN pwd
#pwd执行的结果是/a/b/c
#WORKDIR也可以解析环境变量
#如：ENV DIRPATH /path
#WORKDIR $DIRPATH/$DIRNAME
#RUN pwd
#pwd的执行结果是/path/$DIRNAME

#切换到git clone 目录中去（相当于cd）
WORKDIR /usr/local/CpaByNg2

#下载maven依赖(由于oa-remote是原来项目的jar包,不在公网上,所以先把这个包打入到本地)
RUN java -version \
    mvn -version \
    mvn install:install-file -Dfile=lib/oa-remote-1.1.0.jar -DgroupId=com.ruifight -DartifactId=oa-remote -Dversion=1.1.0 -Dpackaging=jar \
    mvm clean install

#功能为暴露容器运行时的监听端口给宿主机,但是EXPOSE并不会使容器访问主机的端口,如果想使得容器与宿主机的端口有映射关系，必须在容器启动的时候加上 -p参数,也可以同时映射多个端口 EXPOSE port1 port2 port3
#8899是java项目暴露端口,22是对于centos进行ssh操作所需端口
EXPOSE 8899 22

#一个复制命令，把文件复制到镜像中。如果把虚拟机与容器想象成两台linux服务器的话，那么这个命令就类似于scp，只是scp需要加用户名和密码的权限验证，而ADD不用
#语法如下：
#1. ADD <src>... <dest>
#2. ADD ["<src>",... "<dest>"]
#<dest>路径的填写可以是容器内的绝对路径，也可以是相对于工作目录的相对路径
#<src>可以是Dockerfile所在目录的一个相对路径或者是一个本地压缩文件（压缩文件自动解压为目录），还可以是一个url
#如果把<src>写成一个url，那么ADD就类似于wget命令
#尽量不要把<src>写成一个文件夹，如果<src>是一个文件夹了，复制整个目录的内容,包括文件系统元数据

#ADD


#一个复制命令.语法如下：
#1. COPY <src>... <dest>
#2. COPY ["<src>",... "<dest>"]
#与ADD的区别:COPY的<src>只能是本地文件，ADD多了自动解压和支持URL路径的功能,其他用法一致

#COPY




#可实现挂载功能，可以将本地文件夹或者其他容器种得文件夹挂在到这个容器中，需要注意的是
#与 docker run --name test -it -v 相比,
#通过命令行 -v 的方式可以轻松指定 宿主机目录与容器的对应关系 如 docker run --name test -it -v <宿主机目录>:<容器挂载点> <镜像> /bin/bash
#通过dockerfile的 VOLUME 指令创建的挂载点，无法指定主机上对应的目录，但是会自动生成的一个宿主机的目录与容器的挂载点(即VOLUME的值)对应
#通过dockerfile的 VOLUME 指令可以在镜像中创建挂载点，这样只要通过该镜像创建的容器都有了挂载点，而通过docker run命令的-v标识创建的挂载点只能对创建的容器有效
#语法为： VOLUME ["/data"]
#["/data"]可以是一个JsonArray ，也可以是多个值。所以如下几种写法都是正确的
#VOLUME ["/var/log/"]
#VOLUME /var/log
#VOLUME /var/log /var/db
#一般的使用场景为需要持久化存储数据时:容器使用的是AUFS，这种文件系统不能持久化数据，当容器关闭后，所有的更改都会丢失。所以当数据需要持久化时用这个命令。

#该挂载目录为程序生成的日志目录,由于是在dockerfile中定义的VOLUME,所以想要知道宿主机对应容器挂载点的目录需要使用docker inspect 查看
VOLUME ["/usr/local/"]


# 设置启动容器的用户，可以是用户名或UID，所以，只有下面的两种写法是正确的
#  USER daemon
#  USER UID
# 注意：如果设置了容器以daemon用户去运行，那么RUN, CMD 和 ENTRYPOINT 都会以这个用户去运行

USER root






#设置变量命令，ARG命令定义了一个变量，在docker build创建镜像的时候，使用 --build-arg <varname>=<value>来指定参数
#如果用户在build镜像时指定了一个参数没有定义在Dockerfile中，那么将有一个Warning
#提示：[Warning] One or more build-args [foo] were not consumed.
#语法：ARG <name>[=<default value>]
#我们可以定义一个或多个参数，如果我们给了ARG定义的参数默认值，那么当build镜像时没有指定参数值，将会使用这个默认值

#ARG


#这个命令只对当前镜像的子镜像生效，比如当前镜像为A，在Dockerfile种添加：ONBUILD RUN ls -al
#这个 ls -al 命令不会在A镜像构建或启动的时候执行,此时有一个镜像B是基于A镜像构建的，那么这个ls -al 命令会在B镜像构建的时候被执行。

ONBUILD RUN echo "welcome to use KANYUN DOCKER base image"


#STOPSIGNAL命令是的作用是当容器退出时给系统发送什么样的指令,也可以通过命令行create/run 的参数 --stop-signal 设置
#默认的stop-signal是SIGTERM，在执行docker stop的时候会给容器内PID为1的进程发送这个signal，
#通过--stop-signal可以设置自己需要的signal，主要的目的是为了让容器内的应用程序在接收到signal之后
#可以先做一些事情，实现容器的平滑退出，如果不做任何处理，容器将在一段时间之后强制退出，会造成业务的强制中断，这个时间默认是10s
#STOPSIGNAL exec echo " sign out docker container "


# 容器健康状况检查命令
#语法有两种：
#1. HEALTHCHECK [OPTIONS] CMD command
#2. HEALTHCHECK NONE
#第一个的功能是在容器内部运行一个命令来检查容器的健康状况
#第二个的功能是在基础镜像中取消健康检查命令
#[OPTIONS]的选项支持以下三中选项：
#    --interval=DURATION 两次检查默认的时间间隔为30秒
#    --timeout=DURATION 健康检查命令运行超时时长，默认30秒
#    --retries=N 当连续失败指定次数后，则容器被认为是不健康的，状态为unhealthy，默认次数是3
#注意：HEALTHCHECK命令只能出现一次，如果出现了多次，只有最后一个生效。
#CMD后边的命令的返回值决定了本次健康检查是否成功，具体的返回值如下：
#0: success - 表示容器是健康的
#1: unhealthy - 表示容器已经不能工作了
#2: reserved - 保留值
#例子：
#HEALTHCHECK --interval=5m --timeout=3s \
#CMD curl -f http://localhost/ || exit 1
#健康检查命令是：curl -f http://localhost/ || exit 1
#两次检查的间隔时间是5秒
#命令超时时间为3秒

#每分钟检查一次，超时时间为3s ,连续检查3次，如果 都返回1表示是健康的
HEALTHCHECK --interval=1m --timeout=3s --retries=3 CMD curl -f http://localhost:8899/ || exit 1




#功能为容器启动时要运行的命令
#语法有三种写法
#1. CMD ["executable","param1","param2"]
#2. CMD ["param1","param2"]
#3. CMD command param1 param2
#第三种比较好理解了，就是shell这种执行方式和写法(该方式是以shell的方式运行程序。最终程序被执行时，类似于/bin/sh -c的方式运行了我们的程序，这样会导致/bin/sh以PID为1的进程运行，而我们的程序只不过是它fork/execs出来的子进程而已。前面我们提到过docker stop的SIGTERM信号只是发送给容器中PID为1的进程，而这样，我们的程序就没法接收和处理到信号了)
#第一种和第二种其实都是可执行文件加上参数的形式(这种方式执行和启动时，我们的程序会被直接启动执行，而不是以shell的方式，这样我们的程序就能以PID=1的方式开始执行了)
#举例说明两种写法：
#CMD [ "sh", "-c", "echo $HOME"]
#CMD [ "echo", "$HOME" ]
#补充细节：这里边包括参数的一定要用双引号，就是",不能是单引号。千万不能写成单引号。原因是参数传递后，docker解析的是一个JSON array
#不要把RUN和CMD搞混了。RUN是构件容器时就运行的命令以及提交运行结果，CMD是容器启动时执行的命令，在构件时并不运行，构件时紧紧指定了这个命令到底是个什么样子

#使用maven Tomcat插件来启动项目
CMD ["mvn","tomcat7:run"]

#功能是启动时的默认命令 语法如下：
#1. ENTRYPOINT ["executable", "param1", "param2"]
# 2. ENTRYPOINT command param1 param2
#与CMD比较说明（这俩命令太像了，而且还可以配合使用）：
#1. 相同点：
# 只能写一条，如果写了多条，那么只有最后一条生效
# 容器启动时才运行(都是在执行docker run指令时运行)，运行时机相同
#2. 不同点：
# ENTRYPOINT不会被运行的command覆盖，而CMD则会被覆盖
# 如果我们在Dockerfile种同时写了ENTRYPOINT和CMD，并且CMD指令不是一个完整的可执行命令，那么CMD指定的内容将会作为ENTRYPOINT的参数
# 如果我们在Dockerfile种同时写了ENTRYPOINT和CMD，并且CMD是一个完整的指令，那么它们两个会互相覆盖，谁在最后谁生效

#ENTRYPOINT