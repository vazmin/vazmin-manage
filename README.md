# vazmin-manage

管理平台支持、通用模块。用户角色管理、日志记录、权限控制等。

![security manage screenshot]("https://raw.githubusercontent.com/vazmin/vazmin-manage/master/doc/images/security.png")

![change password screenshot]("https://raw.githubusercontent.com/vazmin/vazmin-manage/master/doc/images/change-password.png")
## How to use ##

使用这个依赖，你必须：
* maven 仓库需安装以下两个仓库：
  ```xml
  <dependencies>
      <dependency>
          <groupId>com.github.vazmin.framework</groupId>
          <artifactId>core</artifactId>
      </dependency>
      <dependency>
          <groupId>com.github.vazmin.framework</groupId>
          <artifactId>web</artifactId>
      </dependency>
  </dependencies>
  ```
  你可以安装到本地仓库或者上传到私有仓库
  ```shell script
  git clone https://github.com/vazmin/vazmin-framework.git
  cd vazmin-framwork
  mvn clean install
  ```

后续模块开发可借助[Code Generator](https://github.com/vazmin/code-generator)生成前后端代码。 
 
 
## Structure
```
+-- vazmin-manage
|   |
|   +-- manage-context           // common pojo
|   |
|   +-- manage-log-context       // log pojo
|   |
|   +-- manage-simples           // simples
|   |   |
|   |   +-- manage-simple-ngx    // Ngx Simples
|   |
|   +-- manage-support           // web support. controller, security, listener...
|   |
|   +-- manage-ui-ngx            // frontend. ngx admin.

```


