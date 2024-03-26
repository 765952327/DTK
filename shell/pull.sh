#!/bin/bash
#!key front_pull

# ================================ 插件校验 ========================================
# 检查是否安装了jq
if ! command -v jq &> /dev/null; then
    echo "jq is not installed. Installing jq..."

    # 根据不同系统类型执行对应的安装命令
    if [ -x "$(command -v apt-get)" ]; then
        sudo apt-get update
        sudo apt-get install jq
    elif [ -x "$(command -v yum)" ]; then
        sudo yum install epel-release
        sudo yum install jq
    elif [ -x "$(command -v brew)" ]; then
        brew install jq
    else
        echo "Unsupported package manager. Please install jq manually."
        exit 1
    fi

    # 验证安装是否成功
    if ! command -v jq &> /dev/null; then
        echo "jq installation failed. Please install jq manually."
        exit 1
    fi

    echo "jq has been successfully installed."
else
    echo "jq is already installed."
fi

# Check if sshpass is installed
if command -v sshpass &> /dev/null
then
    echo "sshpass is already installed."
else
    # Install sshpass using Homebrew
    if command -v brew &> /dev/null
    then
        brew install sshpass
    else
        echo "Homebrew is not installed. Please install Homebrew to proceed with the installation of sshpass."
        exit 1
    fi

    # Check if installation was successful
    if [ $? -eq 0 ]
    then
        echo "sshpass has been successfully installed."
    else
        echo "Failed to install sshpass."
        exit 1
    fi
fi
# ================================ 配置 ========================================
# 拉取前端配置
remote_user="root"
remote_password="qiyuesuo"
remote_folder_base="/opt/qiyuesuo/resources/"
remote_folders=("js" "views" "css" "fonts" "img")
plm_url="https://plm.qiyuesuo.me/api/machine/query"
# ================================ 脚本 ========================================

local_folder_base=""
input_project=""
env=""

while getopts ":d:p:e:" opt; do
    case $opt in
        d) local_folder_base="$OPTARG";;
        p) input_project="$OPTARG";;
        e) env="$OPTARG";;
        \?) echo "无效选项: -$OPTARG" >&2;;
    esac
done


# 发起GET请求并将压缩后的响应保存到response.txt.gz文件中
curl -X GET -H "Accept-Encoding: gzip" $plm_url -o response.txt.gz

# 解压缩响应内容
gunzip -c response.txt.gz > response.txt

# 输出响应内容
json_data=$(cat response.txt)
devList=$(echo $json_data | jq -r '.devList')
prodList=$(echo $json_data | jq '.prodList')



if [ -z "$input_project" ]; then
  read -p "请输入项目名称: " input_project
fi

if [ "$env" == "prod" ]; then
    ip=$(echo "$prodList" | jq --arg project "$input_project" '.[] | select(.project == $project) | .ip')
else
    ip=$(echo "$devList" | jq --arg project "$input_project" '.[] | select(.project == $project) | .ip')
fi

if [ -z "$ip" ]; then
  echo "没有找到${input_project}项目IP"
  exit 1
fi
# 远程服务器信息
remote_host=$(echo $ip | tr -d '"')
echo "${input_project} host ${remote_host}"
for subfix in "${remote_folders[@]}"
do
    remote_path="${remote_folder_base}${subfix}"
    local_path="${local_folder_base}/${input_project}"
    echo "start copy file from：${remote_path} to ${local_path}"
    mkdir "${local_folder_base}${input_project}"
    mkdir "${local_path}"
    sshpass -p "$remote_password" scp -r $remote_user@$remote_host:$remote_path $local_path
    echo "End"
done








