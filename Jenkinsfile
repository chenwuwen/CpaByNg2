pipeline {
    # 必选指令 agent 指示 Jenkins 为 Pipeline 分配执行程序和工作空间。没有 agent 指令的话，声明式 Pipeline 无效，无法做任何工作！默认情况下 agent 指令会确保源代码仓库已经检出，并且可用于后续步骤
    # stage 和 step 指令在声明式 Pipeline 中也是必须的，用于指示 Jenkins 执行什么及在哪个 stage 中执行
    agent any

    # 顶级上下文中的 environment 指令会用于 Pipeline 中的所有步骤
    environment {
        CC = 'clang'
     }

    stages {
        stage('Build') {

            # stage 中的 environment 指令仅用于当前 stage 中的步骤
            environment {
                        DEBUG_FLAGS = '-g'
                    }

            steps {
                echo 'Building..'
                # 调用 make 命令
                sh 'make'
                # 捕捉构建时匹配模式 **/target/*.jar 的文件并保存到 Jenkins 主进程供以后检索
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true  // 匹配并保存文件供以后检索
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
