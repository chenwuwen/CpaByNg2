#!/bin/bash

# 编译所有的代码，生成docker 镜像
function progress_print(){
    local GREEN CLEAN
    GREEN='\033[0;32m'
    CLEAN='\033[0m'
    printf "\n${GREEN}$@  ${CLEAN}\n" >&2
}

# 在"set -e"之后出现的代码，一旦出现了返回值非零，整个脚本就会立即退出。有的人喜欢使用这个参数，是出于保证代码安全性的考虑。但有的时候，这种美好的初衷，也会导致严重的问题(如一些不重要的命令出错导致整个脚本的退出)
set -e

progress_print "Building cpa war file ..."
mvn clean package -Dmaven.test.skip=true
progress_print "Building cpa docker image ..."
docker build --tag cpa:latest .


# 启动docker镜像
progress_print "run cpa docker image"
docker-compose up -d