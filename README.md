# README

##  环境准备

```
JDK >= 17 (推荐21版本)
MySQL >= 8 (推荐8.x)
Redis >= 3.0 (推荐5.x或6.x稳定版本)
Maven >= 3.0 (项目打包需要，服务器不需要)
Node >= 12 (推荐16版本)(项目打包需要，服务器不需要)
nginx (无版本要求)
```

## 后端部署

### 数据库搭建

在`ruoyi-admin\src\main\resources\application-druid.yml`中修改以下配置为自己的数据库

```yml
druid:
    # 主库数据源
    master:
    	# 修改localhost:3306 ——> 服务器的ip地址:端口
        url: jdbc:mysql://localhost:3306/data_visual_manage?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
        # 数据库用户名
        username: root
        # 数据库密码
        password: root
```

创建数据库`data_visual_manage` 并导入数据脚本`logic.sql`、`quartz.sql`、`ry_20250522.sql`

### Redis配置

在`ruoyi-admin\src\main\resources\application.yml`中修改Redis配置为服务器的配置

```yml
data:
  # redis 配置
  redis:
    # ip地址
    host: localhost
    # 端口，默认为6379
    port: 6379
    # 数据库索引
    database: 0
    # 密码
    password: root
```

### 文件路径

在配置文件`ruoyi-admin\src\main\resources\application.yml`中修改服务器存放文件路径

```yml
# 项目相关配置
ruoyi:
  # 文件路径 示例（ Windows配置D:/ruoyi/uploadPath，Linux配置 /home/ruoyi/uploadPath）
  profile: D:/ruoyi/uploadPath
```

### 日志存放目录

修改`ruoyi-admin/src/main/resources/logback.xml`中的以下路径可修改日志存放位置

```xml
<configuration>
    <!-- value为日志存放路径 -->
    <property name="log.path" value="/home/ruoyi/logs" />
```

### 项目运行

#### 项目打包

依次运行bin文件夹中的`clean.bat`、`package.bat`后打包项目，在`ruoyi-admin\target`目录中会出现`ruoyi-admin.jar`即为打包成功

#### 项目运行

在`ruoyi-admin.jar`所在目录下运行以下命令即可启动程序

java -jar -Xms256m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m ruoyi-admin.jar

出现以下提示表示启动成功

```
(♥◠‿◠)ﾉﾞ  若依启动成功   ლ(´ڡ`ლ)ﾞ  
 .-------.       ____     __        
 |  _ _   \      \   \   /  /    
 | ( ' )  |       \  _. /  '       
 |(_ o _) /        _( )_ .'         
 | (_,_).' __  ___(_ o _)'          
 |  |\ \  |  ||   |(_,_)'         
 |  | \ `'   /|   `-'  /           
 |  |  \    /  \      /           
 ''-'   `'-'    `-..-'    
```

默认使用端口8080，可以在配置文件`ruoyi-admin\src\main\resources\application.yml`中修改

```yml
# 开发环境配置
server:
  # 服务器的HTTP端口，默认为8080
  port: 8080
```

**windows中可直接运行/bin/run.bat即可启动程序**

****

> **提示：**
>
> 后端运行成功可以通过([http://localhost:8080)访问，但是不会出现静态页面，可以继续参考下面步骤部署`ruoyi-ui`前端，然后通过前端地址来访问。
>
> 项目中修改配置文件之后需要重新打包部署



## 前端部署

#### 项目打包

打开`ruoyi-ui\bin`目录，执行打包命令`build.bat`或在`ruoyi-ui`目录下运行`npm run build:prod`，生成`dist`文件夹 ，前端打包成功

#### 项目部署

在nginx配置文件`nginx.conf`中配置项目访问地址，并配置代理

```js
http {
    include       mime.types;
    default_type  application/octet-stream;

    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #access_log  logs/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;

    #gzip  on;

    # 项目配置
    server {
        # listen下的端口就是代理前的接口，要与前面前端项目的vue.config.js中的端口一致
        listen       81; 
        # server_name是部署项目的服务器ip,即使是使用的本地也建议不要用localhost，避免修改hosts文件导致的问题
        server_name  localhost;

        # location /下面配置的就是代理前前端静态资源的路径等
        location / {
            # root 对应的就是在服务器上前端资源的dist目录的全路径，即代表根路径
            root   D:/code/java-basic/data-visual-manage/ruoyi-ui/dist;
            # 保持默认不要更改，防止404和入口页面
			try_files $uri $uri/ /index.html;
            index  index.html index.htm;
        }

        # location /prod-api/ 是配置的代理后的地址
		location /prod-api/{
		   proxy_set_header Host $http_host;
		   proxy_set_header X-Real-IP $remote_addr;
		   proxy_set_header REMOTE-HOST $remote_addr;
		   proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
           # proxy_pass就是设置的代理后的地址，即自己服务器后台接口的url
		   proxy_pass http://localhost:8080/;
		}

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

    }

    # another virtual host using mix of IP-, name-, and port-based configuration
    #
    #server {
    #    listen       8000;
    #    listen       somename:8080;
    #    server_name  somename  alias  another.alias;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}


    # HTTPS server
    #
    #server {
    #    listen       443 ssl;
    #    server_name  localhost;

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}

}
```

****

> **提示：**
>
> 建议开启Gzip压缩
>
> 在`http`配置中加入如下代码对全局的资源进行压缩，可以减少文件体积和加快网页访问速度。
>
> ```js
> # 开启gzip压缩
> gzip on;
> # 不压缩临界值，大于1K的才压缩，一般不用改
> gzip_min_length 1k;
> # 压缩缓冲区
> gzip_buffers 16 64K;
> # 压缩版本（默认1.1，前端如果是squid2.5请使用1.0）
> gzip_http_version 1.1;
> # 压缩级别，1-10，数字越大压缩的越好，时间也越长
> gzip_comp_level 5;
> # 进行压缩的文件类型
> gzip_types text/plain application/x-javascript text/css application/xml application/javascript;
> # 跟Squid等缓存服务有关，on的话会在Header里增加"Vary: Accept-Encoding"
> gzip_vary on;
> # IE6对Gzip不怎么友好，不给它Gzip了
> gzip_disable "MSIE [1-6]\.";
> ```



## 部署完成

部署完成，浏览器输入http://localhost/81，登录系统

管理员账号admin，密码admin123

系统默认初始密码123456
